package com.android.server.enterprise.auditlog;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InformFailure {
    public static InformFailure mInformFailure;
    public Context mContext;

    public static synchronized InformFailure getInstance() {
        InformFailure informFailure;
        synchronized (InformFailure.class) {
            try {
                if (mInformFailure == null) {
                    mInformFailure = new InformFailure();
                }
                informFailure = mInformFailure;
            } catch (Throwable th) {
                throw th;
            }
        }
        return informFailure;
    }

    public final synchronized void broadcastFailure(Exception exc, String str) {
        try {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.LOG_EXCEPTION");
            intent.putExtra("AUDIT_LOG_EXCEPTION", exc.toString());
            if (str != null) {
                intent.setPackage(str);
            }
            if (this.mContext != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                this.mContext.sendBroadcast(
                        intent, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void broadcastFailure(String str, String str2) {
        try {
            Intent intent = new Intent("com.samsung.android.knox.intent.action.LOG_EXCEPTION");
            intent.putExtra("AUDIT_LOG_EXCEPTION", str);
            if (str2 != null) {
                intent.setPackage(str2);
            }
            if (this.mContext != null) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                this.mContext.sendBroadcast(
                        intent, "com.samsung.android.knox.permission.KNOX_AUDIT_LOG");
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
