package com.example.musicserver.service;

import com.example.musicserver.common.R;
import com.example.musicserver.model.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.musicserver.model.request.CommentRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Xiangly Peng
 * @since 2022-12-30
 */
public interface ICommentService extends IService<Comment> {

    R addComment(CommentRequest addCommentRequest);

    R updateCommentMsg(CommentRequest upCommentRequest);

    R deleteComment(Integer id);

    R commentOfSongId(Integer songId);

    R commentOfSongListId(Integer songListId);
}
