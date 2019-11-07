package com.everis.prystudent.services;

import java.util.Date;

import com.everis.prystudent.documents.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

	public Flux<Student> findAll();
	
	public Mono<Student> findById(String id);
	
	public Mono<Student> save(Student student);
	
	public Mono<Void> delete(Student student);
	
	public Mono<Void> deleteAll(Student student);
	
	public Flux<Student> findByName(String name);
	
	public Mono<Student> findByNumberDocument(String numberDocument);
	
	public Mono<Student> findByNumberDocumentOther(String numberDocument);
	
	public Flux<Student> findByDate(Date start, Date end);
}
