package com.android.systemui.communal.ui.compose;

import androidx.compose.runtime.snapshots.SnapshotStateList;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ContentListState {
    public final SnapshotStateList list;
    public final Function3 onAddWidget;
    public final Function4 onDeleteWidget;
    public final Function1 onReorderWidgets;
    public final Function5 onResizeWidget;

    public ContentListState(List<? extends CommunalContentModel> list, Function3 function3, Function4 function4, Function1 function1, Function5 function5) {
        this.onAddWidget = function3;
        this.onDeleteWidget = function4;
        this.onReorderWidgets = function1;
        this.onResizeWidget = function5;
        SnapshotStateList snapshotStateList = new SnapshotStateList();
        snapshotStateList.addAll(list);
        this.list = snapshotStateList;
    }

    public static void onSaveList$default(ContentListState contentListState) {
        contentListState.getClass();
        ArrayList arrayList = new ArrayList();
        ListIterator listIterator = contentListState.list.listIterator();
        int i = 0;
        while (listIterator.hasNext()) {
            Object next = listIterator.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            CommunalContentModel communalContentModel = (CommunalContentModel) next;
            Pair pair = communalContentModel instanceof CommunalContentModel.WidgetContent ? new Pair(Integer.valueOf(((CommunalContentModel.WidgetContent) communalContentModel).getAppWidgetId()), Integer.valueOf(i)) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
            i = i2;
        }
        contentListState.onReorderWidgets.mo779invoke(MapsKt__MapsKt.toMap(arrayList));
    }

    public final boolean isItemEditable(int i) {
        CommunalContentModel communalContentModel = (CommunalContentModel) this.list.get(i);
        communalContentModel.getClass();
        return communalContentModel instanceof CommunalContentModel.WidgetContent;
    }

    public final void onRemove(int i) {
        SnapshotStateList snapshotStateList = this.list;
        CommunalContentModel communalContentModel = (CommunalContentModel) snapshotStateList.get(i);
        communalContentModel.getClass();
        if (communalContentModel instanceof CommunalContentModel.WidgetContent) {
            CommunalContentModel.WidgetContent widgetContent = (CommunalContentModel.WidgetContent) snapshotStateList.get(i);
            snapshotStateList.remove(i);
            this.onDeleteWidget.invoke(Integer.valueOf(widgetContent.getAppWidgetId()), widgetContent.getKey(), widgetContent.getComponentName(), Integer.valueOf(widgetContent.getRank()));
        }
    }
}
