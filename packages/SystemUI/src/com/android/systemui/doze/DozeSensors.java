package com.android.systemui.doze;

import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.SensorManagerPlugin;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.ThresholdSensor;
import com.android.systemui.util.sensors.ThresholdSensorEvent;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class DozeSensors {
    public static final UiEventLogger UI_EVENT_LOGGER = new UiEventLoggerImpl();
    public final AuthController mAuthController;
    public final AnonymousClass2 mAuthControllerCallback;
    public final AmbientDisplayConfiguration mConfig;
    public long mDebounceFrom;
    public int mDevicePosture;
    public final DozeSensors$$ExternalSyntheticLambda0 mDevicePostureCallback;
    public final DevicePostureController mDevicePostureController;
    public final DozeLog mDozeLog;
    public final Handler mHandler;
    public boolean mListening;
    public boolean mListeningAodOnlySensors;
    public boolean mListeningProxSensors;
    public boolean mListeningTouchScreenSensors;
    public final Consumer mProxCallback;
    public final ProximitySensor mProximitySensor;
    public final boolean mScreenOffUdfpsEnabled;
    public final SecureSettings mSecureSettings;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final boolean mSelectivelyRegisterProxSensors;
    public final Callback mSensorCallback;
    public final AsyncSensorManager mSensorManager;
    public boolean mSettingRegistered;
    public final AnonymousClass1 mSettingsObserver;
    protected TriggerSensor[] mTriggerSensors;
    public boolean mUdfpsEnrolled;
    public final WakeLock mWakeLock;

    public interface Callback {
    }

    public enum DozeSensorsUiEvent implements UiEventLogger.UiEventEnum {
        ACTION_AMBIENT_GESTURE_PICKUP(459);

        private final int mId;

        DozeSensorsUiEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    class PluginSensor extends TriggerSensor implements SensorManagerPlugin.SensorEventListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final long mDebounce;
        public final SensorManagerPlugin.Sensor mPluginSensor;

        public PluginSensor(DozeSensors dozeSensors, SensorManagerPlugin.Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3) {
            this(sensor, str, z, i, z2, z3, 0L);
        }

        @Override // com.android.systemui.plugins.SensorManagerPlugin.SensorEventListener
        public final void onSensorChanged(final SensorManagerPlugin.SensorEvent sensorEvent) {
            DozeLog dozeLog = DozeSensors.this.mDozeLog;
            int i = this.mPulseReason;
            DozeLogger dozeLogger = dozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(5);
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).int1 = i;
            logBuffer.commit(logMessageObtain);
            DozeSensors dozeSensors = DozeSensors.this;
            dozeSensors.mHandler.post(dozeSensors.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeSensors$PluginSensor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DozeSensors.PluginSensor pluginSensor = this.f$0;
                    SensorManagerPlugin.SensorEvent sensorEvent2 = sensorEvent;
                    int i2 = DozeSensors.PluginSensor.$r8$clinit;
                    long jUptimeMillis = SystemClock.uptimeMillis();
                    DozeSensors dozeSensors2 = DozeSensors.this;
                    if (jUptimeMillis < dozeSensors2.mDebounceFrom + pluginSensor.mDebounce) {
                        dozeSensors2.mDozeLog.traceSensorEventDropped(pluginSensor.mPulseReason, "debounce");
                    } else {
                        ((DozeTriggers$$ExternalSyntheticLambda2) dozeSensors2.mSensorCallback).f$0.onSensor(pluginSensor.mPulseReason, -1.0f, -1.0f, sensorEvent2.getValues());
                    }
                }
            }));
        }

        @Override // com.android.systemui.doze.DozeSensors.TriggerSensor
        public final String toString() {
            return "{mRegistered=" + this.mRegistered + ", mRequested=" + this.mRequested + ", mDisabled=false, mConfigured=" + this.mConfigured + ", mIgnoresSetting=" + this.mIgnoresSetting + ", mSensor=" + this.mPluginSensor + "}";
        }

        @Override // com.android.systemui.doze.DozeSensors.TriggerSensor
        public final void updateListening() {
            if (this.mConfigured) {
                AsyncSensorManager asyncSensorManager = DozeSensors.this.mSensorManager;
                if (this.mRequested && ((enabledBySetting() || this.mIgnoresSetting) && !this.mRegistered)) {
                    asyncSensorManager.registerPluginListener(this.mPluginSensor, this);
                    this.mRegistered = true;
                    DozeLogger dozeLogger = DozeSensors.this.mDozeLog.mLogger;
                    dozeLogger.getClass();
                    LogBuffer.log$default(dozeLogger.buffer, "DozeLog", LogLevel.DEBUG, "register plugin sensor");
                    return;
                }
                if (this.mRegistered) {
                    asyncSensorManager.unregisterPluginListener(this.mPluginSensor, this);
                    this.mRegistered = false;
                    DozeLogger dozeLogger2 = DozeSensors.this.mDozeLog.mLogger;
                    dozeLogger2.getClass();
                    LogBuffer.log$default(dozeLogger2.buffer, "DozeLog", LogLevel.DEBUG, "unregister plugin sensor");
                }
            }
        }

        public PluginSensor(SensorManagerPlugin.Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3, long j) {
            super(DozeSensors.this, null, str, z, i, z2, z3);
            this.mPluginSensor = sensor;
            this.mDebounce = j;
        }
    }

    class TriggerSensor extends TriggerEventListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mConfigured;
        public final boolean mIgnoresSetting;
        public final boolean mImmediatelyReRegister;
        public int mPosture;
        public final int mPulseReason;
        public boolean mRegistered;
        public final boolean mReportsTouchCoordinates;
        public boolean mRequested;
        public final boolean mRequiresAod;
        public final boolean mRequiresProx;
        public final boolean mRequiresTouchscreen;
        public final Sensor[] mSensors;
        public final String mSetting;
        public final boolean mSettingDefault;

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3) {
            this(dozeSensors, sensor, str, true, z, i, z2, z3, false, false, true, false);
        }

        public final boolean enabledBySetting() {
            DozeSensors dozeSensors = DozeSensors.this;
            if (!dozeSensors.mConfig.enabled(dozeSensors.mSelectedUserInteractor.getSelectedUserId())) {
                return false;
            }
            if (TextUtils.isEmpty(this.mSetting)) {
                return true;
            }
            DozeSensors dozeSensors2 = DozeSensors.this;
            return dozeSensors2.mSecureSettings.getIntForUser(this.mSetting, this.mSettingDefault ? 1 : 0, dozeSensors2.mSelectedUserInteractor.getSelectedUserId()) != 0;
        }

        @Override // android.hardware.TriggerEventListener
        public final void onTrigger(final TriggerEvent triggerEvent) {
            final Sensor sensor = this.mSensors[this.mPosture];
            DozeLog dozeLog = DozeSensors.this.mDozeLog;
            int i = this.mPulseReason;
            DozeLogger dozeLogger = dozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(5);
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).int1 = i;
            logBuffer.commit(logMessageObtain);
            DozeSensors dozeSensors = DozeSensors.this;
            dozeSensors.mHandler.post(dozeSensors.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeSensors$TriggerSensor$$ExternalSyntheticLambda0
                /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    float f;
                    float f2;
                    DozeSensors.TriggerSensor triggerSensor = this.f$0;
                    Sensor sensor2 = sensor;
                    TriggerEvent triggerEvent2 = triggerEvent;
                    if (sensor2 != null) {
                        int i2 = DozeSensors.TriggerSensor.$r8$clinit;
                        if (sensor2.getType() == 25) {
                            DozeSensors.UI_EVENT_LOGGER.log(DozeSensors.DozeSensorsUiEvent.ACTION_AMBIENT_GESTURE_PICKUP);
                        }
                    }
                    triggerSensor.mRegistered = false;
                    if (triggerSensor.mReportsTouchCoordinates) {
                        float[] fArr = triggerEvent2.values;
                        if (fArr.length >= 2) {
                            f = fArr[0];
                            f2 = fArr[1];
                        } else {
                            f = -1.0f;
                            f2 = -1.0f;
                        }
                    }
                    ((DozeTriggers$$ExternalSyntheticLambda2) DozeSensors.this.mSensorCallback).f$0.onSensor(triggerSensor.mPulseReason, f, f2, triggerEvent2.values);
                    if (triggerSensor.mRegistered || !triggerSensor.mImmediatelyReRegister) {
                        return;
                    }
                    triggerSensor.updateListening();
                }
            }));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{mRegistered=");
            sb.append(this.mRegistered);
            sb.append(", mRequested=");
            sb.append(this.mRequested);
            sb.append(", mDisabled=false, mConfigured=");
            sb.append(this.mConfigured);
            sb.append(", mIgnoresSetting=");
            sb.append(this.mIgnoresSetting);
            sb.append(", mSensors=");
            sb.append(Arrays.toString(this.mSensors));
            if (this.mSensors.length > 2) {
                sb.append(", mPosture=");
                sb.append(DevicePostureController.devicePostureToString(DozeSensors.this.mDevicePosture));
            }
            sb.append("}");
            return sb.toString();
        }

        public void updateListening() {
            Sensor sensor = this.mSensors[this.mPosture];
            if (!this.mConfigured || sensor == null) {
                return;
            }
            if (!this.mRequested || (!enabledBySetting() && !this.mIgnoresSetting)) {
                if (this.mRegistered) {
                    boolean zCancelTriggerSensor = DozeSensors.this.mSensorManager.cancelTriggerSensor(this, sensor);
                    DozeLog dozeLog = DozeSensors.this.mDozeLog;
                    String string = sensor.toString();
                    DozeLogger dozeLogger = dozeLog.mLogger;
                    dozeLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(17);
                    LogBuffer logBuffer = dozeLogger.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                    logMessageImpl.str1 = string;
                    logMessageImpl.bool1 = zCancelTriggerSensor;
                    logBuffer.commit(logMessageObtain);
                    this.mRegistered = false;
                    return;
                }
                return;
            }
            if (this.mRegistered) {
                DozeLog dozeLog2 = DozeSensors.this.mDozeLog;
                String string2 = sensor.toString();
                DozeLogger dozeLogger2 = dozeLog2.mLogger;
                dozeLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda02 = new DozeLogger$$ExternalSyntheticLambda0(25);
                LogBuffer logBuffer2 = dozeLogger2.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("DozeLog", logLevel2, dozeLogger$$ExternalSyntheticLambda02, null);
                ((LogMessageImpl) logMessageObtain2).str1 = string2;
                logBuffer2.commit(logMessageObtain2);
                return;
            }
            this.mRegistered = DozeSensors.this.mSensorManager.requestTriggerSensor(this, sensor);
            DozeLog dozeLog3 = DozeSensors.this.mDozeLog;
            String string3 = sensor.toString();
            boolean z = this.mRegistered;
            DozeLogger dozeLogger3 = dozeLog3.mLogger;
            dozeLogger3.getClass();
            LogLevel logLevel3 = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda03 = new DozeLogger$$ExternalSyntheticLambda0(24);
            LogBuffer logBuffer3 = dozeLogger3.buffer;
            LogMessage logMessageObtain3 = logBuffer3.obtain("DozeLog", logLevel3, dozeLogger$$ExternalSyntheticLambda03, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
            logMessageImpl2.str1 = string3;
            logMessageImpl2.bool1 = z;
            logBuffer3.commit(logMessageObtain3);
        }

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String str, boolean z, boolean z2, int i, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
            this(new Sensor[]{sensor}, str, z, z2, i, z3, z4, z5, z6, z7, 0, z8);
        }

        public TriggerSensor(Sensor[] sensorArr, String str, boolean z, boolean z2, int i, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i2, boolean z8) {
            this.mSensors = sensorArr;
            this.mSetting = str;
            this.mSettingDefault = z;
            this.mConfigured = z2;
            this.mPulseReason = i;
            this.mReportsTouchCoordinates = z3;
            this.mRequiresTouchscreen = z4;
            this.mIgnoresSetting = z5;
            this.mRequiresProx = z6;
            this.mRequiresAod = z8;
            this.mPosture = i2;
            this.mImmediatelyReRegister = z7;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.biometrics.AuthController$Callback, com.android.systemui.doze.DozeSensors$2] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.doze.DozeSensors$1] */
    public DozeSensors(Resources resources, AsyncSensorManager asyncSensorManager, DozeParameters dozeParameters, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, Callback callback, Consumer<Boolean> consumer, DozeLog dozeLog, ProximitySensor proximitySensor, SecureSettings secureSettings, AuthController authController, DevicePostureController devicePostureController, SelectedUserInteractor selectedUserInteractor) throws Resources.NotFoundException {
        boolean z;
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mSettingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.doze.DozeSensors.1
            public final void onChange(boolean z2, Collection collection, int i, int i2) {
                if (i2 != DozeSensors.this.mSelectedUserInteractor.getSelectedUserId()) {
                    return;
                }
                for (TriggerSensor triggerSensor : DozeSensors.this.mTriggerSensors) {
                    triggerSensor.updateListening();
                }
            }
        };
        this.mDevicePostureCallback = new DevicePostureController.Callback() { // from class: com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                DozeSensors dozeSensors = this.f$0;
                if (dozeSensors.mDevicePosture == i) {
                    return;
                }
                dozeSensors.mDevicePosture = i;
                for (DozeSensors.TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
                    int i2 = dozeSensors.mDevicePosture;
                    int i3 = triggerSensor.mPosture;
                    if (i3 != i2) {
                        Sensor[] sensorArr = triggerSensor.mSensors;
                        if (sensorArr.length >= 2 && i2 < sensorArr.length) {
                            Sensor sensor = sensorArr[i3];
                            Sensor sensor2 = sensorArr[i2];
                            if (Objects.equals(sensor, sensor2)) {
                                triggerSensor.mPosture = i2;
                            } else {
                                if (triggerSensor.mRegistered) {
                                    boolean zCancelTriggerSensor = DozeSensors.this.mSensorManager.cancelTriggerSensor(triggerSensor, sensor);
                                    DozeLog dozeLog2 = DozeSensors.this.mDozeLog;
                                    String string = sensor.toString();
                                    DozeLogger dozeLogger = dozeLog2.mLogger;
                                    dozeLogger.getClass();
                                    LogLevel logLevel = LogLevel.INFO;
                                    DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda3 = new DozeLogger$$ExternalSyntheticLambda3(2);
                                    LogBuffer logBuffer = dozeLogger.buffer;
                                    LogMessage logMessageObtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda3, null);
                                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                                    logMessageImpl.str1 = string;
                                    logMessageImpl.bool1 = zCancelTriggerSensor;
                                    logMessageImpl.str2 = "posture changed";
                                    logBuffer.commit(logMessageObtain);
                                    triggerSensor.mRegistered = false;
                                }
                                triggerSensor.mPosture = i2;
                                triggerSensor.updateListening();
                                DozeSensors.this.mDozeLog.tracePostureChanged(triggerSensor.mPosture, "DozeSensors swap {" + sensor + "} => {" + sensor2 + "}, mRegistered=" + triggerSensor.mRegistered);
                            }
                        }
                    }
                }
            }
        };
        ?? r4 = new AuthController.Callback() { // from class: com.android.systemui.doze.DozeSensors.2
            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onAllAuthenticatorsRegistered(int i) {
                if (i == 2) {
                    updateUdfpsEnrolled();
                }
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public final void onEnrollmentsChanged(int i) {
                if (i == 2) {
                    updateUdfpsEnrolled();
                }
            }

            public final void updateUdfpsEnrolled() {
                DozeSensors dozeSensors = DozeSensors.this;
                AuthController authController2 = dozeSensors.mAuthController;
                SelectedUserInteractor selectedUserInteractor2 = dozeSensors.mSelectedUserInteractor;
                dozeSensors.mUdfpsEnrolled = authController2.isUdfpsEnrolled(selectedUserInteractor2.getSelectedUserId());
                for (TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
                    int i = triggerSensor.mPulseReason;
                    if (11 == i) {
                        z = dozeSensors.mUdfpsEnrolled && dozeSensors.mConfig.quickPickupSensorEnabled(selectedUserInteractor2.getSelectedUserId());
                        if (triggerSensor.mConfigured != z) {
                            triggerSensor.mConfigured = z;
                            triggerSensor.updateListening();
                        }
                    } else if (10 == i) {
                        if (!dozeSensors.mUdfpsEnrolled || (!dozeSensors.mConfig.alwaysOnEnabled(selectedUserInteractor2.getSelectedUserId()) && !dozeSensors.mScreenOffUdfpsEnabled)) {
                            z = false;
                        }
                        if (triggerSensor.mConfigured != z) {
                            triggerSensor.mConfigured = z;
                            triggerSensor.updateListening();
                        }
                    }
                }
            }
        };
        this.mAuthControllerCallback = r4;
        this.mSensorManager = asyncSensorManager;
        this.mConfig = ambientDisplayConfiguration;
        this.mWakeLock = wakeLock;
        this.mProxCallback = consumer;
        this.mSecureSettings = secureSettings;
        this.mSensorCallback = callback;
        this.mDozeLog = dozeLog;
        this.mProximitySensor = proximitySensor;
        proximitySensor.setTag("DozeSensors");
        boolean z2 = SystemProperties.getBoolean("doze.prox.selectively_register", dozeParameters.mResources.getBoolean(R.bool.doze_selectively_register_prox));
        this.mSelectivelyRegisterProxSensors = z2;
        this.mListeningProxSensors = !z2;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mScreenOffUdfpsEnabled = ambientDisplayConfiguration.screenOffUdfpsEnabled(selectedUserInteractor.getSelectedUserId());
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = ((DevicePostureControllerImpl) devicePostureController).getDevicePosture();
        this.mAuthController = authController;
        this.mUdfpsEnrolled = authController.isUdfpsEnrolled(selectedUserInteractor.getSelectedUserId());
        authController.addCallback(r4);
        TriggerSensor triggerSensor = new TriggerSensor(this, asyncSensorManager.getDefaultSensor(17), null, SystemProperties.getBoolean("doze.pulse.sigmotion", dozeParameters.mResources.getBoolean(R.bool.doze_pulse_on_significant_motion)), 2, false, false);
        TriggerSensor triggerSensor2 = new TriggerSensor(this, asyncSensorManager.getDefaultSensor(25), "doze_pulse_on_pick_up", resources.getBoolean(android.R.bool.config_eap_sim_based_auth_supported), ambientDisplayConfiguration.dozePickupSensorAvailable(), 3, false, false, false, false, true, false);
        TriggerSensor triggerSensor3 = new TriggerSensor(this, findSensor(asyncSensorManager, ambientDisplayConfiguration.doubleTapSensorType(), null), "doze_pulse_on_double_tap", true, 4, dozeParameters.mResources.getBoolean(R.bool.doze_double_tap_reports_touch_coordinates), true);
        String[] strArrTapSensorTypeMapping = ambientDisplayConfiguration.tapSensorTypeMapping();
        Sensor[] sensorArr = new Sensor[5];
        HashMap map = new HashMap();
        for (int i = 0; i < strArrTapSensorTypeMapping.length; i++) {
            String str = strArrTapSensorTypeMapping[i];
            if (!map.containsKey(str)) {
                map.put(str, findSensor(this.mSensorManager, str, null));
            }
            sensorArr[i] = (Sensor) map.get(str);
        }
        int i2 = this.mDevicePosture;
        int[] intArray = dozeParameters.mResources.getIntArray(R.array.doze_single_tap_uses_prox_posture_mapping);
        boolean z3 = dozeParameters.mResources.getBoolean(R.bool.doze_single_tap_uses_prox);
        if (i2 < intArray.length) {
            z = intArray[i2] != 0;
        } else {
            ClockEventController$$ExternalSyntheticOutline0.m(i2, "Unsupported doze posture ", "DozeParameters");
            z = z3;
        }
        this.mTriggerSensors = new TriggerSensor[]{triggerSensor, triggerSensor2, triggerSensor3, new TriggerSensor(sensorArr, "doze_tap_gesture", true, true, 9, true, true, false, z, true, this.mDevicePosture, false), new TriggerSensor(this, findSensor(this.mSensorManager, ambientDisplayConfiguration.longPressSensorType(), null), "doze_pulse_on_long_press", false, true, 5, true, true, false, dozeParameters.mResources.getBoolean(R.bool.doze_long_press_uses_prox), true, false), new TriggerSensor(this, findSensor(this.mSensorManager, ambientDisplayConfiguration.udfpsLongPressSensorType(), null), "doze_pulse_on_auth", true, this.mUdfpsEnrolled && (this.mConfig.alwaysOnEnabled(this.mSelectedUserInteractor.getSelectedUserId()) || this.mScreenOffUdfpsEnabled), 10, true, true, false, dozeParameters.mResources.getBoolean(R.bool.doze_long_press_uses_prox), true, false), new PluginSensor(this, new SensorManagerPlugin.Sensor(2), "doze_wake_display_gesture", this.mConfig.wakeScreenGestureAvailable() && this.mConfig.alwaysOnEnabled(this.mSelectedUserInteractor.getSelectedUserId()), 7, false, false), new PluginSensor(new SensorManagerPlugin.Sensor(1), "doze_wake_screen_gesture", this.mConfig.wakeScreenGestureAvailable(), 8, false, false, this.mConfig.getWakeLockScreenDebounce()), new TriggerSensor(this, findSensor(this.mSensorManager, ambientDisplayConfiguration.quickPickupSensorType(), null), "doze_quick_pickup_gesture", true, this.mUdfpsEnrolled && this.mConfig.quickPickupSensorEnabled(this.mSelectedUserInteractor.getSelectedUserId()), 11, false, false, false, false, true, false)};
        setProxListening(false);
        this.mProximitySensor.register(new ThresholdSensor.Listener() { // from class: com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
            public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
                DozeSensors dozeSensors = this.f$0;
                if (thresholdSensorEvent != null) {
                    dozeSensors.mProxCallback.accept(Boolean.valueOf(!thresholdSensorEvent.getBelow()));
                } else {
                    UiEventLogger uiEventLogger = DozeSensors.UI_EVENT_LOGGER;
                    dozeSensors.getClass();
                }
            }
        });
        ((DevicePostureControllerImpl) this.mDevicePostureController).addCallback(this.mDevicePostureCallback);
    }

    public static Sensor findSensor(SensorManager sensorManager, String str, String str2) {
        boolean zIsEmpty = TextUtils.isEmpty(str2);
        boolean zIsEmpty2 = TextUtils.isEmpty(str);
        if (zIsEmpty && zIsEmpty2) {
            return null;
        }
        for (Sensor sensor : sensorManager.getSensorList(-1)) {
            if (zIsEmpty || str2.equals(sensor.getName())) {
                if (zIsEmpty2 || str.equals(sensor.getStringType())) {
                    return sensor;
                }
            }
        }
        return null;
    }

    public final void setProxListening(boolean z) {
        ProximitySensor proximitySensor = this.mProximitySensor;
        if (proximitySensor.isRegistered() && z) {
            proximitySensor.alertListeners();
        } else if (z) {
            proximitySensor.resume();
        } else {
            proximitySensor.pause();
        }
    }

    public final void updateListening() {
        boolean z = false;
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            boolean z2 = this.mListening && (!triggerSensor.mRequiresTouchscreen || this.mListeningTouchScreenSensors) && ((!triggerSensor.mRequiresProx || this.mListeningProxSensors) && (!triggerSensor.mRequiresAod || this.mListeningAodOnlySensors));
            if (!this.mListeningAodOnlySensors && "doze_pulse_on_auth".equals(triggerSensor.mSetting)) {
                AmbientDisplayConfiguration ambientDisplayConfiguration = this.mConfig;
                SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
                if (ambientDisplayConfiguration.alwaysOnEnabled(selectedUserInteractor.getSelectedUserId()) && !this.mConfig.screenOffUdfpsEnabled(selectedUserInteractor.getSelectedUserId())) {
                    z2 = false;
                }
            }
            if (triggerSensor.mRequested != z2) {
                triggerSensor.mRequested = z2;
                triggerSensor.updateListening();
            }
            if (z2) {
                z = true;
            }
        }
        if (!z) {
            this.mSecureSettings.unregisterContentObserverAsync(this.mSettingsObserver);
        } else if (!this.mSettingRegistered) {
            for (TriggerSensor triggerSensor2 : this.mTriggerSensors) {
                if (triggerSensor2.mConfigured && !TextUtils.isEmpty(triggerSensor2.mSetting)) {
                    DozeSensors dozeSensors = DozeSensors.this;
                    dozeSensors.mSecureSettings.registerContentObserverForUserAsync(triggerSensor2.mSetting, dozeSensors.mSettingsObserver, -1);
                }
            }
        }
        this.mSettingRegistered = z;
    }
}
