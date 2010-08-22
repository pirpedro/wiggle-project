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
package com.db4o.db4ounit.common.cs;

import com.db4o.config.*;
import com.db4o.cs.internal.*;
import com.db4o.internal.*;
import com.db4o.io.*;

import db4ounit.*;

public class SwitchingFilesFromMultipleClientsTestCase extends StandaloneCSTestCaseBase implements TestLifeCycle {
	
	public static void main(String[] args) throws Throwable {
		new ConsoleTestRunner(SwitchingFilesFromMultipleClientsTestCase.class).run();
	}

	public static class Data {
		public int _id;

		public Data(int id) {
			this._id = id;
		}
	}

	private int _counter;
	
	protected void configure(Configuration config) {
		config.reflectWith(Platform4.reflectorForType(Data.class));
	}

	protected void runTest() {
		_counter = 0;
		ClientObjectContainer clientA = openClient();
		ClientObjectContainer clientB = openClient();
		addData(clientA);
		assertDataCount(clientA, clientB, 1, 0);
		clientA.commit();
		assertDataCount(clientA, clientB, 1, 1);

		clientA.switchToFile(SwitchingFilesFromClientUtil.FILENAME_A);
		assertDataCount(clientA, clientB, 0, 1);
		addData(clientA);
		assertDataCount(clientA, clientB, 1, 1);
		clientA.commit();
		assertDataCount(clientA, clientB, 1, 1);
		
		clientB.switchToFile(SwitchingFilesFromClientUtil.FILENAME_B);
		assertDataCount(clientA, clientB, 1, 0);
		addData(clientA);
		assertDataCount(clientA, clientB, 2, 0);
		clientA.commit();
		assertDataCount(clientA, clientB, 2, 0);
		addData(clientB);
		assertDataCount(clientA, clientB, 2, 1);

		clientA.switchToFile(SwitchingFilesFromClientUtil.FILENAME_B);
		assertDataCount(clientA, clientB, 0, 1);
		clientB.commit();
		assertDataCount(clientA, clientB, 1, 1);
		addData(clientA);
		clientA.commit();
		assertDataCount(clientA, clientB, 2, 2);
		addData(clientB);
		clientB.commit();
		assertDataCount(clientA, clientB, 3, 3);

		clientB.switchToFile(SwitchingFilesFromClientUtil.FILENAME_A);
		assertDataCount(clientA, clientB, 3, 2);

		clientA.switchToMainFile();
		assertDataCount(clientA, clientB, 1, 2);
		
		clientB.switchToMainFile();
		assertDataCount(clientA, clientB, 1, 1);

		clientA.close();
		clientB.close();
	}

	public void setUp() throws Exception {
		SwitchingFilesFromClientUtil.deleteFiles();
	}

	public void tearDown() throws Exception {
		SwitchingFilesFromClientUtil.deleteFiles();
	}

	private void assertDataCount(ClientObjectContainer clientA, ClientObjectContainer clientB, int expectedA, int expectedB) {
		assertDataCount(clientA, expectedA);
		assertDataCount(clientB, expectedB);
	}

	private void assertDataCount(ClientObjectContainer client, int expected) {
		Assert.areEqual(expected, client.query(Data.class).size());
	}
	
	private void addData(ClientObjectContainer client) {
		client.store(new Data(_counter++));
	}
}
