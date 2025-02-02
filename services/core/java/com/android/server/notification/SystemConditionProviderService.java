package com.android.server.notification;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.service.notification.ConditionProviderService;
import android.service.notification.IConditionProvider;
import android.util.TimeUtils;
import java.io.PrintWriter;
import java.util.Date;

/* loaded from: classes2.dex */
public abstract class SystemConditionProviderService extends ConditionProviderService {
  public abstract IConditionProvider asInterface();

  public abstract void attachBase(Context context);

  public abstract void dump(
      PrintWriter printWriter, NotificationManagerService.DumpFilter dumpFilter);

  public abstract ComponentName getComponent();

  public abstract boolean isScheduleEnabled();

  public abstract boolean isValidConditionId(Uri uri);

  public abstract void onBootComplete();

  public abstract void onScheduleEnabled(boolean z);

  /* renamed from: ts */
  public static String m51ts(long j) {
    return new Date(j) + " (" + j + ")";
  }

  public static String formatDuration(long j) {
    StringBuilder sb = new StringBuilder();
    TimeUtils.formatDuration(j, sb);
    return sb.toString();
  }

  public static void dumpUpcomingTime(PrintWriter printWriter, String str, long j, long j2) {
    printWriter.print("      ");
    printWriter.print(str);
    printWriter.print('=');
    if (j > 0) {
      printWriter.printf("%s, in %s, now=%s", m51ts(j), formatDuration(j - j2), m51ts(j2));
    } else {
      printWriter.print(j);
    }
    printWriter.println();
  }
}
