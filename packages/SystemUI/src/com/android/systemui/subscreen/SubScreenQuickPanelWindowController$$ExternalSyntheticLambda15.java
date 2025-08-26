package com.android.systemui.subscreen;

import android.os.Bundle;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 implements Consumer {
    public final /* synthetic */ SubScreenQuickPanelWindowController f$0;

    public /* synthetic */ SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15(SubScreenQuickPanelWindowController subScreenQuickPanelWindowController) {
        this.f$0 = subScreenQuickPanelWindowController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SubScreenQuickPanelWindowController subScreenQuickPanelWindowController = this.f$0;
        subScreenQuickPanelWindowController.collapsePanel();
        if (subScreenQuickPanelWindowController.mSubScreenStateChangedListener != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(SubRoom.EXTRA_KEY_LAUNCH_MEDIA_NOWBAR, true);
            subScreenQuickPanelWindowController.mSubScreenStateChangedListener.onStateChanged(bundle);
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_MEDIA_SONG_TITLE_COVER);
    }
}
