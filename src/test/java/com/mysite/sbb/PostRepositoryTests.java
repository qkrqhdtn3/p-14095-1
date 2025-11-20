package com.mysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
class PostRepositoryTests {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("findAll")
    void t1() {
        List<Question> questions = questionRepository.findAll();
        Question question = questions.get(0);
        assertThat(question.getSubject()).isEqualTo("sbb의 의미");
    }

    @Test
    @DisplayName("findById")
    void t2() {
        Question question = questionRepository.findById(1).get();
        assertThat(question.getSubject()).isEqualTo("sbb의 의미");
    }

    @Test
    @DisplayName("findBySubject")
    void t3() {
        Question question = questionRepository.findBySubject("sbb의 의미").get();
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectAndContent")
    void t4() {
        Question question = questionRepository.findBySubjectAndContent("sbb의 의미", "sbb를 알려주세요").get();
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectLike")
    void t5() {
        List<Question> questions = questionRepository.findBySubjectLike("sbb%");
        Question question = questions.get(0);
        assertThat(question.getSubject()).isEqualTo("sbb의 의미");
    }

    @Test
    @DisplayName("수정")
    @Transactional
    void t6() {
        Question question = questionRepository.findById(1).get();
        assertThat(question).isNotNull();

        question.setSubject("수정된 제목");
        questionRepository.save(question);

        Question foundQuestion = questionRepository.findBySubject("수정된 제목").get();
        assertThat(foundQuestion).isNotNull();
    }

    @Test
    @DisplayName("삭제")
    void t7() {
        assertThat(questionRepository.count()).isEqualTo(2);

        Question question = questionRepository.findById(1).get();
        questionRepository.delete(question);

        assertThat(questionRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("답변 생성")
    @Transactional
    void t8() {
        Question question = questionRepository.findById(2).get();

        Answer answer = new Answer();
        answer.setContent("햄버거");
        answer.setQuestion(question);

        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    @Test
    @DisplayName("답변 생성 by oneToMany")
    @Transactional
    void t9() {
        Question question = questionRepository.findById(2).get();

        int beforeCount = question.getAnswers().size();
        Answer newAnswer = question.addAnswer("햄버거");

        assertThat(newAnswer.getId()).isEqualTo(0);

        int afterCount = question.getAnswers().size();

assertThat(afterCount).isEqualTo(beforeCount + 1);
    }

    @Test
    @DisplayName("답변 조회")
    void t10() {
        Answer answer = answerRepository.findById(1).get();

        assertThat(answer.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("답변 조회 by oneToMany")
    void t11(){
        Question question = questionRepository.findById(2).get();

        List<Answer> answers = question.getAnswers();
        assertThat(answers.size()).isEqualTo(1);

        Answer answer = answers.get(0);
        assertThat(answer.getContent()).isEqualTo("햄버거");
    }

    @Test
    @DisplayName("findAnswer by question")
    void t12(){
        Question question = questionRepository.findById(2).get();

        Answer answer = question.getAnswers().get(0);

        assertThat(answer.getContent()).isEqualTo("햄버거");
    }
}
