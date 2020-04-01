package com.atech.test.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.atech.AtechAuthServerApplication;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = AtechAuthServerApplication.class)
public class OAuthTest {

	private final String AUTHORIZATION = "Basic "+
			new String(Base64.encodeBase64("atech:$2y$12$fLHGxkqq6OFEnvU30g0Fk.BHpmf/ouZpBOSbLpjevG5fN9M5AahRy".getBytes()));
	
	private final String URL_TOKEN = "/oauth/token";
	private final String URL_VALID_TOKEN = "/user";
	
	
	@Autowired
    private WebApplicationContext wac;
 
    @Autowired
    private FilterChainProxy springSecurityFilterChain;
 
    private MockMvc mockMvc;
 
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .addFilter(springSecurityFilterChain).build();
    }
    
    private MultiValueMap<String, String> getHeader(String username, String password){
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password); 
        
        return params;
    }
    
    private Map<String,Object> request(MultiValueMap<String, String> params, String authorization, 
    		String url,ResultMatcher status) throws Exception {              

        ResultActions result
              = mockMvc.perform(post(url)
              .params(params)
              .header("Authorization",authorization)
              .header("Content-Type", "application/json")
              .accept("application/json;charset=UTF-8"))
              .andExpect(status);

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString);
     }    
    
    @Test
    public void unauthorized_get_token() throws Exception {
    	
    	mockMvc.perform(post(URL_TOKEN)
		        .params(getHeader("atec", "123456"))
		        .header("Authorization",AUTHORIZATION)		        
		        .accept("application/json;charset=UTF-8"))
		        .andExpect(status().is4xxClientError());
    }    
    
    @Test
    public void validate_scope_token() throws Exception {    	
    	String scope =  request(getHeader("admin", "atech"), AUTHORIZATION,URL_TOKEN, status().isOk()).get("scope").toString();
    	assertThat("read write",equalTo(scope));  
    }
    
    @Test
    public void validate_expire_token() throws Exception {    	
    	String expires =  request(getHeader("admin", "atech"), AUTHORIZATION,URL_TOKEN, status().isOk()).get("expires_in").toString();    	
    	assertThat("3",equalTo(expires));  
    }    
    
    @Test
    public void validate_token_expirate() throws Exception {
    	String token =  request(getHeader("admin", "atech"), AUTHORIZATION,URL_TOKEN, status().isOk()).get("access_token").toString();    	
    	Thread.sleep(4000);
    	
    	String error =  request(new LinkedMultiValueMap<String,String>(), "Bearer "+token,URL_VALID_TOKEN,
    			status().is4xxClientError()).get("error_description").toString();
    	
    	assertThat("Access token expired: "+token,equalTo(error));
    }
    
    @Test
    public void valid_size_token_authentication() throws Exception {       
    	String token =  request(getHeader("admin", "atech"), AUTHORIZATION,URL_TOKEN, status().isOk()).get("access_token").toString();       
        assertThat(36,equalTo(token.length()));      
    }
    
    @Test
    public void token_valid_sucess() throws Exception {
    	String token =  request(getHeader("admin", "atech"), AUTHORIZATION,URL_TOKEN,status().isOk()).get("access_token").toString();  
    	
    	String ok =  request(new LinkedMultiValueMap<String,String>(), "Bearer "+token,URL_VALID_TOKEN,status().isOk()).get("authenticated").toString();
    	assertThat("true",equalTo(ok)); 
    }
    
    @Test
    public void token_invalid_error() throws Exception {
    	
    	String error =  request(new LinkedMultiValueMap<String,String>(), 
    			"Bearer 15dbf35f-f33e-4b78-a012-35c2e36b6ca5",URL_VALID_TOKEN,status().is4xxClientError()).get("error").toString();
    	assertThat("invalid_token",equalTo(error));  
    }
   
}
