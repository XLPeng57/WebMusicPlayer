package com.example.musicserver.mapper;

import com.example.musicserver.model.domain.RankList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface RankListMapper extends BaseMapper<RankList> {

    int selectScoreSum(Long songListId);

    int selectUserRank(@Param("consumerId") Long consumerId, @Param("consumerId")  Long songListId);

}
