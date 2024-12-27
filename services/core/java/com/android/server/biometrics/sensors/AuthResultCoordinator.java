package com.android.server.biometrics.sensors;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.ArrayMap;

import java.util.Map;
import java.util.function.IntFunction;

public final class AuthResultCoordinator {
    public final Map mAuthenticatorState;

    public AuthResultCoordinator() {
        ArrayMap arrayMap = new ArrayMap();
        this.mAuthenticatorState = arrayMap;
        arrayMap.put(15, 0);
        arrayMap.put(
                Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT), 0);
        arrayMap.put(4095, 0);
    }

    public final void updateState(int i, IntFunction intFunction) {
        if (i == 15) {
            ((ArrayMap) this.mAuthenticatorState)
                    .put(
                            15,
                            (Integer)
                                    intFunction.apply(
                                            ((Integer)
                                                            ((ArrayMap) this.mAuthenticatorState)
                                                                    .get(15))
                                                    .intValue()));
        } else if (i != 255) {
            if (i != 4095) {
                return;
            }
            ((ArrayMap) this.mAuthenticatorState)
                    .put(
                            4095,
                            (Integer)
                                    intFunction.apply(
                                            ((Integer)
                                                            ((ArrayMap) this.mAuthenticatorState)
                                                                    .get(4095))
                                                    .intValue()));
        }
        Map map = this.mAuthenticatorState;
        ArrayMap arrayMap = (ArrayMap) map;
        arrayMap.put(
                Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
                (Integer)
                        intFunction.apply(
                                ((Integer)
                                                ((ArrayMap) this.mAuthenticatorState)
                                                        .get(
                                                                Integer.valueOf(
                                                                        IDnsResolverUnsolicitedEventListener
                                                                                .DNS_HEALTH_RESULT_TIMEOUT)))
                                        .intValue()));
        ((ArrayMap) this.mAuthenticatorState)
                .put(
                        4095,
                        (Integer)
                                intFunction.apply(
                                        ((Integer) ((ArrayMap) this.mAuthenticatorState).get(4095))
                                                .intValue()));
    }
}
