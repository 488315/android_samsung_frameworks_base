package com.android.server.broadcastradio;

import android.text.TextUtils;
import android.util.LocalLog;
import android.util.Log;

import com.android.server.utils.Slogf;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RadioEventLogger {
    public final boolean mDebug;
    public final LocalLog mEventLogger = new LocalLog(25);
    public final String mTag;

    public RadioEventLogger(String str) {
        this.mTag = str;
        this.mDebug = Log.isLoggable(str, 3);
    }

    public final void logRadioEvent(String str, Object... objArr) {
        this.mEventLogger.log(TextUtils.formatSimple(str, objArr));
        if (this.mDebug) {
            Slogf.d(this.mTag, str, objArr);
        }
    }
}
