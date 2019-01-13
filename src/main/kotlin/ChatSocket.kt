import com.google.gson.Gson
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import java.util.*
import kotlin.collections.HashSet

@WebSocket
class ChatSocket{
    companion object{
        //private val usersCache = HashMap<Session, String/*ランダムなUUIDが入る*/>()
        private val usersCache = Collections.synchronizedSet(HashSet<Session>())
    }

    private val gson = Gson()

    @OnWebSocketConnect
    fun connected(session: Session){
        println("接続中のデバイス数 ${usersCache.size}")
        synchronized(usersCache){
            addUsersCache(session)
        }
    }

    @OnWebSocketClose
    fun closed(session: Session, status: Int, reason: String){
        println("接続中のデバイス数 ${usersCache.size}")
        synchronized(usersCache){
            usersCache.remove(session)
        }
    }

    @OnWebSocketMessage
    fun message(session: Session, message: String){
        println("接続中のデバイス数 ${usersCache.size}")
        val obj = gson.fromJson(message, MessageBean::class.java)

        responseMessage(responseData = obj)

    }
    private fun addUsersCache(session: Session){
        synchronized(usersCache){
            usersCache.remove(session)
            usersCache.add(session)
        }
    }

    private fun responseMessage(responseData: MessageBean, cacheList: Set<Session> = usersCache){
        synchronized(usersCache){
            cacheList.forEach{
                if(it.isOpen){
                    it.remote.sendString(gson.toJson(responseData))

                }
            }
        }

    }
}