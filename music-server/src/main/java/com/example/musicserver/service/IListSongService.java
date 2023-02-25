package com.example.musicserver.service;

import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.ListSong;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicserver.model.request.ListSongRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface IListSongService extends IService<ListSong> {

    R addListSong(ListSongRequest addListSongRequest);

    R updateListSongInfo(ListSongRequest updateListSongRequest);

    R deleteListSong(Integer songId);

    //看看这啥
    List<ListSong> allListSong();

    R listSongOfSongId(Integer songListId);

    R allListSongs();

}
