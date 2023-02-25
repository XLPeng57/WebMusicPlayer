package com.example.musicserver.controller;


import com.example.musicserver.common.R;
import com.example.musicserver.model.request.AdminRequest;
import com.example.musicserver.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @PostMapping("admin/login/status")
    public R loginStatus(@RequestBody AdminRequest adminRequest, HttpSession session) {
        return adminService.verifyPassword(adminRequest, session);
    }

}

