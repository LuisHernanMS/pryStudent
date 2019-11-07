package com.everis.prystudent.dao;

import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.prystudent.documents.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentDao extends ReactiveMongoRepository<Student, String>{
	
	public Flux<Student> findByName(String name);
	
	@Query("{ $or : [{ name : ?0  }, { numberDocument : ?0  }, { id : ?0}]}")
	public Flux<Student> obtenerPorName(String name);
	
	public Mono<Student> findByNumberDocument(String numberDocument);
	
	@Query("{ numberDocument : ?0  }")
	public Mono<Student> obtenerPorNumberDocument(String numberDocument);
	
	@Query("{ numberDocumentst : ?0  }")
	public Mono<Student> findNumberDocument(String numberDocument);

	//public Flux<student> findByDate(String start, String end);
	
	@Query("{ birth : {$gt : ?0, $lt : ?1}  }")
	public Flux<Student> obtenerPorDate(Date start, Date end);
}
