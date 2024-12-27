package com.android.systemui.statusbar.policy;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;

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
