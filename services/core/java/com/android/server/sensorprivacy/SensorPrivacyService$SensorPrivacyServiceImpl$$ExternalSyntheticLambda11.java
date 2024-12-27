package com.android.server.sensorprivacy;

import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Pair;

import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda11
        implements QuintConsumer {
    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl =
                (SensorPrivacyService.SensorPrivacyServiceImpl) obj;
        int intValue = ((Integer) obj2).intValue();
        UserHandle userHandle = (UserHandle) obj3;
        Integer num = (Integer) obj5;
        int intValue2 = num.intValue();
        sensorPrivacyServiceImpl.getClass();
        SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo
                sensorUseReminderDialogInfo =
                        new SensorPrivacyService.SensorPrivacyServiceImpl
                                .SensorUseReminderDialogInfo();
        sensorUseReminderDialogInfo.mTaskId = intValue;
        sensorUseReminderDialogInfo.mUser = userHandle;
        sensorUseReminderDialogInfo.mPackageName = (String) obj4;
        if (sensorPrivacyServiceImpl.mQueuedSensorUseReminderDialogs.containsKey(
                sensorUseReminderDialogInfo)) {
            ((ArraySet)
                            sensorPrivacyServiceImpl.mQueuedSensorUseReminderDialogs.get(
                                    sensorUseReminderDialogInfo))
                    .add(num);
            return;
        }
        ArraySet arraySet = new ArraySet();
        if ((intValue2 == 1
                        && sensorPrivacyServiceImpl.mSuppressReminders.containsKey(
                                new Pair(2, userHandle)))
                || (intValue2 == 2
                        && sensorPrivacyServiceImpl.mSuppressReminders.containsKey(
                                new Pair(1, userHandle)))) {
            arraySet.add(1);
            arraySet.add(2);
        } else {
            arraySet.add(num);
        }
        sensorPrivacyServiceImpl.mQueuedSensorUseReminderDialogs.put(
                sensorUseReminderDialogInfo, arraySet);
        sensorPrivacyServiceImpl.mHandler.sendMessageDelayed(
                PooledLambda.obtainMessage(
                        new SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda13(),
                        sensorPrivacyServiceImpl,
                        sensorUseReminderDialogInfo),
                500L);
    }
}
