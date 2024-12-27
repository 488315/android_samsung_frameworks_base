package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.view.MediaHostState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaViewController$stateCallback$1 implements MediaHostStatesManager.Callback {
    public final /* synthetic */ MediaViewController this$0;

    public MediaViewController$stateCallback$1(MediaViewController mediaViewController) {
        this.this$0 = mediaViewController;
    }

    @Override // com.android.systemui.media.controls.ui.controller.MediaHostStatesManager.Callback
    public final void onHostStateChanged(int i, MediaHostState mediaHostState) {
        MediaViewController mediaViewController = this.this$0;
        int i2 = mediaViewController.currentEndLocation;
        if (i == i2 || i == mediaViewController.currentStartLocation) {
            mediaViewController.setCurrentState(mediaViewController.currentStartLocation, i2, mediaViewController.currentTransitionProgress, false, false);
        }
    }
}
