package androidx.compose.runtime;

import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterMap;
import androidx.collection.ObjectList;
import androidx.collection.ScatterMapKt;
import com.android.systemui.compose.EnableCommand$enableCompositionTracing$1;
import java.util.Comparator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MovableContentState {
    public final SlotTable slotTable;

    public MovableContentState(SlotTable slotTable) {
        this.slotTable = slotTable;
    }

    public static final void extractNestedStates$lambda$3$closeToGroupContaining(SlotWriter slotWriter, int i) {
        while (slotWriter.parent >= 0 && slotWriter.currentGroupEnd <= i) {
            slotWriter.skipToGroupEnd();
            slotWriter.endGroup();
        }
    }

    public final MutableScatterMap extractNestedStates$runtime_release(Applier applier, ObjectList objectList) {
        SlotTable slotTable;
        int i;
        Object[] objArr = objectList.content;
        int i2 = objectList._size;
        int i3 = 0;
        while (true) {
            slotTable = this.slotTable;
            if (i3 >= i2) {
                break;
            }
            if (slotTable.ownsAnchor(((MovableContentStateReference) objArr[i3]).anchor)) {
                i3++;
            } else {
                MutableObjectList mutableObjectList = new MutableObjectList(0, 1, null);
                Object[] objArr2 = objectList.content;
                int i4 = objectList._size;
                for (int i5 = 0; i5 < i4; i5++) {
                    Object obj = objArr2[i5];
                    if (slotTable.ownsAnchor(((MovableContentStateReference) obj).anchor)) {
                        mutableObjectList.add(obj);
                    }
                }
                objectList = mutableObjectList;
            }
        }
        final Function1 function1 = new Function1() { // from class: androidx.compose.runtime.MovableContentState$extractNestedStates$referencesToExtract$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                return Integer.valueOf(MovableContentState.this.slotTable.anchorIndex(((MovableContentStateReference) obj2).anchor));
            }
        };
        if (objectList._size > 1) {
            Comparable comparable = (Comparable) function1.mo779invoke(objectList.get(0));
            int i6 = objectList._size;
            int i7 = 1;
            while (true) {
                if (i7 >= i6) {
                    break;
                }
                Comparable comparable2 = (Comparable) function1.mo779invoke(objectList.get(i7));
                if (comparable.compareTo(comparable2) > 0) {
                    MutableObjectList mutableObjectList2 = new MutableObjectList(objectList._size);
                    Object[] objArr3 = objectList.content;
                    int i8 = objectList._size;
                    for (int i9 = 0; i9 < i8; i9++) {
                        mutableObjectList2.add(objArr3[i9]);
                    }
                    MutableObjectList.ObjectListMutableList objectListMutableList = mutableObjectList2.list;
                    if (objectListMutableList == null) {
                        objectListMutableList = new MutableObjectList.ObjectListMutableList(mutableObjectList2);
                        mutableObjectList2.list = objectListMutableList;
                    }
                    if (objectListMutableList.objectList._size > 1) {
                        CollectionsKt__MutableCollectionsJVMKt.sortWith(objectListMutableList, new Comparator() { // from class: androidx.compose.runtime.collection.ExtensionsKt$sortBy$$inlined$sortBy$1
                            @Override // java.util.Comparator
                            public final int compare(Object obj2, Object obj3) {
                                Function1 function12 = Function1.this;
                                return ComparisonsKt__ComparisonsKt.compareValues((Comparable) function12.mo779invoke(obj2), (Comparable) function12.mo779invoke(obj3));
                            }
                        });
                    }
                    objectList = mutableObjectList2;
                } else {
                    i7++;
                    comparable = comparable2;
                }
            }
        }
        if (objectList.isEmpty()) {
            return ScatterMapKt.EmptyScatterMap;
        }
        MutableScatterMap mutableScatterMapOf = ScatterMapKt.mutableScatterMapOf();
        SlotWriter openWriter = slotTable.openWriter();
        try {
            Object[] objArr4 = objectList.content;
            int i10 = objectList._size;
            for (int i11 = 0; i11 < i10; i11++) {
                MovableContentStateReference movableContentStateReference = (MovableContentStateReference) objArr4[i11];
                int anchorIndex = openWriter.anchorIndex(movableContentStateReference.anchor);
                int parent = openWriter.parent(anchorIndex, openWriter.groups);
                extractNestedStates$lambda$3$closeToGroupContaining(openWriter, parent);
                extractNestedStates$lambda$3$closeToGroupContaining(openWriter, parent);
                while (true) {
                    i = openWriter.currentGroup;
                    if (i == parent || i == openWriter.currentGroupEnd) {
                        break;
                    }
                    EnableCommand$enableCompositionTracing$1 enableCommand$enableCompositionTracing$1 = ComposerKt.compositionTracer;
                    if (parent < openWriter.groupSize(i) + i) {
                        openWriter.startGroup();
                    } else {
                        openWriter.skipGroup();
                    }
                }
                if (i != parent) {
                    ComposerKt.composeImmediateRuntimeError("Unexpected slot table structure");
                }
                openWriter.startGroup();
                openWriter.advanceBy(anchorIndex - openWriter.currentGroup);
                mutableScatterMapOf.set(movableContentStateReference, ComposerKt.extractMovableContentAtCurrent(movableContentStateReference.composition, movableContentStateReference, openWriter, applier));
            }
            extractNestedStates$lambda$3$closeToGroupContaining(openWriter, Integer.MAX_VALUE);
            Unit unit = Unit.INSTANCE;
            openWriter.close(true);
            return mutableScatterMapOf;
        } catch (Throwable th) {
            openWriter.close(false);
            throw th;
        }
    }
}
