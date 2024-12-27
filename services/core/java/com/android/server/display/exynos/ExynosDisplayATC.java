package com.android.server.display.exynos;

import android.R;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.util.NetworkConstants;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public final class ExynosDisplayATC {
    public static boolean TUNE_MODE;
    public final String mApsInit;
    public final Context mContext;
    public AnonymousClass2 mCountdownTimer;
    public final Sensor mLightSensor;
    public final Handler mLocalHandler;
    public final SensorManager mSensorManager;
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public boolean mLightSensorEnabled = false;
    public final String ATC_DIM_STATUS_SYSFS_PATH = "/sys/class/dqe/dqe/dim_status";
    public final String ATC_SFR_SYSFS_PATH = "/sys/class/dqe/dqe/aps";
    public final String ATC_LUX_SYSFS_PATH = "/sys/class/dqe/dqe/aps_lux";
    public final String ATC_ONOFF_SYSFS_PATH = "/sys/class/dqe/dqe/aps_onoff";
    public final String HSC48_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_idx";
    public final String HSC48_LCG_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_lcg";
    public final String HSC_SYSFS_PATH = "/sys/class/dqe/dqe/hsc";
    public String mQsize = "8";
    public String mQcoeff = "10,5,4,3,2,1,1,1";
    public String mQalcoeff = "4";
    public String mQdelay = "0";
    public String[] mItem = null;
    public final String ATC_CAL_FILE_PATH = "/data/dqe/calib_data_atc.xml";
    public final String ATC_BL_FILE_PATH = "/data/dqe/calib_data_atc_bl.xml";
    public final String ATC_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_atc.xml";
    public final Object mDataCollectionLock = new Object();
    public final Deque mLastSensorReadings = new ArrayDeque();
    public int[] mAmbientLight = {
        0, 3000, 4000, 5000, 6000, 8000, 10000, 15000, 20000, 25000, 30000, 40000, 50000
    };
    public String[] mApsTable = null;
    public String[] mQueSizeTable = null;
    public String[] mQueCoeffTable = null;
    public String[] mQueDelayTable = null;
    public String[] mQueAlCoeffTable = null;
    public String[] mHsvTable = null;
    public String[][] mHsvLcgTable = null;
    public int[] mApsDelayedOffTable = {0};
    public int mLastLuminance = 0;
    public int mPrevLuminance = 0;
    public String mLastAps = null;
    public String mPrevAps = null;
    public String mLastHsv = null;
    public String mPrevHsv = null;
    public String[] mLastHsvLcg = null;
    public String[] mPrevHsvLcg = null;
    public boolean mPreOnOff = false;
    public int mLastApsDelayedOff = 0;
    public boolean mApsDelayedOffRequired = false;
    public int mEventCount = 0;
    public int mPrevPos = -1;
    public int mDimOperating = 0;
    public int mTimeoutMs = NetworkConstants.ETHER_MTU;
    public int mIntervalMs = 500;
    public final int[] mBrightnessInit = {0};
    public int[] mBrightnessLux = {0};
    public int[] mBrightnessSetting = {0};
    public final Object mLock = new Object();
    public final AnonymousClass1 mHandler =
            new Handler() { // from class: com.android.server.display.exynos.ExynosDisplayATC.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    String[] strArr;
                    super.handleMessage(message);
                    int i = message.what;
                    ExynosDisplayATC exynosDisplayATC = ExynosDisplayATC.this;
                    switch (i) {
                        case 1:
                            ExynosDisplayUtils.sendEmptyUpdate();
                            break;
                        case 2:
                            int i2 = message.arg1;
                            if (exynosDisplayATC.mLightSensorEnabled
                                    && ExynosDisplayATC.TUNE_MODE
                                    && exynosDisplayATC.mPrevLuminance != i2) {
                                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                        i2, "lux: ", "ExynosDisplayATC");
                                ExynosDisplayUtils.sysfsWrite(
                                        i2, exynosDisplayATC.ATC_LUX_SYSFS_PATH);
                                exynosDisplayATC.mPrevLuminance = i2;
                                break;
                            }
                            break;
                        case 3:
                            String obj = message.obj.toString();
                            int i3 = message.arg1;
                            if (exynosDisplayATC.mLightSensorEnabled) {
                                String str = exynosDisplayATC.mPrevAps;
                                if (str != null && obj.equals(str)) {
                                    if (ExynosDisplayATC.TUNE_MODE) {
                                        Log.d("ExynosDisplayATC", "aps skip : ".concat(obj));
                                        break;
                                    }
                                } else {
                                    if (ExynosDisplayATC.TUNE_MODE) {
                                        DualAppManagerService$$ExternalSyntheticOutline0.m(
                                                "aps: ", obj, "ExynosDisplayATC");
                                    }
                                    exynosDisplayATC.sysfsWriteOnOff(true);
                                    ExynosDisplayUtils.sysfsWriteSting(
                                            exynosDisplayATC.ATC_SFR_SYSFS_PATH, obj);
                                    exynosDisplayATC.mPrevAps = obj;
                                    exynosDisplayATC.mApsDelayedOffRequired = i3 != 0;
                                    exynosDisplayATC.startCountDownTimer();
                                    break;
                                }
                            }
                            break;
                        case 4:
                            String obj2 = message.obj.toString();
                            if (exynosDisplayATC.mLightSensorEnabled) {
                                String str2 = exynosDisplayATC.mPrevHsv;
                                if (str2 != null && obj2.equals(str2)) {
                                    if (ExynosDisplayATC.TUNE_MODE) {
                                        Log.d("ExynosDisplayATC", "hsv skip as same");
                                        break;
                                    }
                                } else {
                                    if (ExynosDisplayATC.TUNE_MODE) {
                                        DualAppManagerService$$ExternalSyntheticOutline0.m(
                                                "hsv: ", obj2, "ExynosDisplayATC");
                                    }
                                    ExynosDisplayUtils.sysfsWriteSting(
                                            exynosDisplayATC.HSC_SYSFS_PATH, obj2);
                                    exynosDisplayATC.mPrevHsv = obj2;
                                    exynosDisplayATC.startCountDownTimer();
                                    break;
                                }
                            }
                            break;
                        case 5:
                            String obj3 = message.obj.toString();
                            int i4 = message.arg1;
                            if (exynosDisplayATC.mLightSensorEnabled) {
                                if (i4 < 3 && (strArr = exynosDisplayATC.mPrevHsvLcg) != null) {
                                    String str3 = strArr[i4];
                                    if (str3 != null && obj3.equals(str3)) {
                                        if (ExynosDisplayATC.TUNE_MODE) {
                                            Log.d("ExynosDisplayATC", "hsvlcg skip as same");
                                            break;
                                        }
                                    } else {
                                        if (ExynosDisplayATC.TUNE_MODE) {
                                            DualAppManagerService$$ExternalSyntheticOutline0.m(
                                                    "hsv lcg: ", obj3, "ExynosDisplayATC");
                                        }
                                        ExynosDisplayUtils.sysfsWriteSting(
                                                exynosDisplayATC.HSC48_IDX_SYSFS_PATH,
                                                Integer.toString(i4));
                                        ExynosDisplayUtils.sysfsWriteSting(
                                                exynosDisplayATC.HSC48_LCG_SYSFS_PATH, obj3);
                                        exynosDisplayATC.mPrevHsvLcg[i4] = obj3;
                                        exynosDisplayATC.startCountDownTimer();
                                        break;
                                    }
                                } else if (ExynosDisplayATC.TUNE_MODE) {
                                    Log.d("ExynosDisplayATC", "hsvlcg skip as invalid");
                                    break;
                                }
                            }
                            break;
                        case 6:
                            if (exynosDisplayATC.mApsDelayedOffRequired) {
                                exynosDisplayATC.sysfsWriteOnOff(false);
                            }
                            exynosDisplayATC.mApsDelayedOffRequired = false;
                            break;
                    }
                }
            };
    public final AnonymousClass3 sensorListener =
            new SensorEventListener() { // from class:
                                        // com.android.server.display.exynos.ExynosDisplayATC.3
                @Override // android.hardware.SensorEventListener
                public final void onAccuracyChanged(Sensor sensor, int i) {}

                @Override // android.hardware.SensorEventListener
                public final void onSensorChanged(SensorEvent sensorEvent) {
                    ExynosDisplayATC exynosDisplayATC = ExynosDisplayATC.this;
                    exynosDisplayATC.getClass();
                    SystemClock.elapsedRealtimeNanos();
                    synchronized (exynosDisplayATC.mDataCollectionLock) {
                        try {
                            if (((ArrayDeque) exynosDisplayATC.mLastSensorReadings).isEmpty()
                                    || sensorEvent.timestamp
                                            >= ((LightData)
                                                            ((ArrayDeque)
                                                                            exynosDisplayATC
                                                                                    .mLastSensorReadings)
                                                                    .getLast())
                                                    .timestamp) {
                                String[] split = exynosDisplayATC.mQsize.split("\\s*,\\s*");
                                exynosDisplayATC.mItem = split;
                                int parseInt = Integer.parseInt(split[0]);
                                while (!((ArrayDeque) exynosDisplayATC.mLastSensorReadings)
                                                .isEmpty()
                                        && ((ArrayDeque) exynosDisplayATC.mLastSensorReadings)
                                                        .size()
                                                >= parseInt) {}
                                LightData lightData = new LightData();
                                lightData.timestamp = sensorEvent.timestamp;
                                lightData.lux = sensorEvent.values[0];
                                ((ArrayDeque) exynosDisplayATC.mLastSensorReadings)
                                        .addLast(lightData);
                            } else {
                                Log.d("ExynosDisplayATC", "Ignore event " + sensorEvent.values[0]);
                            }
                        } finally {
                        }
                    }
                    if (ExynosDisplayATC.TUNE_MODE) {
                        ExynosDisplayATC.this.printSensorDeque();
                    }
                    int i = (int) sensorEvent.values[0];
                    ExynosDisplayATC.this.caculateLuminance();
                    ExynosDisplayATC.this.loadLuminanceATCTable();
                    ExynosDisplayATC exynosDisplayATC2 = ExynosDisplayATC.this;
                    String[] split2 = exynosDisplayATC2.mQdelay.split("\\s*,\\s*");
                    exynosDisplayATC2.mItem = split2;
                    int parseInt2 = Integer.parseInt(split2[0]);
                    if (ExynosDisplayATC.TUNE_MODE) {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                parseInt2, "qdelay: ", "ExynosDisplayATC");
                    }
                    if (parseInt2 > 0) {
                        ExynosDisplayATC exynosDisplayATC3 = ExynosDisplayATC.this;
                        int i2 = exynosDisplayATC3.mEventCount;
                        int i3 = i2 + 1;
                        exynosDisplayATC3.mEventCount = i3;
                        if (i2 < parseInt2) {
                            return;
                        }
                        if (i3 >= parseInt2) {
                            exynosDisplayATC3.mEventCount = 0;
                        }
                    }
                    ExynosDisplayATC.this.sendMessage();
                    if (ExynosDisplayATC.TUNE_MODE) {
                        ExynosDisplayATC.this.setBrightnessAdjustment(i);
                    }
                }
            };

    /* renamed from: com.android.server.display.exynos.ExynosDisplayATC$2, reason: invalid class name */
    public final class AnonymousClass2 extends CountDownTimer {
        public AnonymousClass2(long j, long j2) {
            super(j, j2);
        }

        @Override // android.os.CountDownTimer
        public final void onFinish() {
            AnonymousClass1 anonymousClass1 = ExynosDisplayATC.this.mHandler;
            if (anonymousClass1 != null) {
                anonymousClass1.sendEmptyMessage(1);
            }
            ExynosDisplayATC exynosDisplayATC = ExynosDisplayATC.this;
            exynosDisplayATC.getClass();
            String stringFromFile =
                    ExynosDisplayUtils.getStringFromFile(
                            exynosDisplayATC.ATC_DIM_STATUS_SYSFS_PATH);
            if (stringFromFile == null) {
                exynosDisplayATC.mDimOperating = 0;
            } else {
                exynosDisplayATC.mDimOperating = Integer.parseInt(stringFromFile);
            }
            ExynosDisplayATC exynosDisplayATC2 = ExynosDisplayATC.this;
            if (exynosDisplayATC2.mDimOperating != 0) {
                if (exynosDisplayATC2.DEBUG) {
                    Log.d("ExynosDisplayATC", "Restart Refresh Timer as dimming started");
                }
                cancel();
                start();
                return;
            }
            AnonymousClass1 anonymousClass12 = exynosDisplayATC2.mHandler;
            if (anonymousClass12 != null) {
                anonymousClass12.sendEmptyMessage(6);
            }
            if (ExynosDisplayATC.TUNE_MODE) {
                StringBuilder sb = new StringBuilder("Finished Refresh Timer interval: ");
                sb.append(ExynosDisplayATC.this.mIntervalMs);
                sb.append(", timeout: ");
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        sb, ExynosDisplayATC.this.mTimeoutMs, "ExynosDisplayATC");
            }
        }

        @Override // android.os.CountDownTimer
        public final void onTick(long j) {
            ExynosDisplayATC exynosDisplayATC = ExynosDisplayATC.this;
            exynosDisplayATC.getClass();
            AnonymousClass1 anonymousClass1 = exynosDisplayATC.mHandler;
            if (anonymousClass1 != null) {
                anonymousClass1.sendEmptyMessage(1);
            }
        }
    }

    public final class LightData {
        public float lux;
        public long timestamp;
    }

    static {
        TimeUnit.SECONDS.toNanos(10L);
    }

    public ExynosDisplayATC(Context context) {
        this.mLightSensor = null;
        this.mSensorManager = null;
        this.mApsInit = null;
        this.mCountdownTimer = null;
        this.mContext = context;
        SensorManager sensorManager = (SensorManager) context.getSystemService(SensorManager.class);
        this.mSensorManager = sensorManager;
        this.mLightSensor = sensorManager.getDefaultSensor(5);
        this.mLocalHandler = new Handler(context.getMainLooper());
        String[] parserXMLALText =
                ExynosDisplayUtils.parserXMLALText(
                        0, "/vendor/etc/dqe/calib_data_bypass.xml", "aps");
        if (parserXMLALText == null || parserXMLALText.length <= 0) {
            Log.d("ExynosDisplayATC", "xml aps not found");
            this.mApsInit = "0,0,128,128,128,0,10,14,2,0,25,230,140,250,0,3,3,2,3,128,1";
        } else {
            this.mApsInit = parserXMLALText[0];
        }
        this.mCountdownTimer = new AnonymousClass2(this.mTimeoutMs, this.mIntervalMs);
    }

    public static void enableATCTuneMode(boolean z) {
        if (z != TUNE_MODE) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    "enableATCTuneMode: TUNE_MODE=", "ExynosDisplayATC", z);
        }
        TUNE_MODE = z;
    }

    public final void caculateLuminance() {
        try {
            int size = ((ArrayDeque) this.mLastSensorReadings).size();
            float[] fArr = new float[size];
            Iterator it = ((ArrayDeque) this.mLastSensorReadings).iterator();
            int i = 0;
            int i2 = 0;
            while (it.hasNext()) {
                fArr[i2] = ((LightData) it.next()).lux;
                i2++;
            }
            String[] split = this.mQsize.split("\\s*,\\s*");
            this.mItem = split;
            int parseInt = Integer.parseInt(split[0]);
            int[] iArr = new int[parseInt];
            this.mItem = this.mQcoeff.split("\\s*,\\s*");
            for (int i3 = 0; i3 < parseInt; i3++) {
                iArr[i3] = 0;
            }
            int i4 = 0;
            while (true) {
                String[] strArr = this.mItem;
                if (i4 >= strArr.length) {
                    break;
                }
                iArr[i4] = Integer.parseInt(strArr[i4]);
                i4++;
            }
            String[] split2 = this.mQalcoeff.split("\\s*,\\s*");
            this.mItem = split2;
            int parseInt2 = Integer.parseInt(split2[0]);
            if (TUNE_MODE) {
                String str = "qcoef: ";
                for (int i5 = 0; i5 < parseInt; i5++) {
                    str = str + Integer.toString(iArr[i5]) + ",";
                }
                Log.d("ExynosDisplayATC", str + " qalcoeff: " + parseInt2);
            }
            int i6 = 0;
            int i7 = 0;
            for (int i8 = size - 1; i8 >= 0; i8--) {
                if (size < parseInt) {
                    int i9 = (int) fArr[i8];
                    int i10 = iArr[i6];
                    i7 += i10;
                    i6++;
                    i = (i9 * i10) + i;
                } else {
                    int i11 = (int) fArr[i8];
                    int i12 = iArr[i6];
                    i7 = i7 + i12 + parseInt2;
                    i6++;
                    i = (this.mLastLuminance * parseInt2) + (i11 * i12) + i;
                }
            }
            this.mLastLuminance = i / i7;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void enableATC(boolean z) {
        resetATC();
        if (z) {
            String pathWithPanel =
                    !TUNE_MODE
                            ? ExynosDisplayUtils.getPathWithPanel(this.ATC_XML_FILE_PATH)
                            : ExynosDisplayUtils.getPathWithPanel(this.ATC_CAL_FILE_PATH);
            if (pathWithPanel != null) {
                try {
                    String[] parserFactoryXMLAttribute =
                            ExynosDisplayUtils.parserFactoryXMLAttribute(
                                    pathWithPanel, "tune", "atc", "al");
                    if (parserFactoryXMLAttribute != null) {
                        if (parserFactoryXMLAttribute.length <= 0) {
                            Log.e(
                                    "ExynosDisplayATC",
                                    "xml array size wrong: " + parserFactoryXMLAttribute.length);
                        } else {
                            synchronized (this.mLock) {
                                try {
                                    Log.d(
                                            "ExynosDisplayATC",
                                            "array_length: " + parserFactoryXMLAttribute.length);
                                    this.mAmbientLight = new int[parserFactoryXMLAttribute.length];
                                    for (int i = 0; i < parserFactoryXMLAttribute.length; i++) {
                                        this.mAmbientLight[i] =
                                                Integer.parseInt(parserFactoryXMLAttribute[i]);
                                        Log.d("ExynosDisplayATC", "al: " + this.mAmbientLight[i]);
                                    }
                                    int[] iArr = this.mAmbientLight;
                                    this.mApsTable = new String[iArr.length];
                                    this.mApsDelayedOffTable = new int[iArr.length];
                                    this.mQueSizeTable = new String[iArr.length];
                                    this.mQueCoeffTable = new String[iArr.length];
                                    this.mQueDelayTable = new String[iArr.length];
                                    this.mQueAlCoeffTable = new String[iArr.length];
                                    this.mHsvTable = new String[iArr.length];
                                    this.mHsvLcgTable =
                                            (String[][])
                                                    Array.newInstance(
                                                            (Class<?>) String.class,
                                                            iArr.length,
                                                            3);
                                    int i2 = 0;
                                    while (true) {
                                        int[] iArr2 = this.mAmbientLight;
                                        if (i2 >= iArr2.length) {
                                            break;
                                        }
                                        String[] parserFactoryXMLALText =
                                                ExynosDisplayUtils.parserFactoryXMLALText(
                                                        iArr2[i2], 0, pathWithPanel, "aps");
                                        if (parserFactoryXMLALText != null) {
                                            this.mApsTable[i2] = parserFactoryXMLALText[0];
                                        }
                                        String[] parserFactoryXMLALText2 =
                                                ExynosDisplayUtils.parserFactoryXMLALText(
                                                        this.mAmbientLight[i2],
                                                        0,
                                                        pathWithPanel,
                                                        "apsdoff");
                                        if (parserFactoryXMLALText2 != null) {
                                            this.mApsDelayedOffTable[i2] =
                                                    Integer.parseInt(parserFactoryXMLALText2[0]);
                                        } else {
                                            this.mApsDelayedOffTable[i2] = 0;
                                        }
                                        String[] parserFactoryXMLALText3 =
                                                ExynosDisplayUtils.parserFactoryXMLALText(
                                                        this.mAmbientLight[i2],
                                                        0,
                                                        pathWithPanel,
                                                        "qsize");
                                        if (parserFactoryXMLALText3 != null) {
                                            this.mQueSizeTable[i2] = parserFactoryXMLALText3[0];
                                        }
                                        String[] parserFactoryXMLALText4 =
                                                ExynosDisplayUtils.parserFactoryXMLALText(
                                                        this.mAmbientLight[i2],
                                                        0,
                                                        pathWithPanel,
                                                        "qcoeff");
                                        if (parserFactoryXMLALText4 != null) {
                                            this.mQueCoeffTable[i2] = parserFactoryXMLALText4[0];
                                        }
                                        String[] parserFactoryXMLALText5 =
                                                ExynosDisplayUtils.parserFactoryXMLALText(
                                                        this.mAmbientLight[i2],
                                                        0,
                                                        pathWithPanel,
                                                        "qdelay");
                                        if (parserFactoryXMLALText5 != null) {
                                            this.mQueDelayTable[i2] = parserFactoryXMLALText5[0];
                                        }
                                        String[] parserFactoryXMLALText6 =
                                                ExynosDisplayUtils.parserFactoryXMLALText(
                                                        this.mAmbientLight[i2],
                                                        0,
                                                        pathWithPanel,
                                                        "qalcoeff");
                                        if (parserFactoryXMLALText6 != null) {
                                            this.mQueAlCoeffTable[i2] = parserFactoryXMLALText6[0];
                                        }
                                        for (int i3 = 0; i3 < 3; i3++) {
                                            String[] parserFactoryXMLALText7 =
                                                    ExynosDisplayUtils.parserFactoryXMLALText(
                                                            this.mAmbientLight[i2],
                                                            i3,
                                                            pathWithPanel,
                                                            "hsc48_lcg");
                                            if (parserFactoryXMLALText7 != null) {
                                                this.mHsvLcgTable[i2][i3] =
                                                        parserFactoryXMLALText7[0];
                                            } else {
                                                this.mHsvLcgTable[i2][i3] = null;
                                            }
                                        }
                                        String[] parserFactoryXMLALText8 =
                                                ExynosDisplayUtils.parserFactoryXMLALText(
                                                        this.mAmbientLight[i2],
                                                        0,
                                                        pathWithPanel,
                                                        "hsc");
                                        if (parserFactoryXMLALText8 != null) {
                                            this.mHsvTable[i2] = parserFactoryXMLALText8[0];
                                        } else {
                                            this.mHsvTable[i2] = null;
                                        }
                                        i2++;
                                    }
                                    for (int i4 = 0; i4 < this.mAmbientLight.length; i4++) {
                                        Log.d("ExynosDisplayATC", "<aps>" + this.mApsTable[i4]);
                                        Log.d(
                                                "ExynosDisplayATC",
                                                "<apsdoff>" + this.mApsDelayedOffTable[i4]);
                                        Log.d(
                                                "ExynosDisplayATC",
                                                "<qsize>" + this.mQueSizeTable[i4]);
                                        Log.d(
                                                "ExynosDisplayATC",
                                                "<qcoeff>" + this.mQueCoeffTable[i4]);
                                        Log.d(
                                                "ExynosDisplayATC",
                                                "<qdelay>" + this.mQueDelayTable[i4]);
                                        Log.d(
                                                "ExynosDisplayATC",
                                                "<qalcoeff>" + this.mQueAlCoeffTable[i4]);
                                        Log.d("ExynosDisplayATC", "<hsc>" + this.mHsvTable[i4]);
                                    }
                                } finally {
                                }
                            }
                            loadLuminanceATCTable();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (TUNE_MODE && ExynosDisplayUtils.existFile(this.ATC_BL_FILE_PATH)) {
                parserTuneATCBLXML();
            }
        } else {
            sysfsWriteOnOff(false);
        }
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (anonymousClass1 != null) {
            anonymousClass1.sendEmptyMessage(1);
        }
    }

    public final void enableLightSensor(boolean z) {
        int integer =
                this.mContext
                        .getResources()
                        .getInteger(R.integer.config_displayWhiteBalanceBrightnessSensorRate);
        if (integer == 1 || integer == 3) {
            return;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "enableLightSensor: enable=", "ExynosDisplayATC", z);
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (z) {
            if (anonymousClass1 != null) {
                anonymousClass1.removeMessages(2);
            }
            SensorManager sensorManager = this.mSensorManager;
            if (sensorManager != null) {
                sensorManager.registerListener(this.sensorListener, this.mLightSensor, 3);
            }
        } else {
            SensorManager sensorManager2 = this.mSensorManager;
            if (sensorManager2 != null) {
                sensorManager2.unregisterListener(this.sensorListener);
            }
            if (anonymousClass1 != null) {
                anonymousClass1.removeMessages(2);
            }
        }
        this.mLightSensorEnabled = z;
    }

    public final int findBestATCTable() {
        String[] strArr;
        int i = 1;
        while (true) {
            int[] iArr = this.mAmbientLight;
            if (i >= iArr.length || this.mLastLuminance < iArr[i]) {
                break;
            }
            i++;
        }
        int i2 = i - 1;
        if (this.mApsTable[i2] != null) {
            return i2;
        }
        int i3 = i2;
        do {
            i3--;
            if (i3 < 0) {
                break;
            }
        } while (this.mApsTable[i3] == null);
        do {
            i2++;
            strArr = this.mApsTable;
            if (i2 >= strArr.length) {
                break;
            }
        } while (strArr[i2] == null);
        if (i3 < 0 || i2 >= strArr.length) {
            Log.e("ExynosDisplayATC", "invalid ATC table, return prev pos: " + this.mPrevPos);
            return this.mPrevPos;
        }
        int i4 = this.mPrevPos;
        if (i4 != i3 && i4 != i2) {
            i4 = i3;
        }
        if (strArr[i4] != null) {
            if (TUNE_MODE) {
                AudioService$$ExternalSyntheticOutline0.m(
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i4, i3, "adjusted pos: ", " at (", "~"),
                        i2,
                        ")",
                        "ExynosDisplayATC");
            }
            return i4;
        }
        Log.e(
                "ExynosDisplayATC",
                "unable to find proper ATC table, return prev pos: " + this.mPrevPos);
        return this.mPrevPos;
    }

    public final void loadLuminanceATCTable() {
        synchronized (this.mLock) {
            try {
                if (this.mApsTable == null) {
                    return;
                }
                if (this.mQueSizeTable == null) {
                    return;
                }
                if (this.mQueCoeffTable == null) {
                    return;
                }
                if (this.mQueDelayTable == null) {
                    return;
                }
                if (this.mQueAlCoeffTable == null) {
                    return;
                }
                if (this.mHsvTable == null) {
                    return;
                }
                if (this.mHsvLcgTable == null) {
                    return;
                }
                int findBestATCTable = findBestATCTable();
                if (findBestATCTable == -1) {
                    return;
                }
                if (TUNE_MODE || this.mPrevPos != findBestATCTable) {
                    Log.d(
                            "ExynosDisplayATC",
                            "mLastLuminance: "
                                    + this.mLastLuminance
                                    + ", mAmbientLight: "
                                    + this.mAmbientLight[findBestATCTable]);
                }
                this.mLastAps = this.mApsTable[findBestATCTable];
                this.mLastApsDelayedOff = this.mApsDelayedOffTable[findBestATCTable];
                this.mQsize = this.mQueSizeTable[findBestATCTable];
                this.mQcoeff = this.mQueCoeffTable[findBestATCTable];
                this.mQalcoeff = this.mQueAlCoeffTable[findBestATCTable];
                this.mQdelay = this.mQueDelayTable[findBestATCTable];
                this.mLastHsv = this.mHsvTable[findBestATCTable];
                for (int i = 0; i < 3; i++) {
                    this.mLastHsvLcg[i] = this.mHsvLcgTable[findBestATCTable][i];
                }
                this.mPrevPos = findBestATCTable;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void parserTuneATCBLXML() {
        String str = this.ATC_BL_FILE_PATH;
        try {
            String[] parserXMLAttribute = ExynosDisplayUtils.parserXMLAttribute(str, "atc", "al");
            if (parserXMLAttribute == null) {
                return;
            }
            if (parserXMLAttribute.length <= 0) {
                Log.e("ExynosDisplayATC", "xml array size wrong: " + parserXMLAttribute.length);
                return;
            }
            Log.d("ExynosDisplayATC", "array_length: " + parserXMLAttribute.length);
            this.mBrightnessLux = new int[parserXMLAttribute.length];
            this.mBrightnessSetting = new int[parserXMLAttribute.length];
            for (int i = 0; i < parserXMLAttribute.length; i++) {
                this.mBrightnessLux[i] = Integer.parseInt(parserXMLAttribute[i]);
                Log.d("ExynosDisplayATC", "al: " + this.mBrightnessLux[i]);
            }
            int i2 = 0;
            while (true) {
                int[] iArr = this.mBrightnessLux;
                if (i2 >= iArr.length) {
                    break;
                }
                this.mBrightnessSetting[i2] =
                        Integer.parseInt(
                                ExynosDisplayUtils.parserXMLALText(iArr[i2], str, "bl")[0]);
                i2++;
            }
            for (int i3 = 0; i3 < this.mBrightnessLux.length; i3++) {
                Log.d("ExynosDisplayATC", "<bl>" + this.mBrightnessSetting[i3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void printSensorDeque() {
        int size = ((ArrayDeque) this.mLastSensorReadings).size();
        if (size == 0) {
            return;
        }
        float[] fArr = new float[size];
        long[] jArr = new long[size];
        System.currentTimeMillis();
        SystemClock.elapsedRealtimeNanos();
        Iterator it = ((ArrayDeque) this.mLastSensorReadings).iterator();
        String str = null;
        while (it.hasNext()) {
            String str2 = Integer.toString((int) ((LightData) it.next()).lux) + ", ";
            str =
                    str == null
                            ? str2
                            : ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, str2);
        }
        if (TUNE_MODE) {
            DualAppManagerService$$ExternalSyntheticOutline0.m("que: ", str, "ExynosDisplayATC");
        }
    }

    public final void resetATC() {
        this.mLastAps = this.mApsInit;
        this.mPrevAps = null;
        this.mBrightnessLux = this.mBrightnessInit;
        this.mLastHsv = null;
        this.mPrevHsv = null;
        if (this.mLastHsvLcg == null) {
            this.mLastHsvLcg = new String[3];
        }
        for (int i = 0; i < 3; i++) {
            this.mLastHsvLcg[i] = null;
        }
        if (this.mPrevHsvLcg == null) {
            this.mPrevHsvLcg = new String[3];
        }
        for (int i2 = 0; i2 < 3; i2++) {
            this.mPrevHsvLcg[i2] = null;
        }
        this.mPrevPos = -1;
        this.mLastApsDelayedOff = 0;
    }

    public final void sendMessage() {
        AnonymousClass1 anonymousClass1 = this.mHandler;
        if (anonymousClass1 != null) {
            Message obtainMessage = anonymousClass1.obtainMessage(3);
            obtainMessage.arg1 = this.mLastApsDelayedOff;
            obtainMessage.obj = this.mLastAps;
            anonymousClass1.sendMessage(obtainMessage);
            Message obtainMessage2 = anonymousClass1.obtainMessage(2);
            obtainMessage2.arg1 = this.mLastLuminance;
            anonymousClass1.sendMessage(obtainMessage2);
            if (this.mLastHsvLcg != null) {
                for (int i = 0; i < 3; i++) {
                    if (this.mLastHsvLcg[i] != null) {
                        Message obtainMessage3 = anonymousClass1.obtainMessage(5);
                        obtainMessage3.arg1 = i;
                        obtainMessage3.obj = this.mLastHsvLcg[i];
                        anonymousClass1.sendMessage(obtainMessage3);
                    }
                }
            }
            if (this.mLastHsv != null) {
                Message obtainMessage4 = anonymousClass1.obtainMessage(4);
                obtainMessage4.obj = this.mLastHsv;
                anonymousClass1.sendMessage(obtainMessage4);
            }
        }
    }

    public final void setBrightnessAdjustment(int i) {
        int[] iArr = this.mBrightnessLux;
        if (iArr.length > 1) {
            int[] iArr2 = this.mBrightnessSetting;
            if (iArr2.length > 1 && iArr.length == iArr2.length) {
                int i2 = 1;
                while (true) {
                    int[] iArr3 = this.mBrightnessLux;
                    if (i2 >= iArr3.length || i < iArr3[i2]) {
                        break;
                    } else {
                        i2++;
                    }
                }
                int i3 = i2 - 1;
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "lux: ", ", mBrightnessLux: ");
                m.append(this.mBrightnessLux[i3]);
                m.append(", mBrightnessSetting: ");
                GestureWakeup$$ExternalSyntheticOutline0.m(
                        m, this.mBrightnessSetting[i3], "ExynosDisplayATC");
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(),
                        "screen_brightness",
                        this.mBrightnessSetting[i3],
                        -2);
            }
        }
    }

    public final void setCountDownTimer(int i, int i2) {
        if (i < 17 || i2 < 0) {
            return;
        }
        this.mTimeoutMs = i2 * i;
        this.mIntervalMs = i;
        StringBuilder sb = new StringBuilder("mTimeoutMs: ");
        sb.append(this.mTimeoutMs);
        sb.append(", mIntervalMs: ");
        GestureWakeup$$ExternalSyntheticOutline0.m(sb, this.mIntervalMs, "ExynosDisplayATC");
        AnonymousClass2 anonymousClass2 = this.mCountdownTimer;
        if (anonymousClass2 != null) {
            anonymousClass2.cancel();
        }
        this.mLocalHandler.postDelayed(
                new Runnable() { // from class: com.android.server.display.exynos.ExynosDisplayATC.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ExynosDisplayATC exynosDisplayATC = ExynosDisplayATC.this;
                        exynosDisplayATC.getClass();
                        exynosDisplayATC.mCountdownTimer =
                                exynosDisplayATC
                                .new AnonymousClass2(
                                        exynosDisplayATC.mTimeoutMs, exynosDisplayATC.mIntervalMs);
                    }
                },
                0L);
    }

    public final void setLastLuminance(int i) {
        this.mLastAps = this.mApsInit;
        this.mLastLuminance = i;
        loadLuminanceATCTable();
        String[] split = this.mQsize.split("\\s*,\\s*");
        this.mItem = split;
        int parseInt = Integer.parseInt(split[0]);
        while (!((ArrayDeque) this.mLastSensorReadings).isEmpty()
                && ((ArrayDeque) this.mLastSensorReadings).size() >= parseInt) {}
        LightData lightData = new LightData();
        lightData.timestamp = System.currentTimeMillis() / 1000;
        lightData.lux = i;
        ((ArrayDeque) this.mLastSensorReadings).addLast(lightData);
        printSensorDeque();
        caculateLuminance();
        this.mLightSensorEnabled = true;
        sendMessage();
        if ((this.mBrightnessLux.length <= 1 || this.mBrightnessSetting.length <= 1)
                && TUNE_MODE
                && ExynosDisplayUtils.existFile(this.ATC_BL_FILE_PATH)) {
            parserTuneATCBLXML();
        }
        setBrightnessAdjustment(i);
    }

    public final void startCountDownTimer() {
        AnonymousClass2 anonymousClass2 = this.mCountdownTimer;
        if (anonymousClass2 != null) {
            anonymousClass2.cancel();
            this.mCountdownTimer.start();
        }
    }

    public final void sysfsWriteOnOff(boolean z) {
        String str = this.ATC_ONOFF_SYSFS_PATH;
        try {
            if (this.mPreOnOff == z) {
                return;
            }
            if (this.DEBUG) {
                Log.d("ExynosDisplayATC", "onoff : " + z + " aps: " + this.mApsInit);
            }
            String stringFromFile = ExynosDisplayUtils.getStringFromFile(str);
            if (stringFromFile != null && !stringFromFile.equals("0")) {
                ExynosDisplayUtils.sysfsWrite(z ? 1 : 0, str);
            }
            if (z) {
                ExynosDisplayUtils.sysfsWriteSting(this.ATC_SFR_SYSFS_PATH, this.mApsInit);
                ExynosDisplayUtils.sendEmptyUpdate();
                Thread.sleep(100L);
            }
            startCountDownTimer();
            this.mPreOnOff = z;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
