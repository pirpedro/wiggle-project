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
package com.db4o.ta.instrumentation.test.data;

public class ToBeInstrumented {
	
	protected static int _xx;
	protected int _x;
	protected transient int _xxx;

	public void foo() {
		int y = _x;
	}

	protected void bar() {
		int y = _x;
	}

	void baz() {
		int y = _x;
	}
	
	private void boo() {
		int y = _x;
	}
	
	public static void fooStatic() {
		int yy = _xx;
	}

	public void fooTransient() {
		int yy = _xxx;
	}

	public boolean accessNotToBeInstrumented(NotToBeInstrumented other) {
		return _x == other._x;
	}
}
