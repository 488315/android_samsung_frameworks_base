package com.android.systemui.communal.ui.compose;

import android.content.ComponentName;
import android.os.UserHandle;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.runtime.snapshots.StateListIterator;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ContentListState {
    public final SnapshotStateList list;
    public final Function3 onAddWidget;
    public final Function1 onDeleteWidget;
    public final Function1 onReorderWidgets;

    public ContentListState(List<? extends CommunalContentModel> list, Function3 function3, Function1 function1, Function1 function12) {
        this.onAddWidget = function3;
        this.onDeleteWidget = function1;
        this.onReorderWidgets = function12;
        SnapshotStateList snapshotStateList = new SnapshotStateList();
        snapshotStateList.addAll(list);
        this.list = snapshotStateList;
    }

    public final void onRemove(int i) {
        SnapshotStateList snapshotStateList = this.list;
        CommunalContentModel communalContentModel = (CommunalContentModel) snapshotStateList.get(i);
        communalContentModel.getClass();
        if (communalContentModel instanceof CommunalContentModel.WidgetContent) {
            CommunalContentModel.WidgetContent widgetContent = (CommunalContentModel.WidgetContent) snapshotStateList.get(i);
            snapshotStateList.remove(i);
            this.onDeleteWidget.invoke(Integer.valueOf(widgetContent.getAppWidgetId()));
        }
    }

    public final void onSaveList(ComponentName componentName, UserHandle userHandle, Integer num) {
        ArrayList arrayList = new ArrayList();
        SnapshotStateList snapshotStateList = this.list;
        ListIterator listIterator = snapshotStateList.listIterator();
        int i = 0;
        while (true) {
            StateListIterator stateListIterator = (StateListIterator) listIterator;
            if (!stateListIterator.hasNext()) {
                this.onReorderWidgets.invoke(MapsKt__MapsKt.toMap(arrayList));
                if (componentName == null || userHandle == null || num == null) {
                    return;
                }
                this.onAddWidget.invoke(componentName, userHandle, Integer.valueOf(snapshotStateList.size() - num.intValue()));
                return;
            }
            Object next = stateListIterator.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            CommunalContentModel communalContentModel = (CommunalContentModel) next;
            Pair pair = communalContentModel instanceof CommunalContentModel.WidgetContent ? new Pair(Integer.valueOf(((CommunalContentModel.WidgetContent) communalContentModel).getAppWidgetId()), Integer.valueOf(snapshotStateList.size() - i)) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
            i = i2;
        }
    }
}
