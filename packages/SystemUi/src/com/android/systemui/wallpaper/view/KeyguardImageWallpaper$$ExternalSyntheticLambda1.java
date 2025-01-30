package com.android.systemui.wallpaper.view;

import android.util.Log;
import com.android.systemui.wallpaper.tilt.TiltColorController;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardImageWallpaper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ KeyguardImageWallpaper f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KeyguardImageWallpaper$$ExternalSyntheticLambda1(KeyguardImageWallpaper keyguardImageWallpaper, boolean z) {
        this.f$0 = keyguardImageWallpaper;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardImageWallpaper keyguardImageWallpaper = this.f$0;
        boolean z = this.f$1;
        TiltColorController tiltColorController = keyguardImageWallpaper.mTiltColorController;
        if (tiltColorController != null) {
            if (!z) {
                Log.i("TiltColorController", "stop");
                tiltColorController.setTiltSettingObserver(false);
                tiltColorController.stopAllAnimations();
            }
            TiltColorController tiltColorController2 = keyguardImageWallpaper.mTiltColorController;
            tiltColorController2.getClass();
            Log.i("TiltColorController", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            tiltColorController2.setTiltSettingObserver(true);
        }
    }
}
