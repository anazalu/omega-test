# Omega-test

## Mērķis:

nodrošināt, ka šī platforma ir:
- droša 
- uzticama 
- patīkama lietošanai 

## Uzdevumi:

1. Veikt manuālo aplikācijas testu un aprakstīt testēšanas rezultātus.

2. Identificēt svarīgākos testēšanas scenārijus.

3. Uzrakstīt automatizētos testus 2-3 svarīgākajiem scenārijiem.
Tehnoloģijas/valodas/rīkus var izvēlēties pēc saviem ieskatiem.

4. Veikt API validācijas testus tiem endpointiem, kuri ir aprakstīti swagerī: 

- Vismaz 5 testpiemēri POST pieprasījumam

- 2 testpiemēri DELETE pieprasījumam + paskaidrojums, kā tika verificēts, ka dati tika izdzēsti

### Papilduzdevums:

2-3 ieteikumi lietotāja pieredzes uzlabojumiem aplikācijā.

## Testu palaišana:

(Nepieciešams Maven)
- Visi testi:
```
mvn test
```
- Atsevišķi testu faili:
```
mvn test -Dtest=ApiTest
```
