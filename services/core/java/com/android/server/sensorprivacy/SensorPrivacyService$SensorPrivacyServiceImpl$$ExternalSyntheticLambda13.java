package com.android.server.sensorprivacy;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.SensorPrivacyManager;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.sensorprivacy.SensorPrivacyService;
import java.io.Serializable;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SensorPrivacyService$SensorPrivacyServiceImpl$$ExternalSyntheticLambda13 implements BiConsumer {
    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        SensorPrivacyService.SensorPrivacyServiceImpl sensorPrivacyServiceImpl = (SensorPrivacyService.SensorPrivacyServiceImpl) obj;
        SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo sensorUseReminderDialogInfo = (SensorPrivacyService.SensorPrivacyServiceImpl.SensorUseReminderDialogInfo) obj2;
        sensorPrivacyServiceImpl.getClass();
        String str = SensorPrivacyService.ACTION_DISABLE_TOGGLE_SENSOR_PRIVACY;
        StringBuilder sb = new StringBuilder("showSensorUserReminderDialog  taskId=");
        sb.append(sensorUseReminderDialogInfo.mTaskId);
        sb.append("  mIsFolded=");
        sb.append(SensorPrivacyService.this.mIsFolded);
        sb.append("  ");
        String str2 = sensorUseReminderDialogInfo.mPackageName;
        BootReceiver$$ExternalSyntheticOutline0.m(sb, str2, "SensorPrivacyService");
        ArraySet arraySet = (ArraySet) sensorPrivacyServiceImpl.mQueuedSensorUseReminderDialogs.get(sensorUseReminderDialogInfo);
        sensorPrivacyServiceImpl.mQueuedSensorUseReminderDialogs.remove(sensorUseReminderDialogInfo);
        if (arraySet == null) {
            Log.e("SensorPrivacyService", "Unable to show sensor use dialog because sensor set is null. Was the dialog queue modified from outside the handler thread?");
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(ComponentName.unflattenFromString(sensorPrivacyServiceImpl.getSensorUseActivityName(arraySet)));
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        int i = sensorUseReminderDialogInfo.mTaskId;
        if (i == -1) {
            SensorPrivacyService sensorPrivacyService = SensorPrivacyService.this;
            makeBasic.setLaunchDisplayId((sensorPrivacyService.mIsFolded && sensorPrivacyService.mIsLargeCoverScreen) ? 1 : 0);
        } else {
            makeBasic.setLaunchTaskId(i);
        }
        makeBasic.setTaskOverlay(true, true);
        intent.addFlags(8650752);
        if (i == -1) {
            intent.addFlags(402653184);
        }
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str2);
        if (arraySet.size() == 1) {
            intent.putExtra(SensorPrivacyManager.EXTRA_SENSOR, (Serializable) arraySet.valueAt(0));
        } else {
            if (arraySet.size() != 2) {
                Log.e("SensorPrivacyService", "Attempted to show sensor use dialog for " + arraySet.size() + " sensors");
                return;
            }
            intent.putExtra(SensorPrivacyManager.EXTRA_ALL_SENSORS, true);
        }
        SensorPrivacyService.this.mContext.startActivityAsUser(intent, makeBasic.toBundle(), UserHandle.SYSTEM);
    }
}
