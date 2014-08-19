package com.pntluong.video.games.library.service;

import com.pntluong.video.games.library.model.PlatformEntity;

import java.util.List;

/**
 * Created by ptluong on 21/07/2014.
 */
public interface PlatformService {
    public PlatformEntity create(PlatformEntity platformEntity);
    public PlatformEntity delete(Long platformId);
    public List<PlatformEntity> findAll();
    public PlatformEntity findById(Long platformId);
    public PlatformEntity update(PlatformEntity platformEntity);
}
