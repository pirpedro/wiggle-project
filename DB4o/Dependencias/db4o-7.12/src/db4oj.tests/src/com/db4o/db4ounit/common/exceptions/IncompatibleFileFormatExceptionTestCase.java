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
package com.db4o.db4ounit.common.exceptions;

import com.db4o.*;
import com.db4o.db4ounit.common.api.*;
import com.db4o.ext.*;
import com.db4o.foundation.io.*;
import com.db4o.io.*;

import db4ounit.*;
import db4ounit.extensions.*;

public class IncompatibleFileFormatExceptionTestCase extends TestWithTempFile implements Db4oTestCase {
	
	public static void main(String[] args) throws Exception {
		new ConsoleTestRunner(IncompatibleFileFormatExceptionTestCase.class).run();
	}

	public void setUp() throws Exception {
		File4.delete(tempFile());
		IoAdapter adapter = new RandomAccessFileAdapter();
		adapter = adapter.open(tempFile(), false, 0, false);
		adapter.write(new byte[] { 1, 2, 3 }, 3);
		adapter.close();
	}

	public void test() {
		Assert.expect(IncompatibleFileFormatException.class, new CodeBlock() {
			public void run() throws Throwable {
				Db4o.openFile(tempFile());
			}
		});
		File4.delete(tempFile());
		IoAdapter adapter = new RandomAccessFileAdapter();
		Assert.isFalse(adapter.exists(tempFile()));
	}

}
