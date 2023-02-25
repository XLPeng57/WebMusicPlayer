package com.example.musicserver.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.RankList;
import com.example.musicserver.mapper.RankListMapper;
import com.example.musicserver.model.request.RankListRequest;
import com.example.musicserver.service.IRankListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
@Service
public class RankListServiceImpl extends ServiceImpl<RankListMapper, RankList> implements IRankListService {

    @Autowired
    private RankListMapper rankMapper;

    @Override
    public R addRank(RankListRequest rankListAddRequest) {
        RankList rankList = new RankList();
        BeanUtils.copyProperties(rankListAddRequest, rankList);
        if (rankMapper.insert(rankList) > 0) {
            return R.success("评价成功");
        } else {
            return R.error("评价失败");
        }
    }

    @Override
    public R rankOfSongListId(Long songListId) {
        QueryWrapper<RankList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("songListId",songListId);
        Long rankNum = Long.valueOf(rankMapper.selectCount(queryWrapper));
        return R.success(null, (rankNum <= 0) ? 0 : rankMapper.selectScoreSum(songListId) / rankNum);
    }

    @Override
    public R getUserRank(Long consumerId, Long songListId) {
        return R.success(null, rankMapper.selectUserRank(consumerId, songListId));
    }
}
