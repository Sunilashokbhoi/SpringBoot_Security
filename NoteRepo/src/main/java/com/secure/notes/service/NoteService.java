package com.secure.notes.service;

import com.secure.notes.model.Note;
import com.secure.notes.respositories.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteService implements INoteService {

    @Autowired
    private NoteRepo noteRepository;
    @Autowired
    private AuditLogService auditLogService;

    @Override
    public Note createNoteForUser(String username, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(username);
        Note savedNote = noteRepository.save(note);
        auditLogService.logNoteCreation(username,note);
        return savedNote;
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        Note note = noteRepository.findById(noteId).orElseThrow(()
                -> new RuntimeException("Note not found"));
        note.setContent(content);
        Note updatedNote = noteRepository.save(note);
        auditLogService.logNoteUpdate(username,note);
        return updatedNote;
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        Note note  = noteRepository.findById(noteId).orElseThrow(()->new RuntimeException("Not not found"));
        auditLogService.logNoteDeletion(username,noteId);
        noteRepository.deleteById(noteId);

    }

    @Override
    public List<Note> getNotesForUser(String username) {
        return List.of();
    }

    @Override
    public List<Note> getNoteForUser(String username) {
        List<Note> personalNotes = noteRepository
                .findByOwnerUsername(username);
        return personalNotes;
    }

    }