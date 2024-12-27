package com.android.server.biometrics.log;

import android.hardware.biometrics.common.AuthenticateReason;
import android.hardware.biometrics.common.OperationContext;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BiometricFrameworkStatsLogger {
    public static final BiometricFrameworkStatsLogger sInstance =
            new BiometricFrameworkStatsLogger();

    public static void authenticate(
            OperationContextExt operationContextExt,
            int i,
            int i2,
            boolean z,
            long j,
            int i3,
            boolean z2,
            int i4,
            float f) {
        boolean z3 = operationContextExt.mAidlContext.isCrypto;
        long sanitizeLatency = sanitizeLatency(j);
        OperationContext operationContext = operationContextExt.mAidlContext;
        int i5 = operationContext.id;
        byte b = operationContext.reason;
        int i6 = b == 1 ? 2 : b == 2 ? 1 : 0;
        boolean z4 = operationContext.isAod;
        boolean z5 = operationContextExt.mIsDisplayOn;
        int i7 = operationContextExt.mDockState;
        int orientationType = orientationType(operationContextExt.mOrientation);
        int i8 = operationContextExt.mFoldState;
        int i9 = i8 != 1 ? i8 != 2 ? i8 != 3 ? 0 : 2 : 1 : 3;
        BiometricContextSessionInfo biometricContextSessionInfo = operationContextExt.mSessionInfo;
        FrameworkStatsLog.write(
                88,
                i,
                i4,
                z3,
                i2,
                z2,
                i3,
                sanitizeLatency,
                z,
                -1,
                f,
                i5,
                i6,
                z4,
                z5,
                i7,
                orientationType,
                i9,
                biometricContextSessionInfo != null
                        ? biometricContextSessionInfo.mOrder.getAndIncrement()
                        : -1,
                toProtoWakeReason(operationContextExt),
                toProtoWakeReasonDetails(operationContextExt),
                operationContextExt.mIsMandatoryBiometrics);
    }

    public static void error(
            OperationContextExt operationContextExt,
            int i,
            int i2,
            int i3,
            boolean z,
            long j,
            int i4,
            int i5,
            int i6) {
        boolean z2 = operationContextExt.mAidlContext.isCrypto;
        long sanitizeLatency = sanitizeLatency(j);
        OperationContext operationContext = operationContextExt.mAidlContext;
        int i7 = operationContext.id;
        byte b = operationContext.reason;
        int i8 = b == 1 ? 2 : b == 2 ? 1 : 0;
        boolean z3 = operationContext.isAod;
        boolean z4 = operationContextExt.mIsDisplayOn;
        int i9 = operationContextExt.mDockState;
        int orientationType = orientationType(operationContextExt.mOrientation);
        int i10 = operationContextExt.mFoldState;
        int i11 = i10 != 1 ? i10 != 2 ? i10 != 3 ? 0 : 2 : 1 : 3;
        BiometricContextSessionInfo biometricContextSessionInfo = operationContextExt.mSessionInfo;
        FrameworkStatsLog.write(
                89,
                i,
                i6,
                z2,
                i2,
                i3,
                i4,
                i5,
                z,
                sanitizeLatency,
                -1,
                i7,
                i8,
                z3,
                z4,
                i9,
                orientationType,
                i11,
                biometricContextSessionInfo != null
                        ? biometricContextSessionInfo.mOrder.getAndIncrement()
                        : -1,
                toProtoWakeReason(operationContextExt),
                toProtoWakeReasonDetails(operationContextExt),
                operationContextExt.mIsMandatoryBiometrics);
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

    public static long sanitizeLatency(long j) {
        if (j >= 0) {
            return j;
        }
        Slog.w("BiometricFrameworkStatsLogger", "found a negative latency : " + j);
        return -1L;
    }

    public static int toProtoWakeReason(OperationContextExt operationContextExt) {
        switch (operationContextExt.mAidlContext.wakeReason) {
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

    public static int[] toProtoWakeReasonDetails(OperationContextExt operationContextExt) {
        AuthenticateReason authenticateReason = operationContextExt.mAidlContext.authenticateReason;
        int i = 0;
        if (authenticateReason != null && authenticateReason.getTag() == 1) {
            switch (authenticateReason.getFaceAuthenticateReason()) {
                case 1:
                    i = 1;
                    break;
                case 2:
                    i = 2;
                    break;
                case 3:
                    i = 3;
                    break;
                case 4:
                    i = 4;
                    break;
                case 5:
                    i = 5;
                    break;
                case 6:
                    i = 6;
                    break;
                case 7:
                    i = 7;
                    break;
                case 8:
                    i = 8;
                    break;
                case 9:
                    i = 9;
                    break;
                case 10:
                    i = 10;
                    break;
            }
        }
        return Stream.of(Integer.valueOf(i))
                .mapToInt(new BiometricFrameworkStatsLogger$$ExternalSyntheticLambda0())
                .filter(new BiometricFrameworkStatsLogger$$ExternalSyntheticLambda1())
                .toArray();
    }
}
