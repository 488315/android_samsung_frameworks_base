package com.samsung.android.jdsms;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;

/* loaded from: classes6.dex */
final class CallerVerifier {
    private static final String BASE_CLASS = "com.samsung.android.jdsms.Sender";
    private static final String BASE_METHOD = "send";
    private static final String SUBTAG = "[CallPolicy] ";
    private static final CallerAllowList mAllowList = new CallerAllowList();

    CallerVerifier() {
    }

    final boolean wasCallerValid() {
        StackTraceElement callFrame = extractCaller();
        if (callFrame == null) {
            DsmsLog.e("[CallPolicy] DENY (caller frame not found)");
            return false;
        }
        String cannonCallerName = mountFrameCannonName(callFrame);
        if (!mAllowList.contains(cannonCallerName)) {
            DsmsLog.e("[CallPolicy] DENY callerName [" + cannonCallerName + NavigationBarInflaterView.SIZE_MOD_END);
            return false;
        }
        DsmsLog.d("[CallPolicy] ALLOW callerName [" + cannonCallerName + NavigationBarInflaterView.SIZE_MOD_END);
        return true;
    }

    private static StackTraceElement extractCaller() {
        StackTraceElement[] frames = Thread.currentThread().getStackTrace();
        if (frames == null) {
            DsmsLog.e("[CallPolicy] Null stack trace");
            return null;
        }
        DsmsLog.d(SUBTAG + String.format("Frames length: %d", Integer.valueOf(frames.length)));
        Integer baseIndex = findBaseIndex(frames);
        if (baseIndex == null || baseIndex.intValue() + 1 >= frames.length) {
            DsmsLog.e("[CallPolicy] Impossible to reach caller");
            return null;
        }
        return frames[baseIndex.intValue() + 1];
    }

    private static Integer findBaseIndex(StackTraceElement[] frames) {
        DsmsLog.d(SUBTAG + String.format("Frames length Inside: %d", Integer.valueOf(frames.length)));
        for (int index = 0; index < frames.length; index++) {
            StackTraceElement frame = frames[index];
            DsmsLog.d(SUBTAG + String.format("Frame#%d/%d: %s %s", Integer.valueOf(index), Integer.valueOf(frames.length), frame.getClassName(), frame.getMethodName()));
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
