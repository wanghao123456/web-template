package com.wh.web.myweb.service;

import com.wh.web.myweb.bo.PhotoBO;
import org.springframework.transaction.annotation.Propagation;

public interface PhotoService {

    void baseOne(PhotoBO photoBO, Propagation propagation, boolean baseException, boolean testException);

    void baseTwo(PhotoBO photoBO, Propagation propagation, boolean baseException, boolean testException);

    void testRequired(PhotoBO photoBO, boolean testException);

    void testSupports(PhotoBO photoBO, boolean testException);

    void testMandatory(PhotoBO photoBO, boolean testException);

    void testRequiresNew(PhotoBO photoBO, boolean testException);

    void testNotSupported(PhotoBO photoBO, boolean testException);

    void testNever(PhotoBO photoBO, boolean testException);

    void testNested(PhotoBO photoBO, boolean testException);

    void differentDataSourceDifferentTransactionManager(PhotoBO photoBO, boolean testException);
}
