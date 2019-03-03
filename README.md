# wagu-msa
멀티 모듈화 프로젝트, 아래 모든 모듈을 합침. 

TODO:
1. 빌드/테스트/배포 자동화


Post 8081
Comment 8084
User 8082
Gateway 8765
Config 8888
Search 8083
Eureka 8761

localhost:8765(gateway)/auth/login
-> 로그인
-> token 발급

Authorization
Bearer {token}

### 사용자정보 가져오기
GET {gateway}/account

### 글등록
POST {gateway}/api/post
Content-Type:application/json

{
  "contents": "contents",
  "hashtag": "#go#javascript"
}

### 글보기
GET {gateway}/api/post/{postId}


### 글 리스트
GET {gateway}/api/post/list

### 글 수정
PUT {gateway}/api/post/{postId}
Content-Type:application/json

{
	"id":{postId},
	"contents":"new contents",
	"hashtag":"#java#javascript"
}

