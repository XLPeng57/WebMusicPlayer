package com.example.musicserver.service;

import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.Song;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicserver.model.request.SongRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface ISongService extends IService<Song> {

    R addSong (SongRequest addSongRequest, MultipartFile mpfile);

    R updateSongInfo(SongRequest updateSongRequest);

    R updateSongUrl(MultipartFile urlFile, int id);

    R updateSongPic(MultipartFile urlFile, int id);

    R deleteSong(Integer id);

    R allSong();

    R songOfSingerId(Integer singerId);

    R songOfId(Integer id);

    R songOfSingerName(String name);
}
