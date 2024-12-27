package com.android.server.biometrics.sensors.face.aidl;

import android.content.Context;
import android.hardware.biometrics.face.ISession;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.biometrics.sensors.face.hidl.HidlToAidlSessionAdapter;

import java.util.function.Supplier;

public final class AidlSession {
    public final AidlResponseHandler mAidlResponseHandler;
    public final int mHalInterfaceVersion;
    public final ISession mSession;
    public final int mUserId;

    public AidlSession(int i, ISession iSession, int i2, AidlResponseHandler aidlResponseHandler) {
        this.mHalInterfaceVersion = i;
        this.mSession = iSession;
        this.mUserId = i2;
        this.mAidlResponseHandler = aidlResponseHandler;
    }

    public AidlSession(
            Context context, Supplier supplier, int i, AidlResponseHandler aidlResponseHandler) {
        HidlToAidlSessionAdapter hidlToAidlSessionAdapter =
                new HidlToAidlSessionAdapter(context, supplier, i, aidlResponseHandler);
        this.mSession = hidlToAidlSessionAdapter;
        SemFaceServiceExImpl semFaceServiceExImpl = SemFaceServiceExImpl.getInstance();
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("sl : "),
                hidlToAidlSessionAdapter.mSecurityLevel,
                "HidlToAidlSessionAdapter");
        semFaceServiceExImpl.mSecurityLevel = hidlToAidlSessionAdapter.mSecurityLevel;
        SemFaceServiceExImpl.getInstance().mIsHIDL = true;
        this.mHalInterfaceVersion = 0;
        this.mUserId = i;
        this.mAidlResponseHandler = aidlResponseHandler;
    }
}
