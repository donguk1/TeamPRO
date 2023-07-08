package kopo.poly.service.impl;

import kopo.poly.dto.BookingDTO;
import kopo.poly.persistance.mapper.IBookingMapper;
import kopo.poly.service.IBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService implements IBookingService {

    private final IBookingMapper bookingMapper;  // 예약 관련 SQL 사용하기 위한 Mapper 가져오기

    @Transactional
    @Override
    public void insertBooking(BookingDTO pDTO) throws Exception {

        /* 함수 접근 확인용 로그 */
        log.info(this.getClass().getName() + ".getInsertBooking Start!");
        log.info("예약 정보 작성 시작");

        bookingMapper.insertBooking(pDTO);



//        /* 데이터 입력 */
//        String same = CmmUtil.nvl(pDTO.getSame());
//        String user_name = CmmUtil.nvl(pDTO.getUser_name());
//        String tel = CmmUtil.nvl(pDTO.getTel());
//        String user_email = CmmUtil.nvl(pDTO.getUser_email());
//        String start_date = CmmUtil.nvl(pDTO.getStart_date());
//        String last_date = CmmUtil.nvl(pDTO.getLast_date());
//        String stay = CmmUtil.nvl(pDTO.getStay());
//        String room = CmmUtil.nvl(pDTO.getRoom());
//        String person = CmmUtil.nvl(pDTO.getPerson());
//        String local = CmmUtil.nvl(pDTO.getLocal());
//        String foreign = CmmUtil.nvl(pDTO.getForeign());
//        String want = CmmUtil.nvl(pDTO.getWant());
//        String user_id = CmmUtil.nvl(pDTO.getUser_id());
//
//        /* 데이터 입력 확인용 로그 */
//        log.info("same : " + same);
//        log.info("user_name : " + user_name);
//        log.info("tel : " + tel);
//        log.info("user_email : " + user_email);
//        log.info("start_date : " + start_date);
//        log.info("last_date : " + last_date);
//        log.info("stay : " + stay);
//        log.info("room : " + room);
//        log.info("person : " + person);
//        log.info("local : " + local);
//        log.info("foreign : " + foreign);
//        log.info("want : " + want);
//        log.info("user_id : " + user_id);
//
//        log.info(this.getClass().getName() + ".getInsertBooking End!");
//        log.info("예약 정보 작성 종료");


    }
}
