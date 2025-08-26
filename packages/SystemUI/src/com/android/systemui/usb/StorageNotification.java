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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.NotificationChannels;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class StorageNotification implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
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
    public final AnonymousClass1 mListener = new StorageEventListener() { // from class: com.android.systemui.usb.StorageNotification.1
        public final void onDiskDestroyed(DiskInfo diskInfo) {
            Log.d("StorageNotification", "onDiskDestroyed (" + diskInfo + ")");
            StorageNotification.this.mNotificationManager.cancelAsUser(diskInfo.getId(), 1396986699, UserHandle.ALL);
        }

        public final void onDiskScanned(DiskInfo diskInfo, int i) {
            Log.d("StorageNotification", "onDiskScanned : disk(" + diskInfo + "), volumeCount(" + i + ")");
            StorageNotification storageNotification = StorageNotification.this;
            int i2 = StorageNotification.$r8$clinit;
            storageNotification.onDiskScannedInternal(diskInfo, i);
        }

        public final void onVolumeForgotten(String str) {
            KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("onVolumeForgotten (", str, ")", "StorageNotification");
            StorageNotification.this.mNotificationManager.cancelAsUser(str, 1397772886, UserHandle.ALL);
        }

        public final void onVolumeRecordChanged(VolumeRecord volumeRecord) {
            VolumeInfo volumeInfoFindVolumeByUuid = StorageNotification.this.mStorageManager.findVolumeByUuid(volumeRecord.getFsUuid());
            if (volumeInfoFindVolumeByUuid == null || !volumeInfoFindVolumeByUuid.isMountedReadable()) {
                return;
            }
            Log.d("StorageNotification", "onVolumeRecordChanged : vol(" + volumeInfoFindVolumeByUuid + "), disk(" + volumeInfoFindVolumeByUuid.getDisk() + ")");
            StorageNotification.this.onVolumeStateChangedInternal(volumeInfoFindVolumeByUuid);
        }

        public final void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
            StringBuilder sb = new StringBuilder("onVolumeStateChanged : vol(");
            sb.append(volumeInfo);
            sb.append("), disk(");
            sb.append(volumeInfo.getDisk());
            sb.append("), oldState(");
            sb.append(i);
            sb.append("), newState");
            EmergencyButtonController$$ExternalSyntheticOutline0.m(sb, i2, ")", "StorageNotification");
            StorageNotification storageNotification = StorageNotification.this;
            int i3 = StorageNotification.$r8$clinit;
            storageNotification.onVolumeStateChangedInternal(volumeInfo);
        }
    };
    public final AnonymousClass2 mSnoozeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            StorageNotification.this.mStorageManager.setVolumeSnoozed(intent.getStringExtra("android.os.storage.extra.FS_UUID"), true);
        }
    };
    public final AnonymousClass3 mFinishReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.3
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mFinishReceiver (" + intent + ")");
            StorageNotification.this.mNotificationManager.cancelAsUser(null, 1397575510, UserHandle.ALL);
        }
    };
    public final AnonymousClass4 mSDCardPolicyToastReceiver = new BroadcastReceiver(this) { // from class: com.android.systemui.usb.StorageNotification.4
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.intent.action.SDCARD_ITPOLICY_TOAST_EVENT".equals(intent.getAction())) {
                Log.d("StorageNotification", "SDCardPolicyToastReceiver onReceive!!");
                Toast.makeText(context, 17042595, 1).show();
            }
        }
    };
    public final AnonymousClass5 mBadRemovalReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.5
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            int i;
            int i2;
            Log.d("StorageNotification", "mBadRemovalReceiver (" + intent + ")");
            String stringExtra = intent.getStringExtra("message");
            if (stringExtra.equals("REBOOT")) {
                final StorageNotification storageNotification = StorageNotification.this;
                int i3 = StorageNotification.$r8$clinit;
                storageNotification.getClass();
                new Thread(new Runnable() { // from class: com.android.systemui.usb.StorageNotification.13
                    @Override // java.lang.Runnable
                    public final void run() throws InterruptedException, Resources.NotFoundException {
                        String string = StorageNotification.this.mContext.getString(R.string.accessibility_gesture_3finger_prompt_text);
                        Intent intent2 = new Intent();
                        intent2.setClassName("android", "com.android.server.StorageManagerService$StorageBadRemovalReceiver");
                        intent2.putExtra("msg", "reboot");
                        Notification.Builder builderAddAction = new Notification.Builder(StorageNotification.this.mContext, NotificationChannels.ALERTS).setSmallIcon(R.drawable.stat_notify_sdcard_usb).setColor(StorageNotification.this.mContext.getColor(R.color.system_notification_accent_color)).setPriority(2).setVisibility(1).setShowWhen(false).setDefaults(-1).setOnlyAlertOnce(true).setOngoing(true).addAction(new Notification.Action(0, string, PendingIntent.getBroadcastAsUser(StorageNotification.this.mContext, 0, intent2, 1140850688, UserHandle.ALL)));
                        for (int i4 = 15; i4 >= 0; i4--) {
                            String quantityString = StorageNotification.this.mContext.getResources().getQuantityString(R.plurals.bugreport_countdown, i4, Integer.valueOf(i4));
                            String quantityString2 = StorageNotification.this.mContext.getResources().getQuantityString(R.plurals.pinpuk_attempts, i4, Integer.valueOf(i4));
                            builderAddAction.setContentTitle(quantityString).setContentText(quantityString2).setTicker(quantityString).setStyle(new Notification.BigTextStyle().bigText(quantityString2));
                            StorageNotification.this.mNotificationManager.notify(105, builderAddAction.build());
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
                String string = storageNotification2.mContext.getString(R.string.location_changed_notification_text);
                String string2 = storageNotification2.mContext.getString(17042863);
                if (stringExtra.contains(PeripheralConstants.ConnectivityType.USB)) {
                    string2 = storageNotification2.mContext.getString(17043473);
                    i = 17304593;
                    i2 = 122;
                } else {
                    i = R.drawable.stat_notify_sdcard;
                    i2 = 121;
                }
                String string3 = storageNotification2.mContext.getString(R.string.locale_search_menu);
                Intent intent2 = new Intent();
                intent2.setClassName("android", "com.android.server.StorageManagerService$StorageBadRemovalReceiver");
                intent2.putExtra("msg", "reboot");
                Context context2 = storageNotification2.mContext;
                UserHandle userHandle = UserHandle.ALL;
                storageNotification2.mNotificationManager.notifyAsUser(null, i2, new Notification.Builder(storageNotification2.mContext, NotificationChannels.ALERTS).setContentTitle(string).setContentText(string2).setTicker(string).setSmallIcon(i).setColor(storageNotification2.mContext.getColor(R.color.system_notification_accent_color)).setPriority(2).setVisibility(1).setShowWhen(false).setStyle(new Notification.BigTextStyle().bigText(string2)).addAction(new Notification.Action(0, string3, PendingIntent.getBroadcastAsUser(context2, 0, intent2, 1140850688, userHandle))).build(), userHandle);
                Log.d("StorageNotification", "notifyAsUser: title=" + ((Object) string));
            }
        }
    };
    public final AnonymousClass6 mEmergencyModeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.6
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
    public final AnonymousClass7 mNotiDeleteReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.7
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mNotiDeleteReceiver (" + intent + ")");
            if (intent.getIntExtra("NOTE_ID", 0) == 1397773634) {
                String stringExtra = intent.getStringExtra("NOTE_TAG");
                VolumeInfo volumeInfo = (VolumeInfo) ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).get(stringExtra);
                VolumeInfo volumeInfo2 = null;
                String fsUuid = volumeInfo != null ? volumeInfo.getFsUuid() : null;
                String string = StorageNotification.this.mSharedPreferences.getString("persist.systemUI.sdUUID", SignalSeverity.NONE);
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
                        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("mNotiDeleteReceiver Set STORAGE_NOTIFICATION_USB_MEMORY_UUID with Current USB UUID[", fsUuid, "]", "StorageNotification");
                        SharedPreferences.Editor editorEdit = StorageNotification.this.mSharedPreferences.edit();
                        editorEdit.putString("persist.systemUI.usbUUID", fsUuid);
                        editorEdit.commit();
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
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("mNotiDeleteReceiver Set STORAGE_NOTIFICATION_SD_CARD_UUID with Current SD Card UUID[", fsUuid, "]", "StorageNotification");
                SharedPreferences.Editor editorEdit2 = StorageNotification.this.mSharedPreferences.edit();
                editorEdit2.putString("persist.systemUI.sdUUID", fsUuid);
                editorEdit2.commit();
            }
        }
    };
    public final AnonymousClass8 mLocalechangedReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.8
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mLocalechangedReceiver (" + intent + ")");
            Iterator it = ((ConcurrentHashMap) StorageNotification.this.mNotifyingVolumes).values().iterator();
            while (it.hasNext()) {
                StorageNotification.this.onVolumeStateChangedInternal((VolumeInfo) it.next());
            }
        }
    };
    public final AnonymousClass9 mUserReceiver = new BroadcastReceiver() { // from class: com.android.systemui.usb.StorageNotification.9
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("StorageNotification", "mUserReceiver (" + intent + ")");
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            if (intExtra >= 0 && "android.intent.action.USER_SWITCHED".equals(action)) {
                StorageNotification.this.mCurrentUserId = intExtra;
                RecyclerView$$ExternalSyntheticOutline0.m(StorageNotification.this.mCurrentUserId, "StorageNotification", new StringBuilder("Update mCurrentUserId="));
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
    public final AnonymousClass10 mUEventObserver = new UEventObserver() { // from class: com.android.systemui.usb.StorageNotification.10
        public final void onUEvent(UEventObserver.UEvent uEvent) {
            uEvent.toString();
            if ("YES".equals(uEvent.get("IOERROR"))) {
                Log.d("StorageNotification", "SDcard I/O error uevent is occured.");
                StorageNotification storageNotification = StorageNotification.this;
                int i = StorageNotification.$r8$clinit;
                storageNotification.showSDcardErrorNoti(true);
            }
        }
    };
    public final AnonymousClass11 mROMountUEventObserver = new UEventObserver() { // from class: com.android.systemui.usb.StorageNotification.11
        public final void onUEvent(UEventObserver.UEvent uEvent) {
            uEvent.toString();
            String str = uEvent.get("MAJOR");
            if ("179".equals(str)) {
                Log.d("StorageNotification", "SDcard Mounted as Read-Only UEVENT.");
                StorageNotification storageNotification = StorageNotification.this;
                int i = StorageNotification.$r8$clinit;
                storageNotification.showExtStorageReadOnlyMountNoti("sd", true);
                return;
            }
            if (!"8".equals(str)) {
                Log.d("StorageNotification", "WRONG MAJOR Value at Read-Only UEVENT");
                return;
            }
            Log.d("StorageNotification", "USB MEMORY Mounted as Read-Only UEVENT.");
            StorageNotification storageNotification2 = StorageNotification.this;
            int i2 = StorageNotification.$r8$clinit;
            storageNotification2.showExtStorageReadOnlyMountNoti("usb", true);
        }
    };
    public final AnonymousClass12 mMoveCallback = new PackageManager.MoveCallback() { // from class: com.android.systemui.usb.StorageNotification.12
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
            String string;
            String string2;
            StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "mMoveCallback (", ", ", ", ");
            sbM.append(j);
            sbM.append(")");
            Log.d("StorageNotification", sbM.toString());
            MoveInfo moveInfo = (MoveInfo) StorageNotification.this.mMoves.get(i);
            if (moveInfo == null) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Ignoring unknown move ", "StorageNotification");
                return;
            }
            PendingIntent activityAsUser = null;
            if (!PackageManager.isMoveStatusFinished(i2)) {
                if (((Integer) StorageNotification.this.mPrevStatus.get(Integer.valueOf(i))).intValue() != i2) {
                    StorageNotification storageNotification = StorageNotification.this;
                    storageNotification.getClass();
                    Log.d("StorageNotification", "onMoveProgress (" + moveInfo + ", " + i2 + ", " + j + ")");
                    String string3 = !TextUtils.isEmpty(moveInfo.label) ? storageNotification.mContext.getString(R.string.kg_login_password_hint, moveInfo.label) : storageNotification.mContext.getString(R.string.kg_login_username_hint);
                    CharSequence duration = j < 0 ? null : DateUtils.formatDuration(j);
                    if (moveInfo.packageName != null) {
                        Intent intent = new Intent();
                        if (storageNotification.isTv$3()) {
                            intent.setPackage("com.android.tv.settings");
                            intent.setAction("com.android.tv.settings.action.MOVE_APP");
                        } else if (!storageNotification.isAutomotive()) {
                            intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardMoveProgress");
                        }
                        intent.putExtra("android.content.pm.extra.MOVE_ID", moveInfo.moveId);
                        activityAsUser = PendingIntent.getActivityAsUser(storageNotification.mContext, moveInfo.moveId, intent, 335544320, null, UserHandle.CURRENT);
                    } else {
                        Intent intent2 = new Intent();
                        if (storageNotification.isTv$3()) {
                            intent2.setPackage("com.android.tv.settings");
                            intent2.setAction("com.android.tv.settings.action.MIGRATE_STORAGE");
                        } else if (!storageNotification.isAutomotive()) {
                            intent2.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardMigrateProgress");
                        }
                        intent2.putExtra("android.content.pm.extra.MOVE_ID", moveInfo.moveId);
                        VolumeInfo volumeInfoFindVolumeByQualifiedUuid = storageNotification.mStorageManager.findVolumeByQualifiedUuid(moveInfo.volumeUuid);
                        if (volumeInfoFindVolumeByQualifiedUuid != null) {
                            intent2.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfoFindVolumeByQualifiedUuid.getId());
                        }
                        activityAsUser = PendingIntent.getActivityAsUser(storageNotification.mContext, moveInfo.moveId, intent2, 335544320, null, UserHandle.CURRENT);
                    }
                    Notification.Builder showWhen = new Notification.Builder(storageNotification.mContext, NotificationChannels.STORAGE).setSmallIcon(R.drawable.stat_notify_sdcard).setColor(storageNotification.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(string3).setContentText(duration).setContentIntent(activityAsUser).setStyle(new Notification.BigTextStyle().bigText(duration)).setVisibility(1).setLocalOnly(true).setCategory("progress").setPriority(-1).setProgress(100, i2, false).setOngoing(true).setShowWhen(false);
                    SystemUIApplication.overrideNotificationAppName(storageNotification.mContext, showWhen, false);
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
            ExifInterface$$ExternalSyntheticOutline0.m(sb, ")", "StorageNotification");
            String str = moveInfo.packageName;
            if (str != null) {
                storageNotification2.mNotificationManager.cancelAsUser(str, 1397575510, UserHandle.ALL);
            } else {
                VolumeInfo primaryStorageCurrentVolume = storageNotification2.mContext.getPackageManager().getPrimaryStorageCurrentVolume();
                String bestVolumeDescription = storageNotification2.mStorageManager.getBestVolumeDescription(primaryStorageCurrentVolume);
                if (i2 == -100) {
                    string = storageNotification2.mContext.getString(R.string.kg_login_too_many_attempts);
                    string2 = storageNotification2.mContext.getString(R.string.kg_login_submit_button, bestVolumeDescription);
                } else {
                    string = storageNotification2.mContext.getString(R.string.kg_login_invalid_input);
                    string2 = storageNotification2.mContext.getString(R.string.kg_login_instructions);
                }
                if (primaryStorageCurrentVolume != null && primaryStorageCurrentVolume.getDisk() != null) {
                    DiskInfo disk = primaryStorageCurrentVolume.getDisk();
                    Intent intent3 = new Intent();
                    if (storageNotification2.isTv$3()) {
                        intent3.setPackage("com.android.tv.settings");
                        intent3.setAction("android.settings.INTERNAL_STORAGE_SETTINGS");
                    } else if (!storageNotification2.isAutomotive()) {
                        intent3.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardReady");
                    }
                    intent3.putExtra("android.os.storage.extra.DISK_ID", disk.getId());
                    activityAsUser = PendingIntent.getActivityAsUser(storageNotification2.mContext, disk.getId().hashCode(), intent3, 335544320, null, UserHandle.CURRENT);
                } else if (primaryStorageCurrentVolume != null) {
                    Intent intent4 = new Intent();
                    if (storageNotification2.isTv$3()) {
                        intent4.setPackage("com.android.tv.settings");
                        intent4.setAction("android.settings.INTERNAL_STORAGE_SETTINGS");
                    } else if (!storageNotification2.isAutomotive()) {
                        int type = primaryStorageCurrentVolume.getType();
                        if (type == 0) {
                            intent4.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$PublicVolumeSettingsActivity");
                        } else if (type == 1) {
                            intent4.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$PrivateVolumeSettingsActivity");
                        }
                    }
                    intent4.putExtra("android.os.storage.extra.VOLUME_ID", primaryStorageCurrentVolume.getId());
                    activityAsUser = PendingIntent.getActivityAsUser(storageNotification2.mContext, primaryStorageCurrentVolume.getId().hashCode(), intent4, 335544320, null, UserHandle.CURRENT);
                }
                Notification.Builder showWhen2 = new Notification.Builder(storageNotification2.mContext, NotificationChannels.STORAGE).setSmallIcon(R.drawable.stat_notify_sdcard).setColor(storageNotification2.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2).setContentIntent(activityAsUser).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("sys").setPriority(-1).setAutoCancel(true).setShowWhen(false);
                SystemUIApplication.overrideNotificationAppName(storageNotification2.mContext, showWhen2, false);
                storageNotification2.mNotificationManager.notifyAsUser(moveInfo.packageName, 1397575510, showWhen2.build(), UserHandle.ALL);
            }
            StorageNotification.this.mPrevStatus.remove(Integer.valueOf(i));
        }
    };

    public class MoveInfo {
        public String label;
        public int moveId;
        public String packageName;
        public String volumeUuid;

        public /* synthetic */ MoveInfo(int i) {
            this();
        }

        private MoveInfo() {
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
        Notification.Builder builder = new Notification.Builder(this.mContext, NotificationChannels.STORAGE);
        DiskInfo disk = volumeInfo.getDisk();
        volumeInfo.getState();
        Notification.Builder builderExtend = builder.setSmallIcon((!disk.isSd() && disk.isUsb()) ? 17304593 : R.drawable.stat_notify_sdcard).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(charSequence).setContentText(charSequence2).setStyle(new Notification.BigTextStyle().bigText(charSequence2)).setVisibility(1).setShowWhen(false).setLocalOnly(true).extend(new Notification.TvExtender());
        SystemUIApplication.overrideNotificationAppName(this.mContext, builderExtend, false);
        return builderExtend;
    }

    public final PendingIntent buildUnmountPendingIntent(VolumeInfo volumeInfo) {
        Intent intent = new Intent();
        if (isTv$3()) {
            intent.setPackage("com.android.tv.settings");
            intent.setAction("com.android.tv.settings.action.UNMOUNT_STORAGE");
            intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
            return PendingIntent.getActivityAsUser(this.mContext, volumeInfo.getId().hashCode(), intent, 335544320, null, UserHandle.CURRENT);
        }
        if (isAutomotive()) {
            intent.setClassName("com.android.car.settings", "com.android.car.settings.storage.StorageUnmountReceiver");
            intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
            return PendingIntent.getBroadcastAsUser(this.mContext, volumeInfo.getId().hashCode(), intent, 335544320, UserHandle.CURRENT);
        }
        intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageUnmountReceiver");
        intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
        return PendingIntent.getBroadcastAsUser(this.mContext, volumeInfo.getId().hashCode(), intent, 335544320, UserHandle.CURRENT);
    }

    public final boolean isAutomotive() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }

    public final boolean isTv$3() {
        return this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
    }

    public final void onDiskScannedInternal(DiskInfo diskInfo, int i) {
        PendingIntent activityAsUser;
        if (i != 0 || diskInfo.size <= 0) {
            this.mNotificationManager.cancelAsUser(diskInfo.getId(), 1396986699, UserHandle.ALL);
            return;
        }
        String string = this.mContext.getString(R.string.locale_replacement, diskInfo.getDescription());
        String string2 = this.mContext.getString(R.string.loading, diskInfo.getDescription());
        Notification.Builder contentText = new Notification.Builder(this.mContext, NotificationChannels.STORAGE).setSmallIcon((!diskInfo.isSd() && diskInfo.isUsb()) ? 17304593 : R.drawable.stat_notify_sdcard).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2);
        Intent intent = new Intent();
        if (isTv$3()) {
            intent.setPackage("com.android.tv.settings");
            intent.setAction("com.android.tv.settings.action.NEW_STORAGE");
        } else {
            if (isAutomotive()) {
                activityAsUser = null;
                Notification.Builder showWhen = contentText.setContentIntent(activityAsUser).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("err").extend(new Notification.TvExtender()).setShowWhen(false);
                SystemUIApplication.overrideNotificationAppName(this.mContext, showWhen, false);
                this.mNotificationManager.notifyAsUser(diskInfo.getId(), 1396986699, showWhen.build(), UserHandle.ALL);
            }
            intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardInit");
        }
        intent.putExtra("android.os.storage.extra.DISK_ID", diskInfo.getId());
        activityAsUser = PendingIntent.getActivityAsUser(this.mContext, diskInfo.getId().hashCode(), intent, 335544320, null, UserHandle.CURRENT);
        Notification.Builder showWhen2 = contentText.setContentIntent(activityAsUser).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("err").extend(new Notification.TvExtender()).setShowWhen(false);
        SystemUIApplication.overrideNotificationAppName(this.mContext, showWhen2, false);
        this.mNotificationManager.notifyAsUser(diskInfo.getId(), 1396986699, showWhen2.build(), UserHandle.ALL);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:100:0x039c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x03e4  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x04dc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onVolumeStateChangedInternal(VolumeInfo volumeInfo) {
        boolean z;
        String str;
        PendingIntent pendingIntentBuildUnmountPendingIntent;
        DiskInfo diskInfo;
        int state;
        Log.d("StorageNotification", "onVolumeStateChangedInternal (" + volumeInfo.path + ")");
        int type = volumeInfo.getType();
        boolean z2 = true;
        if (type == 0) {
            Log.d("StorageNotification", "Notifying about public volume: " + volumeInfo.toString());
            if (volumeInfo.getMountUserId() != -10000) {
                if (volumeInfo.getDisk() != null) {
                    UserHandle userHandleOf = UserHandle.of(volumeInfo.getMountUserId());
                    String tagForVolumeInfo = getTagForVolumeInfo(volumeInfo);
                    String str2 = volumeInfo.getDisk().isSd() ? "sd" : "usb";
                    String fsUuid = volumeInfo.getFsUuid();
                    Notification notificationBuild = null;
                    notificationBuild = null;
                    notificationBuild = null;
                    notificationBuild = null;
                    notificationBuild = null;
                    notificationBuild = null;
                    PendingIntent activityAsUser = null;
                    notificationBuild = null;
                    notificationBuild = null;
                    notificationBuild = null;
                    string = null;
                    string = null;
                    String string = null;
                    notificationBuild = null;
                    notificationBuild = null;
                    switch (volumeInfo.getState()) {
                        case 0:
                            z = false;
                            if (volumeInfo.getDisk().isSd()) {
                                showSDcardErrorNoti(false);
                            }
                            showExtStorageReadOnlyMountNoti(str2, false);
                            z2 = z;
                            if (volumeInfo.getDisk().isUsb() && fsUuid != null) {
                                state = volumeInfo.getState();
                                if (state != 7 || state == 8) {
                                    return;
                                }
                                VolumeInfo volumeInfo2 = (VolumeInfo) ((ConcurrentHashMap) this.mNotifyingVolumes).get(tagForVolumeInfo);
                                if (volumeInfo2 != null) {
                                    if (!fsUuid.equals(volumeInfo2.getFsUuid()) && ((ConcurrentHashMap) this.mMountedVolumes).get(fsUuid) != null) {
                                        updateMountedVolumes(volumeInfo);
                                        return;
                                    }
                                } else if (((ConcurrentHashMap) this.mMountedVolumes).get(fsUuid) != null) {
                                    updateMountedVolumes(volumeInfo);
                                    return;
                                }
                            }
                            updateMountedVolumes(volumeInfo);
                            if (notificationBuild != null) {
                                if (fsUuid != null) {
                                    ((ConcurrentHashMap) this.mNotifyingVolumes).remove(tagForVolumeInfo);
                                    this.mNotificationManager.cancelAsUser(tagForVolumeInfo, 1397773634, userHandleOf);
                                    Log.d("StorageNotification", "cancelAsUser: Finally, tag=".concat(tagForVolumeInfo));
                                    return;
                                }
                                return;
                            }
                            if (SemEmergencyManager.isEmergencyMode(this.mContext)) {
                                Log.d("StorageNotification", "Emergency Mode now");
                                return;
                            }
                            String string2 = this.mSharedPreferences.getString("persist.systemUI.sdUUID", SignalSeverity.NONE);
                            String string3 = this.mSharedPreferences.getString("persist.systemUI.usbUUID", SignalSeverity.NONE);
                            if (fsUuid != null) {
                                if (volumeInfo.getDisk().isSd() && fsUuid.equalsIgnoreCase(string2)) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Current SD card UUID is same as ", string2, "StorageNotification");
                                    return;
                                } else if (volumeInfo.getDisk().isUsb() && fsUuid.equalsIgnoreCase(string3)) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Current USB Memory UUID is same as ", string3, "StorageNotification");
                                    return;
                                }
                            }
                            Iterator it = ((ConcurrentHashMap) this.mNotifyingVolumes).values().iterator();
                            while (it.hasNext()) {
                                if (tagForVolumeInfo.equals(getTagForVolumeInfo((VolumeInfo) it.next()))) {
                                    ((ConcurrentHashMap) this.mNotifyingVolumes).remove(tagForVolumeInfo);
                                    this.mNotificationManager.cancelAsUser(tagForVolumeInfo, 1397773634, userHandleOf);
                                    Log.d("StorageNotification", "cancelAsUser: Notifying volume, tag=".concat(tagForVolumeInfo));
                                }
                            }
                            if (fsUuid != null) {
                                Intent intent = new Intent("com.samsung.systemui.action.STORAGE_NOTIFICATION_CANCEL");
                                intent.putExtra("NOTE_TAG", tagForVolumeInfo);
                                intent.putExtra("NOTE_ID", 1397773634);
                                notificationBuild.deleteIntent = PendingIntent.getBroadcastAsUser(this.mContext, fsUuid.hashCode(), intent, 335544320, UserHandle.ALL);
                                ((ConcurrentHashMap) this.mNotifyingVolumes).put(tagForVolumeInfo, volumeInfo);
                                this.mNotificationManager.notifyAsUser(tagForVolumeInfo, 1397773634, notificationBuild, userHandleOf);
                                Log.d("StorageNotification", "notifyAsUser: Finally, tag=".concat(tagForVolumeInfo));
                            }
                            if (z2) {
                                if (volumeInfo.getDisk().isSd()) {
                                    Log.d("StorageNotification", "Set STORAGE_NOTIFICATION_SD_CARD_UUID as NONE");
                                    SharedPreferences.Editor editorEdit = this.mSharedPreferences.edit();
                                    editorEdit.putString("persist.systemUI.sdUUID", SignalSeverity.NONE);
                                    editorEdit.commit();
                                    return;
                                }
                                if (volumeInfo.getDisk().isUsb()) {
                                    Log.d("StorageNotification", "Set STORAGE_NOTIFICATION_USB_MEMORY_UUID as NONE");
                                    SharedPreferences.Editor editorEdit2 = this.mSharedPreferences.edit();
                                    editorEdit2.putString("persist.systemUI.usbUUID", SignalSeverity.NONE);
                                    editorEdit2.commit();
                                    return;
                                }
                                return;
                            }
                            return;
                        case 1:
                            DiskInfo disk = volumeInfo.getDisk();
                            z = false;
                            notificationBuild = buildNotificationBuilder(volumeInfo, this.mContext.getString(R.string.kg_invalid_sim_pin_hint, disk.getDescription()), this.mContext.getString(R.string.kg_invalid_puk, disk.getDescription())).setCategory("progress").setPriority(-1).setOngoing(false).setShowWhen(false).build();
                            z2 = z;
                            if (volumeInfo.getDisk().isUsb()) {
                                state = volumeInfo.getState();
                                if (state != 7) {
                                    return;
                                } else {
                                    return;
                                }
                            }
                            updateMountedVolumes(volumeInfo);
                            if (notificationBuild != null) {
                            }
                            break;
                        case 2:
                        case 3:
                            if (volumeInfo.getFsUuid() == null) {
                                Log.d("StorageNotification", "onVolumeMounted(): FsUuid is null");
                            } else {
                                VolumeRecord volumeRecordFindRecordByUuid = this.mStorageManager.findRecordByUuid(volumeInfo.getFsUuid());
                                DiskInfo disk2 = volumeInfo.getDisk();
                                if (volumeRecordFindRecordByUuid == null || disk2 == null) {
                                    Log.d("StorageNotification", "onVolumeMounted() : VolumeRecord or DiskInfo is null");
                                } else if (volumeRecordFindRecordByUuid.isSnoozed() && disk2.isAdoptable()) {
                                    Log.w("StorageNotification", "onVolumeMounted() : isAdoptable");
                                } else if (SemPersonaManager.isKioskModeEnabled(this.mContext)) {
                                    Log.d("StorageNotification", "Container Only Mode is enabled. Do not attach SD Card notification.");
                                } else {
                                    Resources system = Resources.getSystem();
                                    String string4 = volumeInfo.getDisk().isSd() ? system.getString(17042878) : system.getString(17043574);
                                    if (volumeInfo.getDisk().isUsb() && (str = volumeInfo.fsType) != null && "ntfs".equalsIgnoreCase(str)) {
                                        string4 = this.mContext.getString(R.string.storage_usb_drive);
                                        string = this.mContext.getString(R.string.storage_usb);
                                    }
                                    StrictMode.VmPolicy vmPolicyAllowVmViolations = StrictMode.allowVmViolations();
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
                                        StrictMode.setVmPolicy(vmPolicyAllowVmViolations);
                                        notificationBuild = buildNotificationBuilder(volumeInfo, string4, string).addAction(new Notification.Action(0, this.mContext.getString(R.string.kg_invalid_confirm_pin_hint), activityAsUser2)).addAction(new Notification.Action(0, this.mContext.getString(R.string.launchBrowserDefault), buildUnmountPendingIntent(volumeInfo))).setContentIntent(activityAsUser2).setCategory("sys").setPriority(-1).setShowWhen(false).build();
                                    } catch (Throwable th) {
                                        StrictMode.setVmPolicy(vmPolicyAllowVmViolations);
                                        throw th;
                                    }
                                }
                            }
                            z2 = true;
                            if (volumeInfo.getDisk().isUsb()) {
                            }
                            updateMountedVolumes(volumeInfo);
                            if (notificationBuild != null) {
                            }
                            break;
                        case 4:
                        default:
                            z2 = false;
                            if (volumeInfo.getDisk().isUsb()) {
                            }
                            updateMountedVolumes(volumeInfo);
                            if (notificationBuild != null) {
                            }
                            break;
                        case 5:
                            DiskInfo disk3 = volumeInfo.getDisk();
                            notificationBuild = buildNotificationBuilder(volumeInfo, this.mContext.getString(R.string.leave_accessibility_shortcut_on, disk3.getDescription()), this.mContext.getString(R.string.launch_warning_title, disk3.getDescription())).setCategory("progress").setPriority(-1).setOngoing(false).setShowWhen(false).build();
                            z2 = false;
                            if (volumeInfo.getDisk().isUsb()) {
                            }
                            updateMountedVolumes(volumeInfo);
                            if (notificationBuild != null) {
                            }
                            break;
                        case 6:
                            DiskInfo disk4 = volumeInfo.getDisk();
                            String string5 = this.mContext.getString(R.string.launch_warning_replace, disk4.getDescription());
                            String string6 = this.mContext.getString(R.string.launch_warning_original, disk4.getDescription());
                            if (isAutomotive()) {
                                pendingIntentBuildUnmountPendingIntent = buildUnmountPendingIntent(volumeInfo);
                            } else {
                                Intent intent3 = new Intent();
                                if (isTv$3()) {
                                    intent3.setPackage("com.android.tv.settings");
                                    intent3.setAction("com.android.tv.settings.action.NEW_STORAGE");
                                } else {
                                    if (!isAutomotive()) {
                                        intent3.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.deviceinfo.StorageWizardInit");
                                    }
                                    pendingIntentBuildUnmountPendingIntent = activityAsUser;
                                }
                                intent3.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.getId());
                                activityAsUser = PendingIntent.getActivityAsUser(this.mContext, volumeInfo.getId().hashCode(), intent3, 335544320, null, UserHandle.CURRENT);
                                pendingIntentBuildUnmountPendingIntent = activityAsUser;
                            }
                            notificationBuild = buildNotificationBuilder(volumeInfo, string5, string6).setContentIntent(pendingIntentBuildUnmountPendingIntent).setCategory("err").setShowWhen(false).build();
                            z2 = false;
                            if (volumeInfo.getDisk().isUsb()) {
                            }
                            updateMountedVolumes(volumeInfo);
                            if (notificationBuild != null) {
                            }
                            break;
                        case 7:
                            if (volumeInfo.getType() == 0 && (diskInfo = volumeInfo.disk) != null && diskInfo.isSd()) {
                                DiskInfo disk5 = volumeInfo.getDisk();
                                notificationBuild = buildNotificationBuilder(volumeInfo, this.mContext.getString(R.string.kg_sim_pin_instructions, disk5.getDescription()), this.mContext.getString(R.string.kg_reordering_delete_drop_target_text, disk5.getDescription())).setCategory("err").setShowWhen(false).build();
                            }
                            if (volumeInfo.getDisk().isUsb()) {
                            }
                            updateMountedVolumes(volumeInfo);
                            if (notificationBuild != null) {
                            }
                            break;
                        case 8:
                            if (volumeInfo.getType() == 0 && volumeInfo.disk.isSd()) {
                                DiskInfo disk6 = volumeInfo.getDisk();
                                notificationBuild = buildNotificationBuilder(volumeInfo, this.mContext.getString(R.string.kg_forgot_pattern_button_text, disk6.getDescription()), this.mContext.getString(R.string.kg_failed_attempts_now_wiping, disk6.getDescription())).setCategory("err").setShowWhen(false).build();
                            }
                            if (volumeInfo.getDisk().isUsb()) {
                            }
                            updateMountedVolumes(volumeInfo);
                            if (notificationBuild != null) {
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
        if (this.mNotificationManager == null) {
            return;
        }
        if (!z) {
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "showExtStorageReadOnlyMountNoti : cancle id [", "]", "StorageNotification");
            this.mNotificationManager.cancelAsUser(null, i, UserHandle.ALL);
            return;
        }
        boolean zEqualsIgnoreCase = str.equalsIgnoreCase("sd");
        int i2 = R.drawable.stat_notify_sdcard;
        if (!zEqualsIgnoreCase && str.equalsIgnoreCase("usb")) {
            string = this.mContext.getString(17043576);
            string2 = this.mContext.getString(17043575);
            i2 = 17304593;
        } else {
            string = this.mContext.getString(17042880);
            string2 = this.mContext.getString(17042879);
        }
        this.mNotificationManager.notifyAsUser(null, i, new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(i2).setWhen(0L).setOngoing(true).setTicker(string).setDefaults(0).setPriority(2).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setFlag(8, true).setContentTitle(string).setContentText(string2).setVisibility(1).setStyle(new Notification.BigTextStyle().bigText(string2)).setShowWhen(false).setAutoCancel(false).build(), UserHandle.ALL);
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
        String string = this.mContext.getString(17042877);
        String string2 = this.mContext.getString(17042876);
        this.mNotificationManager.notifyAsUser(null, 103, new Notification.Builder(this.mContext, NotificationChannels.ALERTS).setSmallIcon(R.drawable.stat_notify_sdcard_usb).setWhen(0L).setOngoing(false).setTicker(string).setDefaults(0).setPriority(0).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setFlag(8, true).setContentTitle(string).setContentText(string2).setVisibility(1).setStyle(new Notification.BigTextStyle().bigText(string2)).setShowWhen(false).build(), UserHandle.ALL);
        Log.d("StorageNotification", "showSDcardErrorNoti : notify id = 103, title = " + ((Object) string));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Log.d("StorageNotification", "start ()");
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        this.mStorageManager.registerListener(this.mListener);
        this.mBroadcastDispatcher.registerReceiver(this.mSnoozeReceiver, new IntentFilter("com.android.systemui.action.SNOOZE_VOLUME"), null, null, 2, "android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        this.mBroadcastDispatcher.registerReceiver(this.mFinishReceiver, new IntentFilter("com.android.systemui.action.FINISH_WIZARD"), null, null, 2, "android.permission.MOUNT_UNMOUNT_FILESYSTEMS");
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.intent.action.SDCARD_ITPOLICY_TOAST_EVENT"), this.mSDCardPolicyToastReceiver);
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("android.intent.action.LOCALE_CHANGED"), this.mLocalechangedReceiver);
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.intent.action.EMERGENCY_STATE_CHANGED"), this.mEmergencyModeReceiver);
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.intent.action.EXTERNAL_STORAGE_WARNING_SEC"), this.mBadRemovalReceiver);
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(new IntentFilter("com.samsung.systemui.action.STORAGE_NOTIFICATION_CANCEL"), this.mNotiDeleteReceiver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(intentFilter, this.mUserReceiver);
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
        if (isTv$3() || isAutomotive()) {
            return;
        }
        for (VolumeRecord volumeRecord : this.mStorageManager.getVolumeRecords()) {
            if (volumeRecord.getType() == 1) {
                String fsUuid = volumeRecord.getFsUuid();
                VolumeInfo volumeInfoFindVolumeByUuid = this.mStorageManager.findVolumeByUuid(fsUuid);
                if ((volumeInfoFindVolumeByUuid == null || !volumeInfoFindVolumeByUuid.isMountedWritable()) && !volumeRecord.isSnoozed()) {
                    String string = this.mContext.getString(R.string.kg_login_checking_password, volumeRecord.getNickname());
                    String string2 = this.mContext.getString(R.string.kg_login_account_recovery_hint);
                    Notification.Builder contentText = new Notification.Builder(this.mContext, NotificationChannels.STORAGE).setSmallIcon(R.drawable.stat_notify_sdcard).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(string).setContentText(string2);
                    Intent intent = new Intent();
                    intent.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$PrivateVolumeForgetActivity");
                    intent.putExtra("android.os.storage.extra.FS_UUID", volumeRecord.getFsUuid());
                    int iHashCode = volumeRecord.getFsUuid().hashCode();
                    Context context = this.mContext;
                    UserHandle userHandle = UserHandle.CURRENT;
                    Notification.Builder category = contentText.setContentIntent(PendingIntent.getActivityAsUser(context, iHashCode, intent, 335544320, null, userHandle)).setStyle(new Notification.BigTextStyle().bigText(string2)).setVisibility(1).setLocalOnly(true).setCategory("sys");
                    Intent intent2 = new Intent("com.android.systemui.action.SNOOZE_VOLUME");
                    intent2.putExtra("android.os.storage.extra.FS_UUID", fsUuid);
                    Notification.Builder showWhen = category.setDeleteIntent(PendingIntent.getBroadcastAsUser(this.mContext, fsUuid.hashCode(), intent2, 335544320, userHandle)).extend(new Notification.TvExtender()).setShowWhen(false);
                    SystemUIApplication.overrideNotificationAppName(this.mContext, showWhen, false);
                    this.mNotificationManager.notifyAsUser(fsUuid, 1397772886, showWhen.build(), UserHandle.ALL);
                } else {
                    this.mNotificationManager.cancelAsUser(fsUuid, 1397772886, UserHandle.ALL);
                }
            }
        }
    }

    public final void updateMountedVolumes(VolumeInfo volumeInfo) {
        if (volumeInfo.getFsUuid() == null) {
            return;
        }
        int state = volumeInfo.getState();
        if (state == 0) {
            ((ConcurrentHashMap) this.mMountedVolumes).remove(volumeInfo.getFsUuid());
        } else {
            if (state != 2) {
                return;
            }
            ((ConcurrentHashMap) this.mMountedVolumes).put(volumeInfo.getFsUuid(), volumeInfo);
        }
    }
}
