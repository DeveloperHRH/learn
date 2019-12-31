package com.learn.management.auto;


import com.learn.management.LearnManagementSystemApplication;
import com.learn.management.SpringContext;
import com.learn.management.util.AutoGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;


/**
 * 生成数据文件
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LearnManagementSystemApplication.class)
public class AutoGeneratorTest {
    @Test
    public void testGenerate() {
        ApplicationContext BF = SpringContext.getApplicationContext();
        DataSource ds = (DataSource) BF.getBean("dataSource");
        AutoGenerator auto = new AutoGenerator(ds);
//        auto.createNew("learn-management-system", "resources/mybatis-mapper", "learn_sys_timetask", "learn_sys_timetask", "com.learn.management", "learn-management-system-rpc", "com.learn.management.system.rpc","learn-management-system","com.learn.management", "learn");
    }
}