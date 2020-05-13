package com.learn.elsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface JoupService {
    List<Map<String,String>> getJoup(String keyWord) throws IOException;
    List<Map<String,String>> search(String keyWord) throws IOException;
}
