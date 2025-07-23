package com.android.systemui.statusbar.policy;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DelayedOnClickListener implements View.OnClickListener {
    public final View.OnClickListener mActualListener;
    public final long mInitDelayMs;
    public final long mInitTimeMs = SystemClock.elapsedRealtime();

    public DelayedOnClickListener(View.OnClickListener onClickListener, long j) {
        this.mActualListener = onClickListener;
        this.mInitDelayMs = j;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.mInitTimeMs;
        long j2 = this.mInitDelayMs;
        if (elapsedRealtime >= j + j2) {
            this.mActualListener.onClick(view);
            return;
        }
        Log.i("SmartReplyViewInflater", "Accidental Smart Suggestion click registered, delay: " + j2);
    }
}
