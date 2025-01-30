package com.android.server.am.mars;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SemHqmManager;
import android.os.UserHandle;
import android.util.SparseArray;
import com.android.server.clipboard.ClipboardService;
import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPkgMap;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.util.ForegroundServiceMgr;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class MARsBigData {
    public static MARsBigData sInstance;
    public final Context mContext;
    public SemHqmManager mHQM;
    public String PLEVdata = null;
    public BroadcastReceiver mIntentReceiver = new BroadcastReceiver() { // from class: com.android.server.am.mars.MARsBigData.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (action == null || !"com.sec.android.intent.action.HQM_UPDATE_REQ".equals(action)) {
                    return;
                }
                MARsBigData.this.updateBigdataInfo();
                MARsBigData.this.sendBigDataInfoPLEV();
                MARsBigData.this.sendFGSTypeBigData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static MARsBigData getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MARsBigData(context);
        }
        return sInstance;
    }

    public MARsBigData(Context context) {
        this.mHQM = null;
        this.mContext = context;
        if (this.mHQM == null) {
            this.mHQM = (SemHqmManager) context.getSystemService("HqmManagerService");
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
        context.registerReceiverAsUser(this.mIntentReceiver, UserHandle.ALL, intentFilter, "com.samsung.android.permission.HQM_NOTIFICATION_PERMISSION", null);
    }

    public void sendBigDataInfoToHQM() {
        try {
            updateBigdataInfo();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            this.PLEVdata = new UsageInfo().toString();
        }
        sendBigDataInfoPLEV();
    }

    public class UsageInfo {
        public final String batteryUsage;
        public final String disableLevelSize;
        public final String extras;
        public final String fasLevelSize;
        public final String forceStopLevelSize;
        public final String freecessLevelSize;
        public final String packageName;
        public final String totalSize;

        public UsageInfo() {
            this.packageName = "IllegalArgumentException";
            this.totalSize = "IllegalArgumentException";
            this.freecessLevelSize = "0";
            this.fasLevelSize = "0";
            this.forceStopLevelSize = "0";
            this.disableLevelSize = "0";
            this.batteryUsage = "IllegalArgumentException";
            this.extras = "IllegalArgumentException";
        }

        public UsageInfo(String str, String str2, LevelInfo[] levelInfoArr, String str3, String str4) {
            this.packageName = str == null ? "NONE" : str;
            this.totalSize = str2 == null ? "NONE" : str2;
            this.freecessLevelSize = Integer.toString(levelInfoArr[0].totalSize);
            this.fasLevelSize = Integer.toString(levelInfoArr[1].totalSize);
            this.forceStopLevelSize = Integer.toString(levelInfoArr[2].totalSize);
            this.disableLevelSize = Integer.toString(levelInfoArr[3].totalSize);
            this.batteryUsage = str3 == null ? "NONE" : str3;
            if (str4 == null) {
                str4 = "NONE";
            } else if (str4.length() > 400) {
                str4 = str4.substring(0, 400);
            }
            this.extras = str4;
        }

        public String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("PKNA", this.packageName).put("PKLV", this.totalSize).put("COMA", this.freecessLevelSize).put("COMS", this.fasLevelSize).put("COMR", this.forceStopLevelSize).put("COMB", this.disableLevelSize).put("BATU", this.batteryUsage).put("EXTR", this.extras);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jSONObject2 = jSONObject.toString();
            return jSONObject2.substring(1, jSONObject2.length() - 1);
        }
    }

    public class LevelInfo {
        public int totalSize = 0;
        public double batteryUsage = 0.0d;
        public String packageName = "";
        public int prePackageCnt = 0;
        public double preBatteryUsage = 0.0d;
        public String reason = "";

        public LevelInfo() {
        }
    }

    public void updateBigdataInfo() {
        int size;
        String str = null;
        LevelInfo[] levelInfoArr = new LevelInfo[4];
        for (int i = 0; i < 4; i++) {
            levelInfoArr[i] = new LevelInfo();
        }
        MARsPkgMap mARsTargetPkgMap = MARsPolicyManager.getInstance().getMARsTargetPkgMap();
        synchronized (MARsPolicyManager.MARsLock) {
            size = mARsTargetPkgMap.size();
            for (int i2 = 0; i2 < mARsTargetPkgMap.getMap().size(); i2++) {
                SparseArray sparseArray = (SparseArray) mARsTargetPkgMap.getMap().valueAt(i2);
                for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                    MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i3);
                    int maxLevel = mARsPackageInfo.getMaxLevel();
                    if (maxLevel == 0 || maxLevel == 1) {
                        levelInfoArr[0].totalSize++;
                    } else if (maxLevel == 2) {
                        levelInfoArr[1].totalSize++;
                    } else if (maxLevel == 3) {
                        levelInfoArr[2].totalSize++;
                    } else {
                        if (maxLevel != 4) {
                            throw new IllegalStateException("Unexpected value: " + mARsPackageInfo.getMaxLevel());
                        }
                        levelInfoArr[3].totalSize++;
                    }
                }
            }
        }
        this.PLEVdata = new UsageInfo(str, Integer.toString(size), levelInfoArr, null, null).toString();
    }

    public final void sendBigDataInfoPLEV() {
        String str = this.PLEVdata;
        if (str != null) {
            sendBigData("PLEV", str);
        }
    }

    public final void sendBigData(String str, String str2) {
        SemHqmManager semHqmManager = this.mHQM;
        if (semHqmManager == null) {
            return;
        }
        semHqmManager.sendHWParamToHQM(0, "Sluggish", str, "ph", "1.0", "sec", "", str2, "");
    }

    public void sendFalconBigData(MARsPackageInfo mARsPackageInfo) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("PKGN", mARsPackageInfo.getName()).put("UID", mARsPackageInfo.getUid()).put("NUSD", (System.currentTimeMillis() - mARsPackageInfo.getDisableResetTime()) / ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS).put("BUSE", mARsPackageInfo.getBatteryUsage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jSONObject2 = jSONObject.toString();
        sendBigData("FGSN", jSONObject2.substring(1, jSONObject2.length() - 1));
    }

    public void sendFGSTypeBigData() {
        JSONObject jSONObject = new JSONObject();
        for (ForegroundServiceRecord foregroundServiceRecord : ForegroundServiceMgr.getInstance().getMap().values()) {
            try {
                jSONObject.put("PKGN", foregroundServiceRecord.getPackageName()).put("UID", 0).put("NUSD", foregroundServiceRecord.getFGSType()).put("BUSE", foregroundServiceRecord.getUsingFGSType());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String jSONObject2 = jSONObject.toString();
            sendBigData("FGSN", jSONObject2.substring(1, jSONObject2.length() - 1));
        }
        ForegroundServiceMgr.getInstance().clearMap();
    }
}
