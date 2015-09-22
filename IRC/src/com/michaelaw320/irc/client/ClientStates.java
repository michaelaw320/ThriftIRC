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
package com.michaelaw320.irc.client;

import java.util.ArrayList;

/**
 *
 * @author Michael
 */
public class ClientStates {
    public static String NICKNAME = "";
    public static ArrayList<ChannelState> JOINED_CHANNELS = new ArrayList<>();
    
    public static boolean isAlreadyJoin(String channelName) {
        boolean joined = false;
        for(ChannelState cur : JOINED_CHANNELS) {
            if (cur.ChannelName.equals(channelName)) {
                joined = true;
                break;
            } 
        }
        return joined;
    }
    
    public static boolean removeChannel(String channelName) {
        boolean removed = false;
        for(ChannelState cur : JOINED_CHANNELS) {
            if (cur.ChannelName.equals(channelName)) {
                JOINED_CHANNELS.remove(cur);
                removed = true;
                break;
            } 
        }
        return removed;
    }
    
    public static ChannelState getChannelState(String channelName) {
        for(ChannelState cur : JOINED_CHANNELS) {
            if (cur.ChannelName.equals(channelName)) {
                return cur;
            } 
        }
        return new ChannelState("NX");
    }
}

class ChannelState {
    public String ChannelName;
    public int LastMsgTimestamp;
    
    public ChannelState(String name) {
        ChannelName = name;
        LastMsgTimestamp = (int) (System.currentTimeMillis() / 1000);
    }
}