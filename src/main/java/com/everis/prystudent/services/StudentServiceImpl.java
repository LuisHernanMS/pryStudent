package com.everis.prystudent.services;

import java.util.Date;

import javax.naming.ServiceUnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.prystudent.dao.StudentDao;
import com.everis.prystudent.documents.Student;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentDao dao;
	
	@Override
	public Flux<Student> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Student> findById(String id) {
		return dao.findById(id);
	}
	

	@Override
	public Mono<Student> save(Student student) {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("prystudent");
		DBCollection dbCollection = db.getCollection("student");
		DBObject query= new BasicDBObject("numberDocument", student.getNumberDocument());
		Integer result = dbCollection.find(query).count(); 
		if(result>0) {
			return Mono.error(new ServiceUnavailableException("There is a student registered with the ID "+student.getNumberDocument()));
		}else {
			return dao.save(student);
		}
	}

	@Override
	public Mono<Void> delete(Student student) {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("pryfamily");
		DBCollection dbCollection = db.getCollection("family");
		
		DBObject query = new BasicDBObject("numberDocumentst", student.getNumberDocument());
		Integer result = dbCollection.find(query).count(); 
		
		if(result>0) {
			DBObject del=dbCollection.findAndRemove(query);
			db.command(del);
		}
		return dao.delete(student);
	}

	@Override
	public Flux<Student> findByName(String name) {
		return dao.obtenerPorName(name);
	}
	
	@Override
	public Mono<Student> findByNumberDocument(String numberDocument) {
		return dao.obtenerPorNumberDocument(numberDocument);
	}

	@Override
	public Flux<Student> findByDate(Date start, Date end) {
		return dao.obtenerPorDate(start, end);
	}

	@Override
	public Mono<Void> deleteAll(Student student) {
		// TODO Auto-generated method stub
		return dao.deleteAll();
	}

	@Override
	public Mono<Student> findByNumberDocumentOther(String numberDocument) {
		
		return dao.findNumberDocument(numberDocument);
	}
}
