package com.example.musicserver.service;

import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicserver.model.request.AdminRequest;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface IAdminService extends IService<Admin> {
    R verifyPassword(AdminRequest adminRequest, HttpSession sesson);

}
