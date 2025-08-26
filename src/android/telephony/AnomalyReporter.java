package android.telephony;

import android.Manifest;
import android.app.PropertyInvalidatedCache;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.BatteryStats;
import android.os.ParcelUuid;
import android.provider.DeviceConfig;
import com.android.internal.telephony.TelephonyStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public final class AnomalyReporter {
    private static final String KEY_IS_TELEPHONY_ANOMALY_REPORT_ENABLED = "is_telephony_anomaly_report_enabled";
    private static final String TAG = "AnomalyReporter";
    private static Context sContext;
    private static Map<UUID, Integer> sEvents = new ConcurrentHashMap();
    private static String sDebugPackageName = null;

    private AnomalyReporter() {
    }

    public static void reportAnomaly(UUID uuid, String str) {
        reportAnomaly(uuid, str, -1);
    }

    public static void reportAnomaly(UUID uuid, String str, int i) {
        com.android.telephony.Rlog.i(TAG, "reportAnomaly: Received anomaly event report with eventId= " + uuid + " and description= " + str);
        if (sContext == null) {
            com.android.telephony.Rlog.w(TAG, "AnomalyReporter not yet initialized, dropping event=" + uuid);
            return;
        }
        TelephonyStatsLog.write(461, i, uuid.getLeastSignificantBits(), uuid.getMostSignificantBits());
        try {
            if (DeviceConfig.getBoolean(PropertyInvalidatedCache.MODULE_TELEPHONY, KEY_IS_TELEPHONY_ANOMALY_REPORT_ENABLED, false)) {
                int iIntValue = sEvents.containsKey(uuid) ? sEvents.get(uuid).intValue() + 1 : 1;
                Integer numValueOf = Integer.valueOf(iIntValue);
                sEvents.put(uuid, numValueOf);
                numValueOf.getClass();
                if (iIntValue <= 1 && sDebugPackageName != null) {
                    Intent intent = new Intent(TelephonyManager.ACTION_ANOMALY_REPORTED);
                    intent.putExtra(TelephonyManager.EXTRA_ANOMALY_ID, new ParcelUuid(uuid));
                    if (str != null) {
                        intent.putExtra(TelephonyManager.EXTRA_ANOMALY_DESCRIPTION, str);
                    }
                    intent.setPackage(sDebugPackageName);
                    sContext.sendBroadcast(intent, Manifest.permission.READ_PRIVILEGED_PHONE_STATE);
                }
            }
        } catch (Exception unused) {
            com.android.telephony.Rlog.w(TAG, "Unable to read device config, dropping event=" + uuid);
        }
    }

    public static void initialize(Context context) {
        List<ResolveInfo> listQueryBroadcastReceivers;
        if (context == null) {
            throw new IllegalArgumentException("AnomalyReporter needs a non-null context.");
        }
        context.enforceCallingOrSelfPermission(Manifest.permission.MODIFY_PHONE_STATE, "This app does not have privileges to send debug events");
        sContext = context;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null || (listQueryBroadcastReceivers = packageManager.queryBroadcastReceivers(new Intent(TelephonyManager.ACTION_ANOMALY_REPORTED), BatteryStats.HistoryItem.MOST_INTERESTING_STATES)) == null || listQueryBroadcastReceivers.isEmpty()) {
            return;
        }
        if (listQueryBroadcastReceivers.size() > 1) {
            com.android.telephony.Rlog.e(TAG, "Multiple Anomaly Receivers installed.");
        }
        for (ResolveInfo resolveInfo : listQueryBroadcastReceivers) {
            if (resolveInfo.activityInfo == null) {
                com.android.telephony.Rlog.w(TAG, "Found package without activity");
            } else {
                if (packageManager.checkPermission(Manifest.permission.READ_PRIVILEGED_PHONE_STATE, resolveInfo.activityInfo.packageName) == 0) {
                    com.android.telephony.Rlog.d(TAG, "Found a valid package " + resolveInfo.activityInfo.packageName);
                    sDebugPackageName = resolveInfo.activityInfo.packageName;
                    return;
                }
                com.android.telephony.Rlog.w(TAG, "Found package without proper permissions" + resolveInfo.activityInfo.packageName);
            }
        }
    }

    public static void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (sContext == null) {
            return;
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        sContext.enforceCallingOrSelfPermission(Manifest.permission.DUMP, "Requires DUMP");
        indentingPrintWriter.println("Initialized=".concat(sContext != null ? "Yes" : "No"));
        indentingPrintWriter.println("Debug Package=" + sDebugPackageName);
        indentingPrintWriter.println("Anomaly Counts:");
        indentingPrintWriter.increaseIndent();
        for (UUID uuid : sEvents.keySet()) {
            indentingPrintWriter.println(uuid + ": " + sEvents.get(uuid));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.flush();
    }
}
