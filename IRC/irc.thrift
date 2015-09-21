namespace java com.michaelaw320.irc.service  // defines the namespace 

typedef i32 int  
typedef i32 long

service IRCService {  
        list<string> retrievePeriodic(1:string channelName, 2:long timestamp),
        bool sendMessage(1:string channelName,2:string nick, 3:string theMessage, 4:long timestamp),
}
