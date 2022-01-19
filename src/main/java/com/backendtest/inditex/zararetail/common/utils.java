package com.backendtest.inditex.zararetail.common;

import java.util.ArrayList;
import java.util.List;

public class utils {

    public static String urlBuilder(String url, String endpoint) {
        return new StringBuilder().append(url).append(endpoint).toString();
    }

    public static <T> List<T> convertArrayToList(T array[]) {
        List<T> list = new ArrayList<>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }
}
