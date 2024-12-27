package com.android.server.am;

import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.Trace;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class OomAdjuster$$ExternalSyntheticLambda2 implements Handler.Callback {
    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.arg1;
        int i2 = message.arg2;
        if (i == ActivityManagerService.MY_PID) {
            return true;
        }
        boolean isTagEnabled = Trace.isTagEnabled(64L);
        if (isTagEnabled) {
            Trace.traceBegin(64L, "setProcessGroup " + message.obj + " to " + i2);
        }
        try {
            Process.setProcessGroup(i, i2);
            if (!isTagEnabled) {
                return true;
            }
        } catch (Exception unused) {
            if (!isTagEnabled) {
                return true;
            }
        } catch (Throwable th) {
            if (isTagEnabled) {
                Trace.traceEnd(64L);
            }
            throw th;
        }
        Trace.traceEnd(64L);
        return true;
    }
}
