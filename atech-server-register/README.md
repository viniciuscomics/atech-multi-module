# Servidor de rota e registro - API Gateway

## Descrição do projeto
  1. API responsável por registra e rotear requisições para a nossos serviços.
  Nesse projeto nos utilizamos o Eureka server para registro de serviços e o Zuul para 
  nosso API Gateway.

### Configuração do projeto
  1. bootstrap.properties - Esse é o primeiro arquivo que o Spring carrega para definir algumas configurações 
      e dentre elas usaremos as duas listadas abaixo onde definimos o nome da nossa aplicação que vai servir 
      como id para o servidor de configuração, e a URI do servidor de configuração.
       
          spring.application.name=atech-server-register
          spring.cloud.config.uri=http://localhost:8081
       
  2. [application.properties](https://github.com/viniciuscomics/atech-config/blob/master/atech-server-register.properties)
    No link acima você poderá verificar todas as configurações do nosso servidor, dentre elas destaco 
    as configurações abaixo, que é como nós configuramos nossas rotas para os serviços disponibilizados.
    
          zuul.prefix=/atech
          zuul.routes.first.path=/flightcontrol/**
          zuul.routes.first.serviceId=atech-flight-control
          zuul.routes.second.path=/authserver/**
          zuul.routes.second.serviceId=atech-auth-server
    
   Acima vimos a configuração de duas rotas, ou seja, quando chamarmos a url 
   http://seudominio/atech/authserver/, o Zuul vai pegar o valor do ServiceId que é o nome
   da nossa aplicação que se registrou e pegar a url dele e redirecionar a chamada e assim centrelizamos
   todas as nossa requisições para um único ponto.
   
   ### Observação
   1. Esse serviço deve ser iniciado após o servidor de configuração, pois os outros serviços vão se registrar
   para que os mesmos tenha suas rotas configradas e seus endpoints disponiveis.
   Esse é um serviço muito critico pois temos todas nossas rotas centralizadas nele.
