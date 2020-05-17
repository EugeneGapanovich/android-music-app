package by.gapanovich.musicplay;


import java.io.Serializable;

public class MusicInfo implements Serializable {

    private String albumImage;
    private String artistName;
    private String songName;
    private String songUrl;
    

    public MusicInfo(String albumImage, String artistName, String songName, String songUrl) {
        this.albumImage = albumImage;
        this.artistName = artistName;
        this.songName = songName;
        this.songUrl = songUrl;
    }


    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

}


