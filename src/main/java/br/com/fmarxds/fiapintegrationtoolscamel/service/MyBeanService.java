package br.com.fmarxds.fiapintegrationtoolscamel.service;

import br.com.fmarxds.fiapintegrationtoolscamel.bean.MyBean;

import java.util.Random;

public class MyBeanService {

    public static void example(MyBean bean) {
        bean.setId(new Random().nextInt());
        bean.setName("Hello, meu primeiro método Camel");
    }

}
