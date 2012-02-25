package com.uiubd.brta_driving_exam.utility;

public class Question {
	String questionTitle = "";
	String questionImage = "";
	int correctAnswerId = 0;
    public int selectedAnswerId = -1;
	String answer;
    String answerOptions [];
	boolean isAnswered;
	boolean isCorrectAnswered;
	
	public Question(String questionTitle, String questionImage, String answer) {
		super();
		this.questionTitle = questionTitle;
		this.questionImage = questionImage;
        this.answer = answer;
	}

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(String[] answerOptions) {
        this.answerOptions = answerOptions;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isCorrectAnswered() {
        return isCorrectAnswered;
    }

    public void setCorrectAnswered(boolean correctAnswered) {
        isCorrectAnswered = correctAnswered;
    }
}
