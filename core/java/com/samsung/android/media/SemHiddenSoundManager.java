package com.samsung.android.media;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.IAudioService;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemHiddenSoundManager {
    public static final int ERROR = -1;
    public static final int PACKAGE_ALL = 0;
    private static final String TAG = "SemHiddenSoundManager";
    public static final int VOLUME_DEVICE = -3;
    public static final int VOLUME_FULL = -2;
    private static IAudioService sService;

    private SemHiddenSoundManager() {}

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("audio");
        sService = IAudioService.Stub.asInterface(b);
        return sService;
    }

    private static void setAudioServiceConfig(String param) {
        IAudioService service = getService();
        try {
            service.setAudioServiceConfig("audioParam;" + param);
        } catch (RemoteException e) {
        }
    }

    private static String getAudioServiceConfig(String param) {
        IAudioService service = getService();
        try {
            return service.getAudioServiceConfig("audioParam;" + param);
        } catch (RemoteException e) {
            return "";
        }
    }

    private static String getClientAddress() {
        String address =
                String.format(
                        "p:%du:%d",
                        Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myUid()));
        return address;
    }

    public static int getPlaybackRecorderVersion() {
        return 1;
    }

    public static void setPlaybackRecorderVolume(int volume) {
        StringBuilder paramBuilder =
                new StringBuilder(AudioParameter.SEC_LOCAL_HIDDEN_SOUND_KEY)
                        .append(NavigationBarInflaterView.GRAVITY_SEPARATOR)
                        .append("volume")
                        .append("=")
                        .append(volume)
                        .append(NavigationBarInflaterView.GRAVITY_SEPARATOR)
                        .append("address")
                        .append("=")
                        .append(getClientAddress());
        setAudioServiceConfig(paramBuilder.toString());
    }

    public static int getPlaybackRecorderVolume() {
        StringBuilder paramBuilder =
                new StringBuilder(AudioParameter.SEC_LOCAL_HIDDEN_SOUND_KEY)
                        .append(NavigationBarInflaterView.GRAVITY_SEPARATOR)
                        .append("volume")
                        .append(NavigationBarInflaterView.GRAVITY_SEPARATOR)
                        .append("address")
                        .append("=")
                        .append(getClientAddress());
        try {
            int volume = Integer.parseInt(getAudioServiceConfig(paramBuilder.toString()));
            return volume;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid volume", e);
            return -1;
        }
    }

    public static void setPlaybackRecorderPackage(int pid) {
        StringBuilder paramBuilder =
                new StringBuilder(AudioParameter.SEC_LOCAL_HIDDEN_SOUND_KEY)
                        .append(NavigationBarInflaterView.GRAVITY_SEPARATOR)
                        .append("pid")
                        .append("=")
                        .append(pid)
                        .append(NavigationBarInflaterView.GRAVITY_SEPARATOR)
                        .append("address")
                        .append("=")
                        .append(getClientAddress());
        setAudioServiceConfig(paramBuilder.toString());
    }

    public static int getPlaybackRecorderPackage() {
        StringBuilder paramBuilder =
                new StringBuilder(AudioParameter.SEC_LOCAL_HIDDEN_SOUND_KEY)
                        .append(NavigationBarInflaterView.GRAVITY_SEPARATOR)
                        .append("pid")
                        .append(NavigationBarInflaterView.GRAVITY_SEPARATOR)
                        .append("address")
                        .append("=")
                        .append(getClientAddress());
        try {
            int pid = Integer.parseInt(getAudioServiceConfig(paramBuilder.toString()));
            return pid;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid PID", e);
            return -1;
        }
    }
}
