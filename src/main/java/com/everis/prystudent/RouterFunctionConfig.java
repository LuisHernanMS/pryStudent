package com.everis.prystudent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.everis.prystudent.controller.StudentController;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {
	
	/*@Bean
	public RouterFunction<ServerResponse> routes(StudentController controller){
		return RouterFunctions.route(GET("/api/students"), controller::listar)
				.andRoute(GET("/api/students/{name}"), controller::ver)
				.andRoute(GET("/api/students/date/{start}/{end}"), controller::verfec)
				.andRoute(POST("/api/students"), controller::crear)
				.andRoute(PUT("/api/students/{id}"), controller::editar)
				.andRoute(DELETE("/api/students/{numberDocument}"), controller::eliminar);
	}*/
	
	@Bean
	public RouterFunction<ServerResponse> routesf(StudentController controller	){
		return RouterFunctions.route(GET("/api/students"), controller::listar)
				.andRoute(GET("/api/students/{name}"), controller::ver)
				.andRoute(GET("/api/students/date/{start}/{end}"), controller::verfec)
				.andRoute(POST("/api/students"), controller::crear)
				.andRoute(PUT("/api/students/{id}"), controller::editar)
				.andRoute(DELETE("/api/students/{numberDocument}"), controller::eliminar);
	}
	
}
