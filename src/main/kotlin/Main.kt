import spark.Spark.*

fun main(args :Array<String>){
    port(5555)
    staticFileLocation("/public")

    get("/test"){ _, _ ->
        "Hello world"
    }
    webSocket("/chat/socket", ChatSocket::class.java)
    init()
}