package com.android.server.biometrics.sensors.face.hidl;

import android.content.Context;
import android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback;
import android.hardware.biometrics.face.V1_0.OptionalBool;
import android.hardware.biometrics.face.V1_0.OptionalUint64;
import android.hardware.face.Face;
import android.util.Slog;

import com.android.server.biometrics.sensors.face.FaceUtils;

import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFaceClientCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class TestHal extends ISehBiometricsFace.Stub {
    public ISehBiometricsFaceClientCallback mCallback;
    public final Context mContext;
    public final int mSensorId;
    public int mUserId;

    public TestHal(Context context, int i) {
        this.mContext = context;
        this.mSensorId = i;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int authenticate(long j) {
        Slog.w("face.hidl.TestHal", "authenticate");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int cancel() {
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback == null) {
            return 0;
        }
        iSehBiometricsFaceClientCallback.onError(0, 5, 0, 0L);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int enroll(ArrayList arrayList, int i, ArrayList arrayList2) {
        Slog.w("face.hidl.TestHal", "enroll");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int enumerate() {
        Slog.w("face.hidl.TestHal", "enumerate");
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback != null) {
            iSehBiometricsFaceClientCallback.onEnumerate(0, 0L, new ArrayList());
        }
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final OptionalUint64 generateChallenge(int i) {
        Slog.w("face.hidl.TestHal", "generateChallenge");
        OptionalUint64 optionalUint64 = new OptionalUint64();
        optionalUint64.status = 0;
        optionalUint64.value = 0L;
        return optionalUint64;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final OptionalUint64 getAuthenticatorId() {
        OptionalUint64 optionalUint64 = new OptionalUint64();
        optionalUint64.status = 0;
        optionalUint64.value = 0L;
        return optionalUint64;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final OptionalBool getFeature(int i, int i2) {
        OptionalBool optionalBool = new OptionalBool();
        optionalBool.status = 0;
        optionalBool.value = true;
        return optionalBool;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int remove(int i) {
        Slog.w("face.hidl.TestHal", "remove");
        ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = this.mCallback;
        if (iSehBiometricsFaceClientCallback == null) {
            return 0;
        }
        if (i != 0) {
            iSehBiometricsFaceClientCallback.onRemoved(
                    this.mUserId, 0L, new ArrayList(Collections.singletonList(Integer.valueOf(i))));
            return 0;
        }
        List biometricsForUser =
                FaceUtils.getInstance(this.mSensorId, null)
                        .getBiometricsForUser(this.mContext, this.mUserId);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) biometricsForUser).iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((Face) it.next()).getBiometricId()));
        }
        this.mCallback.onRemoved(this.mUserId, 0L, arrayList);
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int resetLockout(ArrayList arrayList) {
        Slog.w("face.hidl.TestHal", "resetLockout");
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int revokeChallenge() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final int sehAuthenticate(int i, long j, ArrayList arrayList) {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace
    public final int sehAuthenticateForIssuance(
            int i, long j, ArrayList arrayList, boolean z, boolean z2) {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final int sehCloseTaSession() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final String sehGetEngineVersion() {
        return null;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final void sehGetSecurityLevel(
            ISehBiometricsFace.sehGetSecurityLevelCallback sehgetsecuritylevelcallback) {
        sehgetsecuritylevelcallback.onValues(0, 2);
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final String sehGetTaInfo() {
        return null;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final boolean sehIsTaSessionClosed() {
        return false;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final int sehOpenTaSession() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final int sehPauseEnrollment() {
        return 0;
    }

    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
    public final int sehResumeEnrollment() {
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int setActiveUser(int i, String str) {
        this.mUserId = i;
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final OptionalUint64 setCallback(
            IBiometricsFaceClientCallback iBiometricsFaceClientCallback) {
        this.mCallback = (ISehBiometricsFaceClientCallback) iBiometricsFaceClientCallback;
        return new OptionalUint64();
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final int setFeature(int i, boolean z, ArrayList arrayList, int i2) {
        return 0;
    }

    @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
    public final void userActivity() {}
}
