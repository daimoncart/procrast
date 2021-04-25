package com.roadmap.procrast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.roadmap.procrast.model.Task;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAll();
    Optional<Task> findById(Long id);
    void deleteById(Long id);
    Task save(Task task);
    Task updateById(Long id, Task task);
}
