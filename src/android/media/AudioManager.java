package android.media;

import android.annotation.SystemApi;
import android.app.PendingIntent;
import android.app.PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0;
import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import android.app.compat.CompatChanges;
import android.bluetooth.BluetoothCodecConfig;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudioCodecConfig;
import android.companion.virtual.VirtualDeviceManager;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.CallbackUtil;
import android.media.IAudioFocusDispatcher;
import android.media.IAudioModeDispatcher;
import android.media.IAudioServerStateDispatcher;
import android.media.IAudioService;
import android.media.ICapturePresetDevicesRoleDispatcher;
import android.media.ICommunicationDeviceDispatcher;
import android.media.IDevicesForAttributesCallback;
import android.media.IMuteAwaitConnectionCallback;
import android.media.IPlaybackConfigDispatcher;
import android.media.IPreferredMixerAttributesDispatcher;
import android.media.IRecordingConfigDispatcher;
import android.media.IStrategyNonDefaultDevicesDispatcher;
import android.media.IStrategyPreferredDevicesDispatcher;
import android.media.IStreamAliasingDispatcher;
import android.media.audiopolicy.AudioPolicy;
import android.media.audiopolicy.IAudioVolumeChangeDispatcher;
import android.media.projection.MediaProjection;
import android.media.session.MediaSessionLegacyHelper;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.IpcDataCache;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.KeyEvent;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.AudioFxHelper;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.media.SemAudioSystem;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class AudioManager {
    public static final String ACTION_AUDIO_BECOMING_NOISY = "android.media.AUDIO_BECOMING_NOISY";
    public static final String ACTION_HDMI_AUDIO_PLUG = "android.media.action.HDMI_AUDIO_PLUG";
    public static final String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final String ACTION_MICROPHONE_MUTE_CHANGED = "android.media.action.MICROPHONE_MUTE_CHANGED";

    @Deprecated
    public static final String ACTION_SCO_AUDIO_STATE_CHANGED = "android.media.SCO_AUDIO_STATE_CHANGED";
    public static final String ACTION_SCO_AUDIO_STATE_UPDATED = "android.media.ACTION_SCO_AUDIO_STATE_UPDATED";
    public static final String ACTION_SPEAKERPHONE_STATE_CHANGED = "android.media.action.SPEAKERPHONE_STATE_CHANGED";

    @SystemApi
    public static final String ACTION_VOLUME_CHANGED = "android.media.VOLUME_CHANGED_ACTION";
    public static final int ADJUST_LOWER = -1;
    public static final int ADJUST_MUTE = -100;
    public static final int ADJUST_RAISE = 1;
    public static final int ADJUST_SAME = 0;
    public static final int ADJUST_TOGGLE_MUTE = 101;
    public static final int ADJUST_UNMUTE = 100;
    public static final int AUDIOFOCUS_FLAGS_APPS = 3;
    public static final int AUDIOFOCUS_FLAGS_SYSTEM = 7;

    @SystemApi
    public static final int AUDIOFOCUS_FLAG_DELAY_OK = 1;

    @SystemApi
    public static final int AUDIOFOCUS_FLAG_LOCK = 4;

    @SystemApi
    public static final int AUDIOFOCUS_FLAG_PAUSES_ON_DUCKABLE_LOSS = 2;
    public static final int AUDIOFOCUS_FLAG_TEST = 8;
    public static final int AUDIOFOCUS_GAIN = 1;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE = 4;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    public static final int AUDIOFOCUS_LOSS = -1;
    public static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
    public static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;
    public static final int AUDIOFOCUS_NONE = 0;
    public static final int AUDIOFOCUS_REQUEST_DELAYED = 2;
    public static final int AUDIOFOCUS_REQUEST_FAILED = 0;
    public static final int AUDIOFOCUS_REQUEST_GRANTED = 1;
    public static final int AUDIOFOCUS_REQUEST_WAITING_FOR_EXT_POLICY = 100;
    private static final int AUDIOPORT_GENERATION_INIT = 0;
    public static final int AUDIO_DEVICE_CATEGORY_CARKIT = 4;
    public static final int AUDIO_DEVICE_CATEGORY_HEADPHONES = 3;
    public static final int AUDIO_DEVICE_CATEGORY_HEARING_AID = 6;
    public static final int AUDIO_DEVICE_CATEGORY_OTHER = 1;
    public static final int AUDIO_DEVICE_CATEGORY_RECEIVER = 7;
    public static final int AUDIO_DEVICE_CATEGORY_SPEAKER = 2;
    public static final int AUDIO_DEVICE_CATEGORY_UNKNOWN = 0;
    public static final int AUDIO_DEVICE_CATEGORY_WATCH = 5;
    public static final int AUDIO_SESSION_ID_GENERATE = 0;
    public static final long CALL_REDIRECTION_AUDIO_MODES = 189472651;
    public static final int CALL_REDIRECT_NONE = 0;
    public static final int CALL_REDIRECT_PSTN = 1;
    public static final int CALL_REDIRECT_VOIP = 2;
    public static final int CSD_WARNING_ACCUMULATION_START = 4;
    public static final int CSD_WARNING_DOSE_REACHED_1X = 1;
    public static final int CSD_WARNING_DOSE_REPEATED_5X = 2;
    public static final int CSD_WARNING_MOMENTARY_EXPOSURE = 3;
    private static final boolean DEBUG = false;

    @SystemApi
    public static final int DEVICE_CONNECTION_STATE_CONNECTED = 1;

    @SystemApi
    public static final int DEVICE_CONNECTION_STATE_DISCONNECTED = 0;
    public static final int DEVICE_IN_ANLG_DOCK_HEADSET = -2147483136;
    public static final int DEVICE_IN_BACK_MIC = -2147483520;
    public static final int DEVICE_IN_BLE_HEADSET = -1610612736;
    public static final int DEVICE_IN_BLUETOOTH_SCO_HEADSET = -2147483640;
    public static final int DEVICE_IN_BUILTIN_MIC = -2147483644;
    public static final int DEVICE_IN_DGTL_DOCK_HEADSET = -2147482624;
    public static final int DEVICE_IN_ECHO_REFERENCE = -1879048192;
    public static final int DEVICE_IN_FM_TUNER = -2147475456;
    public static final int DEVICE_IN_HDMI = -2147483616;
    public static final int DEVICE_IN_HDMI_ARC = -2013265920;
    public static final int DEVICE_IN_HDMI_EARC = -2013265919;
    public static final int DEVICE_IN_LINE = -2147450880;
    public static final int DEVICE_IN_LOOPBACK = -2147221504;
    public static final int DEVICE_IN_SPDIF = -2147418112;
    public static final int DEVICE_IN_TELEPHONY_RX = -2147483584;
    public static final int DEVICE_IN_TV_TUNER = -2147467264;
    public static final int DEVICE_IN_USB_ACCESSORY = -2147481600;
    public static final int DEVICE_IN_USB_DEVICE = -2147479552;
    public static final int DEVICE_IN_WIRED_HEADSET = -2147483632;
    public static final int DEVICE_NONE = 0;
    public static final int DEVICE_OUT_ANLG_DOCK_HEADSET = 2048;
    public static final int DEVICE_OUT_AUX_DIGITAL = 1024;
    public static final int DEVICE_OUT_BLE_BROADCAST = 536870914;
    public static final int DEVICE_OUT_BLE_HEADSET = 536870912;
    public static final int DEVICE_OUT_BLE_SPEAKER = 536870913;
    public static final int DEVICE_OUT_BLUETOOTH_A2DP = 128;
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    public static final int DEVICE_OUT_BLUETOOTH_SCO = 16;
    public static final int DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    public static final int DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    public static final int DEVICE_OUT_DEFAULT = 1073741824;
    public static final int DEVICE_OUT_DGTL_DOCK_HEADSET = 4096;
    public static final int DEVICE_OUT_EARPIECE = 1;
    public static final int DEVICE_OUT_ECHO_CANCELLER = 268435456;
    public static final int DEVICE_OUT_FM = 1048576;
    public static final int DEVICE_OUT_HDMI = 1024;
    public static final int DEVICE_OUT_HDMI_ARC = 262144;
    public static final int DEVICE_OUT_HDMI_EARC = 262145;
    public static final int DEVICE_OUT_LINE = 131072;
    public static final int DEVICE_OUT_MULTICHANNEL_GROUP = 8388609;
    public static final int DEVICE_OUT_REMOTE_SUBMIX = 32768;
    public static final int DEVICE_OUT_SPDIF = 524288;
    public static final int DEVICE_OUT_SPEAKER = 2;
    public static final int DEVICE_OUT_TELEPHONY_TX = 65536;
    public static final int DEVICE_OUT_USB_ACCESSORY = 8192;
    public static final int DEVICE_OUT_USB_DEVICE = 16384;
    public static final int DEVICE_OUT_USB_HEADSET = 67108864;
    public static final int DEVICE_OUT_WIRED_HEADPHONE = 8;
    public static final int DEVICE_OUT_WIRED_HEADSET = 4;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE = 3;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY = 5;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_MULTI_MODE = 4;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_FIXED = 2;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_FULL = 1;
    public static final int DEVICE_VOLUME_BEHAVIOR_UNSET = -1;

    @SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_VARIABLE = 0;
    public static final int DIRECT_PLAYBACK_BITSTREAM_SUPPORTED = 4;
    public static final int DIRECT_PLAYBACK_NOT_SUPPORTED = 0;
    public static final int DIRECT_PLAYBACK_OFFLOAD_GAPLESS_SUPPORTED = 3;
    public static final int DIRECT_PLAYBACK_OFFLOAD_SUPPORTED = 1;
    public static final int ENCODED_SURROUND_OUTPUT_ALWAYS = 2;
    public static final int ENCODED_SURROUND_OUTPUT_AUTO = 0;
    public static final int ENCODED_SURROUND_OUTPUT_MANUAL = 3;
    public static final int ENCODED_SURROUND_OUTPUT_NEVER = 1;
    public static final int ENCODED_SURROUND_OUTPUT_UNKNOWN = -1;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    public static final int ERROR_NO_INIT = -5;
    public static final int ERROR_PERMISSION_DENIED = -4;
    public static final String EXTRA_AUDIO_PLUG_STATE = "android.media.extra.AUDIO_PLUG_STATE";
    public static final String EXTRA_ENCODINGS = "android.media.extra.ENCODINGS";
    public static final String EXTRA_MASTER_VOLUME_MUTED = "android.media.EXTRA_MASTER_VOLUME_MUTED";
    public static final String EXTRA_MAX_CHANNEL_COUNT = "android.media.extra.MAX_CHANNEL_COUNT";
    public static final String EXTRA_PREV_VOLUME_STREAM_DEVICES = "android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES";
    public static final String EXTRA_PREV_VOLUME_STREAM_VALUE = "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE";
    public static final String EXTRA_RINGER_MODE = "android.media.EXTRA_RINGER_MODE";
    public static final String EXTRA_SCO_AUDIO_PREVIOUS_STATE = "android.media.extra.SCO_AUDIO_PREVIOUS_STATE";
    public static final String EXTRA_SCO_AUDIO_STATE = "android.media.extra.SCO_AUDIO_STATE";
    public static final String EXTRA_STREAM_VOLUME_MUTED = "android.media.EXTRA_STREAM_VOLUME_MUTED";
    public static final String EXTRA_VIBRATE_SETTING = "android.media.EXTRA_VIBRATE_SETTING";
    public static final String EXTRA_VIBRATE_TYPE = "android.media.EXTRA_VIBRATE_TYPE";
    public static final String EXTRA_VOLUME_STREAM_DEVICES = "android.media.EXTRA_VOLUME_STREAM_DEVICES";

    @SystemApi
    public static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    public static final String EXTRA_VOLUME_STREAM_TYPE_ALIAS = "android.media.EXTRA_VOLUME_STREAM_TYPE_ALIAS";

    @SystemApi
    public static final String EXTRA_VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE";
    private static final int EXT_FOCUS_POLICY_TIMEOUT_MS = 250;
    public static final int FLAG_ABSOLUTE_VOLUME = 8192;
    public static final int FLAG_ACTIVE_MEDIA_ONLY = 512;
    public static final int FLAG_ADJUST_LOWER = 65536;
    public static final int FLAG_ADJUST_RAISE = 131072;
    public static final int FLAG_ALLOW_RINGER_MODES = 2;

    @SystemApi
    public static final int FLAG_BLUETOOTH_ABS_VOLUME = 64;
    public static final int FLAG_DISMISS_UI_WARNINGS = 134217728;
    public static final int FLAG_DISPLAY_VOLUME_CONTROL = 4194304;
    public static final int FLAG_DUAL_A2DP_MODE = 524288;
    public static final int FLAG_FINE_VOLUME = 1048576;
    public static final int FLAG_FIXED_SCO_VOLUME = 262144;
    public static final int FLAG_FIXED_VOLUME = 32;

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public static final int FLAG_FROM_KEY = 4096;
    public static final int FLAG_HDMI_SYSTEM_AUDIO_VOLUME = 256;
    public static final int FLAG_MULTI_AUDIO_FOCUS = 268435456;
    public static final int FLAG_MULTI_SOUND = 8388608;
    private static final TreeMap<Integer, String> FLAG_NAMES;
    public static final int FLAG_NO_VOICE_ASSISTANT = 2097152;
    public static final int FLAG_PLAY_SOUND = 4;
    public static final int FLAG_REMOTE_MIC = 67108864;
    public static final int FLAG_REMOVE_SOUND_AND_VIBRATE = 8;
    public static final int FLAG_SEC_SOUND_EFFECT_BASE = 99;
    public static final int FLAG_SHOW_CSD_100_WARNINGS = 536870912;
    public static final int FLAG_SHOW_SILENT_HINT = 128;
    public static final int FLAG_SHOW_UI = 1;
    public static final int FLAG_SHOW_UI_WARNINGS = 1024;
    public static final int FLAG_SHOW_VIBRATE_HINT = 2048;
    public static final int FLAG_SKIP_RINGER_MODES = 16777216;
    public static final int FLAG_VIBRATE = 16;
    public static final String FM_RADIO = "FM_RADIO";
    private static final String FOCUS_CLIENT_ID_STRING = "android_audio_focus_client_id";
    public static final int FX_BACK = 10;
    public static final int FX_FOCUS_NAVIGATION_DOWN = 2;
    public static final int FX_FOCUS_NAVIGATION_LEFT = 3;
    public static final int FX_FOCUS_NAVIGATION_REPEAT_1 = 12;
    public static final int FX_FOCUS_NAVIGATION_REPEAT_2 = 13;
    public static final int FX_FOCUS_NAVIGATION_REPEAT_3 = 14;
    public static final int FX_FOCUS_NAVIGATION_REPEAT_4 = 15;
    public static final int FX_FOCUS_NAVIGATION_RIGHT = 4;
    public static final int FX_FOCUS_NAVIGATION_UP = 1;
    public static final int FX_HOME = 11;
    public static final int FX_KEYPRESS_DELETE = 7;
    public static final int FX_KEYPRESS_INVALID = 9;
    public static final int FX_KEYPRESS_RETURN = 8;
    public static final int FX_KEYPRESS_SPACEBAR = 6;
    public static final int FX_KEYPRESS_STANDARD = 5;
    public static final int FX_KEY_CLICK = 0;
    public static final int GET_DEVICES_ALL = 3;
    public static final int GET_DEVICES_INPUTS = 1;
    public static final int GET_DEVICES_OUTPUTS = 2;
    public static final String INTERNAL_RINGER_MODE_CHANGED_ACTION = "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION";
    public static final String MASTER_MUTE_CHANGED_ACTION = "android.media.MASTER_MUTE_CHANGED_ACTION";
    public static final int MIC_INPUT_CONTROL_MODE_CALL_FOCUS_ON_VOICE = 4;
    public static final int MIC_INPUT_CONTROL_MODE_CALL_STANDARD = 3;
    public static final int MIC_INPUT_CONTROL_MODE_FOCUS_ON_ALL_SOUNDS = 2;
    public static final int MIC_INPUT_CONTROL_MODE_FOCUS_ON_VOICE = 1;
    public static final int MIC_INPUT_CONTROL_MODE_STANDARD = 0;
    public static final int MODE_CALL_REDIRECT = 5;
    public static final int MODE_CALL_SCREENING = 4;
    public static final int MODE_COMMUNICATION_REDIRECT = 6;
    public static final int MODE_CURRENT = -1;
    public static final int MODE_INVALID = -2;
    public static final int MODE_IN_CALL = 2;
    public static final int MODE_IN_COMMUNICATION = 3;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RINGTONE = 1;
    private static final int MSG_DEVICES_CALLBACK_REGISTERED = 0;
    private static final int MSG_DEVICES_DEVICES_ADDED = 1;
    private static final int MSG_DEVICES_DEVICES_REMOVED = 2;
    private static final int MSSG_FOCUS_CHANGE = 0;
    private static final int MSSG_PLAYBACK_CONFIG_CHANGE = 2;
    private static final int MSSG_RECORDING_CONFIG_CHANGE = 1;
    public static final int NUM_NAVIGATION_REPEAT_SOUND_EFFECTS = 4;
    public static final int NUM_SOUND_EFFECTS = 23;

    @Deprecated
    public static final int NUM_STREAMS = 5;
    public static final int PLAYBACK_OFFLOAD_GAPLESS_SUPPORTED = 2;
    public static final int PLAYBACK_OFFLOAD_NOT_SUPPORTED = 0;
    public static final int PLAYBACK_OFFLOAD_SUPPORTED = 1;
    public static final String PROPERTY_OUTPUT_FRAMES_PER_BUFFER = "android.media.property.OUTPUT_FRAMES_PER_BUFFER";
    public static final String PROPERTY_OUTPUT_SAMPLE_RATE = "android.media.property.OUTPUT_SAMPLE_RATE";
    public static final String PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED = "android.media.property.SUPPORT_AUDIO_SOURCE_UNPROCESSED";
    public static final String PROPERTY_SUPPORT_MIC_NEAR_ULTRASOUND = "android.media.property.SUPPORT_MIC_NEAR_ULTRASOUND";
    public static final String PROPERTY_SUPPORT_SPEAKER_NEAR_ULTRASOUND = "android.media.property.SUPPORT_SPEAKER_NEAR_ULTRASOUND";
    private static final int QUERY_VOL = 3;
    private static final int QUERY_VOL_MAX = 2;
    private static final int QUERY_VOL_MIN = 1;
    public static final int RECORDER_STATE_STARTED = 0;
    public static final int RECORDER_STATE_STOPPED = 1;
    public static final int RECORD_CONFIG_EVENT_NONE = -1;
    public static final int RECORD_CONFIG_EVENT_POPUP = 99;
    public static final int RECORD_CONFIG_EVENT_RELEASE = 3;
    public static final int RECORD_CONFIG_EVENT_START = 0;
    public static final int RECORD_CONFIG_EVENT_STOP = 1;
    public static final int RECORD_CONFIG_EVENT_UPDATE = 2;
    public static final int RECORD_RIID_INVALID = -1;
    public static final long RETURN_DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY = 240663182;
    public static final String RINGER_MODE_CHANGED_ACTION = "android.media.RINGER_MODE_CHANGED";
    public static final int RINGER_MODE_MAX = 2;
    public static final int RINGER_MODE_NORMAL = 2;
    public static final int RINGER_MODE_SILENT = 0;
    public static final int RINGER_MODE_VIBRATE = 1;

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
    public static final int SCO_AUDIO_STATE_CONNECTED = 1;
    public static final int SCO_AUDIO_STATE_CONNECTING = 2;
    public static final int SCO_AUDIO_STATE_DISCONNECTED = 0;
    public static final int SCO_AUDIO_STATE_ERROR = -1;
    public static final String SEM_ACTION_AUDIO_BECOMING_NOISY = "android.media.AUDIO_BECOMING_NOISY_SEC";
    public static final String SEM_ACTION_AUDIO_MODE_CHANGED = "android.samsung.media.action.AUDIO_MODE";
    public static final int SEM_CONTROL_MODE_INVALID = -1;
    public static final int SEM_CONTROL_MODE_MUTE = 1;
    public static final int SEM_CONTROL_MODE_UNMUTE = 0;
    public static final int SEM_CONTROL_MODE_VOLUME_DOWN = 2;
    public static final String SEM_EXTRA_AUDIO_MODE = "android.samsung.media.extra.AUDIO_MODE";
    public static final String SEM_EXTRA_VOLUME_SHOW_UI = "android.media.EXTRA_VOLUME_SHOW_UI";
    public static final String SEM_EXTRA_VOLUME_STREAM_DEVICES = "android.media.EXTRA_VOLUME_STREAM_DEVICES";
    public static final String SEM_EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    public static final String SEM_EXTRA_VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE";
    public static final int SEM_FLAG_UPDATE_STATE = 33554432;
    public static final String SEM_OUT_DEVICE = "audioParam;l_device_current_output";
    public static final int SEM_SITUATION_BURST_SHOT = 9;
    public static final int SEM_SITUATION_CALL_CONNECTION = 14;
    public static final int SEM_SITUATION_CALL_WAITING = 15;
    public static final int SEM_SITUATION_CAMCORDING_START = 5;
    public static final int SEM_SITUATION_CHARGER_CONNECTION = 16;
    public static final int SEM_SITUATION_HEADSET_VOLUME = 2;
    public static final int SEM_SITUATION_IMPLICIT_VOLUME = 0;
    public static final int SEM_SITUATION_KEYBOARD = 2;
    public static final int SEM_SITUATION_KEY_TONE = 0;
    public static final int SEM_SITUATION_LOCK_SCREEN = 4;
    public static final int SEM_SITUATION_LOW_BATTERY = 11;
    public static final int SEM_SITUATION_MIDI = 6;
    public static final int SEM_SITUATION_SHUTTER = 3;
    public static final int SEM_SITUATION_SPEAKER_VOLUME = 1;
    public static final int SEM_SITUATION_TOUCH_TONE = 1;
    public static final int SEM_SITUATION_UNLOCK_SCREEN = 7;
    public static final int SEM_SITUATION_VIDEO = 7;
    public static final int SEM_SOUND_DRAG_AND_DROP = 106;
    public static final int SEM_SOUND_HW_TOUCH = 102;
    public static final int SEM_SOUND_TOUCH = 100;
    public static final int SEM_STREAM_BIXBY = 6;
    public static final int SEM_STREAM_BLUETOOTH_SCO = 4;
    public static final String SEM_STREAM_DEVICES_CHANGED_ACTION = "android.media.STREAM_DEVICES_CHANGED_ACTION";
    public static final int SEM_STREAM_FM_RADIO = 1;
    public static final int SEM_STREAM_SYSTEM_ENFORCED = 5;
    public static final int SEM_STREAM_VIDEO_CALL = 2;
    public static final int SEM_STREAM_VOICENOTE = 3;
    public static final String SEM_VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    public static final int SOUND_DETACH = 106;
    public static final int SOUND_SILENT_MODE_OFF = 101;
    public static final int SOUND_TIME_PICKER_FAST = 104;
    public static final int SOUND_TIME_PICKER_SCROLL = 103;
    public static final int SOUND_TIME_PICKER_SLOW = 105;
    public static final int STREAM_ACCESSIBILITY = 10;
    public static final int STREAM_ALARM = 4;

    @SystemApi
    public static final int STREAM_ASSISTANT = 11;

    @SystemApi
    public static final int STREAM_BLUETOOTH_SCO = 6;
    public static final String STREAM_DEVICES_CHANGED_ACTION = "android.media.STREAM_DEVICES_CHANGED_ACTION";
    public static final int STREAM_DTMF = 8;
    public static final int STREAM_FM_RADIO = 3;
    public static final int STREAM_MUSIC = 3;
    public static final String STREAM_MUTE_CHANGED_ACTION = "android.media.STREAM_MUTE_CHANGED_ACTION";
    public static final int STREAM_NOTIFICATION = 5;
    public static final int STREAM_RING = 2;
    public static final int STREAM_SEC_VOICE_COMMUNICATION = 0;
    public static final int STREAM_SYSTEM = 1;
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    public static final int STREAM_TTS = 9;
    public static final int STREAM_VIDEO_CALL = 0;
    public static final int STREAM_VOICE_CALL = 0;

    @SystemApi
    public static final int SUCCESS = 0;
    private static final String TAG = "AudioManager";
    public static final int USE_DEFAULT_STREAM_TYPE = Integer.MIN_VALUE;
    public static final String VIBRATE_SETTING_CHANGED_ACTION = "android.media.VIBRATE_SETTING_CHANGED";
    public static final int VIBRATE_SETTING_OFF = 0;
    public static final int VIBRATE_SETTING_ON = 1;
    public static final int VIBRATE_SETTING_ONLY_SILENT = 2;
    public static final int VIBRATE_TYPE_NOTIFICATION = 1;
    public static final int VIBRATE_TYPE_RINGER = 0;
    public static final String VOLUME_CACHING_API = "getStreamVolume";
    private static final int VOLUME_CACHING_SIZE = 16;
    public static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    public static final String VOLUME_MAX_CACHING_API = "getStreamMaxVolume";
    public static final String VOLUME_MIN_CACHING_API = "getStreamMinVolume";
    private static final float VOLUME_MIN_DB = -758.0f;
    private static ArrayList<AudioPatch> sAudioPatchesCached;
    private static int sAudioPortGeneration;
    private static Object sAudioPortGenerationLock;
    private static ArrayList<AudioPort> sAudioPortsCached;
    private static WeakReference<Context> sContext;
    private static ArrayList<AudioPort> sPreviousAudioPortsCached;
    private static IAudioService sService;
    static Object sSetDeviceForceLock;
    private Context mApplicationContext;
    private final IAudioFocusDispatcher mAudioFocusDispatcher;
    private final ConcurrentHashMap<String, FocusRequestInfo> mAudioFocusIdListenerMap;
    private AudioServerStateCallback mAudioServerStateCb;
    private final Object mAudioServerStateCbLock;
    private final IAudioServerStateDispatcher mAudioServerStateDispatcher;
    private Executor mAudioServerStateExec;
    private ArrayList<CallIRedirectionClientInfo> mCallIRedirectionClients;
    private Object mCallRedirectionLock;
    private CallInjectionModeChangedListener mCallRedirectionModeListener;
    private final CallbackUtil.LazyListenerManager<OnCommunicationDeviceChangedListener> mCommDeviceChangedListenerMgr;
    private final Map<Integer, Object> mDevRoleForCapturePresetListeners;
    private final Object mDevRoleForCapturePresetListenersLock;
    private final ArrayMap<AudioDeviceCallback, NativeEventHandlerDelegate> mDeviceCallbacks;
    private int mDeviceRoleListenersStatus;
    private final ConcurrentHashMap<OnDevicesForAttributesChangedListener, IDevicesForAttributesCallbackStub> mDevicesForAttributesListenerToStub;
    private CapturePresetDevicesRoleDispatcherStub mDevicesRoleForCapturePresetDispatcherStub;
    private HashMap<String, BlockingFocusResultReceiver> mFocusRequestsAwaitingResult;
    private final Object mFocusRequestsLock;
    private final IBinder mICallBack;
    private boolean mIsAutomotive;
    private final CallbackUtil.LazyListenerManager<OnModeChangedListener> mModeChangedListenerMgr;
    private MuteAwaitConnectionDispatcherStub mMuteAwaitConnDispatcherStub;
    private final Object mMuteAwaitConnectionListenerLock;
    private ArrayList<CallbackUtil.ListenerInfo<MuteAwaitConnectionCallback>> mMuteAwaitConnectionListeners;
    private final CallbackUtil.LazyListenerManager<OnNonDefaultDevicesForStrategyChangedListener> mNonDefDevListenerMgr;
    private Context mOriginalContext;
    private int mOriginalContextDeviceId = 0;
    private final IPlaybackConfigDispatcher mPlayCb;
    private List<AudioPlaybackCallbackInfo> mPlaybackCallbackList;
    private final Object mPlaybackCallbackLock;
    private OnAmPortUpdateListener mPortListener;
    private final CallbackUtil.LazyListenerManager<OnPreferredDevicesForStrategyChangedListener> mPrefDevListenerMgr;
    private final CallbackUtil.LazyListenerManager<OnPreferredMixerAttributesChangedListener> mPrefMixerAttributesListenerMgr;
    private ArrayList<AudioDevicePort> mPreviousPorts;
    private final IRecordingConfigDispatcher mRecCb;
    private List<AudioRecordingCallbackInfo> mRecordCallbackList;
    private final Object mRecordCallbackLock;
    private final ServiceEventHandlerDelegate mServiceEventHandlerDelegate;
    private final CallbackUtil.LazyListenerManager<Runnable> mStreamAliasingListenerMgr;
    private VirtualDeviceManager mVirtualDeviceManager;
    private final IpcDataCache<VolumeCacheQuery, Integer> mVolCache;
    private final IpcDataCache<VolumeCacheQuery, Integer> mVolMaxCache;
    private final IpcDataCache<VolumeCacheQuery, Integer> mVolMinCache;
    private final IpcDataCache.QueryHandler<VolumeCacheQuery, Integer> mVolQuery;
    private final CallbackUtil.LazyListenerManager<VolumeGroupCallback> mVolumeChangedListenerMgr;
    private static final AudioPortEventHandler sAudioPortEventHandler = new AudioPortEventHandler();
    private static final int[] PUBLIC_STREAM_TYPES = {0, 1, 2, 3, 4, 5, 8, 10};

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioDeviceCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioDeviceRole {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioDirectPlaybackMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioOffloadMode {
    }

    public static abstract class AudioPlaybackCallback {
        public void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list) {
        }
    }

    public static abstract class AudioRecordingCallback {
        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
        }
    }

    @SystemApi
    public static abstract class AudioServerStateCallback {
        public void onAudioServerDown() {
        }

        public void onAudioServerUp() {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallRedirectionMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CsdWarning {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceConnectionState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceVolumeBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EncodedSurroundOutputMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FineStreamTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusRequestResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MicInputControlMode {
    }

    @SystemApi
    public static abstract class MuteAwaitConnectionCallback {
        public static final int EVENT_CANCEL = 3;
        public static final int EVENT_CONNECTION = 1;
        public static final int EVENT_TIMEOUT = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface UnmuteEvent {
        }

        public void onMutedUntilConnection(AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
        }

        public void onUnmutedEvent(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
        }
    }

    public interface OnAudioFocusChangeListener {
        void onAudioFocusChange(int i);
    }

    public interface OnAudioPortUpdateListener {
        void onAudioPatchListUpdate(AudioPatch[] audioPatchArr);

        void onAudioPortListUpdate(AudioPort[] audioPortArr);

        void onServiceDied();
    }

    public interface OnCommunicationDeviceChangedListener {
        void onCommunicationDeviceChanged(AudioDeviceInfo audioDeviceInfo);
    }

    @SystemApi
    public interface OnDevicesForAttributesChangedListener {
        void onDevicesForAttributesChanged(AudioAttributes audioAttributes, List<AudioDeviceAttributes> list);
    }

    public interface OnModeChangedListener {
        void onModeChanged(int i);
    }

    @SystemApi
    public interface OnNonDefaultDevicesForStrategyChangedListener {
        void onNonDefaultDevicesForStrategyChanged(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, List<AudioDeviceAttributes> list);
    }

    @SystemApi
    @Deprecated
    public interface OnPreferredDeviceForStrategyChangedListener {
        void onPreferredDeviceForStrategyChanged(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, AudioDeviceAttributes audioDeviceAttributes);
    }

    @SystemApi
    public interface OnPreferredDevicesForCapturePresetChangedListener {
        void onPreferredDevicesForCapturePresetChanged(int i, List<AudioDeviceAttributes> list);
    }

    @SystemApi
    public interface OnPreferredDevicesForStrategyChangedListener {
        void onPreferredDevicesForStrategyChanged(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, List<AudioDeviceAttributes> list);
    }

    public interface OnPreferredMixerAttributesChangedListener {
        void onPreferredMixerAttributesChanged(AudioAttributes audioAttributes, AudioDeviceInfo audioDeviceInfo, AudioMixerAttributes audioMixerAttributes);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PublicStreamTypes {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PublicVolumeFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface QueryVolCommand {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SemControlMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemSoundEffect {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemVolumeFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VolumeAdjustment {
    }

    @SystemApi
    public static abstract class VolumeGroupCallback {
        public void onAudioVolumeGroupChanged(int i, int i2) {
        }
    }

    public static boolean isPublicStreamType(int i) {
        return i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 8 || i == 10;
    }

    public static int semGetStreamType(int i) {
        switch (i) {
            case 1:
                return 3;
            case 2:
                return 0;
            case 3:
            case 6:
                return 11;
            case 4:
                return 6;
            case 5:
                return 7;
            default:
                return -1;
        }
    }

    public static boolean semIsFineVolumeSupported() {
        return true;
    }

    @SystemApi
    @Deprecated
    public void addOnPreferredDeviceForStrategyChangedListener(Executor executor, OnPreferredDeviceForStrategyChangedListener onPreferredDeviceForStrategyChangedListener) throws SecurityException {
    }

    @Deprecated
    public int getRouting(int i) {
        return -1;
    }

    @SystemApi
    @Deprecated
    public void removeOnPreferredDeviceForStrategyChangedListener(OnPreferredDeviceForStrategyChangedListener onPreferredDeviceForStrategyChangedListener) {
    }

    public boolean semIsFineVolumeAvailable() {
        return true;
    }

    @Deprecated
    public void setBluetoothA2dpOn(boolean z) {
    }

    @Deprecated
    public void setRouting(int i, int i2, int i3) {
    }

    @Deprecated
    public void setWiredHeadsetOn(boolean z) {
    }

    static {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        FLAG_NAMES = treeMap;
        treeMap.put(1, "FLAG_SHOW_UI");
        treeMap.put(2, "FLAG_ALLOW_RINGER_MODES");
        treeMap.put(4, "FLAG_PLAY_SOUND");
        treeMap.put(8, "FLAG_REMOVE_SOUND_AND_VIBRATE");
        treeMap.put(16, "FLAG_VIBRATE");
        treeMap.put(32, "FLAG_FIXED_VOLUME");
        treeMap.put(64, "FLAG_BLUETOOTH_ABS_VOLUME");
        treeMap.put(128, "FLAG_SHOW_SILENT_HINT");
        treeMap.put(256, "FLAG_HDMI_SYSTEM_AUDIO_VOLUME");
        treeMap.put(512, "FLAG_ACTIVE_MEDIA_ONLY");
        treeMap.put(1024, "FLAG_SHOW_UI_WARNINGS");
        treeMap.put(2048, "FLAG_SHOW_VIBRATE_HINT");
        treeMap.put(4096, "FLAG_FROM_KEY");
        treeMap.put(8192, "FLAG_ABSOLUTE_VOLUME");
        sAudioPortGenerationLock = new Object();
        sAudioPortGeneration = 0;
        sAudioPortsCached = new ArrayList<>();
        sPreviousAudioPortsCached = new ArrayList<>();
        sAudioPatchesCached = new ArrayList<>();
        treeMap.put(262144, "FLAG_FIXED_SCO_VOLUME");
        treeMap.put(524288, "FLAG_DUAL_A2DP_MODE");
        treeMap.put(1048576, "FLAG_FINE_VOLUME");
        treeMap.put(2097152, "FLAG_NO_VOICE_ASSISTANT");
        treeMap.put(4194304, "FLAG_DISPLAY_VOLUME_CONTROL");
        treeMap.put(8388608, "FLAG_MULTI_SOUND");
        treeMap.put(33554432, "SEM_FLAG_UPDATE_STATE");
        treeMap.put(16777216, "FLAG_SKIP_RINGER_MODES");
        treeMap.put(67108864, "FLAG_REMOTE_MIC");
        treeMap.put(134217728, "FLAG_DISMISS_UI_WARNINGS");
        treeMap.put(268435456, "FLAG_MULTI_AUDIO_FOCUS");
        treeMap.put(536870912, "FLAG_SHOW_CSD_100_WARNINGS");
        treeMap.put(65536, "FLAG_ADJUST_LOWER");
        treeMap.put(131072, "FLAG_ADJUST_RAISE");
        sSetDeviceForceLock = new Object();
    }

    public static final int[] getPublicStreamTypes() {
        return PUBLIC_STREAM_TYPES;
    }

    public static final String adjustToString(int i) {
        if (i == -100) {
            return "ADJUST_MUTE";
        }
        if (i == -1) {
            return "ADJUST_LOWER";
        }
        if (i == 0) {
            return "ADJUST_SAME";
        }
        if (i == 1) {
            return "ADJUST_RAISE";
        }
        if (i == 100) {
            return "ADJUST_UNMUTE";
        }
        if (i == 101) {
            return "ADJUST_TOGGLE_MUTE";
        }
        return "unknown adjust mode " + i;
    }

    public static String flagsToString(int i) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> entry : FLAG_NAMES.entrySet()) {
            int iIntValue = entry.getKey().intValue();
            if ((i & iIntValue) != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(entry.getValue());
                i &= ~iIntValue;
            }
        }
        if (i != 0) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(i);
        }
        return sb.toString();
    }

    public AudioManager() {
        IpcDataCache.QueryHandler<VolumeCacheQuery, Integer> queryHandler = new IpcDataCache.QueryHandler<VolumeCacheQuery, Integer>(this) { // from class: android.media.AudioManager.1
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public Integer apply(VolumeCacheQuery volumeCacheQuery) {
                IAudioService service = AudioManager.getService();
                try {
                    int i = volumeCacheQuery.queryCommand;
                    if (i == 1) {
                        return Integer.valueOf(service.getStreamMinVolume(volumeCacheQuery.stream));
                    }
                    if (i == 2) {
                        return Integer.valueOf(service.getStreamMaxVolume(volumeCacheQuery.stream));
                    }
                    if (i == 3) {
                        return Integer.valueOf(service.getStreamVolume(volumeCacheQuery.stream));
                    }
                    Log.w(AudioManager.TAG, "Not a valid volume cache query: " + volumeCacheQuery);
                    return null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mVolQuery = queryHandler;
        this.mVolMinCache = new IpcDataCache<>(16, "system_server", VOLUME_MIN_CACHING_API, VOLUME_MIN_CACHING_API, queryHandler);
        this.mVolMaxCache = new IpcDataCache<>(16, "system_server", VOLUME_MAX_CACHING_API, VOLUME_MAX_CACHING_API, queryHandler);
        this.mVolCache = new IpcDataCache<>(16, "system_server", VOLUME_CACHING_API, VOLUME_CACHING_API, queryHandler);
        this.mPrefDevListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mNonDefDevListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mDevRoleForCapturePresetListeners = Map.of(1, new DevRoleListeners());
        this.mDevRoleForCapturePresetListenersLock = new Object();
        this.mDeviceRoleListenersStatus = 0;
        this.mModeChangedListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mAudioFocusIdListenerMap = new ConcurrentHashMap<>();
        this.mServiceEventHandlerDelegate = new ServiceEventHandlerDelegate(null);
        this.mAudioFocusDispatcher = new IAudioFocusDispatcher.Stub() { // from class: android.media.AudioManager.2
            @Override // android.media.IAudioFocusDispatcher
            public void dispatchAudioFocusChange(int i, String str) {
                FocusRequestInfo focusRequestInfoFindFocusRequestInfo = AudioManager.this.findFocusRequestInfo(str);
                if (focusRequestInfoFindFocusRequestInfo == null || focusRequestInfoFindFocusRequestInfo.mRequest.getOnAudioFocusChangeListener() == null) {
                    return;
                }
                Handler handler = focusRequestInfoFindFocusRequestInfo.mHandler == null ? AudioManager.this.mServiceEventHandlerDelegate.getHandler() : focusRequestInfoFindFocusRequestInfo.mHandler;
                handler.sendMessage(handler.obtainMessage(0, i, 0, str));
            }

            @Override // android.media.IAudioFocusDispatcher
            public void dispatchFocusResultFromExtPolicy(int i, String str) {
                synchronized (AudioManager.this.mFocusRequestsLock) {
                    BlockingFocusResultReceiver blockingFocusResultReceiver = (BlockingFocusResultReceiver) AudioManager.this.mFocusRequestsAwaitingResult.remove(str);
                    if (blockingFocusResultReceiver != null) {
                        blockingFocusResultReceiver.notifyResult(i);
                    } else {
                        Log.e(AudioManager.TAG, "dispatchFocusResultFromExtPolicy found no result receiver");
                    }
                }
            }
        };
        this.mFocusRequestsLock = new Object();
        this.mPlaybackCallbackLock = new Object();
        this.mPlayCb = new IPlaybackConfigDispatcher.Stub() { // from class: android.media.AudioManager.3
            @Override // android.media.IPlaybackConfigDispatcher
            public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> list, boolean z) {
                if (z) {
                    Binder.flushPendingCommands();
                }
                synchronized (AudioManager.this.mPlaybackCallbackLock) {
                    if (AudioManager.this.mPlaybackCallbackList != null) {
                        for (int i = 0; i < AudioManager.this.mPlaybackCallbackList.size(); i++) {
                            AudioPlaybackCallbackInfo audioPlaybackCallbackInfo = (AudioPlaybackCallbackInfo) AudioManager.this.mPlaybackCallbackList.get(i);
                            if (audioPlaybackCallbackInfo.mHandler != null) {
                                audioPlaybackCallbackInfo.mHandler.sendMessage(audioPlaybackCallbackInfo.mHandler.obtainMessage(2, new PlaybackConfigChangeCallbackData(audioPlaybackCallbackInfo.mCb, list)));
                            }
                        }
                    }
                }
            }
        };
        this.mRecordCallbackLock = new Object();
        this.mRecCb = new IRecordingConfigDispatcher.Stub() { // from class: android.media.AudioManager.4
            @Override // android.media.IRecordingConfigDispatcher
            public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> list) {
                synchronized (AudioManager.this.mRecordCallbackLock) {
                    if (AudioManager.this.mRecordCallbackList != null) {
                        for (int i = 0; i < AudioManager.this.mRecordCallbackList.size(); i++) {
                            AudioRecordingCallbackInfo audioRecordingCallbackInfo = (AudioRecordingCallbackInfo) AudioManager.this.mRecordCallbackList.get(i);
                            if (audioRecordingCallbackInfo.mHandler != null) {
                                audioRecordingCallbackInfo.mHandler.sendMessage(audioRecordingCallbackInfo.mHandler.obtainMessage(1, new RecordConfigChangeCallbackData(audioRecordingCallbackInfo.mCb, list)));
                            }
                        }
                    }
                }
            }
        };
        this.mICallBack = new Binder();
        this.mDevicesForAttributesListenerToStub = new ConcurrentHashMap<>();
        this.mPortListener = null;
        this.mDeviceCallbacks = new ArrayMap<>();
        this.mPreviousPorts = new ArrayList<>();
        this.mAudioServerStateCbLock = new Object();
        this.mAudioServerStateDispatcher = new AnonymousClass5();
        this.mVolumeChangedListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mCommDeviceChangedListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mCallRedirectionLock = new Object();
        this.mPrefMixerAttributesListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mStreamAliasingListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mMuteAwaitConnectionListenerLock = new Object();
        this.mIsAutomotive = false;
    }

    public AudioManager(Context context) {
        IpcDataCache.QueryHandler<VolumeCacheQuery, Integer> queryHandler = new IpcDataCache.QueryHandler<VolumeCacheQuery, Integer>(this) { // from class: android.media.AudioManager.1
            @Override // android.os.IpcDataCache.QueryHandler, android.app.PropertyInvalidatedCache.QueryHandler
            public Integer apply(VolumeCacheQuery volumeCacheQuery) {
                IAudioService service = AudioManager.getService();
                try {
                    int i = volumeCacheQuery.queryCommand;
                    if (i == 1) {
                        return Integer.valueOf(service.getStreamMinVolume(volumeCacheQuery.stream));
                    }
                    if (i == 2) {
                        return Integer.valueOf(service.getStreamMaxVolume(volumeCacheQuery.stream));
                    }
                    if (i == 3) {
                        return Integer.valueOf(service.getStreamVolume(volumeCacheQuery.stream));
                    }
                    Log.w(AudioManager.TAG, "Not a valid volume cache query: " + volumeCacheQuery);
                    return null;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mVolQuery = queryHandler;
        this.mVolMinCache = new IpcDataCache<>(16, "system_server", VOLUME_MIN_CACHING_API, VOLUME_MIN_CACHING_API, queryHandler);
        this.mVolMaxCache = new IpcDataCache<>(16, "system_server", VOLUME_MAX_CACHING_API, VOLUME_MAX_CACHING_API, queryHandler);
        this.mVolCache = new IpcDataCache<>(16, "system_server", VOLUME_CACHING_API, VOLUME_CACHING_API, queryHandler);
        this.mPrefDevListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mNonDefDevListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mDevRoleForCapturePresetListeners = Map.of(1, new DevRoleListeners());
        this.mDevRoleForCapturePresetListenersLock = new Object();
        this.mDeviceRoleListenersStatus = 0;
        this.mModeChangedListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mAudioFocusIdListenerMap = new ConcurrentHashMap<>();
        this.mServiceEventHandlerDelegate = new ServiceEventHandlerDelegate(null);
        this.mAudioFocusDispatcher = new IAudioFocusDispatcher.Stub() { // from class: android.media.AudioManager.2
            @Override // android.media.IAudioFocusDispatcher
            public void dispatchAudioFocusChange(int i, String str) {
                FocusRequestInfo focusRequestInfoFindFocusRequestInfo = AudioManager.this.findFocusRequestInfo(str);
                if (focusRequestInfoFindFocusRequestInfo == null || focusRequestInfoFindFocusRequestInfo.mRequest.getOnAudioFocusChangeListener() == null) {
                    return;
                }
                Handler handler = focusRequestInfoFindFocusRequestInfo.mHandler == null ? AudioManager.this.mServiceEventHandlerDelegate.getHandler() : focusRequestInfoFindFocusRequestInfo.mHandler;
                handler.sendMessage(handler.obtainMessage(0, i, 0, str));
            }

            @Override // android.media.IAudioFocusDispatcher
            public void dispatchFocusResultFromExtPolicy(int i, String str) {
                synchronized (AudioManager.this.mFocusRequestsLock) {
                    BlockingFocusResultReceiver blockingFocusResultReceiver = (BlockingFocusResultReceiver) AudioManager.this.mFocusRequestsAwaitingResult.remove(str);
                    if (blockingFocusResultReceiver != null) {
                        blockingFocusResultReceiver.notifyResult(i);
                    } else {
                        Log.e(AudioManager.TAG, "dispatchFocusResultFromExtPolicy found no result receiver");
                    }
                }
            }
        };
        this.mFocusRequestsLock = new Object();
        this.mPlaybackCallbackLock = new Object();
        this.mPlayCb = new IPlaybackConfigDispatcher.Stub() { // from class: android.media.AudioManager.3
            @Override // android.media.IPlaybackConfigDispatcher
            public void dispatchPlaybackConfigChange(List<AudioPlaybackConfiguration> list, boolean z) {
                if (z) {
                    Binder.flushPendingCommands();
                }
                synchronized (AudioManager.this.mPlaybackCallbackLock) {
                    if (AudioManager.this.mPlaybackCallbackList != null) {
                        for (int i = 0; i < AudioManager.this.mPlaybackCallbackList.size(); i++) {
                            AudioPlaybackCallbackInfo audioPlaybackCallbackInfo = (AudioPlaybackCallbackInfo) AudioManager.this.mPlaybackCallbackList.get(i);
                            if (audioPlaybackCallbackInfo.mHandler != null) {
                                audioPlaybackCallbackInfo.mHandler.sendMessage(audioPlaybackCallbackInfo.mHandler.obtainMessage(2, new PlaybackConfigChangeCallbackData(audioPlaybackCallbackInfo.mCb, list)));
                            }
                        }
                    }
                }
            }
        };
        this.mRecordCallbackLock = new Object();
        this.mRecCb = new IRecordingConfigDispatcher.Stub() { // from class: android.media.AudioManager.4
            @Override // android.media.IRecordingConfigDispatcher
            public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> list) {
                synchronized (AudioManager.this.mRecordCallbackLock) {
                    if (AudioManager.this.mRecordCallbackList != null) {
                        for (int i = 0; i < AudioManager.this.mRecordCallbackList.size(); i++) {
                            AudioRecordingCallbackInfo audioRecordingCallbackInfo = (AudioRecordingCallbackInfo) AudioManager.this.mRecordCallbackList.get(i);
                            if (audioRecordingCallbackInfo.mHandler != null) {
                                audioRecordingCallbackInfo.mHandler.sendMessage(audioRecordingCallbackInfo.mHandler.obtainMessage(1, new RecordConfigChangeCallbackData(audioRecordingCallbackInfo.mCb, list)));
                            }
                        }
                    }
                }
            }
        };
        this.mICallBack = new Binder();
        this.mDevicesForAttributesListenerToStub = new ConcurrentHashMap<>();
        this.mPortListener = null;
        this.mDeviceCallbacks = new ArrayMap<>();
        this.mPreviousPorts = new ArrayList<>();
        this.mAudioServerStateCbLock = new Object();
        this.mAudioServerStateDispatcher = new AnonymousClass5();
        this.mVolumeChangedListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mCommDeviceChangedListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mCallRedirectionLock = new Object();
        this.mPrefMixerAttributesListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mStreamAliasingListenerMgr = new CallbackUtil.LazyListenerManager<>();
        this.mMuteAwaitConnectionListenerLock = new Object();
        this.mIsAutomotive = false;
        setContext(context);
        initPlatform();
    }

    private Context getContext() {
        if (this.mApplicationContext == null) {
            setContext(this.mOriginalContext);
        }
        Context context = this.mApplicationContext;
        return context != null ? context : this.mOriginalContext;
    }

    private void setContext(Context context) {
        if (context == null) {
            return;
        }
        this.mOriginalContextDeviceId = context.getDeviceId();
        Context applicationContext = context.getApplicationContext();
        this.mApplicationContext = applicationContext;
        if (applicationContext != null) {
            this.mOriginalContext = null;
        } else {
            this.mOriginalContext = context;
        }
        sContext = new WeakReference<>(context);
    }

    static IAudioService getService() {
        IAudioService iAudioService = sService;
        if (iAudioService != null) {
            return iAudioService;
        }
        IAudioService iAudioServiceAsInterface = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        sService = iAudioServiceAsInterface;
        return iAudioServiceAsInterface;
    }

    private VirtualDeviceManager getVirtualDeviceManager() {
        VirtualDeviceManager virtualDeviceManager = this.mVirtualDeviceManager;
        if (virtualDeviceManager != null) {
            return virtualDeviceManager;
        }
        VirtualDeviceManager virtualDeviceManager2 = (VirtualDeviceManager) getContext().getSystemService(VirtualDeviceManager.class);
        this.mVirtualDeviceManager = virtualDeviceManager2;
        return virtualDeviceManager2;
    }

    public void dispatchMediaKeyEvent(KeyEvent keyEvent) {
        MediaSessionLegacyHelper.getHelper(getContext()).sendMediaButtonEvent(keyEvent, false);
    }

    public void preDispatchKeyEvent(KeyEvent keyEvent, int i) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 25 || keyCode == 24 || keyCode == 164 || 300 <= SystemClock.uptimeMillis()) {
            return;
        }
        adjustSuggestedStreamVolume(0, i, 8);
    }

    public boolean isVolumeFixed() {
        try {
            return getService().isVolumeFixed();
        } catch (RemoteException e) {
            Log.e(TAG, "Error querying isVolumeFixed", e);
            return false;
        }
    }

    public void adjustStreamVolume(int i, int i2, int i3) {
        try {
            getService().adjustStreamVolumeWithAttribution(i, i2, i3, getContext().getOpPackageName(), getContext().getAttributionTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void adjustVolume(int i, int i2) {
        if (applyAutoHardening()) {
            try {
                getService().adjustVolume(i, i2);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        MediaSessionLegacyHelper.getHelper(getContext()).sendAdjustVolumeBy(Integer.MIN_VALUE, i, i2);
    }

    public void adjustSuggestedStreamVolume(int i, int i2, int i3) {
        if (AudioManagerHelper.needToLogCaller(getContext().getOpPackageName())) {
            AudioManagerHelper.logCaller("suggestedStreamType=%d, direction=%d", Integer.valueOf(i2), Integer.valueOf(i));
        }
        if (applyAutoHardening()) {
            try {
                getService().adjustSuggestedStreamVolume(i, i2, i3);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        MediaSessionLegacyHelper.getHelper(getContext()).sendAdjustVolumeBy(i2, i, i3);
    }

    public void setMasterMute(boolean z, int i) {
        try {
            getService().setMasterMute(z, i, getContext().getOpPackageName(), getContext().getUserId(), getContext().getAttributionTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRingerMode() {
        try {
            return getService().getRingerModeExternal();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRampingRingerEnabled() {
        return Settings.System.getInt(getContext().getContentResolver(), "apply_ramping_ringer", 0) != 0;
    }

    public void setRampingRingerEnabled(boolean z) {
        Settings.System.putInt(getContext().getContentResolver(), "apply_ramping_ringer", z ? 1 : 0);
    }

    public static boolean isValidRingerMode(int i) {
        if (i < 0 || i > 2) {
            return false;
        }
        try {
            return getService().isValidRingerMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void clearVolumeCache(String str) {
        if (com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags.cacheGetStreamMinMaxVolume() && (VOLUME_MAX_CACHING_API.equals(str) || VOLUME_MIN_CACHING_API.equals(str))) {
            IpcDataCache.invalidateCache("system_server", str);
            return;
        }
        if (com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags.cacheGetStreamVolume() && VOLUME_CACHING_API.equals(str)) {
            IpcDataCache.invalidateCache("system_server", str);
            return;
        }
        Log.w(TAG, "invalid clearVolumeCache for api " + str);
    }

    private static final class VolumeCacheQuery extends Record {
        private final int queryCommand;
        private final int stream;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof VolumeCacheQuery)) {
                return false;
            }
            VolumeCacheQuery volumeCacheQuery = (VolumeCacheQuery) obj;
            return this.stream == volumeCacheQuery.stream && this.queryCommand == volumeCacheQuery.queryCommand;
        }

        private VolumeCacheQuery(int stream, int queryCommand) {
            this.stream = stream;
            this.queryCommand = queryCommand;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.stream, this.queryCommand);
        }

        public int queryCommand() {
            return this.queryCommand;
        }

        public int stream() {
            return this.stream;
        }

        private String queryVolCommandToString() {
            int i = this.queryCommand;
            if (i == 1) {
                return AudioManager.VOLUME_MIN_CACHING_API;
            }
            if (i == 2) {
                return AudioManager.VOLUME_MAX_CACHING_API;
            }
            if (i == 3) {
                return AudioManager.VOLUME_CACHING_API;
            }
            return "invalid command";
        }

        @Override // java.lang.Record
        public String toString() {
            return TextUtils.formatSimple("VolumeCacheQuery(stream=%d, queryCommand=%s)", Integer.valueOf(this.stream), queryVolCommandToString());
        }
    }

    public int getStreamMaxVolume(int i) {
        if (com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags.cacheGetStreamMinMaxVolume()) {
            return this.mVolMaxCache.query(new VolumeCacheQuery(i, 2)).intValue();
        }
        try {
            return getService().getStreamMaxVolume(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStreamMinVolume(int i) {
        if (!isPublicStreamType(i)) {
            throw new IllegalArgumentException("Invalid stream type " + i);
        }
        return getStreamMinVolumeInt(i);
    }

    public int getStreamMinVolumeInt(int i) {
        if (com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags.cacheGetStreamMinMaxVolume()) {
            return this.mVolMinCache.query(new VolumeCacheQuery(i, 1)).intValue();
        }
        try {
            return getService().getStreamMinVolume(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStreamVolume(int i) {
        if (com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags.cacheGetStreamVolume()) {
            return this.mVolCache.query(new VolumeCacheQuery(i, 3)).intValue();
        }
        try {
            return getService().getStreamVolume(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getStreamVolumeDb(int i, int i2, int i3) {
        if (!isPublicStreamType(i)) {
            throw new IllegalArgumentException("Invalid stream type " + i);
        }
        if (i2 > getStreamMaxVolume(i) || i2 < getStreamMinVolume(i)) {
            throw new IllegalArgumentException("Invalid stream volume index " + i2);
        }
        if (!AudioDeviceInfo.isValidAudioDeviceTypeOut(i3)) {
            throw new IllegalArgumentException("Invalid audio output device type " + i3);
        }
        float streamVolumeDB = AudioSystem.getStreamVolumeDB(i, i2, AudioDeviceInfo.convertDeviceTypeToInternalDevice(i3));
        if (streamVolumeDB <= VOLUME_MIN_DB) {
            return Float.NEGATIVE_INFINITY;
        }
        return streamVolumeDB;
    }

    @SystemApi
    public int getLastAudibleStreamVolume(int i) {
        try {
            return getService().getLastAudibleStreamVolume(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUiSoundsStreamType() {
        try {
            return getService().getUiSoundsStreamType();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRingerMode(int i) {
        if (isValidRingerMode(i)) {
            try {
                getService().setRingerModeExternal(i, getContext().getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void setStreamVolume(int i, int i2, int i3) {
        if (AudioManagerHelper.needToLogCaller(getContext().getOpPackageName())) {
            AudioManagerHelper.logCaller("streamType=%d, index=%d", Integer.valueOf(i), Integer.valueOf(i2));
        }
        try {
            getService().setStreamVolumeWithAttribution(i, i2, i3, getContext().getOpPackageName(), getContext().getAttributionTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setVolumeIndexForAttributes(AudioAttributes audioAttributes, int i, int i2) {
        Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        getService();
        setVolumeGroupVolumeIndex(getVolumeGroupIdForAttributes(audioAttributes), i, i2);
    }

    @SystemApi
    public int getVolumeIndexForAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        getService();
        return getVolumeGroupVolumeIndex(getVolumeGroupIdForAttributes(audioAttributes));
    }

    @SystemApi
    public int getMaxVolumeIndexForAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        getService();
        return getVolumeGroupMaxVolumeIndex(getVolumeGroupIdForAttributes(audioAttributes));
    }

    @SystemApi
    public int getMinVolumeIndexForAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        getService();
        return getVolumeGroupMinVolumeIndex(getVolumeGroupIdForAttributes(audioAttributes));
    }

    public int getVolumeGroupIdForAttributes(AudioAttributes audioAttributes) {
        Preconditions.checkNotNull(audioAttributes, "Audio Attributes must not be null");
        return android.media.audiopolicy.AudioProductStrategy.getVolumeGroupIdForAudioAttributes(audioAttributes, true);
    }

    @SystemApi
    public void setVolumeGroupVolumeIndex(int i, int i2, int i3) {
        try {
            getService().setVolumeGroupVolumeIndex(i, i2, i3, getContext().getOpPackageName(), getContext().getAttributionTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getVolumeGroupVolumeIndex(int i) {
        try {
            return getService().getVolumeGroupVolumeIndex(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getVolumeGroupMaxVolumeIndex(int i) {
        try {
            return getService().getVolumeGroupMaxVolumeIndex(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getVolumeGroupMinVolumeIndex(int i) {
        try {
            return getService().getVolumeGroupMinVolumeIndex(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void adjustVolumeGroupVolume(int i, int i2, int i3) {
        try {
            getService().adjustVolumeGroupVolume(i, i2, i3, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getLastAudibleVolumeForVolumeGroup(int i) {
        try {
            return getService().getLastAudibleVolumeForVolumeGroup(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVolumeGroupMuted(int i) {
        try {
            return getService().isVolumeGroupMuted(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setSupportedSystemUsages(int[] iArr) {
        Objects.requireNonNull(iArr, "systemUsages must not be null");
        try {
            getService().setSupportedSystemUsages(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int[] getSupportedSystemUsages() {
        try {
            return getService().getSupportedSystemUsages();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setStreamSolo(int i, boolean z) {
        Log.w(TAG, "setStreamSolo has been deprecated. Do not use.");
    }

    @Deprecated
    public void setStreamMute(int i, boolean z) {
        Log.w(TAG, "setStreamMute is deprecated. adjustStreamVolume should be used instead.");
        int i2 = z ? -100 : 100;
        if (i == Integer.MIN_VALUE) {
            adjustSuggestedStreamVolume(i2, i, 0);
        } else {
            adjustStreamVolume(i, i2, 0);
        }
    }

    public boolean isStreamMute(int i) {
        try {
            return getService().isStreamMute(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMasterMute() {
        try {
            return getService().isMasterMute();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceVolumeControlStream(int i) {
        try {
            getService().forceVolumeControlStream(i, this.mICallBack);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldVibrate(int i) {
        try {
            return getService().shouldVibrate(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getVibrateSetting(int i) {
        try {
            return getService().getVibrateSetting(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVibrateSetting(int i, int i2) {
        try {
            getService().setVibrateSetting(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setSpeakerphoneOn(boolean z) {
        try {
            getService().setSpeakerphoneOn(this.mICallBack, z, getAttributionSource());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private AttributionSource getAttributionSource() {
        Context context = getContext();
        return context != null ? context.getAttributionSource() : AttributionSource.myAttributionSource();
    }

    @Deprecated
    public boolean isSpeakerphoneOn() {
        try {
            return getService().isSpeakerphoneOn();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAllowedCapturePolicy(int i) {
        try {
            int allowedCapturePolicy = getService().setAllowedCapturePolicy(i);
            if (allowedCapturePolicy != 0) {
                Log.e(TAG, "Could not setAllowedCapturePolicy: " + allowedCapturePolicy);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAllowedCapturePolicy() {
        try {
            return getService().getAllowedCapturePolicy();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to query allowed capture policy: " + e);
            return 1;
        }
    }

    @SystemApi
    public boolean setPreferredDeviceForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, AudioDeviceAttributes audioDeviceAttributes) {
        return setPreferredDevicesForStrategy(audioProductStrategy, Arrays.asList(audioDeviceAttributes));
    }

    @SystemApi
    public boolean removePreferredDeviceForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy) {
        Objects.requireNonNull(audioProductStrategy);
        try {
            return getService().removePreferredDevicesForStrategy(audioProductStrategy.getId()) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public AudioDeviceAttributes getPreferredDeviceForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy) {
        List<AudioDeviceAttributes> preferredDevicesForStrategy = getPreferredDevicesForStrategy(audioProductStrategy);
        if (preferredDevicesForStrategy.isEmpty()) {
            return null;
        }
        return preferredDevicesForStrategy.get(0);
    }

    @SystemApi
    public boolean setPreferredDevicesForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, List<AudioDeviceAttributes> list) {
        Objects.requireNonNull(audioProductStrategy);
        Objects.requireNonNull(list);
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Tried to set preferred devices for strategy with a empty list");
        }
        Iterator<AudioDeviceAttributes> it = list.iterator();
        while (it.hasNext()) {
            Objects.requireNonNull(it.next());
        }
        try {
            return getService().setPreferredDevicesForStrategy(audioProductStrategy.getId(), list) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<AudioDeviceAttributes> getPreferredDevicesForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy) {
        Objects.requireNonNull(audioProductStrategy);
        try {
            return getService().getPreferredDevicesForStrategy(audioProductStrategy.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setDeviceAsNonDefaultForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioProductStrategy);
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            return getService().setDeviceAsNonDefaultForStrategy(audioProductStrategy.getId(), audioDeviceAttributes) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean removeDeviceAsNonDefaultForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioProductStrategy);
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            return getService().removeDeviceAsNonDefaultForStrategy(audioProductStrategy.getId(), audioDeviceAttributes) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<AudioDeviceAttributes> getNonDefaultDevicesForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy) {
        Objects.requireNonNull(audioProductStrategy);
        try {
            return getService().getNonDefaultDevicesForStrategy(audioProductStrategy.getId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addOnPreferredDevicesForStrategyChangedListener(Executor executor, OnPreferredDevicesForStrategyChangedListener onPreferredDevicesForStrategyChangedListener) throws SecurityException {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onPreferredDevicesForStrategyChangedListener);
        this.mPrefDevListenerMgr.addListener(executor, onPreferredDevicesForStrategyChangedListener, "addOnPreferredDevicesForStrategyChangedListener", new Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnPreferredDevicesForStrategyChangedListener$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnPreferredDevicesForStrategyChangedListener$0() {
        return new StrategyPreferredDevicesDispatcherStub();
    }

    @SystemApi
    public void removeOnPreferredDevicesForStrategyChangedListener(OnPreferredDevicesForStrategyChangedListener onPreferredDevicesForStrategyChangedListener) {
        Objects.requireNonNull(onPreferredDevicesForStrategyChangedListener);
        this.mPrefDevListenerMgr.removeListener(onPreferredDevicesForStrategyChangedListener, "removeOnPreferredDevicesForStrategyChangedListener");
    }

    @SystemApi
    public void addOnNonDefaultDevicesForStrategyChangedListener(Executor executor, OnNonDefaultDevicesForStrategyChangedListener onNonDefaultDevicesForStrategyChangedListener) throws SecurityException {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onNonDefaultDevicesForStrategyChangedListener);
        this.mNonDefDevListenerMgr.addListener(executor, onNonDefaultDevicesForStrategyChangedListener, "addOnNonDefaultDevicesForStrategyChangedListener", new Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnNonDefaultDevicesForStrategyChangedListener$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnNonDefaultDevicesForStrategyChangedListener$1() {
        return new StrategyNonDefaultDevicesDispatcherStub();
    }

    @SystemApi
    public void removeOnNonDefaultDevicesForStrategyChangedListener(OnNonDefaultDevicesForStrategyChangedListener onNonDefaultDevicesForStrategyChangedListener) {
        Objects.requireNonNull(onNonDefaultDevicesForStrategyChangedListener);
        this.mNonDefDevListenerMgr.removeListener(onNonDefaultDevicesForStrategyChangedListener, "removeOnNonDefaultDevicesForStrategyChangedListener");
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class StrategyPreferredDevicesDispatcherStub extends IStrategyPreferredDevicesDispatcher.Stub implements CallbackUtil.DispatcherStub {
        private StrategyPreferredDevicesDispatcherStub() {
        }

        @Override // android.media.IStrategyPreferredDevicesDispatcher
        public void dispatchPrefDevicesChanged(int i, final List<AudioDeviceAttributes> list) {
            final android.media.audiopolicy.AudioProductStrategy audioProductStrategyWithId = android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategyWithId(i);
            AudioManager.this.mPrefDevListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$StrategyPreferredDevicesDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioManager.OnPreferredDevicesForStrategyChangedListener) obj).onPreferredDevicesForStrategyChanged(audioProductStrategyWithId, list);
                }
            });
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    AudioManager.getService().registerStrategyPreferredDevicesDispatcher(this);
                } else {
                    AudioManager.getService().unregisterStrategyPreferredDevicesDispatcher(this);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class StrategyNonDefaultDevicesDispatcherStub extends IStrategyNonDefaultDevicesDispatcher.Stub implements CallbackUtil.DispatcherStub {
        private StrategyNonDefaultDevicesDispatcherStub() {
        }

        @Override // android.media.IStrategyNonDefaultDevicesDispatcher
        public void dispatchNonDefDevicesChanged(int i, final List<AudioDeviceAttributes> list) {
            final android.media.audiopolicy.AudioProductStrategy audioProductStrategyWithId = android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategyWithId(i);
            AudioManager.this.mNonDefDevListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$StrategyNonDefaultDevicesDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioManager.OnNonDefaultDevicesForStrategyChangedListener) obj).onNonDefaultDevicesForStrategyChanged(audioProductStrategyWithId, list);
                }
            });
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    AudioManager.getService().registerStrategyNonDefaultDevicesDispatcher(this);
                } else {
                    AudioManager.getService().unregisterStrategyNonDefaultDevicesDispatcher(this);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean setPreferredDeviceForCapturePreset(int i, AudioDeviceAttributes audioDeviceAttributes) {
        return setPreferredDevicesForCapturePreset(i, Arrays.asList(audioDeviceAttributes));
    }

    @SystemApi
    public boolean clearPreferredDevicesForCapturePreset(int i) {
        if (!MediaRecorder.isValidAudioSource(i)) {
            return false;
        }
        try {
            return getService().clearPreferredDevicesForCapturePreset(i) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) {
        if (!MediaRecorder.isValidAudioSource(i)) {
            return new ArrayList();
        }
        try {
            return getService().getPreferredDevicesForCapturePreset(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean setPreferredDevicesForCapturePreset(int i, List<AudioDeviceAttributes> list) {
        Objects.requireNonNull(list);
        if (!MediaRecorder.isValidAudioSource(i)) {
            return false;
        }
        if (list.size() != 1) {
            throw new IllegalArgumentException("Only support setting one preferred devices for capture preset");
        }
        Iterator<AudioDeviceAttributes> it = list.iterator();
        while (it.hasNext()) {
            Objects.requireNonNull(it.next());
        }
        try {
            return getService().setPreferredDevicesForCapturePreset(i, list) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addOnPreferredDevicesForCapturePresetChangedListener(Executor executor, OnPreferredDevicesForCapturePresetChangedListener onPreferredDevicesForCapturePresetChangedListener) throws SecurityException {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onPreferredDevicesForCapturePresetChangedListener);
        int iAddOnDevRoleForCapturePresetChangedListener = addOnDevRoleForCapturePresetChangedListener(executor, onPreferredDevicesForCapturePresetChangedListener, 1);
        if (iAddOnDevRoleForCapturePresetChangedListener == -1) {
            throw new RuntimeException("Unknown error happened");
        }
        if (iAddOnDevRoleForCapturePresetChangedListener == -2) {
            throw new IllegalArgumentException("attempt to call addOnPreferredDevicesForCapturePresetChangedListener() on a previously registered listener");
        }
    }

    @SystemApi
    public void removeOnPreferredDevicesForCapturePresetChangedListener(OnPreferredDevicesForCapturePresetChangedListener onPreferredDevicesForCapturePresetChangedListener) {
        Objects.requireNonNull(onPreferredDevicesForCapturePresetChangedListener);
        int iRemoveOnDevRoleForCapturePresetChangedListener = removeOnDevRoleForCapturePresetChangedListener(onPreferredDevicesForCapturePresetChangedListener, 1);
        if (iRemoveOnDevRoleForCapturePresetChangedListener == -1) {
            throw new RuntimeException("Unknown error happened");
        }
        if (iRemoveOnDevRoleForCapturePresetChangedListener == -2) {
            throw new IllegalArgumentException("attempt to call removeOnPreferredDevicesForCapturePresetChangedListener() on an unregistered listener");
        }
    }

    private <T> int addOnDevRoleForCapturePresetChangedListener(Executor executor, T t, int i) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(t);
        DevRoleListeners devRoleListeners = (DevRoleListeners) this.mDevRoleForCapturePresetListeners.get(Integer.valueOf(i));
        if (devRoleListeners == null) {
            return -1;
        }
        synchronized (devRoleListeners.mDevRoleListenersLock) {
            if (devRoleListeners.hasDevRoleListener(t)) {
                return -2;
            }
            if (devRoleListeners.mListenerInfos == null) {
                devRoleListeners.mListenerInfos = new ArrayList();
            }
            int size = devRoleListeners.mListenerInfos.size();
            devRoleListeners.mListenerInfos.add(new DevRoleListenerInfo(this, executor, t));
            if (size == 0 && devRoleListeners.mListenerInfos.size() > 0) {
                synchronized (this.mDevRoleForCapturePresetListenersLock) {
                    int i2 = this.mDeviceRoleListenersStatus;
                    this.mDeviceRoleListenersStatus = (1 << i) | i2;
                    if (i2 != 0) {
                        return 0;
                    }
                    if (this.mDevicesRoleForCapturePresetDispatcherStub == null) {
                        this.mDevicesRoleForCapturePresetDispatcherStub = new CapturePresetDevicesRoleDispatcherStub();
                    }
                    try {
                        getService().registerCapturePresetDevicesRoleDispatcher(this.mDevicesRoleForCapturePresetDispatcherStub);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
            return 0;
        }
    }

    private <T> int removeOnDevRoleForCapturePresetChangedListener(T t, int i) {
        Objects.requireNonNull(t);
        DevRoleListeners devRoleListeners = (DevRoleListeners) this.mDevRoleForCapturePresetListeners.get(Integer.valueOf(i));
        if (devRoleListeners == null) {
            return -1;
        }
        synchronized (devRoleListeners.mDevRoleListenersLock) {
            if (!devRoleListeners.removeDevRoleListener(t)) {
                return -2;
            }
            if (devRoleListeners.mListenerInfos.size() == 0) {
                synchronized (this.mDevRoleForCapturePresetListenersLock) {
                    int i2 = (1 << i) ^ this.mDeviceRoleListenersStatus;
                    this.mDeviceRoleListenersStatus = i2;
                    if (i2 != 0) {
                        return 0;
                    }
                    try {
                        getService().unregisterCapturePresetDevicesRoleDispatcher(this.mDevicesRoleForCapturePresetDispatcherStub);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DevRoleListenerInfo<T> {
        final Executor mExecutor;
        final T mListener;

        DevRoleListenerInfo(AudioManager audioManager, Executor executor, T t) {
            this.mExecutor = executor;
            this.mListener = t;
        }
    }

    private class DevRoleListeners<T> {
        private final Object mDevRoleListenersLock;
        private ArrayList<DevRoleListenerInfo<T>> mListenerInfos;

        private DevRoleListeners(AudioManager audioManager) {
            this.mDevRoleListenersLock = new Object();
        }

        private DevRoleListenerInfo<T> getDevRoleListenerInfo(T t) {
            ArrayList<DevRoleListenerInfo<T>> arrayList = this.mListenerInfos;
            if (arrayList == null) {
                return null;
            }
            Iterator<DevRoleListenerInfo<T>> it = arrayList.iterator();
            while (it.hasNext()) {
                DevRoleListenerInfo<T> next = it.next();
                if (next.mListener == t) {
                    return next;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasDevRoleListener(T t) {
            return getDevRoleListenerInfo(t) != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean removeDevRoleListener(T t) {
            DevRoleListenerInfo<T> devRoleListenerInfo = getDevRoleListenerInfo(t);
            if (devRoleListenerInfo == null) {
                return false;
            }
            this.mListenerInfos.remove(devRoleListenerInfo);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class CapturePresetDevicesRoleDispatcherStub extends ICapturePresetDevicesRoleDispatcher.Stub {
        private CapturePresetDevicesRoleDispatcherStub() {
        }

        @Override // android.media.ICapturePresetDevicesRoleDispatcher
        public void dispatchDevicesRoleChanged(final int i, int i2, final List<AudioDeviceAttributes> list) {
            Object obj = AudioManager.this.mDevRoleForCapturePresetListeners.get(Integer.valueOf(i2));
            if (obj != null && i2 == 1) {
                DevRoleListeners devRoleListeners = (DevRoleListeners) obj;
                synchronized (devRoleListeners.mDevRoleListenersLock) {
                    if (devRoleListeners.mListenerInfos.isEmpty()) {
                        return;
                    }
                    ArrayList arrayList = (ArrayList) devRoleListeners.mListenerInfos.clone();
                    long jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            final DevRoleListenerInfo devRoleListenerInfo = (DevRoleListenerInfo) it.next();
                            devRoleListenerInfo.mExecutor.execute(new Runnable() { // from class: android.media.AudioManager$CapturePresetDevicesRoleDispatcherStub$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ((AudioManager.OnPreferredDevicesForCapturePresetChangedListener) devRoleListenerInfo.mListener).onPreferredDevicesForCapturePresetChanged(i, list);
                                }
                            });
                        }
                    } finally {
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    }
                }
            }
        }
    }

    public static int getDirectPlaybackSupport(AudioFormat audioFormat, AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioFormat);
        Objects.requireNonNull(audioAttributes);
        return AudioSystem.getDirectPlaybackSupport(audioFormat, audioAttributes);
    }

    public static boolean isOffloadedPlaybackSupported(AudioFormat audioFormat, AudioAttributes audioAttributes) {
        if (audioFormat == null) {
            throw new NullPointerException("Illegal null AudioFormat");
        }
        if (audioAttributes != null) {
            return AudioSystem.getOffloadSupport(audioFormat, audioAttributes) != 0;
        }
        throw new NullPointerException("Illegal null AudioAttributes");
    }

    @Deprecated
    public static int getPlaybackOffloadSupport(AudioFormat audioFormat, AudioAttributes audioAttributes) {
        if (audioFormat == null) {
            throw new NullPointerException("Illegal null AudioFormat");
        }
        if (audioAttributes == null) {
            throw new NullPointerException("Illegal null AudioAttributes");
        }
        return AudioSystem.getOffloadSupport(audioFormat, audioAttributes);
    }

    public Spatializer getSpatializer() {
        return new Spatializer(this);
    }

    public boolean isBluetoothScoAvailableOffCall() {
        return getContext().getResources().getBoolean(R.bool.config_bluetooth_sco_off_call);
    }

    @Deprecated
    public void startBluetoothSco() {
        try {
            getService().startBluetoothSco(this.mICallBack, getContext().getApplicationInfo().targetSdkVersion, getAttributionSource());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startBluetoothScoVirtualCall() {
        try {
            getService().startBluetoothScoVirtualCall(this.mICallBack, getAttributionSource());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void stopBluetoothSco() {
        try {
            getService().stopBluetoothSco(this.mICallBack, getAttributionSource());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBluetoothScoOn(boolean z) {
        try {
            getService().setBluetoothScoOn(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean isBluetoothScoOn() {
        try {
            return getService().isBluetoothScoOn();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBluetoothA2dpOn() {
        return AudioSystem.getDeviceConnectionState(128, "") == 1 || AudioSystem.getDeviceConnectionState(256, "") == 1 || AudioSystem.getDeviceConnectionState(512, "") == 1;
    }

    public boolean isWiredHeadsetOn() {
        return (AudioSystem.getDeviceConnectionState(4, "") == 0 && AudioSystem.getDeviceConnectionState(8, "") == 0 && AudioSystem.getDeviceConnectionState(67108864, "") == 0) ? false : true;
    }

    public void setMicrophoneMute(boolean z) {
        try {
            getService().setMicrophoneMute(z, getContext().getOpPackageName(), getContext().getUserId(), getContext().getAttributionTag());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMicrophoneMuteFromSwitch(boolean z) {
        try {
            getService().setMicrophoneMuteFromSwitch(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMicrophoneMute() {
        try {
            return getService().isMicrophoneMuted();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMode(int i) {
        try {
            getService().setMode(i, this.mICallBack, this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMode() {
        int i;
        try {
            int mode = getService().getMode();
            try {
                i = getContext().getApplicationInfo().targetSdkVersion;
            } catch (NullPointerException unused) {
                i = Build.VERSION.SDK_INT;
            }
            if ((mode != 4 || i > 29) && (mode != 5 || CompatChanges.isChangeEnabled(CALL_REDIRECTION_AUDIO_MODES))) {
                if (mode != 6) {
                    return mode;
                }
                if (CompatChanges.isChangeEnabled(CALL_REDIRECTION_AUDIO_MODES)) {
                    return mode;
                }
                return 3;
            }
            return 2;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    final class ModeDispatcherStub extends IAudioModeDispatcher.Stub implements CallbackUtil.DispatcherStub {
        ModeDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    AudioManager.getService().registerModeDispatcher(this);
                } else {
                    AudioManager.getService().unregisterModeDispatcher(this);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IAudioModeDispatcher
        public void dispatchAudioModeChanged(final int i) {
            AudioManager.this.mModeChangedListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$ModeDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioManager.OnModeChangedListener) obj).onModeChanged(i);
                }
            });
        }
    }

    public void addOnModeChangedListener(Executor executor, OnModeChangedListener onModeChangedListener) {
        this.mModeChangedListenerMgr.addListener(executor, onModeChangedListener, "addOnModeChangedListener", new Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnModeChangedListener$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnModeChangedListener$2() {
        return new ModeDispatcherStub();
    }

    public void removeOnModeChangedListener(OnModeChangedListener onModeChangedListener) {
        this.mModeChangedListenerMgr.removeListener(onModeChangedListener, "removeOnModeChangedListener");
    }

    public boolean isCallScreeningModeSupported() {
        try {
            return getService().isCallScreeningModeSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMusicActive() {
        try {
            return getService().isMusicActive(false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMusicActiveRemotely() {
        try {
            return getService().isMusicActive(true);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAudioFocusExclusive() {
        try {
            return getService().getCurrentAudioFocus() == 4;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int generateAudioSessionId() {
        int iNewAudioSessionId = AudioSystem.newAudioSessionId();
        if (iNewAudioSessionId > 0) {
            return iNewAudioSessionId;
        }
        Log.e(TAG, "Failure to generate a new audio session ID");
        return -1;
    }

    @Deprecated
    public void setParameter(String str, String str2) {
        setParameters(str + "=" + str2);
    }

    public void setParameters(String str) {
        Log.i(TAG, "setParameters keyValuePairs = " + str);
        if (str.startsWith(AudioParameter.SEC_GLOBAL_PREFIX)) {
            setAudioServiceConfig(str);
            return;
        }
        if (str.startsWith(AudioParameter.SEC_GLOBAL_FACTORY_PREFIX)) {
            setAudioServiceConfig(str);
            return;
        }
        if (str.startsWith(AudioParameter.SEC_GLOBAL_PTT_MODE_3RD_PARTY)) {
            setAudioServiceConfig(AudioParameter.SEC_GLOBAL_PREFIX + str);
            return;
        }
        if (str.startsWith(AudioParameter.AOSP_CALL_HAC)) {
            setAudioServiceConfig(str);
            return;
        }
        if (str.contains(AudioParameter.SEC_GLOBAL_SCO_SAMPLERATE)) {
            setAudioServiceConfig(str);
            return;
        }
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI && str.contains(AudioParameter.SEC_LOCAL_CALL_TRANSLATION_MODE)) {
            setAudioServiceConfig(str);
        } else if (str.contains(AudioParameter.SEC_LOCAL_VOICE_RX_CONTROL_MODE) || str.contains(AudioParameter.SEC_LOCAL_VOICE_TX_CONTROL_MODE)) {
            setAudioServiceConfig(str);
        } else {
            AudioSystem.setParameters(str);
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setHfpEnabled(boolean z) {
        AudioSystem.setParameters("hfp_enable=" + z);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setHfpVolume(int i) {
        AudioSystem.setParameters("hfp_volume=" + i);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setHfpSamplingRate(int i) {
        AudioSystem.setParameters("hfp_set_sampling_rate=" + i);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setBluetoothHeadsetProperties(String str, boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder("bt_headset_name=");
        sb.append(str);
        sb.append(";bt_headset_nrec=");
        sb.append(z ? "on" : "off");
        sb.append(";bt_wbs=");
        sb.append(z2 ? "on" : "off");
        AudioSystem.setParameters(sb.toString());
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setA2dpSuspended(boolean z) {
        try {
            getService().setA2dpSuspended(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setLeAudioSuspended(boolean z) {
        try {
            getService().setLeAudioSuspended(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getParameters(String str) {
        Log.i(TAG, "getParameters keys = " + str);
        if (str.startsWith(AudioParameter.SEC_GLOBAL_PREFIX)) {
            return getAudioServiceConfig(str);
        }
        return AudioSystem.getParameters(str);
    }

    public static int getNthNavigationRepeatSoundEffect(int i) {
        if (i == 0) {
            return 12;
        }
        if (i == 1) {
            return 13;
        }
        if (i == 2) {
            return 14;
        }
        if (i == 3) {
            return 15;
        }
        Log.w(TAG, "Invalid navigation repeat sound effect id: " + i);
        return -1;
    }

    public void setNavigationRepeatSoundEffectsEnabled(boolean z) {
        try {
            getService().setNavigationRepeatSoundEffectsEnabled(z);
        } catch (RemoteException unused) {
        }
    }

    public boolean areNavigationRepeatSoundEffectsEnabled() {
        try {
            return getService().areNavigationRepeatSoundEffectsEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHomeSoundEffectEnabled(boolean z) {
        try {
            getService().setHomeSoundEffectEnabled(z);
        } catch (RemoteException unused) {
        }
    }

    public boolean isHomeSoundEffectEnabled() {
        try {
            return getService().isHomeSoundEffectEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int i) {
        int playSoundTypeForSEP = AudioFxHelper.getPlaySoundTypeForSEP(i);
        if (playSoundTypeForSEP < 0 || playSoundTypeForSEP >= 23) {
            return;
        }
        playSoundEffect(playSoundTypeForSEP, -2);
    }

    public void playSoundEffect(int i, int i2) {
        int playSoundTypeForSEP = AudioFxHelper.getPlaySoundTypeForSEP(i);
        if (playSoundTypeForSEP < 0 || playSoundTypeForSEP >= 23 || delegateSoundEffectToVdm(playSoundTypeForSEP)) {
            return;
        }
        try {
            getService().playSoundEffect(playSoundTypeForSEP, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int i, float f) {
        int playSoundTypeForSEP = AudioFxHelper.getPlaySoundTypeForSEP(i);
        if (playSoundTypeForSEP < 0 || playSoundTypeForSEP >= 23 || delegateSoundEffectToVdm(playSoundTypeForSEP)) {
            return;
        }
        try {
            getService().playSoundEffectVolume(playSoundTypeForSEP, f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean delegateSoundEffectToVdm(int i) {
        VirtualDeviceManager virtualDeviceManager;
        if (!hasCustomPolicyVirtualDeviceContext() || (virtualDeviceManager = getVirtualDeviceManager()) == null) {
            return false;
        }
        virtualDeviceManager.playSoundEffect(this.mOriginalContextDeviceId, i);
        return true;
    }

    private boolean hasCustomPolicyVirtualDeviceContext() {
        VirtualDeviceManager virtualDeviceManager;
        return (this.mOriginalContextDeviceId == 0 || (virtualDeviceManager = getVirtualDeviceManager()) == null || virtualDeviceManager.getDevicePolicy(this.mOriginalContextDeviceId, 1) == 0) ? false : true;
    }

    public void loadSoundEffects() {
        try {
            getService().loadSoundEffects();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unloadSoundEffects() {
        try {
            getService().unloadSoundEffects();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String audioFocusToString(int i) {
        switch (i) {
            case -3:
                return "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK";
            case -2:
                return "AUDIOFOCUS_LOSS_TRANSIENT";
            case -1:
                return "AUDIOFOCUS_LOSS";
            case 0:
                return "AUDIOFOCUS_NONE";
            case 1:
                return "AUDIOFOCUS_GAIN";
            case 2:
                return "AUDIOFOCUS_GAIN_TRANSIENT";
            case 3:
                return "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK";
            case 4:
                return "AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE";
            default:
                return "AUDIO_FOCUS_UNKNOWN(" + i + NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    private static class FocusRequestInfo {
        final Handler mHandler;
        final AudioFocusRequest mRequest;

        FocusRequestInfo(AudioFocusRequest audioFocusRequest, Handler handler) {
            this.mRequest = audioFocusRequest;
            this.mHandler = handler;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FocusRequestInfo findFocusRequestInfo(String str) {
        return this.mAudioFocusIdListenerMap.get(str);
    }

    private class ServiceEventHandlerDelegate {
        private final Handler mHandler;

        ServiceEventHandlerDelegate(Handler handler) {
            Looper looper;
            if (handler == null) {
                looper = Looper.myLooper();
                if (looper == null) {
                    looper = Looper.getMainLooper();
                }
            } else {
                looper = handler.getLooper();
            }
            if (looper != null) {
                this.mHandler = new Handler(looper) { // from class: android.media.AudioManager.ServiceEventHandlerDelegate.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        OnAudioFocusChangeListener onAudioFocusChangeListener;
                        int i = message.what;
                        if (i == 0) {
                            FocusRequestInfo focusRequestInfoFindFocusRequestInfo = AudioManager.this.findFocusRequestInfo((String) message.obj);
                            if (focusRequestInfoFindFocusRequestInfo == null || (onAudioFocusChangeListener = focusRequestInfoFindFocusRequestInfo.mRequest.getOnAudioFocusChangeListener()) == null) {
                                return;
                            }
                            Slog.i(AudioManager.TAG, "dispatching onAudioFocusChange(" + message.arg1 + ") to " + message.obj);
                            onAudioFocusChangeListener.onAudioFocusChange(message.arg1);
                            return;
                        }
                        if (i == 1) {
                            RecordConfigChangeCallbackData recordConfigChangeCallbackData = (RecordConfigChangeCallbackData) message.obj;
                            if (recordConfigChangeCallbackData.mCb != null) {
                                recordConfigChangeCallbackData.mCb.onRecordingConfigChanged(recordConfigChangeCallbackData.mConfigs);
                                return;
                            }
                            return;
                        }
                        if (i == 2) {
                            PlaybackConfigChangeCallbackData playbackConfigChangeCallbackData = (PlaybackConfigChangeCallbackData) message.obj;
                            if (playbackConfigChangeCallbackData.mCb != null) {
                                playbackConfigChangeCallbackData.mCb.onPlaybackConfigChanged(playbackConfigChangeCallbackData.mConfigs);
                                return;
                            }
                            return;
                        }
                        Log.e(AudioManager.TAG, "Unknown event " + message.what);
                    }
                };
            } else {
                this.mHandler = null;
            }
        }

        Handler getHandler() {
            return this.mHandler;
        }
    }

    private String getIdForAudioFocusListener(OnAudioFocusChangeListener onAudioFocusChangeListener) {
        if (onAudioFocusChangeListener == null) {
            return new String(toString());
        }
        return new String(toString() + onAudioFocusChangeListener.toString());
    }

    public void registerAudioFocusRequest(AudioFocusRequest audioFocusRequest) {
        Handler onAudioFocusChangeListenerHandler = audioFocusRequest.getOnAudioFocusChangeListenerHandler();
        this.mAudioFocusIdListenerMap.put(getIdForAudioFocusListener(audioFocusRequest.getOnAudioFocusChangeListener()), new FocusRequestInfo(audioFocusRequest, onAudioFocusChangeListenerHandler == null ? null : new ServiceEventHandlerDelegate(onAudioFocusChangeListenerHandler).getHandler()));
    }

    public void unregisterAudioFocusRequest(OnAudioFocusChangeListener onAudioFocusChangeListener) {
        this.mAudioFocusIdListenerMap.remove(getIdForAudioFocusListener(onAudioFocusChangeListener));
    }

    public int requestAudioFocus(OnAudioFocusChangeListener onAudioFocusChangeListener, int i, int i2) throws IllegalArgumentException {
        PlayerBase.deprecateStreamTypeForPlayback(i, TAG, "requestAudioFocus()");
        try {
            return requestAudioFocus(onAudioFocusChangeListener, new AudioAttributes.Builder().setInternalLegacyStreamType(i).build(), i2, 0);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Audio focus request denied due to ", e);
            return 0;
        }
    }

    public int requestAudioFocus(AudioFocusRequest audioFocusRequest) {
        return requestAudioFocus(audioFocusRequest, null);
    }

    public int abandonAudioFocusRequest(AudioFocusRequest audioFocusRequest) {
        if (audioFocusRequest == null) {
            throw new IllegalArgumentException("Illegal null AudioFocusRequest");
        }
        return abandonAudioFocus(audioFocusRequest.getOnAudioFocusChangeListener(), audioFocusRequest.getAudioAttributes());
    }

    @SystemApi
    public int requestAudioFocus(OnAudioFocusChangeListener onAudioFocusChangeListener, AudioAttributes audioAttributes, int i, int i2) throws IllegalArgumentException {
        int i3 = i2 & 3;
        if (i2 != i3) {
            throw new IllegalArgumentException("Invalid flags 0x" + Integer.toHexString(i2).toUpperCase());
        }
        return requestAudioFocus(onAudioFocusChangeListener, audioAttributes, i, i3, null);
    }

    @SystemApi
    public int requestAudioFocus(OnAudioFocusChangeListener onAudioFocusChangeListener, AudioAttributes audioAttributes, int i, int i2, AudioPolicy audioPolicy) throws IllegalArgumentException {
        if (audioAttributes == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes argument");
        }
        if (!AudioFocusRequest.isValidFocusGain(i)) {
            throw new IllegalArgumentException("Invalid duration hint");
        }
        if (i2 != (i2 & 7)) {
            throw new IllegalArgumentException("Illegal flags 0x" + Integer.toHexString(i2).toUpperCase());
        }
        int i3 = i2 & 1;
        if (i3 == 1 && onAudioFocusChangeListener == null) {
            throw new IllegalArgumentException("Illegal null focus listener when flagged as accepting delayed focus grant");
        }
        int i4 = i2 & 2;
        if (i4 == 2 && onAudioFocusChangeListener == null) {
            throw new IllegalArgumentException("Illegal null focus listener when flagged as pausing instead of ducking");
        }
        int i5 = i2 & 4;
        if (i5 == 4 && audioPolicy == null) {
            throw new IllegalArgumentException("Illegal null audio policy when locking audio focus");
        }
        return requestAudioFocus(new AudioFocusRequest.Builder(i).setOnAudioFocusChangeListenerInt(onAudioFocusChangeListener, null).setAudioAttributes(audioAttributes).setAcceptsDelayedFocusGain(i3 == 1).setWillPauseWhenDucked(i4 == 2).setLocksFocus(i5 == 4).build(), audioPolicy);
    }

    public int requestAudioFocusForTest(AudioFocusRequest audioFocusRequest, String str, int i, int i2) {
        Objects.requireNonNull(audioFocusRequest);
        Objects.requireNonNull(str);
        synchronized (this.mFocusRequestsLock) {
            try {
                int iRequestAudioFocusForTest = getService().requestAudioFocusForTest(audioFocusRequest.getAudioAttributes(), audioFocusRequest.getFocusGain(), this.mICallBack, this.mAudioFocusDispatcher, str, "com.android.test.fakeclient", audioFocusRequest.getFlags() | 8, i, i2);
                if (iRequestAudioFocusForTest != 100) {
                    return iRequestAudioFocusForTest;
                }
                return handleExternalAudioPolicyWaitIfNeeded(str, addClientIdToFocusReceiverLocked(str), audioFocusRequest);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int abandonAudioFocusForTest(AudioFocusRequest audioFocusRequest, String str) {
        Objects.requireNonNull(audioFocusRequest);
        Objects.requireNonNull(str);
        try {
            return getService().abandonAudioFocusForTest(this.mAudioFocusDispatcher, str, audioFocusRequest.getAudioAttributes(), "com.android.test.fakeclient");
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getFadeOutDurationOnFocusLossMillis(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes);
        try {
            return getService().getFadeOutDurationOnFocusLossMillis(audioAttributes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Integer> getFocusDuckedUidsForTest() {
        try {
            return getService().getFocusDuckedUidsForTest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getFocusFadeOutDurationForTest() {
        try {
            return getService().getFocusFadeOutDurationForTest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getFocusUnmuteDelayAfterFadeOutForTest() {
        try {
            return getService().getFocusUnmuteDelayAfterFadeOutForTest();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean enterAudioFocusFreezeForTest(List<Integer> list) {
        Objects.requireNonNull(list);
        try {
            return getService().enterAudioFocusFreezeForTest(this.mICallBack, list.stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean exitAudioFocusFreezeForTest() {
        try {
            return getService().exitAudioFocusFreezeForTest(this.mICallBack);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int requestAudioFocus(AudioFocusRequest audioFocusRequest, AudioPolicy audioPolicy) {
        int i;
        String opPackageName;
        if (audioFocusRequest == null) {
            throw new NullPointerException("Illegal null AudioFocusRequest");
        }
        if (audioFocusRequest.locksFocus() && audioPolicy == null) {
            throw new IllegalArgumentException("Illegal null audio policy when locking audio focus");
        }
        if (hasCustomPolicyVirtualDeviceContext()) {
            return 1;
        }
        registerAudioFocusRequest(audioFocusRequest);
        IAudioService service = getService();
        try {
            i = getContext().getApplicationInfo().targetSdkVersion;
        } catch (NullPointerException unused) {
            i = Build.VERSION.SDK_INT;
        }
        int i2 = i;
        String idForAudioFocusListener = getIdForAudioFocusListener(audioFocusRequest.getOnAudioFocusChangeListener());
        synchronized (this.mFocusRequestsLock) {
            try {
                boolean zContains = audioFocusRequest.getAudioAttributes().getTags().contains(FM_RADIO);
                if (Rune.SEC_AUDIO_FM_RADIO && zContains) {
                    opPackageName = AudioManagerHelper.getFmRadioPackageName(getContext());
                } else {
                    opPackageName = getContext().getOpPackageName();
                }
                int iRequestAudioFocus = service.requestAudioFocus(audioFocusRequest.getAudioAttributes(), audioFocusRequest.getFocusGain(), this.mICallBack, this.mAudioFocusDispatcher, idForAudioFocusListener, opPackageName, getContext().getAttributionTag(), audioFocusRequest.getFlags(), audioPolicy != null ? audioPolicy.cb() : null, i2);
                if (iRequestAudioFocus != 100) {
                    return iRequestAudioFocus;
                }
                return handleExternalAudioPolicyWaitIfNeeded(idForAudioFocusListener, addClientIdToFocusReceiverLocked(idForAudioFocusListener), audioFocusRequest);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private BlockingFocusResultReceiver addClientIdToFocusReceiverLocked(String str) {
        if (this.mFocusRequestsAwaitingResult == null) {
            this.mFocusRequestsAwaitingResult = new HashMap<>(1);
        }
        BlockingFocusResultReceiver blockingFocusResultReceiver = new BlockingFocusResultReceiver(str);
        this.mFocusRequestsAwaitingResult.put(str, blockingFocusResultReceiver);
        return blockingFocusResultReceiver;
    }

    private int handleExternalAudioPolicyWaitIfNeeded(String str, BlockingFocusResultReceiver blockingFocusResultReceiver, AudioFocusRequest audioFocusRequest) {
        blockingFocusResultReceiver.waitForResult(250L);
        blockingFocusResultReceiver.receivedResult();
        synchronized (this.mFocusRequestsLock) {
            this.mFocusRequestsAwaitingResult.remove(str);
        }
        return blockingFocusResultReceiver.requestResult();
    }

    private static final class SafeWaitObject {
        private boolean mQuit;

        private SafeWaitObject() {
            this.mQuit = false;
        }

        public void safeNotify() {
            synchronized (this) {
                this.mQuit = true;
                notify();
            }
        }

        public void safeWait(long j) throws InterruptedException {
            long jCurrentTimeMillis = System.currentTimeMillis() + j;
            synchronized (this) {
                while (!this.mQuit) {
                    long jCurrentTimeMillis2 = jCurrentTimeMillis - System.currentTimeMillis();
                    if (jCurrentTimeMillis2 <= 0) {
                        break;
                    } else {
                        wait(jCurrentTimeMillis2);
                    }
                }
            }
        }
    }

    private static final class BlockingFocusResultReceiver {
        private final String mFocusClientId;
        private final SafeWaitObject mLock = new SafeWaitObject();
        private boolean mResultReceived = false;
        private int mFocusRequestResult = 0;

        BlockingFocusResultReceiver(String str) {
            this.mFocusClientId = str;
        }

        boolean receivedResult() {
            return this.mResultReceived;
        }

        int requestResult() {
            return this.mFocusRequestResult;
        }

        void notifyResult(int i) {
            synchronized (this.mLock) {
                this.mResultReceived = true;
                this.mFocusRequestResult = i;
                this.mLock.safeNotify();
            }
        }

        public void waitForResult(long j) {
            synchronized (this.mLock) {
                if (this.mResultReceived) {
                    return;
                }
                try {
                    this.mLock.safeWait(j);
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    public void requestAudioFocusForCall(int i, int i2) {
        try {
            getService().requestAudioFocus(new AudioAttributes.Builder().setInternalLegacyStreamType(i).build(), i2, this.mICallBack, null, AudioSystem.IN_VOICE_COMM_FOCUS_ID, getContext().getOpPackageName(), getContext().getAttributionTag(), 4, null, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) {
        try {
            return getService().getFocusRampTimeMs(i, audioAttributes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setFocusRequestResult(AudioFocusInfo audioFocusInfo, int i, AudioPolicy audioPolicy) {
        if (audioFocusInfo == null) {
            throw new IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (audioPolicy == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy");
        }
        try {
            getService().setFocusRequestResultFromExtPolicy(audioFocusInfo, i, audioPolicy.cb());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int dispatchAudioFocusChange(AudioFocusInfo audioFocusInfo, int i, AudioPolicy audioPolicy) {
        if (audioFocusInfo == null) {
            throw new NullPointerException("Illegal null AudioFocusInfo");
        }
        if (audioPolicy == null) {
            throw new NullPointerException("Illegal null AudioPolicy");
        }
        try {
            return getService().dispatchFocusChange(audioFocusInfo, i, audioPolicy.cb());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int dispatchAudioFocusChangeWithFade(AudioFocusInfo audioFocusInfo, int i, AudioPolicy audioPolicy, List<AudioFocusInfo> list, FadeManagerConfiguration fadeManagerConfiguration) {
        Objects.requireNonNull(audioFocusInfo, "AudioFocusInfo cannot be null");
        Objects.requireNonNull(audioPolicy, "AudioPolicy cannot be null");
        Objects.requireNonNull(list, "Other active AudioFocusInfo list cannot be null");
        try {
            return getService().dispatchFocusChangeWithFade(audioFocusInfo, i, audioPolicy.cb(), list, fadeManagerConfiguration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void abandonAudioFocusForCall() {
        try {
            getService().abandonAudioFocus(null, AudioSystem.IN_VOICE_COMM_FOCUS_ID, null, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int abandonAudioFocus(OnAudioFocusChangeListener onAudioFocusChangeListener) {
        return abandonAudioFocus(onAudioFocusChangeListener, null);
    }

    @SystemApi
    public int abandonAudioFocus(OnAudioFocusChangeListener onAudioFocusChangeListener, AudioAttributes audioAttributes) {
        if (hasCustomPolicyVirtualDeviceContext()) {
            return 1;
        }
        unregisterAudioFocusRequest(onAudioFocusChangeListener);
        try {
            return getService().abandonAudioFocus(this.mAudioFocusDispatcher, getIdForAudioFocusListener(onAudioFocusChangeListener), audioAttributes, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void registerMediaButtonEventReceiver(ComponentName componentName) {
        if (componentName == null) {
            return;
        }
        if (!componentName.getPackageName().equals(getContext().getPackageName())) {
            Log.e(TAG, "registerMediaButtonEventReceiver() error: receiver and context package names don't match");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.setComponent(componentName);
        registerMediaButtonIntent(PendingIntent.getBroadcast(getContext(), 0, intent, 67108864), componentName);
    }

    @Deprecated
    public void registerMediaButtonEventReceiver(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return;
        }
        registerMediaButtonIntent(pendingIntent, null);
    }

    public void registerMediaButtonIntent(PendingIntent pendingIntent, ComponentName componentName) {
        if (pendingIntent == null) {
            Log.e(TAG, "Cannot call registerMediaButtonIntent() with a null parameter");
        } else {
            MediaSessionLegacyHelper.getHelper(getContext()).addMediaButtonListener(pendingIntent, componentName, getContext());
        }
    }

    @Deprecated
    public void unregisterMediaButtonEventReceiver(ComponentName componentName) {
        if (componentName == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.setComponent(componentName);
        unregisterMediaButtonIntent(PendingIntent.getBroadcast(getContext(), 0, intent, 67108864));
    }

    @Deprecated
    public void unregisterMediaButtonEventReceiver(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return;
        }
        unregisterMediaButtonIntent(pendingIntent);
    }

    public void unregisterMediaButtonIntent(PendingIntent pendingIntent) {
        MediaSessionLegacyHelper.getHelper(getContext()).removeMediaButtonListener(pendingIntent);
    }

    @Deprecated
    public void registerRemoteControlClient(RemoteControlClient remoteControlClient) {
        if (remoteControlClient == null || remoteControlClient.getRcMediaIntent() == null) {
            return;
        }
        remoteControlClient.registerWithSession(MediaSessionLegacyHelper.getHelper(getContext()));
    }

    @Deprecated
    public void unregisterRemoteControlClient(RemoteControlClient remoteControlClient) {
        if (remoteControlClient == null || remoteControlClient.getRcMediaIntent() == null) {
            return;
        }
        remoteControlClient.unregisterWithSession(MediaSessionLegacyHelper.getHelper(getContext()));
    }

    @Deprecated
    public boolean registerRemoteController(RemoteController remoteController) {
        if (remoteController == null) {
            return false;
        }
        remoteController.startListeningToSessions();
        return true;
    }

    @Deprecated
    public void unregisterRemoteController(RemoteController remoteController) {
        if (remoteController == null) {
            return;
        }
        remoteController.stopListeningToSessions();
    }

    @SystemApi
    public int registerAudioPolicy(AudioPolicy audioPolicy) {
        return registerAudioPolicyStatic(audioPolicy);
    }

    static int registerAudioPolicyStatic(AudioPolicy audioPolicy) {
        if (audioPolicy == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy argument");
        }
        IAudioService service = getService();
        try {
            MediaProjection mediaProjection = audioPolicy.getMediaProjection();
            String strRegisterAudioPolicy = service.registerAudioPolicy(audioPolicy.getConfig(), audioPolicy.cb(), audioPolicy.hasFocusListener(), audioPolicy.isFocusPolicy(), audioPolicy.isTestFocusPolicy(), audioPolicy.isVolumeController(), mediaProjection == null ? null : mediaProjection.getProjection(), audioPolicy.getAttributionSource());
            if (strRegisterAudioPolicy == null) {
                return -1;
            }
            audioPolicy.setRegistration(strRegisterAudioPolicy);
            return 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAudioPolicyAsync(AudioPolicy audioPolicy) {
        unregisterAudioPolicyAsyncStatic(audioPolicy);
    }

    static void unregisterAudioPolicyAsyncStatic(AudioPolicy audioPolicy) {
        if (audioPolicy == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy argument");
        }
        try {
            getService().unregisterAudioPolicyAsync(audioPolicy.cb());
            audioPolicy.reset();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterAudioPolicy(AudioPolicy audioPolicy) {
        Preconditions.checkNotNull(audioPolicy, "Illegal null AudioPolicy argument");
        IAudioService service = getService();
        try {
            audioPolicy.invalidateCaptorsAndInjectors();
            service.unregisterAudioPolicy(audioPolicy.cb());
            audioPolicy.reset();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() {
        if (!android.media.audiopolicy.Flags.audioMixTestApi()) {
            return Collections.EMPTY_LIST;
        }
        try {
            return getService().getRegisteredPolicyMixes();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasRegisteredDynamicPolicy() {
        try {
            return getService().hasRegisteredDynamicPolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class AudioPlaybackCallbackInfo {
        final AudioPlaybackCallback mCb;
        final Handler mHandler;

        AudioPlaybackCallbackInfo(AudioPlaybackCallback audioPlaybackCallback, Handler handler) {
            this.mCb = audioPlaybackCallback;
            this.mHandler = handler;
        }
    }

    private static final class PlaybackConfigChangeCallbackData {
        final AudioPlaybackCallback mCb;
        final List<AudioPlaybackConfiguration> mConfigs;

        PlaybackConfigChangeCallbackData(AudioPlaybackCallback audioPlaybackCallback, List<AudioPlaybackConfiguration> list) {
            this.mCb = audioPlaybackCallback;
            this.mConfigs = list;
        }
    }

    public void registerAudioPlaybackCallback(AudioPlaybackCallback audioPlaybackCallback, Handler handler) {
        if (audioPlaybackCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioPlaybackCallback argument");
        }
        synchronized (this.mPlaybackCallbackLock) {
            if (this.mPlaybackCallbackList == null) {
                this.mPlaybackCallbackList = new ArrayList();
            }
            int size = this.mPlaybackCallbackList.size();
            if (!hasPlaybackCallback_sync(audioPlaybackCallback)) {
                this.mPlaybackCallbackList.add(new AudioPlaybackCallbackInfo(audioPlaybackCallback, new ServiceEventHandlerDelegate(handler).getHandler()));
                int size2 = this.mPlaybackCallbackList.size();
                if (size == 0 && size2 > 0) {
                    try {
                        getService().registerPlaybackCallback(this.mPlayCb);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.w(TAG, "attempt to call registerAudioPlaybackCallback() on a previouslyregistered callback");
            }
        }
    }

    public void unregisterAudioPlaybackCallback(AudioPlaybackCallback audioPlaybackCallback) {
        if (audioPlaybackCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioPlaybackCallback argument");
        }
        synchronized (this.mPlaybackCallbackLock) {
            List<AudioPlaybackCallbackInfo> list = this.mPlaybackCallbackList;
            if (list == null) {
                Log.w(TAG, "attempt to call unregisterAudioPlaybackCallback() on a callback that was never registered");
                return;
            }
            int size = list.size();
            if (removePlaybackCallback_sync(audioPlaybackCallback)) {
                int size2 = this.mPlaybackCallbackList.size();
                if (size > 0 && size2 == 0) {
                    try {
                        getService().unregisterPlaybackCallback(this.mPlayCb);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.w(TAG, "attempt to call unregisterAudioPlaybackCallback() on a callback already unregistered or never registered");
            }
        }
    }

    public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() {
        try {
            return getService().getActivePlaybackConfigurations();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean hasPlaybackCallback_sync(AudioPlaybackCallback audioPlaybackCallback) {
        if (this.mPlaybackCallbackList != null) {
            for (int i = 0; i < this.mPlaybackCallbackList.size(); i++) {
                if (audioPlaybackCallback.equals(this.mPlaybackCallbackList.get(i).mCb)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removePlaybackCallback_sync(AudioPlaybackCallback audioPlaybackCallback) {
        if (this.mPlaybackCallbackList != null) {
            for (int i = 0; i < this.mPlaybackCallbackList.size(); i++) {
                if (audioPlaybackCallback.equals(this.mPlaybackCallbackList.get(i).mCb)) {
                    this.mPlaybackCallbackList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    private static class AudioRecordingCallbackInfo {
        final AudioRecordingCallback mCb;
        final Handler mHandler;

        AudioRecordingCallbackInfo(AudioRecordingCallback audioRecordingCallback, Handler handler) {
            this.mCb = audioRecordingCallback;
            this.mHandler = handler;
        }
    }

    private static final class RecordConfigChangeCallbackData {
        final AudioRecordingCallback mCb;
        final List<AudioRecordingConfiguration> mConfigs;

        RecordConfigChangeCallbackData(AudioRecordingCallback audioRecordingCallback, List<AudioRecordingConfiguration> list) {
            this.mCb = audioRecordingCallback;
            this.mConfigs = list;
        }
    }

    public void registerAudioRecordingCallback(AudioRecordingCallback audioRecordingCallback, Handler handler) {
        if (audioRecordingCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            if (this.mRecordCallbackList == null) {
                this.mRecordCallbackList = new ArrayList();
            }
            int size = this.mRecordCallbackList.size();
            if (!hasRecordCallback_sync(audioRecordingCallback)) {
                this.mRecordCallbackList.add(new AudioRecordingCallbackInfo(audioRecordingCallback, new ServiceEventHandlerDelegate(handler).getHandler()));
                int size2 = this.mRecordCallbackList.size();
                if (size == 0 && size2 > 0) {
                    try {
                        getService().registerRecordingCallback(this.mRecCb);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.w(TAG, "attempt to call registerAudioRecordingCallback() on a previouslyregistered callback");
            }
        }
    }

    public void unregisterAudioRecordingCallback(AudioRecordingCallback audioRecordingCallback) {
        if (audioRecordingCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            List<AudioRecordingCallbackInfo> list = this.mRecordCallbackList;
            if (list == null) {
                return;
            }
            int size = list.size();
            if (removeRecordCallback_sync(audioRecordingCallback)) {
                int size2 = this.mRecordCallbackList.size();
                if (size > 0 && size2 == 0) {
                    try {
                        getService().unregisterRecordingCallback(this.mRecCb);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                Log.w(TAG, "attempt to call unregisterAudioRecordingCallback() on a callback already unregistered or never registered");
            }
        }
    }

    public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() {
        try {
            return getService().getActiveRecordingConfigurations();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean hasRecordCallback_sync(AudioRecordingCallback audioRecordingCallback) {
        if (this.mRecordCallbackList != null) {
            for (int i = 0; i < this.mRecordCallbackList.size(); i++) {
                if (audioRecordingCallback.equals(this.mRecordCallbackList.get(i).mCb)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removeRecordCallback_sync(AudioRecordingCallback audioRecordingCallback) {
        if (this.mRecordCallbackList != null) {
            for (int i = 0; i < this.mRecordCallbackList.size(); i++) {
                if (audioRecordingCallback.equals(this.mRecordCallbackList.get(i).mCb)) {
                    this.mRecordCallbackList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public void reloadAudioSettings() {
        try {
            getService().reloadAudioSettings();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSilentMode() {
        int ringerMode = getRingerMode();
        return ringerMode == 0 || ringerMode == 1;
    }

    public static boolean isOutputDevice(int i) {
        return !AudioSystem.isInputDevice(i);
    }

    public static boolean isInputDevice(int i) {
        return AudioSystem.isInputDevice(i);
    }

    @Deprecated
    public int getDevicesForStream(int i) {
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 8 && i != 10) {
            return 0;
        }
        try {
            return getService().getDeviceMaskForStream(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<AudioDeviceAttributes> getDevicesForAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes);
        try {
            return getService().getDevicesForAttributes(audioAttributes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class IDevicesForAttributesCallbackStub extends IDevicesForAttributesCallback.Stub {
        CallbackUtil.ListenerInfo<OnDevicesForAttributesChangedListener> mInfo;

        IDevicesForAttributesCallbackStub(OnDevicesForAttributesChangedListener onDevicesForAttributesChangedListener, Executor executor) {
            this.mInfo = new CallbackUtil.ListenerInfo<>(onDevicesForAttributesChangedListener, executor);
        }

        public void register(boolean z, AudioAttributes audioAttributes) {
            try {
                if (z) {
                    AudioManager.getService().addOnDevicesForAttributesChangedListener(audioAttributes, this);
                } else {
                    AudioManager.getService().removeOnDevicesForAttributesChangedListener(this);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IDevicesForAttributesCallback
        public void onDevicesForAttributesChanged(final AudioAttributes audioAttributes, boolean z, final List<AudioDeviceAttributes> list) {
            this.mInfo.mExecutor.execute(new Runnable() { // from class: android.media.AudioManager$IDevicesForAttributesCallbackStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDevicesForAttributesChanged$0(audioAttributes, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDevicesForAttributesChanged$0(AudioAttributes audioAttributes, List list) {
            this.mInfo.mListener.onDevicesForAttributesChanged(audioAttributes, list);
        }
    }

    @SystemApi
    public void addOnDevicesForAttributesChangedListener(AudioAttributes audioAttributes, Executor executor, OnDevicesForAttributesChangedListener onDevicesForAttributesChangedListener) {
        Objects.requireNonNull(audioAttributes);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onDevicesForAttributesChangedListener);
        synchronized (this.mDevicesForAttributesListenerToStub) {
            IDevicesForAttributesCallbackStub iDevicesForAttributesCallbackStub = this.mDevicesForAttributesListenerToStub.get(onDevicesForAttributesChangedListener);
            if (iDevicesForAttributesCallbackStub == null) {
                iDevicesForAttributesCallbackStub = new IDevicesForAttributesCallbackStub(onDevicesForAttributesChangedListener, executor);
                this.mDevicesForAttributesListenerToStub.put(onDevicesForAttributesChangedListener, iDevicesForAttributesCallbackStub);
            }
            iDevicesForAttributesCallbackStub.register(true, audioAttributes);
        }
    }

    @SystemApi
    public void removeOnDevicesForAttributesChangedListener(OnDevicesForAttributesChangedListener onDevicesForAttributesChangedListener) {
        Objects.requireNonNull(onDevicesForAttributesChangedListener);
        synchronized (this.mDevicesForAttributesListenerToStub) {
            IDevicesForAttributesCallbackStub iDevicesForAttributesCallbackStub = this.mDevicesForAttributesListenerToStub.get(onDevicesForAttributesChangedListener);
            if (iDevicesForAttributesCallbackStub != null) {
                iDevicesForAttributesCallbackStub.register(false, null);
            }
            this.mDevicesForAttributesListenerToStub.remove(onDevicesForAttributesChangedListener);
        }
    }

    public List<AudioDeviceInfo> getAudioDevicesForAttributes(AudioAttributes audioAttributes) {
        try {
            Objects.requireNonNull(audioAttributes);
            List<AudioDeviceAttributes> devicesForAttributesUnprotected = getService().getDevicesForAttributesUnprotected(audioAttributes);
            AudioDeviceInfo[] devicesStatic = getDevicesStatic(2);
            ArrayList arrayList = new ArrayList();
            for (AudioDeviceAttributes audioDeviceAttributes : devicesForAttributesUnprotected) {
                for (AudioDeviceInfo audioDeviceInfo : devicesStatic) {
                    if (audioDeviceAttributes.getType() == audioDeviceInfo.getType() && TextUtils.equals(audioDeviceAttributes.getAddress(), audioDeviceInfo.getAddress())) {
                        arrayList.add(audioDeviceInfo);
                    }
                }
            }
            return Collections.unmodifiableList(arrayList);
        } catch (Exception unused) {
            Log.i(TAG, "No audio devices available for specified attributes.");
            return Collections.EMPTY_LIST;
        }
    }

    @SystemApi
    public void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i) {
        Objects.requireNonNull(audioDeviceAttributes);
        AudioDeviceVolumeManager.enforceValidVolumeBehavior(i);
        try {
            getService().setDeviceVolumeBehavior(audioDeviceAttributes, i, this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            int deviceVolumeBehavior = getService().getDeviceVolumeBehavior(audioDeviceAttributes);
            if (CompatChanges.isChangeEnabled(RETURN_DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY) || deviceVolumeBehavior != 5) {
                return deviceVolumeBehavior;
            }
            return 1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWiredDeviceConnectionState(int i, int i2, String str, String str2) {
        setWiredDeviceConnectionState(new AudioDeviceAttributes(i, str, str2), i2);
    }

    @SystemApi
    public void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i) {
        try {
            getService().setWiredDeviceConnectionState(audioDeviceAttributes, i, this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        try {
            getService().setTestDeviceConnectionState(audioDeviceAttributes, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void handleBluetoothActiveDeviceChanged(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) {
        Log.d(TAG, "handleBluetoothActiveDeviceChanged newDevice = " + AudioManagerHelper.getAddressForLog(bluetoothDevice) + ", prevDevice = " + AudioManagerHelper.getAddressForLog(bluetoothDevice2));
        try {
            getService().handleBluetoothActiveDeviceChanged(bluetoothDevice, bluetoothDevice2, bluetoothProfileConnectionInfo);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IRingtonePlayer getRingtonePlayer() {
        try {
            return getService().getRingtonePlayer();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public String getProperty(String str) {
        if (PROPERTY_OUTPUT_SAMPLE_RATE.equals(str)) {
            int primaryOutputSamplingRate = AudioSystem.getPrimaryOutputSamplingRate();
            if (primaryOutputSamplingRate > 0) {
                return Integer.toString(primaryOutputSamplingRate);
            }
            return null;
        }
        if (PROPERTY_OUTPUT_FRAMES_PER_BUFFER.equals(str)) {
            int primaryOutputFrameCount = AudioSystem.getPrimaryOutputFrameCount();
            if (primaryOutputFrameCount > 0) {
                return Integer.toString(primaryOutputFrameCount);
            }
            return null;
        }
        if (PROPERTY_SUPPORT_MIC_NEAR_ULTRASOUND.equals(str)) {
            return String.valueOf(getContext().getResources().getBoolean(R.bool.config_supportMicNearUltrasound));
        }
        if (PROPERTY_SUPPORT_SPEAKER_NEAR_ULTRASOUND.equals(str)) {
            return String.valueOf(getContext().getResources().getBoolean(R.bool.config_supportSpeakerNearUltrasound));
        }
        if (PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED.equals(str)) {
            return String.valueOf(getContext().getResources().getBoolean(R.bool.config_supportAudioSourceUnprocessed));
        }
        return null;
    }

    @SystemApi
    public boolean setAdditionalOutputDeviceDelay(AudioDeviceInfo audioDeviceInfo, long j) {
        Objects.requireNonNull(audioDeviceInfo);
        try {
            return getService().setAdditionalOutputDeviceDelay(new AudioDeviceAttributes(audioDeviceInfo), j);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public long getAdditionalOutputDeviceDelay(AudioDeviceInfo audioDeviceInfo) {
        Objects.requireNonNull(audioDeviceInfo);
        try {
            return getService().getAdditionalOutputDeviceDelay(new AudioDeviceAttributes(audioDeviceInfo));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public long getMaxAdditionalOutputDeviceDelay(AudioDeviceInfo audioDeviceInfo) {
        Objects.requireNonNull(audioDeviceInfo);
        try {
            return getService().getMaxAdditionalOutputDeviceDelay(new AudioDeviceAttributes(audioDeviceInfo));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getOutputLatency(int i) {
        return AudioSystem.getOutputLatency(i);
    }

    public void setVolumeController(IVolumeController iVolumeController) {
        try {
            getService().setVolumeController(iVolumeController);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IVolumeController getVolumeController() {
        try {
            return getService().getVolumeController();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean z) {
        try {
            getService().notifyVolumeControllerVisible(iVolumeController, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVolumeControllerLongPressTimeoutEnabled(boolean z) {
        try {
            getService().setVolumeControllerLongPressTimeoutEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStreamAffectedByRingerMode(int i) {
        try {
            return getService().isStreamAffectedByRingerMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStreamAffectedByMute(int i) {
        try {
            return getService().isStreamAffectedByMute(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStreamMutableByUi(int i) {
        try {
            return getService().isStreamMutableByUi(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableSafeMediaVolume() {
        try {
            getService().disableSafeMediaVolume(this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void lowerVolumeToRs1() {
        try {
            getService().lowerVolumeToRs1(this.mApplicationContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getRs2Value() {
        try {
            return getService().getOutputRs2UpperBound();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRs2Value(float f) {
        try {
            getService().setOutputRs2UpperBound(f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getCsd() {
        try {
            return getService().getCsd();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCsd(float f) {
        try {
            getService().setCsd(f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceUseFrameworkMel(boolean z) {
        try {
            getService().forceUseFrameworkMel(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceComputeCsdOnAllDevices(boolean z) {
        try {
            getService().forceComputeCsdOnAllDevices(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCsdEnabled() {
        try {
            return getService().isCsdEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCsdAsAFeatureAvailable() {
        try {
            return getService().isCsdAsAFeatureAvailable();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCsdAsAFeatureEnabled() {
        try {
            return getService().isCsdAsAFeatureEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCsdAsAFeatureEnabled(boolean z) {
        try {
            getService().setCsdAsAFeatureEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static String audioDeviceCategoryToString(int i) {
        switch (i) {
            case 0:
                return "AUDIO_DEVICE_CATEGORY_UNKNOWN";
            case 1:
                return "AUDIO_DEVICE_CATEGORY_OTHER";
            case 2:
                return "AUDIO_DEVICE_CATEGORY_SPEAKER";
            case 3:
                return "AUDIO_DEVICE_CATEGORY_HEADPHONES";
            case 4:
                return "AUDIO_DEVICE_CATEGORY_CARKIT";
            case 5:
                return "AUDIO_DEVICE_CATEGORY_WATCH";
            case 6:
                return "AUDIO_DEVICE_CATEGORY_HEARING_AID";
            case 7:
                return "AUDIO_DEVICE_CATEGORY_RECEIVER";
            default:
                return "unknown AudioDeviceCategory " + i;
        }
    }

    public boolean setBluetoothAudioDeviceCategory(String str, int i) {
        try {
            return getService().setBluetoothAudioDeviceCategory(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getBluetoothAudioDeviceCategory(String str) {
        try {
            return getService().getBluetoothAudioDeviceCategory(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBluetoothAudioDeviceCategoryFixed(String str) {
        try {
            return getService().isBluetoothAudioDeviceCategoryFixed(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRingerModeInternal(int i) {
        try {
            getService().setRingerModeInternal(i, getContext().getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRingerModeInternal() {
        try {
            return getService().getRingerModeInternal();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVolumePolicy(VolumePolicy volumePolicy) {
        try {
            getService().setVolumePolicy(volumePolicy);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public VolumePolicy getVolumePolicy() {
        try {
            return getService().getVolumePolicy();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setHdmiSystemAudioSupported(boolean z) {
        try {
            return getService().setHdmiSystemAudioSupported(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isHdmiSystemAudioSupported() {
        try {
            return getService().isHdmiSystemAudioSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int listAudioPorts(ArrayList<AudioPort> arrayList) {
        return updateAudioPortCache(arrayList, null, null);
    }

    public static int listPreviousAudioPorts(ArrayList<AudioPort> arrayList) {
        return updateAudioPortCache(null, null, arrayList);
    }

    public static int listAudioDevicePorts(ArrayList<AudioDevicePort> arrayList) {
        if (arrayList == null) {
            return -2;
        }
        ArrayList arrayList2 = new ArrayList();
        int iUpdateAudioPortCache = updateAudioPortCache(arrayList2, null, null);
        if (iUpdateAudioPortCache == 0) {
            filterDevicePorts(arrayList2, arrayList);
        }
        return iUpdateAudioPortCache;
    }

    public static int listPreviousAudioDevicePorts(ArrayList<AudioDevicePort> arrayList) {
        if (arrayList == null) {
            return -2;
        }
        ArrayList arrayList2 = new ArrayList();
        int iUpdateAudioPortCache = updateAudioPortCache(null, null, arrayList2);
        if (iUpdateAudioPortCache == 0) {
            filterDevicePorts(arrayList2, arrayList);
        }
        return iUpdateAudioPortCache;
    }

    private static void filterDevicePorts(ArrayList<AudioPort> arrayList, ArrayList<AudioDevicePort> arrayList2) {
        arrayList2.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) instanceof AudioDevicePort) {
                arrayList2.add((AudioDevicePort) arrayList.get(i));
            }
        }
    }

    public static int createAudioPatch(AudioPatch[] audioPatchArr, AudioPortConfig[] audioPortConfigArr, AudioPortConfig[] audioPortConfigArr2) {
        return AudioSystem.createAudioPatch(audioPatchArr, audioPortConfigArr, audioPortConfigArr2);
    }

    public static int releaseAudioPatch(AudioPatch audioPatch) {
        return AudioSystem.releaseAudioPatch(audioPatch);
    }

    public static int listAudioPatches(ArrayList<AudioPatch> arrayList) {
        return updateAudioPortCache(null, arrayList, null);
    }

    public static int setAudioPortGain(AudioPort audioPort, AudioGainConfig audioGainConfig) {
        if (audioPort == null || audioGainConfig == null) {
            return -2;
        }
        AudioPortConfig audioPortConfigActiveConfig = audioPort.activeConfig();
        AudioPortConfig audioPortConfig = new AudioPortConfig(audioPort, audioPortConfigActiveConfig.samplingRate(), audioPortConfigActiveConfig.channelMask(), audioPortConfigActiveConfig.format(), audioGainConfig);
        audioPortConfig.mConfigMask = 8;
        return AudioSystem.setAudioPortConfig(audioPortConfig);
    }

    public void registerAudioPortUpdateListener(OnAudioPortUpdateListener onAudioPortUpdateListener) {
        AudioPortEventHandler audioPortEventHandler = sAudioPortEventHandler;
        audioPortEventHandler.init();
        audioPortEventHandler.registerListener(onAudioPortUpdateListener);
    }

    public void unregisterAudioPortUpdateListener(OnAudioPortUpdateListener onAudioPortUpdateListener) {
        sAudioPortEventHandler.unregisterListener(onAudioPortUpdateListener);
    }

    static int resetAudioPortGeneration() {
        int i;
        synchronized (sAudioPortGenerationLock) {
            i = sAudioPortGeneration;
            sAudioPortGeneration = 0;
        }
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x004a, code lost:
    
        if (r7 == r8) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004e, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004f, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0054, code lost:
    
        if (r3 >= r6.size()) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0056, code lost:
    
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0062, code lost:
    
        if (r7 >= r6.get(r3).sources().length) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0064, code lost:
    
        r6.get(r3).sources()[r7] = updatePortConfig(r6.get(r3).sources()[r7], r5);
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0083, code lost:
    
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x008f, code lost:
    
        if (r7 >= r6.get(r3).sinks().length) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0091, code lost:
    
        r6.get(r3).sinks()[r7] = updatePortConfig(r6.get(r3).sinks()[r7], r5);
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b0, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00b3, code lost:
    
        r3 = r6.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00bb, code lost:
    
        if (r3.hasNext() == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00bd, code lost:
    
        r7 = r3.next();
        r8 = r7.sources();
        r9 = r8.length;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00c9, code lost:
    
        if (r10 >= r9) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00cd, code lost:
    
        if (r8[r10] != null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00cf, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00d1, code lost:
    
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d4, code lost:
    
        r8 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d5, code lost:
    
        r7 = r7.sinks();
        r9 = r7.length;
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00db, code lost:
    
        if (r10 >= r9) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00df, code lost:
    
        if (r7[r10] != null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00e1, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e3, code lost:
    
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e6, code lost:
    
        if (r8 == false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00e8, code lost:
    
        r3.remove();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ec, code lost:
    
        android.media.AudioManager.sPreviousAudioPortsCached = android.media.AudioManager.sAudioPortsCached;
        android.media.AudioManager.sAudioPortsCached = r5;
        android.media.AudioManager.sAudioPatchesCached = r6;
        android.media.AudioManager.sAudioPortGeneration = r4[0];
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static int updateAudioPortCache(ArrayList<AudioPort> arrayList, ArrayList<AudioPatch> arrayList2, ArrayList<AudioPort> arrayList3) {
        sAudioPortEventHandler.init();
        synchronized (sAudioPortGenerationLock) {
            if (sAudioPortGeneration == 0) {
                int[] iArr = new int[1];
                int[] iArr2 = new int[1];
                ArrayList<AudioPort> arrayList4 = new ArrayList<>();
                ArrayList<AudioPatch> arrayList5 = new ArrayList<>();
                while (true) {
                    arrayList4.clear();
                    int iListAudioPorts = AudioSystem.listAudioPorts(arrayList4, iArr2);
                    if (iListAudioPorts != 0) {
                        Log.w(TAG, "updateAudioPortCache: listAudioPorts failed");
                        return iListAudioPorts;
                    }
                    arrayList5.clear();
                    int iListAudioPatches = AudioSystem.listAudioPatches(arrayList5, iArr);
                    if (iListAudioPatches != 0) {
                        Log.w(TAG, "updateAudioPortCache: listAudioPatches failed");
                        return iListAudioPatches;
                    }
                    int i = iArr[0];
                    int i2 = iArr2[0];
                    if (i == i2 || (arrayList != null && arrayList2 != null)) {
                        break;
                    }
                }
            }
            if (arrayList != null) {
                arrayList.clear();
                arrayList.addAll(sAudioPortsCached);
            }
            if (arrayList2 != null) {
                arrayList2.clear();
                arrayList2.addAll(sAudioPatchesCached);
            }
            if (arrayList3 != null) {
                arrayList3.clear();
                arrayList3.addAll(sPreviousAudioPortsCached);
            }
            return 0;
        }
    }

    static AudioPortConfig updatePortConfig(AudioPortConfig audioPortConfig, ArrayList<AudioPort> arrayList) {
        AudioPort audioPortPort = audioPortConfig.port();
        int i = 0;
        while (true) {
            if (i >= arrayList.size()) {
                break;
            }
            if (arrayList.get(i).handle().equals(audioPortPort.handle())) {
                audioPortPort = arrayList.get(i);
                break;
            }
            i++;
        }
        if (i == arrayList.size()) {
            return null;
        }
        AudioGainConfig audioGainConfigGain = audioPortConfig.gain();
        if (audioGainConfigGain != null) {
            audioGainConfigGain = audioPortPort.gain(audioGainConfigGain.index()).buildConfig(audioGainConfigGain.mode(), audioGainConfigGain.channelMask(), audioGainConfigGain.values(), audioGainConfigGain.rampDurationMs());
        }
        return audioPortPort.buildConfig(audioPortConfig.samplingRate(), audioPortConfig.channelMask(), audioPortConfig.format(), audioGainConfigGain);
    }

    private static boolean checkFlags(AudioDevicePort audioDevicePort, int i) {
        return (audioDevicePort.role() == 2 && (i & 2) != 0) || (audioDevicePort.role() == 1 && (i & 1) != 0);
    }

    private static boolean checkTypes(AudioDevicePort audioDevicePort) {
        return AudioDeviceInfo.convertInternalDeviceToDeviceType(audioDevicePort.type()) != 0;
    }

    public Set<Integer> getSupportedDeviceTypes(int i) {
        if (i != 2 && i != 1) {
            throw new IllegalArgumentException("AudioManager.getSupportedDeviceTypes(0x" + Integer.toHexString(i) + ") - Invalid.");
        }
        IntArray intArray = new IntArray();
        int supportedDeviceTypes = AudioSystem.getSupportedDeviceTypes(i, intArray);
        if (supportedDeviceTypes != 0) {
            Log.e(TAG, "AudioManager.getSupportedDeviceTypes(" + i + ") failed. status:" + supportedDeviceTypes);
        }
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < intArray.size(); i2++) {
            hashSet.add(Integer.valueOf(AudioDeviceInfo.convertInternalDeviceToDeviceType(intArray.get(i2))));
        }
        return hashSet;
    }

    public AudioDeviceInfo[] getDevices(int i) {
        return getDevicesStatic(i);
    }

    private static AudioDeviceInfo[] infoListFromPortList(ArrayList<AudioDevicePort> arrayList, int i) {
        Iterator<AudioDevicePort> it = arrayList.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            AudioDevicePort next = it.next();
            if (checkTypes(next) && checkFlags(next, i)) {
                i3++;
            }
        }
        AudioDeviceInfo[] audioDeviceInfoArr = new AudioDeviceInfo[i3];
        Iterator<AudioDevicePort> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            AudioDevicePort next2 = it2.next();
            if (checkTypes(next2) && checkFlags(next2, i)) {
                audioDeviceInfoArr[i2] = new AudioDeviceInfo(next2);
                i2++;
            }
        }
        return audioDeviceInfoArr;
    }

    private static AudioDeviceInfo[] calcListDeltas(ArrayList<AudioDevicePort> arrayList, ArrayList<AudioDevicePort> arrayList2, int i) {
        ArrayList arrayList3 = new ArrayList();
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            AudioDevicePort audioDevicePort = arrayList2.get(i2);
            boolean zIsSameAs = false;
            for (int i3 = 0; i3 < arrayList.size() && !zIsSameAs; i3++) {
                zIsSameAs = audioDevicePort.isSameAs(arrayList.get(i3));
            }
            if (!zIsSameAs) {
                arrayList3.add(audioDevicePort);
            }
        }
        return infoListFromPortList(arrayList3, i);
    }

    public static AudioDeviceInfo[] getDevicesStatic(int i) {
        ArrayList arrayList = new ArrayList();
        if (listAudioDevicePorts(arrayList) != 0) {
            return new AudioDeviceInfo[0];
        }
        return infoListFromPortList(arrayList, i);
    }

    public static AudioDeviceInfo getDeviceForPortId(int i, int i2) {
        if (i == 0) {
            return null;
        }
        for (AudioDeviceInfo audioDeviceInfo : getDevicesStatic(i2)) {
            if (audioDeviceInfo.getId() == i) {
                return audioDeviceInfo;
            }
        }
        return null;
    }

    public void registerAudioDeviceCallback(AudioDeviceCallback audioDeviceCallback, Handler handler) {
        synchronized (this.mDeviceCallbacks) {
            if (audioDeviceCallback != null) {
                if (!this.mDeviceCallbacks.containsKey(audioDeviceCallback)) {
                    if (this.mDeviceCallbacks.size() == 0) {
                        if (this.mPortListener == null) {
                            this.mPortListener = new OnAmPortUpdateListener();
                        }
                        registerAudioPortUpdateListener(this.mPortListener);
                    }
                    NativeEventHandlerDelegate nativeEventHandlerDelegate = new NativeEventHandlerDelegate(this, audioDeviceCallback, handler);
                    this.mDeviceCallbacks.put(audioDeviceCallback, nativeEventHandlerDelegate);
                    broadcastDeviceListChange_sync(nativeEventHandlerDelegate.getHandler());
                }
            }
        }
    }

    public void unregisterAudioDeviceCallback(AudioDeviceCallback audioDeviceCallback) {
        synchronized (this.mDeviceCallbacks) {
            if (this.mDeviceCallbacks.containsKey(audioDeviceCallback)) {
                this.mDeviceCallbacks.remove(audioDeviceCallback);
                if (this.mDeviceCallbacks.size() == 0) {
                    unregisterAudioPortUpdateListener(this.mPortListener);
                }
            }
        }
    }

    public static void setPortIdForMicrophones(ArrayList<MicrophoneInfo> arrayList) {
        AudioDeviceInfo[] devicesStatic = getDevicesStatic(1);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            int length = devicesStatic.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    AudioDeviceInfo audioDeviceInfo = devicesStatic[i];
                    if (audioDeviceInfo.getPort().type() == arrayList.get(size).getInternalDeviceType() && TextUtils.equals(audioDeviceInfo.getAddress(), arrayList.get(size).getAddress())) {
                        arrayList.get(size).setId(audioDeviceInfo.getId());
                        break;
                    }
                    i++;
                } else {
                    Log.i(TAG, "Failed to find port id for device with type:" + arrayList.get(size).getType() + " address:" + arrayList.get(size).getAddress());
                    arrayList.remove(size);
                    break;
                }
            }
        }
    }

    public static MicrophoneInfo microphoneInfoFromAudioDeviceInfo(AudioDeviceInfo audioDeviceInfo) {
        int type = audioDeviceInfo.getType();
        MicrophoneInfo microphoneInfo = new MicrophoneInfo(audioDeviceInfo.getPort().name() + audioDeviceInfo.getId(), audioDeviceInfo.getPort().type(), audioDeviceInfo.getAddress(), (type == 15 || type == 18) ? 1 : type == 0 ? 0 : 3, -1, -1, MicrophoneInfo.POSITION_UNKNOWN, MicrophoneInfo.ORIENTATION_UNKNOWN, new ArrayList(), new ArrayList(), -3.4028235E38f, -3.4028235E38f, -3.4028235E38f, 0);
        microphoneInfo.setId(audioDeviceInfo.getId());
        return microphoneInfo;
    }

    private void addMicrophonesFromAudioDeviceInfo(ArrayList<MicrophoneInfo> arrayList, HashSet<Integer> hashSet) {
        for (AudioDeviceInfo audioDeviceInfo : getDevicesStatic(1)) {
            if (!hashSet.contains(Integer.valueOf(audioDeviceInfo.getType()))) {
                arrayList.add(microphoneInfoFromAudioDeviceInfo(audioDeviceInfo));
            }
        }
    }

    public List<MicrophoneInfo> getMicrophones() throws IOException {
        ArrayList<MicrophoneInfo> arrayList = new ArrayList<>();
        int microphones = AudioSystem.getMicrophones(arrayList);
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(18);
        if (microphones != 0) {
            if (microphones != -3) {
                Log.e(TAG, "getMicrophones failed:" + microphones);
            }
            Log.i(TAG, "fallback on device info");
            addMicrophonesFromAudioDeviceInfo(arrayList, hashSet);
            return arrayList;
        }
        setPortIdForMicrophones(arrayList);
        hashSet.add(15);
        addMicrophonesFromAudioDeviceInfo(arrayList, hashSet);
        return arrayList;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<BluetoothCodecConfig> getHwOffloadFormatsSupportedForA2dp() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int hwOffloadFormatsSupportedForBluetoothMedia = AudioSystem.getHwOffloadFormatsSupportedForBluetoothMedia(128, arrayList);
        if (hwOffloadFormatsSupportedForBluetoothMedia != 0) {
            Log.e(TAG, "getHwOffloadEncodingFormatsSupportedForA2DP failed:" + hwOffloadFormatsSupportedForBluetoothMedia);
            return arrayList2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int iAudioFormatToBluetoothSourceCodec = AudioSystem.audioFormatToBluetoothSourceCodec(((Integer) it.next()).intValue());
            if (iAudioFormatToBluetoothSourceCodec != 1000000) {
                arrayList2.add(new BluetoothCodecConfig.Builder().setCodecType(iAudioFormatToBluetoothSourceCodec).build());
            }
        }
        return arrayList2;
    }

    private List<BluetoothLeAudioCodecConfig> getHwOffloadFormatsSupportedForLeAudio(int i) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int hwOffloadFormatsSupportedForBluetoothMedia = AudioSystem.getHwOffloadFormatsSupportedForBluetoothMedia(i, arrayList);
        if (hwOffloadFormatsSupportedForBluetoothMedia != 0) {
            Log.e(TAG, "getHwOffloadEncodingFormatsSupportedForLeAudio failed:" + hwOffloadFormatsSupportedForBluetoothMedia);
            return arrayList2;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int iAudioFormatToBluetoothLeAudioSourceCodec = AudioSystem.audioFormatToBluetoothLeAudioSourceCodec(((Integer) it.next()).intValue());
            if (iAudioFormatToBluetoothLeAudioSourceCodec != 1000000) {
                arrayList2.add(new BluetoothLeAudioCodecConfig.Builder().setCodecType(iAudioFormatToBluetoothLeAudioSourceCodec).build());
            }
        }
        return arrayList2;
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<BluetoothLeAudioCodecConfig> getHwOffloadFormatsSupportedForLeAudio() {
        return getHwOffloadFormatsSupportedForLeAudio(536870912);
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public List<BluetoothLeAudioCodecConfig> getHwOffloadFormatsSupportedForLeBroadcast() {
        return getHwOffloadFormatsSupportedForLeAudio(536870914);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastDeviceListChange_sync(Handler handler) {
        ArrayList<AudioDevicePort> arrayList = new ArrayList<>();
        if (listAudioDevicePorts(arrayList) != 0) {
            return;
        }
        if (handler != null) {
            handler.sendMessage(Message.obtain(handler, 0, infoListFromPortList(arrayList, 3)));
        } else {
            AudioDeviceInfo[] audioDeviceInfoArrCalcListDeltas = calcListDeltas(this.mPreviousPorts, arrayList, 3);
            AudioDeviceInfo[] audioDeviceInfoArrCalcListDeltas2 = calcListDeltas(arrayList, this.mPreviousPorts, 3);
            if (audioDeviceInfoArrCalcListDeltas.length != 0 || audioDeviceInfoArrCalcListDeltas2.length != 0) {
                for (int i = 0; i < this.mDeviceCallbacks.size(); i++) {
                    Handler handler2 = this.mDeviceCallbacks.valueAt(i).getHandler();
                    if (handler2 != null) {
                        if (audioDeviceInfoArrCalcListDeltas2.length != 0) {
                            handler2.sendMessage(Message.obtain(handler2, 2, audioDeviceInfoArrCalcListDeltas2));
                        }
                        if (audioDeviceInfoArrCalcListDeltas.length != 0) {
                            handler2.sendMessage(Message.obtain(handler2, 1, audioDeviceInfoArrCalcListDeltas));
                        }
                    }
                }
            }
        }
        this.mPreviousPorts = arrayList;
    }

    private class OnAmPortUpdateListener implements OnAudioPortUpdateListener {
        static final String TAG = "OnAmPortUpdateListener";

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onAudioPatchListUpdate(AudioPatch[] audioPatchArr) {
        }

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onServiceDied() {
        }

        private OnAmPortUpdateListener() {
        }

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onAudioPortListUpdate(AudioPort[] audioPortArr) {
            synchronized (AudioManager.this.mDeviceCallbacks) {
                AudioManager.this.broadcastDeviceListChange_sync(null);
            }
        }
    }

    /* renamed from: android.media.AudioManager$5, reason: invalid class name */
    class AnonymousClass5 extends IAudioServerStateDispatcher.Stub {
        AnonymousClass5() {
        }

        @Override // android.media.IAudioServerStateDispatcher
        public void dispatchAudioServerStateChange(boolean z) {
            Executor executor;
            final AudioServerStateCallback audioServerStateCallback;
            synchronized (AudioManager.this.mAudioServerStateCbLock) {
                executor = AudioManager.this.mAudioServerStateExec;
                audioServerStateCallback = AudioManager.this.mAudioServerStateCb;
            }
            if (executor == null || audioServerStateCallback == null) {
                return;
            }
            if (z) {
                executor.execute(new Runnable() { // from class: android.media.AudioManager$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        audioServerStateCallback.onAudioServerUp();
                    }
                });
            } else {
                executor.execute(new Runnable() { // from class: android.media.AudioManager$5$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        audioServerStateCallback.onAudioServerDown();
                    }
                });
            }
        }
    }

    @SystemApi
    public void setAudioServerStateCallback(Executor executor, AudioServerStateCallback audioServerStateCallback) {
        if (audioServerStateCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioServerStateCallback");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Illegal null Executor for the AudioServerStateCallback");
        }
        synchronized (this.mAudioServerStateCbLock) {
            if (this.mAudioServerStateCb != null) {
                throw new IllegalStateException("setAudioServerStateCallback called with already registered callabck");
            }
            try {
                getService().registerAudioServerStateDispatcher(this.mAudioServerStateDispatcher);
                this.mAudioServerStateExec = executor;
                this.mAudioServerStateCb = audioServerStateCallback;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void clearAudioServerStateCallback() {
        synchronized (this.mAudioServerStateCbLock) {
            if (this.mAudioServerStateCb != null) {
                try {
                    getService().unregisterAudioServerStateDispatcher(this.mAudioServerStateDispatcher);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mAudioServerStateExec = null;
            this.mAudioServerStateCb = null;
        }
    }

    @SystemApi
    public boolean isAudioServerRunning() {
        try {
            return getService().isAudioServerRunning();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setEncodedSurroundMode(int i) {
        try {
            return getService().setEncodedSurroundMode(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getEncodedSurroundMode() {
        try {
            return getService().getEncodedSurroundMode(getContext().getApplicationInfo().targetSdkVersion);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Map<Integer, Boolean> getSurroundFormats() {
        try {
            return getService().getSurroundFormats();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setSurroundFormatEnabled(int i, boolean z) {
        try {
            return getService().setSurroundFormatEnabled(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSurroundFormatEnabled(int i) {
        try {
            return getService().isSurroundFormatEnabled(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Integer> getReportedSurroundFormats() {
        try {
            return getService().getReportedSurroundFormats();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isHapticPlaybackSupported() {
        return AudioSystem.isHapticPlaybackSupported();
    }

    @SystemApi
    public boolean isUltrasoundSupported() {
        try {
            return getService().isUltrasoundSupported();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isHotwordStreamSupported(boolean z) {
        try {
            return getService().isHotwordStreamSupported(z);
        } catch (RemoteException unused) {
            return false;
        }
    }

    @SystemApi
    public static List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() {
        try {
            return getService().getAudioProductStrategies();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() {
        try {
            return getService().getAudioVolumeGroups();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerVolumeGroupCallback(Executor executor, VolumeGroupCallback volumeGroupCallback) {
        this.mVolumeChangedListenerMgr.addListener(executor, volumeGroupCallback, "registerVolumeGroupCallback", new Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$registerVolumeGroupCallback$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$registerVolumeGroupCallback$3() {
        return new AudioVolumeChangeDispatcherStub();
    }

    @SystemApi
    public void unregisterVolumeGroupCallback(VolumeGroupCallback volumeGroupCallback) {
        this.mVolumeChangedListenerMgr.removeListener(volumeGroupCallback, "unregisterVolumeGroupCallback");
    }

    final class AudioVolumeChangeDispatcherStub extends IAudioVolumeChangeDispatcher.Stub implements CallbackUtil.DispatcherStub {
        AudioVolumeChangeDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    AudioManager.getService().registerAudioVolumeCallback(this);
                } else {
                    AudioManager.getService().unregisterAudioVolumeCallback(this);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.audiopolicy.IAudioVolumeChangeDispatcher
        public void onAudioVolumeGroupChanged(final int i, final int i2) {
            AudioManager.this.mVolumeChangedListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$AudioVolumeChangeDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioManager.VolumeGroupCallback) obj).onAudioVolumeGroupChanged(i, i2);
                }
            });
        }
    }

    public static boolean hasHapticChannelsImpl(Context context, Uri uri) {
        MediaExtractor mediaExtractor = new MediaExtractor();
        try {
            mediaExtractor.setDataSource(context, uri, (Map<String, String>) null);
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                if (trackFormat.containsKey(MediaFormat.KEY_HAPTIC_CHANNEL_COUNT) && trackFormat.getInteger(MediaFormat.KEY_HAPTIC_CHANNEL_COUNT) > 0) {
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "hasHapticChannels failure:" + e);
        }
        return false;
    }

    public static boolean hasHapticChannels(Context context, Uri uri) {
        Objects.requireNonNull(uri);
        if (context != null) {
            return hasHapticChannelsImpl(context, uri);
        }
        Context context2 = sContext.get();
        if (context2 != null) {
            return hasHapticChannelsImpl(context2, uri);
        }
        try {
            return getService().hasHapticChannels(uri);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setRttEnabled(boolean z) {
        try {
            getService().setRttEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, int i6) {
        if (AudioManagerHelper.needToLogCaller(getContext().getOpPackageName())) {
            AudioManagerHelper.logCaller("suggestedStreamType=%d, direction=%d", Integer.valueOf(i), Integer.valueOf(i2));
        }
        try {
            getService().adjustSuggestedStreamVolumeForUid(i, i2, i3, str, i4, i5, UserHandle.getUserHandleForUid(i4), i6);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void adjustStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, int i6) {
        if (AudioManagerHelper.needToLogCaller(getContext().getOpPackageName())) {
            AudioManagerHelper.logCaller("suggestedStreamType=%d, direction=%d", Integer.valueOf(i), Integer.valueOf(i2));
        }
        try {
            getService().adjustStreamVolumeForUid(i, i2, i3, str, i4, i5, UserHandle.getUserHandleForUid(i4), i6);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, int i6) {
        if (AudioManagerHelper.needToLogCaller(getContext().getOpPackageName())) {
            AudioManagerHelper.logCaller("streamType=%d, index=%d", Integer.valueOf(i), Integer.valueOf(i2));
        }
        try {
            getService().setStreamVolumeForUid(i, i2, i3, str, i4, i5, UserHandle.getUserHandleForUid(i4), i6);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMultiAudioFocusEnabled(boolean z) {
        try {
            getService().setMultiAudioFocusEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioHwSyncForSession(int i) {
        int audioHwSyncForSession = AudioSystem.getAudioHwSyncForSession(i);
        if (audioHwSyncForSession != 0) {
            return audioHwSyncForSession;
        }
        throw new UnsupportedOperationException("HW A/V synchronization is not supported.");
    }

    public boolean setCommunicationDevice(AudioDeviceInfo audioDeviceInfo) {
        Objects.requireNonNull(audioDeviceInfo);
        try {
            if (audioDeviceInfo.getId() == 0) {
                Log.w(TAG, "setCommunicationDevice: device not found: " + audioDeviceInfo);
                return false;
            }
            return getService().setCommunicationDevice(this.mICallBack, audioDeviceInfo.getId(), getAttributionSource());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearCommunicationDevice() {
        try {
            getService().setCommunicationDevice(this.mICallBack, 0, getAttributionSource());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AudioDeviceInfo getCommunicationDevice() {
        try {
            return getDeviceForPortId(getService().getCommunicationDevice(), 2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<AudioDeviceInfo> getAvailableCommunicationDevices() {
        try {
            ArrayList arrayList = new ArrayList();
            for (int i : getService().getAvailableCommunicationDeviceIds()) {
                AudioDeviceInfo deviceForPortId = getDeviceForPortId(i, 2);
                if (deviceForPortId != null) {
                    arrayList.add(deviceForPortId);
                }
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<AudioProfile> getDirectProfilesForAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes);
        ArrayList arrayList = new ArrayList();
        if (AudioSystem.getDirectProfilesForAttributes(audioAttributes, arrayList) == 0) {
            return arrayList;
        }
        Log.w(TAG, "getDirectProfilesForAttributes failed.");
        return new ArrayList();
    }

    public static AudioDeviceInfo getDeviceInfoFromType(int i) {
        return getDeviceInfoFromTypeAndAddress(i, null);
    }

    public static AudioDeviceInfo getDeviceInfoFromTypeAndAddress(int i, String str) {
        AudioDeviceInfo audioDeviceInfo = null;
        for (AudioDeviceInfo audioDeviceInfo2 : getDevicesStatic(2)) {
            if (audioDeviceInfo2.getType() == i) {
                if (str == null || str.equals(audioDeviceInfo2.getAddress())) {
                    return audioDeviceInfo2;
                }
                audioDeviceInfo = audioDeviceInfo2;
            }
        }
        return audioDeviceInfo;
    }

    public void addOnCommunicationDeviceChangedListener(Executor executor, OnCommunicationDeviceChangedListener onCommunicationDeviceChangedListener) {
        this.mCommDeviceChangedListenerMgr.addListener(executor, onCommunicationDeviceChangedListener, "addOnCommunicationDeviceChangedListener", new Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnCommunicationDeviceChangedListener$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnCommunicationDeviceChangedListener$4() {
        return new CommunicationDeviceDispatcherStub();
    }

    public void removeOnCommunicationDeviceChangedListener(OnCommunicationDeviceChangedListener onCommunicationDeviceChangedListener) {
        this.mCommDeviceChangedListenerMgr.removeListener(onCommunicationDeviceChangedListener, "removeOnCommunicationDeviceChangedListener");
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class CommunicationDeviceDispatcherStub extends ICommunicationDeviceDispatcher.Stub implements CallbackUtil.DispatcherStub {
        private CommunicationDeviceDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    AudioManager.getService().registerCommunicationDeviceDispatcher(this);
                } else {
                    AudioManager.getService().unregisterCommunicationDeviceDispatcher(this);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.ICommunicationDeviceDispatcher
        public void dispatchCommunicationDeviceChanged(int i) {
            final AudioDeviceInfo deviceForPortId = AudioManager.getDeviceForPortId(i, 2);
            AudioManager.this.mCommDeviceChangedListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$CommunicationDeviceDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioManager.OnCommunicationDeviceChangedListener) obj).onCommunicationDeviceChanged(deviceForPortId);
                }
            });
        }
    }

    @SystemApi
    public boolean isPstnCallAudioInterceptable() {
        try {
            return getService().isPstnCallAudioInterceptable();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getCallRedirectMode() {
        int mode = getMode();
        if (mode == 2 || mode == 4 || mode == 5) {
            return 1;
        }
        return (mode == 3 || mode == 6) ? 2 : 0;
    }

    private void checkCallRedirectionFormat(AudioFormat audioFormat, boolean z) {
        if (audioFormat.getEncoding() != 2 && audioFormat.getEncoding() != 4) {
            throw new UnsupportedOperationException(" Unsupported encoding ");
        }
        if (audioFormat.getSampleRate() < 8000 || audioFormat.getSampleRate() > 48000) {
            throw new UnsupportedOperationException(" Unsupported sample rate ");
        }
        if (z && audioFormat.getChannelMask() != 4 && audioFormat.getChannelMask() != 12) {
            throw new UnsupportedOperationException(" Unsupported output channel mask ");
        }
        if (!z && audioFormat.getChannelMask() != 16 && audioFormat.getChannelMask() != 12) {
            throw new UnsupportedOperationException(" Unsupported input channel mask ");
        }
    }

    class CallIRedirectionClientInfo {
        public int redirectMode;
        public WeakReference trackOrRecord;

        CallIRedirectionClientInfo(AudioManager audioManager) {
        }
    }

    @SystemApi
    public AudioTrack getCallUplinkInjectionAudioTrack(AudioFormat audioFormat) throws UnsupportedOperationException {
        Objects.requireNonNull(audioFormat);
        checkCallRedirectionFormat(audioFormat, true);
        int callRedirectMode = getCallRedirectMode();
        if (callRedirectMode == 0) {
            throw new IllegalStateException(" not available in mode " + AudioSystem.modeToString(getMode()));
        }
        if (callRedirectMode == 1 && !isPstnCallAudioInterceptable()) {
            throw new UnsupportedOperationException(" PSTN Call audio not accessible ");
        }
        AudioTrack audioTrackBuild = new AudioTrack.Builder().setAudioAttributes(new AudioAttributes.Builder().setSystemUsage(17).setContentType(1).build()).setAudioFormat(audioFormat).setCallRedirectionMode(callRedirectMode).build();
        if (audioTrackBuild != null && audioTrackBuild.getState() != 0) {
            synchronized (this.mCallRedirectionLock) {
                if (this.mCallRedirectionModeListener == null) {
                    this.mCallRedirectionModeListener = new CallInjectionModeChangedListener();
                    try {
                        addOnModeChangedListener(Executors.newSingleThreadExecutor(), this.mCallRedirectionModeListener);
                        this.mCallIRedirectionClients = new ArrayList<>();
                    } catch (Exception e) {
                        Log.e(TAG, "addOnModeChangedListener failed with exception: " + e);
                        this.mCallRedirectionModeListener = null;
                        throw new UnsupportedOperationException(" Cannot register mode listener ");
                    }
                }
                CallIRedirectionClientInfo callIRedirectionClientInfo = new CallIRedirectionClientInfo(this);
                callIRedirectionClientInfo.redirectMode = callRedirectMode;
                callIRedirectionClientInfo.trackOrRecord = new WeakReference(audioTrackBuild);
                this.mCallIRedirectionClients.add(callIRedirectionClientInfo);
            }
            return audioTrackBuild;
        }
        throw new UnsupportedOperationException(" Cannot create the AudioTrack");
    }

    @SystemApi
    public AudioRecord getCallDownlinkExtractionAudioRecord(AudioFormat audioFormat) throws UnsupportedOperationException {
        Objects.requireNonNull(audioFormat);
        checkCallRedirectionFormat(audioFormat, false);
        int callRedirectMode = getCallRedirectMode();
        if (callRedirectMode == 0) {
            throw new IllegalStateException(" not available in mode " + AudioSystem.modeToString(getMode()));
        }
        if (callRedirectMode == 1 && !isPstnCallAudioInterceptable()) {
            throw new UnsupportedOperationException(" PSTN Call audio not accessible ");
        }
        AudioRecord audioRecordBuild = new AudioRecord.Builder().setAudioAttributes(new AudioAttributes.Builder().setInternalCapturePreset(3).build()).setAudioFormat(audioFormat).setCallRedirectionMode(callRedirectMode).build();
        if (audioRecordBuild != null && audioRecordBuild.getState() != 0) {
            synchronized (this.mCallRedirectionLock) {
                if (this.mCallRedirectionModeListener == null) {
                    this.mCallRedirectionModeListener = new CallInjectionModeChangedListener();
                    try {
                        addOnModeChangedListener(Executors.newSingleThreadExecutor(), this.mCallRedirectionModeListener);
                        this.mCallIRedirectionClients = new ArrayList<>();
                    } catch (Exception e) {
                        Log.e(TAG, "addOnModeChangedListener failed with exception: " + e);
                        this.mCallRedirectionModeListener = null;
                        throw new UnsupportedOperationException(" Cannot register mode listener ");
                    }
                }
                CallIRedirectionClientInfo callIRedirectionClientInfo = new CallIRedirectionClientInfo(this);
                callIRedirectionClientInfo.redirectMode = callRedirectMode;
                callIRedirectionClientInfo.trackOrRecord = new WeakReference(audioRecordBuild);
                this.mCallIRedirectionClients.add(callIRedirectionClientInfo);
            }
            return audioRecordBuild;
        }
        throw new UnsupportedOperationException(" Cannot create the AudioRecord");
    }

    class CallInjectionModeChangedListener implements OnModeChangedListener {
        CallInjectionModeChangedListener() {
        }

        @Override // android.media.AudioManager.OnModeChangedListener
        public void onModeChanged(int i) {
            AudioManager audioManager;
            synchronized (AudioManager.this.mCallRedirectionLock) {
                Iterator it = ((ArrayList) AudioManager.this.mCallIRedirectionClients.clone()).iterator();
                while (it.hasNext()) {
                    CallIRedirectionClientInfo callIRedirectionClientInfo = (CallIRedirectionClientInfo) it.next();
                    Object obj = callIRedirectionClientInfo.trackOrRecord.get();
                    if (obj != null && ((callIRedirectionClientInfo.redirectMode == 1 && i != 2 && i != 4 && i != 5) || (callIRedirectionClientInfo.redirectMode == 2 && i != 3 && i != 6))) {
                        if (obj instanceof AudioTrack) {
                            ((AudioTrack) obj).release();
                        } else {
                            ((AudioRecord) obj).release();
                        }
                        AudioManager.this.mCallIRedirectionClients.remove(callIRedirectionClientInfo);
                    }
                }
                if (AudioManager.this.mCallIRedirectionClients.isEmpty()) {
                    try {
                        try {
                            if (AudioManager.this.mCallRedirectionModeListener != null) {
                                AudioManager audioManager2 = AudioManager.this;
                                audioManager2.removeOnModeChangedListener(audioManager2.mCallRedirectionModeListener);
                            }
                            AudioManager.this.mCallRedirectionModeListener = null;
                            audioManager = AudioManager.this;
                        } catch (Throwable th) {
                            AudioManager.this.mCallRedirectionModeListener = null;
                            AudioManager.this.mCallIRedirectionClients = null;
                            throw th;
                        }
                    } catch (Exception e) {
                        Log.e(AudioManager.TAG, "removeOnModeChangedListener failed with exception: " + e);
                        AudioManager.this.mCallRedirectionModeListener = null;
                        audioManager = AudioManager.this;
                    }
                    audioManager.mCallIRedirectionClients = null;
                }
            }
        }
    }

    @SystemApi
    public void muteAwaitConnection(int[] iArr, AudioDeviceAttributes audioDeviceAttributes, long j, TimeUnit timeUnit) throws IllegalStateException {
        if (j <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than 0");
        }
        Objects.requireNonNull(iArr);
        if (iArr.length == 0) {
            throw new IllegalArgumentException("Array of usages to mute cannot be empty");
        }
        Objects.requireNonNull(audioDeviceAttributes);
        Objects.requireNonNull(timeUnit);
        try {
            getService().muteAwaitConnection(iArr, audioDeviceAttributes, timeUnit.toMillis(j));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public AudioDeviceAttributes getMutingExpectedDevice() {
        try {
            return getService().getMutingExpectedDevice();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void cancelMuteAwaitConnection(AudioDeviceAttributes audioDeviceAttributes) throws IllegalStateException {
        Objects.requireNonNull(audioDeviceAttributes);
        try {
            getService().cancelMuteAwaitConnection(audioDeviceAttributes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SystemApi
    public void registerMuteAwaitConnectionCallback(Executor executor, MuteAwaitConnectionCallback muteAwaitConnectionCallback) {
        synchronized (this.mMuteAwaitConnectionListenerLock) {
            Pair pairAddListener = CallbackUtil.addListener("registerMuteAwaitConnectionCallback", executor, muteAwaitConnectionCallback, this.mMuteAwaitConnectionListeners, this.mMuteAwaitConnDispatcherStub, new Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$registerMuteAwaitConnectionCallback$5();
                }
            }, new Consumer() { // from class: android.media.AudioManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AudioManager.MuteAwaitConnectionDispatcherStub) obj).register(true);
                }
            });
            this.mMuteAwaitConnectionListeners = (ArrayList) pairAddListener.first;
            this.mMuteAwaitConnDispatcherStub = (MuteAwaitConnectionDispatcherStub) pairAddListener.second;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ MuteAwaitConnectionDispatcherStub lambda$registerMuteAwaitConnectionCallback$5() {
        return new MuteAwaitConnectionDispatcherStub();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SystemApi
    public void unregisterMuteAwaitConnectionCallback(MuteAwaitConnectionCallback muteAwaitConnectionCallback) {
        synchronized (this.mMuteAwaitConnectionListenerLock) {
            Pair pairRemoveListener = CallbackUtil.removeListener("unregisterMuteAwaitConnectionCallback", muteAwaitConnectionCallback, this.mMuteAwaitConnectionListeners, this.mMuteAwaitConnDispatcherStub, new Consumer() { // from class: android.media.AudioManager$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AudioManager.MuteAwaitConnectionDispatcherStub) obj).register(false);
                }
            });
            this.mMuteAwaitConnectionListeners = (ArrayList) pairRemoveListener.first;
            this.mMuteAwaitConnDispatcherStub = (MuteAwaitConnectionDispatcherStub) pairRemoveListener.second;
        }
    }

    @SystemApi
    public void addAssistantServicesUids(int[] iArr) {
        try {
            getService().addAssistantServicesUids(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void removeAssistantServicesUids(int[] iArr) {
        try {
            getService().removeAssistantServicesUids(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int[] getAssistantServicesUids() {
        try {
            int[] assistantServicesUids = getService().getAssistantServicesUids();
            return Arrays.copyOf(assistantServicesUids, assistantServicesUids.length);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setActiveAssistantServiceUids(int[] iArr) {
        try {
            getService().setActiveAssistantServiceUids(iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int[] getActiveAssistantServicesUids() {
        try {
            int[] activeAssistantServiceUids = getService().getActiveAssistantServiceUids();
            return Arrays.copyOf(activeAssistantServiceUids, activeAssistantServiceUids.length);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static AudioHalVersionInfo getHalVersion() {
        try {
            return getService().getHalVersion();
        } catch (RemoteException e) {
            Log.e(TAG, "Error querying getHalVersion", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public List<AudioMixerAttributes> getSupportedMixerAttributes(AudioDeviceInfo audioDeviceInfo) {
        Objects.requireNonNull(audioDeviceInfo);
        ArrayList arrayList = new ArrayList();
        return AudioSystem.getSupportedMixerAttributes(audioDeviceInfo.getId(), arrayList) == 0 ? arrayList : new ArrayList();
    }

    public boolean setPreferredMixerAttributes(AudioAttributes audioAttributes, AudioDeviceInfo audioDeviceInfo, AudioMixerAttributes audioMixerAttributes) {
        Objects.requireNonNull(audioAttributes);
        Objects.requireNonNull(audioDeviceInfo);
        Objects.requireNonNull(audioMixerAttributes);
        try {
            return getService().setPreferredMixerAttributes(audioAttributes, audioDeviceInfo.getId(), audioMixerAttributes) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AudioMixerAttributes getPreferredMixerAttributes(AudioAttributes audioAttributes, AudioDeviceInfo audioDeviceInfo) {
        Objects.requireNonNull(audioAttributes);
        Objects.requireNonNull(audioDeviceInfo);
        ArrayList arrayList = new ArrayList();
        int preferredMixerAttributes = AudioSystem.getPreferredMixerAttributes(audioAttributes, audioDeviceInfo.getId(), arrayList);
        if (preferredMixerAttributes == 0) {
            if (arrayList.isEmpty()) {
                return null;
            }
            return (AudioMixerAttributes) arrayList.get(0);
        }
        Log.e(TAG, "Failed calling getPreferredMixerAttributes, ret=" + preferredMixerAttributes);
        return null;
    }

    public boolean clearPreferredMixerAttributes(AudioAttributes audioAttributes, AudioDeviceInfo audioDeviceInfo) {
        Objects.requireNonNull(audioAttributes);
        Objects.requireNonNull(audioDeviceInfo);
        try {
            return getService().clearPreferredMixerAttributes(audioAttributes, audioDeviceInfo.getId()) == 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnPreferredMixerAttributesChangedListener(Executor executor, OnPreferredMixerAttributesChangedListener onPreferredMixerAttributesChangedListener) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(onPreferredMixerAttributesChangedListener);
        this.mPrefMixerAttributesListenerMgr.addListener(executor, onPreferredMixerAttributesChangedListener, "addOnPreferredMixerAttributesChangedListener", new Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnPreferredMixerAttributesChangedListener$8();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnPreferredMixerAttributesChangedListener$8() {
        return new PreferredMixerAttributesDispatcherStub();
    }

    public void removeOnPreferredMixerAttributesChangedListener(OnPreferredMixerAttributesChangedListener onPreferredMixerAttributesChangedListener) {
        Objects.requireNonNull(onPreferredMixerAttributesChangedListener);
        this.mPrefMixerAttributesListenerMgr.removeListener(onPreferredMixerAttributesChangedListener, "removeOnPreferredMixerAttributesChangedListener");
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class PreferredMixerAttributesDispatcherStub extends IPreferredMixerAttributesDispatcher.Stub implements CallbackUtil.DispatcherStub {
        private PreferredMixerAttributesDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    AudioManager.getService().registerPreferredMixerAttributesDispatcher(this);
                } else {
                    AudioManager.getService().unregisterPreferredMixerAttributesDispatcher(this);
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IPreferredMixerAttributesDispatcher
        public void dispatchPrefMixerAttributesChanged(final AudioAttributes audioAttributes, int i, final AudioMixerAttributes audioMixerAttributes) {
            final AudioDeviceInfo deviceForPortId = AudioManager.getDeviceForPortId(i, 2);
            if (deviceForPortId == null) {
                Log.d(AudioManager.TAG, "Drop preferred mixer attributes changed as the device(" + i + ") is disconnected");
                return;
            }
            AudioManager.this.mPrefMixerAttributesListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$PreferredMixerAttributesDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioManager.OnPreferredMixerAttributesChangedListener) obj).onPreferredMixerAttributesChanged(audioAttributes, deviceForPortId, audioMixerAttributes);
                }
            });
        }
    }

    @SystemApi
    public boolean supportsBluetoothVariableLatency() {
        try {
            return getService().supportsBluetoothVariableLatency();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setBluetoothVariableLatencyEnabled(boolean z) {
        try {
            getService().setBluetoothVariableLatencyEnabled(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isBluetoothVariableLatencyEnabled() {
        try {
            return getService().isBluetoothVariableLatencyEnabled();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    final class StreamAliasingDispatcherStub extends IStreamAliasingDispatcher.Stub implements CallbackUtil.DispatcherStub {
        StreamAliasingDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                AudioManager.getService().registerStreamAliasingDispatcher(this, z);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IStreamAliasingDispatcher
        public void dispatchStreamAliasingChanged() {
            AudioManager.this.mStreamAliasingListenerMgr.callListeners(new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$StreamAliasingDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((Runnable) obj).run();
                }
            });
        }
    }

    @SystemApi
    public void addOnStreamAliasingChangedListener(Executor executor, Runnable runnable) {
        this.mStreamAliasingListenerMgr.addListener(executor, runnable, "addOnStreamAliasingChangedListener", new Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addOnStreamAliasingChangedListener$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CallbackUtil.DispatcherStub lambda$addOnStreamAliasingChangedListener$9() {
        return new StreamAliasingDispatcherStub();
    }

    @SystemApi
    public void removeOnStreamAliasingChangedListener(Runnable runnable) {
        this.mStreamAliasingListenerMgr.removeListener(runnable, "removeOnStreamAliasingChangedListener");
    }

    public void setNotifAliasRingForTest(boolean z) {
        try {
            getService().setNotifAliasRingForTest(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void permissionUpdateBarrier() {
        try {
            getService().permissionUpdateBarrier();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<Integer> getIndependentStreamTypes() {
        try {
            return getService().getIndependentStreamTypes();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getStreamTypeAlias(int i) {
        try {
            return getService().getStreamTypeAlias(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVolumeControlUsingVolumeGroups() {
        try {
            return getService().isVolumeControlUsingVolumeGroups();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldNotificationSoundPlay(AudioAttributes audioAttributes) {
        try {
            return getService().shouldNotificationSoundPlay((AudioAttributes) Objects.requireNonNull(audioAttributes));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEnableHardening(boolean z) {
        try {
            getService().setEnableHardening(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class MuteAwaitConnectionDispatcherStub extends IMuteAwaitConnectionCallback.Stub {
        private MuteAwaitConnectionDispatcherStub() {
        }

        public void register(boolean z) {
            try {
                AudioManager.getService().registerMuteAwaitConnectionDispatcher(this, z);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnMutedUntilConnection(final AudioDeviceAttributes audioDeviceAttributes, final int[] iArr) {
            CallbackUtil.callListeners(AudioManager.this.mMuteAwaitConnectionListeners, AudioManager.this.mMuteAwaitConnectionListenerLock, new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$MuteAwaitConnectionDispatcherStub$$ExternalSyntheticLambda1
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioManager.MuteAwaitConnectionCallback) obj).onMutedUntilConnection(audioDeviceAttributes, iArr);
                }
            });
        }

        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnUnmutedEvent(final int i, final AudioDeviceAttributes audioDeviceAttributes, final int[] iArr) {
            CallbackUtil.callListeners(AudioManager.this.mMuteAwaitConnectionListeners, AudioManager.this.mMuteAwaitConnectionListenerLock, new CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$MuteAwaitConnectionDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(Object obj) {
                    ((AudioManager.MuteAwaitConnectionCallback) obj).onUnmutedEvent(i, audioDeviceAttributes, iArr);
                }
            });
        }
    }

    private void initPlatform() {
        try {
            Context context = getContext();
            if (context != null) {
                this.mIsAutomotive = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error querying system feature for AUTOMOTIVE", e);
        }
    }

    private boolean applyAutoHardening() {
        return this.mIsAutomotive && com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags.autoPublicVolumeApiHardening();
    }

    private class NativeEventHandlerDelegate {
        private final Handler mHandler;

        NativeEventHandlerDelegate(final AudioManager audioManager, final AudioDeviceCallback audioDeviceCallback, Handler handler) {
            Looper mainLooper;
            if (handler != null) {
                mainLooper = handler.getLooper();
            } else {
                mainLooper = Looper.getMainLooper();
            }
            if (mainLooper != null) {
                this.mHandler = new Handler(this, mainLooper) { // from class: android.media.AudioManager.NativeEventHandlerDelegate.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        int i = message.what;
                        if (i == 0 || i == 1) {
                            AudioDeviceCallback audioDeviceCallback2 = audioDeviceCallback;
                            if (audioDeviceCallback2 != null) {
                                audioDeviceCallback2.onAudioDevicesAdded((AudioDeviceInfo[]) message.obj);
                                return;
                            }
                            return;
                        }
                        if (i == 2) {
                            AudioDeviceCallback audioDeviceCallback3 = audioDeviceCallback;
                            if (audioDeviceCallback3 != null) {
                                audioDeviceCallback3.onAudioDevicesRemoved((AudioDeviceInfo[]) message.obj);
                                return;
                            }
                            return;
                        }
                        Log.e(AudioManager.TAG, "Unknown native event type: " + message.what);
                    }
                };
            } else {
                this.mHandler = null;
            }
        }

        Handler getHandler() {
            return this.mHandler;
        }
    }

    public void setForceSpeakerOn(boolean z) {
        IAudioService service = getService();
        try {
            Log.d(TAG, "setForceSpeakerOn " + z);
            service.setForceSpeakerOn(z);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setForceSpeakerOn", e);
        }
    }

    public boolean isForceSpeakerOn() {
        try {
            return getService().isForceSpeakerOn();
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in isForceSpeakerOn", e);
            return false;
        }
    }

    public boolean semSetRadioOutputPath(int i) {
        IAudioService service = getService();
        try {
            if (i != 2 && i != 3) {
                Log.w(TAG, "Invalid path");
                return false;
            }
            service.setRadioOutputPath(i);
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setRadioOutputPath", e);
            return false;
        }
    }

    public int semGetRadioOutputPath() {
        try {
            return getService().getRadioOutputPath();
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in getRadioOutputPath", e);
            return 0;
        }
    }

    public boolean semIsFmRadioActive() {
        return AudioManagerHelper.isFMPlayerActive();
    }

    public static int semGetActiveStreamType() {
        try {
            return getService().secGetActiveStreamType(Integer.MIN_VALUE);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in semGetActiveStreamType", e);
            return Integer.MIN_VALUE;
        }
    }

    public boolean semIsVoiceCallActive() {
        return "true".equalsIgnoreCase(getAudioServiceConfig(new AudioParameter.Builder().setParam(AudioParameter.SEC_LOCAL_STREAM_ACTIVE, 0).build().toString()));
    }

    public int semGetAvailableDeviceMaskForQuickSoundPath() {
        return getAvailableDeviceMaskForQuickSoundPath();
    }

    public int semSetDeviceForced(int i, String str) {
        return setDeviceToForceByUser(i, str, false);
    }

    public int setDeviceToForceByUser(int i, String str, boolean z) {
        int deviceToForceByUser;
        synchronized (sSetDeviceForceLock) {
            Log.d(TAG, "setDeviceToForceByUser Device 0x" + i);
            try {
                deviceToForceByUser = getService().setDeviceToForceByUser(i, str, z);
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in setDeviceToForceByUser", e);
                return -1;
            }
        }
        return deviceToForceByUser;
    }

    public int getAvailableDeviceMaskForQuickSoundPath() {
        String audioServiceConfig = getAudioServiceConfig(AudioParameter.SEC_GLOBAL_SOUND_PATH_AVAILABLE_DEVICES);
        if (audioServiceConfig != null) {
            return Integer.parseInt(audioServiceConfig, 16);
        }
        return SemAudioSystem.makeDeviceBit(AudioSystem.DEVICE_OUT_ALL_SET);
    }

    public boolean semIsSplitSoundOn() {
        return "true".equals(getAudioServiceConfig(AudioParameter.SEC_LOCAL_SMART_VEIW_SPLIT_SOUND_ENABLE));
    }

    public boolean semIsUhqAvailable() {
        return Rune.SEC_AUDIO_UHQ;
    }

    public static boolean semIsUhqSupported() {
        return Rune.SEC_AUDIO_UHQ;
    }

    public void semSetFineVolume(int i, int i2, int i3, int i4) {
        setFineVolume(i, i2, i3, AudioDeviceInfo.convertDeviceTypeToInternalDevice(i4));
    }

    public int semGetFineVolume(int i, int i2) {
        return getFineVolume(i, AudioDeviceInfo.convertDeviceTypeToInternalDevice(i2));
    }

    public void semSetFineVolume(int i, int i2, int i3) {
        setFineVolume(i, i2, i3, 0);
    }

    public int semGetFineVolume(int i) {
        return getFineVolume(i, 0);
    }

    public int getFineVolume(int i, int i2) {
        if (i != 3) {
            throw new IllegalArgumentException("Bad stream type " + i);
        }
        try {
            return getService().getFineVolume(i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in getFineVolume", e);
            return -1;
        }
    }

    public void setFineVolume(int i, int i2, int i3, int i4) {
        if (i != 3) {
            throw new IllegalArgumentException("Bad stream type " + i);
        }
        if (AudioManagerHelper.needToLogCaller(getContext().getOpPackageName())) {
            AudioManagerHelper.logCaller("streamType=%d, index=%d", Integer.valueOf(i), Integer.valueOf(i2));
        }
        try {
            getService().setFineVolume(i, i2, i3 | 1048576, i4, getContext().getOpPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setFineVolume", e);
        }
    }

    public void updateBluetoothDevice(BluetoothDevice bluetoothDevice, int i, int i2) {
        Log.i(TAG, "updateBluetoothDevice btOffload = " + i2);
        if (i2 >= 0) {
            try {
                getService().setBtOffloadEnable(i2);
            } catch (RemoteException e) {
                Log.e(TAG, "Dead object in setBtOffloadEnable", e);
            }
        }
    }

    public void setMaxLimitedSpkVolume(int i, boolean z) {
        Log.d(TAG, "setMaxLimitedSpkVolume, uid=" + i + ", state=" + z);
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AudioParameter.SEC_LOCAL_VOLUME_PREVENT_OVERHEAT_KEY).setParam("uid", i).setParam("state", z).build().toString());
    }

    public void semSetVolumeLimitEnabled(int i, boolean z) {
        setMaxLimitedSpkVolume(i, z);
    }

    public int getLimitedVolume() {
        Integer num = 14;
        num.getClass();
        return 14;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001f A[PHI: r0
      0x001f: PHI (r0v9 int) = (r0v4 int), (r0v5 int), (r0v6 int) binds: [B:12:0x001d, B:15:0x0025, B:18:0x002c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int semGetCurrentDeviceType() {
        int devicesForStream;
        try {
            devicesForStream = getService().getDeviceMaskForStream(3);
        } catch (RemoteException unused) {
            devicesForStream = AudioSystem.getDevicesForStream(3);
        }
        if (((devicesForStream - 1) & devicesForStream) != 0) {
            if ((devicesForStream & 2) != 0) {
                devicesForStream = 2;
            } else {
                int i = 262144;
                if ((devicesForStream & 262144) != 0) {
                    devicesForStream = i;
                } else {
                    i = 524288;
                    if ((devicesForStream & 524288) == 0) {
                        i = 2097152;
                        if ((devicesForStream & 2097152) == 0) {
                            devicesForStream &= SemAudioSystem.makeDeviceBit(AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
                        }
                    }
                }
            }
        }
        return AudioDeviceInfo.convertInternalDeviceToDeviceType(devicesForStream);
    }

    public boolean isUsingAudio(String str) {
        return isUsingAudio(str, -1);
    }

    public boolean isUsingAudio(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            Log.e(TAG, "Invalid package : " + str);
            return false;
        }
        try {
            return getService().isUsingAudio(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in isUsingAudio", e);
            return false;
        }
    }

    public boolean semIsSafeMediaVolumeDeviceOn() {
        String audioServiceConfig = getAudioServiceConfig(AudioParameter.SEC_LOCAL_SAFE_MEDIA_VOLUME_ENABLE);
        return audioServiceConfig != null && "true".equals(audioServiceConfig);
    }

    public static int semGetDeviceOut(int i) {
        return AudioDeviceInfo.convertDeviceTypeToInternalDevice(i);
    }

    public float semGetSituationVolume(int i, int i2) {
        if (i < 1 || i > 16 || i2 < 0 || i2 > 2) {
            return 1.0f;
        }
        try {
            return Float.parseFloat(getParameters("g_volume_situation_key;type=" + i + ";device=" + i2));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1.0f;
        }
    }

    public void setAppDevice(int i, int i2) {
        setAppDevice(i, i2, true);
    }

    public void setAppDevice(int i, int i2, boolean z) {
        try {
            getService().setAppDevice(i, i2, z);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setAppDevice", e);
        }
    }

    public int getAppDevice(int i) {
        try {
            return getService().getAppDevice(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in getAppDevice", e);
            return 0;
        }
    }

    public void setAppVolume(int i, int i2) {
        if (i2 > 100 || i2 < 0) {
            throw new IllegalArgumentException("Invalid ratio " + i2);
        }
        try {
            getService().setAppVolume(i, i2, getContext().getOpPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setAppVolume", e);
        }
    }

    public int getAppVolume(int i) {
        try {
            return getService().getAppVolume(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in getAppVolume", e);
            return 100;
        }
    }

    public void setAppMute(int i, boolean z) {
        try {
            getService().setAppMute(i, z, getContext().getOpPackageName());
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setAppMute", e);
        }
    }

    public boolean isAppMute(int i) {
        try {
            return getService().isAppMute(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in isAppMute", e);
            return false;
        }
    }

    public void setMultiSoundOn(boolean z) {
        setMultiSoundOn(z, true);
    }

    public void setMultiSoundOn(boolean z, boolean z2) {
        try {
            getService().setMultiSoundOn(z, z2);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setMultiSoundOn", e);
        }
    }

    public boolean isMultiSoundOn() {
        try {
            return getService().isMultiSoundOn();
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in isMultiSoundOn", e);
            return false;
        }
    }

    public void setStreamVolume(int i, int i2, int i3, int i4) {
        if (AudioManagerHelper.needToLogCaller(getContext().getOpPackageName())) {
            AudioManagerHelper.logCaller("streamType=%d, index=%d", Integer.valueOf(i), Integer.valueOf(i2));
        }
        try {
            getService().setStreamVolumeForDeviceWithAttribution(i, i2, i3, getContext().getOpPackageName(), getContext().getAttributionTag(), i4);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetStreamVolume(int i, int i2, int i3, int i4) {
        if (AudioManagerHelper.needToLogCaller(getContext().getOpPackageName())) {
            AudioManagerHelper.logCaller("streamType=%d, index=%d", Integer.valueOf(i), Integer.valueOf(i2));
        }
        int iConvertDeviceTypeToInternalDevice = AudioDeviceInfo.convertDeviceTypeToInternalDevice(i4);
        try {
            getService().setStreamVolumeForDeviceWithAttribution(i, i2, i3, getContext().getOpPackageName(), getContext().getAttributionTag(), iConvertDeviceTypeToInternalDevice);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in semSetStreamVolume", e);
        }
    }

    public int semGetStreamVolume(int i, int i2) {
        return getStreamVolume(i, AudioDeviceInfo.convertDeviceTypeToInternalDevice(i2));
    }

    public int getStreamVolume(int i, int i2) {
        try {
            return getService().getStreamVolumeForDevice(i, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in getStreamVolume", e);
            return -1;
        }
    }

    public int semGetPinDevice() {
        try {
            return getService().getPinDevice();
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling semGetPinDevice", e);
            return 0;
        }
    }

    public String getPinAppName(int i) {
        try {
            return getService().getPinAppInfo(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling getPinAppName", e);
            return "";
        }
    }

    public String getPinDeviceName(int i) {
        try {
            return getService().getAudioServiceConfig("l_multi_sound_key;pin_device_name=" + i);
        } catch (RemoteException e) {
            Log.d(TAG, "Dead object in getPinDeviceName", e);
            return "";
        }
    }

    public boolean isSafeMediaVolumeDeviceOn(int i) {
        StringBuilder sb = new StringBuilder("l_safe_media_volume_enable=");
        sb.append(i);
        return "true".equals(getAudioServiceConfig(sb.toString()));
    }

    public boolean isSafeMediaVolumeStateActive() {
        try {
            return getService().isSafeMediaVolumeStateActive();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semIsRecordActive(int i) {
        return "true".equalsIgnoreCase(getAudioServiceConfig(new AudioParameter.Builder().setParam(AudioParameter.SEC_LOCAL_RECORD_ACTIVE_ENABLE, i).build().toString()));
    }

    public void setMuteInterval(int i) {
        try {
            getService().setMuteInterval(i, getContext().getOpPackageName());
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling setMuteInterval", e);
        }
    }

    public int getMuteInterval() {
        try {
            return getService().getMuteInterval();
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling getMuteInterval", e);
            return 0;
        }
    }

    public int getRemainingMuteIntervalMs() {
        try {
            return getService().getRemainingMuteIntervalMs();
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling getRemainingMuteIntervalMs", e);
            return 0;
        }
    }

    public int getPrevRingerMode() {
        try {
            return getService().getPrevRingerMode();
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling getPrevRingerMode", e);
            return -1;
        }
    }

    public static int semGetEarProtectLimit() {
        try {
            return getService().getEarProtectLimit();
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in semGetEarProtectLimit", e);
            return Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3 ? 8 : 10;
        }
    }

    public void semDismissVolumePanel() {
        try {
            getService().dismissVolumePanel();
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in dismissVolumePanel", e);
        }
    }

    public void updateBluetoothDualA2dpAudio(boolean z) {
        Log.e(TAG, "updateBluetoothDualA2dpAudio() " + z);
        setAudioServiceConfig("dualA2dpAudioEnable=".concat(z ? "true" : " false"));
    }

    public static void setAudioServiceConfig(String str) {
        try {
            getService().setAudioServiceConfig(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in setAudioServiceConfig", e);
        }
    }

    public static String getAudioServiceConfig(String str) {
        try {
            return getService().getAudioServiceConfig(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in getAudioServiceConfig", e);
            return null;
        }
    }

    public void enableVolumeLimiter(boolean z) {
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AudioParameter.SEC_LOCAL_VOLUME_LIMIT_KEY, "1").setParam("enable", "" + z).setParam("package", getContext().getOpPackageName()).build().toString());
    }

    public void setVolumeLimiterValue(int i) {
        setAudioServiceConfig(new AudioParameter.Builder().setParam(AudioParameter.SEC_LOCAL_VOLUME_LIMIT_KEY, "1").setParam("level", "" + i).setParam("package", getContext().getOpPackageName()).build().toString());
    }

    public void semSetFineVolume(BluetoothDevice bluetoothDevice, int i, int i2, int i3) {
        if (bluetoothDevice == null) {
            throw new IllegalArgumentException();
        }
        try {
            getService().setA2dpDeviceVolume(bluetoothDevice, i, i2, i3 | 1048576, getContext().getOpPackageName());
        } catch (RemoteException e) {
            Log.w(TAG, "semSetFineVolume error", e);
        }
    }

    public int semGetFineVolume(BluetoothDevice bluetoothDevice, int i) {
        if (bluetoothDevice == null) {
            throw new IllegalArgumentException();
        }
        try {
            return getService().getA2dpDeviceVolume(bluetoothDevice, i);
        } catch (RemoteException e) {
            Log.w(TAG, "semGetFineVolume error", e);
            return -1;
        }
    }

    public float[] getFloatVolumeTable() {
        try {
            return getService().getFloatVolumeTable();
        } catch (RemoteException e) {
            Log.w(TAG, "getFloatVolumeTable error", e);
            return null;
        }
    }

    public void semSetRemoteMic(boolean z) {
        String str;
        IAudioService service = getService();
        if (z) {
            str = "true";
        } else {
            str = "false";
        }
        try {
            String strConcat = "l_remote_mic_enable=".concat(str);
            service.setRemoteMic(z);
            setAudioServiceConfig(strConcat);
        } catch (RemoteException e) {
            Log.w(TAG, "semSetRemoteMic error", e);
        }
    }

    public int semGetRingerModeInternal() {
        return getRingerModeInternal();
    }

    public boolean shouldShowRingtoneVolume() {
        try {
            return getService().shouldShowRingtoneVolume();
        } catch (RemoteException e) {
            Log.w(TAG, "shouldShowRingtoneVolume error", e);
            return false;
        }
    }

    public static boolean isCurrentHapticPlaybackSupported(boolean z) {
        return AudioSystem.isHapticPlaybackSupported();
    }

    public void setSafeMediaVolume() {
        setAudioServiceConfig("l_set_safe_media_volume=true");
    }

    public void semSetScreenCallEnabled(boolean z) {
        Log.i(TAG, "semSetScreenCallEnabled state = " + z);
        setAudioServiceConfig("l_screen_call=".concat(z ? "on" : "off"));
    }

    public boolean semIsScreenCallEnabled() {
        return "true".equalsIgnoreCase(getAudioServiceConfig(AudioParameter.SEC_LOCAL_SCREEN_CALL));
    }

    public boolean semIsScreenCallAvailable() {
        return Rune.SEC_AUDIO_SCREEN_CALL;
    }

    public int getModeInternal() {
        try {
            return getService().getModeInternal();
        } catch (RemoteException e) {
            Log.e(TAG, "Error get mode internal", e);
            return 0;
        }
    }

    public void setMicInputControlMode(int i) {
        try {
            getService().setMicInputControlMode(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Error set MicMode", e);
        }
    }

    public int getMicModeType() {
        try {
            return getService().getMicModeType();
        } catch (RemoteException e) {
            Log.e(TAG, "Error get MicMode", e);
            return 0;
        }
    }

    public void semSetCallTranslationEnabled(boolean z, int i, int i2) {
        Log.i(TAG, "txMuteMode = " + i + " rxMuteMode = " + i2 + " callTranslationMode = " + z);
        StringBuilder sb = new StringBuilder("l_voice_tx_control_mode=");
        sb.append(i);
        sb.append(";l_voice_rx_control_mode=");
        sb.append(i2);
        sb.append(";l_call_translation_mode=");
        sb.append(z ? "on" : "off");
        setAudioServiceConfig(sb.toString());
    }

    public boolean semIsCallTranslationEnabled() {
        return "true".equalsIgnoreCase(getAudioServiceConfig(AudioParameter.SEC_LOCAL_CALL_TRANSLATION_MODE));
    }

    public int semGetVoiceTxControlMode() {
        String audioServiceConfig = getAudioServiceConfig(AudioParameter.SEC_LOCAL_VOICE_TX_CONTROL_MODE);
        if (audioServiceConfig.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(audioServiceConfig);
    }

    public int semGetVoiceRxControlMode() {
        String audioServiceConfig = getAudioServiceConfig(AudioParameter.SEC_LOCAL_VOICE_RX_CONTROL_MODE);
        if (audioServiceConfig.isEmpty()) {
            return -1;
        }
        return Integer.parseInt(audioServiceConfig);
    }

    public String semGetAudioFocusedPackageName() {
        try {
            return getService().getCurrentAudioFocusPackageName();
        } catch (RemoteException e) {
            Log.e(TAG, "Dead object in getCurrentAudioFocusPackageName", e);
            return null;
        }
    }
}
