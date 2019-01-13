# SimpleChatServer  
Sparkjavaフレームワークを利用した単純なチャットアプリケーションの  
サーバーサイドプログラムです。  

## ロジックについて  
接続を受けるとSession（クライアントの情報）のインスタンスをスレッドセーフな配列（キャッシュ）に格納します  
メッセージを受信すると配列に格納してあるすべてのクライアントに対して受信したメッセージを送信します。  
以上です。
 
## クライアントプログラム  
Android向けに[SimpleChatApplication](https://github.com/Kinoshita0623/SimpleChatApplication/tree/master)のようなAndroidアプリケーションを作成しました。  
Web用のクライアントはindexページなのでドメイン名でそのままアクセスできると思います。
例（localhost:5555/)
