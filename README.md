# wagu-msa
멀티 모듈화 프로젝트, 아래 모든 모듈을 합침. 

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
GET {gateway}/auth/login<br>

### 사용자정보 가져오기<br>
GET {gateway}/account<br>

### 글등록<br>

POST {gateway}/api/post<br>
Content-Type:application/json<br>

{<br>
  "contents": "contents",<br>
  "hashtag": "#go#javascript"<br>
}<br>
<br>
### 글보기<br>
GET {gateway}/api/post/{postId}<br>
<br><br>

### 글 리스트<br>
GET {gateway}/api/post/list<br><br>

### 글 수정<br>
PUT {gateway}/api/post/{postId}<br>
Content-Type:application/json<br>
<br>
{<br>
	"id":{postId},<br>
	"contents":"new contents",<br>
	"hashtag":"#java#javascript"<br>
}<br>
<br>

### 코멘드 등록<br>
POST {gateway}/api/comment<br>
Content-Type:application/json<br>
<br>
{<br>
"contents":"comment"<br>
}<br>
<br>
### 글의 코멘트 리스트<br>
GET {gateway}/api/comment/{postId}<br>
...

<br>
### 키워드 검색<br>
GET {gateway}/api/search?key={keyword}
