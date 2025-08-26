package com.samsung.android.sdk.routines.v3.internal;

import android.os.Bundle;
import com.samsung.android.sdk.routines.v3.data.ActionResult;

/* loaded from: classes4.dex */
public abstract class Dispatcher {
    public static Bundle c$2() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExtraKey.RESULT_INT.a, ActionResult.ResultCode.FAIL_TIMEOUT.value);
        return bundle;
    }

    public abstract String a();

    /* JADX WARN: Removed duplicated region for block: B:18:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean a(Object obj) {
        long jCurrentTimeMillis;
        synchronized (obj) {
            try {
                jCurrentTimeMillis = System.currentTimeMillis();
            } catch (InterruptedException e) {
                e = e;
                jCurrentTimeMillis = 0;
            }
            try {
                obj.wait(7000L);
            } catch (InterruptedException e2) {
                e = e2;
                String methodName = new Throwable().getStackTrace()[1].getMethodName();
                Log.a(a(), "wait: " + methodName + " - " + e.getMessage());
                return !(System.currentTimeMillis() - jCurrentTimeMillis < 7000);
            }
        }
        return !(System.currentTimeMillis() - jCurrentTimeMillis < 7000);
    }
}
