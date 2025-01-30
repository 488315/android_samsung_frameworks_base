package com.android.server.display.exynos;

import android.R;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.util.NetworkConstants;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class ExynosDisplayATC {
    public static final long LUX_EVENT_HORIZON = TimeUnit.SECONDS.toNanos(10);
    public static boolean TUNE_MODE = false;
    public final String APS_INIT;
    public String ATC_BL_FILE_PATH;
    public String ATC_CAL_FILE_PATH;
    public String ATC_DIMOFF_SYSFS_PATH;
    public String ATC_DIM_STATUS_SYSFS_PATH;
    public String ATC_LUX_SYSFS_PATH;
    public String ATC_ONOFF_SYSFS_PATH;
    public String ATC_SFR_SYSFS_PATH;
    public String ATC_XML_FILE_PATH;
    public String BYPASS_CAL_FILE_PATH;
    public final boolean DEBUG;
    public String HSC48_IDX_SYSFS_PATH;
    public String HSC48_LCG_SYSFS_PATH;
    public String HSC_SYSFS_PATH;
    public int[] mAmbientLight;
    public boolean mApsDelayedOffRequired;
    public int[] mApsDelayedOffTable;
    public String mApsInit;
    public String[] mApsTable;
    public int[] mBrightnessInit;
    public int[] mBrightnessLux;
    public int[] mBrightnessSetting;
    public final Context mContext;
    public int mCountDownTimerCount;
    public CountDownTimer mCountdownTimer;
    public final Object mDataCollectionLock;
    public int mDimOperating;
    public int mEventCount;
    public Handler mHandler;
    public String[][] mHsvLcgTable;
    public String[] mHsvTable;
    public int mIntervalMs;
    public String[] mItem;
    public String mLastAps;
    public int mLastApsDelayedOff;
    public String mLastHsv;
    public String[] mLastHsvLcg;
    public int mLastLuminance;
    public Deque mLastSensorReadings;
    public Sensor mLightSensor;
    public boolean mLightSensorEnabled;
    public final Handler mLocalHandler;
    public final Object mLock;
    public String mLux;
    public boolean mPreOnOff;
    public String mPrevAps;
    public String mPrevHsv;
    public String[] mPrevHsvLcg;
    public int mPrevLuminance;
    public int mPrevPos;
    public String mQalcoeff;
    public String mQcoeff;
    public String mQdelay;
    public String mQsize;
    public String[] mQueAlCoeffTable;
    public String[] mQueCoeffTable;
    public String[] mQueDelayTable;
    public String[] mQueSizeTable;
    public SensorManager mSensorManager;
    public int mTimeoutMs;
    public SensorEventListener sensorListener;

    public class LightData {
        public float lux;
        public long timestamp;

        public LightData() {
        }
    }

    public ExynosDisplayATC(Context context) {
        String str = Build.TYPE;
        this.DEBUG = "eng".equals(str) || "userdebug".equals(str);
        this.mLightSensor = null;
        this.mSensorManager = null;
        this.mLightSensorEnabled = false;
        this.ATC_DIM_STATUS_SYSFS_PATH = "/sys/class/dqe/dqe/dim_status";
        this.ATC_SFR_SYSFS_PATH = "/sys/class/dqe/dqe/aps";
        this.ATC_LUX_SYSFS_PATH = "/sys/class/dqe/dqe/aps_lux";
        this.ATC_ONOFF_SYSFS_PATH = "/sys/class/dqe/dqe/aps_onoff";
        this.HSC48_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_idx";
        this.HSC48_LCG_SYSFS_PATH = "/sys/class/dqe/dqe/hsc48_lcg";
        this.HSC_SYSFS_PATH = "/sys/class/dqe/dqe/hsc";
        this.ATC_DIMOFF_SYSFS_PATH = "/sys/class/dqe/dqe/aps_dim_off";
        this.APS_INIT = "0,0,128,128,128,0,10,14,2,0,25,230,140,250,0,3,3,2,3,128,1";
        this.mApsInit = null;
        this.mLux = "3000,4000,5000,6000,8000,10000,15000,20000,25000,30000,40000,50000";
        this.mQsize = "8";
        this.mQcoeff = "10,5,4,3,2,1,1,1";
        this.mQalcoeff = "4";
        this.mQdelay = "0";
        this.mItem = null;
        this.ATC_CAL_FILE_PATH = "/data/dqe/calib_data_atc.xml";
        this.ATC_BL_FILE_PATH = "/data/dqe/calib_data_atc_bl.xml";
        this.ATC_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_atc.xml";
        this.BYPASS_CAL_FILE_PATH = "/vendor/etc/dqe/calib_data_bypass.xml";
        this.mDataCollectionLock = new Object();
        this.mLastSensorReadings = new ArrayDeque();
        this.mAmbientLight = new int[]{0, 3000, 4000, 5000, 6000, 8000, 10000, 15000, 20000, 25000, 30000, 40000, 50000};
        this.mApsTable = null;
        this.mQueSizeTable = null;
        this.mQueCoeffTable = null;
        this.mQueDelayTable = null;
        this.mQueAlCoeffTable = null;
        this.mHsvTable = null;
        this.mHsvLcgTable = null;
        this.mApsDelayedOffTable = new int[]{0};
        this.mLastLuminance = 0;
        this.mPrevLuminance = 0;
        this.mLastAps = null;
        this.mPrevAps = null;
        this.mLastHsv = null;
        this.mPrevHsv = null;
        this.mLastHsvLcg = null;
        this.mPrevHsvLcg = null;
        this.mPreOnOff = false;
        this.mLastApsDelayedOff = 0;
        this.mApsDelayedOffRequired = false;
        this.mEventCount = 0;
        this.mPrevPos = -1;
        this.mDimOperating = 0;
        this.mCountdownTimer = null;
        this.mTimeoutMs = NetworkConstants.ETHER_MTU;
        this.mIntervalMs = 500;
        this.mCountDownTimerCount = 0;
        this.mBrightnessInit = new int[]{0};
        this.mBrightnessLux = new int[]{0};
        this.mBrightnessSetting = new int[]{0};
        this.mLock = new Object();
        this.mHandler = new Handler() { // from class: com.android.server.display.exynos.ExynosDisplayATC.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                switch (message.what) {
                    case 1:
                        ExynosDisplayUtils.sendEmptyUpdate();
                        break;
                    case 2:
                        ExynosDisplayATC.this.sysfsWriteLux(message.arg1);
                        break;
                    case 3:
                        ExynosDisplayATC.this.sysfsWriteAps(message.obj.toString(), message.arg1);
                        break;
                    case 4:
                        ExynosDisplayATC.this.sysfsWriteHsv(message.obj.toString());
                        break;
                    case 5:
                        ExynosDisplayATC.this.sysfsWriteHsvLcg(message.obj.toString(), message.arg1);
                        break;
                    case 6:
                        if (ExynosDisplayATC.this.mApsDelayedOffRequired) {
                            ExynosDisplayATC.this.sysfsWriteOnOff(false);
                        }
                        ExynosDisplayATC.this.mApsDelayedOffRequired = false;
                        break;
                }
            }
        };
        this.sensorListener = new SensorEventListener() { // from class: com.android.server.display.exynos.ExynosDisplayATC.3
            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(SensorEvent sensorEvent) {
                ExynosDisplayATC.this.recordSensorEvent(sensorEvent);
                if (ExynosDisplayATC.TUNE_MODE) {
                    ExynosDisplayATC.this.printSensorDeque();
                }
                int i = (int) sensorEvent.values[0];
                ExynosDisplayATC.this.caculateLuminance();
                ExynosDisplayATC.this.loadLuminanceATCTable();
                int queDelay = ExynosDisplayATC.this.getQueDelay();
                if (queDelay > 0) {
                    ExynosDisplayATC exynosDisplayATC = ExynosDisplayATC.this;
                    int i2 = exynosDisplayATC.mEventCount;
                    exynosDisplayATC.mEventCount = i2 + 1;
                    if (i2 < queDelay) {
                        return;
                    }
                    if (ExynosDisplayATC.this.mEventCount >= queDelay) {
                        ExynosDisplayATC.this.mEventCount = 0;
                    }
                }
                ExynosDisplayATC.this.sendMessage();
                if (ExynosDisplayATC.TUNE_MODE) {
                    ExynosDisplayATC.this.setBrightnessAdjustment(i);
                }
            }
        };
        this.mContext = context;
        SensorManager sensorManager = (SensorManager) context.getSystemService(SensorManager.class);
        this.mSensorManager = sensorManager;
        this.mLightSensor = sensorManager.getDefaultSensor(5);
        this.mLocalHandler = new Handler(context.getMainLooper());
        getApsInit();
        initCountDownTimer();
    }

    public static boolean isDefaultSensorOff(Context context) {
        int integer = context.getResources().getInteger(R.integer.config_defaultUiModeType);
        return integer == 1 || integer == 3;
    }

    public final void checkDimOperating() {
        String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.ATC_DIM_STATUS_SYSFS_PATH);
        if (stringFromFile == null) {
            this.mDimOperating = 0;
        } else {
            this.mDimOperating = Integer.parseInt(stringFromFile);
        }
    }

    public final void getApsInit() {
        String[] parserXMLALText = ExynosDisplayUtils.parserXMLALText(this.BYPASS_CAL_FILE_PATH, "atc", 0, "aps");
        if (parserXMLALText == null || parserXMLALText.length <= 0) {
            Log.d("ExynosDisplayATC", "xml aps not found");
            this.mApsInit = "0,0,128,128,128,0,10,14,2,0,25,230,140,250,0,3,3,2,3,128,1";
        } else {
            this.mApsInit = parserXMLALText[0];
        }
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
        if (strArr[i4] == null) {
            Log.e("ExynosDisplayATC", "unable to find proper ATC table, return prev pos: " + this.mPrevPos);
            return this.mPrevPos;
        }
        if (TUNE_MODE) {
            Log.d("ExynosDisplayATC", "adjusted pos: " + i4 + " at (" + i3 + "~" + i2 + ")");
        }
        return i4;
    }

    public final void loadLuminanceATCTable() {
        if (this.mApsTable == null || this.mQueSizeTable == null || this.mQueCoeffTable == null || this.mQueDelayTable == null || this.mQueAlCoeffTable == null || this.mHsvTable == null || this.mHsvLcgTable == null) {
            return;
        }
        synchronized (this.mLock) {
            int findBestATCTable = findBestATCTable();
            if (findBestATCTable == -1) {
                return;
            }
            if (TUNE_MODE || this.mPrevPos != findBestATCTable) {
                Log.d("ExynosDisplayATC", "mLastLuminance: " + this.mLastLuminance + ", mAmbientLight: " + this.mAmbientLight[findBestATCTable]);
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
        }
    }

    public final void recordSensorEvent(SensorEvent sensorEvent) {
        elapsedRealtimeNanos();
        synchronized (this.mDataCollectionLock) {
            if (!this.mLastSensorReadings.isEmpty() && sensorEvent.timestamp < ((LightData) this.mLastSensorReadings.getLast()).timestamp) {
                Log.d("ExynosDisplayATC", "Ignore event " + sensorEvent.values[0]);
                return;
            }
            String[] split = this.mQsize.split("\\s*,\\s*");
            this.mItem = split;
            int parseInt = Integer.parseInt(split[0]);
            while (!this.mLastSensorReadings.isEmpty() && this.mLastSensorReadings.size() >= parseInt) {
            }
            LightData lightData = new LightData();
            lightData.timestamp = sensorEvent.timestamp;
            lightData.lux = sensorEvent.values[0];
            this.mLastSensorReadings.addLast(lightData);
        }
    }

    public final long elapsedRealtimeNanos() {
        return SystemClock.elapsedRealtimeNanos();
    }

    public final void caculateLuminance() {
        try {
            int size = this.mLastSensorReadings.size();
            float[] fArr = new float[size];
            Iterator it = this.mLastSensorReadings.iterator();
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
                    i += i9 * i10;
                    i7 += i10;
                } else {
                    int i11 = (int) fArr[i8];
                    int i12 = iArr[i6];
                    i += (i11 * i12) + (this.mLastLuminance * parseInt2);
                    i7 = i7 + i12 + parseInt2;
                }
                i6++;
            }
            this.mLastLuminance = i / i7;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final int getQueDelay() {
        String[] split = this.mQdelay.split("\\s*,\\s*");
        this.mItem = split;
        int parseInt = Integer.parseInt(split[0]);
        if (TUNE_MODE) {
            Log.d("ExynosDisplayATC", "qdelay: " + parseInt);
        }
        return parseInt;
    }

    public final void initCountDownTimer() {
        this.mCountdownTimer = new CountDownTimer(this.mTimeoutMs, this.mIntervalMs) { // from class: com.android.server.display.exynos.ExynosDisplayATC.2
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                ExynosDisplayATC.this.mCountDownTimerCount++;
                if (ExynosDisplayATC.this.mHandler != null) {
                    ExynosDisplayATC.this.mHandler.sendEmptyMessage(1);
                }
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (ExynosDisplayATC.this.mHandler != null) {
                    ExynosDisplayATC.this.mHandler.sendEmptyMessage(1);
                }
                ExynosDisplayATC.this.mCountDownTimerCount = 0;
                ExynosDisplayATC.this.checkDimOperating();
                if (ExynosDisplayATC.this.mDimOperating != 0) {
                    if (ExynosDisplayATC.this.DEBUG) {
                        Log.d("ExynosDisplayATC", "Restart Refresh Timer as dimming started");
                    }
                    cancel();
                    start();
                    return;
                }
                if (ExynosDisplayATC.this.mHandler != null) {
                    ExynosDisplayATC.this.mHandler.sendEmptyMessage(6);
                }
                if (ExynosDisplayATC.TUNE_MODE) {
                    Log.d("ExynosDisplayATC", "Finished Refresh Timer interval: " + ExynosDisplayATC.this.mIntervalMs + ", timeout: " + ExynosDisplayATC.this.mTimeoutMs);
                }
            }
        };
    }

    public void startCountDownTimer() {
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            this.mCountDownTimerCount = 0;
            countDownTimer.cancel();
            this.mCountdownTimer.start();
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
                Log.d("ExynosDisplayATC", "lux: " + i + ", mBrightnessLux: " + this.mBrightnessLux[i3] + ", mBrightnessSetting: " + this.mBrightnessSetting[i3]);
                putScreenBrightnessSetting(this.mBrightnessSetting[i3]);
            }
        }
    }

    public final void printSensorDeque() {
        int size = this.mLastSensorReadings.size();
        if (size == 0) {
            return;
        }
        float[] fArr = new float[size];
        long[] jArr = new long[size];
        System.currentTimeMillis();
        elapsedRealtimeNanos();
        Iterator it = this.mLastSensorReadings.iterator();
        String str = null;
        while (it.hasNext()) {
            String str2 = Integer.toString((int) ((LightData) it.next()).lux) + ", ";
            if (str == null) {
                str = str2;
            } else {
                str = str + str2;
            }
        }
        if (TUNE_MODE) {
            Log.d("ExynosDisplayATC", "que: " + str);
        }
    }

    public void sysfsWriteLux(int i) {
        if (this.mLightSensorEnabled && TUNE_MODE && this.mPrevLuminance != i) {
            Log.d("ExynosDisplayATC", "lux: " + i);
            ExynosDisplayUtils.sysfsWrite(this.ATC_LUX_SYSFS_PATH, i);
            this.mPrevLuminance = i;
        }
    }

    public void sysfsWriteAps(String str, int i) {
        if (this.mLightSensorEnabled) {
            String str2 = this.mPrevAps;
            if (str2 != null && str.equals(str2)) {
                if (TUNE_MODE) {
                    Log.d("ExynosDisplayATC", "aps skip : " + str);
                    return;
                }
                return;
            }
            if (TUNE_MODE) {
                Log.d("ExynosDisplayATC", "aps: " + str);
            }
            sysfsWriteOnOff(true);
            ExynosDisplayUtils.sysfsWriteSting(this.ATC_SFR_SYSFS_PATH, str);
            this.mPrevAps = str;
            this.mApsDelayedOffRequired = i != 0;
            startCountDownTimer();
        }
    }

    public void sysfsWriteHsv(String str) {
        if (this.mLightSensorEnabled) {
            String str2 = this.mPrevHsv;
            if (str2 != null && str.equals(str2)) {
                if (TUNE_MODE) {
                    Log.d("ExynosDisplayATC", "hsv skip as same");
                    return;
                }
                return;
            }
            if (TUNE_MODE) {
                Log.d("ExynosDisplayATC", "hsv: " + str);
            }
            ExynosDisplayUtils.sysfsWriteSting(this.HSC_SYSFS_PATH, str);
            this.mPrevHsv = str;
            startCountDownTimer();
        }
    }

    public void sysfsWriteHsvLcg(String str, int i) {
        String str2;
        if (this.mLightSensorEnabled) {
            if (i >= 3) {
                if (TUNE_MODE) {
                    Log.d("ExynosDisplayATC", "hsvlcg skip as invalid");
                    return;
                }
                return;
            }
            String[] strArr = this.mPrevHsvLcg;
            if (strArr != null && (str2 = strArr[i]) != null && str.equals(str2)) {
                if (TUNE_MODE) {
                    Log.d("ExynosDisplayATC", "hsvlcg skip as same");
                    return;
                }
                return;
            }
            if (TUNE_MODE) {
                Log.d("ExynosDisplayATC", "hsv lcg: " + str);
            }
            ExynosDisplayUtils.sysfsWriteSting(this.HSC48_IDX_SYSFS_PATH, Integer.toString(i));
            ExynosDisplayUtils.sysfsWriteSting(this.HSC48_LCG_SYSFS_PATH, str);
            this.mPrevHsvLcg[i] = str;
            startCountDownTimer();
        }
    }

    public void sysfsWriteOnOff(boolean z) {
        try {
            if (this.mPreOnOff == z) {
                return;
            }
            if (this.DEBUG) {
                Log.d("ExynosDisplayATC", "onoff : " + z + " aps: " + this.mApsInit);
            }
            String stringFromFile = ExynosDisplayUtils.getStringFromFile(this.ATC_ONOFF_SYSFS_PATH);
            if (stringFromFile != null && !stringFromFile.equals("0")) {
                ExynosDisplayUtils.sysfsWrite(this.ATC_ONOFF_SYSFS_PATH, !z ? 0 : 1);
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

    public final void putScreenBrightnessSetting(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), "screen_brightness", i, -2);
    }

    public void parserATCXML(String str, String str2) {
        try {
            String[] parserFactoryXMLAttribute = ExynosDisplayUtils.parserFactoryXMLAttribute(str, str2, "atc", "al");
            if (parserFactoryXMLAttribute == null) {
                return;
            }
            if (parserFactoryXMLAttribute.length <= 0) {
                Log.e("ExynosDisplayATC", "xml array size wrong: " + parserFactoryXMLAttribute.length);
                return;
            }
            synchronized (this.mLock) {
                Log.d("ExynosDisplayATC", "array_length: " + parserFactoryXMLAttribute.length);
                this.mAmbientLight = new int[parserFactoryXMLAttribute.length];
                for (int i = 0; i < parserFactoryXMLAttribute.length; i++) {
                    this.mAmbientLight[i] = Integer.parseInt(parserFactoryXMLAttribute[i]);
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
                this.mHsvLcgTable = (String[][]) Array.newInstance((Class<?>) String.class, iArr.length, 3);
                int i2 = 0;
                while (true) {
                    int[] iArr2 = this.mAmbientLight;
                    if (i2 >= iArr2.length) {
                        break;
                    }
                    String[] parserFactoryXMLALText = ExynosDisplayUtils.parserFactoryXMLALText(str, str2, "atc", iArr2[i2], "aps", 0);
                    if (parserFactoryXMLALText != null) {
                        this.mApsTable[i2] = parserFactoryXMLALText[0];
                    }
                    String[] parserFactoryXMLALText2 = ExynosDisplayUtils.parserFactoryXMLALText(str, str2, "atc", this.mAmbientLight[i2], "apsdoff", 0);
                    if (parserFactoryXMLALText2 != null) {
                        this.mApsDelayedOffTable[i2] = Integer.parseInt(parserFactoryXMLALText2[0]);
                    } else {
                        this.mApsDelayedOffTable[i2] = 0;
                    }
                    String[] parserFactoryXMLALText3 = ExynosDisplayUtils.parserFactoryXMLALText(str, str2, "atc", this.mAmbientLight[i2], "qsize", 0);
                    if (parserFactoryXMLALText3 != null) {
                        this.mQueSizeTable[i2] = parserFactoryXMLALText3[0];
                    }
                    String[] parserFactoryXMLALText4 = ExynosDisplayUtils.parserFactoryXMLALText(str, str2, "atc", this.mAmbientLight[i2], "qcoeff", 0);
                    if (parserFactoryXMLALText4 != null) {
                        this.mQueCoeffTable[i2] = parserFactoryXMLALText4[0];
                    }
                    String[] parserFactoryXMLALText5 = ExynosDisplayUtils.parserFactoryXMLALText(str, str2, "atc", this.mAmbientLight[i2], "qdelay", 0);
                    if (parserFactoryXMLALText5 != null) {
                        this.mQueDelayTable[i2] = parserFactoryXMLALText5[0];
                    }
                    String[] parserFactoryXMLALText6 = ExynosDisplayUtils.parserFactoryXMLALText(str, str2, "atc", this.mAmbientLight[i2], "qalcoeff", 0);
                    if (parserFactoryXMLALText6 != null) {
                        this.mQueAlCoeffTable[i2] = parserFactoryXMLALText6[0];
                    }
                    for (int i3 = 0; i3 < 3; i3++) {
                        String[] parserFactoryXMLALText7 = ExynosDisplayUtils.parserFactoryXMLALText(str, str2, "atc", this.mAmbientLight[i2], "hsc48_lcg", i3);
                        if (parserFactoryXMLALText7 != null) {
                            this.mHsvLcgTable[i2][i3] = parserFactoryXMLALText7[0];
                        } else {
                            this.mHsvLcgTable[i2][i3] = null;
                        }
                    }
                    String[] parserFactoryXMLALText8 = ExynosDisplayUtils.parserFactoryXMLALText(str, str2, "atc", this.mAmbientLight[i2], "hsc", 0);
                    if (parserFactoryXMLALText8 != null) {
                        this.mHsvTable[i2] = parserFactoryXMLALText8[0];
                    } else {
                        this.mHsvTable[i2] = null;
                    }
                    i2++;
                }
                for (int i4 = 0; i4 < this.mAmbientLight.length; i4++) {
                    Log.d("ExynosDisplayATC", "<aps>" + this.mApsTable[i4]);
                    Log.d("ExynosDisplayATC", "<apsdoff>" + this.mApsDelayedOffTable[i4]);
                    Log.d("ExynosDisplayATC", "<qsize>" + this.mQueSizeTable[i4]);
                    Log.d("ExynosDisplayATC", "<qcoeff>" + this.mQueCoeffTable[i4]);
                    Log.d("ExynosDisplayATC", "<qdelay>" + this.mQueDelayTable[i4]);
                    Log.d("ExynosDisplayATC", "<qalcoeff>" + this.mQueAlCoeffTable[i4]);
                    Log.d("ExynosDisplayATC", "<hsc>" + this.mHsvTable[i4]);
                }
            }
            loadLuminanceATCTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void parserTuneATCBLXML() {
        try {
            String[] parserXMLAttribute = ExynosDisplayUtils.parserXMLAttribute(this.ATC_BL_FILE_PATH, "atc", "al");
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
                this.mBrightnessSetting[i2] = Integer.parseInt(ExynosDisplayUtils.parserXMLALText(this.ATC_BL_FILE_PATH, "atc", iArr[i2], "bl")[0]);
                i2++;
            }
            for (int i3 = 0; i3 < this.mBrightnessLux.length; i3++) {
                Log.d("ExynosDisplayATC", "<bl>" + this.mBrightnessSetting[i3]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enableATCTuneMode(boolean z) {
        if (z != TUNE_MODE) {
            Log.d("ExynosDisplayATC", "enableATCTuneMode: TUNE_MODE=" + z);
        }
        TUNE_MODE = z;
    }

    public void resetATC() {
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

    public void enableATC(boolean z) {
        String pathWithPanel;
        resetATC();
        if (z) {
            if (!TUNE_MODE) {
                pathWithPanel = ExynosDisplayUtils.getPathWithPanel(this.ATC_XML_FILE_PATH);
            } else {
                pathWithPanel = ExynosDisplayUtils.getPathWithPanel(this.ATC_CAL_FILE_PATH);
            }
            if (pathWithPanel != null) {
                parserATCXML(pathWithPanel, "tune");
            }
            if (TUNE_MODE && ExynosDisplayUtils.existFile(this.ATC_BL_FILE_PATH)) {
                parserTuneATCBLXML();
            }
        } else {
            sysfsWriteOnOff(false);
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(1);
        }
    }

    public void enableLightSensor(boolean z) {
        if (isDefaultSensorOff(this.mContext)) {
            return;
        }
        Log.d("ExynosDisplayATC", "enableLightSensor: enable=" + z);
        if (z) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.removeMessages(2);
            }
        } else {
            Handler handler2 = this.mHandler;
            if (handler2 != null) {
                handler2.removeMessages(2);
            }
        }
        this.mLightSensorEnabled = z;
    }

    public final void sendMessage() {
        Handler handler = this.mHandler;
        if (handler != null) {
            Message obtainMessage = handler.obtainMessage(3);
            obtainMessage.arg1 = this.mLastApsDelayedOff;
            obtainMessage.obj = this.mLastAps;
            this.mHandler.sendMessage(obtainMessage);
            Message obtainMessage2 = this.mHandler.obtainMessage(2);
            obtainMessage2.arg1 = this.mLastLuminance;
            this.mHandler.sendMessage(obtainMessage2);
            if (this.mLastHsvLcg != null) {
                for (int i = 0; i < 3; i++) {
                    if (this.mLastHsvLcg[i] != null) {
                        Message obtainMessage3 = this.mHandler.obtainMessage(5);
                        obtainMessage3.arg1 = i;
                        obtainMessage3.obj = this.mLastHsvLcg[i];
                        this.mHandler.sendMessage(obtainMessage3);
                    }
                }
            }
            if (this.mLastHsv != null) {
                Message obtainMessage4 = this.mHandler.obtainMessage(4);
                obtainMessage4.obj = this.mLastHsv;
                this.mHandler.sendMessage(obtainMessage4);
            }
        }
    }

    public void setLastLuminance(int i) {
        this.mLastAps = this.mApsInit;
        this.mLastLuminance = i;
        loadLuminanceATCTable();
        String[] split = this.mQsize.split("\\s*,\\s*");
        this.mItem = split;
        int parseInt = Integer.parseInt(split[0]);
        while (!this.mLastSensorReadings.isEmpty() && this.mLastSensorReadings.size() >= parseInt) {
        }
        LightData lightData = new LightData();
        lightData.timestamp = System.currentTimeMillis() / 1000;
        lightData.lux = i;
        this.mLastSensorReadings.addLast(lightData);
        printSensorDeque();
        caculateLuminance();
        this.mLightSensorEnabled = true;
        sendMessage();
        if ((this.mBrightnessLux.length <= 1 || this.mBrightnessSetting.length <= 1) && TUNE_MODE && ExynosDisplayUtils.existFile(this.ATC_BL_FILE_PATH)) {
            parserTuneATCBLXML();
        }
        setBrightnessAdjustment(i);
    }

    public void setCountDownTimer(int i, int i2) {
        if (i < 17 || i2 < 0) {
            return;
        }
        this.mTimeoutMs = i2 * i;
        this.mIntervalMs = i;
        Log.d("ExynosDisplayATC", "mTimeoutMs: " + this.mTimeoutMs + ", mIntervalMs: " + this.mIntervalMs);
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            this.mCountDownTimerCount = 0;
            countDownTimer.cancel();
        }
        this.mLocalHandler.postDelayed(new Runnable() { // from class: com.android.server.display.exynos.ExynosDisplayATC.4
            @Override // java.lang.Runnable
            public void run() {
                ExynosDisplayATC.this.initCountDownTimer();
            }
        }, 0L);
    }
}
