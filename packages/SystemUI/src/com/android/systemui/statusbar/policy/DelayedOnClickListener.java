package com.android.systemui.statusbar.policy;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
