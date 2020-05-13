package com.learn.elsearch.service;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class JoupServiceImpl implements JoupService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Override
    public List<Map<String,String>> getJoup(String keyWord)throws IOException {
        String url="https://search.jd.com/Search?keyword="+keyWord;
        Connection connection=Jsoup.connect(url);
        Document document = connection.get();
        Element element=document.getElementById("J_goodsList");
        Elements elements=element.getElementsByTag("li");
        String price=null;
        String name=null;
        String img=null;
        List<Map<String,String>> list=new ArrayList<>(30);
        Map<String,String> map=null;
        BulkRequest request = new BulkRequest();
        for (int i=0;i<elements.size();i++){
            Element el=elements.get(i);
            map=new HashMap<>(16);
            price=el.getElementsByClass("p-price").eq(0).text();
            name=el.getElementsByClass("p-name") .eq(0).text();
            map.put("price",price);
            map.put("name",name);
            list.add(map);
            request.add(new IndexRequest("book").id(String.valueOf(i)).source(XContentType.JSON,"price",price,"name",name));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return list;
    }
    @Override
    public List<Map<String,String>> search(String keyWord)throws IOException {
        String url="https://search.jd.com/Search?keyword="+keyWord;
        Connection connection=Jsoup.connect(url);
        Document document = connection.get();
        Element element=document.getElementById("J_goodsList");
        Elements elements=element.getElementsByTag("li");
        String price=null;
        String name=null;
        String img=null;
        List<Map<String,String>> list=new ArrayList<>(30);
        Map<String,String> map=null;
        for (int i=0;i<elements.size();i++){
            Element el=elements.get(i);
            map=new HashMap<>(16);
            price=el.getElementsByClass("p-price").eq(0).text();
            name=el.getElementsByClass("p-name") .eq(0).text();
            map.put("price",price);
            map.put("name",name);
            list.add(map);
        }
        SearchRequest searchRequest = new SearchRequest("book");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("name", keyWord));
        sourceBuilder.from(0);
        sourceBuilder.size(50);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle =
                new HighlightBuilder.Field("name");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);
        sourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(sourceBuilder);
        System.out.println("查询语句："+searchRequest.toString());
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse);
        SearchHits searchHits=searchResponse.getHits();
        SearchHit[] sh=searchHits.getHits();
        Set<String> set=new HashSet<>();
        for(SearchHit searchHit:sh){
            set.add(searchHit.getId());
        }
        System.out.println(set+":"+set.size());
        for(int i=0;i<=34;i++){
            boolean b=set.add(String.valueOf(i));
            if(b){
                System.out.println("没有找到的是"+i);
            }
        }

        return list;
    }
}
