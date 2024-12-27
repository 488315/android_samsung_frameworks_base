package com.samsung.android.server.dynamicfeature;

import android.os.Build;
import android.os.SystemProperties;
import android.util.Slog;

import java.util.Random;

public abstract class InfoBoard {
    public static long TERM_HANDLING_FEATURE = 0;
    public static int TERM_HANDLING_TEST_FEATURE = 0;
    public static final BasicInfos basicInfo;
    public static final ParamInfos paramInfo;
    public static boolean sDirty = false;
    public static String sExecutableBinaryType = "B";
    public static boolean sParamDirty;

    public final class BasicInfos {
        public int flexMill;
        public boolean isUT;
        public long jobIntervalMill;
        public String lastUpdatedTime;
        public String vid;
    }

    public final class ParamInfos {
        public String binaryType;
        public String csc;
        public String mcc;
        public String mnc;
        public String oneUiVersion;
        public int sdkVersion;
    }

    static {
        BasicInfos basicInfos = new BasicInfos();
        basicInfos.vid = "";
        basicInfos.jobIntervalMill = 15L;
        basicInfos.flexMill = 5;
        basicInfos.isUT = false;
        basicInfos.lastUpdatedTime = "";
        basicInfo = basicInfos;
        ParamInfos paramInfos = new ParamInfos();
        paramInfos.mcc = "";
        paramInfos.mnc = "";
        paramInfos.csc = "";
        paramInfos.sdkVersion = 0;
        paramInfos.oneUiVersion = "";
        paramInfos.binaryType = "C";
        paramInfo = paramInfos;
        sDirty = false;
        sParamDirty = false;
        TERM_HANDLING_FEATURE = 10000L;
        TERM_HANDLING_TEST_FEATURE = 1;
    }

    public static String getProperty() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            String str =
                    (String) cls.getMethod("get", String.class).invoke(cls, "ro.build.version.sep");
            return str != null ? !str.isEmpty() ? str : "" : "";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Slog.e("dynamicfeature_InfoBoard", "IllegalArgumentException e: " + e.toString());
            return "";
        } catch (Exception e2) {
            e2.printStackTrace();
            Slog.e("dynamicfeature_InfoBoard", "IllegalArgumentException e: " + e2.toString());
            return "";
        }
    }

    public static long getStartMilFromNow() {
        if (!"true".equals(SystemProperties.get("ro.product_ship", "UNKNOWN"))) {
            return 0L;
        }
        long nextInt = new Random(System.currentTimeMillis()).nextInt(86400);
        Slog.d("dynamicfeature_InfoBoard", "Update service will start after : " + nextInt);
        return nextInt * 1000;
    }

    public static boolean isBetaBinaryType() {
        if (sExecutableBinaryType.contains("A")) {
            return true;
        }
        String str = Build.DISPLAY;
        if (!sExecutableBinaryType.equals(
                "Z".equals(str.substring(str.length() + (-4), str.length() + (-3))) ? "Z" : "C")) {
            return false;
        }
        Slog.d(
                "dynamicfeature_InfoBoard",
                "This is Beta Binary : " + str.substring(str.length() - 4, str.length() - 3));
        return true;
    }

    public static void removeProperty(String str) {
        try {
            SystemProperties.set(str, (String) null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Slog.e("dynamicfeature_InfoBoard", "IllegalArgumentException e: " + e.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
            Slog.e("dynamicfeature_InfoBoard", "IllegalArgumentException e: " + e2.toString());
        }
    }

    public static void setParams(
            String str, String str2, String str3, String str4, String str5, int i) {
        ParamInfos paramInfos = paramInfo;
        if (!paramInfos.mcc.equals(str)) {
            sParamDirty = true;
            paramInfos.mcc = str;
        }
        if (!paramInfos.mnc.equals(str2)) {
            sParamDirty = true;
            paramInfos.mnc = str2;
        }
        if (!paramInfos.csc.equals(str3)) {
            sParamDirty = true;
            paramInfos.csc = str3;
        }
        if (paramInfos.sdkVersion != i) {
            sParamDirty = true;
            paramInfos.sdkVersion = i;
        }
        if (!paramInfos.oneUiVersion.equals(str4)) {
            sParamDirty = true;
            paramInfos.oneUiVersion = str4;
        }
        if (paramInfos.binaryType.equals(str5)) {
            return;
        }
        sParamDirty = true;
        paramInfos.binaryType = str5;
    }
}
