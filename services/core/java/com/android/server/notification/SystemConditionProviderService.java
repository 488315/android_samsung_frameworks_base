package com.android.server.notification;

import android.content.ComponentName;
import android.content.Context;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.service.notification.ConditionProviderService;
import android.service.notification.IConditionProvider;
import android.util.TimeUtils;

import java.io.PrintWriter;
import java.util.Date;

public abstract class SystemConditionProviderService extends ConditionProviderService {
    public static void dumpUpcomingTime(PrintWriter printWriter, long j, long j2) {
        printWriter.print("      ");
        printWriter.print("mNextAlarmTime");
        printWriter.print('=');
        if (j > 0) {
            String ts = ts(j);
            StringBuilder sb = new StringBuilder();
            TimeUtils.formatDuration(j - j2, sb);
            printWriter.printf("%s, in %s, now=%s", ts, sb.toString(), ts(j2));
        } else {
            printWriter.print(j);
        }
        printWriter.println();
    }

    public static String ts(long j) {
        StringBuilder sb = new StringBuilder();
        sb.append(new Date(j));
        sb.append(" (");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, j, ")");
    }

    public abstract IConditionProvider asInterface();

    public abstract void attachBase(Context context);

    public abstract void dump(PrintWriter printWriter);

    public abstract ComponentName getComponent();

    public abstract boolean isScheduleEnabled();

    public abstract boolean isValidConditionId(Uri uri);

    public abstract void onBootComplete();

    public abstract void onScheduleEnabled(boolean z);
}
