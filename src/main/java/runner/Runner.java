package runner;

import model.Album;
import model.Singer;
import model.Song;
import serializer.Serializer;
import serializer.impl.SerializerInText;
import serializer.impl.SerializerWithEntities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayer Roman on 31.05.2016.
 */
public class Runner {
    public void start() {

        List<Singer> musicCatalog = prepareMusicCatalog();

        System.out.println("From serialization with entities:");

        String fileName = "MusicCatalog.ser";
        Serializer serializer = new SerializerWithEntities();
        serializer.save(musicCatalog, fileName);
        List<Singer> singers = serializer.load(fileName);

        singers.stream().forEach(System.out::println);


        System.out.println();
        System.out.println("From serialization in text format:");

        fileName = "MusicLibrary.txt";
        Serializer serializerInText = new SerializerInText();
        serializerInText.save(musicCatalog, fileName);
        singers = serializerInText.load(fileName);

        singers.stream().forEach(System.out::println);


    }

    private List<Singer> prepareMusicCatalog() {
        List<Singer> musicCatalog = new ArrayList<>();
        Singer u2 = new Singer("U2");
        Singer sting = new Singer("Sting");
        Singer acDc = new Singer("AC/DC");

        Album allThatYouCantLeaveBehind = new Album("All that you can't left behind", "Pop-Rock");
        Album howToDismantleAnAtomicBomb = new Album("How To Dismantle An Atomic Bomb", "Rock");
        Album brandNewDay = new Album("Brand New Day", "Pop");
        Album whoMadeWho = new Album("Who Made Who", "Rock");

        Song elevation = new Song("Elevation", "3:46");
        Song walkOn = new Song("Walk On", "4:56");
        Song vertigo = new Song("Vertigo", "3:13");
        Song desertRose = new Song("Desert Rose", "4:47");
        Song forThoseAboutToRock = new Song("For Those About To Rock", "5:53");

        u2.setAlbum(allThatYouCantLeaveBehind);
        u2.setAlbum(howToDismantleAnAtomicBomb);
        sting.setAlbum(brandNewDay);
        acDc.setAlbum(whoMadeWho);

        allThatYouCantLeaveBehind.setSong(elevation);
        allThatYouCantLeaveBehind.setSong(walkOn);
        howToDismantleAnAtomicBomb.setSong(vertigo);
        brandNewDay.setSong(desertRose);
        whoMadeWho.setSong(forThoseAboutToRock);

        musicCatalog.add(u2);
        musicCatalog.add(sting);
        musicCatalog.add(acDc);

        return musicCatalog;
    }
}
