package com.android.systemui.keyguard;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;

/* loaded from: classes2.dex */
public final class KeyguardDumpLog {
    public static final KeyguardDumpLog INSTANCE = new KeyguardDumpLog();
    public static final String[] STATE_MSG = {"keyguardGoingAway", "setLockScreenShown", "externalEnabled", "screen_toggled", "occluded"};
    public static SamsungServiceLogger logger;

    private KeyguardDumpLog() {
    }

    public static final void log(String str, LogLevel logLevel, String str2, Throwable th) {
        if (th != null) {
            str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, " ", th.getMessage());
        }
        SamsungServiceLogger samsungServiceLogger = logger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).logWithThreadId(str, logLevel, str2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void state$default(KeyguardDumpLog keyguardDumpLog, int i, boolean z, boolean z2, boolean z3, int i2, int i3, int i4) {
        if ((i4 & 2) != 0) {
            z = false;
        }
        if ((i4 & 4) != 0) {
            z2 = false;
        }
        if ((i4 & 8) != 0) {
            z3 = false;
        }
        if ((i4 & 16) != 0) {
            i2 = -1;
        }
        if ((i4 & 32) != 0) {
            i3 = -1;
        }
        keyguardDumpLog.getClass();
        String strM = " ";
        if (i == 0) {
            strM = " " + (z ? Boolean.valueOf(z) : "failed");
        } else if (i == 1) {
            strM = " " + z2 + " " + z3;
            if (!z) {
                strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, " failed");
            }
        } else if (i == 2) {
            strM = KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m(" ", z);
        } else if (i == 3) {
            strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, " ");
            if (i2 == 0 || i2 == 1) {
                strM = strM + " why:" + i3;
            }
        } else if (i == 4) {
        }
        SamsungServiceLogger samsungServiceLogger = logger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).logWithThreadId(STATE_MSG[i], LogLevel.DEBUG, strM);
        }
    }
}
