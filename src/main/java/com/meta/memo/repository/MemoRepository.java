package com.meta.memo.repository;

import com.meta.memo.domain.Memo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByCreateAtDesc();
    List<Memo> findAllByContentsContainingOrderByModifiedAtDesc(String keyword);

}