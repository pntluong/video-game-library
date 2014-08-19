package com.pntluong.video.games.library.repository;

import com.pntluong.video.games.library.model.PlatformEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ptluong on 1/06/2014.
 */
public interface PlatformRepository extends CrudRepository<PlatformEntity, Long>, PlatformRepositoryCustom {
}
