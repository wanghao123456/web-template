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
    private PhotoService photoService;

    @Autowired
    private PhotoMapperOne photoMapperOne;

    @Autowired
    private PhotoMapperTwo photoMapperTwo;


    @Override
    @Transactional(transactionManager = "TransactionManagerOne", rollbackFor = Exception.class)
    public void baseOne(PhotoBO photoBO, Propagation propagation, boolean baseException, boolean testException) {
        insertPhotoOne(photoBO, false);
        selectPropagation(photoBO, propagation, testException);
        if (baseException) {
            throw new RuntimeException("baseOne");
        }
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", rollbackFor = Exception.class)
    public void baseTwo(PhotoBO photoBO, Propagation propagation, boolean baseException, boolean testException) {
        insertPhotoTwo(photoBO, false);
        selectPropagation(photoBO, propagation, testException);
        if (baseException) {
            throw new RuntimeException("baseTwo");
        }
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testRequired(PhotoBO photoBO, boolean testException) {
        insertPhotoTwo(photoBO, testException);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void testSupports(PhotoBO photoBO, boolean testException) {
        insertPhotoTwo(photoBO, testException);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void testMandatory(PhotoBO photoBO, boolean testException) {
        insertPhotoTwo(photoBO, testException);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void testRequiresNew(PhotoBO photoBO, boolean testException) {
        insertPhotoTwo(photoBO, testException);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void testNotSupported(PhotoBO photoBO, boolean testException) {
        insertPhotoTwo(photoBO, testException);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void testNever(PhotoBO photoBO, boolean testException) {
        insertPhotoTwo(photoBO, testException);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerTwo", propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void testNested(PhotoBO photoBO, boolean testException) {
        insertPhotoTwo(photoBO, testException);
    }

    @Override
    @Transactional(transactionManager = "TransactionManagerOne", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void differentDataSourceDifferentTransactionManager(PhotoBO photoBO, boolean testException) {
        insertPhotoTwo(photoBO, testException);
    }

    private void insertPhotoOne(PhotoBO photoBO, boolean testException) {
        PhotoOnePO photoOnePO = new PhotoOnePO();
        BeanUtils.copyProperties(photoBO, photoOnePO);
        photoMapperOne.insert(photoOnePO);
        if (testException) {
            throw new RuntimeException("insertPhotoOne");
        }
    }

    private void insertPhotoTwo(PhotoBO photoBO, boolean testException) {
        PhotoTwoPO photoTwoPO = new PhotoTwoPO();
        BeanUtils.copyProperties(photoBO, photoTwoPO);
        photoMapperTwo.insert(photoTwoPO);
        if (testException) {
            throw new RuntimeException("insertPhotoTwo");
        }
    }

    private void selectPropagation(PhotoBO photoBO, Propagation propagation, boolean testException) {
        try {
            switch (propagation) {
                case REQUIRED:
                    photoService.testRequired(photoBO, testException);
                    break;
                case REQUIRES_NEW:
                    photoService.testRequiresNew(photoBO, testException);
                    break;
                case SUPPORTS:
                    photoService.testSupports(photoBO, testException);
                    break;
                case NOT_SUPPORTED:
                    photoService.testNotSupported(photoBO, testException);
                    break;
                case MANDATORY:
                    photoService.testMandatory(photoBO, testException);
                    break;
                case NEVER:
                    photoService.testNever(photoBO, testException);
                    break;
                case NESTED:
                    photoService.testNested(photoBO, testException);
                    break;
            }
        } catch (Exception e) {
        }
    }
}
