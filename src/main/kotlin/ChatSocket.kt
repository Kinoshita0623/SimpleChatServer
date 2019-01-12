import com.google.gson.Gson
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import java.util.*

class ChatSocket{
    companion object{
        private val usersCache = HashMap<Session, String/*ランダムなUUIDが入る*/>()
    }

    private val gson = Gson()

    @OnWebSocketConnect
    fun connected(session: Session){
        synchronized(usersCache){
            addUsersCache(session)
        }
    }

    @OnWebSocketClose
    fun closed(session: Session, status: Int, reason: String){
        synchronized(usersCache){
            usersCache.remove(session)
        }
    }

    @OnWebSocketMessage
    fun message(session: Session, message: String){
        val obj = gson.fromJson(message, ReceiveMessageBean::class.java)
        val randomId = usersCache[session]
        val responseData = if(randomId == null){
            val uuid = addUsersCache(session)
            SendMessageBean(uuid, obj.message, name = obj.name)

        }else{
            SendMessageBean(randomId, obj.message, name = obj.name)
        }
        responseMessage(responseData = responseData)

    }
    private fun addUsersCache(session: Session): String/*生成したUUIDを返す*/{
        val uuid = UUID.randomUUID().toString()
        synchronized(usersCache){
            usersCache[session] = uuid
        }
        return uuid
    }

    private fun responseMessage(responseData: SendMessageBean, sessionMap: HashMap<Session, String> = usersCache){
        sessionMap.forEach { session, _ ->
            session.remote.sendString(gson.toJson(responseData))
        }
    }
}