package com.everis.prystudent.controller;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.everis.prystudent.documents.Student;
import com.everis.prystudent.services.StudentService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	@Autowired
	private StudentService service;
	
	
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll(), Student.class)
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> ver(ServerRequest request){
		String name = request.pathVariable("name");
		
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(service.findByName(name), Student.class)
					.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> verfec(ServerRequest request){
		String start = request.pathVariable("start");
		String end = request.pathVariable("end");
		start=start+" 00:00:00.000 +0000";
		end=end+" 00:00:00.000 +0000";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
		try {
			Date inicio=df.parse(start);
			Date fin=df.parse(end);
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(service.findByDate(inicio, fin), Student.class)
					.switchIfEmpty(ServerResponse.notFound().build());
		} catch (Exception e) {
			ServerResponse.notFound().build();
			return Mono.error(e);
		}	
	}
	
	public Mono<ServerResponse> crear(ServerRequest request){
		
		Mono<Student> student= request.bodyToMono(Student.class);
		
		return student.flatMap(p->{
				return service.save(p);
		}).flatMap(p->ServerResponse.created(URI.create("api/students/".concat(p.getId())))
				.body(fromObject(p)));
	}
	
	public Mono<ServerResponse> editar(ServerRequest request){
		Mono<Student> student= request.bodyToMono(Student.class);
		String id = request.pathVariable("id");
		
		Mono<Student> studentDB = service.findById(id);
		
		return studentDB.zipWith(student, (db,req)->{
			db.setName(req.getName());
			db.setFlastName(req.getFlastName());
			db.setMlastName(req.getMlastName());
			db.setBirth(req.getBirth());
			db.setDocument(req.getDocument());
			db.setNumberDocument(req.getNumberDocument());
			db.setGender(req.getGender());
			return db;
		}).flatMap(p->ServerResponse.created(URI.create("/api/students".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.save(p),Student.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request){
		String numberDocument = request.pathVariable("numberDocument");
		Mono<Student> studentDB = service.findByNumberDocument(numberDocument);
		return studentDB.flatMap(q->service.delete(q).then(ServerResponse.noContent().build()))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
}
