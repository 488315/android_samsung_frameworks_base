package com.android.systemui.statusbar.phone;

import android.graphics.Rect;
import com.android.systemui.statusbar.disableflags.DisableStateTracker;

/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda2 implements SidelingCutoutContainerInfo, DisableStateTracker.Callback, KeyguardStatusBarWallpaperListener {
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda2(KeyguardStatusBarViewController keyguardStatusBarViewController) {
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // com.android.systemui.statusbar.phone.SidelingCutoutContainerInfo
    public int getRightSideAvailableWidth(Rect rect) {
        return KeyguardStatusBarViewController.$r8$lambda$z2zgdSVI2vZwFMhzcuLHD6QN16w(this.f$0, rect);
    }

    @Override // com.android.systemui.statusbar.phone.KeyguardStatusBarWallpaperListener
    public void onWallpaperUpdated() {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        keyguardStatusBarViewController.mMainExecutor.execute(new KeyguardStatusBarViewController$$ExternalSyntheticLambda15(keyguardStatusBarViewController, 1));
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = keyguardStatusBarViewController.mKeyguardStatusBarWallpaperHelper;
        if (keyguardStatusBarWallpaperHelper != null) {
            keyguardStatusBarViewController.mNotificationIconAreaController.setKeyguardNotifIconTint(keyguardStatusBarWallpaperHelper.fontColorFromWallPaper);
        }
    }
}
