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
        StackTraceElement stackTraceElementExtractCaller = extractCaller();
        if (stackTraceElementExtractCaller == null) {
            DsmsLog.e("[CallPolicy] DENY (caller frame not found)");
            return false;
        }
        String strMountFrameCannonName = mountFrameCannonName(stackTraceElementExtractCaller);
        if (!mAllowList.contains(strMountFrameCannonName)) {
            DsmsLog.e("[CallPolicy] DENY callerName [" + strMountFrameCannonName + NavigationBarInflaterView.SIZE_MOD_END);
            return false;
        }
        DsmsLog.d("[CallPolicy] ALLOW callerName [" + strMountFrameCannonName + NavigationBarInflaterView.SIZE_MOD_END);
        return true;
    }

    private static StackTraceElement extractCaller() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null) {
            DsmsLog.e("[CallPolicy] Null stack trace");
            return null;
        }
        DsmsLog.d(SUBTAG + String.format("Frames length: %d", Integer.valueOf(stackTrace.length)));
        Integer numFindBaseIndex = findBaseIndex(stackTrace);
        if (numFindBaseIndex == null || numFindBaseIndex.intValue() + 1 >= stackTrace.length) {
            DsmsLog.e("[CallPolicy] Impossible to reach caller");
            return null;
        }
        return stackTrace[numFindBaseIndex.intValue() + 1];
    }

    private static Integer findBaseIndex(StackTraceElement[] stackTraceElementArr) {
        DsmsLog.d(SUBTAG + String.format("Frames length Inside: %d", Integer.valueOf(stackTraceElementArr.length)));
        for (int i = 0; i < stackTraceElementArr.length; i++) {
            StackTraceElement stackTraceElement = stackTraceElementArr[i];
            DsmsLog.d(SUBTAG + String.format("Frame#%d/%d: %s %s", Integer.valueOf(i), Integer.valueOf(stackTraceElementArr.length), stackTraceElement.getClassName(), stackTraceElement.getMethodName()));
            if (BASE_CLASS.equals(stackTraceElement.getClassName()) && BASE_METHOD.equals(stackTraceElement.getMethodName())) {
                return Integer.valueOf(i);
            }
        }
        return null;
    }

    private static String mountFrameCannonName(StackTraceElement stackTraceElement) {
        return stackTraceElement.getClassName() + MediaMetrics.SEPARATOR + stackTraceElement.getMethodName();
    }
}
