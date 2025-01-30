package com.android.server.biometrics.sensors;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.ArrayMap;
import java.util.Collections;
import java.util.Map;
import java.util.function.IntFunction;

/* loaded from: classes.dex */
public class AuthResultCoordinator {
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
      this.mAuthenticatorState.put(
          15, (Integer) intFunction.apply(((Integer) this.mAuthenticatorState.get(15)).intValue()));
    } else if (i != 255) {
      if (i != 4095) {
        return;
      }
      this.mAuthenticatorState.put(
          4095,
          (Integer) intFunction.apply(((Integer) this.mAuthenticatorState.get(4095)).intValue()));
    }
    this.mAuthenticatorState.put(
        Integer.valueOf(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
        (Integer)
            intFunction.apply(
                ((Integer)
                        this.mAuthenticatorState.get(
                            Integer.valueOf(
                                IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)))
                    .intValue()));
    this.mAuthenticatorState.put(
        4095,
        (Integer) intFunction.apply(((Integer) this.mAuthenticatorState.get(4095)).intValue()));
  }

  public static /* synthetic */ Integer lambda$authenticatedFor$0(int i) {
    return Integer.valueOf(i | 4);
  }

  public void authenticatedFor(int i) {
    if (i == 15) {
      updateState(
          i,
          new IntFunction() { // from class:
                              // com.android.server.biometrics.sensors.AuthResultCoordinator$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
              Integer lambda$authenticatedFor$0;
              lambda$authenticatedFor$0 = AuthResultCoordinator.lambda$authenticatedFor$0(i2);
              return lambda$authenticatedFor$0;
            }
          });
    }
  }

  public static /* synthetic */ Integer lambda$lockedOutFor$1(int i) {
    return Integer.valueOf(i | 1);
  }

  public void lockedOutFor(int i) {
    updateState(
        i,
        new IntFunction() { // from class:
                            // com.android.server.biometrics.sensors.AuthResultCoordinator$$ExternalSyntheticLambda0
          @Override // java.util.function.IntFunction
          public final Object apply(int i2) {
            Integer lambda$lockedOutFor$1;
            lambda$lockedOutFor$1 = AuthResultCoordinator.lambda$lockedOutFor$1(i2);
            return lambda$lockedOutFor$1;
          }
        });
  }

  public static /* synthetic */ Integer lambda$lockOutTimed$2(int i) {
    return Integer.valueOf(i | 2);
  }

  public void lockOutTimed(int i) {
    updateState(
        i,
        new IntFunction() { // from class:
                            // com.android.server.biometrics.sensors.AuthResultCoordinator$$ExternalSyntheticLambda1
          @Override // java.util.function.IntFunction
          public final Object apply(int i2) {
            Integer lambda$lockOutTimed$2;
            lambda$lockOutTimed$2 = AuthResultCoordinator.lambda$lockOutTimed$2(i2);
            return lambda$lockOutTimed$2;
          }
        });
  }

  public final Map getResult() {
    return Collections.unmodifiableMap(this.mAuthenticatorState);
  }
}
