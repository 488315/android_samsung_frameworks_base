package androidx.compose.runtime;

import android.os.Trace;
import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.internal.RememberEventDispatcher;
import com.android.systemui.compose.EnableCommand$enableCompositionTracing$1;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ComposerKt {
    public static final ComposerKt$$ExternalSyntheticLambda0 InvalidationLocationAscending;
    public static EnableCommand$enableCompositionTracing$1 compositionTracer;
    public static final OpaqueKey providerMaps;
    public static final OpaqueKey reference;
    public static final OpaqueKey invocation = new OpaqueKey("provider");
    public static final OpaqueKey provider = new OpaqueKey("provider");
    public static final OpaqueKey compositionLocalMap = new OpaqueKey("compositionLocalMap");

    static {
        new OpaqueKey("providerValues");
        providerMaps = new OpaqueKey("providers");
        reference = new OpaqueKey("reference");
        InvalidationLocationAscending = new ComposerKt$$ExternalSyntheticLambda0();
    }

    public static final void access$removeRange(List list, int i, int i2) {
        int findLocation = findLocation(i, list);
        if (findLocation < 0) {
            findLocation = -(findLocation + 1);
        }
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (findLocation >= arrayList.size() || ((Invalidation) arrayList.get(findLocation)).location >= i2) {
                return;
            } else {
                arrayList.remove(findLocation);
            }
        }
    }

    public static final void collectNodesFrom$lambda$10$collectFromGroup(SlotReader slotReader, List list, int i) {
        if (slotReader.isNode(i)) {
            ((ArrayList) list).add(slotReader.node(i));
            return;
        }
        int[] iArr = slotReader.groups;
        int i2 = iArr[(i * 5) + 3] + i;
        for (int i3 = i + 1; i3 < i2; i3 += iArr[(i3 * 5) + 3]) {
            collectNodesFrom$lambda$10$collectFromGroup(slotReader, list, i3);
        }
    }

    public static final void composeImmediateRuntimeError(String str) {
        throw new ComposeRuntimeError(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Compose Runtime internal error. Unexpected or incorrect use of the Compose internal runtime API (", str, "). Please report to Google or use https://goo.gle/compose-feedback"));
    }

    public static final Void composeRuntimeError(String str) {
        throw new ComposeRuntimeError(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Compose Runtime internal error. Unexpected or incorrect use of the Compose internal runtime API (", str, "). Please report to Google or use https://goo.gle/compose-feedback"));
    }

    public static final void deactivateCurrentGroup(SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher) {
        int i;
        int[] iArr = slotWriter.groups;
        int i2 = slotWriter.currentGroup;
        int dataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.groupSize(i2) + i2), iArr);
        for (int dataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.currentGroup), slotWriter.groups); dataIndex2 < dataIndex; dataIndex2++) {
            Object obj = slotWriter.slots[slotWriter.dataIndexToDataAddress(dataIndex2)];
            int i3 = -1;
            if (obj instanceof ComposeNodeLifecycleCallback) {
                rememberEventDispatcher.recordLeaving(slotWriter.getSlotsSize() - dataIndex2, -1, -1, (ComposeNodeLifecycleCallback) obj);
            } else if (obj instanceof RememberObserverHolder) {
                RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) obj;
                if (!(rememberObserverHolder.wrapped instanceof ReusableRememberObserver)) {
                    removeData(slotWriter, dataIndex2, obj);
                    int slotsSize = slotWriter.getSlotsSize() - dataIndex2;
                    Anchor anchor = rememberObserverHolder.after;
                    if (anchor == null || !anchor.getValid()) {
                        i = -1;
                    } else {
                        i3 = slotWriter.anchorIndex(anchor);
                        i = slotWriter.getSlotsSize() - slotWriter.slotsEndAllIndex$runtime_release(i3);
                    }
                    rememberEventDispatcher.recordLeaving(slotsSize, i3, i, rememberObserverHolder);
                }
            } else if (obj instanceof RecomposeScopeImpl) {
                removeData(slotWriter, dataIndex2, obj);
                ((RecomposeScopeImpl) obj).release();
            }
        }
    }

    public static final MovableContentState extractMovableContentAtCurrent(final ControlledComposition controlledComposition, final MovableContentStateReference movableContentStateReference, SlotWriter slotWriter, Applier applier) {
        Object obj;
        int i = 1;
        SlotTable slotTable = new SlotTable();
        if (slotWriter.sourceInformationMap != null) {
            slotTable.collectSourceInformation();
        }
        boolean z = false;
        byte b = 0;
        boolean z2 = false;
        if (slotWriter.calledByMap != null) {
            slotTable.calledByMap = new MutableIntObjectMap(b == true ? 1 : 0, i, null);
        }
        int i2 = slotWriter.currentGroup;
        if (applier != null && slotWriter.nodeCount(i2) > 0) {
            int i3 = slotWriter.parent;
            while (i3 > 0 && !slotWriter.isNode(i3)) {
                i3 = slotWriter.parent(i3, slotWriter.groups);
            }
            if (i3 >= 0 && slotWriter.isNode(i3)) {
                Object node = slotWriter.node(i3);
                int i4 = i3 + 1;
                int groupSize = slotWriter.groupSize(i3) + i3;
                int i5 = 0;
                while (i4 < groupSize) {
                    int groupSize2 = slotWriter.groupSize(i4) + i4;
                    if (groupSize2 > i2) {
                        break;
                    }
                    i5 += slotWriter.isNode(i4) ? 1 : slotWriter.nodeCount(i4);
                    i4 = groupSize2;
                }
                int nodeCount = slotWriter.isNode(i2) ? 1 : slotWriter.nodeCount(i2);
                applier.down(node);
                applier.remove(i5, nodeCount);
                applier.up();
            }
        }
        SlotWriter openWriter = slotTable.openWriter();
        try {
            openWriter.beginInsert();
            openWriter.startGroup(126665345, movableContentStateReference.content);
            SlotWriter.markGroup$default(openWriter);
            openWriter.update(movableContentStateReference.parameter);
            List moveTo = slotWriter.moveTo(movableContentStateReference.anchor, openWriter);
            openWriter.skipGroup();
            openWriter.endGroup();
            openWriter.endInsert();
            openWriter.close(true);
            MovableContentState movableContentState = new MovableContentState(slotTable);
            RecomposeScopeImpl.Companion.getClass();
            List list = moveTo;
            if (!list.isEmpty()) {
                int size = list.size();
                for (int i6 = 0; i6 < size; i6++) {
                    Anchor anchor = (Anchor) moveTo.get(i6);
                    if (slotTable.ownsAnchor(anchor)) {
                        int anchorIndex = slotTable.anchorIndex(anchor);
                        int access$slotAnchor = SlotTableKt.access$slotAnchor(anchorIndex, slotTable.groups);
                        int i7 = anchorIndex + 1;
                        if ((i7 < slotTable.groupsSize ? slotTable.groups[(i7 * 5) + 4] : slotTable.slots.length) - access$slotAnchor > 0) {
                            obj = slotTable.slots[access$slotAnchor];
                        } else {
                            Composer.Companion.getClass();
                            obj = Composer.Companion.Empty;
                        }
                        if (obj instanceof RecomposeScopeImpl) {
                            RecomposeScopeOwner recomposeScopeOwner = new RecomposeScopeOwner() { // from class: androidx.compose.runtime.ComposerKt$extractMovableContentAtCurrent$movableContentRecomposeScopeOwner$1
                                @Override // androidx.compose.runtime.RecomposeScopeOwner
                                public final InvalidationResult invalidate(RecomposeScopeImpl recomposeScopeImpl, Object obj2) {
                                    InvalidationResult invalidationResult;
                                    ControlledComposition controlledComposition2 = ControlledComposition.this;
                                    RecomposeScopeOwner recomposeScopeOwner2 = controlledComposition2 instanceof RecomposeScopeOwner ? (RecomposeScopeOwner) controlledComposition2 : null;
                                    if (recomposeScopeOwner2 == null || (invalidationResult = recomposeScopeOwner2.invalidate(recomposeScopeImpl, obj2)) == null) {
                                        invalidationResult = InvalidationResult.IGNORED;
                                    }
                                    if (invalidationResult != InvalidationResult.IGNORED) {
                                        return invalidationResult;
                                    }
                                    MovableContentStateReference movableContentStateReference2 = movableContentStateReference;
                                    movableContentStateReference2.invalidations = CollectionsKt___CollectionsKt.plus(movableContentStateReference2.invalidations, new Pair(recomposeScopeImpl, obj2));
                                    return InvalidationResult.SCHEDULED;
                                }

                                @Override // androidx.compose.runtime.RecomposeScopeOwner
                                public final void recomposeScopeReleased() {
                                }

                                @Override // androidx.compose.runtime.RecomposeScopeOwner
                                public final void recordReadOf(Object obj2) {
                                }
                            };
                            SlotWriter openWriter2 = slotTable.openWriter();
                            try {
                                RecomposeScopeImpl.Companion.getClass();
                                RecomposeScopeImpl.Companion.adoptAnchoredScopes$runtime_release(openWriter2, moveTo, recomposeScopeOwner);
                                Unit unit = Unit.INSTANCE;
                                openWriter2.close(true);
                                return movableContentState;
                            } finally {
                            }
                        }
                    }
                }
            }
            return movableContentState;
        } finally {
        }
    }

    public static final int findLocation(int i, List list) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size() - 1;
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            int compare = Intrinsics.compare(((Invalidation) arrayList.get(i3)).location, i);
            if (compare < 0) {
                i2 = i3 + 1;
            } else {
                if (compare <= 0) {
                    return i3;
                }
                size = i3 - 1;
            }
        }
        return -(i2 + 1);
    }

    public static final boolean isTraceInProgress() {
        return compositionTracer != null && Trace.isEnabled();
    }

    public static final void removeCurrentGroup(SlotWriter slotWriter, RememberEventDispatcher rememberEventDispatcher) {
        int i;
        int[] iArr = slotWriter.groups;
        int i2 = slotWriter.currentGroup;
        int dataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.groupSize(i2) + i2), iArr);
        for (int dataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.currentGroup), slotWriter.groups); dataIndex2 < dataIndex; dataIndex2++) {
            Object obj = slotWriter.slots[slotWriter.dataIndexToDataAddress(dataIndex2)];
            int i3 = -1;
            if (obj instanceof ComposeNodeLifecycleCallback) {
                int slotsSize = slotWriter.getSlotsSize() - dataIndex2;
                Object obj2 = (ComposeNodeLifecycleCallback) obj;
                MutableScatterSet mutableScatterSet = rememberEventDispatcher.releasing;
                if (mutableScatterSet == null) {
                    mutableScatterSet = ScatterSetKt.mutableScatterSetOf();
                    rememberEventDispatcher.releasing = mutableScatterSet;
                }
                mutableScatterSet.plusAssign(obj2);
                rememberEventDispatcher.recordLeaving(slotsSize, -1, -1, obj2);
            }
            if (obj instanceof RememberObserverHolder) {
                int slotsSize2 = slotWriter.getSlotsSize() - dataIndex2;
                RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) obj;
                Anchor anchor = rememberObserverHolder.after;
                if (anchor == null || !anchor.getValid()) {
                    i = -1;
                } else {
                    i3 = slotWriter.anchorIndex(anchor);
                    i = slotWriter.getSlotsSize() - slotWriter.slotsEndAllIndex$runtime_release(i3);
                }
                rememberEventDispatcher.recordLeaving(slotsSize2, i3, i, rememberObserverHolder);
            }
            if (obj instanceof RecomposeScopeImpl) {
                ((RecomposeScopeImpl) obj).release();
            }
        }
        slotWriter.removeGroup();
    }

    public static final void removeData(SlotWriter slotWriter, int i, Object obj) {
        int dataIndexToDataAddress = slotWriter.dataIndexToDataAddress(i);
        Object[] objArr = slotWriter.slots;
        Object obj2 = objArr[dataIndexToDataAddress];
        Composer.Companion.getClass();
        objArr[dataIndexToDataAddress] = Composer.Companion.Empty;
        if (obj == obj2) {
            return;
        }
        composeImmediateRuntimeError("Slot table is out of sync (expected " + obj + ", got " + obj2 + ')');
    }

    public static final void traceEventEnd() {
        if (compositionTracer != null) {
            Trace.traceEnd(4096L);
        }
    }

    public static final void traceEventStart(String str) {
        if (compositionTracer != null) {
            Trace.traceBegin(4096L, str);
        }
    }
}
