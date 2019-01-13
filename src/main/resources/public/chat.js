
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/socket")
//var chatLog = ""

var nameBox = document.getElementById("name")
var messageBox = document.getElementById("message")
var sendButton = document.getElementById("send")
var messageView = document.getElementById("chatMessages")

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
    var msgObj = JSON.parse(msg.data)
    var view = "<p>name:"+ msgObj.name + ", message:"+ msgObj.message + "</p>"
    messageView.insertAdjacentHTML("beforebegin", view)


}
webSocket.onclose = function(){
    alert("通信が途絶えましたリロードしてください。")
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/socket")
}

webSocket.onerror = function(){
    alert("エラーが発生しました")
}
