package com.android.server.biometrics.sensors;

import android.hardware.biometrics.BiometricManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.Slog;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class MultiBiometricLockoutState {
    public final Map mCanUserAuthenticate = new HashMap();
    public final Clock mClock;

    public MultiBiometricLockoutState(Clock clock) {
        this.mClock = clock;
    }

    public final Map createUnlockedMap() {
        HashMap hashMap = new HashMap();
        hashMap.put(15, new AuthenticatorState(15, false, false));
        Integer valueOf = Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        hashMap.put(valueOf, new AuthenticatorState(valueOf, false, false));
        hashMap.put(4095, new AuthenticatorState(4095, false, false));
        return hashMap;
    }

    public final Map getAuthMapForUser(int i) {
        if (!this.mCanUserAuthenticate.containsKey(Integer.valueOf(i))) {
            this.mCanUserAuthenticate.put(Integer.valueOf(i), createUnlockedMap());
        }
        return (Map) this.mCanUserAuthenticate.get(Integer.valueOf(i));
    }

    public void setPermanentLockOut(int i, int i2) {
        Map authMapForUser = getAuthMapForUser(i);
        if (i2 == 15) {
            ((AuthenticatorState) authMapForUser.get(15)).mPermanentlyLockedOut = true;
        } else if (i2 != 255) {
            if (i2 != 4095) {
                Slog.e("MultiBiometricLockoutState", "increaseLockoutTime called for invalid strength : " + i2);
                return;
            }
            ((AuthenticatorState) authMapForUser.get(4095)).mPermanentlyLockedOut = true;
        }
        ((AuthenticatorState) authMapForUser.get(Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT))).mPermanentlyLockedOut = true;
        ((AuthenticatorState) authMapForUser.get(4095)).mPermanentlyLockedOut = true;
    }

    public void clearPermanentLockOut(int i, int i2) {
        Map authMapForUser = getAuthMapForUser(i);
        if (i2 == 15) {
            ((AuthenticatorState) authMapForUser.get(15)).mPermanentlyLockedOut = false;
        } else if (i2 != 255) {
            if (i2 != 4095) {
                Slog.e("MultiBiometricLockoutState", "increaseLockoutTime called for invalid strength : " + i2);
                return;
            }
            ((AuthenticatorState) authMapForUser.get(4095)).mPermanentlyLockedOut = false;
        }
        ((AuthenticatorState) authMapForUser.get(Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT))).mPermanentlyLockedOut = false;
        ((AuthenticatorState) authMapForUser.get(4095)).mPermanentlyLockedOut = false;
    }

    public void setTimedLockout(int i, int i2) {
        Map authMapForUser = getAuthMapForUser(i);
        if (i2 == 15) {
            ((AuthenticatorState) authMapForUser.get(15)).mTimedLockout = true;
        } else if (i2 != 255) {
            if (i2 != 4095) {
                Slog.e("MultiBiometricLockoutState", "increaseLockoutTime called for invalid strength : " + i2);
                return;
            }
            ((AuthenticatorState) authMapForUser.get(4095)).mTimedLockout = true;
        }
        ((AuthenticatorState) authMapForUser.get(Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT))).mTimedLockout = true;
        ((AuthenticatorState) authMapForUser.get(4095)).mTimedLockout = true;
    }

    public void clearTimedLockout(int i, int i2) {
        Map authMapForUser = getAuthMapForUser(i);
        if (i2 == 15) {
            ((AuthenticatorState) authMapForUser.get(15)).mTimedLockout = false;
        } else if (i2 != 255) {
            if (i2 != 4095) {
                Slog.e("MultiBiometricLockoutState", "increaseLockoutTime called for invalid strength : " + i2);
                return;
            }
            ((AuthenticatorState) authMapForUser.get(4095)).mTimedLockout = false;
        }
        ((AuthenticatorState) authMapForUser.get(Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT))).mTimedLockout = false;
        ((AuthenticatorState) authMapForUser.get(4095)).mTimedLockout = false;
    }

    public int getLockoutState(int i, int i2) {
        Map authMapForUser = getAuthMapForUser(i);
        if (!authMapForUser.containsKey(Integer.valueOf(i2))) {
            Slog.e("MultiBiometricLockoutState", "Error, getLockoutState for unknown strength: " + i2 + " returning LOCKOUT_NONE");
            return 0;
        }
        AuthenticatorState authenticatorState = (AuthenticatorState) authMapForUser.get(Integer.valueOf(i2));
        if (authenticatorState.mPermanentlyLockedOut) {
            return 2;
        }
        return authenticatorState.mTimedLockout ? 1 : 0;
    }

    public String toString() {
        final long millis = this.mClock.millis();
        String str = "Permanent Lockouts\n";
        for (Map.Entry entry : this.mCanUserAuthenticate.entrySet()) {
            str = str + "UserId=" + ((Integer) entry.getKey()).intValue() + ", {" + ((String) ((Map) entry.getValue()).entrySet().stream().map(new Function() { // from class: com.android.server.biometrics.sensors.MultiBiometricLockoutState$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    String lambda$toString$0;
                    lambda$toString$0 = MultiBiometricLockoutState.lambda$toString$0(millis, (Map.Entry) obj);
                    return lambda$toString$0;
                }
            }).collect(Collectors.joining(", "))) + "}\n";
        }
        return str;
    }

    public static /* synthetic */ String lambda$toString$0(long j, Map.Entry entry) {
        return ((AuthenticatorState) entry.getValue()).toString(j);
    }

    public class AuthenticatorState {
        public Integer mAuthenticatorType;
        public boolean mPermanentlyLockedOut;
        public boolean mTimedLockout;

        public AuthenticatorState(Integer num, boolean z, boolean z2) {
            this.mAuthenticatorType = num;
            this.mPermanentlyLockedOut = z;
            this.mTimedLockout = z2;
        }

        public String toString(long j) {
            return String.format("(%s, permanentLockout=%s, timedLockout=%s)", BiometricManager.authenticatorToStr(this.mAuthenticatorType.intValue()), this.mPermanentlyLockedOut ? "true" : "false", this.mTimedLockout ? "true" : "false");
        }
    }
}
