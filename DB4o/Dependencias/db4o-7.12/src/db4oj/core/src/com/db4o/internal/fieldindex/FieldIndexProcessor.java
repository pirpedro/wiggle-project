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
package com.db4o.internal.fieldindex;

import com.db4o.foundation.*;
import com.db4o.internal.query.processor.*;

public class FieldIndexProcessor {

	private final QCandidates _candidates;

	public FieldIndexProcessor(QCandidates candidates) {
		_candidates = candidates;
	}
	
	public FieldIndexProcessorResult run() {
		IndexedNode bestIndex = selectBestIndex();
		if (null == bestIndex) {
			return FieldIndexProcessorResult.NO_INDEX_FOUND;
		}
		if (bestIndex.resultSize() > 0) {
			IndexedNode resolved = resolveFully(bestIndex);
			if (null == resolved) {
				return FieldIndexProcessorResult.NO_INDEX_FOUND;
			}
			resolved.markAsBestIndex();
			return new FieldIndexProcessorResult(resolved);
		}
		return FieldIndexProcessorResult.FOUND_INDEX_BUT_NO_MATCH;
	}

	private IndexedNode resolveFully(IndexedNode bestIndex) {
		if (null == bestIndex) {
			return null;
		}
		if (bestIndex.isResolved()) {
			return bestIndex;
		}
		return resolveFully(bestIndex.resolve());
	}
	
	public IndexedNode selectBestIndex() {		
		final Iterator4 i = collectIndexedNodes();
		if (!i.moveNext()) {
			return null;
		}
		
		IndexedNode best = (IndexedNode)i.current();
		while (i.moveNext()) {
			IndexedNode leaf = (IndexedNode)i.current();
			if (leaf.resultSize() < best.resultSize()) {
				best = leaf;
			}
		}
		return best;
	}

	public Iterator4 collectIndexedNodes() {
		return new IndexedNodeCollector(_candidates).getNodes();
	}	    
}
