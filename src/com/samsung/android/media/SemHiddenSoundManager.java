package com.samsung.android.media;

import android.media.IAudioService;
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

    public static int getPlaybackRecorderVersion() {
        return 1;
    }

    private SemHiddenSoundManager() {
    }

    private static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        IAudioService asInterface = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        sService = asInterface;
        return asInterface;
    }

    private static void setAudioServiceConfig(String str) {
        try {
            getService().setAudioServiceConfig("audioParam;" + str);
        } catch (RemoteException unused) {
        }
    }

    private static String getAudioServiceConfig(String str) {
        try {
            return getService().getAudioServiceConfig("audioParam;" + str);
        } catch (RemoteException unused) {
            return "";
        }
    }

    private static String getClientAddress() {
        return String.format("p:%du:%d", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myUid()));
    }

    public static void setPlaybackRecorderVolume(int i) {
        setAudioServiceConfig("l_hidden_sound_key;volume=" + i + ";address=" + getClientAddress());
    }

    public static int getPlaybackRecorderVolume() {
        try {
            return Integer.parseInt(getAudioServiceConfig("l_hidden_sound_key;volume;address=" + getClientAddress()));
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid volume", e);
            return -1;
        }
    }

    public static void setPlaybackRecorderPackage(int i) {
        setAudioServiceConfig("l_hidden_sound_key;pid=" + i + ";address=" + getClientAddress());
    }

    public static int getPlaybackRecorderPackage() {
        try {
            return Integer.parseInt(getAudioServiceConfig("l_hidden_sound_key;pid;address=" + getClientAddress()));
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid PID", e);
            return -1;
        }
    }
}
