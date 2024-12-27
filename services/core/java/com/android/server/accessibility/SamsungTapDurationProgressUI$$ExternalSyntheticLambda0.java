package com.android.server.accessibility;

import android.app.ActivityManager;
import android.content.res.Configuration;
import android.os.RemoteException;

public final /* synthetic */ class SamsungTapDurationProgressUI$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SamsungTapDurationProgressUI f$0;

    public /* synthetic */ SamsungTapDurationProgressUI$$ExternalSyntheticLambda0(
            SamsungTapDurationProgressUI samsungTapDurationProgressUI, int i) {
        this.$r8$classId = i;
        this.f$0 = samsungTapDurationProgressUI;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SamsungTapDurationProgressUI samsungTapDurationProgressUI = this.f$0;
        switch (i) {
            case 0:
                samsungTapDurationProgressUI.getClass();
                try {
                    Configuration configuration = ActivityManager.getService().getConfiguration();
                    int i2 = samsungTapDurationProgressUI.mUiMode;
                    int i3 = configuration.uiMode;
                    if (i2 != i3
                            || samsungTapDurationProgressUI.mDensityDpi
                                    != configuration.densityDpi) {
                        samsungTapDurationProgressUI.mUiMode = i3;
                        samsungTapDurationProgressUI.mDensityDpi = configuration.densityDpi;
                        samsungTapDurationProgressUI.mContext =
                                samsungTapDurationProgressUI.mContext.createConfigurationContext(
                                        configuration);
                        samsungTapDurationProgressUI.mWindowManager.removeView(
                                samsungTapDurationProgressUI.mView);
                        samsungTapDurationProgressUI.mSize =
                                samsungTapDurationProgressUI
                                        .mContext
                                        .getResources()
                                        .getDimensionPixelSize(17106301);
                        samsungTapDurationProgressUI.makeView();
                        samsungTapDurationProgressUI.makeAnimation();
                    }
                } catch (RemoteException unused) {
                }
                if (samsungTapDurationProgressUI.mFadeOutAnimator.isRunning()) {
                    samsungTapDurationProgressUI.mFadeOutAnimator.end();
                }
                samsungTapDurationProgressUI.mRotationSet.setDuration(
                        samsungTapDurationProgressUI.mDuration);
                samsungTapDurationProgressUI.mScaleSet.start();
                break;
            case 1:
                if (samsungTapDurationProgressUI.mScaleSet.isRunning()) {
                    samsungTapDurationProgressUI.mScaleSet.cancel();
                }
                if (samsungTapDurationProgressUI.mRotationSet.isRunning()) {
                    samsungTapDurationProgressUI.mRotationSet.cancel();
                }
                samsungTapDurationProgressUI.mFadeOutAnimator.start();
                break;
            case 2:
                samsungTapDurationProgressUI.mWindowManager.updateViewLayout(
                        samsungTapDurationProgressUI.mView, samsungTapDurationProgressUI.mParams);
                break;
            default:
                if (samsungTapDurationProgressUI.mRotationSet.isRunning()) {
                    samsungTapDurationProgressUI.mRotationSet.end();
                }
                if (!samsungTapDurationProgressUI.mIsRemoveAnimationEnabled
                        && !samsungTapDurationProgressUI.mIsShortThreshold) {
                    samsungTapDurationProgressUI.mFadeOutAnimator.start();
                    break;
                } else {
                    samsungTapDurationProgressUI.mStandBy.setVisibility(8);
                    samsungTapDurationProgressUI.mHold.setVisibility(0);
                    break;
                }
                break;
        }
    }
}
