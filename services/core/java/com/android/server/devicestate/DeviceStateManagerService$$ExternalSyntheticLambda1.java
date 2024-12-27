package com.android.server.devicestate;

import android.R;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.util.Slog;
import com.android.server.devicestate.DeviceStateManagerService;
import com.android.server.devicestate.DeviceStateNotificationController;
import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DeviceStateManagerService$$ExternalSyntheticLambda1 {
    public final /* synthetic */ DeviceStateManagerService f$0;

    public void onStatusChanged(OverrideRequest overrideRequest, int i, int i2) {
        String str;
        String str2;
        String str3;
        String str4;
        DeviceStateManagerService deviceStateManagerService = this.f$0;
        deviceStateManagerService.getClass();
        int i3 = overrideRequest.mRequestType;
        int i4 = overrideRequest.mPid;
        if (i3 == 1) {
            if (i == 1) {
                deviceStateManagerService.setBaseState(overrideRequest.mRequestedState.getIdentifier());
                deviceStateManagerService.mActiveBaseStateOverride = Optional.of(overrideRequest);
                ((DeviceStateManagerService.ProcessRecord) deviceStateManagerService.mProcessRecords.get(i4)).notifyRequestActiveAsync(overrideRequest.mToken);
                return;
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown request status: "));
                }
                if (deviceStateManagerService.mActiveBaseStateOverride.isPresent() && deviceStateManagerService.mActiveBaseStateOverride.get() == overrideRequest) {
                    deviceStateManagerService.mActiveBaseStateOverride = Optional.empty();
                }
            }
        } else {
            if (i3 != 0) {
                throw new IllegalArgumentException("Unknown OverrideRest type: " + overrideRequest.mRequestType);
            }
            final DeviceStateNotificationController deviceStateNotificationController = deviceStateManagerService.mDeviceStateNotificationController;
            if (i == 1) {
                deviceStateManagerService.mActiveOverride = Optional.of(overrideRequest);
                int identifier = overrideRequest.mRequestedState.getIdentifier();
                DeviceStateNotificationController.NotificationInfo notificationInfo = (DeviceStateNotificationController.NotificationInfo) deviceStateNotificationController.getNotificationInfos().get(identifier);
                if (notificationInfo != null && (str = notificationInfo.activeNotificationTitle) != null && str.length() > 0) {
                    PackageManager packageManager = deviceStateNotificationController.mPackageManager;
                    int i5 = overrideRequest.mUid;
                    try {
                        str2 = deviceStateNotificationController.mPackageManager.getApplicationInfo(packageManager.getNameForUid(i5), PackageManager.ApplicationInfoFlags.of(0L)).loadLabel(deviceStateNotificationController.mPackageManager).toString();
                    } catch (PackageManager.NameNotFoundException unused) {
                        str2 = null;
                    }
                    if (str2 != null) {
                        deviceStateNotificationController.showNotification(notificationInfo.name, notificationInfo.activeNotificationTitle, String.format(notificationInfo.activeNotificationContent, str2), true, R.drawable.ic_input_extract_action_return, PendingIntent.getBroadcast(deviceStateNotificationController.mContext, 0, new Intent("com.android.server.devicestate.INTENT_ACTION_CANCEL_STATE").setPackage(deviceStateNotificationController.mContext.getPackageName()), 67108864), deviceStateNotificationController.mContext.getString(R.string.granularity_label_link));
                    } else {
                        Slog.e("DeviceStateNotificationController", "Cannot determine the requesting app name when showing state active notification. uid=" + i5 + ", state=" + identifier);
                    }
                }
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown request status: "));
                }
                if (deviceStateManagerService.mActiveOverride.isPresent() && deviceStateManagerService.mActiveOverride.get() == overrideRequest) {
                    deviceStateManagerService.mActiveOverride = Optional.empty();
                    if (deviceStateNotificationController.getNotificationInfos().get(overrideRequest.mRequestedState.getIdentifier()) != null) {
                        deviceStateNotificationController.mHandler.post(new Runnable() { // from class: com.android.server.devicestate.DeviceStateNotificationController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                DeviceStateNotificationController.this.mNotificationManager.cancel("DeviceStateManager", 1);
                            }
                        });
                    }
                    if ((i2 & 1) == 1) {
                        DeviceStateNotificationController.NotificationInfo notificationInfo2 = (DeviceStateNotificationController.NotificationInfo) deviceStateNotificationController.getNotificationInfos().get(overrideRequest.mRequestedState.getIdentifier());
                        if (notificationInfo2 != null && (str4 = notificationInfo2.thermalCriticalNotificationTitle) != null && str4.length() > 0) {
                            deviceStateNotificationController.showNotification(notificationInfo2.name, notificationInfo2.thermalCriticalNotificationTitle, notificationInfo2.thermalCriticalNotificationContent, false, R.drawable.jog_dial_bg, null, null);
                        }
                    } else if ((i2 & 2) == 2) {
                        DeviceStateNotificationController.NotificationInfo notificationInfo3 = (DeviceStateNotificationController.NotificationInfo) deviceStateNotificationController.getNotificationInfos().get(overrideRequest.mRequestedState.getIdentifier());
                        if (notificationInfo3 != null && (str3 = notificationInfo3.powerSaveModeNotificationTitle) != null && str3.length() > 0) {
                            deviceStateNotificationController.showNotification(notificationInfo3.name, notificationInfo3.powerSaveModeNotificationTitle, notificationInfo3.powerSaveModeNotificationContent, false, R.drawable.jog_dial_bg, PendingIntent.getActivity(deviceStateNotificationController.mContext, 0, new Intent("android.settings.BATTERY_SAVER_SETTINGS"), 67108864), deviceStateNotificationController.mContext.getString(R.string.granularity_label_line));
                        }
                    }
                }
            }
        }
        boolean updatePendingStateLocked = deviceStateManagerService.updatePendingStateLocked();
        DeviceStateManagerService.ProcessRecord processRecord = (DeviceStateManagerService.ProcessRecord) deviceStateManagerService.mProcessRecords.get(i4);
        Handler handler = deviceStateManagerService.mHandler;
        if (processRecord == null) {
            handler.post(new DeviceStateManagerService$$ExternalSyntheticLambda6(deviceStateManagerService, 2));
            return;
        }
        if (i != 1) {
            processRecord.notifyRequestCanceledAsync(overrideRequest.mToken);
        } else if (!updatePendingStateLocked && !deviceStateManagerService.mPendingState.isPresent()) {
            processRecord.notifyRequestActiveAsync(overrideRequest.mToken);
        }
        handler.post(new DeviceStateManagerService$$ExternalSyntheticLambda6(deviceStateManagerService, 2));
    }
}
