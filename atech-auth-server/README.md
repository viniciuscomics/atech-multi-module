# Servidor de autenticação

## Descrição do projeto
1. API resposável por validar e autenticar usuários. Antes de consumir um recurso o client
devera solicitar um token de acesso para consumir os recursos.

### Configuração do projeto
  1.[application.properties](https://github.com/viniciuscomics/atech-config/blob/master/atech-auth-server.properties)
    No link acima podemos verificar as configurações do nosso servidor e dentre elas destaco as configurações 
    do nosso API Gateway, onde nosso servidor de autenticação irá se registrar.
    
    eureka.instance.hostname= localhost
    eureka.instance.port=8082
    eureka.client.register-with-eureka=true
    eureka.client.fetchRegistry= true
    eureka.client.serviceUrl.defaultZone= http://${eureka.instance.hostname}:${eureka.instance.port}/eureka/
    eureka.server.wait-time-in-ms-when-sync-empty= 3000
    
  2. Para os outros serviços que precisam de autenticação para serem consumidos, será necessário utilizar as configurações 
  abaixo, para que o mesmo consiga validar o token recebido.
  
    #Configurando a url apigateway/eurekaserver
    authserver.hostname=http://${eureka.instance.hostname}:${eureka.instance.port}/atech/authserver
    #Endpoint url do recurso disponibilizado pelo nosso servidorpara validar o token
    security.oauth2.resource.userInfoUri=${authserver.hostname}/user  
  Ou seja, o cliennt solicitará um token para o nosso servidor, sendo validado o servidor irá devolver um token 
  e o client irá chamar o serviço desejado passando esse token, o serviço irá fazer uma chamada para o recurso 
  definido na propriedade "authserver.hostname" para verificar se o token é válido e se já expirou, 
  caso seja valido é verificado se o usuário tem a ROLE necessaria para consumir o recurso. 
  
  ### Exemplos
    1. Exemplo de um request para solicitar um token
      curl --location --request POST 'http://localhost:8082/atech/authserver/oauth/token?grant_type=password&username=admin&password=atech' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --header 'Authorization: Basic YXRlY2g6JDJ5JDEyJGZMSEd4a3FxNk9GRW52VTMwZzBGay5CSHBtZi9vdVpwQk9TYkxwamV2RzVmTjlNNUFhaFJ5'
    
    2. Resposta recebida
       {
          "access_token": "9d602369-146b-467c-82ea-88a94f1faaed",
          "token_type": "bearer",
          "refresh_token": "da1cf237-dc4a-4d2b-ad8d-b0ae313a89f9",
          "expires_in": 1480,
          "scope": "read write"
      }     
     
  
 
  
