package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.os.Handler;

import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final class SysUiClientFactoryImpl {
    public final Context mContext;
    public final DisplayStateManager mDsm;
    public final Supplier mGetAddedSensorWindow;
    public final Supplier mGetFpServiceProvider;
    public final Supplier mGetFpsSensorInfo;
    public final Supplier mGetPowerServiceProvider;
    public final Handler mHandler;
    public final Consumer mSensorIconVisibilityHandler;

    public SysUiClientFactoryImpl(
            Context context,
            Handler handler,
            DisplayStateManager displayStateManager,
            Supplier supplier,
            Supplier supplier2,
            Supplier supplier3,
            SysUiUdfpsManager$$ExternalSyntheticLambda0 sysUiUdfpsManager$$ExternalSyntheticLambda0,
            SysUiUdfpsManager$$ExternalSyntheticLambda4
                    sysUiUdfpsManager$$ExternalSyntheticLambda4) {
        this.mContext = context;
        this.mHandler = handler;
        this.mDsm = displayStateManager;
        this.mGetPowerServiceProvider = supplier;
        this.mGetFpServiceProvider = supplier2;
        this.mGetFpsSensorInfo = supplier3;
        this.mGetAddedSensorWindow =
                sysUiUdfpsManager$$ExternalSyntheticLambda0 == null
                        ? new SysUiClientFactoryImpl$$ExternalSyntheticLambda0()
                        : sysUiUdfpsManager$$ExternalSyntheticLambda0;
        this.mSensorIconVisibilityHandler =
                sysUiUdfpsManager$$ExternalSyntheticLambda4 == null
                        ? new SysUiClientFactoryImpl$$ExternalSyntheticLambda1()
                        : sysUiUdfpsManager$$ExternalSyntheticLambda4;
    }

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.SysUiClientFactoryImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends SysUiClient {
        @Override // com.samsung.android.biometrics.app.setting.SysUiClient
        public final void prepareWindows() {}
    }
}
