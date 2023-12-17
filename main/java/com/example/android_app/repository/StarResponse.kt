package com.example.android_app.repository

data class StarResponse(
    val count: Int,
    val results: List<Starship>
)

data class Starship(
    val name: String,
    val model: String,
    val length: String,
    val pilots: List<String>
)

//{
//    "count":36,
//    "next":"https://swapi.dev/api/starships/?page=3",
//    "previous":"https://swapi.dev/api/starships/?page=1",
//    "results":[
//    {
//        "name":"Slave 1",
//        "model":"Firespray-31-class patrol and attack",
//        "manufacturer":"Kuat Systems Engineering",
//        "starship_class":"Patrol craft",
//        "pilots":[
//        "https://swapi.dev/api/people/22/"
//        ],
//        "edited":"2014-12-20T21:23:49.897000Z",
//        "url":"https://swapi.dev/api/starships/21/"
//    },
//    {
//        "name":"Imperial shuttle",
//        "model":"Lambda-class T-4a shuttle",
//        "manufacturer":"Sienar Fleet Systems",
//        "starship_class":"Armed government transport",
//        "pilots":[
//        "https://swapi.dev/api/people/1/",
//        "https://swapi.dev/api/people/13/",
//        "https://swapi.dev/api/people/14/"
//        ],
//        "edited":"2014-12-20T21:23:49.900000Z",
//        "url":"https://swapi.dev/api/starships/22/"
//    }
//    ]
//}