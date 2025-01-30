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
import android.util.Log;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.SensorManagerPlugin;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.ProximitySensorImpl;
import com.android.systemui.util.sensors.ThresholdSensor;
import com.android.systemui.util.sensors.ThresholdSensorEvent;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeSensors {
    public static final UiEventLogger UI_EVENT_LOGGER = new UiEventLoggerImpl();
    public final AuthController mAuthController;
    public final C12462 mAuthControllerCallback;
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
    public final boolean mSelectivelyRegisterProxSensors;
    public final Callback mSensorCallback;
    public final AsyncSensorManager mSensorManager;
    public boolean mSettingRegistered;
    public final C12451 mSettingsObserver;
    protected TriggerSensor[] mTriggerSensors;
    public boolean mUdfpsEnrolled;
    public final UserTracker mUserTracker;
    public final WakeLock mWakeLock;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            DozeLogger$logSensorTriggered$2 dozeLogger$logSensorTriggered$2 = DozeLogger$logSensorTriggered$2.INSTANCE;
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logSensorTriggered$2, null);
            obtain.setInt1(i);
            logBuffer.commit(obtain);
            DozeSensors dozeSensors = DozeSensors.this;
            dozeSensors.mHandler.post(dozeSensors.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeSensors$PluginSensor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DozeSensors.PluginSensor pluginSensor = DozeSensors.PluginSensor.this;
                    SensorManagerPlugin.SensorEvent sensorEvent2 = sensorEvent;
                    int i2 = DozeSensors.PluginSensor.$r8$clinit;
                    pluginSensor.getClass();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    DozeSensors dozeSensors2 = DozeSensors.this;
                    if (uptimeMillis < dozeSensors2.mDebounceFrom + pluginSensor.mDebounce) {
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
                final AsyncSensorManager asyncSensorManager = DozeSensors.this.mSensorManager;
                final int i = 0;
                final int i2 = 1;
                if (!this.mRequested || (!(enabledBySetting() || this.mIgnoresSetting) || this.mRegistered)) {
                    if (this.mRegistered) {
                        final SensorManagerPlugin.Sensor sensor = this.mPluginSensor;
                        asyncSensorManager.mExecutor.execute(new Runnable() { // from class: com.android.systemui.util.sensors.AsyncSensorManager$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i3 = 0;
                                switch (i2) {
                                    case 0:
                                        AsyncSensorManager asyncSensorManager2 = asyncSensorManager;
                                        SensorManagerPlugin.Sensor sensor2 = sensor;
                                        SensorManagerPlugin.SensorEventListener sensorEventListener = this;
                                        while (i3 < ((ArrayList) asyncSensorManager2.mPlugins).size()) {
                                            ((SensorManagerPlugin) ((ArrayList) asyncSensorManager2.mPlugins).get(i3)).registerListener(sensor2, sensorEventListener);
                                            i3++;
                                        }
                                        break;
                                    default:
                                        AsyncSensorManager asyncSensorManager3 = asyncSensorManager;
                                        SensorManagerPlugin.Sensor sensor3 = sensor;
                                        SensorManagerPlugin.SensorEventListener sensorEventListener2 = this;
                                        while (i3 < ((ArrayList) asyncSensorManager3.mPlugins).size()) {
                                            ((SensorManagerPlugin) ((ArrayList) asyncSensorManager3.mPlugins).get(i3)).unregisterListener(sensor3, sensorEventListener2);
                                            i3++;
                                        }
                                        break;
                                }
                            }
                        });
                        this.mRegistered = false;
                        LogBuffer.log$default(DozeSensors.this.mDozeLog.mLogger.buffer, "DozeLog", LogLevel.DEBUG, "unregister plugin sensor", null, 8, null);
                        return;
                    }
                    return;
                }
                final SensorManagerPlugin.Sensor sensor2 = this.mPluginSensor;
                if (((ArrayList) asyncSensorManager.mPlugins).isEmpty()) {
                    Log.w("AsyncSensorManager", "No plugins registered");
                } else {
                    asyncSensorManager.mExecutor.execute(new Runnable() { // from class: com.android.systemui.util.sensors.AsyncSensorManager$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i3 = 0;
                            switch (i) {
                                case 0:
                                    AsyncSensorManager asyncSensorManager2 = asyncSensorManager;
                                    SensorManagerPlugin.Sensor sensor22 = sensor2;
                                    SensorManagerPlugin.SensorEventListener sensorEventListener = this;
                                    while (i3 < ((ArrayList) asyncSensorManager2.mPlugins).size()) {
                                        ((SensorManagerPlugin) ((ArrayList) asyncSensorManager2.mPlugins).get(i3)).registerListener(sensor22, sensorEventListener);
                                        i3++;
                                    }
                                    break;
                                default:
                                    AsyncSensorManager asyncSensorManager3 = asyncSensorManager;
                                    SensorManagerPlugin.Sensor sensor3 = sensor2;
                                    SensorManagerPlugin.SensorEventListener sensorEventListener2 = this;
                                    while (i3 < ((ArrayList) asyncSensorManager3.mPlugins).size()) {
                                        ((SensorManagerPlugin) ((ArrayList) asyncSensorManager3.mPlugins).get(i3)).unregisterListener(sensor3, sensorEventListener2);
                                        i3++;
                                    }
                                    break;
                            }
                        }
                    });
                }
                this.mRegistered = true;
                LogBuffer.log$default(DozeSensors.this.mDozeLog.mLogger.buffer, "DozeLog", LogLevel.DEBUG, "register plugin sensor", null, 8, null);
            }
        }

        public PluginSensor(SensorManagerPlugin.Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3, long j) {
            super(DozeSensors.this, null, str, z, i, z2, z3);
            this.mPluginSensor = sensor;
            this.mDebounce = j;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            if (!dozeSensors.mConfig.enabled(((UserTrackerImpl) dozeSensors.mUserTracker).getUserId())) {
                return false;
            }
            if (TextUtils.isEmpty(this.mSetting)) {
                return true;
            }
            DozeSensors dozeSensors2 = DozeSensors.this;
            return dozeSensors2.mSecureSettings.getIntForUser(this.mSettingDefault ? 1 : 0, ((UserTrackerImpl) dozeSensors2.mUserTracker).getUserId(), this.mSetting) != 0;
        }

        @Override // android.hardware.TriggerEventListener
        public final void onTrigger(final TriggerEvent triggerEvent) {
            final Sensor sensor = this.mSensors[this.mPosture];
            DozeLog dozeLog = DozeSensors.this.mDozeLog;
            int i = this.mPulseReason;
            DozeLogger dozeLogger = dozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            DozeLogger$logSensorTriggered$2 dozeLogger$logSensorTriggered$2 = DozeLogger$logSensorTriggered$2.INSTANCE;
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logSensorTriggered$2, null);
            obtain.setInt1(i);
            logBuffer.commit(obtain);
            DozeSensors dozeSensors = DozeSensors.this;
            dozeSensors.mHandler.post(dozeSensors.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeSensors$TriggerSensor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    float f;
                    float f2;
                    DozeSensors.TriggerSensor triggerSensor = DozeSensors.TriggerSensor.this;
                    Sensor sensor2 = sensor;
                    TriggerEvent triggerEvent2 = triggerEvent;
                    if (sensor2 != null) {
                        int i2 = DozeSensors.TriggerSensor.$r8$clinit;
                        triggerSensor.getClass();
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
                            ((DozeTriggers$$ExternalSyntheticLambda2) DozeSensors.this.mSensorCallback).f$0.onSensor(triggerSensor.mPulseReason, f, f2, triggerEvent2.values);
                            if (triggerSensor.mRegistered && triggerSensor.mImmediatelyReRegister) {
                                triggerSensor.updateListening();
                                return;
                            }
                        }
                    }
                    f = -1.0f;
                    f2 = -1.0f;
                    ((DozeTriggers$$ExternalSyntheticLambda2) DozeSensors.this.mSensorCallback).f$0.onSensor(triggerSensor.mPulseReason, f, f2, triggerEvent2.values);
                    if (triggerSensor.mRegistered) {
                    }
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
                    boolean cancelTriggerSensor = DozeSensors.this.mSensorManager.cancelTriggerSensor(this, sensor);
                    DozeLog dozeLog = DozeSensors.this.mDozeLog;
                    String sensor2 = sensor.toString();
                    DozeLogger dozeLogger = dozeLog.mLogger;
                    dozeLogger.getClass();
                    LogLevel logLevel = LogLevel.INFO;
                    DozeLogger$logSensorUnregisterAttempt$2 dozeLogger$logSensorUnregisterAttempt$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logSensorUnregisterAttempt$2
                        @Override // kotlin.jvm.functions.Function1
                        public final Object invoke(Object obj) {
                            LogMessage logMessage = (LogMessage) obj;
                            return "Unregister sensor. Success=" + logMessage.getBool1() + " sensor=" + logMessage.getStr1();
                        }
                    };
                    LogBuffer logBuffer = dozeLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logSensorUnregisterAttempt$2, null);
                    obtain.setStr1(sensor2);
                    obtain.setBool1(cancelTriggerSensor);
                    logBuffer.commit(obtain);
                    this.mRegistered = false;
                    return;
                }
                return;
            }
            if (this.mRegistered) {
                DozeLog dozeLog2 = DozeSensors.this.mDozeLog;
                String sensor3 = sensor.toString();
                DozeLogger dozeLogger2 = dozeLog2.mLogger;
                dozeLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                DozeLogger$logSkipSensorRegistration$2 dozeLogger$logSkipSensorRegistration$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logSkipSensorRegistration$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return KeyAttributes$$ExternalSyntheticOutline0.m21m("Skipping sensor registration because its already registered. sensor=", ((LogMessage) obj).getStr1());
                    }
                };
                LogBuffer logBuffer2 = dozeLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("DozeLog", logLevel2, dozeLogger$logSkipSensorRegistration$2, null);
                obtain2.setStr1(sensor3);
                logBuffer2.commit(obtain2);
                return;
            }
            this.mRegistered = DozeSensors.this.mSensorManager.requestTriggerSensor(this, sensor);
            DozeLog dozeLog3 = DozeSensors.this.mDozeLog;
            String sensor4 = sensor.toString();
            boolean z = this.mRegistered;
            DozeLogger dozeLogger3 = dozeLog3.mLogger;
            dozeLogger3.getClass();
            LogLevel logLevel3 = LogLevel.INFO;
            DozeLogger$logSensorRegisterAttempt$2 dozeLogger$logSensorRegisterAttempt$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logSensorRegisterAttempt$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return "Register sensor. Success=" + logMessage.getBool1() + " sensor=" + logMessage.getStr1();
                }
            };
            LogBuffer logBuffer3 = dozeLogger3.buffer;
            LogMessage obtain3 = logBuffer3.obtain("DozeLog", logLevel3, dozeLogger$logSensorRegisterAttempt$2, null);
            obtain3.setStr1(sensor4);
            obtain3.setBool1(z);
            logBuffer3.commit(obtain3);
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
    public DozeSensors(Resources resources, AsyncSensorManager asyncSensorManager, DozeParameters dozeParameters, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, Callback callback, Consumer<Boolean> consumer, DozeLog dozeLog, ProximitySensor proximitySensor, SecureSettings secureSettings, AuthController authController, DevicePostureController devicePostureController, UserTracker userTracker) {
        boolean z;
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mSettingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.doze.DozeSensors.1
            public final void onChange(boolean z2, Collection collection, int i, int i2) {
                if (i2 != ((UserTrackerImpl) DozeSensors.this.mUserTracker).getUserId()) {
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
                DozeSensors dozeSensors = DozeSensors.this;
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
                                    boolean cancelTriggerSensor = DozeSensors.this.mSensorManager.cancelTriggerSensor(triggerSensor, sensor);
                                    DozeLog dozeLog2 = DozeSensors.this.mDozeLog;
                                    String sensor3 = sensor.toString();
                                    DozeLogger dozeLogger = dozeLog2.mLogger;
                                    dozeLogger.getClass();
                                    LogLevel logLevel = LogLevel.INFO;
                                    DozeLogger$logSensorUnregisterAttempt$4 dozeLogger$logSensorUnregisterAttempt$4 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logSensorUnregisterAttempt$4
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj) {
                                            LogMessage logMessage = (LogMessage) obj;
                                            return "Unregister sensor. reason=" + logMessage.getStr2() + ". Success=" + logMessage.getBool1() + " sensor=" + logMessage.getStr1();
                                        }
                                    };
                                    LogBuffer logBuffer = dozeLogger.buffer;
                                    LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logSensorUnregisterAttempt$4, null);
                                    obtain.setStr1(sensor3);
                                    obtain.setBool1(cancelTriggerSensor);
                                    obtain.setStr2("posture changed");
                                    logBuffer.commit(obtain);
                                    triggerSensor.mRegistered = false;
                                }
                                triggerSensor.mPosture = i2;
                                triggerSensor.updateListening();
                                DozeLog dozeLog3 = DozeSensors.this.mDozeLog;
                                int i4 = triggerSensor.mPosture;
                                String str = "DozeSensors swap {" + sensor + "} => {" + sensor2 + "}, mRegistered=" + triggerSensor.mRegistered;
                                DozeLogger dozeLogger2 = dozeLog3.mLogger;
                                dozeLogger2.getClass();
                                LogLevel logLevel2 = LogLevel.INFO;
                                DozeLogger$logPostureChanged$2 dozeLogger$logPostureChanged$2 = DozeLogger$logPostureChanged$2.INSTANCE;
                                LogBuffer logBuffer2 = dozeLogger2.buffer;
                                LogMessage obtain2 = logBuffer2.obtain("DozeLog", logLevel2, dozeLogger$logPostureChanged$2, null);
                                obtain2.setInt1(i4);
                                obtain2.setStr1(str);
                                logBuffer2.commit(obtain2);
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
                dozeSensors.mUdfpsEnrolled = dozeSensors.mAuthController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser());
                for (TriggerSensor triggerSensor : dozeSensors.mTriggerSensors) {
                    int i = triggerSensor.mPulseReason;
                    AmbientDisplayConfiguration ambientDisplayConfiguration2 = dozeSensors.mConfig;
                    if (11 == i) {
                        r7 = dozeSensors.mUdfpsEnrolled && ambientDisplayConfiguration2.quickPickupSensorEnabled(KeyguardUpdateMonitor.getCurrentUser());
                        if (triggerSensor.mConfigured != r7) {
                            triggerSensor.mConfigured = r7;
                            triggerSensor.updateListening();
                        }
                    } else if (10 == i) {
                        if (!dozeSensors.mUdfpsEnrolled || (!ambientDisplayConfiguration2.alwaysOnEnabled(((UserTrackerImpl) dozeSensors.mUserTracker).getUserId()) && !dozeSensors.mScreenOffUdfpsEnabled)) {
                            r7 = false;
                        }
                        if (triggerSensor.mConfigured != r7) {
                            triggerSensor.mConfigured = r7;
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
        ((ProximitySensorImpl) proximitySensor).setTag("DozeSensors");
        boolean z2 = SystemProperties.getBoolean("doze.prox.selectively_register", dozeParameters.mResources.getBoolean(R.bool.doze_selectively_register_prox));
        this.mSelectivelyRegisterProxSensors = z2;
        boolean z3 = true;
        this.mListeningProxSensors = !z2;
        this.mScreenOffUdfpsEnabled = ambientDisplayConfiguration.screenOffUdfpsEnabled(KeyguardUpdateMonitor.getCurrentUser());
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = ((DevicePostureControllerImpl) devicePostureController).mCurrentDevicePosture;
        this.mAuthController = authController;
        this.mUserTracker = userTracker;
        this.mUdfpsEnrolled = authController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser());
        authController.addCallback(r4);
        TriggerSensor[] triggerSensorArr = new TriggerSensor[9];
        Sensor defaultSensor = asyncSensorManager.getDefaultSensor(17);
        Resources resources2 = dozeParameters.mResources;
        triggerSensorArr[0] = new TriggerSensor(this, defaultSensor, null, SystemProperties.getBoolean("doze.pulse.sigmotion", resources2.getBoolean(R.bool.doze_pulse_on_significant_motion)), 2, false, false);
        triggerSensorArr[1] = new TriggerSensor(this, asyncSensorManager.getDefaultSensor(25), "doze_pulse_on_pick_up", resources.getBoolean(android.R.bool.config_deskDockEnablesAccelerometer), ambientDisplayConfiguration.dozePickupSensorAvailable(), 3, false, false, false, false, true, false);
        triggerSensorArr[2] = new TriggerSensor(this, findSensor(asyncSensorManager, ambientDisplayConfiguration.doubleTapSensorType(), null), "doze_pulse_on_double_tap", true, 4, resources2.getBoolean(R.bool.doze_double_tap_reports_touch_coordinates), true);
        String[] tapSensorTypeMapping = ambientDisplayConfiguration.tapSensorTypeMapping();
        Sensor[] sensorArr = new Sensor[5];
        HashMap hashMap = new HashMap();
        for (int i = 0; i < tapSensorTypeMapping.length; i++) {
            String str = tapSensorTypeMapping[i];
            if (!hashMap.containsKey(str)) {
                hashMap.put(str, findSensor(this.mSensorManager, str, null));
            }
            sensorArr[i] = (Sensor) hashMap.get(str);
        }
        int i2 = this.mDevicePosture;
        int[] intArray = resources2.getIntArray(R.array.doze_single_tap_uses_prox_posture_mapping);
        boolean z4 = resources2.getBoolean(R.bool.doze_single_tap_uses_prox);
        if (i2 >= intArray.length) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("Unsupported doze posture ", i2, "DozeParameters");
            z3 = z4;
        } else if (intArray[i2] == 0) {
            z3 = false;
        }
        triggerSensorArr[3] = new TriggerSensor(sensorArr, "doze_tap_gesture", true, true, 9, true, true, false, z3, true, this.mDevicePosture, false);
        triggerSensorArr[4] = new TriggerSensor(this, findSensor(this.mSensorManager, ambientDisplayConfiguration.longPressSensorType(), null), "doze_pulse_on_long_press", false, true, 5, true, true, false, resources2.getBoolean(R.bool.doze_long_press_uses_prox), true, false);
        Sensor findSensor = findSensor(this.mSensorManager, ambientDisplayConfiguration.udfpsLongPressSensorType(), null);
        if (this.mUdfpsEnrolled) {
            if (this.mConfig.alwaysOnEnabled(((UserTrackerImpl) this.mUserTracker).getUserId()) || this.mScreenOffUdfpsEnabled) {
                z = true;
                triggerSensorArr[5] = new TriggerSensor(this, findSensor, "doze_pulse_on_auth", true, z, 10, true, true, false, resources2.getBoolean(R.bool.doze_long_press_uses_prox), false, true);
                triggerSensorArr[6] = new PluginSensor(this, new SensorManagerPlugin.Sensor(2), "doze_wake_display_gesture", !this.mConfig.wakeScreenGestureAvailable() && this.mConfig.alwaysOnEnabled(((UserTrackerImpl) this.mUserTracker).getUserId()), 7, false, false);
                triggerSensorArr[7] = new PluginSensor(new SensorManagerPlugin.Sensor(1), "doze_wake_screen_gesture", this.mConfig.wakeScreenGestureAvailable(), 8, false, false, this.mConfig.getWakeLockScreenDebounce());
                triggerSensorArr[8] = new TriggerSensor(this, findSensor(this.mSensorManager, ambientDisplayConfiguration.quickPickupSensorType(), null), "doze_quick_pickup_gesture", true, !this.mUdfpsEnrolled && this.mConfig.quickPickupSensorEnabled(KeyguardUpdateMonitor.getCurrentUser()), 11, false, false, false, false, true, false);
                this.mTriggerSensors = triggerSensorArr;
                setProxListening(false);
                this.mProximitySensor.register(new ThresholdSensor.Listener() { // from class: com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda1
                    @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
                    public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
                        DozeSensors dozeSensors = DozeSensors.this;
                        dozeSensors.getClass();
                        if (thresholdSensorEvent != null) {
                            dozeSensors.mProxCallback.accept(Boolean.valueOf(!thresholdSensorEvent.mBelow));
                        }
                    }
                });
                ((DevicePostureControllerImpl) this.mDevicePostureController).addCallback(this.mDevicePostureCallback);
            }
        }
        z = false;
        triggerSensorArr[5] = new TriggerSensor(this, findSensor, "doze_pulse_on_auth", true, z, 10, true, true, false, resources2.getBoolean(R.bool.doze_long_press_uses_prox), false, true);
        triggerSensorArr[6] = new PluginSensor(this, new SensorManagerPlugin.Sensor(2), "doze_wake_display_gesture", !this.mConfig.wakeScreenGestureAvailable() && this.mConfig.alwaysOnEnabled(((UserTrackerImpl) this.mUserTracker).getUserId()), 7, false, false);
        triggerSensorArr[7] = new PluginSensor(new SensorManagerPlugin.Sensor(1), "doze_wake_screen_gesture", this.mConfig.wakeScreenGestureAvailable(), 8, false, false, this.mConfig.getWakeLockScreenDebounce());
        triggerSensorArr[8] = new TriggerSensor(this, findSensor(this.mSensorManager, ambientDisplayConfiguration.quickPickupSensorType(), null), "doze_quick_pickup_gesture", true, !this.mUdfpsEnrolled && this.mConfig.quickPickupSensorEnabled(KeyguardUpdateMonitor.getCurrentUser()), 11, false, false, false, false, true, false);
        this.mTriggerSensors = triggerSensorArr;
        setProxListening(false);
        this.mProximitySensor.register(new ThresholdSensor.Listener() { // from class: com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda1
            @Override // com.android.systemui.util.sensors.ThresholdSensor.Listener
            public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
                DozeSensors dozeSensors = DozeSensors.this;
                dozeSensors.getClass();
                if (thresholdSensorEvent != null) {
                    dozeSensors.mProxCallback.accept(Boolean.valueOf(!thresholdSensorEvent.mBelow));
                }
            }
        });
        ((DevicePostureControllerImpl) this.mDevicePostureController).addCallback(this.mDevicePostureCallback);
    }

    public static Sensor findSensor(SensorManager sensorManager, String str, String str2) {
        boolean z = !TextUtils.isEmpty(str2);
        boolean z2 = !TextUtils.isEmpty(str);
        if (!z && !z2) {
            return null;
        }
        for (Sensor sensor : sensorManager.getSensorList(-1)) {
            if (!z || str2.equals(sensor.getName())) {
                if (!z2 || str.equals(sensor.getStringType())) {
                    return sensor;
                }
            }
        }
        return null;
    }

    public final void setProxListening(boolean z) {
        ProximitySensor proximitySensor = this.mProximitySensor;
        if (((ProximitySensorImpl) proximitySensor).mRegistered && z) {
            ((ProximitySensorImpl) proximitySensor).alertListeners();
        } else if (z) {
            ((ProximitySensorImpl) proximitySensor).resume();
        } else {
            ((ProximitySensorImpl) proximitySensor).pause();
        }
    }

    public final void updateListening() {
        boolean z = false;
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            boolean z2 = this.mListening && (!triggerSensor.mRequiresTouchscreen || this.mListeningTouchScreenSensors) && ((!triggerSensor.mRequiresProx || this.mListeningProxSensors) && (!triggerSensor.mRequiresAod || this.mListeningAodOnlySensors));
            if (triggerSensor.mRequested != z2) {
                triggerSensor.mRequested = z2;
                triggerSensor.updateListening();
            }
            if (z2) {
                z = true;
            }
        }
        if (!z) {
            this.mSecureSettings.unregisterContentObserver(this.mSettingsObserver);
        } else if (!this.mSettingRegistered) {
            for (TriggerSensor triggerSensor2 : this.mTriggerSensors) {
                if (triggerSensor2.mConfigured && !TextUtils.isEmpty(triggerSensor2.mSetting)) {
                    DozeSensors dozeSensors = DozeSensors.this;
                    dozeSensors.mSecureSettings.registerContentObserverForUser(triggerSensor2.mSetting, dozeSensors.mSettingsObserver, -1);
                }
            }
        }
        this.mSettingRegistered = z;
    }
}
