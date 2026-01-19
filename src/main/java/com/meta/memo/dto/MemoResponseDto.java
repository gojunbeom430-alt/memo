package com.meta.memo.dto;

import com.meta.memo.domain.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
public class MemoResponseDto {
    private Long id;
    private String username;
    private String contents;

    public MemoResponseDto(Memo newmemo){
        this.id = newmemo.getId();
        this.username = newmemo.getUsername();
        this.contents = newmemo.getContents();
    }

}
