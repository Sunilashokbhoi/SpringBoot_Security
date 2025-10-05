package com.secure.notes.controller;

import com.secure.notes.model.Note;
import com.secure.notes.service.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {
    @Autowired
    INoteService noteService;
    @PostMapping
    public Note createNote(@RequestBody String content , @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        System.out.println("User Details: "+username);
        return noteService.createNoteForUser(username,content);
    }
    @GetMapping
    public List<Note> getUserNotes(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        System.out.println("User Name "+username);
        return noteService.getNoteForUser(username);
    }
    @PutMapping("/{userId}")
    public Note updateNote(@PathVariable Long userId,
                           @RequestBody String content,
                           @AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        return noteService.updateNoteForUser(userId,content,username);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId ,@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
         noteService.deleteNoteForUser(noteId,username);
    }
}
