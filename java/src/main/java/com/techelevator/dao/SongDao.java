package com.techelevator.dao;

import com.techelevator.model.Song;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface SongDao {

    List<Song> getAllSongs();

    List<Song> getAllSongsByGenre(String genre);

    Song getSongById(int id);

    Song getSongByTitle(String title);

    void createSong(Song song);

    int deleteSongById(int songID);
}
