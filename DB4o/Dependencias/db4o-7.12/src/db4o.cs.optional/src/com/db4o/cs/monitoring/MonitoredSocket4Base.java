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
package com.db4o.cs.monitoring;

import java.io.*;

import com.db4o.cs.foundation.*;

/**
 * @exclude
 */
@decaf.Ignore
abstract class MonitoredSocket4Base extends Socket4Decorator {
	public MonitoredSocket4Base(Socket4 socket) {
		super(socket);
	}

	public void write(byte[] bytes, int offset, int count) throws IOException {
		super.write(bytes, offset, count);
		bean().notifyWrite(count);
	}
	
	@Override
	public int read(byte[] buffer, int offset, int count) throws IOException {
		int bytesReceived = super.read(buffer, offset, count);
		bean().notifyRead(bytesReceived);
		
		return bytesReceived;
	}
	
	protected abstract Networking bean();
}