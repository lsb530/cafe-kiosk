package com.boki.cafekiosk.spring;

import com.boki.cafekiosk.spring.client.mail.MailSendClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {
    @MockBean
    protected MailSendClient mailSendClient;
}
