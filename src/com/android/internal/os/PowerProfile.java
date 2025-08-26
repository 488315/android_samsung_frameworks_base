package com.android.internal.os;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.hardware.scontext.SContextConstants;
import android.media.audio.Enums;
import android.os.Build;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import com.android.internal.os.PowerProfileProto;
import com.android.internal.power.ModemPowerProfile;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiConsumer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class PowerProfile {
    private static final String ATTR_NAME = "name";
    private static final String CPU_CLUSTER_POWER_COUNT = "cpu.cluster_power.cluster";
    private static final String CPU_CORE_POWER_PREFIX = "cpu.core_power.cluster";
    private static final String CPU_CORE_SPEED_PREFIX = "cpu.core_speeds.cluster";
    private static final String CPU_PER_CLUSTER_CORE_COUNT = "cpu.clusters.cores";
    private static final String CPU_POWER_BRACKETS_PREFIX = "cpu.power_brackets.policy";
    private static final String CPU_SCALING_POLICY_POWER_POLICY = "cpu.scaling_policy_power.policy";
    private static final String CPU_SCALING_STEP_POWER_POLICY = "cpu.scaling_step_power.policy";

    @Deprecated
    public static final String POWER_AMBIENT_DISPLAY = "ambient.on";
    public static final String POWER_AUDIO = "audio";
    public static final String POWER_BATTERY_CAPACITY = "battery.capacity";
    public static final String POWER_BATTERY_TYPICAL_CAPACITY = "battery.typical.capacity";

    @Deprecated
    public static final String POWER_BLUETOOTH_ACTIVE = "bluetooth.active";

    @Deprecated
    public static final String POWER_BLUETOOTH_AT_CMD = "bluetooth.at";
    public static final String POWER_BLUETOOTH_CONTROLLER_IDLE = "bluetooth.controller.idle";
    public static final String POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE = "bluetooth.controller.voltage";
    public static final String POWER_BLUETOOTH_CONTROLLER_RX = "bluetooth.controller.rx";
    public static final String POWER_BLUETOOTH_CONTROLLER_TX = "bluetooth.controller.tx";

    @Deprecated
    public static final String POWER_BLUETOOTH_ON = "bluetooth.on";
    public static final int POWER_BRACKETS_UNSPECIFIED = -1;
    public static final String POWER_CAMERA = "camera.avg";
    public static final String POWER_CPU_ACTIVE = "cpu.active";
    public static final String POWER_CPU_IDLE = "cpu.idle";
    public static final String POWER_CPU_SUSPEND = "cpu.suspend";
    public static final String POWER_FLASHLIGHT = "camera.flashlight";
    public static final String POWER_GPS_ON = "gps.on";
    public static final String POWER_GPS_OPERATING_VOLTAGE = "gps.voltage";
    public static final String POWER_GPS_SIGNAL_QUALITY_BASED = "gps.signalqualitybased";
    public static final String POWER_GROUP_DISPLAY_AMBIENT = "ambient.on.display";
    public static final String POWER_GROUP_DISPLAY_SCREEN_FULL = "screen.full.display";
    public static final String POWER_GROUP_DISPLAY_SCREEN_ON = "screen.on.display";
    public static final String POWER_MEMORY = "memory.bandwidths";
    public static final String POWER_MODEM_CONTROLLER_IDLE = "modem.controller.idle";
    public static final String POWER_MODEM_CONTROLLER_OPERATING_VOLTAGE = "modem.controller.voltage";
    public static final String POWER_MODEM_CONTROLLER_RX = "modem.controller.rx";
    public static final String POWER_MODEM_CONTROLLER_SLEEP = "modem.controller.sleep";
    public static final String POWER_MODEM_CONTROLLER_TX = "modem.controller.tx";
    public static final String POWER_RADIO_ACTIVE = "radio.active";
    public static final String POWER_RADIO_ON = "radio.on";
    public static final String POWER_RADIO_SCANNING = "radio.scanning";

    @Deprecated
    public static final String POWER_SCREEN_FULL = "screen.full";

    @Deprecated
    public static final String POWER_SCREEN_ON = "screen.on";
    public static final String POWER_VIDEO = "video";
    public static final String POWER_WIFI_ACTIVE = "wifi.active";
    public static final String POWER_WIFI_BATCHED_SCAN = "wifi.batchedscan";
    public static final String POWER_WIFI_CONTROLLER_IDLE = "wifi.controller.idle";
    public static final String POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE = "wifi.controller.voltage";
    public static final String POWER_WIFI_CONTROLLER_RX = "wifi.controller.rx";
    public static final String POWER_WIFI_CONTROLLER_TX = "wifi.controller.tx";
    public static final String POWER_WIFI_CONTROLLER_TX_LEVELS = "wifi.controller.tx_levels";
    public static final String POWER_WIFI_ON = "wifi.on";
    public static final String POWER_WIFI_SCAN = "wifi.scan";
    private static final long SUBSYSTEM_FIELDS_MASK = -1;
    private static final long SUBSYSTEM_MASK = 64424509440L;
    public static final long SUBSYSTEM_MODEM = 4294967296L;
    public static final long SUBSYSTEM_NONE = 0;
    public static final String TAG = "PowerProfile";
    private static final String TAG_ARRAY = "array";
    private static final String TAG_ARRAYITEM = "value";
    private static final String TAG_DEVICE = "device";
    private static final String TAG_ITEM = "item";
    private static final String TAG_MODEM = "modem";
    private CpuClusterKey[] mCpuClusters;
    private int mCpuPowerBracketCount;
    private SparseArray<CpuScalingPolicyPower> mCpuScalingPolicies;
    private int mNumDisplays;
    static final HashMap<String, Double> sPowerItemMap = new HashMap<>();
    static final HashMap<String, Double[]> sPowerArrayMap = new HashMap<>();
    static final ModemPowerProfile sModemPowerProfile = new ModemPowerProfile();
    private static final Object sLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface PowerGroup {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Subsystem {
    }

    public PowerProfile() {
        synchronized (sLock) {
            initLocked();
        }
    }

    public PowerProfile(Context context) {
        this(context, false);
    }

    public PowerProfile(Context context, boolean z) {
        synchronized (sLock) {
            initLocked(context, z ? R.xml.power_profile_test : R.xml.power_profile);
        }
    }

    public void initForTesting(XmlPullParser xmlPullParser) {
        initForTesting(xmlPullParser, null);
    }

    public void initForTesting(XmlPullParser xmlPullParser, Resources resources) {
        synchronized (sLock) {
            sPowerItemMap.clear();
            sPowerArrayMap.clear();
            sModemPowerProfile.clear();
            try {
                readPowerValuesFromXml(xmlPullParser, resources);
                initLocked();
            } finally {
                if (xmlPullParser instanceof XmlResourceParser) {
                    ((XmlResourceParser) xmlPullParser).close();
                }
            }
        }
    }

    private void initLocked(Context context, int i) {
        if (sPowerItemMap.size() == 0 && sPowerArrayMap.size() == 0) {
            Resources resources = context.getResources();
            readPowerValuesFromXml(resources.getXml(i), resources);
        }
        initLocked();
    }

    private void initLocked() {
        initCpuClusters();
        initCpuScalingPolicies();
        initCpuPowerBrackets();
        initDisplays();
        initModem();
    }

    private static void readPowerValuesFromXml(XmlPullParser xmlPullParser, Resources resources) {
        double dDoubleValue;
        ArrayList arrayList = new ArrayList();
        try {
            try {
                try {
                    XmlUtils.beginDocument(xmlPullParser, "device");
                    boolean z = false;
                    String attributeValue = null;
                    while (true) {
                        XmlUtils.nextElement(xmlPullParser);
                        String name = xmlPullParser.getName();
                        if (name == null) {
                            break;
                        }
                        if (z && !name.equals("value")) {
                            sPowerArrayMap.put(attributeValue, (Double[]) arrayList.toArray(new Double[arrayList.size()]));
                            z = false;
                        }
                        if (name.equals(TAG_ARRAY)) {
                            arrayList.clear();
                            attributeValue = xmlPullParser.getAttributeValue(null, "name");
                            z = true;
                        } else if (name.equals("item") || name.equals("value")) {
                            String attributeValue2 = !z ? xmlPullParser.getAttributeValue(null, "name") : null;
                            if (xmlPullParser.next() == 4) {
                                try {
                                    dDoubleValue = Double.valueOf(xmlPullParser.getText()).doubleValue();
                                } catch (NumberFormatException unused) {
                                    dDoubleValue = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                                }
                                if (name.equals("item")) {
                                    sPowerItemMap.put(attributeValue2, Double.valueOf(dDoubleValue));
                                } else if (z) {
                                    arrayList.add(Double.valueOf(dDoubleValue));
                                }
                            }
                        } else if (name.equals("modem")) {
                            sModemPowerProfile.parseFromXml(xmlPullParser);
                        }
                    }
                    if (z) {
                        sPowerArrayMap.put(attributeValue, (Double[]) arrayList.toArray(new Double[arrayList.size()]));
                    }
                    if (resources != null) {
                        getDefaultValuesFromConfig(resources);
                    }
                } catch (XmlPullParserException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } finally {
            if (xmlPullParser instanceof XmlResourceParser) {
                ((XmlResourceParser) xmlPullParser).close();
            }
        }
    }

    private static void getDefaultValuesFromConfig(Resources resources) {
        int integer;
        int[] iArr = {R.integer.config_bluetooth_idle_cur_ma, R.integer.config_bluetooth_rx_cur_ma, R.integer.config_bluetooth_tx_cur_ma, R.integer.config_bluetooth_operating_voltage_mv};
        String[] strArr = {POWER_BLUETOOTH_CONTROLLER_IDLE, POWER_BLUETOOTH_CONTROLLER_RX, POWER_BLUETOOTH_CONTROLLER_TX, POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE};
        for (int i = 0; i < 4; i++) {
            String str = strArr[i];
            HashMap<String, Double> map = sPowerItemMap;
            if ((!map.containsKey(str) || map.get(str).doubleValue() <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) && (integer = resources.getInteger(iArr[i])) > 0) {
                map.put(str, Double.valueOf(integer));
            }
        }
    }

    private void initCpuClusters() {
        HashMap<String, Double[]> map = sPowerArrayMap;
        if (map.containsKey(CPU_PER_CLUSTER_CORE_COUNT)) {
            Double[] dArr = map.get(CPU_PER_CLUSTER_CORE_COUNT);
            this.mCpuClusters = new CpuClusterKey[dArr.length];
            for (int i = 0; i < dArr.length; i++) {
                int iRound = (int) Math.round(dArr[i].doubleValue());
                this.mCpuClusters[i] = new CpuClusterKey(CPU_CORE_SPEED_PREFIX + i, CPU_CLUSTER_POWER_COUNT + i, CPU_CORE_POWER_PREFIX + i, iRound);
            }
            return;
        }
        this.mCpuClusters = new CpuClusterKey[1];
        HashMap<String, Double> map2 = sPowerItemMap;
        this.mCpuClusters[0] = new CpuClusterKey("cpu.core_speeds.cluster0", "cpu.cluster_power.cluster0", "cpu.core_power.cluster0", map2.containsKey(CPU_PER_CLUSTER_CORE_COUNT) ? (int) Math.round(map2.get(CPU_PER_CLUSTER_CORE_COUNT).doubleValue()) : 1);
    }

    private void initCpuScalingPolicies() {
        double[] dArr;
        double[] dArr2;
        int iMax = 0;
        for (String str : sPowerItemMap.keySet()) {
            if (str.startsWith(CPU_SCALING_POLICY_POWER_POLICY)) {
                iMax = Math.max(iMax, Integer.parseInt(str.substring(31)) + 1);
            }
        }
        for (String str2 : sPowerArrayMap.keySet()) {
            if (str2.startsWith(CPU_SCALING_STEP_POWER_POLICY)) {
                iMax = Math.max(iMax, Integer.parseInt(str2.substring(29)) + 1);
            }
        }
        if (iMax > 0) {
            this.mCpuScalingPolicies = new SparseArray<>(iMax);
            for (int i = 0; i < iMax; i++) {
                Double d = sPowerItemMap.get(CPU_SCALING_POLICY_POWER_POLICY + i);
                Double[] dArr3 = sPowerArrayMap.get(CPU_SCALING_STEP_POWER_POLICY + i);
                if (d != null || dArr3 != null) {
                    if (dArr3 != null) {
                        dArr2 = new double[dArr3.length];
                        for (int i2 = 0; i2 < dArr3.length; i2++) {
                            dArr2[i2] = dArr3[i2].doubleValue();
                        }
                    } else {
                        dArr2 = new double[0];
                    }
                    this.mCpuScalingPolicies.put(i, new CpuScalingPolicyPower(d != null ? d.doubleValue() : 0.0d, dArr2));
                }
            }
            return;
        }
        CpuClusterKey[] cpuClusterKeyArr = this.mCpuClusters;
        int length = cpuClusterKeyArr.length;
        int i3 = 0;
        int i4 = 0;
        while (i3 < length) {
            int i5 = i4 + 1;
            i4 += cpuClusterKeyArr[i3].numCpus;
            i3++;
            iMax = i5;
        }
        if (iMax > 0) {
            this.mCpuScalingPolicies = new SparseArray<>(iMax);
            int i6 = 0;
            for (CpuClusterKey cpuClusterKey : this.mCpuClusters) {
                double averagePower = getAveragePower(cpuClusterKey.clusterPowerKey);
                int numElements = getNumElements(cpuClusterKey.corePowerKey);
                if (numElements != 0) {
                    dArr = new double[numElements];
                    for (int i7 = 0; i7 < numElements; i7++) {
                        dArr[i7] = getAveragePower(cpuClusterKey.corePowerKey, i7);
                    }
                } else {
                    dArr = new double[1];
                }
                this.mCpuScalingPolicies.put(i6, new CpuScalingPolicyPower(averagePower, dArr));
                i6 += cpuClusterKey.numCpus;
            }
            return;
        }
        SparseArray<CpuScalingPolicyPower> sparseArray = new SparseArray<>(1);
        this.mCpuScalingPolicies = sparseArray;
        sparseArray.put(0, new CpuScalingPolicyPower(getAveragePower(POWER_CPU_ACTIVE), new double[]{SContextConstants.ENVIRONMENT_VALUE_UNKNOWN}));
    }

    private void initCpuPowerBrackets() {
        boolean z = true;
        boolean z2 = false;
        for (int size = this.mCpuScalingPolicies.size() - 1; size >= 0; size--) {
            int iKeyAt = this.mCpuScalingPolicies.keyAt(size);
            CpuScalingPolicyPower cpuScalingPolicyPowerValueAt = this.mCpuScalingPolicies.valueAt(size);
            cpuScalingPolicyPowerValueAt.powerBrackets = new int[cpuScalingPolicyPowerValueAt.stepPower.length];
            if (sPowerArrayMap.get(CPU_POWER_BRACKETS_PREFIX + iKeyAt) != null) {
                z2 = true;
            } else {
                z = false;
            }
        }
        if (z2 && !z) {
            throw new RuntimeException("Power brackets should be specified for all scaling policies or none");
        }
        if (!z) {
            this.mCpuPowerBracketCount = -1;
            return;
        }
        this.mCpuPowerBracketCount = 0;
        for (int size2 = this.mCpuScalingPolicies.size() - 1; size2 >= 0; size2--) {
            int iKeyAt2 = this.mCpuScalingPolicies.keyAt(size2);
            CpuScalingPolicyPower cpuScalingPolicyPowerValueAt2 = this.mCpuScalingPolicies.valueAt(size2);
            Double[] dArr = sPowerArrayMap.get(CPU_POWER_BRACKETS_PREFIX + iKeyAt2);
            if (dArr.length != cpuScalingPolicyPowerValueAt2.powerBrackets.length) {
                throw new RuntimeException("Wrong number of items in cpu.power_brackets.policy" + iKeyAt2 + ", expected: " + cpuScalingPolicyPowerValueAt2.powerBrackets.length);
            }
            for (int i = 0; i < dArr.length; i++) {
                int iRound = (int) Math.round(dArr[i].doubleValue());
                cpuScalingPolicyPowerValueAt2.powerBrackets[i] = iRound;
                if (iRound > this.mCpuPowerBracketCount) {
                    this.mCpuPowerBracketCount = iRound;
                }
            }
        }
        this.mCpuPowerBracketCount++;
    }

    private static class CpuScalingPolicyPower {
        public final double policyPower;
        public int[] powerBrackets;
        public final double[] stepPower;

        private CpuScalingPolicyPower(double d, double[] dArr) {
            this.policyPower = d;
            this.stepPower = dArr;
        }
    }

    public double getAveragePowerForCpuScalingPolicy(int i) {
        CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(i);
        return cpuScalingPolicyPower != null ? cpuScalingPolicyPower.policyPower : SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public double getAveragePowerForCpuScalingStep(int i, int i2) {
        CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(i);
        return (cpuScalingPolicyPower == null || i2 < 0 || i2 >= cpuScalingPolicyPower.stepPower.length) ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : cpuScalingPolicyPower.stepPower[i2];
    }

    private static class CpuClusterKey {
        public final String clusterPowerKey;
        public final String corePowerKey;
        public final String freqKey;
        public final int numCpus;

        private CpuClusterKey(String str, String str2, String str3, int i) {
            this.freqKey = str;
            this.clusterPowerKey = str2;
            this.corePowerKey = str3;
            this.numCpus = i;
        }
    }

    @Deprecated
    public int getNumCpuClusters() {
        return this.mCpuClusters.length;
    }

    @Deprecated
    public int getNumCoresInCpuCluster(int i) {
        if (i < 0) {
            return 0;
        }
        CpuClusterKey[] cpuClusterKeyArr = this.mCpuClusters;
        if (i >= cpuClusterKeyArr.length) {
            return 0;
        }
        return cpuClusterKeyArr[i].numCpus;
    }

    @Deprecated
    public int getNumSpeedStepsInCpuCluster(int i) {
        if (i < 0) {
            return 0;
        }
        CpuClusterKey[] cpuClusterKeyArr = this.mCpuClusters;
        if (i >= cpuClusterKeyArr.length) {
            return 0;
        }
        HashMap<String, Double[]> map = sPowerArrayMap;
        if (map.containsKey(cpuClusterKeyArr[i].freqKey)) {
            return map.get(this.mCpuClusters[i].freqKey).length;
        }
        return 1;
    }

    @Deprecated
    public double getAveragePowerForCpuCluster(int i) {
        if (i < 0) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        CpuClusterKey[] cpuClusterKeyArr = this.mCpuClusters;
        return i < cpuClusterKeyArr.length ? getAveragePower(cpuClusterKeyArr[i].clusterPowerKey) : SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Deprecated
    public double getAveragePowerForCpuCore(int i, int i2) {
        if (i < 0) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        CpuClusterKey[] cpuClusterKeyArr = this.mCpuClusters;
        return i < cpuClusterKeyArr.length ? getAveragePower(cpuClusterKeyArr[i].corePowerKey, i2) : SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public int getCpuPowerBracketCount() {
        return this.mCpuPowerBracketCount;
    }

    public int getCpuPowerBracketForScalingStep(int i, int i2) {
        CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(i);
        if (cpuScalingPolicyPower == null || i2 < 0 || i2 >= cpuScalingPolicyPower.powerBrackets.length) {
            return 0;
        }
        return cpuScalingPolicyPower.powerBrackets[i2];
    }

    private void initDisplays() {
        boolean z;
        this.mNumDisplays = 0;
        while (true) {
            if (Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_AMBIENT, this.mNumDisplays, Double.NaN)) && Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_SCREEN_ON, this.mNumDisplays, Double.NaN)) && Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_SCREEN_FULL, this.mNumDisplays, Double.NaN))) {
                break;
            } else {
                this.mNumDisplays++;
            }
        }
        HashMap<String, Double> map = sPowerItemMap;
        Double d = map.get(POWER_AMBIENT_DISPLAY);
        if (d == null || this.mNumDisplays != 0) {
            z = false;
        } else {
            String ordinalPowerType = getOrdinalPowerType(POWER_GROUP_DISPLAY_AMBIENT, 0);
            Slog.w(TAG, "ambient.on is deprecated! Use " + ordinalPowerType + " instead.");
            map.put(ordinalPowerType, d);
            z = true;
        }
        Double d2 = map.get(POWER_SCREEN_ON);
        if (d2 != null && this.mNumDisplays == 0) {
            String ordinalPowerType2 = getOrdinalPowerType(POWER_GROUP_DISPLAY_SCREEN_ON, 0);
            Slog.w(TAG, "screen.on is deprecated! Use " + ordinalPowerType2 + " instead.");
            map.put(ordinalPowerType2, d2);
            z = true;
        }
        Double d3 = map.get(POWER_SCREEN_FULL);
        if (d3 != null && this.mNumDisplays == 0) {
            String ordinalPowerType3 = getOrdinalPowerType(POWER_GROUP_DISPLAY_SCREEN_FULL, 0);
            Slog.w(TAG, "screen.full is deprecated! Use " + ordinalPowerType3 + " instead.");
            map.put(ordinalPowerType3, d3);
            z = true;
        }
        if (z) {
            this.mNumDisplays = 1;
        }
    }

    public int getNumDisplays() {
        return this.mNumDisplays;
    }

    private void initModem() {
        handleDeprecatedModemConstant(0, POWER_MODEM_CONTROLLER_SLEEP, 0);
        handleDeprecatedModemConstant(268435456, POWER_MODEM_CONTROLLER_IDLE, 0);
        handleDeprecatedModemConstant(536870912, POWER_MODEM_CONTROLLER_RX, 0);
        handleDeprecatedModemConstant(805306368, POWER_MODEM_CONTROLLER_TX, 0);
        handleDeprecatedModemConstant(Enums.AUDIO_FORMAT_APTX_R4, POWER_MODEM_CONTROLLER_TX, 1);
        handleDeprecatedModemConstant(Enums.AUDIO_FORMAT_DTS_HD_MA, POWER_MODEM_CONTROLLER_TX, 2);
        handleDeprecatedModemConstant(Enums.AUDIO_FORMAT_DTS_UHD_P2, POWER_MODEM_CONTROLLER_TX, 3);
        handleDeprecatedModemConstant(872415232, POWER_MODEM_CONTROLLER_TX, 4);
    }

    private void handleDeprecatedModemConstant(int i, String str, int i2) {
        ModemPowerProfile modemPowerProfile = sModemPowerProfile;
        if (Double.isNaN(modemPowerProfile.getAverageBatteryDrainMa(i))) {
            modemPowerProfile.setPowerConstant(i, Double.toString(getAveragePower(str, i2)));
        }
    }

    public int getNumElements(String str) {
        if (sPowerItemMap.containsKey(str)) {
            return 1;
        }
        HashMap<String, Double[]> map = sPowerArrayMap;
        if (map.containsKey(str)) {
            return map.get(str).length;
        }
        return 0;
    }

    public double getAveragePowerOrDefault(String str, double d) {
        HashMap<String, Double> map = sPowerItemMap;
        if (map.containsKey(str)) {
            return map.get(str).doubleValue();
        }
        HashMap<String, Double[]> map2 = sPowerArrayMap;
        return map2.containsKey(str) ? map2.get(str)[0].doubleValue() : d;
    }

    private boolean isIgnoreType(String str) {
        return Build.VERSION.SEM_FIRST_SDK_INT < 31 && str != null && str.equals("video");
    }

    public double getAveragePower(String str) {
        return isIgnoreType(str) ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : getAveragePowerOrDefault(str, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double getAverageBatteryDrainOrDefaultMa(long j, double d) {
        double averageBatteryDrainMa = (64424509440L & j) == 4294967296L ? sModemPowerProfile.getAverageBatteryDrainMa((int) j) : Double.NaN;
        return Double.isNaN(averageBatteryDrainMa) ? d : averageBatteryDrainMa;
    }

    public double getAverageBatteryDrainMa(long j) {
        return getAverageBatteryDrainOrDefaultMa(j, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double getAveragePower(String str, int i) {
        if (isIgnoreType(str)) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        HashMap<String, Double> map = sPowerItemMap;
        if (map.containsKey(str)) {
            return map.get(str).doubleValue();
        }
        HashMap<String, Double[]> map2 = sPowerArrayMap;
        if (map2.containsKey(str)) {
            Double[] dArr = map2.get(str);
            if (dArr.length > i && i >= 0) {
                return dArr[i].doubleValue();
            }
            if (i >= 0 && dArr.length != 0) {
                return dArr[dArr.length - 1].doubleValue();
            }
        }
        return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public double getAveragePowerForOrdinal(String str, int i, double d) {
        return getAveragePowerOrDefault(getOrdinalPowerType(str, i), d);
    }

    public double getAveragePowerForOrdinal(String str, int i) {
        return getAveragePowerForOrdinal(str, i, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double getBatteryCapacity() {
        return getAveragePower(POWER_BATTERY_CAPACITY);
    }

    public double getBatteryTypicalCapacity() {
        return getAveragePower(POWER_BATTERY_TYPICAL_CAPACITY);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream) {
        writePowerConstantToProto(protoOutputStream, POWER_CPU_SUSPEND, 1103806595073L);
        writePowerConstantToProto(protoOutputStream, POWER_CPU_IDLE, 1103806595074L);
        writePowerConstantToProto(protoOutputStream, POWER_CPU_ACTIVE, 1103806595075L);
        for (int i = 0; i < this.mCpuClusters.length; i++) {
            long jStart = protoOutputStream.start(2246267895848L);
            protoOutputStream.write(1120986464257L, i);
            protoOutputStream.write(1103806595074L, sPowerItemMap.get(this.mCpuClusters[i].clusterPowerKey).doubleValue());
            protoOutputStream.write(1120986464259L, this.mCpuClusters[i].numCpus);
            for (Double d : sPowerArrayMap.get(this.mCpuClusters[i].freqKey)) {
                protoOutputStream.write(PowerProfileProto.CpuCluster.SPEED, d.doubleValue());
            }
            for (Double d2 : sPowerArrayMap.get(this.mCpuClusters[i].corePowerKey)) {
                protoOutputStream.write(PowerProfileProto.CpuCluster.CORE_POWER, d2.doubleValue());
            }
            protoOutputStream.end(jStart);
        }
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_SCAN, 1103806595076L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_ON, 1103806595077L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_ACTIVE, 1103806595078L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_IDLE, PowerProfileProto.WIFI_CONTROLLER_IDLE);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_RX, 1103806595080L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_TX, 1103806595081L);
        writePowerConstantArrayToProto(protoOutputStream, POWER_WIFI_CONTROLLER_TX_LEVELS, PowerProfileProto.WIFI_CONTROLLER_TX_LEVELS);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE, PowerProfileProto.WIFI_CONTROLLER_OPERATING_VOLTAGE);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_IDLE, PowerProfileProto.BLUETOOTH_CONTROLLER_IDLE);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_RX, PowerProfileProto.BLUETOOTH_CONTROLLER_RX);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_TX, PowerProfileProto.BLUETOOTH_CONTROLLER_TX);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE, PowerProfileProto.BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE);
        writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_SLEEP, PowerProfileProto.MODEM_CONTROLLER_SLEEP);
        writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_IDLE, PowerProfileProto.MODEM_CONTROLLER_IDLE);
        writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_RX, 1103806595090L);
        writePowerConstantArrayToProto(protoOutputStream, POWER_MODEM_CONTROLLER_TX, PowerProfileProto.MODEM_CONTROLLER_TX);
        writePowerConstantToProto(protoOutputStream, POWER_MODEM_CONTROLLER_OPERATING_VOLTAGE, 1103806595092L);
        writePowerConstantToProto(protoOutputStream, POWER_GPS_ON, 1103806595093L);
        writePowerConstantArrayToProto(protoOutputStream, POWER_GPS_SIGNAL_QUALITY_BASED, PowerProfileProto.GPS_SIGNAL_QUALITY_BASED);
        writePowerConstantToProto(protoOutputStream, POWER_GPS_OPERATING_VOLTAGE, 1103806595095L);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_ON, 1103806595096L);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_ACTIVE, 1103806595097L);
        writePowerConstantToProto(protoOutputStream, POWER_BLUETOOTH_AT_CMD, 1103806595098L);
        writePowerConstantToProto(protoOutputStream, POWER_AMBIENT_DISPLAY, PowerProfileProto.AMBIENT_DISPLAY);
        writePowerConstantToProto(protoOutputStream, POWER_SCREEN_ON, PowerProfileProto.SCREEN_ON);
        writePowerConstantToProto(protoOutputStream, POWER_RADIO_ON, PowerProfileProto.RADIO_ON);
        writePowerConstantToProto(protoOutputStream, POWER_RADIO_SCANNING, PowerProfileProto.RADIO_SCANNING);
        writePowerConstantToProto(protoOutputStream, POWER_RADIO_ACTIVE, PowerProfileProto.RADIO_ACTIVE);
        writePowerConstantToProto(protoOutputStream, POWER_SCREEN_FULL, PowerProfileProto.SCREEN_FULL);
        writePowerConstantToProto(protoOutputStream, "audio", PowerProfileProto.AUDIO);
        writePowerConstantToProto(protoOutputStream, "video", PowerProfileProto.VIDEO);
        writePowerConstantToProto(protoOutputStream, POWER_FLASHLIGHT, 1103806595107L);
        writePowerConstantToProto(protoOutputStream, POWER_MEMORY, 1103806595108L);
        writePowerConstantToProto(protoOutputStream, POWER_CAMERA, 1103806595109L);
        writePowerConstantToProto(protoOutputStream, POWER_WIFI_BATCHED_SCAN, PowerProfileProto.WIFI_BATCHED_SCAN);
        writePowerConstantToProto(protoOutputStream, POWER_BATTERY_CAPACITY, PowerProfileProto.BATTERY_CAPACITY);
    }

    public void dump(PrintWriter printWriter) {
        final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        sPowerItemMap.forEach(new BiConsumer() { // from class: com.android.internal.os.PowerProfile$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PowerProfile.lambda$dump$0(indentingPrintWriter, (String) obj, (Double) obj2);
            }
        });
        sPowerArrayMap.forEach(new BiConsumer() { // from class: com.android.internal.os.PowerProfile$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PowerProfile.lambda$dump$1(indentingPrintWriter, (String) obj, (Double[]) obj2);
            }
        });
        indentingPrintWriter.println("Modem values:");
        indentingPrintWriter.increaseIndent();
        sModemPowerProfile.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    static /* synthetic */ void lambda$dump$0(IndentingPrintWriter indentingPrintWriter, String str, Double d) {
        indentingPrintWriter.print(str, d);
        indentingPrintWriter.println();
    }

    static /* synthetic */ void lambda$dump$1(IndentingPrintWriter indentingPrintWriter, String str, Double[] dArr) {
        indentingPrintWriter.print(str, Arrays.toString(dArr));
        indentingPrintWriter.println();
    }

    private void writePowerConstantToProto(ProtoOutputStream protoOutputStream, String str, long j) {
        HashMap<String, Double> map = sPowerItemMap;
        if (map.containsKey(str)) {
            protoOutputStream.write(j, map.get(str).doubleValue());
        }
    }

    private void writePowerConstantArrayToProto(ProtoOutputStream protoOutputStream, String str, long j) {
        HashMap<String, Double[]> map = sPowerArrayMap;
        if (map.containsKey(str)) {
            for (Double d : map.get(str)) {
                protoOutputStream.write(j, d.doubleValue());
            }
        }
    }

    private static String getOrdinalPowerType(String str, int i) {
        return str + i;
    }

    public int getAllFrequencies() {
        int length = 0;
        for (int size = this.mCpuScalingPolicies.size() - 1; size >= 0; size--) {
            length += this.mCpuScalingPolicies.valueAt(size).stepPower.length;
        }
        return length;
    }
}
