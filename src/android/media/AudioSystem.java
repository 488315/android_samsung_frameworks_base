package android.media;

import android.content.Context;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.ISoundDose;
import android.media.ISpatializer;
import android.media.audio.common.AidlConversion;
import android.media.audiofx.AudioEffect;
import android.media.audiopolicy.AudioMixingRule;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Vibrator;
import android.service.timezone.TimeZoneProviderService;
import android.telephony.TelephonyManager;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags;
import com.samsung.android.audio.Rune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes2.dex */
public class AudioSystem {
    public static final int AUDIO_FORMAT_AAC = 67108864;
    public static final int AUDIO_FORMAT_APTX = 536870912;
    public static final int AUDIO_FORMAT_APTX_HD = 553648128;
    public static final int AUDIO_FORMAT_DEFAULT = 0;
    public static final int AUDIO_FORMAT_INVALID = -1;
    public static final int AUDIO_FORMAT_LC3 = 721420288;
    public static final int AUDIO_FORMAT_LDAC = 587202560;
    public static final int AUDIO_FORMAT_OPUS = 134217728;
    public static final int AUDIO_FORMAT_SBC = 520093696;
    public static final int AUDIO_FORMAT_SEC_BLE_DUAL_MODE = 4194304;
    public static final int AUDIO_FORMAT_SEC_BLE_IMPLICIT = 8388608;
    public static final int AUDIO_FORMAT_SEC_BT_A2DP_OFFLOAD = 2097152;
    public static final int AUDIO_HW_SYNC_INVALID = 0;
    public static final int AUDIO_SESSION_ALLOCATE = 0;
    public static final int AUDIO_STATUS_ERROR = 1;
    public static final int AUDIO_STATUS_OK = 0;
    public static final int AUDIO_STATUS_SERVER_DIED = 100;
    public static final int BAD_VALUE = -2;
    public static final int DEAD_OBJECT = -6;
    private static final boolean DEBUG_VOLUME = false;
    public static final int DEFAULT_MUTE_STREAMS_AFFECTED = 111;
    public static int[] DEFAULT_STREAM_VOLUME = null;
    public static final Set<Integer> DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER_SET;
    public static final int DEVICE_BIT_DEFAULT = 1073741824;
    public static final int DEVICE_BIT_IN = Integer.MIN_VALUE;
    public static final Set<Integer> DEVICE_IN_ALL_BLE_SET;
    public static final Set<Integer> DEVICE_IN_ALL_SCO_SET;
    public static final Set<Integer> DEVICE_IN_ALL_SET;
    public static final Set<Integer> DEVICE_IN_ALL_USB_SET;
    public static final int DEVICE_IN_AMBIENT = -2147483646;
    public static final String DEVICE_IN_AMBIENT_NAME = "ambient";
    public static final int DEVICE_IN_ANLG_DOCK_HEADSET = -2147483136;
    public static final String DEVICE_IN_ANLG_DOCK_HEADSET_NAME = "analog_dock";
    public static final int DEVICE_IN_AUX_DIGITAL = -2147483616;
    public static final String DEVICE_IN_AUX_DIGITAL_NAME = "aux_digital";
    public static final int DEVICE_IN_BACK_MIC = -2147483520;
    public static final String DEVICE_IN_BACK_MIC_NAME = "back_mic";
    public static final int DEVICE_IN_BLE_HEADSET = -1610612736;
    public static final String DEVICE_IN_BLE_HEADSET_NAME = "ble_headset";
    public static final int DEVICE_IN_BLUETOOTH_A2DP = -2147352576;
    public static final String DEVICE_IN_BLUETOOTH_A2DP_NAME = "bt_a2dp";
    public static final int DEVICE_IN_BLUETOOTH_BLE = -2080374784;
    public static final String DEVICE_IN_BLUETOOTH_BLE_NAME = "bt_ble";
    public static final int DEVICE_IN_BLUETOOTH_SCO_HEADSET = -2147483640;
    public static final String DEVICE_IN_BLUETOOTH_SCO_HEADSET_NAME = "bt_sco_hs";
    public static final int DEVICE_IN_BUILTIN_MIC = -2147483644;
    public static final String DEVICE_IN_BUILTIN_MIC_NAME = "mic";
    public static final int DEVICE_IN_BUS = -2146435072;
    public static final String DEVICE_IN_BUS_NAME = "bus";
    public static final int DEVICE_IN_COMMUNICATION = -2147483647;
    public static final String DEVICE_IN_COMMUNICATION_NAME = "communication";
    public static final int DEVICE_IN_DEFAULT = -1073741824;
    public static final int DEVICE_IN_DGTL_DOCK_HEADSET = -2147482624;
    public static final String DEVICE_IN_DGTL_DOCK_HEADSET_NAME = "digital_dock";
    public static final int DEVICE_IN_ECHO_REFERENCE = -1879048192;
    public static final String DEVICE_IN_ECHO_REFERENCE_NAME = "echo_reference";
    public static final int DEVICE_IN_FM_TUNER = -2147475456;
    public static final String DEVICE_IN_FM_TUNER_NAME = "fm_tuner";
    public static final int DEVICE_IN_HDMI = -2147483616;
    public static final int DEVICE_IN_HDMI_ARC = -2013265920;
    public static final String DEVICE_IN_HDMI_ARC_NAME = "hdmi_arc";
    public static final int DEVICE_IN_HDMI_EARC = -2013265919;
    public static final String DEVICE_IN_HDMI_EARC_NAME = "hdmi_earc";
    public static final int DEVICE_IN_IP = -2146959360;
    public static final String DEVICE_IN_IP_NAME = "ip";
    public static final int DEVICE_IN_LINE = -2147450880;
    public static final String DEVICE_IN_LINE_NAME = "line";
    public static final int DEVICE_IN_LOOPBACK = -2147221504;
    public static final String DEVICE_IN_LOOPBACK_NAME = "loopback";
    public static final int DEVICE_IN_PROXY = -2130706432;
    public static final String DEVICE_IN_PROXY_NAME = "proxy";
    public static final int DEVICE_IN_REMOTE_SUBMIX = -2147483392;
    public static final String DEVICE_IN_REMOTE_SUBMIX_NAME = "remote_submix";
    public static final int DEVICE_IN_SPDIF = -2147418112;
    public static final String DEVICE_IN_SPDIF_NAME = "spdif";
    public static final int DEVICE_IN_TELEPHONY_RX = -2147483584;
    public static final String DEVICE_IN_TELEPHONY_RX_NAME = "telephony_rx";
    public static final int DEVICE_IN_TV_TUNER = -2147467264;
    public static final String DEVICE_IN_TV_TUNER_NAME = "tv_tuner";
    public static final int DEVICE_IN_USB_ACCESSORY = -2147481600;
    public static final String DEVICE_IN_USB_ACCESSORY_NAME = "usb_accessory";
    public static final int DEVICE_IN_USB_DEVICE = -2147479552;
    public static final String DEVICE_IN_USB_DEVICE_NAME = "usb_device";
    public static final int DEVICE_IN_USB_HEADSET = -2113929216;
    public static final String DEVICE_IN_USB_HEADSET_NAME = "usb_headset";
    public static final int DEVICE_IN_VOICE_CALL = -2147483584;
    public static final int DEVICE_IN_WIRED_HEADSET = -2147483632;
    public static final String DEVICE_IN_WIRED_HEADSET_NAME = "headset";
    public static final int DEVICE_NONE = 0;
    public static final Set<Integer> DEVICE_OUT_ALL_A2DP_SET;
    public static final Set<Integer> DEVICE_OUT_ALL_BLE_SET;
    public static final Set<Integer> DEVICE_OUT_ALL_BLE_UNICAST_SET;
    public static final Set<Integer> DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET;
    public static final Set<Integer> DEVICE_OUT_ALL_SCO_SET;
    public static final Set<Integer> DEVICE_OUT_ALL_SET;
    public static final int DEVICE_OUT_ALL_USB = 67133440;
    public static final Set<Integer> DEVICE_OUT_ALL_USB_SET;
    public static final int DEVICE_OUT_ANLG_DOCK_HEADSET = 2048;
    public static final String DEVICE_OUT_ANLG_DOCK_HEADSET_NAME = "analog_dock";
    public static final int DEVICE_OUT_AUX_DIGITAL = 1024;
    public static final String DEVICE_OUT_AUX_DIGITAL_NAME = "aux_digital";
    public static final int DEVICE_OUT_AUX_LINE = 2097152;
    public static final String DEVICE_OUT_AUX_LINE_NAME = "aux_line";
    public static final int DEVICE_OUT_BLE_BROADCAST = 536870914;
    public static final String DEVICE_OUT_BLE_BROADCAST_NAME = "ble_broadcast";
    public static final int DEVICE_OUT_BLE_HEADSET = 536870912;
    public static final String DEVICE_OUT_BLE_HEADSET_NAME = "ble_headset";
    public static final int DEVICE_OUT_BLE_SPEAKER = 536870913;
    public static final String DEVICE_OUT_BLE_SPEAKER_NAME = "ble_speaker";
    public static final int DEVICE_OUT_BLUETOOTH_A2DP = 128;
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    public static final String DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES_NAME = "bt_a2dp_hp";
    public static final String DEVICE_OUT_BLUETOOTH_A2DP_NAME = "bt_a2dp";
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    public static final String DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER_NAME = "bt_a2dp_spk";
    public static final int DEVICE_OUT_BLUETOOTH_SCO = 16;
    public static final int DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    public static final String DEVICE_OUT_BLUETOOTH_SCO_CARKIT_NAME = "bt_sco_carkit";
    public static final int DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    public static final String DEVICE_OUT_BLUETOOTH_SCO_HEADSET_NAME = "bt_sco_hs";
    public static final String DEVICE_OUT_BLUETOOTH_SCO_NAME = "bt_sco";
    public static final int DEVICE_OUT_BUS = 16777216;
    public static final String DEVICE_OUT_BUS_NAME = "bus";
    public static final int DEVICE_OUT_DEFAULT = 1073741824;
    public static final int DEVICE_OUT_DGTL_DOCK_HEADSET = 4096;
    public static final String DEVICE_OUT_DGTL_DOCK_HEADSET_NAME = "digital_dock";
    public static final int DEVICE_OUT_EARPIECE = 1;
    public static final String DEVICE_OUT_EARPIECE_NAME = "earpiece";
    public static final int DEVICE_OUT_ECHO_CANCELLER = 268435456;
    public static final String DEVICE_OUT_ECHO_CANCELLER_NAME = "echo_canceller";
    public static final int DEVICE_OUT_FM = 1048576;
    public static final String DEVICE_OUT_FM_NAME = "fm_transmitter";
    public static final int DEVICE_OUT_HDMI = 1024;
    public static final int DEVICE_OUT_HDMI_ARC = 262144;
    public static final String DEVICE_OUT_HDMI_ARC_NAME = "hdmi_arc";
    public static final int DEVICE_OUT_HDMI_EARC = 262145;
    public static final String DEVICE_OUT_HDMI_EARC_NAME = "hdmi_earc";
    public static final String DEVICE_OUT_HDMI_NAME = "hdmi";
    public static final int DEVICE_OUT_HEARING_AID = 134217728;
    public static final String DEVICE_OUT_HEARING_AID_NAME = "hearing_aid_out";
    public static final int DEVICE_OUT_IP = 8388608;
    public static final String DEVICE_OUT_IP_NAME = "ip";
    public static final int DEVICE_OUT_LINE = 131072;
    public static final String DEVICE_OUT_LINE_NAME = "line";
    public static final int DEVICE_OUT_MULTICHANNEL_GROUP = 8388609;
    public static final String DEVICE_OUT_MULTICHANNEL_GROUP_NAME = "multichannel_group";
    public static final Set<Integer> DEVICE_OUT_PICK_FOR_VOLUME_SET;
    public static final int DEVICE_OUT_PROXY = 33554432;
    public static final String DEVICE_OUT_PROXY_NAME = "proxy";
    public static final int DEVICE_OUT_REMOTE_SUBMIX = 32768;
    public static final String DEVICE_OUT_REMOTE_SUBMIX_NAME = "remote_submix";
    public static final int DEVICE_OUT_SPDIF = 524288;
    public static final String DEVICE_OUT_SPDIF_NAME = "spdif";
    public static final int DEVICE_OUT_SPEAKER = 2;
    public static final String DEVICE_OUT_SPEAKER_NAME = "speaker";
    public static final int DEVICE_OUT_SPEAKER_SAFE = 4194304;
    public static final String DEVICE_OUT_SPEAKER_SAFE_NAME = "speaker_safe";
    public static final int DEVICE_OUT_TELEPHONY_TX = 65536;
    public static final String DEVICE_OUT_TELEPHONY_TX_NAME = "telephony_tx";
    public static final int DEVICE_OUT_USB_ACCESSORY = 8192;
    public static final String DEVICE_OUT_USB_ACCESSORY_NAME = "usb_accessory";
    public static final int DEVICE_OUT_USB_DEVICE = 16384;
    public static final String DEVICE_OUT_USB_DEVICE_NAME = "usb_device";
    public static final int DEVICE_OUT_USB_HEADSET = 67108864;
    public static final String DEVICE_OUT_USB_HEADSET_NAME = "usb_headset";
    public static final int DEVICE_OUT_WIRED_HEADPHONE = 8;
    public static final String DEVICE_OUT_WIRED_HEADPHONE_NAME = "headphone";
    public static final int DEVICE_OUT_WIRED_HEADSET = 4;
    public static final String DEVICE_OUT_WIRED_HEADSET_NAME = "headset";
    public static final int DEVICE_ROLE_DISABLED = 2;
    public static final int DEVICE_ROLE_NONE = 0;
    public static final int DEVICE_ROLE_PREFERRED = 1;
    public static final int DEVICE_STATE_AVAILABLE = 1;
    public static final int DEVICE_STATE_AVAILABLE_BY_FORCE = 2;
    public static final int DEVICE_STATE_UNAVAILABLE = 0;
    public static final int DIRECT_BITSTREAM_SUPPORTED = 4;
    public static final int DIRECT_NOT_SUPPORTED = 0;
    public static final int DIRECT_OFFLOAD_GAPLESS_SUPPORTED = 3;
    public static final int DIRECT_OFFLOAD_SUPPORTED = 1;
    private static final int DYNAMIC_POLICY_EVENT_MIX_STATE_UPDATE = 0;
    public static final int ERROR = -1;
    public static final int FCC_24 = 24;
    public static final int FORCE_ANALOG_DOCK = 8;
    public static final int FORCE_BT_A2DP = 4;
    public static final int FORCE_BT_BLE = 16;
    public static final int FORCE_BT_CAR_DOCK = 6;
    public static final int FORCE_BT_DESK_DOCK = 7;
    public static final int FORCE_BT_SCO = 3;
    public static final int FORCE_DEFAULT = 0;
    public static final int FORCE_DIGITAL_DOCK = 9;
    public static final int FORCE_ENCODED_SURROUND_ALWAYS = 14;
    public static final int FORCE_ENCODED_SURROUND_MANUAL = 15;
    public static final int FORCE_ENCODED_SURROUND_NEVER = 13;
    public static final int FORCE_HDMI_SYSTEM_AUDIO_ENFORCED = 12;
    public static final int FORCE_HEADPHONES = 2;
    public static final int FORCE_NONE = 0;
    public static final int FORCE_NO_BT_A2DP = 10;
    public static final int FORCE_SPEAKER = 1;
    public static final int FORCE_SYSTEM_ENFORCED = 11;
    public static final int FORCE_WIRED_ACCESSORY = 5;
    public static final int FOR_COMMUNICATION = 0;
    public static final int FOR_DOCK = 3;
    public static final int FOR_ENCODED_SURROUND = 6;
    public static final int FOR_FMRADIO = 8;
    public static final int FOR_HDMI_SYSTEM_AUDIO = 5;
    public static final int FOR_MEDIA = 1;
    public static final int FOR_RECORD = 2;
    public static final int FOR_SYSTEM = 4;
    public static final int FOR_VIBRATE_RINGING = 7;
    public static final int INVALID_OPERATION = -3;
    public static final String IN_VOICE_COMM_FOCUS_ID = "AudioFocus_For_Phone_Ring_And_Calls";
    public static final String LEGACY_REMOTE_SUBMIX_ADDRESS = "0";
    private static final int MAX_DEVICE_ROUTING = 4;
    public static final int MODE_CALL_REDIRECT = 5;
    public static final int MODE_CALL_SCREENING = 4;
    public static final int MODE_COMMUNICATION_REDIRECT = 6;
    public static final int MODE_CURRENT = -1;
    public static final int MODE_INVALID = -2;
    public static final int MODE_IN_CALL = 2;
    public static final int MODE_IN_COMMUNICATION = 3;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RINGTONE = 1;
    static final int NATIVE_EVENT_ROUTING_CHANGE = 1000;
    public static final int NO_INIT = -5;
    private static final int NUM_DEVICE_STATES = 2;
    public static final int NUM_FORCE_CONFIG = 17;
    private static final int NUM_FORCE_USE = 9;
    public static final int NUM_MODES = 7;
    public static final int NUM_STREAMS = 5;
    private static final int NUM_STREAM_TYPES = 12;
    public static final int OFFLOAD_GAPLESS_SUPPORTED = 2;
    public static final int OFFLOAD_NOT_SUPPORTED = 0;
    public static final int OFFLOAD_SUPPORTED = 1;
    public static final int PERMISSION_DENIED = -4;
    public static final int PHONE_STATE_INCALL = 2;
    public static final int PHONE_STATE_OFFCALL = 0;
    public static final int PHONE_STATE_RINGING = 1;
    public static final int PLATFORM_AUTOMOTIVE = 3;
    public static final int PLATFORM_DEFAULT = 0;
    public static final int PLATFORM_TELEVISION = 2;
    public static final int PLATFORM_VOICE = 1;
    public static final int PLAY_SOUND_DELAY = 300;

    @Deprecated
    public static final int ROUTE_ALL = -1;

    @Deprecated
    public static final int ROUTE_BLUETOOTH = 4;

    @Deprecated
    public static final int ROUTE_BLUETOOTH_A2DP = 16;

    @Deprecated
    public static final int ROUTE_BLUETOOTH_SCO = 4;

    @Deprecated
    public static final int ROUTE_EARPIECE = 1;

    @Deprecated
    public static final int ROUTE_HEADSET = 8;

    @Deprecated
    public static final int ROUTE_SPEAKER = 2;
    public static final int STREAM_ACCESSIBILITY = 10;
    public static final int STREAM_ALARM = 4;
    public static final int STREAM_ASSISTANT = 11;
    public static final int STREAM_BLUETOOTH_SCO = 6;
    public static final int STREAM_DEFAULT = -1;
    public static final int STREAM_DTMF = 8;
    public static final int STREAM_MUSIC = 3;
    public static final int STREAM_NOTIFICATION = 5;
    public static final int STREAM_RING = 2;
    public static final int STREAM_SYSTEM = 1;
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    public static final int STREAM_TTS = 9;
    public static final int STREAM_VOICE_CALL = 0;
    public static final int SUCCESS = 0;
    public static final int SYNC_EVENT_NONE = 0;
    public static final int SYNC_EVENT_PRESENTATION_COMPLETE = 1;
    public static final int SYNC_EVENT_SHARE_AUDIO_HISTORY = 100;
    private static final String TAG = "AudioSystem";
    public static final int WOULD_BLOCK = -7;
    private static DynamicPolicyCallback sDynPolicyCallback;
    private static ErrorCallback sErrorCallback;
    private static AudioRecordingCallback sRecordingCallback;
    private static RoutingUpdateCallback sRoutingUpdateCallback;
    private static VolumeRangeInitRequestCallback sVolRangeInitReqCallback;
    public static final int OUT_CHANNEL_COUNT_MAX = native_getMaxChannelCount();
    public static final int SAMPLE_RATE_HZ_MAX = native_getMaxSampleRate();
    public static final int SAMPLE_RATE_HZ_MIN = native_getMinSampleRate();
    public static final String[] STREAM_NAMES = {"STREAM_VOICE_CALL", "STREAM_SYSTEM", "STREAM_RING", "STREAM_MUSIC", "STREAM_ALARM", "STREAM_NOTIFICATION", "STREAM_BLUETOOTH_SCO", "STREAM_SYSTEM_ENFORCED", "STREAM_DTMF", "STREAM_TTS", "STREAM_ACCESSIBILITY", "STREAM_ASSISTANT"};

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioFormatNativeEnumForBtCodec {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioFormatNativeEnumForBtLeAudioCodec {
    }

    public interface AudioRecordingCallback {
        void onRecordingConfigurationChanged(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int[] iArr, AudioEffect.Descriptor[] descriptorArr, AudioEffect.Descriptor[] descriptorArr2, int i7, String str);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioSystemError {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BtOffloadDeviceType {
    }

    public interface DynamicPolicyCallback {
        void onDynamicPolicyMixStateUpdate(String str, int i);
    }

    public interface ErrorCallback {
        void onError(int i);
    }

    public interface RoutingUpdateCallback {
        void onRoutingUpdated();
    }

    public interface VolumeRangeInitRequestCallback {
        void onVolumeRangeInitializationRequested();
    }

    private static native int addDevicesRoleForCapturePreset(int i, int i2, int[] iArr, String[] strArr);

    public static native boolean canBeSpatialized(AudioAttributes audioAttributes, AudioFormat audioFormat, AudioDeviceAttributes[] audioDeviceAttributesArr);

    public static native int checkAudioFlinger();

    public static native int clearDevicesRoleForCapturePreset(int i, int i2);

    public static native int clearDevicesRoleForStrategy(int i, int i2);

    public static native int clearPreferredMixerAttributes(AudioAttributes audioAttributes, int i, int i2);

    public static native int createAudioPatch(AudioPatch[] audioPatchArr, AudioPortConfig[] audioPortConfigArr, AudioPortConfig[] audioPortConfigArr2);

    public static native int getAudioHwSyncForSession(int i);

    public static native int getDeviceConnectionState(int i, String str);

    private static native int getDevicesForAttributes(AudioAttributes audioAttributes, AudioDeviceAttributes[] audioDeviceAttributesArr, boolean z);

    public static native int getDevicesForRoleAndCapturePreset(int i, int i2, List<AudioDeviceAttributes> list);

    public static native int getDevicesForRoleAndStrategy(int i, int i2, List<AudioDeviceAttributes> list);

    public static native int getDirectPlaybackSupport(AudioFormat audioFormat, AudioAttributes audioAttributes);

    public static native int getDirectProfilesForAttributes(AudioAttributes audioAttributes, ArrayList<AudioProfile> arrayList);

    public static native int getForceUse(int i);

    public static native int getHwOffloadFormatsSupportedForBluetoothMedia(int i, ArrayList<Integer> arrayList);

    public static native float getMasterBalance();

    public static native boolean getMasterMono();

    public static native boolean getMasterMute();

    public static native float getMasterVolume();

    public static native int getMaxVolumeIndexForAttributes(AudioAttributes audioAttributes);

    public static native int getMicrophones(ArrayList<MicrophoneInfo> arrayList);

    public static native int getMinVolumeIndexForAttributes(AudioAttributes audioAttributes);

    public static final int getNumStreamTypes() {
        return 12;
    }

    public static native int getOutputLatency(int i);

    public static native String getParameters(String str);

    public static native int getPreferredMixerAttributes(AudioAttributes audioAttributes, int i, List<AudioMixerAttributes> list);

    public static native int getPrimaryOutputFrameCount();

    public static native int getPrimaryOutputSamplingRate();

    public static native int getRegisteredPolicyMixes(List<android.media.audiopolicy.AudioMix> list);

    public static native int getReportedSurroundFormats(ArrayList<Integer> arrayList);

    public static native float getStreamVolumeDB(int i, int i2, int i3);

    public static native int getStreamVolumeIndex(int i, int i2);

    public static native int getSupportedDeviceTypes(int i, IntArray intArray);

    public static native int getSupportedMixerAttributes(int i, List<AudioMixerAttributes> list);

    public static native int getSurroundFormats(Map<Integer, Boolean> map);

    public static int getValueForVibrateSetting(int i, int i2, int i3) {
        int i4 = i2 * 2;
        return (i & (~(3 << i4))) | ((i3 & 3) << i4);
    }

    public static native int getVolumeIndexForAttributes(AudioAttributes audioAttributes, int i);

    public static native int handleDeviceConfigChange(int i, String str, String str2, int i2);

    public static native int initStreamVolume(int i, int i2, int i3);

    public static native boolean isBluetoothVariableLatencyEnabled();

    public static native boolean isCallScreeningModeSupported();

    public static native boolean isHapticPlaybackSupported();

    public static boolean isInputDevice(int i) {
        return (i & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    public static native boolean isMicrophoneMuted();

    public static boolean isRemoteSubmixDevice(int i) {
        return i == -2147483392 || i == 32768;
    }

    public static native boolean isSourceActive(int i);

    public static native boolean isStreamActive(int i, int i2);

    public static native boolean isStreamActiveRemotely(int i, int i2);

    public static native boolean isUltrasoundSupported();

    public static native int listAudioPatches(ArrayList<AudioPatch> arrayList, int[] iArr);

    public static native int listAudioPorts(ArrayList<AudioPort> arrayList, int[] iArr);

    public static native long listenForSystemPropertyChange(String str, Runnable runnable);

    public static native int muteMicrophone(boolean z);

    private static native IBinder nativeGetSoundDose(ISoundDoseCallback iSoundDoseCallback);

    private static native IBinder nativeGetSpatializer(INativeSpatializerCallback iNativeSpatializerCallback);

    private static native int native_getMaxChannelCount();

    private static native int native_getMaxSampleRate();

    private static native int native_getMinSampleRate();

    private static native int native_get_offload_support(int i, int i2, int i3, int i4, int i5);

    private static final native void native_register_dynamic_policy_callback();

    private static final native void native_register_recording_callback();

    private static native void native_register_routing_callback();

    private static native void native_register_vol_range_init_req_callback();

    public static native int newAudioPlayerId();

    public static native int newAudioRecorderId();

    public static native int newAudioSessionId();

    public static native int registerAudioVolumeGroupCallback(INativeAudioVolumeGroupCallback iNativeAudioVolumeGroupCallback);

    public static native int registerPolicyMixes(ArrayList<android.media.audiopolicy.AudioMix> arrayList, boolean z);

    public static native int releaseAudioPatch(AudioPatch audioPatch);

    private static native int removeDevicesRoleForCapturePreset(int i, int i2, int[] iArr, String[] strArr);

    public static native int removeDevicesRoleForStrategy(int i, int i2, int[] iArr, String[] strArr);

    public static native int removeUidDeviceAffinities(int i);

    public static native int removeUserIdDeviceAffinities(int i);

    public static native int setA11yServicesUids(int[] iArr);

    public static native int setActiveAssistantServicesUids(int[] iArr);

    public static native int setAllowedCapturePolicy(int i, int i2);

    public static native int setAssistantServicesUids(int[] iArr);

    public static native void setAudioFlingerBinder(IBinder iBinder);

    public static native int setAudioHalPids(int[] iArr);

    public static native int setAudioPortConfig(AudioPortConfig audioPortConfig);

    public static native int setBluetoothVariableLatencyEnabled(boolean z);

    public static native int setCurrentImeUid(int i);

    public static native int setDeviceAbsoluteVolumeEnabled(int i, String str, boolean z, int i2);

    public static native int setDeviceConnectionState(int i, Parcel parcel, int i2, boolean z);

    private static native int setDevicesRoleForCapturePreset(int i, int i2, int[] iArr, String[] strArr);

    private static native int setDevicesRoleForStrategy(int i, int i2, int[] iArr, String[] strArr);

    public static native int setForceUse(int i, int i2);

    public static native int setLowRamDevice(boolean z, long j);

    public static native int setMasterBalance(float f);

    public static native int setMasterMono(boolean z);

    public static native int setMasterMute(boolean z);

    public static native int setMasterVolume(float f);

    public static native int setParameters(String str);

    public static native int setPhoneState(int i, int i2);

    public static native int setPreferredMixerAttributes(AudioAttributes audioAttributes, int i, int i2, AudioMixerAttributes audioMixerAttributes);

    public static native int setRttEnabled(boolean z);

    private static native int setStreamVolumeIndex(int i, int i2, boolean z, int i3);

    public static native int setSupportedSystemUsages(int[] iArr);

    public static native int setSurroundFormatEnabled(int i, boolean z);

    public static native int setUidDeviceAffinities(int i, int[] iArr, String[] strArr);

    public static native int setUserIdDeviceAffinities(int i, int[] iArr, String[] strArr);

    public static native int setVibratorInfos(List<Vibrator> list);

    public static native int setVolumeIndexForAttributes(AudioAttributes audioAttributes, int i, boolean z, int i2);

    public static native int startAudioSource(AudioPortConfig audioPortConfig, AudioAttributes audioAttributes);

    public static native int stopAudioSource(int i);

    public static native boolean supportsBluetoothVariableLatency();

    public static native int systemReady();

    public static native void triggerSystemPropertyUpdate(long j);

    public static native int unregisterAudioVolumeGroupCallback(INativeAudioVolumeGroupCallback iNativeAudioVolumeGroupCallback);

    public static native int updatePolicyMixes(android.media.audiopolicy.AudioMix[] audioMixArr, AudioMixingRule[] audioMixingRuleArr);

    private AudioSystem() {
        throw new UnsupportedOperationException("Trying to instantiate AudioSystem");
    }

    static {
        HashSet hashSet = new HashSet();
        DEVICE_OUT_ALL_SET = hashSet;
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(4);
        hashSet.add(8);
        hashSet.add(16);
        hashSet.add(32);
        hashSet.add(64);
        hashSet.add(128);
        hashSet.add(256);
        hashSet.add(512);
        hashSet.add(1024);
        hashSet.add(2048);
        hashSet.add(4096);
        hashSet.add(8192);
        hashSet.add(16384);
        hashSet.add(32768);
        hashSet.add(65536);
        hashSet.add(131072);
        hashSet.add(262144);
        hashSet.add(262145);
        hashSet.add(524288);
        hashSet.add(1048576);
        hashSet.add(2097152);
        hashSet.add(4194304);
        hashSet.add(8388608);
        hashSet.add(8388609);
        hashSet.add(16777216);
        hashSet.add(33554432);
        hashSet.add(67108864);
        hashSet.add(134217728);
        hashSet.add(268435456);
        hashSet.add(536870912);
        hashSet.add(536870913);
        hashSet.add(536870914);
        hashSet.add(1073741824);
        HashSet hashSet2 = new HashSet();
        DEVICE_OUT_ALL_A2DP_SET = hashSet2;
        hashSet2.add(128);
        hashSet2.add(256);
        hashSet2.add(512);
        HashSet hashSet3 = new HashSet();
        DEVICE_OUT_ALL_SCO_SET = hashSet3;
        hashSet3.add(16);
        hashSet3.add(32);
        hashSet3.add(64);
        HashSet hashSet4 = new HashSet();
        DEVICE_OUT_ALL_USB_SET = hashSet4;
        hashSet4.add(8192);
        hashSet4.add(16384);
        hashSet4.add(67108864);
        HashSet hashSet5 = new HashSet();
        DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET = hashSet5;
        hashSet5.add(2097152);
        hashSet5.add(262144);
        hashSet5.add(262145);
        hashSet5.add(524288);
        HashSet hashSet6 = new HashSet();
        DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER_SET = hashSet6;
        hashSet6.addAll(hashSet5);
        hashSet6.add(2);
        HashSet hashSet7 = new HashSet();
        DEVICE_OUT_ALL_BLE_SET = hashSet7;
        hashSet7.add(536870912);
        hashSet7.add(536870913);
        hashSet7.add(536870914);
        HashSet hashSet8 = new HashSet();
        DEVICE_OUT_PICK_FOR_VOLUME_SET = hashSet8;
        hashSet8.add(4);
        hashSet8.add(8);
        hashSet8.add(16384);
        hashSet8.add(67108864);
        hashSet8.add(128);
        hashSet8.add(256);
        hashSet8.add(512);
        hashSet8.add(16);
        hashSet8.add(32);
        hashSet8.add(64);
        hashSet8.add(134217728);
        hashSet8.add(536870912);
        hashSet8.add(536870913);
        hashSet8.add(536870914);
        HashSet hashSet9 = new HashSet();
        DEVICE_OUT_ALL_BLE_UNICAST_SET = hashSet9;
        hashSet9.add(536870912);
        hashSet9.add(536870913);
        HashSet hashSet10 = new HashSet();
        DEVICE_IN_ALL_SET = hashSet10;
        hashSet10.add(Integer.valueOf(DEVICE_IN_COMMUNICATION));
        hashSet10.add(Integer.valueOf(DEVICE_IN_AMBIENT));
        hashSet10.add(-2147483644);
        hashSet10.add(-2147483640);
        hashSet10.add(-2147483632);
        hashSet10.add(-2147483616);
        hashSet10.add(-2147483584);
        hashSet10.add(-2147483520);
        hashSet10.add(Integer.valueOf(DEVICE_IN_REMOTE_SUBMIX));
        hashSet10.add(-2147483136);
        hashSet10.add(-2147482624);
        hashSet10.add(-2147481600);
        hashSet10.add(-2147479552);
        hashSet10.add(-2147475456);
        hashSet10.add(-2147467264);
        hashSet10.add(-2147450880);
        hashSet10.add(-2147418112);
        hashSet10.add(Integer.valueOf(DEVICE_IN_BLUETOOTH_A2DP));
        hashSet10.add(-2147221504);
        hashSet10.add(Integer.valueOf(DEVICE_IN_IP));
        hashSet10.add(Integer.valueOf(DEVICE_IN_BUS));
        hashSet10.add(Integer.valueOf(DEVICE_IN_PROXY));
        hashSet10.add(Integer.valueOf(DEVICE_IN_USB_HEADSET));
        hashSet10.add(Integer.valueOf(DEVICE_IN_BLUETOOTH_BLE));
        hashSet10.add(-2013265920);
        hashSet10.add(-2013265919);
        hashSet10.add(-1879048192);
        hashSet10.add(-1610612736);
        hashSet10.add(Integer.valueOf(DEVICE_IN_DEFAULT));
        HashSet hashSet11 = new HashSet();
        DEVICE_IN_ALL_SCO_SET = hashSet11;
        hashSet11.add(-2147483640);
        HashSet hashSet12 = new HashSet();
        DEVICE_IN_ALL_USB_SET = hashSet12;
        hashSet12.add(-2147481600);
        hashSet12.add(-2147479552);
        hashSet12.add(Integer.valueOf(DEVICE_IN_USB_HEADSET));
        HashSet hashSet13 = new HashSet();
        DEVICE_IN_ALL_BLE_SET = hashSet13;
        hashSet13.add(-1610612736);
        DEFAULT_STREAM_VOLUME = new int[]{Flags.deprecateStreamBtSco() ? 9 : 5, 6, 11, 7, 11, 11, 7, 14, 11, 11, 11, 7};
    }

    public static String modeToString(int i) {
        switch (i) {
            case -2:
                return "MODE_INVALID";
            case -1:
                return "MODE_CURRENT";
            case 0:
                return "MODE_NORMAL";
            case 1:
                return "MODE_RINGTONE";
            case 2:
                return "MODE_IN_CALL";
            case 3:
                return "MODE_IN_COMMUNICATION";
            case 4:
                return "MODE_CALL_SCREENING";
            case 5:
                return "MODE_CALL_REDIRECT";
            case 6:
                return "MODE_COMMUNICATION_REDIRECT";
            default:
                return "unknown mode (" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static int audioFormatToBluetoothSourceCodec(int i) {
        if (i == 67108864) {
            return 1;
        }
        if (i == 134217728) {
            return 6;
        }
        if (i == 520093696) {
            return 0;
        }
        if (i == 536870912) {
            return 2;
        }
        if (i == 553648128) {
            return 3;
        }
        if (i == 587202560) {
            return 4;
        }
        if (i == 721420288) {
            return 5;
        }
        Log.e(TAG, "Unknown audio format 0x" + Integer.toHexString(i) + " for conversion to BT codec");
        return 1000000;
    }

    public static int audioFormatToBluetoothLeAudioSourceCodec(int i) {
        if (i == 721420288) {
            return 0;
        }
        Log.e(TAG, "Unknown audio format 0x" + Integer.toHexString(i) + " for conversion to BT LE audio codec");
        return 1000000;
    }

    public static int bluetoothA2dpCodecToAudioFormat(int i) {
        switch (i) {
            case 0:
                return 520093696;
            case 1:
                return 67108864;
            case 2:
                return 536870912;
            case 3:
                return 553648128;
            case 4:
                return 587202560;
            case 5:
                return 721420288;
            case 6:
                return 134217728;
            default:
                Log.e(TAG, "Unknown A2DP BT codec 0x" + Integer.toHexString(i) + " for conversion to audio format");
                return 0;
        }
    }

    public static int bluetoothLeCodecToAudioFormat(int i) {
        if (i == 0) {
            return 721420288;
        }
        Log.e(TAG, "Unknown LE Audio BT codec 0x" + Integer.toHexString(i) + " for conversion to audio format");
        return 0;
    }

    public static String audioFormatToString(int i) {
        switch (i) {
            case -1:
                return "AUDIO_FORMAT_INVALID";
            case 0:
                return "AUDIO_FORMAT_DEFAULT";
            case 1:
                return "AUDIO_FORMAT_PCM_16_BIT";
            case 2:
                return "AUDIO_FORMAT_PCM_8_BIT";
            case 3:
                return "AUDIO_FORMAT_PCM_32_BIT";
            case 4:
                return "AUDIO_FORMAT_PCM_8_24_BIT";
            case 5:
                return "AUDIO_FORMAT_PCM_FLOAT";
            case 6:
                return "AUDIO_FORMAT_PCM_24_BIT_PACKED";
            case 16777216:
                return "AUDIO_FORMAT_MP3";
            case 33554432:
                return "AUDIO_FORMAT_AMR_NB";
            case 50331648:
                return "AUDIO_FORMAT_AMR_WB";
            case 67108864:
                return "AUDIO_FORMAT_AAC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_MAIN /* 67108865 */:
                return "AUDIO_FORMAT_AAC_MAIN";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LC /* 67108866 */:
                return "AUDIO_FORMAT_AAC_LC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_SSR /* 67108868 */:
                return "AUDIO_FORMAT_AAC_SSR";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LTP /* 67108872 */:
                return "AUDIO_FORMAT_AAC_LTP";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_HE_V1 /* 67108880 */:
                return "AUDIO_FORMAT_AAC_HE_V1";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_SCALABLE /* 67108896 */:
                return "AUDIO_FORMAT_AAC_SCALABLE";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ERLC /* 67108928 */:
                return "AUDIO_FORMAT_AAC_ERLC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LD /* 67108992 */:
                return "AUDIO_FORMAT_AAC_LD";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_HE_V2 /* 67109120 */:
                return "AUDIO_FORMAT_AAC_HE_V2";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ELD /* 67109376 */:
                return "AUDIO_FORMAT_AAC_ELD";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_XHE /* 67109632 */:
                return "AUDIO_FORMAT_AAC_XHE";
            case 83886080:
                return "AUDIO_FORMAT_HE_AAC_V1";
            case 100663296:
                return "AUDIO_FORMAT_HE_AAC_V2";
            case android.media.audio.Enums.AUDIO_FORMAT_VORBIS /* 117440512 */:
                return "AUDIO_FORMAT_VORBIS";
            case 134217728:
                return "AUDIO_FORMAT_OPUS";
            case 150994944:
                return "AUDIO_FORMAT_AC3";
            case 167772160:
                return "AUDIO_FORMAT_E_AC3";
            case android.media.audio.Enums.AUDIO_FORMAT_E_AC3_JOC /* 167772161 */:
                return "AUDIO_FORMAT_E_AC3_JOC";
            case 184549376:
                return "AUDIO_FORMAT_DTS";
            case 201326592:
                return "AUDIO_FORMAT_DTS_HD";
            case 218103808:
                return "AUDIO_FORMAT_IEC61937";
            case android.media.audio.Enums.AUDIO_FORMAT_DOLBY_TRUEHD /* 234881024 */:
                return "AUDIO_FORMAT_DOLBY_TRUEHD";
            case 268435456:
                return "AUDIO_FORMAT_EVRC";
            case 285212672:
                return "AUDIO_FORMAT_EVRCB";
            case 301989888:
                return "AUDIO_FORMAT_EVRCWB";
            case 318767104:
                return "AUDIO_FORMAT_EVRCNW";
            case 335544320:
                return "AUDIO_FORMAT_AAC_ADIF";
            case 352321536:
                return "AUDIO_FORMAT_WMA";
            case 369098752:
                return "AUDIO_FORMAT_WMA_PRO";
            case 385875968:
                return "AUDIO_FORMAT_AMR_WB_PLUS";
            case 402653184:
                return "AUDIO_FORMAT_MP2";
            case 419430400:
                return "AUDIO_FORMAT_QCELP";
            case android.media.audio.Enums.AUDIO_FORMAT_DSD /* 436207616 */:
                return "AUDIO_FORMAT_DSD";
            case android.media.audio.Enums.AUDIO_FORMAT_FLAC /* 452984832 */:
                return "AUDIO_FORMAT_FLAC";
            case android.media.audio.Enums.AUDIO_FORMAT_ALAC /* 469762048 */:
                return "AUDIO_FORMAT_ALAC";
            case android.media.audio.Enums.AUDIO_FORMAT_APE /* 486539264 */:
                return "AUDIO_FORMAT_APE";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS /* 503316480 */:
                return "AUDIO_FORMAT_AAC_ADTS";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_MAIN /* 503316481 */:
                return "AUDIO_FORMAT_AAC_ADTS_MAIN";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_LC /* 503316482 */:
                return "AUDIO_FORMAT_AAC_ADTS_LC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_SSR /* 503316484 */:
                return "AUDIO_FORMAT_AAC_ADTS_SSR";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_LTP /* 503316488 */:
                return "AUDIO_FORMAT_AAC_ADTS_LTP";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_HE_V1 /* 503316496 */:
                return "AUDIO_FORMAT_AAC_ADTS_HE_V1";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_SCALABLE /* 503316512 */:
                return "AUDIO_FORMAT_AAC_ADTS_SCALABLE";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_ERLC /* 503316544 */:
                return "AUDIO_FORMAT_AAC_ADTS_ERLC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_LD /* 503316608 */:
                return "AUDIO_FORMAT_AAC_ADTS_LD";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_HE_V2 /* 503316736 */:
                return "AUDIO_FORMAT_AAC_ADTS_HE_V2";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_ELD /* 503316992 */:
                return "AUDIO_FORMAT_AAC_ADTS_ELD";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_XHE /* 503317248 */:
                return "AUDIO_FORMAT_AAC_ADTS_XHE";
            case 520093696:
                return "AUDIO_FORMAT_SBC";
            case 536870912:
                return "AUDIO_FORMAT_APTX";
            case 553648128:
                return "AUDIO_FORMAT_APTX_HD";
            case android.media.audio.Enums.AUDIO_FORMAT_AC4 /* 570425344 */:
                return "AUDIO_FORMAT_AC4";
            case android.media.audio.Enums.AUDIO_FORMAT_AC4_L4 /* 570425345 */:
                return "AUDIO_FORMAT_AC4_L4";
            case 587202560:
                return "AUDIO_FORMAT_LDAC";
            case android.media.audio.Enums.AUDIO_FORMAT_MAT /* 603979776 */:
                return "AUDIO_FORMAT_MAT";
            case android.media.audio.Enums.AUDIO_FORMAT_MAT_1_0 /* 603979777 */:
                return "AUDIO_FORMAT_MAT_1_0";
            case android.media.audio.Enums.AUDIO_FORMAT_MAT_2_0 /* 603979778 */:
                return "AUDIO_FORMAT_MAT_2_0";
            case android.media.audio.Enums.AUDIO_FORMAT_MAT_2_1 /* 603979779 */:
                return "AUDIO_FORMAT_MAT_2_1";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LATM /* 620756992 */:
                return "AUDIO_FORMAT_AAC_LATM";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LATM_LC /* 620756994 */:
                return "AUDIO_FORMAT_AAC_LATM_LC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LATM_HE_V1 /* 620757008 */:
                return "AUDIO_FORMAT_AAC_LATM_HE_V1";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LATM_HE_V2 /* 620757248 */:
                return "AUDIO_FORMAT_AAC_LATM_HE_V2";
            case android.media.audio.Enums.AUDIO_FORMAT_CELT /* 637534208 */:
                return "AUDIO_FORMAT_CELT";
            case android.media.audio.Enums.AUDIO_FORMAT_APTX_ADAPTIVE /* 654311424 */:
                return "AUDIO_FORMAT_APTX_ADAPTIVE";
            case android.media.audio.Enums.AUDIO_FORMAT_LHDC /* 671088640 */:
                return "AUDIO_FORMAT_LHDC";
            case android.media.audio.Enums.AUDIO_FORMAT_LHDC_LL /* 687865856 */:
                return "AUDIO_FORMAT_LHDC_LL";
            case android.media.audio.Enums.AUDIO_FORMAT_APTX_TWSP /* 704643072 */:
                return "AUDIO_FORMAT_APTX_TWSP";
            case 721420288:
                return "AUDIO_FORMAT_LC3";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH /* 738197504 */:
                return "AUDIO_FORMAT_MPEGH";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH_BL_L3 /* 738197523 */:
                return "AUDIO_FORMAT_MPEGH_SUB_BL_L3";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH_BL_L4 /* 738197524 */:
                return "AUDIO_FORMAT_MPEGH_SUB_BL_L4";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH_LC_L3 /* 738197539 */:
                return "AUDIO_FORMAT_MPEGH_SUB_LC_L3";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH_LC_L4 /* 738197540 */:
                return "AUDIO_FORMAT_MPEGH_SUB_LC_L4";
            case android.media.audio.Enums.AUDIO_FORMAT_IEC60958 /* 754974720 */:
                return "AUDIO_FORMAT_IEC60958";
            case android.media.audio.Enums.AUDIO_FORMAT_DTS_UHD /* 771751936 */:
                return "AUDIO_FORMAT_DTS_UHD";
            case android.media.audio.Enums.AUDIO_FORMAT_DRA /* 788529152 */:
                return "AUDIO_FORMAT_DRA";
            case 805306368:
                return "AUDIO_FORMAT_APTX_ADAPTIVE_QLEA";
            case android.media.audio.Enums.AUDIO_FORMAT_APTX_R4 /* 822083584 */:
                return "AUDIO_FORMAT_APTX_ADAPTIVE_R4";
            case android.media.audio.Enums.AUDIO_FORMAT_DTS_HD_MA /* 838860800 */:
                return "AUDIO_FORMAT_DTS_HD_MA";
            case android.media.audio.Enums.AUDIO_FORMAT_DTS_UHD_P2 /* 855638016 */:
                return "AUDIO_FORMAT_DTS_UHD_P2";
            case 872480769:
                return "AUDIO_FORMAT_IAMF_SIMPLE_OPUS";
            case 872480770:
                return "AUDIO_FORMAT_IAMF_SIMPLE_AAC";
            case 872480772:
                return "AUDIO_FORMAT_IAMF_SIMPLE_FLAC";
            case 872480776:
                return "AUDIO_FORMAT_IAMF_SIMPLE_PCM";
            case 872546305:
                return "AUDIO_FORMAT_IAMF_BASE_OPUS";
            case 872546306:
                return "AUDIO_FORMAT_IAMF_BASE_AAC";
            case 872546308:
                return "AUDIO_FORMAT_IAMF_BASE_FLAC";
            case 872546312:
                return "AUDIO_FORMAT_IAMF_BASE_PCM";
            case 872677377:
                return "AUDIO_FORMAT_IAMF_BASE_ENHANCED_OPUS";
            case 872677378:
                return "AUDIO_FORMAT_IAMF_BASE_ENHANCED_AAC";
            case 872677380:
                return "AUDIO_FORMAT_IAMF_BASE_ENHANCED_FLAC";
            case 872677384:
                return "AUDIO_FORMAT_IAMF_BASE_ENHANCED_PCM";
            default:
                return "AUDIO_FORMAT_(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static void setErrorCallback(ErrorCallback errorCallback) {
        synchronized (AudioSystem.class) {
            sErrorCallback = errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(checkAudioFlinger());
            }
        }
    }

    private static void errorCallbackFromNative(int i) {
        ErrorCallback errorCallback;
        synchronized (AudioSystem.class) {
            errorCallback = sErrorCallback;
        }
        if (errorCallback != null) {
            errorCallback.onError(i);
        }
    }

    public static void setDynamicPolicyCallback(DynamicPolicyCallback dynamicPolicyCallback) {
        synchronized (AudioSystem.class) {
            sDynPolicyCallback = dynamicPolicyCallback;
            native_register_dynamic_policy_callback();
        }
    }

    private static void dynamicPolicyCallbackFromNative(int i, String str, int i2) {
        DynamicPolicyCallback dynamicPolicyCallback;
        synchronized (AudioSystem.class) {
            dynamicPolicyCallback = sDynPolicyCallback;
        }
        if (dynamicPolicyCallback != null) {
            if (i == 0) {
                dynamicPolicyCallback.onDynamicPolicyMixStateUpdate(str, i2);
                return;
            }
            Log.e(TAG, "dynamicPolicyCallbackFromNative: unknown event " + i);
        }
    }

    public static void setRecordingCallback(AudioRecordingCallback audioRecordingCallback) {
        synchronized (AudioSystem.class) {
            sRecordingCallback = audioRecordingCallback;
            native_register_recording_callback();
        }
    }

    private static void recordingCallbackFromNative(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int[] iArr, AudioEffect.Descriptor[] descriptorArr, AudioEffect.Descriptor[] descriptorArr2, int i7) {
        AudioRecordingCallback audioRecordingCallback;
        synchronized (AudioSystem.class) {
            audioRecordingCallback = sRecordingCallback;
        }
        if (descriptorArr.length != 0) {
            String str = descriptorArr[0].name;
        }
        if (descriptorArr2.length != 0) {
            String str2 = descriptorArr2[0].name;
        }
        if (audioRecordingCallback != null) {
            ArrayList arrayList = new ArrayList();
            if (AudioManager.listAudioPatches(arrayList) == 0) {
                int i8 = iArr[6];
                Iterator it = arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((AudioPatch) it.next()).id() == i8) {
                            break;
                        }
                    } else {
                        AudioManager.resetAudioPortGeneration();
                        break;
                    }
                }
            }
            audioRecordingCallback.onRecordingConfigurationChanged(i, i2, i3, i4, i5, i6, z, iArr, descriptorArr, descriptorArr2, i7, "");
        }
    }

    public static void setRoutingCallback(RoutingUpdateCallback routingUpdateCallback) {
        synchronized (AudioSystem.class) {
            sRoutingUpdateCallback = routingUpdateCallback;
            native_register_routing_callback();
        }
    }

    private static void routingCallbackFromNative() {
        RoutingUpdateCallback routingUpdateCallback;
        synchronized (AudioSystem.class) {
            routingUpdateCallback = sRoutingUpdateCallback;
        }
        if (routingUpdateCallback == null) {
            Log.e(TAG, "routing update from APM was not captured");
        } else {
            routingUpdateCallback.onRoutingUpdated();
        }
    }

    public static void setVolumeRangeInitRequestCallback(VolumeRangeInitRequestCallback volumeRangeInitRequestCallback) {
        synchronized (AudioSystem.class) {
            sVolRangeInitReqCallback = volumeRangeInitRequestCallback;
            native_register_vol_range_init_req_callback();
        }
    }

    private static void volRangeInitReqCallbackFromNative() {
        VolumeRangeInitRequestCallback volumeRangeInitRequestCallback;
        synchronized (AudioSystem.class) {
            volumeRangeInitRequestCallback = sVolRangeInitReqCallback;
        }
        if (volumeRangeInitRequestCallback == null) {
            Log.e(TAG, "APM requested volume range initialization, but no callback found");
        } else {
            volumeRangeInitRequestCallback.onVolumeRangeInitializationRequested();
        }
    }

    public static String audioSystemErrorToString(int i) {
        switch (i) {
            case -7:
                return "WOULD_BLOCK";
            case -6:
                return "DEAD_OBJECT";
            case -5:
                return "NO_INIT";
            case -4:
                return "PERMISSION_DENIED";
            case -3:
                return "INVALID_OPERATION";
            case -2:
                return "BAD_VALUE";
            case -1:
                return TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
            case 0:
                return TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
            default:
                return "unknown error:" + i;
        }
    }

    public static boolean isBluetoothDevice(int i) {
        return isBluetoothA2dpOutDevice(i) || isBluetoothScoDevice(i) || isBluetoothLeDevice(i);
    }

    public static boolean isBluetoothOutDevice(int i) {
        return isBluetoothA2dpOutDevice(i) || isBluetoothScoOutDevice(i) || isBluetoothLeOutDevice(i);
    }

    public static boolean isBluetoothInDevice(int i) {
        return isBluetoothScoInDevice(i) || isBluetoothLeInDevice(i);
    }

    public static boolean isBluetoothA2dpOutDevice(int i) {
        return DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i));
    }

    public static boolean isBluetoothScoOutDevice(int i) {
        return DEVICE_OUT_ALL_SCO_SET.contains(Integer.valueOf(i));
    }

    public static boolean isBluetoothScoInDevice(int i) {
        return DEVICE_IN_ALL_SCO_SET.contains(Integer.valueOf(i));
    }

    public static boolean isBluetoothScoDevice(int i) {
        return isBluetoothScoOutDevice(i) || isBluetoothScoInDevice(i);
    }

    public static boolean isBluetoothLeOutDevice(int i) {
        return DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(i));
    }

    public static boolean isBluetoothLeInDevice(int i) {
        return DEVICE_IN_ALL_BLE_SET.contains(Integer.valueOf(i));
    }

    public static boolean isBluetoothLeDevice(int i) {
        return isBluetoothLeOutDevice(i) || isBluetoothLeInDevice(i);
    }

    public static String deviceStateToString(int i) {
        if (i == 0) {
            return "DEVICE_STATE_UNAVAILABLE";
        }
        if (i == 1) {
            return "DEVICE_STATE_AVAILABLE";
        }
        if (i == 2) {
            return "DEVICE_STATE_AVAILABLE_BY_FORCE";
        }
        return "unknown state (" + i + NavigationBarInflaterView.KEY_CODE_END;
    }

    public static String getOutputDeviceName(int i) {
        switch (i) {
            case 1:
                return DEVICE_OUT_EARPIECE_NAME;
            case 2:
                return DEVICE_OUT_SPEAKER_NAME;
            case 4:
                return "headset";
            case 8:
                return DEVICE_OUT_WIRED_HEADPHONE_NAME;
            case 16:
                return DEVICE_OUT_BLUETOOTH_SCO_NAME;
            case 32:
                return "bt_sco_hs";
            case 64:
                return DEVICE_OUT_BLUETOOTH_SCO_CARKIT_NAME;
            case 128:
                return "bt_a2dp";
            case 256:
                return DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES_NAME;
            case 512:
                return DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER_NAME;
            case 1024:
                return "hdmi";
            case 2048:
                return "analog_dock";
            case 4096:
                return "digital_dock";
            case 8192:
                return "usb_accessory";
            case 16384:
                return "usb_device";
            case 32768:
                return "remote_submix";
            case 65536:
                return DEVICE_OUT_TELEPHONY_TX_NAME;
            case 131072:
                return "line";
            case 262144:
                return "hdmi_arc";
            case 262145:
                return "hdmi_earc";
            case 524288:
                return "spdif";
            case 1048576:
                return DEVICE_OUT_FM_NAME;
            case 2097152:
                return DEVICE_OUT_AUX_LINE_NAME;
            case 4194304:
                return DEVICE_OUT_SPEAKER_SAFE_NAME;
            case 8388608:
                return "ip";
            case 8388609:
                return DEVICE_OUT_MULTICHANNEL_GROUP_NAME;
            case 16777216:
                return "bus";
            case 33554432:
                return "proxy";
            case 67108864:
                return "usb_headset";
            case 134217728:
                return DEVICE_OUT_HEARING_AID_NAME;
            case 268435456:
                return DEVICE_OUT_ECHO_CANCELLER_NAME;
            case 536870912:
                return "ble_headset";
            case 536870913:
                return DEVICE_OUT_BLE_SPEAKER_NAME;
            case 536870914:
                return DEVICE_OUT_BLE_BROADCAST_NAME;
            default:
                return "0x" + Integer.toHexString(i);
        }
    }

    public static String getInputDeviceName(int i) {
        switch (i) {
            case DEVICE_IN_COMMUNICATION /* -2147483647 */:
                return DEVICE_IN_COMMUNICATION_NAME;
            case DEVICE_IN_AMBIENT /* -2147483646 */:
                return DEVICE_IN_AMBIENT_NAME;
            case -2147483644:
                return DEVICE_IN_BUILTIN_MIC_NAME;
            case -2147483640:
                return "bt_sco_hs";
            case -2147483632:
                return "headset";
            case -2147483616:
                return "aux_digital";
            case -2147483584:
                return DEVICE_IN_TELEPHONY_RX_NAME;
            case -2147483520:
                return DEVICE_IN_BACK_MIC_NAME;
            case DEVICE_IN_REMOTE_SUBMIX /* -2147483392 */:
                return "remote_submix";
            case -2147483136:
                return "analog_dock";
            case -2147482624:
                return "digital_dock";
            case -2147481600:
                return "usb_accessory";
            case -2147479552:
                return "usb_device";
            case -2147475456:
                return DEVICE_IN_FM_TUNER_NAME;
            case -2147467264:
                return DEVICE_IN_TV_TUNER_NAME;
            case -2147450880:
                return "line";
            case -2147418112:
                return "spdif";
            case DEVICE_IN_BLUETOOTH_A2DP /* -2147352576 */:
                return "bt_a2dp";
            case -2147221504:
                return DEVICE_IN_LOOPBACK_NAME;
            case DEVICE_IN_IP /* -2146959360 */:
                return "ip";
            case DEVICE_IN_BUS /* -2146435072 */:
                return "bus";
            case DEVICE_IN_PROXY /* -2130706432 */:
                return "proxy";
            case DEVICE_IN_USB_HEADSET /* -2113929216 */:
                return "usb_headset";
            case DEVICE_IN_BLUETOOTH_BLE /* -2080374784 */:
                return DEVICE_IN_BLUETOOTH_BLE_NAME;
            case -2013265920:
                return "hdmi_arc";
            case -2013265919:
                return "hdmi_earc";
            case -1879048192:
                return DEVICE_IN_ECHO_REFERENCE_NAME;
            case -1610612736:
                return "ble_headset";
            default:
                return Integer.toString(i);
        }
    }

    public static String getDeviceName(int i) {
        if (isInputDevice(i)) {
            return getInputDeviceName(i);
        }
        return getOutputDeviceName(i);
    }

    public static String forceUseConfigToString(int i) {
        switch (i) {
            case 0:
                return "FORCE_NONE";
            case 1:
                return "FORCE_SPEAKER";
            case 2:
                return "FORCE_HEADPHONES";
            case 3:
                return "FORCE_BT_SCO";
            case 4:
                return "FORCE_BT_A2DP";
            case 5:
                return "FORCE_WIRED_ACCESSORY";
            case 6:
                return "FORCE_BT_CAR_DOCK";
            case 7:
                return "FORCE_BT_DESK_DOCK";
            case 8:
                return "FORCE_ANALOG_DOCK";
            case 9:
                return "FORCE_DIGITAL_DOCK";
            case 10:
                return "FORCE_NO_BT_A2DP";
            case 11:
                return "FORCE_SYSTEM_ENFORCED";
            case 12:
                return "FORCE_HDMI_SYSTEM_AUDIO_ENFORCED";
            case 13:
                return "FORCE_ENCODED_SURROUND_NEVER";
            case 14:
                return "FORCE_ENCODED_SURROUND_ALWAYS";
            case 15:
                return "FORCE_ENCODED_SURROUND_MANUAL";
            case 16:
                return "FORCE_BT_BLE";
            default:
                return "unknown config (" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static String forceUseUsageToString(int i) {
        switch (i) {
            case 0:
                return "FOR_COMMUNICATION";
            case 1:
                return "FOR_MEDIA";
            case 2:
                return "FOR_RECORD";
            case 3:
                return "FOR_DOCK";
            case 4:
                return "FOR_SYSTEM";
            case 5:
                return "FOR_HDMI_SYSTEM_AUDIO";
            case 6:
                return "FOR_ENCODED_SURROUND";
            case 7:
                return "FOR_VIBRATE_RINGING";
            case 8:
                return "FOR_FMRADIO";
            default:
                return "unknown usage (" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static int setStreamVolumeIndexAS(int i, int i2, boolean z, int i3) {
        return setStreamVolumeIndex(i, i2, z, i3);
    }

    public static int setDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, int i2) {
        return setDeviceConnectionState(audioDeviceAttributes, i, i2, false);
    }

    public static int setDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, int i2, boolean z) {
        android.media.audio.common.AudioPort audioPortApi2aidl_AudioDeviceAttributes_AudioPort = AidlConversion.api2aidl_AudioDeviceAttributes_AudioPort(audioDeviceAttributes);
        Parcel parcelObtain = Parcel.obtain();
        audioPortApi2aidl_AudioDeviceAttributes_AudioPort.writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        try {
            return setDeviceConnectionState(i, parcelObtain, i2, z);
        } finally {
            parcelObtain.recycle();
        }
    }

    public static int setPhoneState(int i) {
        Log.w(TAG, "Do not use this method! Use AudioManager.setMode() instead.");
        return 0;
    }

    @Deprecated
    public static int getDevicesForStream(int i) {
        return getDeviceMaskFromSet(generateAudioDeviceTypesSet(getDevicesForAttributes(android.media.audiopolicy.AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i), true)));
    }

    public static int getDeviceMaskFromSet(Set<Integer> set) {
        int iIntValue = 0;
        int iIntValue2 = Integer.MIN_VALUE;
        for (Integer num : set) {
            if ((num.intValue() & (num.intValue() - 1) & Integer.MAX_VALUE) == 0 || (num.intValue() & (-536870915)) == 0) {
                iIntValue |= num.intValue();
                iIntValue2 &= num.intValue();
            } else {
                Log.v(TAG, "getDeviceMaskFromSet skipping multi-bit device value " + num);
            }
        }
        if (!set.isEmpty() && iIntValue2 != (iIntValue & Integer.MIN_VALUE)) {
            Log.e(TAG, "getDeviceMaskFromSet: Invalid set: " + deviceSetToString(set));
        }
        return iIntValue;
    }

    public static String deviceSetToString(Set<Integer> set) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Integer num : set) {
            int i2 = i + 1;
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(getDeviceName(num.intValue()));
            sb.append(NavigationBarInflaterView.KEY_CODE_START + Integer.toHexString(num.intValue()) + NavigationBarInflaterView.KEY_CODE_END);
            i = i2;
        }
        return sb.toString();
    }

    public static ArrayList<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes audioAttributes, boolean z) {
        Objects.requireNonNull(audioAttributes);
        AudioDeviceAttributes[] audioDeviceAttributesArr = new AudioDeviceAttributes[4];
        int devicesForAttributes = getDevicesForAttributes(audioAttributes, audioDeviceAttributesArr, z);
        ArrayList<AudioDeviceAttributes> arrayList = new ArrayList<>();
        if (devicesForAttributes == 0) {
            for (int i = 0; i < 4; i++) {
                AudioDeviceAttributes audioDeviceAttributes = audioDeviceAttributesArr[i];
                if (audioDeviceAttributes != null) {
                    arrayList.add(audioDeviceAttributes);
                }
            }
            return arrayList;
        }
        Log.e(TAG, "error " + devicesForAttributes + " in getDevicesForAttributes attributes: " + audioAttributes + " forVolume: " + z);
        return arrayList;
    }

    static int getOffloadSupport(AudioFormat audioFormat, AudioAttributes audioAttributes) {
        return native_get_offload_support(audioFormat.getEncoding(), audioFormat.getSampleRate(), audioFormat.getChannelMask(), audioFormat.getChannelIndexMask(), audioAttributes.getVolumeControlStream());
    }

    public static int setDevicesRoleForStrategy(int i, int i2, List<AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        int[] iArr = new int[list.size()];
        String[] strArr = new String[list.size()];
        for (int i3 = 0; i3 < list.size(); i3++) {
            iArr[i3] = list.get(i3).getInternalType();
            strArr[i3] = list.get(i3).getAddress();
        }
        return setDevicesRoleForStrategy(i, i2, iArr, strArr);
    }

    public static int removeDevicesRoleForStrategy(int i, int i2, List<AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        int[] iArr = new int[list.size()];
        String[] strArr = new String[list.size()];
        for (int i3 = 0; i3 < list.size(); i3++) {
            iArr[i3] = list.get(i3).getInternalType();
            strArr[i3] = list.get(i3).getAddress();
        }
        return removeDevicesRoleForStrategy(i, i2, iArr, strArr);
    }

    private static Pair<int[], String[]> populateInputDevicesTypeAndAddress(List<AudioDeviceAttributes> list) {
        int[] iArr = new int[list.size()];
        String[] strArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            int internalType = list.get(i).getInternalType();
            iArr[i] = internalType;
            if (internalType == 0) {
                iArr[i] = AudioDeviceInfo.convertDeviceTypeToInternalInputDevice(list.get(i).getType(), list.get(i).getAddress());
            }
            strArr[i] = list.get(i).getAddress();
        }
        return new Pair<>(iArr, strArr);
    }

    public static int setDevicesRoleForCapturePreset(int i, int i2, List<AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        Pair<int[], String[]> pairPopulateInputDevicesTypeAndAddress = populateInputDevicesTypeAndAddress(list);
        return setDevicesRoleForCapturePreset(i, i2, pairPopulateInputDevicesTypeAndAddress.first, pairPopulateInputDevicesTypeAndAddress.second);
    }

    public static int addDevicesRoleForCapturePreset(int i, int i2, List<AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        Pair<int[], String[]> pairPopulateInputDevicesTypeAndAddress = populateInputDevicesTypeAndAddress(list);
        return addDevicesRoleForCapturePreset(i, i2, pairPopulateInputDevicesTypeAndAddress.first, pairPopulateInputDevicesTypeAndAddress.second);
    }

    public static int removeDevicesRoleForCapturePreset(int i, int i2, List<AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        Pair<int[], String[]> pairPopulateInputDevicesTypeAndAddress = populateInputDevicesTypeAndAddress(list);
        return removeDevicesRoleForCapturePreset(i, i2, pairPopulateInputDevicesTypeAndAddress.first, pairPopulateInputDevicesTypeAndAddress.second);
    }

    public static ISpatializer getSpatializer(INativeSpatializerCallback iNativeSpatializerCallback) {
        return ISpatializer.Stub.asInterface(nativeGetSpatializer(iNativeSpatializerCallback));
    }

    public static ISoundDose getSoundDoseInterface(ISoundDoseCallback iSoundDoseCallback) {
        return ISoundDose.Stub.asInterface(nativeGetSoundDose(iSoundDoseCallback));
    }

    public static int getDefaultStreamVolume(int i) {
        return DEFAULT_STREAM_VOLUME[i];
    }

    public static String streamToString(int i) {
        if (i >= 0) {
            String[] strArr = STREAM_NAMES;
            if (i < strArr.length) {
                return strArr[i];
            }
        }
        if (i == Integer.MIN_VALUE) {
            return "USE_DEFAULT_STREAM_TYPE";
        }
        return "UNKNOWN_STREAM_" + i;
    }

    public static int getPlatformType(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) {
            return 3;
        }
        if (Rune.SEC_AUDIO_ATT_SOFTPHONE || ((TelephonyManager) context.getSystemService(TelephonyManager.class)).isVoiceCapable()) {
            return 1;
        }
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK) ? 2 : 0;
    }

    public static boolean isSingleVolume(Context context) {
        return getPlatformType(context) == 2 || context.getResources().getBoolean(R.bool.config_single_volume);
    }

    public static Set<Integer> generateAudioDeviceTypesSet(List<AudioDeviceAttributes> list) {
        TreeSet treeSet = new TreeSet();
        Iterator<AudioDeviceAttributes> it = list.iterator();
        while (it.hasNext()) {
            treeSet.add(Integer.valueOf(it.next().getInternalType()));
        }
        return treeSet;
    }

    public static Set<Integer> intersectionAudioDeviceTypes(Set<Integer> set, Set<Integer> set2) {
        TreeSet treeSet = new TreeSet(set);
        treeSet.retainAll(set2);
        return treeSet;
    }

    public static boolean isSingleAudioDeviceType(Set<Integer> set, int i) {
        return set.size() == 1 && set.contains(Integer.valueOf(i));
    }

    public static boolean isLeAudioDeviceType(int i) {
        return DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(i));
    }

    public static int setPolicyParameters(String str) {
        return setParameters("AUDIO_POLICY;" + str);
    }
}
