package com.example.musicserver.service;

import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.Singer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicserver.model.request.SingerRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface ISingerService extends IService<Singer> {

    R addSinger(SingerRequest addSingerRequest);
    R updateSingerMsg(SingerRequest updateSingerRequest);
    R updateSingerPic(MultipartFile avatorFile, int id);
    R deleteSinger(Integer id);
    R allSinger();

    R singerOfName(String name);
    R singerOfSex(Integer sex);
}
