package com.samsung.android.server.audio;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import com.android.server.audio.AudioSystemAdapter;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.MultiSoundManager;
import com.samsung.android.server.audio.utils.AudioUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class MultiSoundManager {
    public static final Set MULTI_SOUND_SUPPORTED_DEVICE_SET;
    public static final Object sLastSetDeviceToNativeLock;
    public static long sLastSetDeviceToNativeTime;
    public ActivityManager mActivityManager;
    public final IActivityTaskManager mActivityTaskManager;
    public Handler mAudioHandler;
    public final AudioSystemAdapter mAudioSystem;
    public Context mContext;
    public boolean mEnabled;
    public MultiSoundInterface mInterface;
    public NotificationManager mNm;
    public PackageManager mPackageManager;
    public HashMap mPinAppInfoList;
    public PreventOverheatState mPreventOverheatState;
    public final Set mRemoteSubmixApps;
    public SetVolumeRunnable mSetVolumeRunnable;
    public final TaskStackListener mTaskStackListener;
    public UpdateFocusedAppRunnable mUpdateFocusedAppRunnable;
    public float[] mVolumeTable;
    public HashMap mTaskStack = new HashMap();
    public final Object mMultiSoundLock = new Object();

    public interface MultiSoundInterface {
        boolean checkAudioSettingsPermission(String str);

        void clearNotification();

        String getApplicationLabel(String str);

        int getConnectedDevice();

        int getCurrentMediaDevice();

        String getMultiSoundAppFromSetting();

        String[] getPackageName(int i);

        boolean isInstalledApp(String str);

        boolean isLeBroadcasting();

        void sendBecomingNoisyIntent(int i);

        void setAppCastingState(boolean z, int i);

        void showHeadUpNotification(int i);

        void showNotification();

        void updateFocusRequester(int i);

        void updateFocusRequester(int i, boolean z);

        void updateForegroundUid(int i);
    }

    public final boolean isFold() {
        return false;
    }

    static {
        HashSet hashSet = new HashSet();
        MULTI_SOUND_SUPPORTED_DEVICE_SET = hashSet;
        hashSet.add(2);
        hashSet.add(4);
        hashSet.add(8);
        hashSet.add(128);
        hashSet.add(256);
        hashSet.add(512);
        hashSet.add(16384);
        hashSet.add(67108864);
        hashSet.add(32768);
        sLastSetDeviceToNativeLock = new Object();
        sLastSetDeviceToNativeTime = 0L;
    }

    public MultiSoundManager(Context context, MultiSoundInterface multiSoundInterface, Handler handler, AudioSystemAdapter audioSystemAdapter) {
        TaskStackListener taskStackListener = new TaskStackListener() { // from class: com.samsung.android.server.audio.MultiSoundManager.1
            public void onTaskFocusChanged(int i, boolean z) {
                if (z) {
                    MultiSoundManager.this.updateForegroundAppUid(i, true);
                }
            }

            public void onTaskRemoved(int i) {
                MultiSoundManager.this.updateForegroundAppUid(i, false);
            }
        };
        this.mTaskStackListener = taskStackListener;
        this.mSetVolumeRunnable = new SetVolumeRunnable(-1);
        this.mUpdateFocusedAppRunnable = new UpdateFocusedAppRunnable(-1);
        this.mPreventOverheatState = new PreventOverheatState(-1, false);
        this.mRemoteSubmixApps = new HashSet();
        this.mPinAppInfoList = new HashMap();
        this.mEnabled = false;
        makeVolumeTable();
        this.mInterface = multiSoundInterface;
        this.mAudioHandler = handler;
        this.mContext = context;
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mNm = (NotificationManager) this.mContext.getSystemService("notification");
        this.mPackageManager = this.mContext.getPackageManager();
        this.mAudioSystem = audioSystemAdapter;
        IActivityTaskManager service = ActivityTaskManager.getService();
        this.mActivityTaskManager = service;
        try {
            service.registerTaskStackListener(taskStackListener);
        } catch (Exception e) {
            Log.e("AS.MultiSoundManager", "Exception - registerTaskStackListener");
            e.printStackTrace();
        }
    }

    public final void makeVolumeTable() {
        float[] fArr = new float[101];
        this.mVolumeTable = fArr;
        fArr[0] = 0.0f;
        fArr[100] = 1.0f;
        for (int i = 1; i < 100; i++) {
            this.mVolumeTable[i] = (float) (Math.exp(((i / 100.0d) * 5.0d) - 5.0d) - Math.exp(-5.0d));
        }
    }

    public void enable(boolean z) {
        if (this.mEnabled) {
            return;
        }
        Log.d("AS.MultiSoundManager", "enable");
        this.mEnabled = true;
        resetByEnableDisable();
        if (z) {
            this.mInterface.showNotification();
        }
    }

    public void shouldEnable() {
        Log.d("AS.MultiSoundManager", "shouldEnable");
        this.mEnabled = true;
        resetByEnableDisable();
    }

    public void disable() {
        if (this.mEnabled) {
            Log.d("AS.MultiSoundManager", "disable");
            this.mEnabled = false;
            resetByEnableDisable();
            this.mInterface.clearNotification();
        }
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public final void resetByEnableDisable() {
        Log.d("AS.MultiSoundManager", "resetByEnableDisable");
        int devicesForStream = AudioSystem.getDevicesForStream(3);
        for (MultiSoundItem multiSoundItem : new ArrayList(this.mPinAppInfoList.values())) {
            if (multiSoundItem.getAppDevice() != 0) {
                if (!this.mEnabled && devicesForStream != 32768 && devicesForStream != 536870914 && devicesForStream != getAppDevice(multiSoundItem.mUid)) {
                    this.mInterface.sendBecomingNoisyIntent(multiSoundItem.mUid);
                }
                this.mInterface.updateFocusRequester(multiSoundItem.mUid);
            }
        }
        setStateToNative();
    }

    public void setAppDevice(int i, int i2, boolean z) {
        int devicesForStream = AudioSystem.getDevicesForStream(3);
        synchronized (this.mMultiSoundLock) {
            if (!this.mInterface.checkAudioSettingsPermission("setAppDevice")) {
                Log.e("AS.MultiSoundManager", "setAppDevice: permission error");
                return;
            }
            if (!MULTI_SOUND_SUPPORTED_DEVICE_SET.contains(Integer.valueOf(i2)) && i2 != 0) {
                Log.e("AS.MultiSoundManager", "setAppDevice: unsupported device");
                return;
            }
            MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
            if (multiSoundItem != null && multiSoundItem.getAppDevice() == i2) {
                Log.d("AS.MultiSoundManager", "same device pin.");
                return;
            }
            if (SemAudioSystem.MULTI_SOUND_PRIMARY_DEVICE_SET.contains(Integer.valueOf(devicesForStream))) {
                devicesForStream = 2;
            }
            MultiSoundItem multiSoundItem2 = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
            if (multiSoundItem2 == null) {
                multiSoundItem2 = new MultiSoundItem(i, i2, 100);
            } else {
                multiSoundItem2.setAppDevice(i2);
            }
            if (multiSoundItem2.removable()) {
                this.mPinAppInfoList.remove(Integer.valueOf(i));
            } else {
                this.mPinAppInfoList.put(Integer.valueOf(i), multiSoundItem2);
            }
            setDeviceToNative(i, i2, 0);
            if (this.mEnabled && i2 == devicesForStream && z) {
                this.mInterface.showHeadUpNotification(devicesForStream);
            }
        }
    }

    public int getAppDevice(int i, boolean z) {
        if (z) {
            MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
            if (multiSoundItem != null) {
                return multiSoundItem.getAppDevice();
            }
            return 0;
        }
        return getAppDevice(i);
    }

    public final int getCurrentMediaDevice() {
        int currentMediaDevice = this.mInterface.getCurrentMediaDevice();
        if (((currentMediaDevice - 1) & currentMediaDevice) == 0) {
            return currentMediaDevice;
        }
        if ((currentMediaDevice & 2) != 0) {
            return 2;
        }
        int i = 262144;
        if ((currentMediaDevice & 262144) == 0) {
            i = 524288;
            if ((currentMediaDevice & 524288) == 0) {
                i = 2097152;
                if ((currentMediaDevice & 2097152) == 0) {
                    Iterator it = AudioSystem.DEVICE_OUT_ALL_A2DP_SET.iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        if ((intValue & currentMediaDevice) == intValue) {
                            return intValue;
                        }
                    }
                    return currentMediaDevice;
                }
            }
        }
        return i;
    }

    public int getAppDevice(int i) {
        if (this.mInterface.isLeBroadcasting()) {
            return 0;
        }
        synchronized (this.mRemoteSubmixApps) {
            if (this.mRemoteSubmixApps.contains(Integer.valueOf(i))) {
                return 32768;
            }
            MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
            if (multiSoundItem == null) {
                return 0;
            }
            int appDevice = multiSoundItem.getAppDevice();
            if (!isDeviceConnected(appDevice)) {
                appDevice = 0;
            }
            if (appDevice == 32768) {
                return appDevice;
            }
            if (!this.mEnabled) {
                return 0;
            }
            if (appDevice != 2) {
                return appDevice;
            }
            int currentMediaDevice = getCurrentMediaDevice();
            if (SemAudioSystem.MULTI_SOUND_PRIMARY_DEVICE_SET.contains(Integer.valueOf(currentMediaDevice))) {
                return currentMediaDevice;
            }
            int connectedDevice = this.mInterface.getConnectedDevice();
            if ((connectedDevice & 16384) != 0) {
                return 16384;
            }
            int i2 = 67108864;
            if ((connectedDevice & 67108864) == 0) {
                if ((connectedDevice & 4) != 0) {
                    return 4;
                }
                if ((connectedDevice & 8) != 0) {
                    return 8;
                }
                i2 = 1024;
                if ((connectedDevice & 1024) == 0) {
                    return 2;
                }
            }
            return i2;
        }
    }

    public void setAppVolume(int i, int i2) {
        boolean removable;
        if (i2 > 100 || i2 < 0) {
            Log.e("AS.MultiSoundManager", "setAppVolume: Invalid volume");
            return;
        }
        synchronized (this.mMultiSoundLock) {
            MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
            if (multiSoundItem != null) {
                multiSoundItem.setAppVolume(i2);
                removable = multiSoundItem.removable();
            } else {
                if (i2 == 100) {
                    return;
                }
                removable = false;
                multiSoundItem = new MultiSoundItem(i, 0, i2);
            }
            if (removable) {
                this.mPinAppInfoList.remove(Integer.valueOf(i));
            } else {
                this.mPinAppInfoList.put(Integer.valueOf(i), multiSoundItem);
            }
            setAppVolumeToNative(i);
        }
    }

    public void setAppVolumeToNative(int i) {
        if (this.mSetVolumeRunnable.mUid == i) {
            this.mAudioHandler.removeCallbacks(this.mSetVolumeRunnable);
        } else {
            this.mSetVolumeRunnable = new SetVolumeRunnable(i);
        }
        this.mAudioHandler.post(this.mSetVolumeRunnable);
    }

    public int getAppVolume(int i) {
        synchronized (this.mMultiSoundLock) {
            MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
            if (multiSoundItem == null) {
                return 100;
            }
            return multiSoundItem.getAppVolume();
        }
    }

    public float getAppVolumeFloat(int i) {
        float f;
        synchronized (this.mMultiSoundLock) {
            MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
            f = multiSoundItem != null ? this.mVolumeTable[multiSoundItem.getAppVolume()] : 1.0f;
            if (this.mPreventOverheatState.needLimitVolumeForApp(i) && isSpeakerOut(i)) {
                f *= this.mPreventOverheatState.mLimitedVolumeForOverheat;
            }
            if (multiSoundItem != null && multiSoundItem.isShouldMute()) {
                f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
            }
        }
        return f;
    }

    public void setAppMute(int i, boolean z) {
        synchronized (this.mMultiSoundLock) {
            MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
            if (multiSoundItem == null) {
                multiSoundItem = new MultiSoundItem(i, 0, 100);
            }
            multiSoundItem.setShouldMute(z);
            if (multiSoundItem.removable()) {
                this.mPinAppInfoList.remove(Integer.valueOf(i));
            } else {
                this.mPinAppInfoList.put(Integer.valueOf(i), multiSoundItem);
            }
        }
        setAppVolumeToNative(i);
    }

    public boolean isAppMute(int i) {
        boolean booleanValue;
        synchronized (this.mMultiSoundLock) {
            booleanValue = ((Boolean) Optional.ofNullable((MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i))).map(new Function() { // from class: com.samsung.android.server.audio.MultiSoundManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(((MultiSoundManager.MultiSoundItem) obj).isShouldMute());
                }
            }).orElse(Boolean.FALSE)).booleanValue();
        }
        return booleanValue;
    }

    public final boolean isSpeakerOut(int i) {
        int appDevice;
        boolean z = (getCurrentMediaDevice() & 2) != 0;
        return (this.mEnabled && (appDevice = getAppDevice(i)) != 0) ? appDevice == 2 : z;
    }

    public void sendBecomingNoisyToPinnedApp(int i) {
        if (this.mEnabled) {
            for (MultiSoundItem multiSoundItem : new ArrayList(this.mPinAppInfoList.values())) {
                if (getAppDevice(multiSoundItem.mUid) == i) {
                    this.mInterface.sendBecomingNoisyIntent(multiSoundItem.mUid);
                }
            }
        }
    }

    public int getPinDevice() {
        return getPinDevice(false);
    }

    public int getPinDevice(boolean z) {
        int appDevice;
        if (!z && !this.mEnabled) {
            return 0;
        }
        for (MultiSoundItem multiSoundItem : new ArrayList(this.mPinAppInfoList.values())) {
            if (multiSoundItem.getAppDevice() != 0 && (appDevice = getAppDevice(multiSoundItem.mUid, z)) != 0 && appDevice != 32768) {
                return appDevice;
            }
        }
        return 0;
    }

    public String getPinAppInfo(int i) {
        String join;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mRemoteSubmixApps) {
            if (isRemoteSubmixAppExist() && i == 32768) {
                Iterator it = this.mRemoteSubmixApps.iterator();
                while (it.hasNext()) {
                    try {
                        arrayList.add(this.mInterface.getApplicationLabel(this.mInterface.getPackageName(((Integer) it.next()).intValue())[0]));
                    } catch (Exception unused) {
                    }
                }
                return String.join(", ", arrayList);
            }
            synchronized (this.mMultiSoundLock) {
                Iterator it2 = this.mPinAppInfoList.entrySet().iterator();
                while (it2.hasNext()) {
                    int intValue = ((Integer) ((Map.Entry) it2.next()).getKey()).intValue();
                    if (getAppDevice(intValue, true) == i) {
                        String[] packageName = this.mInterface.getPackageName(intValue);
                        String str = packageName[0];
                        String multiSoundAppFromSetting = this.mInterface.getMultiSoundAppFromSetting();
                        for (String str2 : packageName) {
                            if (str2.equals(multiSoundAppFromSetting)) {
                                str = str2;
                                break;
                            }
                        }
                        try {
                            arrayList.add(this.mInterface.getApplicationLabel(str));
                        } catch (Exception unused2) {
                            Log.e("AS.MultiSoundManager", "not found");
                        }
                    }
                }
                join = String.join(", ", arrayList);
            }
            return join;
        }
    }

    public void resetPinDevice() {
        for (MultiSoundItem multiSoundItem : new ArrayList(this.mPinAppInfoList.values())) {
            if (multiSoundItem.getAppDevice() != 0) {
                setAppDevice(multiSoundItem.mUid, 0, true);
            }
        }
        disable();
    }

    public int getDeviceMultiSoundUsingActually() {
        synchronized (this.mRemoteSubmixApps) {
            Iterator it = this.mRemoteSubmixApps.iterator();
            while (it.hasNext()) {
                if (AudioUtils.isUsingAudioForUid(((Integer) it.next()).intValue())) {
                    return 32768;
                }
            }
            if (!this.mEnabled) {
                return 0;
            }
            for (MultiSoundItem multiSoundItem : new ArrayList(this.mPinAppInfoList.values())) {
                if (multiSoundItem.getAppDevice() != 0 && AudioUtils.isUsingAudioForUid(multiSoundItem.mUid)) {
                    return getAppDevice(multiSoundItem.mUid);
                }
            }
            return 0;
        }
    }

    public String[] getPinPackageName(int i) {
        synchronized (this.mMultiSoundLock) {
            for (MultiSoundItem multiSoundItem : new ArrayList(this.mPinAppInfoList.values())) {
                if (multiSoundItem.getAppDevice() == i) {
                    return this.mInterface.getPackageName(multiSoundItem.getUid());
                }
            }
            return new String[]{""};
        }
    }

    public void resetByAudioServerDied() {
        synchronized (this.mMultiSoundLock) {
            Log.d("AS.MultiSoundManager", "resetByAudioServerDied");
            Iterator it = this.mPinAppInfoList.entrySet().iterator();
            while (it.hasNext()) {
                MultiSoundItem multiSoundItem = (MultiSoundItem) ((Map.Entry) it.next()).getValue();
                setDeviceToNative(multiSoundItem.getUid(), multiSoundItem.getAppDevice(), 0);
                setAppVolumeToNative(multiSoundItem.getUid());
            }
            setStateToNative();
        }
        synchronized (this.mRemoteSubmixApps) {
            Iterator it2 = this.mRemoteSubmixApps.iterator();
            int i = -1;
            while (it2.hasNext()) {
                i = ((Integer) it2.next()).intValue();
                setRemoteSubmixAppToNative(i, 32768);
            }
            if (this.mRemoteSubmixApps.size() > 0) {
                enableSeparateRemoteSubmix(true, i);
            }
        }
    }

    public boolean removeItem(int i) {
        synchronized (this.mMultiSoundLock) {
            if (this.mPinAppInfoList.get(Integer.valueOf(i)) == null) {
                return false;
            }
            Log.d("AS.MultiSoundManager", "removeItem, " + i);
            this.mPinAppInfoList.remove(Integer.valueOf(i));
            setDeviceToNative(i, 0, 0);
            return true;
        }
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this.mMultiSoundLock) {
            printWriter.println("\nMultiSound, size:" + this.mPinAppInfoList.size() + ", isEnabled:" + this.mEnabled);
        }
        Iterator it = this.mPinAppInfoList.entrySet().iterator();
        while (it.hasNext()) {
            MultiSoundItem multiSoundItem = (MultiSoundItem) ((Map.Entry) it.next()).getValue();
            printWriter.print("  uid = " + multiSoundItem.getUid() + "(" + this.mInterface.getPackageName(multiSoundItem.getUid())[0] + "), device=0x" + Integer.toHexString(multiSoundItem.getAppDevice()) + ", ");
            StringBuilder sb = new StringBuilder();
            sb.append("volume=");
            sb.append(multiSoundItem.getAppVolume());
            printWriter.println(sb.toString());
        }
        printWriter.println("  Current Default Device: 0x" + Integer.toHexString(this.mInterface.getCurrentMediaDevice()));
        printWriter.println("  Pin Device (IgnoreConnection): 0x" + Integer.toHexString(getPinDevice(true)));
        printWriter.println("  Pin Apps: " + getPinAppInfo(getPinDevice(true)));
        printWriter.println("  SEC_AUDIO_MULTI_SOUND=true");
        if (this.mInterface.isInstalledApp("com.samsung.android.oneconnect")) {
            printWriter.println("  Smart Things=install");
        }
        printWriter.print("  mPreventOverheatState=");
        printWriter.println(this.mPreventOverheatState);
        printWriter.print("  Dual App : ");
        synchronized (this.mRemoteSubmixApps) {
            Iterator it2 = this.mRemoteSubmixApps.iterator();
            while (it2.hasNext()) {
                printWriter.print(((Integer) it2.next()) + " ");
            }
        }
        printWriter.println("");
    }

    public final void setStateToNative() {
        boolean z = this.mEnabled;
        if (this.mInterface.isLeBroadcasting()) {
            Log.d("AS.MultiSoundManager", "send multisound off during le broadcasting");
            z = false;
        }
        final AudioParameter build = new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("enable", z).setParam("type", 1).build();
        this.mAudioHandler.postDelayed(new Runnable() { // from class: com.samsung.android.server.audio.MultiSoundManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MultiSoundManager.this.lambda$setStateToNative$0(build);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStateToNative$0(AudioParameter audioParameter) {
        this.mAudioSystem.setParameters(audioParameter.toString());
    }

    public final void setDeviceToNative(int i, int i2, int i3) {
        final AudioParameter build = new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("uid", i).setParam("device", i2).setParam("type", 1).build();
        synchronized (sLastSetDeviceToNativeLock) {
            long uptimeMillis = SystemClock.uptimeMillis() + i3;
            long j = sLastSetDeviceToNativeTime;
            if (j >= uptimeMillis) {
                uptimeMillis = 30 + j;
            }
            sLastSetDeviceToNativeTime = uptimeMillis;
            this.mAudioHandler.postAtTime(new Runnable() { // from class: com.samsung.android.server.audio.MultiSoundManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MultiSoundManager.this.lambda$setDeviceToNative$1(build);
                }
            }, uptimeMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceToNative$1(AudioParameter audioParameter) {
        this.mAudioSystem.setParameters(audioParameter.toString());
    }

    public final void updateForegroundAppUid(int i, boolean z) {
        if (z) {
            if (this.mAudioHandler.hasCallbacks(this.mUpdateFocusedAppRunnable)) {
                this.mAudioHandler.removeCallbacks(this.mUpdateFocusedAppRunnable);
            }
            UpdateFocusedAppRunnable updateFocusedAppRunnable = new UpdateFocusedAppRunnable(i);
            this.mUpdateFocusedAppRunnable = updateFocusedAppRunnable;
            this.mAudioHandler.post(updateFocusedAppRunnable);
            return;
        }
        this.mAudioHandler.post(new RemoveStackAppRunnable(i));
    }

    public final int getUidByTaskId(int i) {
        ComponentName componentName;
        ActivityManager activityManager = this.mActivityManager;
        if (activityManager == null) {
            Log.e("AS.MultiSoundManager", "ActivityManager is null.");
            return -1;
        }
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
        if (runningTasks != null) {
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                if (runningTaskInfo.taskId == i && (componentName = runningTaskInfo.baseActivity) != null) {
                    String packageName = componentName.getPackageName();
                    try {
                        return this.mPackageManager.getApplicationInfo(packageName, 0).uid;
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.w("AS.MultiSoundManager", "not installed " + packageName);
                    }
                }
            }
        }
        return -1;
    }

    public void setPreventOverheatState(int i, boolean z) {
        this.mPreventOverheatState.setState(i, z);
    }

    public void setLimitedVolumeForOverheat() {
        this.mPreventOverheatState.setLimitedVolumeForOverheat();
    }

    public boolean getPreventOverheatState() {
        return this.mPreventOverheatState.mState;
    }

    public void setPreventOverheatState(int i) {
        this.mPreventOverheatState.setDevice(i);
    }

    public void updateAudioServiceNotificationChannel() {
        NotificationManager notificationManager = this.mNm;
        if (notificationManager != null) {
            notificationManager.deleteNotificationChannel("AudioCore_Notification");
        }
        this.mNm = (NotificationManager) this.mContext.getSystemService("notification");
        this.mNm.createNotificationChannel(new NotificationChannel("AudioCore_Notification", this.mContext.getResources().getString(R.string.policydesc_resetPassword), 4));
    }

    public void showNotification() {
        String string;
        if (this.mNm == null) {
            return;
        }
        int devicesForStream = AudioSystem.getDevicesForStream(3);
        int pinDevice = getPinDevice();
        if (pinDevice == 0 || devicesForStream == pinDevice) {
            return;
        }
        Intent intent = new Intent();
        if (isSupportMultiPane()) {
            intent.setAction("android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY");
            Intent intent2 = new Intent();
            intent2.setClassName("com.samsung.android.setting.multisound", "com.samsung.android.setting.multisound.MultiSoundSettingsActivity");
            intent.putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI", intent2.toUri(1));
            intent.putExtra("android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY", "top_level_sounds");
        } else {
            intent.setAction("android.intent.action.Launch_Setting");
            intent.setClassName("com.samsung.android.setting.multisound", "com.samsung.android.setting.multisound.MultiSoundSettingsActivity");
        }
        PendingIntent activity = PendingIntent.getActivity(this.mContext, 0, intent, 335544320);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, new Intent("android.intent.action.TurnOff_MultiSound"), 67108864);
        Notification.Builder builder = new Notification.Builder(this.mContext, "AudioCore_Notification");
        if (pinDevice == 4 || pinDevice == 8) {
            string = this.mContext.getString(R.string.phoneTypeTtyTdd);
        } else if (pinDevice == 128) {
            string = this.mContext.getString(R.string.phoneTypeRadio);
        } else if (pinDevice == 16384 || pinDevice == 67108864) {
            string = this.mContext.getString(R.string.phoneTypeTtyTdd);
        } else {
            string = this.mContext.getString(R.string.phoneTypeWorkMobile);
        }
        builder.setStyle(new Notification.BigTextStyle().bigText(string)).setContentTitle(this.mContext.getString(R.string.pin_target)).setSmallIcon(17304203).setContentText(string).setAutoCancel(false).setVibrate(null).setContentIntent(activity).addAction(R.drawable.stat_notify_error, this.mContext.getResources().getText(R.string.phoneTypeOtherFax), activity).addAction(R.drawable.stat_notify_error, this.mContext.getResources().getText(R.string.policydesc_disableCamera), broadcast);
        this.mNm.notify(1004, builder.build());
    }

    public void clearNotification() {
        this.mNm.cancel(1004);
    }

    public void showHeadUpNotification(int i, int i2) {
        AudioDeviceInfo[] devicesStatic = AudioManager.getDevicesStatic(2);
        String[] strArr = {"", ""};
        int[] iArr = {i, i2};
        if (SemAudioSystem.MULTI_SOUND_PRIMARY_DEVICE_SET.contains(Integer.valueOf(iArr[0])) && SemAudioSystem.MULTI_SOUND_PRIMARY_DEVICE_SET.contains(Integer.valueOf(iArr[1]))) {
            return;
        }
        for (int i3 = 0; i3 < 2; i3++) {
            int i4 = iArr[i3];
            if (i4 == 2) {
                strArr[i3] = this.mContext.getResources().getString(R.string.policydesc_resetPassword);
            } else if (i4 == 4 || i4 == 8) {
                strArr[i3] = this.mContext.getResources().getString(R.string.mediasize_na_arch_d);
            } else {
                int length = devicesStatic.length;
                int i5 = 0;
                while (true) {
                    if (i5 < length) {
                        AudioDeviceInfo audioDeviceInfo = devicesStatic[i5];
                        if (audioDeviceInfo.semGetInternalType() == iArr[i3]) {
                            strArr[i3] = audioDeviceInfo.getProductName().toString();
                            break;
                        }
                        i5++;
                    }
                }
            }
        }
        String format = String.format(this.mContext.getString(R.string.phoneTypeMobile), strArr[0], strArr[1]);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 1, new Intent("com.samsung.android.audio.headup.changedevice"), 201326592);
        this.mNm.notify(1005, new Notification.Builder(this.mContext, "AudioCore_Notification").setAutoCancel(true).setShowWhen(true).setSmallIcon(17304203).setContentTitle(this.mContext.getString(R.string.phoneTypeOther)).setContentText(format).setStyle(new Notification.BigTextStyle().bigText(format)).setPriority(2).setDefaults(1).addAction(0, this.mContext.getString(R.string.phoneTypeIsdn), broadcast).setContentIntent(broadcast).build());
    }

    public void clearHeadUpNotification() {
        this.mNm.cancel(1005);
    }

    public void showToast() {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light);
        Context context = this.mContext;
        Toast.makeText(contextThemeWrapper, context.getString(R.string.phoneTypeCustom, context.getResources().getText(R.string.pin_target)), 0).show();
    }

    public class MultiSoundItem {
        public int mDevice;
        public int mRatio;
        public boolean mShouldMute;
        public int mUid;

        public MultiSoundItem(int i, int i2, int i3) {
            this.mUid = i;
            this.mDevice = i2;
            setAppVolume(i3);
            this.mRatio = i3;
            this.mShouldMute = false;
        }

        public int getUid() {
            return this.mUid;
        }

        public void setAppDevice(int i) {
            this.mDevice = i;
        }

        public int getAppDevice() {
            return this.mDevice;
        }

        public int getAppVolume() {
            return this.mRatio;
        }

        public void setAppVolume(int i) {
            if (i < 0 || i > 100) {
                Log.e("AS.MultiSoundManager", "Invalid app volume");
            } else {
                this.mRatio = i;
            }
        }

        public boolean isShouldMute() {
            return this.mShouldMute;
        }

        public void setShouldMute(boolean z) {
            this.mShouldMute = z;
        }

        public boolean removable() {
            return this.mDevice == 0 && this.mRatio == 100 && !this.mShouldMute;
        }
    }

    public void sendBecomingNoisyIntentToUnpinApps(int i) {
        String[] pinPackageName;
        Log.d("AS.MultiSoundManager", "sendBecomingNoisyIntentToUnpinApps, " + i);
        if (isDeviceConnected(32768) && i != 32768 && isRemoteSubmixAppExist()) {
            pinPackageName = getRemoteSubmixPacakageNames();
        } else {
            pinPackageName = getPinPackageName(getPinDevice());
        }
        Intent intent = new Intent();
        intent.setAction("android.media.AUDIO_BECOMING_NOISY_SEC");
        if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
            intent.putExtra("android.bluetooth.a2dp.extra.DISCONNECT_A2DP", true);
        }
        sendIntentToSpecificPackage(intent, pinPackageName);
        intent.setAction("android.media.AUDIO_BECOMING_NOISY");
        if (intent.getPackage() != null) {
            intent.setPackage(null);
        }
        sendIntentToSpecificPackage(intent, pinPackageName);
    }

    public final void sendIntentToSpecificPackage(Intent intent, String[] strArr) {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List queryBroadcastReceiversAsUser = this.mPackageManager.queryBroadcastReceiversAsUser(intent, PackageManager.ResolveInfoFlags.of(0L), getCurrentUserId());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            String[] queryRegisteredReceiverPackages = this.mActivityManager.queryRegisteredReceiverPackages(intent);
            Iterator it = queryBroadcastReceiversAsUser.iterator();
            while (true) {
                boolean z2 = true;
                if (!it.hasNext()) {
                    break;
                }
                ResolveInfo resolveInfo = (ResolveInfo) it.next();
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo != null) {
                    String str = activityInfo.packageName;
                    if (!str.isEmpty()) {
                        int length = strArr.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                z2 = false;
                                break;
                            }
                            String str2 = strArr[i];
                            if (str.equals(str2)) {
                                Log.d("AS.MultiSoundManager", "send intent except " + str2);
                                break;
                            }
                            i++;
                        }
                        if (!z2) {
                            intent.setPackage(resolveInfo.activityInfo.packageName);
                            sendBroadcastToAll(intent);
                            Log.d("AS.MultiSoundManager", "sendBecomingNoisyIntentToUnpinApps to " + resolveInfo.activityInfo.packageName);
                        }
                    }
                }
            }
            for (String str3 : queryRegisteredReceiverPackages) {
                if (str3 != null && !str3.isEmpty()) {
                    int length2 = strArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length2) {
                            z = false;
                            break;
                        }
                        String str4 = strArr[i2];
                        if (str3.equals(str4)) {
                            Log.d("AS.MultiSoundManager", "send intent except " + str4);
                            z = true;
                            break;
                        }
                        i2++;
                    }
                    if (!z) {
                        intent.setPackage(str3);
                        sendBroadcastToAll(intent);
                        Log.d("AS.MultiSoundManager", "sendBecomingNoisyIntentToUnpinApps to " + str3);
                    }
                }
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void sendBroadcastToAll(Intent intent) {
        intent.addFlags(67108864);
        intent.addFlags(268435456);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getCurrentUserId() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i = ActivityManager.getService().getCurrentUser().id;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean isDeviceConnected(int i) {
        return this.mAudioSystem.getDeviceConnectionState(i, i == 32768 ? "0" : "") > 0;
    }

    public class SetVolumeRunnable implements Runnable {
        public int mUid;

        public SetVolumeRunnable(int i) {
            this.mUid = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            MultiSoundManager.this.mAudioSystem.setParameters(new AudioParameter.Builder().setParam("l_multi_sound_key").setParam("uid", this.mUid).setParam("volume", MultiSoundManager.this.getAppVolumeFloat(this.mUid)).build().toString());
        }
    }

    public class RemoveStackAppRunnable implements Runnable {
        public final int mRemoveTaskId;

        public RemoveStackAppRunnable(int i) {
            this.mRemoveTaskId = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            MultiSoundManager.this.mTaskStack.remove(Integer.valueOf(this.mRemoveTaskId));
        }
    }

    public class UpdateFocusedAppRunnable implements Runnable {
        public final int mTaskId;

        public UpdateFocusedAppRunnable(int i) {
            this.mTaskId = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            int uidByTaskId;
            if (MultiSoundManager.this.mTaskStack.containsKey(Integer.valueOf(this.mTaskId))) {
                uidByTaskId = ((Integer) MultiSoundManager.this.mTaskStack.get(Integer.valueOf(this.mTaskId))).intValue();
            } else {
                uidByTaskId = MultiSoundManager.this.getUidByTaskId(this.mTaskId);
                if (uidByTaskId == -1) {
                    Log.d("AS.MultiSoundManager", "No app found taskId:" + this.mTaskId);
                    return;
                }
                MultiSoundManager.this.mTaskStack.put(Integer.valueOf(this.mTaskId), Integer.valueOf(uidByTaskId));
            }
            MultiSoundManager.this.mInterface.updateForegroundUid(uidByTaskId);
            Log.d("AS.MultiSoundManager", "mForegroundUid = " + uidByTaskId);
        }
    }

    public class PreventOverheatState {
        public boolean mState;
        public int mUid;
        public float mLimitedVolumeForOverheat = 1.0f;
        public int mCurDevice = 0;

        public PreventOverheatState(int i, boolean z) {
            this.mUid = i;
            this.mState = z;
        }

        public void setState(int i, boolean z) {
            int i2 = this.mUid;
            if (i2 == -1 || i2 == i) {
                i2 = -1;
            }
            this.mUid = i;
            this.mState = z;
            if (i2 != -1) {
                MultiSoundManager.this.setAppVolumeToNative(i2);
            }
        }

        public final boolean isSameUid(int i) {
            return i % 100000 == this.mUid % 100000;
        }

        public void setDevice(int i) {
            if (this.mCurDevice != i) {
                this.mCurDevice = i;
                setLimitedVolumeForOverheat();
            }
        }

        public String toString() {
            return "mUid: " + this.mUid + ", state: " + this.mState + ", volume: " + this.mLimitedVolumeForOverheat + ", mCureDevice: " + this.mCurDevice;
        }

        public void setLimitedVolumeForOverheat() {
            float f;
            try {
                f = Float.parseFloat(SemAudioSystem.getPolicyParameters("l_volume_prevent_overheat_key;gain"));
            } catch (NullPointerException | NumberFormatException unused) {
                f = 1.0f;
            }
            this.mLimitedVolumeForOverheat = f;
            MultiSoundManager.this.setAppVolumeToNative(this.mUid);
        }

        public boolean needLimitVolumeForApp(int i) {
            return this.mState && isSameUid(i);
        }
    }

    public final void enableSeparateRemoteSubmix(boolean z, int i) {
        this.mAudioSystem.setParameters(new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("enable", z).setParam("exclusive", z).setParam("device", 32768).setParam("type", 2).build().toString());
        this.mInterface.setAppCastingState(z, i);
    }

    public final void enableSeparateRemoteSubmix(boolean z) {
        enableSeparateRemoteSubmix(z, -1);
    }

    public final void setRemoteSubmixAppToNative(int i, int i2) {
        this.mAudioSystem.setParameters(new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("uid", i).setParam("device", i2).setParam("type", 2).build().toString());
    }

    public boolean isRemoteSubmixAppExist() {
        boolean z;
        synchronized (this.mRemoteSubmixApps) {
            z = this.mRemoteSubmixApps.size() > 0;
        }
        return z;
    }

    public final String[] getRemoteSubmixPacakageNames() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mRemoteSubmixApps) {
            Iterator it = this.mRemoteSubmixApps.iterator();
            while (it.hasNext()) {
                arrayList.addAll(Arrays.asList(this.mInterface.getPackageName(((Integer) it.next()).intValue())));
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public void setAppToRemoteSubmix(int i, int i2) {
        synchronized (this.mRemoteSubmixApps) {
            boolean z = true;
            boolean z2 = false;
            try {
                if (i2 == -1000) {
                    if (this.mRemoteSubmixApps.contains(Integer.valueOf(i))) {
                        Log.w("AS.MultiSoundManager", "Already set uid " + i);
                        return;
                    }
                    setRemoteSubmixAppToNative(i, 32768);
                    if (this.mRemoteSubmixApps.size() == 0) {
                        enableSeparateRemoteSubmix(true, i);
                    }
                    this.mRemoteSubmixApps.add(Integer.valueOf(i));
                } else {
                    if (i2 != -1002) {
                        z = false;
                    }
                    if (!this.mRemoteSubmixApps.contains(Integer.valueOf(i))) {
                        Log.w("AS.MultiSoundManager", "Invalid uid " + i);
                        return;
                    }
                    this.mRemoteSubmixApps.remove(Integer.valueOf(i));
                    if (this.mRemoteSubmixApps.size() == 0) {
                        enableSeparateRemoteSubmix(false);
                    }
                    setRemoteSubmixAppToNative(i, 0);
                    z2 = z;
                }
                this.mInterface.updateFocusRequester(i, z2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isSupportMultiPane() {
        return isTablet() || isFold();
    }

    public final boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }

    public Set getRemoteSubmixApps() {
        return this.mRemoteSubmixApps;
    }
}
