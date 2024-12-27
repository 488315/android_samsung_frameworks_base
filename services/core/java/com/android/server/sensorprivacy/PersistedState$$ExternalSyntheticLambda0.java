package com.android.server.sensorprivacy;

import android.util.ArrayMap;
import android.util.Log;
import android.util.Xml;

import com.android.modules.utils.TypedXmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PersistedState$$ExternalSyntheticLambda0 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        PersistedState persistedState = (PersistedState) obj;
        ArrayMap arrayMap = (ArrayMap) obj2;
        persistedState.getClass();
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream startWrite = persistedState.mAtomicFile.startWrite();
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                resolveSerializer.startTag((String) null, "sensor-privacy");
                resolveSerializer.attributeInt((String) null, "persistence-version", 2);
                resolveSerializer.attributeInt((String) null, "version", 2);
                for (int i = 0; i < arrayMap.size(); i++) {
                    PersistedState.TypeUserSensor typeUserSensor =
                            (PersistedState.TypeUserSensor) arrayMap.keyAt(i);
                    SensorState sensorState = (SensorState) arrayMap.valueAt(i);
                    if (typeUserSensor.mType == 1) {
                        resolveSerializer.startTag((String) null, "sensor-state");
                        resolveSerializer.attributeInt(
                                (String) null, "toggle-type", typeUserSensor.mType);
                        resolveSerializer.attributeInt(
                                (String) null, "user-id", typeUserSensor.mUserId);
                        resolveSerializer.attributeInt(
                                (String) null, "sensor", typeUserSensor.mSensor);
                        resolveSerializer.attributeInt(
                                (String) null, "state-type", sensorState.mStateType);
                        resolveSerializer.attributeLong(
                                (String) null, "last-change", sensorState.mLastChange);
                        resolveSerializer.endTag((String) null, "sensor-state");
                    }
                }
                resolveSerializer.endTag((String) null, "sensor-privacy");
                resolveSerializer.endDocument();
                persistedState.mAtomicFile.finishWrite(startWrite);
            } catch (IOException e) {
                e = e;
                fileOutputStream = startWrite;
                Log.e(
                        "PersistedState",
                        "Caught an exception persisting the sensor privacy state: ",
                        e);
                persistedState.mAtomicFile.failWrite(fileOutputStream);
            }
        } catch (IOException e2) {
            e = e2;
        }
    }
}
