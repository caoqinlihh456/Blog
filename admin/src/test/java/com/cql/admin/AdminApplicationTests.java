package com.cql.admin;

import com.cql.user.entity.Cat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class AdminApplicationTests {

    @Autowired
    private Cat cat;

    @Test
    public void contextLoads() {
        System.out.println(cat);
    }

    @Test
    public void asd(){

    }

}
