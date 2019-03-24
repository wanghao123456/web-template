package com.wh.web.myweb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wh.web.myweb.dao.mapper.PhotoMapper;
import com.wh.web.myweb.dao.po.PhotoPO;
import com.wh.web.myweb.service.IPhotoService;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, PhotoPO> implements IPhotoService {

}
