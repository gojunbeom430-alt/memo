package com.meta.memo.service;

import com.meta.memo.domain.Memo;
import com.meta.memo.dto.MemoResponseDto;
import com.meta.memo.dto.MemoRequestDto;
import com.meta.memo.repository.MemoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemoService {
    //멤버 변수 선언
    private final MemoRepository memoRepository;
    // 생성자 주입 (DI)
    public MemoService(JdbcTemplate jdbcTemplate) {
        this.memoRepository = new MemoRepository(jdbcTemplate);
    }


    public MemoResponseDto createMemo(@RequestBody MemoRequestDto memoRequestDto) {
        // RequestDto -> Entity 변환
        Memo newMemo = new Memo(memoRequestDto);

        Memo savedMemo = memoRepository.save(newMemo);

        // Entity -> ResponseDto 변환
        MemoResponseDto memoResponseDto = new MemoResponseDto(savedMemo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        List<MemoResponseDto> memoResponseDtoList = memoRepository.findAll();
        return memoResponseDtoList;
    }

    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto) {
        // 해당 id의 메모가 존재하는지 확인
        Memo foundmemo = memoRepository.findById(id);

        // 해당 id의 메모가 데이터베이스에 존재하는지 확인, 메모 내용 수정
        if (foundmemo != null) {
            Long updatedID = memoRepository.update(id, memoRequestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 id의 메모는 존재하지 않습니다.");
        }
    }

    public Long deleteMemo(@PathVariable Long id) {
        // 해당 id의 메모가 존재하는지 확인
        Memo foundmemo = MemoRepository.findById(id);

        // 메모 내용 삭제
        if (foundmemo != null){
            Long deletedID = MemoRepository.delete(id);
            return deletedID;

        } else {
            throw new IllegalArgumentException("선택한 id의 메모는 존재하지 않습니다.");
        }
    }

}
