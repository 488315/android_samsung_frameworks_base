package com.android.server.am.mars;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SemHqmManager;
import android.util.SparseArray;

import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPkgMap;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.util.ForegroundServiceMgr;
import com.android.server.pm.pu.ProfileUtilizationService;

import org.json.JSONException;
import org.json.JSONObject;

public final class MARsBigData {
    public String PLEVdata;
    public Context mContext;
    public SemHqmManager mHQM;
    public AnonymousClass1 mIntentReceiver;

    public final class LevelInfo {
        public int totalSize;
    }

    public abstract class MARsBigDataHolder {
        public static final MARsBigData INSTANCE;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.am.mars.MARsBigData$1] */
        static {
            final MARsBigData mARsBigData = new MARsBigData();
            mARsBigData.mHQM = null;
            mARsBigData.PLEVdata = null;
            mARsBigData.mIntentReceiver =
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.am.mars.MARsBigData.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            try {
                                String action = intent.getAction();
                                if (action == null
                                        || !"com.sec.android.intent.action.HQM_UPDATE_REQ"
                                                .equals(action)) {
                                    return;
                                }
                                MARsBigData.this.updateBigdataInfo();
                                MARsBigData mARsBigData2 = MARsBigData.this;
                                String str = mARsBigData2.PLEVdata;
                                if (str != null) {
                                    mARsBigData2.sendBigData("PLEV", str);
                                }
                                MARsBigData.this.sendFGSTypeBigData();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
            INSTANCE = mARsBigData;
        }
    }

    public final class UsageInfo {
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

        public UsageInfo(String str, LevelInfo[] levelInfoArr) {
            this.packageName = "NONE";
            this.totalSize = str == null ? "NONE" : str;
            this.freecessLevelSize = Integer.toString(levelInfoArr[0].totalSize);
            this.fasLevelSize = Integer.toString(levelInfoArr[1].totalSize);
            this.forceStopLevelSize = Integer.toString(levelInfoArr[2].totalSize);
            this.disableLevelSize = Integer.toString(levelInfoArr[3].totalSize);
            this.batteryUsage = "NONE";
            this.extras = "NONE";
        }

        public final String toString() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject
                        .put("PKNA", this.packageName)
                        .put("PKLV", this.totalSize)
                        .put("COMA", this.freecessLevelSize)
                        .put("COMS", this.fasLevelSize)
                        .put("COMR", this.forceStopLevelSize)
                        .put("COMB", this.disableLevelSize)
                        .put("BATU", this.batteryUsage)
                        .put("EXTR", this.extras);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(
                    1, 1, jSONObject.toString());
        }
    }

    public final void sendBigData(String str, String str2) {
        SemHqmManager semHqmManager = this.mHQM;
        if (semHqmManager == null) {
            return;
        }
        semHqmManager.sendHWParamToHQM(
                0,
                "Sluggish",
                str,
                "ph",
                ProfileUtilizationService.PU_VERSION,
                "sec",
                "",
                str2,
                "");
    }

    public final void sendFGSTypeBigData() {
        JSONObject jSONObject = new JSONObject();
        int i = ForegroundServiceMgr.$r8$clinit;
        for (ForegroundServiceRecord foregroundServiceRecord :
                ForegroundServiceMgr.ForegroundServiceMgrHolder.INSTANCE.mMapFGSRecord.values()) {
            try {
                jSONObject
                        .put("PKGN", foregroundServiceRecord.mPackageName)
                        .put("UID", 0)
                        .put("NUSD", foregroundServiceRecord.mForegroundType)
                        .put("BUSE", foregroundServiceRecord.mUsingForegroundType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String jSONObject2 = jSONObject.toString();
            sendBigData("FGSN", jSONObject2.substring(1, jSONObject2.length() - 1));
        }
        ForegroundServiceMgr.ForegroundServiceMgrHolder.INSTANCE.mMapFGSRecord.clear();
    }

    public final void updateBigdataInfo() {
        int size;
        LevelInfo[] levelInfoArr = new LevelInfo[4];
        for (int i = 0; i < 4; i++) {
            LevelInfo levelInfo = new LevelInfo();
            levelInfo.totalSize = 0;
            levelInfoArr[i] = levelInfo;
        }
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPkgMap mARsPkgMap =
                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.mMARsTargetPackages;
        synchronized (MARsPolicyManager.MARsLock) {
            try {
                size = mARsPkgMap.mMap.size();
                for (int i2 = 0; i2 < mARsPkgMap.mMap.size(); i2++) {
                    SparseArray sparseArray = (SparseArray) mARsPkgMap.mMap.valueAt(i2);
                    for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                        MARsPackageInfo mARsPackageInfo = (MARsPackageInfo) sparseArray.valueAt(i3);
                        int i4 = mARsPackageInfo.maxLevel;
                        if (i4 == 0 || i4 == 1) {
                            levelInfoArr[0].totalSize++;
                        } else if (i4 == 2) {
                            levelInfoArr[1].totalSize++;
                        } else if (i4 == 3) {
                            levelInfoArr[2].totalSize++;
                        } else {
                            if (i4 != 4) {
                                throw new IllegalStateException(
                                        "Unexpected value: " + mARsPackageInfo.maxLevel);
                            }
                            levelInfoArr[3].totalSize++;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.PLEVdata = new UsageInfo(Integer.toString(size), levelInfoArr).toString();
    }
}
