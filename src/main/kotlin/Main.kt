import spark.Spark.*

fun main(args :Array<String>){
    port(5555)
    webSocket("/chat/socket", ChatSocket::class.java)
    staticFileLocation("/public")

    get("/test"){ _, _ ->
        "Hello world"
    }

    init()
}