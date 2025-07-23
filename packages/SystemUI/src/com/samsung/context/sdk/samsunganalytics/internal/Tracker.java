package com.samsung.context.sdk.samsunganalytics.internal;

import android.app.Application;
import android.content.Context;
import android.os.Trace;
import android.provider.Settings;
import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.samsung.context.sdk.samsunganalytics.internal.util.DeleteApiCallChecker;
import com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient;
import com.sec.android.diagmonagent.common.util.executor.SingleThreadExecutor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Tracker {
    public final Application application;
    public final Configuration configuration;
    public final DeleteApiCallChecker deleteApiCallChecker;
    public final Context mContext;
    public int mStatus = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.samsung.context.sdk.samsunganalytics.internal.Tracker$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final boolean isAgreement() {
            return Settings.System.getInt(Tracker.this.mContext.getContentResolver(), "samsung_errorlog_agree", 0) == 1;
        }
    }

    public Tracker(final Application application, final Configuration configuration) {
        Trace.beginSection("Tracker Constructor");
        this.application = application;
        this.configuration = configuration;
        Context applicationContext = application.getApplicationContext();
        this.mContext = applicationContext;
        this.deleteApiCallChecker = new DeleteApiCallChecker(applicationContext);
        configuration.getClass();
        configuration.userAgreement = new AnonymousClass1();
        Trace.beginAsyncSection("Tracker Constructor SingleThreadExecutor", -757204973);
        SingleThreadExecutor.getInstance().execute(new AsyncTaskClient() { // from class: com.samsung.context.sdk.samsunganalytics.internal.Tracker.2
            @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
            public final int onFinish() {
                return 0;
            }

            /* JADX WARN: Removed duplicated region for block: B:10:0x0086 A[LOOP:0: B:8:0x007e->B:10:0x0086, LOOP_END] */
            /* JADX WARN: Removed duplicated region for block: B:11:0x00c2 A[EDGE_INSN: B:11:0x00c2->B:12:0x00c2 BREAK  A[LOOP:0: B:8:0x007e->B:10:0x0086], SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:14:0x00c8  */
            @Override // com.sec.android.diagmonagent.common.util.executor.AsyncTaskClient
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 363
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.context.sdk.samsunganalytics.internal.Tracker.AnonymousClass2.run():void");
            }
        });
        Trace.endAsyncSection("Tracker Constructor SingleThreadExecutor", -757204973);
        Debug.LogI("Tracker start:6.05.073");
        Trace.endSection();
    }

    public static boolean access$100(Tracker tracker) {
        synchronized (tracker) {
            boolean z = false;
            if (-1 == tracker.mStatus) {
                Debug.LogD("Tracker is not initialized, status : " + tracker.mStatus);
                return false;
            }
            if (1 == tracker.init() && tracker.deleteApiCallChecker.isNotOverLimit()) {
                z = true;
            }
            return z;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0113  */
    /* JADX WARN: Type inference failed for: r9v6, types: [com.samsung.context.sdk.samsunganalytics.internal.Tracker$7] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int init() {
        /*
            Method dump skipped, instructions count: 543
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.context.sdk.samsunganalytics.internal.Tracker.init():int");
    }
}
