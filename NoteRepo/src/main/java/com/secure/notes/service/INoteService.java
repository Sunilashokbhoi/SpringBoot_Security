package com.secure.notes.service;

import com.secure.notes.model.Note;

import java.util.List;

public interface INoteService {
    Note createNoteForUser(String username ,String content);

    Note updateNoteForUser(Long noteId ,String content,String username);

    void deleteNoteForUser(Long noteId ,String username);

    List<Note> getNoteForUser(String username);
}
