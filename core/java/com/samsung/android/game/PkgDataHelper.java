package com.samsung.android.game;

import android.p009os.Handler;
import android.p009os.Message;
import android.p009os.UserHandle;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes5.dex */
public class PkgDataHelper {
    private static final int MSG_APP_CREATE = 10010;
    private static final String TAG = "PkgDataHelper";
    private static PkgDataHelper mInstance = null;
    private HashMap<Integer, HashMap<String, PkgData>> mPkgDataMap = new HashMap<>();
    private Handler mHandler = null;

    public static synchronized PkgDataHelper getInstance() {
        PkgDataHelper pkgDataHelper;
        synchronized (PkgDataHelper.class) {
            if (mInstance == null) {
                mInstance = new PkgDataHelper();
            }
            pkgDataHelper = mInstance;
        }
        return pkgDataHelper;
    }

    private PkgDataHelper() {
    }

    public synchronized int size() {
        return this.mPkgDataMap.size();
    }

    public synchronized Set<String> getPkgNameSet(int userID) {
        Set<String> keyset;
        HashMap<String, PkgData> values;
        keyset = new HashSet<>();
        if (this.mPkgDataMap.containsKey(Integer.valueOf(userID)) && (values = this.mPkgDataMap.get(Integer.valueOf(userID))) != null) {
            keyset = values.keySet();
        }
        return keyset;
    }

    public synchronized HashMap<String, List<Integer>> getPkgNameUserIdMap() {
        HashMap<String, List<Integer>> pkgNameUserId;
        List<Integer> userIdList;
        pkgNameUserId = new HashMap<>();
        for (Map.Entry<Integer, HashMap<String, PkgData>> entry : this.mPkgDataMap.entrySet()) {
            Integer key_UserId = entry.getKey();
            HashMap<String, PkgData> userPkgMap = entry.getValue();
            for (Map.Entry<String, PkgData> values : userPkgMap.entrySet()) {
                String pkgName = values.getKey();
                if (pkgNameUserId.get(pkgName) == null) {
                    userIdList = new ArrayList<>();
                } else {
                    List<Integer> userIdList2 = pkgNameUserId.get(pkgName);
                    userIdList = userIdList2;
                }
                userIdList.add(key_UserId);
                pkgNameUserId.put(pkgName, userIdList);
            }
        }
        return pkgNameUserId;
    }

    public synchronized Collection<PkgData> getPkgDataSet(int userID) {
        Collection<PkgData> set;
        HashMap<String, PkgData> values;
        set = new HashSet<>();
        if (this.mPkgDataMap.containsKey(Integer.valueOf(userID)) && (values = this.mPkgDataMap.get(Integer.valueOf(userID))) != null) {
            set = values.values();
        }
        return set;
    }

    public synchronized void putPkgData(String pkgName, int userID, PkgData pkgData) {
        if (pkgName != null && pkgData != null) {
            GmsLog.m256d(TAG, "putPkgData(). " + pkgName + ", userId = " + userID);
            HashMap<String, PkgData> values = this.mPkgDataMap.get(Integer.valueOf(userID));
            if (values == null) {
                values = new HashMap<>();
                this.mPkgDataMap.put(Integer.valueOf(userID), values);
            }
            values.put(pkgName, pkgData);
        }
    }

    public synchronized void removePkgData(String pkgName, int userID) {
        if (this.mPkgDataMap.containsKey(Integer.valueOf(userID))) {
            GmsLog.m256d(TAG, "removePkgData(). " + pkgName + ", userId = " + userID);
            HashMap<String, PkgData> values = this.mPkgDataMap.get(Integer.valueOf(userID));
            if (values != null && pkgName != null) {
                values.remove(pkgName);
                if (values.isEmpty()) {
                    this.mPkgDataMap.remove(Integer.valueOf(userID));
                }
            }
        }
    }

    public synchronized void removeUser(int userId) {
        if (this.mPkgDataMap.containsKey(Integer.valueOf(userId))) {
            GmsLog.m256d(TAG, "removeUserId(). userId = " + userId);
            this.mPkgDataMap.remove(Integer.valueOf(userId));
        }
    }

    public synchronized void dumpPkgDataHelper(PrintWriter pw) {
        for (Map.Entry<Integer, HashMap<String, PkgData>> entry : this.mPkgDataMap.entrySet()) {
            entry.getKey();
            HashMap<String, PkgData> userPkgMap = entry.getValue();
            for (Map.Entry<String, PkgData> value : userPkgMap.entrySet()) {
                PkgData pkgData = value.getValue();
                if (pkgData != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("  [" + entry.getKey() + "] ");
                    sb.append(" Category: " + pkgData.getCategory());
                    if (pkgData.getCategory() == 1) {
                        sb.append(", GTC: " + pkgData.getCpuLevel() + "/" + pkgData.getGpuLevel() + "/" + pkgData.getShiftTemperature());
                        sb.append(", GameSDK: " + pkgData.getCpuMinPercent() + "/" + pkgData.getGpuMinPercent());
                        sb.append(", UserID: " + pkgData.getUserID());
                        if (pkgData.getGovernorSetting() != null) {
                            sb.append(", Setting: " + pkgData.getGovernorSetting());
                        }
                    }
                    if (pw != null) {
                        pw.println(sb);
                        GmsLog.m256d(TAG, sb.toString());
                    }
                }
            }
        }
    }

    public synchronized PkgData getPkgData(String pkgName, int userID) {
        PkgData resultData;
        HashMap<String, PkgData> values;
        resultData = null;
        if (this.mPkgDataMap.containsKey(Integer.valueOf(userID)) && (values = this.mPkgDataMap.get(Integer.valueOf(userID))) != null && pkgName != null) {
            resultData = values.get(pkgName);
        }
        return resultData;
    }

    public synchronized PkgData preparePkgData(String pkgName, int userID) {
        PkgData resultData;
        resultData = getPkgData(pkgName, userID);
        if (resultData == null) {
            resultData = new PkgData(pkgName);
            resultData.setUserID(userID);
            putPkgData(pkgName, userID, resultData);
        }
        return resultData;
    }

    public synchronized PkgData getGamePkgData(String pkgName, int userID) {
        PkgData resultData;
        HashMap<String, PkgData> values;
        PkgData tempData;
        GmsLog.m256d(TAG, "getGamePkgData(). " + pkgName);
        resultData = null;
        if (this.mPkgDataMap.containsKey(Integer.valueOf(userID)) && (values = this.mPkgDataMap.get(Integer.valueOf(userID))) != null && pkgName != null && (tempData = values.get(pkgName)) != null) {
            if (tempData.getCategory() == 1) {
                resultData = tempData;
            }
        }
        return resultData;
    }

    public synchronized PkgData getGamePkgData(String pkgName) {
        return getGamePkgData(pkgName, UserHandle.semGetCallingUserId());
    }

    public void notifyAppCreate(String pkgName, int userId) {
        Handler handler = this.mHandler;
        if (handler != null) {
            Message m = handler.obtainMessage(10010);
            m.obj = pkgName;
            m.arg1 = userId;
            boolean sendRet = this.mHandler.sendMessage(m);
            GmsLog.m256d(TAG, "notifyAppCreate(), pkgName: " + pkgName + ", userId: " + userId + ", sendRet: " + sendRet);
            return;
        }
        GmsLog.m261w(TAG, "notifyAppCreate(), pkgName: " + pkgName + ", userId: " + userId + ", mHandler is null");
    }

    public void setFgCheckHandler(Handler fgCheckHandler) {
        this.mHandler = fgCheckHandler;
    }
}
