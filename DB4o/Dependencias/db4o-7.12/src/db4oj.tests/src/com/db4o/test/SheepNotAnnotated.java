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
package com.db4o.test;



/**
 */
@decaf.Ignore
public class SheepNotAnnotated {
	
	private String name;
	private boolean constructorCalled=false;
	
	SheepNotAnnotated parent;
	public SheepNotAnnotated(String name, SheepNotAnnotated parent) {
		this.name = name;
		this.parent = parent;
		constructorCalled=true;
	}
@Override
public String toString() {
	return name+ " "+ parent;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

	public boolean constructorCalled() {
		return constructorCalled;
	}
}
