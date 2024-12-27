package com.samsung.android.hardware.secinputdev.taas;

import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputDeviceManagerService;
import com.samsung.android.hardware.secinputdev.device.SemInputDeviceFactory;
import com.samsung.android.hardware.secinputdev.device.Taas;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.BiFunction;

/* loaded from: classes.dex */
public class SemInputDeviceHqmData {
    private static final String CASE_PATH = "/data/vendor/taas/";
    private static final int TAAS_EFS_MAX_COUNT = 50;
    private static final String TAG = "SemInputDeviceHqmData";
    private int mCaseACount;
    private int mCaseBCount;
    private Taas taas;
    private int mCaseACountHqm = 0;
    private int mCaseBCountHqm = 0;
    private Map<String, Integer> caseAMap = new HashMap();
    private HashMap<String, Integer> mLoggingData = new HashMap<>();

    public Map getCaseAMap() {
        return this.caseAMap;
    }

    public SemInputDeviceHqmData() {
        this.mCaseACount = 0;
        this.mCaseBCount = 0;
        this.taas = null;
        this.taas = (Taas) SemInputDeviceFactory.create("TAAS", 41, 0, "NG");
        try {
            String casStr = this.taas.read();
            if (casStr == null || "NG".equals(casStr)) {
                Log.d(TAG, "readTaas init failed");
                return;
            }
            try {
                StringTokenizer stk = new StringTokenizer(casStr);
                this.mCaseACount = Integer.parseInt(stk.nextToken());
                this.mCaseBCount = Integer.parseInt(stk.nextToken());
                Log.d(
                        TAG,
                        "init: mCase1Count:"
                                + this.mCaseACount
                                + " mCase2Count:"
                                + this.mCaseBCount);
            } catch (Exception e) {
                SemInputDeviceManagerService.loggingException(TAG, "parseInt", e);
            }
        } catch (Exception e2) {
            Log.d(TAG, "init: failed to set initial value");
            SemInputDeviceManagerService.loggingException(TAG, "init", e2);
        }
    }

    public void clear() {
        this.mCaseACountHqm = 0;
        this.mCaseBCountHqm = 0;
        this.caseAMap.clear();
    }

    private boolean isValueMatched(String pkgName) {
        for (String key : this.caseAMap.keySet()) {
            if (key.equals(pkgName)) {
                return true;
            }
        }
        return false;
    }

    public void setCaseACount(String pkgName) {
        if (this.mCaseACountHqm >= 2147483547) {
            this.mCaseACountHqm = 2147483547;
        } else {
            this.mCaseACountHqm++;
        }
        if (this.mCaseACount >= 50) {
            this.mCaseACount = 50;
        } else {
            this.mCaseACount++;
        }
        if (this.caseAMap.size() < 100 || isValueMatched(pkgName)) {
            this.caseAMap.merge(
                    pkgName,
                    1,
                    new BiFunction() { // from class:
                                       // com.samsung.android.hardware.secinputdev.taas.SemInputDeviceHqmData$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            return Integer.valueOf(
                                    Integer.sum(
                                            ((Integer) obj).intValue(),
                                            ((Integer) obj2).intValue()));
                        }
                    });
        } else {
            Log.d(TAG, "caseAMap.size is full!" + this.caseAMap.size());
        }
    }

    public void setCaseBCount() {
        if (this.mCaseBCountHqm >= 2147483547) {
            this.mCaseBCountHqm = 2147483547;
        } else {
            this.mCaseBCountHqm++;
        }
        if (this.mCaseBCount >= 50) {
            this.mCaseBCount = 50;
        } else {
            this.mCaseBCount++;
        }
    }

    public int getCaseACountHqm() {
        return this.mCaseACountHqm;
    }

    public int getCaseBCountHqm() {
        return this.mCaseBCountHqm;
    }

    public void writeForCaseToEfs(String getTime, String TaasLog, String taasCase) {
        String tempStr =
                Integer.toString(this.mCaseACount)
                        + " "
                        + Integer.toString(this.mCaseBCount)
                        + " "
                        + getTime
                        + " "
                        + taasCase
                        + " "
                        + TaasLog;
        Log.d(
                TAG,
                "increaseCount mCaseACount:"
                        + this.mCaseACount
                        + " mCaseBCount:"
                        + this.mCaseBCount
                        + " tempStr:"
                        + tempStr);
        if (this.taas.write(tempStr) < 0) {
            Log.d(TAG, "writeTaas error caseA");
        }
    }

    public void setLoggingData(String key, int value) {
        this.mLoggingData.put(key, Integer.valueOf(value));
    }

    public Taas getTaas() {
        return this.taas;
    }

    public int get(String key) {
        if (key == null) {
            return 0;
        }
        return this.mLoggingData.getOrDefault(key, 0).intValue();
    }

    public void destroy() {}
}
