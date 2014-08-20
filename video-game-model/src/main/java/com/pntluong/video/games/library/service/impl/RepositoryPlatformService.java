package com.pntluong.video.games.library.service.impl;

import com.pntluong.video.games.library.model.PlatformEntity;
import com.pntluong.video.games.library.repository.PlatformRepository;
import com.pntluong.video.games.library.service.PlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ptluong on 21/07/2014.
 */
@Service
public class RepositoryPlatformService implements PlatformService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryPlatformService.class);

    @Resource
    private PlatformRepository platformRepository;

    @Transactional
    @Override
    public PlatformEntity create(PlatformEntity platformEntity) {
        LOGGER.debug("Creating a new platform with information: " + platformEntity);

        return platformRepository.save(platformEntity);
    }

    @Transactional
    @Override
    public PlatformEntity delete(Long platformId) {
        LOGGER.debug("Deleting platform with id: " + platformId);

        PlatformEntity deleted = platformRepository.findOne(platformId);

        if (deleted == null) {
            LOGGER.debug("No platform found with id: " + platformId);
//            throw new PersonNotFoundException();
        }

        platformRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PlatformEntity> findAll() {
        LOGGER.debug("Finding all platforms");
        return (List<PlatformEntity>) platformRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public PlatformEntity findById(Long platformId) {
        LOGGER.debug("Finding platform by id: " + platformId);
        return platformRepository.findOne(platformId);
    }

    @Transactional
    @Override
    public PlatformEntity update(PlatformEntity updatedPlatformEntity) {
        LOGGER.debug("Updating platform with information: " + updatedPlatformEntity);

        PlatformEntity platform = platformRepository.findOne(updatedPlatformEntity.getPlatformId());

        if (platform == null) {
            LOGGER.debug("No platform found with id: " + updatedPlatformEntity.getPlatformId());
//            throw new PersonNotFoundException();
        }

        platform.setPlatformName(updatedPlatformEntity.getPlatformName());
        platform.setVideoGameEntities(updatedPlatformEntity.getVideoGameEntities());

        return platform;
    }

    /**
     * This setter method should be used only by unit tests
     * @param platformRepository
     */
    protected void setPlatformRepository(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }
}
