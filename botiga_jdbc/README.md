# Botiga JDBC - Documentación

## Descripción

Este proyecto es una aplicación de consola en Java que simula una tienda (botiga) utilizando JDBC para conectarse a una base de datos H2 embebida. Permite listar productos y crear órdenes de compra con transacciones.

## Arquitectura

- **Modelo**: Clases `Client`, `Comanda`, `LiniaComanda`, `Producte`
- **DAO**: `ProducteDAO`, `ComandaTransaccioDAO` para acceso a datos
- **Util**: `Connexio` para conexión a la base de datos
- **App**: Clase principal con menú de consola

## Base de Datos

Utiliza H2 database embebida con las siguientes tablas:

- `clients`: id, nom
- `productes`: id, nom, preu, estoc
- `comandes`: id, client_id, data, total
- `linies_comanda`: comanda_id, producte_id, quantitat, preu_unitari

Datos de ejemplo incluidos.

## Funcionalidades

### 1. Listar Productes

- Muestra todos los productos disponibles con ID, nombre, precio y stock.

### 2. Crear Comanda (Transacción B)

- Solicita ID del cliente
- Pide número de líneas de la orden
- Para cada línea:
  - Solicita ID del producto (valida existencia)
  - Solicita cantidad
- Calcula total y crea la orden en transacción
- Si se fuerza error de notificación, hace rollback parcial

### 0. Salir

- Termina la aplicación

## Transacciones

La creación de órdenes usa transacciones con savepoints:

- Decrementa stock de productos
- Inserta líneas de orden
- Actualiza total de la orden
- Si hay error en notificación, hace rollback al savepoint y actualiza total con 0

## Requisitos

- Java 21
- H2 database (incluido en lib/)

## Ejecución

```bash
java -cp ".;lib/h2-2.2.224.jar" App
```

O usar `run.bat` en Windows.

## Configuración

- Base de datos: `jdbc:h2:~/BotigaCRUD`
- Usuario: sa
- Password: (vacío)

La base de datos se crea automáticamente si no existe.
