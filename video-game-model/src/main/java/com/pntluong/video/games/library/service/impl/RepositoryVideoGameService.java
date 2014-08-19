package com.pntluong.video.games.library.service.impl;

import com.pntluong.video.games.library.model.VideoGameEntity;
import com.pntluong.video.games.library.repository.VideoGameRepository;
import com.pntluong.video.games.library.service.VideoGameService;
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
public class RepositoryVideoGameService implements VideoGameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryVideoGameService.class);

    @Resource
    private VideoGameRepository videoGameRepository;

    @Transactional
    @Override
    public VideoGameEntity create(VideoGameEntity videoGameEntity) {
        LOGGER.debug("Creating a new videoGame with information: " + videoGameEntity);

        return videoGameRepository.save(videoGameEntity);
    }

    @Transactional
    @Override
    public VideoGameEntity delete(Long videoGameId) {
        LOGGER.debug("Deleting videoGame with id: " + videoGameId);

        VideoGameEntity deleted = videoGameRepository.findOne(videoGameId);

        if (deleted == null) {
            LOGGER.debug("No videoGame found with id: " + videoGameId);
//            throw new PersonNotFoundException();
        }

        videoGameRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<VideoGameEntity> findAll() {
        LOGGER.debug("Finding all videoGames");
        return (List<VideoGameEntity>) videoGameRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public VideoGameEntity findById(Long videoGameId) {
        LOGGER.debug("Finding videoGame by id: " + videoGameId);
        return videoGameRepository.findOne(videoGameId);
    }

    @Transactional
    @Override
    public VideoGameEntity update(VideoGameEntity updatedVideoGameEntity) {
        LOGGER.debug("Updating videoGame with information: " + updatedVideoGameEntity);

        VideoGameEntity videoGame = videoGameRepository.findOne(updatedVideoGameEntity.getVideoGameId());

        if (videoGame == null) {
            LOGGER.debug("No videoGame found with id: " + updatedVideoGameEntity.getVideoGameId());
//            throw new PersonNotFoundException();
        }

        videoGame.setVideoGameName(updatedVideoGameEntity.getVideoGameName());
//        videoGame.setVideoGameEntities(updatedVideoGameEntity.getVideoGameEntities());

        return videoGame;
    }

    /**
     * This setter method should be used only by unit tests
     * @param videoGameRepository
     */
    protected void setVideoGameRepository(VideoGameRepository videoGameRepository) {
        this.videoGameRepository = videoGameRepository;
    }
}
