# Repositório mega-hack-women-2020
 Armazena o projeto desenvolvido pelo time 21, para o desafio da Árvore, durante o Mega Hack Women de 09/2020.

# Projeto Otto Play

O projeto Otto Play extrai textos de um pdf e os converte em aúdio, ou seja, realiza a leitura digital do pdf.</br>
A sintetização do texto em áudio é feita através de uma API de Serviços Cognitivos do Azure, a *text-to-speech*. 
A mesma pode ser utilizada via SDK ou REST, sendo esta última a forma adotada por este projeto.

*Obs:*</br>
A sintetização do texto em voz está sendo feita do lado servidor.</br>
É possível alterar para que a sintetização seja feita do lado cliente, adicionando a implementação em *getAudioDaPagina* no arquivo *scripts.js*.


## Sobre a API

A API *text-to-speech* foi adotada por ser um recurso possível de ser utilizado por um período gratuíto em nossa região (Brasil), possibilitando, dessa forma, a ilustração da leitura digital.</br>
Entretanto, para o propósito da leitura digital, o mais indicado é o uso da API de Idioma - Leitura Avançada *(immersive-reader)*, também de Serviços Cognitivos do Azure.</br>
Infelizmente não foi possível utilizá-la por **não ser** um recurso disponível para uso **gratuíto** temporário no Brasil. Porém, assim como ocorre para a *text-to-speech*, existe uma vasta documentação de apoio ao desenvolvimento para a *immersive-reader*.

## Pré Requisitos

Ter seguido os passos [Experimente o serviço de Fala gratuitamente](https://docs.microsoft.com/pt-br/azure/cognitive-services/speech-service/overview#try-the-speech-service-for-free), que consiste em:
  * Criar uma conta Microsoft
  * Criar uma conta do Azure usando a conta Microsoft
  * Criar o recurso de Fala no portal do Azure 
  * Após a conclusão da implantação do recurso, copiar a chave de assinatura do serviço de Fala para utilizar no projeto.

## Execute o Projeto

> O Otto Play foi desenvolvido utilizando a ferramenta Eclipse Java EE, criado como Dynamic Web Project e, posteriormente configurado para o Maven.

* [Baixe/ clone o projeto](https://github.com/Gerusa/mega-hack-women-2020.git)
* Importe para sua IDE
* Substitua o valor *ConstantUtil.CHAVE_ACESSO* pela chave de acesso gerada em *Pré Requisitos*
* Configure o servidor Tomcat em sua workspace. Versão utilizada durante o desenvolvimento: apache-tomcat-9.0.37.
* Adicione o projeto no servidor
* Inicie o servidor
* Acesse a aplicação *http://localhost:8080/otto-play/index.jsp* Obs: Mude a porta se necessário.
* Aperte o play/pause, localizado no 'coração' do macaquinho Otto, para ouvir/pausar a leitura. 

## Links Úteis
- [Documentação da API de Leitura Avançada - immersive-reader](https://docs.microsoft.com/pt-br/azure/cognitive-services/immersive-reader/overview)
- [Documentação da API de Fala - text-to-speech](https://docs.microsoft.com/pt-br/azure/cognitive-services/speech-service/text-to-speech)
- [Todas as API's de Serviços Cognitivos do Azure](https://azure.microsoft.com/pt-br/services/cognitive-services/#api)