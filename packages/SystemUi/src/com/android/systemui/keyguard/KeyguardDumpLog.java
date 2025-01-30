package com.android.systemui.keyguard;

import android.support.v4.media.AbstractC0000x2c234b15;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardDumpLog {
    public static final KeyguardDumpLog INSTANCE = new KeyguardDumpLog();
    public static final String[] STATE_MSG = {"keyguardGoingAway", "setLockScreenShown", "externalEnabled", "screen_toggled", "occluded"};
    public static SamsungServiceLogger logger;

    private KeyguardDumpLog() {
    }

    public static final void log(String str, LogLevel logLevel, String str2, Throwable th) {
        if (th != null) {
            str2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str2, " ", th.getMessage());
        }
        SamsungServiceLogger samsungServiceLogger = logger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).logWithThreadId(str, logLevel, str2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x002c, code lost:
    
        if (r3 != 4) goto L41;
     */
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
        String str = " ";
        if (i == 0) {
            str = " " + (z ? Boolean.valueOf(z) : "failed");
        } else if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    str = AbstractC0000x2c234b15.m0m(" ", i2);
                    if (i2 == 0 || i2 == 1) {
                        str = str + " why:" + i3;
                    }
                }
            }
            str = AbstractC0866xb1ce8deb.m86m(" ", z);
        } else {
            str = " " + z2 + " " + z3;
            if (!z) {
                str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, " failed");
            }
        }
        SamsungServiceLogger samsungServiceLogger = logger;
        if (samsungServiceLogger != null) {
            ((SamsungServiceLoggerImpl) samsungServiceLogger).logWithThreadId(STATE_MSG[i], LogLevel.DEBUG, str);
        }
    }
}
