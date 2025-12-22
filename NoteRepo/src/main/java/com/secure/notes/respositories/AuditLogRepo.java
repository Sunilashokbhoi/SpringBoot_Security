package com.secure.notes.respositories;

import com.secure.notes.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepo extends JpaRepository<AuditLog,Long> {
    List<AuditLog> findByNoteId(Long id);
}
