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
package com.db4o.db4ounit.jre11.migration;

import db4ounit.*;

public class DoubleMigrationTestCase extends MigrationTestCaseBase {
	
	public static class Item extends MigrationItem {
		public Double value;
		
		public Item() {
		}
		
		public Item(String name_, Double value_) {
			super(name_);
			value = value_;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value_) {
			value = (Double) value_;
		}
	}
	
	protected MigrationItem newItem(String name, Object value) {
		return new Item(name, (Double)value);
	}
	
	protected String getDatabaseFileName() {
		return "doubles.db4o";
	}
	
	protected Object getMinValue() {
		return new Double(Double.MIN_VALUE);
	}

	protected Object getMaxValue() {
		return new Double(Double.MAX_VALUE-1);
	}

	protected Object getOrdinaryValue() {
		return new Double(41.9999);
	}
	
	protected Object getUpdateValue() {
		return new Double(Double.NaN);
	}
	
	public static void main(String[] args) {
		// reference db4o 5.2 and uncomment the line below
		// if you ever need to regenerate the file
//		new DoubleMigrationTestCase().generateFile();
		new ConsoleTestRunner(DoubleMigrationTestCase.class).run();
	}
}
