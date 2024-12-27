package com.android.server;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.DiskInfo;
import android.os.storage.IStorageManager;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.security.DirEncryptionWrapper;
import com.samsung.android.security.IDirEncryptServiceListener;
import com.samsung.android.security.SemSdCardEncryption;
import com.samsung.android.security.SemSdCardEncryptionPolicy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DirEncryptServiceHelper {
    public static boolean mMountSDcardToHelper;
    public static final Object mSync = new Object();
    public final AnimatingNotification mAnimator;
    public final AnonymousClass1 mBroadcastReceiver;
    public final Context mContext;
    public final DirEncryptionWrapper mDew;
    public final SDStorageEventListener mStorageEventListener = new SDStorageEventListener();
    public Handler mHandler = null;
    public boolean mSelfSDMountRequested = false;
    public boolean mUnnmountRequested = false;
    public boolean mAlreadyDecrypted = false;
    public boolean mBootCompleted = false;
    public String mEncryptTimestamp = "";
    public String mDecryptTimestamp = "";
    public int mErrAdditionalSpace = 0;
    public int mLastError = 0;
    public int mServiceStatus = 0;
    public final RemoteCallbackList mListeners = new RemoteCallbackList();
    public final Object mAnimateSync = new Object();
    public long mProgressTime = -1;
    public int mPrevPercent = -1;

    public final class AnimatingNotification implements Runnable {
        public final Context mContext;
        public boolean mStop = false;
        public int mEnctype = 0;
        public int mProgress = 0;
        public Notification.Builder notification_builder = null;

        public AnimatingNotification(Context context) {
            this.mContext = context;
        }

        @Override // java.lang.Runnable
        public final void run() {
            String string;
            String string2;
            int i;
            synchronized (DirEncryptServiceHelper.this.mAnimateSync) {
                try {
                    if (this.mEnctype == 1) {
                        string = this.mContext.getResources().getString(17042741);
                        string2 = this.mContext.getResources().getString(17042740);
                        i = R.drawable.fastscroll_thumb_pressed_holo;
                    } else {
                        string = this.mContext.getResources().getString(17042737);
                        string2 = this.mContext.getResources().getString(17042736);
                        i = R.drawable.dialog_ic_close_normal_holo_dark;
                    }
                    String str = string;
                    if (this.notification_builder == null) {
                        this.notification_builder =
                                DirEncryptServiceHelper.this.getNotification(
                                        null, i, string2, str, str);
                    }
                    this.notification_builder.setSmallIcon(i);
                    this.notification_builder.setOngoing(true);
                    this.notification_builder.setProgress(100, this.mProgress, false);
                    this.notification_builder.setContentTitle(
                            string2 + ": \u202a" + this.mProgress + "%");
                    if (this.mStop) {
                        this.notification_builder = null;
                    } else {
                        NotificationManager notificationManager =
                                (NotificationManager)
                                        this.mContext.getSystemService("notification");
                        notificationManager.createNotificationChannel(
                                new NotificationChannel(
                                        "sdcard_encryption_channel",
                                        this.mContext.getResources().getString(17042740),
                                        2));
                        notificationManager.notify(
                                SemSdCardEncryption.SECURITY_POLICY_NOTIFICATION_ID,
                                this.notification_builder.build());
                        DirEncryptServiceHelper.this.mHandler.postDelayed(this, 500L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class SDStorageEventListener extends StorageEventListener {
        public SDStorageEventListener() {}

        public final void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
            DiskInfo diskInfo;
            if (volumeInfo.getType() != 0 || (diskInfo = volumeInfo.disk) == null) {
                Log.i("DirEncryptServiceHelper", "vol.getType() = " + volumeInfo.getType());
                return;
            }
            if (!diskInfo.isSd()) {
                Log.i("DirEncryptServiceHelper", "Not SD card");
                return;
            }
            String environmentForState = VolumeInfo.getEnvironmentForState(i);
            String environmentForState2 = VolumeInfo.getEnvironmentForState(i2);
            if (environmentForState2.equals(environmentForState)) {
                Log.i(
                        "DirEncryptServiceHelper",
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "newState is a same state with oldState:: newState: ",
                                environmentForState2,
                                " , oldState: ",
                                environmentForState,
                                " !!!"));
            }
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onVolumeStateChanged UUID : "),
                    volumeInfo.fsUuid,
                    "DirEncryptServiceHelper");
            DirEncryptServiceHelper.this.mDew.setExternalSDvolId(volumeInfo.id);
            DirEncryptServiceHelper.this.mDew.setExternalSDvolFsUuid(volumeInfo.fsUuid);
            DirEncryptServiceHelper.this.mDew.setExternalSDvolState(environmentForState2);
            boolean equals =
                    "trigger_restart_min_framework"
                            .equals(SystemProperties.get("vold.decrypt", ""));
            if (DirEncryptServiceHelper.this.mDew.getCurrentUserID() != 0
                    || environmentForState == null
                    || environmentForState.equals(environmentForState2)) {
                return;
            }
            StringBuilder sb = new StringBuilder("onVolumeStateChanged:: ");
            sb.append(volumeInfo.id);
            sb.append(" , ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    sb, volumeInfo.path, " , oldstate: ", environmentForState, " newState: ");
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    sb, environmentForState2, "DirEncryptServiceHelper");
            if (equals) {
                return;
            }
            if (!DirEncryptServiceHelper.this.mBootCompleted
                    && "unmounted".equals(environmentForState)
                    && "removed".equals(environmentForState2)) {
                return;
            }
            DirEncryptServiceHelper.this
                    .mHandler
                    .obtainMessage(3, environmentForState2)
                    .sendToTarget();
        }
    }

    public DirEncryptServiceHelper(Context context) {
        this.mDew = null;
        this.mAnimator = null;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.DirEncryptServiceHelper.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, final Intent intent) {
                        new Thread() { // from class: com.android.server.DirEncryptServiceHelper.1.1
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                String action = intent.getAction();
                                Log.i("DirEncryptServiceHelper", "received " + action);
                                if ("com.samsung.android.security.SemSdCardEncryption.UNMOUNT_POLICY"
                                        .equals(action)) {
                                    Log.i("DirEncryptServiceHelper", "Unmount policy noti pressed");
                                    DirEncryptServiceHelper.this
                                            .mHandler
                                            .obtainMessage(10)
                                            .sendToTarget();
                                    return;
                                }
                                if ("android.intent.action.USER_SWITCHED".equals(action)
                                        && DirEncryptServiceHelper.this.mBootCompleted) {
                                    Log.i("DirEncryptServiceHelper", "Switch User");
                                    if (DirEncryptServiceHelper.this.isSdCardEncryped()) {
                                        try {
                                            DirEncryptServiceHelper.this.mDew.unmountVolume();
                                            DirEncryptServiceHelper.this.mDew.mountVolume();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }.start();
                    }
                };
        this.mContext = context;
        this.mAnimator = new AnimatingNotification(context);
        this.mDew = new DirEncryptionWrapper(context);
        context.registerReceiver(
                broadcastReceiver,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.security.SemSdCardEncryption.UNMOUNT_POLICY",
                        "android.intent.action.USER_SWITCHED"),
                null,
                null);
    }

    public static String getCurrentTime() {
        try {
            return new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US)
                    .format(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "Exception : ", "DirEncryptServiceHelper");
            return "Unknown";
        }
    }

    public final void checkSdCardMetafile() {
        this.mLastError = 0;
        this.mErrAdditionalSpace = 0;
        this.mAlreadyDecrypted = false;
        int isAdminApplied = isAdminApplied();
        boolean isCeStorageUnlocked = StorageManager.isCeStorageUnlocked(0);
        boolean z = this.mDew.getKeyguardStoredPasswordQuality() != 0;
        if (isCeStorageUnlocked) {
            z = false;
        }
        String str = SystemProperties.get("sec.fle.encryption.status", "");
        Log.i(
                "DirEncryptServiceHelper",
                "checkSdCardMetafile result:"
                        + str
                        + " unlocked:"
                        + isCeStorageUnlocked
                        + " Policy:"
                        + isAdminApplied
                        + " skipMounting:"
                        + z);
        if ("encrypted".equals(str)) {
            Log.i("DirEncryptServiceHelper", "ENC_META_CHECK : Encryption State Normal");
            if (z) {
                Log.i("DirEncryptServiceHelper", "checkSdCardMetafile but user locked yet");
                return;
            } else {
                this.mSelfSDMountRequested = true;
                doWorkForUSBState(this.mDew.getVolumeState());
            }
        } else if ("decrypting".equals(str) || "encrypting".equals(str)) {
            if ("decrypting".equals(str) && isAdminApplied == 1) {
                Log.i("DirEncryptServiceHelper", "ENC_META_CHECK : EAS Policy Set");
                startCryptSDCardSettingsActivity();
            } else {
                Log.i(
                        "DirEncryptServiceHelper",
                        "SD card has encrypting/decrypting state -> Self Encrypting/Decrypting!!");
                if (z) {
                    Log.i("DirEncryptServiceHelper", "checkSdCardMetafile but user locked yet");
                    return;
                } else {
                    this.mSelfSDMountRequested = true;
                    doWorkForUSBState(this.mDew.getVolumeState());
                }
            }
        } else if (isAdminApplied == 1) {
            Log.i("DirEncryptServiceHelper", "ENC_META_CHECK : EAS Policy Set");
            startCryptSDCardSettingsActivity();
        } else {
            Log.i("DirEncryptServiceHelper", "ENC_META_CHECK : Normal SD Card");
            this.mSelfSDMountRequested = false;
            this.mAlreadyDecrypted = true;
            notifyEncryptionStatusChanged(3, 0, 0, "done");
        }
        mMountSDcardToHelper = false;
    }

    public final void clearNotification() {
        Log.i("DirEncryptServiceHelper", "clearNotification");
        ((NotificationManager) this.mContext.getSystemService("notification"))
                .cancel(SemSdCardEncryption.SECURITY_POLICY_NOTIFICATION_ID);
        this.mPrevPercent = -1;
    }

    public final void doWorkForUSBState(String str) {
        Log.i("DirEncryptServiceHelper", "doWorkForUSBState:: " + str);
        if ("trigger_restart_min_framework".equals(SystemProperties.get("vold.decrypt", "0"))) {
            Log.i("DirEncryptServiceHelper", "Do not work if encryption lock page");
            return;
        }
        boolean z = true;
        if ("HiddenMount".equals(str)) {
            clearNotification();
            ActivityManager.getCurrentUser();
            new LockPatternUtils(this.mContext);
            if (!this.mSelfSDMountRequested) {
                try {
                    if ("1".equals(SystemProperties.get("vold.crypto.ext_migrate", ""))) {
                        return;
                    }
                    checkSdCardMetafile();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            this.mSelfSDMountRequested = false;
            String str2 = SystemProperties.get("sec.fle.encryption.status", "");
            if (!"encrypted".equals(str2) && !"encrypting".equals(str2)) {
                z = false;
            }
            try {
                IBinder service = ServiceManager.getService("mount");
                if (service == null) {
                    Log.e("DirEncryptServiceHelper", "Can't get storagemanager service");
                    throw new RemoteException("Could not contact storagemanager service");
                }
                IStorageManager asInterface = IStorageManager.Stub.asInterface(service);
                if (asInterface != null) {
                    asInterface.encryptExternalStorage(z);
                    return;
                }
                return;
            } catch (RemoteException unused) {
                Log.i("DirEncryptServiceHelper", "Unable to communicate with Mountservice");
                return;
            }
        }
        if ("MoveMount".equals(str)) {
            if (!isSdCardEncryped() && this.mLastError != 8) {
                Log.i(
                        "DirEncryptServiceHelper",
                        "Since encrypt is OFF: no final mount command (DECRYPTED)");
                setStatus(0);
                notifyEncryptionStatusChanged(3, 0, 0, "Mount");
                Log.i("DirEncryptServiceHelper", "delete uuid");
                if (this.mAlreadyDecrypted) {
                    Log.i("DirEncryptServiceHelper", "don't send encryption notification");
                    return;
                } else {
                    showNotification(2, 0, "success");
                    return;
                }
            }
            if (isSdCardEncryped()) {
                if (isSdCardEncryped()) {
                    Log.i(
                            "DirEncryptServiceHelper",
                            "MOVE_MOUNT => ENCRYPTING, ENCRYPTED or OTHER_ENCRYPT");
                    return;
                }
                return;
            } else {
                Log.i(
                        "DirEncryptServiceHelper",
                        "looks like encryption policies were received while SD card decryption was"
                            + " on going (DECRYPTING)!!");
                showNotification(2, 0, "success");
                setStatus(0);
                notifyEncryptionStatusChanged(3, 0, 0, "busy");
                return;
            }
        }
        if ("mounted".equals(str)) {
            Log.i("DirEncryptServiceHelper", "Update SD card encryption status");
            String str3 = SystemProperties.get("sec.vold.ext_encrypted_type");
            String str4 = SystemProperties.get("sec.fle.encryption.status");
            if (!"block".equals(str3) || !"encrypted".equals(str4)) {
                setStatus(0);
                notifyEncryptionStatusChanged(3, 0, 0, "free");
                return;
            }
            this.mLastError = 0;
            setStatus(0);
            showNotification(2, 1, "success");
            notifyEncryptionStatusChanged(2, 0, 0, "Mount");
            Log.d("DirEncryptServiceHelper", "ENCRYPT Response admin : " + isAdminApplied());
            this.mEncryptTimestamp = getCurrentTime();
            this.mDecryptTimestamp = "";
            return;
        }
        if ("removed".equals(str)) {
            this.mUnnmountRequested = false;
            this.mSelfSDMountRequested = false;
            this.mLastError = 0;
            Log.i("DirEncryptServiceHelper", "SD card removed");
            clearNotification();
            setStatus(0);
            notifyEncryptionStatusChanged(0, 0, 0, "free");
            return;
        }
        if ("unmounted".equals(str)) {
            Log.i("DirEncryptServiceHelper", "SD card unmounted");
            if (this.mLastError == 0) {
                clearNotification();
            }
            if (this.mUnnmountRequested) {
                this.mUnnmountRequested = false;
                showNotification(10, 0, null);
                return;
            }
            return;
        }
        if ("bad_removal".equals(str)) {
            this.mUnnmountRequested = false;
            Log.i("DirEncryptServiceHelper", "SD card bad removed");
            if (this.mLastError == 0) {
                clearNotification();
                setStatus(0);
                notifyEncryptionStatusChanged(0, 0, 0, "free");
            }
            this.mSelfSDMountRequested = false;
            this.mLastError = 0;
        }
    }

    public final Notification.Builder getNotification(
            PendingIntent pendingIntent, int i, String str, String str2, String str3) {
        Notification.Builder builder = new Notification.Builder(this.mContext);
        builder.setSmallIcon(i);
        builder.setPriority(0);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        builder.setContentTitle(str);
        builder.setContentText(str2);
        builder.setStyle(new Notification.BigTextStyle().bigText(str2));
        builder.setTicker(str3);
        builder.setChannelId("sdcard_encryption_channel");
        return builder;
    }

    public final String getTopClassName() {
        List<ActivityManager.RunningTaskInfo> runningTasks =
                ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1);
        return !runningTasks.isEmpty() ? runningTasks.get(0).topActivity.getClassName() : "";
    }

    public final int isAdminApplied() {
        SemSdCardEncryptionPolicy semSdCardEncryptionPolicy = new SemSdCardEncryptionPolicy();
        if (!((DevicePolicyManager) this.mContext.getSystemService("device_policy"))
                .semGetRequireStorageCardEncryption(null)) {
            return 0;
        }
        semSdCardEncryptionPolicy.mIsPolicy = 1;
        return 1;
    }

    public final boolean isSdCardEncryped() {
        String str = SystemProperties.get("sec.fle.encryption.status", "");
        String str2 = SystemProperties.get("persist.vold.ext_encrypted_type", "");
        String volumeState = this.mDew.getVolumeState();
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "isSdCardEncryped state: ",
                        volumeState,
                        " isExistMeta: ",
                        str,
                        " isEncryptionType: "),
                str2,
                "DirEncryptServiceHelper");
        if (volumeState == null || !"mounted".equals(volumeState)) {
            return false;
        }
        return "encrypted".equals(str) || "encrypting".equals(str);
    }

    public final void notifyEncryptionStatusChanged(int i, int i2, int i3, String str) {
        Log.i("DirEncryptServiceHelper", "notifyEncryptionStatusChanged: " + i + " " + str);
        synchronized (this.mListeners) {
            int beginBroadcast = this.mListeners.beginBroadcast();
            for (int i4 = 0; i4 < beginBroadcast; i4++) {
                IDirEncryptServiceListener broadcastItem = this.mListeners.getBroadcastItem(i4);
                try {
                    try {
                        Log.i("DirEncryptServiceHelper", "Listener :" + broadcastItem);
                        broadcastItem.onEncryptionStatusChanged(
                                this.mDew.getExternalSDvolId(), i, str, i2, i3);
                    } catch (Exception e) {
                        Log.i("DirEncryptServiceHelper", "Listener failed: " + e);
                    }
                } catch (RemoteException unused) {
                    Log.i("DirEncryptServiceHelper", "Listener dead");
                }
            }
            this.mListeners.finishBroadcast();
        }
    }

    public final void setStatus(int i) {
        DirEncryptService$$ExternalSyntheticOutline0.m(i, "setStatus:", "DirEncryptServiceHelper");
        this.mServiceStatus = i;
    }

    public final void showNotification(int i, int i2, String str) {
        String string;
        String string2;
        int i3;
        String str2;
        int i4;
        String str3;
        PendingIntent pendingIntent;
        String string3;
        PendingIntent activity;
        String string4;
        int i5;
        String str4;
        String str5;
        String string5;
        String string6;
        String string7;
        String string8;
        PendingIntent activity2;
        String str6 = "";
        if (i != 1) {
            int i6 = R.drawable.dialog_ic_close_normal_holo_light;
            if (i != 2) {
                if (i == 3) {
                    if (i2 == 1) {
                        string5 = this.mContext.getResources().getString(17042740);
                        string6 = this.mContext.getResources().getString(17042738);
                    } else {
                        string5 = this.mContext.getResources().getString(17042736);
                        string6 = this.mContext.getResources().getString(17042733);
                    }
                    str6 = string5;
                    str2 = str6;
                    str3 = string6;
                    pendingIntent =
                            PendingIntent.getActivity(
                                    this.mContext,
                                    0,
                                    new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"),
                                    201326592);
                    i4 = 17302328;
                } else {
                    if (i == 5) {
                        int parseInt = Integer.parseInt(str);
                        synchronized (this.mAnimateSync) {
                            try {
                                if (this.mProgressTime == -1) {
                                    this.mProgressTime = System.currentTimeMillis();
                                }
                                AnimatingNotification animatingNotification = this.mAnimator;
                                animatingNotification.mProgress = parseInt;
                                animatingNotification.mEnctype = i2;
                                if (parseInt == 0) {
                                    animatingNotification.mStop = false;
                                    this.mHandler.post(animatingNotification);
                                } else if (100 == parseInt) {
                                    animatingNotification.mStop = true;
                                    animatingNotification.notification_builder = null;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return;
                    }
                    if (i == 6) {
                        this.mErrAdditionalSpace = Integer.parseInt(str);
                        if (i2 == 1) {
                            string7 = this.mContext.getResources().getString(17042740);
                            string8 = this.mContext.getResources().getString(17042738);
                            i6 = 17302328;
                        } else {
                            string7 = this.mContext.getResources().getString(17042736);
                            string8 = this.mContext.getResources().getString(17042733);
                        }
                        activity2 =
                                PendingIntent.getActivity(
                                        this.mContext,
                                        0,
                                        new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"),
                                        201326592);
                        this.mLastError = 4;
                    } else if (i == 7) {
                        String string9 = this.mContext.getResources().getString(17042787);
                        String string10 = this.mContext.getResources().getString(17042786);
                        Context context = this.mContext;
                        Intent intent = new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION");
                        intent.putExtra("adminStart", "1");
                        PendingIntent activity3 =
                                PendingIntent.getActivity(context, 0, intent, 201326592);
                        str2 = string9;
                        str3 = string10;
                        pendingIntent = activity3;
                        i4 = R.drawable.fastscroll_track_default_holo_light;
                    } else if (i == 10) {
                        String string11 = this.mContext.getResources().getString(17042748);
                        String string12 = this.mContext.getResources().getString(17042747);
                        PendingIntent broadcast =
                                PendingIntent.getBroadcast(
                                        this.mContext,
                                        0,
                                        new Intent(
                                                "com.samsung.android.security.SemSdCardEncryption.UNMOUNT_POLICY"),
                                        201326592);
                        this.mLastError = 0;
                        str2 = string11;
                        str3 = string12;
                        pendingIntent = broadcast;
                        i4 = 17301627;
                        str6 = str2;
                    } else {
                        if (i != 11) {
                            AnimatingNotification animatingNotification2 = this.mAnimator;
                            if (!animatingNotification2.mStop) {
                                animatingNotification2.mStop = true;
                                animatingNotification2.notification_builder = null;
                                return;
                            }
                            return;
                        }
                        if (i2 == 1) {
                            string7 = this.mContext.getResources().getString(17042740);
                            string8 = this.mContext.getResources().getString(17042738);
                            i6 = 17302328;
                        } else {
                            string7 = this.mContext.getResources().getString(17042736);
                            string8 = this.mContext.getResources().getString(17042733);
                        }
                        this.mLastError = 11;
                        activity2 =
                                PendingIntent.getActivity(
                                        this.mContext,
                                        0,
                                        new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"),
                                        201326592);
                    }
                    str6 = string7;
                    str2 = str6;
                    str3 = string8;
                    pendingIntent = activity2;
                    i4 = i6;
                }
            } else if (i2 == 1) {
                string3 = this.mContext.getResources().getString(17042740);
                if ("success".equals(str)) {
                    str4 = this.mContext.getResources().getString(17042739);
                    this.mLastError = 0;
                    str5 = string3;
                    pendingIntent = null;
                    i5 = R.drawable.fastscroll_track_default_holo_light;
                    str2 = str4;
                    str6 = str5;
                    i4 = i5;
                    str3 = str2;
                } else if ("exception".equals(str)) {
                    String string13 = this.mContext.getResources().getString(17042734);
                    this.mLastError = 8;
                    str4 = string13;
                    i5 = 17302328;
                    str5 = string3;
                    pendingIntent = null;
                    str2 = str4;
                    str6 = str5;
                    i4 = i5;
                    str3 = str2;
                } else {
                    activity =
                            PendingIntent.getActivity(
                                    this.mContext,
                                    0,
                                    new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"),
                                    201326592);
                    string4 = this.mContext.getResources().getString(17042738);
                    this.mLastError = 5;
                    i5 = 17302328;
                    String str7 = string3;
                    pendingIntent = activity;
                    str4 = string4;
                    str5 = str7;
                    str2 = str4;
                    str6 = str5;
                    i4 = i5;
                    str3 = str2;
                }
            } else {
                string3 = this.mContext.getResources().getString(17042736);
                if ("success".equals(str)) {
                    str4 = this.mContext.getResources().getString(17042735);
                    this.mLastError = 0;
                    str5 = string3;
                    pendingIntent = null;
                    i5 = R.drawable.dialog_ic_close_pressed_holo_dark;
                    str2 = str4;
                    str6 = str5;
                    i4 = i5;
                    str3 = str2;
                } else if ("exception".equals(str)) {
                    String string14 = this.mContext.getResources().getString(17042734);
                    this.mLastError = 8;
                    str4 = string14;
                    i5 = 17302236;
                    str5 = string3;
                    pendingIntent = null;
                    str2 = str4;
                    str6 = str5;
                    i4 = i5;
                    str3 = str2;
                } else {
                    activity =
                            PendingIntent.getActivity(
                                    this.mContext,
                                    0,
                                    new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"),
                                    201326592);
                    string4 = this.mContext.getResources().getString(17042733);
                    this.mLastError = 6;
                    i5 = 17302236;
                    String str72 = string3;
                    pendingIntent = activity;
                    str4 = string4;
                    str5 = str72;
                    str2 = str4;
                    str6 = str5;
                    i4 = i5;
                    str3 = str2;
                }
            }
        } else {
            if (i2 == 1) {
                string = this.mContext.getResources().getString(17042740);
                string2 = this.mContext.getResources().getString(17042741);
                i3 = R.drawable.fastscroll_track_default_holo_light;
            } else {
                string = this.mContext.getResources().getString(17042736);
                string2 = this.mContext.getResources().getString(17042737);
                i3 = R.drawable.dialog_ic_close_pressed_holo_dark;
            }
            str6 = string;
            str2 = str6;
            i4 = i3;
            str3 = string2;
            pendingIntent = null;
        }
        AnimatingNotification animatingNotification3 = this.mAnimator;
        if (!animatingNotification3.mStop) {
            animatingNotification3.mStop = true;
            animatingNotification3.notification_builder = null;
        }
        Notification build = getNotification(pendingIntent, i4, str6, str3, str2).build();
        if (build != null) {
            build.flags |= 17;
            build.defaults |= 4;
            NotificationManager notificationManager =
                    (NotificationManager) this.mContext.getSystemService("notification");
            notificationManager.createNotificationChannel(
                    new NotificationChannel(
                            "sdcard_encryption_channel",
                            this.mContext.getResources().getString(17042740),
                            2));
            notificationManager.notify(SemSdCardEncryption.SECURITY_POLICY_NOTIFICATION_ID, build);
        }
    }

    public final void startCryptSDCardSettingsActivity() {
        Log.d("DirEncryptServiceHelper", "isCryptSDCardSettings : " + getTopClassName());
        if (getTopClassName() == null
                || !getTopClassName()
                        .equals("com.android.settings.Settings$CryptSDCardSettingsActivity")) {
            Log.d("DirEncryptServiceHelper", "startCryptSDCardSettingsActivity");
            Intent intent = new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION");
            intent.setFlags(272629760);
            if (mMountSDcardToHelper) {
                return;
            }
            try {
                this.mContext.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.e(
                        "DirEncryptServiceHelper",
                        "startCryptSDCardSettingsActivity Failed to start intent activity" + e);
            }
        }
    }
}
