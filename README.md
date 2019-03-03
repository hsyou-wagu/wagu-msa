# wagu-msa
멀티 모듈화 프로젝트, 아래 모든 모듈을 합침. 


## Requirements
- JDK >= 8
- [Gradle](https://gradle.org/install/)
- Docker

## Quick Start

#### Single node ES
``` sh
docker run  --name kibana_test -e ELASTICSEARCH_URL=http://localhost:9200 -p 5601:5601 -d docker.elastic.co/kibana/kibana:6.6.0
```

### Single-broker Kafka
``` sh
docker-compose -f docker-compose-single-broker.yml up
```

#### Build
``` sh
gradle clean build
```
#### Run
``` sh
docker-compose up
```


## TODO:
1. 빌드/테스트/배포 자동화

Post 8081 <br>
Comment 8084 <br>
User 8082 <br>
Gateway 8765 <br>
Config 8888 <br>
Search 8083 <br>
Eureka 8761 <br>


## 주요 API 내용<br>

localhost:8765(gateway)/auth/login <br>
-> 로그인 <br>
-> token 발급 <br>
<br>
Authorization <br>
Bearer {token} <br>
<br>

### 로그인<br>
``` sh
GET {gateway}/auth/login
```

### 사용자정보 가져오기<br>
``` sh
GET {gateway}/account
```

### 글등록<br>
``` sh
POST {gateway}/api/post
Content-Type:application/json

{
  "contents": "contents",
  "hashtag": "#go#javascript"
}

```
### 글보기<br>
``` sh
GET {gateway}/api/post/{postId}
```
### 글 리스트<br>
``` sh
GET {gateway}/api/post/list
```
### 글 수정<br>
``` sh
PUT {gateway}/api/post/{postId}
Content-Type:application/json

{
	"id":{postId},
	"contents":"new contents",
	"hashtag":"#java#javascript"
}
```
### 코멘드 등록<br>
``` sh
POST {gateway}/api/comment
Content-Type:application/json

{
"contents":"comment"
}

```
### 글의 코멘트 리스트<br>
``` sh
GET {gateway}/api/comment/{postId}
```
...

<br>
### 키워드 검색<br>
``` sh
GET {gateway}/api/search?key={keyword}
```
