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
package com.db4o.test.legacy.soda.deepOR;

import java.util.*;

import com.db4o.query.*;
import com.db4o.test.legacy.soda.*;


public class STOrContains implements STClass {

	public static transient SodaTest st;
	
	SodaTest pm;
	
	String name;
	ArrayList al1;
	ArrayList al2;
	
	public STOrContains() {
	}
	
	public STOrContains(String name, Object[] l1, Object[] l2) {
		this.name = name;
		al1 = new ArrayList();
		if(l1 != null){
			for (int i = 0; i < l1.length; i++) {
				al1.add(l1[i]);
			}
		}
		al2 = new ArrayList();
		if(l2 != null){
			for (int i = 0; i < l2.length; i++) {
				al2.add(l2[i]);
			}
		}
		
	}

	public Object[] store() {
		return new Object[] {
			new STOrContains("one", new Object[] {
				new Named("Marcus"),
				new Named("8"),
				new Named("Woohaa")
			}, new Object[] {
				new Named("one"),
				new Named("two"),
				new Named("three")
			}),
			new STOrContains("two", new Object[] {
				new Named("one"),
				new Named("two"),
				new Named("three")
			}, new Object[] {
				new Named("Marcus"),
				new Named("8"),
				new Named("Woohaa")
			}),
			new STOrContains("three", new Object[] {
				new Named("is"),
				new Named("this"),
				new Named("true")
			}, new Object[] {
				new Named("no"),
				new Named("ho"),
				new Named("wo")
			})
			};
	}

	public void testNoneFound() {
		Query q = st.query();
		q.constrain(STOrContains.class);
		Query name1 = q.descend("al1").descend("name");
		Query name2 = q.descend("al2").descend("name");
		name1.constrain("hugolo").or(name2.constrain("hugoli"));
		Object[] r = store();
		st.expectNone(q);
	}
	
	public void testOneFound() {
		Query q = st.query();
		q.constrain(STOrContains.class);
		Query name1 = q.descend("al1").descend("name");
		Query name2 = q.descend("al2").descend("name");
		name1.constrain("Woohaa").or(name2.constrain("Woohaa"));
		Object[] r = store();
		st.expect(q, new Object[] { r[0], r[1] });
	}
	
	public void testBothFound() {
		Query q = st.query();
		q.constrain(STOrContains.class);
		Query name1 = q.descend("al1").descend("name");
		Query name2 = q.descend("al2").descend("name");
		name1.constrain("Marcus").or(name2.constrain("three"));
		Object[] r = store();
		st.expect(q, new Object[] { r[0]});
	}
	
	
	public void testMoreOr1(){
		Query q = st.query();
		q.constrain(STOrContains.class);
		Query name1 = q.descend("al1").descend("name");
		Query name2 = q.descend("al2").descend("name");
		name1.constrain("Marcus")
		.or(name2.constrain("three"))
		.or(q.descend("name").constrain("three"));
		Object[] r = store();
		st.expect(q, new Object[] { r[0], r[2]});
	}
	
	public void testMoreOr2(){
		Query q = st.query();
		q.constrain(STOrContains.class);
		Query name1 = q.descend("al1").descend("name");
		Query name2 = q.descend("al2").descend("name");
		name1.constrain("Marcus")
		.or(name2.constrain("wo"))
		.or(q.descend("name").constrain("three"));
		Object[] r = store();
		st.expect(q, new Object[] { r[0], r[2]});
	}
	
	public static class Named{
		String name;
		
		public Named(){
		}
		
		public Named(String name){
			this.name = name;
		}
	}

}


