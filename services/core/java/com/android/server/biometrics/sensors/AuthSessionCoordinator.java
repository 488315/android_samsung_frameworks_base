package com.android.server.biometrics.sensors;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.time.Clock;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class AuthSessionCoordinator {
    public final Set mAuthOperations;
    public AuthResultCoordinator mAuthResultCoordinator;
    public boolean mIsAuthenticating;
    public final MultiBiometricLockoutState mMultiBiometricLockoutState;
    public final RingBuffer mRingBuffer;
    public int mUserId;

    public AuthSessionCoordinator() {
        this(SystemClock.elapsedRealtimeClock());
    }

    public AuthSessionCoordinator(Clock clock) {
        this.mAuthOperations = new HashSet();
        this.mAuthResultCoordinator = new AuthResultCoordinator();
        this.mMultiBiometricLockoutState = new MultiBiometricLockoutState(clock);
        this.mRingBuffer = new RingBuffer(100);
    }

    public void onAuthSessionStarted(int i) {
        this.mAuthOperations.clear();
        this.mUserId = i;
        this.mIsAuthenticating = true;
        this.mAuthResultCoordinator = new AuthResultCoordinator();
        this.mRingBuffer.addApiCall("internal : onAuthSessionStarted(" + i + ")");
    }

    public void endAuthSession() {
        Map result = this.mAuthResultCoordinator.getResult();
        Iterator it = Arrays.asList(4095, Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT), 15).iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            Integer num = (Integer) result.get(Integer.valueOf(intValue));
            if ((num.intValue() & 4) == 4) {
                this.mMultiBiometricLockoutState.clearPermanentLockOut(this.mUserId, intValue);
                this.mMultiBiometricLockoutState.clearTimedLockout(this.mUserId, intValue);
            } else if ((num.intValue() & 1) == 1) {
                this.mMultiBiometricLockoutState.setPermanentLockOut(this.mUserId, intValue);
            } else if ((num.intValue() & 2) == 2) {
                this.mMultiBiometricLockoutState.setTimedLockout(this.mUserId, intValue);
            }
        }
        if (this.mAuthOperations.isEmpty()) {
            this.mRingBuffer.addApiCall("internal : onAuthSessionEnded(" + this.mUserId + ")");
            clearSession();
        }
    }

    public final void clearSession() {
        this.mIsAuthenticating = false;
        this.mAuthOperations.clear();
    }

    public synchronized int getLockoutStateFor(int i, int i2) {
        return this.mMultiBiometricLockoutState.getLockoutState(i, i2);
    }

    public synchronized void authStartedFor(int i, int i2, long j) {
        this.mRingBuffer.addApiCall("authStartedFor(userId=" + i + ", sensorId=" + i2 + ", requestId=" + j + ")");
        if (!this.mIsAuthenticating) {
            onAuthSessionStarted(i);
        }
        if (this.mAuthOperations.contains(Integer.valueOf(i2))) {
            Slog.e("AuthSessionCoordinator", "Error, authStartedFor(" + i2 + ") without being finished");
            return;
        }
        if (this.mUserId != i) {
            Slog.e("AuthSessionCoordinator", "Error authStartedFor(" + i + ") Incorrect userId, expected" + this.mUserId + ", ignoring...");
            return;
        }
        this.mAuthOperations.add(Integer.valueOf(i2));
    }

    public synchronized void lockedOutFor(int i, int i2, int i3, long j) {
        String str = "lockOutFor(userId=" + i + ", biometricStrength=" + i2 + ", sensorId=" + i3 + ", requestId=" + j + ")";
        this.mRingBuffer.addApiCall(str);
        this.mAuthResultCoordinator.lockedOutFor(i2);
        attemptToFinish(i, i3, str);
    }

    public synchronized void lockOutTimed(int i, int i2, int i3, long j, long j2) {
        String str = "lockOutTimedFor(userId=" + i + ", biometricStrength=" + i2 + ", sensorId=" + i3 + "time=" + j + ", requestId=" + j2 + ")";
        this.mRingBuffer.addApiCall(str);
        this.mAuthResultCoordinator.lockOutTimed(i2);
        attemptToFinish(i, i3, str);
    }

    public synchronized void authEndedFor(int i, int i2, int i3, long j, boolean z) {
        String str = "authEndedFor(userId=" + i + " ,biometricStrength=" + i2 + ", sensorId=" + i3 + ", requestId=" + j + ", wasSuccessful=" + z + ")";
        this.mRingBuffer.addApiCall(str);
        if (z) {
            this.mAuthResultCoordinator.authenticatedFor(i2);
        }
        attemptToFinish(i, i3, str);
    }

    public synchronized void resetLockoutFor(int i, int i2, long j) {
        this.mRingBuffer.addApiCall("resetLockoutFor(userId=" + i + " ,biometricStrength=" + i2 + ", requestId=" + j + ")");
        if (i2 == 15) {
            clearSession();
            this.mMultiBiometricLockoutState.clearTimedLockout(i, i2);
            this.mMultiBiometricLockoutState.clearPermanentLockOut(i, i2);
            return;
        }
        this.mMultiBiometricLockoutState.clearTimedLockout(i, i2);
    }

    public final void attemptToFinish(int i, int i2, String str) {
        boolean z;
        boolean z2 = true;
        if (this.mAuthOperations.contains(Integer.valueOf(i2))) {
            z = false;
        } else {
            Slog.e("AuthSessionCoordinator", "Error unable to find auth operation : " + str);
            z = true;
        }
        if (i != this.mUserId) {
            Slog.e("AuthSessionCoordinator", "Error mismatched userId, expected=" + this.mUserId + " for " + str);
        } else {
            z2 = z;
        }
        if (z2) {
            return;
        }
        this.mAuthOperations.remove(Integer.valueOf(i2));
        if (this.mIsAuthenticating) {
            endAuthSession();
        }
    }

    public String toString() {
        return this.mRingBuffer + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + this.mMultiBiometricLockoutState;
    }

    public class RingBuffer {
        public int mApiCallNumber;
        public final String[] mApiCalls;
        public int mCurr;
        public final int mSize;

        public RingBuffer(int i) {
            if (i <= 0) {
                Slog.wtf("AuthSessionCoordinator", "Cannot initialize ring buffer of size: " + i);
            }
            this.mApiCalls = new String[i];
            this.mCurr = 0;
            this.mSize = i;
            this.mApiCallNumber = 0;
        }

        public void addApiCall(String str) {
            String[] strArr = this.mApiCalls;
            int i = this.mCurr;
            strArr[i] = str;
            int i2 = i + 1;
            this.mCurr = i2;
            this.mCurr = i2 % this.mSize;
            this.mApiCallNumber++;
        }

        public String toString() {
            int i = this.mApiCallNumber;
            int i2 = this.mSize;
            int i3 = 0;
            int i4 = i > i2 ? i - i2 : 0;
            String str = "";
            while (true) {
                int i5 = this.mSize;
                if (i3 >= i5) {
                    return str;
                }
                int i6 = (this.mCurr + i3) % i5;
                if (this.mApiCalls[i6] != null) {
                    str = str + String.format("#%-5d %s\n", Integer.valueOf(i4), this.mApiCalls[i6]);
                    i4++;
                }
                i3++;
            }
        }
    }
}
