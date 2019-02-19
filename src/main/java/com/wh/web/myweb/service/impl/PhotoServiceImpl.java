package com.wh.web.myweb.service.impl;

import com.wh.web.myweb.bo.PhotoBO;
import com.wh.web.myweb.dao.mapper.one.PhotoMapperOne;
import com.wh.web.myweb.dao.mapper.two.PhotoMapperTwo;
import com.wh.web.myweb.dao.po.one.PhotoOnePO;
import com.wh.web.myweb.dao.po.two.PhotoTwoPO;
import com.wh.web.myweb.service.PhotoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoMapperOne photoMapperOne;

    @Autowired
    private PhotoMapperTwo photoMapperTwo;


    @Override
    @Transactional(transactionManager = "TransactionManagerOne", rollbackFor = Exception.class)
    public void base(PhotoBO photoBO) {
        insertPhotoOne(photoBO);

        testRequired(photoBO);
        testRequiresNew(photoBO);
        testSupports(photoBO);
        testNotSupported(photoBO);
        testNested(photoBO);
        testMandatory(photoBO);
        testNever(photoBO);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired(PhotoBO photoBO) {
        insertPhotoTwo(photoBO);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void testSupports(PhotoBO photoBO) {
        insertPhotoTwo(photoBO);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void testMandatory(PhotoBO photoBO) {
        insertPhotoTwo(photoBO);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiresNew(PhotoBO photoBO) {
        insertPhotoTwo(photoBO);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void testNotSupported(PhotoBO photoBO) {
        insertPhotoTwo(photoBO);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void testNever(PhotoBO photoBO) {
        insertPhotoTwo(photoBO);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void testNested(PhotoBO photoBO) {
        insertPhotoTwo(photoBO);
    }

    private void insertPhotoOne(PhotoBO photoBO) {
        PhotoOnePO photoOnePO = new PhotoOnePO();
        BeanUtils.copyProperties(photoBO, photoOnePO);
        photoMapperOne.insert(photoOnePO);
    }

    private void insertPhotoTwo(PhotoBO photoBO) {
        PhotoTwoPO photoTwoPO = new PhotoTwoPO();
        BeanUtils.copyProperties(photoBO, photoTwoPO);
        photoMapperTwo.insert(photoTwoPO);
    }
}
