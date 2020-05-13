package com.learn.elsearch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.jni.Address;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vo.UserVO;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
class ElsearchApplicationTests {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //创建索引测试,测试索引是否存在
    @Test
    void testCreateIndex() {
        //创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("springboot");
        GetIndexRequest getIndexRequest = new GetIndexRequest("springboot");
        CreateIndexResponse createIndexResponse = null;
        IndicesClient indicesClient = restHighLevelClient.indices();
        try {
            //测试索引是否存在
            boolean exists = indicesClient.exists(getIndexRequest, RequestOptions.DEFAULT);
            System.out.println("测试索引是否创建成功:" + exists);
            if (!exists) {
                createIndexResponse = indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
                System.out.println(createIndexResponse.isAcknowledged());
            }

        } catch (IOException e) {
            System.out.println("异常：" + e.getMessage());
        }
    }

    //测试删除索引
    @Test
    void testDelIndex() {
        DeleteIndexRequest request = new DeleteIndexRequest("book");
        IndicesClient indicesClient = restHighLevelClient.indices();
        try {
            AcknowledgedResponse acknowledgedResponse = indicesClient.delete(request, RequestOptions.DEFAULT);
            System.out.println("是否成功：" + acknowledgedResponse.isAcknowledged());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 测试添加文档数据
     */
    @Test
    void testAddDoc() {
        IndexRequest request = new IndexRequest("springboot");
        request.id("11");
        UserVO userVO = new UserVO();
        userVO.setName("李四");
        userVO.setAddress("山西");
        userVO.setAge(14);
        IndexRequest indexRequest = request.source(JSON.toJSONString(userVO), XContentType.JSON);
        IndexResponse indexResponse = null;
        try {
            indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(indexResponse.toString());
            System.out.println(indexResponse.getResult());
            System.out.println(indexResponse.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试异步添加文档数据
     */
    @Test
    void testAddAsynDoc() {
        IndexRequest request = new IndexRequest("springboot");
        request.id("2");
        UserVO userVO = new UserVO();
        userVO.setName("李四");
        userVO.setAddress("山西");
        userVO.setAge(14);
        IndexRequest indexRequest = request.source(JSONObject.toJSON(userVO), XContentType.JSON);
        restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println("响应数据" + indexResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("发生异常：" + e.getMessage());
            }
        });
    }

    /**
     * 根据id测试文档是否存在
     *
     * @throws IOException
     */
    @Test
    void testIsExitsDoc() throws IOException {
        GetRequest getRequest = new GetRequest("springboot", "2");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 测试根据id获取文档信息
     *
     * @throws IOException
     */
    @Test
    void testContentDoc() throws IOException {
        GetRequest getRequest = new GetRequest("springboot", "2");
        getRequest.fetchSourceContext(FetchSourceContext.FETCH_SOURCE);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.toString());
        System.out.println(getResponse.getSource());
    }

    /**
     * 测试更新文档
     * @throws IOException
     */
    @Test
    void testUpdateDoc()throws IOException{
        UpdateRequest request = new UpdateRequest("springboot", "3");
        UserVO userVO = new UserVO();
        userVO.setName("李四");
        userVO.setAddress("北京");
        userVO.setAge(15);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", userVO.getName());
        jsonMap.put("address", userVO.getAddress());
        jsonMap.put("age", userVO.getAge());
        request.doc(jsonMap);
        UpdateResponse updateResponse = restHighLevelClient.update(
                request, RequestOptions.DEFAULT);
        System.out.println(updateResponse.toString());
    }

    /**
     * 测试删除文档内容
     * @throws IOException
     */
    @Test
    void testDelDoc()throws IOException{
        DeleteRequest request = new DeleteRequest("springboot", "2");
        DeleteResponse deleteResponse = restHighLevelClient.delete(
                request, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.getResult());
    }

    /**
     * 批量添加数据
     * @throws IOException
     */
    @Test
    void testBulkAdd()throws  IOException{
        BulkRequest request = new BulkRequest();
        IndexRequest indexRequest=null;
        for (int i = 4; i < 10; i++) {
            indexRequest= new IndexRequest("springboot");
            indexRequest.id(String.valueOf(i)).source(XContentType.JSON,"name",i,"age",i+1, "address",i+2);
            request.add(indexRequest);
        }
        BulkResponse bulkResponse =restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.toString());
    }
    @Test
    void testSearch()throws  IOException{
        SearchRequest searchRequest = new SearchRequest("springboot");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("name", "李四"));
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.toString());

    }

}
