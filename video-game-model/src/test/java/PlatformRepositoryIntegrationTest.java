import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.pntluong.video.games.library.config.PersistenceJPAConfig;
import com.pntluong.video.games.library.model.PlatformEntity;
import com.pntluong.video.games.library.model.VideoGameEntity;
import com.pntluong.video.games.library.model.VideoGameStatusType;
import com.pntluong.video.games.library.service.PlatformService;
import com.pntluong.video.games.library.service.VideoGameService;
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
import static org.hamcrest.CoreMatchers.nullValue;
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
//@DatabaseSetup({"platformData.xml"})
//@ActiveProfiles("dev")
@Transactional
public class PlatformRepositoryIntegrationTest {

    @Autowired
    private PlatformService platformService;

    @Autowired
    private VideoGameService videoGameService;

    @Test
    public void createAndFindVideoGameAndPlatform() throws Exception {
        String expectedPlatformName = "xbox";

        String videoGameName = "I am some game";
        Date dateReleased = new Date();

        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setPlatformName(expectedPlatformName);

        PlatformEntity savedPlatformEntity = platformService.create(platformEntity);
        PlatformEntity retrievedPlatformEntity = platformService.findById(savedPlatformEntity.getPlatformId());

        assertThat(retrievedPlatformEntity.getPlatformId(), is(retrievedPlatformEntity.getPlatformId()));
        assertThat(retrievedPlatformEntity.getPlatformName(), is(expectedPlatformName));

        VideoGameEntity videoGameEntity = new VideoGameEntity();
        videoGameEntity.setVideoGameName(videoGameName);
        videoGameEntity.setDateReleased(dateReleased);
        videoGameEntity.setVideoGameStatusType(VideoGameStatusType.COMPLETED);
        videoGameEntity.setPlatformEntity(retrievedPlatformEntity);

        VideoGameEntity savedVideoGameEntity = videoGameService.create(videoGameEntity);

        VideoGameEntity retrievedVideoGameEntity = videoGameService.findById(savedVideoGameEntity.getVideoGameId());

        assertThat(retrievedVideoGameEntity.getVideoGameId(), is(savedVideoGameEntity.getVideoGameId()));
        assertThat(retrievedVideoGameEntity.getVideoGameStatusType(), is(VideoGameStatusType.COMPLETED));
        assertThat(retrievedVideoGameEntity.getVideoGameName(), is(videoGameName));
        assertThat(retrievedVideoGameEntity.getDateReleased(), is(dateReleased));
        assertThat(retrievedVideoGameEntity.getPlatformEntity().getPlatformId(), is(retrievedPlatformEntity.getPlatformId()));
        assertThat(retrievedVideoGameEntity.getPlatformEntity().getPlatformName(), is(retrievedPlatformEntity.getPlatformName()));

        Set<VideoGameEntity> videoGameEntities = new TreeSet<VideoGameEntity>();
        retrievedPlatformEntity.setVideoGameEntities(videoGameEntities);

        PlatformEntity update = platformService.update(retrievedPlatformEntity);

        List<PlatformEntity> platformEntities = platformService.findAll();

        assertThat(platformEntities.size(), is(1));
        assertThat(platformEntities.get(0).getPlatformId(), is(retrievedPlatformEntity.getPlatformId()));
        assertThat(platformEntities.get(0).getPlatformName(), is(retrievedPlatformEntity.getPlatformName()));
    }

    @Test
    public void updatePlatform() throws Exception {

        String expectedPlatformName = "xbox";

        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setPlatformName(expectedPlatformName);

        PlatformEntity savedPlatformEntity = platformService.create(platformEntity);

        String updatedPlatformName = "xBone";
        savedPlatformEntity.setPlatformName(updatedPlatformName);

        PlatformEntity updatedEntity = platformService.update(savedPlatformEntity);

        assertThat(updatedEntity.getPlatformId(), is(savedPlatformEntity.getPlatformId()));
        assertThat(updatedEntity.getPlatformName(), is(updatedPlatformName));
    }

    @Test
    public void deletePlatform() throws Exception {
        String expectedPlatformName = "xbox";

        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setPlatformName(expectedPlatformName);

        PlatformEntity savedPlatformEntity = platformService.create(platformEntity);

        Long platformId = savedPlatformEntity.getPlatformId();
        platformService.delete(platformId);


        assertThat(platformService.findById(platformId), is(nullValue()));
        assertThat(platformService.findAll(), is(Collections.<PlatformEntity>emptyList()));
    }

    @Test
    public void createAndFindVideoGame() throws Exception {
        String platformName = "xbox";

        PlatformEntity platformEntity = new PlatformEntity();
        platformEntity.setPlatformName(platformName);

        PlatformEntity savedPlatformEntity = platformService.create(platformEntity);
        PlatformEntity retrievedPlatformEntity = platformService.findById(savedPlatformEntity.getPlatformId());

        assertThat(retrievedPlatformEntity.getPlatformId(), is(retrievedPlatformEntity.getPlatformId()));
        assertThat(retrievedPlatformEntity.getPlatformName(), is(platformName));

        Date expectedReleaseDate = new Date();
        String expectedVideoGameName = "red dead";

        VideoGameEntity videoGameEntity = new VideoGameEntity();
        videoGameEntity.setDateReleased(expectedReleaseDate);
        videoGameEntity.setPlatformEntity(savedPlatformEntity);
        videoGameEntity.setVideoGameName(expectedVideoGameName);
        videoGameEntity.setVideoGameStatusType(COMPLETED);

        VideoGameEntity savedVideoGameEntity = videoGameService.create(videoGameEntity);

        VideoGameEntity retrievedVideoGameEntity = videoGameService.findById(savedVideoGameEntity.getVideoGameId());

        assertThat(retrievedVideoGameEntity.getVideoGameId(), is(savedVideoGameEntity.getVideoGameId()));
//        assertThat(retrievedVideoGameEntity.getDateReleased(), is(savedVideoGameEntity.getDateReleased()));
        assertThat(retrievedVideoGameEntity.getPlatformEntity().getPlatformId(), is(savedPlatformEntity.getPlatformId()));
        assertThat(retrievedVideoGameEntity.getVideoGameName(), is(expectedVideoGameName));
        assertThat(retrievedVideoGameEntity.getVideoGameStatusType(), is(COMPLETED));
    }
}
