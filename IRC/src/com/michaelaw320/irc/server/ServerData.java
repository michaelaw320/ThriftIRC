/*
 * Copyright (C) 2015 Michael
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.michaelaw320.irc.server;

import com.michaelaw320.irc.utils.IRCMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael
 */
public class ServerData {
    public static List<Channel> CHANNELS = new ArrayList<>();
    public static boolean isChannelExist(String channelName) {
        boolean exist = false;
        for(Channel currentChannel : CHANNELS) {
            if (currentChannel.Name.equals(channelName)) {
                exist = true;
                break;
            }
        }
        return exist;
    }
    public static Channel getChannel(String channelName) {
        for(Channel currentChannel : CHANNELS) {
            if (currentChannel.Name.equals(channelName)) {
                return currentChannel;
            }
        }
        return new Channel("NX");
    }
}

class Channel {
    public String Name;
    public List<IRCMessage> Messages;
    
    public Channel(String chanName) {
        Name = chanName;
        Messages = new ArrayList<>();
    }
}
