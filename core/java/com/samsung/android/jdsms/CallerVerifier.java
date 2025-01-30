package com.samsung.android.jdsms;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;

/* loaded from: classes5.dex */
final class CallerVerifier {
  private static final String BASE_CLASS = "com.samsung.android.jdsms.Sender";
  private static final String BASE_METHOD = "send";
  private static final boolean CALLER_DEBUG;
  private static final String SUBTAG = "[CallPolicy] ";
  private static final CallerAllowList mAllowList = new CallerAllowList();

  CallerVerifier() {}

  static {
    DsmsLog.isDebuggable();
    CALLER_DEBUG = false;
  }

  final boolean wasCallerValid() {
    StackTraceElement callFrame = extractCaller();
    if (callFrame == null) {
      DsmsLog.m280e("[CallPolicy] DENY (caller frame not found)");
      return false;
    }
    String cannonCallerName = mountFrameCannonName(callFrame);
    if (!mAllowList.contains(cannonCallerName)) {
      DsmsLog.m280e(
          "[CallPolicy] DENY callerName ["
              + cannonCallerName
              + NavigationBarInflaterView.SIZE_MOD_END);
      return false;
    }
    DsmsLog.m278d(
        "[CallPolicy] ALLOW callerName ["
            + cannonCallerName
            + NavigationBarInflaterView.SIZE_MOD_END);
    return true;
  }

  private static StackTraceElement extractCaller() {
    StackTraceElement[] frames = Thread.currentThread().getStackTrace();
    if (frames == null) {
      DsmsLog.m280e("[CallPolicy] Null stack trace");
      return null;
    }
    if (CALLER_DEBUG) {
      DsmsLog.m278d(SUBTAG + String.format("Frames length: %d", Integer.valueOf(frames.length)));
    }
    Integer baseIndex = findBaseIndex(frames);
    if (baseIndex == null || baseIndex.intValue() + 1 >= frames.length) {
      DsmsLog.m280e("[CallPolicy] Impossible to reach caller");
      return null;
    }
    return frames[baseIndex.intValue() + 1];
  }

  private static Integer findBaseIndex(StackTraceElement[] frames) {
    if (CALLER_DEBUG) {
      DsmsLog.m278d(
          SUBTAG + String.format("Frames length Inside: %d", Integer.valueOf(frames.length)));
    }
    for (int index = 0; index < frames.length; index++) {
      StackTraceElement frame = frames[index];
      if (CALLER_DEBUG) {
        DsmsLog.m278d(
            SUBTAG
                + String.format(
                    "Frame#%d/%d: %s %s",
                    Integer.valueOf(index),
                    Integer.valueOf(frames.length),
                    frame.getClassName(),
                    frame.getMethodName()));
      }
      if (BASE_CLASS.equals(frame.getClassName()) && BASE_METHOD.equals(frame.getMethodName())) {
        return Integer.valueOf(index);
      }
    }
    return null;
  }

  private static String mountFrameCannonName(StackTraceElement frame) {
    return frame.getClassName() + MediaMetrics.SEPARATOR + frame.getMethodName();
  }
}
