package com.milena.lamejoraplicacion.beans;

/**
 * Created by ASUS on 26/10/2015.
 */
public class Serie {

    private Long idSerie;

    private int type;

    private String name;

    private Integer season;

    private int episode;

    private String image;

    public Serie() {
    }

    public Serie(int type, String name, int season, int episode, long idSerie) {
        this.type = type;
        this.name = name;
        this.season = season;
        this.episode = episode;
        this.idSerie = idSerie;
    }

    public Serie(String name, int type, int episode, int season) {
        this.name = name;
        this.type = type;
        this.episode = episode;
        this.season = season;
    }

    public Long getIdSerie() {
        return idSerie;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public String getImage() {
        return image;
    }

    public void setIdSerie(Long idSerie) {
        this.idSerie = idSerie;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
