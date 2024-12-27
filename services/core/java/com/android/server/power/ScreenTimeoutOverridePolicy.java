package com.android.server.power;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;

import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

final class ScreenTimeoutOverridePolicy {
    public int mLastAutoReleaseReason;
    public PowerManagerService$$ExternalSyntheticLambda14 mPolicyCallback;
    public long mScreenTimeoutOverrideConfig;

    public final void dump(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println();
        indentingPrintWriter.println("ScreenTimeoutOverridePolicy:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(
                "mScreenTimeoutOverrideConfig=" + this.mScreenTimeoutOverrideConfig);
        indentingPrintWriter.println("mLastAutoReleaseReason=" + this.mLastAutoReleaseReason);
    }

    public final void releaseAllWakeLocks(int i) {
        PowerManagerService powerManagerService = this.mPolicyCallback.f$0;
        Handler handler = powerManagerService.mHandler;
        Message obtainMessage = handler.obtainMessage(6);
        obtainMessage.arg1 = i;
        powerManagerService.mClock.getClass();
        handler.sendMessageAtTime(obtainMessage, SystemClock.uptimeMillis());
        this.mLastAutoReleaseReason = i;
        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                new StringBuilder("Releasing all screen timeout override wake lock. (reason="),
                this.mLastAutoReleaseReason,
                ")",
                "ScreenTimeoutOverridePolicy");
    }
}
