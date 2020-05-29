package com.example.quizgame;

public class QuestionBank
{
    private  int answerResId;
    private boolean  isAnswerTrue;

    public QuestionBank(int answerResId, boolean isAnswerTrue) {
        this.answerResId = answerResId;
        this.isAnswerTrue = isAnswerTrue;
    }

    public int getAnswerResId() {
        return answerResId;
    }

    public void setAnswerResId(int answerResId) {
        this.answerResId = answerResId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }
}
