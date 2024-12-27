package com.samsung.android.server.audio;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackListener;
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
import android.provider.Settings;
import android.util.Log;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory;
import com.android.server.audio.AudioService;
import com.android.server.audio.AudioSystemAdapter;
import com.android.server.media.MediaSessionService;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.media.SemAudioSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MultiSoundManager {
    public static final Set MULTI_SOUND_SUPPORTED_DEVICE_SET;
    public static final Object sLastSetDeviceToNativeLock = new Object();
    public static long sLastSetDeviceToNativeTime;
    public final ActivityManager mActivityManager;
    public final Handler mAudioHandler;
    public final AudioSystemAdapter mAudioSystem;
    public final Context mContext;
    public final AudioService.AnonymousClass11 mInterface;
    public NotificationManager mNm;
    public final PackageManager mPackageManager;
    public SetVolumeRunnable mSetVolumeRunnable;
    public SetVolumeRunnable mUpdateFocusedAppRunnable;
    public final float[] mVolumeTable;
    public final HashMap mTaskStack = new HashMap();
    public final Object mMultiSoundLock = new Object();
    public final PreventOverheatState mPreventOverheatState = new PreventOverheatState();
    public final Set mRemoteSubmixApps = new HashSet();
    public final HashMap mPinAppInfoList = new HashMap();
    public boolean mEnabled = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MultiSoundItem {
        public int mDevice;
        public int mRatio;
        public boolean mShouldMute;
        public final int mUid;

        public MultiSoundItem(int i, int i2, int i3) {
            this.mUid = i;
            this.mDevice = i2;
            if (i3 < 0 || i3 > 100) {
                Log.e("AS.MultiSoundManager", "Invalid app volume");
            } else {
                this.mRatio = i3;
            }
            this.mRatio = i3;
            this.mShouldMute = false;
        }

        public final boolean removable() {
            return this.mDevice == 0 && this.mRatio == 100 && !this.mShouldMute;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PreventOverheatState {
        public float mLimitedVolumeForOverheat = 1.0f;
        public int mCurDevice = 0;
        public int mUid = -1;
        public boolean mState = false;

        public PreventOverheatState() {
        }

        public final String toString() {
            return "mUid: " + this.mUid + ", state: " + this.mState + ", volume: " + this.mLimitedVolumeForOverheat + ", mCureDevice: " + this.mCurDevice;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SetVolumeRunnable implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final int mUid;
        public final /* synthetic */ MultiSoundManager this$0;

        public /* synthetic */ SetVolumeRunnable(MultiSoundManager multiSoundManager, int i, int i2) {
            this.$r8$classId = i2;
            this.this$0 = multiSoundManager;
            this.mUid = i;
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0074  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0081  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 320
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.server.audio.MultiSoundManager.SetVolumeRunnable.run():void");
        }
    }

    /* renamed from: -$$Nest$mupdateForegroundAppUid, reason: not valid java name */
    public static void m1221$$Nest$mupdateForegroundAppUid(MultiSoundManager multiSoundManager, int i, boolean z) {
        Handler handler = multiSoundManager.mAudioHandler;
        if (!z) {
            handler.post(new SetVolumeRunnable(multiSoundManager, i, 1));
            return;
        }
        if (handler.hasCallbacks(multiSoundManager.mUpdateFocusedAppRunnable)) {
            handler.removeCallbacks(multiSoundManager.mUpdateFocusedAppRunnable);
        }
        SetVolumeRunnable setVolumeRunnable = new SetVolumeRunnable(multiSoundManager, i, 2);
        multiSoundManager.mUpdateFocusedAppRunnable = setVolumeRunnable;
        handler.post(setVolumeRunnable);
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
        hashSet.add(Integer.valueOf(EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION));
        hashSet.add(67108864);
        hashSet.add(32768);
    }

    public MultiSoundManager(Context context, AudioService.AnonymousClass11 anonymousClass11, Handler handler, AudioSystemAdapter audioSystemAdapter) {
        int i = -1;
        this.mSetVolumeRunnable = new SetVolumeRunnable(this, i, 0);
        this.mUpdateFocusedAppRunnable = new SetVolumeRunnable(this, i, 2);
        float[] fArr = new float[101];
        this.mVolumeTable = fArr;
        fArr[0] = 0.0f;
        fArr[100] = 1.0f;
        for (int i2 = 1; i2 < 100; i2++) {
            this.mVolumeTable[i2] = (float) (Math.exp(((i2 / 100.0d) * 5.0d) - 5.0d) - Math.exp(-5.0d));
        }
        this.mInterface = anonymousClass11;
        this.mAudioHandler = handler;
        this.mContext = context;
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mNm = (NotificationManager) context.getSystemService("notification");
        this.mPackageManager = context.getPackageManager();
        this.mAudioSystem = audioSystemAdapter;
        try {
            ActivityTaskManager.getService().registerTaskStackListener(new TaskStackListener() { // from class: com.samsung.android.server.audio.MultiSoundManager.1
                public final void onTaskFocusChanged(int i3, boolean z) {
                    if (z) {
                        MultiSoundManager.m1221$$Nest$mupdateForegroundAppUid(MultiSoundManager.this, i3, true);
                    }
                }

                public final void onTaskRemoved(int i3) {
                    MultiSoundManager.m1221$$Nest$mupdateForegroundAppUid(MultiSoundManager.this, i3, false);
                }
            });
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception - registerTaskStackListener "), "AS.MultiSoundManager");
        }
    }

    public final void enableSeparateRemoteSubmix(int i, boolean z) {
        this.mAudioSystem.setParameters(new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("enable", z).setParam("exclusive", z).setParam("device", 32768).setParam("type", 2).build().toString());
        MediaSessionService mediaSessionService = MediaSessionService.this;
        mediaSessionService.mIsAppCastingOn = z;
        mediaSessionService.mAppCastingUid = i;
    }

    public final int getAppDevice(int i) {
        AudioService.AnonymousClass11 anonymousClass11 = this.mInterface;
        int i2 = 0;
        if (AudioService.this.mIsLeBroadCasting || anonymousClass11.isSmartViewEnabled()) {
            return 0;
        }
        synchronized (this.mRemoteSubmixApps) {
            try {
                if (((HashSet) this.mRemoteSubmixApps).contains(Integer.valueOf(i))) {
                    return 32768;
                }
                MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
                if (multiSoundItem == null) {
                    return 0;
                }
                int i3 = multiSoundItem.mDevice;
                String str = i3 == 32768 ? "0" : "";
                this.mAudioSystem.getClass();
                if (AudioSystem.getDeviceConnectionState(i3, str) <= 0) {
                    i3 = 0;
                }
                if (i3 == 32768) {
                    return i3;
                }
                if (!this.mEnabled) {
                    return 0;
                }
                if (i3 != 2) {
                    return i3;
                }
                int currentMediaDevice = getCurrentMediaDevice();
                if (SemAudioSystem.MULTI_SOUND_PRIMARY_DEVICE_SET.contains(Integer.valueOf(currentMediaDevice))) {
                    return currentMediaDevice;
                }
                AudioDeviceInventory audioDeviceInventory = AudioService.this.mDeviceBroker.mDeviceInventory;
                for (Map.Entry entry : audioDeviceInventory.mConnectedDevices.entrySet()) {
                    String str2 = (String) entry.getKey();
                    AudioDeviceInventory.DeviceInfo deviceInfo = (AudioDeviceInventory.DeviceInfo) audioDeviceInventory.mConnectedDevices.get(str2);
                    int i4 = deviceInfo.mDeviceType;
                    if ((Integer.MIN_VALUE & i4) == 0 && SemAudioSystem.MULTI_SOUND_PRIMARY_DEVICE_SET.contains(Integer.valueOf(i4))) {
                        i2 = deviceInfo.mDeviceType | i2;
                    }
                }
                if ((i2 & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) != 0) {
                    return EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
                }
                int i5 = 67108864;
                if ((i2 & 67108864) == 0) {
                    if ((i2 & 4) != 0) {
                        return 4;
                    }
                    if ((i2 & 8) != 0) {
                        return 8;
                    }
                    i5 = 1024;
                    if ((i2 & 1024) == 0) {
                        return 2;
                    }
                }
                return i5;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getAppDevice(int i, boolean z) {
        if (!z) {
            return getAppDevice(i);
        }
        MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
        if (multiSoundItem != null) {
            return multiSoundItem.mDevice;
        }
        return 0;
    }

    public final int getCurrentMediaDevice() {
        int deviceMaskFromSet = AudioSystem.getDeviceMaskFromSet(AudioService.this.mStreamStates[3].mObservedDeviceSet);
        if (((deviceMaskFromSet - 1) & deviceMaskFromSet) == 0) {
            return deviceMaskFromSet;
        }
        if ((deviceMaskFromSet & 2) != 0) {
            return 2;
        }
        int i = 262144;
        if ((deviceMaskFromSet & 262144) == 0) {
            i = 524288;
            if ((deviceMaskFromSet & 524288) == 0) {
                i = 2097152;
                if ((deviceMaskFromSet & 2097152) == 0) {
                    Iterator it = AudioSystem.DEVICE_OUT_ALL_A2DP_SET.iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        if ((intValue & deviceMaskFromSet) == intValue) {
                            return intValue;
                        }
                    }
                    return deviceMaskFromSet;
                }
            }
        }
        return i;
    }

    public final String getPinAppInfo(int i) {
        String join;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mRemoteSubmixApps) {
            if (isRemoteSubmixAppExist() && i == 32768) {
                Iterator it = ((HashSet) this.mRemoteSubmixApps).iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    AudioService.AnonymousClass11 anonymousClass11 = this.mInterface;
                    anonymousClass11.getClass();
                    int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                    String str = AudioService.this.getPackageName(intValue)[0];
                    try {
                        PackageManager packageManager = AudioService.this.mPackageManager;
                        arrayList.add(packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString());
                    } catch (Exception unused) {
                    }
                }
                return String.join(", ", arrayList);
            }
            synchronized (this.mMultiSoundLock) {
                Iterator it2 = this.mPinAppInfoList.entrySet().iterator();
                while (it2.hasNext()) {
                    int intValue2 = ((Integer) ((Map.Entry) it2.next()).getKey()).intValue();
                    if (getAppDevice(intValue2, true) == i) {
                        AudioService.AnonymousClass11 anonymousClass112 = this.mInterface;
                        anonymousClass112.getClass();
                        int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
                        String[] packageName = AudioService.this.getPackageName(intValue2);
                        String str2 = packageName[0];
                        String string = Settings.System.getString(AudioService.this.mContentResolver, "multisound_app");
                        for (String str3 : packageName) {
                            if (str3.equals(string)) {
                                str2 = str3;
                                break;
                            }
                        }
                        try {
                            PackageManager packageManager2 = AudioService.this.mPackageManager;
                            arrayList.add(packageManager2.getApplicationLabel(packageManager2.getApplicationInfo(str2, 0)).toString());
                        } catch (Exception e) {
                            Log.e("AS.MultiSoundManager", "not found " + e.getMessage());
                        }
                    }
                }
                join = String.join(", ", arrayList);
            }
            return join;
        }
    }

    public final int getPinDevice(boolean z) {
        int appDevice;
        if (!z && !this.mEnabled) {
            return 0;
        }
        Iterator it = new ArrayList(this.mPinAppInfoList.values()).iterator();
        while (it.hasNext()) {
            MultiSoundItem multiSoundItem = (MultiSoundItem) it.next();
            if (multiSoundItem.mDevice != 0 && (appDevice = getAppDevice(multiSoundItem.mUid, z)) != 0 && appDevice != 32768) {
                return appDevice;
            }
        }
        return 0;
    }

    public final boolean isRemoteSubmixAppExist() {
        boolean z;
        synchronized (this.mRemoteSubmixApps) {
            z = !((HashSet) this.mRemoteSubmixApps).isEmpty();
        }
        return z;
    }

    public final void resetByEnableDisable() {
        Log.d("AS.MultiSoundManager", "resetByEnableDisable");
        int devicesForStream = AudioSystem.getDevicesForStream(3);
        Iterator it = new ArrayList(this.mPinAppInfoList.values()).iterator();
        while (it.hasNext()) {
            MultiSoundItem multiSoundItem = (MultiSoundItem) it.next();
            if (multiSoundItem.mDevice != 0) {
                boolean z = this.mEnabled;
                AudioService.AnonymousClass11 anonymousClass11 = this.mInterface;
                int i = multiSoundItem.mUid;
                if (!z && devicesForStream != 32768 && devicesForStream != 536870914 && !anonymousClass11.isSmartViewEnabled() && devicesForStream != getAppDevice(i)) {
                    int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                    AudioService audioService = AudioService.this;
                    audioService.sendBecomingNoisyIntent(i, audioService.getPackageName(i));
                }
                AudioService.this.mMediaFocusControl.updateFocusRequester(i, false);
            }
        }
        setStateToNative();
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

    public final void sendIntentToSpecificPackage(Intent intent, String[] strArr) {
        int i;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            PackageManager packageManager = this.mPackageManager;
            PackageManager.ResolveInfoFlags of = PackageManager.ResolveInfoFlags.of(0L);
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    i = ActivityManager.getService().getCurrentUser().id;
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (RemoteException unused) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                i = 0;
            }
            List<ResolveInfo> queryBroadcastReceiversAsUser = packageManager.queryBroadcastReceiversAsUser(intent, of, i);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            String[] queryRegisteredReceiverPackages = this.mActivityManager.queryRegisteredReceiverPackages(intent);
            for (ResolveInfo resolveInfo : queryBroadcastReceiversAsUser) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                if (activityInfo != null) {
                    String str = activityInfo.packageName;
                    if (!str.isEmpty()) {
                        int length = strArr.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                intent.setPackage(resolveInfo.activityInfo.packageName);
                                sendBroadcastToAll(intent);
                                VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("sendBecomingNoisyIntentToUnpinApps to "), resolveInfo.activityInfo.packageName, "AS.MultiSoundManager");
                                break;
                            } else {
                                String str2 = strArr[i2];
                                if (str.equals(str2)) {
                                    DualAppManagerService$$ExternalSyntheticOutline0.m("send intent except ", str2, "AS.MultiSoundManager");
                                    break;
                                }
                                i2++;
                            }
                        }
                    }
                }
            }
            for (String str3 : queryRegisteredReceiverPackages) {
                if (str3 != null && !str3.isEmpty()) {
                    int length2 = strArr.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length2) {
                            intent.setPackage(str3);
                            sendBroadcastToAll(intent);
                            Log.d("AS.MultiSoundManager", "sendBecomingNoisyIntentToUnpinApps to ".concat(str3));
                            break;
                        } else {
                            String str4 = strArr[i3];
                            if (str3.equals(str4)) {
                                DualAppManagerService$$ExternalSyntheticOutline0.m("send intent except ", str4, "AS.MultiSoundManager");
                                break;
                            }
                            i3++;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void setAppDevice(int i, int i2, boolean z) {
        int devicesForStream = AudioSystem.getDevicesForStream(3);
        synchronized (this.mMultiSoundLock) {
            try {
                if (!AudioService.this.checkAudioSettingsPermission("setAppDevice")) {
                    Log.e("AS.MultiSoundManager", "setAppDevice: permission error");
                    return;
                }
                if (!((HashSet) MULTI_SOUND_SUPPORTED_DEVICE_SET).contains(Integer.valueOf(i2)) && i2 != 0) {
                    Log.e("AS.MultiSoundManager", "setAppDevice: unsupported device");
                    return;
                }
                MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
                if (multiSoundItem != null && multiSoundItem.mDevice == i2) {
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
                    multiSoundItem2.mDevice = i2;
                }
                if (multiSoundItem2.removable()) {
                    this.mPinAppInfoList.remove(Integer.valueOf(i));
                } else {
                    this.mPinAppInfoList.put(Integer.valueOf(i), multiSoundItem2);
                }
                setDeviceToNative(i, i2);
                if (this.mEnabled && i2 == devicesForStream && z) {
                    AudioService audioService = AudioService.this;
                    audioService.mMultiSoundManager.showHeadUpNotification(devicesForStream, audioService.mDeviceBroker.getPriorityDevice(devicesForStream));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setAppToRemoteSubmix(int i, int i2) {
        synchronized (this.mRemoteSubmixApps) {
            boolean z = true;
            boolean z2 = false;
            try {
                if (i2 == -1000) {
                    if (((HashSet) this.mRemoteSubmixApps).contains(Integer.valueOf(i))) {
                        Log.w("AS.MultiSoundManager", "Already set uid " + i);
                        return;
                    } else {
                        setRemoteSubmixAppToNative(i, 32768);
                        if (((HashSet) this.mRemoteSubmixApps).isEmpty()) {
                            enableSeparateRemoteSubmix(i, true);
                        }
                        ((HashSet) this.mRemoteSubmixApps).add(Integer.valueOf(i));
                    }
                } else {
                    if (i2 != -1002) {
                        z = false;
                    }
                    if (!((HashSet) this.mRemoteSubmixApps).contains(Integer.valueOf(i))) {
                        Log.w("AS.MultiSoundManager", "Invalid uid " + i);
                        return;
                    } else {
                        ((HashSet) this.mRemoteSubmixApps).remove(Integer.valueOf(i));
                        if (((HashSet) this.mRemoteSubmixApps).isEmpty()) {
                            enableSeparateRemoteSubmix(-1, false);
                        }
                        setRemoteSubmixAppToNative(i, 0);
                        z2 = z;
                    }
                }
                AudioService.this.mMediaFocusControl.updateFocusRequester(i, z2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setAppVolume(int i, int i2) {
        boolean removable;
        if (i2 > 100 || i2 < 0) {
            Log.e("AS.MultiSoundManager", "setAppVolume: Invalid volume");
            return;
        }
        synchronized (this.mMultiSoundLock) {
            try {
                MultiSoundItem multiSoundItem = (MultiSoundItem) this.mPinAppInfoList.get(Integer.valueOf(i));
                if (multiSoundItem != null) {
                    if (i2 >= 0 && i2 <= 100) {
                        multiSoundItem.mRatio = i2;
                        removable = multiSoundItem.removable();
                    }
                    Log.e("AS.MultiSoundManager", "Invalid app volume");
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setAppVolumeToNative(int i) {
        SetVolumeRunnable setVolumeRunnable = this.mSetVolumeRunnable;
        int i2 = setVolumeRunnable.mUid;
        Handler handler = this.mAudioHandler;
        if (i2 == i) {
            handler.removeCallbacks(setVolumeRunnable);
        } else {
            this.mSetVolumeRunnable = new SetVolumeRunnable(this, i, 0);
        }
        handler.post(this.mSetVolumeRunnable);
    }

    public final void setDeviceToNative(int i, int i2) {
        AudioParameter build = new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("uid", i).setParam("device", i2).setParam("type", 1).build();
        synchronized (sLastSetDeviceToNativeLock) {
            long uptimeMillis = SystemClock.uptimeMillis() + 0;
            long j = sLastSetDeviceToNativeTime;
            if (j >= uptimeMillis) {
                uptimeMillis = 30 + j;
            }
            sLastSetDeviceToNativeTime = uptimeMillis;
            this.mAudioHandler.postAtTime(new MultiSoundManager$$ExternalSyntheticLambda1(this, build, 1), uptimeMillis);
        }
    }

    public final void setRemoteSubmixAppToNative(int i, int i2) {
        this.mAudioSystem.setParameters(new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("uid", i).setParam("device", i2).setParam("type", 2).build().toString());
    }

    public final void setStateToNative() {
        boolean z = this.mEnabled;
        AudioService.AnonymousClass11 anonymousClass11 = this.mInterface;
        if (!AudioService.this.mIsLeBroadCasting) {
            if (anonymousClass11.isSmartViewEnabled()) {
                Log.d("AS.MultiSoundManager", "send multisound off during smart view");
            }
            this.mAudioHandler.postDelayed(new MultiSoundManager$$ExternalSyntheticLambda1(this, new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("enable", z).setParam("type", 1).build(), 0), 200L);
        }
        Log.d("AS.MultiSoundManager", "send multisound off during le broadcasting");
        z = false;
        this.mAudioHandler.postDelayed(new MultiSoundManager$$ExternalSyntheticLambda1(this, new AudioParameter.Builder().setParam("audioParam").setParam("l_multi_sound_key").setParam("enable", z).setParam("type", 1).build(), 0), 200L);
    }

    public final void showHeadUpNotification(int i, int i2) {
        AudioDeviceInfo[] devicesStatic = AudioManager.getDevicesStatic(2);
        String[] strArr = new String[2];
        strArr[0] = "";
        strArr[1] = "";
        int[] iArr = {i, i2};
        Set set = SemAudioSystem.MULTI_SOUND_PRIMARY_DEVICE_SET;
        if (set.contains(Integer.valueOf(iArr[0])) && set.contains(Integer.valueOf(iArr[1]))) {
            return;
        }
        for (int i3 = 0; i3 < 2; i3++) {
            int i4 = iArr[i3];
            if (i4 == 2) {
                strArr[i3] = this.mContext.getResources().getString(R.string.roamingText4);
            } else if (i4 == 4 || i4 == 8) {
                strArr[i3] = this.mContext.getResources().getString(R.string.miniresolver_private_space_phone_information);
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
        String format = String.format(this.mContext.getString(R.string.review_notification_settings_remind_me_action), strArr[0], strArr[1]);
        PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 1, new Intent("com.samsung.android.audio.headup.changedevice"), 201326592);
        this.mNm.notify(1005, new Notification.Builder(this.mContext, "AudioCore_Notification").setAutoCancel(true).setShowWhen(true).setSmallIcon(17304429).setContentTitle(this.mContext.getString(R.string.review_notification_settings_text)).setContentText(format).setStyle(new Notification.BigTextStyle().bigText(format)).setPriority(2).setDefaults(1).addAction(0, this.mContext.getString(R.string.restr_pin_incorrect), broadcast).setContentIntent(broadcast).build());
    }

    public final void showNotification() {
        if (this.mNm == null) {
            return;
        }
        int devicesForStream = AudioSystem.getDevicesForStream(3);
        int pinDevice = getPinDevice(false);
        if (pinDevice == 0 || devicesForStream == pinDevice) {
            return;
        }
        Intent intent = new Intent();
        if (SystemProperties.get("ro.build.characteristics").contains("tablet")) {
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
        String string = (pinDevice == 4 || pinDevice == 8) ? this.mContext.getString(R.string.ringtone_picker_title) : pinDevice != 128 ? (pinDevice == 16384 || pinDevice == 67108864) ? this.mContext.getString(R.string.ringtone_picker_title) : this.mContext.getString(R.string.ringtone_picker_title_notification) : this.mContext.getString(R.string.ringtone_default);
        builder.setStyle(new Notification.BigTextStyle().bigText(string)).setContentTitle(this.mContext.getString(R.string.roamingText0)).setSmallIcon(17304429).setContentText(string).setAutoCancel(false).setVibrate(null).setContentIntent(activity).addAction(R.drawable.stat_notify_error, this.mContext.getResources().getText(R.string.review_notification_settings_title), activity).addAction(R.drawable.stat_notify_error, this.mContext.getResources().getText(R.string.roamingText1), broadcast);
        this.mNm.notify(1004, builder.build());
    }

    public final void updateAudioServiceNotificationChannel() {
        NotificationManager notificationManager = this.mNm;
        if (notificationManager != null) {
            notificationManager.deleteNotificationChannel("AudioCore_Notification");
        }
        this.mNm = (NotificationManager) this.mContext.getSystemService("notification");
        this.mNm.createNotificationChannel(new NotificationChannel("AudioCore_Notification", this.mContext.getResources().getString(R.string.roamingText4), 4));
    }
}
