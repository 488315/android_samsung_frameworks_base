package com.android.server.policy;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.devicestate.DeviceState;
import android.os.PowerManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Dumpable;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.devicestate.DeviceStateManagerService;
import com.android.server.devicestate.DeviceStateManagerService$$ExternalSyntheticLambda6;
import com.android.server.devicestate.OverrideRequest;
import com.android.server.devicestate.OverrideRequestController;
import com.android.server.input.InputManagerInternal$LidSwitchCallback;
import com.android.server.input.InputManagerService;
import com.android.server.policy.devicestate.config.Conditions;
import com.android.server.policy.devicestate.config.DeviceStateConfig;
import com.android.server.policy.devicestate.config.LidSwitchCondition;
import com.android.server.policy.devicestate.config.NumericRange;
import com.android.server.policy.devicestate.config.Properties;
import com.android.server.policy.devicestate.config.SensorCondition;
import com.android.server.policy.devicestate.config.XmlParser;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DeviceStateProviderImpl implements Dumpable, InputManagerInternal$LidSwitchCallback, SensorEventListener, PowerManager.OnThermalStatusChangedListener {
    static final DeviceState DEFAULT_DEVICE_STATE = new DeviceState(new DeviceState.Configuration.Builder(0, "DEFAULT").build());
    public static final DeviceStateProviderImpl$$ExternalSyntheticLambda0 FALSE_BOOLEAN_SUPPLIER;
    public static final DeviceStateProviderImpl$$ExternalSyntheticLambda0 TRUE_BOOLEAN_SUPPLIER;
    public final Context mContext;
    public Boolean mIsLidOpen;
    public final DeviceState[] mOrderedStates;
    public boolean mPowerSaveModeEnabled;
    public final Object mLock = new Object();
    public final SparseArray mStateConditions = new SparseArray();
    public DeviceStateManagerService.DeviceStateProviderListener mListener = null;
    public int mLastReportedState = -1;
    public final Map mLatestSensorEvent = new ArrayMap();
    public int mThermalStatus = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AndBooleanSupplier implements BooleanSupplier {
        public List mBooleanSuppliers;

        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            for (int i = 0; i < this.mBooleanSuppliers.size(); i++) {
                if (!((BooleanSupplier) this.mBooleanSuppliers.get(i)).getAsBoolean()) {
                    return false;
                }
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LidSwitchBooleanSupplier implements BooleanSupplier {
        public final boolean mExpectedOpen;

        public LidSwitchBooleanSupplier(boolean z) {
            this.mExpectedOpen = z;
        }

        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            boolean z;
            synchronized (DeviceStateProviderImpl.this.mLock) {
                try {
                    Boolean bool = DeviceStateProviderImpl.this.mIsLidOpen;
                    if (bool == null) {
                        throw new IllegalStateException("Have not received lid switch value.");
                    }
                    z = bool.booleanValue() == this.mExpectedOpen;
                } finally {
                }
            }
            return z;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ReadableConfig {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReadableFileConfig implements ReadableConfig {
        public final File mFile;

        public ReadableFileConfig(File file) {
            this.mFile = file;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SensorBooleanSupplier implements BooleanSupplier {
        public final List mExpectedValues;
        public final Sensor mSensor;

        public SensorBooleanSupplier(Sensor sensor, List list) {
            this.mSensor = sensor;
            this.mExpectedValues = list;
        }

        @Override // java.util.function.BooleanSupplier
        public final boolean getAsBoolean() {
            int i;
            BigDecimal bigDecimal;
            BigDecimal bigDecimal2;
            BigDecimal bigDecimal3;
            synchronized (DeviceStateProviderImpl.this.mLock) {
                try {
                    SensorEvent sensorEvent = (SensorEvent) ((ArrayMap) DeviceStateProviderImpl.this.mLatestSensorEvent).get(this.mSensor);
                    if (sensorEvent == null) {
                        throw new IllegalStateException("Have not received sensor event.");
                    }
                    if (sensorEvent.values.length < this.mExpectedValues.size()) {
                        throw new RuntimeException("Number of supplied numeric range(s) does not match the number of values in the latest sensor event for sensor: " + this.mSensor);
                    }
                    for (0; i < this.mExpectedValues.size(); i + 1) {
                        float f = sensorEvent.values[i];
                        NumericRange numericRange = (NumericRange) this.mExpectedValues.get(i);
                        BigDecimal bigDecimal4 = numericRange.min_optional;
                        i = ((bigDecimal4 == null || f > bigDecimal4.floatValue()) && ((bigDecimal = numericRange.minInclusive_optional) == null || f >= bigDecimal.floatValue()) && (((bigDecimal2 = numericRange.max_optional) == null || f < bigDecimal2.floatValue()) && ((bigDecimal3 = numericRange.maxInclusive_optional) == null || f <= bigDecimal3.floatValue()))) ? i + 1 : 0;
                        return false;
                    }
                    return true;
                } finally {
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.policy.DeviceStateProviderImpl$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.policy.DeviceStateProviderImpl$$ExternalSyntheticLambda0] */
    static {
        final int i = 0;
        TRUE_BOOLEAN_SUPPLIER = new BooleanSupplier() { // from class: com.android.server.policy.DeviceStateProviderImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i) {
                    case 0:
                        DeviceStateProviderImpl$$ExternalSyntheticLambda0 deviceStateProviderImpl$$ExternalSyntheticLambda0 = DeviceStateProviderImpl.TRUE_BOOLEAN_SUPPLIER;
                        return true;
                    default:
                        DeviceStateProviderImpl$$ExternalSyntheticLambda0 deviceStateProviderImpl$$ExternalSyntheticLambda02 = DeviceStateProviderImpl.TRUE_BOOLEAN_SUPPLIER;
                        return false;
                }
            }
        };
        final int i2 = 1;
        FALSE_BOOLEAN_SUPPLIER = new BooleanSupplier() { // from class: com.android.server.policy.DeviceStateProviderImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                switch (i2) {
                    case 0:
                        DeviceStateProviderImpl$$ExternalSyntheticLambda0 deviceStateProviderImpl$$ExternalSyntheticLambda0 = DeviceStateProviderImpl.TRUE_BOOLEAN_SUPPLIER;
                        return true;
                    default:
                        DeviceStateProviderImpl$$ExternalSyntheticLambda0 deviceStateProviderImpl$$ExternalSyntheticLambda02 = DeviceStateProviderImpl.TRUE_BOOLEAN_SUPPLIER;
                        return false;
                }
            }
        };
    }

    public DeviceStateProviderImpl(Context context, List list, List list2) {
        ArrayList arrayList;
        boolean z;
        ArrayList arrayList2;
        ArrayList arrayList3;
        int i;
        DeviceStateProviderImpl$$ExternalSyntheticLambda0 deviceStateProviderImpl$$ExternalSyntheticLambda0;
        Sensor sensor;
        ArrayList arrayList4 = (ArrayList) list;
        ArrayList arrayList5 = (ArrayList) list2;
        Preconditions.checkArgument(arrayList4.size() == arrayList5.size(), "Number of device states must be equal to the number of device state conditions.");
        this.mContext = context;
        DeviceState[] deviceStateArr = (DeviceState[]) arrayList4.toArray(new DeviceState[arrayList4.size()]);
        Arrays.sort(deviceStateArr, Comparator.comparingInt(new DeviceStateProviderImpl$$ExternalSyntheticLambda2()));
        this.mOrderedStates = deviceStateArr;
        ArraySet arraySet = new ArraySet();
        int i2 = 0;
        boolean z2 = false;
        while (i2 < arrayList5.size()) {
            int identifier = ((DeviceState) arrayList4.get(i2)).getIdentifier();
            Conditions conditions = (Conditions) arrayList5.get(i2);
            DeviceStateProviderImpl$$ExternalSyntheticLambda0 deviceStateProviderImpl$$ExternalSyntheticLambda02 = TRUE_BOOLEAN_SUPPLIER;
            DeviceStateProviderImpl$$ExternalSyntheticLambda0 deviceStateProviderImpl$$ExternalSyntheticLambda03 = FALSE_BOOLEAN_SUPPLIER;
            if (conditions == null) {
                if (((DeviceState) arrayList4.get(i2)).hasProperty(10)) {
                    this.mStateConditions.put(identifier, deviceStateProviderImpl$$ExternalSyntheticLambda03);
                } else {
                    this.mStateConditions.put(identifier, deviceStateProviderImpl$$ExternalSyntheticLambda02);
                }
                arrayList3 = arrayList4;
                arrayList2 = arrayList5;
                i = i2;
            } else {
                ArraySet arraySet2 = new ArraySet();
                ArrayList arrayList6 = new ArrayList();
                LidSwitchCondition lidSwitchCondition = conditions.lidSwitch;
                if (lidSwitchCondition != null) {
                    Boolean bool = lidSwitchCondition.open;
                    arrayList6.add(new LidSwitchBooleanSupplier(bool == null ? false : bool.booleanValue()));
                    z = true;
                } else {
                    z = false;
                }
                if (conditions.sensor == null) {
                    conditions.sensor = new ArrayList();
                }
                List list3 = conditions.sensor;
                int i3 = 0;
                while (true) {
                    ArrayList arrayList7 = (ArrayList) list3;
                    arrayList2 = arrayList5;
                    if (i3 < arrayList7.size()) {
                        SensorCondition sensorCondition = (SensorCondition) arrayList7.get(i3);
                        String str = sensorCondition.type;
                        List list4 = list3;
                        String str2 = sensorCondition.name;
                        arrayList3 = arrayList4;
                        List<Sensor> sensorList = ((SensorManager) this.mContext.getSystemService(SensorManager.class)).getSensorList(-1);
                        i = i2;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= sensorList.size()) {
                                deviceStateProviderImpl$$ExternalSyntheticLambda0 = deviceStateProviderImpl$$ExternalSyntheticLambda02;
                                sensor = null;
                                break;
                            }
                            sensor = sensorList.get(i4);
                            List<Sensor> list5 = sensorList;
                            String stringType = sensor.getStringType();
                            deviceStateProviderImpl$$ExternalSyntheticLambda0 = deviceStateProviderImpl$$ExternalSyntheticLambda02;
                            String name = sensor.getName();
                            if (stringType != null && name != null && stringType.equals(str) && name.equals(str2)) {
                                break;
                            }
                            i4++;
                            sensorList = list5;
                            deviceStateProviderImpl$$ExternalSyntheticLambda02 = deviceStateProviderImpl$$ExternalSyntheticLambda0;
                        }
                        if (sensor == null) {
                            Slog.e("DeviceStateProviderImpl", "Failed to find Sensor with type: " + str + " and name: " + str2);
                            this.mStateConditions.put(identifier, deviceStateProviderImpl$$ExternalSyntheticLambda03);
                            break;
                        }
                        if (sensorCondition.value == null) {
                            sensorCondition.value = new ArrayList();
                        }
                        arrayList6.add(new SensorBooleanSupplier(sensor, sensorCondition.value));
                        arraySet2.add(sensor);
                        i3++;
                        arrayList5 = arrayList2;
                        list3 = list4;
                        arrayList4 = arrayList3;
                        i2 = i;
                        deviceStateProviderImpl$$ExternalSyntheticLambda02 = deviceStateProviderImpl$$ExternalSyntheticLambda0;
                    } else {
                        arrayList3 = arrayList4;
                        i = i2;
                        DeviceStateProviderImpl$$ExternalSyntheticLambda0 deviceStateProviderImpl$$ExternalSyntheticLambda04 = deviceStateProviderImpl$$ExternalSyntheticLambda02;
                        z2 |= z;
                        arraySet.addAll(arraySet2);
                        if (arrayList6.size() > 1) {
                            SparseArray sparseArray = this.mStateConditions;
                            AndBooleanSupplier andBooleanSupplier = new AndBooleanSupplier();
                            andBooleanSupplier.mBooleanSuppliers = arrayList6;
                            sparseArray.put(identifier, andBooleanSupplier);
                        } else if (arrayList6.size() > 0) {
                            this.mStateConditions.put(identifier, (BooleanSupplier) arrayList6.get(0));
                        } else {
                            this.mStateConditions.put(identifier, deviceStateProviderImpl$$ExternalSyntheticLambda04);
                        }
                    }
                }
            }
            i2 = i + 1;
            arrayList5 = arrayList2;
            arrayList4 = arrayList3;
        }
        ArrayList arrayList8 = arrayList4;
        if (z2) {
            InputManagerService.this.registerLidSwitchCallbackInternal(this);
        }
        SensorManager sensorManager = (SensorManager) this.mContext.getSystemService(SensorManager.class);
        for (int i5 = 0; i5 < arraySet.size(); i5++) {
            sensorManager.registerListener(this, (Sensor) arraySet.valueAt(i5), 0);
        }
        final PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        if (powerManager != null) {
            int i6 = 0;
            while (true) {
                if (i6 >= arrayList8.size()) {
                    arrayList = arrayList8;
                    break;
                }
                arrayList = arrayList8;
                if (((DeviceState) arrayList.get(i6)).hasProperty(6)) {
                    powerManager.addThermalStatusListener(this);
                    break;
                } else {
                    i6++;
                    arrayList8 = arrayList;
                }
            }
            for (int i7 = 0; i7 < arrayList.size(); i7++) {
                if (((DeviceState) arrayList.get(i7)).hasProperty(7)) {
                    this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.policy.DeviceStateProviderImpl.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context2, Intent intent) {
                            if ("android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL".equals(intent.getAction())) {
                                DeviceStateProviderImpl.this.onPowerSaveModeChanged(powerManager.isPowerSaveMode());
                            }
                        }
                    }, new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED_INTERNAL"));
                    return;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static DeviceStateProviderImpl createFromConfig(Context context, ReadableConfig readableConfig) {
        DeviceStateConfig deviceStateConfig;
        int i;
        int i2 = 16;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (readableConfig != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(((ReadableFileConfig) readableConfig).mFile);
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    try {
                        deviceStateConfig = XmlParser.read(bufferedInputStream);
                        bufferedInputStream.close();
                        fileInputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (IOException | DatatypeConfigurationException | XmlPullParserException e) {
                Slog.e("DeviceStateProviderImpl", "Encountered an error while reading device state config", e);
                deviceStateConfig = null;
            }
            if (deviceStateConfig != null) {
                if (deviceStateConfig.deviceState == null) {
                    deviceStateConfig.deviceState = new ArrayList();
                }
                Iterator it = ((ArrayList) deviceStateConfig.deviceState).iterator();
                while (it.hasNext()) {
                    com.android.server.policy.devicestate.config.DeviceState deviceState = (com.android.server.policy.devicestate.config.DeviceState) it.next();
                    int intValue = deviceState.identifier.intValue();
                    String str = deviceState.name;
                    if (str == null) {
                        str = "";
                    }
                    HashSet hashSet = new HashSet();
                    HashSet hashSet2 = new HashSet();
                    Properties properties = deviceState.properties;
                    if (properties != null) {
                        if (properties.property == null) {
                            properties.property = new ArrayList();
                        }
                        List list = properties.property;
                        int i3 = 0;
                        while (true) {
                            ArrayList arrayList3 = (ArrayList) list;
                            if (i3 < arrayList3.size()) {
                                String str2 = (String) arrayList3.get(i3);
                                str2.getClass();
                                switch (str2.hashCode()) {
                                    case -2049159887:
                                        if (str2.equals("com.android.server.policy.PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_CLOSED")) {
                                            i = 0;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case -1924910618:
                                        if (str2.equals("com.android.server.policy.PROPERTY_APP_INACCESSIBLE")) {
                                            i = 1;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case -1722863955:
                                        if (str2.equals("com.android.server.policy.PROPERTY_EMULATED_ONLY")) {
                                            i = 2;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case -1628585137:
                                        if (str2.equals("com.android.server.policy.PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_OPEN")) {
                                            i = 3;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case -1474991328:
                                        if (str2.equals("com.android.server.policy.PROPERTY_POWER_CONFIGURATION_TRIGGER_SLEEP")) {
                                            i = 4;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case -1119149719:
                                        if (str2.equals("com.android.server.policy.PROPERTY_POLICY_UNSUPPORTED_WHEN_THERMAL_STATUS_CRITICAL")) {
                                            i = 5;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case -296614396:
                                        if (str2.equals("com.android.server.policy.PROPERTY_POLICY_CANCEL_WHEN_REQUESTER_NOT_ON_TOP")) {
                                            i = 6;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case -184629335:
                                        if (str2.equals("com.android.server.policy.PROPERTY_FEATURE_DUAL_DISPLAY_INTERNAL_DEFAULT")) {
                                            i = 7;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case -47471589:
                                        if (str2.equals("com.android.server.policy.PROPERTY_POWER_CONFIGURATION_TRIGGER_WAKE")) {
                                            i = 8;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case 716062204:
                                        if (str2.equals("com.android.server.policy.PROPERTY_FOLDABLE_DISPLAY_CONFIGURATION_INNER_PRIMARY")) {
                                            i = 9;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case 989265137:
                                        if (str2.equals("com.android.server.policy.PROPERTY_FOLDABLE_HARDWARE_CONFIGURATION_FOLD_IN_HALF_OPEN")) {
                                            i = 10;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case 1003429125:
                                        if (str2.equals("com.android.server.policy.PROPERTY_POLICY_AVAILABLE_FOR_APP_REQUEST")) {
                                            i = 11;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case 1091897292:
                                        if (str2.equals("com.android.server.policy.PROPERTY_EXTENDED_DEVICE_STATE_EXTERNAL_DISPLAY")) {
                                            i = 12;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case 1178406448:
                                        if (str2.equals("com.android.server.policy.PROPERTY_POLICY_UNSUPPORTED_WHEN_POWER_SAVE_MODE")) {
                                            i = 13;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case 1533529885:
                                        if (str2.equals("com.android.server.policy.PROPERTY_FEATURE_REAR_DISPLAY")) {
                                            i = 14;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case 1755933010:
                                        if (str2.equals("com.android.server.policy.PROPERTY_POLICY_CANCEL_OVERRIDE_REQUESTS")) {
                                            i = 15;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    case 1962416929:
                                        if (str2.equals("com.android.server.policy.PROPERTY_FOLDABLE_DISPLAY_CONFIGURATION_OUTER_PRIMARY")) {
                                            i = i2;
                                            break;
                                        }
                                        i = -1;
                                        break;
                                    default:
                                        i = -1;
                                        break;
                                }
                                switch (i) {
                                    case 0:
                                        hashSet2.add(1);
                                        break;
                                    case 1:
                                        hashSet.add(9);
                                        break;
                                    case 2:
                                        hashSet.add(10);
                                        break;
                                    case 3:
                                        hashSet2.add(3);
                                        break;
                                    case 4:
                                        hashSet.add(13);
                                        break;
                                    case 5:
                                        hashSet.add(6);
                                        break;
                                    case 6:
                                        hashSet.add(5);
                                        break;
                                    case 7:
                                        hashSet.add(17);
                                        break;
                                    case 8:
                                        hashSet.add(14);
                                        break;
                                    case 9:
                                        hashSet.add(12);
                                        break;
                                    case 10:
                                        hashSet2.add(2);
                                        break;
                                    case 11:
                                        hashSet.add(8);
                                        break;
                                    case 12:
                                        hashSet.add(15);
                                        break;
                                    case 13:
                                        hashSet.add(7);
                                        break;
                                    case 14:
                                        hashSet.add(Integer.valueOf(i2));
                                        break;
                                    case 15:
                                        hashSet.add(4);
                                        break;
                                    case 16:
                                        hashSet.add(11);
                                        break;
                                    default:
                                        Slog.w("DeviceStateProviderImpl", "Parsed unknown flag with name: ".concat(str2));
                                        break;
                                }
                                i3++;
                                i2 = 16;
                            }
                        }
                    }
                    arrayList.add(new DeviceState(new DeviceState.Configuration.Builder(intValue, str).setSystemProperties(hashSet).setPhysicalProperties(hashSet2).build()));
                    arrayList2.add(deviceState.conditions);
                    i2 = 16;
                }
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(DEFAULT_DEVICE_STATE);
            arrayList2.add(null);
        }
        return new DeviceStateProviderImpl(context, arrayList, arrayList2);
    }

    public static String toSensorValueString(Sensor sensor, SensorEvent sensorEvent) {
        return AnyMotionDetector$$ExternalSyntheticOutline0.m(sensor == null ? "null" : sensor.getName(), " : ", sensorEvent != null ? Arrays.toString(sensorEvent.values) : "null");
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("DeviceStateProviderImpl");
        synchronized (this.mLock) {
            try {
                printWriter.println("  mLastReportedState = " + this.mLastReportedState);
                printWriter.println("  mPowerSaveModeEnabled = " + this.mPowerSaveModeEnabled);
                printWriter.println("  mThermalStatus = " + this.mThermalStatus);
                printWriter.println("  mIsLidOpen = " + this.mIsLidOpen);
                printWriter.println("  Sensor values:");
                for (Sensor sensor : ((ArrayMap) this.mLatestSensorEvent).keySet()) {
                    printWriter.println("   - " + toSensorValueString(sensor, (SensorEvent) ((ArrayMap) this.mLatestSensorEvent).get(sensor)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyDeviceStateChangedIfNeeded() {
        int i;
        synchronized (this.mLock) {
            try {
                if (this.mListener == null) {
                    return;
                }
                int i2 = 0;
                while (true) {
                    DeviceState[] deviceStateArr = this.mOrderedStates;
                    if (i2 >= deviceStateArr.length) {
                        i = -1;
                        break;
                    }
                    i = deviceStateArr[i2].getIdentifier();
                    if (((BooleanSupplier) this.mStateConditions.get(i)).getAsBoolean()) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i == -1) {
                    Slog.e("DeviceStateProviderImpl", "No declared device states match any of the required conditions.");
                    Slog.i("DeviceStateProviderImpl", "Sensor values:");
                    for (Sensor sensor : ((ArrayMap) this.mLatestSensorEvent).keySet()) {
                        Slog.i("DeviceStateProviderImpl", toSensorValueString(sensor, (SensorEvent) ((ArrayMap) this.mLatestSensorEvent).get(sensor)));
                    }
                }
                if (i == -1 || i == this.mLastReportedState) {
                    i = -1;
                } else {
                    this.mLastReportedState = i;
                }
                if (i != -1) {
                    DeviceStateManagerService.DeviceStateProviderListener deviceStateProviderListener = this.mListener;
                    deviceStateProviderListener.getClass();
                    if (i < 0 || i > 10000) {
                        throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid identifier: "));
                    }
                    deviceStateProviderListener.mCurrentBaseState = i;
                    DeviceStateManagerService.this.setBaseState(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.input.InputManagerInternal$LidSwitchCallback
    public final void notifyLidSwitchChanged(boolean z) {
        synchronized (this.mLock) {
            this.mIsLidOpen = Boolean.valueOf(z);
        }
        notifyDeviceStateChangedIfNeeded();
    }

    public final void notifySupportedStatesChanged(int i) {
        int i2;
        int i3;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                DeviceStateManagerService.DeviceStateProviderListener deviceStateProviderListener = this.mListener;
                if (deviceStateProviderListener == null) {
                    return;
                }
                int i4 = 0;
                for (DeviceState deviceState : this.mOrderedStates) {
                    int i5 = this.mThermalStatus;
                    i2 = ((i5 == 4 || i5 == 5 || i5 == 6) && deviceState.hasProperty(6)) ? i2 + 1 : 0;
                    if (!this.mPowerSaveModeEnabled || !deviceState.hasProperty(7)) {
                        arrayList.add(deviceState);
                    }
                }
                DeviceState[] deviceStateArr = (DeviceState[]) arrayList.toArray(new DeviceState[arrayList.size()]);
                if (deviceStateArr.length == 0) {
                    throw new IllegalArgumentException("Supported device states must not be empty");
                }
                DeviceStateManagerService deviceStateManagerService = DeviceStateManagerService.this;
                synchronized (deviceStateManagerService.mLock) {
                    try {
                        int[] supportedStateIdentifiersLocked = deviceStateManagerService.getSupportedStateIdentifiersLocked();
                        deviceStateManagerService.mDeviceStates.clear();
                        int i6 = 0;
                        boolean z = false;
                        while (true) {
                            i3 = 1;
                            if (i6 >= deviceStateArr.length) {
                                break;
                            }
                            DeviceState deviceState2 = deviceStateArr[i6];
                            if (deviceState2.hasProperty(4)) {
                                z = true;
                            }
                            deviceStateManagerService.mDeviceStates.put(deviceState2.getIdentifier(), deviceState2);
                            i6++;
                        }
                        OverrideRequestController overrideRequestController = deviceStateManagerService.mOverrideRequestController;
                        overrideRequestController.mStickyRequestsAllowed = z;
                        if (!z && overrideRequestController.mStickyRequest) {
                            overrideRequestController.cancelCurrentRequestLocked(0);
                        }
                        int[] supportedStateIdentifiersLocked2 = deviceStateManagerService.getSupportedStateIdentifiersLocked();
                        if (Arrays.equals(supportedStateIdentifiersLocked, supportedStateIdentifiersLocked2)) {
                            return;
                        }
                        OverrideRequestController overrideRequestController2 = deviceStateManagerService.mOverrideRequestController;
                        overrideRequestController2.getClass();
                        if (i != 3) {
                            i3 = 0;
                        }
                        int i7 = (i == 4 ? 2 : 0) | i3;
                        OverrideRequest overrideRequest = overrideRequestController2.mBaseStateRequest;
                        if (overrideRequest != null) {
                            int identifier = overrideRequest.mRequestedState.getIdentifier();
                            int i8 = 0;
                            while (true) {
                                if (i8 >= supportedStateIdentifiersLocked2.length) {
                                    overrideRequestController2.cancelCurrentBaseStateRequestLocked(i7);
                                    break;
                                } else if (supportedStateIdentifiersLocked2[i8] == identifier) {
                                    break;
                                } else {
                                    i8++;
                                }
                            }
                        }
                        OverrideRequest overrideRequest2 = overrideRequestController2.mRequest;
                        if (overrideRequest2 != null) {
                            int identifier2 = overrideRequest2.mRequestedState.getIdentifier();
                            while (true) {
                                if (i4 >= supportedStateIdentifiersLocked2.length) {
                                    overrideRequestController2.cancelCurrentRequestLocked(i7);
                                    break;
                                } else if (supportedStateIdentifiersLocked2[i4] != identifier2) {
                                    i4++;
                                }
                            }
                        }
                        deviceStateManagerService.updatePendingStateLocked();
                        int integer = deviceStateManagerService.getContext().getResources().getInteger(R.integer.config_emergency_call_wait_for_connection_timeout_millis);
                        if (integer != -1) {
                            deviceStateManagerService.mRearDisplayState = (DeviceState) deviceStateManagerService.mDeviceStates.get(integer);
                        }
                        deviceStateManagerService.notifyDeviceStateInfoChangedAsync();
                        deviceStateManagerService.mHandler.post(new DeviceStateManagerService$$ExternalSyntheticLambda6(deviceStateManagerService, 2));
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onPowerSaveModeChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mPowerSaveModeEnabled != z) {
                    this.mPowerSaveModeEnabled = z;
                    notifySupportedStatesChanged(z ? 4 : 5);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        synchronized (this.mLock) {
            ((ArrayMap) this.mLatestSensorEvent).put(sensorEvent.sensor, sensorEvent);
        }
        notifyDeviceStateChangedIfNeeded();
    }

    @Override // android.os.PowerManager.OnThermalStatusChangedListener
    public final void onThermalStatusChanged(int i) {
        int i2;
        synchronized (this.mLock) {
            i2 = this.mThermalStatus;
            this.mThermalStatus = i;
        }
        boolean z = true;
        boolean z2 = i == 4 || i == 5 || i == 6;
        if (i2 != 4 && i2 != 5 && i2 != 6) {
            z = false;
        }
        if (z2 != z) {
            Slog.i("DeviceStateProviderImpl", "Updating supported device states due to thermal status change. isThermalStatusCriticalOrAbove: " + z2);
            notifySupportedStatesChanged(z2 ? 3 : 2);
        }
    }
}
