/*
    Authors: Daniel Davis, Dalton Claxton, Peyton White
    Date: 7 May 2020
    Description: A simple implementation of an 'impossible' quiz
 */

package edu.apsu.csci.final_4020.classes;

public class Question {
    private int id;

    private String question;
    private String category;
    private String answer;
    private String difficulty;

    Question(String difficulty, String question, String category, String answer, int id) {
        this.difficulty = difficulty;
        this.question = question;
        this.category = category;
        this.answer = answer;
        this.id = id;
    }
    public Question() {
        //default constructor that does nothing
        this.question = "";
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

    public String getDifficulty() {
        return difficulty;
    }

    public int getID() {
        return id;
    }
}
