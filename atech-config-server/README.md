# Servidor de configuração

## Descrição do projeto
  1. API responsável por obter as configurações dos serviços em um repósitório configurado. Ex Github.
  Para esse projeto foi utilizado o Github, mas em um projeto real o mesmo não é recomendado, o mais 
  certo seria usar o S3 da AWS.
 
### Configuração do projeto
    1. application.properties      
       Será necessario informar as duas propiedades abaixo, colocando a porta e a url do repositório de configurações.
       server.port=8081
       spring.cloud.config.server.git.uri=https://github.com/viniciuscomics/atech-config
    
    2. Os nomes dos arquivos de configuração que ficarem no repositório deverá ser o mesmo da 
       propriedade "spring.application.name" pois a mesma irá funcionar como o id da aplicação.
       Quando o serviço fizer uma chamada requisitando sua configuração, o server vai receber essa
       propriedade e vai até o repositório e buscar um arquivo .properties ou .yml de mesmo nome e 
       devolver para o solicitante.
       
### Observação
    1. Esse deve ser o primeiro serviço a ser iniciado. Sabemos que isso cria uma certa dependencia entre 
    os serviços, mas em um mundo real esse serviço seria escalado em uma cloud como a AWS, 
    onde o mesmo ficaria no ar 24x7 e em regiões e zonas diferentes. 

