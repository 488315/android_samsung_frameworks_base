package com.android.systemui.media.controls.ui.controller;

import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.view.MediaHostState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
