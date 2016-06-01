package serializer.entityModel;

import java.io.Serializable;

/**
 * Created by Mayer Roman on 30.05.2016.
 */
public class SongEntity implements Serializable {
    private static final long serialVersionUID = 333L;

    public String title;

    public String duration;


    public SongEntity(String title, String duration) {
        this.title = title;
        this.duration = duration;
    }
}
