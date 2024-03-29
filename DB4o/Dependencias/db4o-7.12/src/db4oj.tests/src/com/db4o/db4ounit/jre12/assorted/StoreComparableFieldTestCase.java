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
package com.db4o.db4ounit.jre12.assorted;

import com.db4o.*;
import com.db4o.query.*;

import db4ounit.*;
import db4ounit.extensions.*;


/**
 */
@decaf.Ignore(decaf.Platform.JDK11)
public class StoreComparableFieldTestCase extends AbstractDb4oTestCase{
    
    public static class Item{
        
        public Comparable _name;

        public Item(String name_) {
            _name = name_;
        }
        
    }
    
    protected void store() throws Exception {
        store(new Item("one"));
        store(new Item("two"));
    }
    
    public void testStoreStringInComparable(){
        Query query = newQuery(Item.class);
        query.descend("_name").constrain("one");
        ObjectSet objectSet = query.execute();
        Assert.areEqual(1, objectSet.size());
        Item item = (Item) objectSet.next();
        Assert.areEqual("one", item._name);
    }

}
