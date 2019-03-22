package com.wh.web.myweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wh.web.myweb.dao.po.PhotoPO;
import com.wh.web.myweb.model.bo.PhotoBO;
import com.wh.web.myweb.service.IPhotoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("photo")
public class PhotoController {

    @Autowired
    private IPhotoService iPhotoService;

    @PreAuthorize("hasAuthority('boss')")
    @PostMapping("addPhoto")
    public Object addPhoto(@RequestBody PhotoBO photoBO) {
        PhotoPO photoPO = new PhotoPO();
        BeanUtils.copyProperties(photoBO, photoPO);
        return iPhotoService.save(photoPO);
    }

    @PreAuthorize("hasAuthority('staff')")
    @GetMapping("queryPhoto")
    public Object queryPhoto(@RequestParam("photoName") String photoName) {
        PhotoPO photoPO = new PhotoPO();
        photoPO.setName(photoName);
        QueryWrapper queryWrapper = new QueryWrapper(photoPO);
        return iPhotoService.list(queryWrapper);
    }
}
