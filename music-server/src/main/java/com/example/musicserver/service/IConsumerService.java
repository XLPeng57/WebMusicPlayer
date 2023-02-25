package com.example.musicserver.service;

import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.Consumer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicserver.model.request.ConsumerRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface IConsumerService extends IService<Consumer> {

    R addUser(ConsumerRequest registryRequest);

    R updateUserInfo(ConsumerRequest updateRequest);

    R updateUserAvator(MultipartFile avatorFile, int id);

    R updatePassword(ConsumerRequest updatePasswordRequest);

    boolean existUser(String username);

    boolean verifyPassword(String username, String password);

    R deleteUser(Integer id);

    R allUser();

    R userOfId(Integer id);

    R loginStatus(ConsumerRequest loginRequest, HttpSession session);



}
