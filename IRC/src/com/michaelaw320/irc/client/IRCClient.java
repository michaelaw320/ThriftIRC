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

import static com.michaelaw320.irc.client.ClientStates.JOINED_CHANNELS;
import com.michaelaw320.irc.service.IRCService;
import com.michaelaw320.irc.utils.IRCMessage;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IRCClient {

  private static volatile boolean updater = true;
  
 public static void main(String[] args) {
  Scanner userInput;
  String input;
  Thread updaterThread;
  boolean exit = false;
  try {
   TTransport transport;

   transport = new TSocket("localhost", 9090);
   transport.open();

   TProtocol protocol = new TBinaryProtocol(transport);
   IRCService.Client client = new IRCService.Client(protocol);

   System.out.println("Welcome to IRC");

   //New thread to run periodic check
   //check whether important thing has been set else do not run
   
   updaterThread = new Thread(new Runnable() {
           @Override
           public void run() {
               while(updater) {
                    try {
                        if(!ClientStates.NICKNAME.equals("")){
                            for(ChannelState currentChannel : JOINED_CHANNELS) {
                                List<String> recv;
                                try {
                                    recv = client.retrievePeriodic(currentChannel.ChannelName, currentChannel.LastMsgTimestamp);
                                    for(String currentString : recv) {
                                        IRCMessage currentMessage = new IRCMessage();
                                        currentMessage.parseString(currentString);
                                        //build string to output to stdout
                                        String build;
                                        build = "["+currentChannel.ChannelName+"]"+ "("+currentMessage.getNick()+")" + currentMessage.getText();
                                        if (currentChannel.LastMsgTimestamp < currentMessage.getTimestamp()) {
                                            currentChannel.LastMsgTimestamp = currentMessage.getTimestamp();
                                            System.out.println(build);
                                        }
                                    }

                                } catch (TException ex) {
                                    System.err.println("Exception in updater");
                                    updater = false;
                                }
                            }
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
               }
           }
        });
   updaterThread.start();
   //Run the main loop here
   userInput = new Scanner(System.in);
   while (!exit) {
       input = userInput.nextLine();
       if(input.equalsIgnoreCase("/EXIT")) {
           exit = true;
       } else if (input.matches("(?i)/NICK(.*)?")) {
           String nickname = input.substring(5).trim();
           if (nickname == "") {
               //randomize name
           } else {
               ClientStates.NICKNAME = nickname;
               System.out.println("Hi "+nickname);
           }
       } else if (input.matches("(?i)/JOIN .*") && ClientStates.NICKNAME != "") {
           String roomName = input.substring(6);
           if (ClientStates.isAlreadyJoin(roomName)) {
               System.out.println("Anda sudah join ke room "+roomName);
           } else {
               if (client.join(roomName)) {
                   System.out.println("Anda membuat room baru bernama "+roomName);
               } else {
                   System.out.println("Berhasil bergabung ke dalam room "+roomName);
               }
               ClientStates.JOINED_CHANNELS.add(new ChannelState(roomName));
           }
       } else if (input.matches("(?i)/LEAVE .*") && ClientStates.NICKNAME != "") {
           String roomName = input.substring(7);
           if (ClientStates.removeChannel(roomName)) {
               System.out.println("Anda berhasil leave room "+roomName);
           } else {
               System.out.println("Anda tidak pernah bergabung di room "+roomName);
           }
       } else if (input.matches("(?i)@.*? .+") && ClientStates.NICKNAME != "") {
           String chatRoom;
           String message;
           int timestamp = (int) (System.currentTimeMillis() / 1000);
           chatRoom = input.substring(1,input.indexOf(" "));
           message = input.substring(input.indexOf(" ")+1);
           if (!client.sendMessage(chatRoom, ClientStates.NICKNAME, message, timestamp))
               System.out.println("Ruangan chat yang anda masukkan tidak ada");
           else
           {
               ClientStates.getChannelState(chatRoom).LastMsgTimestamp = timestamp;
               String build;
               build = "["+chatRoom+"]"+ "("+ClientStates.NICKNAME+")" + message;
               System.out.println(build);
           }
       } else if (ClientStates.NICKNAME != "") {
           String message;
           String chatRoom;
           int timestamp = (int) (System.currentTimeMillis() / 1000);
           message = input;
           for(ChannelState currentChannel : JOINED_CHANNELS) {
               chatRoom = currentChannel.ChannelName;
               if (!client.sendMessage(chatRoom, ClientStates.NICKNAME, message, timestamp)) {
                    //System.out.println("Ruangan chat yang anda masukkan tidak ada");
               } else
                {
                    ClientStates.getChannelState(chatRoom).LastMsgTimestamp = timestamp;
                    String build;
                    build = "["+chatRoom+"]"+ "("+ClientStates.NICKNAME+")" + message;
                    System.out.println(build);
                }
           }
       }
               
   }
   updater = false; //shuts thread
   transport.close();
    } catch (TTransportException e) {
     e.printStackTrace();
    } catch (TException x) {
     x.printStackTrace();
    }
 }

}

