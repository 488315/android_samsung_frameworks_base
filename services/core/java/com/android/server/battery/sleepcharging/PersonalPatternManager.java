package com.android.server.battery.sleepcharging;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Slog;
import com.android.server.battery.sleepcharging.SleepPatternContract;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class PersonalPatternManager {
    public static final String TAG = "[SS]" + PersonalPatternManager.class.getSimpleName();
    public final String KEY_CALLER_PACKAGE_NAME = "com.android.server.BatteryService";
    public Context mContext;

    public PersonalPatternManager(Context context) {
        Slog.d(TAG, "PersonalPatternManager Created");
        this.mContext = context;
    }

    public String queryRunestoneStatus() {
        String message;
        try {
            Bundle call = this.mContext.getContentResolver().call(RunestoneStateContract.CONTENT_URI, "getRubinState", "android_com.android.server.BatteryService", (Bundle) null);
            if (call != null) {
                boolean z = call.getBoolean("isDeviceRubinSupported");
                boolean z2 = call.getBoolean("isDeviceRubinWorkable");
                boolean z3 = call.getBoolean("isEnabledInSupportedApps");
                message = "currentRubinState:" + call.getString("currentRubinState") + " ,isDeviceRubinSupported:" + z + " ,isDeviceRubinWorkable:" + z2 + " ,isEnabledInSupportedApps:" + z3;
            } else {
                message = "bundle null";
            }
        } catch (Exception e) {
            Slog.e(TAG, "[queryRunestoneStatus]Exception");
            e.printStackTrace();
            message = e.getMessage();
        }
        Slog.w(TAG, "[queryRunestoneStatus]" + message);
        return message;
    }

    public Map getSleepPatterns() {
        HashMap hashMap = new HashMap();
        Bundle bundle = new Bundle();
        bundle.putString("keyCallerPackagerName", "com.android.server.BatteryService");
        bundle.putString("android:query-arg-sql-sort-order", "week_type");
        try {
            Cursor query = this.mContext.getContentResolver().query(SleepPatternContract.SleepPatternInfo.CONTENT_URI, null, bundle, null);
            try {
                if (query == null) {
                    Slog.w(TAG, "[getSleepPatterns]Fail - cursor null");
                } else if (query.getCount() == 0) {
                    Slog.w(TAG, "[getSleepPatterns]Fail - cursor empty");
                } else {
                    Slog.i(TAG, "[getSleepPatterns]cursor count:" + query.getCount());
                    query.moveToFirst();
                    do {
                        String string = query.getString(query.getColumnIndex("week_type"));
                        long j = query.getLong(query.getColumnIndex("bedtime"));
                        long j2 = query.getLong(query.getColumnIndex("wakeup_time"));
                        float f = query.getFloat(query.getColumnIndex("confidence"));
                        boolean z = query.getInt(query.getColumnIndex("is_confident")) > 0;
                        if (string != null && string.length() >= 3 && j >= 0 && j2 >= 0 && j != j2 && f >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                            hashMap.put(string, new SleepPattern(string, j, j2, f, z));
                        }
                        String str = TAG;
                        Slog.e(str, "[getSleepPatterns]invalid values from runestone");
                        Slog.e(str, "[getSleepPatterns]weekType:" + string + ",bedTime:" + j + ",wakeupTime:" + j2 + ",confidence:" + f + ",isConfident:" + z);
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
            } finally {
            }
        } catch (Exception e) {
            Slog.e(TAG, "[getSleepPatterns]Exception");
            e.printStackTrace();
        }
        Slog.i(TAG, "[getSleepPatterns]sleepPatterns size:" + hashMap.size());
        return hashMap;
    }

    public SleepPattern getSleepPattern(String str, long j, long j2, float f, boolean z) {
        if (str == null || str.length() < 3 || j < 0 || j2 < 0 || j == j2 || f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            String str2 = TAG;
            Slog.e(str2, "[getSleepPattern]invalid values from runestone");
            Slog.e(str2, "[getSleepPattern]weekType:" + str + ",bedTime:" + j + ",wakeupTime:" + j2 + ",confidence:" + f + ",isConfident:" + z);
            return null;
        }
        return new SleepPattern(str, j, j2, f, z);
    }

    public class SleepPattern {
        public final long bedTimeMillis;
        public final float confidence;
        public final boolean isConfident;
        public final long wakeupTimeMillis;
        public final String weekType;

        public SleepPattern(String str, long j, long j2, float f, boolean z) {
            this.weekType = str;
            this.bedTimeMillis = j;
            this.wakeupTimeMillis = j2;
            this.confidence = f;
            this.isConfident = z;
        }

        public String getInfo() {
            StringBuilder sb = new StringBuilder();
            sb.append("[BackupOnOffExceptTime]" + this.weekType.substring(0, 3));
            sb.append(" ,time:" + convertMillisToLocalTime(this.bedTimeMillis) + " ~ " + convertMillisToLocalTime(this.wakeupTimeMillis));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(" ,confidence:");
            sb2.append(this.confidence);
            sb.append(sb2.toString());
            sb.append(" ,isConfident:" + this.isConfident);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            return sb.toString();
        }

        public final LocalTime convertMillisToLocalTime(long j) {
            return Instant.ofEpochMilli(j).atZone(ZoneId.of("UTC")).toLocalTime();
        }
    }
}
