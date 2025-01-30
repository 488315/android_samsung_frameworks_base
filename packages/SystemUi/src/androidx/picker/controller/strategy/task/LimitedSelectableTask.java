package androidx.picker.controller.strategy.task;

import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.viewdata.AppInfoViewData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LimitedSelectableTask {
    public LimitedSelectableTask$$ExternalSyntheticLambda0 disposableHandle;
    public final int limited;
    public HashSet selectedSet;

    public LimitedSelectableTask(int i) {
        this.limited = i;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.picker.controller.strategy.task.LimitedSelectableTask$$ExternalSyntheticLambda0] */
    public final void execute(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof AppInfoViewData) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object next2 = it2.next();
            if (((AppInfoViewData) next2).selectableItem != null) {
                arrayList2.add(next2);
            }
        }
        final ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it3 = arrayList2.iterator();
        while (it3.hasNext()) {
            AppInfoViewData appInfoViewData = (AppInfoViewData) it3.next();
            SelectableItem selectableItem = appInfoViewData.selectableItem;
            Intrinsics.checkNotNull(selectableItem);
            arrayList3.add(new Pair(appInfoViewData, selectableItem));
        }
        if (arrayList3.isEmpty()) {
            return;
        }
        ArrayList arrayList4 = new ArrayList();
        Iterator it4 = arrayList3.iterator();
        while (it4.hasNext()) {
            Object next3 = it4.next();
            Pair pair = (Pair) next3;
            if (!((AppInfoViewData) pair.component1()).getDimmed() && ((SelectableItem) pair.component2()).isSelected()) {
                arrayList4.add(next3);
            }
        }
        ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
        Iterator it5 = arrayList4.iterator();
        while (it5.hasNext()) {
            arrayList5.add(((AppInfoViewData) ((Pair) it5.next()).getFirst()).getAppInfo());
        }
        HashSet hashSet = new HashSet(MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList5, 12)));
        CollectionsKt___CollectionsKt.toCollection(arrayList5, hashSet);
        this.selectedSet = hashSet;
        LimitedSelectableTask$$ExternalSyntheticLambda0 limitedSelectableTask$$ExternalSyntheticLambda0 = this.disposableHandle;
        if (limitedSelectableTask$$ExternalSyntheticLambda0 != null) {
            limitedSelectableTask$$ExternalSyntheticLambda0.dispose();
        }
        final ArrayList arrayList6 = new ArrayList();
        Iterator it6 = arrayList3.iterator();
        while (it6.hasNext()) {
            Pair pair2 = (Pair) it6.next();
            final AppInfoViewData appInfoViewData2 = (AppInfoViewData) pair2.component1();
            final SelectableItem selectableItem2 = (SelectableItem) pair2.component2();
            CollectionsKt__MutableCollectionsKt.addAll(CollectionsKt__CollectionsKt.listOf(selectableItem2.registerBeforeChangeUpdateListener(new Function1() { // from class: androidx.picker.controller.strategy.task.LimitedSelectableTask$execute$disposableHandleList$1$disposableBefore$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    if (((Boolean) obj).booleanValue()) {
                        LimitedSelectableTask limitedSelectableTask = LimitedSelectableTask.this;
                        HashSet hashSet2 = limitedSelectableTask.selectedSet;
                        if (hashSet2 == null) {
                            hashSet2 = null;
                        }
                        if (hashSet2.size() >= limitedSelectableTask.limited) {
                            return Boolean.FALSE;
                        }
                    }
                    return Boolean.TRUE;
                }
            }), selectableItem2.registerAfterChangeUpdateListener(new Function1() { // from class: androidx.picker.controller.strategy.task.LimitedSelectableTask$execute$disposableHandleList$1$disposableAfter$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    if (booleanValue) {
                        HashSet hashSet2 = LimitedSelectableTask.this.selectedSet;
                        if (hashSet2 == null) {
                            hashSet2 = null;
                        }
                        hashSet2.add(appInfoViewData2.getAppInfo());
                    } else {
                        HashSet hashSet3 = LimitedSelectableTask.this.selectedSet;
                        if (hashSet3 == null) {
                            hashSet3 = null;
                        }
                        hashSet3.remove(appInfoViewData2.getAppInfo());
                    }
                    selectableItem2.setValueSilence(Boolean.valueOf(booleanValue));
                    List<Pair<AppInfoViewData, SelectableItem>> list2 = arrayList3;
                    LimitedSelectableTask limitedSelectableTask = LimitedSelectableTask.this;
                    Iterator<T> it7 = list2.iterator();
                    while (it7.hasNext()) {
                        Pair pair3 = (Pair) it7.next();
                        AppInfoViewData appInfoViewData3 = (AppInfoViewData) pair3.component1();
                        SelectableItem selectableItem3 = (SelectableItem) pair3.component2();
                        if (!appInfoViewData3.getDimmed() || !appInfoViewData3.getSelected()) {
                            HashSet hashSet4 = limitedSelectableTask.selectedSet;
                            if (hashSet4 == null) {
                                hashSet4 = null;
                            }
                            appInfoViewData3.dimmedItem.setValueSilence(Boolean.valueOf((hashSet4.size() >= limitedSelectableTask.limited) && !selectableItem3.isSelected()));
                        }
                    }
                    return Unit.INSTANCE;
                }
            })), arrayList6);
        }
        this.disposableHandle = new DisposableHandle() { // from class: androidx.picker.controller.strategy.task.LimitedSelectableTask$$ExternalSyntheticLambda0
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                Iterator it7 = arrayList6.iterator();
                while (it7.hasNext()) {
                    ((DisposableHandle) it7.next()).dispose();
                }
            }
        };
    }
}
