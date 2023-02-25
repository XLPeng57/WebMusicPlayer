package com.example.musicserver.service;

import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.SongList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicserver.model.request.SongListRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface ISongListService extends IService<SongList> {

    R addSongList(SongListRequest addSongListRequest);

    R updateSongListInfo(SongListRequest updateSongListRequest);

    R updateSongListImg(MultipartFile avatorFile, int id);

    R deleteSongList(Integer id);

    R allSongList();

    R likeTitle(String title);

    R likeStyle(String style);


}
