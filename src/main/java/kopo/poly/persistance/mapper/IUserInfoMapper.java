package kopo.poly.persistance.mapper;

import kopo.poly.dto.UserInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserInfoMapper {

    // 회원 가입하기 (회원정보 등록하기)
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;

    // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기
    // 매개변수로 UserInfoDTO 를 받고 UserInfoDTO 를 반환하는 getLogin 함수 실행
    UserInfoDTO getLogin(UserInfoDTO pDTO) throws Exception;

    // 회원 가입 전 아이디 중복체크하기 (DB 조회하기)
    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

    // 회원 가입 전 이메일 중복체크하기 (DB 조회하기)
    UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception;


}
