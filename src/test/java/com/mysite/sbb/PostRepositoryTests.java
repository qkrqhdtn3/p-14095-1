package com.mysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class PostRepositoryTests {
    @Autowired
    private QuestionRepository questionRepository;

	@Test
    @DisplayName("findAll")
	void t1(){
//        List<Question> all = questionRepository.findAll();
//        assertEquals(2, all.size());
//        Question q = all.get(0);
//        assertEquals("sbb의 의미", q.getSubject());
        List<Question> questions = questionRepository.findAll();
        Question question = questions.get(0);
        assertThat(question.getSubject()).isEqualTo("sbb의 의미");
    }

    @Test
    @DisplayName("findById")
    void t2(){
//        Optional<Question> oq = this.questionRepository.findById(1);
//
//        if(oq.isPresent()){
//            Question q = oq.get();
//            assertEquals("sbb의 의미", q.getSubject());
//        }
        Question question = questionRepository.findById(1).get();
        assertThat(question.getSubject()).isEqualTo("sbb의 의미");
    }

    @Test
    @DisplayName("findBySubject")
    void t3(){
        Question question = questionRepository.findBySubject("sbb의 의미").get();
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectAndContent")
    void t4(){
        Question question = questionRepository.findBySubjectAndContent("sbb의 의미", "sbb를 알려주세요").get();
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectLike")
    void t5(){
        List<Question> questions = questionRepository.findBySubjectLike("sbb%");
        Question question = questions.get(0);
        assertThat(question.getSubject()).isEqualTo("sbb의 의미");
    }
}
