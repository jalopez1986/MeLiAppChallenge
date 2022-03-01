# Proyecto

Meli Android App Challenge

Single activity application 100% que consume el API de Mercadolibre presentando 3 pantallas principales:

- Campo de búsqueda.
- Visualización de resultados de la búsqueda.
- Detalle de un producto.

## Estructura de carpetas
```
|-- app     --> Modulo de la app (Front Android)
|-- core    --> Modulo de los casos de uso, dominio y repositorio
|-- di      --> Modulo con la configuración de inyector de dependencias
|-- test    --> Unit tests

```
## Arquitectura
- Se implementó la arquitectura limpia (Clean Architecture) a nivel de feature
- Single activity using Navigation component
- MVVM


## Tech Stack

- Kotlin
- JetPack (Navigation, LiveData, ViewModel)
- Coroutines & Flow
- Hilt (Dependency injection)
- Retrofit
- JUnit 4 and Mockito.

## Notas al revisor
- Solo se hicieron Unit test al feature de SearchItem y ItemsList, quedó faltando el feature de ItemDetail pero la filosofia sería la misma
- Se decidió un enfoque simple para el manejo de excepciones.
- Tuve dudas en la mejor forma de implementar un scroll infinito utilizando el paging que ofrece la API, por tal razón deje una propuesta en la rama feature/infinite_scroll https://github.com/jalopez1986/MeLiAppChallenge/tree/feature/infinite_scroll para su revisón