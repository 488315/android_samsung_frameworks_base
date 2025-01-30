package com.android.systemui.usb;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.UEventObserver;
import android.os.UserHandle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;
import android.preference.PreferenceManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import com.sec.ims.configuration.DATA;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StorageNotification implements CoreStartable {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final NotificationManager mNotificationManager;
    public SharedPreferences mSharedPreferences;
    public final StorageManager mStorageManager;
    public final Map mNotifyingVolumes = new ConcurrentHashMap();
    public final Map mMountedVolumes = new ConcurrentHashMap();
    public volatile int mCurrentUserId = 0;
    public final ArrayMap mPrevStatus = new ArrayMap();
    public final SparseArray mMoves = new SparseArray();
    public final StorageEventListenerC35271 mListener = new StorageEventListener() { // from class: com.android.systemui.usb.StorageNotification.1
        public final void onDiskDestroyed(DiskInfo diskInfo) {
            Log.d("StorageNotification", "onDiskDestroyed (" + diskInfo + ")");
            StorageNotification storageNotification = StorageNotification.this;
            storageNotification.getClass();
            storageNotification.mNotificationManager.cancelAsUser(diskInfo.getId(), 1396986699, UserHandle.ALL);
        }

        public final void onDiskScanned(DiskInfo diskInfo, int i) {
            Log.d("StorageNotification", "onDiskScanned : disk(" + diskInfo + "), volumeCount(" + i + ")");
            StorageNotification.this.onDiskScannedInternal(diskInfo, i);
        }

        public final void onVolumeForgotten(String str) {
            AbstractC0689x6838b71d.m68m("onVolumeForgotten (", str, ")", "StorageNotification");
            StorageNotification.this.mNotificationManager.cancelAsUser(str, 1397772886, UserHandle.ALL);
        }

        public final void onVolumeRecordChanged(VolumeRecord volumeRecord) {
            VolumeInfo findVolumeByUuid = StorageNotification.this.mStorageManager.findVolumeByUuid(volumeRecord.getFsUuid());
            if (findVolumeByUuid == null || !findVolumeByUuid.isMountedReadable()) {
                return;
            }
            Log.d("StorageNotification", "onVolumeRecordChanged : vol(" + findVolumeByUuid + "), disk(" + findVolumeByUuid.getDisk() + ")");
            StorageNotification.this.onVolumeStateChangedInternal(findVolumeByUuid);
        }

        public final void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
            StringBuilder sb = new StringBuilder("onVolumeStateChanged : vol(");
            sb.append(volumeInfo);
            sb.append("), disk(");
            sb.append(volumeInfo.getDisk());
            sb.append("), oldState(");
            sb.append(i);
            sb.append("), newState");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(sb, i2, ")", "StorageNotification");
            StorageNotification.this.onVolumeStateChangedInternal(volumeInfo);
        }
    };
    public final C35322 mSnoozeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            StorageNotification.this.mStorageManager.setVolumeSnoozed(intent.getStringExtra("android.os.storage.extra.FS_UUID"), true);
        }
    };
    public final C35333 mFinishReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.3
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mFinishReceiver (" + intent + ")");
            StorageNotification.this.mNotificationManager.cancelAsUser(null, 1397575510, UserHandle.ALL);
        }
    };
    public final C35344 mSDCardPolicyToastReceiver = new BroadcastReceiver(this) { // from class: com.android.systemui.usb.StorageNotification.4
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.intent.action.SDCARD_ITPOLICY_TOAST_EVENT".equals(intent.getAction())) {
                Log.d("StorageNotification", "SDCardPolicyToastReceiver onReceive!!");
                Toast.makeText(context, 17042333, 1).show();
            }
        }
    };
    public final C35355 mBadRemovalReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.5
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int i;
            int i2;
            Log.d("StorageNotification", "mBadRemovalReceiver (" + intent + ")");
            String stringExtra = intent.getStringExtra("message");
            if (stringExtra.equals("REBOOT")) {
                final StorageNotification storageNotification = StorageNotification.this;
                storageNotification.getClass();
                new Thread(new Runnable() { // from class: com.android.systemui.usb.StorageNotification.13
                    @Override // java.lang.Runnable
                    public final void run() {
                        Notification.Builder addAction = new Notification.Builder(StorageNotification.this.mContext, "ALR").setSmallIcon(R.drawable.stat_notify_sdcard_usb).setColor(StorageNotification.this.mContext.getColor(R.color.system_notification_accent_color)).setPriority(2).setVisibility(1).setShowWhen(false).setDefaults(-1).setOnlyAlertOnce(true).setOngoing(true).addAction(new Notification.Action(0, StorageNotification.this.mContext.getString(R.string.accessibility_edit_shortcut_menu_volume_title), PendingIntent.getBroadcastAsUser(StorageNotification.this.mContext, 0, new Intent("com.samsung.intent.action.RESTART_OF_SDCARDBADREMOVED_HASAPK"), 1140850688, UserHandle.ALL)));
                        for (int i3 = 15; i3 >= 0; i3--) {
                            String quantityString = StorageNotification.this.mContext.getResources().getQuantityString(R.plurals.bugreport_countdown, i3, Integer.valueOf(i3));
                            String quantityString2 = StorageNotification.this.mContext.getResources().getQuantityString(R.plurals.pinpuk_attempts, i3, Integer.valueOf(i3));
                            addAction.setContentTitle(quantityString).setContentText(quantityString2).setTicker(quantityString).setStyle(new Notification.BigTextStyle().bigText(quantityString2));
                            StorageNotification.this.mNotificationManager.notify(105, addAction.build());
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException unused) {
                                Log.d("StorageNotification", "sleep failure");
                            }
                        }
                    }
                }).start();
                return;
            }
            if (stringExtra.contains("BAD_REMOVAL")) {
                StorageNotification storageNotification2 = StorageNotification.this;
                Context context2 = storageNotification2.mContext;
                String string = context2.getString(R.string.js_dialog_before_unload_title);
                String string2 = context2.getString(17042530);
                if (stringExtra.contains(PeripheralConstants.ConnectivityType.USB)) {
                    string2 = context2.getString(17043103);
                    i = 17304253;
                    i2 = 122;
                } else {
                    i = R.drawable.stat_notify_sdcard;
                    i2 = 121;
                }
                storageNotification2.mNotificationManager.notifyAsUser(null, i2, new Notification.Builder(context2, "ALR").setContentTitle(string).setContentText(string2).setTicker(string).setSmallIcon(i).setColor(context2.getColor(R.color.system_notification_accent_color)).setPriority(2).setVisibility(1).setShowWhen(false).setStyle(new Notification.BigTextStyle().bigText(string2)).addAction(new Notification.Action(0, context2.getString(R.string.js_dialog_before_unload_positive_button), PendingIntent.getBroadcastAsUser(context2, 0, new Intent("com.samsung.intent.action.RESTART_OF_SDCARDBADREMOVED_HASAPK"), 1140850688, UserHandle.ALL))).build(), UserHandle.ALL);
                Log.d("StorageNotification", "notifyAsUser: title=" + ((Object) string));
            }
        }
    };
    public final C35366 mEmergencyModeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.6
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mEmergencyModeReceiver (" + intent + ")");
            int intExtra = intent.getIntExtra("reason", 0);
            if (intExtra == 3) {
                for (VolumeInfo volumeInfo : ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).values()) {
                    StorageNotification.this.getClass();
                    String tagForVolumeInfo = StorageNotification.getTagForVolumeInfo(volumeInfo);
                    ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).remove(tagForVolumeInfo);
                    StorageNotification.this.mNotificationManager.cancelAsUser(tagForVolumeInfo, 1397773634, UserHandle.ALL);
                    Log.d("StorageNotification", "cancelAsUser: Emergency Mode enabled, tag=".concat(tagForVolumeInfo));
                }
                return;
            }
            if (intExtra == 5) {
                ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).clear();
                ((ConcurrentHashMap) StorageNotification.this.mMountedVolumes).clear();
                for (VolumeInfo volumeInfo2 : StorageNotification.this.mStorageManager.getVolumes()) {
                    if (volumeInfo2.getMountUserId() == StorageNotification.this.mCurrentUserId && volumeInfo2.getType() == 0) {
                        StorageNotification.this.onVolumeStateChangedInternal(volumeInfo2);
                    }
                }
            }
        }
    };
    public final C35377 mNotiDeleteReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.7
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mNotiDeleteReceiver (" + intent + ")");
            if (intent.getIntExtra("NOTE_ID", 0) == 1397773634) {
                String stringExtra = intent.getStringExtra("NOTE_TAG");
                VolumeInfo volumeInfo = (VolumeInfo) ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).get(stringExtra);
                VolumeInfo volumeInfo2 = null;
                String fsUuid = volumeInfo != null ? volumeInfo.getFsUuid() : null;
                String string = StorageNotification.this.mSharedPreferences.getString("persist.systemUI.sdUUID", "none");
                StorageNotification.this.mSharedPreferences.getString("persist.systemUI.usbUUID", "none");
                ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).remove(stringExtra);
                List volumes = StorageNotification.this.mStorageManager.getVolumes();
                Collections.sort(volumes, VolumeInfo.getDescriptionComparator());
                if (!stringExtra.equals("public:179")) {
                    if (stringExtra.equals("public:8")) {
                        Log.d("StorageNotification", "USB Memory Noti is deleted.");
                        if (fsUuid == null) {
                            Log.d("StorageNotification", "mNotiDeleteReceiver do Nothing for USB MEMORY UUID NO VALUE");
                            return;
                        }
                        AbstractC0689x6838b71d.m68m("mNotiDeleteReceiver Set STORAGE_NOTIFICATION_USB_MEMORY_UUID with Current USB UUID[", fsUuid, "]", "StorageNotification");
                        SharedPreferences.Editor edit = StorageNotification.this.mSharedPreferences.edit();
                        edit.putString("persist.systemUI.usbUUID", fsUuid);
                        edit.commit();
                        return;
                    }
                    return;
                }
                Log.d("StorageNotification", "SD Card Noti is deleted.");
                Iterator it = volumes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    VolumeInfo volumeInfo3 = (VolumeInfo) it.next();
                    if (volumeInfo3 != null && volumeInfo3.getMountUserId() == StorageNotification.this.mCurrentUserId && volumeInfo3.getType() == 0 && volumeInfo3.getDisk() != null && volumeInfo3.getDisk().isSd()) {
                        volumeInfo2 = volumeInfo3;
                        break;
                    }
                }
                if (volumeInfo2 != null) {
                    Log.d("StorageNotification", "SD Card is NOT removed.");
                    fsUuid = volumeInfo2.getFsUuid();
                } else if (volumeInfo2 != null || fsUuid == null) {
                    fsUuid = string;
                } else {
                    Log.d("StorageNotification", "SD Card is removed.");
                }
                if (fsUuid == null) {
                    Log.d("StorageNotification", "mNotiDeleteReceiver do Nothing for SD Card UUID NO VALUE");
                    return;
                }
                AbstractC0689x6838b71d.m68m("mNotiDeleteReceiver Set STORAGE_NOTIFICATION_SD_CARD_UUID with Current SD Card UUID[", fsUuid, "]", "StorageNotification");
                SharedPreferences.Editor edit2 = StorageNotification.this.mSharedPreferences.edit();
                edit2.putString("persist.systemUI.sdUUID", fsUuid);
                edit2.commit();
            }
        }
    };
    public final C35388 mLocalechangedReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.8
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mLocalechangedReceiver (" + intent + ")");
            Iterator it = ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).values().iterator();
            while (it.hasNext()) {
                StorageNotification.this.onVolumeStateChangedInternal((VolumeInfo) it.next());
            }
        }
    };
    public final C35399 mUserReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.9
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mUserReceiver (" + intent + ")");
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            if (intExtra >= 0 && "android.intent.action.USER_SWITCHED".equals(action)) {
                StorageNotification.this.mCurrentUserId = intExtra;
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("Update mCurrentUserId="), StorageNotification.this.mCurrentUserId, "StorageNotification");
                for (VolumeInfo volumeInfo : ((ConcurrentHashMap) StorageNotification.this.mMountedVolumes).values()) {
                    DiskInfo disk = volumeInfo.getDisk();
                    if (disk != null && disk.isUsb() && volumeInfo.getMountUserId() != StorageNotification.this.mCurrentUserId) {
                        ((ConcurrentHashMap) StorageNotification.this.mMountedVolumes).remove(volumeInfo.getFsUuid());
                    }
                }
                for (VolumeInfo volumeInfo2 : ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).values()) {
                    if (volumeInfo2.getMountUserId() != StorageNotification.this.mCurrentUserId) {
                        StorageNotification.this.getClass();
                        String tagForVolumeInfo = StorageNotification.getTagForVolumeInfo(volumeInfo2);
                        ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).remove(tagForVolumeInfo);
                        StorageNotification.this.mNotificationManager.cancelAsUser(tagForVolumeInfo, 1397773634, UserHandle.ALL);
                        Log.d("StorageNotification", "cancelAsUser: User switched, tag=".concat(tagForVolumeInfo));
                    }
                }
            }
        }
    };
    public final UEventObserverC352810 mUEventObserver = new UEventObserver() { // from class: com.android.systemui.usb.StorageNotification.10
        public final void onUEvent(UEventObserver.UEvent uEvent) {
            uEvent.toString();
            if ("YES".equals(uEvent.get("IOERROR"))) {
                Log.d("StorageNotification", "SDcard I/O error uevent is occured.");
                StorageNotification.this.showSDcardErrorNoti(true);
            }
        }
    };
    public final UEventObserverC352911 mROMountUEventObserver = new UEventObserver() { // from class: com.android.systemui.usb.StorageNotification.11
        public final void onUEvent(UEventObserver.UEvent uEvent) {
            uEvent.toString();
            String str = uEvent.get("MAJOR");
            if ("179".equals(str)) {
                Log.d("StorageNotification", "SDcard Mounted as Read-Only UEVENT.");
                StorageNotification.this.showExtStorageReadOnlyMountNoti("sd", true);
            } else if (!DATA.DM_FIELD_INDEX.SIP_SESSION_TIMER.equals(str)) {
                Log.d("StorageNotification", "WRONG MAJOR Value at Read-Only UEVENT");
            } else {
                Log.d("StorageNotification", "USB MEMORY Mounted as Read-Only UEVENT.");
                StorageNotification.this.showExtStorageReadOnlyMountNoti("usb", true);
            }
        }
    };
    public final C353012 mMoveCallback = new PackageManager.MoveCallback() { // from class: com.android.systemui.usb.StorageNotification.12
        public final void onCreated(int i, Bundle bundle) {
            Log.d("StorageNotification", "mMoveCallback (" + i + ")");
            MoveInfo moveInfo = new MoveInfo(0);
            moveInfo.moveId = i;
            if (bundle != null) {
                moveInfo.packageName = bundle.getString("android.intent.extra.PACKAGE_NAME");
                moveInfo.label = bundle.getString("android.intent.extra.TITLE");
                moveInfo.volumeUuid = bundle.getString("android.os.storage.extra.FS_UUID");
            }
            StorageNotification.this.mMoves.put(i, moveInfo);
            StorageNotification.this.mPrevStatus.put(Integer.valueOf(moveInfo.moveId), -1);
        }

        public final void onStatusChanged(int i, int i2, long j) {
            PendingIntent activityAsUser;
            String string;
            String string2;
            PendingIntent activityAsUser2;
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("mMoveCallback (", i, ", ", i2, ", ");
            m45m.append(j);
            m45m.append(")");
            Log.d("StorageNotification", m45m.toString());
            MoveInfo moveInfo = (MoveInfo) StorageNotification.this.mMoves.get(i);
            if (moveInfo == null) {
                IconCompat$$ExternalSyntheticOutline0.m30m("Ignoring unknown move ", i, "StorageNotification");
                return;
            }
            if (!PackageManager.isMoveStatusFinished(i2)) {
                if (((Integer) StorageNotification.this.mPrevStatus.get(Integer.valueOf(i))).intValue() != i2) {
                    StorageNotification storageNotification = StorageNotification.this;
                    storageNotification.getClass();
                    Log.d("StorageNotification", "onMoveProgress (" + moveInfo + ", " + i2 + ", " + j + ")");
                    boolean isEmpty = TextUtils.isEmpty(moveInfo.label);
                    Context context = storageNotification.mContext;
                    String string3 = !isEmpty ? context.getString(R.string.indeterminate_progress_43, moveInfo.label) : context.getString(R.string.indeterminate_progress_46);
                    CharSequence formatDuration = j < 0 ? null : DateUtils.formatDuration(j);
                    if (moveInfo.packageName != null) {
                        Intent intent = new Intent();
                        if (storageNotification.isTv()) {
                            intent.setPackage("com.android.tv.settings");
                            intent.setAction("com.android.tv.settings.action.MOVE_APP");
                        } else {
                            if (!storageNotification.isAutomotive()) {
                                intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardMoveProgress");
                            }
                            activityAsUser = null;
                        }
                        intent.putExtra("android.content.pm.extra.MOVE_ID", moveInfo.moveId);
                        activityAsUser = PendingIntent.getActivityAsUser(storageNotification.mContext, moveInfo.moveId, intent, 335544320, null, UserHandle.CURRENT);
                    } else {
                        Intent intent2 = new Intent();
                        if (storageNotification.isTv()) {
                            intent2.setPackage("com.android.tv.settings");
                            intent2.setAction("com.android.tv.settings.action.MIGRATE_STORAGE");
                        } else {
                            if (!storageNotification.isAutomotive()) {
                                intent2.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardMigrateProgress");
                            }
                            activityAsUser = null;
                        }
                        intent2.putExtra("android.content.pm.extra.MOVE_ID", moveInfo.moveId);
                        VolumeInfo findVolumeByQualifiedUuid = storageNotification.mStorageManager.findVolumeByQualifiedUuid(moveInfo.volumeUuid);
                        if (findVolumeByQualifiedUuid != null) {
                            intent2.putExtra("android.os.storage.extra.VOLUME_ID", findVolumeByQualifiedUuid.getId());
                        }
                        activityAsUser = PendingIntent.getActivityAsUser(storageNotification.mContext, moveInfo.moveId, intent2, 335544320, null, UserHandle.CURRENT);
                    }
                    Notification.Builder showWhen = new Notification.Builder(context, "DSK").setSmallIcon(R.drawable.stat_notify_sdcard).setColor(context.getColor(R.color.system_notification_accent_color)).setContentTitle(string3).setContentText(formatDuration).setContentIntent(activityAsUser).setStyle(new Notification.BigTextStyle().bigText(formatDuration)).setVisibility(1).setLocalOnly(true).setCategory("progress").setPriority(-1).setProgress(100, i2, false).setOngoing(true).setShowWhen(false);
                    SystemUIApplication.overrideNotificationAppName(context, showWhen, false);
                    storageNotification.mNotificationManager.notifyAsUser(moveInfo.packageName, 1397575510, showWhen.build(), UserHandle.ALL);
                    storageNotification.mPrevStatus.put(Integer.valueOf(moveInfo.moveId), Integer.valueOf(i2));
                    return;
                }
                return;
            }
            StorageNotification storageNotification2 = StorageNotification.this;
            storageNotification2.getClass();
            StringBuilder sb = new StringBuilder("onMoveFinished (");
            sb.append(moveInfo);
            sb.append(", ");
            sb.append(i2);
            ExifInterface$$ExternalSyntheticOutline0.m35m(sb, ")", "StorageNotification");
            String str = moveInfo.packageName;
            NotificationManager notificationManager = storageNotification2.mNotificationManager;
            if (str != null) {
                notificationManager.cancelAsUser(str, 1397575510, UserHandle.ALL);
            } else {
                Context context2 = storageNotification2.mContext;
                VolumeInfo primaryStorageCurrentVolume = context2.getPackageManager().getPrimaryStorageCurrentVolume();
                String bestVolumeDescription = storageNotification2.mStorageManager.getBestVolumeDescription(primaryStorageCurrentVolume);
                if (i2 == -100) {
                    string = context2.getString(R.string.indeterminate_progress_45);
                    string2 = context2.getString(R.string.indeterminate_progress_44, bestVolumeDescription);
                } else {
                    string = context2.getString(R.string.indeterminate_progress_42);
                    string2 = context2.getString(R.string.indeterminate_progress_41);
                }
                if (primaryStorageCurrentVolume == null || primaryStorageCurrentVolume.getDisk() == null) {
                    if (primaryStorageCurrentVolume != null) {
                        Intent intent3 = new Intent();
                        if (storageNotification2.isTv()) {
                            intent3.setPackage("com.android.tv.settings");
                            intent3.setAction("android.settings.INTERNAL_STORAGE_SETTINGS");
                        } else if (!storageNotification2.isAutomotive()) {
                            int type = primaryStorageCurrentVolume.getType();
                            if (type == 0) {
                                intent3.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$PublicVolumeSettingsActivity");
                            } else if (type == 1) {
                                intent3.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$PrivateVolumeSettingsActivity");
                            }
                        }
                        intent3.putExtra("android.os.storage.extra.VOLUME_ID", primaryStorageCurrentVolume.getId());
                        activityAsUser2 = PendingIntent.getActivityAsUser(storageNotification2.mContext, primaryStorageCurrentVolume.getId().hashCode(), intent3, 335544320, null, UserHandle.CURRENT);
                        Notification.Builder showWhen2 = new Notification.Builder(context2, "DSK").setSmallIcon(R.drawable.stat_notify_sdcard).setColor(context2.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2).setContentIntent(activityAsUser2).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setPriority(-1).setAutoCancel(true).setShowWhen(false);
                        SystemUIApplication.overrideNotificationAppName(context2, showWhen2, false);
                        notificationManager.notifyAsUser(moveInfo.packageName, 1397575510, showWhen2.build(), UserHandle.ALL);
                    }
                    activityAsUser2 = null;
                    Notification.Builder showWhen22 = new Notification.Builder(context2, "DSK").setSmallIcon(R.drawable.stat_notify_sdcard).setColor(context2.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2).setContentIntent(activityAsUser2).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setPriority(-1).setAutoCancel(true).setShowWhen(false);
                    SystemUIApplication.overrideNotificationAppName(context2, showWhen22, false);
                    notificationManager.notifyAsUser(moveInfo.packageName, 1397575510, showWhen22.build(), UserHandle.ALL);
                } else {
                    DiskInfo disk = primaryStorageCurrentVolume.getDisk();
                    Intent intent4 = new Intent();
                    if (storageNotification2.isTv()) {
                        intent4.setPackage("com.android.tv.settings");
                        intent4.setAction("android.settings.INTERNAL_STORAGE_SETTINGS");
                    } else {
                        if (!storageNotification2.isAutomotive()) {
                            intent4.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardReady");
                        }
                        activityAsUser2 = null;
                        Notification.Builder showWhen222 = new Notification.Builder(context2, "DSK").setSmallIcon(R.drawable.stat_notify_sdcard).setColor(context2.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2).setContentIntent(activityAsUser2).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setPriority(-1).setAutoCancel(true).setShowWhen(false);
                        SystemUIApplication.overrideNotificationAppName(context2, showWhen222, false);
                        notificationManager.notifyAsUser(moveInfo.packageName, 1397575510, showWhen222.build(), UserHandle.ALL);
                    }
                    intent4.putExtra("android.os.storage.extra.DISK_ID", disk.getId());
                    activityAsUser2 = PendingIntent.getActivityAsUser(storageNotification2.mContext, disk.getId().hashCode(), intent4, 335544320, null, UserHandle.CURRENT);
                    Notification.Builder showWhen2222 = new Notification.Builder(context2, "DSK").setSmallIcon(R.drawable.stat_notify_sdcard).setColor(context2.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2).setContentIntent(activityAsUser2).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setPriority(-1).setAutoCancel(true).setShowWhen(false);
                    SystemUIApplication.overrideNotificationAppName(context2, showWhen2222, false);
                    notificationManager.notifyAsUser(moveInfo.packageName, 1397575510, showWhen2222.build(), UserHandle.ALL);
                }
            }
            StorageNotification.this.mPrevStatus.remove(Integer.valueOf(i));
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MoveInfo {
        public String label;
        public int moveId;
        public String packageName;
        public String volumeUuid;

        private MoveInfo() {
        }

        public /* synthetic */ MoveInfo(int i) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.usb.StorageNotification$6] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.usb.StorageNotification$7] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.systemui.usb.StorageNotification$8] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.systemui.usb.StorageNotification$9] */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.systemui.usb.StorageNotification$10] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.android.systemui.usb.StorageNotification$11] */
    /* JADX WARN: Type inference failed for: r0v16, types: [com.android.systemui.usb.StorageNotification$12] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.usb.StorageNotification$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.usb.StorageNotification$2] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.usb.StorageNotification$3] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.usb.StorageNotification$4] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.usb.StorageNotification$5] */
    public StorageNotification(Context context, BroadcastDispatcher broadcastDispatcher, NotificationManager notificationManager, StorageManager storageManager) {
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mNotificationManager = notificationManager;
        this.mStorageManager = storageManager;
    }

    public static String getTagForVolumeInfo(VolumeInfo volumeInfo) {
        DiskInfo disk = volumeInfo.getDisk();
        return disk != null ? disk.isSd() ? "public:179" : disk.isUsb() ? "public:8" : "unknown" : "unknown";
    }

    public final Notification.Builder buildNotificationBuilder(VolumeInfo volumeInfo, CharSequence charSequence, CharSequence charSequence2) {
        Context context = this.mContext;
        Notification.Builder builder = new Notification.Builder(context, "DSK");
        DiskInfo disk = volumeInfo.getDisk();
        volumeInfo.getState();
        boolean isSd = disk.isSd();
        int i = R.drawable.stat_notify_sdcard;
        if (!isSd && disk.isUsb()) {
            i = 17304253;
        }
        Notification.Builder extend = builder.setSmallIcon(i).setColor(context.getColor(R.color.system_notification_accent_color)).setContentTitle(charSequence).setContentText(charSequence2).setStyle(new Notification.BigTextStyle().bigText(charSequence2)).setVisibility(1).setShowWhen(false).setLocalOnly(true).extend(new Notification.TvExtender());
        SystemUIApplication.overrideNotificationAppName(context, extend, false);
        return extend;
    }

    public final PendingIntent buildUnmountPendingIntent(VolumeInfo volumeInfo) {
        Intent intent = new Intent();
        if (isTv()) {
            intent.setPackage("com.android.tv.settings");
            intent.setAction("com.android.tv.settings.action.UNMOUNT_STORAGE");
            intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
            return PendingIntent.getActivityAsUser(this.mContext, volumeInfo.getId().hashCode(), intent, 335544320, null, UserHandle.CURRENT);
        }
        boolean isAutomotive = isAutomotive();
        Context context = this.mContext;
        if (isAutomotive) {
            intent.setClassName("com.android.car.settings", "com.android.car.settings.storage.StorageUnmountReceiver");
            intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
            return PendingIntent.getBroadcastAsUser(context, volumeInfo.getId().hashCode(), intent, 335544320, UserHandle.CURRENT);
        }
        intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageUnmountReceiver");
        intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
        return PendingIntent.getBroadcastAsUser(context, volumeInfo.getId().hashCode(), intent, 335544320, UserHandle.CURRENT);
    }

    public final boolean isAutomotive() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }

    public final boolean isTv() {
        return this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
    }

    public final void onDiskScannedInternal(DiskInfo diskInfo, int i) {
        PendingIntent pendingIntent;
        NotificationManager notificationManager = this.mNotificationManager;
        if (i != 0 || diskInfo.size <= 0) {
            notificationManager.cancelAsUser(diskInfo.getId(), 1396986699, UserHandle.ALL);
            return;
        }
        Object[] objArr = {diskInfo.getDescription()};
        Context context = this.mContext;
        String string = context.getString(R.string.js_dialog_before_unload_negative_button, objArr);
        String string2 = context.getString(R.string.js_dialog_before_unload, diskInfo.getDescription());
        Notification.Builder builder = new Notification.Builder(context, "DSK");
        boolean isSd = diskInfo.isSd();
        int i2 = R.drawable.stat_notify_sdcard;
        if (!isSd && diskInfo.isUsb()) {
            i2 = 17304253;
        }
        Notification.Builder contentText = builder.setSmallIcon(i2).setColor(context.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2);
        Intent intent = new Intent();
        if (isTv()) {
            intent.setPackage("com.android.tv.settings");
            intent.setAction("com.android.tv.settings.action.NEW_STORAGE");
        } else {
            if (isAutomotive()) {
                pendingIntent = null;
                Notification.Builder showWhen = contentText.setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("err").extend(new Notification.TvExtender()).setShowWhen(false);
                SystemUIApplication.overrideNotificationAppName(context, showWhen, false);
                notificationManager.notifyAsUser(diskInfo.getId(), 1396986699, showWhen.build(), UserHandle.ALL);
            }
            intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardInit");
        }
        intent.putExtra("android.os.storage.extra.DISK_ID", diskInfo.getId());
        pendingIntent = PendingIntent.getActivityAsUser(this.mContext, diskInfo.getId().hashCode(), intent, 335544320, null, UserHandle.CURRENT);
        Notification.Builder showWhen2 = contentText.setContentIntent(pendingIntent).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("err").extend(new Notification.TvExtender()).setShowWhen(false);
        SystemUIApplication.overrideNotificationAppName(context, showWhen2, false);
        notificationManager.notifyAsUser(diskInfo.getId(), 1396986699, showWhen2.build(), UserHandle.ALL);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0380 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onVolumeStateChangedInternal(VolumeInfo volumeInfo) {
        boolean isUsb;
        String str;
        int state;
        String str2;
        DiskInfo diskInfo;
        Notification build;
        Log.d("StorageNotification", "onVolumeStateChangedInternal (" + volumeInfo.path + ")");
        int type = volumeInfo.getType();
        if (type == 0) {
            Log.d("StorageNotification", "Notifying about public volume: " + volumeInfo.toString());
            if (volumeInfo.getMountUserId() != -10000) {
                if (volumeInfo.getDisk() != null) {
                    UserHandle of = UserHandle.of(volumeInfo.getMountUserId());
                    String tagForVolumeInfo = getTagForVolumeInfo(volumeInfo);
                    String str3 = volumeInfo.getDisk().isSd() ? "sd" : "usb";
                    String fsUuid = volumeInfo.getFsUuid();
                    int state2 = volumeInfo.getState();
                    boolean z = false;
                    Notification notification2 = null;
                    notification2 = null;
                    notification2 = null;
                    notification2 = null;
                    notification2 = null;
                    notification2 = null;
                    PendingIntent activityAsUser = null;
                    notification2 = null;
                    notification2 = null;
                    notification2 = null;
                    r12 = null;
                    r12 = null;
                    String str4 = null;
                    notification2 = null;
                    notification2 = null;
                    Context context = this.mContext;
                    switch (state2) {
                        case 0:
                            if (volumeInfo.getDisk().isSd()) {
                                showSDcardErrorNoti(false);
                            }
                            showExtStorageReadOnlyMountNoti(str3, false);
                            isUsb = volumeInfo.getDisk().isUsb();
                            Map map = this.mNotifyingVolumes;
                            if (isUsb && fsUuid != null) {
                                state = volumeInfo.getState();
                                if (state != 7 || state == 8) {
                                    return;
                                }
                                VolumeInfo volumeInfo2 = (VolumeInfo) ((ConcurrentHashMap) map).get(tagForVolumeInfo);
                                Map map2 = this.mMountedVolumes;
                                if (volumeInfo2 != null) {
                                    if (!fsUuid.equals(volumeInfo2.getFsUuid()) && ((ConcurrentHashMap) map2).get(fsUuid) != null) {
                                        updateMountedVolumes(volumeInfo);
                                        return;
                                    }
                                } else if (((ConcurrentHashMap) map2).get(fsUuid) != null) {
                                    updateMountedVolumes(volumeInfo);
                                    return;
                                }
                            }
                            updateMountedVolumes(volumeInfo);
                            NotificationManager notificationManager = this.mNotificationManager;
                            if (notification2 != null) {
                                if (fsUuid != null) {
                                    ((ConcurrentHashMap) map).remove(tagForVolumeInfo);
                                    notificationManager.cancelAsUser(tagForVolumeInfo, 1397773634, of);
                                    Log.d("StorageNotification", "cancelAsUser: Finally, tag=".concat(tagForVolumeInfo));
                                    return;
                                }
                                return;
                            }
                            if (SemEmergencyManager.isEmergencyMode(context)) {
                                Log.d("StorageNotification", "Emergency Mode now");
                                return;
                            }
                            String string = this.mSharedPreferences.getString("persist.systemUI.sdUUID", "none");
                            String string2 = this.mSharedPreferences.getString("persist.systemUI.usbUUID", "none");
                            if (fsUuid != null) {
                                if (volumeInfo.getDisk().isSd() && fsUuid.equalsIgnoreCase(string)) {
                                    AbstractC0000x2c234b15.m3m("Current SD card UUID is same as ", string, "StorageNotification");
                                    return;
                                } else if (volumeInfo.getDisk().isUsb() && fsUuid.equalsIgnoreCase(string2)) {
                                    AbstractC0000x2c234b15.m3m("Current USB Memory UUID is same as ", string2, "StorageNotification");
                                    return;
                                }
                            }
                            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) map;
                            Iterator it = concurrentHashMap.values().iterator();
                            while (it.hasNext()) {
                                if (tagForVolumeInfo.equals(getTagForVolumeInfo((VolumeInfo) it.next()))) {
                                    concurrentHashMap.remove(tagForVolumeInfo);
                                    notificationManager.cancelAsUser(tagForVolumeInfo, 1397773634, of);
                                    Log.d("StorageNotification", "cancelAsUser: Notifying volume, tag=".concat(tagForVolumeInfo));
                                }
                            }
                            if (fsUuid != null) {
                                Intent intent = new Intent("com.samsung.systemui.action.STORAGE_NOTIFICATION_CANCEL");
                                intent.putExtra("NOTE_TAG", tagForVolumeInfo);
                                str = "persist.systemUI.usbUUID";
                                intent.putExtra("NOTE_ID", 1397773634);
                                notification2.deleteIntent = PendingIntent.getBroadcastAsUser(context, fsUuid.hashCode(), intent, 335544320, UserHandle.ALL);
                                concurrentHashMap.put(tagForVolumeInfo, volumeInfo);
                                notificationManager.notifyAsUser(tagForVolumeInfo, 1397773634, notification2, of);
                                Log.d("StorageNotification", "notifyAsUser: Finally, tag=".concat(tagForVolumeInfo));
                            } else {
                                str = "persist.systemUI.usbUUID";
                            }
                            if (z) {
                                if (volumeInfo.getDisk().isSd()) {
                                    Log.d("StorageNotification", "Set STORAGE_NOTIFICATION_SD_CARD_UUID as NONE");
                                    SharedPreferences.Editor edit = this.mSharedPreferences.edit();
                                    edit.putString("persist.systemUI.sdUUID", "none");
                                    edit.commit();
                                    return;
                                }
                                if (volumeInfo.getDisk().isUsb()) {
                                    Log.d("StorageNotification", "Set STORAGE_NOTIFICATION_USB_MEMORY_UUID as NONE");
                                    SharedPreferences.Editor edit2 = this.mSharedPreferences.edit();
                                    edit2.putString(str, "none");
                                    edit2.commit();
                                    return;
                                }
                                return;
                            }
                            return;
                        case 1:
                            DiskInfo disk = volumeInfo.getDisk();
                            z = false;
                            notification2 = buildNotificationBuilder(volumeInfo, context.getString(R.string.indeterminate_progress_37, disk.getDescription()), context.getString(R.string.indeterminate_progress_36, disk.getDescription())).setCategory("progress").setPriority(-1).setOngoing(false).setShowWhen(false).build();
                            isUsb = volumeInfo.getDisk().isUsb();
                            Map map3 = this.mNotifyingVolumes;
                            if (isUsb) {
                                state = volumeInfo.getState();
                                if (state != 7) {
                                    return;
                                } else {
                                    return;
                                }
                            }
                            updateMountedVolumes(volumeInfo);
                            NotificationManager notificationManager2 = this.mNotificationManager;
                            if (notification2 != null) {
                            }
                            break;
                        case 2:
                        case 3:
                            if (volumeInfo.getFsUuid() == null) {
                                Log.d("StorageNotification", "onVolumeMounted(): FsUuid is null");
                            } else {
                                VolumeRecord findRecordByUuid = this.mStorageManager.findRecordByUuid(volumeInfo.getFsUuid());
                                DiskInfo disk2 = volumeInfo.getDisk();
                                if (findRecordByUuid == null || disk2 == null) {
                                    Log.d("StorageNotification", "onVolumeMounted() : VolumeRecord or DiskInfo is null");
                                } else if (findRecordByUuid.isSnoozed() && disk2.isAdoptable()) {
                                    Log.w("StorageNotification", "onVolumeMounted() : isAdoptable");
                                } else if (SemPersonaManager.isKioskModeEnabled(context)) {
                                    Log.d("StorageNotification", "Container Only Mode is enabled. Do not attach SD Card notification.");
                                } else {
                                    Resources system = Resources.getSystem();
                                    String string3 = volumeInfo.getDisk().isSd() ? system.getString(17042545) : system.getString(17043203);
                                    if (volumeInfo.getDisk().isUsb() && (str2 = volumeInfo.fsType) != null && "ntfs".equalsIgnoreCase(str2)) {
                                        string3 = context.getString(R.string.restr_pin_error_doesnt_match);
                                        str4 = context.getString(R.string.restr_pin_enter_pin);
                                    }
                                    StrictMode.VmPolicy allowVmViolations = StrictMode.allowVmViolations();
                                    try {
                                        String fsUuid2 = volumeInfo.getFsUuid();
                                        Intent intent2 = new Intent("samsung.myfiles.intent.action.LAUNCH_MY_FILES");
                                        if (volumeInfo.getDisk() == null || !volumeInfo.getDisk().isUsb()) {
                                            intent2.putExtra("samsung.myfiles.intent.extra.START_PATH", "/storage/" + fsUuid2);
                                        } else {
                                            intent2.putExtra("samsung.myfiles.intent.extra.START_PATH", "/mnt/media_rw/" + fsUuid2);
                                        }
                                        intent2.addFlags(335544320);
                                        PendingIntent activityAsUser2 = PendingIntent.getActivityAsUser(this.mContext, volumeInfo.getId().hashCode(), intent2, 335544320, null, UserHandle.CURRENT);
                                        StrictMode.setVmPolicy(allowVmViolations);
                                        notification2 = buildNotificationBuilder(volumeInfo, string3, str4).addAction(new Notification.Action(0, context.getString(R.string.indeterminate_progress_35), activityAsUser2)).addAction(new Notification.Action(0, context.getString(R.string.invalidPin), buildUnmountPendingIntent(volumeInfo))).setContentIntent(activityAsUser2).setCategory("sys").setPriority(-1).setShowWhen(false).build();
                                    } catch (Throwable th) {
                                        StrictMode.setVmPolicy(allowVmViolations);
                                        throw th;
                                    }
                                }
                            }
                            z = true;
                            isUsb = volumeInfo.getDisk().isUsb();
                            Map map32 = this.mNotifyingVolumes;
                            if (isUsb) {
                            }
                            updateMountedVolumes(volumeInfo);
                            NotificationManager notificationManager22 = this.mNotificationManager;
                            if (notification2 != null) {
                            }
                            break;
                        case 4:
                        default:
                            isUsb = volumeInfo.getDisk().isUsb();
                            Map map322 = this.mNotifyingVolumes;
                            if (isUsb) {
                            }
                            updateMountedVolumes(volumeInfo);
                            NotificationManager notificationManager222 = this.mNotificationManager;
                            if (notification2 != null) {
                            }
                            break;
                        case 5:
                            DiskInfo disk3 = volumeInfo.getDisk();
                            notification2 = buildNotificationBuilder(volumeInfo, context.getString(R.string.issued_to, disk3.getDescription()), context.getString(R.string.issued_on, disk3.getDescription())).setCategory("progress").setPriority(-1).setOngoing(false).setShowWhen(false).build();
                            isUsb = volumeInfo.getDisk().isUsb();
                            Map map3222 = this.mNotifyingVolumes;
                            if (isUsb) {
                            }
                            updateMountedVolumes(volumeInfo);
                            NotificationManager notificationManager2222 = this.mNotificationManager;
                            if (notification2 != null) {
                            }
                            break;
                        case 6:
                            DiskInfo disk4 = volumeInfo.getDisk();
                            String string4 = context.getString(R.string.issued_by, disk4.getDescription());
                            String string5 = context.getString(R.string.invalidPuk, disk4.getDescription());
                            if (isAutomotive()) {
                                activityAsUser = buildUnmountPendingIntent(volumeInfo);
                            } else {
                                Intent intent3 = new Intent();
                                if (isTv()) {
                                    intent3.setPackage("com.android.tv.settings");
                                    intent3.setAction("com.android.tv.settings.action.NEW_STORAGE");
                                } else if (!isAutomotive()) {
                                    intent3.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardInit");
                                }
                                intent3.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
                                activityAsUser = PendingIntent.getActivityAsUser(this.mContext, volumeInfo.getId().hashCode(), intent3, 335544320, null, UserHandle.CURRENT);
                            }
                            notification2 = buildNotificationBuilder(volumeInfo, string4, string5).setContentIntent(activityAsUser).setCategory("err").setShowWhen(false).build();
                            isUsb = volumeInfo.getDisk().isUsb();
                            Map map32222 = this.mNotifyingVolumes;
                            if (isUsb) {
                            }
                            updateMountedVolumes(volumeInfo);
                            NotificationManager notificationManager22222 = this.mNotificationManager;
                            if (notification2 != null) {
                            }
                            break;
                        case 7:
                            if (volumeInfo.getType() == 0 && (diskInfo = volumeInfo.disk) != null && diskInfo.isSd()) {
                                DiskInfo disk5 = volumeInfo.getDisk();
                                build = buildNotificationBuilder(volumeInfo, context.getString(R.string.indeterminate_progress_54, disk5.getDescription()), context.getString(R.string.indeterminate_progress_53, disk5.getDescription())).setCategory("err").setShowWhen(false).build();
                                notification2 = build;
                            }
                            z = true;
                            isUsb = volumeInfo.getDisk().isUsb();
                            Map map322222 = this.mNotifyingVolumes;
                            if (isUsb) {
                            }
                            updateMountedVolumes(volumeInfo);
                            NotificationManager notificationManager222222 = this.mNotificationManager;
                            if (notification2 != null) {
                            }
                            break;
                        case 8:
                            if (volumeInfo.getType() == 0 && volumeInfo.disk.isSd()) {
                                DiskInfo disk6 = volumeInfo.getDisk();
                                build = buildNotificationBuilder(volumeInfo, context.getString(R.string.indeterminate_progress_34, disk6.getDescription()), context.getString(R.string.indeterminate_progress_33, disk6.getDescription())).setCategory("err").setShowWhen(false).build();
                                notification2 = build;
                            }
                            z = true;
                            isUsb = volumeInfo.getDisk().isUsb();
                            Map map3222222 = this.mNotifyingVolumes;
                            if (isUsb) {
                            }
                            updateMountedVolumes(volumeInfo);
                            NotificationManager notificationManager2222222 = this.mNotificationManager;
                            if (notification2 != null) {
                            }
                            break;
                    }
                }
            } else {
                Log.d("StorageNotification", "Ignore public volume state change event of removed user");
            }
        } else {
            if (type != 1) {
                return;
            }
            Log.d("StorageNotification", "Notifying about private volume: " + volumeInfo.toString());
            updateMissingPrivateVolumes();
        }
    }

    public final void showExtStorageReadOnlyMountNoti(String str, boolean z) {
        String string;
        String string2;
        int i = str.equalsIgnoreCase("sd") ? 119 : 120;
        NotificationManager notificationManager = this.mNotificationManager;
        if (notificationManager == null) {
            return;
        }
        if (!z) {
            AbstractC0147x487e7be7.m26m("showExtStorageReadOnlyMountNoti : cancle id [", i, "]", "StorageNotification");
            notificationManager.cancelAsUser(null, i, UserHandle.ALL);
            return;
        }
        boolean equalsIgnoreCase = str.equalsIgnoreCase("sd");
        int i2 = R.drawable.stat_notify_sdcard;
        Context context = this.mContext;
        if (equalsIgnoreCase) {
            string = context.getString(17042547);
            string2 = context.getString(17042546);
        } else if (str.equalsIgnoreCase("usb")) {
            string = context.getString(17043205);
            string2 = context.getString(17043204);
            i2 = 17304253;
        } else {
            string = context.getString(17042547);
            string2 = context.getString(17042546);
        }
        notificationManager.notifyAsUser(null, i, new Notification.Builder(context, "ALR").setSmallIcon(i2).setWhen(0L).setOngoing(true).setTicker(string).setDefaults(0).setPriority(2).setColor(context.getColor(R.color.system_notification_accent_color)).setFlag(8, true).setContentTitle(string).setContentText(string2).setVisibility(1).setStyle(new Notification.BigTextStyle().bigText(string2)).setShowWhen(false).setAutoCancel(false).build(), UserHandle.ALL);
        Log.d("StorageNotification", "showExtStorageReadOnlyMountNoti : notify id [" + i + "], title [" + ((Object) string) + "]");
    }

    public final void showSDcardErrorNoti(boolean z) {
        NotificationManager notificationManager = this.mNotificationManager;
        if (notificationManager == null) {
            return;
        }
        if (!z) {
            notificationManager.cancelAsUser(null, 103, UserHandle.ALL);
            return;
        }
        Context context = this.mContext;
        String string = context.getString(17042544);
        String string2 = context.getString(17042543);
        notificationManager.notifyAsUser(null, 103, new Notification.Builder(context, "ALR").setSmallIcon(R.drawable.stat_notify_sdcard_usb).setWhen(0L).setOngoing(false).setTicker(string).setDefaults(0).setPriority(0).setColor(context.getColor(R.color.system_notification_accent_color)).setFlag(8, true).setContentTitle(string).setContentText(string2).setVisibility(1).setStyle(new Notification.BigTextStyle().bigText(string2)).setShowWhen(false).build(), UserHandle.ALL);
        Log.d("StorageNotification", "showSDcardErrorNoti : notify id = 103, title = " + ((Object) string));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Log.d("StorageNotification", "start ()");
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        this.mStorageManager.registerListener(this.mListener);
        this.mBroadcastDispatcher.registerReceiver(this.mSnoozeReceiver, new IntentFilter("com.android.systemui.action.SNOOZE_VOLUME"), null, null, 2, "android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        this.mBroadcastDispatcher.registerReceiver(this.mFinishReceiver, new IntentFilter("com.android.systemui.action.FINISH_WIZARD"), null, null, 2, "android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.intent.action.SDCARD_ITPOLICY_TOAST_EVENT"), this.mSDCardPolicyToastReceiver);
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("android.intent.action.LOCALE_CHANGED"), this.mLocalechangedReceiver);
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"), this.mEmergencyModeReceiver);
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.intent.action.EXTERNAL_STORAGE_WARNING_SEC"), this.mBadRemovalReceiver);
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.systemui.action.STORAGE_NOTIFICATION_CANCEL"), this.mNotiDeleteReceiver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(intentFilter, this.mUserReceiver);
        startObserving("DEVPATH=/devices/virtual/sec/sdcard");
        startObserving("DEVPATH=/fs/sdfat/uevent");
        startObserving("DEVPATH=/fs/fat/uevent");
        for (DiskInfo diskInfo : this.mStorageManager.getDisks()) {
            Log.d("StorageNotification", "onDiskScannedInternal (" + diskInfo + ")");
            onDiskScannedInternal(diskInfo, diskInfo.volumeCount);
        }
        for (VolumeInfo volumeInfo : this.mStorageManager.getVolumes()) {
            if (volumeInfo.getType() != 0) {
                Log.d("StorageNotification", "start : vol(" + volumeInfo + "), disk(" + volumeInfo.getDisk() + ")");
                onVolumeStateChangedInternal(volumeInfo);
            } else if (volumeInfo.getMountUserId() == this.mCurrentUserId) {
                Log.d("StorageNotification", "start : vol(" + volumeInfo + "), disk(" + volumeInfo.getDisk() + ")");
                onVolumeStateChangedInternal(volumeInfo);
            }
        }
        this.mContext.getPackageManager().registerMoveCallback(this.mMoveCallback, new Handler());
        updateMissingPrivateVolumes();
    }

    public final void updateMissingPrivateVolumes() {
        Log.d("StorageNotification", "updateMissingPrivateVolumes ()");
        if (isTv() || isAutomotive()) {
            return;
        }
        StorageManager storageManager = this.mStorageManager;
        for (VolumeRecord volumeRecord : storageManager.getVolumeRecords()) {
            if (volumeRecord.getType() == 1) {
                String fsUuid = volumeRecord.getFsUuid();
                VolumeInfo findVolumeByUuid = storageManager.findVolumeByUuid(fsUuid);
                NotificationManager notificationManager = this.mNotificationManager;
                if ((findVolumeByUuid == null || !findVolumeByUuid.isMountedWritable()) && !volumeRecord.isSnoozed()) {
                    Object[] objArr = {volumeRecord.getNickname()};
                    Context context = this.mContext;
                    String string = context.getString(R.string.indeterminate_progress_40, objArr);
                    String string2 = context.getString(R.string.indeterminate_progress_39);
                    Notification.Builder contentText = new Notification.Builder(context, "DSK").setSmallIcon(R.drawable.stat_notify_sdcard).setColor(context.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2);
                    Intent intent = new Intent();
                    intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$PrivateVolumeForgetActivity");
                    intent.putExtra("android.os.storage.extra.FS_UUID", volumeRecord.getFsUuid());
                    Notification.Builder category = contentText.setContentIntent(PendingIntent.getActivityAsUser(this.mContext, volumeRecord.getFsUuid().hashCode(), intent, 335544320, null, UserHandle.CURRENT)).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("sys");
                    Intent intent2 = new Intent("com.android.systemui.action.SNOOZE_VOLUME");
                    intent2.putExtra("android.os.storage.extra.FS_UUID", fsUuid);
                    Notification.Builder showWhen = category.setDeleteIntent(PendingIntent.getBroadcastAsUser(context, fsUuid.hashCode(), intent2, 335544320, UserHandle.CURRENT)).extend(new Notification.TvExtender()).setShowWhen(false);
                    SystemUIApplication.overrideNotificationAppName(context, showWhen, false);
                    notificationManager.notifyAsUser(fsUuid, 1397772886, showWhen.build(), UserHandle.ALL);
                } else {
                    notificationManager.cancelAsUser(fsUuid, 1397772886, UserHandle.ALL);
                }
            }
        }
    }

    public final void updateMountedVolumes(VolumeInfo volumeInfo) {
        if (volumeInfo.getFsUuid() == null) {
            return;
        }
        int state = volumeInfo.getState();
        Map map = this.mMountedVolumes;
        if (state == 0) {
            ((ConcurrentHashMap) map).remove(volumeInfo.getFsUuid());
        } else {
            if (state != 2) {
                return;
            }
            ((ConcurrentHashMap) map).put(volumeInfo.getFsUuid(), volumeInfo);
        }
    }
}
