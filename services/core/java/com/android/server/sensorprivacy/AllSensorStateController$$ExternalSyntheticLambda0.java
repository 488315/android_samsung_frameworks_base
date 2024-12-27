package com.android.server.sensorprivacy;

import android.hardware.ISensorPrivacyListener;
import android.os.RemoteException;
import android.util.Log;
import android.util.Xml;

import com.android.modules.utils.TypedXmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AllSensorStateController$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AllSensorStateController$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        FileOutputStream startWrite;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                SensorPrivacyService.SensorPrivacyHandler sensorPrivacyHandler =
                        (SensorPrivacyService.SensorPrivacyHandler)
                                ((SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1)
                                                obj2)
                                        .f$0;
                int beginBroadcast = sensorPrivacyHandler.mListeners.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast; i2++) {
                    ISensorPrivacyListener broadcastItem =
                            sensorPrivacyHandler.mListeners.getBroadcastItem(i2);
                    try {
                        broadcastItem.onSensorPrivacyChanged(-1, -1, booleanValue);
                    } catch (RemoteException e) {
                        String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
                        Log.e(
                                "SensorPrivacyService",
                                "Caught an exception notifying listener " + broadcastItem + ": ",
                                e);
                    }
                }
                sensorPrivacyHandler.mListeners.finishBroadcast();
                break;
            default:
                AllSensorStateController allSensorStateController = (AllSensorStateController) obj2;
                boolean booleanValue2 = ((Boolean) obj).booleanValue();
                allSensorStateController.getClass();
                FileOutputStream fileOutputStream = null;
                try {
                    startWrite = allSensorStateController.mAtomicFile.startWrite();
                } catch (IOException e2) {
                    e = e2;
                }
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.startTag((String) null, "all-sensor-privacy");
                    resolveSerializer.attributeBoolean((String) null, "enabled", booleanValue2);
                    resolveSerializer.endTag((String) null, "all-sensor-privacy");
                    resolveSerializer.endDocument();
                    allSensorStateController.mAtomicFile.finishWrite(startWrite);
                    break;
                } catch (IOException e3) {
                    e = e3;
                    fileOutputStream = startWrite;
                    Log.e(
                            "AllSensorStateController",
                            "Caught an exception persisting the sensor privacy state: ",
                            e);
                    allSensorStateController.mAtomicFile.failWrite(fileOutputStream);
                }
        }
    }
}
