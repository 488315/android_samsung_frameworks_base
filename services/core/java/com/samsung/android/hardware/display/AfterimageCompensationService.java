package com.samsung.android.hardware.display;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.net.util.NetworkConstants;
import android.os.Build;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;
import android.view.WindowManager;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AfterimageCompensationService {
    public static final String[] AFPC_KEYS = {"BURN_IN_INIT_POC_VECTOR", "BURN_IN_POC_APPLY_COUNT", "BURN_IN_JND"};
    public static final String[] mAFPC_KEYS = {"ApplyCount", "JND", "AvgLum", "MaxBDI", "NBDI", "EffAvgLum", "EffMaxBDI", "EffNBDI", "ApplyCount_sub", "JND_sub", "AvgLum_sub", "MaxBDI_sub", "NBDI_sub", "EffAvgLum_sub", "EffMaxBDI_sub", "EffNBDI_sub"};
    public boolean AfcStateCondition;
    public int AfpcPeriodMax;
    public boolean mAFPCVersion1;
    public int mAfcType;
    public int mAfpcHeight;
    public int mAfpcHeight_sub;
    public int mAfpcPanelNumber_main;
    public int mAfpcPanelNumber_sub;
    public int mAfpcSize;
    public int mAfpcSize_sub;
    public int mAfpcWidth;
    public int mAfpcWidth_sub;
    public int mApplyScaleEffect;
    public float mApplyValue;
    public int[] mBrightnessBorderValue;
    public final Context mContext;
    public boolean mMcaRewrited;
    public boolean mPocInitVector;
    public boolean mThreadAFPC;
    public boolean mThreadAPC;
    public long mThreadSleepTime;
    public Thread.State state;
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public final int AFC_TYPE_AFC = 0;
    public final int AFC_TYPE_AFC_V1 = 1;
    public final int AFC_TYPE_AFPC = 2;
    public final int AFC_TYPE_MAFPC = 3;
    public final int AFC_TYPE_AFPC_V2 = 4;
    public final int AFC_TYPE_MAFPC_V2 = 5;
    public final int AFC_TYPE_MAFPC_V3 = 6;
    public final int AFC_TYPE_MAFPC_V4 = 7;
    public final int AFC_TYPE_MAFPC_V5 = 8;
    public final int AFC_TYPE_MAFPC_V6 = 9;
    public final int AFC_TYPE_MAFPC_V7 = 10;
    public final int AFC_TYPE_MAFPC_V8 = 11;
    public final int MODEL_BEYOND0 = 190100;
    public final int MODEL_BEYOND1 = 190101;
    public final int MODEL_BEYOND2 = 190102;
    public final int MODEL_BEYONDX = 190103;
    public final int MODEL_DAVINC1 = 190201;
    public final int MODEL_DAVINC2 = 190202;
    public final int MODEL_X1 = 200101;
    public final int MODEL_Y2 = 200102;
    public final int MODEL_Z3 = 200103;
    public final int MODEL_C1 = 200201;
    public final int MODEL_C2 = 200202;
    public final int MODEL_TOP = 200203;
    public final int MODEL_O1 = 210101;
    public final int MODEL_T2 = 210102;
    public final int MODEL_P3 = 210103;
    public final int MODEL_B2 = 210201;
    public final int MODEL_Q2 = 210202;
    public final int MODEL_R0 = 220101;
    public final int MODEL_G0 = 220102;
    public final int MODEL_B0 = 220103;
    public final int MODEL_B4 = 220201;
    public final int MODEL_Q4_MAIN = 220202;
    public final int MODEL_Q4_SUB = 220203;
    public final int MODEL_DM1 = 230101;
    public final int MODEL_DM2 = 230102;
    public final int MODEL_DM3 = 230103;
    public final int MODEL_B5 = 230201;
    public final int MODEL_Q5_MAIN = 230202;
    public final int MODEL_Q5_SUB = 230203;
    public final int MODEL_E1 = 240101;
    public final int MODEL_E2 = 240102;
    public final int MODEL_E3 = 240103;
    public final int MODEL_B6 = 240201;
    public final int MODEL_Q6_MAIN = 240202;
    public final int MODEL_Q6_SUB = 240203;
    public final int MODEL_Q6A_MAIN = 240204;
    public final int MODEL_Q6A_SUB = 240205;
    public final long MILLIS_AFC_PERIOD = 10000;
    public final long MILLIS_AFPC_PERIOD = 60000;
    public final long MILLIS_AFPC_V2_PERIOD = 30000;
    public final int MAX_APPLY_COUNT = 10;
    public final double APPLY_BDI = 300.0d;
    public final double APPLY_AVG_LUM = 80.0d;
    public final double APPLY_AVG_LUM_V2 = 75.0d;
    public final double APPLY_AVG_LUM_V3 = 70.0d;
    public final double APPLY_AVG_LUM_V4 = 70.0d;
    public final double APPLY_AVG_LUM_V5 = 50.0d;
    public final double APPLY_AVG_LUM_V6 = 50.0d;
    public final double APPLY_AVG_LUM_V7 = 50.0d;
    public final double APPLY_AVG_LUM_V8 = 50.0d;
    public final String SYSFS_COPR_FILE_PATH = "/sys/class/lcd/panel/copr_roi";
    public final String SYSFS_SENSOR_COPR_FILE_PATH = "/sys/class/sensors/light_sensor/copr_roix";
    public final String SYSFS_BRIGHTNESS_FILE_PATH = "/sys/class/lcd/panel/brt_avg";
    public final String SYSFS_BRIGHTNESS_FILE_PATH_SUB = "/sys/class/lcd/panel1/brt_avg";
    public final String SYSFS_PANEL_CELL_ID = "/sys/class/lcd/panel/cell_id";
    public final String SYSFS_PANEL_CELL_ID_SUB = "/sys/class/lcd/panel1/cell_id";
    public final String SYSFS_PANEL_POC = "/sys/class/lcd/panel/poc";
    public final String AFC_DIRECTORY = "/efs/afc";
    public final String AFC_DIRECTORY_SUB = "/efs/afc1";
    public final String AFC_PANEL_CELL_ID = "/efs/afc/cell_id";
    public final String AFC_PANEL_CELL_ID_SUB = "/efs/afc1/cell_id";
    public final String AFC_LOGGING_DATA = "/efs/afc/logging_data";
    public final String AFC_REG_DATA = "/efs/afc/afc_data";
    public final String AFC_ORIGINAL_VEC = "/efs/afc/org.vec";
    public final String AFC_TIME_DATA = "/efs/afc/time_data";
    public final String AFC_TIME_DATA_SUB = "/efs/afc1/time_data";
    public final String AFC_POC_DATA = "/efs/afc/poc_data";
    public final String AFC_POC_DATA_SUB = "/efs/afc1/poc_data";
    public final String AFC_APPLY_COUNT = "/efs/afc/apply_count";
    public final String AFC_APPLY_COUNT_SUB = "/efs/afc1/apply_count";
    public final String AFC_APPLY_LDU = "/efs/afc/apply_ldu";
    public final String AFC_DIFF_DATA = "/efs/afc/diff_data";
    public final String AFC_DIFF_DATA_SUB = "/efs/afc1/diff_data";
    public final String AFC_STATE = "/efs/afc/afc_state";
    public final String AFC_MDNIE_BLOCK = "/efs/afc/mdnie_block";
    public final String AFC_MDNIE_BLOCK_SUB = "/efs/afc1/mdnie_block";
    public final String AFC_TEST_BLOCK = "/efs/afc/test_block";
    public final String MCA_REWRITED = "/efs/afc/rewrited";
    public final String MCA_ORG_VEC = "/efs/afc/original.vec";
    public final String DEV_POC = "/dev/poc";
    public final String RECOVERY_POC = "/cache/recovery/poc.vec";
    public final String BEYOND_POC_COMMAND = "7 0 551186";
    public final String POC_ERASE_COMMAND = "7 0 ";
    public final String AFC_DEFAULT_VALUE = "0";
    public final String AFC_INIT_VALUE = "1 255 255 255 255 255 255 255 255 255 255 255 255";
    public final int AFC_LOGGING_DATA_SIZE = 15;
    public final int AFC_MAX_COUNT = 1100000;
    public final int AFC_RGB_NUMBER = 3;
    public final int BRIGHTNESS_MAX_NUMBER = 11;
    public final int AFC_COPR_ROI_MAX_NUMBER = 12;
    public final int AFPC_PERIOD_COUNT = 1440;
    public final int AFPC_V2_PERIOD_COUNT = 2880;
    public final int AFC_COPR_ROI_MAX_VALUE = 256;
    public final int AFC_COPR_ROI_MAX_INDEX = FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP;
    public final int AFC_XRGB_MAX_VALUE = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    public final int AFC_XRGB_MAX_INDEX = 256;
    public final int AFC_LUX_MAX_NUMBER = 11;
    public final int AFC_RGB_MAX_NUMBER = 360;
    public final int AFC_RGB_ADDRESS = 120;
    public final int AFC_XRGB_BASE_POINT = 136;
    public final int AFC_XY_COPR_ROI_SIZE = 16;
    public final int AFC_COPR_ROI_TABLE_SIZE = 771;
    public final int AFC_XRGB_TABLE_SIZE = FrameworkStatsLog.APP_STANDBY_BUCKET_CHANGED__MAIN_REASON__MAIN_USAGE;
    public final int AFC_TABLE_SIZE = 15840;
    public final int AFC_STATE_0 = 0;
    public final int AFC_STATE_1 = 1;
    public final int AFC_STATE_2 = 2;
    public final int AFC_STATE_3 = 3;
    public final int AFC_STATE_END = 4;
    public final int EFF_NBDI_MAX_NUM = 20;
    public final int EFF_NBDI_V3_MAX_NUM = 30;
    public final int EFF_NBDI_V4_MAX_NUM = 30;
    public final int EFF_NBDI_V5_MAX_NUM = 50;
    public final int EFF_NBDI_V6_MAX_NUM = 100;
    public final int EFF_NBDI_V7_MAX_NUM = 100;
    public final int EFF_NBDI_V8_MAX_NUM = 100;
    public final int BRIGHTNESS_MAX_VALUE = NetworkConstants.ETHER_MTU;
    public Thread mAfcThread = null;
    public ScreenWatchingReceiver mScreenWatchingReceiver = null;
    public int mAfcState = 0;
    public boolean mAfcLoggingDataValid = false;
    public boolean AfcThreadCondition = true;
    public boolean AfcThreadAODCondition = false;
    public boolean AfcThreadTerminateCondition = false;
    public boolean isRunningCameraApp = false;
    public int AodBrightness = 0;
    public int mApplyCount = 0;
    public int mApplyCount_sub = 0;
    public float mApplyValue_sub = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mCurrentBrightnessValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public int[] mCoprRoi = new int[12];
    public int mLuminance = 0;
    public int mLuminance_sub = 0;
    public int AfpcPeriodCount = 0;
    public int AfpcPeriodCount_sub = 0;
    public long mThreadSleepTimeAod = 1000;
    public int interpolationCount = 0;
    public int[] interpolationCoprRoi = new int[12];
    public int interpolationLuminance = 0;
    public double[] interpolationCoprRoiDouble = new double[12];
    public double interpolationLuminanceDouble = 0.0d;
    public float[] mAfpcJndRef = {7.0f, 11.0f};
    public float[] mAfpcJndRefV2 = {3.0f, 5.0f, 7.0f, 9.0f, 11.0f};
    public float[] effNbdiTh = {150.0f, 150.0f, 200.0f, 200.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f};
    public float[] effNbdiTh_V3 = {100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 150.0f, 150.0f, 150.0f, 150.0f, 150.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f};
    public float[] effNbdiTh_V4 = {100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 150.0f, 150.0f, 150.0f, 150.0f, 150.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f};
    public float[] effNbdiTh_V5 = {100.0f, 100.0f, 100.0f, 100.0f, 100.0f, 150.0f, 150.0f, 150.0f, 150.0f, 150.0f, 200.0f, 200.0f, 200.0f, 200.0f, 200.0f, 250.0f, 250.0f, 250.0f, 250.0f, 250.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f, 300.0f};
    public float[] effNbdiTh_V6 = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f};
    public float[] effNbdiTh_V7 = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f};
    public float[] effNbdiTh_V8 = {30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f, 30.0f};
    public double mAvgLum = 0.0d;
    public double mMaxBDI = 0.0d;
    public double mNBDI = 0.0d;
    public double mEffAvgLum = 0.0d;
    public double mEffMaxBDI = 0.0d;
    public double mEffNBDI = 0.0d;
    public double mAvgLum_sub = 0.0d;
    public double mMaxBDI_sub = 0.0d;
    public double mNBDI_sub = 0.0d;
    public double mEffAvgLum_sub = 0.0d;
    public double mEffMaxBDI_sub = 0.0d;
    public double mEffNBDI_sub = 0.0d;
    public SemHqmManager mSemHqmManager = null;
    public WindowManager mWindowManager = null;

    private static native int nativeDataApply(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native float nativeDataEvaluate();

    private static native int nativeDataInit(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native int nativeDataInitSub(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    private static native int nativeDataOff();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataRead(int i, int i2, int i3, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadAvgLum();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadAvgLumSub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffAvgLum();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffAvgLumSub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffMaxBDI();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffMaxBDISub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffNBDI();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadEffNBDISub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadMaxBDI();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadMaxBDISub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadNBDI();

    /* JADX INFO: Access modifiers changed from: private */
    public static native double nativeDataReadNBDISub();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataReadSub(int i, int i2, int i3, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataSave(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataSaveSub(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataTerminate(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataTerminateSub(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataUpdate(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataUpdateSub(int i);

    private static native int nativeDataVerify();

    private static native int nativeDataWrite(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeDataWriteV2(int i);

    /* JADX WARN: Multi-variable type inference failed */
    public AfterimageCompensationService(Context context) {
        Object[] objArr;
        Object[] objArr2;
        int i;
        int i2;
        this.mBrightnessBorderValue = new int[11];
        this.AfcStateCondition = false;
        this.mThreadAPC = false;
        this.mThreadAFPC = false;
        this.mAFPCVersion1 = false;
        this.mMcaRewrited = false;
        this.mPocInitVector = false;
        this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mThreadSleepTime = 0L;
        this.AfpcPeriodMax = 0;
        this.mAfcType = 0;
        this.mAfpcPanelNumber_main = 0;
        this.mAfpcPanelNumber_sub = 0;
        this.mAfpcSize = 0;
        this.mAfpcHeight = 0;
        this.mAfpcWidth = 0;
        this.mAfpcSize_sub = 0;
        this.mAfpcHeight_sub = 0;
        this.mAfpcWidth_sub = 0;
        this.mApplyScaleEffect = 180;
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("com.sec.android.app.server.power.DISPLAY_ON_TIME");
        intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
        this.mContext.registerReceiver(new ScreenWatchingReceiver(), intentFilter);
        File file = new File("/efs/afc");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File("/efs/afc1");
        if (!file2.exists()) {
            file2.mkdirs();
        }
        new File("/sys/class/lcd/panel/copr_roi").exists();
        new File("/sys/class/lcd/panel/brt_avg").exists();
        this.mAfcType = this.mContext.getResources().getInteger(R.integer.button_pressed_animation_duration);
        this.mAfpcPanelNumber_main = this.mContext.getResources().getInteger(R.integer.config_activeTaskDurationHours);
        this.mAfpcPanelNumber_sub = this.mContext.getResources().getInteger(R.integer.config_activityDefaultDur);
        this.mAfpcSize = this.mContext.getResources().getInteger(R.integer.config_activityShortDur);
        this.mAfpcHeight = this.mContext.getResources().getInteger(R.integer.config_MaxConcurrentDownloadsAllowed);
        this.mAfpcWidth = this.mContext.getResources().getInteger(R.integer.config_alertDialogController);
        this.mAfpcSize_sub = this.mContext.getResources().getInteger(R.integer.config_aggregatedPowerStatsSpanDuration);
        this.mAfpcHeight_sub = this.mContext.getResources().getInteger(R.integer.config_accessibilityColorMode);
        this.mAfpcWidth_sub = this.mContext.getResources().getInteger(R.integer.config_allowedUnprivilegedKeepalivePerUid);
        this.mContext.getResources().getIntArray(R.array.config_displayCutoutApproximationRectArray);
        this.mBrightnessBorderValue = this.mContext.getResources().getIntArray(R.array.config_deviceStatesOnWhichToWakeUp);
        this.mContext.getResources().getIntArray(R.array.config_displayCompositionColorSpaces);
        this.mContext.getResources().getIntArray(R.array.config_locationExtraPackageNames);
        this.mContext.getResources().getIntArray(R.array.config_deviceStatesToReverseDefaultDisplayRotationAroundZAxis);
        int i3 = this.mAfcType;
        if (i3 >= 2) {
            this.mThreadAFPC = true;
            if (i3 >= 4) {
                this.mThreadSleepTime = 30000L;
                this.AfpcPeriodMax = 2880;
            } else {
                this.mThreadSleepTime = 60000L;
                this.AfpcPeriodMax = 1440;
            }
        } else if (i3 == 1) {
            this.mThreadAPC = true;
            this.mThreadSleepTime = 10000L;
        }
        if (i3 == 1) {
            this.mAFPCVersion1 = true;
        }
        Slog.i("AfterimageCompensationService", "mAfcType : " + this.mAfcType + ", mThreadAFPC : " + this.mThreadAFPC + ", mThreadSleepTime : " + this.mThreadSleepTime + ", AfpcPeriodMax : " + this.AfpcPeriodMax);
        Slog.i("AfterimageCompensationService", "mAfpcPanelNumber_main : " + this.mAfpcPanelNumber_main + ", mAfpcSize : " + this.mAfpcSize + ", mAfpcHeight : " + this.mAfpcHeight + ", mAfpcWidth : " + this.mAfpcWidth);
        Slog.i("AfterimageCompensationService", "mAfpcPanelNumber_sub : " + this.mAfpcPanelNumber_sub + ", mAfpcSize_sub : " + this.mAfpcSize_sub + ", mAfpcHeight_sub : " + this.mAfpcHeight_sub + ", mAfpcWidth_sub : " + this.mAfpcWidth_sub);
        if (this.mThreadAFPC) {
            update_check_panel_id();
            if (new File("/efs/afc/rewrited").exists()) {
                this.mMcaRewrited = true;
            }
            boolean exists = new File("/efs/afc/poc_data").exists();
            boolean exists2 = new File("/efs/afc/time_data").exists();
            boolean exists3 = new File("/efs/afc/diff_data").exists();
            boolean exists4 = new File("/efs/afc1/poc_data").exists();
            boolean exists5 = new File("/efs/afc1/time_data").exists();
            boolean exists6 = new File("/efs/afc1/diff_data").exists();
            if (nativeDataInit(this.mAfcType, this.mAfpcPanelNumber_main, this.mAfpcSize, this.mAfpcHeight, this.mAfpcWidth, exists ? 1 : 0, exists2 ? 1 : 0, exists3 ? 1 : 0) == 0) {
                processApplyData_main();
                objArr = true;
            } else {
                objArr = false;
            }
            if (nativeDataInitSub(this.mAfcType, this.mAfpcPanelNumber_sub, this.mAfpcSize_sub, this.mAfpcHeight_sub, this.mAfpcWidth_sub, exists4 ? 1 : 0, exists5 ? 1 : 0, exists6 ? 1 : 0) == 0) {
                processApplyData_sub();
                objArr2 = true;
            } else {
                objArr2 = false;
            }
            if (objArr != false || objArr2 != false) {
                if (this.mAfcType == 3 && (i2 = this.mAfpcPanelNumber_main) >= 200101 && i2 <= 200103 && new File("/efs/afc/mdnie_block").exists()) {
                    nativeDataApply(this.mApplyScaleEffect);
                }
                int i4 = this.mAfpcPanelNumber_main;
                if (i4 == 190201 || i4 == 190202) {
                    this.mApplyScaleEffect = 100;
                }
                if (this.mAfcType == 4 && new File("/sys/class/lcd/panel/poc").exists() && new File("/dev/poc").exists() && new File("/cache/recovery/poc.vec").exists()) {
                    Slog.d("AfterimageCompensationService", "AFPC_V2 Update");
                    fileWriteString("/sys/class/lcd/panel/poc", "7 0 " + this.mAfpcSize);
                    fileCopy("/cache/recovery/poc.vec", "/dev/poc");
                    if (new File("/cache/recovery/poc.vec").delete()) {
                        Slog.d("AfterimageCompensationService", "AFPC_V2 Update RECOVERY_POC  delete");
                    }
                    this.mApplyCount++;
                    try {
                        String str = this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(this.mApplyValue));
                        fileWriteString("/efs/afc/apply_count", str);
                        Slog.i("AfterimageCompensationService", "afpcDataWrite - str : " + str);
                    } catch (NumberFormatException e) {
                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                        this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                    }
                }
                if (this.mAfcType == 2 && ((((i = this.mAfpcPanelNumber_main) >= 190101 && i <= 190103) || (i >= 190201 && i <= 190202)) && new File("/sys/class/lcd/panel/poc").exists() && new File("/dev/poc").exists() && new File("/cache/recovery/poc.vec").exists())) {
                    Slog.d("AfterimageCompensationService", "FOTA AFPC");
                    fileWriteString("/sys/class/lcd/panel/poc", "7 0 " + this.mAfpcSize);
                    fileCopy("/cache/recovery/poc.vec", "/dev/poc");
                    if (new File("/cache/recovery/poc.vec").delete()) {
                        Slog.d("AfterimageCompensationService", "FOTA AFPC RECOVERY_POC  delete");
                    }
                }
                if (new File("/efs/afc/org.vec").exists()) {
                    this.mPocInitVector = true;
                }
                if (!new File("/efs/afc/rewrited").exists()) {
                    this.AfcStateCondition = true;
                }
            } else {
                Slog.i("AfterimageCompensationService", "mAFPC init Failed");
            }
        }
        Slog.i("AfterimageCompensationService", "AfterimageCompensationService Init Success");
    }

    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Slog.i("AfterimageCompensationService", "ACTION_BOOT_COMPLETED");
                AfterimageCompensationService afterimageCompensationService = AfterimageCompensationService.this;
                afterimageCompensationService.mSemHqmManager = (SemHqmManager) afterimageCompensationService.mContext.getSystemService("HqmManagerService");
                AfterimageCompensationService afterimageCompensationService2 = AfterimageCompensationService.this;
                afterimageCompensationService2.mWindowManager = (WindowManager) afterimageCompensationService2.mContext.getSystemService("window");
                if (AfterimageCompensationService.this.AfcStateCondition) {
                    AfterimageCompensationService.this.mAfcThread = new AfcThread();
                    AfterimageCompensationService.this.mAfcThread.start();
                    Slog.i("AfterimageCompensationService", "AFC Thread Start");
                    return;
                }
                Slog.i("AfterimageCompensationService", "AfcStateCondition is already done - " + AfterimageCompensationService.this.AfcStateCondition);
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                Slog.i("AfterimageCompensationService", "ACTION_SCREEN_ON");
                AfterimageCompensationService.this.receive_screen_on_intent();
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                Slog.i("AfterimageCompensationService", "ACTION_SCREEN_OFF");
                AfterimageCompensationService.this.receive_screen_off_intent();
                return;
            }
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                Slog.i("AfterimageCompensationService", "ACTION_SHUTDOWN");
                if (AfterimageCompensationService.this.mAfcState >= 0 && AfterimageCompensationService.this.mAfcState <= 4 && AfterimageCompensationService.this.interpolationCount > 0 && AfterimageCompensationService.this.interpolationCount <= 1100000) {
                    AfterimageCompensationService.this.writeLoggingDataEfs();
                }
                if (AfterimageCompensationService.this.AfcStateCondition && AfterimageCompensationService.this.mAfcType == 6) {
                    if (AfterimageCompensationService.this.AfpcPeriodCount > 20) {
                        AfterimageCompensationService.nativeDataTerminate(AfterimageCompensationService.this.AfpcPeriodCount);
                        return;
                    }
                    return;
                }
                if (AfterimageCompensationService.this.AfcStateCondition && AfterimageCompensationService.this.mAfcType == 7) {
                    if (AfterimageCompensationService.this.AfpcPeriodCount > 20) {
                        AfterimageCompensationService.nativeDataTerminate(AfterimageCompensationService.this.AfpcPeriodCount);
                        return;
                    }
                    return;
                }
                if (AfterimageCompensationService.this.AfcStateCondition && AfterimageCompensationService.this.mAfcType == 8) {
                    if (AfterimageCompensationService.this.AfpcPeriodCount > 20) {
                        AfterimageCompensationService.nativeDataTerminate(AfterimageCompensationService.this.AfpcPeriodCount);
                    }
                } else {
                    if (AfterimageCompensationService.this.AfcStateCondition && (AfterimageCompensationService.this.mAfcType == 9 || AfterimageCompensationService.this.mAfcType == 11)) {
                        if (AfterimageCompensationService.this.AfpcPeriodCount > 20) {
                            AfterimageCompensationService.nativeDataTerminate(AfterimageCompensationService.this.AfpcPeriodCount);
                        }
                        if (AfterimageCompensationService.this.AfpcPeriodCount_sub > 20) {
                            AfterimageCompensationService.nativeDataTerminateSub(AfterimageCompensationService.this.AfpcPeriodCount_sub);
                            return;
                        }
                        return;
                    }
                    if (AfterimageCompensationService.this.AfcStateCondition && AfterimageCompensationService.this.mAfcType == 10 && AfterimageCompensationService.this.AfpcPeriodCount > 20) {
                        AfterimageCompensationService.nativeDataTerminate(AfterimageCompensationService.this.AfpcPeriodCount);
                    }
                }
            }
        }
    }

    public final void receive_screen_on_intent() {
        Thread thread;
        if (this.AfcStateCondition) {
            this.AfcThreadCondition = true;
            this.AfcThreadAODCondition = false;
            Thread thread2 = this.mAfcThread;
            if (thread2 != null) {
                synchronized (thread2) {
                    this.mAfcThread.notify();
                }
            }
            if (this.mAfcType < 10 || (thread = this.mAfcThread) == null || !this.AfcThreadTerminateCondition) {
                return;
            }
            Thread.State state = thread.getState();
            this.state = state;
            if (state == Thread.State.TERMINATED) {
                AfcThread afcThread = new AfcThread();
                this.mAfcThread = afcThread;
                afcThread.start();
                Slog.i("AfterimageCompensationService", "AFC Thread ReStart");
                this.AfcThreadTerminateCondition = false;
            }
        }
    }

    public final void receive_screen_off_intent() {
        Thread thread;
        this.AfcThreadCondition = false;
        if (!this.AfcStateCondition || this.mAfcType < 10 || (thread = this.mAfcThread) == null) {
            return;
        }
        synchronized (thread) {
            this.mAfcThread.interrupt();
        }
    }

    public void updateAlwaysOnDisplayForBurnInService(boolean z, int i) {
        if (this.AfcStateCondition && this.mAfcType >= 10 && z) {
            this.AfcThreadAODCondition = z;
            this.AodBrightness = i;
            Thread thread = this.mAfcThread;
            if (thread != null) {
                synchronized (thread) {
                    this.mAfcThread.notify();
                }
            }
        }
    }

    public class AfcThread extends Thread {
        public double[] mAvgCoprRoi;
        public boolean mBrightnessValid;
        public boolean mDataValid;
        public boolean mNormalValid;
        public int rotation;

        public AfcThread() {
            this.mDataValid = false;
            this.mBrightnessValid = false;
            this.mNormalValid = false;
            this.rotation = 0;
            this.mAvgCoprRoi = new double[12];
        }

        /* JADX WARN: Removed duplicated region for block: B:100:0x02be A[Catch: all -> 0x11bf, Exception -> 0x11c1, TRY_LEAVE, TryCatch #19 {Exception -> 0x11c1, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x0012, B:10:0x001a, B:13:0x0033, B:15:0x003b, B:17:0x004c, B:19:0x005d, B:20:0x0068, B:22:0x0070, B:24:0x0078, B:53:0x0080, B:55:0x0086, B:57:0x0096, B:59:0x009e, B:63:0x00aa, B:65:0x00e7, B:67:0x00ee, B:69:0x0101, B:72:0x0114, B:74:0x0126, B:76:0x012e, B:77:0x0139, B:79:0x0141, B:80:0x0171, B:82:0x0179, B:87:0x018f, B:88:0x0192, B:89:0x0195, B:90:0x0198, B:91:0x019b, B:92:0x019d, B:94:0x01af, B:333:0x01c3, B:335:0x01d5, B:337:0x0236, B:340:0x0243, B:341:0x0249, B:351:0x0258, B:354:0x0259, B:98:0x025d, B:100:0x02be, B:103:0x02cb, B:104:0x02d1, B:114:0x02e0, B:117:0x02e1, B:360:0x014c, B:362:0x0154, B:363:0x015f, B:365:0x0167, B:120:0x02e5, B:122:0x02fa, B:124:0x0302, B:126:0x030e, B:128:0x0330, B:129:0x036a, B:132:0x034f, B:133:0x0371, B:135:0x037a, B:137:0x0386, B:139:0x03a8, B:140:0x03e2, B:142:0x03f1, B:144:0x0409, B:146:0x0415, B:148:0x0436, B:151:0x0485, B:152:0x04a0, B:155:0x03c7, B:156:0x04a7, B:158:0x04b1, B:160:0x04bd, B:162:0x0579, B:164:0x0581, B:166:0x058d, B:168:0x05a1, B:170:0x05ab, B:173:0x05fb, B:174:0x0616, B:175:0x061d, B:177:0x0625, B:179:0x0631, B:181:0x06e8, B:183:0x06f7, B:185:0x0710, B:187:0x0724, B:189:0x072e, B:192:0x077e, B:193:0x0799, B:194:0x07a0, B:196:0x07b0, B:198:0x07bc, B:200:0x0871, B:202:0x087b, B:204:0x0894, B:206:0x08a8, B:208:0x08b2, B:211:0x0902, B:212:0x091d, B:213:0x0924, B:215:0x092d, B:217:0x0939, B:219:0x09ee, B:221:0x09f8, B:223:0x0a11, B:225:0x0a25, B:227:0x0a2f, B:230:0x0a7f, B:231:0x0a9a, B:232:0x0aa1, B:234:0x0aab, B:236:0x0ab7, B:238:0x0b6f, B:240:0x0b77, B:242:0x0b81, B:244:0x0b9a, B:246:0x0bae, B:248:0x0bb8, B:251:0x0c09, B:252:0x0cdc, B:253:0x0c26, B:255:0x0c30, B:257:0x0c3a, B:259:0x0c53, B:261:0x0c67, B:263:0x0c71, B:266:0x0cc1, B:267:0x0ce3, B:269:0x0ceb, B:272:0x0cf5, B:274:0x0cfd, B:276:0x0d09, B:278:0x0dbe, B:280:0x0dc8, B:282:0x0de1, B:284:0x0df5, B:286:0x0dff, B:289:0x0e4f, B:290:0x0e6a, B:291:0x0e71, B:293:0x0e7d, B:295:0x0f32, B:297:0x0f3c, B:299:0x0f55, B:301:0x0f69, B:303:0x0f73, B:306:0x0fc3, B:307:0x0fde, B:308:0x0fe3, B:310:0x0ff1, B:312:0x0ff9, B:314:0x1001, B:316:0x100d, B:318:0x10c2, B:320:0x10cc, B:322:0x10e5, B:324:0x10f9, B:326:0x1103, B:329:0x1153, B:330:0x116e, B:368:0x00af, B:370:0x00d1, B:372:0x00d9, B:376:0x00e5, B:379:0x0063, B:27:0x1173, B:30:0x117b, B:33:0x1183, B:34:0x1189, B:45:0x1198, B:49:0x1199, B:381:0x0026, B:384:0x0029, B:386:0x0031), top: B:2:0x0001, outer: #20 }] */
        /* JADX WARN: Removed duplicated region for block: B:331:0x02e5 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:335:0x01d5 A[Catch: all -> 0x11bf, Exception -> 0x11c1, TryCatch #19 {Exception -> 0x11c1, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x0012, B:10:0x001a, B:13:0x0033, B:15:0x003b, B:17:0x004c, B:19:0x005d, B:20:0x0068, B:22:0x0070, B:24:0x0078, B:53:0x0080, B:55:0x0086, B:57:0x0096, B:59:0x009e, B:63:0x00aa, B:65:0x00e7, B:67:0x00ee, B:69:0x0101, B:72:0x0114, B:74:0x0126, B:76:0x012e, B:77:0x0139, B:79:0x0141, B:80:0x0171, B:82:0x0179, B:87:0x018f, B:88:0x0192, B:89:0x0195, B:90:0x0198, B:91:0x019b, B:92:0x019d, B:94:0x01af, B:333:0x01c3, B:335:0x01d5, B:337:0x0236, B:340:0x0243, B:341:0x0249, B:351:0x0258, B:354:0x0259, B:98:0x025d, B:100:0x02be, B:103:0x02cb, B:104:0x02d1, B:114:0x02e0, B:117:0x02e1, B:360:0x014c, B:362:0x0154, B:363:0x015f, B:365:0x0167, B:120:0x02e5, B:122:0x02fa, B:124:0x0302, B:126:0x030e, B:128:0x0330, B:129:0x036a, B:132:0x034f, B:133:0x0371, B:135:0x037a, B:137:0x0386, B:139:0x03a8, B:140:0x03e2, B:142:0x03f1, B:144:0x0409, B:146:0x0415, B:148:0x0436, B:151:0x0485, B:152:0x04a0, B:155:0x03c7, B:156:0x04a7, B:158:0x04b1, B:160:0x04bd, B:162:0x0579, B:164:0x0581, B:166:0x058d, B:168:0x05a1, B:170:0x05ab, B:173:0x05fb, B:174:0x0616, B:175:0x061d, B:177:0x0625, B:179:0x0631, B:181:0x06e8, B:183:0x06f7, B:185:0x0710, B:187:0x0724, B:189:0x072e, B:192:0x077e, B:193:0x0799, B:194:0x07a0, B:196:0x07b0, B:198:0x07bc, B:200:0x0871, B:202:0x087b, B:204:0x0894, B:206:0x08a8, B:208:0x08b2, B:211:0x0902, B:212:0x091d, B:213:0x0924, B:215:0x092d, B:217:0x0939, B:219:0x09ee, B:221:0x09f8, B:223:0x0a11, B:225:0x0a25, B:227:0x0a2f, B:230:0x0a7f, B:231:0x0a9a, B:232:0x0aa1, B:234:0x0aab, B:236:0x0ab7, B:238:0x0b6f, B:240:0x0b77, B:242:0x0b81, B:244:0x0b9a, B:246:0x0bae, B:248:0x0bb8, B:251:0x0c09, B:252:0x0cdc, B:253:0x0c26, B:255:0x0c30, B:257:0x0c3a, B:259:0x0c53, B:261:0x0c67, B:263:0x0c71, B:266:0x0cc1, B:267:0x0ce3, B:269:0x0ceb, B:272:0x0cf5, B:274:0x0cfd, B:276:0x0d09, B:278:0x0dbe, B:280:0x0dc8, B:282:0x0de1, B:284:0x0df5, B:286:0x0dff, B:289:0x0e4f, B:290:0x0e6a, B:291:0x0e71, B:293:0x0e7d, B:295:0x0f32, B:297:0x0f3c, B:299:0x0f55, B:301:0x0f69, B:303:0x0f73, B:306:0x0fc3, B:307:0x0fde, B:308:0x0fe3, B:310:0x0ff1, B:312:0x0ff9, B:314:0x1001, B:316:0x100d, B:318:0x10c2, B:320:0x10cc, B:322:0x10e5, B:324:0x10f9, B:326:0x1103, B:329:0x1153, B:330:0x116e, B:368:0x00af, B:370:0x00d1, B:372:0x00d9, B:376:0x00e5, B:379:0x0063, B:27:0x1173, B:30:0x117b, B:33:0x1183, B:34:0x1189, B:45:0x1198, B:49:0x1199, B:381:0x0026, B:384:0x0029, B:386:0x0031), top: B:2:0x0001, outer: #20 }] */
        /* JADX WARN: Removed duplicated region for block: B:358:0x02e5 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0179 A[Catch: all -> 0x11bf, Exception -> 0x11c1, TryCatch #19 {Exception -> 0x11c1, blocks: (B:3:0x0001, B:5:0x0009, B:7:0x0012, B:10:0x001a, B:13:0x0033, B:15:0x003b, B:17:0x004c, B:19:0x005d, B:20:0x0068, B:22:0x0070, B:24:0x0078, B:53:0x0080, B:55:0x0086, B:57:0x0096, B:59:0x009e, B:63:0x00aa, B:65:0x00e7, B:67:0x00ee, B:69:0x0101, B:72:0x0114, B:74:0x0126, B:76:0x012e, B:77:0x0139, B:79:0x0141, B:80:0x0171, B:82:0x0179, B:87:0x018f, B:88:0x0192, B:89:0x0195, B:90:0x0198, B:91:0x019b, B:92:0x019d, B:94:0x01af, B:333:0x01c3, B:335:0x01d5, B:337:0x0236, B:340:0x0243, B:341:0x0249, B:351:0x0258, B:354:0x0259, B:98:0x025d, B:100:0x02be, B:103:0x02cb, B:104:0x02d1, B:114:0x02e0, B:117:0x02e1, B:360:0x014c, B:362:0x0154, B:363:0x015f, B:365:0x0167, B:120:0x02e5, B:122:0x02fa, B:124:0x0302, B:126:0x030e, B:128:0x0330, B:129:0x036a, B:132:0x034f, B:133:0x0371, B:135:0x037a, B:137:0x0386, B:139:0x03a8, B:140:0x03e2, B:142:0x03f1, B:144:0x0409, B:146:0x0415, B:148:0x0436, B:151:0x0485, B:152:0x04a0, B:155:0x03c7, B:156:0x04a7, B:158:0x04b1, B:160:0x04bd, B:162:0x0579, B:164:0x0581, B:166:0x058d, B:168:0x05a1, B:170:0x05ab, B:173:0x05fb, B:174:0x0616, B:175:0x061d, B:177:0x0625, B:179:0x0631, B:181:0x06e8, B:183:0x06f7, B:185:0x0710, B:187:0x0724, B:189:0x072e, B:192:0x077e, B:193:0x0799, B:194:0x07a0, B:196:0x07b0, B:198:0x07bc, B:200:0x0871, B:202:0x087b, B:204:0x0894, B:206:0x08a8, B:208:0x08b2, B:211:0x0902, B:212:0x091d, B:213:0x0924, B:215:0x092d, B:217:0x0939, B:219:0x09ee, B:221:0x09f8, B:223:0x0a11, B:225:0x0a25, B:227:0x0a2f, B:230:0x0a7f, B:231:0x0a9a, B:232:0x0aa1, B:234:0x0aab, B:236:0x0ab7, B:238:0x0b6f, B:240:0x0b77, B:242:0x0b81, B:244:0x0b9a, B:246:0x0bae, B:248:0x0bb8, B:251:0x0c09, B:252:0x0cdc, B:253:0x0c26, B:255:0x0c30, B:257:0x0c3a, B:259:0x0c53, B:261:0x0c67, B:263:0x0c71, B:266:0x0cc1, B:267:0x0ce3, B:269:0x0ceb, B:272:0x0cf5, B:274:0x0cfd, B:276:0x0d09, B:278:0x0dbe, B:280:0x0dc8, B:282:0x0de1, B:284:0x0df5, B:286:0x0dff, B:289:0x0e4f, B:290:0x0e6a, B:291:0x0e71, B:293:0x0e7d, B:295:0x0f32, B:297:0x0f3c, B:299:0x0f55, B:301:0x0f69, B:303:0x0f73, B:306:0x0fc3, B:307:0x0fde, B:308:0x0fe3, B:310:0x0ff1, B:312:0x0ff9, B:314:0x1001, B:316:0x100d, B:318:0x10c2, B:320:0x10cc, B:322:0x10e5, B:324:0x10f9, B:326:0x1103, B:329:0x1153, B:330:0x116e, B:368:0x00af, B:370:0x00d1, B:372:0x00d9, B:376:0x00e5, B:379:0x0063, B:27:0x1173, B:30:0x117b, B:33:0x1183, B:34:0x1189, B:45:0x1198, B:49:0x1199, B:381:0x0026, B:384:0x0029, B:386:0x0031), top: B:2:0x0001, outer: #20 }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            String str;
            StringBuilder sb;
            boolean z;
            boolean z2;
            while (AfterimageCompensationService.this.AfcStateCondition) {
                try {
                    try {
                        if (AfterimageCompensationService.this.AfcThreadCondition && !AfterimageCompensationService.this.AfcThreadAODCondition) {
                            try {
                                Thread.sleep(AfterimageCompensationService.this.mThreadSleepTime);
                                this.mNormalValid = true;
                            } catch (InterruptedException unused) {
                                this.mNormalValid = false;
                            }
                        } else if (AfterimageCompensationService.this.AfcThreadAODCondition) {
                            this.mNormalValid = false;
                        }
                        if (AfterimageCompensationService.this.mThreadAFPC) {
                            if (SystemProperties.get("service.camera.running", "0").equals("1") && SystemProperties.get("service.camera.sfs.running", "0").equals("0")) {
                                AfterimageCompensationService.this.isRunningCameraApp = true;
                            } else {
                                AfterimageCompensationService.this.isRunningCameraApp = false;
                            }
                            if (!AfterimageCompensationService.this.isRunningCameraApp && (AfterimageCompensationService.this.AfcThreadCondition || AfterimageCompensationService.this.AfcThreadAODCondition)) {
                                boolean z3 = this.mNormalValid;
                                if (z3) {
                                    this.mDataValid = AfterimageCompensationService.this.AfcThreadCondition;
                                    if (AfterimageCompensationService.this.AfcThreadCondition) {
                                        if (!AfterimageCompensationService.this.getBrightness() && !AfterimageCompensationService.this.getBrightness_sub()) {
                                            z2 = false;
                                            this.mBrightnessValid = z2;
                                        }
                                        z2 = true;
                                        this.mBrightnessValid = z2;
                                    }
                                } else if (!z3) {
                                    this.mDataValid = AfterimageCompensationService.this.AfcThreadAODCondition;
                                    AfterimageCompensationService afterimageCompensationService = AfterimageCompensationService.this;
                                    afterimageCompensationService.mLuminance = afterimageCompensationService.AodBrightness;
                                    AfterimageCompensationService afterimageCompensationService2 = AfterimageCompensationService.this;
                                    afterimageCompensationService2.mLuminance_sub = afterimageCompensationService2.AodBrightness;
                                    if (AfterimageCompensationService.this.mAfcType >= 11) {
                                        if (!AfterimageCompensationService.this.getBrightness() && !AfterimageCompensationService.this.getBrightness_sub()) {
                                            z = false;
                                            this.mBrightnessValid = z;
                                        }
                                        z = true;
                                        this.mBrightnessValid = z;
                                    }
                                }
                                if (this.mDataValid) {
                                    if (AfterimageCompensationService.this.mContext.getResources().getConfiguration().semDisplayDeviceType != 0 && AfterimageCompensationService.this.mContext.getResources().getConfiguration().semDisplayDeviceType != -1) {
                                        if (AfterimageCompensationService.this.mContext.getResources().getConfiguration().semDisplayDeviceType == 5) {
                                            if (AfterimageCompensationService.this.AfcThreadCondition) {
                                                AfterimageCompensationService.this.AfpcPeriodCount_sub++;
                                            } else if (AfterimageCompensationService.this.AfcThreadAODCondition) {
                                                AfterimageCompensationService.this.AfpcPeriodCount_sub += 2;
                                            }
                                        }
                                        if (AfterimageCompensationService.this.mWindowManager != null) {
                                            int rotation = AfterimageCompensationService.this.mWindowManager.getDefaultDisplay().getRotation();
                                            if (rotation == 0) {
                                                this.rotation = 0;
                                            } else if (rotation == 1) {
                                                this.rotation = 1;
                                            } else if (rotation == 2) {
                                                this.rotation = 2;
                                            } else if (rotation == 3) {
                                                this.rotation = 3;
                                            } else {
                                                this.rotation = 0;
                                            }
                                        }
                                        if (AfterimageCompensationService.this.mContext.getResources().getConfiguration().semDisplayDeviceType != 0 && AfterimageCompensationService.this.mContext.getResources().getConfiguration().semDisplayDeviceType != -1) {
                                            if (AfterimageCompensationService.this.mContext.getResources().getConfiguration().semDisplayDeviceType != 5) {
                                                Slog.i("AfterimageCompensationService", "AfcThread mLuminance_sub : " + AfterimageCompensationService.this.mLuminance_sub + " , AfpcPeriodCount_sub : " + AfterimageCompensationService.this.AfpcPeriodCount_sub + " , rotation : " + this.rotation + " , AOD : " + AfterimageCompensationService.this.AfcThreadAODCondition);
                                                AfterimageCompensationService.nativeDataReadSub(AfterimageCompensationService.this.mLuminance_sub, AfterimageCompensationService.this.AfpcPeriodCount_sub, this.rotation, AfterimageCompensationService.this.AfcThreadAODCondition);
                                                if (AfterimageCompensationService.this.AfcThreadAODCondition) {
                                                    AfterimageCompensationService.this.AfcThreadAODCondition = false;
                                                    if (AfterimageCompensationService.this.mAfcThread != null) {
                                                        try {
                                                            synchronized (AfterimageCompensationService.this.mAfcThread) {
                                                                AfterimageCompensationService.this.mAfcThread.wait();
                                                            }
                                                        } catch (InterruptedException unused2) {
                                                            this.mNormalValid = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        Slog.i("AfterimageCompensationService", "AfcThread mLuminance : " + AfterimageCompensationService.this.mLuminance + " , AfpcPeriodCount : " + AfterimageCompensationService.this.AfpcPeriodCount + " , rotation : " + this.rotation + " , AOD : " + AfterimageCompensationService.this.AfcThreadAODCondition);
                                        AfterimageCompensationService.nativeDataRead(AfterimageCompensationService.this.mLuminance, AfterimageCompensationService.this.AfpcPeriodCount, this.rotation, AfterimageCompensationService.this.AfcThreadAODCondition);
                                        if (!AfterimageCompensationService.this.AfcThreadAODCondition) {
                                            AfterimageCompensationService.this.AfcThreadAODCondition = false;
                                            if (AfterimageCompensationService.this.mAfcThread != null) {
                                                try {
                                                    synchronized (AfterimageCompensationService.this.mAfcThread) {
                                                        AfterimageCompensationService.this.mAfcThread.wait();
                                                    }
                                                } catch (InterruptedException unused3) {
                                                    this.mNormalValid = false;
                                                }
                                            }
                                        }
                                    }
                                    if (AfterimageCompensationService.this.AfcThreadCondition) {
                                        AfterimageCompensationService.this.AfpcPeriodCount++;
                                    } else if (AfterimageCompensationService.this.AfcThreadAODCondition) {
                                        AfterimageCompensationService.this.AfpcPeriodCount += 2;
                                    }
                                    if (AfterimageCompensationService.this.mWindowManager != null) {
                                    }
                                    if (AfterimageCompensationService.this.mContext.getResources().getConfiguration().semDisplayDeviceType != 0) {
                                        if (AfterimageCompensationService.this.mContext.getResources().getConfiguration().semDisplayDeviceType != 5) {
                                        }
                                    }
                                    Slog.i("AfterimageCompensationService", "AfcThread mLuminance : " + AfterimageCompensationService.this.mLuminance + " , AfpcPeriodCount : " + AfterimageCompensationService.this.AfpcPeriodCount + " , rotation : " + this.rotation + " , AOD : " + AfterimageCompensationService.this.AfcThreadAODCondition);
                                    AfterimageCompensationService.nativeDataRead(AfterimageCompensationService.this.mLuminance, AfterimageCompensationService.this.AfpcPeriodCount, this.rotation, AfterimageCompensationService.this.AfcThreadAODCondition);
                                    if (!AfterimageCompensationService.this.AfcThreadAODCondition) {
                                    }
                                }
                                if (AfterimageCompensationService.this.AfpcPeriodCount >= AfterimageCompensationService.this.AfpcPeriodMax) {
                                    if (AfterimageCompensationService.this.mAfcType == 2) {
                                        if (AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                            AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                            Slog.i("AfterimageCompensationService", "mApplyValue : " + AfterimageCompensationService.this.mApplyValue);
                                            try {
                                                AfterimageCompensationService.this.mApplyValue = Float.parseFloat(String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.nativeDataEvaluate())));
                                            } catch (NumberFormatException e) {
                                                Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                                                AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                            }
                                            AfterimageCompensationService.this.updateHWParam();
                                        }
                                    } else if (AfterimageCompensationService.this.mAfcType == 4) {
                                        if (AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                            AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                            Slog.i("AfterimageCompensationService", "mApplyValue : " + AfterimageCompensationService.this.mApplyValue);
                                            try {
                                                AfterimageCompensationService.this.mApplyValue = Float.parseFloat(String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.nativeDataEvaluate())));
                                            } catch (NumberFormatException e2) {
                                                Slog.e("AfterimageCompensationService", "NumberFormatException : " + e2);
                                                AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                            }
                                            if (AfterimageCompensationService.this.mApplyCount < AfterimageCompensationService.this.mAfpcJndRefV2.length && AfterimageCompensationService.this.mApplyValue >= AfterimageCompensationService.this.mAfpcJndRefV2[AfterimageCompensationService.this.mApplyCount]) {
                                                if (AfterimageCompensationService.nativeDataWriteV2(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                    Intent intent = new Intent("com.samsung.android.sm.ACTION_ABC_SOLUTION");
                                                    intent.setPackage(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.lool"));
                                                    AfterimageCompensationService.this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                                                }
                                                try {
                                                    String str2 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                    AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str2);
                                                    Slog.i("AfterimageCompensationService", "afpcDataWrite - str : " + str2);
                                                } catch (NumberFormatException e3) {
                                                    Slog.e("AfterimageCompensationService", "NumberFormatException : " + e3);
                                                    AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                }
                                            }
                                            AfterimageCompensationService.this.updateHWParam();
                                        }
                                    } else if (AfterimageCompensationService.this.mAfcType == 3) {
                                        if (AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                            AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                            AfterimageCompensationService.this.mAvgLum = AfterimageCompensationService.nativeDataReadAvgLum();
                                            AfterimageCompensationService.this.mMaxBDI = AfterimageCompensationService.nativeDataReadMaxBDI();
                                            AfterimageCompensationService.this.mNBDI = AfterimageCompensationService.nativeDataReadNBDI();
                                            AfterimageCompensationService.this.mEffAvgLum = AfterimageCompensationService.nativeDataReadEffAvgLum();
                                            AfterimageCompensationService.this.mEffMaxBDI = AfterimageCompensationService.nativeDataReadEffMaxBDI();
                                            AfterimageCompensationService.this.mEffNBDI = AfterimageCompensationService.nativeDataReadEffNBDI();
                                            Slog.i("AfterimageCompensationService", "mAvgLum : " + AfterimageCompensationService.this.mAvgLum + ", mMaxBDI : " + AfterimageCompensationService.this.mMaxBDI + ", mNBDI : " + AfterimageCompensationService.this.mNBDI);
                                            Slog.i("AfterimageCompensationService", "mEffAvgLum : " + AfterimageCompensationService.this.mEffAvgLum + ", mEffMaxBDI : " + AfterimageCompensationService.this.mEffMaxBDI + ", mEffNBDI : " + AfterimageCompensationService.this.mEffNBDI);
                                            if (AfterimageCompensationService.this.mEffNBDI >= 300.0d && AfterimageCompensationService.this.mApplyCount < 10 && AfterimageCompensationService.this.mEffAvgLum >= 80.0d) {
                                                Slog.i("AfterimageCompensationService", "nativeDataUpdate");
                                                if (AfterimageCompensationService.nativeDataUpdate(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                    AfterimageCompensationService.this.mApplyCount++;
                                                    try {
                                                        String str3 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                        AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str3);
                                                        Slog.i("AfterimageCompensationService", "nativeDataUpdate - str : " + str3);
                                                    } catch (NumberFormatException e4) {
                                                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e4);
                                                        AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                    }
                                                }
                                            }
                                            AfterimageCompensationService.this.updateHWParam();
                                        }
                                    } else if (AfterimageCompensationService.this.mAfcType == 5) {
                                        if (AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                            AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                            AfterimageCompensationService.this.mAvgLum = AfterimageCompensationService.nativeDataReadAvgLum();
                                            AfterimageCompensationService.this.mMaxBDI = AfterimageCompensationService.nativeDataReadMaxBDI();
                                            AfterimageCompensationService.this.mNBDI = AfterimageCompensationService.nativeDataReadNBDI();
                                            AfterimageCompensationService.this.mEffAvgLum = AfterimageCompensationService.nativeDataReadEffAvgLum();
                                            AfterimageCompensationService.this.mEffMaxBDI = AfterimageCompensationService.nativeDataReadEffMaxBDI();
                                            AfterimageCompensationService.this.mEffNBDI = AfterimageCompensationService.nativeDataReadEffNBDI();
                                            Slog.i("AfterimageCompensationService", "mAvgLum : " + AfterimageCompensationService.this.mAvgLum + ", mMaxBDI : " + AfterimageCompensationService.this.mMaxBDI + ", mNBDI : " + AfterimageCompensationService.this.mNBDI);
                                            Slog.i("AfterimageCompensationService", "mEffAvgLum : " + AfterimageCompensationService.this.mEffAvgLum + ", mEffMaxBDI : " + AfterimageCompensationService.this.mEffMaxBDI + ", mEffNBDI : " + AfterimageCompensationService.this.mEffNBDI);
                                            if (AfterimageCompensationService.this.mApplyCount < 20 && AfterimageCompensationService.this.mEffAvgLum >= 75.0d && AfterimageCompensationService.this.mEffNBDI >= AfterimageCompensationService.this.effNbdiTh[AfterimageCompensationService.this.mApplyCount]) {
                                                Slog.i("AfterimageCompensationService", "nativeDataUpdate");
                                                if (AfterimageCompensationService.nativeDataUpdate(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                    AfterimageCompensationService.this.mApplyCount++;
                                                    try {
                                                        String str4 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                        AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str4);
                                                        Slog.i("AfterimageCompensationService", "nativeDataUpdate - str : " + str4);
                                                    } catch (NumberFormatException e5) {
                                                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e5);
                                                        AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                    }
                                                }
                                            }
                                            AfterimageCompensationService.this.updateHWParam();
                                        }
                                    } else if (AfterimageCompensationService.this.mAfcType == 6) {
                                        if (AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                            AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                            AfterimageCompensationService.this.mAvgLum = AfterimageCompensationService.nativeDataReadAvgLum();
                                            AfterimageCompensationService.this.mMaxBDI = AfterimageCompensationService.nativeDataReadMaxBDI();
                                            AfterimageCompensationService.this.mNBDI = AfterimageCompensationService.nativeDataReadNBDI();
                                            AfterimageCompensationService.this.mEffAvgLum = AfterimageCompensationService.nativeDataReadEffAvgLum();
                                            AfterimageCompensationService.this.mEffMaxBDI = AfterimageCompensationService.nativeDataReadEffMaxBDI();
                                            AfterimageCompensationService.this.mEffNBDI = AfterimageCompensationService.nativeDataReadEffNBDI();
                                            Slog.i("AfterimageCompensationService", "mAvgLum : " + AfterimageCompensationService.this.mAvgLum + ", mMaxBDI : " + AfterimageCompensationService.this.mMaxBDI + ", mNBDI : " + AfterimageCompensationService.this.mNBDI);
                                            Slog.i("AfterimageCompensationService", "mEffAvgLum : " + AfterimageCompensationService.this.mEffAvgLum + ", mEffMaxBDI : " + AfterimageCompensationService.this.mEffMaxBDI + ", mEffNBDI : " + AfterimageCompensationService.this.mEffNBDI);
                                            if (AfterimageCompensationService.this.mApplyCount < 30 && AfterimageCompensationService.this.mEffAvgLum >= 70.0d && AfterimageCompensationService.this.mEffNBDI >= AfterimageCompensationService.this.effNbdiTh_V3[AfterimageCompensationService.this.mApplyCount]) {
                                                Slog.i("AfterimageCompensationService", "nativeDataUpdate");
                                                if (AfterimageCompensationService.nativeDataUpdate(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                    AfterimageCompensationService.this.mApplyCount++;
                                                    try {
                                                        String str5 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                        AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str5);
                                                        Slog.i("AfterimageCompensationService", "nativeDataUpdate - str : " + str5);
                                                    } catch (NumberFormatException e6) {
                                                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e6);
                                                        AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                    }
                                                }
                                            }
                                            AfterimageCompensationService.this.updateHWParam();
                                        }
                                    } else if (AfterimageCompensationService.this.mAfcType == 7) {
                                        if (AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                            AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                            AfterimageCompensationService.this.mAvgLum = AfterimageCompensationService.nativeDataReadAvgLum();
                                            AfterimageCompensationService.this.mMaxBDI = AfterimageCompensationService.nativeDataReadMaxBDI();
                                            AfterimageCompensationService.this.mNBDI = AfterimageCompensationService.nativeDataReadNBDI();
                                            AfterimageCompensationService.this.mEffAvgLum = AfterimageCompensationService.nativeDataReadEffAvgLum();
                                            AfterimageCompensationService.this.mEffMaxBDI = AfterimageCompensationService.nativeDataReadEffMaxBDI();
                                            AfterimageCompensationService.this.mEffNBDI = AfterimageCompensationService.nativeDataReadEffNBDI();
                                            Slog.i("AfterimageCompensationService", "mAvgLum : " + AfterimageCompensationService.this.mAvgLum + ", mMaxBDI : " + AfterimageCompensationService.this.mMaxBDI + ", mNBDI : " + AfterimageCompensationService.this.mNBDI);
                                            Slog.i("AfterimageCompensationService", "mEffAvgLum : " + AfterimageCompensationService.this.mEffAvgLum + ", mEffMaxBDI : " + AfterimageCompensationService.this.mEffMaxBDI + ", mEffNBDI : " + AfterimageCompensationService.this.mEffNBDI);
                                            if (AfterimageCompensationService.this.mApplyCount < 30 && AfterimageCompensationService.this.mEffAvgLum >= 70.0d && AfterimageCompensationService.this.mEffNBDI >= AfterimageCompensationService.this.effNbdiTh_V4[AfterimageCompensationService.this.mApplyCount]) {
                                                Slog.i("AfterimageCompensationService", "nativeDataUpdate");
                                                if (AfterimageCompensationService.nativeDataUpdate(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                    AfterimageCompensationService.this.mApplyCount++;
                                                    try {
                                                        String str6 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                        AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str6);
                                                        Slog.i("AfterimageCompensationService", "nativeDataUpdate - str : " + str6);
                                                    } catch (NumberFormatException e7) {
                                                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e7);
                                                        AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                    }
                                                }
                                            }
                                            AfterimageCompensationService.this.updateHWParam();
                                        }
                                    } else if (AfterimageCompensationService.this.mAfcType == 8) {
                                        if (AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                            AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                            AfterimageCompensationService.this.mAvgLum = AfterimageCompensationService.nativeDataReadAvgLum();
                                            AfterimageCompensationService.this.mMaxBDI = AfterimageCompensationService.nativeDataReadMaxBDI();
                                            AfterimageCompensationService.this.mNBDI = AfterimageCompensationService.nativeDataReadNBDI();
                                            AfterimageCompensationService.this.mEffAvgLum = AfterimageCompensationService.nativeDataReadEffAvgLum();
                                            AfterimageCompensationService.this.mEffMaxBDI = AfterimageCompensationService.nativeDataReadEffMaxBDI();
                                            AfterimageCompensationService.this.mEffNBDI = AfterimageCompensationService.nativeDataReadEffNBDI();
                                            Slog.i("AfterimageCompensationService", "mAvgLum : " + AfterimageCompensationService.this.mAvgLum + ", mMaxBDI : " + AfterimageCompensationService.this.mMaxBDI + ", mNBDI : " + AfterimageCompensationService.this.mNBDI);
                                            Slog.i("AfterimageCompensationService", "mEffAvgLum : " + AfterimageCompensationService.this.mEffAvgLum + ", mEffMaxBDI : " + AfterimageCompensationService.this.mEffMaxBDI + ", mEffNBDI : " + AfterimageCompensationService.this.mEffNBDI);
                                            if (AfterimageCompensationService.this.mAfpcPanelNumber_main == 220101) {
                                                if (AfterimageCompensationService.this.mApplyCount < 30 && AfterimageCompensationService.this.mEffAvgLum >= 50.0d && AfterimageCompensationService.this.mEffNBDI >= AfterimageCompensationService.this.effNbdiTh_V4[AfterimageCompensationService.this.mApplyCount]) {
                                                    Slog.i("AfterimageCompensationService", "nativeDataUpdate");
                                                    if (AfterimageCompensationService.nativeDataUpdate(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                        AfterimageCompensationService.this.mApplyCount++;
                                                        try {
                                                            String str7 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                            AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str7);
                                                            Slog.i("AfterimageCompensationService", "nativeDataUpdate - str : " + str7);
                                                        } catch (NumberFormatException e8) {
                                                            Slog.e("AfterimageCompensationService", "NumberFormatException : " + e8);
                                                            AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                        }
                                                    }
                                                }
                                            } else if (AfterimageCompensationService.this.mApplyCount < 50 && AfterimageCompensationService.this.mEffAvgLum >= 50.0d && AfterimageCompensationService.this.mEffNBDI >= AfterimageCompensationService.this.effNbdiTh_V5[AfterimageCompensationService.this.mApplyCount]) {
                                                Slog.i("AfterimageCompensationService", "nativeDataUpdate");
                                                if (AfterimageCompensationService.nativeDataUpdate(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                    AfterimageCompensationService.this.mApplyCount++;
                                                    try {
                                                        String str8 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                        AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str8);
                                                        Slog.i("AfterimageCompensationService", "nativeDataUpdate - str : " + str8);
                                                    } catch (NumberFormatException e9) {
                                                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e9);
                                                        AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                    }
                                                }
                                            }
                                            AfterimageCompensationService.this.updateHWParam();
                                        }
                                    } else {
                                        if (AfterimageCompensationService.this.mAfcType != 9 && AfterimageCompensationService.this.mAfcType != 11) {
                                            if (AfterimageCompensationService.this.mAfcType == 10 && AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                                AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                                AfterimageCompensationService.this.mAvgLum = AfterimageCompensationService.nativeDataReadAvgLum();
                                                AfterimageCompensationService.this.mMaxBDI = AfterimageCompensationService.nativeDataReadMaxBDI();
                                                AfterimageCompensationService.this.mNBDI = AfterimageCompensationService.nativeDataReadNBDI();
                                                AfterimageCompensationService.this.mEffAvgLum = AfterimageCompensationService.nativeDataReadEffAvgLum();
                                                AfterimageCompensationService.this.mEffMaxBDI = AfterimageCompensationService.nativeDataReadEffMaxBDI();
                                                AfterimageCompensationService.this.mEffNBDI = AfterimageCompensationService.nativeDataReadEffNBDI();
                                                Slog.i("AfterimageCompensationService", "mAvgLum : " + AfterimageCompensationService.this.mAvgLum + ", mMaxBDI : " + AfterimageCompensationService.this.mMaxBDI + ", mNBDI : " + AfterimageCompensationService.this.mNBDI);
                                                Slog.i("AfterimageCompensationService", "mEffAvgLum : " + AfterimageCompensationService.this.mEffAvgLum + ", mEffMaxBDI : " + AfterimageCompensationService.this.mEffMaxBDI + ", mEffNBDI : " + AfterimageCompensationService.this.mEffNBDI);
                                                if (AfterimageCompensationService.this.mApplyCount < 100 && AfterimageCompensationService.this.mEffAvgLum >= 50.0d && AfterimageCompensationService.this.mEffNBDI >= AfterimageCompensationService.this.effNbdiTh_V7[AfterimageCompensationService.this.mApplyCount]) {
                                                    Slog.i("AfterimageCompensationService", "nativeDataUpdate");
                                                    if (AfterimageCompensationService.nativeDataUpdate(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                        AfterimageCompensationService.this.mApplyCount++;
                                                        try {
                                                            String str9 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                            AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str9);
                                                            Slog.i("AfterimageCompensationService", "nativeDataUpdate - str : " + str9);
                                                        } catch (NumberFormatException e10) {
                                                            Slog.e("AfterimageCompensationService", "NumberFormatException : " + e10);
                                                            AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                        }
                                                    }
                                                }
                                                AfterimageCompensationService.this.updateHWParam();
                                            }
                                        }
                                        if (AfterimageCompensationService.nativeDataSave(AfterimageCompensationService.this.AfpcPeriodCount) == 0) {
                                            AfterimageCompensationService.this.AfpcPeriodCount = 0;
                                            AfterimageCompensationService.this.mAvgLum = AfterimageCompensationService.nativeDataReadAvgLum();
                                            AfterimageCompensationService.this.mMaxBDI = AfterimageCompensationService.nativeDataReadMaxBDI();
                                            AfterimageCompensationService.this.mNBDI = AfterimageCompensationService.nativeDataReadNBDI();
                                            AfterimageCompensationService.this.mEffAvgLum = AfterimageCompensationService.nativeDataReadEffAvgLum();
                                            AfterimageCompensationService.this.mEffMaxBDI = AfterimageCompensationService.nativeDataReadEffMaxBDI();
                                            AfterimageCompensationService.this.mEffNBDI = AfterimageCompensationService.nativeDataReadEffNBDI();
                                            Slog.i("AfterimageCompensationService", "mAvgLum : " + AfterimageCompensationService.this.mAvgLum + ", mMaxBDI : " + AfterimageCompensationService.this.mMaxBDI + ", mNBDI : " + AfterimageCompensationService.this.mNBDI);
                                            Slog.i("AfterimageCompensationService", "mEffAvgLum : " + AfterimageCompensationService.this.mEffAvgLum + ", mEffMaxBDI : " + AfterimageCompensationService.this.mEffMaxBDI + ", mEffNBDI : " + AfterimageCompensationService.this.mEffNBDI);
                                            if (AfterimageCompensationService.this.mApplyCount < 100 && AfterimageCompensationService.this.mEffAvgLum >= 50.0d && AfterimageCompensationService.this.mEffNBDI >= AfterimageCompensationService.this.effNbdiTh_V6[AfterimageCompensationService.this.mApplyCount]) {
                                                Slog.i("AfterimageCompensationService", "nativeDataUpdate");
                                                if (AfterimageCompensationService.nativeDataUpdate(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                                    AfterimageCompensationService.this.mApplyCount++;
                                                    try {
                                                        String str10 = AfterimageCompensationService.this.mApplyCount + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue));
                                                        AfterimageCompensationService.this.fileWriteString("/efs/afc/apply_count", str10);
                                                        Slog.i("AfterimageCompensationService", "nativeDataUpdate - str : " + str10);
                                                    } catch (NumberFormatException e11) {
                                                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e11);
                                                        AfterimageCompensationService.this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                                    }
                                                }
                                            }
                                            AfterimageCompensationService.this.updateHWParam();
                                        }
                                    }
                                }
                                if (AfterimageCompensationService.this.AfpcPeriodCount_sub >= AfterimageCompensationService.this.AfpcPeriodMax && ((AfterimageCompensationService.this.mAfcType == 9 || AfterimageCompensationService.this.mAfcType == 11) && AfterimageCompensationService.nativeDataSaveSub(AfterimageCompensationService.this.AfpcPeriodCount_sub) == 0)) {
                                    AfterimageCompensationService.this.AfpcPeriodCount_sub = 0;
                                    AfterimageCompensationService.this.mAvgLum_sub = AfterimageCompensationService.nativeDataReadAvgLumSub();
                                    AfterimageCompensationService.this.mMaxBDI_sub = AfterimageCompensationService.nativeDataReadMaxBDISub();
                                    AfterimageCompensationService.this.mNBDI_sub = AfterimageCompensationService.nativeDataReadNBDISub();
                                    AfterimageCompensationService.this.mEffAvgLum_sub = AfterimageCompensationService.nativeDataReadEffAvgLumSub();
                                    AfterimageCompensationService.this.mEffMaxBDI_sub = AfterimageCompensationService.nativeDataReadEffMaxBDISub();
                                    AfterimageCompensationService.this.mEffNBDI_sub = AfterimageCompensationService.nativeDataReadEffNBDISub();
                                    Slog.i("AfterimageCompensationService", "mAvgLum_sub : " + AfterimageCompensationService.this.mAvgLum_sub + ", mMaxBDI_sub : " + AfterimageCompensationService.this.mMaxBDI_sub + ", mNBDI_sub : " + AfterimageCompensationService.this.mNBDI_sub);
                                    Slog.i("AfterimageCompensationService", "mEffAvgLum_sub : " + AfterimageCompensationService.this.mEffAvgLum_sub + ", mEffMaxBDI_sub : " + AfterimageCompensationService.this.mEffMaxBDI_sub + ", mEffNBDI_sub : " + AfterimageCompensationService.this.mEffNBDI_sub);
                                    if (AfterimageCompensationService.this.mApplyCount_sub < 100 && AfterimageCompensationService.this.mEffAvgLum_sub >= 50.0d && AfterimageCompensationService.this.mEffNBDI_sub >= AfterimageCompensationService.this.effNbdiTh_V6[AfterimageCompensationService.this.mApplyCount_sub]) {
                                        Slog.i("AfterimageCompensationService", "nativeDataUpdateSub");
                                        if (AfterimageCompensationService.nativeDataUpdateSub(AfterimageCompensationService.this.mApplyScaleEffect) == 0) {
                                            AfterimageCompensationService.this.mApplyCount_sub++;
                                            try {
                                                String str11 = AfterimageCompensationService.this.mApplyCount_sub + " " + String.format(Locale.US, "%.2f", Float.valueOf(AfterimageCompensationService.this.mApplyValue_sub));
                                                AfterimageCompensationService.this.fileWriteString("/efs/afc1/apply_count", str11);
                                                Slog.i("AfterimageCompensationService", "nativeDataUpdateSub - str : " + str11);
                                            } catch (NumberFormatException e12) {
                                                Slog.e("AfterimageCompensationService", "NumberFormatException : " + e12);
                                                AfterimageCompensationService.this.mApplyValue_sub = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                                            }
                                        }
                                    }
                                    AfterimageCompensationService.this.updateHWParam();
                                }
                            }
                        }
                        if (!AfterimageCompensationService.this.AfcThreadCondition && AfterimageCompensationService.this.mAfcThread != null) {
                            try {
                                synchronized (AfterimageCompensationService.this.mAfcThread) {
                                    AfterimageCompensationService.this.mAfcThread.wait();
                                }
                            } catch (InterruptedException unused4) {
                                this.mNormalValid = false;
                            }
                        }
                    } catch (Exception e13) {
                        if (AfterimageCompensationService.this.DEBUG) {
                            e13.printStackTrace();
                        }
                        str = "AfterimageCompensationService";
                        sb = new StringBuilder();
                    }
                } catch (Throwable th) {
                    Slog.i("AfterimageCompensationService", "AfcThread is Terminated - " + AfterimageCompensationService.this.interpolationCount);
                    AfterimageCompensationService.this.AfcThreadTerminateCondition = true;
                    throw th;
                }
            }
            str = "AfterimageCompensationService";
            sb = new StringBuilder();
            sb.append("AfcThread is Terminated - ");
            sb.append(AfterimageCompensationService.this.interpolationCount);
            Slog.i(str, sb.toString());
            AfterimageCompensationService.this.AfcThreadTerminateCondition = true;
        }
    }

    public final void writeLoggingDataEfs() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mAfcState);
        sb.append(" ");
        sb.append(this.interpolationCount);
        sb.append(" ");
        sb.append(this.interpolationLuminance);
        for (int i = 0; i < 12; i++) {
            sb.append(" ");
            sb.append(this.interpolationCoprRoi[i]);
        }
        Slog.i("AfterimageCompensationService", "AFC Loggin Data - " + ((Object) sb));
        fileWriteString("/efs/afc/logging_data", sb.toString());
    }

    public final boolean getBrightness() {
        if (!new File("/sys/class/lcd/panel/brt_avg").exists()) {
            return false;
        }
        try {
            String readStrFromFile = readStrFromFile("/sys/class/lcd/panel/brt_avg");
            if (readStrFromFile == null) {
                return false;
            }
            try {
                int parseInt = Integer.parseInt(readStrFromFile);
                this.mLuminance = parseInt;
                return parseInt >= 0;
            } catch (NumberFormatException e) {
                Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final boolean getBrightness_sub() {
        if (!new File("/sys/class/lcd/panel1/brt_avg").exists()) {
            return false;
        }
        try {
            String readStrFromFile = readStrFromFile("/sys/class/lcd/panel1/brt_avg");
            if (readStrFromFile == null) {
                return false;
            }
            try {
                int parseInt = Integer.parseInt(readStrFromFile);
                this.mLuminance_sub = parseInt;
                return parseInt >= 0;
            } catch (NumberFormatException e) {
                Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0206  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0345  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0242 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update_check_panel_id() {
        String str;
        String str2;
        String str3;
        Slog.i("AfterimageCompensationService", "update_cell_id() function start");
        String str4 = null;
        try {
            str = new File("/sys/class/lcd/panel/cell_id").exists() ? readStrFromFile("/sys/class/lcd/panel/cell_id") : null;
        } catch (Exception e) {
            e = e;
            str = null;
        }
        try {
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            str2 = null;
            Slog.i("AfterimageCompensationService", "USER_PANEL_INFO : " + str);
            Slog.i("AfterimageCompensationService", "USER_PANEL_INFO_SUB : " + str2);
            if (new File("/efs/afc/cell_id").exists()) {
            }
            if (!new File("/efs/afc1/cell_id").exists()) {
            }
            Slog.i("AfterimageCompensationService", "update_cell_id() function end");
        }
        if (new File("/sys/class/lcd/panel1/cell_id").exists()) {
            str2 = readStrFromFile("/sys/class/lcd/panel1/cell_id");
            Slog.i("AfterimageCompensationService", "USER_PANEL_INFO : " + str);
            Slog.i("AfterimageCompensationService", "USER_PANEL_INFO_SUB : " + str2);
            if (new File("/efs/afc/cell_id").exists()) {
                try {
                    str3 = readStrFromFile("/efs/afc/cell_id");
                } catch (Exception e3) {
                    e = e3;
                    str3 = null;
                }
                try {
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO : " + str3);
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                    if (str3 == null) {
                    }
                    if (str3 != null) {
                    }
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO , USER_PANEL_INFO Diff X");
                    if (!new File("/efs/afc1/cell_id").exists()) {
                    }
                    Slog.i("AfterimageCompensationService", "update_cell_id() function end");
                }
                if (str3 == null && !str3.startsWith(str)) {
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO , USER_PANEL_INFO Diff O");
                    if (fileWriteString("/efs/afc/cell_id", str)) {
                        Slog.i("AfterimageCompensationService", "fileWriteString success_1 USER_PANEL_INFO : " + str + " , EFS_PANEL_INFO : " + str3);
                    }
                    if (new File("/efs/afc/afc_data").exists() && new File("/efs/afc/afc_data").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_REG_DATA.delete() success");
                    }
                    if (new File("/efs/afc/logging_data").exists() && new File("/efs/afc/logging_data").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_LOGGING_DATA.delete() success");
                    }
                    if (new File("/efs/afc/rewrited").exists() && new File("/efs/afc/rewrited").delete()) {
                        Slog.i("AfterimageCompensationService", "MCA_REWRITED.delete() success");
                    }
                    if (new File("/efs/afc/original.vec").exists() && new File("/efs/afc/original.vec").delete()) {
                        Slog.i("AfterimageCompensationService", "MCA_ORG_VEC.delete() success");
                    }
                    if (new File("/efs/afc/org.vec").exists() && new File("/efs/afc/org.vec").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_ORIGINAL_VEC.delete() success");
                    }
                    if (new File("/efs/afc/time_data").exists() && new File("/efs/afc/time_data").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_TIME_DATA.delete() success");
                    }
                    if (new File("/efs/afc/diff_data").exists() && new File("/efs/afc/diff_data").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_DIFF_DATA.delete() success");
                    }
                    if (new File("/efs/afc/poc_data").exists() && new File("/efs/afc/poc_data").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_POC_DATA.delete() success");
                    }
                    if (new File("/efs/afc/apply_count").exists() && new File("/efs/afc/apply_count").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_APPLY_COUNT.delete() success");
                    }
                    if (new File("/efs/afc/mdnie_block").exists() && new File("/efs/afc/mdnie_block").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_MDNIE_BLOCK.delete() success");
                    }
                } else if (str3 != null && str != null) {
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO value is NULL");
                    fileWriteString("/efs/afc/cell_id", str);
                    Slog.i("AfterimageCompensationService", "UPDATE EFS_PANEL_INFO : " + str3);
                } else {
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO , USER_PANEL_INFO Diff X");
                }
            } else if (!new File("/efs/afc/cell_id").exists() && str != null && fileWriteString("/efs/afc/cell_id", str)) {
                Slog.i("AfterimageCompensationService", "fileWriteString success_2 USER_PANEL_INFO : " + str + " , EFS_PANEL_INFO : " + ((String) null));
            }
            if (!new File("/efs/afc1/cell_id").exists()) {
                try {
                    str4 = readStrFromFile("/efs/afc1/cell_id");
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO_SUB : " + str4);
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                if (str4 != null && !str4.startsWith(str2)) {
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO_SUB , USER_PANEL_INFO_SUB Diff O");
                    if (fileWriteString("/efs/afc1/cell_id", str2)) {
                        Slog.i("AfterimageCompensationService", "fileWriteString success_1 USER_PANEL_INFO_SUB : " + str2 + " , EFS_PANEL_INFO_SUB : " + str4);
                    }
                    if (new File("/efs/afc1/time_data").exists() && new File("/efs/afc1/time_data").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_TIME_DATA_SUB.delete() success");
                    }
                    if (new File("/efs/afc1/diff_data").exists() && new File("/efs/afc1/diff_data").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_DIFF_DATA_SUB.delete() success");
                    }
                    if (new File("/efs/afc1/poc_data").exists() && new File("/efs/afc1/poc_data").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_POC_DATA_SUB.delete() success");
                    }
                    if (new File("/efs/afc1/apply_count").exists() && new File("/efs/afc1/apply_count").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_APPLY_COUNT_SUB.delete() success");
                    }
                    if (new File("/efs/afc1/mdnie_block").exists() && new File("/efs/afc1/mdnie_block").delete()) {
                        Slog.i("AfterimageCompensationService", "AFC_MDNIE_BLOCK_SUB.delete() success");
                    }
                } else if (str4 == null && str2 != null) {
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO_SUB value is NULL");
                    fileWriteString("/efs/afc1/cell_id", str2);
                    Slog.i("AfterimageCompensationService", "UPDATE EFS_PANEL_INFO_SUB : " + str4);
                } else {
                    Slog.i("AfterimageCompensationService", "EFS_PANEL_INFO_SUB , USER_PANEL_INFO_SUB Diff X");
                }
            } else if (!new File("/efs/afc1/cell_id").exists() && str2 != null && fileWriteString("/efs/afc1/cell_id", str2)) {
                Slog.i("AfterimageCompensationService", "fileWriteString success_3 USER_PANEL_INFO_SUB : " + str2 + " , EFS_PANEL_INFO_SUB : " + ((String) null));
            }
            Slog.i("AfterimageCompensationService", "update_cell_id() function end");
        }
        str2 = null;
        Slog.i("AfterimageCompensationService", "USER_PANEL_INFO : " + str);
        Slog.i("AfterimageCompensationService", "USER_PANEL_INFO_SUB : " + str2);
        if (new File("/efs/afc/cell_id").exists()) {
        }
        if (!new File("/efs/afc1/cell_id").exists()) {
        }
        Slog.i("AfterimageCompensationService", "update_cell_id() function end");
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0045 -> B:14:0x005e). Please report as a decompilation issue!!! */
    public final void processApplyData_main() {
        if (new File("/efs/afc/apply_count").exists()) {
            try {
                String stringFromFile = getStringFromFile("/efs/afc/apply_count");
                if (stringFromFile != null) {
                    String[] split = stringFromFile.trim().split(" ");
                    try {
                        if (split.length == 2) {
                            this.mApplyCount = Integer.parseInt(split[0].trim());
                            this.mApplyValue = Float.parseFloat(split[1].trim());
                        } else {
                            this.mApplyCount = 0;
                            this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                        }
                    } catch (NumberFormatException e) {
                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        Slog.i("AfterimageCompensationService", "processApplyData_main() mApplyCount : " + this.mApplyCount + " , mApplyValue : " + this.mApplyValue);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0045 -> B:14:0x005e). Please report as a decompilation issue!!! */
    public final void processApplyData_sub() {
        if (new File("/efs/afc1/apply_count").exists()) {
            try {
                String stringFromFile = getStringFromFile("/efs/afc1/apply_count");
                if (stringFromFile != null) {
                    String[] split = stringFromFile.trim().split(" ");
                    try {
                        if (split.length == 2) {
                            this.mApplyCount_sub = Integer.parseInt(split[0].trim());
                            this.mApplyValue_sub = Float.parseFloat(split[1].trim());
                        } else {
                            this.mApplyCount_sub = 0;
                            this.mApplyValue_sub = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                        }
                    } catch (NumberFormatException e) {
                        Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        Slog.i("AfterimageCompensationService", "processApplyData_sub() mApplyCount_sub : " + this.mApplyCount_sub + " , mApplyValue_sub : " + this.mApplyValue_sub);
    }

    public final void updateHWParam() {
        String[] strArr = {Integer.toString(this.mApplyCount), Float.toString(this.mApplyValue), Double.toString(this.mAvgLum), Double.toString(this.mMaxBDI), Double.toString(this.mNBDI), Double.toString(this.mEffAvgLum), Double.toString(this.mEffMaxBDI), Double.toString(this.mEffNBDI), Integer.toString(this.mApplyCount_sub), Float.toString(this.mApplyValue_sub), Double.toString(this.mAvgLum_sub), Double.toString(this.mMaxBDI_sub), Double.toString(this.mNBDI_sub), Double.toString(this.mEffAvgLum_sub), Double.toString(this.mEffMaxBDI_sub), Double.toString(this.mEffNBDI_sub)};
        String[] strArr2 = mAFPC_KEYS;
        handleSendBroadcastToHWParam("DIQE", parseBigData(strArr2, strArr, strArr2.length));
    }

    public final void handleSendBroadcastToHWParam(String str, String str2) {
        if (this.mSemHqmManager != null) {
            Slog.i("AfterimageCompensationService", "sendBroadcastToHWParam() mSemHqmManager.sendHWParamToHQM");
            this.mSemHqmManager.sendHWParamToHQM(0, "Display", str, "sm", "0.0", "sec", "", str2, "");
        } else {
            Slog.d("AfterimageCompensationService", "sendBroadcastToHWParam() mSemHqmManager is null");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String parseBigData(String[] strArr, String[] strArr2, int i) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = null;
        try {
            jSONObject = new JSONObject();
            for (int i2 = 0; i2 < i; i2++) {
                try {
                    jSONObject.put(strArr[i2], strArr2[i2]);
                } catch (JSONException e) {
                    e = e;
                    jSONObject2 = jSONObject;
                    e.printStackTrace();
                    jSONObject = jSONObject2;
                    String replaceAll = jSONObject.toString().replaceAll("\\{", "").replaceAll("\\}", "");
                    if (this.DEBUG) {
                    }
                    return replaceAll;
                }
            }
        } catch (JSONException e2) {
            e = e2;
        }
        String replaceAll2 = jSONObject.toString().replaceAll("\\{", "").replaceAll("\\}", "");
        if (this.DEBUG) {
            Slog.d("AfterimageCompensationService", "customDataSet : " + replaceAll2);
        }
        return replaceAll2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v9 */
    public final String getStringFromFile(String str) {
        FileInputStream fileInputStream;
        byte[] bArr = new byte[128];
        for (int i = 0; i < 128; i++) {
            bArr[i] = 0;
        }
        FileInputStream fileInputStream2 = null;
        r1 = 0;
        ?? r1 = 0;
        FileInputStream fileInputStream3 = null;
        FileInputStream fileInputStream4 = null;
        try {
            try {
                try {
                    fileInputStream = new FileInputStream(new File(str));
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    int read = fileInputStream.read(bArr);
                    r1 = read != 0 ? new String(bArr, 0, read - 1, StandardCharsets.UTF_8) : 0;
                    fileInputStream.close();
                    try {
                        fileInputStream.close();
                        return r1;
                    } catch (IOException unused) {
                        Slog.e("AfterimageCompensationService", "File Close error");
                        return r1;
                    }
                } catch (FileNotFoundException e) {
                    e = e;
                    bArr = r1;
                    fileInputStream4 = fileInputStream;
                    Slog.e("AfterimageCompensationService", "FileNotFoundException : " + e);
                    if (fileInputStream4 != null) {
                        fileInputStream4.close();
                    }
                    return bArr;
                } catch (IOException e2) {
                    e = e2;
                    bArr = r1;
                    fileInputStream2 = fileInputStream;
                    e.printStackTrace();
                    Slog.e("AfterimageCompensationService", "IOException : " + e);
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    return bArr;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream3 = fileInputStream;
                    if (fileInputStream3 != null) {
                        try {
                            fileInputStream3.close();
                        } catch (IOException unused2) {
                            Slog.e("AfterimageCompensationService", "File Close error");
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e3) {
                e = e3;
                bArr = null;
            } catch (IOException e4) {
                e = e4;
                bArr = null;
            }
        } catch (IOException unused3) {
            Slog.e("AfterimageCompensationService", "File Close error");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v8, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r8v9 */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11, types: [java.nio.channels.FileChannel] */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.nio.channels.FileChannel] */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.nio.channels.FileChannel] */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    public static void fileCopy(String e, String str) {
        FileInputStream fileInputStream;
        FileChannel fileChannel = null;
        try {
            try {
                try {
                    fileInputStream = new FileInputStream((String) e);
                    try {
                        e = new FileOutputStream((String) str);
                    } catch (IOException e2) {
                        e = e2;
                        e = 0;
                        str = 0;
                    } catch (Throwable th) {
                        th = th;
                        e = 0;
                        str = 0;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    str = fileInputStream.getChannel();
                    try {
                        fileChannel = e.getChannel();
                        str.transferTo(0L, str.size(), fileChannel);
                        if (fileChannel != null) {
                            fileChannel.close();
                        }
                        str.close();
                        fileInputStream.close();
                        e.close();
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        if (fileChannel != null) {
                            fileChannel.close();
                        }
                        if (str != 0) {
                            str.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (e != 0) {
                            e.close();
                        }
                    }
                } catch (IOException e4) {
                    e = e4;
                    str = 0;
                } catch (Throwable th3) {
                    th = th3;
                    str = 0;
                    if (fileChannel != null) {
                        try {
                            fileChannel.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                            throw th;
                        }
                    }
                    if (str != 0) {
                        str.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (e != 0) {
                        e.close();
                    }
                    throw th;
                }
            } catch (IOException e6) {
                e = e6;
                e = 0;
                str = 0;
                fileInputStream = null;
            } catch (Throwable th4) {
                th = th4;
                e = 0;
                str = 0;
                fileInputStream = null;
            }
        } catch (IOException e7) {
            e = e7;
            e.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v30 */
    /* JADX WARN: Type inference failed for: r7v37 */
    /* JADX WARN: Type inference failed for: r7v39 */
    /* JADX WARN: Type inference failed for: r7v40 */
    /* JADX WARN: Type inference failed for: r7v41 */
    /* JADX WARN: Type inference failed for: r7v42 */
    /* JADX WARN: Type inference failed for: r7v43 */
    /* JADX WARN: Type inference failed for: r7v44 */
    /* JADX WARN: Type inference failed for: r7v45 */
    /* JADX WARN: Type inference failed for: r7v46 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.io.BufferedReader] */
    public static String readStrFromFile(String str) {
        FileReader fileReader;
        Throwable th;
        BufferedReader bufferedReader;
        PrintStream printStream;
        StringBuilder sb;
        String str2 = null;
        try {
            try {
                fileReader = new FileReader((String) str);
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                bufferedReader = new BufferedReader(fileReader);
                try {
                    str2 = bufferedReader.readLine();
                    try {
                        bufferedReader.close();
                        str = bufferedReader;
                    } catch (IOException e) {
                        PrintStream printStream2 = System.out;
                        String str3 = "BufferedReader Close IOException : " + e.getMessage();
                        printStream2.println(str3);
                        str = str3;
                    }
                    try {
                        fileReader.close();
                    } catch (IOException e2) {
                        e = e2;
                        printStream = System.out;
                        sb = new StringBuilder();
                        sb.append("FileReader Close IOException : ");
                        sb.append(e.getMessage());
                        printStream.println(sb.toString());
                        return str2;
                    }
                } catch (FileNotFoundException e3) {
                    e = e3;
                    e.printStackTrace();
                    str = bufferedReader;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                            str = bufferedReader;
                        } catch (IOException e4) {
                            PrintStream printStream3 = System.out;
                            String str4 = "BufferedReader Close IOException : " + e4.getMessage();
                            printStream3.println(str4);
                            str = str4;
                        }
                    }
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (IOException e5) {
                            e = e5;
                            printStream = System.out;
                            sb = new StringBuilder();
                            sb.append("FileReader Close IOException : ");
                            sb.append(e.getMessage());
                            printStream.println(sb.toString());
                            return str2;
                        }
                    }
                    return str2;
                } catch (IOException e6) {
                    e = e6;
                    e.printStackTrace();
                    str = bufferedReader;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                            str = bufferedReader;
                        } catch (IOException e7) {
                            PrintStream printStream4 = System.out;
                            String str5 = "BufferedReader Close IOException : " + e7.getMessage();
                            printStream4.println(str5);
                            str = str5;
                        }
                    }
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (IOException e8) {
                            e = e8;
                            printStream = System.out;
                            sb = new StringBuilder();
                            sb.append("FileReader Close IOException : ");
                            sb.append(e.getMessage());
                            printStream.println(sb.toString());
                            return str2;
                        }
                    }
                    return str2;
                }
            } catch (FileNotFoundException e9) {
                e = e9;
                bufferedReader = null;
            } catch (IOException e10) {
                e = e10;
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                str = 0;
                if (str != 0) {
                    try {
                        str.close();
                    } catch (IOException e11) {
                        System.out.println("BufferedReader Close IOException : " + e11.getMessage());
                    }
                }
                if (fileReader == null) {
                    throw th;
                }
                try {
                    fileReader.close();
                    throw th;
                } catch (IOException e12) {
                    System.out.println("FileReader Close IOException : " + e12.getMessage());
                    throw th;
                }
            }
        } catch (FileNotFoundException e13) {
            e = e13;
            bufferedReader = null;
            fileReader = null;
        } catch (IOException e14) {
            e = e14;
            bufferedReader = null;
            fileReader = null;
        } catch (Throwable th4) {
            fileReader = null;
            th = th4;
            str = 0;
        }
        return str2;
    }

    public final boolean fileWriteString(String str, String str2) {
        if (this.DEBUG) {
            Slog.i("AfterimageCompensationService", "fileWriteString : " + str + "  value : " + str2);
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                try {
                    fileOutputStream2.write(str2.getBytes());
                    fileOutputStream2.close();
                    return true;
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return false;
                }
            } catch (FileNotFoundException unused) {
                Slog.d("AfterimageCompensationService", "fileWriteString : file not found : " + str);
                return false;
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    public boolean afpcDataVerify() {
        Slog.i("AfterimageCompensationService", "afpcDataVerify() - " + this.mAfpcSize + ", " + this.mAfpcSize);
        return nativeDataVerify() == 0;
    }

    public boolean afpcDataApply() {
        Slog.i("AfterimageCompensationService", "afpcDataApply()");
        int i = this.mAfcType;
        if (i != 3 && i < 5) {
            return false;
        }
        if (new File("/efs/afc/mdnie_block").exists()) {
            nativeDataApply(this.mApplyScaleEffect);
            return true;
        }
        Slog.i("AfterimageCompensationService", "afpcDataApply - AFC_MDNIE_BLOCK not exist");
        return false;
    }

    public boolean afpcDataOff() {
        Slog.i("AfterimageCompensationService", "afpcDataOff()");
        int i = this.mAfcType;
        if (i != 3 && i < 5) {
            return false;
        }
        nativeDataOff();
        return true;
    }

    public boolean afpcWorkOff() {
        Slog.i("AfterimageCompensationService", "afpcWorkOff()");
        this.AfcStateCondition = false;
        return true;
    }

    public boolean afpcDataWrite() {
        Slog.i("AfterimageCompensationService", "afpcDataWrite()");
        if (this.mAfcType == 2 && this.mThreadAFPC && this.AfcStateCondition && this.mApplyCount < this.mAfpcJndRef.length) {
            float nativeDataEvaluate = nativeDataEvaluate();
            Locale locale = Locale.US;
            float parseFloat = Float.parseFloat(String.format(locale, "%.2f", Float.valueOf(nativeDataEvaluate)));
            this.mApplyValue = parseFloat;
            if (parseFloat >= this.mAfpcJndRef[this.mApplyCount] && nativeDataWrite(this.mApplyScaleEffect) == 0) {
                this.mApplyCount++;
                try {
                    String str = this.mApplyCount + " " + String.format(locale, "%.2f", Float.valueOf(this.mApplyValue));
                    fileWriteString("/efs/afc/apply_count", str);
                    Slog.i("AfterimageCompensationService", "afpcDataWrite - str : " + str);
                } catch (NumberFormatException e) {
                    Slog.e("AfterimageCompensationService", "NumberFormatException : " + e);
                    this.mApplyValue = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
                }
                return true;
            }
        }
        return false;
    }
}
