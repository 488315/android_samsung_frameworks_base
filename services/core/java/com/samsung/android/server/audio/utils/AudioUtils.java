package com.samsung.android.server.audio.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioSystem;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.audio.Rune;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.server.audio.SamsungMusicHelper;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class AudioUtils {
    public static final Set DEVICE_OUT_WIRED_DEVICE_SET;
    public static final Set SKIP_RESTORE_DEVICE_SET;

    static {
        HashSet hashSet = new HashSet();
        DEVICE_OUT_WIRED_DEVICE_SET = hashSet;
        hashSet.add(4);
        hashSet.add(8);
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_USB_SET);
        HashSet hashSet2 = new HashSet();
        SKIP_RESTORE_DEVICE_SET = hashSet2;
        hashSet2.add(2);
        hashSet2.add(32768);
    }

    public static boolean isUsingAudioForUid(int i) {
        return "true".equalsIgnoreCase(AudioSystem.getParameters("l_multi_sound_active_track_uid=" + i));
    }

    public static boolean isUsingAudioUponDevice(int i) {
        return "true".equalsIgnoreCase(AudioSystem.getParameters("l_multi_sound_active_track_device=" + i));
    }

    public static void setDeviceVolumeProperty(int i, int i2) {
        String num = Integer.toString(i2);
        if (i == 2) {
            SystemProperties.set("persist.audio.sysvolume", num);
            return;
        }
        if (isWiredDeviceType(i)) {
            if (i == 4) {
                SystemProperties.set("persist.audio.headsetsysvolume", num);
            } else if (i == 8) {
                SystemProperties.set("persist.audio.hphonesysvolume", num);
            }
        }
    }

    public static int getUidForPackage(Context context, String str) {
        try {
            return context.getPackageManager().getApplicationInfoAsUser(str, 0, getCurrentUserId()).uid;
        } catch (PackageManager.NameNotFoundException unused) {
            return 0;
        }
    }

    public static int getCurrentUserId() {
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

    public static boolean isSkipRestoreDevice(int i) {
        return SKIP_RESTORE_DEVICE_SET.contains(Integer.valueOf(i));
    }

    public static boolean isWiredDeviceType(int i) {
        return DEVICE_OUT_WIRED_DEVICE_SET.contains(Integer.valueOf(i));
    }

    public static int getWiredDeviceIdFromSysFile() {
        int i = -1;
        try {
            FileReader fileReader = new FileReader("/sys/class/switch/h2w/state", StandardCharsets.UTF_8);
            try {
                if (fileReader.ready()) {
                    char[] cArr = new char[1024];
                    i = Integer.parseInt(new String(cArr, 0, fileReader.read(cArr, 0, 1024)).trim());
                }
                fileReader.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static void sendBroadcastToUser(Context context, Intent intent, UserHandle userHandle, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            context.sendBroadcastAsUser(intent, userHandle, str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static void wakeUpDeviceByWiredHeadset(Context context, int i) {
        if (Rune.SEC_AUDIO_SCREEN_OFF_MUSIC && SamsungMusicHelper.isScreenOffMusicEnabled(context)) {
            Log.i("AS.AudioUtils", "Screen on is handled by samsung music");
        } else if (isWiredDeviceType(i)) {
            ((PowerManager) context.getSystemService("power")).semWakeUp(SystemClock.uptimeMillis(), 3, "WAKE_REASON_PLUGGED_IN");
        }
    }

    public static List getVisibleTasks() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return new MultiWindowManager().getVisibleTasks();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean checkRunningBackground(String str) {
        List visibleTasks = getVisibleTasks();
        int size = visibleTasks.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(((ActivityManager.RunningTaskInfo) visibleTasks.get(i)).topActivity.getPackageName())) {
                Log.d("AS.AudioUtils", "checkRunningBackground : visible Tasks =" + str);
                return false;
            }
        }
        return true;
    }

    public static boolean isRecordActive(int i) {
        if (i == -1) {
            return "true".equalsIgnoreCase(AudioSystem.getParameters("l_record_active_enable"));
        }
        if (!MediaRecorder.isValidAudioSource(i) && !MediaRecorder.isValidAudioSourceForSem(i)) {
            Log.e("AS.AudioUtils", "Invalid AudioSource : " + i);
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return AudioSystem.isSourceActive(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
