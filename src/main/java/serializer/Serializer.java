package serializer;

import model.Singer;

import java.util.List;

/**
 * Created by Mayer Roman on 29.05.2016.
 */
public interface Serializer {

    void save(List<Singer> singers, String fileName);

    List<Singer> load(String fileName);
}
