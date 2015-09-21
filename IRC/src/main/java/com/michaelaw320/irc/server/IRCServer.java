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

import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import com.michaelaw320.irc.service.*;
import org.apache.thrift.server.TThreadPoolServer;

public class IRCServer {

 public static void StartsimpleServer(IRCService.Processor<IRCServiceHandler> processor) {
  try {
   TServerTransport serverTransport = new TServerSocket(9090);
   //TServer server = new TSimpleServer(
   //  new Args(serverTransport).processor(processor));

   // Use this for a multithreaded server
    TServer server = new TThreadPoolServer(new
    TThreadPoolServer.Args(serverTransport).processor(processor));

   System.out.println("Starting the simple server...");
   server.serve();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
 
 public static void main(String[] args) {
  StartsimpleServer(new IRCService.Processor<IRCServiceHandler>(new IRCServiceHandler()));
 }

}

