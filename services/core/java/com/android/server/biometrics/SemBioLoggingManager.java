package com.android.server.biometrics;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.UserHandle;
import android.util.SparseArray;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public final class SemBioLoggingManager {
    public static final int LOG_ARRAY_SIZE;
    public static SemBioLoggingManager sInstance;
    public static final Object sInstanceLock;
    public boolean mIsFpBioStarEnabled;
    public String mLastFaceEnrollLog;
    public String mLastFaceRemoveLog;
    public String mLastFpEnrollLog;
    public String mLastFpRemoveLog;
    public String mLastFpAuthenticatorId = "Unknown";
    public final ArrayList mFpAuthLogList = new ArrayList();
    public final ArrayList mFaceAuthLogList = new ArrayList();
    public final ArrayList mBpAuthLogList = new ArrayList();
    public final SparseArray mFpLoggingInfo = new SparseArray();
    public final SparseArray mFaceLoggingInfo = new SparseArray();
    public final SparseArray mBpLoggingInfo = new SparseArray();

    public final class LoggingInfo {
        public int mBadQualityCount;
        public int mExtra;
        public long mLatency;
        public String mPackageName;
        public String mResult;
        public long mResultTime;
        public long mStartTime;
        public String mTouchMap;
        public String mType;
        public int mFpScreenStatus = -1;
        public int mFpAlphaMaskLvl = -1;

        public final void sendBroadcast(Context context, Intent intent, boolean z) {
            String str;
            if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE
                    && (str = this.mTouchMap) != null) {
                intent.putExtra("touch_map", str);
            }
            intent.putExtra("screen_status", z);
            intent.putExtra("pkg_name", this.mPackageName);
            try {
                context.sendBroadcastAsUser(
                        intent, UserHandle.CURRENT, "android.permission.MANAGE_BIOMETRIC");
            } catch (Exception e) {
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("sendBroadcast: "), "BiometricStats");
            }
        }

        public final void sendBroadcastFaceInfo(Context context, Intent intent) {
            intent.putExtra("pkg_name", this.mPackageName);
            if (!SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FLIP
                    && !SemBiometricFeature.FEATURE_SUPPORT_FOLDABLE_TYPE_FOLD) {
                intent.putExtra("fold_state", 0);
            } else if (Utils.isFolderOpened(context) || Utils.isFlipOpened(context)) {
                intent.putExtra("fold_state", 1);
            } else {
                intent.putExtra("fold_state", 2);
            }
            try {
                context.sendBroadcastAsUser(
                        intent, UserHandle.CURRENT, "android.permission.MANAGE_BIOMETRIC");
            } catch (Exception e) {
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("sendBroadcast: "), "BiometricStats");
            }
        }

        public final String toDumpFormat() {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("MM/dd HH:mm:ss.SSS", Locale.ENGLISH);
            try {
                String format =
                        this.mResultTime == 0
                                ? "-----------"
                                : simpleDateFormat.format(new Date(this.mResultTime));
                return this.mType
                        + ", "
                        + this.mPackageName
                        + ", "
                        + simpleDateFormat.format(new Date(this.mStartTime))
                        + ", "
                        + format
                        + ", "
                        + this.mLatency
                        + ", "
                        + this.mExtra
                        + ", "
                        + this.mBadQualityCount
                        + ", "
                        + this.mResult;
            } catch (Exception e) {
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("toDumpFormat: "), "BiometricStats");
                return "formatting error";
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("LoggingInfo{mType='");
            sb.append(this.mType);
            sb.append("', mPackageName='");
            sb.append(this.mPackageName);
            sb.append("', mStartTime=");
            sb.append(this.mStartTime);
            sb.append(", mLatency=");
            sb.append(this.mLatency);
            sb.append(", mResultTime=");
            sb.append(this.mResultTime);
            sb.append(", mExtra=");
            sb.append(this.mExtra);
            sb.append(", mBadQualityCount=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.mBadQualityCount, '}');
        }
    }

    static {
        LOG_ARRAY_SIZE = Utils.DEBUG ? 50 : 20;
        sInstanceLock = new Object();
    }

    public static SemBioLoggingManager get() {
        synchronized (sInstanceLock) {
            try {
                if (sInstance == null) {
                    sInstance = new SemBioLoggingManager();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sInstance;
    }

    public final void faceAddLoggingInfo(LoggingInfo loggingInfo) {
        synchronized (this) {
            try {
                if ("E".equals(loggingInfo.mType)) {
                    this.mLastFaceEnrollLog = loggingInfo.toDumpFormat();
                }
                this.mFaceAuthLogList.add(loggingInfo.toDumpFormat());
                if (this.mFaceAuthLogList.size() > LOG_ARRAY_SIZE) {
                    this.mFaceAuthLogList.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void faceDump(PrintWriter printWriter) {
        if (this.mLastFaceEnrollLog != null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Enroll: "), this.mLastFaceEnrollLog, printWriter);
        }
        if (this.mLastFaceRemoveLog != null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Remove: "), this.mLastFaceRemoveLog, printWriter);
        }
        Iterator it = this.mFaceAuthLogList.iterator();
        while (it.hasNext()) {
            printWriter.println((String) it.next());
        }
        synchronized (this.mFaceLoggingInfo) {
            try {
                int size = this.mFaceLoggingInfo.size();
                if (size > 0) {
                    printWriter.println(" [ in progress client ]");
                    for (int i = 0; i < size; i++) {
                        LoggingInfo loggingInfo = (LoggingInfo) this.mFaceLoggingInfo.valueAt(i);
                        if (loggingInfo != null) {
                            printWriter.println(" - " + loggingInfo.toDumpFormat());
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println();
    }

    public final void faceStart(int i, String str, String str2) {
        synchronized (this.mFaceLoggingInfo) {
            try {
                LoggingInfo loggingInfo = new LoggingInfo();
                loggingInfo.mStartTime = System.currentTimeMillis();
                loggingInfo.mType = str;
                loggingInfo.mPackageName = str2;
                this.mFaceLoggingInfo.append(i, loggingInfo);
                if (this.mFaceLoggingInfo.size() >= LOG_ARRAY_SIZE) {
                    this.mFaceLoggingInfo.removeAt(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void faceStop(int i, int i2, long j, String str) {
        synchronized (this.mFaceLoggingInfo) {
            try {
                LoggingInfo loggingInfo = (LoggingInfo) this.mFaceLoggingInfo.get(i);
                if (loggingInfo != null) {
                    loggingInfo.mResultTime = System.currentTimeMillis();
                    loggingInfo.mLatency = j;
                    loggingInfo.mResult = str;
                    loggingInfo.mExtra = i2;
                    faceAddLoggingInfo(loggingInfo);
                    this.mFaceLoggingInfo.delete(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void fpAddLoggingInfo(LoggingInfo loggingInfo) {
        synchronized (this.mFpAuthLogList) {
            try {
                String dumpFormat = loggingInfo.toDumpFormat();
                if ("E".equals(loggingInfo.mType)) {
                    this.mLastFpEnrollLog = dumpFormat;
                } else if ("R".equals(loggingInfo.mType)) {
                    this.mLastFpRemoveLog = dumpFormat;
                } else if (SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(dumpFormat);
                    sb.append(
                            ", S:"
                                    + loggingInfo.mFpScreenStatus
                                    + ", A:"
                                    + loggingInfo.mFpAlphaMaskLvl);
                    dumpFormat = sb.toString();
                }
                this.mFpAuthLogList.add(dumpFormat);
                if (this.mFpAuthLogList.size() > LOG_ARRAY_SIZE) {
                    this.mFpAuthLogList.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void fpDump(PrintWriter printWriter) {
        synchronized (this.mFpAuthLogList) {
            try {
                printWriter.println("AID: " + this.mLastFpAuthenticatorId);
                if (this.mLastFpEnrollLog != null) {
                    printWriter.println("Enroll: " + this.mLastFpEnrollLog);
                }
                if (this.mLastFpRemoveLog != null) {
                    printWriter.println("Remove: " + this.mLastFpRemoveLog);
                }
                Iterator it = this.mFpAuthLogList.iterator();
                while (it.hasNext()) {
                    printWriter.println((String) it.next());
                }
            } finally {
            }
        }
        synchronized (this.mFpLoggingInfo) {
            try {
                int size = this.mFpLoggingInfo.size();
                if (size > 0) {
                    printWriter.println(" [ in progress client ]");
                    for (int i = 0; i < size; i++) {
                        LoggingInfo loggingInfo = (LoggingInfo) this.mFpLoggingInfo.valueAt(i);
                        if (loggingInfo != null) {
                            printWriter.println(" - " + loggingInfo.toDumpFormat());
                        }
                    }
                }
            } finally {
            }
        }
        printWriter.println();
    }

    public Handler getFpHandler() {
        return BiometricHandlerProvider.sBiometricHandlerProvider.getFingerprintHandler();
    }
}
