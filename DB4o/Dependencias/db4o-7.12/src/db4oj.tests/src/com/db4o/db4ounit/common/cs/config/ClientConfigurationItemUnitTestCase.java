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
package com.db4o.db4ounit.common.cs.config;

import java.util.*;

import com.db4o.cs.*;
import com.db4o.cs.config.*;
import com.db4o.cs.internal.config.*;
import com.db4o.ext.*;

import db4ounit.*;
import db4ounit.extensions.dbmock.*;

public class ClientConfigurationItemUnitTestCase implements TestLifeCycle {

	private List<DummyConfigurationItem> _applied;
	private ClientConfigurationImpl _config;
	
	public void testPrepareApply() {
		List<DummyConfigurationItem> items = Arrays.asList(
				new DummyConfigurationItem(_applied),
				new DummyConfigurationItem(_applied)
		);
		for (DummyConfigurationItem item : items) {
			_config.addConfigurationItem(item);
			Assert.areEqual(1, item.prepareCount());
		}
		Assert.areEqual(0, _applied.size());
		_config.applyConfigurationItems(new MockClient());
		assertListsAreEqual(items, _applied);
		for (DummyConfigurationItem item : items) {
			Assert.areEqual(1, item.prepareCount());
		}
	}
	
	public void testAddTwice() {
		DummyConfigurationItem item = new DummyConfigurationItem(_applied);
		_config.addConfigurationItem(item);
		_config.addConfigurationItem(item);
		_config.applyConfigurationItems(new MockClient());
		Assert.areEqual(1, item.prepareCount());
		assertListsAreEqual(Arrays.asList(item), _applied);
	}

	private <T> void assertListsAreEqual(List<T> a, List<T> b) {
		Assert.areEqual(a.size(), b.size());
		for(int i = 0; i < a.size(); i++) {
			Assert.areEqual(a.get(i), b.get(i));
		}
	}

	public void setUp() throws Exception {
		_applied = new ArrayList<DummyConfigurationItem>();
		_config = (ClientConfigurationImpl) Db4oClientServer.newClientConfiguration();
	}

	public void tearDown() throws Exception {
	}

	private static class DummyConfigurationItem implements ClientConfigurationItem {
		private int _prepareCount = 0;
		private List<DummyConfigurationItem> _applied;
		
		public DummyConfigurationItem(List<DummyConfigurationItem> applied) {
			_applied = applied;
		}
		
		public void apply(ExtClient client) {
			_applied.add(this);
		}

		public void prepare(ClientConfiguration configuration) {
			_prepareCount++;
		}
		
		public int prepareCount() {
			return _prepareCount;
		}
	}
}
