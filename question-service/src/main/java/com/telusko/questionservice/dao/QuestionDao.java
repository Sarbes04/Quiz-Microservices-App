package com.telusko.questionservice.dao;

import com.telusko.questionservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {
    //We don't have to define this function, and we also don't need to
    //write the SQL Query - No!. The thing is, category is a part of the
    //table. So the Data JPA is smart enough to understand that you are
    //trying to find data based on a category.
    //But when you are trying to do a lot of customization, then it will not work.
    //In that case you have to use HQL or JPQL.
    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, Integer numQ);
//    nativeQuery = true
//    Tells Spring that this is raw SQL, not JPQL
//    Uses database-specific SQL features
//    Required here because RANDOM() is not supported in JPQL
}
