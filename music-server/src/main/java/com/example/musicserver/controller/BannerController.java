package com.example.musicserver.controller;


import com.example.musicserver.common.R;
import com.example.musicserver.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/banner")
@CrossOrigin
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        return R.success("Banner Success!",bannerService.getAllBanner());
    }

}

