package com.Tsing.tsingojbackendjudgeservice.judge;

import com.Tsing.tsingojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.Tsing.tsingojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.Tsing.tsingojbackendjudgeservice.judge.strategy.JudgeContext;
import com.Tsing.tsingojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.Tsing.tsingojbackendmodel.model.codesandbox.JudgeInfo;
import com.Tsing.tsingojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
