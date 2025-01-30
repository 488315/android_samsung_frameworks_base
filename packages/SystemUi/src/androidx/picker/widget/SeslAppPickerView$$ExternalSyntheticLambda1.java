package androidx.picker.widget;

import androidx.picker.loader.select.AllAppsSelectableItem;
import androidx.picker.loader.select.SelectableItem;
import androidx.picker.model.viewdata.AppInfoViewData;
import androidx.picker.model.viewdata.ViewData;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SeslAppPickerView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SeslAppPickerView f$0;

    public /* synthetic */ SeslAppPickerView$$ExternalSyntheticLambda1(SeslAppPickerView seslAppPickerView, int i) {
        this.$r8$classId = i;
        this.f$0 = seslAppPickerView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SelectableItem selectableItem;
        switch (this.$r8$classId) {
            case 0:
                SeslAppPickerView seslAppPickerView = this.f$0;
                seslAppPickerView.getClass();
                ArrayList arrayList = new ArrayList();
                for (ViewData viewData : seslAppPickerView.mViewDataController.currentList) {
                    if ((viewData instanceof AppInfoViewData) && (selectableItem = ((AppInfoViewData) viewData).selectableItem) != null) {
                        arrayList.add(selectableItem);
                    }
                }
                AllAppsSelectableItem allAppsSelectableItem = seslAppPickerView.mSelectStateLoader.allAppsSelectableItem;
                if (allAppsSelectableItem != null) {
                    allAppsSelectableItem.reset(arrayList);
                    break;
                }
                break;
            default:
                this.f$0.mAdapter.notifyDataSetChanged();
                break;
        }
    }
}
