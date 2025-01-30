package com.android.systemui.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.Dependency;
import com.android.systemui.p016qs.QSTileHost;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QsStatusEventLog {
    public static final Long SA_SEVEN_DAYS_IN_MILLISECONDS = 604800000L;
    public final Context mContext;
    public final Handler mHandler = new Handler((Looper) Dependency.get(Dependency.BG_LOOPER));
    public final QSTileHost mHost;

    public QsStatusEventLog(Context context, QSTileHost qSTileHost) {
        this.mContext = context;
        this.mHost = qSTileHost;
        Thread thread = new Thread(new QsStatusEventLog$$ExternalSyntheticLambda0(this));
        thread.setName("WeeklySALogging");
        thread.start();
    }
}
