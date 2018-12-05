# url-revision-sample
url-revision-sample

[pt_BR]
# Revisão de URLs de conteúdos estáticos
É considerado aqui, aplicações java web no estilo MVC (não SPA), ou seja, a cada interação/requisição do usuário, uma nova página é renderizada com um novo conteúdo HTML como resposta.

# WRO4J; Concatenação, Compactação e Minificação 
Embora eu tenha no diretório físico os artefatos "jquery.maskedinput-1.3.js", "jquery-1.8.3.js" e "jquery-ui.js", por conta do "wro4j", podemos mapear um grupo lógico como "jquery", e invocar com base no nome do agrupador permitindo concatenação dos três artefatos, sendo esta uma otimização para o browser, pois apenas uma transação http request precisa ser aberta.

# CustomDispatcherFilter
Um dos meus objetivos é obrigar o browser a fazer requisições de novos artefatos estáticos a cada build/release, mas que o browser passa usar amplamente o cache para os estes recursos estáticos enquanto um pacote está estável, rodando em produção.

Sendo assim eu não queria deixar mapeado na aplicação requisições direto para o "wro4j". Eu precisava que a cada build, uma informação (de preferência de versão do build) fosse concatenado no HTML renderizado indicando que o browser necessáriamente precisa fazer novas requisições para os conteúdos estáticos (estes que serão num segundo momento disponibilizados via "wro4j").

A solução foi criar um Filter nomeado aqui como "CustomDispatcherFilter.java" que na declaração no "web.xml" tem em suas definições regras no estilo "from/to", que me permite através da url versionada, redirecionar internamente (no contexto da própria requisição) para o "wro4j" fazer o seu trabalho.

e.g; http://myhost.com:portnumber/url-revision-sample/static/v/201811231949/js/jquery.js

No filter "CustomDispatcherFilter.java" há a regra "from" ^.+\/static\/v\/\d{12}\/js\/(.*)$ que define um grupo (regex) no final que resultaria neste grupo o valor; /js/jquery.js

Ainda neste filter supracitado, há a regra "to" \/static\/compressed\/js\/$1 que uma vez combinado com o resultado do "from" resulta em; /static/compressed/js/jquery.js

Este ultimo está mapeado no "wro4j", que tem como agrupamento "jquery" para javascript os artefatos físicos "jquery.maskedinput-1.3.js", "jquery-1.8.3.js" e "jquery-ui.js", logo todos estes são devolvidos numa única transação http request (com a revisão do build) concatenados, compactados e minimizados.

# Run It
$ mvn jetty:run

# Invoke It, e.g;
$ curl 'localhost:8080/'

$ curl 'localhost:8080/static/v/201811231949/css/jquery.css'

$ curl 'localhost:8080/static/v/201811231949/js/jquery.js'

Thats it!
