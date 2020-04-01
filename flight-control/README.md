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
     Get / Post
    
