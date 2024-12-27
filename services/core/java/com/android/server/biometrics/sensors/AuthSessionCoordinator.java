package com.android.server.biometrics.sensors;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.Slog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.sensors.MultiBiometricLockoutState;
import java.time.Clock;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AuthSessionCoordinator {
    public boolean mIsAuthenticating;
    public final MultiBiometricLockoutState mMultiBiometricLockoutState;
    public int mUserId;
    public final Set mAuthOperations = new HashSet();
    public AuthResultCoordinator mAuthResultCoordinator = new AuthResultCoordinator();
    public final RingBuffer mRingBuffer = new RingBuffer();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RingBuffer {
        public final String[] mApiCalls = new String[100];
        public int mCurr = 0;
        public final int mSize = 100;
        public int mApiCallNumber = 0;

        public final synchronized void addApiCall(String str) {
            String[] strArr = this.mApiCalls;
            int i = this.mCurr;
            strArr[i] = str;
            int i2 = i + 1;
            this.mCurr = i2;
            this.mCurr = i2 % this.mSize;
            this.mApiCallNumber++;
        }

        public final synchronized String toString() {
            String str;
            str = "";
            int i = this.mApiCallNumber;
            int i2 = this.mSize;
            int i3 = 0;
            int i4 = i > i2 ? i - i2 : 0;
            while (true) {
                int i5 = this.mSize;
                if (i3 < i5) {
                    int i6 = (this.mCurr + i3) % i5;
                    if (this.mApiCalls[i6] != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        int i7 = i4 + 1;
                        sb.append(String.format("#%-5d %s\n", Integer.valueOf(i4), this.mApiCalls[i6]));
                        str = sb.toString();
                        i4 = i7;
                    }
                    i3++;
                }
            }
            return str;
        }
    }

    public AuthSessionCoordinator(Clock clock) {
        this.mMultiBiometricLockoutState = new MultiBiometricLockoutState(clock);
    }

    public final void attemptToFinish(int i, int i2, String str) {
        boolean z;
        if (((HashSet) this.mAuthOperations).contains(Integer.valueOf(i2))) {
            z = false;
        } else {
            BootReceiver$$ExternalSyntheticOutline0.m("Error unable to find auth operation : ", str, "AuthSessionCoordinator");
            z = true;
        }
        if (i != this.mUserId) {
            Slog.e("AuthSessionCoordinator", "Error mismatched userId, expected=" + this.mUserId + " for " + str);
            z = true;
        }
        if (z) {
            return;
        }
        ((HashSet) this.mAuthOperations).remove(Integer.valueOf(i2));
        if (this.mIsAuthenticating) {
            Map unmodifiableMap = Collections.unmodifiableMap(this.mAuthResultCoordinator.mAuthenticatorState);
            for (Integer num : Arrays.asList(4095, Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT), 15)) {
                int intValue = num.intValue();
                Integer num2 = (Integer) unmodifiableMap.get(num);
                int intValue2 = num2.intValue() & 4;
                MultiBiometricLockoutState multiBiometricLockoutState = this.mMultiBiometricLockoutState;
                if (intValue2 == 4) {
                    multiBiometricLockoutState.clearPermanentLockOut(this.mUserId, intValue);
                    multiBiometricLockoutState.clearTimedLockout(this.mUserId, intValue);
                } else if ((num2.intValue() & 1) == 1) {
                    Map authMapForUser = multiBiometricLockoutState.getAuthMapForUser(this.mUserId);
                    if (intValue == 15) {
                        ((MultiBiometricLockoutState.AuthenticatorState) authMapForUser.get(15)).mPermanentlyLockedOut = true;
                    } else if (intValue != 255) {
                        if (intValue != 4095) {
                            NandswapManager$$ExternalSyntheticOutline0.m(intValue, "increaseLockoutTime called for invalid strength : ", "MultiBiometricLockoutState");
                        } else {
                            ((MultiBiometricLockoutState.AuthenticatorState) authMapForUser.get(4095)).mPermanentlyLockedOut = true;
                        }
                    }
                    ((MultiBiometricLockoutState.AuthenticatorState) authMapForUser.get(Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT))).mPermanentlyLockedOut = true;
                    ((MultiBiometricLockoutState.AuthenticatorState) authMapForUser.get(4095)).mPermanentlyLockedOut = true;
                } else if ((num2.intValue() & 2) == 2) {
                    Map authMapForUser2 = multiBiometricLockoutState.getAuthMapForUser(this.mUserId);
                    if (intValue == 15) {
                        ((MultiBiometricLockoutState.AuthenticatorState) authMapForUser2.get(15)).mTimedLockout = true;
                    } else if (intValue != 255) {
                        if (intValue != 4095) {
                            NandswapManager$$ExternalSyntheticOutline0.m(intValue, "increaseLockoutTime called for invalid strength : ", "MultiBiometricLockoutState");
                        } else {
                            ((MultiBiometricLockoutState.AuthenticatorState) authMapForUser2.get(4095)).mTimedLockout = true;
                        }
                    }
                    ((MultiBiometricLockoutState.AuthenticatorState) authMapForUser2.get(Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT))).mTimedLockout = true;
                    ((MultiBiometricLockoutState.AuthenticatorState) authMapForUser2.get(4095)).mTimedLockout = true;
                }
            }
            if (((HashSet) this.mAuthOperations).isEmpty()) {
                this.mRingBuffer.addApiCall(AmFmBandRange$$ExternalSyntheticOutline0.m(this.mUserId, new StringBuilder("internal : onAuthSessionEnded("), ")"));
                this.mIsAuthenticating = false;
                ((HashSet) this.mAuthOperations).clear();
            }
        }
    }

    public final synchronized void authEndedFor(long j, int i, int i2, int i3, boolean z) {
        try {
            String str = "authEndedFor(userId=" + i + " ,biometricStrength=" + i2 + ", sensorId=" + i3 + ", requestId=" + j + ", wasSuccessful=" + z + ")";
            this.mRingBuffer.addApiCall(str);
            if (z) {
                AuthResultCoordinator authResultCoordinator = this.mAuthResultCoordinator;
                authResultCoordinator.getClass();
                if (i2 == 15) {
                    authResultCoordinator.updateState(i2, new AuthResultCoordinator$$ExternalSyntheticLambda0(2));
                }
            }
            attemptToFinish(i, i3, str);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void authStartedFor(int i, int i2, long j) {
        try {
            this.mRingBuffer.addApiCall("authStartedFor(userId=" + i + ", sensorId=" + i2 + ", requestId=" + j + ")");
            if (!this.mIsAuthenticating) {
                onAuthSessionStarted(i);
            }
            if (((HashSet) this.mAuthOperations).contains(Integer.valueOf(i2))) {
                Slog.e("AuthSessionCoordinator", "Error, authStartedFor(" + i2 + ") without being finished");
                return;
            }
            if (this.mUserId == i) {
                ((HashSet) this.mAuthOperations).add(Integer.valueOf(i2));
            } else {
                Slog.e("AuthSessionCoordinator", "Error authStartedFor(" + i + ") Incorrect userId, expected" + this.mUserId + ", ignoring...");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void lockOutTimed(int i, int i2, int i3, long j, long j2) {
        String str = "lockOutTimedFor(userId=" + i + ", biometricStrength=" + i2 + ", sensorId=" + i3 + ", time=" + j + ", requestId=" + j2 + ")";
        this.mRingBuffer.addApiCall(str);
        AuthResultCoordinator authResultCoordinator = this.mAuthResultCoordinator;
        authResultCoordinator.getClass();
        authResultCoordinator.updateState(i2, new AuthResultCoordinator$$ExternalSyntheticLambda0(1));
        attemptToFinish(i, i3, str);
    }

    public final synchronized void lockedOutFor(int i, int i2, int i3, long j) {
        String str = "lockOutFor(userId=" + i + ", biometricStrength=" + i2 + ", sensorId=" + i3 + ", requestId=" + j + ")";
        this.mRingBuffer.addApiCall(str);
        AuthResultCoordinator authResultCoordinator = this.mAuthResultCoordinator;
        authResultCoordinator.getClass();
        authResultCoordinator.updateState(i2, new AuthResultCoordinator$$ExternalSyntheticLambda0(0));
        attemptToFinish(i, i3, str);
    }

    public final void onAuthSessionStarted(int i) {
        ((HashSet) this.mAuthOperations).clear();
        this.mUserId = i;
        this.mIsAuthenticating = true;
        this.mAuthResultCoordinator = new AuthResultCoordinator();
        this.mRingBuffer.addApiCall("internal : onAuthSessionStarted(" + i + ")");
    }

    public final synchronized void resetLockoutFor(int i, int i2, long j) {
        this.mRingBuffer.addApiCall("resetLockoutFor(userId=" + i + " ,biometricStrength=" + i2 + ", requestId=" + j + ")");
        if (i2 != 15) {
            this.mMultiBiometricLockoutState.clearTimedLockout(i, i2);
            return;
        }
        this.mIsAuthenticating = false;
        ((HashSet) this.mAuthOperations).clear();
        this.mMultiBiometricLockoutState.clearPermanentLockOut(i, i2);
        this.mMultiBiometricLockoutState.clearTimedLockout(i, i2);
    }

    public final String toString() {
        return this.mRingBuffer + "\n" + this.mMultiBiometricLockoutState;
    }
}
