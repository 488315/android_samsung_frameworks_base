package android.hardware.biometrics.face.V1_0;

import android.hidl.base.V1_0.IBase;

import java.util.ArrayList;

public interface IBiometricsFace extends IBase {
    int authenticate(long j);

    int cancel();

    int enroll(ArrayList arrayList, int i, ArrayList arrayList2);

    int enumerate();

    OptionalUint64 generateChallenge(int i);

    OptionalUint64 getAuthenticatorId();

    OptionalBool getFeature(int i, int i2);

    int remove(int i);

    int resetLockout(ArrayList arrayList);

    int revokeChallenge();

    int setActiveUser(int i, String str);

    OptionalUint64 setCallback(IBiometricsFaceClientCallback iBiometricsFaceClientCallback);

    int setFeature(int i, boolean z, ArrayList arrayList, int i2);

    void userActivity();
}
