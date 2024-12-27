package com.android.server.am.pmm;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.Slog;

import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;

public final class PersonalizedMemoryManager {
    public DmaBufLeakDetector mDmaBufLeakDetector;

    public abstract class LazyHolder {
        public static final PersonalizedMemoryManager INSTANCE = new PersonalizedMemoryManager();
    }

    public enum MemoryEventType {
        LMKD_KILL("LMKD_KILL"),
        EF1("PREV_PROC_DIED"),
        EF2("DEVICE_IDLE"),
        APP_LAUNCHED("APP_LAUNCHED");

        private String mTypeName;

        MemoryEventType(String str) {
            this.mTypeName = str;
        }

        @Override // java.lang.Enum
        public final String toString() {
            return this.mTypeName;
        }
    }

    public final void onMemoryEvent(final Context context, MemoryEventType memoryEventType) {
        int ordinal = memoryEventType.ordinal();
        if (ordinal == 0 || ordinal == 3) {
            final DmaBufLeakDetector dmaBufLeakDetector = this.mDmaBufLeakDetector;
            synchronized (dmaBufLeakDetector) {
                try {
                    if (dmaBufLeakDetector.mContext == null) {
                        dmaBufLeakDetector.mContext = context;
                        dmaBufLeakDetector.mIsListenerAppInstalled =
                                DmaBufLeakDetector.isListenerAppInstalled(context);
                    }
                    if (dmaBufLeakDetector.mIsReporting) {
                        return;
                    }
                    long currentTimeMillis = System.currentTimeMillis();
                    if (dmaBufLeakDetector.mIsTestMode) {
                        Slog.d(
                                "DmaBufLeakDetector",
                                "getDmaBufSizeKb()="
                                        + DmaBufLeakDetector.getDmaBufSizeKb()
                                        + ", getLargestDmaBufProcess()="
                                        + DmaBufLeakDetector.getLargestDmaBufProcess()
                                        + ", isCameraRunning()="
                                        + DmaBufLeakDetector.isCameraRunning()
                                        + ", mLeakThreshold="
                                        + dmaBufLeakDetector.mLeakThreshold
                                        + ", mIsListenerAppInstalled="
                                        + dmaBufLeakDetector.mIsListenerAppInstalled);
                    } else if (dmaBufLeakDetector.mIsListenerAppInstalled) {
                        if (currentTimeMillis > dmaBufLeakDetector.mLastLeakTime + 600000) {
                            if (DmaBufLeakDetector.isCameraRunning()
                                    || DmaBufLeakDetector.getDmaBufSizeKb()
                                            <= dmaBufLeakDetector.mLeakThreshold) {
                                return;
                            }
                            final String largestDmaBufProcess =
                                    DmaBufLeakDetector.getLargestDmaBufProcess();
                            if (largestDmaBufProcess == null) {
                                return;
                            }
                            if (DmaBufLeakDetector.isCameraRunning()
                                    || DmaBufLeakDetector.getDmaBufSizeKb()
                                            <= dmaBufLeakDetector.mLeakThreshold) {
                                return;
                            }
                            dmaBufLeakDetector.mLastLeakTime = currentTimeMillis;
                            dmaBufLeakDetector.mIsReporting = true;
                            Thread thread =
                                    new Thread(
                                            new Runnable() { // from class:
                                                // com.android.server.am.pmm.DmaBufLeakDetector$$ExternalSyntheticLambda1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    DmaBufLeakDetector dmaBufLeakDetector2 =
                                                            DmaBufLeakDetector.this;
                                                    Context context2 = context;
                                                    String str = largestDmaBufProcess;
                                                    dmaBufLeakDetector2.getClass();
                                                    Intent intent = new Intent();
                                                    intent.setAction(
                                                            "com.sec.android.ISSUE_TRACKER_ACTION");
                                                    intent.putExtra("ERRCODE", -134);
                                                    intent.putExtra("ERRPKG", str);
                                                    intent.putExtra("ERRNAME", "DMABUF");
                                                    intent.putExtra("ERRMSG", "DMABUF_leak");
                                                    context2.sendBroadcastAsUser(
                                                            intent, UserHandle.SYSTEM);
                                                    dmaBufLeakDetector2.mReportCount++;
                                                    SystemServiceManager$$ExternalSyntheticOutline0
                                                            .m(
                                                                    new StringBuilder(
                                                                            "sent intent to"
                                                                                + " issuetracker."
                                                                                + " Report Count:"
                                                                                + " "),
                                                                    dmaBufLeakDetector2
                                                                            .mReportCount,
                                                                    "DmaBufLeakDetector");
                                                    dmaBufLeakDetector2.mIsReporting = false;
                                                }
                                            });
                            thread.setName("DmaBufLeakDetector");
                            thread.start();
                        }
                    } else if (currentTimeMillis > dmaBufLeakDetector.mLastCheckTime + 60000) {
                        dmaBufLeakDetector.mLastCheckTime = currentTimeMillis;
                        if (DmaBufLeakDetector.isCameraRunning()
                                || DmaBufLeakDetector.getDmaBufSizeKb()
                                        <= dmaBufLeakDetector.mLeakThreshold) {
                        } else {
                            DmaBufLeakDetector.getLargestDmaBufProcess();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
