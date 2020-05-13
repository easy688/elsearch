package com.learn.elsearch;

import org.elasticsearch.action.ActionListener;
import org.springframework.stereotype.Component;

@Component
public class MyListen<IndexResponse> implements ActionListener<IndexResponse> {
    @Override
    public void onResponse(IndexResponse indexResponse) {
        System.out.println("相应数据"+indexResponse.toString());
    }

    @Override
    public void onFailure(Exception e) {

    }
}
