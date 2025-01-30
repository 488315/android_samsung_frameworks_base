package com.android.systemui.util.sensors;

import android.util.Log;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PostureDependentProximitySensor extends ProximitySensorImpl {
    public final PostureDependentProximitySensor$$ExternalSyntheticLambda0 mDevicePostureCallback;
    public final DevicePostureController mDevicePostureController;
    public final HashSet mListenersRegisteredWhenProxUnavailable;
    public final ThresholdSensor[] mPostureToPrimaryProxSensorMap;
    public final ThresholdSensor[] mPostureToSecondaryProxSensorMap;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.systemui.util.sensors.PostureDependentProximitySensor$$ExternalSyntheticLambda0, java.lang.Object] */
    public PostureDependentProximitySensor(ThresholdSensor[] thresholdSensorArr, ThresholdSensor[] thresholdSensorArr2, DelayableExecutor delayableExecutor, Execution execution, DevicePostureController devicePostureController) {
        super(thresholdSensorArr[0], thresholdSensorArr2[0], delayableExecutor, execution);
        this.mListenersRegisteredWhenProxUnavailable = new HashSet();
        ?? r5 = new DevicePostureController.Callback() { // from class: com.android.systemui.util.sensors.PostureDependentProximitySensor$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                PostureDependentProximitySensor postureDependentProximitySensor = PostureDependentProximitySensor.this;
                if (postureDependentProximitySensor.mDevicePosture == i) {
                    return;
                }
                postureDependentProximitySensor.mDevicePosture = i;
                postureDependentProximitySensor.chooseSensors();
            }
        };
        this.mDevicePostureCallback = r5;
        this.mPostureToPrimaryProxSensorMap = thresholdSensorArr;
        this.mPostureToSecondaryProxSensorMap = thresholdSensorArr2;
        this.mDevicePostureController = devicePostureController;
        DevicePostureControllerImpl devicePostureControllerImpl = (DevicePostureControllerImpl) devicePostureController;
        this.mDevicePosture = devicePostureControllerImpl.mCurrentDevicePosture;
        devicePostureControllerImpl.addCallback(r5);
        chooseSensors();
    }

    public final void chooseSensors() {
        int i = this.mDevicePosture;
        ThresholdSensor[] thresholdSensorArr = this.mPostureToPrimaryProxSensorMap;
        if (i < thresholdSensorArr.length) {
            ThresholdSensor[] thresholdSensorArr2 = this.mPostureToSecondaryProxSensorMap;
            if (i < thresholdSensorArr2.length) {
                ThresholdSensor thresholdSensor = thresholdSensorArr[i];
                ThresholdSensor thresholdSensor2 = thresholdSensorArr2[i];
                if (thresholdSensor == this.mPrimaryThresholdSensor && thresholdSensor2 == this.mSecondaryThresholdSensor) {
                    return;
                }
                logDebug("Register new proximity sensors newPosture=" + DevicePostureController.devicePostureToString(this.mDevicePosture));
                unregisterInternal();
                ThresholdSensor thresholdSensor3 = this.mPrimaryThresholdSensor;
                if (thresholdSensor3 != null) {
                    thresholdSensor3.unregister(this.mPrimaryEventListener);
                }
                ThresholdSensor thresholdSensor4 = this.mSecondaryThresholdSensor;
                if (thresholdSensor4 != null) {
                    thresholdSensor4.unregister(this.mSecondaryEventListener);
                }
                this.mPrimaryThresholdSensor = thresholdSensor;
                this.mSecondaryThresholdSensor = thresholdSensor2;
                this.mInitializedListeners = false;
                registerInternal();
                HashSet hashSet = this.mListenersRegisteredWhenProxUnavailable;
                ThresholdSensor.Listener[] listenerArr = (ThresholdSensor.Listener[]) hashSet.toArray(new ThresholdSensor.Listener[0]);
                hashSet.clear();
                for (ThresholdSensor.Listener listener : listenerArr) {
                    logDebug("Re-register listener " + listener);
                    register(listener);
                }
                return;
            }
        }
        Log.e("PostureDependProxSensor", "unsupported devicePosture=" + this.mDevicePosture);
    }

    @Override // com.android.systemui.util.sensors.ProximitySensorImpl, com.android.systemui.util.sensors.ProximitySensor
    public final void destroy() {
        pause();
        ((DevicePostureControllerImpl) this.mDevicePostureController).removeCallback(this.mDevicePostureCallback);
    }

    @Override // com.android.systemui.util.sensors.ProximitySensorImpl, com.android.systemui.util.sensors.ThresholdSensor
    public final void register(ThresholdSensor.Listener listener) {
        if (!isLoaded()) {
            logDebug("No prox sensor when registering listener=" + listener);
            this.mListenersRegisteredWhenProxUnavailable.add(listener);
        }
        super.register(listener);
    }

    @Override // com.android.systemui.util.sensors.ProximitySensorImpl
    public final String toString() {
        return String.format("{posture=%s, proximitySensor=%s}", DevicePostureController.devicePostureToString(this.mDevicePosture), super.toString());
    }

    @Override // com.android.systemui.util.sensors.ProximitySensorImpl, com.android.systemui.util.sensors.ThresholdSensor
    public final void unregister(ThresholdSensor.Listener listener) {
        if (this.mListenersRegisteredWhenProxUnavailable.remove(listener)) {
            logDebug("Removing listener from mListenersRegisteredWhenProxUnavailable " + listener);
        }
        super.unregister(listener);
    }
}
