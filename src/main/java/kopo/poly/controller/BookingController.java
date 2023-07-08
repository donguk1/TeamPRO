package kopo.poly.controller;

import kopo.poly.dto.BookingDTO;
import kopo.poly.service.IBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookingController {

    private final IBookingService BookingService;


    /*  예약화면으로 이동  */
    @GetMapping(value = "/siso/booking")
    public String booking(ModelMap model) throws Exception{

        // 확인용 로그(실행 된건지)
        log.info(this.getClass().getName() + "./siso/booking");
        log.info("예약화면 접속");

        return "/siso/booking";
    }


    /* 예약 내용 추가 */
    @PostMapping(value = "/siso/bookingInsert")
    public String bookingInsert(HttpServletRequest request, ModelMap model, HttpSession session) {

        log.info(this.getClass().getName() + ".bookingInsert Start!");
        log.info("예약 시작");

        String msg = "";
        String url = "/siso/booking";

        try {
            /* 데이터 입력 */
            String same = kopo.poly.Util.CmmUtil.nvl(request.getParameter("same"));
            String user_name = kopo.poly.Util.CmmUtil.nvl(request.getParameter("title"));
            String tel = kopo.poly.Util.CmmUtil.nvl(request.getParameter("tel"));
            String user_email = kopo.poly.Util.CmmUtil.nvl(request.getParameter("user_email"));
            String start_date = kopo.poly.Util.CmmUtil.nvl(request.getParameter("start_date"));
            String last_date = kopo.poly.Util.CmmUtil.nvl(request.getParameter("last_date"));
            String stay = kopo.poly.Util.CmmUtil.nvl(request.getParameter("stay"));
            String room = kopo.poly.Util.CmmUtil.nvl(request.getParameter("room"));
            String person = kopo.poly.Util.CmmUtil.nvl(request.getParameter("person"));
            String local = kopo.poly.Util.CmmUtil.nvl(request.getParameter("local"));
            String foreign = kopo.poly.Util.CmmUtil.nvl(request.getParameter("foreign"));
            String want = kopo.poly.Util.CmmUtil.nvl(request.getParameter("want"));
            String user_id = kopo.poly.Util.CmmUtil.nvl((String) session.getAttribute("user_id"));


            /* 데이터 입력 확인용 로그 */
            log.info("same : " + same);
            log.info("user_name : " + user_name);
            log.info("tel : " + tel);
            log.info("user_email : " + user_email);
            log.info("start_date : " + start_date);
            log.info("last_date : " + last_date);
            log.info("stay : " + stay);
            log.info("room : " + room);
            log.info("person : " + person);
            log.info("local : " + local);
            log.info("foreign : " + foreign);
            log.info("want : " + want);
            log.info("user_id : " + user_id);

            /* 예약 정보 삽입 종료 로그 */
            log.info(this.getClass().getName() + ".getInsertBooking End!");
            log.info("예약 정보 작성 종료");

            /* 데이터 저장하기 위해 DTO에 저장하기 */
            BookingDTO pDTO = new BookingDTO();
            pDTO.setSame(same);
            pDTO.setUser_name(user_name);
            pDTO.setTel(tel);
            pDTO.setUser_email(user_email);
            pDTO.setStart_date(start_date);
            pDTO.setLast_date(last_date);
            pDTO.setStay(stay);
            pDTO.setRoom(room);
            pDTO.setPerson(person);
            pDTO.setLocal(local);
            pDTO.setForeign(foreign);
            pDTO.setWant(want);
            pDTO.setUser_id(user_id);

            /* 예약하기 위한 비즈니스 로직 호출 */
            BookingService.insertBooking(pDTO);

            msg = "예약 되었습니다.";
            url = "main";


        } catch (Exception e) {

            /* 예약 실패시 사용자에게 보여줄 메시지 */
            msg = "예약 실패하였습니다." + e.getMessage();

            /* 예약 실패 로그*/
            log.info("예약실패 로그");
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
}


