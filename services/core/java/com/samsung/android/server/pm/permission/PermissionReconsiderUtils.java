package com.samsung.android.server.pm.permission;

import android.content.Context;
import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.internal.util.jobs.XmlUtils;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.PmSharedPreferences;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class PermissionReconsiderUtils {
    public final Context mContext;
    public int mRollbackId;
    public String mUpdateTimeStr;

    public PermissionReconsiderUtils(Context context) {
        this.mRollbackId = -1;
        this.mUpdateTimeStr = "";
        this.mContext = context;
        int intValue = PmSharedPreferences.getLong(context, "perm_apex_rollback_id", -1L).intValue();
        this.mRollbackId = intValue;
        if (intValue > -1) {
            this.mUpdateTimeStr = PmSharedPreferences.getString(context, "perm_apex_update_time", "");
        }
    }

    public void setRollbackIdAndUpdateTime(int i, String str) {
        this.mRollbackId = i;
        this.mUpdateTimeStr = str;
    }

    public boolean runWhenRollbackPermission(Consumer consumer) {
        if (this.mRollbackId == -1) {
            return false;
        }
        long rollbackUpdateTime = getRollbackUpdateTime();
        if (rollbackUpdateTime <= 0) {
            return false;
        }
        PmLog.logCriticalInfoAndLogcat("Reconsidering permissions due to rollback. apex update time: " + rollbackUpdateTime);
        consumer.accept(Long.valueOf(rollbackUpdateTime));
        return true;
    }

    public void clearRollbackHistory() {
        PmSharedPreferences.putLong(this.mContext, "perm_apex_rollback_id", -1L, false);
    }

    public long getRollbackUpdateTime() {
        Map updateTimes = getUpdateTimes();
        if (updateTimes.containsKey(Integer.valueOf(this.mRollbackId))) {
            return ((Long) updateTimes.get(Integer.valueOf(this.mRollbackId))).longValue();
        }
        return -1L;
    }

    public Map getUpdateTimes() {
        ArrayMap arrayMap = new ArrayMap();
        if (TextUtils.isEmpty(this.mUpdateTimeStr)) {
            return arrayMap;
        }
        try {
            String[] split = this.mUpdateTimeStr.split(",");
            if (split != null && split.length != 0) {
                for (String str : split) {
                    String[] split2 = str.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                    if (split2 != null && split2.length == 2) {
                        arrayMap.put(Integer.valueOf(Integer.parseInt(split2[0])), Long.valueOf(Long.parseLong(split2[1])));
                    }
                }
            }
        } catch (Exception unused) {
        }
        return arrayMap;
    }

    public static boolean saveRollbackIdWhenPermRollback(Context context, RollbackInfo rollbackInfo) {
        if (rollbackInfo != null && rollbackInfo.getPackages() != null) {
            Iterator it = rollbackInfo.getPackages().iterator();
            while (it.hasNext()) {
                String packageName = ((PackageRollbackInfo) it.next()).getPackageName();
                if ("com.google.android.permission".equals(packageName) || "com.google.android.permissioncontroller".equals(packageName)) {
                    int rollbackId = rollbackInfo.getRollbackId();
                    PmSharedPreferences.putLong(context, "perm_apex_rollback_id", rollbackId, false);
                    PmLog.logCriticalInfoAndLogcat("Rolling back permission module, rollback id: " + rollbackId);
                    break;
                }
            }
        }
        return false;
    }

    public static void saveRollbackUpdateTime(Context context, Supplier supplier) {
        Map map = (Map) supplier.get();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            sb.append(entry.getKey() + XmlUtils.STRING_ARRAY_SEPARATOR + entry.getValue() + ",");
        }
        String sb2 = sb.toString();
        if (TextUtils.isEmpty(sb2)) {
            return;
        }
        PmSharedPreferences.putString(context, "perm_apex_update_time", sb2, true);
        PmLog.logCriticalInfoAndLogcat("Rollback update time: " + sb2);
    }
}
