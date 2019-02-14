package com.wh.web.myweb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wh.web.myweb.dao.mapper.one.PhotoMapperOne;
import com.wh.web.myweb.dao.mapper.two.PhotoMapperTwo;
import com.wh.web.myweb.dao.po.one.PhotoOnePO;
import com.wh.web.myweb.dao.po.two.PhotoTwoPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MywebApplicationTests {

    @Autowired
    private PhotoMapperOne photoMapperOne;
    @Autowired
    private PhotoMapperTwo photoMapperTwo;

    @Test
    public void contextLoads() {
        PhotoOnePO photoOnePO = new PhotoOnePO();
        photoOnePO.setId(1L);
        QueryWrapper queryWrapperOne = new QueryWrapper(photoOnePO);
        System.out.println(photoMapperOne.selectCount(queryWrapperOne));
        System.out.println(photoMapperOne.selectList(queryWrapperOne));
        Page pageOne = new Page();
        pageOne.setSize(100);
        pageOne.setCurrent(1);
        photoMapperOne.selectPage(pageOne, null);
        System.out.println(pageOne.getRecords());


        PhotoTwoPO photoTwoPO = new PhotoTwoPO();
        photoTwoPO.setId(1L);
        QueryWrapper queryWrapperTwo = new QueryWrapper(photoTwoPO);
        System.out.println(photoMapperTwo.selectCount(queryWrapperTwo));
        System.out.println(photoMapperTwo.selectList(queryWrapperTwo));
        Page pageTwo = new Page();
        pageTwo.setSize(100);
        pageTwo.setCurrent(1);
        photoMapperTwo.selectPage(pageTwo, null);
        System.out.println(pageTwo.getRecords());
    }

}

