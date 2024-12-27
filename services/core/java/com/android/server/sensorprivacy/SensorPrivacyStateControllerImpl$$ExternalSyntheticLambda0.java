package com.android.server.sensorprivacy;

import android.hardware.ISensorPrivacyListener;
import android.os.RemoteException;
import android.util.Log;

import com.android.internal.camera.flags.Flags;
import com.android.internal.util.function.QuintConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SensorPrivacyStateControllerImpl$$ExternalSyntheticLambda0
        implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        int intValue3 = ((Integer) obj4).intValue();
        SensorState sensorState = (SensorState) obj5;
        SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                ((SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2) obj).f$0;
        sensorPrivacyServiceImpl.mHandler.handleSensorPrivacyChanged(
                intValue2, intValue, intValue3, sensorState.isEnabled());
        if (Flags.cameraPrivacyAllowlist()) {
            SensorPrivacyService.SensorPrivacyHandler sensorPrivacyHandler =
                    sensorPrivacyServiceImpl.mHandler;
            int i = sensorState.mStateType;
            SensorPrivacyService sensorPrivacyService = SensorPrivacyService.this;
            if (intValue2 == sensorPrivacyService.mCurrentUser) {
                SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl2 =
                        sensorPrivacyService.mSensorPrivacyServiceImpl;
                sensorPrivacyServiceImpl2.setGlobalRestriction(
                        intValue3,
                        sensorPrivacyServiceImpl2.isCombinedToggleSensorPrivacyEnabled(intValue3));
            }
            if (intValue2 != SensorPrivacyService.this.mCurrentUser) {
                return;
            }
            synchronized (sensorPrivacyHandler.mListenerLock) {
                try {
                    int beginBroadcast =
                            sensorPrivacyHandler.mToggleSensorListeners.beginBroadcast();
                    for (int i2 = 0; i2 < beginBroadcast; i2++) {
                        ISensorPrivacyListener broadcastItem =
                                sensorPrivacyHandler.mToggleSensorListeners.getBroadcastItem(i2);
                        try {
                            broadcastItem.onSensorPrivacyStateChanged(intValue, intValue3, i);
                        } catch (RemoteException e) {
                            String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                            Log.e(
                                    "SensorPrivacyService",
                                    "Caught an exception notifying listener "
                                            + broadcastItem
                                            + ": ",
                                    e);
                        }
                    }
                } finally {
                    sensorPrivacyHandler.mToggleSensorListeners.finishBroadcast();
                }
            }
            SensorPrivacyService.SensorPrivacyServiceImpl
                    .m857$$Nest$mshowSensorStateChangedActivity(
                            SensorPrivacyService.this.mSensorPrivacyServiceImpl,
                            intValue3,
                            intValue);
        }
    }
}
