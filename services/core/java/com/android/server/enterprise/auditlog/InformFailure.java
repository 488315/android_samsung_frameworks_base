package com.android.server.enterprise.auditlog;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;

/* loaded from: classes2.dex */
public class InformFailure {
    public static InformFailure mInformFailure;
    public Context mContext;

    public static synchronized InformFailure getInstance() {
        InformFailure informFailure;
        synchronized (InformFailure.class) {
            if (mInformFailure == null) {
                mInformFailure = new InformFailure();
            }
            informFailure = mInformFailure;
        }
        return informFailure;
    }

    public synchronized void broadcastFailure(Exception exc, String str) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.LOG_EXCEPTION");
        intent.putExtra("AUDIT_LOG_EXCEPTION", exc.toString());
        if (str != null) {
            intent.setPackage(str);
        }
        if (this.mContext != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            this.mContext.sendBroadcast(intent, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public synchronized void broadcastFailure(String str, String str2) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.LOG_EXCEPTION");
        intent.putExtra("AUDIT_LOG_EXCEPTION", str);
        if (str2 != null) {
            intent.setPackage(str2);
        }
        if (this.mContext != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            this.mContext.sendBroadcast(intent, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public synchronized void setContext(Context context) {
        this.mContext = context;
    }
}
