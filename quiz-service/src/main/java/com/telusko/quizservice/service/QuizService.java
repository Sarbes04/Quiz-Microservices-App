package com.telusko.quizservice.service;


import com.telusko.quizservice.dao.QuizDao;
import com.telusko.quizservice.feign.QuizInterface;
import com.telusko.quizservice.model.QuestionWrapper;
import com.telusko.quizservice.model.Quiz;
import com.telusko.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        //List<Integer> questions =
        //call the generate url of question-service, using a rest template
        //to the url http://localhost:8080/question/generate, this is the first way. Alternatively,
        //This is the first time we are interacting between two services
        //So we will need two things:

        // 1)Feign Client: It is the same as your http web request, the difference
        // being is that it provides a declarative way of requesting the other service.
        // so you don't have to hardcode everything.

        // 2)Service Discovery: Every service we are using in our project has to be discoverable
        // So we solve this issue by using Eureka, a server, using which we can discover a microservice
        // and all the microservices register themselves on this eureka server
        // and then one microservice can search another microservice using a eureka client from the eureka server
        // so every microservice that we will search will have a eureka client with it.

        //So we are solving the problem of IP Address, don't need to expose PORTS, and with the help of Feign
        //we can directly request the service using the service name

        System.out.println("reached quiz service");
        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestions();
        ResponseEntity<List<QuestionWrapper>> questions =  quizInterface.getQuestionsFromId(questionIds);


        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
