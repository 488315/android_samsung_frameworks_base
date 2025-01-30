package com.android.systemui.wallpaper.view;

import android.util.Log;
import com.android.systemui.wallpaper.tilt.TiltColorController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardImageWallpaper$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ KeyguardImageWallpaper f$0;

    public /* synthetic */ KeyguardImageWallpaper$$ExternalSyntheticLambda0(KeyguardImageWallpaper keyguardImageWallpaper) {
        this.f$0 = keyguardImageWallpaper;
    }

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardImageWallpaper keyguardImageWallpaper = this.f$0;
        TiltColorController tiltColorController = keyguardImageWallpaper.mTiltColorController;
        if (tiltColorController != null) {
            tiltColorController.setEnable(false);
            TiltColorController tiltColorController2 = keyguardImageWallpaper.mTiltColorController;
            tiltColorController2.getClass();
            Log.i("TiltColorController", "stop");
            tiltColorController2.setTiltSettingObserver(false);
            tiltColorController2.stopAllAnimations();
        }
    }
}
