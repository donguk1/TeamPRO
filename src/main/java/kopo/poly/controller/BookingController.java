package kopo.poly.controller;

import kopo.poly.dto.BookingDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.IBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookingController {

    private final IBookingService BookingService;


    /*  예약화면으로 이동  */
    @GetMapping(value = "/siso/booking")
    public String booking() {

        // 확인용 로그(실행 된건지)
        log.info(this.getClass().getName() + "./siso/booking 예약화면 접속");

        return "/siso/booking";
    }

    @PostMapping(value = "/siso/bookingInsert")
    public String bookingInsert(HttpServletRequest request, ModelMap model, HttpSession session) {

        log.info(this.getClass().getName() + ".bookingInsert Start!");
        log.info("예약 시작");

        String msg = "";
        String url = "/siso/booking";
        
        try {
            /* 데이터 입력 */
            String user_id = kopo.poly.Util.CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String same = kopo.poly.Util.CmmUtil.nvl(request.getParameter("same"));
            String booking_name = kopo.poly.Util.CmmUtil.nvl(request.getParameter("booking_name"));
            String tel = kopo.poly.Util.CmmUtil.nvl(request.getParameter("tel"));
            String booking_email = kopo.poly.Util.CmmUtil.nvl(request.getParameter("booking_email"));
            String start_date = kopo.poly.Util.CmmUtil.nvl(request.getParameter("start_date"));
            String last_date = kopo.poly.Util.CmmUtil.nvl(request.getParameter("last_date"));
            String stay = kopo.poly.Util.CmmUtil.nvl(request.getParameter("stay"));
            String room = kopo.poly.Util.CmmUtil.nvl(request.getParameter("room"));
            String person = kopo.poly.Util.CmmUtil.nvl(request.getParameter("person"));
            String local = kopo.poly.Util.CmmUtil.nvl(request.getParameter("local"));
            String foreignn = kopo.poly.Util.CmmUtil.nvl(request.getParameter("foreignn"));
            String want = kopo.poly.Util.CmmUtil.nvl(request.getParameter("want"));



            /* 데이터 입력 확인용 로그 */
            log.info("session user_id : " + user_id);
            log.info("same : " + same);
            log.info("booking_name : " + booking_name);
            log.info("tel : " + tel);
            log.info("booking_email : " + booking_email);
            log.info("start_date : " + start_date);
            log.info("last_date : " + last_date);
            log.info("stay : " + stay);
            log.info("room : " + room);
            log.info("person : " + person);
            log.info("local : " + local);
            log.info("foreignn : " + foreignn);
            log.info("want : " + want);

            log.info(this.getClass().getName() + ".getInsertBooking End!");
            log.info("예약 정보 작성 종료");

            /* 데이터 저장하기 위해 DTO에 저장하기 */
            BookingDTO pDTO = new BookingDTO();
            pDTO.setSame(same);
            pDTO.setBooking_name(booking_name);
            pDTO.setTel(tel);
            pDTO.setBooking_email(booking_email);
            pDTO.setStart_date(start_date);
            pDTO.setLast_date(last_date);
            pDTO.setStay(stay);
            pDTO.setRoom(room);
            pDTO.setPerson(person);
            pDTO.setLocal(local);
            pDTO.setForeignn(foreignn);
            pDTO.setWant(want);

            /* 예약하기 위한 비즈니스 로직 호출 */
            BookingService.insertBooking(pDTO);

            msg = "예약 되었습니다.";
            url = "/siso/bookingList"; // booking에서 bookingList로 변경


        } catch (Exception e) {

            /* 예약 실패시 사용자에게 보여줄 메시지 */
            msg = "예약 실패하였습니다.";
            log.info(e.toString());
            e.printStackTrace();



        } finally {
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);

            log.info(this.getClass().getName() + ".bookingInsert End!");
            log.info("예약 끝");

        }

        return "/redirect";

    }


    /* 예약리스트 */
    @GetMapping(value = "/siso/bookingList")
    public String bookingList(ModelMap model) throws Exception {

        // 로그 찍기
        log.info(this.getClass().getName() + ".bookingList 페이지 보여주는 함수 실행");

        // 예약 리스트 조회하기
        List<BookingDTO> rList = BookingService.getBookingList();
        if (rList == null) rList = new ArrayList<>();

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        // 로그 찍기
        log.info(this.getClass().getName() + ".bookingList 페이지 보여주는 함수 끝");

        // 함수 처리가 끝나고 보여줄 html 파일명
        return "/siso/bookingList";
    }
}


