package com.samsung.android.biometrics.app.setting.fingerprint;

import android.content.Context;
import android.os.Handler;

import com.samsung.android.biometrics.app.setting.SysUiUdfpsManager;
import com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$$ExternalSyntheticLambda0;
import com.samsung.android.biometrics.app.setting.SysUiUdfpsManager$$ExternalSyntheticLambda5;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class UdfpsFodHandler {
    public AnonymousClass1 mBroadCastReceiverForTSP;
    public final Context mContext;
    public final Supplier mGetUdfpsCenterPointInPortraitMode;
    public boolean mIsClientRunning;
    public boolean mIsTouchDown;
    public final Handler mMainHandler;
    public UdfpsFodHandler$$ExternalSyntheticLambda3 mRunnablePrintTouchInfo;
    public final BooleanSupplier mShouldIgnoreSingleTap;
    public final BooleanSupplier mShouldUseDelayOfSingleTap;
    public final UdfpsFodHandler$$ExternalSyntheticLambda0 mRunnableTimeoutTouchUp =
            new UdfpsFodHandler$$ExternalSyntheticLambda0(this, 0);
    public final List mFodConsumers = new CopyOnWriteArrayList();

    public UdfpsFodHandler(
            Context context,
            Handler handler,
            SysUiUdfpsManager$$ExternalSyntheticLambda5 sysUiUdfpsManager$$ExternalSyntheticLambda5,
            SysUiUdfpsManager$$ExternalSyntheticLambda5
                    sysUiUdfpsManager$$ExternalSyntheticLambda52,
            SysUiUdfpsManager$$ExternalSyntheticLambda0
                    sysUiUdfpsManager$$ExternalSyntheticLambda0) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mShouldUseDelayOfSingleTap = sysUiUdfpsManager$$ExternalSyntheticLambda5;
        this.mShouldIgnoreSingleTap = sysUiUdfpsManager$$ExternalSyntheticLambda52;
        this.mGetUdfpsCenterPointInPortraitMode = sysUiUdfpsManager$$ExternalSyntheticLambda0;
    }

    public final void handleTouchUp(float f, float f2) {
        this.mMainHandler.removeCallbacks(this.mRunnableTimeoutTouchUp);
        if (this.mIsTouchDown) {
            Iterator it = ((CopyOnWriteArrayList) this.mFodConsumers).iterator();
            while (it.hasNext()) {
                SysUiUdfpsManager.this.handleOnFodTouchUp();
            }
            this.mIsTouchDown = false;
        }
    }
}
