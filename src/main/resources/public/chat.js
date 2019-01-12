
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/socket")

var nameBox = document.getElementById("name")
var messageBox = document.getElementById("message")
var sendButton = document.getElementById("send")

sendButton.addEventListener("click", function(){
    console.log("接続されました")
    var obj = {
        "name":nameBox.value,
        "message": messageBox.value
    }
    var json = JSON.stringify(obj)

    console.log(json)
    webSocket.send(json)

})
webSocket.onmessage = function(msg){
    console.log(msg)
}
webSocket.onclose = function(){
    alert("通信が途絶えましたリロードしてください。")
}

