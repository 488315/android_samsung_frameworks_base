package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class DeleteApiCallChecker {
    public int apiCallCount;
    public final Context context;
    public long lastResetTimeMs;

    public DeleteApiCallChecker(Context context) {
        this.context = context;
    }

    public final boolean isNotOverLimit() {
        SharedPreferences preferences = Preferences.getPreferences(this.context);
        if (this.lastResetTimeMs == 0) {
            this.lastResetTimeMs = preferences.getLong("deleteCountResetTime", 0L);
            this.apiCallCount = preferences.getInt("deleteCount", 0);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (TimeUnit.DAYS.toMillis(1L) + this.lastResetTimeMs >= jCurrentTimeMillis) {
            boolean z = this.apiCallCount < 5;
            if (!z) {
                Debug.LogI("SDK operation was stopped for 24 hours due to excessive delete API calls");
            }
            return z;
        }
        Debug.LogI("Initialize delete api call counting");
        this.lastResetTimeMs = jCurrentTimeMillis;
        this.apiCallCount = 0;
        SharedPreferences.Editor editorEdit = preferences.edit();
        editorEdit.putInt("deleteCount", this.apiCallCount);
        editorEdit.putLong("deleteCountResetTime", this.lastResetTimeMs).apply();
        return true;
    }
}
