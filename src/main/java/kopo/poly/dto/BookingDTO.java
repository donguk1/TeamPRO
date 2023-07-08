package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingDTO {
    private String same;        // 회원정보 동일 체크
    private String booking_name;   // 이름
    private String tel;         // 전화번호
    private String booking_email;  // 이메일
    private String start_date;  // 예약 시작일
    private String last_date;   // 예약 종료일
    private String stay;        // 숙소 타입
    private String room;        // 방 개수
    private String person;      // 예약 인원
    private String local;       // 여행지(국내)
    private String foreignn;     // 여행지(국외)
    private String want;        // 요청 사항

}

