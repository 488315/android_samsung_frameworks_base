package com.android.server.biometrics.sensors;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MultiBiometricLockoutState {
    public final Map mCanUserAuthenticate = new HashMap();
    public final Clock mClock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthenticatorState {
        public final Integer mAuthenticatorType;
        public boolean mPermanentlyLockedOut = false;
        public boolean mTimedLockout = false;

        public AuthenticatorState(Integer num) {
            this.mAuthenticatorType = num;
        }
    }

    public MultiBiometricLockoutState(Clock clock) {
        this.mClock = clock;
    }

    public final void clearPermanentLockOut(int i, int i2) {
        Map authMapForUser = getAuthMapForUser(i);
        if (i2 == 15) {
            ((AuthenticatorState) authMapForUser.get(15)).mPermanentlyLockedOut = false;
        } else if (i2 != 255) {
            if (i2 != 4095) {
                NandswapManager$$ExternalSyntheticOutline0.m(i2, "increaseLockoutTime called for invalid strength : ", "MultiBiometricLockoutState");
                return;
            }
            ((AuthenticatorState) authMapForUser.get(4095)).mPermanentlyLockedOut = false;
        }
        ((AuthenticatorState) authMapForUser.get(Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT))).mPermanentlyLockedOut = false;
        ((AuthenticatorState) authMapForUser.get(4095)).mPermanentlyLockedOut = false;
    }

    public final void clearTimedLockout(int i, int i2) {
        Map authMapForUser = getAuthMapForUser(i);
        if (i2 == 15) {
            ((AuthenticatorState) authMapForUser.get(15)).mTimedLockout = false;
        } else if (i2 != 255) {
            if (i2 != 4095) {
                NandswapManager$$ExternalSyntheticOutline0.m(i2, "increaseLockoutTime called for invalid strength : ", "MultiBiometricLockoutState");
                return;
            }
            ((AuthenticatorState) authMapForUser.get(4095)).mTimedLockout = false;
        }
        ((AuthenticatorState) authMapForUser.get(Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT))).mTimedLockout = false;
        ((AuthenticatorState) authMapForUser.get(4095)).mTimedLockout = false;
    }

    public final Map getAuthMapForUser(int i) {
        if (!((HashMap) this.mCanUserAuthenticate).containsKey(Integer.valueOf(i))) {
            Map map = this.mCanUserAuthenticate;
            Integer valueOf = Integer.valueOf(i);
            HashMap hashMap = new HashMap();
            hashMap.put(15, new AuthenticatorState(15));
            Integer valueOf2 = Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            hashMap.put(valueOf2, new AuthenticatorState(valueOf2));
            hashMap.put(4095, new AuthenticatorState(4095));
            ((HashMap) map).put(valueOf, hashMap);
        }
        return (Map) ((HashMap) this.mCanUserAuthenticate).get(Integer.valueOf(i));
    }

    public final int getLockoutState(int i, int i2) {
        Map authMapForUser = getAuthMapForUser(i);
        if (!authMapForUser.containsKey(Integer.valueOf(i2))) {
            FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(i2, "Error, getLockoutState for unknown strength: ", " returning LOCKOUT_NONE", "MultiBiometricLockoutState");
            return 0;
        }
        AuthenticatorState authenticatorState = (AuthenticatorState) authMapForUser.get(Integer.valueOf(i2));
        if (authenticatorState.mPermanentlyLockedOut) {
            return 2;
        }
        return authenticatorState.mTimedLockout ? 1 : 0;
    }

    public final String toString() {
        this.mClock.millis();
        String str = "Permanent Lockouts\n";
        for (Map.Entry entry : ((HashMap) this.mCanUserAuthenticate).entrySet()) {
            str = str + "UserId=" + ((Integer) entry.getKey()).intValue() + ", {" + ((String) ((Map) entry.getValue()).entrySet().stream().map(new MultiBiometricLockoutState$$ExternalSyntheticLambda0()).collect(Collectors.joining(", "))) + "}\n";
        }
        return str;
    }
}
