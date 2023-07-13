package kopo.poly.service.impl;

import kopo.poly.dto.BookingDTO;
import kopo.poly.persistance.mapper.IBookingMapper;
import kopo.poly.service.IBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService implements IBookingService {

    private final IBookingMapper bookingMapper;  // 예약 관련 SQL 사용하기 위한 Mapper 가져오기

    @Transactional
    @Override
    public void insertBooking(BookingDTO pDTO) throws Exception { // 예약하기

        /* 함수 접근 확인용 로그 */
        log.info(this.getClass().getName() + ".getInsertBooking Start!");
        log.info("예약 정보 작성 시작");

        bookingMapper.insertBooking(pDTO);

    }


    @Override
    public List<BookingDTO> getBookingList() throws Exception { // 예약리스트
        log.info(this.getClass().getName() + ".getBookingList start!");
        return bookingMapper.getBookingList();
    }
}
