package com.android.server.wm;

import android.util.EventLog;

/* loaded from: classes3.dex */
public abstract class EventLogTags {
  public static void writeWmFinishActivity(int i, int i2, int i3, String str, String str2) {
    EventLog.writeEvent(
        30001, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str, str2);
  }

  public static void writeWmTaskToFront(int i, int i2, int i3) {
    EventLog.writeEvent(30002, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
  }

  public static void writeWmCreateTask(int i, int i2, int i3, int i4, int i5) {
    EventLog.writeEvent(
        30004,
        Integer.valueOf(i),
        Integer.valueOf(i2),
        Integer.valueOf(i3),
        Integer.valueOf(i4),
        Integer.valueOf(i5));
  }

  public static void writeWmRestartActivity(int i, int i2, int i3, String str) {
    EventLog.writeEvent(30006, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str);
  }

  public static void writeWmResumeActivity(int i, int i2, int i3, String str) {
    EventLog.writeEvent(30007, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str);
  }

  public static void writeWmFailedToPause(int i, int i2, String str, String str2) {
    EventLog.writeEvent(30012, Integer.valueOf(i), Integer.valueOf(i2), str, str2);
  }

  public static void writeWmPauseActivity(int i, int i2, String str, String str2, String str3) {
    EventLog.writeEvent(30013, Integer.valueOf(i), Integer.valueOf(i2), str, str2, str3);
  }

  public static void writeWmDestroyActivity(int i, int i2, int i3, String str, String str2) {
    EventLog.writeEvent(
        30018, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str, str2);
  }

  public static void writeWmRelaunchResumeActivity(int i, int i2, int i3, String str, String str2) {
    EventLog.writeEvent(
        30019, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str, str2);
  }

  public static void writeWmRelaunchActivity(int i, int i2, int i3, String str, String str2) {
    EventLog.writeEvent(
        30020, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str, str2);
  }

  public static void writeWmSetResumedActivity(int i, String str, String str2) {
    EventLog.writeEvent(30043, Integer.valueOf(i), str, str2);
  }

  public static void writeWmFocusedRootTask(int i, int i2, int i3, int i4, String str) {
    EventLog.writeEvent(
        30044,
        Integer.valueOf(i),
        Integer.valueOf(i2),
        Integer.valueOf(i3),
        Integer.valueOf(i4),
        str);
  }

  public static void writeWmStopActivity(int i, int i2, String str) {
    EventLog.writeEvent(30048, Integer.valueOf(i), Integer.valueOf(i2), str);
  }

  public static void writeWmAddToStopping(int i, int i2, String str, String str2) {
    EventLog.writeEvent(30066, Integer.valueOf(i), Integer.valueOf(i2), str, str2);
  }

  public static void writeWmSetKeyguardShown(int i, int i2, int i3, int i4, int i5, String str) {
    EventLog.writeEvent(
        30067,
        Integer.valueOf(i),
        Integer.valueOf(i2),
        Integer.valueOf(i3),
        Integer.valueOf(i4),
        Integer.valueOf(i5),
        str);
  }

  public static void writeWmNoSurfaceMemory(String str, int i, String str2) {
    EventLog.writeEvent(31000, str, Integer.valueOf(i), str2);
  }

  public static void writeWmTaskCreated(int i) {
    EventLog.writeEvent(31001, i);
  }

  public static void writeWmTaskMoved(int i, int i2, int i3, int i4, int i5) {
    EventLog.writeEvent(
        31002,
        Integer.valueOf(i),
        Integer.valueOf(i2),
        Integer.valueOf(i3),
        Integer.valueOf(i4),
        Integer.valueOf(i5));
  }

  public static void writeWmTaskRemoved(int i, int i2, int i3, String str, int i4) {
    EventLog.writeEvent(
        31003,
        Integer.valueOf(i),
        Integer.valueOf(i2),
        Integer.valueOf(i3),
        str,
        Integer.valueOf(i4));
  }

  public static void writeWmSetRequestedOrientation(int i, String str) {
    EventLog.writeEvent(31006, Integer.valueOf(i), str);
  }

  public static void writeWmBootAnimationDone(long j) {
    EventLog.writeEvent(31007, j);
  }

  public static void writeWmSetKeyguardOccluded(int i, int i2, int i3, String str) {
    EventLog.writeEvent(31008, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), str);
  }

  public static void writeWmBackNaviCanceled(String str) {
    EventLog.writeEvent(31100, str);
  }

  public static void writeWmRequestedOrientation(int i, String str) {
    EventLog.writeEvent(31900, Integer.valueOf(i), str);
  }

  public static void writeWmUserRotationChanged(int i, int i2, int i3) {
    EventLog.writeEvent(31901, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
  }

  public static void writeWmTaskWindowingModeChanged(int i, int i2, int i3) {
    EventLog.writeEvent(31902, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
  }

  public static void writeWmEnterPip(int i, int i2, String str, String str2) {
    EventLog.writeEvent(38000, Integer.valueOf(i), Integer.valueOf(i2), str, str2);
  }

  public static void writeWmSleepToken(int i, int i2, String str) {
    EventLog.writeEvent(1000201, Integer.valueOf(i), Integer.valueOf(i2), str);
  }

  public static void writeWmOccludedChanged(int i, int i2) {
    EventLog.writeEvent(1000202, Integer.valueOf(i), Integer.valueOf(i2));
  }

  public static void writeWmIdleActivity(int i, int i2, String str) {
    EventLog.writeEvent(1000203, Integer.valueOf(i), Integer.valueOf(i2), str);
  }

  public static void writeWmSetShowWhenLocked(int i, int i2, String str, int i3) {
    EventLog.writeEvent(1000205, Integer.valueOf(i), Integer.valueOf(i2), str, Integer.valueOf(i3));
  }
}
