package com.students.studentsmanagement.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResponseCodes {
    /**
     * 返回码及描述
     */

    SUCCESS(200, "操作成功"),
    PARAMS_ERROR(400, "请求参数错误"),
    NO_AUTH(401, "无权限"),
    SYSTEM_ERROR(500, "系统内部异常"),
    FAIL(501, "操作失败"),
    QUERY_NULL_ERROR(601, "查询数据为空"),
    INSERT_ERROR(602, "插入数据错误"),
    DELETE_ERROR(603, "删除数据错误"),
    TOKEN_EXPIRE(605, "token已过期"),
    NULL_ERROR(606, "请求参数为空");


    private final Integer code;

    private final String message;
}
