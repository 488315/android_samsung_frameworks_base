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
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
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
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.samsung.android.security.DirEncryptionWrapper;
import com.samsung.android.security.IDirEncryptServiceListener;
import com.samsung.android.security.SemSdCardEncryption;
import com.samsung.android.security.SemSdCardEncryptionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class DirEncryptServiceHelper {
    public static boolean mMountSDcardToHelper = false;
    public static boolean mNeedToCreateKey = false;
    public static String mNotificationChannelID = "sdcard_encryption_channel";
    public static Object mSync = new Object();
    public AnimatingNotification mAnimator;
    public final BroadcastReceiver mBroadcastReceiver;
    public Context mContext;
    public DirEncryptionWrapper mDew;
    public SDStorageEventListener mStorageEventListener = new SDStorageEventListener();
    public Handler mHandler = null;
    public boolean mSelfSDMountRequested = false;
    public boolean mUnnmountRequested = false;
    public boolean mFirstUnlockLockscreen = false;
    public boolean mAlreadyDecrypted = false;
    public boolean mBootCompleted = false;
    public String mEncryptTimestamp = "";
    public String mDecryptTimestamp = "";
    public int mErrAdditionalSpace = 0;
    public int mLastError = 0;
    public int mServiceStatus = 0;
    public final RemoteCallbackList mListeners = new RemoteCallbackList();
    public Object mAnimateSync = new Object();
    public long mProgressTime = -1;
    public int mPrevPercent = -1;

    public static boolean isSupportBlockEncryption() {
        return true;
    }

    public static IStorageManager getStorageManagerService() {
        IBinder service = ServiceManager.getService("mount");
        if (service != null) {
            return IStorageManager.Stub.asInterface(service);
        }
        Log.e("DirEncryptServiceHelper", "Can't get storagemanager service");
        throw new RemoteException("Could not contact storagemanager service");
    }

    public class AnimatingNotification implements Runnable {
        public Context mContext;
        public boolean mStop = false;
        public int mEnctype = 0;
        public int mProgress = 0;
        public Notification.Builder notification_builder = null;

        public AnimatingNotification(Context context) {
            this.mContext = context;
        }

        public void stop() {
            this.mStop = true;
            this.notification_builder = null;
        }

        public void start() {
            this.mStop = false;
        }

        public boolean isInProgress() {
            return !this.mStop;
        }

        @Override // java.lang.Runnable
        public void run() {
            String string;
            String string2;
            int i;
            synchronized (DirEncryptServiceHelper.this.mAnimateSync) {
                if (this.mEnctype == 1) {
                    string = this.mContext.getResources().getString(17042542);
                    string2 = this.mContext.getResources().getString(17042541);
                    i = R.drawable.dropdown_ic_arrow_focused_holo_dark;
                } else {
                    string = this.mContext.getResources().getString(17042538);
                    string2 = this.mContext.getResources().getString(17042537);
                    i = R.drawable.cab_background_bottom_mtrl_alpha;
                }
                String str = string;
                if (this.notification_builder == null) {
                    this.notification_builder = DirEncryptServiceHelper.this.getNotification(null, i, string2, str, str);
                }
                this.notification_builder.setSmallIcon(i);
                this.notification_builder.setOngoing(true);
                this.notification_builder.setProgress(100, this.mProgress, false);
                this.notification_builder.setContentTitle(string2 + ": \u202a" + this.mProgress + "%");
                if (!this.mStop) {
                    NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
                    notificationManager.createNotificationChannel(new NotificationChannel(DirEncryptServiceHelper.mNotificationChannelID, this.mContext.getResources().getString(17042541), 2));
                    notificationManager.notify(SemSdCardEncryption.SECURITY_POLICY_NOTIFICATION_ID, this.notification_builder.build());
                    DirEncryptServiceHelper.this.mHandler.postDelayed(this, 500L);
                } else {
                    this.notification_builder = null;
                }
            }
        }
    }

    public class SDStorageEventListener extends StorageEventListener {
        public SDStorageEventListener() {
        }

        public void onVolumeStateChanged(VolumeInfo volumeInfo, int i, int i2) {
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
                Log.i("DirEncryptServiceHelper", "newState is a same state with oldState:: newState: " + environmentForState2 + " , oldState: " + environmentForState + " !!!");
            }
            Log.i("DirEncryptServiceHelper", "onVolumeStateChanged UUID : " + volumeInfo.fsUuid);
            DirEncryptServiceHelper.this.mDew.setExternalSDvolId(volumeInfo.id);
            DirEncryptServiceHelper.this.mDew.setExternalSDvolFsUuid(volumeInfo.fsUuid);
            DirEncryptServiceHelper.this.mDew.setExternalSDvolState(environmentForState2);
            boolean equals = "trigger_restart_min_framework".equals(SystemProperties.get("vold.decrypt", ""));
            if (DirEncryptServiceHelper.this.mDew.getCurrentUserID() != 0 || environmentForState == null || environmentForState.equals(environmentForState2)) {
                return;
            }
            Log.i("DirEncryptServiceHelper", "onVolumeStateChanged:: " + volumeInfo.id + " , " + volumeInfo.path + " , oldstate: " + environmentForState + " newState: " + environmentForState2);
            if (equals) {
                return;
            }
            if (!DirEncryptServiceHelper.this.mBootCompleted && "unmounted".equals(environmentForState) && "removed".equals(environmentForState2)) {
                return;
            }
            DirEncryptServiceHelper.this.mHandler.obtainMessage(3, environmentForState2).sendToTarget();
        }
    }

    public DirEncryptServiceHelper(Context context) {
        this.mDew = null;
        this.mAnimator = null;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.DirEncryptServiceHelper.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, final Intent intent) {
                new Thread() { // from class: com.android.server.DirEncryptServiceHelper.1.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        String action = intent.getAction();
                        Log.i("DirEncryptServiceHelper", "received " + action);
                        if ("com.samsung.android.security.SemSdCardEncryption.UNMOUNT_POLICY".equals(action)) {
                            Log.i("DirEncryptServiceHelper", "Unmount policy noti pressed");
                            DirEncryptServiceHelper.this.mHandler.obtainMessage(10).sendToTarget();
                            return;
                        }
                        if ("android.intent.action.USER_SWITCHED".equals(action) && DirEncryptServiceHelper.this.mBootCompleted) {
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
        this.mBroadcastReceiver = broadcastReceiver;
        this.mContext = context;
        this.mAnimator = new AnimatingNotification(context);
        this.mDew = new DirEncryptionWrapper(this.mContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.security.SemSdCardEncryption.UNMOUNT_POLICY");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    public boolean registerStorageEventListener() {
        if (!this.mDew.registerStorageEventListener(this.mStorageEventListener)) {
            Log.i("DirEncryptServiceHelper", "RegisterStorageEventListner fail");
            return false;
        }
        Log.i("DirEncryptServiceHelper", "RegisterStorageEventListner success");
        return true;
    }

    public void setNeedToCreateKey(boolean z) {
        mNeedToCreateKey = z;
    }

    public void setMountSDcardToHelper(boolean z) {
        mMountSDcardToHelper = z;
    }

    public int getCurrentStatus() {
        return this.mServiceStatus;
    }

    public int getLastError() {
        return this.mLastError;
    }

    public int getAdditionalSpaceRequired() {
        return this.mErrAdditionalSpace;
    }

    public String getEncryptTimestamp() {
        return this.mEncryptTimestamp;
    }

    public String getDecryptTimestamp() {
        return this.mDecryptTimestamp;
    }

    public final void setStatus(int i) {
        Log.i("DirEncryptServiceHelper", "setStatus:" + i);
        this.mServiceStatus = i;
    }

    public void setBootComplted(boolean z) {
        this.mBootCompleted = z;
    }

    public void ready() {
        setStatus(1);
    }

    public void setExecParams(Handler handler) {
        this.mHandler = handler;
    }

    public int isStorageCardEncryptionPoliciesApplied() {
        return isAdminApplied();
    }

    public void registerListener(IDirEncryptServiceListener iDirEncryptServiceListener) {
        this.mListeners.register(iDirEncryptServiceListener);
    }

    public void unregisterListener(IDirEncryptServiceListener iDirEncryptServiceListener) {
        this.mListeners.unregister(iDirEncryptServiceListener);
    }

    public void doHandleMessage(Message message) {
        synchronized (mSync) {
            int i = message.what;
            if (i == 2) {
                unmountSDCard();
            } else if (i == 3) {
                String str = (String) message.obj;
                Log.i("DirEncryptServiceHelper", "Command.USB_STATE:: " + str);
                doWorkForUSBState(str);
            } else if (i == 10) {
                this.mDew.mountVolume();
            } else {
                Log.e("DirEncryptServiceHelper", "Invalid command!!!");
            }
        }
    }

    public boolean onEventInner(int i, PersistableBundle persistableBundle) {
        synchronized (mSync) {
            persistableBundle.getString("path");
            int i2 = persistableBundle.getInt("status");
            String string = persistableBundle.getString("description");
            int i3 = persistableBundle.getInt("arg1");
            int i4 = persistableBundle.getInt("arg2");
            Log.i("DirEncryptServiceHelper", "onEventInner resp_code: " + i + " status : " + i2);
            int i5 = 3;
            switch (i) {
                case 683:
                case 691:
                    if ("success".equals(string)) {
                        Log.i("DirEncryptServiceHelper", "RESP_ENCRYPT success");
                        this.mLastError = 0;
                        setStatus(0);
                        showNotification(2, i2, "success");
                        if (i2 != 0) {
                            i5 = 2;
                        }
                        notifyEncryptionStatusChanged(i5, "Mount");
                        Log.i("DirEncryptServiceHelper", "ENCRYPT Response admin : " + isAdminApplied());
                        if (i2 == 0) {
                            Log.i("DirEncryptServiceHelper", "Decrypt compelte");
                            this.mEncryptTimestamp = "";
                            this.mDecryptTimestamp = getCurrentTime();
                            break;
                        } else {
                            Log.i("DirEncryptServiceHelper", "Encrypt complete");
                            this.mEncryptTimestamp = getCurrentTime();
                            this.mDecryptTimestamp = "";
                            break;
                        }
                    } else if ("exception".equals(string)) {
                        Log.i("DirEncryptServiceHelper", "RESP_ENCRYPT : encrypted on other device");
                        this.mLastError = 8;
                        setStatus(0);
                        showNotification(2, i2, "exception");
                        if (i2 != 0) {
                            i5 = 2;
                        }
                        notifyEncryptionStatusChanged(i5, "Mount");
                        Log.i("DirEncryptServiceHelper", "ENCRYPT Response admin : " + isAdminApplied());
                        if (i2 == 0) {
                            Log.i("DirEncryptServiceHelper", "Decrypt compelte");
                            this.mEncryptTimestamp = "";
                            this.mDecryptTimestamp = getCurrentTime();
                            break;
                        } else {
                            Log.i("DirEncryptServiceHelper", "Encrypt complete");
                            this.mEncryptTimestamp = getCurrentTime();
                            this.mDecryptTimestamp = "";
                            break;
                        }
                    } else {
                        Log.i("DirEncryptServiceHelper", "RESP_ENCRYPT failed");
                        if (this.mLastError == 0) {
                            if (i2 == 1) {
                                this.mLastError = 5;
                            } else {
                                this.mLastError = 6;
                            }
                        }
                        showNotification(2, i2, "failed");
                        setStatus(0);
                        if (i2 != 0) {
                            i5 = 2;
                        }
                        notifyEncryptionStatusChanged(i5, "free");
                        break;
                    }
                case FrameworkStatsLog.MEDIA_CODEC_RENDERED /* 684 */:
                    Log.i("DirEncryptServiceHelper", "DirEncryptServiceHelperVoldResponse.PROGRESS percentage(" + i3 + "), storage(" + i4 + ")");
                    if (i2 == 1) {
                        setStatus(2);
                    } else {
                        setStatus(3);
                    }
                    if (i3 - this.mPrevPercent >= 1 && !isSupportBlockEncryption()) {
                        showNotification(5, i2, "" + i3);
                        this.mPrevPercent = i3;
                    }
                    if (i3 == 100) {
                        this.mPrevPercent = -1;
                    }
                    if (i2 != 0) {
                        i5 = 2;
                    }
                    notifyEncryptionStatusChanged(i5, "busy", i3, i4);
                    break;
                case 686:
                    this.mErrAdditionalSpace = i3;
                    this.mLastError = 4;
                    showNotification(6, i2, "" + i3);
                    setStatus(0);
                    if (i2 != 0) {
                        i5 = 2;
                    }
                    notifyEncryptionStatusChanged(i5, "free");
                    break;
                case 687:
                    this.mLastError = 7;
                    setStatus(0);
                    if (i2 != 0) {
                        i5 = 2;
                    }
                    notifyEncryptionStatusChanged(i5, "free");
                    break;
                case 692:
                    this.mLastError = 0;
                    this.mErrAdditionalSpace = 0;
                    this.mAlreadyDecrypted = false;
                    String volumeState = this.mDew.getVolumeState();
                    if (volumeState == null) {
                        Log.i("DirEncryptServiceHelper", "there is no SD card");
                        break;
                    } else {
                        if (i2 == 1) {
                            Log.i("DirEncryptServiceHelper", "ENC_META_CHECK : Encryption State Normal");
                            this.mSelfSDMountRequested = true;
                            doWorkForUSBState(volumeState);
                        } else if (isAdminApplied() == 1) {
                            Log.d("DirEncryptServiceHelper", "ENC_META_CHECK : EAS Policy Set" + string);
                            if (isSupportBlockEncryption() && !"".equals(string)) {
                                this.mDew.setExternalSDvolUsedSize(Long.parseLong(string));
                            }
                            startCryptSDCardSettingsActivity();
                        } else {
                            Log.i("DirEncryptServiceHelper", "ENC_META_CHECK : Normal SD Card");
                            this.mSelfSDMountRequested = false;
                            this.mAlreadyDecrypted = true;
                        }
                        mMountSDcardToHelper = false;
                        break;
                    }
                    break;
                case 693:
                    this.mLastError = 11;
                    showNotification(11, i2, "");
                    setStatus(0);
                    if (i2 != 0) {
                        i5 = 2;
                    }
                    notifyEncryptionStatusChanged(i5, "free");
                    break;
            }
        }
        return true;
    }

    public final void notifyEncryptionStatusChanged(int i, String str) {
        notifyEncryptionStatusChanged(i, str, 0, 0);
    }

    public final void notifyEncryptionStatusChanged(int i, String str, int i2, int i3) {
        Log.i("DirEncryptServiceHelper", "notifyEncryptionStatusChanged: " + i + " " + str);
        synchronized (this.mListeners) {
            int beginBroadcast = this.mListeners.beginBroadcast();
            for (int i4 = 0; i4 < beginBroadcast; i4++) {
                IDirEncryptServiceListener broadcastItem = this.mListeners.getBroadcastItem(i4);
                try {
                    Log.i("DirEncryptServiceHelper", "Listener :" + broadcastItem);
                    broadcastItem.onEncryptionStatusChanged(this.mDew.getExternalSDvolId(), i, str, i2, i3);
                } catch (RemoteException unused) {
                    Log.i("DirEncryptServiceHelper", "Listener dead");
                } catch (Exception e) {
                    Log.i("DirEncryptServiceHelper", "Listener failed: " + e);
                }
            }
            this.mListeners.finishBroadcast();
        }
    }

    public final void unmountSDCard() {
        this.mSelfSDMountRequested = true;
        try {
            this.mDew.unmountVolume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unmountSDCardByAdmin() {
        String volumeState = this.mDew.getVolumeState();
        if (volumeState != null) {
            Log.i("DirEncryptServiceHelper", "SD card  : State = " + volumeState + " / Admin policy : " + isAdminApplied() + " / Encrypted : " + isSdCardEncryped());
            if (isAdminApplied() == 1 && !isSdCardEncryped() && "mounted".equals(volumeState)) {
                Log.i("DirEncryptServiceHelper", "Unmount SD Card By Admin");
                this.mUnnmountRequested = true;
                try {
                    this.mDew.unmountVolume();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final int isAdminApplied() {
        SemSdCardEncryptionPolicy semSdCardEncryptionPolicy = new SemSdCardEncryptionPolicy();
        if (!((DevicePolicyManager) this.mContext.getSystemService("device_policy")).semGetRequireStorageCardEncryption(null)) {
            return 0;
        }
        semSdCardEncryptionPolicy.mIsPolicy = 1;
        return 1;
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
            if (this.mSelfSDMountRequested) {
                this.mSelfSDMountRequested = false;
                String str2 = SystemProperties.get("sec.fle.encryption.status", "");
                if (!"encrypted".equals(str2) && !"encrypting".equals(str2)) {
                    z = false;
                }
                try {
                    IStorageManager storageManagerService = getStorageManagerService();
                    if (storageManagerService != null) {
                        storageManagerService.encryptExternalStorage(z);
                        return;
                    }
                    return;
                } catch (RemoteException unused) {
                    Log.i("DirEncryptServiceHelper", "Unable to communicate with Mountservice");
                    return;
                }
            }
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
        if ("MoveMount".equals(str)) {
            if (!isSdCardEncryped() && this.mLastError != 8) {
                Log.i("DirEncryptServiceHelper", "Since encrypt is OFF: no final mount command (DECRYPTED)");
                setStatus(0);
                notifyEncryptionStatusChanged(3, "Mount");
                Log.i("DirEncryptServiceHelper", "delete uuid");
                if (!this.mAlreadyDecrypted) {
                    showNotification(2, 0, "success");
                    return;
                } else {
                    Log.i("DirEncryptServiceHelper", "don't send encryption notification");
                    return;
                }
            }
            if (!isSdCardEncryped()) {
                Log.i("DirEncryptServiceHelper", "looks like encryption policies were received while SD card decryption was on going (DECRYPTING)!!");
                showNotification(2, 0, "success");
                setStatus(0);
                notifyEncryptionStatusChanged(3, "busy");
                return;
            }
            if (isSdCardEncryped()) {
                Log.i("DirEncryptServiceHelper", "MOVE_MOUNT => ENCRYPTING, ENCRYPTED or OTHER_ENCRYPT");
                return;
            }
            return;
        }
        if ("mounted".equals(str)) {
            Log.i("DirEncryptServiceHelper", "Update SD card encryption status");
            String str3 = SystemProperties.get("sec.vold.ext_encrypted_type");
            String str4 = SystemProperties.get("sec.fle.encryption.status");
            if ("block".equals(str3) && "encrypted".equals(str4)) {
                this.mLastError = 0;
                setStatus(0);
                showNotification(2, 1, "success");
                notifyEncryptionStatusChanged(2, "Mount");
                Log.d("DirEncryptServiceHelper", "ENCRYPT Response admin : " + isAdminApplied());
                this.mEncryptTimestamp = getCurrentTime();
                this.mDecryptTimestamp = "";
                return;
            }
            setStatus(0);
            notifyEncryptionStatusChanged(3, "free");
            return;
        }
        if ("removed".equals(str)) {
            this.mUnnmountRequested = false;
            this.mSelfSDMountRequested = false;
            this.mLastError = 0;
            Log.i("DirEncryptServiceHelper", "SD card removed");
            clearNotification();
            setStatus(0);
            notifyEncryptionStatusChanged(0, "free");
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
                notifyEncryptionStatusChanged(0, "free");
            }
            this.mSelfSDMountRequested = false;
            this.mLastError = 0;
        }
    }

    public boolean isSdCardEncryped() {
        String str = SystemProperties.get("sec.fle.encryption.status", "");
        String str2 = SystemProperties.get("persist.vold.ext_encrypted_type", "");
        String volumeState = this.mDew.getVolumeState();
        Log.i("DirEncryptServiceHelper", "isSdCardEncryped state: " + volumeState + " isExistMeta: " + str + " isEncryptionType: " + str2);
        if (volumeState == null) {
            return false;
        }
        if (isSupportBlockEncryption()) {
            if ("mounted".equals(volumeState)) {
                return "block".equals(str2) || "encrypted".equals(str);
            }
            return false;
        }
        if ("mounted".equals(volumeState)) {
            return "encrypted".equals(str) || "encrypting".equals(str);
        }
        return false;
    }

    public final boolean isCryptSDCardSettings() {
        Log.d("DirEncryptServiceHelper", "isCryptSDCardSettings : " + getTopClassName());
        return getTopClassName() != null && getTopClassName().equals("com.android.settings.Settings$CryptSDCardSettingsActivity");
    }

    public final String getTopClassName() {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1);
        return !runningTasks.isEmpty() ? runningTasks.get(0).topActivity.getClassName() : "";
    }

    public final void startCryptSDCardSettingsActivity() {
        if (isCryptSDCardSettings()) {
            return;
        }
        Log.d("DirEncryptServiceHelper", "startCryptSDCardSettingsActivity");
        Intent intent = new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION");
        intent.setFlags(272629760);
        if (mMountSDcardToHelper) {
            return;
        }
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("DirEncryptServiceHelper", "startCryptSDCardSettingsActivity Failed to start intent activity" + e);
        }
    }

    public final void checkSdCardMetafile() {
        this.mLastError = 0;
        this.mErrAdditionalSpace = 0;
        this.mAlreadyDecrypted = false;
        int isAdminApplied = isAdminApplied();
        boolean isUserKeyUnlocked = StorageManager.isUserKeyUnlocked(0);
        boolean z = this.mDew.getKeyguardStoredPasswordQuality() != 0;
        if (isUserKeyUnlocked) {
            z = false;
        }
        String str = SystemProperties.get("sec.fle.encryption.status", "");
        Log.i("DirEncryptServiceHelper", "checkSdCardMetafile result:" + str + " unlocked:" + isUserKeyUnlocked + " Policy:" + isAdminApplied + " skipMounting:" + z);
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
                Log.i("DirEncryptServiceHelper", "SD card has encrypting/decrypting state -> Self Encrypting/Decrypting!!");
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
            notifyEncryptionStatusChanged(3, "done");
        }
        mMountSDcardToHelper = false;
    }

    public final Notification.Builder getNotification(PendingIntent pendingIntent, int i, String str, String str2, String str3) {
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
        builder.setChannelId(mNotificationChannelID);
        return builder;
    }

    public final Intent getSecurityIntent() {
        Intent intent = new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION");
        intent.putExtra("adminStart", "1");
        return intent;
    }

    public final void clearNotification() {
        Log.i("DirEncryptServiceHelper", "clearNotification");
        ((NotificationManager) this.mContext.getSystemService("notification")).cancel(SemSdCardEncryption.SECURITY_POLICY_NOTIFICATION_ID);
        this.mPrevPercent = -1;
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
        String string5;
        String string6;
        PendingIntent activity2;
        String str4 = "";
        if (i != 1) {
            int i6 = R.drawable.dropdown_ic_arrow_focused_holo_light;
            if (i != 2) {
                if (i == 3) {
                    if (i2 == 1) {
                        string5 = this.mContext.getResources().getString(17042541);
                        string6 = this.mContext.getResources().getString(17042539);
                    } else {
                        string5 = this.mContext.getResources().getString(17042537);
                        string6 = this.mContext.getResources().getString(17042534);
                    }
                    activity2 = PendingIntent.getActivity(this.mContext, 0, new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"), 201326592);
                } else {
                    if (i == 5) {
                        int parseInt = Integer.parseInt(str);
                        synchronized (this.mAnimateSync) {
                            if (this.mProgressTime == -1) {
                                this.mProgressTime = System.currentTimeMillis();
                            }
                            this.mAnimator.mProgress = parseInt;
                            this.mAnimator.mEnctype = i2;
                            if (parseInt == 0) {
                                this.mAnimator.start();
                                this.mHandler.post(this.mAnimator);
                            } else if (100 == parseInt) {
                                this.mAnimator.stop();
                            }
                        }
                        return;
                    }
                    if (i == 6) {
                        this.mErrAdditionalSpace = Integer.parseInt(str);
                        if (i2 == 1) {
                            string5 = this.mContext.getResources().getString(17042541);
                            string6 = this.mContext.getResources().getString(17042539);
                        } else {
                            string5 = this.mContext.getResources().getString(17042537);
                            string6 = this.mContext.getResources().getString(17042534);
                            i6 = R.drawable.cab_background_top_holo_dark;
                        }
                        activity2 = PendingIntent.getActivity(this.mContext, 0, new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"), 201326592);
                        this.mLastError = 4;
                    } else if (i == 7) {
                        String string7 = this.mContext.getResources().getString(17042588);
                        str2 = string7;
                        str3 = this.mContext.getResources().getString(17042587);
                        pendingIntent = PendingIntent.getActivity(this.mContext, 0, getSecurityIntent(), 201326592);
                        i4 = R.drawable.dropdown_ic_arrow_normal_holo_dark;
                    } else if (i == 10) {
                        String string8 = this.mContext.getResources().getString(17042549);
                        String string9 = this.mContext.getResources().getString(17042548);
                        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.samsung.android.security.SemSdCardEncryption.UNMOUNT_POLICY"), 201326592);
                        this.mLastError = 0;
                        str2 = string8;
                        str3 = string9;
                        pendingIntent = broadcast;
                        i4 = 17301627;
                        str4 = str2;
                    } else if (i == 11) {
                        if (i2 == 1) {
                            string5 = this.mContext.getResources().getString(17042541);
                            string6 = this.mContext.getResources().getString(17042539);
                        } else {
                            string5 = this.mContext.getResources().getString(17042537);
                            string6 = this.mContext.getResources().getString(17042534);
                            i6 = R.drawable.cab_background_top_holo_dark;
                        }
                        this.mLastError = 11;
                        activity2 = PendingIntent.getActivity(this.mContext, 0, new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"), 201326592);
                    } else {
                        if (this.mAnimator.isInProgress()) {
                            this.mAnimator.stop();
                            return;
                        }
                        return;
                    }
                }
                str4 = string5;
                str2 = str4;
                str3 = string6;
                pendingIntent = activity2;
                i4 = i6;
            } else if (i2 == 1) {
                string3 = this.mContext.getResources().getString(17042541);
                if ("success".equals(str)) {
                    string4 = this.mContext.getResources().getString(17042540);
                    this.mLastError = 0;
                    i5 = R.drawable.dropdown_ic_arrow_normal_holo_dark;
                } else if ("exception".equals(str)) {
                    String string10 = this.mContext.getResources().getString(17042535);
                    this.mLastError = 8;
                    string4 = string10;
                    i5 = 17302266;
                } else {
                    activity = PendingIntent.getActivity(this.mContext, 0, new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"), 201326592);
                    string4 = this.mContext.getResources().getString(17042539);
                    this.mLastError = 5;
                    i5 = 17302266;
                    str2 = string4;
                    str4 = string3;
                    i4 = i5;
                    pendingIntent = activity;
                    str3 = str2;
                }
                activity = null;
                str2 = string4;
                str4 = string3;
                i4 = i5;
                pendingIntent = activity;
                str3 = str2;
            } else {
                string3 = this.mContext.getResources().getString(17042537);
                if ("success".equals(str)) {
                    string4 = this.mContext.getResources().getString(17042536);
                    this.mLastError = 0;
                    i5 = R.drawable.cab_background_top_holo_light;
                } else if ("exception".equals(str)) {
                    String string11 = this.mContext.getResources().getString(17042535);
                    this.mLastError = 8;
                    string4 = string11;
                    i5 = R.drawable.cab_background_top_holo_dark;
                } else {
                    activity = PendingIntent.getActivity(this.mContext, 0, new Intent("com.sec.app.action.START_SDCARD_ENCRYPTION"), 201326592);
                    string4 = this.mContext.getResources().getString(17042534);
                    this.mLastError = 6;
                    i5 = R.drawable.cab_background_top_holo_dark;
                    str2 = string4;
                    str4 = string3;
                    i4 = i5;
                    pendingIntent = activity;
                    str3 = str2;
                }
                activity = null;
                str2 = string4;
                str4 = string3;
                i4 = i5;
                pendingIntent = activity;
                str3 = str2;
            }
        } else {
            if (i2 == 1) {
                string = this.mContext.getResources().getString(17042541);
                string2 = this.mContext.getResources().getString(17042542);
                i3 = R.drawable.dropdown_ic_arrow_normal_holo_dark;
            } else {
                string = this.mContext.getResources().getString(17042537);
                string2 = this.mContext.getResources().getString(17042538);
                i3 = R.drawable.cab_background_top_holo_light;
            }
            str4 = string;
            str2 = str4;
            i4 = i3;
            str3 = string2;
            pendingIntent = null;
        }
        if (this.mAnimator.isInProgress()) {
            this.mAnimator.stop();
        }
        Notification build = getNotification(pendingIntent, i4, str4, str3, str2).build();
        if (build != null) {
            build.flags = build.flags | 1 | 16;
            build.defaults |= 4;
            NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
            notificationManager.createNotificationChannel(new NotificationChannel(mNotificationChannelID, this.mContext.getResources().getString(17042541), 2));
            notificationManager.notify(SemSdCardEncryption.SECURITY_POLICY_NOTIFICATION_ID, build);
        }
    }

    public final String getCurrentTime() {
        try {
            return new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US).format(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            Log.e("DirEncryptServiceHelper", "Exception : " + e);
            return "Unknown";
        }
    }
}
