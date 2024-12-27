package com.samsung.android.biometrics.app.setting;

import android.graphics.Point;

import com.samsung.android.biometrics.app.setting.fingerprint.FingerprintSensorInfo;

import java.util.function.Supplier;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */ class SysUiUdfpsManager$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SysUiUdfpsManager f$0;

    public /* synthetic */ SysUiUdfpsManager$$ExternalSyntheticLambda0(
            SysUiUdfpsManager sysUiUdfpsManager, int i) {
        this.$r8$classId = i;
        this.f$0 = sysUiUdfpsManager;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        int i = this.$r8$classId;
        SysUiUdfpsManager sysUiUdfpsManager = this.f$0;
        switch (i) {
            case 0:
                return sysUiUdfpsManager.mPsProvider;
            case 1:
                return sysUiUdfpsManager.mFpSvcProvider;
            case 2:
                return sysUiUdfpsManager.mFingerprintSensorInfo;
            case 3:
                return sysUiUdfpsManager.mUdfpsSensorWindow;
            default:
                int i2 = sysUiUdfpsManager.mDisplayStateManager.mCurrentRotation;
                FingerprintSensorInfo fingerprintSensorInfo =
                        sysUiUdfpsManager.mFingerprintSensorInfo;
                boolean z = true;
                if (i2 != 1 && i2 != 3) {
                    z = false;
                }
                if (!fingerprintSensorInfo.mIsAnyUdfps) {
                    return new Point();
                }
                Point maximumWindowSize =
                        Utils.getMaximumWindowSize(fingerprintSensorInfo.mContext);
                int i3 = maximumWindowSize.x;
                int i4 = maximumWindowSize.y;
                if (z) {
                    i3 = i4;
                    i4 = i3;
                }
                Point point = new Point();
                point.x = (i3 / 2) - fingerprintSensorInfo.mSensorMarginLeft;
                point.y =
                        i4
                                - ((fingerprintSensorInfo.mSensorAreaHeight / 2)
                                        + fingerprintSensorInfo.mSensorMarginBottom);
                return point;
        }
    }
}
