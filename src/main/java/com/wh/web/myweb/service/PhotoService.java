package com.wh.web.myweb.service;

import com.wh.web.myweb.bo.PhotoBO;

public interface PhotoService {

    void base(PhotoBO photoBO);

    void testRequired(PhotoBO photoBO);

    void testSupports(PhotoBO photoBO);

    void testMandatory(PhotoBO photoBO);

    void testRequiresNew(PhotoBO photoBO);

    void testNotSupported(PhotoBO photoBO);

    void testNever(PhotoBO photoBO);

    void testNested(PhotoBO photoBO);
}
