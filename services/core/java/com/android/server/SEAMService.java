package com.android.server;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;

import com.samsung.android.knox.seams.ISEAMS;
import com.samsung.android.knox.seams.SEAMSPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SEAMService extends ISEAMS.Stub {
    public static int SELF_PID;
    public static final SKLogger mSKLog = SKLogger.getLogger();
    public final Context mContext;
    public final Object mLock = new Object[0];
    public final Handler mSKHandler = new Handler();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BrHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {}
    }

    public SEAMService(Context context) {
        this.mContext = context;
        new BrHandler(
                KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("SEAMService").getLooper());
        new Thread() { // from class: com.android.server.SEAMService.1

            /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
            /* renamed from: com.android.server.SEAMService$1$1, reason: invalid class name and collision with other inner class name */
            public final class RunnableC00051 implements Runnable {
                @Override // java.lang.Runnable
                public final void run() {
                    ServiceKeeper.getServiceKeeper();
                    boolean authorizeLoadProcedure = ServiceKeeper.authorizeLoadProcedure();
                    if (SEAMSPolicy.DEBUG) {
                        SEAMService.mSKLog.getClass();
                        SKLogger.logAll(
                                "SEAMService",
                                "Service Keeper Initialized = " + authorizeLoadProcedure);
                    }
                }
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                SEAMService.this.mSKHandler.post(new RunnableC00051());
            }
        }.start();
        SELF_PID = Process.myPid();
    }

    public final int isAuthorized(int i, int i2, String str, String str2) {
        if (SELF_PID == i) {
            return 0;
        }
        ServiceKeeper.getServiceKeeper();
        if (ServiceKeeper.isActive) {
            return ServiceKeeper.isAuthorized(i, i2, this.mContext, str, str2);
        }
        if (SEAMSPolicy.DEBUG) {
            mSKLog.getClass();
            SKLogger.logAll("SEAMService", "Returning 0 directly as tables are not ready in SK.");
        }
        return 0;
    }
}
