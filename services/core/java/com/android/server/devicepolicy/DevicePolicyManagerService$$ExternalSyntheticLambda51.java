package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyEventLogger;
import android.app.admin.StartInstallingUpdateCallback;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.CrossProfileApps;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.FunctionalUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DevicePolicyManagerService$$ExternalSyntheticLambda51
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda51(
            DevicePolicyManagerService devicePolicyManagerService,
            ComponentName componentName,
            Bundle bundle) {
        this.$r8$classId = 0;
        this.f$0 = devicePolicyManagerService;
        this.f$1 = componentName;
        this.f$2 = bundle;
    }

    public /* synthetic */ DevicePolicyManagerService$$ExternalSyntheticLambda51(
            Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void runOrThrow() {
        final NonAbUpdateInstaller nonAbUpdateInstaller;
        switch (this.$r8$classId) {
            case 0:
                DevicePolicyManagerService devicePolicyManagerService =
                        (DevicePolicyManagerService) this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                Bundle bundle = (Bundle) this.f$2;
                if (devicePolicyManagerService.mTelephonyManager.getCallState() != 0) {
                    throw new IllegalStateException(
                            "Cannot be called with ongoing call on the device");
                }
                DevicePolicyEventLogger.createEvent(34)
                        .setAdmin(componentName)
                        .setKnoxBundleValue(bundle)
                        .write();
                ((PowerManager)
                                devicePolicyManagerService.mInjector.mContext.getSystemService(
                                        PowerManager.class))
                        .reboot("deviceowner");
                return;
            case 1:
                DevicePolicyManagerService devicePolicyManagerService2 =
                        (DevicePolicyManagerService) this.f$0;
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) this.f$1;
                StartInstallingUpdateCallback startInstallingUpdateCallback =
                        (StartInstallingUpdateCallback) this.f$2;
                devicePolicyManagerService2.getClass();
                if ("true".equalsIgnoreCase(SystemProperties.get("ro.build.ab_update", ""))) {
                    AbUpdateInstaller abUpdateInstaller =
                            new AbUpdateInstaller(
                                    devicePolicyManagerService2.mContext,
                                    parcelFileDescriptor,
                                    startInstallingUpdateCallback,
                                    devicePolicyManagerService2.mInjector,
                                    devicePolicyManagerService2.mConstants);
                    abUpdateInstaller.mUpdateInstalled = false;
                    nonAbUpdateInstaller = abUpdateInstaller;
                } else {
                    nonAbUpdateInstaller =
                            new NonAbUpdateInstaller(
                                    devicePolicyManagerService2.mContext,
                                    parcelFileDescriptor,
                                    startInstallingUpdateCallback,
                                    devicePolicyManagerService2.mInjector,
                                    devicePolicyManagerService2.mConstants);
                }
                nonAbUpdateInstaller.mCopiedUpdateFile = null;
                float intExtra = (r0.getIntExtra("level", -1) * 100) / r0.getIntExtra("scale", -1);
                int intExtra2 =
                        nonAbUpdateInstaller
                                .mContext
                                .registerReceiver(
                                        null,
                                        new IntentFilter("android.intent.action.BATTERY_CHANGED"))
                                .getIntExtra("plugged", -1);
                DevicePolicyConstants devicePolicyConstants = nonAbUpdateInstaller.mConstants;
                if (intExtra2 <= 0
                        ? intExtra >= devicePolicyConstants.BATTERY_THRESHOLD_NOT_CHARGING
                        : intExtra >= devicePolicyConstants.BATTERY_THRESHOLD_CHARGING) {
                    Thread thread =
                            new Thread(
                                    new Runnable() { // from class:
                                        // com.android.server.devicepolicy.UpdateInstaller$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            File file;
                                            UpdateInstaller updateInstaller = UpdateInstaller.this;
                                            updateInstaller.getClass();
                                            try {
                                                file =
                                                        File.createTempFile(
                                                                "update",
                                                                ".zip",
                                                                new File(
                                                                        Environment
                                                                                        .getDataDirectory()
                                                                                + "/ota_package"));
                                                FileUtils.setPermissions(file, 484, -1, -1);
                                                FileOutputStream fileOutputStream =
                                                        new FileOutputStream(file);
                                                try {
                                                    ParcelFileDescriptor.AutoCloseInputStream
                                                            autoCloseInputStream =
                                                                    new ParcelFileDescriptor
                                                                            .AutoCloseInputStream(
                                                                            updateInstaller
                                                                                    .mUpdateFileDescriptor);
                                                    try {
                                                        FileUtils.copy(
                                                                autoCloseInputStream,
                                                                fileOutputStream);
                                                        autoCloseInputStream.close();
                                                        fileOutputStream.close();
                                                    } finally {
                                                    }
                                                } finally {
                                                }
                                            } catch (IOException e) {
                                                Log.w(
                                                        "UpdateInstaller",
                                                        "Failed to copy update file to OTA"
                                                            + " directory",
                                                        e);
                                                updateInstaller.notifyCallbackOnError(
                                                        1, Log.getStackTraceString(e));
                                                file = null;
                                            }
                                            updateInstaller.mCopiedUpdateFile = file;
                                            if (file == null) {
                                                updateInstaller.notifyCallbackOnError(
                                                        1, "Error while copying file.");
                                            } else {
                                                updateInstaller.installUpdateInThread();
                                            }
                                        }
                                    });
                    thread.setPriority(10);
                    thread.start();
                    return;
                } else {
                    nonAbUpdateInstaller.notifyCallbackOnError(
                            5,
                            "The battery level must be above "
                                    + devicePolicyConstants.BATTERY_THRESHOLD_NOT_CHARGING
                                    + " while not charging or above "
                                    + devicePolicyConstants.BATTERY_THRESHOLD_CHARGING
                                    + " while charging");
                    return;
                }
            case 2:
                ((DevicePolicyManagerService) this.f$0)
                        .mUserManagerInternal.setUserIcon(
                                UserHandle.getUserId(((CallerIdentity) this.f$1).mUid),
                                (Bitmap) this.f$2);
                return;
            case 3:
                ((DevicePolicyManagerService) this.f$0)
                        .tryRetrieveAndSendLocationUpdate(
                                (ActiveAdmin) this.f$1,
                                (AndroidFuture) this.f$2,
                                new String[] {"fused", "network", "gps"},
                                0);
                return;
            default:
                ((CrossProfileApps) this.f$0)
                        .resetInteractAcrossProfilesAppOps(
                                (List) this.f$1, new HashSet((List) this.f$2));
                return;
        }
    }
}
