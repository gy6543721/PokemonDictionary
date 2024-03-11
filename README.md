# PokemonDictionary

<img src="https://github.com/gy6543721/PokemonDictionary/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" height="200"/>

Pokemon Dictionary written in Kotlin and Compose.  
Now support English, Japanese, Traditional Chinese and Simplified Chinese.  

[Demo Video](https://youtu.be/VlXGaoFUh3c)  

## Introduction
Please add your own API key into `local.properties` after cloning this app.  
You can get Gemini API key [here](https://ai.google.dev/).  

```
apiKey=[your api key]
```

## Screenshots

Light | Dark
:--: | :--:
<img width="270" alt="Light001" src="images/Light001.png"> | <img width="270" alt="Dark001" src="images/Dark001.png">
<img width="270" alt="Light002" src="images/Light002.png"> | <img width="270" alt="Dark002" src="images/Dark002.png">
<img width="270" alt="Light003" src="images/Light003.png"> | <img width="270" alt="Dark003" src="images/Dark003.png">
<img width="270" alt="Light003" src="images/Light004.png"> | <img width="270" alt="Dark003" src="images/Dark004.png">

## API
- API Documentation: https://pokeapi.co/  
- Pokemon search API endpoint: **GET** https://pokeapi.co/api/v2/pokemon/{id}/  
- Pokemon detail API endpoint: **GET** https://pokeapi.co/api/v2/pokemon-species/{id}/

## Main Libraries Used
- Compose
- Coroutines
- Navigation 
- Palette
- Room
- Dagger Hilt
- Retrofit2
- OkHttp3
- Gson
- Coil
- Gemini AI
