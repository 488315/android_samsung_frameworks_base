package com.android.server.am;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.app.ProcessMap;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AppStateBroadcaster {
    public static Context mContext;
    public static Handler mObjHandler;
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static volatile boolean mIsBroadcastEnabled = false;
    public static String mLastFocusAppName = null;
    public static final String[] sPackages = {"com.tmobile.echolocate", "com.tmobile.pr.mytmobile", "com.sprint.ms.smf.services"};
    public static final String[] APP_TERM_REASONS = {"NORMAL_SYSTEM_HALT", "USER_HALTED", "ANR", "CRASH"};
    public static final HashMap sKnownRunningPackages = new HashMap();

    public static void broadcastAppState(String str, String str2) {
        boolean z = DEBUG;
        if (z) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(" packageName : ", str, " appState : ", str2, "AppStateBroadcaster");
        }
        if (mContext == null || str == null || str2.equals("EXITED")) {
            if (z) {
                Slog.d("AppStateBroadcaster", "Can't send broadcast mContext is null");
                return;
            }
            return;
        }
        for (String str3 : sPackages) {
            Intent m = ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("diagandroid.app.ApplicationState", str3);
            m.putExtra("ApplicationPackageName", stripPackageName(str));
            m.putExtra("ApplicationState", str2);
            m.putExtra("oemIntentTimestamp", System.currentTimeMillis());
            mContext.sendBroadcastAsUser(m, UserHandle.ALL, "diagandroid.app.receiveDetailedApplicationState");
        }
    }

    public static void enableIntentBroadcast(Context context, ProcessMap processMap) {
        mContext = context;
        mIsBroadcastEnabled = true;
        mLastFocusAppName = null;
        mObjHandler = new Handler();
        for (SparseArray sparseArray : processMap.getMap().values()) {
            for (int i = 0; i < sparseArray.size(); i++) {
                ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i);
                sendApplicationStart(processRecord.mPid, processRecord.info.packageName);
            }
        }
    }

    public static void logOriginFunction(String str) {
        Throwable th = new Throwable();
        if (th.getStackTrace().length > 1) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " called from ");
            m.append(th.getStackTrace()[2].toString());
            Slog.d("AppStateBroadcaster", m.toString());
        }
    }

    public static void sendApplicationFocusLoss(String str) {
        boolean z = DEBUG;
        if (z) {
            logOriginFunction("sendApplicationFocusLoss(" + str + ")");
        }
        if (!mIsBroadcastEnabled || TextUtils.isEmpty(str) || TextUtils.isEmpty(mLastFocusAppName)) {
            return;
        }
        broadcastAppState(str, "FOCUS_LOSS");
        if (z) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m("sendApplicationFocusLoss sent APP_STATE_FOCUS_LOSS for ", str, "AppStateBroadcaster");
        }
        if (str.equals(mLastFocusAppName)) {
            if (z) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("packageName is same as mLastFocusAppName: "), mLastFocusAppName, "; setting it to null", "AppStateBroadcaster");
            }
            mLastFocusAppName = null;
        }
    }

    public static void sendApplicationStart(int i, String str) {
        boolean z;
        boolean z2;
        if (!mIsBroadcastEnabled || TextUtils.isEmpty(str)) {
            return;
        }
        HashMap hashMap = sKnownRunningPackages;
        synchronized (hashMap) {
            try {
                String stripPackageName = stripPackageName(str);
                z = DEBUG;
                if (z) {
                    Slog.d("AppStateBroadcaster", "packageRunning for " + stripPackageName + " with processId " + i + " packageName " + str);
                }
                ApplicationState applicationState = (ApplicationState) hashMap.get(stripPackageName);
                z2 = false;
                if (applicationState == null) {
                    applicationState = new ApplicationState();
                    applicationState.mStopReason = 0;
                    applicationState.mProcessIds = new HashSet();
                    hashMap.put(stripPackageName, applicationState);
                    z2 = true;
                }
                applicationState.mProcessIds.add(Integer.valueOf(i));
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            broadcastAppState(str, "START");
            if (z) {
                Slog.d("AppStateBroadcaster", "packageRunning sent APP_STATE_START for ".concat(str));
            }
        }
    }

    public static String stripPackageName(String str) {
        int indexOf = str.indexOf(58);
        return indexOf != -1 ? str.substring(0, indexOf) : str;
    }
}
