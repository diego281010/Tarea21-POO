# Generar un archivo `.exe` para el proyecto

Esta guía explica cómo convertir esto en un ejecutable `.exe` para Windows utilizando `jpackage`.

---

## Integrantes

- Diego Almeida 
- Gregory Araujo
- Alejandro Aguirre 
- Jordy Cajas 
- Nicolay Barreno 
- Sebastian Caiza 
- Matias Borja
- Emely Bone

---

## Requisitos

Antes de comenzar, verifica que tienes instalado:

- Java JDK 21 o superior
- Maven
- JavaFX
- WiX Toolset (para generar el instalador `.exe`)
- Variables de entorno configuradas (`JAVA_HOME` y `PATH`)

Puedes comprobar las versiones con:

```bash
java --version
mvn -version
jpackage --version
```

---

## 1. Compilar el proyecto

Desde la raíz del proyecto ejecuta:

```bash
mvn clean package
```

Esto generará el archivo `.jar` dentro de:

```
target/
```

---

La otra opción es en intelliJ generar el archivo jar mediante la opción artifacts que se encuentra
en el apartado de project structure, copiar las dependencias y usar la clase que contenga el método
main (en nuestro caso Launcher)

Luego en el apartado de build generar un artifact y construir (build) el archivo jar
## 2. Instalar WiX Toolset

Para que `jpackage` pueda generar un instalador `.exe`, instala WiX.

### WiX v5

Descargar desde:

https://wixtoolset.org/releases/

Una vez instalado, verifica que funciona:

```bash
wix --version
```

Si Windows no reconoce el comando, agrega la carpeta de instalación de WiX al `PATH`.

---

## 3. Generar el ejecutable

Ejecuta el siguiente comando desde la carpeta del proyecto.

```bash
jpackage ^
  --type exe ^
  --input target ^
  --main-jar NombreDelJar.jar ^
  --main-class paquete.Main ^
  --name NombreAplicacion ^
  --vendor "Nombre del Autor" ^
  --app-version 1.0.0 ^
  --dest instalador
```

### Ejemplo

```bash
jpackage --input . --name proyecto --main-jar proyecto.jar --main-class com.example.proyecto.Launcher --type exe
```

---

## 4. Resultado

Al finalizar se creará un archivo parecido a:

```
proyecto.exe
```

Este es un instalador de Windows.

---

# Si no tienes WiX

También puedes crear una imagen de la aplicación:

```bash
jpackage --input . --name proyecto --main-jar proyecto.jar --main-class com.example.proyecto.Launcher --type app-imag```
```

Esto genera una carpeta con todos los archivos necesarios para ejecutar la aplicación, pero **no crea un instalador**.

---

# Distribución

El instalador `.exe` ya incluye:

- El runtime de Java
- JavaFX
- Tu aplicación

Por lo tanto, el usuario **no necesita instalar Java**.

---

# Notas

- El `.exe` generado por `jpackage` es un **instalador**, no un único archivo que contiene toda la aplicación.
- Durante la instalación, Windows copiará la aplicación a `Program Files` (o la carpeta elegida) y creará accesos directos si así se configura.
- Si se desea un único ejecutable sin instalador, se pueden utilizar herramientas como **Launch4j**, aunque en aplicaciones JavaFX modernas se recomienda `jpackage`.
