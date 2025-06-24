package com.desafio.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot
 * 
 * Esta classe é responsável por iniciar a aplicação Spring Boot e configurar
 * automaticamente os componentes necessários baseado nas dependências do projeto.
 * 
 * A anotação @SpringBootApplication combina três anotações:
 * - @Configuration: Marca a classe como fonte de definições de beans
 * - @EnableAutoConfiguration: Habilita a configuração automática do Spring Boot
 * - @ComponentScan: Habilita a varredura de componentes no pacote atual
 */
@SpringBootApplication
public class DevApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevApplication.class, args);
	}
	//TODO: fazer os testes no JUnit
	//TODO: fazer o README

}
