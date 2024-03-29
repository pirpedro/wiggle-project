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

import java.io.*;

import com.db4o.*;


public class LookingAtFileFormat {
    
    public String str;
    
    public LookingAtFileFormat(){
        
    }
    
    public LookingAtFileFormat(String str){
        this.str = str;
    }

    public static void main(String[] args) {
        final String fileName = "lff.db4o";
		new File(fileName).delete();
        ObjectContainer con = Db4o.openFile(fileName);
        LookingAtFileFormat laff = new LookingAtFileFormat();
        for (int i = 0; i < 10000; i++) {
            laff.str = "WWWWWWWWWW" + i;
            con.store(laff);
            con.commit();
        }
        con.close();
    }
}
