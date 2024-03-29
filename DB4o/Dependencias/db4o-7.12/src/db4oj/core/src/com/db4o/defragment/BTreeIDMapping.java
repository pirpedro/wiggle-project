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
package com.db4o.defragment;

import java.io.*;

import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.btree.*;
import com.db4o.internal.mapping.*;

/**
 * BTree mapping for IDs during a defragmentation run.
 * 
 * @see Defragment
 */
public class BTreeIDMapping extends AbstractContextIDMapping {

	private String _fileName;

	private LocalObjectContainer _mappingDb;

	private BTree _idTree;

	private MappedIDPair _cache = new MappedIDPair(0, 0);
	
	private BTreeSpec _treeSpec=null;
	
	private int _commitFrequency=0; // <=0 : never commit
	private int _insertCount=0;
	
	/**
	 * Will maintain the ID mapping as a BTree in the file with the given path.
	 * If a file exists in this location, it will be DELETED.
	 * 
	 * Node size and cache height of the tree will be the default values used by
	 * the BTree implementation. The tree will never commit.
	 * 
	 * @param fileName The location where the BTree file should be created.
	 */
	public BTreeIDMapping(String fileName) {
		this(fileName,null,0);
	}

	/**
	 * Will maintain the ID mapping as a BTree in the file with the given path.
	 * If a file exists in this location, it will be DELETED.
	 * 
	 * @param fileName The location where the BTree file should be created.
	 * @param nodeSize The size of a BTree node
	 * @param commitFrequency The number of inserts after which a commit should be issued (<=0: never commit)
	 */
	public BTreeIDMapping(String fileName,int nodeSize,int commitFrequency) {
		this(fileName,new BTreeSpec(nodeSize),commitFrequency);
	}

	private BTreeIDMapping(String fileName,BTreeSpec treeSpec,int commitFrequency) {
		_fileName = fileName;
		_treeSpec=treeSpec;
		_commitFrequency=commitFrequency;
	}

	public int mappedID(int oldID, boolean lenient) {
		if (_cache.orig() == oldID) {
			return _cache.mapped();
		}
		int classID = mappedClassID(oldID);
		if (classID != 0) {
			return classID;
		}
		BTreeRange range = _idTree.search(trans(), new MappedIDPair(oldID, 0));
		Iterator4 pointers = range.pointers();
		if (pointers.moveNext()) {
			BTreePointer pointer = (BTreePointer) pointers.current();
			_cache = (MappedIDPair) pointer.key();
			return _cache.mapped();
		}
		if (lenient) {
			return mapLenient(oldID, range);
		}
		return 0;
	}

	private int mapLenient(int oldID, BTreeRange range) {
		range = range.smaller();
		BTreePointer pointer = range.lastPointer();
		if (pointer == null) {
			return 0;
		}
		MappedIDPair mappedIDs = (MappedIDPair) pointer.key();
		return mappedIDs.mapped() + (oldID - mappedIDs.orig());
	}

	protected void mapNonClassIDs(int origID, int mappedID) {
		_cache = new MappedIDPair(origID, mappedID);
		_idTree.add(trans(), _cache);
		if(_commitFrequency>0) {
			_insertCount++;
			if(_commitFrequency==_insertCount) {
				_idTree.commit(trans());
				_insertCount=0;
			}
		}
	}

	public void open() throws IOException {
		_mappingDb = DefragmentServicesImpl.freshTempFile(_fileName,1);
		Indexable4 handler = new MappedIDPairHandler();
		_idTree = (_treeSpec==null ? new BTree(trans(), 0, handler) : new BTree(trans(), 0, handler, _treeSpec.nodeSize()));
	}

	public void close() {
		_mappingDb.close();
	}

	private Transaction trans() {
		return _mappingDb.systemTransaction();
	}
	
	private static class BTreeSpec {
		private int _nodeSize;
		
		public BTreeSpec(int nodeSize) {
			_nodeSize = nodeSize;
		}
		
		public int nodeSize() {
			return _nodeSize;
		}
	}
}