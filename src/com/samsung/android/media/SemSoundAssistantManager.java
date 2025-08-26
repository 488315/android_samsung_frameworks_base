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
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import com.google.android.collect.Sets;
import com.samsung.android.media.AudioParameter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.IntPredicate;

/* loaded from: classes6.dex */
public class SemSoundAssistantManager {
    public static final String ACTION_SOUND_EVENT_CHANGED = "com.samsung.android.intent.action.SOUND_EVENT";
    public static final String ADJUST_MEDIA_ONLY = "adjust_media_volume_only";
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
    public static final String IGNORE_DUCKING_BY_ALL_APPS = "ignore_ducking_by_all_apps";
    public static final String IGNORE_DUCKING_BY_NAVIGATION = "ignore_ducking";
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
    public static final String MUTE_MEDIA_BY_VIBRATE_OR_SILENT_MODE = "mute_media_by_vibrate_or_silent_mode";
    public static final String NO_FADEOUT_FROM_AUDIOFOCUS = "NO_FADEOUT_FROM_AUDIOFOCUS";
    public static final String NO_MUTE_IN_CALL = "NO_MUTE_IN_CALL";
    public static final String PARAMETER_PREFIX = "sound_assistant";
    public static final String REMOVE_APP_VOLUME = "remove_app_volume";
    public static final String SETTING_RINGTONE_THROUGH_HEADSET_ONLY = "ring_through_headset";
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
    protected static final Set<Integer> VOLUME_MODE_ALL;
    protected static final String[] VOLUME_MODE_KEY;
    public static final IntPredicate VOLUME_MODE_PREDICATE;
    public static final int VOLUME_STAR_DISABLE = 101;
    public static final int VOLUME_STAR_ENABLE = 100;
    public static final String VOLUME_STAR_ENABLE_PARAM = "volumestar_enable";
    private static final Object mLock;
    private static boolean sIsRunning;
    private static final Object sLock;
    private static final List<OnMediaKeyEventSessionChangedListener> sMediaKeySessionChangedCallbacks;
    private static final MediaSessionManager.OnMediaKeyEventSessionChangedListener sMediaKeySessionChangedListener;
    static final ArrayMap<Integer, String> sMicModeParamTable;
    private static IAudioService sService;
    private Context mApplicationContext;
    private AudioManager mAudioManager;
    private boolean mFloatingButton;
    private Context mOriginalContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MicInputControlMode {
    }

    public interface OnMediaKeyEventSessionChangedListener {
        void onMediaKeyEventSessionChanged(String str, MediaSession.Token token);
    }

    static {
        final HashSet hashSetNewHashSet = Sets.newHashSet(1, 2);
        VOLUME_MODE_ALL = hashSetNewHashSet;
        VOLUME_MODE_KEY = new String[]{"", ADJUST_MEDIA_ONLY, MUTE_MEDIA_BY_VIBRATE_OR_SILENT_MODE};
        Objects.requireNonNull(hashSetNewHashSet);
        VOLUME_MODE_PREDICATE = new IntPredicate() { // from class: com.samsung.android.media.SemSoundAssistantManager$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return hashSetNewHashSet.contains(Integer.valueOf(i));
            }
        };
        sIsRunning = false;
        sLock = new Object();
        mLock = new Object();
        sMediaKeySessionChangedCallbacks = new ArrayList();
        sMediaKeySessionChangedListener = new MediaSessionManager.OnMediaKeyEventSessionChangedListener() { // from class: com.samsung.android.media.SemSoundAssistantManager$$ExternalSyntheticLambda1
            @Override // android.media.session.MediaSessionManager.OnMediaKeyEventSessionChangedListener
            public final void onMediaKeyEventSessionChanged(String str, MediaSession.Token token) {
                SemSoundAssistantManager.lambda$static$0(str, token);
            }
        };
        ArrayMap<Integer, String> arrayMap = new ArrayMap<>();
        sMicModeParamTable = arrayMap;
        arrayMap.put(0, "l_mic_input_control_mode=0");
        arrayMap.put(1, "l_mic_input_control_mode=1");
        arrayMap.put(2, "l_mic_input_control_mode=2");
        arrayMap.put(3, "l_call_nc_booster_enable=1");
        arrayMap.put(4, "l_call_nc_booster_enable=2");
        arrayMap.put(5, "l_mic_input_control_mode_2mic=0");
        arrayMap.put(6, "l_mic_input_control_mode_2mic=1");
        arrayMap.put(7, "l_mic_input_control_mode_2mic=2");
        arrayMap.put(100, "l_mic_input_control_mode=100");
    }

    public SemSoundAssistantManager(Context context) {
        setContext(context);
        this.mAudioManager = (AudioManager) getContext().getSystemService("audio");
    }

    private void setContext(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mApplicationContext = applicationContext;
        if (applicationContext != null) {
            this.mOriginalContext = null;
        } else {
            this.mOriginalContext = context;
        }
    }

    private Context getContext() {
        if (this.mApplicationContext == null) {
            setContext(this.mOriginalContext);
        }
        Context context = this.mApplicationContext;
        return context != null ? context : this.mOriginalContext;
    }

    private static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        IAudioService iAudioServiceAsInterface = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        sService = iAudioServiceAsInterface;
        return iAudioServiceAsInterface;
    }

    public void initApplicationVolume(int i) {
        setSoundAssistantProperty("remove_app_volume=" + i);
    }

    public void adjustSoundBalance(int i) {
        if (i < 0 || i > 100) {
            throw new IllegalArgumentException("Bad ratio value");
        }
        setSoundAssistantParam("sound_balance=" + i);
    }

    public void forceMonoSound(boolean z) {
        setSoundAssistantParam("mono_sound=" + (z ? 1 : 0));
    }

    public void activateFloatingButton(boolean z) {
        this.mFloatingButton = z;
    }

    public boolean isFloatingButtonActivated() {
        return this.mFloatingButton;
    }

    public void setForceDeviceForAppSoundOutput(int i, int i2) {
        if (i2 >= 0) {
            if (i2 <= 2) {
                setMultiSoundTargetDevice(i, i2 != 1 ? i2 == 2 ? 8 : 0 : 2);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid parameter");
    }

    public void ignoreAudioFocusForApp(int i, boolean z) {
        StringBuilder sb = new StringBuilder("ignore_audio_focus=");
        sb.append(z ? "1" : "0");
        sb.append(";uid_for_soundassistant=");
        sb.append(i);
        setSoundAssistantParam(sb.toString());
    }

    public int getUidIgnoredAudioFocus() {
        try {
            return Integer.valueOf(getSoundAssistantParam(IGNORE_AUDIO_FOCUS)).intValue();
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public String[] getRecommandedPackagesForSoundAssistant() {
        try {
            return getService().getSelectedAppList();
        } catch (RemoteException e) {
            Log.e(TAG, "getRecommendedPackagesForSoundAssistant " + e.getMessage());
            return null;
        }
    }

    private ArrayList<Integer> getIntegerArrayFromString(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(str, NavigationBarInflaterView.GRAVITY_SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (strNextToken.length() != 0) {
                try {
                    arrayList.add(Integer.valueOf(strNextToken));
                } catch (NumberFormatException unused) {
                }
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return arrayList;
    }

    public void setApplicationVolume(int i, int i2) {
        this.mAudioManager.setAppVolume(i, i2);
    }

    public int getApplicationVolume(int i) {
        return this.mAudioManager.getAppVolume(i);
    }

    public void setApplicationMute(int i, boolean z) {
        this.mAudioManager.setAppMute(i, z);
    }

    public boolean isApplicationMute(int i) {
        return this.mAudioManager.isAppMute(i);
    }

    public void setVolumeMode(int i, boolean z) {
        if (!VOLUME_MODE_PREDICATE.test(i)) {
            Log.e(TAG, "Invalid mode.");
            return;
        }
        setSoundAssistantParam(VOLUME_MODE_KEY[i] + "=" + (z ? 1 : 0));
    }

    public boolean getVolumeMode(int i) throws NumberFormatException {
        int i2;
        if (!VOLUME_MODE_PREDICATE.test(i)) {
            Log.e(TAG, "Invalid mode.");
            return false;
        }
        try {
            i2 = Integer.parseInt(getSoundAssistantProperty(VOLUME_MODE_KEY[i]));
        } catch (NumberFormatException unused) {
            i2 = 0;
        }
        return i2 == 1;
    }

    public String getSoundAssistantProperty(String str) {
        return AudioManager.getAudioServiceConfig("sound_assistant;" + str);
    }

    public void setSoundAssistantProperty(String str) {
        AudioManager.setAudioServiceConfig("sound_assistant=1;" + str);
    }

    public boolean isMultiSoundOn() {
        return this.mAudioManager.isMultiSoundOn();
    }

    public void setMultiSoundOn(boolean z) {
        this.mAudioManager.setMultiSoundOn(z);
    }

    public void setMultiSoundDevice(int i, int i2) {
        if (i2 >= 0) {
            if (i2 <= 2) {
                setMultiSoundTargetDevice(i, i2 != 1 ? i2 == 2 ? 8 : 0 : 2);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid parameter");
    }

    public void setMultiSoundTargetDevice(int i, int i2) {
        this.mAudioManager.setAppDevice(i, i2, false);
    }

    public int getMultiSoundDevice() {
        return AudioDeviceInfo.convertInternalDeviceToDeviceType(this.mAudioManager.semGetPinDevice());
    }

    public void addToMultiSoundSupportedList(String str) {
        try {
            getService().addPackage(0, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling addPackage", e);
        }
    }

    public void removeFromMultiSoundSupportedList(String str) {
        try {
            getService().removePackageForName(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling removePackageForName", e);
        }
    }

    public boolean isMultiSoundSupportedPackage(String str) {
        try {
            return getService().isAlreadyInDB(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling isAlreadyInDB", e);
            return false;
        }
    }

    public boolean isPredefinedMultiSoundSupportedPackage(String str) {
        try {
            return getService().isInAllowedList(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling isInAllowedList", e);
            return false;
        }
    }

    public int getMultiSoundDeviceVolume(int i) {
        if (!isMultiSoundOn()) {
            Log.e(TAG, "Multisound is disabled");
            return -1;
        }
        AudioManager audioManager = this.mAudioManager;
        return audioManager.getFineVolume(i, audioManager.semGetPinDevice());
    }

    public boolean setMultiSoundDeviceVolume(int i, int i2, int i3) {
        if (!isMultiSoundOn()) {
            Log.e(TAG, "Multisound is disabled");
            return false;
        }
        AudioManager audioManager = this.mAudioManager;
        audioManager.setFineVolume(i, i2, i3, audioManager.semGetPinDevice());
        return true;
    }

    public boolean setDefaultSoundOutputDevice(int i) {
        boolean z;
        boolean z2;
        if (i != 1 && i != 2) {
            return false;
        }
        int iConvertDeviceTypeToInternalDevice = AudioDeviceInfo.convertDeviceTypeToInternalDevice(this.mAudioManager.semGetCurrentDeviceType());
        AudioDeviceInfo[] devices = this.mAudioManager.getDevices(2);
        String address = "";
        if (i == 1) {
            int[] iArr = {32768, 8, 4, 67108864, 8192, 16384, 4096, 1024, 2048, 2};
            for (int i2 = 0; i2 < 10; i2++) {
                int i3 = iArr[i2];
                int length = devices.length;
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        z2 = false;
                        break;
                    }
                    AudioDeviceInfo audioDeviceInfo = devices[i4];
                    if ((audioDeviceInfo.getType() != 25 || "0".equals(audioDeviceInfo.getAddress())) && audioDeviceInfo.getDeviceId() == i3) {
                        address = audioDeviceInfo.getAddress();
                        iConvertDeviceTypeToInternalDevice = i3;
                        z2 = true;
                        break;
                    }
                    i4++;
                }
                if (z2) {
                    break;
                }
            }
        } else if (i == 2) {
            int length2 = devices.length;
            int i5 = 0;
            while (true) {
                if (i5 >= length2) {
                    z = false;
                    break;
                }
                AudioDeviceInfo audioDeviceInfo2 = devices[i5];
                if (audioDeviceInfo2.getDeviceId() == 128) {
                    address = audioDeviceInfo2.getAddress();
                    z = true;
                    iConvertDeviceTypeToInternalDevice = 128;
                    break;
                }
                i5++;
            }
            if (!z) {
                return false;
            }
        }
        return this.mAudioManager.setDeviceToForceByUser(iConvertDeviceTypeToInternalDevice, address, false) == 0;
    }

    public void setMediaVolumeInterval(int i) {
        if (i < 1 || i > 10) {
            Log.e(TAG, "Invalid index");
            return;
        }
        setSoundAssistantProperty("media_volume_step_index=" + i);
    }

    public int getMediaVolumeInterval() {
        try {
            return Integer.valueOf(getSoundAssistantProperty(MEDIA_VOLUME_STEP_INDEX)).intValue();
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public boolean setMediaVolumeSteps(int[] iArr) {
        try {
            return getService().setMediaVolumeSteps(iArr);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int[] getMediaVolumeSteps() {
        try {
            return getService().getMediaVolumeSteps();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSoundSettingEventBroadcastIntent(int i, PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            throw new IllegalArgumentException("Invalid parameter");
        }
        try {
            getService().setSoundSettingEventBroadcastIntent(i, pendingIntent);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setAppDevice", e);
        }
    }

    public void setDeviceForStream(int i, int i2) {
        int iIntValue;
        int i3;
        if (i != 2 && i != 5 && i != 4) {
            Log.e(TAG, "Invalid parameter");
            return;
        }
        if (i2 != 0 && i2 != 3) {
            Log.e(TAG, "Invalid parameter");
            return;
        }
        try {
            iIntValue = Integer.valueOf(getSoundAssistantParam(SETTING_RINGTONE_THROUGH_HEADSET_ONLY)).intValue();
        } catch (NumberFormatException unused) {
            iIntValue = 0;
        }
        if (isSeparateStreamForHeadsetOnly()) {
            iIntValue &= -5;
            i3 = i == 2 ? 1 : i == 5 ? 32 : 16;
        } else {
            i3 = 49;
        }
        setSoundAssistantProperty("ring_through_headset=" + (i2 == 3 ? i3 | iIntValue : (~i3) & iIntValue));
    }

    public int getDeviceForStream(int i) {
        int iIntValue;
        if (i != 2 && i != 5 && i != 4) {
            Log.e(TAG, "Invalid parameter");
        }
        try {
            iIntValue = Integer.valueOf(getSoundAssistantParam(SETTING_RINGTONE_THROUGH_HEADSET_ONLY)).intValue();
        } catch (NumberFormatException unused) {
            iIntValue = 0;
        }
        if (isSeparateStreamForHeadsetOnly()) {
            return (iIntValue & (i != 2 ? i == 5 ? 32 : 16 : 1)) != 0 ? 3 : 0;
        }
        return (iIntValue & 1) != 0 ? 3 : 0;
    }

    private String getSoundAssistantParam(String str) {
        return AudioManager.getAudioServiceConfig("sound_assistant;" + str);
    }

    private void setSoundAssistantParam(String str) {
        AudioManager.setAudioServiceConfig("sound_assistant=1;" + str);
    }

    private boolean isSeparateStreamForHeadsetOnly() {
        try {
            ApplicationInfo applicationInfo = this.mApplicationContext.getPackageManager().getApplicationInfo(this.mApplicationContext.getPackageName(), 128);
            if (applicationInfo.metaData != null) {
                return applicationInfo.metaData.getBoolean("separate_stream", false);
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public void setFastAudioOpenMode() {
        setFastAudioOpenMode(false);
    }

    public void setFastAudioOpenMode(boolean z) {
        synchronized (sLock) {
            if (sIsRunning) {
                return;
            }
            Log.i(TAG, "setFastAudioOpenMode: play sound for quick audio path opening " + z);
            sIsRunning = true;
            new Thread(new FastTrackPlayerRunnable(z)).start();
        }
    }

    private static class FastTrackPlayerRunnable implements Runnable {
        final int mPlayTimeMs;

        FastTrackPlayerRunnable(boolean z) {
            this.mPlayTimeMs = z ? 300 : 100;
        }

        @Override // java.lang.Runnable
        public void run() throws IllegalStateException, InterruptedException {
            try {
                playDummyAudio();
            } catch (UnsupportedOperationException e) {
                Log.e(SemSoundAssistantManager.TAG, "Track fail", e);
            }
            SemSoundAssistantManager.sIsRunning = false;
        }

        private void playDummyAudio() throws UnsupportedOperationException, IllegalStateException, InterruptedException {
            int i = this.mPlayTimeMs * 192;
            byte[] bArr = new byte[i];
            for (int i2 = 0; i2 < i; i2++) {
                bArr[i2] = 0;
            }
            AudioTrack audioTrackBuild = new AudioTrack.Builder().setAudioAttributes(new AudioAttributes.Builder().setContentType(4).setUsage(13).addTag(AudioTag.TAG_FAST_AUDIO_PRE_OPEN).build()).setPerformanceMode(1).setAudioFormat(new AudioFormat.Builder().setEncoding(2).setSampleRate(48000).setChannelMask(12).build()).setBufferSizeInBytes(i).setTransferMode(0).build();
            audioTrackBuild.setVolume(0.0f);
            audioTrackBuild.write(bArr, 0, i, 0);
            int i3 = this.mPlayTimeMs / 100;
            while (true) {
                int i4 = i3 - 1;
                if (i3 > 0) {
                    audioTrackBuild.play();
                    SemSoundAssistantManager.sleep(100L);
                    audioTrackBuild.pause();
                    SemSoundAssistantManager.sleep(2500L);
                    i3 = i4;
                } else {
                    audioTrackBuild.stop();
                    audioTrackBuild.release();
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sleep(long j) throws InterruptedException {
        try {
            Thread.sleep(j);
        } catch (InterruptedException unused) {
        }
    }

    static /* synthetic */ void lambda$static$0(String str, MediaSession.Token token) {
        synchronized (mLock) {
            Iterator<OnMediaKeyEventSessionChangedListener> it = sMediaKeySessionChangedCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onMediaKeyEventSessionChanged(str, token);
            }
        }
    }

    public void addOnMediaKeyEventSessionChangedListener(OnMediaKeyEventSessionChangedListener onMediaKeyEventSessionChangedListener) {
        Executor executorNewSingleThreadExecutor;
        Objects.requireNonNull(onMediaKeyEventSessionChangedListener, "listener shouldn't be null");
        synchronized (mLock) {
            List<OnMediaKeyEventSessionChangedListener> list = sMediaKeySessionChangedCallbacks;
            if (list.contains(onMediaKeyEventSessionChangedListener)) {
                Log.w(TAG, "Already added : " + onMediaKeyEventSessionChangedListener);
                return;
            }
            if (list.size() == 0) {
                Looper looperMyLooper = Looper.myLooper();
                if (looperMyLooper == null) {
                    looperMyLooper = Looper.getMainLooper();
                }
                if (looperMyLooper != null) {
                    executorNewSingleThreadExecutor = new HandlerExecutor(new Handler(looperMyLooper));
                } else {
                    executorNewSingleThreadExecutor = Executors.newSingleThreadExecutor();
                }
                ((MediaSessionManager) getContext().getSystemService(Context.MEDIA_SESSION_SERVICE)).addOnMediaKeyEventSessionChangedListener(executorNewSingleThreadExecutor, sMediaKeySessionChangedListener);
            }
            list.add(onMediaKeyEventSessionChangedListener);
        }
    }

    public void removeOnMediaKeyEventSessionChangedListener(OnMediaKeyEventSessionChangedListener onMediaKeyEventSessionChangedListener) {
        synchronized (mLock) {
            List<OnMediaKeyEventSessionChangedListener> list = sMediaKeySessionChangedCallbacks;
            if (!list.contains(onMediaKeyEventSessionChangedListener)) {
                Log.w(TAG, "Invalid listener : " + onMediaKeyEventSessionChangedListener);
            } else {
                list.remove(onMediaKeyEventSessionChangedListener);
                if (list.size() == 0) {
                    ((MediaSessionManager) getContext().getSystemService(Context.MEDIA_SESSION_SERVICE)).removeOnMediaKeyEventSessionChangedListener(sMediaKeySessionChangedListener);
                }
            }
        }
    }

    public void setMicInputControlMode(int i) {
        String str;
        ArrayMap<Integer, String> arrayMap = sMicModeParamTable;
        if (!arrayMap.containsKey(Integer.valueOf(i))) {
            Log.w(TAG, "attempt to call setMicInputControlMode() invalid mode.");
            return;
        }
        Log.d(TAG, "setMicInputControlMode mode=" + i + ", caller=" + getContext().getOpPackageName());
        if (i != 0) {
            if (i != 3 && i != 4) {
                str = "l_mic_input_control_mode=0";
            } else {
                str = "l_call_nc_booster_enable=0";
            }
            arrayMap.put(0, str);
        }
        AudioManager.setAudioServiceConfig(arrayMap.get(Integer.valueOf(i)));
    }

    public void setVoipExtraVolumeMode(boolean z) {
        AudioManager.setAudioServiceConfig(new AudioParameter.Builder().setParam(AudioParameter.SEC_LOCAL_CALL_VOIP_EXTRA_VOLUME_ENABLE, z).build().toString());
    }

    public void setVoipAntiHowlingMode(boolean z) {
        AudioManager.setAudioServiceConfig(new AudioParameter.Builder().setParam(AudioParameter.SEC_LOCAL_CALL_VOIP_EXTRA_VOLUME_ENABLE, z).build().toString());
    }

    public void sendMediaKeyEvent(String str, KeyEvent keyEvent) {
        Objects.requireNonNull(str, "packageName shouldn't be null");
        Objects.requireNonNull(keyEvent, "keyEvent shouldn't be null");
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.addFlags(268435456);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", getContext().getPackageName());
        intent.setPackage(str);
        BroadcastOptions broadcastOptionsMakeBasic = BroadcastOptions.makeBasic();
        broadcastOptionsMakeBasic.setTemporaryAppAllowlist(10000L, 0, 313, "");
        broadcastOptionsMakeBasic.setBackgroundActivityStartsAllowed(true);
        getContext().sendBroadcast(intent, (String) null, broadcastOptionsMakeBasic.toBundle());
    }
}
