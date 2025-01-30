package com.android.server.biometrics.log;

import android.hardware.biometrics.common.AuthenticateReason;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class BiometricFrameworkStatsLogger {
  public static final BiometricFrameworkStatsLogger sInstance = new BiometricFrameworkStatsLogger();

  public static int foldType(int i) {
    if (i == 1) {
      return 3;
    }
    if (i != 2) {
      return i != 3 ? 0 : 2;
    }
    return 1;
  }

  public static /* synthetic */ boolean lambda$toProtoWakeReasonDetails$2(int i) {
    return i != 0;
  }

  public static int orientationType(int i) {
    if (i == 0) {
      return 1;
    }
    if (i == 1) {
      return 2;
    }
    if (i != 2) {
      return i != 3 ? 0 : 4;
    }
    return 3;
  }

  public static int sessionType(byte b) {
    if (b == 1) {
      return 2;
    }
    return b == 2 ? 1 : 0;
  }

  public static int toProtoWakeReasonDetailsFromFace(int i) {
    switch (i) {
      case 1:
        return 1;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
        return 4;
      case 5:
        return 5;
      case 6:
        return 6;
      case 7:
        return 7;
      case 8:
        return 8;
      case 9:
        return 9;
      case 10:
        return 10;
      default:
        return 0;
    }
  }

  public static BiometricFrameworkStatsLogger getInstance() {
    return sInstance;
  }

  public void acquired(
      OperationContextExt operationContextExt,
      int i,
      int i2,
      int i3,
      boolean z,
      int i4,
      int i5,
      int i6) {
    FrameworkStatsLog.write(
        87,
        i,
        i6,
        operationContextExt.isCrypto(),
        i2,
        i3,
        i4,
        i5,
        z,
        -1,
        operationContextExt.getId(),
        sessionType(operationContextExt.getReason()),
        operationContextExt.isAod(),
        operationContextExt.isDisplayOn(),
        operationContextExt.getDockState(),
        orientationType(operationContextExt.getOrientation()),
        foldType(operationContextExt.getFoldState()),
        operationContextExt.getOrderAndIncrement(),
        toProtoWakeReason(operationContextExt));
  }

  public void authenticate(
      OperationContextExt operationContextExt,
      int i,
      int i2,
      int i3,
      boolean z,
      long j,
      int i4,
      boolean z2,
      int i5,
      float f) {
    FrameworkStatsLog.write(
        88,
        i,
        i5,
        operationContextExt.isCrypto(),
        i3,
        z2,
        i4,
        sanitizeLatency(j),
        z,
        -1,
        f,
        operationContextExt.getId(),
        sessionType(operationContextExt.getReason()),
        operationContextExt.isAod(),
        operationContextExt.isDisplayOn(),
        operationContextExt.getDockState(),
        orientationType(operationContextExt.getOrientation()),
        foldType(operationContextExt.getFoldState()),
        operationContextExt.getOrderAndIncrement(),
        toProtoWakeReason(operationContextExt),
        toProtoWakeReasonDetails(operationContextExt));
  }

  public void authenticate(
      final OperationContextExt operationContextExt,
      final int i,
      final int i2,
      final int i3,
      final boolean z,
      final long j,
      final int i4,
      final boolean z2,
      final int i5,
      ALSProbe aLSProbe) {
    aLSProbe.awaitNextLux(
        new Consumer() { // from class:
                         // com.android.server.biometrics.log.BiometricFrameworkStatsLogger$$ExternalSyntheticLambda2
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            BiometricFrameworkStatsLogger.this.lambda$authenticate$0(
                operationContextExt, i, i2, i3, z, j, i4, z2, i5, (Float) obj);
          }
        },
        null);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$authenticate$0(
      OperationContextExt operationContextExt,
      int i,
      int i2,
      int i3,
      boolean z,
      long j,
      int i4,
      boolean z2,
      int i5,
      Float f) {
    authenticate(operationContextExt, i, i2, i3, z, j, i4, z2, i5, f.floatValue());
  }

  public void enroll(int i, int i2, int i3, int i4, long j, boolean z, float f) {
    FrameworkStatsLog.write(184, i, i4, sanitizeLatency(j), z, -1, f);
  }

  public void error(
      OperationContextExt operationContextExt,
      int i,
      int i2,
      int i3,
      boolean z,
      long j,
      int i4,
      int i5,
      int i6) {
    FrameworkStatsLog.write(
        89,
        i,
        i6,
        operationContextExt.isCrypto(),
        i2,
        i3,
        i4,
        i5,
        z,
        sanitizeLatency(j),
        -1,
        operationContextExt.getId(),
        sessionType(operationContextExt.getReason()),
        operationContextExt.isAod(),
        operationContextExt.isDisplayOn(),
        operationContextExt.getDockState(),
        orientationType(operationContextExt.getOrientation()),
        foldType(operationContextExt.getFoldState()),
        operationContextExt.getOrderAndIncrement(),
        toProtoWakeReason(operationContextExt),
        toProtoWakeReasonDetails(operationContextExt));
  }

  public static int[] toProtoWakeReasonDetails(OperationContextExt operationContextExt) {
    return Stream.of(
            Integer.valueOf(
                toProtoWakeReasonDetails(operationContextExt.toAidlContext().authenticateReason)))
        .mapToInt(
            new ToIntFunction() { // from class:
                                  // com.android.server.biometrics.log.BiometricFrameworkStatsLogger$$ExternalSyntheticLambda0
              @Override // java.util.function.ToIntFunction
              public final int applyAsInt(Object obj) {
                int intValue;
                intValue = ((Integer) obj).intValue();
                return intValue;
              }
            })
        .filter(
            new IntPredicate() { // from class:
                                 // com.android.server.biometrics.log.BiometricFrameworkStatsLogger$$ExternalSyntheticLambda1
              @Override // java.util.function.IntPredicate
              public final boolean test(int i) {
                boolean lambda$toProtoWakeReasonDetails$2;
                lambda$toProtoWakeReasonDetails$2 =
                    BiometricFrameworkStatsLogger.lambda$toProtoWakeReasonDetails$2(i);
                return lambda$toProtoWakeReasonDetails$2;
              }
            })
        .toArray();
  }

  public static int toProtoWakeReason(OperationContextExt operationContextExt) {
    switch (operationContextExt.getWakeReason()) {
      case 1:
        return 1;
      case 2:
        return 2;
      case 3:
        return 3;
      case 4:
        return 4;
      case 5:
        return 5;
      case 6:
        return 6;
      case 7:
        return 7;
      case 8:
        return 8;
      case 9:
        return 9;
      default:
        return 0;
    }
  }

  public static int toProtoWakeReasonDetails(AuthenticateReason authenticateReason) {
    if (authenticateReason == null || authenticateReason.getTag() != 1) {
      return 0;
    }
    return toProtoWakeReasonDetailsFromFace(authenticateReason.getFaceAuthenticateReason());
  }

  public void reportUnknownTemplateEnrolledHal(int i) {
    FrameworkStatsLog.write(148, i, 3, -1);
  }

  public void reportUnknownTemplateEnrolledFramework(int i) {
    FrameworkStatsLog.write(148, i, 2, -1);
  }

  public final long sanitizeLatency(long j) {
    if (j >= 0) {
      return j;
    }
    Slog.w("BiometricFrameworkStatsLogger", "found a negative latency : " + j);
    return -1L;
  }
}
