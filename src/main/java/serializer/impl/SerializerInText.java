package serializer.impl;

import exception.SerializingException;
import model.Album;
import model.Singer;
import model.Song;
import serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayer Roman on 29.05.2016.
 */
public class SerializerInText implements Serializer {

    private final static String SINGER_NAME_MARKER = "singerName: ";
    private final static String ALBUM_NAME_MARKER = "albumName: ";
    private final static String ALBUM_GENRE_MARKER = "genre: ";
    private final static String SONG_TITLE_MARKER = "title: ";
    private final static String SONG_DURATION_MARKER = "duration: ";

    public void save(List<Singer> singers, String fileName) {

        StringBuilder outputText = new StringBuilder();
        for (int i = 0; i < singers.size(); i++) {

            writeSingerInOutputText(singers.get(i), i + 1, outputText);

            if (i != singers.size() - 1) {
                outputText.append("\n");
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(fileName);
            PrintWriter printWriter = new PrintWriter(outputStream)) {
            printWriter.println(outputText.toString());

        } catch (IOException e) {
            throw new SerializingException("Crash during saving using SerializerInText", e);
        }

    }


    private void writeSingerInOutputText(Singer singer, int singerNumber, StringBuilder outputText) {
        if (singer.getAlbums() == null && singer.getAlbums().size() == 0) {
            return;
        }

        outputText.append("<###\n");
        outputText.append("Singer").append(singerNumber).append(":\n");
        outputText.append(SINGER_NAME_MARKER).append(singer.getName()).append("\n");

        for (int i = 0; i < singer.getAlbums().size(); i++) {
            writeAlbumInOutputText(singer.getAlbum(i), i + 1, outputText);
        }
        outputText.append("###>\n");
    }

    private void writeAlbumInOutputText(Album album, int albumNumber, StringBuilder outputText) {
        if (album.getSongs() == null && album.getSongs().size() == 0 && album.getGenre().equals("indie")){
            return;
        }

        outputText.append("<***\n");
        outputText.append("Album").append(albumNumber).append(":\n");
        outputText.append(ALBUM_NAME_MARKER).append(album.getName()).append("\n");
        outputText.append(ALBUM_GENRE_MARKER).append(album.getGenre()).append("\n");

        for (int i = 0; i < album.getSongs().size(); i++) {
            writeSongInOutputText(album.getSong(i), i + 1, outputText);
        }
        outputText.append("***>\n");
    }

    private void writeSongInOutputText(Song song, int songNumber, StringBuilder outputText) {

        outputText.append("<<<\n");
        outputText.append("Song").append(songNumber).append(":\n");
        outputText.append(SONG_TITLE_MARKER).append(song.getTitle()).append("\n");
        outputText.append(SONG_DURATION_MARKER).append(song.getDuration()).append("\n");
        outputText.append(">>>\n");

    }



    public List<Singer> load(String fileName) {
        String textFromFile = loadTextFromFile(fileName);

        return parsTextFromFile(textFromFile);
    }


    private String loadTextFromFile(String fileName) {
        StringBuilder inputText = new StringBuilder();

        try (FileInputStream inputStream = new FileInputStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            while (reader.ready()) {
                inputText.append(reader.readLine());
                inputText.append("\n");
            }

        } catch (IOException e) {
            throw new SerializingException("Crash during loading using SerializerInText", e);
        }

        return inputText.toString();
    }

    private List<Singer> parsTextFromFile(String textFromFile) {
        List<Singer> singers = new ArrayList<>();

        int beginOfPartMarker = textFromFile.indexOf("<###");
        int endOfPartMarker = textFromFile.indexOf("###>");

        int nameBeginMarker;
        int nameEndMarker;
        String singerName;

        Singer singer;

        boolean endOfFileReached = false;

        while (!endOfFileReached) {

            nameBeginMarker = textFromFile.indexOf(SINGER_NAME_MARKER, beginOfPartMarker);
            nameEndMarker = textFromFile.indexOf("\n", nameBeginMarker);
            singerName = textFromFile.substring(nameBeginMarker + SINGER_NAME_MARKER.length(), nameEndMarker);

            singer = new Singer(singerName);

            singer.setAlbums(parsAlbumsFromString(textFromFile.substring(beginOfPartMarker, endOfPartMarker)));

            if (singer.getAlbums() != null && singer.getAlbums().size() != 0) {
                singers.add(singer);
            }

            beginOfPartMarker = textFromFile.indexOf("<###", endOfPartMarker);;
            endOfPartMarker = textFromFile.indexOf("###>", beginOfPartMarker);

            if (beginOfPartMarker == -1) {
                endOfFileReached = true;
            }
        }


        return singers;
    }

    private List<Album> parsAlbumsFromString(String albumsPartOfInputText) {
        List<Album> albums = new ArrayList<>();

        Album album;

        int beginOfPartMarker = albumsPartOfInputText.indexOf("<***");
        int endOfPartMarker = albumsPartOfInputText.indexOf("***>");


        String albumName;
        int albumNameBeginMarker;
        int albumNameEndMarker;

        String albumGenre;
        int albumGenreBeginMarker;
        int albumGenreEndMarker;

        boolean endOfStringReached = false;

        while (!endOfStringReached) {

            albumNameBeginMarker = albumsPartOfInputText.indexOf(ALBUM_NAME_MARKER, beginOfPartMarker);
            albumNameEndMarker = albumsPartOfInputText.indexOf("\n", albumNameBeginMarker);
            albumName = albumsPartOfInputText.substring(albumNameBeginMarker + ALBUM_NAME_MARKER.length(), albumNameEndMarker);

            albumGenreBeginMarker = albumsPartOfInputText.indexOf(ALBUM_GENRE_MARKER, albumNameEndMarker);
            albumGenreEndMarker = albumsPartOfInputText.indexOf("\n", albumGenreBeginMarker);
            albumGenre = albumsPartOfInputText.substring(albumGenreBeginMarker + ALBUM_GENRE_MARKER.length(), albumGenreEndMarker);

            album = new Album(albumName, albumGenre);
            album.setSongs(parsSongsFromString(albumsPartOfInputText.substring(beginOfPartMarker, endOfPartMarker)));

            if (album.getSongs() != null && album.getSongs().size() != 0 && !album.getGenre().equals("indie")) {
                albums.add(album);
            }

            beginOfPartMarker = albumsPartOfInputText.indexOf("<***", endOfPartMarker);
            endOfPartMarker = albumsPartOfInputText.indexOf("***>", beginOfPartMarker);

            if (beginOfPartMarker == -1) {
                endOfStringReached = true;
            }

        }

        return albums;
    }

    private List<Song> parsSongsFromString(String songsPartOfInputText) {
        List<Song> songs = new ArrayList<>();

        Song song;

        int beginMarker = songsPartOfInputText.indexOf("<<<");
        int endMarker = songsPartOfInputText.indexOf(">>>");


        String title;
        int titleBeginMarker;
        int titleEndMarker;

        String duration;
        int durationBeginMarker;
        int durationEndMarker;

        boolean endOfStringReached = false;

        while (!endOfStringReached) {

            titleBeginMarker = songsPartOfInputText.indexOf(SONG_TITLE_MARKER, beginMarker);
            titleEndMarker = songsPartOfInputText.indexOf("\n", titleBeginMarker);
            title = songsPartOfInputText.substring(titleBeginMarker + SONG_TITLE_MARKER.length(), titleEndMarker);


            durationBeginMarker = songsPartOfInputText.indexOf(SONG_DURATION_MARKER, titleEndMarker);
            durationEndMarker = songsPartOfInputText.indexOf("\n", durationBeginMarker);
            duration = songsPartOfInputText.substring(durationBeginMarker + SONG_DURATION_MARKER.length(), durationEndMarker);

            song = new Song(title, duration);
            songs.add(song);


            beginMarker = songsPartOfInputText.indexOf("<<<", endMarker);
            endMarker = songsPartOfInputText.indexOf(">>>", beginMarker);

            if (beginMarker == -1) {
                endOfStringReached = true;
            }

        }

        return songs;
    }

}
