package com.android.systemui.volume;

import androidx.slice.widget.EventInfo;

public final /* synthetic */ class VolumePanelDialog$$ExternalSyntheticLambda5 {
    public final /* synthetic */ VolumePanelDialog f$0;

    public final void onSliceAction(EventInfo eventInfo) {
        VolumePanelDialog volumePanelDialog = this.f$0;
        volumePanelDialog.getClass();
        if (eventInfo.actionType == 2) {
            return;
        }
        volumePanelDialog.dismiss();
    }
}
