package com.android.systemui.volume;

import androidx.slice.widget.EventInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class VolumePanelDialog$$ExternalSyntheticLambda4 {
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
