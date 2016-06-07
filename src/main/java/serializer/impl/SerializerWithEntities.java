package serializer.impl;

import exception.SerializingException;
import model.Album;
import model.Singer;
import model.Song;
import serializer.Serializer;
import serializer.entityModel.AlbumEntity;
import serializer.entityModel.SingerEntity;
import serializer.entityModel.SongEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayer Roman on 29.05.2016.
 */
public class SerializerWithEntities implements Serializer {

    public void save(List<Singer> singers, String fileName) {
        List<SingerEntity> singerEntities = convertSingersFromModelToEntity(singers);

        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));) {
            outputStream.writeObject(singerEntities);

        } catch (IOException e) {
            throw new SerializingException("Crash during saving using SerializerWithEntities", e);
        }
    }

    private List<SingerEntity> convertSingersFromModelToEntity(List<Singer> singers) {
        List<SingerEntity> singerEntities = new ArrayList<>();

        SingerEntity singerEntity;

        for (Singer singer : singers) {
            if (singer.getAlbums() != null && singer.getAlbums().size() != 0) {

                singerEntity = new SingerEntity(singer.getName());

                singerEntity.albums = copyAlbumsFromModelToEntity(singer.getAlbums());

                singerEntities.add(singerEntity);
            }
        }

        return singerEntities;
    }

    private List<AlbumEntity> copyAlbumsFromModelToEntity(List<Album> albums) {
        List<AlbumEntity> albumEntities = new ArrayList<>();

        AlbumEntity albumEntity;

        for (Album album : albums) {
            if (album.getSongs() != null && album.getSongs().size() != 0 && !album.getGenre().equals("indie")) {

                albumEntity = new AlbumEntity(album.getName(), album.getGenre());

                albumEntity.songs = copySongsFromModelToEntity(album.getSongs());

                albumEntities.add(albumEntity);
            }
        }

        return albumEntities;
    }

    private List<SongEntity> copySongsFromModelToEntity(List<Song> songs) {
        List<SongEntity> songEntities = new ArrayList<>();

        SongEntity songEntity;

        for (Song song : songs) {

            songEntity = new SongEntity(song.getTitle(), song.getDuration());
            songEntities.add(songEntity);
        }

        return songEntities;
    }


    public List<Singer> load(String fileName) {
        List<Singer> singers = null;

        try (ObjectInputStream inputStream =
                     new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));) {

            List<SingerEntity> singerEntities = (ArrayList<SingerEntity>) inputStream.readObject();
            singers = convertSingersFromEntityToModel(singerEntities);

        } catch (IOException | ClassNotFoundException e) {
            throw new SerializingException("Crash during loading using SerializerWithEntities", e);
        }

        return singers;

    }

    private List<Singer> convertSingersFromEntityToModel(List<SingerEntity> singerEntities) {
        List<Singer> singers = new ArrayList<>();

        Singer singer;

        for (SingerEntity singerEntity : singerEntities) {
            if (singerEntity.albums != null && singerEntity.albums.size() != 0) {

                singer = new Singer(singerEntity.name);

                singer.setAlbums(copyAlbumsFromEntityToModel(singerEntity.albums));

                singers.add(singer);
            }
        }

        return singers;
    }

    private List<Album> copyAlbumsFromEntityToModel(List<AlbumEntity> albumEntities) {
        List<Album> albums = new ArrayList<>();

        Album album;

        for (AlbumEntity albumEntity : albumEntities) {
            if (albumEntity.songs != null && albumEntity.songs.size() != 0 && !albumEntity.genre.equals("indie")) {

                album = new Album(albumEntity.name, albumEntity.genre);

                album.setSongs(copySongsFromEntityToModel(albumEntity.songs));

                albums.add(album);
            }
        }

        return albums;
    }

    private List<Song> copySongsFromEntityToModel(List<SongEntity> songEntities) {
        List<Song> songs = new ArrayList<>();

        Song song;

        for (SongEntity songEntity : songEntities) {

            song = new Song(songEntity.title, songEntity.duration);
            songs.add(song);
        }

        return songs;
    }
}
