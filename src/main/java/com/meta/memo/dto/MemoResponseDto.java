package com.meta.memo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meta.memo.domain.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//Dto는 수정이 될수도 있고 별도의 추가가 될수 있지만 Entity 같은 잘 바꾸지마라 , 요청과 응답을 조정하는것은 Dto로 움직이자.
@Getter
@AllArgsConstructor
public class MemoResponseDto {
    private Long id;
    private String username;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt;


    public MemoResponseDto(Memo newmemo){
        this.id = newmemo.getId();
        this.username = newmemo.getUsername();
        this.contents = newmemo.getContents();
        this.modifiedAt = newmemo.getModifiedAt();
    }

}
