# ThriftIRC
Very simple implementation of IRC like program with Apache Thrift

Building Instruction:
	- Open the project with NetBeans IDE
	- Choose the project main file before building (client or server)
	- Build the program

Installation Instruction:
	- N/A
	
How to use program:
	- Open up cmd/terminal and type in
		: java -jar IRCServer.jar
		: java -jar IRCClient.jar
	- Make sure Java Runtime Environment is installed before using this program

Commands:
	- /NICK <nickname>
		: set the nickname, this is mandatory
	- /JOIN <channelname>
		: Join / Create (if not exist) the channel
	- /LEAVE <channelname>
		: Leave the channel
	- /EXIT
		: Exit the program
	- <type anything>
		: broadcasts to all channel joined
	- @<channelname> <message>
		: sends message to particular channel only
	
All functions has been tested under normal condition and works perfectly.

Steps to do the test:
	- /NICK
		+ /nick				< Will give alert, input not accepted
		+ /NICK				< Will give alert, input not accepted
		+ /nick asd			< works
		+ /NICK ASD ASD		< works
	- /JOIN
		+ /JOIN				< Will not work, input not accepted
		+ /join				< Will not work, input not accepted
		+ /join asdasd		< Will work
	- /LEAVE
		+ /LEAVE XYZ (not joined before)	< Will not work, giving alert
		+ /LEAVE ASD (joined before)		< Will work
		+ /LEAVE ASD (after leaving)		< same result as not joined
	- /EXIT
	- <type anything>
		+ run 2 clients or more then join the same and different room, message should appear on other clients
	- @<channelname> <message>
		+ run 2 clients or more, join into one room then chat as usual, chat should be visible only to the room
		