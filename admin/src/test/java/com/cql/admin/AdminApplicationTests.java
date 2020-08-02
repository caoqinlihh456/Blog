package com.cql.admin;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = {"com.cql"}) //包扫描
class AdminApplicationTests {

    @Test
    void contextLoads() {
    }

}
