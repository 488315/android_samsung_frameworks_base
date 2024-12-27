package com.android.server.location.injector;

import android.app.AlarmManager;
import android.content.Context;
import android.os.SystemClock;

import com.android.internal.util.Preconditions;
import com.android.server.location.LocationServiceThread;

import java.util.Objects;

public final class SystemAlarmHelper {
    public final Context mContext;

    public SystemAlarmHelper(Context context) {
        this.mContext = context;
    }

    public final void cancel(AlarmManager.OnAlarmListener onAlarmListener) {
        AlarmManager alarmManager =
                (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        Objects.requireNonNull(alarmManager);
        alarmManager.cancel(onAlarmListener);
    }

    public final void setDelayedAlarm(long j, AlarmManager.OnAlarmListener onAlarmListener) {
        Preconditions.checkArgument(j > 0);
        AlarmManager alarmManager =
                (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        Objects.requireNonNull(alarmManager);
        alarmManager.set(
                2,
                SystemClock.elapsedRealtime() + j,
                0L,
                0L,
                onAlarmListener,
                LocationServiceThread.getHandler(),
                null);
    }
}
