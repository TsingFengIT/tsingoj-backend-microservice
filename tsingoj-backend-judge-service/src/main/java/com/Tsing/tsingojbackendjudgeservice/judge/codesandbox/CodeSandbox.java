package com.Tsing.tsingojbackendjudgeservice.judge.codesandbox;

import com.Tsing.tsingojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.Tsing.tsingojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
