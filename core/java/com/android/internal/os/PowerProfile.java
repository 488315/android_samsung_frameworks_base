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

    public PowerProfile(Context context, boolean forTest) {
        synchronized (sLock) {
            int xmlId = forTest ? R.xml.power_profile_test : R.xml.power_profile;
            initLocked(context, xmlId);
        }
    }

    public void initForTesting(XmlPullParser parser) {
        initForTesting(parser, null);
    }

    public void initForTesting(XmlPullParser parser, Resources resources) {
        synchronized (sLock) {
            sPowerItemMap.clear();
            sPowerArrayMap.clear();
            sModemPowerProfile.clear();
            try {
                readPowerValuesFromXml(parser, resources);
                initLocked();
            } finally {
                if (parser instanceof XmlResourceParser) {
                    ((XmlResourceParser) parser).close();
                }
            }
        }
    }

    private void initLocked(Context context, int xmlId) {
        if (sPowerItemMap.size() == 0 && sPowerArrayMap.size() == 0) {
            Resources resources = context.getResources();
            XmlResourceParser parser = resources.getXml(xmlId);
            readPowerValuesFromXml(parser, resources);
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

    private static void readPowerValuesFromXml(XmlPullParser parser, Resources resources) {
        boolean parsingArray = false;
        ArrayList<Double> array = new ArrayList<>();
        String arrayName = null;
        try {
            try {
                XmlUtils.beginDocument(parser, "device");
                while (true) {
                    XmlUtils.nextElement(parser);
                    String element = parser.getName();
                    if (element == null) {
                        break;
                    }
                    if (parsingArray && !element.equals("value")) {
                        sPowerArrayMap.put(arrayName, (Double[]) array.toArray(new Double[array.size()]));
                        parsingArray = false;
                    }
                    if (element.equals(TAG_ARRAY)) {
                        parsingArray = true;
                        array.clear();
                        arrayName = parser.getAttributeValue(null, "name");
                    } else {
                        if (!element.equals("item") && !element.equals("value")) {
                            if (element.equals("modem")) {
                                sModemPowerProfile.parseFromXml(parser);
                            }
                        }
                        String name = parsingArray ? null : parser.getAttributeValue(null, "name");
                        if (parser.next() == 4) {
                            String power = parser.getText();
                            double value = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                            try {
                                value = Double.valueOf(power).doubleValue();
                            } catch (NumberFormatException e) {
                            }
                            if (element.equals("item")) {
                                sPowerItemMap.put(name, Double.valueOf(value));
                            } else if (parsingArray) {
                                array.add(Double.valueOf(value));
                            }
                        }
                    }
                }
                if (parsingArray) {
                    sPowerArrayMap.put(arrayName, (Double[]) array.toArray(new Double[array.size()]));
                }
                if (resources != null) {
                    getDefaultValuesFromConfig(resources);
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            } catch (XmlPullParserException e3) {
                throw new RuntimeException(e3);
            }
        } finally {
            if (parser instanceof XmlResourceParser) {
                ((XmlResourceParser) parser).close();
            }
        }
    }

    private static void getDefaultValuesFromConfig(Resources resources) {
        int value;
        int[] configResIds = {R.integer.config_bluetooth_idle_cur_ma, R.integer.config_bluetooth_rx_cur_ma, R.integer.config_bluetooth_tx_cur_ma, R.integer.config_bluetooth_operating_voltage_mv};
        String[] configResIdKeys = {POWER_BLUETOOTH_CONTROLLER_IDLE, POWER_BLUETOOTH_CONTROLLER_RX, POWER_BLUETOOTH_CONTROLLER_TX, POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE};
        for (int i = 0; i < configResIds.length; i++) {
            String key = configResIdKeys[i];
            if ((!sPowerItemMap.containsKey(key) || sPowerItemMap.get(key).doubleValue() <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) && (value = resources.getInteger(configResIds[i])) > 0) {
                sPowerItemMap.put(key, Double.valueOf(value));
            }
        }
    }

    private void initCpuClusters() {
        if (sPowerArrayMap.containsKey(CPU_PER_CLUSTER_CORE_COUNT)) {
            Double[] data = sPowerArrayMap.get(CPU_PER_CLUSTER_CORE_COUNT);
            this.mCpuClusters = new CpuClusterKey[data.length];
            for (int cluster = 0; cluster < data.length; cluster++) {
                int numCpusInCluster = (int) Math.round(data[cluster].doubleValue());
                this.mCpuClusters[cluster] = new CpuClusterKey(CPU_CORE_SPEED_PREFIX + cluster, CPU_CLUSTER_POWER_COUNT + cluster, CPU_CORE_POWER_PREFIX + cluster, numCpusInCluster);
            }
            return;
        }
        this.mCpuClusters = new CpuClusterKey[1];
        int numCpus = 1;
        if (sPowerItemMap.containsKey(CPU_PER_CLUSTER_CORE_COUNT)) {
            numCpus = (int) Math.round(sPowerItemMap.get(CPU_PER_CLUSTER_CORE_COUNT).doubleValue());
        }
        this.mCpuClusters[0] = new CpuClusterKey("cpu.core_speeds.cluster0", "cpu.cluster_power.cluster0", "cpu.core_power.cluster0", numCpus);
    }

    private void initCpuScalingPolicies() {
        double[] stepPower;
        double[] primitiveStepPower;
        int policyCount = 0;
        for (String key : sPowerItemMap.keySet()) {
            if (key.startsWith(CPU_SCALING_POLICY_POWER_POLICY)) {
                int policy = Integer.parseInt(key.substring(CPU_SCALING_POLICY_POWER_POLICY.length()));
                policyCount = Math.max(policyCount, policy + 1);
            }
        }
        for (String key2 : sPowerArrayMap.keySet()) {
            if (key2.startsWith(CPU_SCALING_STEP_POWER_POLICY)) {
                int policy2 = Integer.parseInt(key2.substring(CPU_SCALING_STEP_POWER_POLICY.length()));
                policyCount = Math.max(policyCount, policy2 + 1);
            }
        }
        if (policyCount > 0) {
            this.mCpuScalingPolicies = new SparseArray<>(policyCount);
            for (int policy3 = 0; policy3 < policyCount; policy3++) {
                Double policyPower = sPowerItemMap.get(CPU_SCALING_POLICY_POWER_POLICY + policy3);
                Double[] stepPower2 = sPowerArrayMap.get(CPU_SCALING_STEP_POWER_POLICY + policy3);
                if (policyPower != null || stepPower2 != null) {
                    if (stepPower2 != null) {
                        primitiveStepPower = new double[stepPower2.length];
                        for (int i = 0; i < stepPower2.length; i++) {
                            primitiveStepPower[i] = stepPower2[i].doubleValue();
                        }
                    } else {
                        primitiveStepPower = new double[0];
                    }
                    this.mCpuScalingPolicies.put(policy3, new CpuScalingPolicyPower(policyPower != null ? policyPower.doubleValue() : 0.0d, primitiveStepPower));
                }
            }
            return;
        }
        int cpuId = 0;
        for (CpuClusterKey cpuClusterKey : this.mCpuClusters) {
            policyCount = cpuId + 1;
            cpuId += cpuClusterKey.numCpus;
        }
        if (policyCount > 0) {
            this.mCpuScalingPolicies = new SparseArray<>(policyCount);
            int cpuId2 = 0;
            for (CpuClusterKey cpuCluster : this.mCpuClusters) {
                double clusterPower = getAveragePower(cpuCluster.clusterPowerKey);
                int numSteps = getNumElements(cpuCluster.corePowerKey);
                if (numSteps != 0) {
                    stepPower = new double[numSteps];
                    for (int step = 0; step < numSteps; step++) {
                        stepPower[step] = getAveragePower(cpuCluster.corePowerKey, step);
                    }
                } else {
                    stepPower = new double[1];
                }
                this.mCpuScalingPolicies.put(cpuId2, new CpuScalingPolicyPower(clusterPower, stepPower));
                cpuId2 += cpuCluster.numCpus;
            }
            return;
        }
        this.mCpuScalingPolicies = new SparseArray<>(1);
        this.mCpuScalingPolicies.put(0, new CpuScalingPolicyPower(getAveragePower(POWER_CPU_ACTIVE), new double[]{SContextConstants.ENVIRONMENT_VALUE_UNKNOWN}));
    }

    private void initCpuPowerBrackets() {
        boolean anyBracketsSpecified = false;
        boolean allBracketsSpecified = true;
        int i = this.mCpuScalingPolicies.size();
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            int policy = this.mCpuScalingPolicies.keyAt(i);
            CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.valueAt(i);
            int steps = cpuScalingPolicyPower.stepPower.length;
            cpuScalingPolicyPower.powerBrackets = new int[steps];
            if (sPowerArrayMap.get(CPU_POWER_BRACKETS_PREFIX + policy) != null) {
                anyBracketsSpecified = true;
            } else {
                allBracketsSpecified = false;
            }
        }
        if (anyBracketsSpecified && !allBracketsSpecified) {
            throw new RuntimeException("Power brackets should be specified for all scaling policies or none");
        }
        if (!allBracketsSpecified) {
            this.mCpuPowerBracketCount = -1;
            return;
        }
        this.mCpuPowerBracketCount = 0;
        for (int i2 = this.mCpuScalingPolicies.size() - 1; i2 >= 0; i2--) {
            int policy2 = this.mCpuScalingPolicies.keyAt(i2);
            CpuScalingPolicyPower cpuScalingPolicyPower2 = this.mCpuScalingPolicies.valueAt(i2);
            Double[] data = sPowerArrayMap.get(CPU_POWER_BRACKETS_PREFIX + policy2);
            if (data.length != cpuScalingPolicyPower2.powerBrackets.length) {
                throw new RuntimeException("Wrong number of items in cpu.power_brackets.policy" + policy2 + ", expected: " + cpuScalingPolicyPower2.powerBrackets.length);
            }
            for (int j = 0; j < data.length; j++) {
                int bracket = (int) Math.round(data[j].doubleValue());
                cpuScalingPolicyPower2.powerBrackets[j] = bracket;
                if (bracket > this.mCpuPowerBracketCount) {
                    this.mCpuPowerBracketCount = bracket;
                }
            }
        }
        int i3 = this.mCpuPowerBracketCount;
        this.mCpuPowerBracketCount = i3 + 1;
    }

    private static class CpuScalingPolicyPower {
        public final double policyPower;
        public int[] powerBrackets;
        public final double[] stepPower;

        private CpuScalingPolicyPower(double policyPower, double[] stepPower) {
            this.policyPower = policyPower;
            this.stepPower = stepPower;
        }
    }

    public double getAveragePowerForCpuScalingPolicy(int policy) {
        CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(policy);
        if (cpuScalingPolicyPower != null) {
            return cpuScalingPolicyPower.policyPower;
        }
        return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public double getAveragePowerForCpuScalingStep(int policy, int step) {
        CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(policy);
        if (cpuScalingPolicyPower != null && step >= 0 && step < cpuScalingPolicyPower.stepPower.length) {
            return cpuScalingPolicyPower.stepPower[step];
        }
        return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    private static class CpuClusterKey {
        public final String clusterPowerKey;
        public final String corePowerKey;
        public final String freqKey;
        public final int numCpus;

        private CpuClusterKey(String freqKey, String clusterPowerKey, String corePowerKey, int numCpus) {
            this.freqKey = freqKey;
            this.clusterPowerKey = clusterPowerKey;
            this.corePowerKey = corePowerKey;
            this.numCpus = numCpus;
        }
    }

    @Deprecated
    public int getNumCpuClusters() {
        return this.mCpuClusters.length;
    }

    @Deprecated
    public int getNumCoresInCpuCluster(int cluster) {
        if (cluster < 0 || cluster >= this.mCpuClusters.length) {
            return 0;
        }
        return this.mCpuClusters[cluster].numCpus;
    }

    @Deprecated
    public int getNumSpeedStepsInCpuCluster(int cluster) {
        if (cluster < 0 || cluster >= this.mCpuClusters.length) {
            return 0;
        }
        if (sPowerArrayMap.containsKey(this.mCpuClusters[cluster].freqKey)) {
            return sPowerArrayMap.get(this.mCpuClusters[cluster].freqKey).length;
        }
        return 1;
    }

    @Deprecated
    public double getAveragePowerForCpuCluster(int cluster) {
        if (cluster >= 0 && cluster < this.mCpuClusters.length) {
            return getAveragePower(this.mCpuClusters[cluster].clusterPowerKey);
        }
        return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Deprecated
    public double getAveragePowerForCpuCore(int cluster, int step) {
        if (cluster >= 0 && cluster < this.mCpuClusters.length) {
            return getAveragePower(this.mCpuClusters[cluster].corePowerKey, step);
        }
        return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    public int getCpuPowerBracketCount() {
        return this.mCpuPowerBracketCount;
    }

    public int getCpuPowerBracketForScalingStep(int policy, int step) {
        CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.get(policy);
        if (cpuScalingPolicyPower != null && step >= 0 && step < cpuScalingPolicyPower.powerBrackets.length) {
            return cpuScalingPolicyPower.powerBrackets[step];
        }
        return 0;
    }

    private void initDisplays() {
        this.mNumDisplays = 0;
        while (true) {
            if (Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_AMBIENT, this.mNumDisplays, Double.NaN)) && Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_SCREEN_ON, this.mNumDisplays, Double.NaN)) && Double.isNaN(getAveragePowerForOrdinal(POWER_GROUP_DISPLAY_SCREEN_FULL, this.mNumDisplays, Double.NaN))) {
                break;
            } else {
                this.mNumDisplays++;
            }
        }
        Double deprecatedAmbientDisplay = sPowerItemMap.get(POWER_AMBIENT_DISPLAY);
        boolean legacy = false;
        if (deprecatedAmbientDisplay != null && this.mNumDisplays == 0) {
            String key = getOrdinalPowerType(POWER_GROUP_DISPLAY_AMBIENT, 0);
            Slog.w(TAG, "ambient.on is deprecated! Use " + key + " instead.");
            sPowerItemMap.put(key, deprecatedAmbientDisplay);
            legacy = true;
        }
        Double deprecatedScreenOn = sPowerItemMap.get(POWER_SCREEN_ON);
        if (deprecatedScreenOn != null && this.mNumDisplays == 0) {
            String key2 = getOrdinalPowerType(POWER_GROUP_DISPLAY_SCREEN_ON, 0);
            Slog.w(TAG, "screen.on is deprecated! Use " + key2 + " instead.");
            sPowerItemMap.put(key2, deprecatedScreenOn);
            legacy = true;
        }
        Double deprecatedScreenFull = sPowerItemMap.get(POWER_SCREEN_FULL);
        if (deprecatedScreenFull != null && this.mNumDisplays == 0) {
            String key3 = getOrdinalPowerType(POWER_GROUP_DISPLAY_SCREEN_FULL, 0);
            Slog.w(TAG, "screen.full is deprecated! Use " + key3 + " instead.");
            sPowerItemMap.put(key3, deprecatedScreenFull);
            legacy = true;
        }
        if (legacy) {
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

    private void handleDeprecatedModemConstant(int key, String deprecatedKey, int level) {
        double drain = sModemPowerProfile.getAverageBatteryDrainMa(key);
        if (Double.isNaN(drain)) {
            double deprecatedDrain = getAveragePower(deprecatedKey, level);
            sModemPowerProfile.setPowerConstant(key, Double.toString(deprecatedDrain));
        }
    }

    public int getNumElements(String key) {
        if (sPowerItemMap.containsKey(key)) {
            return 1;
        }
        if (sPowerArrayMap.containsKey(key)) {
            return sPowerArrayMap.get(key).length;
        }
        return 0;
    }

    public double getAveragePowerOrDefault(String type, double defaultValue) {
        if (sPowerItemMap.containsKey(type)) {
            return sPowerItemMap.get(type).doubleValue();
        }
        if (sPowerArrayMap.containsKey(type)) {
            return sPowerArrayMap.get(type)[0].doubleValue();
        }
        return defaultValue;
    }

    private boolean isIgnoreType(String type) {
        if (Build.VERSION.SEM_FIRST_SDK_INT < 31 && type != null && type.equals("video")) {
            return true;
        }
        return false;
    }

    public double getAveragePower(String type) {
        return isIgnoreType(type) ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : getAveragePowerOrDefault(type, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double getAverageBatteryDrainOrDefaultMa(long key, double defaultValue) {
        double value;
        long subsystemType = 64424509440L & key;
        int subsystemFields = (int) ((-1) & key);
        if (subsystemType == 4294967296L) {
            value = sModemPowerProfile.getAverageBatteryDrainMa(subsystemFields);
        } else {
            value = Double.NaN;
        }
        return Double.isNaN(value) ? defaultValue : value;
    }

    public double getAverageBatteryDrainMa(long key) {
        return getAverageBatteryDrainOrDefaultMa(key, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double getAveragePower(String type, int level) {
        if (isIgnoreType(type)) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        if (sPowerItemMap.containsKey(type)) {
            return sPowerItemMap.get(type).doubleValue();
        }
        if (!sPowerArrayMap.containsKey(type)) {
            return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        }
        Double[] values = sPowerArrayMap.get(type);
        if (values.length <= level || level < 0) {
            return (level < 0 || values.length == 0) ? SContextConstants.ENVIRONMENT_VALUE_UNKNOWN : values[values.length - 1].doubleValue();
        }
        return values[level].doubleValue();
    }

    public double getAveragePowerForOrdinal(String group, int ordinal, double defaultValue) {
        String type = getOrdinalPowerType(group, ordinal);
        return getAveragePowerOrDefault(type, defaultValue);
    }

    public double getAveragePowerForOrdinal(String group, int ordinal) {
        return getAveragePowerForOrdinal(group, ordinal, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
    }

    public double getBatteryCapacity() {
        return getAveragePower(POWER_BATTERY_CAPACITY);
    }

    public double getBatteryTypicalCapacity() {
        return getAveragePower(POWER_BATTERY_TYPICAL_CAPACITY);
    }

    public void dumpDebug(ProtoOutputStream proto) {
        writePowerConstantToProto(proto, POWER_CPU_SUSPEND, 1103806595073L);
        writePowerConstantToProto(proto, POWER_CPU_IDLE, 1103806595074L);
        writePowerConstantToProto(proto, POWER_CPU_ACTIVE, 1103806595075L);
        for (int cluster = 0; cluster < this.mCpuClusters.length; cluster++) {
            long token = proto.start(2246267895848L);
            proto.write(1120986464257L, cluster);
            proto.write(1103806595074L, sPowerItemMap.get(this.mCpuClusters[cluster].clusterPowerKey).doubleValue());
            proto.write(1120986464259L, this.mCpuClusters[cluster].numCpus);
            for (Double speed : sPowerArrayMap.get(this.mCpuClusters[cluster].freqKey)) {
                proto.write(PowerProfileProto.CpuCluster.SPEED, speed.doubleValue());
            }
            for (Double corePower : sPowerArrayMap.get(this.mCpuClusters[cluster].corePowerKey)) {
                proto.write(PowerProfileProto.CpuCluster.CORE_POWER, corePower.doubleValue());
            }
            proto.end(token);
        }
        writePowerConstantToProto(proto, POWER_WIFI_SCAN, 1103806595076L);
        writePowerConstantToProto(proto, POWER_WIFI_ON, 1103806595077L);
        writePowerConstantToProto(proto, POWER_WIFI_ACTIVE, 1103806595078L);
        writePowerConstantToProto(proto, POWER_WIFI_CONTROLLER_IDLE, PowerProfileProto.WIFI_CONTROLLER_IDLE);
        writePowerConstantToProto(proto, POWER_WIFI_CONTROLLER_RX, 1103806595080L);
        writePowerConstantToProto(proto, POWER_WIFI_CONTROLLER_TX, 1103806595081L);
        writePowerConstantArrayToProto(proto, POWER_WIFI_CONTROLLER_TX_LEVELS, PowerProfileProto.WIFI_CONTROLLER_TX_LEVELS);
        writePowerConstantToProto(proto, POWER_WIFI_CONTROLLER_OPERATING_VOLTAGE, PowerProfileProto.WIFI_CONTROLLER_OPERATING_VOLTAGE);
        writePowerConstantToProto(proto, POWER_BLUETOOTH_CONTROLLER_IDLE, PowerProfileProto.BLUETOOTH_CONTROLLER_IDLE);
        writePowerConstantToProto(proto, POWER_BLUETOOTH_CONTROLLER_RX, PowerProfileProto.BLUETOOTH_CONTROLLER_RX);
        writePowerConstantToProto(proto, POWER_BLUETOOTH_CONTROLLER_TX, PowerProfileProto.BLUETOOTH_CONTROLLER_TX);
        writePowerConstantToProto(proto, POWER_BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE, PowerProfileProto.BLUETOOTH_CONTROLLER_OPERATING_VOLTAGE);
        writePowerConstantToProto(proto, POWER_MODEM_CONTROLLER_SLEEP, PowerProfileProto.MODEM_CONTROLLER_SLEEP);
        writePowerConstantToProto(proto, POWER_MODEM_CONTROLLER_IDLE, PowerProfileProto.MODEM_CONTROLLER_IDLE);
        writePowerConstantToProto(proto, POWER_MODEM_CONTROLLER_RX, 1103806595090L);
        writePowerConstantArrayToProto(proto, POWER_MODEM_CONTROLLER_TX, PowerProfileProto.MODEM_CONTROLLER_TX);
        writePowerConstantToProto(proto, POWER_MODEM_CONTROLLER_OPERATING_VOLTAGE, 1103806595092L);
        writePowerConstantToProto(proto, POWER_GPS_ON, 1103806595093L);
        writePowerConstantArrayToProto(proto, POWER_GPS_SIGNAL_QUALITY_BASED, PowerProfileProto.GPS_SIGNAL_QUALITY_BASED);
        writePowerConstantToProto(proto, POWER_GPS_OPERATING_VOLTAGE, 1103806595095L);
        writePowerConstantToProto(proto, POWER_BLUETOOTH_ON, 1103806595096L);
        writePowerConstantToProto(proto, POWER_BLUETOOTH_ACTIVE, 1103806595097L);
        writePowerConstantToProto(proto, POWER_BLUETOOTH_AT_CMD, 1103806595098L);
        writePowerConstantToProto(proto, POWER_AMBIENT_DISPLAY, PowerProfileProto.AMBIENT_DISPLAY);
        writePowerConstantToProto(proto, POWER_SCREEN_ON, PowerProfileProto.SCREEN_ON);
        writePowerConstantToProto(proto, POWER_RADIO_ON, PowerProfileProto.RADIO_ON);
        writePowerConstantToProto(proto, POWER_RADIO_SCANNING, PowerProfileProto.RADIO_SCANNING);
        writePowerConstantToProto(proto, POWER_RADIO_ACTIVE, PowerProfileProto.RADIO_ACTIVE);
        writePowerConstantToProto(proto, POWER_SCREEN_FULL, PowerProfileProto.SCREEN_FULL);
        writePowerConstantToProto(proto, "audio", PowerProfileProto.AUDIO);
        writePowerConstantToProto(proto, "video", PowerProfileProto.VIDEO);
        writePowerConstantToProto(proto, POWER_FLASHLIGHT, 1103806595107L);
        writePowerConstantToProto(proto, POWER_MEMORY, 1103806595108L);
        writePowerConstantToProto(proto, POWER_CAMERA, 1103806595109L);
        writePowerConstantToProto(proto, POWER_WIFI_BATCHED_SCAN, PowerProfileProto.WIFI_BATCHED_SCAN);
        writePowerConstantToProto(proto, POWER_BATTERY_CAPACITY, PowerProfileProto.BATTERY_CAPACITY);
    }

    public void dump(PrintWriter pw) {
        final IndentingPrintWriter ipw = new IndentingPrintWriter(pw);
        sPowerItemMap.forEach(new BiConsumer() { // from class: com.android.internal.os.PowerProfile$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PowerProfile.lambda$dump$0(IndentingPrintWriter.this, (String) obj, (Double) obj2);
            }
        });
        sPowerArrayMap.forEach(new BiConsumer() { // from class: com.android.internal.os.PowerProfile$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PowerProfile.lambda$dump$1(IndentingPrintWriter.this, (String) obj, (Double[]) obj2);
            }
        });
        ipw.println("Modem values:");
        ipw.increaseIndent();
        sModemPowerProfile.dump(ipw);
        ipw.decreaseIndent();
    }

    static /* synthetic */ void lambda$dump$0(IndentingPrintWriter ipw, String key, Double value) {
        ipw.print(key, value);
        ipw.println();
    }

    static /* synthetic */ void lambda$dump$1(IndentingPrintWriter ipw, String key, Double[] value) {
        ipw.print(key, Arrays.toString(value));
        ipw.println();
    }

    private void writePowerConstantToProto(ProtoOutputStream proto, String key, long fieldId) {
        if (sPowerItemMap.containsKey(key)) {
            proto.write(fieldId, sPowerItemMap.get(key).doubleValue());
        }
    }

    private void writePowerConstantArrayToProto(ProtoOutputStream proto, String key, long fieldId) {
        if (sPowerArrayMap.containsKey(key)) {
            for (Double d : sPowerArrayMap.get(key)) {
                proto.write(fieldId, d.doubleValue());
            }
        }
    }

    private static String getOrdinalPowerType(String group, int ordinal) {
        return group + ordinal;
    }

    public int getAllFrequencies() {
        int result = 0;
        for (int i = this.mCpuScalingPolicies.size() - 1; i >= 0; i--) {
            CpuScalingPolicyPower cpuScalingPolicyPower = this.mCpuScalingPolicies.valueAt(i);
            result += cpuScalingPolicyPower.stepPower.length;
        }
        return result;
    }
}
