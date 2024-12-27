package com.android.server.biometrics.log;

import android.hardware.biometrics.common.OperationContext;
import android.hardware.biometrics.common.OperationState;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OperationContextExt {
    public final OperationContext mAidlContext;
    public final boolean mIsBP;
    public final boolean mIsMandatoryBiometrics;
    public BiometricContextSessionInfo mSessionInfo;
    public boolean mIsDisplayOn = false;
    public int mDockState = 0;
    public int mOrientation = 0;
    public int mFoldState = 0;

    public OperationContextExt(OperationContext operationContext, boolean z, int i, boolean z2) {
        this.mAidlContext = operationContext;
        this.mIsBP = z;
        this.mIsMandatoryBiometrics = z2;
        if (i == 2) {
            operationContext.operationState = OperationState.fingerprintOperationState(new OperationState.FingerprintOperationState());
        } else if (i == 8) {
            operationContext.operationState = OperationState.faceOperationState(new OperationState.FaceOperationState());
        }
    }

    public final void update(BiometricContext biometricContext, boolean z) {
        OperationContext operationContext = this.mAidlContext;
        BiometricContextProvider biometricContextProvider = (BiometricContextProvider) biometricContext;
        int i = biometricContextProvider.mDisplayState;
        int i2 = 4;
        operationContext.isAod = i == 4;
        int i3 = 3;
        if (i == 1) {
            i2 = 1;
        } else if (i == 2) {
            i2 = 2;
        } else if (i == 3) {
            i2 = 3;
        } else if (i != 4) {
            i2 = 0;
        }
        operationContext.displayState = i2;
        int i4 = biometricContextProvider.mFoldState;
        if (i4 == 1) {
            i3 = 1;
        } else if (i4 == 2) {
            i3 = 2;
        } else if (i4 != 3) {
            i3 = 0;
        }
        operationContext.foldState = i3;
        operationContext.isCrypto = z;
        OperationState operationState = operationContext.operationState;
        if (operationState != null && operationState.getTag() == 0) {
            this.mAidlContext.operationState.getFingerprintOperationState().isHardwareIgnoringTouches = biometricContextProvider.mIsHardwareIgnoringTouches;
        }
        if (this.mIsBP) {
            BiometricContextSessionInfo biometricContextSessionInfo = (BiometricContextSessionInfo) ((ConcurrentHashMap) biometricContextProvider.mSession).get(2);
            this.mSessionInfo = biometricContextSessionInfo;
            if (biometricContextSessionInfo != null) {
                this.mAidlContext.id = biometricContextSessionInfo.mId.getId();
                this.mAidlContext.reason = (byte) 1;
            }
            OperationContext operationContext2 = this.mAidlContext;
            operationContext2.id = 0;
            operationContext2.reason = (byte) 0;
        } else {
            BiometricContextSessionInfo biometricContextSessionInfo2 = (BiometricContextSessionInfo) ((ConcurrentHashMap) biometricContextProvider.mSession).get(1);
            this.mSessionInfo = biometricContextSessionInfo2;
            if (biometricContextSessionInfo2 != null) {
                this.mAidlContext.id = biometricContextSessionInfo2.mId.getId();
                this.mAidlContext.reason = (byte) 2;
            }
            OperationContext operationContext22 = this.mAidlContext;
            operationContext22.id = 0;
            operationContext22.reason = (byte) 0;
        }
        this.mIsDisplayOn = biometricContextProvider.mWindowManager.getDefaultDisplay().getState() == 2;
        this.mDockState = biometricContextProvider.mDockState;
        this.mFoldState = biometricContextProvider.mFoldState;
        this.mOrientation = biometricContextProvider.mWindowManager.getDefaultDisplay().getRotation();
    }
}
