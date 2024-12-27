package com.android.server.display.exynos;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

import java.lang.reflect.Array;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ExynosDisplayFactory {
    public final String APS_SYSFS_PATH;
    public final String CGC17_CON_SYSFS_PATH;
    public final String CGC17_DEC_SYSFS_PATH;
    public final String CGC17_ENC_SYSFS_PATH;
    public final String CGC17_IDX_SYSFS_PATH;
    public final String CGC_DITHER_SYSFS_PATH;
    public final boolean DEBUG;
    public final String DEGAMMA_EXT_SYSFS_PATH;
    public final String DEGAMMA_SYSFS_PATH;
    public final String DE_SYSFS_PATH;
    public final String DQE_COEF_XML_FILE_PATH;
    public final String DQE_SYSFS_PATH;
    public final String EXTENSION_OFF;
    public final String EXTENSION_ON;
    public final String GAMMA_EXT_SYSFS_PATH;
    public final String GAMMA_MATRIX_SYSFS_PATH;
    public final String GAMMA_SYSFS_PATH;
    public final String HDR_SYSFS_PATH;
    public final String HSC48_IDX_SYSFS_PATH;
    public final String HSC48_LCG_SYSFS_PATH;
    public final String HSC_SYSFS_PATH;
    public final String MODE_IDX_SYSFS_PATH;
    public final String SCL_SYSFS_PATH;
    public final int[] mColorModeModeIdx;
    public final String[] mColorModeSettingTable;
    public int mCountDownTimerCount;
    public final int[][] mCountDownTimerTable;
    public final AnonymousClass3 mCountdownTimer;
    public String mFactoryXMLPath;
    public final int mIntervalMs;
    public final Handler mLocalHandler;
    public final int mTimeoutMs;

    /* renamed from: -$$Nest$msetCalibrationMODE_IDX, reason: not valid java name */
    public static boolean m482$$Nest$msetCalibrationMODE_IDX(
            ExynosDisplayFactory exynosDisplayFactory, int i) {
        exynosDisplayFactory.getClass();
        return ExynosDisplayUtils.sysfsWriteSting(
                exynosDisplayFactory.MODE_IDX_SYSFS_PATH, Integer.toString(i));
    }

    /* JADX WARN: Type inference failed for: r13v41, types: [com.android.server.display.exynos.ExynosDisplayFactory$3] */
    public ExynosDisplayFactory(Context context) {
        boolean equals = "eng".equals(Build.TYPE);
        this.DEBUG = equals;
        this.mCountdownTimer = null;
        this.mTimeoutMs = 800;
        this.mIntervalMs = 40;
        this.mCountDownTimerCount = 0;
        this.DQE_SYSFS_PATH = "/sys/class/dqe/dqe";
        this.HDR_SYSFS_PATH = "/sys/class/dqe/hdr";
        this.MODE_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/mode_idx";
        this.APS_SYSFS_PATH = "/sys/class/dqe/dqe/aps";
        this.GAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_ext";
        this.GAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/gamma";
        this.DEGAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/degamma_ext";
        this.DEGAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/degamma";
        this.HSC_SYSFS_PATH = "/sys/class/dqe/dqe/hsc";
        this.CGC17_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_idx";
        this.CGC17_ENC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_enc";
        this.CGC17_DEC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_dec";
        this.CGC17_CON_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_con";
        this.GAMMA_MATRIX_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_matrix";
        this.CGC_DITHER_SYSFS_PATH = "/sys/class/dqe/dqe/cgc_dither";
        this.HSC48_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_idx";
        this.HSC48_LCG_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_lcg";
        this.SCL_SYSFS_PATH = "/sys/class/dqe/dqe/scl";
        this.DE_SYSFS_PATH = "/sys/class/dqe/dqe/de";
        this.DQE_COEF_XML_FILE_PATH = "/vendor/etc/dqe/DQE_coef_data.xml";
        this.EXTENSION_OFF = "0";
        this.EXTENSION_ON = "1";
        this.mFactoryXMLPath = null;
        this.mColorModeModeIdx = new int[] {1, 2};
        this.mColorModeSettingTable = new String[] {"hdr10", "hdr10p"};
        this.mCountDownTimerTable = new int[][] {new int[] {0}, new int[] {0}};
        new Handler() { // from class: com.android.server.display.exynos.ExynosDisplayFactory.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                super.handleMessage(message);
                int i = message.what;
                if (i == 1) {
                    ExynosDisplayUtils.sendEmptyUpdate();
                    return;
                }
                ExynosDisplayFactory exynosDisplayFactory = ExynosDisplayFactory.this;
                if (i == 2) {
                    exynosDisplayFactory.sysfsWriteCGC17_IDX(message.arg1);
                } else {
                    if (i != 3) {
                        return;
                    }
                    ExynosDisplayUtils.sysfsWriteSting(
                            exynosDisplayFactory.CGC17_ENC_SYSFS_PATH, message.obj.toString());
                }
            }
        };
        this.mLocalHandler = new Handler(context.getMainLooper());
        this.MODE_IDX_SYSFS_PATH = sysfsPathReplace(this.MODE_IDX_SYSFS_PATH);
        this.APS_SYSFS_PATH = sysfsPathReplace(this.APS_SYSFS_PATH);
        this.GAMMA_EXT_SYSFS_PATH = sysfsPathReplace(this.GAMMA_EXT_SYSFS_PATH);
        this.GAMMA_SYSFS_PATH = sysfsPathReplace(this.GAMMA_SYSFS_PATH);
        this.DEGAMMA_EXT_SYSFS_PATH = sysfsPathReplace(this.DEGAMMA_EXT_SYSFS_PATH);
        this.DEGAMMA_SYSFS_PATH = sysfsPathReplace(this.DEGAMMA_SYSFS_PATH);
        this.HSC_SYSFS_PATH = sysfsPathReplace(this.HSC_SYSFS_PATH);
        this.CGC17_IDX_SYSFS_PATH = sysfsPathReplace(this.CGC17_IDX_SYSFS_PATH);
        this.CGC17_ENC_SYSFS_PATH = sysfsPathReplace(this.CGC17_ENC_SYSFS_PATH);
        this.CGC17_DEC_SYSFS_PATH = sysfsPathReplace(this.CGC17_DEC_SYSFS_PATH);
        this.CGC17_CON_SYSFS_PATH = sysfsPathReplace(this.CGC17_CON_SYSFS_PATH);
        this.GAMMA_MATRIX_SYSFS_PATH = sysfsPathReplace(this.GAMMA_MATRIX_SYSFS_PATH);
        this.CGC_DITHER_SYSFS_PATH = sysfsPathReplace(this.CGC_DITHER_SYSFS_PATH);
        this.HSC48_IDX_SYSFS_PATH = sysfsPathReplace(this.HSC48_IDX_SYSFS_PATH);
        this.HSC48_LCG_SYSFS_PATH = sysfsPathReplace(this.HSC48_LCG_SYSFS_PATH);
        this.SCL_SYSFS_PATH = sysfsPathReplace(this.SCL_SYSFS_PATH);
        this.DE_SYSFS_PATH = sysfsPathReplace(this.DE_SYSFS_PATH);
        if (equals) {
            Log.d(
                    "ExynosDisplayFactory",
                    "setSysfsPath: " + sysfsPathReplace("/sys/class/dqe/dqe"));
        }
        this.mCountDownTimerTable = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 2, 20);
        for (int i = 0; i < 2; i++) {
            for (int i2 = 0; i2 < 20; i2++) {
                this.mCountDownTimerTable[i][i2] = 0;
            }
        }
        this.mCountdownTimer =
                new CountDownTimer(
                        this.mTimeoutMs,
                        this
                                .mIntervalMs) { // from class:
                                                // com.android.server.display.exynos.ExynosDisplayFactory.3
                    @Override // android.os.CountDownTimer
                    public final void onFinish() {
                        if (ExynosDisplayFactory.this.DEBUG) {
                            GestureWakeup$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("CountDownTimer finished = "),
                                    ExynosDisplayFactory.this.mCountDownTimerCount,
                                    "ExynosDisplayFactory");
                        }
                        ExynosDisplayFactory.this.mCountDownTimerCount = 0;
                    }

                    @Override // android.os.CountDownTimer
                    public final void onTick(long j) {
                        ExynosDisplayFactory exynosDisplayFactory = ExynosDisplayFactory.this;
                        int i3 = exynosDisplayFactory.mCountDownTimerCount;
                        if (i3 <= 0) {
                            for (int i4 = 0; i4 < 2; i4++) {
                                ExynosDisplayFactory exynosDisplayFactory2 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory2
                                                        .mCountDownTimerTable[i4][
                                                        exynosDisplayFactory2.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory2,
                                                exynosDisplayFactory2.mColorModeModeIdx[i4])) {
                                    ExynosDisplayFactory exynosDisplayFactory3 =
                                            ExynosDisplayFactory.this;
                                    String str = exynosDisplayFactory3.mFactoryXMLPath;
                                    String str2 = exynosDisplayFactory3.mColorModeSettingTable[i4];
                                    Log.d("ExynosDisplayFactory", "setCalibrationAPS");
                                    try {
                                        String[] parserFactoryXMLText =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str, str2, "aps");
                                        if (parserFactoryXMLText == null) {
                                            Log.d("ExynosDisplayFactory", "xml aps not found");
                                        } else if (parserFactoryXMLText.length < 1) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml array size wrong: "
                                                            + parserFactoryXMLText.length);
                                        } else {
                                            String str3 = parserFactoryXMLText[0];
                                            String stringFromFile =
                                                    ExynosDisplayUtils.getStringFromFile(
                                                            exynosDisplayFactory3.APS_SYSFS_PATH);
                                            if (stringFromFile != null
                                                    && !stringFromFile.equals("0")) {
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory3.APS_SYSFS_PATH, str3);
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 1) {
                            for (int i5 = 0; i5 < 2; i5++) {
                                ExynosDisplayFactory exynosDisplayFactory4 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory4
                                                        .mCountDownTimerTable[i5][
                                                        exynosDisplayFactory4.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory4,
                                                exynosDisplayFactory4.mColorModeModeIdx[i5])) {
                                    ExynosDisplayFactory exynosDisplayFactory5 =
                                            ExynosDisplayFactory.this;
                                    String str4 = exynosDisplayFactory5.mFactoryXMLPath;
                                    String str5 = exynosDisplayFactory5.mColorModeSettingTable[i5];
                                    Log.d("ExynosDisplayFactory", "setCalibrationDEGAMMA");
                                    try {
                                        String[] parserFactoryXMLText2 =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str4, str5, "degamma");
                                        String str6 = exynosDisplayFactory5.EXTENSION_OFF;
                                        if (parserFactoryXMLText2 == null
                                                || parserFactoryXMLText2.length < 1) {
                                            String[] parserFactoryXMLText3 =
                                                    ExynosDisplayUtils.parserFactoryXMLText(
                                                            10, 0, str4, str5, "degamma");
                                            if (parserFactoryXMLText3 != null
                                                    && parserFactoryXMLText3.length >= 1) {
                                                exynosDisplayFactory5.sysfsWriteDEGAMMA(
                                                        parserFactoryXMLText3[0], str6);
                                                String[] parserFactoryXMLText4 =
                                                        ExynosDisplayUtils.parserFactoryXMLText(
                                                                8, 0, str4, str5, "degamma");
                                                if (parserFactoryXMLText4 != null
                                                        && parserFactoryXMLText4.length >= 1) {
                                                    exynosDisplayFactory5.sysfsWriteDEGAMMA(
                                                            parserFactoryXMLText4[0],
                                                            exynosDisplayFactory5.EXTENSION_ON);
                                                }
                                            }
                                        } else {
                                            exynosDisplayFactory5.sysfsWriteDEGAMMA(
                                                    parserFactoryXMLText2[0], str6);
                                        }
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 2) {
                            for (int i6 = 0; i6 < 2; i6++) {
                                ExynosDisplayFactory exynosDisplayFactory6 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory6
                                                        .mCountDownTimerTable[i6][
                                                        exynosDisplayFactory6.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory6,
                                                exynosDisplayFactory6.mColorModeModeIdx[i6])) {
                                    ExynosDisplayFactory exynosDisplayFactory7 =
                                            ExynosDisplayFactory.this;
                                    String str7 = exynosDisplayFactory7.mFactoryXMLPath;
                                    String str8 = exynosDisplayFactory7.mColorModeSettingTable[i6];
                                    Log.d("ExynosDisplayFactory", "setCalibrationGAMMA");
                                    try {
                                        String[] parserFactoryXMLText5 =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str7, str8, "gamma");
                                        String str9 = exynosDisplayFactory7.EXTENSION_OFF;
                                        if (parserFactoryXMLText5 == null
                                                || parserFactoryXMLText5.length < 1) {
                                            String[] parserFactoryXMLText6 =
                                                    ExynosDisplayUtils.parserFactoryXMLText(
                                                            10, 0, str7, str8, "gamma");
                                            if (parserFactoryXMLText6 != null
                                                    && parserFactoryXMLText6.length >= 1) {
                                                exynosDisplayFactory7.sysfsWriteGAMMA(
                                                        parserFactoryXMLText6[0], str9);
                                                String[] parserFactoryXMLText7 =
                                                        ExynosDisplayUtils.parserFactoryXMLText(
                                                                8, 0, str7, str8, "gamma");
                                                if (parserFactoryXMLText7 != null
                                                        && parserFactoryXMLText7.length >= 1) {
                                                    exynosDisplayFactory7.sysfsWriteGAMMA(
                                                            parserFactoryXMLText7[0],
                                                            exynosDisplayFactory7.EXTENSION_ON);
                                                }
                                            }
                                        } else {
                                            exynosDisplayFactory7.sysfsWriteGAMMA(
                                                    parserFactoryXMLText5[0], str9);
                                        }
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 3) {
                            for (int i7 = 0; i7 < 2; i7++) {
                                ExynosDisplayFactory exynosDisplayFactory8 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory8
                                                        .mCountDownTimerTable[i7][
                                                        exynosDisplayFactory8.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory8,
                                                exynosDisplayFactory8.mColorModeModeIdx[i7])) {
                                    ExynosDisplayFactory exynosDisplayFactory9 =
                                            ExynosDisplayFactory.this;
                                    String str10 = exynosDisplayFactory9.mFactoryXMLPath;
                                    String str11 = exynosDisplayFactory9.mColorModeSettingTable[i7];
                                    Log.d("ExynosDisplayFactory", "setCalibrationGAMMA_MATRIX");
                                    try {
                                        String[] parserFactoryXMLText8 =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str10, str11, "gamma_matrix");
                                        if (parserFactoryXMLText8 == null) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml gamma_matrix not found");
                                        } else if (parserFactoryXMLText8.length < 1) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml array size wrong: "
                                                            + parserFactoryXMLText8.length);
                                        } else {
                                            String str12 = parserFactoryXMLText8[0];
                                            String stringFromFile2 =
                                                    ExynosDisplayUtils.getStringFromFile(
                                                            exynosDisplayFactory9
                                                                    .GAMMA_MATRIX_SYSFS_PATH);
                                            if (stringFromFile2 != null
                                                    && !stringFromFile2.equals("0")) {
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory9
                                                                .GAMMA_MATRIX_SYSFS_PATH,
                                                        str12);
                                            }
                                        }
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 4) {
                            for (int i8 = 0; i8 < 2; i8++) {
                                ExynosDisplayFactory exynosDisplayFactory10 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory10
                                                        .mCountDownTimerTable[i8][
                                                        exynosDisplayFactory10.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory10,
                                                exynosDisplayFactory10.mColorModeModeIdx[i8])) {
                                    ExynosDisplayFactory exynosDisplayFactory11 =
                                            ExynosDisplayFactory.this;
                                    String str13 = exynosDisplayFactory11.mFactoryXMLPath;
                                    String str14 =
                                            exynosDisplayFactory11.mColorModeSettingTable[i8];
                                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                            exynosDisplayFactory11.mCountDownTimerCount - 4,
                                            "setCalibrationHSC48_LCG: ",
                                            "ExynosDisplayFactory");
                                    for (int i9 = 0; i9 < 3; i9++) {
                                        try {
                                            String[] parserFactoryXMLText9 =
                                                    ExynosDisplayUtils.parserFactoryXMLText(
                                                            i9, 0, str13, str14, "hsc48_lcg");
                                            if (parserFactoryXMLText9 != null
                                                    && parserFactoryXMLText9.length >= 1) {
                                                String str15 = parserFactoryXMLText9[0];
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory11.HSC48_IDX_SYSFS_PATH,
                                                        Integer.toString(i9));
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory11.HSC48_LCG_SYSFS_PATH,
                                                        str15);
                                            }
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml hsc48_lcg not found");
                                            break;
                                        } catch (Exception e5) {
                                            e5.printStackTrace();
                                        }
                                    }
                                }
                            }
                        } else if (i3 <= 5) {
                            for (int i10 = 0; i10 < 2; i10++) {
                                ExynosDisplayFactory exynosDisplayFactory12 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory12
                                                        .mCountDownTimerTable[i10][
                                                        exynosDisplayFactory12.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory12,
                                                exynosDisplayFactory12.mColorModeModeIdx[i10])) {
                                    ExynosDisplayFactory exynosDisplayFactory13 =
                                            ExynosDisplayFactory.this;
                                    String str16 = exynosDisplayFactory13.mFactoryXMLPath;
                                    String str17 =
                                            exynosDisplayFactory13.mColorModeSettingTable[i10];
                                    Log.d("ExynosDisplayFactory", "setCalibrationHSC");
                                    try {
                                        String[] parserFactoryXMLText10 =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str16, str17, "hsc");
                                        if (parserFactoryXMLText10 == null) {
                                            Log.d("ExynosDisplayFactory", "xml hsc not found");
                                        } else if (parserFactoryXMLText10.length < 1) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml array size wrong: "
                                                            + parserFactoryXMLText10.length);
                                        } else {
                                            String str18 = parserFactoryXMLText10[0];
                                            String stringFromFile3 =
                                                    ExynosDisplayUtils.getStringFromFile(
                                                            exynosDisplayFactory13.HSC_SYSFS_PATH);
                                            if (stringFromFile3 != null
                                                    && !stringFromFile3.equals("0")) {
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory13.HSC_SYSFS_PATH,
                                                        str18);
                                            }
                                        }
                                    } catch (Exception e6) {
                                        e6.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 6) {
                            for (int i11 = 0; i11 < 2; i11++) {
                                ExynosDisplayFactory exynosDisplayFactory14 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory14
                                                        .mCountDownTimerTable[i11][
                                                        exynosDisplayFactory14.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory14,
                                                exynosDisplayFactory14.mColorModeModeIdx[i11])) {
                                    ExynosDisplayFactory exynosDisplayFactory15 =
                                            ExynosDisplayFactory.this;
                                    String str19 = exynosDisplayFactory15.mFactoryXMLPath;
                                    String str20 =
                                            exynosDisplayFactory15.mColorModeSettingTable[i11];
                                    Log.d("ExynosDisplayFactory", "setCalibrationSCL");
                                    try {
                                        String[] parserFactoryXMLText11 =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str19, str20, "scl");
                                        if (parserFactoryXMLText11 == null) {
                                            Log.d("ExynosDisplayFactory", "xml scl not found");
                                        } else if (parserFactoryXMLText11.length < 1) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml array size wrong: "
                                                            + parserFactoryXMLText11.length);
                                        } else {
                                            String str21 = parserFactoryXMLText11[0];
                                            String stringFromFile4 =
                                                    ExynosDisplayUtils.getStringFromFile(
                                                            exynosDisplayFactory15.SCL_SYSFS_PATH);
                                            if (stringFromFile4 != null
                                                    && !stringFromFile4.equals("0")) {
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory15.SCL_SYSFS_PATH,
                                                        str21);
                                            }
                                        }
                                    } catch (Exception e7) {
                                        e7.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 9) {
                            for (int i12 = 0; i12 < 2; i12++) {
                                ExynosDisplayFactory exynosDisplayFactory16 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory16
                                                        .mCountDownTimerTable[i12][
                                                        exynosDisplayFactory16.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory16,
                                                exynosDisplayFactory16.mColorModeModeIdx[i12])) {
                                    ExynosDisplayFactory exynosDisplayFactory17 =
                                            ExynosDisplayFactory.this;
                                    String str22 = exynosDisplayFactory17.mFactoryXMLPath;
                                    String str23 =
                                            exynosDisplayFactory17.mColorModeSettingTable[i12];
                                    int i13 = exynosDisplayFactory17.mCountDownTimerCount - 7;
                                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                            i13,
                                            "setCalibrationCGC17_ENC + ",
                                            "ExynosDisplayFactory");
                                    for (int i14 = 0; i14 < 17; i14++) {
                                        try {
                                            String[] parserFactoryXMLText12 =
                                                    ExynosDisplayUtils.parserFactoryXMLText(
                                                            i13, i14, str22, str23, "cgc17_enc");
                                            if (parserFactoryXMLText12 != null
                                                    && parserFactoryXMLText12.length >= 1) {
                                                String str24 = parserFactoryXMLText12[0];
                                                exynosDisplayFactory17.sysfsWriteCGC17_IDX(
                                                        (i13 * 17) + i14);
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory17.CGC17_ENC_SYSFS_PATH,
                                                        str24);
                                            }
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml cgc17_enc not found");
                                            break;
                                        } catch (Exception e8) {
                                            e8.printStackTrace();
                                        }
                                    }
                                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                            i13,
                                            "setCalibrationCGC17_ENC - ",
                                            "ExynosDisplayFactory");
                                }
                            }
                        } else if (i3 <= 10) {
                            for (int i15 = 0; i15 < 2; i15++) {
                                ExynosDisplayFactory exynosDisplayFactory18 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory18
                                                        .mCountDownTimerTable[i15][
                                                        exynosDisplayFactory18.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory18,
                                                exynosDisplayFactory18.mColorModeModeIdx[i15])) {
                                    ExynosDisplayFactory exynosDisplayFactory19 =
                                            ExynosDisplayFactory.this;
                                    exynosDisplayFactory19.getClass();
                                    Log.d("ExynosDisplayFactory", "setCalibrationCGC17_DEC");
                                    ExynosDisplayUtils.sysfsWriteSting(
                                            exynosDisplayFactory19.CGC17_DEC_SYSFS_PATH, "7");
                                }
                            }
                        } else if (i3 <= 11) {
                            for (int i16 = 0; i16 < 2; i16++) {
                                ExynosDisplayFactory exynosDisplayFactory20 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory20
                                                        .mCountDownTimerTable[i16][
                                                        exynosDisplayFactory20.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory20,
                                                exynosDisplayFactory20.mColorModeModeIdx[i16])) {
                                    ExynosDisplayFactory exynosDisplayFactory21 =
                                            ExynosDisplayFactory.this;
                                    String str25 = exynosDisplayFactory21.mFactoryXMLPath;
                                    String str26 =
                                            exynosDisplayFactory21.mColorModeSettingTable[i16];
                                    Log.d("ExynosDisplayFactory", "setCalibrationCGC17_CON");
                                    try {
                                        String[] parserFactoryXMLText13 =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str25, str26, "cgc17_con");
                                        if (parserFactoryXMLText13 == null) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml cgc17_con not found");
                                        } else if (parserFactoryXMLText13.length < 1) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml array size wrong: "
                                                            + parserFactoryXMLText13.length);
                                        } else {
                                            String str27 = parserFactoryXMLText13[0];
                                            String stringFromFile5 =
                                                    ExynosDisplayUtils.getStringFromFile(
                                                            exynosDisplayFactory21
                                                                    .CGC17_CON_SYSFS_PATH);
                                            if (stringFromFile5 != null
                                                    && !stringFromFile5.equals("0")) {
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory21.CGC17_CON_SYSFS_PATH,
                                                        str27);
                                            }
                                        }
                                    } catch (Exception e9) {
                                        e9.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 12) {
                            for (int i17 = 0; i17 < 2; i17++) {
                                ExynosDisplayFactory exynosDisplayFactory22 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory22
                                                        .mCountDownTimerTable[i17][
                                                        exynosDisplayFactory22.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory22,
                                                exynosDisplayFactory22.mColorModeModeIdx[i17])) {
                                    ExynosDisplayFactory exynosDisplayFactory23 =
                                            ExynosDisplayFactory.this;
                                    String str28 = exynosDisplayFactory23.mFactoryXMLPath;
                                    String str29 =
                                            exynosDisplayFactory23.mColorModeSettingTable[i17];
                                    Log.d("ExynosDisplayFactory", "setCalibrationCGC_DITHER");
                                    try {
                                        String[] parserFactoryXMLText14 =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str28, str29, "cgc_dither");
                                        if (parserFactoryXMLText14 == null) {
                                            Log.d("ExynosDisplayFactory", "xml degamma not found");
                                        } else if (parserFactoryXMLText14.length < 1) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml array size wrong: "
                                                            + parserFactoryXMLText14.length);
                                        } else {
                                            ExynosDisplayUtils.sysfsWriteSting(
                                                    exynosDisplayFactory23.CGC_DITHER_SYSFS_PATH,
                                                    parserFactoryXMLText14[0]);
                                        }
                                    } catch (Exception e10) {
                                        e10.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 13) {
                            for (int i18 = 0; i18 < 2; i18++) {
                                ExynosDisplayFactory exynosDisplayFactory24 =
                                        ExynosDisplayFactory.this;
                                if (exynosDisplayFactory24
                                                        .mCountDownTimerTable[i18][
                                                        exynosDisplayFactory24.mCountDownTimerCount]
                                                != 0
                                        && ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                                exynosDisplayFactory24,
                                                exynosDisplayFactory24.mColorModeModeIdx[i18])) {
                                    ExynosDisplayFactory exynosDisplayFactory25 =
                                            ExynosDisplayFactory.this;
                                    String str30 = exynosDisplayFactory25.mFactoryXMLPath;
                                    String str31 =
                                            exynosDisplayFactory25.mColorModeSettingTable[i18];
                                    Log.d("ExynosDisplayFactory", "setCalibrationDE");
                                    try {
                                        String[] parserFactoryXMLText15 =
                                                ExynosDisplayUtils.parserFactoryXMLText(
                                                        0, 0, str30, str31, "de");
                                        if (parserFactoryXMLText15 == null) {
                                            Log.d("ExynosDisplayFactory", "xml de not found");
                                        } else if (parserFactoryXMLText15.length < 1) {
                                            Log.d(
                                                    "ExynosDisplayFactory",
                                                    "xml array size wrong: "
                                                            + parserFactoryXMLText15.length);
                                        } else {
                                            String str32 = parserFactoryXMLText15[0];
                                            String stringFromFile6 =
                                                    ExynosDisplayUtils.getStringFromFile(
                                                            exynosDisplayFactory25.DE_SYSFS_PATH);
                                            if (stringFromFile6 != null
                                                    && !stringFromFile6.equals("0")) {
                                                ExynosDisplayUtils.sysfsWriteSting(
                                                        exynosDisplayFactory25.DE_SYSFS_PATH,
                                                        str32);
                                            }
                                        }
                                    } catch (Exception e11) {
                                        e11.printStackTrace();
                                    }
                                }
                            }
                        } else if (i3 <= 14) {
                            ExynosDisplayFactory.m482$$Nest$msetCalibrationMODE_IDX(
                                    exynosDisplayFactory, 0);
                        }
                        ExynosDisplayFactory.this.mCountDownTimerCount++;
                    }
                };
        this.mFactoryXMLPath = null;
        this.mLocalHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.android.server.display.exynos.ExynosDisplayFactory.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ExynosDisplayFactory exynosDisplayFactory = ExynosDisplayFactory.this;
                        exynosDisplayFactory.startCountDownTimer(
                                exynosDisplayFactory.mFactoryXMLPath);
                    }
                },
                0L);
    }

    public static int getItemEnable(String str, String str2, String str3) {
        try {
            String[] parserFactoryXMLText =
                    ExynosDisplayUtils.parserFactoryXMLText(0, 0, str, str2, str3);
            if (parserFactoryXMLText != null && parserFactoryXMLText.length >= 1) {
                return Integer.parseInt(parserFactoryXMLText[0].split("\\s*,\\s*")[0]);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final void startCountDownTimer(String str) {
        int[][] iArr;
        if (str == null) {
            str = ExynosDisplayUtils.getPathWithPanel(this.DQE_COEF_XML_FILE_PATH);
        }
        this.mFactoryXMLPath = str;
        if (ExynosDisplayUtils.existFile(str)) {
            boolean z = this.DEBUG;
            if (z) {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "startCountDownTimer: xml_path=", str, "ExynosDisplayFactory");
            }
            int i = 0;
            while (true) {
                iArr = this.mCountDownTimerTable;
                if (i < 2) {
                    for (int i2 = 0; i2 < 20; i2++) {
                        iArr[i][i2] = 0;
                    }
                    i++;
                } else {
                    try {
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            String[] parserXMLNodeText = ExynosDisplayUtils.parserXMLNodeText(str);
            if (parserXMLNodeText != null && parserXMLNodeText.length >= 1) {
                if (z) {
                    Log.d("ExynosDisplayFactory", "xml version: " + parserXMLNodeText[0]);
                }
                for (int i3 = 0; i3 < 2; i3++) {
                    String str2 = this.mColorModeSettingTable[i3];
                    if (getItemEnable(str, str2, "aps") > 0) {
                        iArr[i3][0] = 1;
                    }
                    if (getItemEnable(str, str2, "degamma") > 0) {
                        iArr[i3][1] = 1;
                    }
                    if (getItemEnable(str, str2, "gamma") > 0) {
                        iArr[i3][2] = 1;
                    }
                    if (getItemEnable(str, str2, "gamma_matrix") > 0) {
                        iArr[i3][3] = 1;
                    }
                    int itemEnable = getItemEnable(str, str2, "hsc");
                    for (int i4 = 4; i4 <= 5; i4++) {
                        if (itemEnable > 0) {
                            iArr[i3][i4] = 1;
                        }
                    }
                    if (getItemEnable(str, str2, "scl") > 0) {
                        iArr[i3][6] = 1;
                    }
                    int itemEnable2 = getItemEnable(str, str2, "cgc17_con");
                    for (int i5 = 7; i5 <= 11; i5++) {
                        if (itemEnable2 > 0) {
                            iArr[i3][i5] = 1;
                        }
                    }
                    if (getItemEnable(str, str2, "cgc_dither") > 0) {
                        iArr[i3][12] = 1;
                    }
                    if (getItemEnable(str, str2, "de") > 0) {
                        iArr[i3][13] = 1;
                    }
                    Log.d("ExynosDisplayFactory", str2 + " enable " + Arrays.toString(iArr[i3]));
                }
                AnonymousClass3 anonymousClass3 = this.mCountdownTimer;
                if (anonymousClass3 != null) {
                    this.mCountDownTimerCount = 0;
                    anonymousClass3.cancel();
                    start();
                    return;
                }
                return;
            }
            Log.d("ExynosDisplayFactory", "xml version not found");
        }
    }

    public final String sysfsPathReplace(String str) {
        String str2 = this.HDR_SYSFS_PATH;
        return (str2 != null && BatteryService$$ExternalSyntheticOutline0.m45m(str2))
                ? str.replaceFirst(this.DQE_SYSFS_PATH, str2)
                : str;
    }

    public final void sysfsWriteCGC17_IDX(int i) {
        ExynosDisplayUtils.sysfsWriteSting(
                this.CGC17_IDX_SYSFS_PATH,
                Integer.toString(i / 17) + " " + Integer.toString(i % 17));
    }

    public final void sysfsWriteDEGAMMA(String str, String str2) {
        String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.DEGAMMA_SYSFS_PATH);
        if (stringFromFile == null || stringFromFile.equals("0") || str2 == null) {
            return;
        }
        ExynosDisplayUtils.sysfsWriteSting(this.DEGAMMA_EXT_SYSFS_PATH, str2);
        ExynosDisplayUtils.sysfsWriteSting(this.DEGAMMA_SYSFS_PATH, str);
    }

    public final void sysfsWriteGAMMA(String str, String str2) {
        String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.GAMMA_SYSFS_PATH);
        if (stringFromFile == null || stringFromFile.equals("0") || str2 == null) {
            return;
        }
        ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_EXT_SYSFS_PATH, str2);
        ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_SYSFS_PATH, str);
    }
}
