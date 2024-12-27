package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyManagerLiteInternal;
import android.app.admin.DevicePolicySafetyChecker;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;

import com.android.internal.os.IResultReceiver;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OneTimeSafetyChecker implements DevicePolicySafetyChecker {
    public boolean mDone;
    public final int mOperation;
    public final DevicePolicySafetyChecker mRealSafetyChecker;
    public final int mReason;
    public final DevicePolicyManagerService mService;

    public OneTimeSafetyChecker(
            DevicePolicyManagerService devicePolicyManagerService, int i, int i2) {
        Handler handler = new Handler(Looper.getMainLooper());
        Objects.requireNonNull(devicePolicyManagerService);
        this.mService = devicePolicyManagerService;
        this.mOperation = i;
        this.mReason = i2;
        DevicePolicySafetyChecker devicePolicySafetyChecker =
                devicePolicyManagerService.mSafetyChecker;
        this.mRealSafetyChecker = devicePolicySafetyChecker;
        Slog.i(
                "OneTimeSafetyChecker",
                "OneTimeSafetyChecker constructor: operation="
                        + DevicePolicyManager.operationToString(i)
                        + ", reason="
                        + DevicePolicyManager.operationSafetyReasonToString(i2)
                        + ", realChecker="
                        + devicePolicySafetyChecker
                        + ", maxDuration=10000ms");
        handler.postDelayed(
                new Runnable() { // from class:
                                 // com.android.server.devicepolicy.OneTimeSafetyChecker$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OneTimeSafetyChecker oneTimeSafetyChecker = OneTimeSafetyChecker.this;
                        if (oneTimeSafetyChecker.mDone) {
                            return;
                        }
                        Slog.e(
                                "OneTimeSafetyChecker",
                                "Self destructing "
                                        + oneTimeSafetyChecker
                                        + ", as it was not automatically disabled");
                        oneTimeSafetyChecker.disableSelf();
                    }
                },
                10000L);
    }

    public final void disableSelf() {
        if (this.mDone) {
            Slog.w("OneTimeSafetyChecker", "disableSelf(): already disabled");
            return;
        }
        Slog.i(
                "OneTimeSafetyChecker",
                "restoring DevicePolicySafetyChecker to " + this.mRealSafetyChecker);
        this.mService.setDevicePolicySafetyCheckerUnchecked(this.mRealSafetyChecker);
        this.mDone = true;
    }

    public final int getUnsafeOperationReason(int i) {
        int i2;
        String operationToString = DevicePolicyManager.operationToString(i);
        BootReceiver$$ExternalSyntheticOutline0.m58m(
                "getUnsafeOperationReason(", operationToString, ")", "OneTimeSafetyChecker");
        if (i == this.mOperation) {
            i2 = this.mReason;
        } else {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "invalid call to isDevicePolicyOperationSafe(): asked for ",
                            operationToString,
                            ", should be ");
            m.append(DevicePolicyManager.operationToString(this.mOperation));
            Slog.wtf("OneTimeSafetyChecker", m.toString());
            i2 = -1;
        }
        String operationSafetyReasonToString =
                DevicePolicyManager.operationSafetyReasonToString(i2);
        DevicePolicyManagerLiteInternal devicePolicyManagerLiteInternal =
                (DevicePolicyManagerLiteInternal)
                        LocalServices.getService(DevicePolicyManagerLiteInternal.class);
        BootReceiver$$ExternalSyntheticOutline0.m58m(
                "notifying ", operationSafetyReasonToString, " is UNSAFE", "OneTimeSafetyChecker");
        devicePolicyManagerLiteInternal.notifyUnsafeOperationStateChanged(this, i2, false);
        BootReceiver$$ExternalSyntheticOutline0.m59m(
                new StringBuilder("notifying "),
                operationSafetyReasonToString,
                " is SAFE",
                "OneTimeSafetyChecker");
        devicePolicyManagerLiteInternal.notifyUnsafeOperationStateChanged(this, i2, true);
        Slog.i("OneTimeSafetyChecker", "returning " + operationSafetyReasonToString);
        disableSelf();
        return i2;
    }

    public final boolean isSafeOperation(int i) {
        boolean z = this.mReason != i;
        Slog.i(
                "OneTimeSafetyChecker",
                "isSafeOperation("
                        + DevicePolicyManager.operationSafetyReasonToString(i)
                        + "): "
                        + z);
        disableSelf();
        return z;
    }

    public final void onFactoryReset(IResultReceiver iResultReceiver) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        return "OneTimeSafetyChecker[id="
                + System.identityHashCode(this)
                + ", reason="
                + DevicePolicyManager.operationSafetyReasonToString(this.mReason)
                + ", operation="
                + DevicePolicyManager.operationToString(this.mOperation)
                + ']';
    }
}
