# TicketChef

App Android nativa (Kotlin + Jetpack Compose) que fotografía tickets de la compra, detecta los productos con OCR on-device y sugiere recetas españolas con lo que ya tienes en casa.

## Como funciona

1. **Escaneo**: la pantalla de camara (CameraX) captura una foto del ticket.
2. **OCR**: Google ML Kit Text Recognition (100% en el dispositivo, sin conexion ni API keys) extrae el texto.
3. **Parseo de ingredientes**: `IngredientParser` limpia cada linea del ticket y la compara contra `IngredientDictionary`, un diccionario de palabras habituales en tickets de supermercado espanol mapeadas a ~90 ingredientes canonicos.
4. **Ingredientes editables**: el usuario puede quitar lo que sobre o anadir a mano lo que falte.
5. **Motor de recetas**: `RecipeMatcher` puntua las 76 recetas de `assets/recipes.json` (cocina espanola tradicional) segun cuantos ingredientes coinciden y las ordena de mayor a menor coincidencia.
6. **Detalle**: ingredientes que ya tienes marcados con check, ingredientes que faltan, y pasos de preparacion.

Sin cuentas, sin backend, sin persistencia entre sesiones: cada uso es independiente.

## Stack

- Kotlin + Jetpack Compose (Material 3), modo claro/oscuro automatico
- CameraX para la captura
- Google ML Kit (`text-recognition`) para el OCR, on-device
- Sin dependencias de red: todo el motor de recetas es local

## Estructura

```
app/src/main/java/com/ticketchef/app/
  data/         Recipe, IngredientDictionary, RecipeRepository
  ocr/          TicketTextRecognizer (ML Kit), IngredientParser
  recipes/      RecipeMatcher (scoring de recetas)
  ui/theme/     Colores, tipografia, Material3 theme
  ui/components/TicketMascot (logo animado), RecipeCard
  ui/screens/   Splash, Scan, Ingredients, Results, RecipeDetail
  navigation/   NavHost con las 5 pantallas
  viewmodel/    AppViewModel (estado de la app)
app/src/main/assets/recipes.json   Base de datos de recetas (editable a mano)
```

## Ampliar el recetario

Anade objetos al array de `app/src/main/assets/recipes.json` con este esquema:

```json
{
  "id": "id-unico",
  "name": "Nombre de la receta",
  "category": "comida | cena | postre",
  "minutes": 30,
  "difficulty": "facil | media | dificil",
  "emoji": "🍲",
  "ingredients": ["patata", "cebolla", "..."],
  "steps": ["Paso 1...", "Paso 2..."]
}
```

Los ids de `ingredients` deben existir como clave en `IngredientDictionary.canonicalToKeywords` (`app/src/main/java/com/ticketchef/app/data/IngredientDictionary.kt`); si no, esa receta nunca podra detectarse automaticamente desde un ticket.

## Build

Este proyecto se genero fuera de Android Studio, en un entorno sin acceso a `dl.google.com` / `maven.google.com` (el repositorio de Google esta bloqueado por la politica de red de este entorno), asi que **no se ha podido compilar aqui**. Para compilarlo:

1. Abre la carpeta en Android Studio (Hedgehog o mas reciente).
2. Deja que sincronice Gradle (descargara el Android Gradle Plugin, Compose y CameraX/ML Kit).
3. Ejecuta en un dispositivo o emulador con Android 8.0 (API 26) o superior.

O por linea de comandos, con el SDK de Android instalado y `ANDROID_HOME` configurado:

```
./gradlew assembleDebug
```
