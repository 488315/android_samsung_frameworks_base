package com.android.systemui.volume;

import androidx.slice.widget.EventInfo;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
