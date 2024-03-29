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
package EDU.purdue.cs.bloat.util;

import java.util.*;

/**
 * ImmutableIterator is simply an iterator whose contents can not be changed.
 * That is, the remove() method has no effect.
 */
public class ImmutableIterator implements Iterator {
	Iterator iter;

	public ImmutableIterator(final Collection c) {
		iter = new ArrayList(c).iterator();
	}

	public Object next() {
		return iter.next();
	}

	public boolean hasNext() {
		return iter.hasNext();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
