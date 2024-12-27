package com.android.server.sensorprivacy;

import android.os.Handler;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.SparseArray;

import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.IoThread;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SensorPrivacyStateControllerImpl {
    public static SensorPrivacyStateControllerImpl sInstance;
    public static SensorPrivacyStateControllerImpl sInstance$1;
    public final AllSensorStateController mAllSensorStateController;
    public SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2 mListener;
    public Handler mListenerHandler;
    public final Object mLock;
    public final PersistedState mPersistedState;

    public SensorPrivacyStateControllerImpl() {
        if (AllSensorStateController.sInstance == null) {
            AllSensorStateController.sInstance = new AllSensorStateController();
        }
        AllSensorStateController allSensorStateController = AllSensorStateController.sInstance;
        this.mAllSensorStateController = allSensorStateController;
        Object obj = new Object();
        this.mLock = obj;
        this.mPersistedState = new PersistedState();
        synchronized (obj) {
            allSensorStateController.getClass();
            IoThread.getHandler()
                    .sendMessage(
                            PooledLambda.obtainMessage(
                                    new AllSensorStateController$$ExternalSyntheticLambda0(
                                            1, allSensorStateController),
                                    Boolean.valueOf(allSensorStateController.mEnabled)));
            schedulePersistLocked();
        }
    }

    public static void sendSetStateCallback(
            Handler handler,
            SensorPrivacyStateController$SetStateResultCallback
                    sensorPrivacyStateController$SetStateResultCallback,
            boolean z) {
        handler.sendMessage(
                PooledLambda.obtainMessage(
                        new SensorPrivacyStateController$$ExternalSyntheticLambda0(),
                        sensorPrivacyStateController$SetStateResultCallback,
                        Boolean.valueOf(z)));
    }

    public final void atomic(Runnable runnable) {
        synchronized (this.mLock) {
            runnable.run();
        }
    }

    public final void dumpLocked(DualDumpOutputStream dualDumpOutputStream) {
        PersistedState persistedState = this.mPersistedState;
        persistedState.getClass();
        SparseArray sparseArray = new SparseArray();
        int size = persistedState.mStates.size();
        for (int i = 0; i < size; i++) {
            int i2 = ((PersistedState.TypeUserSensor) persistedState.mStates.keyAt(i)).mType;
            int i3 = ((PersistedState.TypeUserSensor) persistedState.mStates.keyAt(i)).mUserId;
            int i4 = ((PersistedState.TypeUserSensor) persistedState.mStates.keyAt(i)).mSensor;
            SparseArray sparseArray2 = (SparseArray) sparseArray.get(i3);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                sparseArray.put(i3, sparseArray2);
            }
            sparseArray2.put(
                    i4,
                    new Pair(Integer.valueOf(i2), (SensorState) persistedState.mStates.valueAt(i)));
        }
        dualDumpOutputStream.write(
                "storage_implementation",
                1138166333444L,
                SensorPrivacyStateControllerImpl.class.getName());
        int size2 = sparseArray.size();
        for (int i5 = 0; i5 < size2; i5++) {
            int keyAt = sparseArray.keyAt(i5);
            long start = dualDumpOutputStream.start("users", 2246267895811L);
            long j = 1120986464257L;
            dualDumpOutputStream.write("user_id", 1120986464257L, keyAt);
            SparseArray sparseArray3 = (SparseArray) sparseArray.valueAt(i5);
            int size3 = sparseArray3.size();
            int i6 = 0;
            while (i6 < size3) {
                int keyAt2 = sparseArray3.keyAt(i6);
                int intValue = ((Integer) ((Pair) sparseArray3.valueAt(i6)).first).intValue();
                SensorState sensorState = (SensorState) ((Pair) sparseArray3.valueAt(i6)).second;
                long start2 = dualDumpOutputStream.start("sensors", 2246267895812L);
                dualDumpOutputStream.write("sensor", j, keyAt2);
                long start3 = dualDumpOutputStream.start("toggles", 2246267895810L);
                dualDumpOutputStream.write("toggle_type", 1159641169924L, intValue);
                dualDumpOutputStream.write("state_type", 1159641169925L, sensorState.mStateType);
                dualDumpOutputStream.write("last_change", 1112396529667L, sensorState.mLastChange);
                dualDumpOutputStream.end(start3);
                dualDumpOutputStream.end(start2);
                i6++;
                size2 = size2;
                size3 = size3;
                j = 1120986464257L;
            }
            dualDumpOutputStream.end(start);
        }
    }

    public final void forEachStateLocked(
            SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                    sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1) {
        PersistedState persistedState = this.mPersistedState;
        int size = persistedState.mStates.size();
        for (int i = 0; i < size; i++) {
            PersistedState.TypeUserSensor typeUserSensor =
                    (PersistedState.TypeUserSensor) persistedState.mStates.keyAt(i);
            SensorState sensorState = (SensorState) persistedState.mStates.valueAt(i);
            Integer valueOf = Integer.valueOf(typeUserSensor.mType);
            Integer valueOf2 = Integer.valueOf(typeUserSensor.mUserId);
            Integer valueOf3 = Integer.valueOf(typeUserSensor.mSensor);
            int intValue = valueOf.intValue();
            int intValue2 = valueOf2.intValue();
            int intValue3 = valueOf3.intValue();
            switch (sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                    .$r8$classId) {
                case 1:
                    SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                            (SensorPrivacyService.SensorPrivacyServiceImpl)
                                    sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                                            .f$0;
                    if (intValue != 1) {
                        sensorPrivacyServiceImpl.getClass();
                        break;
                    } else if (!sensorPrivacyServiceImpl.supportsSensorToggle(1, intValue3)
                            && sensorState.isEnabled()) {
                        sensorPrivacyServiceImpl.setToggleSensorPrivacyUnchecked(
                                1, intValue2, 5, intValue3, false);
                        break;
                    }
                    break;
                default:
                    SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl2 =
                            ((SensorPrivacyService.SensorPrivacyServiceImpl.AnonymousClass1)
                                            sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda1
                                                    .f$0)
                                    .this$1;
                    boolean isEnabled = sensorState.isEnabled();
                    long j = sensorState.mLastChange;
                    sensorPrivacyServiceImpl2.getClass();
                    SensorPrivacyService.SensorPrivacyServiceImpl.logSensorPrivacyToggle(
                            5, intValue3, j, isEnabled, true);
                    break;
            }
        }
    }

    public final SensorState getState(int i, int i2, int i3) {
        SensorState sensorState;
        synchronized (this.mLock) {
            SensorState sensorState2 =
                    (SensorState)
                            this.mPersistedState.mStates.get(
                                    new PersistedState.TypeUserSensor(i, i2, i3));
            sensorState = sensorState2 != null ? new SensorState(sensorState2) : new SensorState(2);
        }
        return sensorState;
    }

    public final void notifyStateChangeLocked(int i, int i2, int i3, SensorState sensorState) {
        SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2
                sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2;
        Handler handler = this.mListenerHandler;
        if (handler != null
                && (sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2 =
                                this.mListener)
                        != null) {
            handler.sendMessage(
                    PooledLambda.obtainMessage(
                            new SensorPrivacyStateControllerImpl$$ExternalSyntheticLambda0(),
                            sensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda2,
                            Integer.valueOf(i),
                            Integer.valueOf(i2),
                            Integer.valueOf(i3),
                            new SensorState(sensorState)));
        }
        schedulePersistLocked();
    }

    public final void schedulePersistLocked() {
        PersistedState persistedState = this.mPersistedState;
        int size = persistedState.mStates.size();
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < size; i++) {
            PersistedState.TypeUserSensor typeUserSensor =
                    (PersistedState.TypeUserSensor) persistedState.mStates.keyAt(i);
            arrayMap.put(
                    new PersistedState.TypeUserSensor(
                            typeUserSensor.mType, typeUserSensor.mUserId, typeUserSensor.mSensor),
                    new SensorState((SensorState) persistedState.mStates.valueAt(i)));
        }
        IoThread.getHandler()
                .sendMessage(
                        PooledLambda.obtainMessage(
                                new PersistedState$$ExternalSyntheticLambda0(),
                                persistedState,
                                arrayMap));
    }
}
