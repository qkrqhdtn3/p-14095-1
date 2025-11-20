package com.mysite.sbb;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//@Profile("test")
@Configuration
@RequiredArgsConstructor
public class TestInitData {
    @Autowired
    @Lazy
    private TestInitData self;
    private final QuestionRepository questionRepository;

    @Bean
    ApplicationRunner testInitDataApllicationRunner() {
        return args ->{
            self.work1();
        };
    }

    @Transactional
    void work1(){
        if(questionRepository.count() > 0) return;

        Question q1 = new Question();
        q1.setSubject("sbb의 의미");
        q1.setContent("sbb를 알려주세요");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("점심 추천");
        q2.setContent("점심 메뉴를 추천해주세요");
        q2.setCreateDate(LocalDateTime.now());
        q2.addAnswer("햄버거");
        questionRepository.save(q2);
    }
}
