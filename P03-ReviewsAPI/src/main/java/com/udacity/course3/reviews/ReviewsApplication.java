package com.udacity.course3.reviews;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.udacity.course3.reviews.entity.MongoComment;
import com.udacity.course3.reviews.entity.MongoProduct;
import com.udacity.course3.reviews.repository.MongoCommentRepository;
import com.udacity.course3.reviews.repository.MongoProductRepository;
import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReviewsApplication {

		public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(MongoProductRepository mongoProductRepository) {
			return args -> {


				/*MongoComment mongoComment = new MongoComment();

				mongoComment.setCommentText("Prueba");
				mongoCommentRepository.save(mongoComment);*/

				MongoProduct mongoProduct = new MongoProduct();
				mongoProduct.setName("Test Name");
				mongoProduct.setDescription("Test Description");
				mongoProductRepository.save(mongoProduct);


				/*MongoClient mongoClient = MongoClients.create();

				MongoDatabase mongoDatabase = mongoClient.getDatabase("jdnd-c3");

				mongoDatabase.createCollection("Test2");

				mongoClient.close();*/
			};

	}
}