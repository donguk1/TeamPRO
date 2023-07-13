package kopo.poly.persistance.mapper;

import kopo.poly.dto.BookingDTO;
import kopo.poly.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBookingMapper {

    void insertBooking(BookingDTO pDTO) throws Exception; // 예약정보 저장하기

    List<BookingDTO> getBookingList() throws Exception; // 예약 리스트 조회하기

}