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
package com.db4o.test.types;

import com.db4o.*;
import com.db4o.test.*;

public class SelfReference implements InterfaceHelper, RTestable
{
	public SelfReference self;
	public String name;

	public SelfReference(){
		self = this;
	}

	public SelfReference(String a_name){
		this();
		name = a_name;
	}
		
	public void compare(ObjectContainer con, Object obj, int ver){
		Compare.compare(con, set(newInstance(), ver), obj,"", null);
	}
	public boolean equals(Object obj){
		if(obj != null){
			if(obj instanceof SelfReference){
				if(name != null){
					return name.equals(((SelfReference)obj).name);
				}
			}
		}
		return false;
	}
	
	public Object newInstance(){
		return new SelfReference();	}

	
	public Object set(Object obj, int ver){
		((SelfReference)obj).set(ver);		return obj;
	}

	public void set(int ver){
		if(ver == 1){
			name = "OneONEOneONEOneONEOneONEOneONEOneONE";	
		}else{
			name = "TwoTWOTwoTWOTwoTWOTwoTWOTwoTWO";	
		}
	}

	public boolean jdk2(){
		return false;
	}
	
	public boolean ver3(){
		return false;	}
}
