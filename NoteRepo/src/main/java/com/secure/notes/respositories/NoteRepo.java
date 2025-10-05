package com.secure.notes.respositories;


import com.secure.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo  extends JpaRepository<Note, Long> {
    List<Note> findByOwnerUsername(String ownerUsername);
}
