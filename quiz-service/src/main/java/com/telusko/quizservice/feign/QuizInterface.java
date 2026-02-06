package com.telusko.quizservice.feign;

import com.telusko.quizservice.model.QuestionWrapper;
import com.telusko.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="question-service")
public interface QuizInterface {
    // When we say that quiz service needs to access the question-service
    // we need to mention that what methods we are going to call

    // generate
    @PostMapping("/question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
    (@RequestParam String categoryName, @RequestParam Integer numQuestions);


    // getQuestions {id}
    @PostMapping("/question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);


    // getScore
    @PostMapping("/question/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

}
