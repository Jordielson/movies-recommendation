# movies-recommendation
A recomendação de filmes é atraves do algoritmo do vizinho mais próximo a partir das avaliações dos filmes pelos usuários. Sendo assim, quando mais avaliações e usuários o software tiver, maiores a chance do algoritmo fazer melhores recomendações. 

Foi usado o Java com Spring para fazer a API REST e o Hibernate no mapeamento objeto relacional para o bando de dados gerenciado pelo MYSQL.

Neste projeto usamos as informações dos filmes que estão base do The Movie Database(https://www.themoviedb.org/), portanto armazenamos apenas as informações que são realmente relevantes para fazer a recomendação dos filmes.

## Como executar este projeto
  - Primeiramente, é necessário ter o MYSQL e o Java instalado
  - Gerar sua API KEY no site https://www.themoviedb.org/ 
  - Adicionar sua chave do The MovieDB em movies-recommendation/src/main/resources/static/js/app_header.js, no lugar de "YOUR API KEY" 
  - Criar o banco de dados *movies_db* 
  - Adcionar a senha do mysql em movies-recommendation/src/main/resources/application.properties 
  - Iniciar movies-recommendation/src/main/java/com/recommedation/movie/Application.java 
  - Abrir seu navegador e acessar http://localhost:8080/movies/home
