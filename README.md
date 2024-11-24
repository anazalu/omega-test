# Omega-test

## Manuālo testu pārskats:
https://docs.google.com/document/d/1EpmJKQKN2E691fSuPNqmfEovVwBCx8Bh6gmhr1pHRuo/edit?usp=sharing 

## Automatizēto testu palaišana:

(Nepieciešams Maven)
- Visi testi:
```
mvn test
```
- Pa vienam testu failam:
```
mvn test -Dtest=ApiTest
```
- Pa vienam scenārijam:
```
mvn test -Dtest=ApiTest#deleteEventIncorrectEndpoint test
```
