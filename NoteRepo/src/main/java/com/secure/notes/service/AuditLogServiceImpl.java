package com.secure.notes.service;

import com.secure.notes.model.AuditLog;
import com.secure.notes.model.Note;
import com.secure.notes.respositories.AuditLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService{

    @Autowired
    AuditLogRepo repo;
    @Override
    public void logNoteCreation(String username, Note note){
        AuditLog log = new AuditLog();
        log.setAction("CREATE");
        log.setUsername(username);
        log.setNoteId(note.getId());
        log.setNoteContaint(note.getContent());
        log.setTimestamp(LocalDateTime.now());
        repo.save(log);
      }

    @Override
    public void logNoteUpdate(String username, Note note){
        AuditLog log = new AuditLog();
        log.setAction("UPDATE");
        log.setUsername(username);
        log.setNoteId(note.getId());
        log.setNoteContaint(note.getContent());
        log.setTimestamp(LocalDateTime.now());
        repo.save(log);
    }
    @Override
    public void logNoteDeletion(String username, Long noteId){
        AuditLog log = new AuditLog();
        log.setAction("DELETE");
        log.setUsername(username);
        log.setNoteId(noteId);
        log.setTimestamp(LocalDateTime.now());
        repo.save(log);
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {
        return repo.findAll();
    }

    @Override
    public List<AuditLog> getAuditLogsForNoteId(Long id) {
        return repo.findByNoteId(id);
    }

}
