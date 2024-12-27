package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.ProgramIdentifier;
import android.hardware.broadcastradio.V2_0.ProgramInfo;
import android.hardware.broadcastradio.V2_0.ProgramListChunk;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.hardware.radio.UniqueProgramIdentifier;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProgramInfoCache {
    public boolean mComplete;
    public final ProgramList.Filter mFilter;
    public final ArrayMap mProgramInfoMap;

    public ProgramInfoCache(ProgramList.Filter filter) {
        this.mProgramInfoMap = new ArrayMap();
        this.mComplete = true;
        this.mFilter = filter;
    }

    public ProgramInfoCache(ProgramList.Filter filter, boolean z, RadioManager.ProgramInfo... programInfoArr) {
        this.mProgramInfoMap = new ArrayMap();
        this.mFilter = filter;
        this.mComplete = z;
        for (RadioManager.ProgramInfo programInfo : programInfoArr) {
            putInfo(programInfo);
        }
    }

    public static List buildChunks(boolean z, boolean z2, Collection collection, int i, Collection collection2, int i2) {
        Iterator it;
        int i3;
        if (z) {
            collection2 = null;
        }
        int max = Math.max(z ? 1 : 0, roundUpFraction(((ArraySet) collection).size(), i));
        if (collection2 != null) {
            max = Math.max(max, roundUpFraction(((ArraySet) collection2).size(), i2));
        }
        if (max == 0) {
            return new ArrayList();
        }
        ArraySet arraySet = (ArraySet) collection;
        int roundUpFraction = roundUpFraction(arraySet.size(), max);
        Iterator it2 = arraySet.iterator();
        if (collection2 != null) {
            ArraySet arraySet2 = (ArraySet) collection2;
            i3 = roundUpFraction(arraySet2.size(), max);
            it = arraySet2.iterator();
        } else {
            it = null;
            i3 = 0;
        }
        ArrayList arrayList = new ArrayList(max);
        int i4 = 0;
        while (i4 < max) {
            ArraySet arraySet3 = new ArraySet();
            ArraySet arraySet4 = new ArraySet();
            if (it2 != null) {
                for (int i5 = 0; i5 < roundUpFraction && it2.hasNext(); i5++) {
                    arraySet3.add((RadioManager.ProgramInfo) it2.next());
                }
            }
            if (it != null) {
                for (int i6 = 0; i6 < i3 && it.hasNext(); i6++) {
                    arraySet4.add((UniqueProgramIdentifier) it.next());
                }
            }
            boolean z3 = true;
            boolean z4 = z && i4 == 0;
            if (!z2 || i4 != max - 1) {
                z3 = false;
            }
            arrayList.add(new ProgramList.Chunk(z4, z3, arraySet3, arraySet4));
            i4++;
        }
        return arrayList;
    }

    public static int roundUpFraction(int i, int i2) {
        return (i / i2) + (i % i2 > 0 ? 1 : 0);
    }

    public List filterAndApplyChunkInternal(ProgramListChunk programListChunk, int i, int i2) {
        if (programListChunk.purge) {
            this.mProgramInfoMap.clear();
        }
        ArraySet arraySet = new ArraySet();
        Iterator it = programListChunk.modified.iterator();
        while (it.hasNext()) {
            RadioManager.ProgramInfo programInfoFromHal = Convert.programInfoFromHal((ProgramInfo) it.next());
            if (passesFilter(programInfoFromHal.getSelector().getPrimaryId()) && shouldIncludeInModified(programInfoFromHal)) {
                putInfo(programInfoFromHal);
                arraySet.add(programInfoFromHal);
            }
        }
        ArraySet arraySet2 = new ArraySet();
        Iterator it2 = programListChunk.removed.iterator();
        while (it2.hasNext()) {
            ProgramSelector.Identifier programIdentifierFromHal = Convert.programIdentifierFromHal((ProgramIdentifier) it2.next());
            if (programIdentifierFromHal != null && this.mProgramInfoMap.containsKey(programIdentifierFromHal)) {
                arraySet2.addAll(((ArrayMap) this.mProgramInfoMap.get(programIdentifierFromHal)).keySet());
                this.mProgramInfoMap.remove(programIdentifierFromHal);
            }
        }
        if (arraySet.isEmpty() && arraySet2.isEmpty() && this.mComplete == programListChunk.complete && !programListChunk.purge) {
            return null;
        }
        boolean z = programListChunk.complete;
        this.mComplete = z;
        return buildChunks(programListChunk.purge, z, arraySet, i, arraySet2, i2);
    }

    public List filterAndUpdateFromInternal(ProgramInfoCache programInfoCache, boolean z, int i, int i2) {
        if (z) {
            this.mProgramInfoMap.clear();
        }
        if (this.mProgramInfoMap.isEmpty()) {
            z = true;
        }
        boolean z2 = z;
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        for (int i3 = 0; i3 < this.mProgramInfoMap.size(); i3++) {
            arraySet2.addAll(((ArrayMap) this.mProgramInfoMap.valueAt(i3)).keySet());
        }
        for (int i4 = 0; i4 < programInfoCache.mProgramInfoMap.size(); i4++) {
            if (passesFilter((ProgramSelector.Identifier) programInfoCache.mProgramInfoMap.keyAt(i4))) {
                ArrayMap arrayMap = (ArrayMap) programInfoCache.mProgramInfoMap.valueAt(i4);
                for (int i5 = 0; i5 < arrayMap.size(); i5++) {
                    arraySet2.remove(arrayMap.keyAt(i5));
                    RadioManager.ProgramInfo programInfo = (RadioManager.ProgramInfo) arrayMap.valueAt(i5);
                    if (shouldIncludeInModified(programInfo)) {
                        putInfo(programInfo);
                        arraySet.add(programInfo);
                    }
                }
            }
        }
        for (int i6 = 0; i6 < arraySet2.size(); i6++) {
            UniqueProgramIdentifier uniqueProgramIdentifier = (UniqueProgramIdentifier) arraySet2.valueAt(i6);
            ProgramSelector.Identifier primaryId = uniqueProgramIdentifier.getPrimaryId();
            if (this.mProgramInfoMap.containsKey(primaryId)) {
                ((ArrayMap) this.mProgramInfoMap.get(primaryId)).remove(uniqueProgramIdentifier);
                if (((ArrayMap) this.mProgramInfoMap.get(primaryId)).isEmpty()) {
                    this.mProgramInfoMap.remove(primaryId);
                }
            }
        }
        boolean z3 = programInfoCache.mComplete;
        this.mComplete = z3;
        return buildChunks(z2, z3, arraySet, i, arraySet2, i2);
    }

    public final boolean passesFilter(ProgramSelector.Identifier identifier) {
        ProgramList.Filter filter = this.mFilter;
        if (filter == null) {
            return true;
        }
        if (!filter.getIdentifierTypes().isEmpty() && !this.mFilter.getIdentifierTypes().contains(Integer.valueOf(identifier.getType()))) {
            return false;
        }
        if (this.mFilter.getIdentifiers().isEmpty() || this.mFilter.getIdentifiers().contains(identifier)) {
            return this.mFilter.areCategoriesIncluded() || !identifier.isCategoryType();
        }
        return false;
    }

    public final void putInfo(RadioManager.ProgramInfo programInfo) {
        ProgramSelector.Identifier primaryId = programInfo.getSelector().getPrimaryId();
        if (!this.mProgramInfoMap.containsKey(primaryId)) {
            this.mProgramInfoMap.put(primaryId, new ArrayMap());
        }
        ((ArrayMap) this.mProgramInfoMap.get(primaryId)).put(new UniqueProgramIdentifier(programInfo.getSelector()), programInfo);
    }

    public final boolean shouldIncludeInModified(RadioManager.ProgramInfo programInfo) {
        RadioManager.ProgramInfo programInfo2;
        ProgramSelector.Identifier primaryId = programInfo.getSelector().getPrimaryId();
        if (this.mProgramInfoMap.containsKey(primaryId)) {
            programInfo2 = (RadioManager.ProgramInfo) ((ArrayMap) this.mProgramInfoMap.get(primaryId)).get(new UniqueProgramIdentifier(programInfo.getSelector()));
        } else {
            programInfo2 = null;
        }
        if (programInfo2 == null) {
            return true;
        }
        ProgramList.Filter filter = this.mFilter;
        if (filter == null || !filter.areModificationsExcluded()) {
            return !programInfo2.equals(programInfo);
        }
        return false;
    }

    public List toProgramInfoList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mProgramInfoMap.size(); i++) {
            arrayList.addAll(((ArrayMap) this.mProgramInfoMap.valueAt(i)).values());
        }
        return arrayList;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ProgramInfoCache(mComplete = ");
        sb.append(this.mComplete);
        sb.append(", mFilter = ");
        sb.append(this.mFilter);
        sb.append(", mProgramInfoMap = [");
        for (int i = 0; i < this.mProgramInfoMap.size(); i++) {
            ArrayMap arrayMap = (ArrayMap) this.mProgramInfoMap.valueAt(i);
            for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                sb.append(", ");
                sb.append(arrayMap.valueAt(i2));
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
