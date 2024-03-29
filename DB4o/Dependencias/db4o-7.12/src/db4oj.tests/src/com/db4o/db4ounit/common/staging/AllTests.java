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
package com.db4o.db4ounit.common.staging;

import db4ounit.extensions.*;

public class AllTests extends ComposibleTestSuite {

	public static void main(String[] args) {
		new AllTests().runSolo();
    }

	protected Class[] testCases() {
		return composeTests(
					new Class[] {			
							/**
							 *  When you add a test here, make sure you create a Jira issue. 
							 */
							ActivateDepthTestCase.class,
							InterfaceQueryTestCase.class, // COR-1131
							LazyQueryDeleteTestCase.class,
							RemovedFieldDefragmentTestCase.class,
							StoredClassUnknownClassQueryTestCase.class, // COR-1542
							UnavailableEnumTestCase.class,
							UntypedFieldSortingTestCase.class,
					});
	}
	
	/**
	 * @sharpen.if !SILVERLIGHT
	 */
	@Override
	protected Class[] composeWith() {
		return new Class[] {
						ClientServerPingTestCase.class,
						DeepPrefetchingCacheConcurrencyTestCase.class, // COR-1762
						PingTestCase.class,
					};
	}
}
