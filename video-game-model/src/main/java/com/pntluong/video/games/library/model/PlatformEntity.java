package com.pntluong.video.games.library.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ptluong on 1/06/2014.
 */
@Entity
@Table(name = "\"platform\"")
public class PlatformEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "platform_id")
    private Long platformId;

    @Column(name = "platform_name")
    private String platformName;

    @OneToMany(mappedBy = "platformEntity", targetEntity = VideoGameEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VideoGameEntity> videoGameEntities = new HashSet<VideoGameEntity>();

//    @Version
//    private long version= 0;

    public PlatformEntity() {
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Set<VideoGameEntity> getVideoGameEntities() {
        return videoGameEntities;
    }

    public void setVideoGameEntities(Set<VideoGameEntity> videoGameEntities) {
        this.videoGameEntities = videoGameEntities;
    }

//    public long getVersion() {
//        return version;
//    }
}
