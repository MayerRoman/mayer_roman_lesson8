package serializer.entityModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayer Roman on 30.05.2016.
 */
public class AlbumEntity implements Serializable {
    private static final long serialVersionUID = 222L;

    public String name;

    public String genre;

    public List<SongEntity> songs = new ArrayList<>();


    public AlbumEntity(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }

    public AlbumEntity(String name, String genre, List<SongEntity> songs) {
        this.name = name;
        this.genre = genre;
        this.songs = songs;
    }
}
