package kopo.poly.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserInfoController {

    private final IUserInfoService userInfoService; // 서비스를 안에서 사용할 수 있게 하는 선언문

    /* 회원가입 화면으로 이동 */
    @GetMapping(value = "/user/join")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/join");

        return "/user/join";
    }


    //회원가입 로직 처리
    @PostMapping(value = "/user/insertUserInfo")
    public String insertUserInfo(HttpServletRequest request, ModelMap modelMap) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo start!");
        int res;
        String msg = ""; // 회원가입 결과에 대한 메시지를 전달할 변수
        String url = ""; // 회원가입 결과에 대한 url 을 전달할 변수

        UserInfoDTO pDTO = null; // 웹(외원정보 입력화면)에서 받는 정보를 저장할 변수

        try { /*
             웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!
             무조건 웹으로 받은 정보는 DTO 에 저장하기 위해 임시로 String 변수에 저장.

             CmmUtil 함수 : 자바는 null 값이 들어가면 무조건 에러남.
             해당 함수는 들어가있는 문자열이 null 값이면 빈 문자열로 바꿔준다.

             getParameter 함수 : HTTP 요청에 웹페이지 폼을 통해 전송된 데이터 값을 가져오는 함수.
             */

            String user_id = kopo.poly.Util.CmmUtil.nvl(request.getParameter("user_id")); //아이디
            String user_name = kopo.poly.Util.CmmUtil.nvl(request.getParameter("user_name")); //이름
            String password = kopo.poly.Util.CmmUtil.nvl(request.getParameter("password")); //비번
            String email = kopo.poly.Util.CmmUtil.nvl(request.getParameter("email")); //이메일
            String tel = kopo.poly.Util.CmmUtil.nvl(request.getParameter("tel")); //전화번호
            String addr1 = kopo.poly.Util.CmmUtil.nvl(request.getParameter("addr1")); //주소
            String addr2 = kopo.poly.Util.CmmUtil.nvl(request.getParameter("addr2")); //상세주소


            /* 값을 받았으면 반드시 로그를 찍어서 값이 제대로 들어오는지 파악해야함 */
            log.info("user_id : " + user_id);
            log.info("user_name : " + user_name);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("tel : " + tel);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);


            /* 웹(회원정보 입력화면)에서 받는 정보를 DTO 에 저장하기 시작
               무조건 웹으로 받는 정보는 DTO 에 저장해야한다고 이해할 것 */

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);
            pDTO.setUser_name(user_name);

            // 비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPassword(kopo.poly.Util.EncryptUtil.encHashSHA256(password));

            // 민감 정보인 이메일과 전화번호는 AES128-CBC 로 암호화함
            pDTO.setEmail(kopo.poly.Util.EncryptUtil.encAES128CBC(email));
            pDTO.setTel(kopo.poly.Util.EncryptUtil.encAES128CBC(tel));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);

            // 회원가입
            res = userInfoService.insertUserInfo(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다.";
                url = "/index";
                // 추후 회원가입 입력화면에서 ajax 를 활용해서 아이디 중복, 이메일 중복 체크 할 것.
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }

        } catch (Exception e) { // 저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            modelMap.addAttribute("msg", msg);
            modelMap.addAttribute("url", url);

            log.info(this.getClass().getName() + ".insertUserInfo End!");
        }

        return "/redirect";
    }


    // 로그인 처리 및 결과 알려주는 화면으로 이동
    @PostMapping(value = "/user/loginProc")
    public String loginProc(HttpServletRequest request, ModelMap model, HttpSession session) {
        // (값을 읽어옴, 값을 반환, 세션(웹서버의 기억공간)을 사용하기 위한 객체)
        log.info(this.getClass().getName() + ".loginProc Start!");

        String msg = ""; // 로그인 결과에 대한 메시지를 전달할 변수
        String url = ""; // 웹에서 받는 정보를 저장할 변수
        UserInfoDTO pDTO = null;

        try {
            /* 화면으로 넘어온 데이터를 Input 태그속 name 값과 매칭시켜 꺼냄.
            잘못돼서 null 값이 들어오면 nvl 함수를 사용해서 비어있는 값을 반환해라 */
            String user_id = kopo.poly.Util.CmmUtil.nvl(request.getParameter("user_id")); // 아이디
            String password = kopo.poly.Util.CmmUtil.nvl(request.getParameter("password")); // 비번

            log.info("user_id : " + user_id);
            log.info("password : " + password);

            /* 로그인 정보를 pDTO 에 담아서 서비스 로그인 로직을 실행시키고
               로그인 결과를 받아와서 rDTO 에 저장함 */
            // 웹에서 받는 정보를 저장할 변수를 메모리에 올림
            pDTO = new UserInfoDTO();
            pDTO.setUser_id(user_id);

            // 비밀번호는 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPassword(kopo.poly.Util.EncryptUtil.encHashSHA256(password));

            // 로그인을 위해 아이디와 비밀버호가 일치하는지 확인하기위한 userInfoService 호출하기
            UserInfoDTO rDTO = userInfoService.getLogin(pDTO);

            // 로그인 성공시 회원아이디 정보를 session 에 저장함.
            // 세션은 톰캣(was)의 메모리에 존재하며, 웹사이트에 접속한 사람(연결된 객체)마다 메모리에 값을 올린다.

            if (kopo.poly.Util.CmmUtil.nvl(rDTO.getUser_id()).length() > 0) { // 로그인 성공시

                session.setAttribute("SS_USER_ID", user_id);
                session.setAttribute("SS_USER_NAME", kopo.poly.Util.CmmUtil.nvl(rDTO.getUser_name()));

                msg = "로그인이 성공했습니다. \n" + rDTO.getUser_name() + "님 환영합니다.";
                url = "/main";

            } else { // 로그인 실패시
                msg = "로그인이 실패했습니다. \n";
                url = "/index";
            }
        } catch (Exception e) { // 저장이 실패되면 사용자에게 보여줄 메시지
            msg = "시스템 문제로 로그인이 실패했습니다.";
            log.info(e.toString());
            e.printStackTrace();
        } finally { // 다음 페이지로 넘어갈 정보를 전달
            model.addAttribute("msg", msg);
            model.addAttribute("url", url);

            log.info(this.getClass().getName() + ".loginProc End!");
        }
        return "/redirect";
    }

    // 로그인을 위한 입력 화면으로 이동
    @GetMapping(value = "/index")
    public String login() {
        log.info(this.getClass().getName() + "./index Start!");
        log.info(this.getClass().getName() + "./index End!");
        return "/index";
    }


    // 회원가입 전 아이디 중복체크하기 (Ajax 를 통해 입력한 아이디 정보 받음)
    @ResponseBody
    @PostMapping(value = "/user/getUserIdExists")
    public UserInfoDTO getUserExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists Start!");

        String user_id = kopo.poly.Util.CmmUtil.nvl(request.getParameter("user_id")); // 회원아이디

        log.info("user_id : " + user_id);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUser_id(user_id);

        // 회원아이디를 통해 중복된 아이디인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserIdExists(pDTO))
                .orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getUserIdExists End!");

        return rDTO;
    }


    // 회원가입 전 이메일 중복체크하기 (Ajax 를 통해 입력한 아이디 정보 받음)
    // 유효한 이메일인지 확인하기 위해 입력된 이메일에 인증번호 포함하여 메일 발송
    @ResponseBody
    @PostMapping(value = "/user/getEmailExists")
    public UserInfoDTO getEmailExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getEmailExists Start!");

        String email = kopo.poly.Util.CmmUtil.nvl(request.getParameter("email")); // 이메일

        log.info("email : " + email);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setEmail(kopo.poly.Util.EncryptUtil.encAES128CBC(email));

        // 입력된 이메일이 중복된 이메일인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getEmailExists(pDTO))
                .orElseGet(UserInfoDTO::new);

        log.info(this.getClass().getName() + ".getEmailExists End!");

        return rDTO;
    }

    // 아이디 찾기 화면 불러오기
    @GetMapping(value = "/user/searchUserId")
    public String searchUserId() {
        log.info(this.getClass().getName() + "아이디찾기 화면 불러오기");

        return "/user/searchUserId";
    }

    @GetMapping(value = "/user/searchPassword")
    public String searchPassword() {
        log.info(this.getClass().getName() + "비밀번호찾기 화면 불러오기");

        return "/user/searchPassword";
    }
}