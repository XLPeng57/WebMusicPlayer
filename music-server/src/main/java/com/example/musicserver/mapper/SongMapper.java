package com.example.musicserver.mapper;

import com.example.musicserver.model.domain.Song;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
@Repository
public interface SongMapper extends BaseMapper<Song> {

}
