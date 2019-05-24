# Desafio Votação - User guide
Uma Sessão de Votação possui uma pauta, onde os Associados poderão votar. Cada associado poderá votar em apenas uma Sessão de Votação.
A aplicação deve permitir:
* Criar Associados
* Criar Pautas
* Abrir uma Sessão de Votação para uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default)
* Receber votos dos associados em pautas (os votos são apenas 'Favor'/'Contra'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta)
* Contabilizar os votos e dar o resultado da votação na pauta
## Associado
#### Criando Associados

Vamos criar 4 associados:

curl -X POST http://localhost:8080/associado \
  -d '{ 
	"nome" : "João da Silva", 
	"cpf": "111.111.111-11" 
}'

curl -X POST http://localhost:8080/associado \
  -d '{ 
	"nome" : "Juca de Oliveira", 
	"cpf": "222.222.222-22" 
}'

curl -X POST http://localhost:8080/associado \
  -d '{ 
	"nome" : "José Adamastor", 
	"cpf": "333.333.333-33" 
}'

curl -X POST http://localhost:8080/associado \
  -d '{ 
	"nome" : "Associado para deletar", 
	"cpf": "4444.4444.4444-44" 
}'

#### Consultando Associados

É possível consultar todos os associados:

curl -X GET http://localhost:8080/associado

Retorno: 
```
[ 
    {
        "id": 1,
        "nome": "João da Silva",
        "cpf": "111.111.111-11"
    }, 
    {
        "id": 2,
        "nome": "Juca de Oliveira",
        "cpf": "222.222.222-22"
    }, 
    {
        "id": 3,
        "nome": "José Adamastor",
        "cpf": "333.333.333-33"
    }, 
    {
        "id": 4,
        "nome": "Associado para deletar",
        "cpf": "4444.4444.4444-44"
    } 
]
```
Ou consultar o Associado pelo seu identificador:

curl -X GET http://localhost:8080/associado/1

Retorno:
```
{
    "id": 1,
    "nome": "João da Silva",
    "cpf": "111.111.111-11"
}
```
#### Deletando Associados

É possível deletar um Assosciado pelo identificador:

curl -X DELETE http://localhost:8080/associado/4

## Pauta
#### Criando Pautas

Vamos criar 3 pautas:

curl -X POST http://localhost:8080/pauta \
  -d '{
	"titulo": "Nova Previdência",
	"descricao": "Votação para aprovar a nova previdência"
}'

curl -X POST http://localhost:8080/pauta \
  -d '{
	"titulo": "Lava-Jato",
	"descricao": "Votação para aprovar a Lava-Jato"
}'

curl -X POST http://localhost:8080/pauta \
  -d '{
	"titulo": "Pauta para deletar",
	"descricao": "Pauta para deletar"
}'

#### Consultando Pautas

É possível consultar todas as Pautas:

curl -X GET http://localhost:8080/pauta

Retorno:
```
[
    {
        "id": 1,
        "titulo": "Nova Previdência",
        "descricao": "Votação para aprovar a nova previdência"
    },
    {
        "id": 2,
        "titulo": "Lava-Jato",
        "descricao": "Votação para aprovar a Lava-Jato"
    },
    {
        "id": 3,
        "titulo": "Pauta para deletar",
        "descricao": "Pauta para deletar"
    }
]
```
Ou consultar a Pauta pelo seu identificador:

curl -X GET http://localhost:8080/pauta/2

Retorno: 
```
{
    "id": 2,
    "titulo": "Lava-Jato",
    "descricao": "Votação para aprovar a Lava-Jato"
}
```
#### Deletando Pautas

É possível deletar uma Pauta pelo identificador:

curl -X DELETE http://localhost:8080/pauta/3

## Sessão de Votação

Uma sessão de votação é constituída por apenas uma pauta e um conjuntos de votos onde cada associado poderá votar apenas uma vez.
A Sessão de Votação tem um tempo de duração em segundos, onde só será possível adicionar um voto se a sessão não tiver sido expirada.

#### Criando Sessão de Votação

Agora vamos criar a Sessão de Votação propriamente dita.
Para criar a sessão é preciso passar uma pauta e o tempo de duração da sessão. O tempo de duração é opcional com default de 60 segundos.

Vamos criar 2 sessões:

curl -X POST 'http://localhost:8080/votacao?pautaId=1'

Retorno:
```
{
    "id": 1,
    "ativa": true,
    "dataHoraAtivacao": "2019-05-24T13:01:36.988+0000",
    "duracao": 60,
    "pauta": { 
        "id": 1,
        "titulo": "Nova Previdência",
        "descricao": "Votação para aprovar a nova previdência"
    }, \
    "votos": []
}
```
curl -X POST 'http://localhost:8080/votacao?pautaId=2&timeout=120' 

Retorno:
```
{
    "id": 2,
    "ativa": true,
    "dataHoraAtivacao": "2019-05-24T13:03:50.858+0000",
    "duracao": 120,
    "pauta": {
        "id": 2,
        "titulo": "Lava-Jato",
        "descricao": "Votação para aprovar a Lava-Jato"
    },
    "votos": []
}
```
## Votando!!!

Com as Sessões de Votação criadas, é possível receber os votos dos associados. 
Cada associado poderá votar apenas 1 vez em cada sessão.

#### Enviando o voto

Para enviar o voto, é preciso espeficiar o identificador da Sessão de Votação, o identificador do Associado e mais o Parecer.
O parecer com valor "true" significa "a Favor", enquanto o parecer com valor "false" significa "Contra".

curl -X POST 'http://localhost:8080/voto?votacaoId=1&associadoId=1&parecer=true' 

Retorno:
```
{
    "id": 1,
    "ativa": true,
    "dataHoraAtivacao": "2019-05-24T13:20:17.885+0000",
    "duracao": 120,
    "pauta": {
        "id": 1,
        "titulo": "Nova Previdência",
        "descricao": "Votação para aprovar a nova previdência"
    },
    "votos": [
        {
            "id": 1,
            "associado": {
                "id": 1,
                "nome": "João da Silva",
                "cpf": "111.111.111-11"
            },
            "parecer": true
        }
    ]
}
```
#### Mais votos

curl -X POST 'http://localhost:8080/voto?votacaoId=1&associadoId=2&parecer=true' \
curl -X POST 'http://localhost:8080/voto?votacaoId=1&associadoId=3&parecer=false' 

curl -X POST 'http://localhost:8080/voto?votacaoId=2&associadoId=1&parecer=false' \
curl -X POST 'http://localhost:8080/voto?votacaoId=2&associadoId=2&parecer=false' \
curl -X POST 'http://localhost:8080/voto?votacaoId=2&associadoId=3&parecer=true' 

## Resultado da Sessão de Votação

O resultado da Sessão de Votação traz o total de votos a favor e o total de votos contra além de informações adicionais sobre a sessão, como por exemplo, se ela está ativa, o tempo de duração e data/hora que ela foi criada.
Adicionalmente, traz uma conjunto com todos os votos de cada associado.

#### Consultado resultado

É possível consultar o resultado da votação através do identificador da Sessão de Votação:

curl -X GET http://localhost:8080/votacao/1

Resultado:
```
{
    "totalFavor": 2,
    "totalContra": 1,
    "votacao": {
        "id": 1,
        "ativa": true,
        "dataHoraAtivacao": "2019-05-24T13:20:17.885+0000",
        "duracao": 120,
        "pauta": {
            "id": 1,
            "titulo": "Nova Previdência",
            "descricao": "Votação para aprovar a nova previdência"
        },
        "votos": [
            {
                "id": 1,
                "associado": {
                    "id": 1,
                    "nome": "João da Silva",
                    "cpf": "111.111.111-11"
                },
                "parecer": true
            },
            { 
                "id": 2,
                "associado": {
                    "id": 2,
                    "nome": "Juca de Oliveira",
                    "cpf": "222.222.222-22"
                },
                "parecer": true
            },
            {
                "id": 3,
                "associado": {
                    "id": 3,
                    "nome": "José Adamastor",
                    "cpf": "333.333.333-33"
                },
                "parecer": false
            }
        ]
    }
}
```
curl -X GET http://localhost:8080/votacao/2

Resultado:
```
{ 
    "totalFavor": 1,
    "totalContra": 2,
    "votacao": {
        "id": 2,
        "ativa": true,
        "dataHoraAtivacao": "2019-05-24T13:20:23.620+0000",
        "duracao": 120,
        "pauta": {
            "id": 2,
            "titulo": "Lava-Jato",
            "descricao": "Votação para aprovar a Lava-Jato"
        },
        "votos": [
            {
                "id": 4,
                "associado": {
                    "id": 1,
                    "nome": "João da Silva",
                    "cpf": "111.111.111-11"
                },
                "parecer": false
            },
            {
                "id": 5,
                "associado": {
                    "id": 2,
                    "nome": "Juca de Oliveira",
                    "cpf": "222.222.222-22"
                },
                "parecer": false
            },
            {
                "id": 6,
                "associado": {
                    "id": 3,
                    "nome": "José Adamastor",
                    "cpf": "333.333.333-33"
                },
                "parecer": true
            }
        ]
    }
}
```

