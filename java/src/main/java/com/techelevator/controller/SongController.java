package com.techelevator.controller;

import com.techelevator.dao.JdbcSongDao;
import com.techelevator.dao.SongDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Song;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/songs")
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
public class SongController {

    private JdbcSongDao songDao;

    public SongController(JdbcSongDao songDao){
        this.songDao = songDao;
    }

    @GetMapping(path = "")
    public List<Song> list(@RequestParam(defaultValue = "") String genre, @RequestParam(defaultValue = "") String title){
        if (!genre.equals("")){
            // genre parameter query is currently case sensitive
            return songDao.getAllSongsByGenre(genre);
        }

        if (!title.equals("")){
            return songDao.getAllSongsByTitle(title);
        }
         return songDao.getAllSongs();
    }

    @GetMapping(path = "/{id}")
    public Song get(@PathVariable int id){
        Song song = songDao.getSongById(id);
        if (song == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song Not Found");
        } else {
            return songDao.getSongById(id);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "")
    public Song addSong(@Valid @RequestBody Song song) {
        try {
            return songDao.createSong(song);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable int id){

        if(songDao.deleteSongById(id) == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song was not found.");
    }

    @PutMapping(path = "/{id}")
    public Song update(@Valid @RequestBody Song song, @PathVariable int id){
        song.setId(id);
        try {
            Song updatedReservation = songDao.updateSong(song);
            return updatedReservation;
        }
        catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found.");
        }
    }

}
