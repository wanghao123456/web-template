package com.wh.web.myweb.dao.po.two;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by superman on 2019/1/1.
 */

@Data
@TableName("photo")
public class PhotoTwoPO {

    private Long id;
    private String name;
    private String url;
}
