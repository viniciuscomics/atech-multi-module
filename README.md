# Desafio Desenvolvedor Java - Atech

## Informações do projeto
  1. Para criar o projeto utilizamos o multi-module do Maven, onde conseguimos 
  centrelizar o gerenciamento das dependencias no pom.xml "pai" e termos um
  melhor controle.
  Abaixo segue um resumo dos 4 serviços que foram criados.

### Ferramentas
  1. O projeto foi desenvolvido com Java 8 e utilizando o framework Spring Boot v2.2.6.
  2. Para testes unitários foi utilizado Junit 4.
  3. Postgres - Docker image postgres:10.5-alpine.
  
### Estrutura do projeto  
  1. atech-config-server - Esse serviço tem a responsabilidade de buscar arquivo de 
  configuração de um determinado serviço em um repositório de arquivos. Ex. Github.
  Esse deve ser o primeiro serviço a ser iniciado.	
  Informações do projeto [aqui](https://github.com/viniciuscomics/atech-multi-module/tree/master/atech-config-server)
       
  2. atech-server-register - Esse serviço é responsavel pelo registro e trafego dos nossos serviços, ou seja,
  nosso API Gateway.
  Esse deve ser o segundo serviço a ser iniciado.
  Informações do projeto [aqui](https://github.com/viniciuscomics/atech-multi-module/tree/master/atech-server-register)
	
  3. atech-auth-server - Esse serviço tem a responsabilidade de authenticar o acesso de um usuário,
  fornecendo um token de acesso com o tempo de duração definido pelo administrador do sistema.
  Esse deve ser o terceiro serviço a ser iniciado,
  Informações do projeto [aqui](https://github.com/viniciuscomics/atech-multi-module/tree/master/atech-auth-server)
	
  4. flight-control - Esse serviço tem a responsabilidade de cadastrar,atualizar e buscar voos.
  Após os tres serviços anteriores estiverem no ar esse serviço pode ser iniciado, pois ele precisa 
  se registrar em nosso API Gateway, caso API Gateway não estiver no ar, ele vai iniciar normal mas 
  vai ficar inacessivel de fora.
  Informações do projeto [aqui](https://github.com/viniciuscomics/atech-multi-module/tree/master/flight-control)
	
## Pré Requisitos 
	1. JDK Java 8 instalado
	2. Maven 3.x.x Instalado
	3. Docker 19.x.x
	Para desenvolvimento utilizei o docker para criar containers com o postgres.	
		
## Compilando o projeto

	1. Apos ter baixado o projeto, abra um terminal de comando(ex: git bash)  no diretório do projeto.
	2. Para compilar execute o script run.sh dentro do diretorio raiz do projeto
	"atech-multi-module" que será executado os testes unitários e de integração e compilar todos os 
	projetos e executar o docker-compose.	
	
## Executando os testes unitários
	1. Apos ter baixado o projeto, abra um terminal de comando(ex: git bash)  no diretório do projeto.
	2. Execute docker run -p 5665:5432 --name authserverdbteste -e POSTGRES_USER=atech -e POSTGRES_PASSWORD=atech -e POSTGRES_DB=authserverdbteste -d postgres:10.5-alpine
	3. Digite o comando "mvn test" e aguarde o fim dos testes.
		 
## Executando a API no ambiente local
	
	1. Será necessario criar o banco de dados do atech-auth-server e o do flight-control.
	
	# docker run -p 5442:5432 --name authserverdb -e POSTGRES_USER=atech -e POSTGRES_PASSWORD=atech -e POSTGRES_DB=authserverdb -d postgres:10.5-alpine
	
	# docker run -p 5432:5432 --name flightcontroldb -e POSTGRES_USER=atech -e POSTGRES_PASSWORD=atech -e POSTGRES_DB=flightcontroldb -d postgres:10.5-alpine	
	
	1. Após ter compilado os serviços, vá até a pasta target que se encontra no diretório do projeto.

	2. Na pasta target, copie o .jar da API  e abra o terminal de comando(ex: git bash)
	
	3. Digite o comando "java -jar o_jar_da_sua_api.jar". Na ordem que foi mostrado no inicio desse documento.
	Caso queira passar o um arquivo de propiedade customizado, basta rodar o comando 
	"java -jar o_jar_da_sua_api.jar -Dspring.config.location=your.properties".
	Mas lembre-se, temos o servidor de configuração e os nossos serviços estão buscando suas respectivas configuração lá.
	
	4. Aguarde o sua api inicializar, se tudo der certo as últimas mensagens impressas no console serão;
		
		Tomcat initialized with port(s): xxxx (http)
		Starting service [Tomcat]
		Starting Servlet engine: [Apache Tomcat/9.0.29]
		Initializing Spring embedded WebApplicationContext
		Root WebApplicationContext: initialization completed in 2664 ms	
