# Actividad 4

Grupo CDI_4

> Victor Málvarez Filgueira
> Martín Molina Álvarez

## Respuestas

### 1
-

### 2
-

### 3
-

### 4
El método `notifyAll` notifica a todos los hilos que están esperando (han hecho `wait`) al objeto de dicho método. El método `notify` notifica únicamente a uno de los hilos que están esperando (no se sabe cuál, uno de ellos). En nuestro caso resulta conveniente utilizar el método `notify` ya que en cada instante solo un hilo va a estar a estar esperando que se despierte el propio objeto.
