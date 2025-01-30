package androidx.picker.controller.strategy.task;

import androidx.picker.model.viewdata.ViewData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SortAppInfoViewDataTask {
    public final Comparator comparator;

    /* JADX WARN: Multi-variable type inference failed */
    public SortAppInfoViewDataTask() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final List execute(List list) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list);
        Comparator comparator = this.comparator;
        if (comparator != null) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, comparator);
        }
        return arrayList;
    }

    public SortAppInfoViewDataTask(Comparator<ViewData> comparator) {
        this.comparator = comparator;
    }

    public /* synthetic */ SortAppInfoViewDataTask(Comparator comparator, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : comparator);
    }
}
