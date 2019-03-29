package com.wh.web.myweb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wh.web.myweb.dao.mapper.PhotoMapper;
import com.wh.web.myweb.dao.po.PhotoPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MywebApplicationTests {

    @Autowired
    private PhotoMapper photoMapper;

    @Test
    public void contextLoads() {
        PhotoPO photoPO = new PhotoPO();
        photoPO.setId(666L);
        QueryWrapper queryWrapper = new QueryWrapper(photoPO);
        System.out.println(photoMapper.selectCount(queryWrapper));
        System.out.println(photoMapper.selectList(queryWrapper));
        Page page = new Page();
        page.setSize(100);
        page.setCurrent(1);
        photoMapper.selectPage(page, null);
        System.out.println(page.getRecords());
    }

}

