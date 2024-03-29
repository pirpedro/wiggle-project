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
package com.db4o.foundation;

public class IntIterators {

	public static FixedSizeIntIterator4 forInts(final int[] array, final int count) {
	    return new IntIterator4Impl(array, count);
    }

	public static IntIterator4 forLongs(final long[] ids) {
		return new IntIterator4() {
			int _next = 0;
			int _current;
			
			public int currentInt() {
				return _current;
            }

			public Object current() {
				return _current;
            }

			public boolean moveNext() {
				if (_next < ids.length) {
					_current = (int)ids[_next];
					++_next;
					return true;
				}
				return false;
            }

			public void reset() {
	            throw new com.db4o.foundation.NotImplementedException();
            }
		};
    }
	
}
