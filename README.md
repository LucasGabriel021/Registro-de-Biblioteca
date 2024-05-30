# Projeto Biblioteca

## Descrição do Projeto
Este projeto consiste na implementação de um sistema de gerenciamento de uma biblioteca utilizando sockets em Java. O sistema permite a listagem de livros disponíveis, aluguel e devolução de livros, e adição de novos livros ao acervo (em JSON).

## Estrutura do Projeto
O projeto é composto por três classes principais:
1. **Servidor**: Responsável por gerenciar as conexões dos clientes e realizar as operações solicitadas.
2. **Livro**: Representa os dados de um livro na biblioteca.
3. **Cliente**: Permite a interação dos usuários com o servidor, enviando comandos e recebendo respostas.

## Funcionalidades
- **LISTA**: Lista todos os livros disponíveis na biblioteca.
- **ALUGUEL**: Permite o aluguel de um livro, reduzindo o número de exemplares disponíveis.
- **DEVOLUCAO**: Permite a devolução de um livro, aumentando o número de exemplares disponíveis.
- **ADICIONA**: Adiciona um novo livro ao acervo da biblioteca.

## Como Executar

1. Certifique-se que o arquivo 'livros.json' está no mesmo diretório do servidor e contém um array JSON de livros.
2. Compile o código do servidor.
2. Compile o código do cliente.
3. No terminal, insira um dos comandos: LISTA, ALUGUEL, DEVOLUCAO, ADICIONA OU SAIDA.

## Integrantes do Grupo

João Pedro de Oliveira Teles

Kassya Beathrys Rosa Silva

Leonardo Casagrande e Silva

Lucas Gabriel Gomes de Oliveira
