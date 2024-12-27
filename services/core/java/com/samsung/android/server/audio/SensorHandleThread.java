package com.samsung.android.server.audio;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioSystem;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.audio.AudioService;

import com.samsung.android.hardware.context.SemContext;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SensorHandleThread extends Thread
        implements SensorEventListener, SemContextListener {
    public static final Set sBluetoothCommunicationDevices;
    public final AudioService mAudioService;
    public boolean mDevicePositionChangedDuringRingtone;
    public boolean mIsClosed;
    public boolean mIsProximateRegistered;
    public boolean mIsSemMotionRegistered;
    public final Sensor mJdmFlatMotionSensor;
    public Set mMusicDevices;
    public final Sensor mProximitySensor;
    public final SemContextManager mSemContextManager;
    public final boolean mSemDevicePositionSensorEnabled;
    public final boolean mSemFlatMotionSensorEnabled;
    public Handler mSensorHandler;
    public final SensorManager mSensorManager;
    public Set mVoiceDevices;
    public final SensorHandleThread$$ExternalSyntheticLambda0 mediaCallback;
    public final SensorHandleThread$$ExternalSyntheticLambda0 voiceCallback;

    static {
        HashSet hashSet = new HashSet();
        sBluetoothCommunicationDevices = hashSet;
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_SCO_SET);
        hashSet.add(536870912);
    }

    public SensorHandleThread(Context context, AudioService audioService) {
        super("SensorHandleThread");
        this.mIsSemMotionRegistered = false;
        this.mIsProximateRegistered = false;
        this.mIsClosed = false;
        this.mVoiceDevices = null;
        this.mMusicDevices = null;
        this.voiceCallback = new SensorHandleThread$$ExternalSyntheticLambda0(this, 0);
        this.mediaCallback = new SensorHandleThread$$ExternalSyntheticLambda0(this, 1);
        this.mAudioService = audioService;
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        this.mSensorManager = sensorManager;
        this.mProximitySensor = sensorManager.getDefaultSensor(8);
        this.mJdmFlatMotionSensor = sensorManager.getDefaultSensor(65737);
        if (context.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
            SemContextManager semContextManager =
                    (SemContextManager) context.getSystemService("scontext");
            this.mSemContextManager = semContextManager;
            if (semContextManager != null) {
                boolean isAvailableService = semContextManager.isAvailableService(22);
                this.mSemDevicePositionSensorEnabled = isAvailableService;
                if (isAvailableService) {
                    return;
                }
                this.mSemFlatMotionSensorEnabled = semContextManager.isAvailableService(20);
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {}

    public final void onSemContextChanged(SemContextEvent semContextEvent) {
        Set set;
        SemContext semContext = semContextEvent.semContext;
        if (this.mDevicePositionChangedDuringRingtone || (set = this.mMusicDevices) == null) {
            return;
        }
        if (set.contains(2) || this.mAudioService.mMode.get() > 1) {
            int type = semContext.getType();
            if (type != 20) {
                if (type != 22) {
                    return;
                }
                if (semContextEvent.getDevicePositionContext().getPosition() == 1) {
                    AudioSystem.setParameters("l_hw_flat_motion_state=1");
                    return;
                }
                AudioSystem.setParameters("l_hw_flat_motion_state=0");
                if (this.mAudioService.getMode() == 1) {
                    this.mDevicePositionChangedDuringRingtone = true;
                    return;
                }
                return;
            }
            int action = semContextEvent.getFlatMotionContext().getAction();
            if (action == 1) {
                AudioSystem.setParameters("l_hw_flat_motion_state=1");
            } else if (action == 2) {
                AudioSystem.setParameters("l_hw_flat_motion_state=0");
                if (this.mAudioService.getMode() == 1) {
                    this.mDevicePositionChangedDuringRingtone = true;
                }
            }
        }
    }

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        Set set;
        synchronized (this) {
            try {
                if (this.mAudioService.getMode() > 1) {
                    if (sensorEvent.values[0] != 0.0d
                            || "GAMEVOIP".equals(this.mAudioService.mAppMode)) {
                        this.mIsClosed = false;
                    } else {
                        this.mIsClosed = true;
                    }
                    if (this.mIsClosed) {
                        AudioSystem.setParameters("l_hw_proximity_sensor_state=1");
                    } else {
                        AudioSystem.setParameters("l_hw_proximity_sensor_state=0");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mDevicePositionChangedDuringRingtone || (set = this.mMusicDevices) == null) {
            return;
        }
        if ((set.contains(2) || this.mAudioService.mMode.get() > 1)
                && TextUtils.equals("jdm", "in_house")) {
            float f = sensorEvent.values[0];
            if (f == 1.0f) {
                AudioSystem.setParameters("l_hw_flat_motion_state=1");
            } else if (f == 2.0f) {
                AudioSystem.setParameters("l_hw_flat_motion_state=0");
                if (this.mAudioService.getMode() == 1) {
                    this.mDevicePositionChangedDuringRingtone = true;
                }
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        Looper.prepare();
        this.mSensorHandler = new Handler();
        Looper.loop();
    }

    public final synchronized void startProximate() {
        boolean anyMatch;
        Set set = this.mVoiceDevices;
        if (set == null) {
            anyMatch = false;
        } else {
            Stream stream = set.stream();
            Set set2 = sBluetoothCommunicationDevices;
            Objects.requireNonNull(set2);
            anyMatch = stream.anyMatch(new SensorHandleThread$$ExternalSyntheticLambda2(set2));
        }
        if (anyMatch) {
            Log.d(
                    "AS.SensorHandleThread",
                    "start fail by bluetooth communication device connection");
        } else {
            if (this.mIsProximateRegistered) {
                return;
            }
            this.mIsProximateRegistered = true;
            this.mSensorManager.registerListener(
                    this, this.mProximitySensor, 3, this.mSensorHandler);
        }
    }

    public final void startSensor() {
        Executor handlerExecutor = new HandlerExecutor(this.mSensorHandler);
        this.mAudioService.registerCurrentDeviceChangedCallback(
                0, this.voiceCallback, handlerExecutor);
        this.mAudioService.registerCurrentDeviceChangedCallback(
                3, this.mediaCallback, handlerExecutor);
        startProximate();
        synchronized (this) {
            try {
                if (!this.mIsSemMotionRegistered) {
                    this.mIsSemMotionRegistered = true;
                    if (this.mSemDevicePositionSensorEnabled) {
                        this.mSemContextManager.registerListener(this, 22);
                    } else if (this.mSemFlatMotionSensorEnabled) {
                        this.mSemContextManager.registerListener(this, 20);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (TextUtils.equals("jdm", "in_house")) {
            this.mSensorManager.registerListener(this, this.mJdmFlatMotionSensor, 3);
        }
    }

    public final synchronized void stopProximate() {
        if (this.mIsProximateRegistered) {
            this.mIsProximateRegistered = false;
            this.mSensorManager.unregisterListener(this, this.mProximitySensor);
        }
    }

    public final void stopSensor() {
        this.mAudioService.unregisterCurrentDeviceChangedCallback(0, this.voiceCallback);
        this.mAudioService.unregisterCurrentDeviceChangedCallback(3, this.mediaCallback);
        stopProximate();
        synchronized (this) {
            try {
                if (this.mIsSemMotionRegistered) {
                    this.mIsSemMotionRegistered = false;
                    if (this.mSemDevicePositionSensorEnabled) {
                        this.mSemContextManager.unregisterListener(this, 22);
                        AudioSystem.setParameters("l_hw_flat_motion_state=1");
                    } else if (this.mSemFlatMotionSensorEnabled) {
                        this.mSemContextManager.unregisterListener(this, 20);
                        AudioSystem.setParameters("l_hw_flat_motion_state=1");
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (TextUtils.equals("jdm", "in_house")) {
            this.mSensorManager.unregisterListener(this, this.mJdmFlatMotionSensor);
        }
    }
}
