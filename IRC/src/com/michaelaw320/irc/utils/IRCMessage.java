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
package com.michaelaw320.irc.utils;


/**
 *
 * @author Michael
 */
public class IRCMessage {
    private String nick;
    private String text;
    private int timestamp;
    
    public IRCMessage() {
        
    }
    
    public IRCMessage(String nick, String text, int timestamp) {
        this.nick = nick;
        this.text = text;
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return nick + '\n' + text + "\n" + Integer.toString(timestamp);
    }
    
    public void parseString(String str) {
        String[] split = str.split("\n");
        nick = split[0];
        text = split[1];
        timestamp = Integer.parseInt(split[2]);
    }
    
    public String getNick() {
        return nick;
    }
    
    public String getText() {
        return text;
    }
    
    public int getTimestamp() {
        return timestamp;
    }
    
    public void setNick(String n) {
        nick = n;
    }
    
    public void setText(String tex) {
        text = tex;
    }
    
    public void setTimestamp(long time) {
        timestamp = (int) time;
    }
}
