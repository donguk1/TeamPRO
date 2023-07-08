package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {

    private String user_id;     // 사용자 아이디
    private String user_name;   // 사용자명
    private String password;    // 비밀번호
    private String email;       // 이메일
    private String tel;         // 전화번호
    private String addr1;       // 주소
    private String addr2;       // 상세 주소
    private String reg_id;      // 등록 id
    private String reg_dt;      // 가입 일자
    private String chg_id;      // 변경한 id
    private String chg_dt;      // 변경일


    /*
     * 회원가입시 중복가입을 방지 위해 사용할 변수
     * DB를 조회해서 회원이 존재하면 Y값 반환
     * DB테이블에 존재하지 않는 가상의 컬럼(ALIAS)
     */
    private String exists_yn;

    private int authNumber;     // 이메일 중복체크를 위한 인증번호
}
