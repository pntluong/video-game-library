import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.pntluong.video.games.library.config.PersistenceJPAConfig;
import com.pntluong.video.games.library.model.PlatformEntity;
import com.pntluong.video.games.library.model.VideoGameEntity;
import com.pntluong.video.games.library.model.VideoGameStatusType;
import com.pntluong.video.games.library.service.PlatformService;
import com.pntluong.video.games.library.service.VideoGameService;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.pntluong.video.games.library.model.VideoGameStatusType.COMPLETED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertThat;

/**
 * Created by ptluong on 26/07/2014.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {PersistenceJPAConfig.class}, loader = AnnotationConfigContextLoader.class)
@ContextConfiguration(classes = {PersistenceJPAConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup({"testData.xml"})
//@ActiveProfiles("dev")
@Transactional
public class PlatformRepositoryIntegrationTest {

    @Autowired
    private PlatformService platformService;

    @Autowired
    private VideoGameService videoGameService;

    @Test
    public void createAndFindPlatformEntityAndItsChildren() throws Exception {
        String expectedPlatformName = "platformOne";

        String expectedVideoGameName1 = "Some Awesome title";
        String expectedVideoGameName2 = "Another Awesome title";

        Date dateReleased = new Date();

        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setPlatformName(expectedPlatformName);

        VideoGameEntity videoGameEntity1 = new VideoGameEntity();
        videoGameEntity1.setVideoGameName(expectedVideoGameName1);
        videoGameEntity1.setDateReleased(dateReleased);
        videoGameEntity1.setVideoGameStatusType(VideoGameStatusType.COMPLETED);
        videoGameEntity1.setPlatformEntity(platformEntity);

        VideoGameEntity videoGameEntity2 = new VideoGameEntity();
        videoGameEntity2.setVideoGameName(expectedVideoGameName2);
        videoGameEntity2.setDateReleased(dateReleased);
        videoGameEntity2.setVideoGameStatusType(VideoGameStatusType.BACKLOG);
        videoGameEntity2.setPlatformEntity(platformEntity);

        platformEntity.getVideoGameEntities().add(videoGameEntity1);
        platformEntity.getVideoGameEntities().add(videoGameEntity2);

        PlatformEntity savedPlatformEntity = platformService.create(platformEntity);

        assertionsForPlatformEntityAndChildVideoGameEntity(expectedPlatformName, expectedVideoGameName1, expectedVideoGameName2, dateReleased, savedPlatformEntity);


        PlatformEntity retrievedPlatformEntity = platformService.findById(savedPlatformEntity.getPlatformId());

        assertThat(retrievedPlatformEntity.getPlatformId(), is(savedPlatformEntity.getPlatformId()));
        assertionsForPlatformEntityAndChildVideoGameEntity(expectedPlatformName, expectedVideoGameName1, expectedVideoGameName2, dateReleased, retrievedPlatformEntity);

        List<PlatformEntity> platformEntities = platformService.findAll();

        assertThat(platformEntities.size(), is(3));
        assertionsForPlatformEntityAndChildVideoGameEntity(expectedPlatformName, expectedVideoGameName1, expectedVideoGameName2, dateReleased, retrievedPlatformEntity);
    }

    private void assertionsForPlatformEntityAndChildVideoGameEntity(String expectedPlatformName, String expectedVideoGameName1, String expectedVideoGameName2, Date expectedDateReleased, PlatformEntity savedPlatformEntity) {
        assertThat(savedPlatformEntity.getPlatformName(), is(expectedPlatformName));
        assertThat(savedPlatformEntity.getVideoGameEntities(), IsIterableContainingInAnyOrder.<VideoGameEntity> containsInAnyOrder(hasProperty("videoGameName", is(expectedVideoGameName1)),
                hasProperty("dateReleased", is(expectedDateReleased))));
        assertThat(savedPlatformEntity.getVideoGameEntities(), IsIterableContainingInAnyOrder.<VideoGameEntity> containsInAnyOrder(hasProperty("videoGameName", is(expectedVideoGameName2)),
                hasProperty("dateReleased", is(expectedDateReleased))));
    }

    @Test
    public void updatePlatformEntity() throws Exception {
        long existingPlatformId = 22L;
        String updatedPlatformName = "I've updated the name";

        PlatformEntity updatedPlatformEntity = new PlatformEntity();
        updatedPlatformEntity.setPlatformId(existingPlatformId);
        updatedPlatformEntity.setPlatformName(updatedPlatformName);

        PlatformEntity retrievedPlatformEntity = platformService.update(updatedPlatformEntity);

        assertThat(retrievedPlatformEntity.getPlatformId(), is(existingPlatformId));
        assertThat(retrievedPlatformEntity.getPlatformName(), is(updatedPlatformName));
        assertThat(retrievedPlatformEntity.getVideoGameEntities().isEmpty(), is(true));
    }

    @Test
    public void updateVideoGameEntity() throws Exception {
        long existingVideoGameId = 22L;
        String updatedVideoGameName = "some new name";
        Date updatedDateReleased = new Date();

        VideoGameEntity updatedVideoGameEntity = new VideoGameEntity();
        updatedVideoGameEntity.setVideoGameId(existingVideoGameId);
        updatedVideoGameEntity.setVideoGameName(updatedVideoGameName);
        updatedVideoGameEntity.setDateReleased(updatedDateReleased);
        updatedVideoGameEntity.setVideoGameStatusType(VideoGameStatusType.COMPLETED);

        VideoGameEntity retrievedVideoGameEntity = videoGameService.update(updatedVideoGameEntity);

        assertThat(retrievedVideoGameEntity.getVideoGameId(), is(existingVideoGameId));
        assertThat(retrievedVideoGameEntity.getVideoGameName(), is(updatedVideoGameName));
        assertThat(retrievedVideoGameEntity.getVideoGameStatusType(), is(VideoGameStatusType.COMPLETED));
        assertThat(retrievedVideoGameEntity.getDateReleased(), is(updatedDateReleased));
        assertThat(retrievedVideoGameEntity.getPlatformEntity(), is(nullValue()));
    }

    @Test
    public void deletePlatformEntity() throws Exception {
        long platformIdToDelete = 22L;

        assertThat(platformService.findById(platformIdToDelete), is(notNullValue()));
        platformService.delete(platformIdToDelete);
        assertThat(platformService.findById(platformIdToDelete), is(nullValue()));
    }

    @Test
    public void deleteVideoGameEntity() throws Exception {
        long videoGameIdToDelete = 22L;

        assertThat(videoGameService.findById(videoGameIdToDelete), is(notNullValue()));
        videoGameService.delete(videoGameIdToDelete);
        assertThat(videoGameService.findById(videoGameIdToDelete), is(nullValue()));
    }

    @Test
    public void createAndFindVideoGameEntityAndItsParent() throws Exception {
        PlatformEntity platformEntity = new PlatformEntity();
        String platformName = "I am a platform";
        platformEntity.setPlatformName(platformName);
        Date videoGamedateReleased = new Date();
        String videoGameName = "new title";

        VideoGameEntity videoGameEntity = new VideoGameEntity();
        videoGameEntity.setDateReleased(videoGamedateReleased);
        videoGameEntity.setVideoGameStatusType(VideoGameStatusType.PLAYING);
        videoGameEntity.setVideoGameName(videoGameName);
        videoGameEntity.setPlatformEntity(platformEntity);

        VideoGameEntity savedVideoGameEntity = videoGameService.create(videoGameEntity);

        VideoGameEntity retrievedVideoGameEntity = videoGameService.findById(savedVideoGameEntity.getVideoGameId());

        assertThat(retrievedVideoGameEntity.getVideoGameId(), is(savedVideoGameEntity.getVideoGameId()));
        assertThat(retrievedVideoGameEntity.getVideoGameName(), is(savedVideoGameEntity.getVideoGameName()));
        assertThat(retrievedVideoGameEntity.getVideoGameStatusType(), is(VideoGameStatusType.PLAYING));
        assertThat(retrievedVideoGameEntity.getPlatformEntity().getPlatformName(), is(platformName));
    }
}
