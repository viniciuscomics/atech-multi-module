package com.atech.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig extends WebSecurityConfigurerAdapter{
 
	@Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }
	
	@Bean
	public Docket detalheApi() {
 
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
 
		docket
		    .select()
		       .apis(RequestHandlerSelectors.basePackage("com.atech.resource"))
		       .paths(PathSelectors.any())
		       .build()
		       .globalResponseMessage(RequestMethod.GET, responseMessageForGET())
		       .apiInfo(this.informacoesApi().build());		
		   
	    docket.globalOperationParameters(Arrays.asList(
	           new ParameterBuilder()
	                 .name("Authorization")
	                 .modelRef(new ModelRef("string"))
	                 .parameterType("header")
	                 .required(true)
	                 .hidden(true)
	                 .description("You need to authenticate to consume.")	                            
	                 .build()
	               )	                    
	            );
	    
	    docket.globalOperationParameters(Arrays.asList(
		           new ParameterBuilder()
		                 .name("Content-type")
		                 .modelRef(new ModelRef("string"))
		                 .parameterType("header")
		                 .required(true)
		                 .hidden(true)
		                 .defaultValue("application/json")	                            
		                 .build()
		               )	                    
		            );
 
		return docket;
	}	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	   
	        web.ignoring().antMatchers("/v2/api-docs",
	                               "/configuration/ui",
	                               "/swagger-resources/**",
	                               "/configuration/security",
	                               "/swagger-ui.html",
	                               "/webjars/**","/actuator/health");
	}

	
 
	private ApiInfoBuilder informacoesApi() {
 
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
 
		apiInfoBuilder.title("Flight Control");
		apiInfoBuilder.description("Flight registration API");
		apiInfoBuilder.version("1.0");		
		apiInfoBuilder.licenseUrl("https://www.atech.com.br");
		apiInfoBuilder.contact(this.contato())
		.license("");
 
		return apiInfoBuilder;
 
	}
	private Contact contato() {
 
		return new Contact(
				"Vinicius Costa",
				"https://www.atech.com.br/", 
				"vinicius_csa@yahoo.com.br");
	}
	
	@SuppressWarnings("serial")
	private List<ResponseMessage> responseMessageForGET()
	{
	    return new ArrayList<ResponseMessage>() {{
	        add(new ResponseMessageBuilder()
	            .code(HttpStatus.BAD_REQUEST.value())
	            .message("parameter(s) invalid")
	            .responseModel(new ModelRef("Bad request"))
	            .build());
	        add(new ResponseMessageBuilder()
	            .code(HttpStatus.FORBIDDEN.value())
	            .message("Resource not allow!")
	            .build());
	        add(new ResponseMessageBuilder()
		            .code(HttpStatus.UNAUTHORIZED.value())
		            .message("Unauthorized!")
		            .build());
	    }};
	}
}
