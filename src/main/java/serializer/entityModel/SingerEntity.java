package serializer.entityModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayer Roman on 29.05.2016.
 */
public class SingerEntity implements Serializable {
    private static final long serialVersionUID = 111L;

    public String name;

    public List<AlbumEntity> albums = new ArrayList<>();


    public SingerEntity(String name) {
        this.name = name;
    }

    public SingerEntity(String name, List<AlbumEntity> albums) {
        this.name = name;
        this.albums = albums;
    }
}
