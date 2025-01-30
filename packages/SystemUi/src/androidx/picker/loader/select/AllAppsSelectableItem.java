package androidx.picker.loader.select;

import androidx.picker.features.observable.BooleanState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AllAppsSelectableItem extends SelectableItem implements DisposableHandle {
    private DisposableHandle disposableHandle;
    private final List<SelectableItem> selectableItemList;

    public /* synthetic */ AllAppsSelectableItem(List list, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? new Function1() { // from class: androidx.picker.loader.select.AllAppsSelectableItem.1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                ((Boolean) obj).booleanValue();
                return Unit.INSTANCE;
            }
        } : function1);
    }

    private final void bindSelectableItemList() {
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
        final DisposableHandle registerAfterChangeUpdateListener = registerAfterChangeUpdateListener(new Function1() { // from class: androidx.picker.loader.select.AllAppsSelectableItem$bindSelectableItemList$disposable1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                List list;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                list = AllAppsSelectableItem.this.selectableItemList;
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ((SelectableItem) it.next()).setValueSilence(Boolean.valueOf(booleanValue));
                }
                return Unit.INSTANCE;
            }
        });
        List<SelectableItem> list = this.selectableItemList;
        final ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((SelectableItem) it.next()).registerAfterChangeUpdateListener(new Function1() { // from class: androidx.picker.loader.select.AllAppsSelectableItem$bindSelectableItemList$disposableHandleList$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    ((Boolean) obj).booleanValue();
                    AllAppsSelectableItem.this.updateAllAppsStatus();
                    return Unit.INSTANCE;
                }
            }));
        }
        this.disposableHandle = new DisposableHandle() { // from class: androidx.picker.loader.select.AllAppsSelectableItem$$ExternalSyntheticLambda0
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                AllAppsSelectableItem.m338bindSelectableItemList$lambda4(DisposableHandle.this, arrayList);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: bindSelectableItemList$lambda-4, reason: not valid java name */
    public static final void m338bindSelectableItemList$lambda4(DisposableHandle disposableHandle, List list) {
        disposableHandle.dispose();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((DisposableHandle) it.next()).dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateAllAppsStatus() {
        if (this.selectableItemList.isEmpty()) {
            return;
        }
        List<SelectableItem> list = this.selectableItemList;
        boolean z = true;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (!((SelectableItem) it.next()).isSelected()) {
                    z = false;
                    break;
                }
            }
        }
        setValueSilence(Boolean.valueOf(z));
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public void dispose() {
        DisposableHandle disposableHandle = this.disposableHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
    }

    public final void reset(List<? extends SelectableItem> list) {
        List<SelectableItem> list2 = this.selectableItemList;
        list2.clear();
        list2.addAll(list);
        bindSelectableItemList();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AllAppsSelectableItem(List<? extends SelectableItem> list, Function1 function1) {
        super(new BooleanState(r1), function1);
        boolean z = true;
        if (!list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (!((SelectableItem) it.next()).isSelected()) {
                    z = false;
                    break;
                }
            }
        }
        this.selectableItemList = new ArrayList(list);
        bindSelectableItemList();
    }
}
