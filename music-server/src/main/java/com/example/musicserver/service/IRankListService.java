package com.example.musicserver.service;

import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.RankList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicserver.model.request.RankListRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface IRankListService extends IService<RankList> {


    R addRank(RankListRequest rankListAddRequest);

    R rankOfSongListId(Long songListId);

    R getUserRank(Long consumerId, Long songListId);

}
