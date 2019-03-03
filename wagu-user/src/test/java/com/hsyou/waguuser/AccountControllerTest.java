package com.hsyou.waguuser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsyou.waguuser.controller.AccountController;
import com.hsyou.waguuser.model.AccountDTO;
import com.hsyou.waguuser.model.ExternalAuthProvider;
import com.hsyou.waguuser.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

//import javax.ws.rs.core.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountService accountService;

    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();
    @Test
    public void testAccount() throws Exception {

//        String uid = "123123123";
//        String name = "name";
//        String email = "test@test.com";
//        ExternalAuthProvider provider = ExternalAuthProvider.GOOGLE;
//
//        AccountDTO accountDTO = AccountDTO.builder()
//                .uid(uid)
//                .name(name)
//                .email(email)
//                .provider(provider)
//                .build();
//
//        String json = OBJECT_MAPPER.writeValueAsString(accountDTO);
//
//        System.out.println(json);
//
//        mvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
//        .content(json)).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.uid").value(uid))
//                .andExpect(jsonPath("$.name").value(name))
//                .andExpect(jsonPath("$.email").value(email))
//                .andExpect(jsonPath("$.provider").value(ExternalAuthProvider.GOOGLE));

    }

}
