/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.classes;

public class Question {
    private int difficulty;
    private int id;

    private String question;
    private String category;
    private String answer;

    public Question(int difficulty) {
        this.difficulty = difficulty;
        this.question = "[Test] What is 1 + 1?";
        this.answer = "2";
        this.id = 1;
    }

    public String getQuestion() {
        return question;
    }

    public String getCategory() {
        return category;
    }

    public String getAnswer() {
        return answer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getID() {
        return id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setID(int id) {
        this.id = id;
    }
}
