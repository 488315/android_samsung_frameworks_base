package com.android.systemui.shade.domain.interactor;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;

/* loaded from: classes3.dex */
public final class SecPanelExpansionStateInteractor$stateListener$1 implements StatusBarStateController.StateListener {
    public final /* synthetic */ SecPanelExpansionStateInteractor this$0;

    public SecPanelExpansionStateInteractor$stateListener$1(SecPanelExpansionStateInteractor secPanelExpansionStateInteractor) {
        this.this$0 = secPanelExpansionStateInteractor;
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        super.onStateChanged(i);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onStateChanged to ", StatusBarState.toString(i), "SecPanelExpansionStateInteractor");
        int i2 = SecPanelExpansionStateInteractor.$r8$clinit;
        this.this$0.getRepository()._statusBarState.updateState(null, Integer.valueOf(i));
    }
}
