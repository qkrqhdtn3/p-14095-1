package com.mysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
        List<Question> questions = questionRepository.findAll();
        Question question = questions.get(0);
        assertThat(question.getSubject()).isEqualTo("sbb의 의미");
    }

    @Test
    @DisplayName("findById")
    void t2(){
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

    @Test
    @DisplayName("수정")
    @Transactional
    void t0(){
        Question question = questionRepository.findById(1).get();
        assertThat(question).isNotNull();

        question.setSubject("수정된 제목");
        questionRepository.save(question);

        Question foundQuestion = questionRepository.findBySubject("수정된 제목").get();
        assertThat(foundQuestion).isNotNull();
    }
}
