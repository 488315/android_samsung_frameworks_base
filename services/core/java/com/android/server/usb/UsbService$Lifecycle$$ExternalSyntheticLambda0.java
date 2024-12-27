package com.android.server.usb;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.IAudioService;
import android.os.FileUtils;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.sysfwutil.Slog;
import com.android.server.LocalServices;
import com.android.server.usb.UsbService;
import com.android.server.usb.hal.port.UsbPortHal;
import com.android.server.wm.ActivityTaskManagerInternal;
import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class UsbService$Lifecycle$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UsbService.Lifecycle f$0;

    public /* synthetic */ UsbService$Lifecycle$$ExternalSyntheticLambda0(UsbService.Lifecycle lifecycle, int i) {
        this.$r8$classId = i;
        this.f$0 = lifecycle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.os.IBinder, com.android.server.usb.UsbService] */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        UsbService.Lifecycle lifecycle = this.f$0;
        switch (i) {
            case 0:
                lifecycle.getClass();
                ?? usbService = new UsbService(lifecycle.getContext());
                lifecycle.mUsbService = usbService;
                lifecycle.publishBinderService("usb", usbService);
                lifecycle.mOnStartFinished.complete(null);
                return;
            default:
                lifecycle.mOnStartFinished.join();
                UsbService usbService2 = lifecycle.mUsbService;
                UsbUI usbUI = usbService2.mUsbUI;
                if (usbUI != null) {
                    Slog.d("UsbUI", "system ready");
                    usbUI.mNotificationManager = (NotificationManager) usbUI.mContext.getSystemService("notification");
                    ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(usbUI);
                    usbUI.mSystemReady = true;
                    try {
                        File file = new File("/sys/class/sec/ccic/water");
                        if (file.exists()) {
                            String trim = FileUtils.readTextFile(file, 0, null).trim();
                            Slog.d("UsbUI", "isUsbWet: state=" + trim);
                            if ("1".equals(trim)) {
                                usbUI.mIsUsbPortWet = true;
                                usbUI.notifyUsbWetDetection(true);
                            }
                        }
                    } catch (Exception e) {
                        Slog.e("UsbUI", "Can't read /sys/class/sec/ccic/water", e);
                    }
                    usbUI.mIsEmergencyMode = Settings.System.getIntForUser(usbUI.mContentResolver, "emergency_mode", 0, -2) != 0;
                }
                UsbAlsaManager usbAlsaManager = usbService2.mAlsaManager;
                usbAlsaManager.getClass();
                usbAlsaManager.mAudioService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
                usbAlsaManager.mAlsaObserver.startWatching();
                UsbDeviceManager usbDeviceManager = usbService2.mDeviceManager;
                if (usbDeviceManager != null) {
                    Slog.d("UsbDeviceManager", "systemReady");
                    ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(usbDeviceManager);
                    usbDeviceManager.mHandler.sendEmptyMessage(3);
                }
                final UsbHostManager usbHostManager = usbService2.mHostManager;
                if (usbHostManager != null) {
                    Slog.d("UsbHostManager", "systemReady");
                    synchronized (usbHostManager.mLock) {
                        new Thread(null, new Runnable() { // from class: com.android.server.usb.UsbHostManager$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                UsbHostManager.this.monitorUsbHostBus();
                            }
                        }, "UsbService host thread").start();
                    }
                    synchronized (usbHostManager.mPendingIntentLock) {
                        try {
                            usbHostManager.mSystemReady = true;
                            while (!usbHostManager.mPendingIntent.isEmpty()) {
                                usbHostManager.mContext.sendBroadcastAsUser((Intent) usbHostManager.mPendingIntent.remove(0), UserHandle.ALL);
                            }
                        } finally {
                        }
                    }
                }
                UsbPortManager usbPortManager = usbService2.mPortManager;
                if (usbPortManager != null) {
                    UsbPortHal usbPortHal = usbPortManager.mUsbPortHal;
                    if (usbPortHal != null) {
                        usbPortHal.systemReady();
                        try {
                            UsbPortHal usbPortHal2 = usbPortManager.mUsbPortHal;
                            long j = usbPortManager.mTransactionId + 1;
                            usbPortManager.mTransactionId = j;
                            usbPortHal2.queryPortStatus(j);
                        } catch (Exception e2) {
                            Slog.e("UsbPortManager", "ServiceStart: Failed to query port status", e2);
                        }
                    }
                    usbPortManager.mHandler.sendEmptyMessage(2);
                }
                UsbMonitorImpl usbMonitorImpl = usbService2.mUsbMonitorImpl;
                if (usbMonitorImpl != null) {
                    usbMonitorImpl.mHandler.sendEmptyMessage(0);
                }
                UsbHostRestrictor usbHostRestrictor = usbService2.mHostRestrictor;
                if (usbHostRestrictor != null) {
                    Slog.d("UsbHostRestrictor", "systemReady");
                    UsbHostRestrictor.mSettingBlockUsbLock = Settings.Secure.getInt(usbHostRestrictor.mContext.getContentResolver(), "block_usb_lock", 1);
                    ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(usbHostRestrictor);
                }
                lifecycle.mOnActivityManagerPhaseFinished.complete(null);
                return;
        }
    }
}
