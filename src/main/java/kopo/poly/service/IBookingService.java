package kopo.poly.service;

import kopo.poly.dto.BookingDTO;

import java.util.List;

public interface IBookingService {

    /*  예약정보 저장하기 */
    void insertBooking(BookingDTO pDTO) throws Exception;


    // 예약 리스트
    // @return 조회 결과
    List<BookingDTO> getBookingList() throws Exception;
}
