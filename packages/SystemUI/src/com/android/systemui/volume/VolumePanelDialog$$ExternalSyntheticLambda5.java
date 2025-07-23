package com.android.systemui.volume;

import android.net.Uri;
import androidx.slice.widget.EventInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumePanelDialog$$ExternalSyntheticLambda5 {
    public final /* synthetic */ VolumePanelDialog f$0;

    public final void onSliceAction(EventInfo eventInfo) {
        Uri uri = VolumePanelDialog.REMOTE_MEDIA_SLICE_URI;
        VolumePanelDialog volumePanelDialog = this.f$0;
        volumePanelDialog.getClass();
        if (eventInfo.actionType == 2) {
            return;
        }
        volumePanelDialog.dismiss();
    }
}
