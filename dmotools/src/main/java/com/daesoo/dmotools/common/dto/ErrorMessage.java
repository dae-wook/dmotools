package com.daesoo.dmotools.common.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
	ACCESS_DENIED("권한이 없습니다."),
    ALREADY_EXIST_COMMENT("이미 해당 게시글에 댓글을 작성한 사용자입니다."),
    ALREADY_VOTED("이미 투표했습니다."),
    ALREADY_EXPIRED_TIMER("이미 만료된 타이머입니다."),
    AUTHENTICATION_CODE_MISSMATCH("인증코드가 일치하지 않습니다."),
    AUTHENTICATION_FAILED("JWT가 올바르지 않습니다"),
    COMMENT_NOT_FOUND("해당 댓글이 존재하지 않습니다."),
    CLIENT_NOT_FOUND("클라이언트가 존재하지 않습니다."),
    CHARACTER_SLOT_IS_FULL("캐릭터는 최대 5개까지 생성 가능합니다"),
    CHARACTER_NOT_FOUND("캐릭터를 찾을 수 없습니다."),
    DUPLICATE_REQUEST("중복된 요청입니다."),
    EMAIL_AUTH_INFO_NOT_FOUND("이메일 인증 정보가 없습니다"),
    EMAIL_DUPLICATION("email이 중복됐습니다."),
    EMAIL_NOT_VERIFIED("인증되지 않은 이메일입니다"),
    EXPIRED_AUTHENTICATION("인증기한이 만료되었습니다."),
    IMAGE_NOT_FOUND("이미지가 존재하지 않습니다."),
    INTRODUCTION_TOO_LONG("소개는 500자 이내로 작성해야 합니다."),
    INVALID_AUTHENTICATION_REQUEST("잘못된 인증요청입니다."),
    KEYWORD_NOT_FOUND("해당 키워드 검색 기록이 없습니다"),
    MEMBER_NOT_FOUND("해당 사용자가 존재하지 않습니다."),
    NO_PERMISSION("권한이 없습니다."),
    OUT_OF_STAR_RANGE("별점 범위를 벗어났습니다."),
    RAID_NOT_FOUND("해당 레이드가 존재하지 않습니다."),
    TIMER_NOT_FOUND("해당 타이머가 존재하지 않습니다."),
    REPLY_MISSMATCH("일치하지 않는 대댓글 대상입니다."),
    REPORT_NOT_FOUND("해당 신고가 존재하지 않습니다"),
    SAME_PASSWORD("기존과 동일한 비밀번호로는 변경이 불가합니다."),
    SEAL_NOT_FOUND("해당 씰이 존재하지 않습니다"),
    UNAHTHORIZED("인증이 필요합니다."),
    USERID_DUPLICATION("userid가 중복됐습니다."),
    USERNAME_DUPLICATION("nickname이 중복됐습니다."),
    WRONG_EMAIL("해당 이메일의 유저정보가 없습니다."),
    WRONG_EMAIL_OR_PASSWORD("이메일 또는 패스워드가 일치하지 않습니다."),
    WRONG_JWT_TOKEN("JWT Token이 잘못되었습니다."),
    WRONG_PASSWORD("패스워드가 일치하지 않습니다."),
    WRONG_SORT_PARAMETER("잘못된 정렬 매개변수 입니다."),
    WRONG_USERID("userId가 일치하지 않습니다."),
    NULL_DTO("DTO 객체가 null입니다.");

    private final String message;

    @JsonValue
    public String getMessage() {
        return this.message;
    }
}
