package com.secure.notes.service;

import com.secure.notes.model.Note;
import com.secure.notes.respositories.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteService implements INoteService {
    @Autowired
    NoteRepo repo;
    @Override
    public Note createNoteForUser(String username, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(username);
        Note saveNote = repo.save(note);
        return saveNote;
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        Note note = repo.findById(noteId).orElseThrow(()-> new RuntimeException("Note Not Found "));
        note.setContent(content);
        Note updateNote = repo.save(note);
        return updateNote;
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        repo.deleteById(noteId);
    }

    @Override
    public List<Note> getNoteForUser(String username) {
        List<Note> personalNote = repo.findByOwnerUsername(username);
        return personalNote;
    }
}

