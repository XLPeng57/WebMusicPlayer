package com.example.musicserver.service;

import com.example.musicserver.model.domain.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface IBannerService extends IService<Banner> {

    List<Banner> getAllBanner();
}
