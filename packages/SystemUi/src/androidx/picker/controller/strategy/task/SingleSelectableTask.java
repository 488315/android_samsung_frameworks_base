package androidx.picker.controller.strategy.task;

import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.viewdata.AppInfoViewData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SingleSelectableTask {
    public SingleSelectableTask$$ExternalSyntheticLambda0 disposableHandle;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v6, types: [androidx.picker.controller.strategy.task.SingleSelectableTask$$ExternalSyntheticLambda0] */
    public final void execute(List list) {
        T t;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof AppInfoViewData) {
                arrayList.add(next);
            }
        }
        final ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            SelectableItem selectableItem = ((AppInfoViewData) it2.next()).selectableItem;
            if (selectableItem != null) {
                arrayList2.add(selectableItem);
            }
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        SingleSelectableTask$$ExternalSyntheticLambda0 singleSelectableTask$$ExternalSyntheticLambda0 = this.disposableHandle;
        if (singleSelectableTask$$ExternalSyntheticLambda0 != null) {
            singleSelectableTask$$ExternalSyntheticLambda0.dispose();
        }
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        Iterator it3 = arrayList2.iterator();
        while (true) {
            if (!it3.hasNext()) {
                t = 0;
                break;
            } else {
                t = it3.next();
                if (((SelectableItem) t).isSelected()) {
                    break;
                }
            }
        }
        ref$ObjectRef.element = t;
        final ArrayList arrayList3 = new ArrayList();
        Iterator it4 = arrayList2.iterator();
        while (it4.hasNext()) {
            final SelectableItem selectableItem2 = (SelectableItem) it4.next();
            selectableItem2.setValueSilence(Boolean.valueOf(Intrinsics.areEqual(selectableItem2, ref$ObjectRef.element)));
            CollectionsKt__MutableCollectionsKt.addAll(CollectionsKt__CollectionsKt.listOf(selectableItem2.registerBeforeChangeUpdateListener(new Function1() { // from class: androidx.picker.controller.strategy.task.SingleSelectableTask$execute$disposableHandleList$1$disposableBefore$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ((Boolean) obj).booleanValue();
                    return Intrinsics.areEqual(SelectableItem.this, ref$ObjectRef.element) ? Boolean.FALSE : Boolean.TRUE;
                }
            }), selectableItem2.registerAfterChangeUpdateListener(new Function1() { // from class: androidx.picker.controller.strategy.task.SingleSelectableTask$execute$disposableHandleList$1$disposableAfter$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                /* JADX WARN: Type inference failed for: r0v0, types: [T, androidx.picker.loader.select.SelectableItem] */
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    if (((Boolean) obj).booleanValue()) {
                        ref$ObjectRef.element = selectableItem2;
                        List<SelectableItem> list2 = arrayList2;
                        ArrayList arrayList4 = new ArrayList();
                        for (Object obj2 : list2) {
                            if (!Intrinsics.areEqual((SelectableItem) obj2, r6.element)) {
                                arrayList4.add(obj2);
                            }
                        }
                        Iterator it5 = arrayList4.iterator();
                        while (it5.hasNext()) {
                            ((SelectableItem) it5.next()).setValue(Boolean.FALSE);
                        }
                        SelectableItem selectableItem3 = ref$ObjectRef.element;
                        if (selectableItem3 != null) {
                            selectableItem3.setValueSilence(Boolean.TRUE);
                        }
                    }
                    return Unit.INSTANCE;
                }
            })), arrayList3);
        }
        SelectableItem selectableItem3 = (SelectableItem) ref$ObjectRef.element;
        if (selectableItem3 != null) {
            selectableItem3.setValue(Boolean.TRUE);
        }
        this.disposableHandle = new DisposableHandle() { // from class: androidx.picker.controller.strategy.task.SingleSelectableTask$$ExternalSyntheticLambda0
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                Iterator it5 = arrayList3.iterator();
                while (it5.hasNext()) {
                    ((DisposableHandle) it5.next()).dispose();
                }
            }
        };
    }
}
