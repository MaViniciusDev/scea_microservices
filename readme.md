# Sistema de Controle de Espaços Acadêmicos — Arquitetura de Microsserviços

Este repositório apresenta a refatoração de uma aplicação monolítica para uma **arquitetura baseada em microsserviços**, conforme os princípios discutidos na disciplina **Arquitetura de Software**, sob orientação do professor **Fernando Cesar Reis Borges**. O projeto foi desenvolvido como parte da avaliação da segunda unidade da disciplina.

## 🎯 Objetivo

Reestruturar o back-end do sistema de controle de espaços acadêmicos, previamente desenvolvido com arquitetura monolítica, adotando a abordagem de microsserviços. O front-end permanece inalterado, sendo apenas ajustado para se comunicar com os novos serviços por meio de uma API Gateway.

## ⚙️ Arquitetura Proposta

A nova arquitetura do sistema é composta por:

- **Microsserviços independentes**, segmentados por domínio de responsabilidade;
- **API Gateway**, que atua como ponto único de entrada para as requisições do cliente;
- **Service Discovery** (Eureka), responsável pelo registro e descoberta dinâmica dos serviços.

### Princípios de Arquitetura Aplicados

Cada microsserviço foi projetado seguindo rigorosamente os princípios de:

- **Baixo Acoplamento**
- **Alta Coesão**
- **Reúso**
- **Abstração**
- **Autonomia**
- **Ausência de Estado**
- **Separação de Subdomínios**

Cada microsserviço opera de forma isolada, com banco de dados próprio, respeitando seu contexto de negócio.

## 🧱 Componentes do Sistema

- `appuser`: Responsável pela gestão de usuários e seus respectivos papéis e permissões.
- `academicspaces`: Responsável pelo gerenciamento de espaços físicos acadêmicos.
- `api-gateway`: (A ser configurado) Ponto de entrada único para o sistema.
- `eureka-server`: (A ser configurado) Serviço de descoberta para registro e localização dos microsserviços.

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Cloud (Eureka, Gateway)**
- **Spring Data JPA**
- **Docker & Docker Compose**
- **Maven**
- **PostgreSQL**

## 🧪 Execução do Projeto

### Pré-requisitos

- Docker e Docker Compose instalados
- Java 17+
- Maven

### Executando via Docker

Para iniciar todos os serviços, execute:

```bash
docker-compose up --build

##📂 Estrutura do Repositório

scea_microservices-master/
├── academicspaces/         # Microsserviço de espaços acadêmicos
├── appuser/                # Microsserviço de usuários
├── docker-compose.yml      # Orquestração dos serviços
├── pom.xml                 # Projeto pai (Maven)
└── .idea/                  # Configurações do IntelliJ (opcional)

##👨‍🏫 Informações Acadêmicas

Curso: Engenharia de Software
Disciplina: Arquitetura de Software
Professor Orientador: Fernando Cesar Reis Borges
Unidade Avaliativa: Segunda Unidade
Aluno: Marcelo Vinicius
        Lucas Gaspari
        Yuri Brito
        João Henrique
        Gustavo Cruz
        Matheus Pires
        Luan 
        

