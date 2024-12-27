package com.samsung.android.media;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.IAudioService;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.collect.Sets;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.IntPredicate;

/* loaded from: classes6.dex */
public class SemSoundAssistantManager {
    public static final String ACTION_SOUND_EVENT_CHANGED =
            "com.samsung.android.intent.action.SOUND_EVENT";
    public static final int BOOT_COMPLETED = 1003;
    public static final String BRAND_SOUND_VERSION = "brand_sound_version";
    public static final int CARLIFE_FOCUS_GRANT_INDEX = 1;
    public static final int CARLIFE_FOCUS_LOSS_INDEX = 2;
    private static final long DEFAULT_MEDIA_SESSION_CALLBACK_FGS_ALLOWLIST_DURATION_MS = 10000;
    public static final int DEVICE_BLUETOOTH = 2;
    public static final int DEVICE_DEFAULT = 0;
    public static final int DEVICE_HEADSET = 3;
    public static final int DEVICE_SPEAKER_OR_HEADSET = 1;
    public static final String ENABLE_FLOATING_BUTTON = "enable_floating_button";
    public static final int EXECUTE_FLOATING_BUTTON = 0;
    public static final String GET_APP_VOLUME_LIST = "get_app_volume_list";
    public static final String GET_MODE_OWNER_UIDS = "get_mode_owner_uids";
    public static final int HEADSET_ONLY_ALARM = 16;
    public static final int HEADSET_ONLY_ALL = 4;
    public static final int HEADSET_ONLY_NOTIFICATION = 32;
    public static final int HEADSET_ONLY_RINGTONE = 1;
    public static final String IGNORE_AUDIO_FOCUS = "ignore_audio_focus";
    public static final String IGNORE_DUCKING = "ignore_ducking";
    public static final String MEDIA_BUTTON_PACKAGE = "media_button_package";
    public static final String MEDIA_VOLUME_MULTI_STEP = "sec_volume_steps";
    public static final int MEDIA_VOLUME_STEP_DEFAULT = 10;
    public static final String MEDIA_VOLUME_STEP_INDEX = "media_volume_step_index";
    public static final int MEDIA_VOLUME_STEP_MAX = 10;
    public static final int MEDIA_VOLUME_STEP_MIN = 1;
    public static final int MIC_INPUT_CONTROL_MODE_DEFAULT = 100;
    public static final int MIC_INPUT_CONTROL_MODE_FOCUS_ON_ALL_SOUNDS = 2;
    public static final int MIC_INPUT_CONTROL_MODE_FOCUS_ON_ALL_SOUNDS_FOR_VIDEO_CALL_ON_2MIC = 7;
    public static final int MIC_INPUT_CONTROL_MODE_FOCUS_ON_ALL_SOUNDS_FOR_VOICE_CALL_ON_2MIC = 4;
    public static final int MIC_INPUT_CONTROL_MODE_FOCUS_ON_VOICE = 1;
    public static final int MIC_INPUT_CONTROL_MODE_FOCUS_ON_VOICE_FOR_VIDEO_CALL_ON_2MIC = 6;
    public static final int MIC_INPUT_CONTROL_MODE_FOCUS_ON_VOICE_FOR_VOICE_CALL_ON_2MIC = 3;
    public static final int MIC_INPUT_CONTROL_MODE_STANDARD = 0;
    public static final int MIC_INPUT_CONTROL_MODE_STANDARD_FOR_VIDEO_CALL_ON_2MIC = 5;
    public static final int MODE_ADJUST_MEDIA_VOLUME_ONLY = 1;
    public static final int MODE_DEFAULT = 0;
    public static final int MODE_MUTE_MEDIA_BY_VIBRATE_OR_SILENT_MODE = 2;
    public static final String MONO_SOUND = "mono_sound";
    public static final String MULTI_AUDIO_FOCUS = "multi_audio_focus";
    public static final String NO_FADEOUT_FROM_AUDIOFOCUS = "NO_FADEOUT_FROM_AUDIOFOCUS";
    public static final String NO_MUTE_IN_CALL = "NO_MUTE_IN_CALL";
    public static final String PARAMETER_PREFIX = "sound_assistant";
    public static final String REMOVE_APP_VOLUME = "remove_app_volume";
    public static final String SETTING_RINGTONE_THROUGH_HEADSET_ONLY = "ring_through_headset";
    public static final String SETTING_SOUND_LR_SWITCH = "sound_lr_switch";
    public static final int SOUNDSETTING_EVENT_A2DP_CONNECTION_CHANGED = 8;
    public static final int SOUNDSETTING_EVENT_CARLIFE_RECEIVER = 512;
    public static final int SOUNDSETTING_EVENT_HEADSET_CONNECTION_CHANGED = 4;
    public static final int SOUNDSETTING_EVENT_MEDIA_KEY_RECEIVER = 64;
    public static final int SOUNDSETTING_EVENT_MEDIA_MUTE_CHANGED = 2;
    public static final int SOUNDSETTING_EVENT_MEDIA_VOLUME_CHANGED = 256;
    public static final int SOUNDSETTING_EVENT_NONE = 0;
    public static final int SOUNDSETTING_EVENT_PLAYBACK_STATE_CHANGED = 16;
    public static final int SOUNDSETTING_EVENT_RECORDING_STARTED_RECEIVER = 128;
    public static final int SOUNDSETTING_EVENT_RINGERMODE_CHANGED = 1;
    public static final int SOUNDSETTING_EVENT_VOLUMEKEY_LONGPRESS = 32;
    public static final String SOUNDSETTING_EXTRA_EVENT_CALLING_PACKAGE = "package";
    public static final String SOUNDSETTING_EXTRA_EVENT_TYPE = "type";
    public static final String SOUNDSETTING_EXTRA_EVENT_VALUE = "value";
    public static final String SOUND_BALANCE = "sound_balance";
    private static final String TAG = "SemSoundAssistant";
    public static final String UID_FOR_SOUNDASSISTANT = "uid_for_soundassistant";
    public static final String USING_AUDIO_UIDS = "using_audio_uids";
    public static final String VERSION = "version";
    public static final IntPredicate VOLUME_MODE_PREDICATE;
    public static final int VOLUME_STAR_DISABLE = 101;
    public static final int VOLUME_STAR_ENABLE = 100;
    public static final String VOLUME_STAR_ENABLE_PARAM = "volumestar_enable";
    private static final Object mLock;
    private static boolean sIsRunning;
    private static final Object sLock;
    private static final List<OnMediaKeyEventSessionChangedListener>
            sMediaKeySessionChangedCallbacks;
    private static final MediaSessionManager.OnMediaKeyEventSessionChangedListener
            sMediaKeySessionChangedListener;
    static final ArrayMap<Integer, String> sMicModeParamTable;
    private static IAudioService sService;
    private Context mApplicationContext;
    private AudioManager mAudioManager;
    private boolean mFloatingButton;
    private Context mOriginalContext;
    protected static final Set<Integer> VOLUME_MODE_ALL = Sets.newHashSet(1, 2);
    public static final String ADJUST_MEDIA_ONLY = "adjust_media_volume_only";
    public static final String MUTE_MEDIA_BY_VIBRATE_OR_SILENT_MODE =
            "mute_media_by_vibrate_or_silent_mode";
    protected static final String[] VOLUME_MODE_KEY = {
        "", ADJUST_MEDIA_ONLY, MUTE_MEDIA_BY_VIBRATE_OR_SILENT_MODE
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface MicInputControlMode {}

    public interface OnMediaKeyEventSessionChangedListener {
        void onMediaKeyEventSessionChanged(String str, MediaSession.Token token);
    }

    static {
        final Set<Integer> set = VOLUME_MODE_ALL;
        Objects.requireNonNull(set);
        VOLUME_MODE_PREDICATE =
                new IntPredicate() { // from class:
                                     // com.samsung.android.media.SemSoundAssistantManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i) {
                        return set.contains(Integer.valueOf(i));
                    }
                };
        sIsRunning = false;
        sLock = new Object();
        mLock = new Object();
        sMediaKeySessionChangedCallbacks = new ArrayList();
        sMediaKeySessionChangedListener =
                new MediaSessionManager
                        .OnMediaKeyEventSessionChangedListener() { // from class:
                                                                   // com.samsung.android.media.SemSoundAssistantManager$$ExternalSyntheticLambda1
                    @Override // android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListener
                    public final void onMediaKeyEventSessionChanged(
                            String str, MediaSession.Token token) {
                        SemSoundAssistantManager.lambda$static$0(str, token);
                    }
                };
        sMicModeParamTable = new ArrayMap<>();
        sMicModeParamTable.put(0, "l_mic_input_control_mode=0");
        sMicModeParamTable.put(1, "l_mic_input_control_mode=1");
        sMicModeParamTable.put(2, "l_mic_input_control_mode=2");
        sMicModeParamTable.put(3, "l_call_nc_booster_enable=1");
        sMicModeParamTable.put(4, "l_call_nc_booster_enable=2");
        sMicModeParamTable.put(5, "l_mic_input_control_mode_2mic=0");
        sMicModeParamTable.put(6, "l_mic_input_control_mode_2mic=1");
        sMicModeParamTable.put(7, "l_mic_input_control_mode_2mic=2");
        sMicModeParamTable.put(100, "l_mic_input_control_mode=100");
    }

    public SemSoundAssistantManager(Context context) {
        setContext(context);
        this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
    }

    private void setContext(Context context) {
        this.mApplicationContext = context.getApplicationContext();
        if (this.mApplicationContext != null) {
            this.mOriginalContext = null;
        } else {
            this.mOriginalContext = context;
        }
    }

    private Context getContext() {
        if (this.mApplicationContext == null) {
            setContext(this.mOriginalContext);
        }
        if (this.mApplicationContext != null) {
            return this.mApplicationContext;
        }
        return this.mOriginalContext;
    }

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        IBinder b = ServiceManager.getService("audio");
        sService = IAudioService.Stub.asInterface(b);
        return sService;
    }

    public void initApplicationVolume(int uid) {
        String param = "remove_app_volume=" + uid;
        setSoundAssistantProperty(param);
    }

    public void adjustSoundBalance(int balance) {
        if (balance < 0 || balance > 100) {
            throw new IllegalArgumentException("Bad ratio value");
        }
        setSoundAssistantParam("sound_balance=" + balance);
    }

    public void forceMonoSound(boolean z) {
        setSoundAssistantParam("mono_sound=" + (z ? 1 : 0));
    }

    public void activateFloatingButton(boolean on) {
        this.mFloatingButton = on;
    }

    public boolean isFloatingButtonActivated() {
        return this.mFloatingButton;
    }

    public void setForceDeviceForAppSoundOutput(int uid, int device) {
        if (device < 0 || device > 2) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        int output = 0;
        if (device == 1) {
            output = 2;
        } else if (device == 2) {
            output = 8;
        }
        setMultiSoundTargetDevice(uid, output);
    }

    public void ignoreAudioFocusForApp(int uid, boolean on) {
        setSoundAssistantParam(
                "ignore_audio_focus="
                        + (on ? "1" : "0")
                        + NavigationBarInflaterView.GRAVITY_SEPARATOR
                        + UID_FOR_SOUNDASSISTANT
                        + "="
                        + uid);
    }

    public int getUidIgnoredAudioFocus() {
        String result = getSoundAssistantParam(IGNORE_AUDIO_FOCUS);
        try {
            int uid = Integer.valueOf(result).intValue();
            return uid;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String[] getRecommandedPackagesForSoundAssistant() {
        IAudioService service = getService();
        try {
            return service.getSelectedAppList();
        } catch (RemoteException e) {
            Log.e(TAG, "getRecommendedPackagesForSoundAssistant " + e.getMessage());
            return null;
        }
    }

    private ArrayList<Integer> getIntegerArrayFromString(String strUids) {
        if (TextUtils.isEmpty(strUids)) {
            return null;
        }
        ArrayList<Integer> uidList = new ArrayList<>();
        StringTokenizer token =
                new StringTokenizer(strUids, NavigationBarInflaterView.GRAVITY_SEPARATOR);
        while (token.hasMoreTokens()) {
            String strUid = token.nextToken();
            if (strUid.length() != 0) {
                try {
                    uidList.add(Integer.valueOf(strUid));
                } catch (NumberFormatException e) {
                }
            }
        }
        if (uidList.size() == 0) {
            return null;
        }
        return uidList;
    }

    public void setVolumeKeyMode(int mode) {
        if (mode != 0 && mode != 1) {
            Log.e(TAG, "Invalid mode");
        } else {
            String param = "adjust_media_volume_only=" + mode;
            setSoundAssistantProperty(param);
        }
    }

    public int getVolumeKeyMode() {
        String ret = getSoundAssistantProperty(ADJUST_MEDIA_ONLY);
        int mode = 0;
        try {
            mode = Integer.valueOf(ret).intValue();
        } catch (NumberFormatException e) {
        }
        return mode == 1 ? 1 : 0;
    }

    public ArrayList<Integer> getApplicationUidListUsingAudio() {
        String result = getSoundAssistantProperty(USING_AUDIO_UIDS);
        return getIntegerArrayFromString(result);
    }

    public void setApplicationVolume(int uid, int ratio) {
        this.mAudioManager.setAppVolume(uid, ratio);
    }

    public int getApplicationVolume(int uid) {
        return this.mAudioManager.getAppVolume(uid);
    }

    public void setApplicationMute(int uid, boolean shouldMute) {
        this.mAudioManager.setAppMute(uid, shouldMute);
    }

    public boolean isApplicationMute(int uid) {
        return this.mAudioManager.isAppMute(uid);
    }

    public void setVolumeMode(int i, boolean z) {
        if (!VOLUME_MODE_PREDICATE.test(i)) {
            Log.e(TAG, "Invalid mode.");
        } else {
            setSoundAssistantParam(VOLUME_MODE_KEY[i] + "=" + (z ? 1 : 0));
        }
    }

    public boolean getVolumeMode(int mode) {
        if (!VOLUME_MODE_PREDICATE.test(mode)) {
            Log.e(TAG, "Invalid mode.");
            return false;
        }
        String ret = getSoundAssistantProperty(VOLUME_MODE_KEY[mode]);
        int res = 0;
        try {
            res = Integer.parseInt(ret);
        } catch (NumberFormatException e) {
        }
        return res == 1;
    }

    public boolean enableSelfieStickMode() {
        return false;
    }

    public boolean isSelfieStickModeEnabled() {
        return false;
    }

    public int getAudioFrameworkVersion() {
        String ret = getSoundAssistantProperty("version");
        try {
            int version = Integer.valueOf(ret).intValue();
            return version;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String getSoundAssistantProperty(String param) {
        String newParam = "sound_assistant;" + param;
        return AudioManager.getAudioServiceConfig(newParam);
    }

    public void setSoundAssistantProperty(String param) {
        String newParam = "sound_assistant=1;" + param;
        AudioManager.setAudioServiceConfig(newParam);
    }

    public boolean isMultiSoundOn() {
        return this.mAudioManager.isMultiSoundOn();
    }

    public void setMultiSoundOn(boolean on) {
        this.mAudioManager.setMultiSoundOn(on);
    }

    public void setMultiSoundDevice(int uid, int deviceCategory) {
        if (deviceCategory < 0 || deviceCategory > 2) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        int output = 0;
        if (deviceCategory == 1) {
            output = 2;
        } else if (deviceCategory == 2) {
            output = 8;
        }
        setMultiSoundTargetDevice(uid, output);
    }

    public void setMultiSoundTargetDevice(int uid, int device) {
        this.mAudioManager.setAppDevice(uid, device, false);
    }

    public int getMultiSoundDevice() {
        return AudioDeviceInfo.convertInternalDeviceToDeviceType(
                this.mAudioManager.semGetPinDevice());
    }

    public void addToMultiSoundSupportedList(String packageName) {
        IAudioService service = getService();
        try {
            service.addPackage(0, packageName);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling addPackage", e);
        }
    }

    public void removeFromMultiSoundSupportedList(String packageName) {
        IAudioService service = getService();
        try {
            service.removePackageForName(packageName);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling removePackageForName", e);
        }
    }

    public boolean isMultiSoundSupportedPackage(String packageName) {
        IAudioService service = getService();
        try {
            return service.isAlreadyInDB(packageName);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling isAlreadyInDB", e);
            return false;
        }
    }

    public boolean isPredefinedMultiSoundSupportedPackage(String packageName) {
        IAudioService service = getService();
        try {
            return service.isInAllowedList(packageName);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling isInAllowedList", e);
            return false;
        }
    }

    public int getMultiSoundDeviceVolume(int streamType) {
        if (!isMultiSoundOn()) {
            Log.e(TAG, "Multisound is disabled");
            return -1;
        }
        return this.mAudioManager.getFineVolume(streamType, this.mAudioManager.semGetPinDevice());
    }

    public boolean setMultiSoundDeviceVolume(int streamType, int index, int flags) {
        if (!isMultiSoundOn()) {
            Log.e(TAG, "Multisound is disabled");
            return false;
        }
        this.mAudioManager.setFineVolume(
                streamType, index, flags, this.mAudioManager.semGetPinDevice());
        return true;
    }

    public boolean setDefaultSoundOutputDevice(int deviceCategory) {
        boolean z;
        int i = 0;
        if (deviceCategory != 1 && deviceCategory != 2) {
            return false;
        }
        int curDevice =
                AudioDeviceInfo.convertDeviceTypeToInternalDevice(
                        this.mAudioManager.semGetCurrentDeviceType());
        AudioDeviceInfo[] outDevicesInfo = this.mAudioManager.getDevices(2);
        int forceDevice = curDevice;
        String forceAddr = "";
        if (deviceCategory == 1) {
            int[] priorityDeviceOrder = {32768, 8, 4, 67108864, 8192, 16384, 4096, 1024, 2048, 2};
            int length = priorityDeviceOrder.length;
            int i2 = 0;
            while (i2 < length) {
                int order = priorityDeviceOrder[i2];
                boolean found = false;
                int length2 = outDevicesInfo.length;
                int i3 = i;
                while (true) {
                    if (i3 >= length2) {
                        break;
                    }
                    AudioDeviceInfo connectedDevice = outDevicesInfo[i3];
                    if ((connectedDevice.getType() == 25
                                    && !"0".equals(connectedDevice.getAddress()))
                            || connectedDevice.getDeviceId() != order) {
                        i3++;
                    } else {
                        found = true;
                        forceDevice = order;
                        forceAddr = connectedDevice.getAddress();
                        break;
                    }
                }
                if (found) {
                    break;
                }
                i2++;
                i = 0;
            }
            z = false;
        } else if (deviceCategory != 2) {
            z = false;
        } else {
            boolean found2 = false;
            int length3 = outDevicesInfo.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length3) {
                    break;
                }
                AudioDeviceInfo connectedDevice2 = outDevicesInfo[i4];
                if (connectedDevice2.getDeviceId() != 128) {
                    i4++;
                } else {
                    found2 = true;
                    forceDevice = 128;
                    forceAddr = connectedDevice2.getAddress();
                    break;
                }
            }
            if (!found2) {
                return false;
            }
            z = false;
        }
        if (this.mAudioManager.setDeviceToForceByUser(forceDevice, forceAddr, z) == 0) {
            return true;
        }
        return z;
    }

    public void setMediaVolumeInterval(int volumeInterval) {
        if (volumeInterval < 1 || volumeInterval > 10) {
            Log.e(TAG, "Invalid index");
        } else {
            String param = "media_volume_step_index=" + volumeInterval;
            setSoundAssistantProperty(param);
        }
    }

    public int getMediaVolumeInterval() {
        String ret = getSoundAssistantProperty(MEDIA_VOLUME_STEP_INDEX);
        try {
            int index = Integer.valueOf(ret).intValue();
            return index;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean setMediaVolumeSteps(int[] volumeStep) {
        IAudioService service = getService();
        try {
            return service.setMediaVolumeSteps(volumeStep);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int[] getMediaVolumeSteps() {
        IAudioService service = getService();
        try {
            return service.getMediaVolumeSteps();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSoundSettingEventBroadcastIntent(int eventType, PendingIntent broadcastIntent) {
        if (broadcastIntent == null) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        IAudioService service = getService();
        try {
            service.setSoundSettingEventBroadcastIntent(eventType, broadcastIntent);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setAppDevice", e);
        }
    }

    public void setDeviceForStream(int stream, int device) {
        int currentStream;
        int newStreams;
        if (stream != 2 && stream != 5 && stream != 4) {
            Log.e(TAG, "Invalid parameter");
            return;
        }
        if (device != 0 && device != 3) {
            Log.e(TAG, "Invalid parameter");
            return;
        }
        String result = getSoundAssistantParam(SETTING_RINGTONE_THROUGH_HEADSET_ONLY);
        int prevStreams = 0;
        try {
            prevStreams = Integer.valueOf(result).intValue();
        } catch (NumberFormatException e) {
        }
        boolean separateStream = isSeparateStreamForHeadsetOnly();
        if (separateStream) {
            prevStreams &= -5;
            if (stream == 2) {
                currentStream = 1;
            } else if (stream == 5) {
                currentStream = 32;
            } else {
                currentStream = 16;
            }
        } else {
            currentStream = 49;
        }
        if (device == 3) {
            newStreams = prevStreams | currentStream;
        } else {
            int newStreams2 = ~currentStream;
            newStreams = newStreams2 & prevStreams;
        }
        String param = "ring_through_headset=" + newStreams;
        setSoundAssistantProperty(param);
    }

    public int getDeviceForStream(int stream) {
        int stream2;
        if (stream != 2 && stream != 5 && stream != 4) {
            Log.e(TAG, "Invalid parameter");
        }
        String result = getSoundAssistantParam(SETTING_RINGTONE_THROUGH_HEADSET_ONLY);
        int prevStreams = 0;
        try {
            prevStreams = Integer.valueOf(result).intValue();
        } catch (NumberFormatException e) {
        }
        boolean separateStream = isSeparateStreamForHeadsetOnly();
        if (!separateStream) {
            return (prevStreams & 1) != 0 ? 3 : 0;
        }
        if (stream == 2) {
            stream2 = 1;
        } else if (stream == 5) {
            stream2 = 32;
        } else {
            stream2 = 16;
        }
        return (prevStreams & stream2) != 0 ? 3 : 0;
    }

    private String getSoundAssistantParam(String param) {
        String newParam = "sound_assistant;" + param;
        return AudioManager.getAudioServiceConfig(newParam);
    }

    private void setSoundAssistantParam(String param) {
        String newParam = "sound_assistant=1;" + param;
        AudioManager.setAudioServiceConfig(newParam);
    }

    private boolean isSeparateStreamForHeadsetOnly() {
        try {
            ApplicationInfo info =
                    this.mApplicationContext
                            .getPackageManager()
                            .getApplicationInfo(this.mApplicationContext.getPackageName(), 128);
            if (info.metaData == null) {
                return false;
            }
            boolean separateStream = info.metaData.getBoolean("separate_stream", false);
            return separateStream;
        } catch (Exception e) {
            return false;
        }
    }

    public void setFastAudioOpenMode() {
        setFastAudioOpenMode(false);
    }

    public void setFastAudioOpenMode(boolean longOpen) {
        synchronized (sLock) {
            if (sIsRunning) {
                return;
            }
            Log.i(TAG, "setFastAudioOpenMode: play sound for quick audio path opening " + longOpen);
            sIsRunning = true;
            new Thread(new FastTrackPlayerRunnable(longOpen)).start();
        }
    }

    private static class FastTrackPlayerRunnable implements Runnable {
        final int mPlayTimeMs;

        FastTrackPlayerRunnable(boolean longOpen) {
            this.mPlayTimeMs = longOpen ? 300 : 100;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                playDummyAudio();
            } catch (UnsupportedOperationException e) {
                Log.e(SemSoundAssistantManager.TAG, "Track fail", e);
            }
            SemSoundAssistantManager.sIsRunning = false;
        }

        private void playDummyAudio() throws UnsupportedOperationException {
            int bufferSize = this.mPlayTimeMs * 192;
            byte[] dummyData = new byte[bufferSize];
            for (int index = 0; index < bufferSize; index++) {
                dummyData[index] = 0;
            }
            AudioTrack track =
                    new AudioTrack.Builder()
                            .setAudioAttributes(
                                    new AudioAttributes.Builder()
                                            .setContentType(4)
                                            .setUsage(13)
                                            .addTag(AudioTag.TAG_FAST_AUDIO_PRE_OPEN)
                                            .build())
                            .setPerformanceMode(1)
                            .setAudioFormat(
                                    new AudioFormat.Builder()
                                            .setEncoding(2)
                                            .setSampleRate(48000)
                                            .setChannelMask(12)
                                            .build())
                            .setBufferSizeInBytes(bufferSize)
                            .setTransferMode(0)
                            .build();
            track.setVolume(0.0f);
            track.write(dummyData, 0, bufferSize, 0);
            int count = this.mPlayTimeMs / 100;
            while (true) {
                int count2 = count - 1;
                if (count > 0) {
                    track.play();
                    SemSoundAssistantManager.sleep(100L);
                    track.pause();
                    SemSoundAssistantManager.sleep(2500L);
                    count = count2;
                } else {
                    track.stop();
                    track.release();
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    static /* synthetic */ void lambda$static$0(
            String packageName, MediaSession.Token sessionToken) {
        synchronized (mLock) {
            for (OnMediaKeyEventSessionChangedListener callback :
                    sMediaKeySessionChangedCallbacks) {
                callback.onMediaKeyEventSessionChanged(packageName, sessionToken);
            }
        }
    }

    public void addOnMediaKeyEventSessionChangedListener(
            OnMediaKeyEventSessionChangedListener listener) {
        Executor executor;
        Objects.requireNonNull(listener, "listener shouldn't be null");
        synchronized (mLock) {
            if (sMediaKeySessionChangedCallbacks.contains(listener)) {
                Log.w(TAG, "Already added : " + listener);
                return;
            }
            if (sMediaKeySessionChangedCallbacks.size() == 0) {
                Looper myLooper = Looper.myLooper();
                Looper looper = myLooper;
                if (myLooper == null) {
                    looper = Looper.getMainLooper();
                }
                if (looper != null) {
                    executor = new HandlerExecutor(new Handler(looper));
                } else {
                    executor = Executors.newSingleThreadExecutor();
                }
                MediaSessionManager manager =
                        (MediaSessionManager)
                                getContext().getSystemService(Context.MEDIA_SESSION_SERVICE);
                manager.addOnMediaKeyEventSessionChangedListener(
                        executor, sMediaKeySessionChangedListener);
            }
            sMediaKeySessionChangedCallbacks.add(listener);
        }
    }

    public void removeOnMediaKeyEventSessionChangedListener(
            OnMediaKeyEventSessionChangedListener listener) {
        synchronized (mLock) {
            if (!sMediaKeySessionChangedCallbacks.contains(listener)) {
                Log.w(TAG, "Invalid listener : " + listener);
                return;
            }
            sMediaKeySessionChangedCallbacks.remove(listener);
            if (sMediaKeySessionChangedCallbacks.size() == 0) {
                MediaSessionManager manager =
                        (MediaSessionManager)
                                getContext().getSystemService(Context.MEDIA_SESSION_SERVICE);
                manager.removeOnMediaKeyEventSessionChangedListener(
                        sMediaKeySessionChangedListener);
            }
        }
    }

    public void setMicInputControlMode(int mode) {
        if (!sMicModeParamTable.containsKey(Integer.valueOf(mode))) {
            Log.w(TAG, "attempt to call setMicInputControlMode() invalid mode.");
            return;
        }
        String caller = getContext().getOpPackageName();
        Log.d(TAG, "setMicInputControlMode mode=" + mode + ", caller=" + caller);
        if (mode != 0) {
            String standardParam = "l_mic_input_control_mode=0";
            if (mode == 3 || mode == 4) {
                standardParam = "l_call_nc_booster_enable=0";
            }
            sMicModeParamTable.put(0, standardParam);
        }
        AudioManager.setAudioServiceConfig(sMicModeParamTable.get(Integer.valueOf(mode)));
    }

    public void setVoipExtraVolumeMode(boolean onOff) {
        AudioParameter param =
                new AudioParameter.Builder()
                        .setParam(AudioParameter.SEC_LOCAL_CALL_VOIP_EXTRA_VOLUME_ENABLE, onOff)
                        .build();
        AudioManager.setAudioServiceConfig(param.toString());
    }

    public void setVoipAntiHowlingMode(boolean onOff) {
        AudioParameter param =
                new AudioParameter.Builder()
                        .setParam(AudioParameter.SEC_LOCAL_CALL_VOIP_EXTRA_VOLUME_ENABLE, onOff)
                        .build();
        AudioManager.setAudioServiceConfig(param.toString());
    }

    public void sendMediaKeyEvent(String packageName, KeyEvent keyEvent) {
        Objects.requireNonNull(packageName, "packageName shouldn't be null");
        Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.addFlags(268435456);
        mediaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
        mediaButtonIntent.putExtra(
                "android.intent.extra.PACKAGE_NAME", getContext().getPackageName());
        mediaButtonIntent.setPackage(packageName);
        BroadcastOptions options = BroadcastOptions.makeBasic();
        options.setTemporaryAppAllowlist(10000L, 0, 313, "");
        options.setBackgroundActivityStartsAllowed(true);
        getContext().sendBroadcast(mediaButtonIntent, (String) null, options.toBundle());
    }
}
