# Serviço de cadastro de voo.

## Descrição de projeto
1. API resposável por expor e cadastrar voos de uma empresa aéra com seus respctivos horários.

### Configuração do projeto

  1. Estamos utilizando o flywaydb para fazermos migrações do nosso banco de dados.
  2. Será necessário configurar o servidor de configuração como explicado [aqui](https://github.com/viniciuscomics/atech-multi-module/blob/master/atech-server-register/README.md) na seção 1 da "Configuraçoes do projeto".
  3. Será necessario configurar o servidor de autenticação e api gateway como explicado [aqui](https://github.com/viniciuscomics/atech-multi-module/blob/master/atech-auth-server/README.md) 
  4. [Aqui](https://github.com/viniciuscomics/atech-config/blob/master/atech-flight-control.properties) 
     você encontra o arquivo de configuração desse projeto.
     
  5. Foram disponibilizados quatro recursos para esse serviço.
    
         #Get - Listar todos os voos
          http://seudomainapigateway/atech/flightcontrol/flight
         #Get - Buscar por id 
           http://seudomainapigateway/atech/flightcontrol/flight/{id}
           
         #Post - Cadastrar um novo voo
          http://seudomainapigateway/atech/flightcontrol/flight
          
         #PUT - Atualizar um voo
          http://seudomainapigateway/atech/flightcontrol/flight/{id}
          
### Observaçoes
  1. Ainda não temos api para cadastro de cidade,piloto e avião. Por isso estou usando o cascade all nos models, 
     não é recomendado, mas para uma segunda versão podemos liberar recursos para esses cadastros e fazer essa melhoria.
     Onde podemos criar outro serviço para esse cadastro e usar o Graphql.
          
### SWAGGER
  1. Essa API tambem fornece um recurso para a documentação em Swagger.
      
          http://seudomainapigateway//atech/flightcontrol/swagger-ui.html#/
          
          
![WhatsApp Image 2020-04-01 at 14 02 42](https://user-images.githubusercontent.com/42116742/78165525-cf5ddf80-7421-11ea-9f1f-5126b8e541f3.jpeg)

    
    
    
