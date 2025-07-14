# 📈 Cache을 이용한 성능개선 프로젝트

# 👨‍💻 Team

* 팀명:
* 팀소개:
* 팀원 및 역할 분담

| 이름   | 역할  | 주요 담당 업무 |
| ------ | ----- | -------------- |
| 안지현 | 팀장  | 게시글(레시피) |
| 김원준 | 팀원  | 인증/인가 |
| 안은욱 | 팀원  | 프로필/유저 |
| 이효희 | 팀원  | 좋아요 |
| 김도한 | 팀원  | 대시보드 |
| 윤도웅 | 팀원  | 활동로그 |

## 🛠 사용 기술

| 구분        | 내용 |
| ----------- | ---- |
| **언어** | Java 17 |
| **프레임워크** | Spring Boot 3.5.0 |
| **빌드 도구** | Gradle 8.5 |
| **ORM** | Spring Data JPA (Hibernate 6.6.13.Final), QueryDSL 5.1.0 |
| **데이터베이스** | MySQL 8.0 이상 (JDBC Driver) |
| **보안** | Spring Security, JWT (jjwt 0.11.5), 비밀번호 암호화 (BCrypt 0.10.2) |
| **라이브러리** | Lombok |
| **API 테스트** | Postman |
| **DB 설계** | ERD Cloud |
| **개발 환경** | IntelliJ IDEA |

## 💻 개발 도구

* IntelliJ IDEA
* Git
* Postman

## 📃 프로젝트 설계

* API 명세서
    * https://www.notion.so/teamsparta/2292dc3ef51480fc8953cf5c55f8bbfe?v=2292dc3ef5148111887a000c395f877b&source=copy_link
* 와이어 프레임
    * https://claude.ai/public/artifacts/7693aa5a-aca9-4014-8dee-68b06af3b6a1
    * ID: `admin`
    * PASSWORD: `password`

## 📁 폴더 구조
```
com.example.project
│
├── global                           ← 공통 설정 및 보안 (담당: 김원준)
│   ├── config
│   ├── exception
│   ├── security
│   └── util
│
├── domain
│   ├── auth                         ← 로그인/로그아웃 (담당: 김원준)
│   │   ├── controller
│   │   │   └── dto                  // 요청/응답 DTO
│   │   ├── service
│   │   │   └── dto                  // 비즈니스 내부 전달 DTO (Optional)
│   │   └── domain
│   │       ├── model               // RefreshToken, AuthUser 등
│   │       ├── repository
│   │       └── dto                 // 인증 관련 공용 DTO
│   │
│   ├── user                         ← 회원 관리 (담당: 안은욱)
│   │   ├── controller
│   │   │   └── dto
│   │   ├── service
│   │   │   └── dto
│   │   └── domain
│   │       ├── model               // User, Profile, Authority 등
│   │       ├── repository
│   │       └── dto
│   │
│   ├── recipe                         ← 레시피(게시글) (담당: 안지현)
│   │   ├── controller
│   │   │   └── dto
│   │   ├── service
│   │   │   └── dto
│   │   └── domain
│   │       ├── model
│   │       ├── repository
│   │       └── dto
│   │
│   ├── like                      ← 좋아요 (담당: 이효희)
│   │   ├── controller
│   │   │   └── dto
│   │   ├── service
│   │   │   └── dto
│   │   └── domain
│   │       ├── model
│   │       ├── repository
│   │       └── dto
│   │
│   ├── dashboard                    ← 대시보드 (담당: 김도한)
│   │   ├── controller
│   │   │   └── dto
│   │   ├── service
│   │      └── dto
│   │   
│   │
│   └── log                          ← 활동 로그 (담당: 윤도웅)
│       ├── controller
│       │   └── dto
│       ├── service
│       │   └── dto
│       └── domain
│           ├── model
│           ├── repository
│           └── dto
│
└── Application.java
```

## 📊 API 통계

### API Count Summary

```
- AuthController.java
  ├── GET APIs:     1
  ├── POST APIs:    4
  └── DELETE APIs:  1

- UserController.java
  ├── GET APIs:     2

- RecipesController.java
  ├── GET APIs:     6
  ├── POST APIs:    1
  ├── PATCH APIs:   1
  └── DELETE APIs:  1

- LikesController.java
  ├── GET APIs:     1
  ├── POST APIs:    1
  └── DELETE APIs:  1

- LogController.java
  ├── GET APIs:     1

- DashboardController.java
  ├── GET APIs:     3

```

### 📋 API Statistics

| 메서드 | 개수 |
| :----- | :--- |
| GET    | 14   |
| POST   | 6    |
| PATCH  | 2    |
| DELETE | 2    |
| **총합** | **24** |

---

## 🚀 사용법

* ~~~

## ✨ 기능 구현

### ㅇㅇㅇ

## 📝 새로 배운 내용
* ㅇㅇㅇ

## ❗ 문제 해결 (트러블 슈팅)

* ㅇㅇㅇ
