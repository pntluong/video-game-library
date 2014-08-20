package com.pntluong.video.games.library.model;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ptluong on 1/06/2014.
 */
@Entity
@Table(name = "\"video_game\"")
public class VideoGameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_game_id")
    private Long videoGameId;

    @Column(name = "video_game_name")
    private String videoGameName;

    @Enumerated(EnumType.STRING)
    @Column(name = "video_game_status_type")
    private VideoGameStatusType videoGameStatusType;

    @Column(name ="date_released")
    private Date dateReleased;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id", referencedColumnName = "platform_id")
    private PlatformEntity platformEntity;

    public VideoGameEntity() {
    }

    public Long getVideoGameId() {
        return videoGameId;
    }

    public void setVideoGameId(Long videoGameId) {
        this.videoGameId = videoGameId;
    }

    public String getVideoGameName() {
        return videoGameName;
    }

    public void setVideoGameName(String videoGameName) {
        this.videoGameName = videoGameName;
    }

    public VideoGameStatusType getVideoGameStatusType() {
        return videoGameStatusType;
    }

    public void setVideoGameStatusType(VideoGameStatusType videoGameStatusType) {
        this.videoGameStatusType = videoGameStatusType;
    }

    public Date getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(Date dateReleased) {
        this.dateReleased = dateReleased;
    }

    public PlatformEntity getPlatformEntity() {
        return platformEntity;
    }

    public void setPlatformEntity(PlatformEntity platformEntity) {
        this.platformEntity = platformEntity;
    }

    @Override
    public String toString() {
        return "VideoGameEntity{" +
                "videoGameId=" + videoGameId +
                ", videoGameName='" + videoGameName + '\'' +
                ", videoGameStatusType=" + videoGameStatusType +
                ", dateReleased=" + dateReleased +
                ", platformEntity=" + platformEntity +
                '}';
    }
}
