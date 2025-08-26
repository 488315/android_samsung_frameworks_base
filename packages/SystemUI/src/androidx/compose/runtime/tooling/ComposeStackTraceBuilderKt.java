package androidx.compose.runtime.tooling;

import androidx.collection.MutableIntObjectMap;
import androidx.collection.MutableObjectList;
import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.SlotReader;
import androidx.compose.runtime.SlotWriter;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ComposeStackTraceBuilderKt {
    public static final int[] EmptyIntArray = new int[0];

    public static final List buildTrace(SlotWriter slotWriter, Object obj, int i, Integer num) {
        int iParent;
        MutableObjectList mutableObjectList;
        if (slotWriter.closed || slotWriter.getSize$runtime_release() == 0) {
            return EmptyList.INSTANCE;
        }
        WriterTraceBuilder writerTraceBuilder = new WriterTraceBuilder(slotWriter);
        if (num != null) {
            iParent = num.intValue();
        } else {
            iParent = slotWriter.parent;
            if (iParent < 0) {
                iParent = slotWriter.parent(i, slotWriter.groups);
            }
        }
        if (obj == null) {
            int iSlotIndex = slotWriter.currentSlot - slotWriter.slotIndex(slotWriter.groupIndexToAddress(i), slotWriter.groups);
            MutableIntObjectMap mutableIntObjectMap = slotWriter.deferredSlotWrites;
            obj = Integer.valueOf(iSlotIndex + ((mutableIntObjectMap == null || (mutableObjectList = (MutableObjectList) mutableIntObjectMap.get(i)) == null) ? 0 : mutableObjectList._size));
        }
        while (i >= 0) {
            writerTraceBuilder.processEdge(slotWriter.sourceInformationOf$runtime_release(i), obj);
            obj = slotWriter.anchor(i);
            if (iParent >= 0) {
                int i2 = iParent;
                iParent = slotWriter.parent(iParent, slotWriter.groups);
                i = i2;
            } else {
                i = iParent;
            }
        }
        return writerTraceBuilder.trace;
    }

    public static final Integer findSubcompositionContextGroup$lambda$4$scanGroup(SlotReader slotReader, CompositionContext compositionContext, int i, int i2) {
        Integer numFindSubcompositionContextGroup$lambda$4$scanGroup;
        while (true) {
            if (i >= i2) {
                return null;
            }
            int[] iArr = slotReader.groups;
            int i3 = iArr[(i * 5) + 3] + i;
            if (slotReader.hasMark(i) && slotReader.groupKey(i) == 206 && Intrinsics.areEqual(slotReader.objectKey(i, iArr), ComposerKt.reference)) {
                Object objGroupGet = slotReader.groupGet(i, 0);
                ComposerImpl.CompositionContextHolder compositionContextHolder = objGroupGet instanceof ComposerImpl.CompositionContextHolder ? (ComposerImpl.CompositionContextHolder) objGroupGet : null;
                if (compositionContextHolder != null && Intrinsics.areEqual(compositionContextHolder.ref, compositionContext)) {
                    return Integer.valueOf(i);
                }
            }
            if (slotReader.containsMark(i) && (numFindSubcompositionContextGroup$lambda$4$scanGroup = findSubcompositionContextGroup$lambda$4$scanGroup(slotReader, compositionContext, i + 1, i3)) != null) {
                return Integer.valueOf(numFindSubcompositionContextGroup$lambda$4$scanGroup.intValue());
            }
            i = i3;
        }
    }

    public static final List traceForGroup(SlotReader slotReader, int i, Object obj) {
        ReaderTraceBuilder readerTraceBuilder = new ReaderTraceBuilder(slotReader);
        int iParent = slotReader.parent(i);
        Anchor anchor = slotReader.anchor(i);
        while (i >= 0) {
            readerTraceBuilder.processEdge(slotReader.table.sourceInformationOf(i), obj);
            if (iParent >= 0) {
                Anchor anchor2 = anchor;
                anchor = slotReader.anchor(iParent);
                i = iParent;
                iParent = slotReader.parent(iParent);
                obj = anchor2;
            } else {
                i = iParent;
                obj = anchor;
            }
        }
        return readerTraceBuilder.trace;
    }
}
