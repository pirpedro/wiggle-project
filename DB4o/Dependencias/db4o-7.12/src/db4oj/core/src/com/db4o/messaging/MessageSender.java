/* This file is part of the db4o object database http://www.db4o.com

Copyright (C) 2004 - 2009  Versant Corporation http://www.versant.com

db4o is free software; you can redistribute it and/or modify it under
the terms of version 3 of the GNU General Public License as published
by the Free Software Foundation.

db4o is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License along
with this program.  If not, see http://www.gnu.org/licenses/. */
package com.db4o.messaging;

/**
 * message sender for client/server messaging.
 * <br><br>db4o allows using the client/server TCP connection to send
 * messages from the client to the server. Any object that can be
 * stored to a db4o database file may be used as a message.<br><br>
 * For an example see Reference documentation: <br>
 * http://developer.db4o.com/Resources/view.aspx/Reference/Client-Server/Messaging<br>
 * http://developer.db4o.com/Resources/view.aspx/Reference/Client-Server/Remote_Code_Execution<br><br>
 * <b>See Also:</b><br>
 * {@link com.db4o.config.ClientServerConfiguration#getMessageSender()},<br>
 * {@link MessageRecipient},<br>
 * {@link com.db4o.config.ClientServerConfiguration#setMessageRecipient(MessageRecipient)}
 */
public interface MessageSender {
	
	/**
	 * sends a message to the server.
	 * @param obj the message parameter, any object may be used.
	 */
    public void send(Object obj);
}
