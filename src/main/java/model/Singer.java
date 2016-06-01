package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayer Roman on 29.05.2016.
 */
public class Singer {
    private String name;

    private List<Album> albums = new ArrayList<>();


    public Singer(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Album> getAlbums() {
        return albums;
    }

    public Album getAlbum(int albumIndex) {
        return albums.get(albumIndex);
    }


    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public void setAlbum(Album album) {
        albums.add(album);
    }


    @Override
    public String toString() {
        return "Singer{" +
                "name='" + name + '\'' +
                ", albums=" + albums +
                '}';
    }
}
