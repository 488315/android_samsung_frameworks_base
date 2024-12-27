package com.android.systemui.util.sensors;

import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.sensors.ThresholdSensor;
import java.util.HashSet;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class PostureDependentProximitySensor extends ProximitySensorImpl {
    private final DevicePostureController.Callback mDevicePostureCallback;
    private final DevicePostureController mDevicePostureController;
    private final HashSet<ThresholdSensor.Listener> mListenersRegisteredWhenProxUnavailable;
    private final ThresholdSensor[] mPostureToPrimaryProxSensorMap;
    private final ThresholdSensor[] mPostureToSecondaryProxSensorMap;

    public PostureDependentProximitySensor(@PrimaryProxSensor ThresholdSensor[] thresholdSensorArr, @SecondaryProxSensor ThresholdSensor[] thresholdSensorArr2, DelayableExecutor delayableExecutor, Execution execution, DevicePostureController devicePostureController) {
        super(thresholdSensorArr[0], thresholdSensorArr2[0], delayableExecutor, execution);
        this.mListenersRegisteredWhenProxUnavailable = new HashSet<>();
        DevicePostureController.Callback callback = new DevicePostureController.Callback() { // from class: com.android.systemui.util.sensors.PostureDependentProximitySensor$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                PostureDependentProximitySensor.this.lambda$new$0(i);
            }
        };
        this.mDevicePostureCallback = callback;
        this.mPostureToPrimaryProxSensorMap = thresholdSensorArr;
        this.mPostureToSecondaryProxSensorMap = thresholdSensorArr2;
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = ((DevicePostureControllerImpl) devicePostureController).getDevicePosture();
        ((DevicePostureControllerImpl) devicePostureController).addCallback(callback);
        chooseSensors();
    }

    private void chooseSensors() {
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
                ThresholdSensor.Listener[] listenerArr = (ThresholdSensor.Listener[]) this.mListenersRegisteredWhenProxUnavailable.toArray(new ThresholdSensor.Listener[0]);
                this.mListenersRegisteredWhenProxUnavailable.clear();
                for (ThresholdSensor.Listener listener : listenerArr) {
                    logDebug("Re-register listener " + listener);
                    register(listener);
                }
                return;
            }
        }
        Log.e("PostureDependProxSensor", "unsupported devicePosture=" + this.mDevicePosture);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i) {
        if (this.mDevicePosture == i) {
            return;
        }
        this.mDevicePosture = i;
        chooseSensors();
    }

    @Override // com.android.systemui.util.sensors.ProximitySensorImpl, com.android.systemui.util.sensors.ProximitySensor
    public void destroy() {
        super.destroy();
        ((DevicePostureControllerImpl) this.mDevicePostureController).removeCallback(this.mDevicePostureCallback);
    }

    @Override // com.android.systemui.util.sensors.ProximitySensorImpl, com.android.systemui.util.sensors.ThresholdSensor
    public void register(ThresholdSensor.Listener listener) {
        if (!isLoaded()) {
            logDebug("No prox sensor when registering listener=" + listener);
            this.mListenersRegisteredWhenProxUnavailable.add(listener);
        }
        super.register(listener);
    }

    @Override // com.android.systemui.util.sensors.ProximitySensorImpl
    public String toString() {
        return MotionLayout$$ExternalSyntheticOutline0.m("{posture=", DevicePostureController.devicePostureToString(this.mDevicePosture), ", proximitySensor=", super.toString(), "}");
    }

    @Override // com.android.systemui.util.sensors.ProximitySensorImpl, com.android.systemui.util.sensors.ThresholdSensor
    public void unregister(ThresholdSensor.Listener listener) {
        if (this.mListenersRegisteredWhenProxUnavailable.remove(listener)) {
            logDebug("Removing listener from mListenersRegisteredWhenProxUnavailable " + listener);
        }
        super.unregister(listener);
    }
}
