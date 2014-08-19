package com.pntluong.video.games.library.service;

import com.pntluong.video.games.library.model.VideoGameEntity;

import java.util.List;

/**
 * Created by ptluong on 21/07/2014.
 */
public interface VideoGameService {
    public VideoGameEntity create(VideoGameEntity VideoGameEntity);
    public VideoGameEntity delete(Long VideoGameId);
    public List<VideoGameEntity> findAll();
    public VideoGameEntity findById(Long VideoGameId);
    public VideoGameEntity update(VideoGameEntity VideoGameEntity);
}
