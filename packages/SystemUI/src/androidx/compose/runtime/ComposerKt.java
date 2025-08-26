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
        int iFindLocation = findLocation(i, list);
        if (iFindLocation < 0) {
            iFindLocation = -(iFindLocation + 1);
        }
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (iFindLocation >= arrayList.size() || ((Invalidation) arrayList.get(iFindLocation)).location >= i2) {
                return;
            } else {
                arrayList.remove(iFindLocation);
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
        int slotsSize;
        int[] iArr = slotWriter.groups;
        int i = slotWriter.currentGroup;
        int iDataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.groupSize(i) + i), iArr);
        for (int iDataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.currentGroup), slotWriter.groups); iDataIndex2 < iDataIndex; iDataIndex2++) {
            Object obj = slotWriter.slots[slotWriter.dataIndexToDataAddress(iDataIndex2)];
            int iAnchorIndex = -1;
            if (obj instanceof ComposeNodeLifecycleCallback) {
                rememberEventDispatcher.recordLeaving(slotWriter.getSlotsSize() - iDataIndex2, -1, -1, (ComposeNodeLifecycleCallback) obj);
            } else if (obj instanceof RememberObserverHolder) {
                RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) obj;
                if (!(rememberObserverHolder.wrapped instanceof ReusableRememberObserver)) {
                    removeData(slotWriter, iDataIndex2, obj);
                    int slotsSize2 = slotWriter.getSlotsSize() - iDataIndex2;
                    Anchor anchor = rememberObserverHolder.after;
                    if (anchor == null || !anchor.getValid()) {
                        slotsSize = -1;
                    } else {
                        iAnchorIndex = slotWriter.anchorIndex(anchor);
                        slotsSize = slotWriter.getSlotsSize() - slotWriter.slotsEndAllIndex$runtime_release(iAnchorIndex);
                    }
                    rememberEventDispatcher.recordLeaving(slotsSize2, iAnchorIndex, slotsSize, rememberObserverHolder);
                }
            } else if (obj instanceof RecomposeScopeImpl) {
                removeData(slotWriter, iDataIndex2, obj);
                ((RecomposeScopeImpl) obj).release();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final MovableContentState extractMovableContentAtCurrent(final ControlledComposition controlledComposition, final MovableContentStateReference movableContentStateReference, SlotWriter slotWriter, Applier applier) {
        Object obj;
        int i = 1;
        SlotTable slotTable = new SlotTable();
        if (slotWriter.sourceInformationMap != null) {
            slotTable.collectSourceInformation();
        }
        boolean z = false;
        Object[] objArr = 0;
        boolean z2 = false;
        if (slotWriter.calledByMap != null) {
            slotTable.calledByMap = new MutableIntObjectMap(objArr == true ? 1 : 0, i, null);
        }
        int i2 = slotWriter.currentGroup;
        if (applier != null && slotWriter.nodeCount(i2) > 0) {
            int iParent = slotWriter.parent;
            while (iParent > 0 && !slotWriter.isNode(iParent)) {
                iParent = slotWriter.parent(iParent, slotWriter.groups);
            }
            if (iParent >= 0 && slotWriter.isNode(iParent)) {
                Object objNode = slotWriter.node(iParent);
                int i3 = iParent + 1;
                int iGroupSize = slotWriter.groupSize(iParent) + iParent;
                int iNodeCount = 0;
                while (i3 < iGroupSize) {
                    int iGroupSize2 = slotWriter.groupSize(i3) + i3;
                    if (iGroupSize2 > i2) {
                        break;
                    }
                    iNodeCount += slotWriter.isNode(i3) ? 1 : slotWriter.nodeCount(i3);
                    i3 = iGroupSize2;
                }
                int iNodeCount2 = slotWriter.isNode(i2) ? 1 : slotWriter.nodeCount(i2);
                applier.down(objNode);
                applier.remove(iNodeCount, iNodeCount2);
                applier.up();
            }
        }
        SlotWriter slotWriterOpenWriter = slotTable.openWriter();
        try {
            slotWriterOpenWriter.beginInsert();
            slotWriterOpenWriter.startGroup(126665345, movableContentStateReference.content);
            SlotWriter.markGroup$default(slotWriterOpenWriter);
            slotWriterOpenWriter.update(movableContentStateReference.parameter);
            List listMoveTo = slotWriter.moveTo(movableContentStateReference.anchor, slotWriterOpenWriter);
            slotWriterOpenWriter.skipGroup();
            slotWriterOpenWriter.endGroup();
            slotWriterOpenWriter.endInsert();
            slotWriterOpenWriter.close(true);
            MovableContentState movableContentState = new MovableContentState(slotTable);
            RecomposeScopeImpl.Companion.getClass();
            List list = listMoveTo;
            if (!list.isEmpty()) {
                int size = list.size();
                for (int i4 = 0; i4 < size; i4++) {
                    Anchor anchor = (Anchor) listMoveTo.get(i4);
                    if (slotTable.ownsAnchor(anchor)) {
                        int iAnchorIndex = slotTable.anchorIndex(anchor);
                        int iAccess$slotAnchor = SlotTableKt.access$slotAnchor(iAnchorIndex, slotTable.groups);
                        int i5 = iAnchorIndex + 1;
                        if ((i5 < slotTable.groupsSize ? slotTable.groups[(i5 * 5) + 4] : slotTable.slots.length) - iAccess$slotAnchor > 0) {
                            obj = slotTable.slots[iAccess$slotAnchor];
                        } else {
                            Composer.Companion.getClass();
                            obj = Composer.Companion.Empty;
                        }
                        if (obj instanceof RecomposeScopeImpl) {
                            RecomposeScopeOwner recomposeScopeOwner = new RecomposeScopeOwner() { // from class: androidx.compose.runtime.ComposerKt$extractMovableContentAtCurrent$movableContentRecomposeScopeOwner$1
                                @Override // androidx.compose.runtime.RecomposeScopeOwner
                                public final InvalidationResult invalidate(RecomposeScopeImpl recomposeScopeImpl, Object obj2) {
                                    InvalidationResult invalidationResultInvalidate;
                                    ControlledComposition controlledComposition2 = controlledComposition;
                                    RecomposeScopeOwner recomposeScopeOwner2 = controlledComposition2 instanceof RecomposeScopeOwner ? (RecomposeScopeOwner) controlledComposition2 : null;
                                    if (recomposeScopeOwner2 == null || (invalidationResultInvalidate = recomposeScopeOwner2.invalidate(recomposeScopeImpl, obj2)) == null) {
                                        invalidationResultInvalidate = InvalidationResult.IGNORED;
                                    }
                                    if (invalidationResultInvalidate != InvalidationResult.IGNORED) {
                                        return invalidationResultInvalidate;
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
                            SlotWriter slotWriterOpenWriter2 = slotTable.openWriter();
                            try {
                                RecomposeScopeImpl.Companion.getClass();
                                RecomposeScopeImpl.Companion.adoptAnchoredScopes$runtime_release(slotWriterOpenWriter2, listMoveTo, recomposeScopeOwner);
                                Unit unit = Unit.INSTANCE;
                                slotWriterOpenWriter2.close(true);
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
            int iCompare = Intrinsics.compare(((Invalidation) arrayList.get(i3)).location, i);
            if (iCompare < 0) {
                i2 = i3 + 1;
            } else {
                if (iCompare <= 0) {
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
        int slotsSize;
        int[] iArr = slotWriter.groups;
        int i = slotWriter.currentGroup;
        int iDataIndex = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.groupSize(i) + i), iArr);
        for (int iDataIndex2 = slotWriter.dataIndex(slotWriter.groupIndexToAddress(slotWriter.currentGroup), slotWriter.groups); iDataIndex2 < iDataIndex; iDataIndex2++) {
            Object obj = slotWriter.slots[slotWriter.dataIndexToDataAddress(iDataIndex2)];
            int iAnchorIndex = -1;
            if (obj instanceof ComposeNodeLifecycleCallback) {
                int slotsSize2 = slotWriter.getSlotsSize() - iDataIndex2;
                Object obj2 = (ComposeNodeLifecycleCallback) obj;
                MutableScatterSet mutableScatterSetMutableScatterSetOf = rememberEventDispatcher.releasing;
                if (mutableScatterSetMutableScatterSetOf == null) {
                    mutableScatterSetMutableScatterSetOf = ScatterSetKt.mutableScatterSetOf();
                    rememberEventDispatcher.releasing = mutableScatterSetMutableScatterSetOf;
                }
                mutableScatterSetMutableScatterSetOf.plusAssign(obj2);
                rememberEventDispatcher.recordLeaving(slotsSize2, -1, -1, obj2);
            }
            if (obj instanceof RememberObserverHolder) {
                int slotsSize3 = slotWriter.getSlotsSize() - iDataIndex2;
                RememberObserverHolder rememberObserverHolder = (RememberObserverHolder) obj;
                Anchor anchor = rememberObserverHolder.after;
                if (anchor == null || !anchor.getValid()) {
                    slotsSize = -1;
                } else {
                    iAnchorIndex = slotWriter.anchorIndex(anchor);
                    slotsSize = slotWriter.getSlotsSize() - slotWriter.slotsEndAllIndex$runtime_release(iAnchorIndex);
                }
                rememberEventDispatcher.recordLeaving(slotsSize3, iAnchorIndex, slotsSize, rememberObserverHolder);
            }
            if (obj instanceof RecomposeScopeImpl) {
                ((RecomposeScopeImpl) obj).release();
            }
        }
        slotWriter.removeGroup();
    }

    public static final void removeData(SlotWriter slotWriter, int i, Object obj) {
        int iDataIndexToDataAddress = slotWriter.dataIndexToDataAddress(i);
        Object[] objArr = slotWriter.slots;
        Object obj2 = objArr[iDataIndexToDataAddress];
        Composer.Companion.getClass();
        objArr[iDataIndexToDataAddress] = Composer.Companion.Empty;
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
