package androidx.compose.runtime;

import androidx.collection.MutableIntObjectMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SlotTable implements Iterable<Object>, KMappedMarker {
    public MutableIntObjectMap calledByMap;
    public int groupsSize;
    public int readers;
    public int slotsSize;
    public HashMap sourceInformationMap;
    public int version;
    public boolean writer;
    public int[] groups = new int[0];
    public Object[] slots = new Object[0];
    public final Object lock = new Object();
    public ArrayList anchors = new ArrayList();

    public final int anchorIndex(Anchor anchor) {
        if (this.writer) {
            ComposerKt.composeImmediateRuntimeError("Use active SlotWriter to determine anchor location instead");
        }
        if (!anchor.getValid()) {
            PreconditionsKt.throwIllegalArgumentException("Anchor refers to a group that was removed");
        }
        return anchor.location;
    }

    public final void collectSourceInformation() {
        this.sourceInformationMap = new HashMap();
    }

    @Override // java.lang.Iterable
    public final Iterator<Object> iterator() {
        return new GroupIterator(this, 0, this.groupsSize);
    }

    public final SlotReader openReader() {
        if (this.writer) {
            throw new IllegalStateException("Cannot read while a writer is pending");
        }
        this.readers++;
        return new SlotReader(this);
    }

    public final SlotWriter openWriter() {
        if (this.writer) {
            ComposerKt.composeImmediateRuntimeError("Cannot start a writer when another writer is pending");
        }
        if (this.readers > 0) {
            ComposerKt.composeImmediateRuntimeError("Cannot start a writer when a reader is pending");
        }
        this.writer = true;
        this.version++;
        return new SlotWriter(this);
    }

    public final boolean ownsAnchor(Anchor anchor) {
        int search;
        return anchor.getValid() && (search = SlotTableKt.search(this.anchors, anchor.location, this.groupsSize)) >= 0 && Intrinsics.areEqual(this.anchors.get(search), anchor);
    }

    public final GroupSourceInformation sourceInformationOf(int i) {
        int i2;
        ArrayList arrayList;
        int search;
        HashMap hashMap = this.sourceInformationMap;
        if (hashMap != null) {
            if (this.writer) {
                ComposerKt.composeImmediateRuntimeError("use active SlotWriter to crate an anchor for location instead");
            }
            Anchor anchor = (i < 0 || i >= (i2 = this.groupsSize) || (search = SlotTableKt.search((arrayList = this.anchors), i, i2)) < 0) ? null : (Anchor) arrayList.get(search);
            if (anchor != null) {
                return (GroupSourceInformation) hashMap.get(anchor);
            }
        }
        return null;
    }
}
