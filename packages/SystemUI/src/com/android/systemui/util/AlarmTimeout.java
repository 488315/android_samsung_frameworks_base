package com.android.systemui.util;

import android.app.AlarmManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class AlarmTimeout implements AlarmManager.OnAlarmListener {
    public static final int MODE_IGNORE_IF_SCHEDULED = 1;
    public static final int MODE_RESCHEDULE_IF_SCHEDULED = 2;
    private final AlarmManager mAlarmManager;
    private final Handler mHandler;
    private final AlarmManager.OnAlarmListener mListener;
    private boolean mScheduled;
    private final String mTag;

    public AlarmTimeout(AlarmManager alarmManager, AlarmManager.OnAlarmListener onAlarmListener, String str, Handler handler) {
        this.mAlarmManager = alarmManager;
        this.mListener = onAlarmListener;
        this.mTag = str;
        this.mHandler = handler;
    }

    public void cancel() {
        if (this.mScheduled) {
            this.mAlarmManager.cancel(this);
            this.mScheduled = false;
        }
    }

    public boolean isScheduled() {
        return this.mScheduled;
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
        if (this.mScheduled) {
            this.mScheduled = false;
            this.mListener.onAlarm();
        }
    }

    public boolean schedule(long j, int i) {
        if (i != 1) {
            if (i != 2) {
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Illegal mode: "));
            }
            if (this.mScheduled) {
                cancel();
            }
        } else if (this.mScheduled) {
            return false;
        }
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + j, this.mTag, this, this.mHandler);
        this.mScheduled = true;
        return true;
    }
}
