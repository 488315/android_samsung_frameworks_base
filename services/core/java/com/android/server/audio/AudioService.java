package com.android.server.audio;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.IUidObserver;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.hardware.SensorPrivacyManager;
import android.hardware.SensorPrivacyManagerInternal;
import android.hardware.display.DisplayManager;
import android.hardware.hdmi.HdmiAudioSystemClient;
import android.hardware.hdmi.HdmiClient;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiPlaybackClient;
import android.hardware.hdmi.HdmiTvClient;
import android.hardware.input.InputManager;
import android.hidl.manager.V1_0.IServiceManager;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioDeviceVolumeManager;
import android.media.AudioFocusInfo;
import android.media.AudioFormat;
import android.media.AudioHalVersionInfo;
import android.media.AudioManager;
import android.media.AudioManagerInternal;
import android.media.AudioMixerAttributes;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.media.AudioRoutesInfo;
import android.media.AudioSystem;
import android.media.BluetoothProfileConnectionInfo;
import android.media.IAudioDeviceVolumeDispatcher;
import android.media.IAudioFocusDispatcher;
import android.media.IAudioModeDispatcher;
import android.media.IAudioRoutesObserver;
import android.media.IAudioServerStateDispatcher;
import android.media.IAudioService;
import android.media.ICapturePresetDevicesRoleDispatcher;
import android.media.ICommunicationDeviceDispatcher;
import android.media.IDeviceVolumeBehaviorDispatcher;
import android.media.IDevicesForAttributesCallback;
import android.media.IMuteAwaitConnectionCallback;
import android.media.IPlaybackConfigDispatcher;
import android.media.IPreferredMixerAttributesDispatcher;
import android.media.IRecordingConfigDispatcher;
import android.media.IRingtonePlayer;
import android.media.ISpatializerCallback;
import android.media.ISpatializerHeadToSoundStagePoseCallback;
import android.media.ISpatializerHeadTrackerAvailableCallback;
import android.media.ISpatializerHeadTrackingModeCallback;
import android.media.ISpatializerOutputCallback;
import android.media.IStrategyNonDefaultDevicesDispatcher;
import android.media.IStrategyPreferredDevicesDispatcher;
import android.media.IStreamAliasingDispatcher;
import android.media.IVolumeController;
import android.media.MediaMetrics;
import android.media.PlayerBase;
import android.media.VolumeInfo;
import android.media.VolumePolicy;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioPolicyConfig;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.audiopolicy.AudioVolumeGroup;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.SafeCloseable;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionCallback;
import android.media.projection.IMediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.net.INetd;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HwBinder;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.ContextThemeWrapper;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.EventLogTags;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.UiModeManagerService$15$$ExternalSyntheticLambda1;
import com.android.server.audio.AudioDeviceBroker;
import com.android.server.audio.AudioService;
import com.android.server.audio.AudioSystemAdapter;
import com.android.server.audio.RecordingActivityMonitor;
import com.android.server.audio.SoundEffectsHelper;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.media.MediaSessionService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.utils.EventLogger;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.knox.custom.CustomDeviceManagerProxy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.media.AudioFxHelper;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.AudioExecutor;
import com.samsung.android.server.audio.AudioGameManager;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.DesktopModeHelper;
import com.samsung.android.server.audio.DeviceAliasManager;
import com.samsung.android.server.audio.DualA2dpVolumeManager;
import com.samsung.android.server.audio.FactoryUtils;
import com.samsung.android.server.audio.FrequentWorker;
import com.samsung.android.server.audio.GoodCatchManager;
import com.samsung.android.server.audio.MicModeManager;
import com.samsung.android.server.audio.MultiSoundManager;
import com.samsung.android.server.audio.PackageListHelper;
import com.samsung.android.server.audio.RecordingPopupHelper;
import com.samsung.android.server.audio.SamsungRingerModeDelegate;
import com.samsung.android.server.audio.ScreenSharingHelper;
import com.samsung.android.server.audio.SemAudioServiceInternal;
import com.samsung.android.server.audio.SoundAppPolicyManager;
import com.samsung.android.server.audio.SoundCraftManager;
import com.samsung.android.server.audio.utils.AudioStreamUtils;
import com.samsung.android.server.audio.utils.AudioUtils;
import com.samsung.android.server.audio.utils.BtUtils;
import com.samsung.android.server.audio.utils.CommandUtils;
import com.samsung.android.server.audio.utils.KaraokeUtils;
import com.samsung.android.server.audio.utils.PlatformTypeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class AudioService extends IAudioService.Stub implements AccessibilityManager.TouchExplorationStateChangeListener, AccessibilityManager.AccessibilityServicesStateChangeListener, AudioSystemAdapter.OnRoutingUpdatedListener, AudioSystemAdapter.OnVolRangeInitRequestListener {
    public static final int BECOMING_NOISY_DELAY_MS = 500;
    public static final Set DEVICE_MEDIA_UNMUTED_ON_PLUG_SET;
    public static final String[] EMPTY_PACKAGE;
    public static final String[] RINGER_MODE_NAMES;
    public static final String[] SYSTEM_PACKAGE;
    public static int[] VOLUME_LIMIT_INDEX_EFFECT_ON;
    public static final Set mAppCastingDevice;
    public static AudioDeviceBroker.BtDeviceChangedData mBtDeviceChangedData;
    public static int[] mStreamVolumeAlias;
    public static final EventLogger sAppVolumeLogger;
    public static VolumeInfo sDefaultVolumeInfo;
    public static final EventLogger sDeviceLogger;
    public static final EventLogger sFactoryTestLogger;
    public static final EventLogger sForceUseLogger;
    public static boolean sIndependentA11yVolume;
    public static final EventLogger sLifecycleLogger;
    public static final EventLogger sMasterMuteLogger;
    public static final EventLogger sMicrophoneLogger;
    public static final EventLogger sMuteLogger;
    public static volatile int sRingerAndZenModeMutedStreams;
    public static final EventLogger sRingerModeLogger;
    public static final EventLogger sRingtoneChangeLogger;
    public static final EventLogger sScoPreventionLogger;
    public static final EventLogger sScpmLogger;
    public static final EventLogger sSpatialLogger;
    public static int sStreamOverrideDelayMs;
    public static final EventLogger sUsingAudioLogger;
    public static final SparseArray sVolumeGroupStates;
    public static final EventLogger sVolumeLogger;
    public final int[] AVC_AFFECTED_STREAMS;
    public final String CAR_CONNECTION_AUTHORITY;
    public final Uri PROJECTION_HOST_URI;
    public final int[] STREAM_VOLUME_ALIAS_DEFAULT;
    public final int[] STREAM_VOLUME_ALIAS_NONE;
    public final int[] STREAM_VOLUME_ALIAS_TELEVISION;
    public final int[] STREAM_VOLUME_ALIAS_VOICE;
    public Set mAbsVolumeMultiModeCaseDevices;
    public Map mAbsoluteVolumeDeviceInfoMap;
    public int[] mAccessibilityServiceUids;
    public final Object mAccessibilityServiceUidsLock;
    public int[] mActiveAssistantServiceUids;
    public final ActivityManagerInternal mActivityManagerInternal;
    public boolean mAdjustMediaVolumeOnly;
    public AlarmManager mAlarmManager;
    public String mAppMode;
    public final AppOpsManager mAppOps;
    public SparseIntArray mAppVolumeFromSoundAssistant;
    public final ArraySet mAssistantUids;
    public PowerManager.WakeLock mAudioEventWakeLock;
    public AudioGameManager mAudioGameManager;
    public AudioHandler mAudioHandler;
    public final HashMap mAudioPolicies;
    public final AudioPolicyFacade mAudioPolicy;
    public int mAudioPolicyCounter;
    public final HashMap mAudioServerStateListeners;
    public final AudioSystemAdapter mAudioSystem;
    public final AudioSystem.ErrorCallback mAudioSystemCallback;
    public AudioSystemThread mAudioSystemThread;
    public volatile boolean mAvrcpAbsVolSupported;
    public String mBtScoDeviceInfo;
    public boolean mBtScoOnByApp;
    public String mCallForwardingEnable;
    public String mCallMemoState;
    public boolean mCameraSoundForced;
    public boolean mConnectedAndroidAuto;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public DesktopModeHelper mDesktopModeHelper;
    public final DeviceAliasManager mDeviceAliasManager;
    public final AudioDeviceBroker mDeviceBroker;
    public final RemoteCallbackList mDeviceVolumeBehaviorDispatchers;
    public DisplayManager mDisplayManager;
    public boolean mDockAudioMediaEnabled;
    public int mDockState;
    public String mDvDhaparam;
    public final AudioSystem.DynamicPolicyCallback mDynPolicyCallback;
    public final EventLogger mDynPolicyLogger;
    public String mEnabledSurroundFormats;
    public int mEncodedSurroundMode;
    public final ArrayList mEventReceivers;
    public final AudioServiceExt mExt;
    public IAudioPolicyCallback mExtVolumeController;
    public final Object mExtVolumeControllerLock;
    public SetModeDeathHandler mExternalVoipModeOwner;
    public FrequentWorker mFMRadioRecordingChecker;
    public Set mFixedVolumeDevices;
    public ForceControlStreamClient mForceControlStreamClient;
    public final Object mForceControlStreamLock;
    public int mForceSpeaker;
    public int mForcedUseForFMRadio;
    public int mForcedUseForMedia;
    public int mForegroundUid;
    public Set mFullVolumeDevices;
    public GoodCatchManager mGoodCatchManager;
    public String mHAC;
    public final boolean mHasSpatializerEffect;
    public final boolean mHasVibrator;
    public HdmiAudioSystemClient mHdmiAudioSystemClient;
    public boolean mHdmiCecVolumeControlEnabled;
    public final Object mHdmiClientLock;
    public MyHdmiControlStatusChangeListenerCallback mHdmiControlStatusChangeListenerCallback;
    public HdmiControlManager mHdmiManager;
    public HdmiPlaybackClient mHdmiPlaybackClient;
    public boolean mHdmiSystemAudioSupported;
    public HdmiTvClient mHdmiTvClient;
    public int mHeadsetOnlyStream;
    public boolean mHomeSoundEffectEnabled;
    public int mInputMethodServiceUid;
    public final Object mInputMethodServiceUidLock;
    public boolean mIsBluetoothCastState;
    public boolean mIsCallScreeningModeSupported;
    public boolean mIsLeBroadCasting;
    public final boolean mIsSingleVolume;
    public boolean mIsTalkBackEnabled;
    public boolean mIsVibrate;
    public boolean mIsVolumeEffectOn;
    public KeyguardManager mKeyguardManager;
    public int mLRSwitching;
    public String mLastLoopbackOn;
    public int mLastVolumeChangedIntentDevice;
    public boolean mLiveTranslateDuringCall;
    public String mLoopbackState;
    public long mLoweredFromNormalToVibrateTime;
    public final MediaFocusControl mMediaFocusControl;
    public AtomicBoolean mMediaPlaybackActive;
    public MediaSessionService.MediaSessionServiceInternal mMediaSessionServiceInternal;
    public int mMediaVolumeStepIndex;
    public MicModeManager mMicModeManager;
    public boolean mMicMuteFromApi;
    public boolean mMicMuteFromPrivacyToggle;
    public boolean mMicMuteFromRestrictions;
    public boolean mMicMuteFromSwitch;
    public boolean mMicMuteFromSystemCached;
    public AtomicInteger mMode;
    public final RemoteCallbackList mModeDispatchers;
    public final EventLogger mModeLogger;
    public final boolean mMonitorRotation;
    public MultiSoundManager.MultiSoundInterface mMultiSoundInterface;
    public MultiSoundManager mMultiSoundManager;
    public int mMuteAffectedStreams;
    public final RemoteCallbackList mMuteAwaitConnectionDispatchers;
    public final Object mMuteAwaitConnectionLock;
    public int mMuteIntervalMs;
    public boolean mMuteMediaByVibrateOrSilentMode;
    public long mMuteTime;
    public int[] mMutedUsagesAwaitingConnection;
    public AudioDeviceAttributes mMutingExpectedDevice;
    public MyHdmiCecVolumeControlFeatureListener mMyHdmiCecVolumeControlFeatureListener;
    public boolean mNavigationRepeatSoundEffectsEnabled;
    public NotificationManager mNm;
    public boolean mNotifAliasRing;
    public PackageListHelper mPackageListHelper;
    public PackageManager mPackageManager;
    public String mPhoneType;
    public int mPlatformType;
    public final IPlaybackConfigDispatcher mPlaybackActivityMonitor;
    public final PlaybackActivityMonitor mPlaybackMonitor;
    public PowerManager mPowerManager;
    public final RemoteCallbackList mPrefMixerAttrDispatcher;
    public float[] mPrescaleAbsoluteVolume;
    public int mPrevRingerMode;
    public int mPrevVolDirection;
    public int mPrimaryAssistantUid;
    public IMediaProjectionManager mProjectionService;
    public final BroadcastReceiver mReceiver;
    public RecordingActivityMonitor.IRecordingEventChecker mRecordEventChecker;
    public final RecordingActivityMonitor mRecordMonitor;
    public boolean mRemoteMic;
    public RestorableParameters mRestorableParameters;
    public int mRingerMode;
    public int mRingerModeAffectedStreams;
    public AudioManagerInternal.RingerModeDelegate mRingerModeDelegate;
    public int mRingerModeExternal;
    public volatile IRingtonePlayer mRingtonePlayer;
    public final ArrayList mRmtSbmxFullVolDeathHandlers;
    public int mRmtSbmxFullVolRefCount;
    public RoleObserver mRoleObserver;
    public boolean mRttEnabled;
    public final BroadcastReceiver mSamsungReceiver;
    public SamsungSettingsObserver mSamsungSettingsObserver;
    public int mSavedSpeakerMediaIndex;
    public ScreenSharingHelper mScreenSharingHelper;
    public final SensorPrivacyManagerInternal mSensorPrivacyManagerInternal;
    public final ArrayList mSetModeDeathHandlers;
    public AudioSettingsHelper mSettingHelper;
    public final SettingsAdapter mSettings;
    public final Object mSettingsLock;
    public SettingsObserver mSettingsObserver;
    public SoundEffectsHelper mSfxHelper;
    public SoundAppPolicyManager mSoundAppPolicyManager;
    public SoundCraftManager mSoundCraftManager;
    public final SoundDoseHelper mSoundDoseHelper;
    public final SpatializerHelper mSpatializerHelper;
    public final RemoteCallbackList mStreamAliasingDispatchers;
    public VolumeStreamState[] mStreamStates;
    public SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionChangedListener;
    public int[] mSupportedSystemUsages;
    public final Object mSupportedSystemUsagesLock;
    public boolean mSupportsMicPrivacyToggle;
    public boolean mSurroundModeChanged;
    public boolean mSystemReady;
    public final SystemServerAdapter mSystemServer;
    public final IUidObserver mUidObserver;
    public final boolean mUseFixedVolume;
    public final boolean mUseVolumeGroupAliases;
    public final UserManagerInternal mUserManagerInternal;
    public final UserManagerInternal.UserRestrictionsListener mUserRestrictionsListener;
    public boolean mUserSelectedVolumeControlStream;
    public boolean mUserSwitchedReceived;
    public int mVibrateSetting;
    public Vibrator mVibrator;
    public AtomicBoolean mVoicePlaybackActive;
    public final IRecordingConfigDispatcher mVoiceRecordingActivityMonitor;
    public int mVolumeControlStream;
    public VolumeController mVolumeController;
    public int mVolumeControllerStream;
    public boolean mVolumeLimitOn;
    public int mVolumeLimitValue;
    public VolumeMap[] mVolumeMap;
    public VolumePolicy mVolumePolicy;
    public int[] mVolumeSteps;
    public int mZenModeAffectedStreams;
    public static final int[] NO_ACTIVE_ASSISTANT_SERVICE_UIDS = new int[0];
    public static int[] MAX_STREAM_VOLUME = {5, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
    public static int[] MIN_STREAM_VOLUME = {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
    public static final int[] STREAM_VOLUME_OPS = {34, 36, 35, 36, 37, 38, 39, 36, 36, 36, 64, 36};
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);

    enum BypassReason {
        NO_BYPASS,
        DISPLAY_VOLUME_CONTROL,
        ALL_SOUND_MUTE,
        TMS_CONNECT,
        CONSUME_ADJUST_SAME,
        MULTISOUND,
        MEDIA_VOLUME_STEP_ON,
        SKIP_WARNING_POPUP_VISIBLE,
        SKIP_VOLUME_PANEL_NOT_VISIBLE,
        VOLUME_LIMITER,
        LE_BROADCAST
    }

    public interface ISafeHearingVolumeController {
        void postDisplayCsdWarning(int i, int i2);

        void postDisplaySafeVolumeWarning(int i);
    }

    public static boolean isCallStream(int i) {
        return i == 0 || i == 6;
    }

    public static /* synthetic */ void lambda$applyDeviceAlias$23(int i) {
    }

    public final int getBluetoothContextualVolumeStream(int i) {
        return (i == 2 || i == 3 || i != 0) ? 0 : 3;
    }

    /* renamed from: ignorePlayerLogs, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0(PlayerBase playerBase) {
    }

    public final boolean isAlarm(int i) {
        return i == 4;
    }

    public final boolean isMedia(int i) {
        return i == 11 || i == 3;
    }

    public final boolean isMuteAdjust(int i) {
        return i == -100 || i == 100 || i == 101;
    }

    public final boolean isNotificationOrRinger(int i) {
        return i == 5 || i == 2;
    }

    public final boolean isRaiseOrLowerOrSame(int i) {
        return i == -1 || i == 0 || i == 1;
    }

    public final boolean isSystem(int i) {
        return i == 1;
    }

    public boolean isValidRingerMode(int i) {
        return i >= 0 && i <= 2;
    }

    public final int toEncodedSurroundOutputMode(int i, int i2) {
        if (i2 <= 31 && i > 3) {
            return -1;
        }
        if (i == 0) {
            return 0;
        }
        int i3 = 1;
        if (i != 1) {
            i3 = 2;
            if (i != 2) {
                return i != 3 ? -1 : 3;
            }
        }
        return i3;
    }

    public final int toEncodedSurroundSetting(int i) {
        if (i == 1) {
            return 1;
        }
        if (i != 2) {
            return i != 3 ? 0 : 3;
        }
        return 2;
    }

    public void setNotifAliasRingForTest(boolean z) {
        super.setNotifAliasRingForTest_enforcePermission();
        boolean z2 = this.mNotifAliasRing != z;
        this.mNotifAliasRing = z;
        if (z2) {
            updateStreamVolumeAlias(true, "AudioServiceTest");
        }
    }

    public boolean isPlatformVoice() {
        return this.mPlatformType == 1;
    }

    public boolean isPlatformTelevision() {
        return this.mPlatformType == 2;
    }

    public boolean isPlatformAutomotive() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }

    static {
        HashSet hashSet = new HashSet();
        DEVICE_MEDIA_UNMUTED_ON_PLUG_SET = hashSet;
        hashSet.add(4);
        hashSet.add(8);
        hashSet.add(Integer.valueOf(IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES));
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_BLE_SET);
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_USB_SET);
        hashSet.add(1024);
        sVolumeGroupStates = new SparseArray();
        sIndependentA11yVolume = false;
        sLifecycleLogger = new EventLogger(20, "audio services lifecycle");
        sMuteLogger = new EventLogger(30, "mute commands");
        sDeviceLogger = new EventLogger(50, "wired/A2DP/hearing aid device connection");
        sForceUseLogger = new EventLogger(20, "force use (logged before setForceUse() is executed)");
        sVolumeLogger = new EventLogger(100, "volume changes (logged when command received by AudioService)");
        sSpatialLogger = new EventLogger(30, "spatial audio");
        RINGER_MODE_NAMES = new String[]{"SILENT", "VIBRATE", "NORMAL"};
        mBtDeviceChangedData = null;
        mAppCastingDevice = new HashSet(Arrays.asList(-1000, -1001, -1002));
        sMicrophoneLogger = new EventLogger(30, "setMicrophoneMute use (logged after setMicrophoneMute() is executed)");
        sMasterMuteLogger = new EventLogger(30, "setMasterMute (logged when command received by AudioService)");
        sAppVolumeLogger = new EventLogger(40, "App Volume");
        sUsingAudioLogger = new EventLogger(30, "Using Audio");
        sRingtoneChangeLogger = new EventLogger(30, "Ringtone change history");
        sRingerModeLogger = new EventLogger(30, "Ringer mode change history");
        sScpmLogger = new EventLogger(30, "SCPM history");
        sFactoryTestLogger = new EventLogger(30, "Factory test parameters history");
        sScoPreventionLogger = new EventLogger(30, "SCO prevention history");
        SYSTEM_PACKAGE = new String[]{"android"};
        EMPTY_PACKAGE = new String[]{""};
        VOLUME_LIMIT_INDEX_EFFECT_ON = new int[]{6, 7, 8, 9, 11, 13, 13};
    }

    public int getVssVolumeForDevice(int i, int i2) {
        return this.mStreamStates[i].getIndex(i2);
    }

    public VolumeStreamState getVssVolumeForStream(int i) {
        return this.mStreamStates[i];
    }

    public int getMaxVssVolumeForStream(int i) {
        return this.mStreamStates[i].getMaxIndex();
    }

    public final class AbsoluteVolumeDeviceInfo {
        public final IAudioDeviceVolumeDispatcher mCallback;
        public final AudioDeviceAttributes mDevice;
        public int mDeviceVolumeBehavior;
        public final boolean mHandlesVolumeAdjustment;
        public final List mVolumeInfos;

        public static /* synthetic */ boolean lambda$getMatchingVolumeInfoForStream$0(int i, int i2) {
            return i2 == i;
        }

        public AbsoluteVolumeDeviceInfo(AudioDeviceAttributes audioDeviceAttributes, List list, IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, boolean z, int i) {
            this.mDevice = audioDeviceAttributes;
            this.mVolumeInfos = list;
            this.mCallback = iAudioDeviceVolumeDispatcher;
            this.mHandlesVolumeAdjustment = z;
            this.mDeviceVolumeBehavior = i;
        }

        public final VolumeInfo getMatchingVolumeInfoForStream(final int i) {
            for (VolumeInfo volumeInfo : this.mVolumeInfos) {
                boolean z = volumeInfo.hasStreamType() && volumeInfo.getStreamType() == i;
                boolean z2 = volumeInfo.hasVolumeGroup() && Arrays.stream(volumeInfo.getVolumeGroup().getLegacyStreamTypes()).anyMatch(new IntPredicate() { // from class: com.android.server.audio.AudioService$AbsoluteVolumeDeviceInfo$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i2) {
                        boolean lambda$getMatchingVolumeInfoForStream$0;
                        lambda$getMatchingVolumeInfoForStream$0 = AudioService.AbsoluteVolumeDeviceInfo.lambda$getMatchingVolumeInfoForStream$0(i, i2);
                        return lambda$getMatchingVolumeInfoForStream$0;
                    }
                });
                if (z || z2) {
                    return volumeInfo;
                }
            }
            return null;
        }
    }

    public class RestorableParameters {
        public Map mMap;

        public RestorableParameters() {
            this.mMap = new LinkedHashMap() { // from class: com.android.server.audio.AudioService.RestorableParameters.1
                @Override // java.util.LinkedHashMap
                public boolean removeEldestEntry(Map.Entry entry) {
                    if (size() <= 1000) {
                        return false;
                    }
                    Log.w("AS.AudioService", "Parameter map exceeds 1000 removing " + entry.getKey());
                    return true;
                }
            };
        }

        public int setParameters(String str, final String str2) {
            int parameters;
            Objects.requireNonNull(str, "id must not be null");
            Objects.requireNonNull(str2, "parameter must not be null");
            synchronized (this.mMap) {
                parameters = AudioSystem.setParameters(str2);
                if (parameters == 0) {
                    queueRestoreWithRemovalIfTrue(str, new BooleanSupplier() { // from class: com.android.server.audio.AudioService$RestorableParameters$$ExternalSyntheticLambda0
                        @Override // java.util.function.BooleanSupplier
                        public final boolean getAsBoolean() {
                            boolean lambda$setParameters$0;
                            lambda$setParameters$0 = AudioService.RestorableParameters.lambda$setParameters$0(str2);
                            return lambda$setParameters$0;
                        }
                    });
                }
            }
            return parameters;
        }

        public static /* synthetic */ boolean lambda$setParameters$0(String str) {
            return AudioSystem.setParameters(str) != 0;
        }

        public void queueRestoreWithRemovalIfTrue(String str, BooleanSupplier booleanSupplier) {
            Objects.requireNonNull(str, "id must not be null");
            synchronized (this.mMap) {
                if (booleanSupplier != null) {
                    this.mMap.put(str, booleanSupplier);
                } else {
                    this.mMap.remove(str);
                }
            }
        }

        public void restoreAll() {
            synchronized (this.mMap) {
                this.mMap.values().removeIf(new Predicate() { // from class: com.android.server.audio.AudioService$RestorableParameters$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean asBoolean;
                        asBoolean = ((BooleanSupplier) obj).getAsBoolean();
                        return asBoolean;
                    }
                });
            }
        }
    }

    public static String makeAlsaAddressString(int i, int i2) {
        return "card=" + i + ";device=" + i2;
    }

    public final class Lifecycle extends SystemService {
        public AudioService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = new AudioService(context, AudioSystemAdapter.getDefaultAdapter(), SystemServerAdapter.getDefaultAdapter(context), SettingsAdapter.getDefaultAdapter(), new DefaultAudioPolicyFacade(), null);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            publishBinderService("audio", this.mService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mService.systemReady();
            } else if (i == 1000) {
                this.mService.onBootCompleted();
                this.mService.mExt.bootCompleted();
            }
        }
    }

    public AudioService(Context context, AudioSystemAdapter audioSystemAdapter, SystemServerAdapter systemServerAdapter, SettingsAdapter settingsAdapter, AudioPolicyFacade audioPolicyFacade, Looper looper) {
        this(context, audioSystemAdapter, systemServerAdapter, settingsAdapter, audioPolicyFacade, looper, (AppOpsManager) context.getSystemService(AppOpsManager.class), PermissionEnforcer.fromContext(context));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AudioService(Context context, AudioSystemAdapter audioSystemAdapter, SystemServerAdapter systemServerAdapter, SettingsAdapter settingsAdapter, AudioPolicyFacade audioPolicyFacade, Looper looper, AppOpsManager appOpsManager, PermissionEnforcer permissionEnforcer) {
        super(permissionEnforcer);
        this.mNotifAliasRing = false;
        this.mVolumeController = new VolumeController();
        this.mMode = new AtomicInteger(0);
        this.mSettingsLock = new Object();
        this.STREAM_VOLUME_ALIAS_VOICE = new int[]{0, 1, 2, 3, 4, 2, 6, 7, 2, 3, 3, 3};
        this.STREAM_VOLUME_ALIAS_TELEVISION = new int[]{3, 3, 3, 3, 3, 3, 6, 3, 3, 3, 3, 3};
        this.STREAM_VOLUME_ALIAS_NONE = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        this.STREAM_VOLUME_ALIAS_DEFAULT = new int[]{0, 1, 2, 3, 4, 2, 6, 7, 2, 3, 3, 3};
        this.mAvrcpAbsVolSupported = false;
        AudioSystem.ErrorCallback errorCallback = new AudioSystem.ErrorCallback() { // from class: com.android.server.audio.AudioService.1
            public void onError(int i) {
                if (i != 100) {
                    return;
                }
                if (AudioService.this.mRecordMonitor != null) {
                    AudioService.this.mRecordMonitor.onAudioServerDied();
                }
                if (AudioService.this.mPlaybackMonitor != null) {
                    AudioService.this.mPlaybackMonitor.onAudioServerDied();
                }
                AudioService.sendMsg(AudioService.this.mAudioHandler, 4, 1, 0, 0, null, 0);
                AudioService.sendMsg(AudioService.this.mAudioHandler, 23, 2, 0, 0, null, 0);
            }
        };
        this.mAudioSystemCallback = errorCallback;
        this.mRingerModeExternal = -1;
        this.mRingerModeAffectedStreams = 0;
        this.mZenModeAffectedStreams = 0;
        this.mReceiver = new AudioServiceBroadcastReceiver();
        AudioServiceUserRestrictionsListener audioServiceUserRestrictionsListener = new AudioServiceUserRestrictionsListener();
        this.mUserRestrictionsListener = audioServiceUserRestrictionsListener;
        this.mSetModeDeathHandlers = new ArrayList();
        this.mPrevVolDirection = 0;
        this.mVolumeControlStream = -1;
        this.mUserSelectedVolumeControlStream = false;
        this.mForceControlStreamLock = new Object();
        this.mForceControlStreamClient = null;
        this.mFixedVolumeDevices = new HashSet(Arrays.asList(Integer.valueOf(IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES), 2097152));
        this.mFullVolumeDevices = new HashSet(Arrays.asList(262144, 262145));
        this.mAbsoluteVolumeDeviceInfoMap = new ArrayMap();
        this.mAbsVolumeMultiModeCaseDevices = new HashSet(Arrays.asList(134217728));
        this.mDockAudioMediaEnabled = true;
        this.mRestorableParameters = new RestorableParameters();
        this.mDockState = 0;
        this.mPrescaleAbsoluteVolume = new float[]{0.6f, 0.8f, 0.9f};
        this.mVolumePolicy = VolumePolicy.DEFAULT;
        this.mAssistantUids = new ArraySet();
        this.mPrimaryAssistantUid = -1;
        this.mActiveAssistantServiceUids = NO_ACTIVE_ASSISTANT_SERVICE_UIDS;
        this.mAccessibilityServiceUidsLock = new Object();
        this.mInputMethodServiceUid = -1;
        this.mInputMethodServiceUidLock = new Object();
        this.mSupportedSystemUsagesLock = new Object();
        this.mSupportedSystemUsages = new int[]{17};
        this.mUidObserver = new UidObserver() { // from class: com.android.server.audio.AudioService.2
            public void onUidGone(int i, boolean z) {
                disableAudioForUid(false, i);
            }

            public void onUidCachedChanged(int i, boolean z) {
                disableAudioForUid(z, i);
            }

            public final void disableAudioForUid(boolean z, int i) {
                AudioService audioService = AudioService.this;
                audioService.queueMsgUnderWakeLock(audioService.mAudioHandler, 100, z ? 1 : 0, i, null, 0);
            }
        };
        this.mRttEnabled = false;
        this.mSubscriptionChangedListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.audio.AudioService.3
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public void onSubscriptionsChanged() {
                Log.i("AS.AudioService", "onSubscriptionsChanged()");
                AudioService.sendMsg(AudioService.this.mAudioHandler, 54, 0, 0, 0, null, 0);
            }
        };
        this.mVoicePlaybackActive = new AtomicBoolean(false);
        this.mMediaPlaybackActive = new AtomicBoolean(false);
        IPlaybackConfigDispatcher iPlaybackConfigDispatcher = new IPlaybackConfigDispatcher.Stub() { // from class: com.android.server.audio.AudioService.4
            public void dispatchPlaybackConfigChange(List list, boolean z) {
                AudioService.sendMsg(AudioService.this.mAudioHandler, 29, 0, 0, 0, list, 0);
            }
        };
        this.mPlaybackActivityMonitor = iPlaybackConfigDispatcher;
        IRecordingConfigDispatcher iRecordingConfigDispatcher = new IRecordingConfigDispatcher.Stub() { // from class: com.android.server.audio.AudioService.5
            public void dispatchRecordingConfigChange(List list) {
                AudioService.sendMsg(AudioService.this.mAudioHandler, 37, 0, 0, 0, list, 0);
            }
        };
        this.mVoiceRecordingActivityMonitor = iRecordingConfigDispatcher;
        this.mRmtSbmxFullVolRefCount = 0;
        this.mRmtSbmxFullVolDeathHandlers = new ArrayList();
        this.mStreamAliasingDispatchers = new RemoteCallbackList();
        this.mIsCallScreeningModeSupported = false;
        this.mModeDispatchers = new RemoteCallbackList();
        this.mMuteAwaitConnectionLock = new Object();
        this.mMuteAwaitConnectionDispatchers = new RemoteCallbackList();
        this.mDeviceVolumeBehaviorDispatchers = new RemoteCallbackList();
        this.mHdmiClientLock = new Object();
        this.mHdmiSystemAudioSupported = false;
        this.mHdmiControlStatusChangeListenerCallback = new MyHdmiControlStatusChangeListenerCallback();
        this.mMyHdmiCecVolumeControlFeatureListener = new MyHdmiCecVolumeControlFeatureListener();
        this.mModeLogger = new EventLogger(20, "phone state (logged after successful call to AudioSystem.setPhoneState(int, int))");
        this.mDynPolicyLogger = new EventLogger(10, "dynamic policy events (logged when command received by AudioService)");
        this.mPrefMixerAttrDispatcher = new RemoteCallbackList();
        this.mExtVolumeControllerLock = new Object();
        this.mDynPolicyCallback = new AudioSystem.DynamicPolicyCallback() { // from class: com.android.server.audio.AudioService.7
            public void onDynamicPolicyMixStateUpdate(String str, int i) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                AudioService.sendMsg(AudioService.this.mAudioHandler, 19, 2, i, 0, str, 0);
            }
        };
        this.mAudioServerStateListeners = new HashMap();
        this.mAudioPolicies = new HashMap();
        this.mAudioPolicyCounter = 0;
        this.mSamsungReceiver = new SamsungBroadcastReceiver();
        this.mIsVibrate = false;
        this.mIsTalkBackEnabled = false;
        this.mIsBluetoothCastState = false;
        this.AVC_AFFECTED_STREAMS = new int[]{1, 10, 11};
        this.mAppMode = "NORMAL";
        this.mForceSpeaker = 0;
        this.mSavedSpeakerMediaIndex = -1;
        this.mMuteMediaByVibrateOrSilentMode = false;
        this.mPrevRingerMode = 2;
        this.mRemoteMic = false;
        this.mAlarmManager = null;
        this.mMuteIntervalMs = 0;
        this.mMuteTime = 0L;
        this.mVolumeLimitOn = false;
        this.mVolumeLimitValue = 9;
        this.mMediaSessionServiceInternal = null;
        this.mIsLeBroadCasting = false;
        this.mGoodCatchManager = null;
        this.mMicModeManager = null;
        this.mDeviceAliasManager = new DeviceAliasManager();
        this.mExternalVoipModeOwner = null;
        this.mLiveTranslateDuringCall = true;
        this.mLoopbackState = "off";
        this.mLastLoopbackOn = "";
        this.mAdjustMediaVolumeOnly = true;
        this.mAppVolumeFromSoundAssistant = new SparseIntArray(5);
        this.mMediaVolumeStepIndex = 10;
        this.mHeadsetOnlyStream = 0;
        this.mLRSwitching = 0;
        this.mEventReceivers = new ArrayList(1);
        this.mVolumeSteps = null;
        this.mVolumeMap = null;
        this.mRecordEventChecker = new RecordingActivityMonitor.IRecordingEventChecker() { // from class: com.android.server.audio.AudioService.8
            @Override // com.android.server.audio.RecordingActivityMonitor.IRecordingEventChecker
            public void notifyRecordingEvent(String str, int i) {
                AudioService.this.sendBroadcastToSoundEventReceiver(128, i, str);
            }
        };
        this.mForegroundUid = -1;
        this.mVolumeControllerStream = -1;
        this.mMultiSoundInterface = new MultiSoundManager.MultiSoundInterface() { // from class: com.android.server.audio.AudioService.9
            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public void showNotification() {
                AudioService.this.mMultiSoundManager.showNotification();
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public void clearNotification() {
                AudioService.this.mMultiSoundManager.clearNotification();
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public void showHeadUpNotification(int i) {
                AudioService.this.mMultiSoundManager.showHeadUpNotification(i, AudioService.this.mDeviceBroker.getPriorityDevice(i));
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public void updateForegroundUid(int i) {
                AudioService.this.mForegroundUid = i;
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public void updateFocusRequester(int i) {
                AudioService.this.mMediaFocusControl.updateFocusRequester(i);
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public void updateFocusRequester(int i, boolean z) {
                AudioService.this.mMediaFocusControl.updateFocusRequester(i, z);
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public int getConnectedDevice() {
                return AudioService.this.mDeviceBroker.getConnectedDevice();
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public int getCurrentMediaDevice() {
                return AudioService.this.getObservedDevicesForMedia();
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public String[] getPackageName(int i) {
                return AudioService.this.getPackageName(i);
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public String getApplicationLabel(String str) {
                return AudioService.this.mPackageManager.getApplicationLabel(AudioService.this.mPackageManager.getApplicationInfo(str, 0)).toString();
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public boolean isInstalledApp(String str) {
                return AudioService.this.mContext.getPackageManager().getApplicationInfo(str, 0) != null;
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public void sendBecomingNoisyIntent(int i) {
                AudioService.this.sendBecomingNoisyIntent(i);
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public boolean checkAudioSettingsPermission(String str) {
                return AudioService.this.checkAudioSettingsPermission(str);
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public String getMultiSoundAppFromSetting() {
                return Settings.System.getString(AudioService.this.mContentResolver, "multisound_app");
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public void setAppCastingState(boolean z, int i) {
                AudioService.this.mMediaSessionServiceInternal.setAppCastingState(z, i);
            }

            @Override // com.samsung.android.server.audio.MultiSoundManager.MultiSoundInterface
            public boolean isLeBroadcasting() {
                return AudioService.this.mIsLeBroadCasting;
            }
        };
        this.mKeyguardManager = null;
        this.mFMRadioRecordingChecker = new FrequentWorker() { // from class: com.android.server.audio.AudioService.10
            {
                this.mPeriodMs = 500;
                this.mCachedValue = Boolean.FALSE;
            }

            @Override // com.samsung.android.server.audio.FrequentWorker
            public Boolean func() {
                return Boolean.valueOf("true".equals(SemAudioSystem.getPolicyParameters("l_fmradio_record_active")));
            }
        };
        this.mLastVolumeChangedIntentDevice = -1;
        this.CAR_CONNECTION_AUTHORITY = "androidx.car.app.connection";
        this.PROJECTION_HOST_URI = new Uri.Builder().scheme("content").authority("androidx.car.app.connection").build();
        this.mConnectedAndroidAuto = false;
        Trace.traceBegin(256L, "AudioService_InIt");
        sLifecycleLogger.enqueue(new EventLogger.StringEvent("AudioService()"));
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mAppOps = appOpsManager;
        this.mAudioSystem = audioSystemAdapter;
        this.mSystemServer = systemServerAdapter;
        this.mSettings = settingsAdapter;
        this.mAudioPolicy = audioPolicyFacade;
        int platformType = PlatformTypeUtils.getPlatformType(context);
        this.mPlatformType = platformType;
        Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION |= platformType != 1;
        if (platformType == 1) {
            Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION = false;
        }
        this.mIsSingleVolume = AudioSystem.isSingleVolume(context);
        UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        this.mUserManagerInternal = userManagerInternal;
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mSensorPrivacyManagerInternal = (SensorPrivacyManagerInternal) LocalServices.getService(SensorPrivacyManagerInternal.class);
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.mPowerManager = powerManager;
        this.mAudioEventWakeLock = powerManager.newWakeLock(1, "handleAudioEvent");
        this.mSfxHelper = new SoundEffectsHelper(context, new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AudioService.this.lambda$new$0((PlayerBase) obj);
            }
        });
        this.mSpatializerHelper = new SpatializerHelper(this, audioSystemAdapter, SystemProperties.getBoolean("ro.audio.spatializer_binaural_enabled_default", true), SystemProperties.getBoolean("ro.audio.spatializer_transaural_enabled_default", true), context.getResources().getBoolean(R.bool.config_safe_media_volume_enabled));
        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
        this.mVibrator = vibrator;
        this.mHasVibrator = vibrator == null ? false : vibrator.hasVibrator();
        this.mSupportsMicPrivacyToggle = ((SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class)).supportsSensorToggle(1);
        this.mUseVolumeGroupAliases = context.getResources().getBoolean(R.bool.config_enable_a11y_magnification_single_panning);
        this.mPackageManager = context.getPackageManager();
        initializeVolumeSteps();
        if (looper == null) {
            createAudioSystemThread();
        } else {
            this.mAudioHandler = new AudioHandler(looper);
        }
        this.mSoundDoseHelper = new SoundDoseHelper(this, context, this.mAudioHandler, settingsAdapter, this.mVolumeController);
        AudioSystem.setErrorCallback(errorCallback);
        updateAudioHalPids();
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            Log.i("AS.AudioService", "new MicModeManager");
            this.mMicModeManager = MicModeManager.getInstance(context);
        }
        AudioSettingsHelper audioSettingsHelper = AudioSettingsHelper.getInstance(context);
        this.mSettingHelper = audioSettingsHelper;
        this.mExt = new AudioServiceExt(context, this, audioSystemAdapter, audioSettingsHelper, this.mSfxHelper, this.mMicModeManager);
        this.mUseFixedVolume = context.getResources().getBoolean(R.bool.config_supportShortPressPowerWhenDefaultDisplayOn);
        this.mDeviceBroker = new AudioDeviceBroker(context, this, audioSystemAdapter);
        RecordingActivityMonitor recordingActivityMonitor = new RecordingActivityMonitor(context);
        this.mRecordMonitor = recordingActivityMonitor;
        recordingActivityMonitor.registerRecordingCallback(iRecordingConfigDispatcher, true);
        this.mFixedVolumeDevices.clear();
        this.mMultiSoundManager = new MultiSoundManager(context, this.mMultiSoundInterface, this.mAudioHandler, audioSystemAdapter);
        this.mSoundCraftManager = new SoundCraftManager(context);
        updateStreamVolumeAlias(false, "AS.AudioService");
        readPersistedSettings();
        readUserRestrictions();
        PlaybackActivityMonitor playbackActivityMonitor = new PlaybackActivityMonitor(context, MAX_STREAM_VOLUME[4], new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AudioService.this.lambda$new$1((AudioDeviceAttributes) obj);
            }
        }, this);
        this.mPlaybackMonitor = playbackActivityMonitor;
        playbackActivityMonitor.registerPlaybackCallback(iPlaybackConfigDispatcher, true);
        this.mMediaFocusControl = new MediaFocusControl(context, playbackActivityMonitor, this);
        readAndSetLowRamDevice();
        this.mIsCallScreeningModeSupported = AudioSystem.isCallScreeningModeSupported();
        if (systemServerAdapter.isPrivileged()) {
            LocalServices.addService(AudioManagerInternal.class, new AudioServiceInternal());
            userManagerInternal.addUserRestrictionsListener(audioServiceUserRestrictionsListener);
            recordingActivityMonitor.initMonitor();
        }
        this.mMonitorRotation = SystemProperties.getBoolean("ro.audio.monitorRotation", false);
        this.mHasSpatializerEffect = SystemProperties.getBoolean("ro.audio.spatializer_enabled", false);
        AudioSystemAdapter.setRoutingListener(this);
        AudioSystemAdapter.setVolRangeInitReqListener(this);
        queueMsgUnderWakeLock(this.mAudioHandler, 101, 0, 0, null, 0);
        queueMsgUnderWakeLock(this.mAudioHandler, 102, 0, 0, null, 0);
        setupCustomRoutine();
        Trace.endSection();
    }

    public final void initVolumeStreamStates() {
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        synchronized (VolumeStreamState.class) {
            for (int i = numStreamTypes - 1; i >= 0; i--) {
                VolumeStreamState volumeStreamState = this.mStreamStates[i];
                int volumeGroupForStreamType = getVolumeGroupForStreamType(i);
                if (volumeGroupForStreamType != -1) {
                    SparseArray sparseArray = sVolumeGroupStates;
                    if (sparseArray.indexOfKey(volumeGroupForStreamType) >= 0) {
                        volumeStreamState.setVolumeGroupState((VolumeGroupState) sparseArray.get(volumeGroupForStreamType));
                    }
                }
            }
        }
    }

    public final void onInitStreamsAndVolumes() {
        int i;
        synchronized (this.mSettingsLock) {
            boolean readCameraSoundForced = readCameraSoundForced();
            this.mCameraSoundForced = readCameraSoundForced;
            sendMsg(this.mAudioHandler, 8, 2, 4, readCameraSoundForced ? 11 : 0, new String("AudioService ctor"), 0);
        }
        createStreamStates();
        initVolumeGroupStates();
        this.mSoundDoseHelper.initSafeMediaVolumeIndex();
        initVolumeStreamStates();
        sRingerAndZenModeMutedStreams = 0;
        sMuteLogger.enqueue(new AudioServiceEvents$RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "onInitStreamsAndVolumes"));
        setRingerModeInt(getRingerModeInternal(), false);
        float[] fArr = {this.mContext.getResources().getFraction(R.fraction.config_maximumScreenDimRatio, 1, 1), this.mContext.getResources().getFraction(R.fraction.config_prescaleAbsoluteVolume_index1, 1, 1), this.mContext.getResources().getFraction(R.fraction.config_prescaleAbsoluteVolume_index2, 1, 1)};
        for (i = 0; i < 3; i++) {
            float f = fArr[i];
            if (DisplayPowerController2.RATE_FROM_DOZE_TO_ON <= f && f <= 1.0f) {
                this.mPrescaleAbsoluteVolume[i] = f;
            }
        }
        initExternalEventReceivers();
        checkVolumeRangeInitialization("AudioService()");
    }

    public final void initExternalEventReceivers() {
        this.mSettingsObserver = new SettingsObserver();
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED");
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_BACKGROUND");
        intentFilter.addAction("android.intent.action.USER_FOREGROUND");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        if (this.mMonitorRotation) {
            RotationHelper.init(this.mContext, this.mAudioHandler, new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda21
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AudioService.this.lambda$initExternalEventReceivers$2((Integer) obj);
                }
            }, new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AudioService.this.lambda$initExternalEventReceivers$3((Boolean) obj);
                }
            });
        }
        intentFilter.addAction("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION");
        intentFilter.addAction("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION");
        intentFilter.addAction("com.android.server.audio.action.CHECK_MUSIC_ACTIVE");
        intentFilter.setPriority(1000);
        this.mContext.registerReceiverAsUser(this.mReceiver, UserHandle.ALL, intentFilter, null, null, 2);
        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        if (subscriptionManager == null) {
            Log.e("AS.AudioService", "initExternalEventReceivers cannot create SubscriptionManager!");
        } else {
            subscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionChangedListener);
        }
        this.mSamsungSettingsObserver = new SamsungSettingsObserver();
        initCustomExternalEventReceivers();
    }

    public void systemReady() {
        sendMsg(this.mAudioHandler, 16, 2, 0, 0, null, 0);
    }

    public final void updateVibratorInfos() {
        VibratorManager vibratorManager = (VibratorManager) this.mContext.getSystemService(VibratorManager.class);
        if (vibratorManager == null) {
            Slog.e("AS.AudioService", "Vibrator manager is not found");
            return;
        }
        int[] vibratorIds = vibratorManager.getVibratorIds();
        if (vibratorIds.length == 0) {
            Slog.d("AS.AudioService", "No vibrator found");
            return;
        }
        ArrayList arrayList = new ArrayList(vibratorIds.length);
        for (int i : vibratorIds) {
            Vibrator vibrator = vibratorManager.getVibrator(i);
            if (vibrator != null) {
                arrayList.add(vibrator);
            } else {
                Slog.w("AS.AudioService", "Vibrator(" + i + ") is not found");
            }
        }
        if (arrayList.isEmpty()) {
            Slog.w("AS.AudioService", "Cannot find any available vibrator");
        } else {
            AudioSystem.setVibratorInfos(arrayList);
        }
    }

    public void onSystemReady() {
        Trace.traceBegin(256L, "onSystemReady");
        this.mSystemReady = true;
        scheduleLoadSoundEffects();
        this.mDeviceBroker.onSystemReady();
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.hdmi.cec")) {
            synchronized (this.mHdmiClientLock) {
                HdmiControlManager hdmiControlManager = (HdmiControlManager) this.mContext.getSystemService(HdmiControlManager.class);
                this.mHdmiManager = hdmiControlManager;
                if (hdmiControlManager != null) {
                    hdmiControlManager.addHdmiControlStatusChangeListener(this.mHdmiControlStatusChangeListenerCallback);
                    this.mHdmiManager.addHdmiCecVolumeControlFeatureListener(this.mContext.getMainExecutor(), this.mMyHdmiCecVolumeControlFeatureListener);
                }
                HdmiTvClient tvClient = this.mHdmiManager.getTvClient();
                this.mHdmiTvClient = tvClient;
                if (tvClient != null) {
                    this.mFixedVolumeDevices.removeAll(AudioSystem.DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER_SET);
                }
                this.mHdmiPlaybackClient = this.mHdmiManager.getPlaybackClient();
                this.mHdmiAudioSystemClient = this.mHdmiManager.getAudioSystemClient();
            }
        }
        if (this.mSupportsMicPrivacyToggle) {
            this.mSensorPrivacyManagerInternal.addSensorPrivacyListenerForAllUsers(1, new SensorPrivacyManagerInternal.OnUserSensorPrivacyChangedListener() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda25
                public final void onSensorPrivacyChanged(int i, boolean z) {
                    AudioService.this.lambda$onSystemReady$4(i, z);
                }
            });
        }
        this.mNm = (NotificationManager) this.mContext.getSystemService("notification");
        this.mSoundDoseHelper.configureSafeMedia(true, "AS.AudioService");
        initA11yMonitoring();
        RoleObserver roleObserver = new RoleObserver();
        this.mRoleObserver = roleObserver;
        roleObserver.register();
        onIndicateSystemReady();
        this.mMicMuteFromSystemCached = this.mAudioSystem.isMicrophoneMuted();
        setMicMuteFromSwitchInput();
        initMinStreamVolumeWithoutModifyAudioSettings();
        updateVibratorInfos();
        synchronized (this.mSupportedSystemUsagesLock) {
            AudioSystem.setSupportedSystemUsages(this.mSupportedSystemUsages);
        }
        onCustomSystemReady();
        this.mExt.systemReady();
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$4(int i, boolean z) {
        if (i == getCurrentUserId()) {
            this.mMicMuteFromPrivacyToggle = z;
            setMicrophoneMuteNoCallerCheck(getCurrentUserId());
        }
    }

    @Override // com.android.server.audio.AudioSystemAdapter.OnRoutingUpdatedListener
    public void onRoutingUpdatedFromNative() {
        sendMsg(this.mAudioHandler, 41, 0, 0, 0, null, 0);
    }

    public void onRoutingUpdatedFromAudioThread() {
        if (this.mHasSpatializerEffect) {
            this.mSpatializerHelper.onRoutingUpdated();
        }
        checkMuteAwaitConnection();
    }

    /* renamed from: onRotationUpdate, reason: merged with bridge method [inline-methods] */
    public void lambda$initExternalEventReceivers$2(Integer num) {
        this.mSpatializerHelper.setDisplayOrientation((float) ((num.intValue() * 3.141592653589793d) / 180.0d));
        sendMsg(this.mAudioHandler, 48, 0, 0, 0, "rotation=" + num, 0);
    }

    /* renamed from: onFoldStateUpdate, reason: merged with bridge method [inline-methods] */
    public void lambda$initExternalEventReceivers$3(Boolean bool) {
        this.mSpatializerHelper.setFoldState(bool.booleanValue());
        StringBuilder sb = new StringBuilder();
        sb.append("device_folded=");
        sb.append(bool.booleanValue() ? "on" : "off");
        sendMsg(this.mAudioHandler, 49, 0, 0, 0, sb.toString(), 0);
    }

    @Override // com.android.server.audio.AudioSystemAdapter.OnVolRangeInitRequestListener
    public void onVolumeRangeInitRequestFromNative() {
        sendMsg(this.mAudioHandler, 34, 0, 0, 0, "onVolumeRangeInitRequestFromNative", 0);
    }

    public class RoleObserver implements OnRoleHoldersChangedListener {
        public final Executor mExecutor;
        public RoleManager mRm;

        public RoleObserver() {
            this.mExecutor = AudioService.this.mContext.getMainExecutor();
        }

        public void register() {
            RoleManager roleManager = (RoleManager) AudioService.this.mContext.getSystemService("role");
            this.mRm = roleManager;
            if (roleManager != null) {
                roleManager.addOnRoleHoldersChangedListenerAsUser(this.mExecutor, this, UserHandle.ALL);
                synchronized (AudioService.this.mSettingsLock) {
                    AudioService.this.updateAssistantUIdLocked(true);
                }
            }
        }

        public void onRoleHoldersChanged(String str, UserHandle userHandle) {
            if ("android.app.role.ASSISTANT".equals(str)) {
                synchronized (AudioService.this.mSettingsLock) {
                    AudioService.this.updateAssistantUIdLocked(false);
                }
            }
        }

        public String getAssistantRoleHolder() {
            RoleManager roleManager = this.mRm;
            if (roleManager == null) {
                return "";
            }
            List roleHolders = roleManager.getRoleHolders("android.app.role.ASSISTANT");
            return roleHolders.size() == 0 ? "" : (String) roleHolders.get(0);
        }
    }

    public void onIndicateSystemReady() {
        if (AudioSystem.systemReady() == 0) {
            SemAudioSystem.setPolicyParameters("l_system_ready");
        } else {
            sendMsg(this.mAudioHandler, 20, 0, 0, 0, null, 1000);
        }
    }

    public void onAudioServerDied() {
        int i;
        if (!this.mSystemReady || AudioSystem.checkAudioFlinger() != 0) {
            Log.e("AS.AudioService", "Audioserver died.");
            sLifecycleLogger.enqueue(new EventLogger.StringEvent("onAudioServerDied() audioserver died"));
            sendMsg(this.mAudioHandler, 4, 1, 0, 0, null, 500);
            return;
        }
        Log.i("AS.AudioService", "Audioserver started.");
        sLifecycleLogger.enqueue(new EventLogger.StringEvent("onAudioServerDied() audioserver started"));
        updateAudioHalPids();
        AudioSystem.setParameters("restarting=true");
        readAndSetLowRamDevice();
        this.mIsCallScreeningModeSupported = AudioSystem.isCallScreeningModeSupported();
        onCustomAudioServerDied();
        this.mDeviceBroker.onAudioServerDied();
        AudioDeviceBroker.BtDeviceChangedData btDeviceChangedData = mBtDeviceChangedData;
        if (btDeviceChangedData != null) {
            this.mDeviceBroker.updateBluetoothA2dpDeviceConfigChange(btDeviceChangedData);
        }
        if (Rune.SEC_AUDIO_FM_RADIO && AudioManagerHelper.isFMPlayerActive()) {
            this.mAudioSystem.setForceUse(8, this.mForcedUseForFMRadio);
            this.mDeviceBroker.handleFmRadioDeviceConnection(-2147475456, 1, "dummy");
        }
        if (this.mBtScoDeviceInfo != null && isBluetoothScoOn()) {
            this.mAudioSystem.setParameters(this.mBtScoDeviceInfo);
        }
        synchronized (this.mDeviceBroker.mSetModeLock) {
            onUpdateAudioMode(-1, Process.myPid(), this.mContext.getPackageName(), true);
        }
        synchronized (this.mSettingsLock) {
            i = this.mCameraSoundForced ? 11 : 0;
        }
        this.mDeviceBroker.setForceUse_Async(4, i, "onAudioServerDied");
        onReinitVolumes("after audioserver restart");
        restoreVolumeGroups();
        updateMasterMono(this.mContentResolver);
        updateMasterBalance(this.mContentResolver);
        setRingerModeInt(getRingerModeInternal(), false);
        if (this.mMonitorRotation) {
            RotationHelper.updateOrientation();
        }
        this.mRestorableParameters.restoreAll();
        synchronized (this.mSettingsLock) {
            this.mDeviceBroker.setForceUse_Async(3, this.mDockAudioMediaEnabled ? 9 : 0, "onAudioServerDied");
            sendEncodedSurroundMode(this.mContentResolver, "onAudioServerDied");
            sendEnabledSurroundFormats(this.mContentResolver, true);
            AudioSystem.setRttEnabled(this.mRttEnabled);
            resetAssistantServicesUidsLocked();
        }
        synchronized (this.mAccessibilityServiceUidsLock) {
            AudioSystem.setA11yServicesUids(this.mAccessibilityServiceUids);
        }
        synchronized (this.mInputMethodServiceUidLock) {
            this.mAudioSystem.setCurrentImeUid(this.mInputMethodServiceUid);
        }
        synchronized (this.mHdmiClientLock) {
            if (this.mHdmiManager != null && this.mHdmiTvClient != null) {
                setHdmiSystemAudioSupported(this.mHdmiSystemAudioSupported);
            }
        }
        synchronized (this.mSupportedSystemUsagesLock) {
            AudioSystem.setSupportedSystemUsages(this.mSupportedSystemUsages);
        }
        synchronized (this.mAudioPolicies) {
            ArrayList arrayList = new ArrayList();
            for (AudioPolicyProxy audioPolicyProxy : this.mAudioPolicies.values()) {
                int connectMixes = audioPolicyProxy.connectMixes();
                if (connectMixes != 0) {
                    Log.e("AS.AudioService", "onAudioServerDied: error " + AudioSystem.audioSystemErrorToString(connectMixes) + " when connecting mixes for policy " + audioPolicyProxy.toLogFriendlyString());
                    arrayList.add(audioPolicyProxy);
                } else {
                    int i2 = audioPolicyProxy.setupDeviceAffinities();
                    if (i2 != 0) {
                        Log.e("AS.AudioService", "onAudioServerDied: error " + AudioSystem.audioSystemErrorToString(i2) + " when connecting device affinities for policy " + audioPolicyProxy.toLogFriendlyString());
                        arrayList.add(audioPolicyProxy);
                    }
                }
            }
            arrayList.forEach(new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda24
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((AudioService.AudioPolicyProxy) obj).release();
                }
            });
        }
        synchronized (this.mPlaybackMonitor) {
            for (Map.Entry entry : this.mPlaybackMonitor.getAllAllowedCapturePolicies().entrySet()) {
                int allowedCapturePolicy = this.mAudioSystem.setAllowedCapturePolicy(((Integer) entry.getKey()).intValue(), AudioAttributes.capturePolicyToFlags(((Integer) entry.getValue()).intValue(), 0));
                if (allowedCapturePolicy != 0) {
                    Log.e("AS.AudioService", "Failed to restore capture policy, uid: " + entry.getKey() + ", capture policy: " + entry.getValue() + ", result: " + allowedCapturePolicy);
                    this.mPlaybackMonitor.setAllowedCapturePolicy(((Integer) entry.getKey()).intValue(), 1);
                }
            }
        }
        this.mSpatializerHelper.reset(this.mHasSpatializerEffect);
        this.mSoundDoseHelper.reset();
        if (this.mMonitorRotation) {
            RotationHelper.forceUpdate();
        }
        onIndicateSystemReady();
        AudioSystem.setParameters("restarting=false");
        int intValue = this.mSettingHelper.getIntValue("ring_through_headset", 32);
        this.mHeadsetOnlyStream = intValue;
        setHeadsetOnlyStream(intValue);
        sendMsg(this.mAudioHandler, 23, 2, 1, 0, null, 0);
        setMicrophoneMuteNoCallerCheck(getCurrentUserId());
        setMicMuteFromSwitchInput();
        updateVibratorInfos();
    }

    public final void onRemoveAssistantServiceUids(int[] iArr) {
        synchronized (this.mSettingsLock) {
            removeAssistantServiceUidsLocked(iArr);
        }
    }

    public final void removeAssistantServiceUidsLocked(int[] iArr) {
        boolean z = false;
        for (int i = 0; i < iArr.length; i++) {
            if (this.mAssistantUids.remove(Integer.valueOf(iArr[i]))) {
                z = true;
            } else {
                Slog.e("AS.AudioService", TextUtils.formatSimple("Cannot remove assistant service, uid(%d) not present", new Object[]{Integer.valueOf(iArr[i])}));
            }
        }
        if (z) {
            updateAssistantServicesUidsLocked();
        }
    }

    public final void onAddAssistantServiceUids(int[] iArr) {
        synchronized (this.mSettingsLock) {
            addAssistantServiceUidsLocked(iArr);
        }
    }

    public final void addAssistantServiceUidsLocked(int[] iArr) {
        boolean z = false;
        for (int i = 0; i < iArr.length; i++) {
            int i2 = iArr[i];
            if (i2 != -1) {
                if (this.mAssistantUids.add(Integer.valueOf(i2))) {
                    z = true;
                } else {
                    Slog.e("AS.AudioService", TextUtils.formatSimple("Cannot add assistant service, uid(%d) already present", new Object[]{Integer.valueOf(iArr[i])}));
                }
            }
        }
        if (z) {
            updateAssistantServicesUidsLocked();
        }
    }

    public final void resetAssistantServicesUidsLocked() {
        this.mAssistantUids.clear();
        updateAssistantUIdLocked(true);
    }

    public final void updateAssistantServicesUidsLocked() {
        AudioSystem.setAssistantServicesUids(this.mAssistantUids.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray());
    }

    public final void updateActiveAssistantServiceUids() {
        int[] iArr;
        synchronized (this.mSettingsLock) {
            iArr = this.mActiveAssistantServiceUids;
        }
        AudioSystem.setActiveAssistantServicesUids(iArr);
    }

    public final void onReinitVolumes(String str) {
        int i;
        int numStreamTypes = AudioSystem.getNumStreamTypes() - 1;
        while (true) {
            if (numStreamTypes < 0) {
                i = 0;
                break;
            }
            VolumeStreamState volumeStreamState = this.mStreamStates[numStreamTypes];
            i = AudioSystem.initStreamVolume(numStreamTypes, volumeStreamState.mIndexMin / 10, volumeStreamState.mIndexMax / 10);
            if (i != 0) {
                Log.e("AS.AudioService", "Failed to initStreamVolume (" + i + ") for stream " + numStreamTypes);
                break;
            }
            volumeStreamState.applyAllVolumes();
            numStreamTypes--;
        }
        if (i != 0) {
            sLifecycleLogger.enqueue(new EventLogger.StringEvent(str + ": initStreamVolume failed with " + i + " will retry").printLog(1, "AS.AudioService"));
            sendMsg(this.mAudioHandler, 34, 1, 0, 0, str, 2000);
            return;
        }
        if (checkVolumeRangeInitialization(str)) {
            sLifecycleLogger.enqueue(new EventLogger.StringEvent(str + ": initStreamVolume succeeded").printLog(0, "AS.AudioService"));
        }
    }

    public final boolean checkVolumeRangeInitialization(String str) {
        boolean z = false;
        int[] iArr = {4, 2, 3, 0, 10};
        int i = 0;
        while (true) {
            if (i >= 5) {
                z = true;
                break;
            }
            AudioAttributes build = new AudioAttributes.Builder().setInternalLegacyStreamType(iArr[i]).build();
            if (AudioSystem.getMaxVolumeIndexForAttributes(build) < 0 || AudioSystem.getMinVolumeIndexForAttributes(build) < 0) {
                break;
            }
            i++;
        }
        if (!z) {
            sLifecycleLogger.enqueue(new EventLogger.StringEvent(str + ": initStreamVolume succeeded but invalid mix/max levels, will retry").printLog(2, "AS.AudioService"));
            sendMsg(this.mAudioHandler, 34, 1, 0, 0, str, 2000);
        }
        return z;
    }

    public final void onDispatchAudioServerStateChange(boolean z) {
        synchronized (this.mAudioServerStateListeners) {
            Iterator it = this.mAudioServerStateListeners.values().iterator();
            while (it.hasNext()) {
                try {
                    ((AsdProxy) it.next()).callback().dispatchAudioServerStateChange(z);
                } catch (RemoteException e) {
                    Log.w("AS.AudioService", "Could not call dispatchAudioServerStateChange()", e);
                }
            }
        }
    }

    public final void createAudioSystemThread() {
        AudioSystemThread audioSystemThread = new AudioSystemThread();
        this.mAudioSystemThread = audioSystemThread;
        audioSystemThread.start();
        waitForAudioHandlerCreation();
    }

    public final void waitForAudioHandlerCreation() {
        synchronized (this) {
            while (this.mAudioHandler == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                    Log.e("AS.AudioService", "Interrupted while waiting on volume handler.");
                }
            }
        }
    }

    public void setSupportedSystemUsages(int[] iArr) {
        super.setSupportedSystemUsages_enforcePermission();
        verifySystemUsages(iArr);
        synchronized (this.mSupportedSystemUsagesLock) {
            AudioSystem.setSupportedSystemUsages(iArr);
            this.mSupportedSystemUsages = iArr;
        }
    }

    public int[] getSupportedSystemUsages() {
        int[] copyOf;
        super.getSupportedSystemUsages_enforcePermission();
        synchronized (this.mSupportedSystemUsagesLock) {
            int[] iArr = this.mSupportedSystemUsages;
            copyOf = Arrays.copyOf(iArr, iArr.length);
        }
        return copyOf;
    }

    public final void verifySystemUsages(int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            if (!AudioAttributes.isSystemUsage(iArr[i])) {
                throw new IllegalArgumentException("Non-system usage provided: " + iArr[i]);
            }
        }
    }

    public List getAudioProductStrategies() {
        super.getAudioProductStrategies_enforcePermission();
        return AudioProductStrategy.getAudioProductStrategies();
    }

    public List getAudioVolumeGroups() {
        super.getAudioVolumeGroups_enforcePermission();
        return AudioVolumeGroup.getAudioVolumeGroups();
    }

    public final void checkAllAliasStreamVolumes() {
        synchronized (this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                int numStreamTypes = AudioSystem.getNumStreamTypes();
                for (int i = 0; i < numStreamTypes; i++) {
                    VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                    volumeStreamStateArr[i].setAllIndexes(volumeStreamStateArr[mStreamVolumeAlias[i]], "AS.AudioService");
                    if (!this.mStreamStates[i].mIsMuted) {
                        this.mStreamStates[i].applyAllVolumes();
                    }
                }
            }
        }
    }

    public void postCheckVolumeCecOnHdmiConnection(int i, String str) {
        sendMsg(this.mAudioHandler, 28, 0, i, 0, str, 0);
    }

    public final void onCheckVolumeCecOnHdmiConnection(int i, String str) {
        if (i == 1) {
            if (this.mSoundDoseHelper.safeDevicesContains(1024)) {
                this.mSoundDoseHelper.scheduleMusicActiveCheck();
            }
            if (isPlatformTelevision()) {
                synchronized (this.mHdmiClientLock) {
                    if (this.mHdmiManager != null && this.mHdmiPlaybackClient != null) {
                        updateHdmiCecSinkLocked(this.mFullVolumeDevices.contains(1024));
                    }
                }
            }
            sendEnabledSurroundFormats(this.mContentResolver, true);
            return;
        }
        if (isPlatformTelevision()) {
            synchronized (this.mHdmiClientLock) {
                if (this.mHdmiManager != null) {
                    updateHdmiCecSinkLocked(this.mFullVolumeDevices.contains(1024));
                }
            }
        }
    }

    public final void postUpdateVolumeStatesForAudioDevice(int i, String str) {
        sendMsg(this.mAudioHandler, 33, 2, i, 0, str, 0);
    }

    public final void onUpdateVolumeStatesForAudioDevice(int i, String str) {
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        synchronized (this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                for (int i2 = 0; i2 < numStreamTypes; i2++) {
                    updateVolumeStates(i, i2, str);
                }
            }
        }
    }

    public final void updateVolumeStates(int i, int i2, String str) {
        if (i == 4194304) {
            i = 2;
        }
        if (!this.mStreamStates[i2].hasIndexForDevice(i)) {
            VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
            volumeStreamStateArr[i2].setIndex(volumeStreamStateArr[mStreamVolumeAlias[i2]].getIndex(1073741824), i, str, true);
        }
        Iterator it = getDevicesForAttributesInt(new AudioAttributes.Builder().setInternalLegacyStreamType(i2).build(), true).iterator();
        while (it.hasNext()) {
            if (((AudioDeviceAttributes) it.next()).getType() == AudioDeviceInfo.convertInternalDeviceToDeviceType(i)) {
                this.mStreamStates[i2].checkFixedVolumeDevices();
                if (isStreamMute(i2) && this.mFullVolumeDevices.contains(Integer.valueOf(i))) {
                    this.mStreamStates[i2].mute(false, "updateVolumeStates(" + str);
                }
            }
        }
    }

    public final void checkAllFixedVolumeDevices() {
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        for (int i = 0; i < numStreamTypes; i++) {
            this.mStreamStates[i].checkFixedVolumeDevices();
        }
    }

    public final void checkAllFixedVolumeDevices(int i) {
        this.mStreamStates[i].checkFixedVolumeDevices();
    }

    public final void checkMuteAffectedStreams() {
        int i = 0;
        while (true) {
            VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
            if (i >= volumeStreamStateArr.length) {
                return;
            }
            VolumeStreamState volumeStreamState = volumeStreamStateArr[i];
            if (volumeStreamState.mIndexMin > 0 && volumeStreamState.mStreamType != 0 && volumeStreamState.mStreamType != 6) {
                this.mMuteAffectedStreams = (~(1 << volumeStreamState.mStreamType)) & this.mMuteAffectedStreams;
            }
            i++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void createStreamStates() {
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        VolumeStreamState[] volumeStreamStateArr = new VolumeStreamState[numStreamTypes];
        this.mStreamStates = volumeStreamStateArr;
        for (int i = 0; i < numStreamTypes; i++) {
            Object[] objArr = 0;
            if (i == 3) {
                volumeStreamStateArr[i] = new MediaVolumeStreamState(Settings.System.VOLUME_SETTINGS_INT[mStreamVolumeAlias[i]], i);
            } else {
                volumeStreamStateArr[i] = new VolumeStreamState(Settings.System.VOLUME_SETTINGS_INT[mStreamVolumeAlias[i]], i);
            }
        }
        checkAllFixedVolumeDevices();
        checkAllAliasStreamVolumes();
        checkMuteAffectedStreams();
        updateDefaultVolumes();
    }

    public final void updateDefaultVolumes() {
        for (int i = 0; i < this.mStreamStates.length; i++) {
            int i2 = mStreamVolumeAlias[i];
            if (this.mUseVolumeGroupAliases) {
                if (AudioSystem.DEFAULT_STREAM_VOLUME[i] == -1) {
                    i2 = 3;
                    int uiDefaultRescaledIndex = getUiDefaultRescaledIndex(3, i);
                    if (uiDefaultRescaledIndex >= MIN_STREAM_VOLUME[i] && uiDefaultRescaledIndex <= MAX_STREAM_VOLUME[i]) {
                        AudioSystem.DEFAULT_STREAM_VOLUME[i] = uiDefaultRescaledIndex;
                    }
                }
            }
            if (i != i2) {
                AudioSystem.DEFAULT_STREAM_VOLUME[i] = getUiDefaultRescaledIndex(i2, i);
            }
        }
    }

    public final int getUiDefaultRescaledIndex(int i, int i2) {
        return (rescaleIndex(AudioSystem.DEFAULT_STREAM_VOLUME[i] * 10, i, i2) + 5) / 10;
    }

    public final void dumpStreamStates(PrintWriter printWriter) {
        printWriter.println("\nStream volumes (device: index)");
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        for (int i = 0; i < numStreamTypes; i++) {
            StringBuilder sb = new StringBuilder();
            if (mStreamVolumeAlias[i] != i) {
                sb.append(" (aliased to: ");
                sb.append(AudioSystem.STREAM_NAMES[mStreamVolumeAlias[i]]);
                sb.append(")");
            }
            printWriter.println("- " + AudioSystem.STREAM_NAMES[i] + ((Object) sb) + XmlUtils.STRING_ARRAY_SEPARATOR);
            this.mStreamStates[i].dump(printWriter);
            printWriter.println("");
        }
        printWriter.print("\n- mute affected streams = 0x");
        printWriter.println(Integer.toHexString(this.mMuteAffectedStreams));
    }

    public final void updateStreamVolumeAlias(boolean z, String str) {
        int i = 3;
        int i2 = sIndependentA11yVolume ? 10 : 3;
        int i3 = this.mContext.getResources().getBoolean(R.bool.config_supportAutoRotation) ? 11 : 3;
        if (this.mIsSingleVolume) {
            mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_TELEVISION.clone();
        } else if (this.mUseVolumeGroupAliases) {
            mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_NONE.clone();
            i = 8;
        } else {
            if (this.mPlatformType == 1) {
                mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_VOICE.clone();
                i = 2;
            } else {
                mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_DEFAULT.clone();
                if (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION) {
                    i = 5;
                }
            }
            if (!this.mNotifAliasRing) {
                mStreamVolumeAlias[5] = 5;
            }
        }
        if (this.mIsSingleVolume) {
            this.mRingerModeAffectedStreams = 0;
        } else if (isInCommunication()) {
            this.mRingerModeAffectedStreams &= -257;
            i = 0;
        } else {
            this.mRingerModeAffectedStreams |= 256;
        }
        int[] iArr = mStreamVolumeAlias;
        iArr[8] = i;
        iArr[10] = i2;
        iArr[11] = i3;
        if (z && this.mStreamStates != null) {
            updateDefaultVolumes();
            synchronized (this.mSettingsLock) {
                synchronized (VolumeStreamState.class) {
                    VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                    volumeStreamStateArr[8].setAllIndexes(volumeStreamStateArr[i], str);
                    this.mStreamStates[10].setSettingName(Settings.System.VOLUME_SETTINGS_INT[i2]);
                    VolumeStreamState[] volumeStreamStateArr2 = this.mStreamStates;
                    volumeStreamStateArr2[10].setAllIndexes(volumeStreamStateArr2[i2], str);
                }
            }
            if (sIndependentA11yVolume) {
                this.mStreamStates[10].readSettings();
            }
            setRingerModeInt(getRingerModeInternal(), false);
            sendMsg(this.mAudioHandler, 10, 2, 0, 0, this.mStreamStates[8], 0);
            sendMsg(this.mAudioHandler, 10, 2, 0, 0, this.mStreamStates[10], 0);
        }
        dispatchStreamAliasingUpdate();
    }

    public final void readDockAudioSettings(ContentResolver contentResolver) {
        boolean z = this.mSettings.getGlobalInt(contentResolver, "dock_audio_media_enabled", 0) == 1;
        this.mDockAudioMediaEnabled = z;
        sendMsg(this.mAudioHandler, 8, 2, 3, z ? 9 : 0, new String("readDockAudioSettings"), 0);
    }

    public final void updateMasterMono(ContentResolver contentResolver) {
        boolean z = this.mSettings.getSystemIntForUser(contentResolver, "master_mono", 0, -2) == 1;
        Log.d("AS.AudioService", String.format("Master mono %b", Boolean.valueOf(z)));
        AudioSystem.setMasterMono(z);
        int systemIntForUser = this.mSettings.getSystemIntForUser(contentResolver, "mono_audio_type", 0, -2);
        this.mAudioSystem.setParameters("l_mono_type=" + systemIntForUser);
        this.mExt.updateMonoSetting(z);
    }

    public final void updateMasterBalance(ContentResolver contentResolver) {
        float floatForUser = Settings.System.getFloatForUser(contentResolver, "master_balance", DisplayPowerController2.RATE_FROM_DOZE_TO_ON, -2);
        Log.d("AS.AudioService", String.format("Master balance %f", Float.valueOf(floatForUser)));
        if (AudioSystem.setMasterBalance(floatForUser) != 0) {
            Log.e("AS.AudioService", String.format("setMasterBalance failed for %f", Float.valueOf(floatForUser)));
        }
        this.mExt.updateBalance(floatForUser);
    }

    public final void sendEncodedSurroundMode(ContentResolver contentResolver, String str) {
        sendEncodedSurroundMode(this.mSettings.getGlobalInt(contentResolver, "encoded_surround_output", 0), str);
    }

    public final void sendEncodedSurroundMode(int i, String str) {
        int i2;
        if (i == 0) {
            i2 = 0;
        } else if (i == 1) {
            i2 = 13;
        } else if (i == 2) {
            i2 = 14;
        } else if (i != 3) {
            Log.e("AS.AudioService", "updateSurroundSoundSettings: illegal value " + i);
            i2 = 16;
        } else {
            i2 = 15;
        }
        if (i2 != 16) {
            this.mDeviceBroker.setForceUse_Async(6, i2, str);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_AUDIO_POLICY") != 0) {
            throw new SecurityException("Missing MANAGE_AUDIO_POLICY permission");
        }
        new AudioManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public Map getSurroundFormats() {
        HashMap hashMap = new HashMap();
        int surroundFormats = AudioSystem.getSurroundFormats(hashMap);
        if (surroundFormats == 0) {
            return hashMap;
        }
        Log.e("AS.AudioService", "getSurroundFormats failed:" + surroundFormats);
        return new HashMap();
    }

    public List getReportedSurroundFormats() {
        ArrayList arrayList = new ArrayList();
        int reportedSurroundFormats = AudioSystem.getReportedSurroundFormats(arrayList);
        if (reportedSurroundFormats == 0) {
            return arrayList;
        }
        Log.e("AS.AudioService", "getReportedSurroundFormats failed:" + reportedSurroundFormats);
        return new ArrayList();
    }

    public boolean isSurroundFormatEnabled(int i) {
        boolean contains;
        if (!isSurroundFormat(i)) {
            Log.w("AS.AudioService", "audioFormat to enable is not a surround format.");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                contains = getEnabledFormats().contains(Integer.valueOf(i));
            }
            return contains;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean setSurroundFormatEnabled(int i, boolean z) {
        if (!isSurroundFormat(i)) {
            Log.w("AS.AudioService", "audioFormat to enable is not a surround format.");
            return false;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") != 0) {
            throw new SecurityException("Missing WRITE_SETTINGS permission");
        }
        HashSet enabledFormats = getEnabledFormats();
        if (z) {
            enabledFormats.add(Integer.valueOf(i));
        } else {
            enabledFormats.remove(Integer.valueOf(i));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                this.mSettings.putGlobalString(this.mContentResolver, "encoded_surround_output_enabled_formats", TextUtils.join(",", enabledFormats));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public boolean setEncodedSurroundMode(int i) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") != 0) {
            throw new SecurityException("Missing WRITE_SETTINGS permission");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                this.mSettings.putGlobalInt(this.mContentResolver, "encoded_surround_output", toEncodedSurroundSetting(i));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public int getEncodedSurroundMode(int i) {
        int encodedSurroundOutputMode;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                encodedSurroundOutputMode = toEncodedSurroundOutputMode(this.mSettings.getGlobalInt(this.mContentResolver, "encoded_surround_output", 0), i);
            }
            return encodedSurroundOutputMode;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final HashSet getEnabledFormats() {
        final HashSet hashSet = new HashSet();
        String globalString = this.mSettings.getGlobalString(this.mContentResolver, "encoded_surround_output_enabled_formats");
        if (globalString != null) {
            try {
                Arrays.stream(TextUtils.split(globalString, ",")).mapToInt(new ToIntFunction() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda7
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        return Integer.parseInt((String) obj);
                    }
                }).forEach(new IntConsumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda8
                    @Override // java.util.function.IntConsumer
                    public final void accept(int i) {
                        hashSet.add(Integer.valueOf(i));
                    }
                });
            } catch (NumberFormatException e) {
                Log.w("AS.AudioService", "ENCODED_SURROUND_OUTPUT_ENABLED_FORMATS misformatted.", e);
            }
        }
        return hashSet;
    }

    public final boolean isSurroundFormat(int i) {
        for (int i2 : AudioFormat.SURROUND_SOUND_ENCODING) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public final void sendEnabledSurroundFormats(ContentResolver contentResolver, boolean z) {
        if (this.mEncodedSurroundMode != 3) {
            return;
        }
        String globalString = this.mSettings.getGlobalString(contentResolver, "encoded_surround_output_enabled_formats");
        if (globalString == null) {
            globalString = "";
        }
        if (z || !TextUtils.equals(globalString, this.mEnabledSurroundFormats)) {
            this.mEnabledSurroundFormats = globalString;
            String[] split = TextUtils.split(globalString, ",");
            ArrayList arrayList = new ArrayList();
            for (String str : split) {
                try {
                    int intValue = Integer.valueOf(str).intValue();
                    if (isSurroundFormat(intValue) && !arrayList.contains(Integer.valueOf(intValue))) {
                        arrayList.add(Integer.valueOf(intValue));
                    }
                } catch (Exception unused) {
                    Log.e("AS.AudioService", "Invalid enabled surround format:" + str);
                }
            }
            this.mSettings.putGlobalString(this.mContext.getContentResolver(), "encoded_surround_output_enabled_formats", TextUtils.join(",", arrayList));
            sendMsg(this.mAudioHandler, 24, 2, 0, 0, arrayList, 0);
        }
    }

    public final void onEnableSurroundFormats(ArrayList arrayList) {
        for (int i : AudioFormat.SURROUND_SOUND_ENCODING) {
            boolean contains = arrayList.contains(Integer.valueOf(i));
            Log.i("AS.AudioService", "enable surround format:" + i + " " + contains + " " + AudioSystem.setSurroundFormatEnabled(i, contains));
        }
    }

    public final void updateAssistantUIdLocked(boolean z) {
        int i;
        int i2;
        RoleObserver roleObserver = this.mRoleObserver;
        String assistantRoleHolder = roleObserver != null ? roleObserver.getAssistantRoleHolder() : "";
        if (TextUtils.isEmpty(assistantRoleHolder)) {
            String secureStringForUser = this.mSettings.getSecureStringForUser(this.mContentResolver, "voice_interaction_service", -2);
            if (TextUtils.isEmpty(secureStringForUser)) {
                secureStringForUser = this.mSettings.getSecureStringForUser(this.mContentResolver, "assistant", -2);
            }
            if (!TextUtils.isEmpty(secureStringForUser)) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(secureStringForUser);
                if (unflattenFromString == null) {
                    Slog.w("AS.AudioService", "Invalid service name for voice_interaction_service: " + secureStringForUser);
                    return;
                }
                assistantRoleHolder = unflattenFromString.getPackageName();
            }
        }
        if (!TextUtils.isEmpty(assistantRoleHolder)) {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager.checkPermission("android.permission.CAPTURE_AUDIO_HOTWORD", assistantRoleHolder) == 0) {
                try {
                    i = packageManager.getPackageUidAsUser(assistantRoleHolder, getCurrentUserId());
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("AS.AudioService", "updateAssistantUId() could not find UID for package: " + assistantRoleHolder);
                }
                i2 = this.mPrimaryAssistantUid;
                if (i2 == i || z) {
                    this.mAssistantUids.remove(Integer.valueOf(i2));
                    this.mPrimaryAssistantUid = i;
                    addAssistantServiceUidsLocked(new int[]{i});
                }
                return;
            }
        }
        i = -1;
        i2 = this.mPrimaryAssistantUid;
        if (i2 == i) {
        }
        this.mAssistantUids.remove(Integer.valueOf(i2));
        this.mPrimaryAssistantUid = i;
        addAssistantServiceUidsLocked(new int[]{i});
    }

    public final void readPersistedSettings() {
        if (this.mSystemServer.isPrivileged()) {
            ContentResolver contentResolver = this.mContentResolver;
            int globalInt = this.mSettings.getGlobalInt(contentResolver, "mode_ringer", 2);
            int i = !isValidRingerMode(globalInt) ? 2 : globalInt;
            if (i == 1 && !this.mHasVibrator) {
                i = 0;
            }
            if (i != globalInt) {
                this.mSettings.putGlobalInt(contentResolver, "mode_ringer", i);
            }
            if (this.mUseFixedVolume || this.mIsSingleVolume) {
                i = 2;
            }
            synchronized (this.mSettingsLock) {
                this.mRingerMode = i;
                if (this.mRingerModeExternal == -1) {
                    this.mRingerModeExternal = i;
                }
                int valueForVibrateSetting = AudioSystem.getValueForVibrateSetting(0, 1, this.mHasVibrator ? 2 : 0);
                this.mVibrateSetting = valueForVibrateSetting;
                this.mVibrateSetting = AudioSystem.getValueForVibrateSetting(valueForVibrateSetting, 0, this.mHasVibrator ? 2 : 0);
                updateRingerAndZenModeAffectedStreams();
                readDockAudioSettings(contentResolver);
                sendEncodedSurroundMode(contentResolver, "readPersistedSettings");
                sendEnabledSurroundFormats(contentResolver, true);
                updateAssistantUIdLocked(true);
                resetActiveAssistantUidsLocked();
                AudioSystem.setRttEnabled(this.mRttEnabled);
            }
            this.mMuteAffectedStreams = this.mSettings.getSystemIntForUser(contentResolver, "mute_streams_affected", 111, -2);
            if (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION) {
                int[] iArr = this.STREAM_VOLUME_ALIAS_DEFAULT;
                iArr[2] = 5;
                iArr[5] = 5;
                int[] iArr2 = mStreamVolumeAlias;
                iArr2[2] = 5;
                iArr2[5] = 5;
            }
            updateMasterMono(contentResolver);
            updateMasterBalance(contentResolver);
            broadcastRingerMode("android.media.RINGER_MODE_CHANGED", this.mRingerModeExternal);
            broadcastRingerMode("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", this.mRingerMode);
            broadcastVibrateSetting(0);
            broadcastVibrateSetting(1);
            this.mVolumeController.loadSettings(contentResolver);
            readPersistedCustomSettings();
        }
    }

    public final void resetActiveAssistantUidsLocked() {
        this.mActiveAssistantServiceUids = NO_ACTIVE_ASSISTANT_SERVICE_UIDS;
        updateActiveAssistantServiceUids();
    }

    public final void readUserRestrictions() {
        if (this.mSystemServer.isPrivileged()) {
            int currentUserId = getCurrentUserId();
            boolean z = false;
            boolean z2 = this.mUserManagerInternal.getUserRestriction(currentUserId, "disallow_unmute_device") || this.mUserManagerInternal.getUserRestriction(currentUserId, "no_adjust_volume");
            if (this.mUseFixedVolume) {
                AudioSystem.setMasterVolume(1.0f);
            } else {
                z = z2;
            }
            Log.d("AS.AudioService", String.format("Master mute %s, user=%d", Boolean.valueOf(z), Integer.valueOf(currentUserId)));
            AudioSystem.setMasterMute(z);
            broadcastMasterMuteStatus(z);
            boolean userRestriction = this.mUserManagerInternal.getUserRestriction(currentUserId, "no_unmute_microphone");
            this.mMicMuteFromRestrictions = userRestriction;
            Log.d("AS.AudioService", String.format("Mic mute %b, user=%d", Boolean.valueOf(userRestriction), Integer.valueOf(currentUserId)));
            setMicrophoneMuteNoCallerCheck(currentUserId);
            this.mExt.readAllSoundMuteUserRestriction(currentUserId);
        }
    }

    public final int getIndexRange(int i) {
        return this.mStreamStates[i].getMaxIndex() - this.mStreamStates[i].getMinIndex();
    }

    public final int rescaleIndex(VolumeInfo volumeInfo, int i) {
        if (volumeInfo.getVolumeIndex() == -100 || volumeInfo.getMinVolumeIndex() == -100 || volumeInfo.getMaxVolumeIndex() == -100) {
            Log.e("AS.AudioService", "rescaleIndex: volumeInfo has invalid index or range");
            return this.mStreamStates[i].getMinIndex();
        }
        return rescaleIndex(volumeInfo.getVolumeIndex(), volumeInfo.getMinVolumeIndex(), volumeInfo.getMaxVolumeIndex(), this.mStreamStates[i].getMinIndex(), this.mStreamStates[i].getMaxIndex());
    }

    public final int rescaleIndex(int i, int i2, VolumeInfo volumeInfo) {
        int minVolumeIndex = volumeInfo.getMinVolumeIndex();
        int maxVolumeIndex = volumeInfo.getMaxVolumeIndex();
        return (minVolumeIndex == -100 || maxVolumeIndex == -100) ? i : rescaleIndex(i, this.mStreamStates[i2].getMinIndex(), this.mStreamStates[i2].getMaxIndex(), minVolumeIndex, maxVolumeIndex);
    }

    public final int rescaleIndex(int i, int i2, int i3) {
        return rescaleIndex(i, this.mStreamStates[i2].getMinIndex(), this.mStreamStates[i2].getMaxIndex(), this.mStreamStates[i3].getMinIndex(), this.mStreamStates[i3].getMaxIndex());
    }

    public final int rescaleIndex(int i, int i2, int i3, int i4, int i5) {
        int i6 = i3 - i2;
        int i7 = i5 - i4;
        if (i6 == 0) {
            Log.e("AS.AudioService", "rescaleIndex : index range should not be zero");
            return i4;
        }
        return i4 + ((((i - i2) * i7) + (i6 / 2)) / i6);
    }

    public final int rescaleStep(int i, int i2, int i3) {
        int indexRange = getIndexRange(i2);
        int indexRange2 = getIndexRange(i3);
        if (indexRange == 0) {
            Log.e("AS.AudioService", "rescaleStep : index range should not be zero");
            return 0;
        }
        return ((i * indexRange2) + (indexRange / 2)) / indexRange;
    }

    public int setPreferredDevicesForStrategy(int i, List list) {
        super.setPreferredDevicesForStrategy_enforcePermission();
        if (list == null) {
            return -1;
        }
        String format = String.format("setPreferredDeviceForStrategy u/pid:%d/%d strat:%d dev:%s", Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(i), list.stream().map(new Function() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String audioDeviceAttributes;
                audioDeviceAttributes = ((AudioDeviceAttributes) obj).toString();
                return audioDeviceAttributes;
            }
        }).collect(Collectors.joining(",")));
        sDeviceLogger.enqueue(new EventLogger.StringEvent(format).printLog("AS.AudioService"));
        if (list.stream().anyMatch(new Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$setPreferredDevicesForStrategy$7;
                lambda$setPreferredDevicesForStrategy$7 = AudioService.lambda$setPreferredDevicesForStrategy$7((AudioDeviceAttributes) obj);
                return lambda$setPreferredDevicesForStrategy$7;
            }
        })) {
            Log.e("AS.AudioService", "Unsupported input routing in " + format);
            return -1;
        }
        int preferredDevicesForStrategySync = this.mDeviceBroker.setPreferredDevicesForStrategySync(i, list);
        if (preferredDevicesForStrategySync != 0) {
            Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(preferredDevicesForStrategySync), format));
        }
        return preferredDevicesForStrategySync;
    }

    public static /* synthetic */ boolean lambda$setPreferredDevicesForStrategy$7(AudioDeviceAttributes audioDeviceAttributes) {
        return audioDeviceAttributes.getRole() == 1;
    }

    public int removePreferredDevicesForStrategy(int i) {
        super.removePreferredDevicesForStrategy_enforcePermission();
        String format = String.format("removePreferredDeviceForStrategy strat:%d", Integer.valueOf(i));
        sDeviceLogger.enqueue(new EventLogger.StringEvent(format).printLog("AS.AudioService"));
        int removePreferredDevicesForStrategySync = this.mDeviceBroker.removePreferredDevicesForStrategySync(i);
        if (removePreferredDevicesForStrategySync != 0) {
            Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(removePreferredDevicesForStrategySync), format));
        }
        return removePreferredDevicesForStrategySync;
    }

    public List getPreferredDevicesForStrategy(int i) {
        super.getPreferredDevicesForStrategy_enforcePermission();
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int devicesForRoleAndStrategy = AudioSystem.getDevicesForRoleAndStrategy(i, 1, arrayList);
            if (devicesForRoleAndStrategy == 0) {
                return arrayList;
            }
            Log.e("AS.AudioService", String.format("Error %d in getPreferredDeviceForStrategy(%d)", Integer.valueOf(devicesForRoleAndStrategy), Integer.valueOf(i)));
            return new ArrayList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int setDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) {
        super.setDeviceAsNonDefaultForStrategy_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        String format = String.format("setDeviceAsNonDefaultForStrategy u/pid:%d/%d strat:%d dev:%s", Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(i), audioDeviceAttributes.toString());
        sDeviceLogger.enqueue(new EventLogger.StringEvent(format).printLog("AS.AudioService"));
        if (audioDeviceAttributes.getRole() == 1) {
            Log.e("AS.AudioService", "Unsupported input routing in " + format);
            return -1;
        }
        int deviceAsNonDefaultForStrategySync = this.mDeviceBroker.setDeviceAsNonDefaultForStrategySync(i, audioDeviceAttributes);
        if (deviceAsNonDefaultForStrategySync != 0) {
            Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(deviceAsNonDefaultForStrategySync), format));
        }
        return deviceAsNonDefaultForStrategySync;
    }

    public int removeDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) {
        super.removeDeviceAsNonDefaultForStrategy_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        String format = String.format("removeDeviceAsNonDefaultForStrategy strat:%d dev:%s", Integer.valueOf(i), audioDeviceAttributes.toString());
        sDeviceLogger.enqueue(new EventLogger.StringEvent(format).printLog("AS.AudioService"));
        if (audioDeviceAttributes.getRole() == 1) {
            Log.e("AS.AudioService", "Unsupported input routing in " + format);
            return -1;
        }
        int removeDeviceAsNonDefaultForStrategySync = this.mDeviceBroker.removeDeviceAsNonDefaultForStrategySync(i, audioDeviceAttributes);
        if (removeDeviceAsNonDefaultForStrategySync != 0) {
            Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(removeDeviceAsNonDefaultForStrategySync), format));
        }
        return removeDeviceAsNonDefaultForStrategySync;
    }

    public List getNonDefaultDevicesForStrategy(int i) {
        super.getNonDefaultDevicesForStrategy_enforcePermission();
        ArrayList arrayList = new ArrayList();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            int devicesForRoleAndStrategy = AudioSystem.getDevicesForRoleAndStrategy(i, 2, arrayList);
            if (create != null) {
                create.close();
            }
            if (devicesForRoleAndStrategy == 0) {
                return arrayList;
            }
            Log.e("AS.AudioService", String.format("Error %d in getNonDefaultDeviceForStrategy(%d)", Integer.valueOf(devicesForRoleAndStrategy), Integer.valueOf(i)));
            return new ArrayList();
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void registerStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        if (iStrategyPreferredDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.registerStrategyPreferredDevicesDispatcher(iStrategyPreferredDevicesDispatcher);
    }

    public void unregisterStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        if (iStrategyPreferredDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.unregisterStrategyPreferredDevicesDispatcher(iStrategyPreferredDevicesDispatcher);
    }

    public void registerStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        if (iStrategyNonDefaultDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.registerStrategyNonDefaultDevicesDispatcher(iStrategyNonDefaultDevicesDispatcher);
    }

    public void unregisterStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        if (iStrategyNonDefaultDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.unregisterStrategyNonDefaultDevicesDispatcher(iStrategyNonDefaultDevicesDispatcher);
    }

    public int setPreferredDevicesForCapturePreset(int i, List list) {
        if (list == null) {
            return -1;
        }
        enforceModifyAudioRoutingPermission();
        String format = String.format("setPreferredDevicesForCapturePreset u/pid:%d/%d source:%d dev:%s", Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(i), list.stream().map(new Function() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String audioDeviceAttributes;
                audioDeviceAttributes = ((AudioDeviceAttributes) obj).toString();
                return audioDeviceAttributes;
            }
        }).collect(Collectors.joining(",")));
        sDeviceLogger.enqueue(new EventLogger.StringEvent(format).printLog("AS.AudioService"));
        if (list.stream().anyMatch(new Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$setPreferredDevicesForCapturePreset$9;
                lambda$setPreferredDevicesForCapturePreset$9 = AudioService.lambda$setPreferredDevicesForCapturePreset$9((AudioDeviceAttributes) obj);
                return lambda$setPreferredDevicesForCapturePreset$9;
            }
        })) {
            Log.e("AS.AudioService", "Unsupported output routing in " + format);
            return -1;
        }
        int preferredDevicesForCapturePresetSync = this.mDeviceBroker.setPreferredDevicesForCapturePresetSync(i, list);
        if (preferredDevicesForCapturePresetSync != 0) {
            Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(preferredDevicesForCapturePresetSync), format));
        }
        return preferredDevicesForCapturePresetSync;
    }

    public static /* synthetic */ boolean lambda$setPreferredDevicesForCapturePreset$9(AudioDeviceAttributes audioDeviceAttributes) {
        return audioDeviceAttributes.getRole() == 2;
    }

    public int clearPreferredDevicesForCapturePreset(int i) {
        super.clearPreferredDevicesForCapturePreset_enforcePermission();
        String format = String.format("removePreferredDeviceForCapturePreset source:%d", Integer.valueOf(i));
        sDeviceLogger.enqueue(new EventLogger.StringEvent(format).printLog("AS.AudioService"));
        int clearPreferredDevicesForCapturePresetSync = this.mDeviceBroker.clearPreferredDevicesForCapturePresetSync(i);
        if (clearPreferredDevicesForCapturePresetSync != 0) {
            Log.e("AS.AudioService", String.format("Error %d in %s", Integer.valueOf(clearPreferredDevicesForCapturePresetSync), format));
        }
        return clearPreferredDevicesForCapturePresetSync;
    }

    public List getPreferredDevicesForCapturePreset(int i) {
        super.getPreferredDevicesForCapturePreset_enforcePermission();
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int devicesForRoleAndCapturePreset = AudioSystem.getDevicesForRoleAndCapturePreset(i, 1, arrayList);
            if (devicesForRoleAndCapturePreset == 0) {
                return arrayList;
            }
            Log.e("AS.AudioService", String.format("Error %d in getPreferredDeviceForCapturePreset(%d)", Integer.valueOf(devicesForRoleAndCapturePreset), Integer.valueOf(i)));
            return new ArrayList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        if (iCapturePresetDevicesRoleDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.registerCapturePresetDevicesRoleDispatcher(iCapturePresetDevicesRoleDispatcher);
    }

    public void unregisterCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        if (iCapturePresetDevicesRoleDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.unregisterCapturePresetDevicesRoleDispatcher(iCapturePresetDevicesRoleDispatcher);
    }

    /* renamed from: getDevicesForAttributes, reason: merged with bridge method [inline-methods] */
    public ArrayList m2937getDevicesForAttributes(AudioAttributes audioAttributes) {
        enforceQueryStateOrModifyRoutingPermission();
        return getDevicesForAttributesInt(audioAttributes, false);
    }

    /* renamed from: getDevicesForAttributesUnprotected, reason: merged with bridge method [inline-methods] */
    public ArrayList m2938getDevicesForAttributesUnprotected(AudioAttributes audioAttributes) {
        return getDevicesForAttributesInt(audioAttributes, false);
    }

    public boolean isMusicActive(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (z) {
                return AudioSystem.isStreamActiveRemotely(3, 0);
            }
            return AudioSystem.isStreamActive(3, 0);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public ArrayList getDevicesForAttributesInt(AudioAttributes audioAttributes, boolean z) {
        Objects.requireNonNull(audioAttributes);
        return this.mAudioSystem.getDevicesForAttributes(audioAttributes, z);
    }

    public void addOnDevicesForAttributesChangedListener(AudioAttributes audioAttributes, IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        this.mAudioSystem.addOnDevicesForAttributesChangedListener(audioAttributes, false, iDevicesForAttributesCallback);
    }

    public void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        this.mAudioSystem.removeOnDevicesForAttributesChangedListener(iDevicesForAttributesCallback);
    }

    public void handleVolumeKey(KeyEvent keyEvent, boolean z, String str, String str2) {
        int i;
        if (z) {
            i = keyEvent.getAction() == 0 ? 1 : 2;
        } else if (keyEvent.getAction() != 0) {
            return;
        } else {
            i = 0;
        }
        int i2 = i;
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 24) {
            adjustSuggestedStreamVolume(1, Integer.MIN_VALUE, 4101, str, str2, Binder.getCallingUid(), Binder.getCallingPid(), true, i2);
            return;
        }
        if (keyCode == 25) {
            adjustSuggestedStreamVolume(-1, Integer.MIN_VALUE, 4101, str, str2, Binder.getCallingUid(), Binder.getCallingPid(), true, i2);
            return;
        }
        if (keyCode == 164) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                adjustSuggestedStreamVolume(101, Integer.MIN_VALUE, 4101, str, str2, Binder.getCallingUid(), Binder.getCallingPid(), true, 0);
                return;
            }
            return;
        }
        Log.e("AS.AudioService", "Invalid key code " + keyEvent.getKeyCode() + " sent by " + str);
    }

    public void setNavigationRepeatSoundEffectsEnabled(boolean z) {
        this.mNavigationRepeatSoundEffectsEnabled = z;
    }

    public boolean areNavigationRepeatSoundEffectsEnabled() {
        return this.mNavigationRepeatSoundEffectsEnabled;
    }

    public void setHomeSoundEffectEnabled(boolean z) {
        this.mHomeSoundEffectEnabled = z;
    }

    public boolean isHomeSoundEffectEnabled() {
        return this.mHomeSoundEffectEnabled;
    }

    public final void adjustSuggestedStreamVolume(int i, int i2, int i3, String str, String str2, int i4, int i5, boolean z, int i6) {
        int activeStreamType;
        boolean wasStreamActiveRecently;
        int i7;
        Log.d("AS.AudioService", "adjustSuggestedStreamVolume() stream=" + i2 + ", flags=" + i3 + ", caller=" + str2 + ", volControlStream=" + this.mVolumeControlStream + ", userSelect=" + this.mUserSelectedVolumeControlStream);
        if (i != 0) {
            sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(0, i2, i, i3, str + "/" + str2 + " uid:" + i4));
        }
        boolean notifyExternalVolumeController = notifyExternalVolumeController(i);
        new MediaMetrics.Item("audio.service.adjustSuggestedStreamVolume").setUid(Binder.getCallingUid()).set(MediaMetrics.Property.CALLING_PACKAGE, str).set(MediaMetrics.Property.CLIENT_NAME, str2).set(MediaMetrics.Property.DIRECTION, i > 0 ? INetd.IF_STATE_UP : INetd.IF_STATE_DOWN).set(MediaMetrics.Property.EXTERNAL, notifyExternalVolumeController ? "yes" : "no").set(MediaMetrics.Property.FLAGS, Integer.valueOf(i3)).record();
        if (notifyExternalVolumeController) {
            return;
        }
        synchronized (this.mForceControlStreamLock) {
            if (this.mUserSelectedVolumeControlStream) {
                activeStreamType = this.mVolumeControlStream;
            } else {
                activeStreamType = getActiveStreamType(i2);
                if (activeStreamType != 2 && activeStreamType != 5) {
                    wasStreamActiveRecently = this.mAudioSystem.isStreamActive(activeStreamType, 0);
                    if (!wasStreamActiveRecently && (i7 = this.mVolumeControlStream) != -1) {
                        activeStreamType = i7;
                    }
                }
                wasStreamActiveRecently = wasStreamActiveRecently(activeStreamType, 0);
                if (!wasStreamActiveRecently) {
                    activeStreamType = i7;
                }
            }
        }
        isMuteAdjust(i);
        ensureValidStreamType(activeStreamType);
        int i8 = mStreamVolumeAlias[activeStreamType];
        adjustStreamVolume(activeStreamType, i, i3, str, str2, i4, i5, null, z, i6);
    }

    public final boolean notifyExternalVolumeController(int i) {
        IAudioPolicyCallback iAudioPolicyCallback;
        synchronized (this.mExtVolumeControllerLock) {
            iAudioPolicyCallback = this.mExtVolumeController;
        }
        if (iAudioPolicyCallback == null) {
            return false;
        }
        sendMsg(this.mAudioHandler, 22, 2, i, 0, iAudioPolicyCallback, 0);
        return true;
    }

    public void adjustStreamVolume(int i, int i2, int i3, String str) {
        adjustStreamVolumeWithAttribution(i, i2, i3, str, null);
    }

    public void adjustStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) {
        if (i == 10 && !canChangeAccessibilityVolume()) {
            Log.w("AS.AudioService", "Trying to call adjustStreamVolume() for a11y withoutCHANGE_ACCESSIBILITY_VOLUME / callingPackage=" + str);
            return;
        }
        AudioServiceEvents$VolumeEvent audioServiceEvents$VolumeEvent = new AudioServiceEvents$VolumeEvent(1, i, i2, i3, str);
        sVolumeLogger.enqueue(audioServiceEvents$VolumeEvent);
        if (isMuteAdjust(i2)) {
            sMuteLogger.enqueue(audioServiceEvents$VolumeEvent);
        }
        adjustStreamVolume(i, i2, i3, str, str, Binder.getCallingUid(), Binder.getCallingPid(), str2, callingHasAudioSettingsPermission(), 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x049c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x04f2  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x052b  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0451  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x055e  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0599 A[Catch: all -> 0x05e7, TRY_LEAVE, TryCatch #2 {, blocks: (B:248:0x0561, B:250:0x0565, B:254:0x056e, B:258:0x0575, B:272:0x0599, B:280:0x05ce, B:286:0x05d3, B:287:0x05d6, B:295:0x05e0, B:297:0x05e5, B:279:0x05a5, B:282:0x05bc, B:283:0x05c1, B:284:0x05c6), top: B:247:0x0561, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:324:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x02c6  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x02b5  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x02a4  */
    /* JADX WARN: Type inference failed for: r5v49 */
    /* JADX WARN: Type inference failed for: r5v50 */
    /* JADX WARN: Type inference failed for: r5v51, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v63 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void adjustStreamVolume(int i, int i2, int i3, String str, String str2, int i4, int i5, String str3, boolean z, int i6) {
        int i7;
        VolumeStreamState volumeStreamState;
        int i8;
        int i9;
        boolean z2;
        int rescaleStep;
        int i10;
        int i11;
        int i12;
        boolean z3;
        int i13;
        GoodCatchManager goodCatchManager;
        int i14;
        int i15;
        boolean z4;
        boolean z5;
        int index;
        ?? r5;
        AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo;
        KeyguardManager keyguardManager;
        if (Rune.SEC_AUDIO_FIXED_SCO_VOLUME && i == 6 && !this.mDeviceBroker.isRemoteVolumeControlSupported()) {
            Log.w("AS.AudioService", "adjustStreamVolume() connected BT doesn't support Remote Volume Control");
            i7 = 0;
        } else {
            i7 = i2;
        }
        if (this.mUseFixedVolume) {
            return;
        }
        Log.d("AS.AudioService", "adjustStreamVolume() stream=" + i + ", dir=" + i7 + ", flags=" + i3 + ", caller=" + str2);
        ensureValidDirection(i7);
        ensureValidStreamType(i);
        boolean isMuteAdjust = isMuteAdjust(i7);
        if (!isMuteAdjust || isStreamAffectedByMute(mStreamVolumeAlias[i])) {
            if (isMuteAdjust && ((i == 0 || i == 6) && this.mContext.checkPermission("android.permission.MODIFY_PHONE_STATE", i5, i4) != 0)) {
                Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial: adjustStreamVolume from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
                return;
            }
            if (i == 11 && this.mContext.checkPermission("android.permission.MODIFY_AUDIO_ROUTING", i5, i4) != 0) {
                Log.w("AS.AudioService", "MODIFY_AUDIO_ROUTING Permission Denial: adjustStreamVolume from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
                return;
            }
            int i16 = mStreamVolumeAlias[i];
            VolumeStreamState volumeStreamState2 = this.mStreamStates[i16];
            int deviceForStream = getDeviceForStream(i16);
            if ((i == 11 || i == 10) && (isA2dpAbsoluteVolumeDevice(deviceForStream) || AudioSystem.isLeAudioDeviceType(deviceForStream))) {
                Log.w("AS.AudioService", "Change to music stream when bluetooth ABS volume is supported");
                volumeStreamState = this.mStreamStates[3];
                i16 = 3;
                i8 = 3;
            } else {
                volumeStreamState = volumeStreamState2;
                i8 = i;
            }
            int index2 = volumeStreamState.getIndex(deviceForStream);
            if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(deviceForStream)) || AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(deviceForStream)) || (i3 & 64) == 0) {
                int uid = i4 == 1000 ? UserHandle.getUid(getCurrentUserId(), UserHandle.getAppId(i4)) : i4;
                if (checkNoteAppOp(STREAM_VOLUME_OPS[i16], uid, str, str3)) {
                    int updateVolumePanelFlags = updateVolumePanelFlags(i16, i7, i3);
                    VolumeStreamState volumeStreamState3 = volumeStreamState;
                    int i17 = i16;
                    int i18 = i8;
                    int i19 = uid;
                    boolean z6 = isMuteAdjust;
                    if (skipAdjustStreamVolume(i8, i7, updateVolumePanelFlags, str, str2, str3, i19, deviceForStream)) {
                        return;
                    }
                    if (i18 == 3 && this.mMediaSessionServiceInternal.isAudioMirroring()) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (this.mMediaSessionServiceInternal.adjustMirroringVolume(i7, updateVolumePanelFlags, str2, i19, i5)) {
                                Log.d("AS.AudioService", "adjusting " + str2 + " mirroring volume");
                                return;
                            }
                        } finally {
                        }
                    }
                    boolean booleanValue = ((Boolean) this.mFMRadioRecordingChecker.runOrSkip()).booleanValue();
                    if (Rune.SEC_AUDIO_FM_RADIO_RECORDING && booleanValue) {
                        if ((updateVolumePanelFlags & 1) != 0 && (keyguardManager = this.mKeyguardManager) != null && keyguardManager.isKeyguardLocked()) {
                            updateVolumePanelFlags &= -2;
                        }
                        int i20 = updateVolumePanelFlags;
                        Log.w("AS.AudioService", "adjustStreamVolume - FM Recording... change adjustVolume value(false)");
                        if (i20 == 33554432 || i20 == 4113) {
                            return;
                        }
                        if (i20 == 0 && i18 != 3) {
                            return;
                        }
                        i9 = i17;
                        if ((i20 & 1) == 0 || i9 != 3) {
                            updateVolumePanelFlags = i20;
                        } else {
                            updateVolumePanelFlags = i20 & (-2);
                            Log.d("AS.AudioService", "adjustStreamVolume - FM Recording... block Media volume bar");
                        }
                        z2 = false;
                    } else {
                        i9 = i17;
                        z2 = true;
                    }
                    this.mSoundDoseHelper.invalidatPendingVolumeCommand();
                    int i21 = updateVolumePanelFlags & (-33);
                    if (i9 == 3 && isFixedVolumeDevice(deviceForStream)) {
                        i21 |= 32;
                        rescaleStep = this.mSoundDoseHelper.getSafeMediaVolumeIndex(deviceForStream);
                        if (rescaleStep < 0) {
                            rescaleStep = volumeStreamState3.getMaxIndex();
                        }
                        if (index2 != 0) {
                            i11 = rescaleStep;
                            i10 = i11;
                            if ((i21 & 2) == 0 || isUiSoundsStreamType(i9)) {
                                if (getRingerModeInternal() == 1) {
                                    i21 &= -17;
                                }
                                int i22 = i21;
                                i12 = deviceForStream;
                                int checkForRingerModeChange = checkForRingerModeChange(i10, i7, i11, volumeStreamState3.mIsMuted, str, i22);
                                boolean z7 = (checkForRingerModeChange & 1) == 0;
                                int i23 = (checkForRingerModeChange & 128) == 0 ? i22 | 128 : i22;
                                boolean z8 = z7;
                                i21 = (checkForRingerModeChange & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) == 0 ? i23 | IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES : i23;
                                z2 = z8;
                            } else if (isStreamMutedByRingerOrZenMode(i9) && volumeStreamState3.mIsMuted && (i7 == 101 || i7 == 100 || i7 == 1)) {
                                i12 = deviceForStream;
                                z2 = false;
                            } else {
                                i12 = deviceForStream;
                            }
                            z3 = volumeAdjustmentAllowedByDnd(i9, i21) ? false : z2;
                            int index3 = this.mStreamStates[i18].getIndex(i12);
                            boolean z9 = (i9 != 3 && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i12)) && (i21 & 64) == 0) ? !this.mDeviceBroker.skipSendingAVRCPVolume(i7) : true;
                            if (isStreamMute(i18)) {
                                i13 = 101;
                            } else {
                                i13 = 101;
                                if (i7 == 100 || i7 == 101) {
                                    index3 = 0;
                                }
                            }
                            if (isAbsoluteVolumeDevice(i12) && (i21 & IInstalld.FLAG_FORCE) == 0) {
                                absoluteVolumeDeviceInfo = (AbsoluteVolumeDeviceInfo) this.mAbsoluteVolumeDeviceInfoMap.get(Integer.valueOf(i12));
                                if (absoluteVolumeDeviceInfo.mHandlesVolumeAdjustment) {
                                    dispatchAbsoluteVolumeAdjusted(i18, absoluteVolumeDeviceInfo, index3, i7, i6);
                                    return;
                                }
                            }
                            if (z3 && i7 != 0 && i6 != 2) {
                                this.mAudioHandler.removeMessages(18);
                                z4 = z6;
                                if (!z4 && !this.mFullVolumeDevices.contains(Integer.valueOf(i12))) {
                                    if (i7 == i13) {
                                        r5 = !volumeStreamState3.mIsMuted;
                                    } else {
                                        r5 = i7 == -100 ? 1 : 0;
                                    }
                                    muteAliasStreams(i9, r5);
                                    if (i18 == 3) {
                                        sendBroadcastToSoundEventReceiver(2, r5, str2);
                                    }
                                } else if (i7 != 1 && this.mSoundDoseHelper.raiseVolumeDisplaySafeMediaVolume(i9, i10 + i11, i12, i21)) {
                                    Log.e("AS.AudioService", "adjustStreamVolume() safe volume index = " + index3);
                                    if (Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME && !this.mExt.isCoverOpen()) {
                                        this.mExt.setCoverSafetyVolume(true);
                                    }
                                } else if (!Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME && i7 == -1 && this.mVolumeController.isSafeVolumeDialogShowing()) {
                                    this.mVolumeController.postDisplaySafeVolumeWarning(134217728);
                                    i21 &= -2;
                                } else if (!isFullVolumeDevice(i12) && (volumeStreamState3.adjustIndex(i7 * i11, i12, str2, z) || volumeStreamState3.mIsMuted)) {
                                    if (volumeStreamState3.mIsMuted) {
                                        z6 = z4;
                                    } else if (i7 == 1) {
                                        synchronized (this.mSettingsLock) {
                                            if (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION || i9 != 5) {
                                                int i24 = 0;
                                                while (true) {
                                                    VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                                                    if (i24 >= volumeStreamStateArr.length) {
                                                        break;
                                                    }
                                                    if (i9 == mStreamVolumeAlias[i24]) {
                                                        VolumeStreamState volumeStreamState4 = volumeStreamStateArr[i24];
                                                        StringBuilder sb = new StringBuilder();
                                                        z5 = z4;
                                                        sb.append("adjustStreamVolume: ringer_mode: ");
                                                        sb.append(this.mRingerMode);
                                                        volumeStreamState4.mute(false, sb.toString());
                                                    } else {
                                                        z5 = z4;
                                                    }
                                                    i24++;
                                                    z4 = z5;
                                                }
                                            } else if (this.mRingerMode == 2) {
                                                volumeStreamState3.mute(false, "adjustStreamVolume: ringer_mode: " + this.mRingerMode);
                                            }
                                            z6 = z4;
                                        }
                                    } else {
                                        z6 = z4;
                                        if (i7 == -1 && this.mIsSingleVolume) {
                                            sendMsg(this.mAudioHandler, 18, 2, i9, i21, null, 350);
                                        }
                                    }
                                    if (Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME && this.mExt.isCoverSafetyVolume() && i7 == -1 && index3 == this.mSoundDoseHelper.safeMediaVolumeIndex(i12)) {
                                        Log.i("AS.AudioService", "Remove the SafeVolumeWarning when cover is closed");
                                        this.mVolumeController.postDisplaySafeVolumeWarning(134217728);
                                        this.mExt.setCoverSafetyVolume(false);
                                    }
                                    sendMsg(this.mAudioHandler, 0, 2, i12, 0, volumeStreamState3, 0);
                                    index = this.mStreamStates[i18].getIndex(i12);
                                    if (i9 != 3 && z9 && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i12)) && (i21 & 64) == 0) {
                                        Log.d("AS.AudioService", "adjustStreamVolume: postSetAvrcpAbsoluteVolumeIndex index=" + index + "stream=" + i18);
                                        this.mDeviceBroker.postSetAvrcpAbsoluteVolumeIndex(index);
                                    } else if (isAbsoluteVolumeDevice(i12) && (i21 & IInstalld.FLAG_FORCE) == 0) {
                                        dispatchAbsoluteVolumeChanged(i18, (AbsoluteVolumeDeviceInfo) this.mAbsoluteVolumeDeviceInfoMap.get(Integer.valueOf(i12)), index);
                                    }
                                    if (AudioSystem.isLeAudioDeviceType(i12) && i18 == getBluetoothContextualVolumeStream() && (i21 & 64) == 0) {
                                        Log.d("AS.AudioService", "adjustStreamVolume postSetLeAudioVolumeIndex index=" + index + " stream=" + i18);
                                        this.mDeviceBroker.postSetLeAudioVolumeIndex(index, this.mStreamStates[i18].getMaxIndex(), i18);
                                    }
                                    if (i12 == 134217728 && i18 == getBluetoothContextualVolumeStream()) {
                                        Log.d("AS.AudioService", "adjustStreamVolume postSetHearingAidVolumeIndex index=" + index + " stream=" + i18);
                                        this.mDeviceBroker.postSetHearingAidVolumeIndex(index, i18);
                                    }
                                }
                                z6 = z4;
                                index = this.mStreamStates[i18].getIndex(i12);
                                if (i9 != 3) {
                                }
                                if (isAbsoluteVolumeDevice(i12)) {
                                    dispatchAbsoluteVolumeChanged(i18, (AbsoluteVolumeDeviceInfo) this.mAbsoluteVolumeDeviceInfoMap.get(Integer.valueOf(i12)), index);
                                }
                                if (AudioSystem.isLeAudioDeviceType(i12)) {
                                    Log.d("AS.AudioService", "adjustStreamVolume postSetLeAudioVolumeIndex index=" + index + " stream=" + i18);
                                    this.mDeviceBroker.postSetLeAudioVolumeIndex(index, this.mStreamStates[i18].getMaxIndex(), i18);
                                }
                                if (i12 == 134217728) {
                                    Log.d("AS.AudioService", "adjustStreamVolume postSetHearingAidVolumeIndex index=" + index + " stream=" + i18);
                                    this.mDeviceBroker.postSetHearingAidVolumeIndex(index, i18);
                                }
                            }
                            int index4 = this.mStreamStates[i18].getIndex(i12);
                            if (z3) {
                                synchronized (this.mHdmiClientLock) {
                                    if (this.mHdmiManager != null) {
                                        HdmiClient hdmiClient = this.mHdmiPlaybackClient;
                                        HdmiClient hdmiClient2 = this.mHdmiTvClient;
                                        if (hdmiClient2 != null) {
                                            hdmiClient = hdmiClient2;
                                        }
                                        if (hdmiClient != null && this.mHdmiCecVolumeControlEnabled) {
                                            if (i9 != 3) {
                                                i14 = 3;
                                                if (i9 == i14 && (index3 != index4 || z6)) {
                                                    maybeSendSystemAudioStatusCommand(z6);
                                                }
                                            } else if (isFullVolumeDevice(i12)) {
                                                if (i7 != -100) {
                                                    if (i7 == -1) {
                                                        i15 = 25;
                                                    } else if (i7 == 1) {
                                                        i15 = 24;
                                                    } else if (i7 != 100 && i7 != 101) {
                                                        i15 = 0;
                                                    }
                                                    if (i15 != 0) {
                                                        long clearCallingIdentity2 = Binder.clearCallingIdentity();
                                                        try {
                                                            if (i6 == 0) {
                                                                hdmiClient.sendVolumeKeyEvent(i15, true);
                                                                hdmiClient.sendVolumeKeyEvent(i15, false);
                                                            } else if (i6 == 1) {
                                                                hdmiClient.sendVolumeKeyEvent(i15, true);
                                                            } else if (i6 == 2) {
                                                                hdmiClient.sendVolumeKeyEvent(i15, false);
                                                            } else {
                                                                Log.e("AS.AudioService", "Invalid keyEventMode " + i6);
                                                            }
                                                            Binder.restoreCallingIdentity(clearCallingIdentity2);
                                                        } finally {
                                                        }
                                                    }
                                                }
                                                i15 = FrameworkStatsLog.f636xd07885aa;
                                                if (i15 != 0) {
                                                }
                                            }
                                        }
                                        i14 = 3;
                                        if (i9 == i14) {
                                            maybeSendSystemAudioStatusCommand(z6);
                                        }
                                    }
                                }
                            }
                            sendVolumeUpdate(i18, index3, index4, i21, i12);
                            goodCatchManager = this.mGoodCatchManager;
                            if (goodCatchManager == null && goodCatchManager.isMediaVolumeCatchEnabled() && i18 == 3) {
                                if ((z3 && index3 > 0 && index4 == 0) || ((i7 == 101 && this.mStreamStates[i18].mIsMuted) || i7 == -100)) {
                                    this.mGoodCatchManager.updateMediaVolume(str, this.mContext.getPackageName());
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    } else {
                        rescaleStep = rescaleStep(10, i18, i9);
                    }
                    i10 = index2;
                    i11 = rescaleStep;
                    if ((i21 & 2) == 0) {
                    }
                    if (getRingerModeInternal() == 1) {
                    }
                    int i222 = i21;
                    i12 = deviceForStream;
                    int checkForRingerModeChange2 = checkForRingerModeChange(i10, i7, i11, volumeStreamState3.mIsMuted, str, i222);
                    if ((checkForRingerModeChange2 & 1) == 0) {
                    }
                    if ((checkForRingerModeChange2 & 128) == 0) {
                    }
                    boolean z82 = z7;
                    i21 = (checkForRingerModeChange2 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) == 0 ? i23 | IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES : i23;
                    z2 = z82;
                    if (volumeAdjustmentAllowedByDnd(i9, i21)) {
                    }
                    int index32 = this.mStreamStates[i18].getIndex(i12);
                    if (i9 != 3) {
                    }
                    if (isStreamMute(i18)) {
                    }
                    if (isAbsoluteVolumeDevice(i12)) {
                        absoluteVolumeDeviceInfo = (AbsoluteVolumeDeviceInfo) this.mAbsoluteVolumeDeviceInfoMap.get(Integer.valueOf(i12));
                        if (absoluteVolumeDeviceInfo.mHandlesVolumeAdjustment) {
                        }
                    }
                    if (z3) {
                        this.mAudioHandler.removeMessages(18);
                        z4 = z6;
                        if (!z4) {
                        }
                        if (i7 != 1) {
                        }
                        if (!Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME) {
                        }
                        if (!isFullVolumeDevice(i12)) {
                            if (volumeStreamState3.mIsMuted) {
                            }
                            if (Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME) {
                                Log.i("AS.AudioService", "Remove the SafeVolumeWarning when cover is closed");
                                this.mVolumeController.postDisplaySafeVolumeWarning(134217728);
                                this.mExt.setCoverSafetyVolume(false);
                            }
                            sendMsg(this.mAudioHandler, 0, 2, i12, 0, volumeStreamState3, 0);
                            index = this.mStreamStates[i18].getIndex(i12);
                            if (i9 != 3) {
                            }
                            if (isAbsoluteVolumeDevice(i12)) {
                            }
                            if (AudioSystem.isLeAudioDeviceType(i12)) {
                            }
                            if (i12 == 134217728) {
                            }
                        }
                        z6 = z4;
                        index = this.mStreamStates[i18].getIndex(i12);
                        if (i9 != 3) {
                        }
                        if (isAbsoluteVolumeDevice(i12)) {
                        }
                        if (AudioSystem.isLeAudioDeviceType(i12)) {
                        }
                        if (i12 == 134217728) {
                        }
                    }
                    int index42 = this.mStreamStates[i18].getIndex(i12);
                    if (z3) {
                    }
                    sendVolumeUpdate(i18, index32, index42, i21, i12);
                    goodCatchManager = this.mGoodCatchManager;
                    if (goodCatchManager == null) {
                    }
                }
            }
        }
    }

    public final void muteAliasStreams(int i, final boolean z) {
        synchronized (this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                while (true) {
                    VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                    if (i2 < volumeStreamStateArr.length) {
                        VolumeStreamState volumeStreamState = volumeStreamStateArr[i2];
                        if (i == mStreamVolumeAlias[i2] && volumeStreamState.isMutable() && ((!this.mCameraSoundForced || volumeStreamState.getStreamType() != 7) && volumeStreamState.mute(z, false, "muteAliasStreams"))) {
                            arrayList.add(Integer.valueOf(i2));
                        }
                        i2++;
                    } else {
                        arrayList.forEach(new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda18
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AudioService.this.lambda$muteAliasStreams$10(z, (Integer) obj);
                            }
                        });
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$muteAliasStreams$10(boolean z, Integer num) {
        this.mStreamStates[num.intValue()].doMute();
        broadcastMuteSetting(num.intValue(), z);
    }

    public final void broadcastMuteSetting(int i, boolean z) {
        Intent intent = new Intent("android.media.STREAM_MUTE_CHANGED_ACTION");
        intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", i);
        intent.putExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", z);
        sendBroadcastToAll(intent, null);
    }

    public final void onUnmuteStream(int i, int i2) {
        boolean mute;
        synchronized (this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                VolumeStreamState volumeStreamState = this.mStreamStates[i];
                mute = volumeStreamState.mute(false, "onUnmuteStream");
                int deviceForStream = getDeviceForStream(i);
                int index = volumeStreamState.getIndex(deviceForStream);
                sendVolumeUpdate(i, index, index, i2, deviceForStream);
            }
            if (i == 3 && mute) {
                synchronized (this.mHdmiClientLock) {
                    maybeSendSystemAudioStatusCommand(true);
                }
            }
        }
    }

    public final void maybeSendSystemAudioStatusCommand(boolean z) {
        if (this.mHdmiAudioSystemClient != null && this.mHdmiSystemAudioSupported && this.mHdmiCecVolumeControlEnabled) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mHdmiAudioSystemClient.sendReportAudioStatusCecCommand(z, getStreamVolume(3), getStreamMaxVolume(3), isStreamMute(3));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final int getNewRingerMode(int i, int i2, int i3) {
        int i4;
        if (this.mIsSingleVolume) {
            return getRingerModeExternal();
        }
        if ((i3 & 2) == 0 && i != getUiSoundsStreamType()) {
            return getRingerModeExternal();
        }
        if (i2 != 0) {
            return 2;
        }
        if (this.mHasVibrator) {
            i4 = 1;
        } else {
            if (!this.mVolumePolicy.volumeDownToEnterSilent) {
                return 2;
            }
            i4 = 0;
        }
        return i4;
    }

    public final boolean isAndroidNPlus(String str) {
        try {
            return this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, UserHandle.getUserId(Binder.getCallingUid())).targetSdkVersion >= 24;
        } catch (PackageManager.NameNotFoundException unused) {
            return true;
        }
    }

    public final boolean wouldToggleZenMode(int i) {
        if (getRingerModeExternal() != 0 || i == 0) {
            return getRingerModeExternal() != 0 && i == 0;
        }
        return true;
    }

    public void onSetStreamVolume(int i, int i2, int i3, int i4, String str, boolean z, boolean z2) {
        int i5 = mStreamVolumeAlias[i];
        if ((i3 & 2) != 0 || i5 == getUiSoundsStreamType()) {
            str = "AS.AudioService.onSetStreamVolume(" + str + ")";
            setRingerMode(getNewRingerMode(i5, i2, i3), str, false);
        }
        setStreamVolumeInt(i5, i2, i4, false, str, z);
        if (i2 != 0) {
            synchronized (this.mSettingsLock) {
                int i6 = 0;
                while (true) {
                    VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                    if (i6 < volumeStreamStateArr.length) {
                        if (i5 == mStreamVolumeAlias[i6]) {
                            volumeStreamStateArr[i6].mute(false, "onSetStreamVolume");
                        }
                        i6++;
                    }
                }
            }
        }
    }

    public final void enforceModifyAudioRoutingPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING permission");
        }
    }

    public final void enforceQueryStateOrModifyRoutingPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.QUERY_AUDIO_STATE") != 0) {
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING or QUERY_AUDIO_STATE permissions");
        }
    }

    public void setVolumeGroupVolumeIndex(int i, int i2, int i3, String str, String str2) {
        super.setVolumeGroupVolumeIndex_enforcePermission();
        SparseArray sparseArray = sVolumeGroupStates;
        if (sparseArray.indexOfKey(i) < 0) {
            Log.e("AS.AudioService", ": no volume group found for id " + i);
            return;
        }
        VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray.get(i);
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(8, volumeGroupState.name(), i2, i3, str + ", user " + getCurrentUserId()));
        volumeGroupState.setVolumeIndex(i2, i3);
        for (int i4 : volumeGroupState.getLegacyStreamTypes()) {
            try {
                ensureValidStreamType(i4);
                setStreamVolume(i4, i2, i3, null, str, str, str2, Binder.getCallingUid(), true);
            } catch (IllegalArgumentException unused) {
                Log.d("AS.AudioService", "volume group " + i + " has internal streams (" + i4 + "), do not change associated stream volume");
            }
        }
    }

    public int getVolumeGroupVolumeIndex(int i) {
        int volumeIndex;
        super.getVolumeGroupVolumeIndex_enforcePermission();
        synchronized (VolumeStreamState.class) {
            SparseArray sparseArray = sVolumeGroupStates;
            if (sparseArray.indexOfKey(i) < 0) {
                throw new IllegalArgumentException("No volume group for id " + i);
            }
            VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray.get(i);
            volumeIndex = volumeGroupState.isMuted() ? 0 : volumeGroupState.getVolumeIndex();
        }
        return volumeIndex;
    }

    public int getVolumeGroupMaxVolumeIndex(int i) {
        int maxIndex;
        super.getVolumeGroupMaxVolumeIndex_enforcePermission();
        synchronized (VolumeStreamState.class) {
            SparseArray sparseArray = sVolumeGroupStates;
            if (sparseArray.indexOfKey(i) < 0) {
                throw new IllegalArgumentException("No volume group for id " + i);
            }
            maxIndex = ((VolumeGroupState) sparseArray.get(i)).getMaxIndex();
        }
        return maxIndex;
    }

    public int getVolumeGroupMinVolumeIndex(int i) {
        int minIndex;
        super.getVolumeGroupMinVolumeIndex_enforcePermission();
        synchronized (VolumeStreamState.class) {
            SparseArray sparseArray = sVolumeGroupStates;
            if (sparseArray.indexOfKey(i) < 0) {
                throw new IllegalArgumentException("No volume group for id " + i);
            }
            minIndex = ((VolumeGroupState) sparseArray.get(i)).getMinIndex();
        }
        return minIndex;
    }

    public void setDeviceVolume(VolumeInfo volumeInfo, final AudioDeviceAttributes audioDeviceAttributes, final String str) {
        super.setDeviceVolume_enforcePermission();
        Objects.requireNonNull(volumeInfo);
        Objects.requireNonNull(audioDeviceAttributes);
        Objects.requireNonNull(str);
        if (!volumeInfo.hasStreamType()) {
            Log.e("AS.AudioService", "Unsupported non-stream type based VolumeInfo", new Exception());
            return;
        }
        final int volumeIndex = volumeInfo.getVolumeIndex();
        if (volumeIndex == -100 && !volumeInfo.hasMuteCommand()) {
            throw new IllegalArgumentException("changing device volume requires a volume index or mute command");
        }
        this.mAudioSystem.clearRoutingCache();
        final int deviceForStream = getDeviceForStream(volumeInfo.getStreamType());
        boolean z = deviceForStream == audioDeviceAttributes.getInternalType();
        EventLogger eventLogger = sVolumeLogger;
        final int streamType = volumeInfo.getStreamType();
        final boolean z2 = z;
        eventLogger.enqueue(new EventLogger.Event(streamType, volumeIndex, audioDeviceAttributes, deviceForStream, str, z2) { // from class: com.android.server.audio.AudioServiceEvents$DeviceVolumeEvent
            public final String mCaller;
            public final String mDeviceAddress;
            public final int mDeviceForStream;
            public final String mDeviceNativeType;
            public final boolean mSkipped;
            public final int mStream;
            public final int mVolIndex;

            {
                this.mStream = streamType;
                this.mVolIndex = volumeIndex;
                String str2 = "0x" + Integer.toHexString(audioDeviceAttributes.getInternalType());
                this.mDeviceNativeType = str2;
                String address = audioDeviceAttributes.getAddress();
                this.mDeviceAddress = address;
                this.mDeviceForStream = deviceForStream;
                this.mCaller = str;
                this.mSkipped = z2;
                new MediaMetrics.Item("audio.volume.event").set(MediaMetrics.Property.EVENT, "setDeviceVolume").set(MediaMetrics.Property.STREAM_TYPE, AudioSystem.streamToString(streamType)).set(MediaMetrics.Property.INDEX, Integer.valueOf(volumeIndex)).set(MediaMetrics.Property.DEVICE, str2).set(MediaMetrics.Property.ADDRESS, address).set(MediaMetrics.Property.CALLING_PACKAGE, str).record();
            }

            @Override // com.android.server.utils.EventLogger.Event
            public String eventToString() {
                StringBuilder sb = new StringBuilder("setDeviceVolume(stream:");
                sb.append(AudioSystem.streamToString(this.mStream));
                sb.append(" index:");
                sb.append(this.mVolIndex);
                sb.append(" device:");
                sb.append(this.mDeviceNativeType);
                sb.append(" addr:");
                sb.append(this.mDeviceAddress);
                sb.append(") from ");
                sb.append(this.mCaller);
                if (this.mSkipped) {
                    sb.append(" skipped [device in use]");
                } else {
                    sb.append(" currDevForStream:Ox");
                    sb.append(Integer.toHexString(this.mDeviceForStream));
                }
                return sb.toString();
            }
        });
        if (z) {
            return;
        }
        if (volumeInfo.hasMuteCommand() && volumeInfo.isMuted() && !isStreamMute(volumeInfo.getStreamType())) {
            setStreamVolumeWithAttributionInt(volumeInfo.getStreamType(), this.mStreamStates[volumeInfo.getStreamType()].getMinIndex(), 0, audioDeviceAttributes, str, null);
            return;
        }
        eventLogger.enqueueAndLog("setDeviceVolume from:" + str + " " + volumeInfo + " " + audioDeviceAttributes, 0, "AS.AudioService");
        if (volumeInfo.getMinVolumeIndex() == -100 || volumeInfo.getMaxVolumeIndex() == -100) {
            int i = volumeIndex * 10;
            if (i < this.mStreamStates[volumeInfo.getStreamType()].getMinIndex() || i > this.mStreamStates[volumeInfo.getStreamType()].getMaxIndex()) {
                throw new IllegalArgumentException("invalid volume index " + volumeIndex + " not between min/max for stream " + volumeInfo.getStreamType());
            }
        } else {
            int minIndex = (this.mStreamStates[volumeInfo.getStreamType()].getMinIndex() + 5) / 10;
            int maxIndex = (this.mStreamStates[volumeInfo.getStreamType()].getMaxIndex() + 5) / 10;
            if (volumeInfo.getMinVolumeIndex() != minIndex || volumeInfo.getMaxVolumeIndex() != maxIndex) {
                volumeIndex = rescaleIndex(volumeIndex, volumeInfo.getMinVolumeIndex(), volumeInfo.getMaxVolumeIndex(), minIndex, maxIndex);
            }
        }
        setStreamVolumeWithAttributionInt(volumeInfo.getStreamType(), volumeIndex, 0, audioDeviceAttributes, str, null);
    }

    public void setStreamVolume(int i, int i2, int i3, String str) {
        setStreamVolumeWithAttribution(i, i2, i3, str, null);
    }

    public void adjustVolumeGroupVolume(int i, int i2, int i3, String str) {
        ensureValidDirection(i2);
        SparseArray sparseArray = sVolumeGroupStates;
        if (sparseArray.indexOfKey(i) < 0) {
            Log.e("AS.AudioService", ": no volume group found for id " + i);
            return;
        }
        VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray.get(i);
        boolean z = false;
        for (int i4 : volumeGroupState.getLegacyStreamTypes()) {
            try {
                ensureValidStreamType(i4);
                if (volumeGroupState.isVssMuteBijective(i4)) {
                    adjustStreamVolume(i4, i2, i3, str);
                    if (isMuteAdjust(i2)) {
                        return;
                    } else {
                        z = true;
                    }
                } else {
                    continue;
                }
            } catch (IllegalArgumentException unused) {
                Log.d("AS.AudioService", "volume group " + i + " has internal streams (" + i4 + "), do not change associated stream volume");
            }
        }
        if (z) {
            return;
        }
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(11, volumeGroupState.name(), i2, i3, str));
        volumeGroupState.adjustVolume(i2, i3);
    }

    public int getLastAudibleVolumeForVolumeGroup(int i) {
        super.getLastAudibleVolumeForVolumeGroup_enforcePermission();
        synchronized (VolumeStreamState.class) {
            SparseArray sparseArray = sVolumeGroupStates;
            if (sparseArray.indexOfKey(i) < 0) {
                Log.e("AS.AudioService", ": no volume group found for id " + i);
                return 0;
            }
            return ((VolumeGroupState) sparseArray.get(i)).getVolumeIndex();
        }
    }

    public boolean isVolumeGroupMuted(int i) {
        synchronized (VolumeStreamState.class) {
            SparseArray sparseArray = sVolumeGroupStates;
            if (sparseArray.indexOfKey(i) < 0) {
                Log.e("AS.AudioService", ": no volume group found for id " + i);
                return false;
            }
            return ((VolumeGroupState) sparseArray.get(i)).isMuted();
        }
    }

    public void setStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) {
        setStreamVolumeWithAttributionInt(i, i2, i3, null, str, str2);
    }

    public void setStreamVolumeWithAttributionInt(int i, int i2, int i3, AudioDeviceAttributes audioDeviceAttributes, String str, String str2) {
        if (i == 10 && !canChangeAccessibilityVolume()) {
            Log.w("AS.AudioService", "Trying to call setStreamVolume() for a11y without CHANGE_ACCESSIBILITY_VOLUME  callingPackage=" + str);
            return;
        }
        if (i == 0 && i2 == 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "Trying to call setStreamVolume() for STREAM_VOICE_CALL and index 0 without MODIFY_PHONE_STATE  callingPackage=" + str);
            return;
        }
        if (i == 11 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            Log.w("AS.AudioService", "Trying to call setStreamVolume() for STREAM_ASSISTANT without MODIFY_AUDIO_ROUTING  callingPackage=" + str);
            return;
        }
        if (audioDeviceAttributes == null) {
            sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i2, i3, str));
        }
        setStreamVolume(i, i2, i3, audioDeviceAttributes, str, str, str2, Binder.getCallingUid(), callingOrSelfHasAudioSettingsPermission());
    }

    public boolean isUltrasoundSupported() {
        super.isUltrasoundSupported_enforcePermission();
        return AudioSystem.isUltrasoundSupported();
    }

    public boolean isHotwordStreamSupported(boolean z) {
        super.isHotwordStreamSupported_enforcePermission();
        try {
            return this.mAudioPolicy.isHotwordStreamSupported(z);
        } catch (IllegalStateException e) {
            Log.e("AS.AudioService", "Suppressing exception calling into AudioPolicy", e);
            return false;
        }
    }

    public final boolean canChangeAccessibilityVolume() {
        synchronized (this.mAccessibilityServiceUidsLock) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.CHANGE_ACCESSIBILITY_VOLUME") == 0) {
                return true;
            }
            if (this.mAccessibilityServiceUids != null) {
                int callingUid = Binder.getCallingUid();
                int i = 0;
                while (true) {
                    int[] iArr = this.mAccessibilityServiceUids;
                    if (i >= iArr.length) {
                        break;
                    }
                    if (iArr[i] == callingUid) {
                        return true;
                    }
                    i++;
                }
            }
            return false;
        }
    }

    public int getBluetoothContextualVolumeStream() {
        return getBluetoothContextualVolumeStream(this.mMode.get());
    }

    public final void onPlaybackConfigChange(List list) {
        Iterator it = list.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it.next();
            int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
            if (audioPlaybackConfiguration.isActive()) {
                if (usage == 2 || usage == 3) {
                    z = true;
                }
                if (usage == 1 || usage == 14) {
                    z2 = true;
                }
            }
        }
        if (this.mVoicePlaybackActive.getAndSet(z) != z) {
            updateHearingAidVolumeOnVoiceActivityUpdate();
        }
        if (this.mMediaPlaybackActive.getAndSet(z2) != z2 && z2) {
            this.mSoundDoseHelper.scheduleMusicActiveCheck();
        }
        updateAudioModeHandlers(list, null);
        this.mDeviceBroker.updateCommunicationRouteClientsActivity(list, null);
    }

    public void updateAudioModeHandlers(List list, List list2) {
        synchronized (this.mDeviceBroker.mSetModeLock) {
            Iterator it = this.mSetModeDeathHandlers.iterator();
            int i = 2;
            int i2 = 6000;
            boolean z = false;
            while (it.hasNext()) {
                SetModeDeathHandler setModeDeathHandler = (SetModeDeathHandler) it.next();
                boolean isActive = setModeDeathHandler.isActive();
                if (list != null) {
                    setModeDeathHandler.setPlaybackActive(false);
                    Iterator it2 = list.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it2.next();
                        int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
                        if (audioPlaybackConfiguration.getClientUid() == setModeDeathHandler.getUid() && (usage == 2 || usage == 3)) {
                            if (audioPlaybackConfiguration.isActive()) {
                                setModeDeathHandler.setPlaybackActive(true);
                                break;
                            }
                        }
                    }
                }
                if (list2 != null) {
                    setModeDeathHandler.setRecordingActive(false);
                    Iterator it3 = list2.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        AudioRecordingConfiguration audioRecordingConfiguration = (AudioRecordingConfiguration) it3.next();
                        if (audioRecordingConfiguration.getClientUid() == setModeDeathHandler.getUid() && !audioRecordingConfiguration.isClientSilenced() && audioRecordingConfiguration.getAudioSource() == 7) {
                            setModeDeathHandler.setRecordingActive(true);
                            break;
                        }
                    }
                }
                if (isActive != setModeDeathHandler.isActive()) {
                    if (setModeDeathHandler.isActive() && setModeDeathHandler == getAudioModeOwnerHandler()) {
                        i = 0;
                        i2 = 0;
                    }
                    z = true;
                }
            }
            if (z) {
                sendMsg(this.mAudioHandler, 36, i, -1, Process.myPid(), this.mContext.getPackageName(), i2);
            }
        }
    }

    public final void onRecordingConfigChange(List list) {
        updateAudioModeHandlers(null, list);
        this.mDeviceBroker.updateCommunicationRouteClientsActivity(null, list);
    }

    public final void dumpAudioMode(PrintWriter printWriter) {
        printWriter.println("\nAudio mode: ");
        printWriter.println("- Requested mode = " + AudioSystem.modeToString(getMode()));
        printWriter.println("- Actual mode = " + AudioSystem.modeToString(this.mMode.get()));
        printWriter.println("- Mode owner: ");
        SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
        if (audioModeOwnerHandler != null) {
            audioModeOwnerHandler.dump(printWriter, -1);
        } else {
            printWriter.println("   None");
        }
        printWriter.println("- Mode owner stack: ");
        if (this.mSetModeDeathHandlers.isEmpty()) {
            printWriter.println("   Empty");
            return;
        }
        for (int i = 0; i < this.mSetModeDeathHandlers.size(); i++) {
            ((SetModeDeathHandler) this.mSetModeDeathHandlers.get(i)).dump(printWriter, i);
        }
    }

    public final void updateHearingAidVolumeOnVoiceActivityUpdate() {
        int bluetoothContextualVolumeStream = getBluetoothContextualVolumeStream();
        int streamVolume = getStreamVolume(bluetoothContextualVolumeStream);
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(6, this.mVoicePlaybackActive.get(), bluetoothContextualVolumeStream, streamVolume));
        this.mDeviceBroker.postSetHearingAidVolumeIndex(streamVolume * 10, bluetoothContextualVolumeStream);
    }

    public void updateAbsVolumeMultiModeDevices(int i, int i2) {
        if (i == i2) {
            return;
        }
        if (i2 == 0 || i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5 || i2 == 6) {
            int bluetoothContextualVolumeStream = getBluetoothContextualVolumeStream(i2);
            int index = this.mStreamStates[bluetoothContextualVolumeStream].getIndex(134217728);
            sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(7, i2, bluetoothContextualVolumeStream, index));
            this.mDeviceBroker.postSetHearingAidVolumeIndex(index, bluetoothContextualVolumeStream);
        }
    }

    public final void setLeAudioVolumeOnModeUpdate(int i, int i2, int i3, int i4, int i5) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                if (!AudioSystem.isLeAudioDeviceType(i2)) {
                    Log.w("AS.AudioService", "setLeAudioVolumeOnModeUpdate ignoring invalid device=" + i2 + ", mode=" + i + ", index=" + i4 + " maxIndex=" + i5 + " streamType=" + i3);
                    break;
                } else {
                    Log.d("AS.AudioService", "setLeAudioVolumeOnModeUpdate postSetLeAudioVolumeIndex device=" + i2 + ", mode=" + i + ", index=" + i4 + " maxIndex=" + i5 + " streamType=" + i3);
                    this.mDeviceBroker.postSetLeAudioVolumeIndex(i4, i5, i3);
                    for (int numStreamTypes = AudioSystem.getNumStreamTypes() + (-1); numStreamTypes >= 0; numStreamTypes--) {
                        setDeviceVolume(this.mStreamStates[numStreamTypes], 536870912);
                    }
                    break;
                }
        }
    }

    public final void setStreamVolume(int i, int i2, int i3, AudioDeviceAttributes audioDeviceAttributes, String str, String str2, String str3, int i4, boolean z) {
        setStreamVolumeWithAttribution(i, i2, i3, audioDeviceAttributes, str, str2, str3, i4, z, 0);
    }

    public final void dispatchAbsoluteVolumeChanged(int i, AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo, int i2) {
        VolumeInfo matchingVolumeInfoForStream = absoluteVolumeDeviceInfo.getMatchingVolumeInfoForStream(i);
        if (matchingVolumeInfoForStream != null) {
            try {
                absoluteVolumeDeviceInfo.mCallback.dispatchDeviceVolumeChanged(absoluteVolumeDeviceInfo.mDevice, new VolumeInfo.Builder(matchingVolumeInfoForStream).setVolumeIndex(rescaleIndex(i2, i, matchingVolumeInfoForStream)).build());
            } catch (RemoteException unused) {
                Log.w("AS.AudioService", "Couldn't dispatch absolute volume behavior volume change");
            }
        }
    }

    public final void dispatchAbsoluteVolumeAdjusted(int i, AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo, int i2, int i3, int i4) {
        VolumeInfo matchingVolumeInfoForStream = absoluteVolumeDeviceInfo.getMatchingVolumeInfoForStream(i);
        if (matchingVolumeInfoForStream != null) {
            try {
                absoluteVolumeDeviceInfo.mCallback.dispatchDeviceVolumeAdjusted(absoluteVolumeDeviceInfo.mDevice, new VolumeInfo.Builder(matchingVolumeInfoForStream).setVolumeIndex(rescaleIndex(i2, i, matchingVolumeInfoForStream)).build(), i3, i4);
            } catch (RemoteException unused) {
                Log.w("AS.AudioService", "Couldn't dispatch absolute volume behavior volume adjustment");
            }
        }
    }

    public final boolean volumeAdjustmentAllowedByDnd(int i, int i2) {
        int zenMode = this.mNm.getZenMode();
        return ((zenMode == 1 || zenMode == 2 || zenMode == 3) && isStreamMutedByRingerOrZenMode(i) && !isUiSoundsStreamType(i) && (i2 & 2) == 0) ? false : true;
    }

    public void forceVolumeControlStream(int i, IBinder iBinder) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            return;
        }
        Log.d("AS.AudioService", String.format("forceVolumeControlStream(%d)", Integer.valueOf(i)));
        synchronized (this.mForceControlStreamLock) {
            if (isMultiSoundOnInternal()) {
                this.mVolumeControllerStream = i;
            }
            if (i == 10003) {
                i = 3;
            }
            if (this.mVolumeControlStream != -1 && i != -1) {
                this.mUserSelectedVolumeControlStream = true;
            }
            this.mVolumeControlStream = i;
            if (i == -1) {
                ForceControlStreamClient forceControlStreamClient = this.mForceControlStreamClient;
                if (forceControlStreamClient != null) {
                    forceControlStreamClient.release();
                    this.mForceControlStreamClient = null;
                }
                this.mUserSelectedVolumeControlStream = false;
            } else {
                ForceControlStreamClient forceControlStreamClient2 = this.mForceControlStreamClient;
                if (forceControlStreamClient2 == null) {
                    this.mForceControlStreamClient = new ForceControlStreamClient(iBinder);
                } else if (forceControlStreamClient2.getBinder() == iBinder) {
                    Log.d("AS.AudioService", "forceVolumeControlStream cb:" + iBinder + " is already linked.");
                } else {
                    this.mForceControlStreamClient.release();
                    this.mForceControlStreamClient = new ForceControlStreamClient(iBinder);
                }
            }
        }
    }

    public class ForceControlStreamClient implements IBinder.DeathRecipient {
        public IBinder mCb;

        public ForceControlStreamClient(IBinder iBinder) {
            if (iBinder != null) {
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    Log.w("AS.AudioService", "ForceControlStreamClient() could not link to " + iBinder + " binder death");
                    iBinder = null;
                }
            }
            this.mCb = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (AudioService.this.mForceControlStreamLock) {
                Log.w("AS.AudioService", "SCO client died");
                if (AudioService.this.mForceControlStreamClient != this) {
                    Log.w("AS.AudioService", "unregistered control stream client died");
                } else {
                    AudioService.this.mForceControlStreamClient = null;
                    AudioService.this.mVolumeControlStream = -1;
                    AudioService.this.mUserSelectedVolumeControlStream = false;
                }
            }
        }

        public void release() {
            IBinder iBinder = this.mCb;
            if (iBinder != null) {
                iBinder.unlinkToDeath(this, 0);
                this.mCb = null;
            }
        }

        public IBinder getBinder() {
            return this.mCb;
        }
    }

    public void sendBroadcastToAll(Intent intent, Bundle bundle) {
        if (this.mSystemServer.isPrivileged()) {
            intent.addFlags(67108864);
            intent.addFlags(268435456);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, null, bundle);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void sendStickyBroadcastToAll(Intent intent) {
        intent.addFlags(268435456);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
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

    public void sendVolumeUpdate(int i, int i2, int i3, int i4, int i5) {
        int i6 = mStreamVolumeAlias[i];
        if (i6 == 3 && isFullVolumeDevice(i5)) {
            i4 &= -2;
        }
        int updateFlagsForSamsungVolume = updateFlagsForSamsungVolume(i6, i4, i3, i5);
        this.mVolumeController.postVolumeChanged(i6, updateFlagsForSamsungVolume);
        sendVolumeChangedIntent(i6, i2, i3, updateFlagsForSamsungVolume, i5);
    }

    public final int updateFlagsForTvPlatform(int i) {
        synchronized (this.mHdmiClientLock) {
            if (this.mHdmiTvClient != null && this.mHdmiSystemAudioSupported && this.mHdmiCecVolumeControlEnabled) {
                i &= -2;
            }
        }
        return i;
    }

    public final void sendMasterMuteUpdate(boolean z, int i) {
        this.mVolumeController.postMasterMuteChanged(updateFlagsForTvPlatform(i));
        broadcastMasterMuteStatus(z);
    }

    public final void broadcastMasterMuteStatus(boolean z) {
        Intent intent = new Intent("android.media.MASTER_MUTE_CHANGED_ACTION");
        intent.putExtra("android.media.EXTRA_MASTER_VOLUME_MUTED", z);
        intent.addFlags(603979776);
        sendStickyBroadcastToAll(intent);
    }

    public final void setStreamVolumeInt(int i, int i2, int i3, boolean z, String str, boolean z2) {
        if (isFullVolumeDevice(i3)) {
            return;
        }
        VolumeStreamState volumeStreamState = this.mStreamStates[i];
        if (volumeStreamState.setIndex(i2, i3, str, z2) || z) {
            sendMsg(this.mAudioHandler, 0, 2, i3, 0, volumeStreamState, 0);
        }
    }

    public boolean isStreamMute(int i) {
        boolean z;
        if (i == Integer.MIN_VALUE) {
            i = getActiveStreamType(i);
        }
        synchronized (VolumeStreamState.class) {
            ensureValidStreamType(i);
            z = this.mStreamStates[i].mIsMuted;
        }
        return z;
    }

    public class RmtSbmxFullVolDeathHandler implements IBinder.DeathRecipient {
        public IBinder mICallback;

        public RmtSbmxFullVolDeathHandler(IBinder iBinder) {
            this.mICallback = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException e) {
                Log.e("AS.AudioService", "can't link to death", e);
            }
        }

        public boolean isHandlerFor(IBinder iBinder) {
            return this.mICallback.equals(iBinder);
        }

        public void forget() {
            try {
                this.mICallback.unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
                Log.e("AS.AudioService", "error unlinking to death", e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.w("AS.AudioService", "Recorder with remote submix at full volume died " + this.mICallback);
            AudioService.this.forceRemoteSubmixFullVolume(false, this.mICallback);
        }
    }

    public final boolean discardRmtSbmxFullVolDeathHandlerFor(IBinder iBinder) {
        Iterator it = this.mRmtSbmxFullVolDeathHandlers.iterator();
        while (it.hasNext()) {
            RmtSbmxFullVolDeathHandler rmtSbmxFullVolDeathHandler = (RmtSbmxFullVolDeathHandler) it.next();
            if (rmtSbmxFullVolDeathHandler.isHandlerFor(iBinder)) {
                rmtSbmxFullVolDeathHandler.forget();
                this.mRmtSbmxFullVolDeathHandlers.remove(rmtSbmxFullVolDeathHandler);
                return true;
            }
        }
        return false;
    }

    public final boolean hasRmtSbmxFullVolDeathHandlerFor(IBinder iBinder) {
        Iterator it = this.mRmtSbmxFullVolDeathHandlers.iterator();
        while (it.hasNext()) {
            if (((RmtSbmxFullVolDeathHandler) it.next()).isHandlerFor(iBinder)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0077 A[Catch: all -> 0x0084, TryCatch #0 {, blocks: (B:13:0x001f, B:15:0x0025, B:17:0x0035, B:19:0x0039, B:20:0x004c, B:23:0x0077, B:24:0x0082, B:27:0x0052, B:29:0x0058, B:31:0x005c, B:33:0x0061), top: B:11:0x001d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void forceRemoteSubmixFullVolume(boolean z, IBinder iBinder) {
        int i;
        if (iBinder == null) {
            return;
        }
        if (this.mContext.checkCallingOrSelfPermission("android.permission.CAPTURE_AUDIO_OUTPUT") != 0) {
            Log.w("AS.AudioService", "Trying to call forceRemoteSubmixFullVolume() without CAPTURE_AUDIO_OUTPUT");
            return;
        }
        synchronized (this.mRmtSbmxFullVolDeathHandlers) {
            boolean z2 = true;
            boolean z3 = false;
            if (z) {
                if (!hasRmtSbmxFullVolDeathHandlerFor(iBinder)) {
                    this.mRmtSbmxFullVolDeathHandlers.add(new RmtSbmxFullVolDeathHandler(iBinder));
                    if (!ScreenSharingHelper.isWifiDisplayConnected() && this.mRmtSbmxFullVolRefCount == 0) {
                        this.mFullVolumeDevices.add(32768);
                        this.mFixedVolumeDevices.add(32768);
                        z3 = true;
                    }
                    this.mRmtSbmxFullVolRefCount++;
                }
            } else if (discardRmtSbmxFullVolDeathHandlerFor(iBinder) && (i = this.mRmtSbmxFullVolRefCount) > 0) {
                int i2 = i - 1;
                this.mRmtSbmxFullVolRefCount = i2;
                if (i2 == 0) {
                    this.mFullVolumeDevices.remove(32768);
                    this.mFixedVolumeDevices.remove(32768);
                    if (z2) {
                        checkAllFixedVolumeDevices(3);
                        this.mStreamStates[3].applyAllVolumes();
                    }
                }
            }
            z2 = z3;
            if (z2) {
            }
        }
    }

    public final void setMasterMuteInternal(boolean z, final int i, final String str, int i2, int i3, int i4, String str2) {
        if (i2 == 1000) {
            i2 = UserHandle.getUid(i3, UserHandle.getAppId(i2));
        }
        if (z || checkNoteAppOp(33, i2, str, str2)) {
            if (i3 == UserHandle.getCallingUserId() || this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i4, i2) == 0) {
                EventLogger eventLogger = sMasterMuteLogger;
                final int i5 = z ? 1 : 0;
                eventLogger.enqueue(new EventLogger.Event(i5, i, str) { // from class: com.samsung.android.server.audio.AudioEvents$MasterMuteEvent
                    public final String mCaller;
                    public final int mFlags;
                    public final int mIsMute;

                    {
                        this.mIsMute = i5;
                        this.mFlags = i;
                        this.mCaller = str;
                    }

                    @Override // com.android.server.utils.EventLogger.Event
                    public String eventToString() {
                        return "setMasterMute(mute:" + this.mIsMute + " flags:0x" + Integer.toHexString(this.mFlags) + ") from " + this.mCaller;
                    }
                });
                setMasterMuteInternalNoCallerCheck(z, i, i3);
            }
        }
    }

    public final void setMasterMuteInternalNoCallerCheck(boolean z, int i, int i2) {
        Log.d("AS.AudioService", String.format("Master mute %s, %d, user=%d", Boolean.valueOf(z), Integer.valueOf(i), Integer.valueOf(i2)));
        if (isPlatformAutomotive() || !this.mUseFixedVolume) {
            if (!((isPlatformAutomotive() && i2 == 0) || getCurrentUserId() == i2) || z == AudioSystem.getMasterMute()) {
                return;
            }
            AudioSystem.setMasterMute(z);
            sendMasterMuteUpdate(z, i);
        }
    }

    public boolean isMasterMute() {
        return AudioSystem.getMasterMute();
    }

    public void setMasterMute(boolean z, int i, String str, int i2, String str2) {
        super.setMasterMute_enforcePermission();
        setMasterMuteInternal(z, i, str, Binder.getCallingUid(), i2, Binder.getCallingPid(), str2);
    }

    public int getStreamVolume(int i) {
        return getStreamVolume(i, 0);
    }

    public VolumeInfo getDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) {
        int index;
        VolumeInfo build;
        super.getDeviceVolume_enforcePermission();
        Objects.requireNonNull(volumeInfo);
        Objects.requireNonNull(audioDeviceAttributes);
        Objects.requireNonNull(str);
        if (!volumeInfo.hasStreamType()) {
            Log.e("AS.AudioService", "Unsupported non-stream type based VolumeInfo", new Exception());
            return getDefaultVolumeInfo();
        }
        int streamType = volumeInfo.getStreamType();
        VolumeInfo.Builder builder = new VolumeInfo.Builder(volumeInfo);
        builder.setMinVolumeIndex((this.mStreamStates[streamType].mIndexMin + 5) / 10);
        builder.setMaxVolumeIndex((this.mStreamStates[streamType].mIndexMax + 5) / 10);
        synchronized (VolumeStreamState.class) {
            if (isFixedVolumeDevice(audioDeviceAttributes.getInternalType())) {
                index = (this.mStreamStates[streamType].mIndexMax + 5) / 10;
            } else {
                index = (this.mStreamStates[streamType].getIndex(audioDeviceAttributes.getInternalType()) + 5) / 10;
            }
            builder.setVolumeIndex(index);
            if (this.mStreamStates[streamType].mIsMuted) {
                builder.setMuted(true);
            }
            build = builder.build();
        }
        return build;
    }

    public int getStreamMaxVolume(int i) {
        ensureValidStreamType(i);
        return (this.mStreamStates[i].getMaxIndex() + 5) / 10;
    }

    public int getStreamMinVolume(int i) {
        ensureValidStreamType(i);
        return (this.mStreamStates[i].getMinIndex(Binder.getCallingUid() == 1000 || callingHasAudioSettingsPermission() || this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0) + 5) / 10;
    }

    public int getLastAudibleStreamVolume(int i) {
        super.getLastAudibleStreamVolume_enforcePermission();
        ensureValidStreamType(i);
        return getIndexDividedBy10(this.mStreamStates[i].getIndex(getDeviceForStream(i)), i);
    }

    public VolumeInfo getDefaultVolumeInfo() {
        if (sDefaultVolumeInfo == null) {
            sDefaultVolumeInfo = new VolumeInfo.Builder(3).setMinVolumeIndex(getStreamMinVolume(3)).setMaxVolumeIndex(getStreamMaxVolume(3)).build();
        }
        return sDefaultVolumeInfo;
    }

    public void registerStreamAliasingDispatcher(IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) {
        super.registerStreamAliasingDispatcher_enforcePermission();
        Objects.requireNonNull(iStreamAliasingDispatcher);
        if (z) {
            this.mStreamAliasingDispatchers.register(iStreamAliasingDispatcher);
        } else {
            this.mStreamAliasingDispatchers.unregister(iStreamAliasingDispatcher);
        }
    }

    public void dispatchStreamAliasingUpdate() {
        int beginBroadcast = this.mStreamAliasingDispatchers.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mStreamAliasingDispatchers.getBroadcastItem(i).dispatchStreamAliasingChanged();
            } catch (RemoteException e) {
                Log.e("AS.AudioService", "Error on stream alias update dispatch", e);
            }
        }
        this.mStreamAliasingDispatchers.finishBroadcast();
    }

    /* renamed from: getIndependentStreamTypes, reason: merged with bridge method [inline-methods] */
    public ArrayList m2939getIndependentStreamTypes() {
        super.getIndependentStreamTypes_enforcePermission();
        if (this.mUseVolumeGroupAliases) {
            return new ArrayList(Arrays.stream(AudioManager.getPublicStreamTypes()).boxed().toList());
        }
        ArrayList arrayList = new ArrayList(1);
        for (int i : mStreamVolumeAlias) {
            if (!arrayList.contains(Integer.valueOf(i))) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    public int getStreamTypeAlias(int i) {
        super.getStreamTypeAlias_enforcePermission();
        ensureValidStreamType(i);
        return mStreamVolumeAlias[i];
    }

    public boolean isVolumeControlUsingVolumeGroups() {
        super.isVolumeControlUsingVolumeGroups_enforcePermission();
        return this.mUseVolumeGroupAliases;
    }

    public int getUiSoundsStreamType() {
        return this.mUseVolumeGroupAliases ? this.STREAM_VOLUME_ALIAS_VOICE[2] : mStreamVolumeAlias[2];
    }

    public final boolean isUiSoundsStreamType(int i) {
        if (this.mUseVolumeGroupAliases) {
            int[] iArr = this.STREAM_VOLUME_ALIAS_VOICE;
            if (iArr[i] == iArr[2]) {
                return true;
            }
        } else if (i == mStreamVolumeAlias[2]) {
            return true;
        }
        return false;
    }

    public void setMicrophoneMute(final boolean z, final String str, int i, String str2) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 1000) {
            callingUid = UserHandle.getUid(i, UserHandle.getAppId(callingUid));
        }
        MediaMetrics.Item item = new MediaMetrics.Item("audio.mic").setUid(callingUid).set(MediaMetrics.Property.CALLING_PACKAGE, str).set(MediaMetrics.Property.EVENT, "setMicrophoneMute").set(MediaMetrics.Property.REQUEST, z ? "mute" : "unmute");
        if (!z && !checkNoteAppOp(44, callingUid, str, str2)) {
            item.set(MediaMetrics.Property.EARLY_RETURN, "disallow unmuting").record();
            return;
        }
        if (!checkAudioSettingsPermission("setMicrophoneMute()")) {
            item.set(MediaMetrics.Property.EARLY_RETURN, "!checkAudioSettingsPermission").record();
            return;
        }
        if (i != UserHandle.getCallingUserId() && this.mContext.checkCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            item.set(MediaMetrics.Property.EARLY_RETURN, "permission").record();
            return;
        }
        this.mMicMuteFromApi = z;
        item.record();
        setMicrophoneMuteNoCallerCheck(i);
        final int callingPid = Binder.getCallingPid();
        sMicrophoneLogger.enqueue(new EventLogger.Event(str, callingPid, z) { // from class: com.samsung.android.server.audio.AudioEvents$MicrophoneEvent
            public final boolean mIsEnableMute;
            public final String mPackage;
            public final int mRequesterPid;

            {
                this.mPackage = str;
                this.mRequesterPid = callingPid;
                this.mIsEnableMute = z;
            }

            @Override // com.android.server.utils.EventLogger.Event
            public String eventToString() {
                return "setMicrophoneMute() from package=" + this.mPackage + " pid=" + this.mRequesterPid + " isisEnableMute=" + this.mIsEnableMute;
            }
        });
    }

    public void setMicrophoneMuteFromSwitch(boolean z) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000) {
            Log.e("AS.AudioService", "setMicrophoneMuteFromSwitch() called from non system user!");
            return;
        }
        this.mMicMuteFromSwitch = z;
        new MediaMetrics.Item("audio.mic").setUid(callingUid).set(MediaMetrics.Property.EVENT, "setMicrophoneMuteFromSwitch").set(MediaMetrics.Property.REQUEST, z ? "mute" : "unmute").record();
        setMicrophoneMuteNoCallerCheck(callingUid);
    }

    public final void setMicMuteFromSwitchInput() {
        InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
        if (inputManager.isMicMuted() != -1) {
            setMicrophoneMuteFromSwitch(inputManager.isMicMuted() != 0);
        }
    }

    public boolean isMicrophoneMuted() {
        return this.mMicMuteFromSystemCached && (!this.mMicMuteFromPrivacyToggle || this.mMicMuteFromApi || this.mMicMuteFromRestrictions || this.mMicMuteFromSwitch);
    }

    public final boolean isMicrophoneSupposedToBeMuted() {
        return this.mMicMuteFromSwitch || this.mMicMuteFromRestrictions || this.mMicMuteFromApi || this.mMicMuteFromPrivacyToggle;
    }

    public final void setMicrophoneMuteNoCallerCheck(int i) {
        boolean isMicrophoneSupposedToBeMuted = isMicrophoneSupposedToBeMuted();
        Log.d("AS.AudioService", String.format("Mic mute %b, user=%d", Boolean.valueOf(isMicrophoneSupposedToBeMuted), Integer.valueOf(i)));
        if (getCurrentUserId() == i || i == 1000) {
            boolean isMicrophoneMuted = this.mAudioSystem.isMicrophoneMuted();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int muteMicrophone = this.mAudioSystem.muteMicrophone(isMicrophoneSupposedToBeMuted);
                this.mMicMuteFromSystemCached = this.mAudioSystem.isMicrophoneMuted();
                if (muteMicrophone != 0) {
                    Log.e("AS.AudioService", "Error changing mic mute state to " + isMicrophoneSupposedToBeMuted + " current:" + this.mMicMuteFromSystemCached);
                }
                new MediaMetrics.Item("audio.mic").setUid(i).set(MediaMetrics.Property.EVENT, "setMicrophoneMuteNoCallerCheck").set(MediaMetrics.Property.MUTE, this.mMicMuteFromSystemCached ? "on" : "off").set(MediaMetrics.Property.REQUEST, isMicrophoneSupposedToBeMuted ? "mute" : "unmute").set(MediaMetrics.Property.STATUS, Integer.valueOf(muteMicrophone)).record();
                if (isMicrophoneSupposedToBeMuted != isMicrophoneMuted) {
                    sendMsg(this.mAudioHandler, 30, 1, 0, 0, null, 0);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public int getRingerModeExternal() {
        int i;
        synchronized (this.mSettingsLock) {
            i = this.mRingerModeExternal;
        }
        return i;
    }

    public int getRingerModeInternal() {
        int i;
        synchronized (this.mSettingsLock) {
            i = this.mRingerMode;
        }
        return i;
    }

    public final void ensureValidRingerMode(int i) {
        if (isValidRingerMode(i)) {
            return;
        }
        throw new IllegalArgumentException("Bad ringer mode " + i);
    }

    public void setRingerModeExternal(int i, String str) {
        if (isAndroidNPlus(str) && wouldToggleZenMode(i) && !this.mNm.isNotificationPolicyAccessGrantedForPackage(str)) {
            throw new SecurityException("Not allowed to change Do Not Disturb state");
        }
        setRingerMode(i, str, true);
    }

    public void setRingerModeInternal(int i, String str) {
        enforceVolumeController("setRingerModeInternal");
        setRingerMode(i, str, false);
    }

    public void silenceRingerModeInternal(String str) {
        VibrationEffect vibrationEffect;
        int i;
        int secureIntForUser = this.mContext.getResources().getBoolean(R.bool.config_swipeDisambiguation) ? this.mSettings.getSecureIntForUser(this.mContentResolver, "volume_hush_gesture", 0, -2) : 0;
        int i2 = 1;
        if (secureIntForUser == 1) {
            vibrationEffect = VibrationEffect.get(5);
            i = 17043228;
        } else if (secureIntForUser != 2) {
            vibrationEffect = null;
            i2 = 0;
            i = 0;
        } else {
            vibrationEffect = VibrationEffect.get(1);
            i = 17043227;
            i2 = 0;
        }
        maybeVibrate(vibrationEffect, str);
        setRingerModeInternal(i2, str);
        Toast.makeText(this.mContext, i, 0).show();
    }

    public final boolean maybeVibrate(VibrationEffect vibrationEffect, String str) {
        if (!this.mHasVibrator || vibrationEffect == null) {
            return false;
        }
        this.mVibrator.vibrate(Binder.getCallingUid(), this.mContext.getOpPackageName(), vibrationEffect, str, TOUCH_VIBRATION_ATTRIBUTES);
        return true;
    }

    public final void setRingerMode(int i, String str, boolean z) {
        if (this.mUseFixedVolume || this.mIsSingleVolume || this.mUseVolumeGroupAliases) {
            return;
        }
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Bad caller: " + str);
        }
        ensureValidRingerMode(i);
        int i2 = (i != 1 || this.mHasVibrator) ? i : 0;
        if (this.mMuteIntervalMs != 0 && i2 != 0) {
            this.mMuteIntervalMs = 0;
        }
        if (i2 != getRingerModeInternal()) {
            sendBroadcastToSoundEventReceiver(1, i2, str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                int ringerModeInternal = getRingerModeInternal();
                int ringerModeExternal = getRingerModeExternal();
                if (z) {
                    setRingerModeExt(i2);
                    AudioManagerInternal.RingerModeDelegate ringerModeDelegate = this.mRingerModeDelegate;
                    if (ringerModeDelegate != null) {
                        i2 = ringerModeDelegate.onSetRingerModeExternal(ringerModeExternal, i2, str, ringerModeInternal, this.mVolumePolicy);
                    }
                    if (i2 != ringerModeInternal) {
                        setRingerModeInt(i2, true);
                        handleSetRingerMode(i2, str, z);
                    }
                } else {
                    if (i2 != ringerModeInternal) {
                        setRingerModeInt(i2, true);
                        handleSetRingerMode(i2, str, z);
                    }
                    AudioManagerInternal.RingerModeDelegate ringerModeDelegate2 = this.mRingerModeDelegate;
                    if (ringerModeDelegate2 != null) {
                        i2 = ringerModeDelegate2.onSetRingerModeInternal(ringerModeInternal, i2, str, ringerModeExternal, this.mVolumePolicy);
                    }
                    setRingerModeExt(i2);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setRingerModeExt(int i) {
        synchronized (this.mSettingsLock) {
            if (i == this.mRingerModeExternal) {
                return;
            }
            this.mRingerModeExternal = i;
            broadcastRingerMode("android.media.RINGER_MODE_CHANGED", i);
        }
    }

    public final void muteRingerModeStreams() {
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        if (this.mNm == null) {
            this.mNm = (NotificationManager) this.mContext.getSystemService("notification");
        }
        int i = this.mRingerMode;
        boolean z = i == 1 || i == 0;
        boolean z2 = i == 1 && this.mDeviceBroker.isBluetoothScoActive();
        String str = "muteRingerModeStreams() from u/pid:" + Binder.getCallingUid() + "/" + Binder.getCallingPid();
        if (z2 && !this.mDeviceBroker.isBluetoothScoOnForAs()) {
            Log.i("AS.AudioService", "shouldRingSco set to false. because, BT SCO state is not connected.");
            z2 = false;
        }
        sendMsg(this.mAudioHandler, 8, 2, 7, z2 ? 3 : 0, str, 0);
        int i2 = numStreamTypes - 1;
        while (i2 >= 0) {
            boolean isStreamMutedByRingerOrZenMode = isStreamMutedByRingerOrZenMode(i2);
            boolean z3 = (z2 && i2 == 2) ? false : true;
            boolean shouldZenMuteStream = shouldZenMuteStream(i2);
            boolean z4 = shouldZenMuteStream || (z && isStreamAffectedByRingerMode(i2) && z3);
            if (isStreamMutedByRingerOrZenMode == z4) {
                if (shouldZenMuteStream) {
                    this.mStreamStates[i2].mute(true, "muteRingerModeStreams");
                }
            } else if (!z4) {
                if (mStreamVolumeAlias[i2] == (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION ? 5 : 2)) {
                    synchronized (VolumeStreamState.class) {
                        VolumeStreamState volumeStreamState = this.mStreamStates[i2];
                        for (int i3 = 0; i3 < volumeStreamState.mIndexMap.size(); i3++) {
                            int keyAt = volumeStreamState.mIndexMap.keyAt(i3);
                            if (volumeStreamState.mIndexMap.valueAt(i3) == 0) {
                                volumeStreamState.setIndex(10, keyAt, "AS.AudioService", true);
                            }
                        }
                        sendMsg(this.mAudioHandler, 1, 2, getDeviceForStream(i2), 0, this.mStreamStates[i2], 500);
                    }
                }
                sRingerAndZenModeMutedStreams &= ~(1 << i2);
                sMuteLogger.enqueue(new AudioServiceEvents$RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "muteRingerModeStreams"));
                this.mStreamStates[i2].mute(false, "muteRingerModeStreams");
            } else {
                sRingerAndZenModeMutedStreams |= 1 << i2;
                sMuteLogger.enqueue(new AudioServiceEvents$RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "muteRingerModeStreams"));
                this.mStreamStates[i2].mute(true, "muteRingerModeStreams");
            }
            i2--;
        }
        SystemProperties.set("persist.sys.silent", isStreamMutedByRingerOrZenMode(1) ? "1" : "0");
    }

    public final void setRingerModeInt(int i, boolean z) {
        boolean z2;
        synchronized (this.mSettingsLock) {
            int i2 = this.mRingerMode;
            z2 = i2 != i;
            if (z2) {
                this.mPrevRingerMode = i2;
                sendMsg(this.mAudioHandler, 2766, 0, 0, 0, 0, 0);
            }
            this.mRingerMode = i;
            muteRingerModeStreams();
        }
        if (z) {
            sendMsg(this.mAudioHandler, 3, 0, 0, 0, null, 500);
        }
        if (z2) {
            broadcastRingerMode("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", i);
            if (this.mMuteMediaByVibrateOrSilentMode) {
                if ((this.mPrevRingerMode == 2 || i == 2) ? false : true) {
                    return;
                }
                muteMediaStreamOfSpeaker(i != 2);
            }
        }
    }

    public void postUpdateRingerModeServiceInt() {
        sendMsg(this.mAudioHandler, 25, 2, 0, 0, null, 0);
    }

    public final void onUpdateRingerModeServiceInt() {
        setRingerModeInt(getRingerModeInternal(), false);
    }

    public boolean shouldVibrate(int i) {
        if (!this.mHasVibrator) {
            return false;
        }
        int vibrateSetting = getVibrateSetting(i);
        return vibrateSetting != 1 ? vibrateSetting == 2 && getRingerModeExternal() == 1 : getRingerModeExternal() != 0;
    }

    public int getVibrateSetting(int i) {
        if (this.mHasVibrator) {
            return (this.mVibrateSetting >> (i * 2)) & 3;
        }
        return 0;
    }

    public void setVibrateSetting(int i, int i2) {
        if (this.mHasVibrator) {
            this.mVibrateSetting = AudioSystem.getValueForVibrateSetting(this.mVibrateSetting, i, i2);
            broadcastVibrateSetting(i);
        }
    }

    public class SetModeDeathHandler implements IBinder.DeathRecipient {
        public final IBinder mCb;
        public final boolean mIsPrivileged;
        public int mMode;
        public final String mPackage;
        public final int mPid;
        public final int mUid;
        public boolean mPlaybackActive = false;
        public boolean mRecordingActive = false;
        public String mPackageType = "NORMAL";
        public long mUpdateTime = System.currentTimeMillis();

        public SetModeDeathHandler(IBinder iBinder, int i, int i2, boolean z, String str, int i3) {
            this.mMode = i3;
            this.mCb = iBinder;
            this.mPid = i;
            this.mUid = i2;
            this.mPackage = str;
            this.mIsPrivileged = z;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (AudioService.this.mDeviceBroker.mSetModeLock) {
                Log.w("AS.AudioService", "SetModeDeathHandler client died");
                int indexOf = AudioService.this.mSetModeDeathHandlers.indexOf(this);
                if (indexOf < 0) {
                    Log.w("AS.AudioService", "unregistered SetModeDeathHandler client died");
                } else {
                    SetModeDeathHandler setModeDeathHandler = (SetModeDeathHandler) AudioService.this.mSetModeDeathHandlers.get(indexOf);
                    AudioService.this.mSetModeDeathHandlers.remove(indexOf);
                    AudioService.this.mExt.updateCallGuardInfo(-1, setModeDeathHandler.getPid(), false);
                    AudioService.sendMsg(AudioService.this.mAudioHandler, 36, 2, -1, Process.myPid(), AudioService.this.mContext.getPackageName(), 0);
                }
            }
        }

        public int getPid() {
            return this.mPid;
        }

        public void setMode(int i) {
            this.mMode = i;
            this.mUpdateTime = System.currentTimeMillis();
        }

        public int getMode() {
            return this.mMode;
        }

        public IBinder getBinder() {
            return this.mCb;
        }

        public int getUid() {
            return this.mUid;
        }

        public String getPackage() {
            return this.mPackage;
        }

        public boolean isPrivileged() {
            return this.mIsPrivileged;
        }

        public long getUpdateTime() {
            return this.mUpdateTime;
        }

        public void setPlaybackActive(boolean z) {
            this.mPlaybackActive = z;
        }

        public void setRecordingActive(boolean z) {
            this.mRecordingActive = z;
        }

        public boolean isActive() {
            if (this.mIsPrivileged) {
                return true;
            }
            int i = this.mMode;
            return (i == 3 && (this.mRecordingActive || this.mPlaybackActive)) || i == 1 || i == 4;
        }

        public void dump(PrintWriter printWriter, int i) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss:SSS");
            if (i >= 0) {
                printWriter.println("  Requester # " + (i + 1) + XmlUtils.STRING_ARRAY_SEPARATOR);
            }
            printWriter.println("  - Mode: " + AudioSystem.modeToString(this.mMode));
            printWriter.println("  - Binder: " + this.mCb);
            printWriter.println("  - Pid: " + this.mPid);
            printWriter.println("  - Uid: " + this.mUid);
            printWriter.println("  - Package: " + this.mPackage);
            printWriter.println("  - Privileged: " + this.mIsPrivileged);
            printWriter.println("  - Active: " + isActive());
            printWriter.println("    Playback active: " + this.mPlaybackActive);
            printWriter.println("    Recording active: " + this.mRecordingActive);
            printWriter.println("  - update time: " + simpleDateFormat.format(new Date(this.mUpdateTime)));
        }
    }

    public final SetModeDeathHandler getAudioModeOwnerHandler() {
        Iterator it = this.mSetModeDeathHandlers.iterator();
        SetModeDeathHandler setModeDeathHandler = null;
        SetModeDeathHandler setModeDeathHandler2 = null;
        while (it.hasNext()) {
            SetModeDeathHandler setModeDeathHandler3 = (SetModeDeathHandler) it.next();
            if (setModeDeathHandler3.isActive()) {
                if (setModeDeathHandler3.isPrivileged()) {
                    if (setModeDeathHandler == null || setModeDeathHandler3.getUpdateTime() > setModeDeathHandler.getUpdateTime()) {
                        setModeDeathHandler = setModeDeathHandler3;
                    }
                } else if (setModeDeathHandler2 == null || setModeDeathHandler3.getUpdateTime() > setModeDeathHandler2.getUpdateTime()) {
                    setModeDeathHandler2 = setModeDeathHandler3;
                }
            }
        }
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            this.mExternalVoipModeOwner = null;
            if (setModeDeathHandler != null && setModeDeathHandler2 != null && setModeDeathHandler.mMode == 3 && setModeDeathHandler2.mMode == 3 && setModeDeathHandler2.getUpdateTime() > setModeDeathHandler.getUpdateTime()) {
                this.mExternalVoipModeOwner = setModeDeathHandler2;
            }
        }
        return setModeDeathHandler != null ? setModeDeathHandler : setModeDeathHandler2;
    }

    public AudioDeviceBroker.AudioModeInfo getAudioModeOwner() {
        SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
        if (audioModeOwnerHandler != null) {
            return new AudioDeviceBroker.AudioModeInfo(audioModeOwnerHandler.getMode(), audioModeOwnerHandler.getPid(), audioModeOwnerHandler.getUid());
        }
        return new AudioDeviceBroker.AudioModeInfo(0, 0, 0);
    }

    public void setMode(int i, IBinder iBinder, String str) {
        SetModeDeathHandler setModeDeathHandler;
        SetModeDeathHandler setModeDeathHandler2;
        int i2;
        int i3 = i;
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        Log.v("AS.AudioService", "setMode(mode=" + i3 + ", pid=" + callingPid + ", uid=" + callingUid + ", caller=" + str + ")");
        if (checkAudioSettingsPermission("setMode()")) {
            if (iBinder == null) {
                Log.e("AS.AudioService", "setMode() called with null binder");
                return;
            }
            if (i3 < -1 || i3 >= 7) {
                Log.w("AS.AudioService", "setMode() invalid mode: " + i3);
                return;
            }
            if (i3 == -1) {
                i3 = getMode();
            }
            int i4 = i3;
            if (i4 == 4 && !this.mIsCallScreeningModeSupported) {
                Log.w("AS.AudioService", "setMode(MODE_CALL_SCREENING) not permitted when call screening is not supported");
                return;
            }
            boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
            if ((i4 == 2 || i4 == 5 || i4 == 6) && !z) {
                Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial: setMode(" + AudioSystem.modeToString(i4) + ") from pid=" + callingPid + ", uid=" + Binder.getCallingUid());
                return;
            }
            synchronized (this.mDeviceBroker.mSetModeLock) {
                Iterator it = this.mSetModeDeathHandlers.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        setModeDeathHandler = null;
                        break;
                    } else {
                        setModeDeathHandler = (SetModeDeathHandler) it.next();
                        if (setModeDeathHandler.getPid() == callingPid) {
                            break;
                        }
                    }
                }
                if (i4 != 0) {
                    if (setModeDeathHandler != null) {
                        setModeDeathHandler.setMode(i4);
                        Log.v("AS.AudioService", "setMode(" + i4 + ") updating hldr for pid: " + callingPid);
                        i2 = 3;
                        setModeDeathHandler2 = setModeDeathHandler;
                    } else {
                        SetModeDeathHandler setModeDeathHandler3 = new SetModeDeathHandler(iBinder, callingPid, callingUid, z, str, i4);
                        try {
                            iBinder.linkToDeath(setModeDeathHandler3, 0);
                            this.mSetModeDeathHandlers.add(setModeDeathHandler3);
                            Log.v("AS.AudioService", "setMode(" + i4 + ") adding handler for pid=" + callingPid);
                            setModeDeathHandler2 = setModeDeathHandler3;
                            i2 = 3;
                        } catch (RemoteException unused) {
                            Log.w("AS.AudioService", "setMode() could not link to " + iBinder + " binder death");
                            return;
                        }
                    }
                    if (i4 == i2 && !setModeDeathHandler2.isPrivileged()) {
                        setModeDeathHandler2.setPlaybackActive(true);
                        setModeDeathHandler2.setRecordingActive(true);
                        sendMsg(this.mAudioHandler, 31, 2, 0, 0, setModeDeathHandler2, 6000);
                    }
                    this.mExt.updateCallGuardInfo(i4, callingPid, false);
                    sendMsg(this.mAudioHandler, 36, 0, i4, callingPid, str, 0);
                } else {
                    if (setModeDeathHandler != null) {
                        if (!setModeDeathHandler.isPrivileged() && setModeDeathHandler.getMode() == 3) {
                            this.mAudioHandler.removeEqualMessages(31, setModeDeathHandler);
                            this.mDeviceBroker.postCheckAbnormalSco(Process.getUidForPid(callingPid));
                        }
                        this.mSetModeDeathHandlers.remove(setModeDeathHandler);
                        Log.v("AS.AudioService", "setMode(" + i4 + ") removing hldr for pid: " + callingPid);
                        try {
                            setModeDeathHandler.getBinder().unlinkToDeath(setModeDeathHandler, 0);
                        } catch (NoSuchElementException unused2) {
                            Log.w("AS.AudioService", "setMode link does not exist ...");
                        }
                    }
                    this.mExt.updateCallGuardInfo(i4, callingPid, false);
                    sendMsg(this.mAudioHandler, 36, 0, i4, callingPid, str, 0);
                }
            }
        }
    }

    public void onUpdateAudioMode(int i, final int i2, final String str, boolean z) {
        final int i3;
        String str2;
        int i4;
        int i5;
        MicModeManager micModeManager;
        SetModeDeathHandler setModeDeathHandler;
        AudioDeviceBroker.AudioModeInfo modeOwner;
        int i6;
        final int mode = i == -1 ? getMode() : i;
        SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
        if (audioModeOwnerHandler != null) {
            int mode2 = audioModeOwnerHandler.getMode();
            i4 = audioModeOwnerHandler.getUid();
            int pid = audioModeOwnerHandler.getPid();
            str2 = audioModeOwnerHandler.getPackage();
            i5 = mode2;
            i3 = pid;
        } else {
            i3 = 0;
            str2 = "";
            i4 = 0;
            i5 = 0;
        }
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            Log.v("AS.AudioService", "onUpdateAudioMode() new mode: " + i5 + " uid = " + i4 + " pid = " + i3 + " package = " + str2 + " current mode: " + this.mMode.get() + " requested mode: " + mode + " requested pid: " + i2 + " requesterPackage: " + str);
        } else {
            Log.v("AS.AudioService", "onUpdateAudioMode() new mode: " + i5 + ", current mode: " + this.mMode.get() + " requested mode: " + mode);
        }
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE && (i6 = (modeOwner = this.mDeviceBroker.getModeOwner()).mMode) == 3 && ((i5 == i6 || mode == i6) && modeOwner.mPid != i2 && !"android".equals(str))) {
            Log.i("AS.AudioService", "onUpdateAudioMode requesterPackage = " + str + " requestedMode = " + mode);
            this.mMicModeManager.updateAudioMode(str, mode);
        }
        if (i5 != this.mMode.get() || z) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI && this.mMicModeManager != null) {
                    if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                        if (i5 == mode && (i5 != 3 || !"android".equals(str))) {
                            str2 = str;
                            setModeDeathHandler = this.mExternalVoipModeOwner;
                            if (setModeDeathHandler != null && i5 == 3) {
                                str2 = setModeDeathHandler.getPackage();
                                Log.i("AS.AudioService", "onUpdateAudioMode mExternalVoipModeOwner packageName = " + str2);
                            }
                        }
                        Log.i("AS.AudioService", "onUpdateAudioMode packageName " + str2 + "instead of requesterPackage = " + str);
                        setModeDeathHandler = this.mExternalVoipModeOwner;
                        if (setModeDeathHandler != null) {
                            str2 = setModeDeathHandler.getPackage();
                            Log.i("AS.AudioService", "onUpdateAudioMode mExternalVoipModeOwner packageName = " + str2);
                        }
                    } else {
                        str2 = str;
                    }
                    this.mMicModeManager.updateAudioMode(str2, i5);
                }
                if (this.mAudioSystem.setPhoneState(i5, i4) == 0) {
                    Log.v("AS.AudioService", "onUpdateAudioMode: mode successfully set to " + i5);
                    this.mDeviceBroker.setModeRequesterPackage(str);
                    GoodCatchManager goodCatchManager = this.mGoodCatchManager;
                    if (goodCatchManager != null && goodCatchManager.isCallModeCatchEnabled()) {
                        this.mGoodCatchManager.updateCallMode(str, i5, str);
                    }
                    if (i5 == 3) {
                        this.mExt.startCPUBoostForVoIP(this.mContext);
                    } else {
                        this.mExt.stopCPUBoostForVoIP();
                    }
                    Intent intent = new Intent("android.samsung.media.action.AUDIO_MODE");
                    intent.addFlags(16777248);
                    intent.putExtra("android.samsung.media.extra.AUDIO_MODE", i5);
                    intent.putExtra("android.intent.extra.PACKAGES", str);
                    sendBroadcastToAll(intent, null);
                    this.mDeviceBroker.postSarControl();
                    sendMsg(this.mAudioHandler, 40, 0, i5, 0, null, 0);
                    int andSet = this.mMode.getAndSet(i5);
                    int i7 = i4;
                    final int i8 = i5;
                    this.mModeLogger.enqueue(new EventLogger.Event(str, i2, mode, i3, i8) { // from class: com.android.server.audio.AudioServiceEvents$PhoneStateEvent
                        public final int mActualMode;
                        public final int mOp = 0;
                        public final int mOwnerPid;
                        public final String mPackage;
                        public final int mRequestedMode;
                        public final int mRequesterPid;

                        {
                            this.mPackage = str;
                            this.mRequesterPid = i2;
                            this.mRequestedMode = mode;
                            this.mOwnerPid = i3;
                            this.mActualMode = i8;
                            logMetricEvent();
                        }

                        @Override // com.android.server.utils.EventLogger.Event
                        public String eventToString() {
                            int i9 = this.mOp;
                            if (i9 != 0) {
                                if (i9 == 1) {
                                    return "mode IN COMMUNICATION timeout for package=" + this.mPackage + " pid=" + this.mOwnerPid;
                                }
                                return "FIXME invalid op:" + this.mOp;
                            }
                            return "setMode(" + AudioSystem.modeToString(this.mRequestedMode) + ") from package=" + this.mPackage + " pid=" + this.mRequesterPid + " selected mode=" + AudioSystem.modeToString(this.mActualMode) + " by pid=" + this.mOwnerPid;
                        }

                        public final void logMetricEvent() {
                            int i9 = this.mOp;
                            if (i9 == 0) {
                                new MediaMetrics.Item("audio.mode").set(MediaMetrics.Property.EVENT, "set").set(MediaMetrics.Property.REQUESTED_MODE, AudioSystem.modeToString(this.mRequestedMode)).set(MediaMetrics.Property.MODE, AudioSystem.modeToString(this.mActualMode)).set(MediaMetrics.Property.CALLING_PACKAGE, this.mPackage).record();
                            } else {
                                if (i9 != 1) {
                                    return;
                                }
                                new MediaMetrics.Item("audio.mode").set(MediaMetrics.Property.EVENT, "inCommunicationTimeout").set(MediaMetrics.Property.CALLING_PACKAGE, this.mPackage).record();
                            }
                        }
                    });
                    int bluetoothContextualVolumeStream = getBluetoothContextualVolumeStream(i5);
                    int i9 = mStreamVolumeAlias[bluetoothContextualVolumeStream];
                    Log.v("AS.AudioService", "onUpdateAudioMode: streamType=" + bluetoothContextualVolumeStream + ", streamAlias=" + i9);
                    int index = this.mStreamStates[i9].getIndex(536870912);
                    int maxIndex = this.mStreamStates[i9].getMaxIndex();
                    int i10 = i5;
                    setStreamVolumeInt(i9, index, 536870912, true, str, true);
                    updateStreamVolumeAlias(true, str);
                    updateAbsVolumeMultiModeDevices(andSet, i10);
                    setLeAudioVolumeOnModeUpdate(i10, 536870912, i9, index, maxIndex);
                    this.mDeviceBroker.postSetModeOwner(i10, i3, i7);
                    return;
                }
                Log.w("AS.AudioService", "onUpdateAudioMode: failed to set audio mode to: " + i5);
                if (!Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI || (micModeManager = this.mMicModeManager) == null) {
                    return;
                }
                micModeManager.updateAudioMode("", this.mMode.get());
                return;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        this.mExt.updateCallGuardInfo(i5, i3, true);
    }

    public int getMode() {
        synchronized (this.mDeviceBroker.mSetModeLock) {
            SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
            if (audioModeOwnerHandler == null) {
                return 0;
            }
            return audioModeOwnerHandler.getMode();
        }
    }

    public boolean isCallScreeningModeSupported() {
        return this.mIsCallScreeningModeSupported;
    }

    public void dispatchMode(int i) {
        int beginBroadcast = this.mModeDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mModeDispatchers.getBroadcastItem(i2).dispatchAudioModeChanged(i);
            } catch (RemoteException unused) {
            }
        }
        this.mModeDispatchers.finishBroadcast();
    }

    public void registerModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) {
        this.mModeDispatchers.register(iAudioModeDispatcher);
    }

    public void unregisterModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) {
        this.mModeDispatchers.unregister(iAudioModeDispatcher);
    }

    public boolean isPstnCallAudioInterceptable() {
        super.isPstnCallAudioInterceptable_enforcePermission();
        boolean z = false;
        boolean z2 = false;
        for (AudioDeviceInfo audioDeviceInfo : AudioManager.getDevicesStatic(3)) {
            if (audioDeviceInfo.getInternalType() == 65536) {
                z = true;
            } else if (audioDeviceInfo.getInternalType() == -2147483584) {
                z2 = true;
            }
            if (z && z2) {
                return true;
            }
        }
        return false;
    }

    public void setRttEnabled(boolean z) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial: setRttEnabled from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        synchronized (this.mSettingsLock) {
            this.mRttEnabled = z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AudioSystem.setRttEnabled(z);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Should only be called from system process");
        }
        adjustSuggestedStreamVolume(i2, i, i3, str, str, i4, i5, hasAudioSettingsPermission(i4, i5), 0);
    }

    public void adjustStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Should only be called from system process");
        }
        if (i2 != 0) {
            sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(5, i, i2, i3, str + " uid:" + i4));
        }
        adjustStreamVolume(i, i2, i3, str, str, i4, i5, null, hasAudioSettingsPermission(i4, i5), 0);
    }

    public void setStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Should only be called from system process");
        }
        setStreamVolume(i, i2, i3, null, str, str, null, i4, hasAudioSettingsPermission(i4, i5));
    }

    public final class LoadSoundEffectReply implements SoundEffectsHelper.OnEffectsLoadCompleteHandler {
        public int mStatus;

        public LoadSoundEffectReply() {
            this.mStatus = 1;
        }

        @Override // com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler
        public synchronized void run(boolean z) {
            this.mStatus = z ? 0 : -1;
            notify();
        }

        public synchronized boolean waitForLoaded(int i) {
            int i2;
            while (true) {
                i2 = this.mStatus;
                if (i2 != 1) {
                    break;
                }
                int i3 = i - 1;
                if (i <= 0) {
                    break;
                }
                try {
                    wait(5000L);
                } catch (InterruptedException unused) {
                    Log.w("AS.AudioService", "Interrupted while waiting sound pool loaded.");
                }
                i = i3;
            }
            return i2 == 0;
        }
    }

    public void playSoundEffect(int i, int i2) {
        if (querySoundEffectsEnabled(i2)) {
            playSoundEffectVolume(i, -1.0f);
        }
    }

    public final boolean querySoundEffectsEnabled(int i) {
        return this.mSettings.getSystemIntForUser(getContentResolver(), "sound_effects_enabled", 0, i) != 0;
    }

    public void playSoundEffectVolume(int i, float f) {
        if (isStreamMute(1)) {
            return;
        }
        if (i >= 23 || i < 0) {
            Log.w("AS.AudioService", "AudioService effectType value " + i + " out of range");
            return;
        }
        sendMsg(this.mAudioHandler, 5, 2, i, (int) (f * 1000.0f), null, 0);
    }

    public boolean loadSoundEffects() {
        LoadSoundEffectReply loadSoundEffectReply = new LoadSoundEffectReply();
        sendMsg(this.mAudioHandler, 7, 2, 0, 0, loadSoundEffectReply, 0);
        return loadSoundEffectReply.waitForLoaded(3);
    }

    public void scheduleLoadSoundEffects() {
        sendMsg(this.mAudioHandler, 7, 2, 0, 0, null, 0);
    }

    public void unloadSoundEffects() {
        sendMsg(this.mAudioHandler, 15, 2, 0, 0, null, 0);
    }

    public void reloadAudioSettings() {
        readAudioSettings(false);
    }

    public final void readAudioSettings(boolean z) {
        readPersistedSettings();
        readUserRestrictions();
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        for (int i = 0; i < numStreamTypes; i++) {
            VolumeStreamState volumeStreamState = this.mStreamStates[i];
            if (!z || mStreamVolumeAlias[i] != 3) {
                volumeStreamState.readSettings();
                synchronized (VolumeStreamState.class) {
                    if (volumeStreamState.mIsMuted && ((!isStreamAffectedByMute(i) && !isStreamMutedByRingerOrZenMode(i)) || this.mUseFixedVolume)) {
                        volumeStreamState.mIsMuted = false;
                    }
                }
            }
        }
        readVolumeGroupsSettings(z);
        setRingerModeInt(getRingerModeInternal(), false);
        checkAllFixedVolumeDevices();
        checkAllAliasStreamVolumes();
        checkMuteAffectedStreams();
        this.mSoundDoseHelper.restoreMusicActiveMs();
        this.mSoundDoseHelper.enforceSafeMediaVolumeIfActive("AS.AudioService");
        Log.d("AS.AudioService", "Restoring device volume behavior");
        restoreDeviceVolumeBehavior();
    }

    public int[] getAvailableCommunicationDeviceIds() {
        return AudioDeviceBroker.getAvailableCommunicationDevices().stream().mapToInt(new ToIntFunction() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AudioDeviceInfo) obj).getId();
            }
        }).toArray();
    }

    public boolean setCommunicationDevice(IBinder iBinder, int i) {
        AudioDeviceInfo audioDeviceInfo;
        int i2;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        String str = null;
        if (i != 0) {
            audioDeviceInfo = AudioManager.getDeviceForPortId(i, 2);
            if (audioDeviceInfo == null) {
                Log.w("AS.AudioService", "setCommunicationDevice: invalid portID " + i);
                return false;
            }
            if (!AudioDeviceBroker.isValidCommunicationDevice(audioDeviceInfo)) {
                if (!audioDeviceInfo.isSink()) {
                    throw new IllegalArgumentException("device must have sink role");
                }
                throw new IllegalArgumentException("invalid device type: " + audioDeviceInfo.getType());
            }
        } else {
            audioDeviceInfo = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(audioDeviceInfo == null ? "clearCommunicationDevice(" : "setCommunicationDevice(");
        sb.append(") from u/pid:");
        sb.append(callingUid);
        sb.append("/");
        sb.append(callingPid);
        String sb2 = sb.toString();
        if (audioDeviceInfo != null) {
            i2 = audioDeviceInfo.getPort().type();
            str = audioDeviceInfo.getAddress();
        } else {
            AudioDeviceInfo communicationDevice = this.mDeviceBroker.getCommunicationDevice();
            if (communicationDevice != null) {
                int type = communicationDevice.getPort().type();
                str = communicationDevice.getAddress();
                i2 = type;
            } else {
                i2 = 1073741824;
            }
        }
        if (i2 != 1073741824) {
            new MediaMetrics.Item("audio.device.setCommunicationDevice").set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(i2)).set(MediaMetrics.Property.ADDRESS, str).set(MediaMetrics.Property.STATE, audioDeviceInfo != null ? "connected" : "disconnected").record();
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mDeviceBroker.setCommunicationDevice(iBinder, callingUid, audioDeviceInfo, z, sb2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getCommunicationDevice() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AudioDeviceInfo communicationDevice = this.mDeviceBroker.getCommunicationDevice();
            return communicationDevice != null ? communicationDevice.getId() : 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
        if (iCommunicationDeviceDispatcher == null) {
            return;
        }
        this.mDeviceBroker.registerCommunicationDeviceDispatcher(iCommunicationDeviceDispatcher);
    }

    public void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
        if (iCommunicationDeviceDispatcher == null) {
            return;
        }
        this.mDeviceBroker.unregisterCommunicationDeviceDispatcher(iCommunicationDeviceDispatcher);
    }

    public void setSpeakerphoneOn(IBinder iBinder, boolean z) {
        if (checkAudioSettingsPermission("setSpeakerphoneOn()")) {
            boolean z2 = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            String str = "setSpeakerphoneOn(" + z + ") from u/pid:" + callingUid + "/" + callingPid + " Package Name:" + getPackageName(callingUid)[0];
            new MediaMetrics.Item("audio.device.setSpeakerphoneOn").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.STATE, z ? "on" : "off").record();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mDeviceBroker.setSpeakerphoneOn(iBinder, callingUid, z, z2, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public boolean isSpeakerphoneOn() {
        return this.mDeviceBroker.isSpeakerphoneOn();
    }

    public void setBluetoothScoOn(boolean z) {
        if (checkAudioSettingsPermission("setBluetoothScoOn()")) {
            if (!z || this.mDeviceBroker.isBluetoothScoSupported()) {
                if (UserHandle.getCallingAppId() >= 10000) {
                    this.mBtScoOnByApp = z;
                    sDeviceLogger.enqueue(new EventLogger.StringEvent("setBluetoothScoOn update mBtScoOnByApp = " + z + " uid = " + Binder.getCallingUid() + " pid = " + Binder.getCallingPid()));
                    return;
                }
                int callingUid = Binder.getCallingUid();
                int callingPid = Binder.getCallingPid();
                String str = "setBluetoothScoOn(" + z + ") from u/pid:" + callingUid + "/" + callingPid;
                new MediaMetrics.Item("audio.device.setBluetoothScoOn").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.STATE, z ? "on" : "off").record();
                this.mDeviceBroker.setBluetoothScoOn(z, str);
            }
        }
    }

    public void setA2dpSuspended(boolean z) {
        super.setA2dpSuspended_enforcePermission();
        this.mDeviceBroker.setA2dpSuspended(z, false, "setA2dpSuspended(" + z + ") from u/pid:" + Binder.getCallingUid() + "/" + Binder.getCallingPid());
    }

    public void setLeAudioSuspended(boolean z) {
        super.setLeAudioSuspended_enforcePermission();
        this.mDeviceBroker.setLeAudioSuspended(z, false, "setLeAudioSuspended(" + z + ") from u/pid:" + Binder.getCallingUid() + "/" + Binder.getCallingPid());
    }

    public boolean isBluetoothScoOn() {
        return this.mBtScoOnByApp || this.mDeviceBroker.isBluetoothScoOn();
    }

    public void setBluetoothA2dpOn(boolean z) {
        if (checkAudioSettingsPermission("setBluetoothA2dpOn()")) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            String str = "setBluetoothA2dpOn(" + z + ") from u/pid:" + callingUid + "/" + callingPid;
            new MediaMetrics.Item("audio.device.setBluetoothA2dpOn").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.STATE, z ? "on" : "off").record();
            this.mDeviceBroker.setBluetoothA2dpOn_Async(z, str);
        }
    }

    public boolean isBluetoothA2dpOn() {
        return this.mDeviceBroker.isBluetoothA2dpOn();
    }

    public void startBluetoothSco(IBinder iBinder, int i) {
        if (checkAudioSettingsPermission("startBluetoothSco()")) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int i2 = i < 18 ? 0 : -1;
            new MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.EVENT, "startBluetoothSco").set(MediaMetrics.Property.SCO_AUDIO_MODE, BtHelper.scoAudioModeToString(i2)).record();
            startBluetoothScoInt(iBinder, callingUid, i2, "startBluetoothSco()) from u/pid:" + callingUid + "/" + callingPid);
        }
    }

    public void startBluetoothScoVirtualCall(IBinder iBinder) {
        if (checkAudioSettingsPermission("startBluetoothScoVirtualCall()")) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            new MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.EVENT, "startBluetoothScoVirtualCall").set(MediaMetrics.Property.SCO_AUDIO_MODE, BtHelper.scoAudioModeToString(0)).record();
            startBluetoothScoInt(iBinder, callingUid, 0, "startBluetoothScoVirtualCall()) from u/pid:" + callingUid + "/" + callingPid);
        }
    }

    public void startBluetoothScoInt(IBinder iBinder, int i, int i2, String str) {
        MediaMetrics.Item item = new MediaMetrics.Item("audio.bluetooth").set(MediaMetrics.Property.EVENT, "startBluetoothScoInt").set(MediaMetrics.Property.SCO_AUDIO_MODE, BtHelper.scoAudioModeToString(i2));
        if (!checkAudioSettingsPermission("startBluetoothSco()") || !this.mSystemReady) {
            item.set(MediaMetrics.Property.EARLY_RETURN, "permission or systemReady").record();
            return;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.startBluetoothScoForClient(iBinder, i, i2, z, str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            item.record();
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void stopBluetoothSco(IBinder iBinder) {
        if (checkAudioSettingsPermission("stopBluetoothSco()") && this.mSystemReady) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            String str = "stopBluetoothSco()) from u/pid:" + callingUid + "/" + callingPid;
            boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mDeviceBroker.stopBluetoothScoForClient(iBinder, callingUid, z, str);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                new MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.EVENT, "stopBluetoothSco").set(MediaMetrics.Property.SCO_AUDIO_MODE, BtHelper.scoAudioModeToString(-1)).record();
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x00a8, code lost:
    
        if (r11 != 100) goto L73;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int checkForRingerModeChange(int i, int i2, int i3, boolean z, String str, int i4) {
        int i5;
        int i6 = 1;
        r1 = 1;
        int i7 = 1;
        r1 = 1;
        r1 = 1;
        r1 = 1;
        r1 = 1;
        r1 = 1;
        r1 = 1;
        int i8 = 1;
        i6 = 1;
        i6 = 1;
        i6 = 1;
        i6 = 1;
        i6 = 1;
        i6 = 1;
        i6 = 1;
        if (isPlatformTelevision() || this.mIsSingleVolume) {
            return 1;
        }
        int ringerModeInternal = getRingerModeInternal();
        if (ringerModeInternal == 0) {
            if (!this.mIsSingleVolume || i2 != -1 || i < i3 * 2 || !z) {
                if (i2 == 1 || i2 == 101 || i2 == 100) {
                    if (!this.mVolumePolicy.volumeUpToExitSilent) {
                        i6 = 129;
                    } else if (this.mHasVibrator && i2 == 1) {
                        ringerModeInternal = 1;
                    }
                }
                i8 = i6 & (-2);
            }
            ringerModeInternal = 2;
            i8 = i6 & (-2);
        } else if (ringerModeInternal != 1) {
            if (ringerModeInternal != 2) {
                Log.e("AS.AudioService", "checkForRingerModeChange() wrong ringer mode: " + ringerModeInternal);
            } else if (i2 == -1) {
                if (this.mHasVibrator) {
                    if (i3 <= i && i < i3 * 2) {
                        this.mLoweredFromNormalToVibrateTime = SystemClock.uptimeMillis();
                        i5 = 1;
                        if (!isAndroidNPlus(str) && wouldToggleZenMode(i7) && !this.mNm.isNotificationPolicyAccessGrantedForPackage(str) && (i4 & IInstalld.FLAG_USE_QUOTA) == 0) {
                            throw new SecurityException("Not allowed to change Do Not Disturb state");
                        }
                        setRingerMode(i7, "AS.AudioService.checkForRingerModeChange", false);
                        this.mPrevVolDirection = i2;
                        return i5;
                    }
                } else if (i == i3 && this.mVolumePolicy.volumeDownToEnterSilent) {
                    i5 = 1;
                    i7 = 0;
                    if (!isAndroidNPlus(str)) {
                    }
                    setRingerMode(i7, "AS.AudioService.checkForRingerModeChange", false);
                    this.mPrevVolDirection = i2;
                    return i5;
                }
            } else if (this.mIsSingleVolume && (i2 == 101 || i2 == -100)) {
                i5 = 0;
                i7 = this.mHasVibrator;
                if (!isAndroidNPlus(str)) {
                }
                setRingerMode(i7, "AS.AudioService.checkForRingerModeChange", false);
                this.mPrevVolDirection = i2;
                return i5;
            }
        } else if (!this.mHasVibrator) {
            Log.e("AS.AudioService", "checkForRingerModeChange() current ringer mode is vibratebut no vibrator is present");
        } else if (i2 == -1) {
            if (!this.mIsSingleVolume || i < i3 * 2 || !z) {
                if (this.mPrevVolDirection != -1) {
                    if (!this.mVolumePolicy.volumeDownToEnterSilent) {
                        i6 = 2049;
                    } else if (SystemClock.uptimeMillis() - this.mLoweredFromNormalToVibrateTime > this.mVolumePolicy.vibrateToSilentDebounce && this.mRingerModeDelegate.canVolumeDownEnterSilent()) {
                        ringerModeInternal = 0;
                    }
                }
                i8 = i6 & (-2);
            }
            ringerModeInternal = 2;
            i8 = i6 & (-2);
        } else {
            if (i2 != 1) {
                if (i2 != 101) {
                }
            }
            ringerModeInternal = 2;
            i8 = i6 & (-2);
        }
        i5 = i8;
        i7 = ringerModeInternal;
        if (!isAndroidNPlus(str)) {
        }
        setRingerMode(i7, "AS.AudioService.checkForRingerModeChange", false);
        this.mPrevVolDirection = i2;
        return i5;
    }

    public boolean isStreamAffectedByRingerMode(int i) {
        return (this.mRingerModeAffectedStreams & (1 << i)) != 0;
    }

    public final boolean shouldZenMuteStream(int i) {
        if (this.mNm.getZenMode() == 2 && (i == 4 || i == 3 || i == 11)) {
            return true;
        }
        if (this.mNm.getZenMode() != 1) {
            return false;
        }
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNm.getConsolidatedNotificationPolicy();
        int i2 = consolidatedNotificationPolicy.priorityCategories;
        boolean z = (i2 & 32) == 0;
        boolean z2 = (i2 & 64) == 0;
        boolean z3 = (i2 & 128) == 0;
        boolean areAllPriorityOnlyRingerSoundsMuted = ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(consolidatedNotificationPolicy);
        if (z && isAlarm(i)) {
            return true;
        }
        if (z2 && isMedia(i)) {
            return true;
        }
        if (z3 && isSystem(i)) {
            return true;
        }
        return areAllPriorityOnlyRingerSoundsMuted && isNotificationOrRinger(i);
    }

    public final boolean isStreamMutedByRingerOrZenMode(int i) {
        return (sRingerAndZenModeMutedStreams & (1 << i)) != 0;
    }

    public final boolean updateZenModeAffectedStreams() {
        int i;
        if (!this.mSystemReady) {
            return false;
        }
        int zenMode = this.mNm.getZenMode();
        if (zenMode == 2) {
            i = 24;
        } else if (zenMode == 1) {
            NotificationManager.Policy consolidatedNotificationPolicy = this.mNm.getConsolidatedNotificationPolicy();
            int i2 = consolidatedNotificationPolicy.priorityCategories;
            int i3 = (i2 & 32) == 0 ? 16 : 0;
            if ((i2 & 64) == 0) {
                i3 |= 8;
            }
            int i4 = (i2 & 128) == 0 ? i3 | 2 : i3;
            i = ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(consolidatedNotificationPolicy) ? i4 | 32 | 4 : i4;
        } else {
            i = 0;
        }
        if (this.mZenModeAffectedStreams == i) {
            return false;
        }
        this.mZenModeAffectedStreams = i;
        return true;
    }

    public final boolean updateRingerAndZenModeAffectedStreams() {
        boolean updateZenModeAffectedStreams = updateZenModeAffectedStreams();
        int systemIntForUser = this.mSettings.getSystemIntForUser(this.mContentResolver, "mode_ringer_streams_affected", FrameworkStatsLog.f642x8c80549a, -2);
        if (this.mIsSingleVolume) {
            systemIntForUser = 0;
        } else {
            AudioManagerInternal.RingerModeDelegate ringerModeDelegate = this.mRingerModeDelegate;
            if (ringerModeDelegate != null) {
                systemIntForUser = ringerModeDelegate.getRingerModeAffectedStreams(systemIntForUser);
            }
        }
        int i = systemIntForUser & (-129) & (-9);
        int i2 = mStreamVolumeAlias[8] == 2 ? i | 256 : i & (-257);
        if (i2 == this.mRingerModeAffectedStreams) {
            return updateZenModeAffectedStreams;
        }
        this.mSettings.putSystemIntForUser(this.mContentResolver, "mode_ringer_streams_affected", i2, -2);
        this.mRingerModeAffectedStreams = i2;
        return true;
    }

    public boolean isStreamAffectedByMute(int i) {
        return (this.mMuteAffectedStreams & (1 << i)) != 0;
    }

    public final void ensureValidDirection(int i) {
        if (i == -100 || i == -1 || i == 0 || i == 1 || i == 100 || i == 101) {
            return;
        }
        throw new IllegalArgumentException("Bad direction " + i);
    }

    public final void ensureValidStreamType(int i) {
        if (i < 0 || i >= this.mStreamStates.length) {
            throw new IllegalArgumentException("Bad stream type " + i);
        }
    }

    public boolean isInCommunication() {
        if (this.mExt.isPttCallVolumeEnabled()) {
            return true;
        }
        TelecomManager telecomManager = (TelecomManager) this.mContext.getSystemService("telecom");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            boolean isInCall = telecomManager.isInCall();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int i = this.mMode.get();
            return isInCall || i == 3 || i == 2;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean wasStreamActiveRecently(int i, int i2) {
        return this.mAudioSystem.isStreamActive(i, i2) || this.mAudioSystem.isStreamActiveRemotely(i, i2);
    }

    public final int getActiveStreamType(int i) {
        CustomDeviceManagerProxy customDeviceManagerProxy;
        boolean isInCommunication = isInCommunication();
        if (i == Integer.MIN_VALUE && !isInCommunication && (customDeviceManagerProxy = CustomDeviceManagerProxy.getInstance()) != null) {
            int volumeControlStream = customDeviceManagerProxy.getVolumeControlStream();
            if (volumeControlStream == 1) {
                return 1;
            }
            if (volumeControlStream == 2) {
                return 2;
            }
            if (volumeControlStream == 3) {
                return 3;
            }
            if (volumeControlStream == 4) {
                return 5;
            }
        }
        if (this.mIsSingleVolume && i == Integer.MIN_VALUE) {
            return 3;
        }
        if (this.mPlatformType == 1) {
            if (isInCommunication) {
                return this.mDeviceBroker.isBluetoothScoActive() ? 6 : 0;
            }
            if (Rune.SEC_AUDIO_REMOTE_MIC && getRemoteMic() && this.mDeviceBroker.isBluetoothScoOn()) {
                Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_BLUETOOTH_SCO for remote mic");
                return 6;
            }
            if (i == Integer.MIN_VALUE) {
                return AudioStreamUtils.getActiveStreamTypeInternal(this.mPlatformType, this.mAdjustMediaVolumeOnly);
            }
            if (wasStreamActiveRecently(5, sStreamOverrideDelayMs)) {
                Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_NOTIFICATION stream active");
                return 5;
            }
            if (wasStreamActiveRecently(2, sStreamOverrideDelayMs)) {
                Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_RING stream active");
                return 2;
            }
        }
        if (isInCommunication) {
            if (this.mDeviceBroker.isBluetoothScoActive()) {
                Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_BLUETOOTH_SCO");
                return 6;
            }
            Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_VOICE_CALL");
            return 0;
        }
        if (Rune.SEC_AUDIO_REMOTE_MIC && getRemoteMic() && this.mDeviceBroker.isBluetoothScoOn()) {
            Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_BLUETOOTH_SCO for remote mic");
            return 6;
        }
        if (this.mAudioSystem.isStreamActive(5, sStreamOverrideDelayMs)) {
            Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_NOTIFICATION");
            return 5;
        }
        if (this.mAudioSystem.isStreamActive(2, sStreamOverrideDelayMs)) {
            Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_RING");
            return 2;
        }
        if (i == Integer.MIN_VALUE) {
            return AudioStreamUtils.getActiveStreamTypeInternal(this.mPlatformType, this.mAdjustMediaVolumeOnly);
        }
        Log.v("AS.AudioService", "getActiveStreamType: Returning suggested type " + i);
        return i;
    }

    public final void broadcastRingerMode(String str, int i) {
        if (this.mSystemServer.isPrivileged()) {
            Intent intent = new Intent(str);
            intent.putExtra("android.media.EXTRA_RINGER_MODE", i);
            intent.addFlags(603979776);
            sendStickyBroadcastToAll(intent);
        }
    }

    public final void broadcastVibrateSetting(int i) {
        if (this.mSystemServer.isPrivileged() && this.mActivityManagerInternal.isSystemReady()) {
            Intent intent = new Intent("android.media.VIBRATE_SETTING_CHANGED");
            intent.putExtra("android.media.EXTRA_VIBRATE_TYPE", i);
            intent.putExtra("android.media.EXTRA_VIBRATE_SETTING", getVibrateSetting(i));
            sendBroadcastToAll(intent, null);
        }
    }

    public final void queueMsgUnderWakeLock(Handler handler, int i, int i2, int i3, Object obj, int i4) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAudioEventWakeLock.acquire();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            sendMsg(handler, i, 2, i2, i3, obj, i4);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static void sendMsg(Handler handler, int i, int i2, int i3, int i4, Object obj, int i5) {
        if (i2 == 0) {
            handler.removeMessages(i);
        } else if (i2 == 1 && handler.hasMessages(i)) {
            return;
        }
        handler.sendMessageAtTime(handler.obtainMessage(i, i3, i4, obj), SystemClock.uptimeMillis() + i5);
    }

    public static void sendBundleMsg(Handler handler, int i, int i2, int i3, int i4, Object obj, Bundle bundle, int i5) {
        if (i2 == 0) {
            handler.removeMessages(i);
        } else if (i2 == 1 && handler.hasMessages(i)) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() + i5;
        Message obtainMessage = handler.obtainMessage(i, i3, i4, obj);
        obtainMessage.setData(bundle);
        handler.sendMessageAtTime(obtainMessage, uptimeMillis);
    }

    public boolean checkAudioSettingsPermission(String str) {
        if (callingOrSelfHasAudioSettingsPermission()) {
            return true;
        }
        Log.w("AS.AudioService", "Audio Settings Permission Denial: " + str + " from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
        return false;
    }

    public final boolean callingOrSelfHasAudioSettingsPermission() {
        return this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0;
    }

    public final boolean callingHasAudioSettingsPermission() {
        return this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0;
    }

    public final boolean hasAudioSettingsPermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.MODIFY_AUDIO_SETTINGS", i2, i) == 0;
    }

    public void initMinStreamVolumeWithoutModifyAudioSettings() {
        int i = Float.isNaN(AudioSystem.getStreamVolumeDB(4, MIN_STREAM_VOLUME[4], 4194304)) ? 2 : 4194304;
        int i2 = MAX_STREAM_VOLUME[4];
        while (i2 >= MIN_STREAM_VOLUME[4] && AudioSystem.getStreamVolumeDB(4, i2, i) >= -36.0f) {
            i2--;
        }
        int i3 = MIN_STREAM_VOLUME[4];
        if (i2 > i3) {
            i3 = Math.min(i2 + 1, MAX_STREAM_VOLUME[4]);
        }
        for (int i4 : mStreamVolumeAlias) {
            if (mStreamVolumeAlias[i4] == 4) {
                this.mStreamStates[i4].updateNoPermMinIndex(i3);
            }
        }
    }

    public int getDeviceForStream(int i) {
        Set deviceSetForStream = getDeviceSetForStream(i);
        if (i == 3 && this.mMode.get() == 0) {
            Iterator it = deviceSetForStream.iterator();
            while (it.hasNext()) {
                if (AudioSystem.DEVICE_OUT_ALL_SCO_SET.contains(Integer.valueOf(((Integer) it.next()).intValue()))) {
                    return 128;
                }
            }
        }
        return selectOneAudioDevice(deviceSetForStream);
    }

    public final int selectOneAudioDevice(Set set) {
        if (set.isEmpty()) {
            return 0;
        }
        if (set.size() == 1) {
            return ((Integer) set.iterator().next()).intValue();
        }
        if (set.contains(Integer.valueOf(IInstalld.FLAG_USE_QUOTA))) {
            return IInstalld.FLAG_USE_QUOTA;
        }
        if (set.contains(2)) {
            return 2;
        }
        if (set.contains(4194304)) {
            return 4194304;
        }
        if (set.contains(262144)) {
            return 262144;
        }
        if (set.contains(262145)) {
            return 262145;
        }
        if (set.contains(2097152)) {
            return 2097152;
        }
        if (set.contains(524288)) {
            return 524288;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(intValue))) {
                return intValue;
            }
        }
        Log.w("AS.AudioService", "selectOneAudioDevice returning DEVICE_NONE from invalid device combination " + AudioSystem.deviceSetToString(set));
        return 0;
    }

    public int getDeviceMaskForStream(int i) {
        ensureValidStreamType(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return AudioSystem.getDeviceMaskFromSet(getDeviceSetForStreamDirect(i));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Set getDeviceSetForStreamDirect(int i) {
        return AudioSystem.generateAudioDeviceTypesSet(getDevicesForAttributesInt(AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i), true));
    }

    public Set getDeviceSetForStream(int i) {
        Set observeDevicesForStream_syncVSS;
        ensureValidStreamType(i);
        synchronized (VolumeStreamState.class) {
            observeDevicesForStream_syncVSS = this.mStreamStates[i].observeDevicesForStream_syncVSS(true);
        }
        return observeDevicesForStream_syncVSS;
    }

    public final void onObserveDevicesForAllStreams(int i) {
        synchronized (this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                int i2 = 0;
                while (true) {
                    VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                    if (i2 < volumeStreamStateArr.length) {
                        if (i2 != i) {
                            volumeStreamStateArr[i2].observeDevicesForStream_syncVSS(false);
                        }
                        i2++;
                    }
                }
            }
        }
    }

    public void postObserveDevicesForAllStreams() {
        postObserveDevicesForAllStreams(-1);
    }

    public void postObserveDevicesForAllStreams(int i) {
        sendMsg(this.mAudioHandler, 27, 2, i, 0, null, 0);
    }

    public void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, String str, AudioDeviceAttributes audioDeviceAttributes, List list, boolean z2, int i) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.BLUETOOTH_PRIVILEGED") != 0) {
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING or BLUETOOTH_PRIVILEGED permissions");
        }
        Objects.requireNonNull(audioDeviceAttributes);
        Objects.requireNonNull(list);
        int internalType = audioDeviceAttributes.getInternalType();
        if (z) {
            AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo = new AbsoluteVolumeDeviceInfo(audioDeviceAttributes, list, iAudioDeviceVolumeDispatcher, z2, i);
            AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo2 = (AbsoluteVolumeDeviceInfo) this.mAbsoluteVolumeDeviceInfoMap.get(Integer.valueOf(internalType));
            if (absoluteVolumeDeviceInfo2 != null && absoluteVolumeDeviceInfo2.mDeviceVolumeBehavior == i) {
                r11 = false;
            }
            if (r11) {
                removeAudioSystemDeviceOutFromFullVolumeDevices(internalType);
                removeAudioSystemDeviceOutFromFixedVolumeDevices(internalType);
                addAudioSystemDeviceOutToAbsVolumeDevices(internalType, absoluteVolumeDeviceInfo);
                dispatchDeviceVolumeBehavior(audioDeviceAttributes, i);
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                VolumeInfo volumeInfo = (VolumeInfo) it.next();
                if (volumeInfo.getVolumeIndex() != -100 && volumeInfo.getMinVolumeIndex() != -100 && volumeInfo.getMaxVolumeIndex() != -100) {
                    if (volumeInfo.hasStreamType()) {
                        setStreamVolumeInt(volumeInfo.getStreamType(), rescaleIndex(volumeInfo, volumeInfo.getStreamType()), internalType, false, str, true);
                    } else {
                        for (int i2 : volumeInfo.getVolumeGroup().getLegacyStreamTypes()) {
                            setStreamVolumeInt(i2, rescaleIndex(volumeInfo, i2), internalType, false, str, true);
                        }
                    }
                }
            }
            return;
        }
        if (removeAudioSystemDeviceOutFromAbsVolumeDevices(internalType) != null) {
            dispatchDeviceVolumeBehavior(audioDeviceAttributes, 0);
        }
    }

    public void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
        super.setDeviceVolumeBehavior_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        AudioManager.enforceValidVolumeBehavior(i);
        sVolumeLogger.enqueue(new EventLogger.StringEvent("setDeviceVolumeBehavior: dev:" + AudioSystem.getOutputDeviceName(audioDeviceAttributes.getInternalType()) + " addr:" + audioDeviceAttributes.getAddress() + " behavior:" + AudioDeviceVolumeManager.volumeBehaviorName(i) + " pack:" + str).printLog("AS.AudioService"));
        if (str == null) {
            str = "";
        }
        if (audioDeviceAttributes.getType() == 8) {
            avrcpSupportsAbsoluteVolume(audioDeviceAttributes.getAddress(), i == 3);
        } else {
            setDeviceVolumeBehaviorInternal(audioDeviceAttributes, i, str);
            persistDeviceVolumeBehavior(audioDeviceAttributes.getInternalType(), i);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x002c, code lost:
    
        if (removeAudioSystemDeviceOutFromAbsVolumeDevices(r0) != null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004f, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0050, code lost:
    
        r2 = false | (r1 | r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003c, code lost:
    
        if (removeAudioSystemDeviceOutFromAbsVolumeDevices(r0) != null) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x004c, code lost:
    
        if (removeAudioSystemDeviceOutFromAbsVolumeDevices(r0) != null) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setDeviceVolumeBehaviorInternal(AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
        boolean removeAudioSystemDeviceOutFromFullVolumeDevices;
        int internalType = audioDeviceAttributes.getInternalType();
        boolean z = true;
        boolean z2 = false;
        if (i == 0) {
            removeAudioSystemDeviceOutFromFullVolumeDevices = removeAudioSystemDeviceOutFromFullVolumeDevices(internalType) | removeAudioSystemDeviceOutFromFixedVolumeDevices(internalType);
        } else if (i == 1) {
            removeAudioSystemDeviceOutFromFullVolumeDevices = addAudioSystemDeviceOutToFullVolumeDevices(internalType) | removeAudioSystemDeviceOutFromFixedVolumeDevices(internalType);
        } else if (i == 2) {
            removeAudioSystemDeviceOutFromFullVolumeDevices = removeAudioSystemDeviceOutFromFullVolumeDevices(internalType) | addAudioSystemDeviceOutToFixedVolumeDevices(internalType);
        } else if (i == 3 || i == 4 || i == 5) {
            throw new IllegalArgumentException("Absolute volume unsupported for now");
        }
        if (z2) {
            sendMsg(this.mAudioHandler, 47, 2, i, 0, audioDeviceAttributes, 0);
        }
        sDeviceLogger.enqueue(new EventLogger.StringEvent("Volume behavior " + i + " for dev=0x" + Integer.toHexString(internalType) + " from:" + str));
        StringBuilder sb = new StringBuilder();
        sb.append("setDeviceVolumeBehavior:");
        sb.append(str);
        postUpdateVolumeStatesForAudioDevice(internalType, sb.toString());
    }

    public int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) {
        super.getDeviceVolumeBehavior_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        return getDeviceVolumeBehaviorInt(audioDeviceAttributes);
    }

    public final int getDeviceVolumeBehaviorInt(AudioDeviceAttributes audioDeviceAttributes) {
        int internalType = audioDeviceAttributes.getInternalType();
        if (this.mFullVolumeDevices.contains(Integer.valueOf(internalType))) {
            return 1;
        }
        if (this.mFixedVolumeDevices.contains(Integer.valueOf(internalType))) {
            return 2;
        }
        if (this.mAbsVolumeMultiModeCaseDevices.contains(Integer.valueOf(internalType))) {
            return 4;
        }
        if (this.mAbsoluteVolumeDeviceInfoMap.containsKey(Integer.valueOf(internalType))) {
            return ((AbsoluteVolumeDeviceInfo) this.mAbsoluteVolumeDeviceInfoMap.get(Integer.valueOf(internalType))).mDeviceVolumeBehavior;
        }
        return (isA2dpAbsoluteVolumeDevice(internalType) || AudioSystem.isLeAudioDeviceType(internalType)) ? 3 : 0;
    }

    public boolean isVolumeFixed() {
        if (this.mUseFixedVolume) {
            return true;
        }
        Iterator it = getDevicesForAttributesInt(new AudioAttributes.Builder().setUsage(1).build(), true).iterator();
        while (it.hasNext()) {
            if (getDeviceVolumeBehaviorInt((AudioDeviceAttributes) it.next()) == 2) {
                return true;
            }
        }
        return false;
    }

    public void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
        super.setWiredDeviceConnectionState_enforcePermission();
        if (i != 1 && i != 0) {
            throw new IllegalArgumentException("Invalid state " + i);
        }
        new MediaMetrics.Item("audio.service.setWiredDeviceConnectionState").set(MediaMetrics.Property.ADDRESS, audioDeviceAttributes.getAddress()).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(audioDeviceAttributes.getInternalType())).set(MediaMetrics.Property.NAME, audioDeviceAttributes.getName()).set(MediaMetrics.Property.STATE, i == 1 ? "connected" : "disconnected").record();
        this.mDeviceBroker.setWiredDeviceConnectionState(audioDeviceAttributes, i, str);
        if (audioDeviceAttributes.getInternalType() == -2013265920) {
            updateHdmiAudioSystemClient();
        }
    }

    public final void updateHdmiAudioSystemClient() {
        Slog.d("AS.AudioService", "Hdmi Audio System Client is updated");
        synchronized (this.mHdmiClientLock) {
            this.mHdmiAudioSystemClient = this.mHdmiManager.getAudioSystemClient();
        }
    }

    public void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.setTestDeviceConnectionState(audioDeviceAttributes, z ? 1 : 0);
        sendMsg(this.mAudioHandler, 41, 0, 0, 0, null, 0);
    }

    public void handleBluetoothActiveDeviceChanged(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.BLUETOOTH_STACK") != 0) {
            throw new SecurityException("Bluetooth is the only caller allowed");
        }
        if (bluetoothProfileConnectionInfo == null) {
            throw new IllegalArgumentException("Illegal null BluetoothProfileConnectionInfo for device " + bluetoothDevice2 + " -> " + bluetoothDevice);
        }
        int profile = bluetoothProfileConnectionInfo.getProfile();
        if (profile != 2 && profile != 11 && profile != 22 && profile != 26 && profile != 21) {
            throw new IllegalArgumentException("Illegal BluetoothProfile profile for device " + bluetoothDevice2 + " -> " + bluetoothDevice + ". Got: " + profile);
        }
        sendMsg(this.mAudioHandler, 38, 2, 0, 0, new AudioDeviceBroker.BtDeviceChangedData(bluetoothDevice, bluetoothDevice2, bluetoothProfileConnectionInfo, "AudioService"), 0);
    }

    public void setMusicMute(boolean z) {
        this.mStreamStates[3].muteInternally(z);
    }

    public void postAccessoryPlugMediaUnmute(int i) {
        sendMsg(this.mAudioHandler, 21, 2, i, 0, null, 0);
    }

    public final void onAccessoryPlugMediaUnmute(int i) {
        Log.i("AS.AudioService", String.format("onAccessoryPlugMediaUnmute newDevice=%d [%s]", Integer.valueOf(i), AudioSystem.getOutputDeviceName(i)));
        if (this.mNm.getZenMode() == 2 || isStreamMutedByRingerOrZenMode(3) || !DEVICE_MEDIA_UNMUTED_ON_PLUG_SET.contains(Integer.valueOf(i)) || !this.mStreamStates[3].mIsMuted || this.mStreamStates[3].getIndex(i) == 0 || !getDeviceSetForStreamDirect(3).contains(Integer.valueOf(i))) {
            return;
        }
        Log.i("AS.AudioService", String.format("onAccessoryPlugMediaUnmute unmuting device=%d [%s]", Integer.valueOf(i), AudioSystem.getOutputDeviceName(i)));
        synchronized (this.mSettingsLock) {
            this.mStreamStates[3].mute(false, "onAccessoryPlugMediaUnmute");
        }
    }

    public boolean hasHapticChannels(Uri uri) {
        return AudioManager.hasHapticChannelsImpl(this.mContext, uri);
    }

    public final void initVolumeGroupStates() {
        for (AudioVolumeGroup audioVolumeGroup : getAudioVolumeGroups()) {
            try {
                ensureValidAttributes(audioVolumeGroup);
                sVolumeGroupStates.append(audioVolumeGroup.getId(), new VolumeGroupState(audioVolumeGroup));
            } catch (IllegalArgumentException unused) {
                Log.d("AS.AudioService", "volume group " + audioVolumeGroup.name() + " for internal policy needs");
            }
        }
        synchronized (this.mSettingsLock) {
            int i = 0;
            while (true) {
                SparseArray sparseArray = sVolumeGroupStates;
                if (i < sparseArray.size()) {
                    ((VolumeGroupState) sparseArray.valueAt(i)).applyAllVolumes(false);
                    i++;
                }
            }
        }
    }

    public final void ensureValidAttributes(AudioVolumeGroup audioVolumeGroup) {
        if (audioVolumeGroup.getAudioAttributes().stream().anyMatch(new Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda27
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$ensureValidAttributes$11;
                lambda$ensureValidAttributes$11 = AudioService.lambda$ensureValidAttributes$11((AudioAttributes) obj);
                return lambda$ensureValidAttributes$11;
            }
        })) {
            return;
        }
        throw new IllegalArgumentException("Volume Group " + audioVolumeGroup.name() + " has no valid audio attributes");
    }

    public static /* synthetic */ boolean lambda$ensureValidAttributes$11(AudioAttributes audioAttributes) {
        return !audioAttributes.equals(AudioProductStrategy.getDefaultAttributes());
    }

    public final void readVolumeGroupsSettings(boolean z) {
        synchronized (this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                Log.d("AS.AudioService", "readVolumeGroupsSettings userSwitch=" + z);
                int i = 0;
                while (true) {
                    SparseArray sparseArray = sVolumeGroupStates;
                    if (i < sparseArray.size()) {
                        VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray.valueAt(i);
                        if (!z || !volumeGroupState.isMusic()) {
                            volumeGroupState.clearIndexCache();
                            volumeGroupState.readSettings();
                        }
                        volumeGroupState.applyAllVolumes(z);
                        i++;
                    }
                }
            }
        }
    }

    public final void restoreVolumeGroups() {
        Log.v("AS.AudioService", "restoreVolumeGroups");
        synchronized (this.mSettingsLock) {
            int i = 0;
            while (true) {
                SparseArray sparseArray = sVolumeGroupStates;
                if (i < sparseArray.size()) {
                    ((VolumeGroupState) sparseArray.valueAt(i)).applyAllVolumes(false);
                    i++;
                }
            }
        }
    }

    public final void dumpVolumeGroups(PrintWriter printWriter) {
        printWriter.println("\nVolume Groups (device: index)");
        int i = 0;
        while (true) {
            SparseArray sparseArray = sVolumeGroupStates;
            if (i >= sparseArray.size()) {
                return;
            }
            ((VolumeGroupState) sparseArray.valueAt(i)).dump(printWriter);
            printWriter.println("");
            i++;
        }
    }

    public static int getVolumeGroupForStreamType(int i) {
        AudioAttributes audioAttributesForStrategyWithLegacyStreamType = AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i);
        if (audioAttributesForStrategyWithLegacyStreamType.equals(new AudioAttributes.Builder().build())) {
            return -1;
        }
        return AudioProductStrategy.getVolumeGroupIdForAudioAttributes(audioAttributesForStrategyWithLegacyStreamType, false);
    }

    public class VolumeGroupState {
        public AudioAttributes mAudioAttributes;
        public final AudioVolumeGroup mAudioVolumeGroup;
        public boolean mHasValidStreamType;
        public final SparseIntArray mIndexMap;
        public int mIndexMax;
        public int mIndexMin;
        public boolean mIsMuted;
        public int mPublicStreamType;
        public String mSettingName;

        public final int getDeviceForVolume() {
            return AudioService.this.getDeviceForStream(this.mPublicStreamType);
        }

        public VolumeGroupState(AudioVolumeGroup audioVolumeGroup) {
            this.mIndexMap = new SparseIntArray(8);
            int i = 0;
            this.mHasValidStreamType = false;
            this.mPublicStreamType = 3;
            this.mAudioAttributes = AudioProductStrategy.getDefaultAttributes();
            this.mIsMuted = false;
            this.mAudioVolumeGroup = audioVolumeGroup;
            Log.v("AS.AudioService", "VolumeGroupState for " + audioVolumeGroup.toString());
            Iterator it = audioVolumeGroup.getAudioAttributes().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                AudioAttributes audioAttributes = (AudioAttributes) it.next();
                if (!audioAttributes.equals(this.mAudioAttributes)) {
                    this.mAudioAttributes = audioAttributes;
                    break;
                }
            }
            int[] legacyStreamTypes = this.mAudioVolumeGroup.getLegacyStreamTypes();
            String str = "";
            if (legacyStreamTypes.length != 0) {
                int length = legacyStreamTypes.length;
                while (true) {
                    if (i < length) {
                        int i2 = legacyStreamTypes[i];
                        if (i2 != -1 && i2 < AudioSystem.getNumStreamTypes()) {
                            this.mPublicStreamType = i2;
                            this.mHasValidStreamType = true;
                            str = Settings.System.VOLUME_SETTINGS_INT[i2];
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
                int[] iArr = AudioService.MIN_STREAM_VOLUME;
                int i3 = this.mPublicStreamType;
                this.mIndexMin = iArr[i3];
                this.mIndexMax = AudioService.MAX_STREAM_VOLUME[i3];
            } else if (!audioVolumeGroup.getAudioAttributes().isEmpty()) {
                this.mIndexMin = AudioSystem.getMinVolumeIndexForAttributes(this.mAudioAttributes);
                this.mIndexMax = AudioSystem.getMaxVolumeIndexForAttributes(this.mAudioAttributes);
            } else {
                throw new IllegalArgumentException("volume group: " + this.mAudioVolumeGroup.name() + " has neither valid attributes nor valid stream types assigned");
            }
            if (str.isEmpty()) {
                str = "volume_" + name();
            }
            this.mSettingName = str;
            readSettings();
        }

        public int[] getLegacyStreamTypes() {
            return this.mAudioVolumeGroup.getLegacyStreamTypes();
        }

        public String name() {
            return this.mAudioVolumeGroup.name();
        }

        public final boolean isVssMuteBijective(int i) {
            return AudioService.this.isStreamAffectedByMute(i) && getMinIndex() == (AudioService.this.mStreamStates[i].mIndexMin + 5) / 10 && (getMinIndex() == 0 || AudioService.isCallStream(i));
        }

        public final boolean isMutable() {
            return this.mIndexMin == 0 || (this.mHasValidStreamType && isVssMuteBijective(this.mPublicStreamType));
        }

        public boolean mute(boolean z) {
            if (!isMutable()) {
                Log.d("AS.AudioService", "invalid mute on unmutable volume group " + name());
                return false;
            }
            boolean z2 = this.mIsMuted != z;
            if (z2) {
                this.mIsMuted = z;
            }
            return z2;
        }

        public boolean isMuted() {
            return this.mIsMuted;
        }

        public void adjustVolume(int i, int i2) {
            synchronized (AudioService.this.mSettingsLock) {
                synchronized (VolumeStreamState.class) {
                    int deviceForVolume = getDeviceForVolume();
                    int index = getIndex(deviceForVolume);
                    if (AudioService.this.isMuteAdjust(i) && !isMutable()) {
                        Log.d("AS.AudioService", "invalid mute on unmutable volume group " + name());
                        return;
                    }
                    boolean z = true;
                    if (i == -100) {
                        if (index != 0) {
                            mute(true);
                        }
                        this.mIsMuted = true;
                    } else if (i != -1) {
                        if (i == 1) {
                            setVolumeIndex(Math.min(index + 1, this.mIndexMax), deviceForVolume, i2);
                        } else if (i == 100) {
                            mute(false);
                        } else if (i == 101) {
                            if (this.mIsMuted) {
                                z = false;
                            }
                            mute(z);
                        }
                    } else if (isMuted() && index != 0) {
                        mute(false);
                    } else {
                        setVolumeIndex(Math.max(index - 1, this.mIndexMin), deviceForVolume, i2);
                    }
                }
            }
        }

        public int getVolumeIndex() {
            int index;
            synchronized (VolumeStreamState.class) {
                index = getIndex(getDeviceForVolume());
            }
            return index;
        }

        public void setVolumeIndex(int i, int i2) {
            synchronized (AudioService.this.mSettingsLock) {
                synchronized (VolumeStreamState.class) {
                    if (AudioService.this.mUseFixedVolume) {
                        return;
                    }
                    setVolumeIndex(i, getDeviceForVolume(), i2);
                }
            }
        }

        public final void setVolumeIndex(int i, int i2, int i3) {
            updateVolumeIndex(i, i2);
            if (mute(i == 0)) {
                return;
            }
            setVolumeIndexInt(getValidIndex(i), i2, i3);
        }

        public void updateVolumeIndex(int i, int i2) {
            if (this.mIndexMap.indexOfKey(i2) < 0 || this.mIndexMap.get(i2) != i) {
                this.mIndexMap.put(i2, getValidIndex(i));
                AudioService.sendMsg(AudioService.this.mAudioHandler, 2, 2, i2, 0, this, 500);
            }
        }

        public final void setVolumeIndexInt(int i, int i2, int i3) {
            if (this.mHasValidStreamType && isVssMuteBijective(this.mPublicStreamType) && AudioService.this.mStreamStates[this.mPublicStreamType].isFullyMuted()) {
                i = 0;
            } else if (this.mPublicStreamType == 6 && i == 0) {
                i = 1;
            }
            AudioSystem.setVolumeIndexForAttributes(this.mAudioAttributes, i, i2);
        }

        public final int getIndex(int i) {
            int i2 = this.mIndexMap.get(i, -1);
            return i2 != -1 ? i2 : this.mIndexMap.get(1073741824);
        }

        public int getMaxIndex() {
            return this.mIndexMax;
        }

        public int getMinIndex() {
            return this.mIndexMin;
        }

        public final boolean isValidStream(int i) {
            return i != -1 && i < AudioService.this.mStreamStates.length;
        }

        public boolean isMusic() {
            return this.mHasValidStreamType && this.mPublicStreamType == 3;
        }

        public void applyAllVolumes(boolean z) {
            int i;
            int i2;
            synchronized (VolumeStreamState.class) {
                int i3 = 0;
                while (true) {
                    i = 1073741824;
                    if (i3 >= this.mIndexMap.size()) {
                        break;
                    }
                    int keyAt = this.mIndexMap.keyAt(i3);
                    int valueAt = this.mIndexMap.valueAt(i3);
                    if (keyAt != 1073741824) {
                        boolean z2 = false;
                        for (int i4 : getLegacyStreamTypes()) {
                            if (isValidStream(i4)) {
                                boolean z3 = AudioService.this.mStreamStates[i4].mIsMuted;
                                int deviceForStream = AudioService.this.getDeviceForStream(i4);
                                int index = (AudioService.this.mStreamStates[i4].getIndex(deviceForStream) + 5) / 10;
                                if (keyAt == deviceForStream) {
                                    if (index == valueAt && isMuted() == z3 && isVssMuteBijective(i4)) {
                                        z2 = true;
                                    } else {
                                        if (index != valueAt) {
                                            AudioService.this.mStreamStates[i4].setIndex(valueAt * 10, keyAt, "from vgs", true);
                                        }
                                        if (isMuted() != z3 && isVssMuteBijective(i4)) {
                                            AudioService.this.mStreamStates[i4].mute(isMuted(), "VGS.applyAllVolumes#1");
                                        }
                                    }
                                }
                            }
                        }
                        if (!z2) {
                            Log.d("AS.AudioService", "applyAllVolumes: apply index " + valueAt + ", group " + this.mAudioVolumeGroup.name() + " and device " + AudioSystem.getOutputDeviceName(keyAt));
                            if (isMuted()) {
                                valueAt = 0;
                            }
                            setVolumeIndexInt(valueAt, keyAt, 0);
                        }
                    }
                    i3++;
                }
                int index2 = getIndex(1073741824);
                int deviceForVolume = getDeviceForVolume();
                boolean z4 = z && this.mIndexMap.indexOfKey(deviceForVolume) < 0;
                int[] legacyStreamTypes = getLegacyStreamTypes();
                int length = legacyStreamTypes.length;
                int i5 = 0;
                boolean z5 = false;
                while (i5 < length) {
                    int i6 = legacyStreamTypes[i5];
                    if (isValidStream(i6)) {
                        boolean z6 = AudioService.this.mStreamStates[i6].mIsMuted;
                        int index3 = (AudioService.this.mStreamStates[i6].getIndex(i) + 5) / 10;
                        if (z4) {
                            AudioService.this.mStreamStates[i6].setIndex(index2 * 10, deviceForVolume, "from vgs", true);
                        }
                        if (index3 == index2 && isMuted() == z6 && isVssMuteBijective(i6)) {
                            z5 = true;
                        } else {
                            if (index3 != index2) {
                                AudioService.this.mStreamStates[i6].setIndex(index2 * 10, 1073741824, "from vgs", true);
                            }
                            if (isMuted() != z6 && isVssMuteBijective(i6)) {
                                AudioService.this.mStreamStates[i6].mute(isMuted(), "VGS.applyAllVolumes#2");
                            }
                        }
                    }
                    i5++;
                    i = 1073741824;
                }
                if (!z5) {
                    Log.d("AS.AudioService", "applyAllVolumes: apply default device index " + index2 + ", group " + this.mAudioVolumeGroup.name());
                    setVolumeIndexInt(isMuted() ? 0 : index2, 1073741824, 0);
                }
                if (z4) {
                    Log.d("AS.AudioService", "applyAllVolumes: forceDeviceSync index " + index2 + ", device " + AudioSystem.getOutputDeviceName(deviceForVolume) + ", group " + this.mAudioVolumeGroup.name());
                    if (isMuted()) {
                        i2 = 0;
                        index2 = 0;
                    } else {
                        i2 = 0;
                    }
                    setVolumeIndexInt(index2, deviceForVolume, i2);
                }
            }
        }

        public void clearIndexCache() {
            this.mIndexMap.clear();
        }

        public final void persistVolumeGroup(int i) {
            if (AudioService.this.mUseFixedVolume || this.mHasValidStreamType) {
                return;
            }
            Log.v("AS.AudioService", "persistVolumeGroup: storing index " + getIndex(i) + " for group " + this.mAudioVolumeGroup.name() + ", device " + AudioSystem.getOutputDeviceName(i) + " and User=" + AudioService.this.getCurrentUserId() + " mSettingName: " + this.mSettingName);
            if (AudioService.this.mSettings.putSystemIntForUser(AudioService.this.mContentResolver, getSettingNameForDevice(i), getIndex(i), isMusic() ? 0 : -2)) {
                return;
            }
            Log.e("AS.AudioService", "persistVolumeGroup failed for group " + this.mAudioVolumeGroup.name());
        }

        public void readSettings() {
            synchronized (VolumeStreamState.class) {
                if (AudioService.this.mUseFixedVolume) {
                    this.mIndexMap.put(1073741824, this.mIndexMax);
                    return;
                }
                Iterator it = AudioSystem.DEVICE_OUT_ALL_SET.iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    int i = intValue == 1073741824 ? AudioSystem.DEFAULT_STREAM_VOLUME[this.mPublicStreamType] : -1;
                    String settingNameForDevice = getSettingNameForDevice(intValue);
                    int systemIntForUser = AudioService.this.mSettings.getSystemIntForUser(AudioService.this.mContentResolver, settingNameForDevice, i, isMusic() ? 0 : -2);
                    if (systemIntForUser != -1) {
                        if (this.mPublicStreamType == 7 && AudioService.this.mCameraSoundForced) {
                            systemIntForUser = this.mIndexMax;
                        }
                        Log.v("AS.AudioService", "readSettings: found stored index " + getValidIndex(systemIntForUser) + " for group " + this.mAudioVolumeGroup.name() + ", device: " + settingNameForDevice + ", User=" + AudioService.this.getCurrentUserId());
                        this.mIndexMap.put(intValue, getValidIndex(systemIntForUser));
                    }
                }
            }
        }

        public final int getValidIndex(int i) {
            int i2 = this.mIndexMin;
            return i < i2 ? i2 : (AudioService.this.mUseFixedVolume || i > this.mIndexMax) ? this.mIndexMax : i;
        }

        public String getSettingNameForDevice(int i) {
            if (AudioSystem.getOutputDeviceName(i).isEmpty()) {
                return this.mSettingName;
            }
            return this.mSettingName + "_" + AudioSystem.getOutputDeviceName(i);
        }

        public void setSettingName(String str) {
            this.mSettingName = str;
        }

        public final void dump(final PrintWriter printWriter) {
            printWriter.println("- VOLUME GROUP " + this.mAudioVolumeGroup.name() + XmlUtils.STRING_ARRAY_SEPARATOR);
            printWriter.print("   Muted: ");
            printWriter.println(this.mIsMuted);
            printWriter.print("   Min: ");
            printWriter.println(this.mIndexMin);
            printWriter.print("   Max: ");
            printWriter.println(this.mIndexMax);
            printWriter.print("   Current: ");
            int i = 0;
            for (int i2 = 0; i2 < this.mIndexMap.size(); i2++) {
                if (i2 > 0) {
                    printWriter.print(", ");
                }
                int keyAt = this.mIndexMap.keyAt(i2);
                printWriter.print(Integer.toHexString(keyAt));
                String outputDeviceName = keyAt == 1073741824 ? "default" : AudioSystem.getOutputDeviceName(keyAt);
                if (!outputDeviceName.isEmpty()) {
                    printWriter.print(" (");
                    printWriter.print(outputDeviceName);
                    printWriter.print(")");
                }
                printWriter.print(": ");
                printWriter.print(this.mIndexMap.valueAt(i2));
            }
            printWriter.println();
            printWriter.print("   Devices: ");
            int deviceForVolume = getDeviceForVolume();
            Iterator it = AudioSystem.DEVICE_OUT_ALL_SET.iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                if ((deviceForVolume & intValue) == intValue) {
                    int i3 = i + 1;
                    if (i > 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print(AudioSystem.getOutputDeviceName(intValue));
                    i = i3;
                }
            }
            printWriter.println();
            printWriter.print("   Streams: ");
            Arrays.stream(getLegacyStreamTypes()).forEach(new IntConsumer() { // from class: com.android.server.audio.AudioService$VolumeGroupState$$ExternalSyntheticLambda0
                @Override // java.util.function.IntConsumer
                public final void accept(int i4) {
                    AudioService.VolumeGroupState.lambda$dump$0(printWriter, i4);
                }
            });
        }

        public static /* synthetic */ void lambda$dump$0(PrintWriter printWriter, int i) {
            printWriter.print(AudioSystem.streamToString(i) + " ");
        }
    }

    public class VolumeStreamState {
        public CurrentDeviceManager mCurrentDeviceManager;
        public final SparseIntArray mIndexMap;
        public int mIndexMax;
        public int mIndexMin;
        public int mIndexMinNoPerm;
        public boolean mIsMuted;
        public boolean mIsMutedInternally;
        public Set mObservedDeviceSet;
        public final Intent mStreamDevicesChanged;
        public final Bundle mStreamDevicesChangedOptions;
        public final int mStreamType;
        public final Intent mVolumeChanged;
        public final Bundle mVolumeChangedOptions;
        public VolumeGroupState mVolumeGroupState;
        public String mVolumeIndexSettingName;

        public VolumeStreamState(String str, int i) {
            this.mVolumeGroupState = null;
            this.mIsMuted = false;
            this.mIsMutedInternally = false;
            this.mObservedDeviceSet = new TreeSet();
            this.mIndexMap = new SparseIntArray(8) { // from class: com.android.server.audio.AudioService.VolumeStreamState.1
                @Override // android.util.SparseIntArray
                public void put(int i2, int i3) {
                    super.put(i2, i3);
                    record("put", i2, i3);
                }

                @Override // android.util.SparseIntArray
                public void setValueAt(int i2, int i3) {
                    super.setValueAt(i2, i3);
                    record("setValueAt", keyAt(i2), i3);
                }

                public final void record(String str2, int i2, int i3) {
                    new MediaMetrics.Item("audio.volume." + AudioSystem.streamToString(VolumeStreamState.this.mStreamType) + "." + (i2 == 1073741824 ? "default" : AudioSystem.getOutputDeviceName(i2))).set(MediaMetrics.Property.EVENT, str2).set(MediaMetrics.Property.INDEX, Integer.valueOf(i3)).set(MediaMetrics.Property.MIN_INDEX, Integer.valueOf(VolumeStreamState.this.mIndexMin)).set(MediaMetrics.Property.MAX_INDEX, Integer.valueOf(VolumeStreamState.this.mIndexMax)).record();
                }
            };
            this.mVolumeChanged = new Intent("android.media.VOLUME_CHANGED_ACTION");
            this.mCurrentDeviceManager = new CurrentDeviceManager();
            this.mVolumeIndexSettingName = str;
            this.mStreamType = i;
            int i2 = AudioService.MIN_STREAM_VOLUME[i] * 10;
            this.mIndexMin = i2;
            this.mIndexMinNoPerm = i2;
            int i3 = AudioService.MAX_STREAM_VOLUME[i] * 10;
            this.mIndexMax = i3;
            int initStreamVolume = AudioSystem.initStreamVolume(i, i2 / 10, i3 / 10);
            if (initStreamVolume != 0) {
                AudioService.sLifecycleLogger.enqueue(new EventLogger.StringEvent("VSS() stream:" + i + " initStreamVolume=" + initStreamVolume).printLog(1, "AS.AudioService"));
                AudioService.sendMsg(AudioService.this.mAudioHandler, 34, 1, 0, 0, "VSS()", 2000);
            }
            readSettings();
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setDeliveryGroupPolicy(1);
            makeBasic.setDeliveryGroupMatchingKey("android.media.VOLUME_CHANGED_ACTION", String.valueOf(i));
            makeBasic.setDeferralPolicy(2);
            this.mVolumeChangedOptions = makeBasic.toBundle();
            Intent intent = new Intent("android.media.STREAM_DEVICES_CHANGED_ACTION");
            this.mStreamDevicesChanged = intent;
            intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", i);
            BroadcastOptions makeBasic2 = BroadcastOptions.makeBasic();
            makeBasic2.setDeliveryGroupPolicy(1);
            makeBasic2.setDeliveryGroupMatchingKey("android.media.STREAM_DEVICES_CHANGED_ACTION", String.valueOf(i));
            makeBasic2.setDeferralPolicy(2);
            this.mStreamDevicesChangedOptions = makeBasic2.toBundle();
        }

        public void setVolumeGroupState(VolumeGroupState volumeGroupState) {
            this.mVolumeGroupState = volumeGroupState;
            if (volumeGroupState != null) {
                volumeGroupState.setSettingName(this.mVolumeIndexSettingName);
            }
        }

        public void updateNoPermMinIndex(int i) {
            int i2 = i * 10;
            this.mIndexMinNoPerm = i2;
            if (i2 < this.mIndexMin) {
                Log.e("AS.AudioService", "Invalid mIndexMinNoPerm for stream " + this.mStreamType);
                this.mIndexMinNoPerm = this.mIndexMin;
            }
        }

        public Set observeDevicesForStream_syncVSS(boolean z) {
            if (!AudioService.this.mSystemServer.isPrivileged()) {
                return new TreeSet();
            }
            Set deviceSetForStreamDirect = AudioService.this.getDeviceSetForStreamDirect(this.mStreamType);
            if (deviceSetForStreamDirect.equals(this.mObservedDeviceSet)) {
                return this.mObservedDeviceSet;
            }
            int deviceMaskFromSet = AudioSystem.getDeviceMaskFromSet(deviceSetForStreamDirect);
            int deviceMaskFromSet2 = AudioSystem.getDeviceMaskFromSet(this.mObservedDeviceSet);
            this.mObservedDeviceSet = deviceSetForStreamDirect;
            if (z) {
                AudioService.this.postObserveDevicesForAllStreams(this.mStreamType);
            }
            int[] iArr = AudioService.mStreamVolumeAlias;
            int i = this.mStreamType;
            if (iArr[i] == i) {
                EventLogTags.writeStreamDevicesChanged(i, deviceMaskFromSet2, deviceMaskFromSet);
            }
            if (AudioService.this.mMultiSoundManager != null && AudioService.this.mMultiSoundManager.getPreventOverheatState() && this.mStreamType == 3) {
                AudioService.this.mMultiSoundManager.setPreventOverheatState(AudioSystem.getDeviceMaskFromSet(this.mObservedDeviceSet));
            }
            if (this.mStreamType == 3 && AudioService.this.mMode.get() == 0) {
                AudioService.sendMsg(AudioService.this.mAudioHandler, 2765, 0, deviceMaskFromSet, 0, null, 0);
                AudioService.sendMsg(AudioService.this.mAudioHandler, 2767, 0, deviceMaskFromSet, 0, null, 0);
            }
            this.mCurrentDeviceManager.changedCurrentDevice(Collections.unmodifiableSet(this.mObservedDeviceSet));
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = this.mStreamDevicesChanged;
            obtain.arg2 = this.mStreamDevicesChangedOptions;
            AudioService.sendMsg(AudioService.this.mAudioHandler, 32, 2, deviceMaskFromSet2, deviceMaskFromSet, obtain, 0);
            return this.mObservedDeviceSet;
        }

        public String getSettingNameForDevice(int i) {
            if (!hasValidSettingsName()) {
                return null;
            }
            String outputDeviceName = AudioSystem.getOutputDeviceName(i);
            if (outputDeviceName.isEmpty()) {
                return this.mVolumeIndexSettingName;
            }
            return this.mVolumeIndexSettingName + "_" + outputDeviceName;
        }

        public final boolean hasValidSettingsName() {
            String str = this.mVolumeIndexSettingName;
            return (str == null || str.isEmpty()) ? false : true;
        }

        public void setSettingName(String str) {
            this.mVolumeIndexSettingName = str;
            VolumeGroupState volumeGroupState = this.mVolumeGroupState;
            if (volumeGroupState != null) {
                volumeGroupState.setSettingName(str);
            }
        }

        public void readSettings() {
            synchronized (AudioService.this.mSettingsLock) {
                synchronized (VolumeStreamState.class) {
                    if (AudioService.this.mUseFixedVolume) {
                        this.mIndexMap.put(1073741824, this.mIndexMax);
                        return;
                    }
                    synchronized (VolumeStreamState.class) {
                        Iterator it = AudioSystem.DEVICE_OUT_ALL_SET.iterator();
                        while (it.hasNext()) {
                            int intValue = ((Integer) it.next()).intValue();
                            int i = intValue == 1073741824 ? AudioSystem.DEFAULT_STREAM_VOLUME[this.mStreamType] : -1;
                            if (hasValidSettingsName()) {
                                i = AudioService.this.mSettings.getSystemIntForUser(AudioService.this.mContentResolver, getSettingNameForDevice(intValue), i, -2);
                            }
                            if (i != -1) {
                                this.mIndexMap.put(intValue, getValidIndex(i * 10, true));
                            }
                        }
                    }
                }
            }
        }

        public final int getAbsoluteVolumeIndex(int i) {
            if (i == 0) {
                return 0;
            }
            return (this.mIndexMax + 5) / 10;
        }

        /* renamed from: setStreamVolumeIndex, reason: merged with bridge method [inline-methods] */
        public final void lambda$applyDeviceVolume_syncVSS$1(int i, int i2) {
            if (this.mStreamType == 6 && i == 0 && !isFullyMuted()) {
                if (Rune.SEC_AUDIO_REMOTE_MIC && AudioService.this.getRemoteMic()) {
                    Log.w("AS.AudioService", "SCO stream volume can be muted while remote mic is active");
                } else {
                    i = 1;
                }
            }
            int i3 = this.mStreamType;
            if (i3 == 3 && i2 != 1073741824) {
                int index = getIndex(i2) % 10;
                if (this.mIsMuted) {
                    index = 0;
                }
                final String str = "l_volume_fine_key;index=" + ((i * 10) + index) + KnoxVpnFirewallHelper.DELIMITER + "device=" + i2;
                AudioService.this.mAudioHandler.post(new Runnable() { // from class: com.android.server.audio.AudioService$VolumeStreamState$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioService.VolumeStreamState.lambda$setStreamVolumeIndex$0(str);
                    }
                });
                return;
            }
            AudioSystem.setStreamVolumeIndexAS(i3, i, i2);
        }

        public static /* synthetic */ void lambda$setStreamVolumeIndex$0(String str) {
            SemAudioSystem.setPolicyParameters(str);
            Log.i("AS.AudioService", "fine volume : " + str);
        }

        public void applyDeviceVolume_syncVSS(int i) {
            final int absoluteVolumeIndex;
            int i2;
            if (isFullyMuted()) {
                absoluteVolumeIndex = 0;
            } else if (AudioSystem.isLeAudioDeviceType(i) && AudioService.this.mMode.get() != 0 && (i2 = this.mStreamType) != 0) {
                absoluteVolumeIndex = i2 == 3 ? getIndex(i) / 10 : (getIndex(i) + 5) / 10;
            } else if (AudioService.this.isAbsoluteVolumeDevice(i) || AudioService.this.isA2dpAbsoluteVolumeDevice(i) || AudioSystem.isLeAudioDeviceType(i)) {
                absoluteVolumeIndex = getAbsoluteVolumeIndex(getIndexDividedBy10(i));
            } else if (AudioService.this.isFullVolumeDevice(i)) {
                absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
            } else if (i == 134217728) {
                absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
            } else {
                absoluteVolumeIndex = getIndex(i) / 10;
                if (i == 536870912 && AudioService.this.mAvrcpAbsVolSupported) {
                    absoluteVolumeIndex = getAbsoluteVolumeIndex(getIndexDividedBy10(i));
                }
            }
            lambda$applyDeviceVolume_syncVSS$1(absoluteVolumeIndex, i);
            AudioService.this.applyDeviceAlias(i, this.mStreamType, new DeviceAliasManager.DeviceAliasRunner() { // from class: com.android.server.audio.AudioService$VolumeStreamState$$ExternalSyntheticLambda1
                @Override // com.samsung.android.server.audio.DeviceAliasManager.DeviceAliasRunner
                public final void run(int i3) {
                    AudioService.VolumeStreamState.this.lambda$applyDeviceVolume_syncVSS$1(absoluteVolumeIndex, i3);
                }
            });
        }

        public void applyAllVolumes() {
            int absoluteVolumeIndex;
            int i;
            synchronized (VolumeStreamState.class) {
                int i2 = 0;
                boolean z = false;
                for (int i3 = 0; i3 < this.mIndexMap.size(); i3++) {
                    int keyAt = this.mIndexMap.keyAt(i3);
                    if (keyAt != 1073741824) {
                        if (isFullyMuted()) {
                            absoluteVolumeIndex = 0;
                        } else if (!AudioSystem.isLeAudioDeviceType(keyAt) || AudioService.this.mMode.get() == 0 || (i = this.mStreamType) == 0) {
                            if (!AudioService.this.isAbsoluteVolumeDevice(keyAt) && !AudioService.this.isA2dpAbsoluteVolumeDevice(keyAt) && !AudioSystem.isLeAudioDeviceType(keyAt)) {
                                if (AudioService.this.isFullVolumeDevice(keyAt)) {
                                    absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
                                } else if (keyAt == 134217728) {
                                    absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
                                } else if (this.mStreamType == 3) {
                                    absoluteVolumeIndex = this.mIndexMap.valueAt(i3) / 10;
                                } else {
                                    absoluteVolumeIndex = (this.mIndexMap.valueAt(i3) + 5) / 10;
                                }
                            }
                            if (this.mStreamType == 2) {
                                absoluteVolumeIndex = (this.mIndexMap.valueAt(i3) + 5) / 10;
                                z = false;
                            } else {
                                absoluteVolumeIndex = getAbsoluteVolumeIndex(getIndexDividedBy10(keyAt));
                                z = true;
                            }
                        } else if (i == 3) {
                            absoluteVolumeIndex = getIndex(keyAt) / 10;
                        } else {
                            absoluteVolumeIndex = (getIndex(keyAt) + 5) / 10;
                        }
                        AudioService.sendMsg(AudioService.this.mAudioHandler, 1006, 0, keyAt, z ? 1 : 0, this, 0);
                        lambda$applyDeviceVolume_syncVSS$1(absoluteVolumeIndex, keyAt);
                    }
                }
                if (!isFullyMuted()) {
                    if (this.mStreamType == 3) {
                        i2 = getIndex(1073741824) / 10;
                    } else {
                        i2 = (getIndex(1073741824) + 5) / 10;
                    }
                }
                lambda$applyDeviceVolume_syncVSS$1(i2, 1073741824);
            }
        }

        public boolean adjustIndex(int i, int i2, String str, boolean z) {
            return setIndex(getIndex(i2) + i, i2, str, z);
        }

        public boolean setIndex(int i, int i2, String str, boolean z) {
            int index;
            final int validIndex;
            boolean z2;
            synchronized (AudioService.this.mSettingsLock) {
                synchronized (VolumeStreamState.class) {
                    index = getIndex(i2);
                    validIndex = getValidIndex(i, z);
                    if (this.mStreamType == 7 && AudioService.this.mCameraSoundForced) {
                        validIndex = this.mIndexMax;
                    }
                    this.mIndexMap.put(i2, validIndex);
                    z2 = index != validIndex;
                    boolean z3 = i2 == AudioService.this.getDeviceForStream(this.mStreamType);
                    for (int numStreamTypes = AudioSystem.getNumStreamTypes() - 1; numStreamTypes >= 0; numStreamTypes--) {
                        VolumeStreamState volumeStreamState = AudioService.this.mStreamStates[numStreamTypes];
                        int i3 = this.mStreamType;
                        if (numStreamTypes != i3 && AudioService.mStreamVolumeAlias[numStreamTypes] == i3 && (z2 || !volumeStreamState.hasIndexForDevice(i2))) {
                            int rescaleIndex = AudioService.this.rescaleIndex(validIndex, this.mStreamType, numStreamTypes);
                            volumeStreamState.setIndex(rescaleIndex, i2, str, z);
                            if (z3) {
                                volumeStreamState.setIndex(rescaleIndex, AudioService.this.getDeviceForStream(numStreamTypes), str, z);
                            }
                        }
                    }
                    if (z2 && this.mStreamType == 2 && i2 == 2) {
                        for (int i4 = 0; i4 < this.mIndexMap.size(); i4++) {
                            int keyAt = this.mIndexMap.keyAt(i4);
                            if (AudioSystem.DEVICE_OUT_ALL_SCO_SET.contains(Integer.valueOf(keyAt))) {
                                this.mIndexMap.put(keyAt, validIndex);
                            }
                        }
                    }
                    AudioService.this.applyDeviceAlias(i2, this.mStreamType, new DeviceAliasManager.DeviceAliasRunner() { // from class: com.android.server.audio.AudioService$VolumeStreamState$$ExternalSyntheticLambda0
                        @Override // com.samsung.android.server.audio.DeviceAliasManager.DeviceAliasRunner
                        public final void run(int i5) {
                            AudioService.VolumeStreamState.this.lambda$setIndex$2(validIndex, i5);
                        }
                    });
                }
            }
            if (z2) {
                updateVolumeGroupIndex(i2, false);
                int i5 = this.mStreamType;
                if (i5 != 3) {
                    index = (index + 5) / 10;
                    validIndex = (validIndex + 5) / 10;
                }
                if (AudioService.mStreamVolumeAlias[i5] == i5) {
                    if (str == null) {
                        Log.w("AS.AudioService", "No caller for volume_changed event", new Throwable());
                    }
                    EventLogTags.writeVolumeChanged(this.mStreamType, index, validIndex, this.mIndexMax / 10, str);
                }
                if (this.mStreamType == 3) {
                    if ((index != 0 && validIndex == 0) || (index == 0 && validIndex != 0)) {
                        AudioService.this.sendBroadcastToSoundEventReceiver(2, validIndex != 0 ? 0 : 1, str);
                    } else {
                        AudioService.this.sendBroadcastToSoundEventReceiver(256, validIndex, str);
                    }
                }
            }
            return z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setIndex$2(int i, int i2) {
            this.mIndexMap.put(i2, i);
        }

        public int getIndex(int i) {
            int i2;
            synchronized (VolumeStreamState.class) {
                i2 = this.mIndexMap.get(i, -1);
                if (i2 == -1) {
                    i2 = this.mIndexMap.get(1073741824);
                }
            }
            return i2;
        }

        public boolean hasIndexForDevice(int i) {
            boolean z;
            synchronized (VolumeStreamState.class) {
                z = this.mIndexMap.get(i, -1) != -1;
            }
            return z;
        }

        public int getMaxIndex() {
            return this.mIndexMax;
        }

        public int getMinIndex() {
            return this.mIndexMin;
        }

        public int getMinIndex(boolean z) {
            return z ? this.mIndexMin : this.mIndexMinNoPerm;
        }

        public void setAllIndexes(VolumeStreamState volumeStreamState, String str) {
            if (this.mStreamType == volumeStreamState.mStreamType) {
                return;
            }
            int streamType = volumeStreamState.getStreamType();
            int rescaleIndex = AudioService.this.rescaleIndex(volumeStreamState.getIndex(1073741824), streamType, this.mStreamType);
            for (int i = 0; i < this.mIndexMap.size(); i++) {
                SparseIntArray sparseIntArray = this.mIndexMap;
                sparseIntArray.put(sparseIntArray.keyAt(i), rescaleIndex);
            }
            SparseIntArray sparseIntArray2 = volumeStreamState.mIndexMap;
            for (int i2 = 0; i2 < sparseIntArray2.size(); i2++) {
                setIndex(AudioService.this.rescaleIndex(sparseIntArray2.valueAt(i2), streamType, this.mStreamType), sparseIntArray2.keyAt(i2), str, true);
            }
        }

        public void setAllIndexesToMax() {
            for (int i = 0; i < this.mIndexMap.size(); i++) {
                SparseIntArray sparseIntArray = this.mIndexMap;
                sparseIntArray.put(sparseIntArray.keyAt(i), this.mIndexMax);
            }
        }

        public final void updateVolumeGroupIndex(int i, boolean z) {
            synchronized (AudioService.this.mSettingsLock) {
                synchronized (VolumeStreamState.class) {
                    if (this.mVolumeGroupState != null) {
                        int index = (getIndex(i) + 5) / 10;
                        Log.d("AS.AudioService", "updateVolumeGroupIndex for stream " + this.mStreamType + ", muted=" + this.mIsMuted + ", device=" + i + ", index=" + getIndex(i) + ", group " + this.mVolumeGroupState.name() + " Muted=" + this.mVolumeGroupState.isMuted() + ", Index=" + index + ", forceMuteState=" + z);
                        this.mVolumeGroupState.updateVolumeIndex(index, i);
                        if (isMutable()) {
                            this.mVolumeGroupState.mute(this.mIsMuted);
                        }
                    }
                }
            }
        }

        public boolean mute(boolean z, String str) {
            boolean mute;
            synchronized (VolumeStreamState.class) {
                mute = mute(z, true, str);
            }
            if (mute) {
                AudioService.this.broadcastMuteSetting(this.mStreamType, z);
            }
            return mute;
        }

        public boolean muteInternally(boolean z) {
            boolean z2;
            synchronized (VolumeStreamState.class) {
                if (z != this.mIsMutedInternally) {
                    this.mIsMutedInternally = z;
                    applyAllVolumes();
                    z2 = true;
                } else {
                    z2 = false;
                }
            }
            if (z2) {
                AudioService.sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(9, this.mStreamType, z));
            }
            return z2;
        }

        public boolean isFullyMuted() {
            return this.mIsMuted || this.mIsMutedInternally;
        }

        public final boolean isMutable() {
            return AudioService.this.isStreamAffectedByMute(this.mStreamType) && (this.mIndexMin == 0 || AudioService.isCallStream(this.mStreamType));
        }

        public boolean mute(final boolean z, boolean z2, final String str) {
            int i;
            synchronized (VolumeStreamState.class) {
                boolean z3 = z != this.mIsMuted;
                if (z3) {
                    if ((AudioService.this.mIsLeBroadCasting || (AudioService.this.isStreamMutedByRingerOrZenMode(this.mStreamType) && "setLeBroadcasting".equals(str))) && this.mIsMuted && ((i = this.mStreamType) == 1 || i == 5)) {
                        Log.e("AS.AudioService", "Do not unmuting stream " + this.mStreamType + " because of le broadcasting or ringer, zen mode");
                        return false;
                    }
                    EventLogger eventLogger = AudioService.sMuteLogger;
                    final int i2 = this.mStreamType;
                    eventLogger.enqueue(new EventLogger.Event(i2, z, str) { // from class: com.android.server.audio.AudioServiceEvents$StreamMuteEvent
                        public final boolean mMuted;
                        public final String mSource;
                        public final int mStreamType;

                        {
                            this.mStreamType = i2;
                            this.mMuted = z;
                            this.mSource = str;
                        }

                        @Override // com.android.server.utils.EventLogger.Event
                        public String eventToString() {
                            String str2;
                            int i3;
                            if (this.mStreamType <= AudioSystem.getNumStreamTypes() && (i3 = this.mStreamType) >= 0) {
                                str2 = AudioSystem.STREAM_NAMES[i3];
                            } else {
                                str2 = "stream " + this.mStreamType;
                            }
                            StringBuilder sb = new StringBuilder(str2);
                            sb.append(this.mMuted ? " muting by " : " unmuting by ");
                            sb.append(this.mSource);
                            return sb.toString();
                        }
                    });
                    if (!z && AudioService.this.isStreamMutedByRingerOrZenMode(this.mStreamType)) {
                        Log.e("AS.AudioService", "Unmuting stream " + this.mStreamType + " despite ringer-zen muted stream 0x" + Integer.toHexString(AudioService.sRingerAndZenModeMutedStreams), new Exception());
                        final int i3 = this.mStreamType;
                        final int i4 = AudioService.sRingerAndZenModeMutedStreams;
                        eventLogger.enqueue(new EventLogger.Event(i3, i4) { // from class: com.android.server.audio.AudioServiceEvents$StreamUnmuteErrorEvent
                            public final int mRingerZenMutedStreams;
                            public final int mStreamType;

                            {
                                this.mStreamType = i3;
                                this.mRingerZenMutedStreams = i4;
                            }

                            @Override // com.android.server.utils.EventLogger.Event
                            public String eventToString() {
                                String str2;
                                int i5;
                                if (this.mStreamType <= AudioSystem.getNumStreamTypes() && (i5 = this.mStreamType) >= 0) {
                                    str2 = AudioSystem.STREAM_NAMES[i5];
                                } else {
                                    str2 = "stream " + this.mStreamType;
                                }
                                return "Invalid call to unmute " + str2 + " despite muted streams 0x" + Integer.toHexString(this.mRingerZenMutedStreams);
                            }
                        });
                    }
                    this.mIsMuted = z;
                    if (z2) {
                        doMute();
                    }
                }
                return z3;
            }
        }

        public void doMute() {
            synchronized (VolumeStreamState.class) {
                updateVolumeGroupIndex(AudioService.this.getDeviceForStream(this.mStreamType), true);
                AudioService.sendMsg(AudioService.this.mAudioHandler, 10, 2, 0, 0, this, 0);
            }
        }

        public int getStreamType() {
            return this.mStreamType;
        }

        public void checkFixedVolumeDevices() {
            synchronized (VolumeStreamState.class) {
                if (AudioService.mStreamVolumeAlias[this.mStreamType] == 3) {
                    for (int i = 0; i < this.mIndexMap.size(); i++) {
                        int keyAt = this.mIndexMap.keyAt(i);
                        int valueAt = this.mIndexMap.valueAt(i);
                        if (AudioService.this.isFullVolumeDevice(keyAt) || (AudioService.this.isFixedVolumeDevice(keyAt) && valueAt != 0)) {
                            this.mIndexMap.put(keyAt, this.mIndexMax);
                        }
                        applyDeviceVolume_syncVSS(keyAt);
                    }
                }
            }
        }

        public final int getValidIndex(int i, boolean z) {
            int i2 = z ? this.mIndexMin : this.mIndexMinNoPerm;
            return i < i2 ? i2 : (AudioService.this.mUseFixedVolume || i > this.mIndexMax) ? this.mIndexMax : i;
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.print("   Muted: ");
            printWriter.println(this.mIsMuted);
            printWriter.print("   Muted Internally: ");
            printWriter.println(this.mIsMutedInternally);
            printWriter.print("   Min: ");
            printWriter.print((this.mIndexMin + 5) / 10);
            if (this.mIndexMin != this.mIndexMinNoPerm) {
                printWriter.print(" w/o perm:");
                printWriter.println((this.mIndexMinNoPerm + 5) / 10);
            } else {
                printWriter.println();
            }
            printWriter.print("   Max: ");
            printWriter.println((this.mIndexMax + 5) / 10);
            printWriter.print("   streamVolume:");
            printWriter.println(AudioService.this.getStreamVolume(this.mStreamType));
            printWriter.print("   Current: ");
            for (int i = 0; i < this.mIndexMap.size(); i++) {
                if (i > 0) {
                    printWriter.print(", ");
                }
                int keyAt = this.mIndexMap.keyAt(i);
                printWriter.print(Integer.toHexString(keyAt));
                String outputDeviceName = keyAt == 1073741824 ? "default" : AudioSystem.getOutputDeviceName(keyAt);
                if (!outputDeviceName.isEmpty()) {
                    printWriter.print(" (");
                    printWriter.print(outputDeviceName);
                    printWriter.print(")");
                }
                printWriter.print(": ");
                printWriter.print(AudioService.this.getIndexDividedBy10(this.mIndexMap.valueAt(i), this.mStreamType));
                if (this.mStreamType == 3) {
                    printWriter.print("\\");
                    printWriter.print(this.mIndexMap.valueAt(i));
                }
            }
            printWriter.println();
            printWriter.print("   Devices: ");
            printWriter.print(AudioSystem.deviceSetToString(AudioService.this.getDeviceSetForStream(this.mStreamType)));
            printWriter.println();
            printWriter.print("   Volume Group: ");
            VolumeGroupState volumeGroupState = this.mVolumeGroupState;
            printWriter.println(volumeGroupState != null ? volumeGroupState.name() : "n/a");
        }

        public int getIndexDividedBy10(int i) {
            int index = getIndex(i);
            if (this.mStreamType == 3) {
                return (index + 9) / 10;
            }
            return (index + 5) / 10;
        }
    }

    public class AudioSystemThread extends Thread {
        public AudioSystemThread() {
            super("AudioService");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Looper.prepare();
            synchronized (AudioService.this) {
                AudioService.this.mAudioHandler = AudioService.this.new AudioHandler();
                AudioService.this.notify();
            }
            Looper.loop();
        }
    }

    public final class DeviceVolumeUpdate {
        public final String mCaller;
        public final int mDevice;
        public final int mStreamType;
        public final int mVssVolIndex;

        public DeviceVolumeUpdate(int i, int i2, int i3, String str) {
            this.mStreamType = i;
            this.mVssVolIndex = i2;
            this.mDevice = i3;
            this.mCaller = str;
        }

        public DeviceVolumeUpdate(int i, int i2, String str) {
            this.mStreamType = i;
            this.mVssVolIndex = -2049;
            this.mDevice = i2;
            this.mCaller = str;
        }

        public boolean hasVolumeIndex() {
            return this.mVssVolIndex != -2049;
        }

        public int getVolumeIndex() {
            Preconditions.checkState(this.mVssVolIndex != -2049);
            return this.mVssVolIndex;
        }
    }

    public void postSetVolumeIndexOnDevice(int i, int i2, int i3, String str) {
        sendMsg(this.mAudioHandler, 26, 2, 0, 0, new DeviceVolumeUpdate(i, i2, i3, str), 0);
    }

    public void postApplyVolumeOnDevice(int i, int i2, String str) {
        sendMsg(this.mAudioHandler, 26, 2, 0, 0, new DeviceVolumeUpdate(i, i2, str), 0);
    }

    public final void onSetVolumeIndexOnDevice(DeviceVolumeUpdate deviceVolumeUpdate) {
        VolumeStreamState volumeStreamState = this.mStreamStates[deviceVolumeUpdate.mStreamType];
        if (deviceVolumeUpdate.hasVolumeIndex()) {
            int volumeIndex = deviceVolumeUpdate.getVolumeIndex();
            if (this.mSoundDoseHelper.checkSafeMediaVolume(deviceVolumeUpdate.mStreamType, volumeIndex, deviceVolumeUpdate.mDevice)) {
                volumeIndex = this.mSoundDoseHelper.safeMediaVolumeIndex(deviceVolumeUpdate.mDevice);
            }
            volumeStreamState.setIndex(volumeIndex, deviceVolumeUpdate.mDevice, deviceVolumeUpdate.mCaller, true);
            sVolumeLogger.enqueue(new EventLogger.StringEvent(deviceVolumeUpdate.mCaller + " dev:0x" + Integer.toHexString(deviceVolumeUpdate.mDevice) + " volIdx:" + volumeIndex));
        } else if ("makeLeAudioDeviceAvailable".equals(deviceVolumeUpdate.mCaller)) {
            if (deviceVolumeUpdate.mDevice == 536870914 && isLeBroadcastWithoutLeDevice()) {
                volumeStreamState.setIndex(150, deviceVolumeUpdate.mDevice, deviceVolumeUpdate.mCaller, true);
                sVolumeLogger.enqueue(new EventLogger.StringEvent(deviceVolumeUpdate.mCaller + " dev:0x" + Integer.toHexString(deviceVolumeUpdate.mDevice) + " broadcast volIdx to 150"));
            }
            sendSetDeviceAbsoluteVolume(deviceVolumeUpdate.mDevice);
        } else {
            sVolumeLogger.enqueue(new EventLogger.StringEvent(deviceVolumeUpdate.mCaller + " update vol on dev:0x" + Integer.toHexString(deviceVolumeUpdate.mDevice)));
        }
        setDeviceVolume(volumeStreamState, deviceVolumeUpdate.mDevice);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0030 A[Catch: all -> 0x0088, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000f, B:8:0x0015, B:12:0x0020, B:14:0x0030, B:16:0x0036, B:18:0x0040, B:20:0x0046, B:22:0x004c, B:24:0x0052, B:26:0x0058, B:27:0x005f, B:30:0x0070, B:32:0x0077, B:38:0x007a), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setDeviceVolume(VolumeStreamState volumeStreamState, int i) {
        int i2;
        int numStreamTypes;
        synchronized (VolumeStreamState.class) {
            AudioHandler audioHandler = this.mAudioHandler;
            if (!isAbsoluteVolumeDevice(i) && !isA2dpAbsoluteVolumeDevice(i) && !AudioSystem.isLeAudioDeviceType(i)) {
                i2 = 0;
                sendMsg(audioHandler, 1006, 0, i, i2, volumeStreamState, 0);
                volumeStreamState.applyDeviceVolume_syncVSS(i);
                for (numStreamTypes = AudioSystem.getNumStreamTypes() - 1; numStreamTypes >= 0; numStreamTypes--) {
                    if (numStreamTypes != volumeStreamState.mStreamType && mStreamVolumeAlias[numStreamTypes] == volumeStreamState.mStreamType) {
                        int deviceForStream = getDeviceForStream(numStreamTypes);
                        if (i != deviceForStream && (isAbsoluteVolumeDevice(i) || isA2dpAbsoluteVolumeDevice(i) || AudioSystem.isLeAudioDeviceType(i))) {
                            this.mStreamStates[numStreamTypes].applyDeviceVolume_syncVSS(i);
                        }
                        this.mStreamStates[numStreamTypes].applyDeviceVolume_syncVSS(deviceForStream);
                        if (this.mMultiSoundManager.isEnabled() && i != deviceForStream) {
                            this.mStreamStates[numStreamTypes].applyDeviceVolume_syncVSS(i);
                        }
                    }
                }
            }
            i2 = 1;
            sendMsg(audioHandler, 1006, 0, i, i2, volumeStreamState, 0);
            volumeStreamState.applyDeviceVolume_syncVSS(i);
            while (numStreamTypes >= 0) {
            }
        }
        sendMsg(this.mAudioHandler, 1, 2, i, 0, volumeStreamState, 500);
    }

    public class AudioHandler extends Handler {
        public AudioHandler() {
        }

        public AudioHandler(Looper looper) {
            super(looper);
        }

        public final void setAllVolumes(VolumeStreamState volumeStreamState) {
            volumeStreamState.applyAllVolumes();
            for (int numStreamTypes = AudioSystem.getNumStreamTypes() - 1; numStreamTypes >= 0; numStreamTypes--) {
                if (numStreamTypes != volumeStreamState.mStreamType && AudioService.mStreamVolumeAlias[numStreamTypes] == volumeStreamState.mStreamType) {
                    AudioService.this.mStreamStates[numStreamTypes].applyAllVolumes();
                }
            }
        }

        public final void persistVolume(final VolumeStreamState volumeStreamState, int i) {
            if (AudioService.this.mUseFixedVolume) {
                return;
            }
            if ((!AudioService.this.mIsSingleVolume || volumeStreamState.mStreamType == 3) && volumeStreamState.hasValidSettingsName()) {
                int indexDividedBy10 = volumeStreamState.getIndexDividedBy10(i);
                if (volumeStreamState.mStreamType == 1) {
                    AudioUtils.setDeviceVolumeProperty(i, indexDividedBy10);
                }
                AudioService.this.applyDeviceAlias(i, volumeStreamState.mStreamType, new DeviceAliasManager.DeviceAliasRunner() { // from class: com.android.server.audio.AudioService$AudioHandler$$ExternalSyntheticLambda0
                    @Override // com.samsung.android.server.audio.DeviceAliasManager.DeviceAliasRunner
                    public final void run(int i2) {
                        AudioService.AudioHandler.this.lambda$persistVolume$0(volumeStreamState, i2);
                    }
                });
                AudioService.this.mSettings.putSystemIntForUser(AudioService.this.mContentResolver, volumeStreamState.getSettingNameForDevice(i), volumeStreamState.getIndexDividedBy10(i), -2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$persistVolume$0(VolumeStreamState volumeStreamState, int i) {
            AudioService.this.mSettings.putSystemIntForUser(AudioService.this.mContentResolver, volumeStreamState.getSettingNameForDevice(i), volumeStreamState.getIndexDividedBy10(i), -2);
        }

        public final void persistRingerMode(int i) {
            if (AudioService.this.mUseFixedVolume) {
                return;
            }
            AudioService.this.mSettings.putGlobalInt(AudioService.this.mContentResolver, "mode_ringer", i);
        }

        public final void onNotifyVolumeEvent(IAudioPolicyCallback iAudioPolicyCallback, int i) {
            try {
                iAudioPolicyCallback.notifyVolumeAdjust(i);
            } catch (Exception unused) {
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                AudioService.this.setDeviceVolume((VolumeStreamState) message.obj, message.arg1);
                return;
            }
            if (i == 1) {
                persistVolume((VolumeStreamState) message.obj, message.arg1);
                return;
            }
            if (i == 2) {
                ((VolumeGroupState) message.obj).persistVolumeGroup(message.arg1);
                return;
            }
            if (i == 3) {
                persistRingerMode(AudioService.this.getRingerModeInternal());
                return;
            }
            if (i == 4) {
                AudioService.this.onAudioServerDied();
                return;
            }
            if (i == 5) {
                AudioService.this.mSfxHelper.playSoundEffect(message.arg1, message.arg2);
                return;
            }
            if (i == 7) {
                LoadSoundEffectReply loadSoundEffectReply = (LoadSoundEffectReply) message.obj;
                if (AudioService.this.mSystemReady) {
                    AudioService.this.mSfxHelper.loadSoundEffects(loadSoundEffectReply);
                    return;
                }
                Log.w("AS.AudioService", "[schedule]loadSoundEffects() called before boot complete");
                if (loadSoundEffectReply != null) {
                    loadSoundEffectReply.run(false);
                    return;
                }
                return;
            }
            if (i == 8) {
                String str = (String) message.obj;
                int i2 = message.arg1;
                int i3 = message.arg2;
                new MediaMetrics.Item("audio.forceUse." + AudioSystem.forceUseUsageToString(i2)).set(MediaMetrics.Property.EVENT, "setForceUse").set(MediaMetrics.Property.FORCE_USE_DUE_TO, str).set(MediaMetrics.Property.FORCE_USE_MODE, AudioSystem.forceUseConfigToString(i3)).record();
                AudioService.sForceUseLogger.enqueue(new AudioServiceEvents$ForceUseEvent(i2, i3, str));
                AudioService.this.mAudioSystem.setForceUse(i2, i3);
                return;
            }
            if (i == 10) {
                setAllVolumes((VolumeStreamState) message.obj);
                return;
            }
            if (i == 15) {
                AudioService.this.mSfxHelper.unloadSoundEffects();
                return;
            }
            if (i == 16) {
                AudioService.this.onSystemReady();
                return;
            }
            switch (i) {
                case 18:
                    AudioService.this.onUnmuteStream(message.arg1, message.arg2);
                    return;
                case 19:
                    AudioService.this.onDynPolicyMixStateUpdate((String) message.obj, message.arg1);
                    return;
                case 20:
                    AudioService.this.onIndicateSystemReady();
                    return;
                case 21:
                    AudioService.this.onAccessoryPlugMediaUnmute(message.arg1);
                    return;
                case 22:
                    onNotifyVolumeEvent((IAudioPolicyCallback) message.obj, message.arg1);
                    return;
                case 23:
                    AudioService.this.onDispatchAudioServerStateChange(message.arg1 == 1);
                    return;
                case 24:
                    AudioService.this.onEnableSurroundFormats((ArrayList) message.obj);
                    return;
                case 25:
                    AudioService.this.onUpdateRingerModeServiceInt();
                    return;
                case 26:
                    AudioService.this.onSetVolumeIndexOnDevice((DeviceVolumeUpdate) message.obj);
                    if ("muteMediaStreamOfSpeaker".equals(((DeviceVolumeUpdate) message.obj).mCaller)) {
                        AudioService.this.sendVolumeUpdate(3, message.arg1, message.arg2, 0, 2);
                        return;
                    }
                    return;
                case 27:
                    AudioService.this.onObserveDevicesForAllStreams(message.arg1);
                    return;
                case 28:
                    AudioService.this.onCheckVolumeCecOnHdmiConnection(message.arg1, (String) message.obj);
                    return;
                case 29:
                    AudioService.this.onPlaybackConfigChange((List) message.obj);
                    return;
                case 30:
                    AudioService.this.mSystemServer.sendMicrophoneMuteChangedIntent();
                    return;
                case 31:
                    synchronized (AudioService.this.mDeviceBroker.mSetModeLock) {
                        AudioService.this.mDeviceBroker.checkAndResetBtSco();
                        Object obj = message.obj;
                        if (obj == null) {
                            return;
                        }
                        SetModeDeathHandler setModeDeathHandler = (SetModeDeathHandler) obj;
                        if (AudioService.this.mSetModeDeathHandlers.indexOf(setModeDeathHandler) < 0) {
                            return;
                        }
                        boolean isActive = setModeDeathHandler.isActive();
                        setModeDeathHandler.setPlaybackActive(AudioService.this.isPlaybackActiveForUid(setModeDeathHandler.getUid()));
                        setModeDeathHandler.setRecordingActive(AudioService.this.isRecordingActiveForUid(setModeDeathHandler.getUid()));
                        if (isActive != setModeDeathHandler.isActive()) {
                            AudioService.this.onUpdateAudioMode(-1, Process.myPid(), AudioService.this.mContext.getPackageName(), false);
                        }
                        return;
                    }
                case 32:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    Intent intent = (Intent) someArgs.arg1;
                    Bundle bundle = (Bundle) someArgs.arg2;
                    someArgs.recycle();
                    AudioService.this.sendBroadcastToAll(intent.putExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", message.arg1).putExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", message.arg2), bundle);
                    if (!Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI || AudioService.this.mMicModeManager == null) {
                        return;
                    }
                    AudioService.this.mMicModeManager.streamDevicesChanged(intent.putExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", message.arg1).putExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", message.arg2));
                    return;
                case 33:
                    AudioService.this.onUpdateVolumeStatesForAudioDevice(message.arg1, (String) message.obj);
                    return;
                case 34:
                    AudioService.this.onReinitVolumes((String) message.obj);
                    return;
                case 35:
                    AudioService.this.onUpdateAccessibilityServiceUids();
                    return;
                case 36:
                    synchronized (AudioService.this.mDeviceBroker.mSetModeLock) {
                        AudioService.this.onUpdateAudioMode(message.arg1, message.arg2, (String) message.obj, false);
                    }
                    return;
                case 37:
                    AudioService.this.onRecordingConfigChange((List) message.obj);
                    return;
                case 38:
                    AudioService.this.mDeviceBroker.queueOnBluetoothActiveDeviceChanged((AudioDeviceBroker.BtDeviceChangedData) message.obj);
                    return;
                default:
                    switch (i) {
                        case 40:
                            AudioService.this.dispatchMode(message.arg1);
                            return;
                        case 41:
                            AudioService.this.onRoutingUpdatedFromAudioThread();
                            return;
                        case 42:
                            AudioService.this.mSpatializerHelper.onInitSensors();
                            return;
                        case 43:
                            AudioService.this.onPersistSpatialAudioDeviceSettings();
                            return;
                        case 44:
                            AudioService.this.onAddAssistantServiceUids(new int[]{message.arg1});
                            return;
                        case 45:
                            AudioService.this.onRemoveAssistantServiceUids(new int[]{message.arg1});
                            return;
                        case 46:
                            AudioService.this.updateActiveAssistantServiceUids();
                            return;
                        case 47:
                            AudioService.this.dispatchDeviceVolumeBehavior((AudioDeviceAttributes) message.obj, message.arg1);
                            return;
                        case 48:
                            AudioService.this.mAudioSystem.setParameters((String) message.obj);
                            return;
                        case 49:
                            AudioService.this.mAudioSystem.setParameters((String) message.obj);
                            return;
                        case 50:
                            AudioService.this.mSpatializerHelper.reset(AudioService.this.mHasSpatializerEffect);
                            return;
                        case 51:
                            AudioService.this.mPlaybackMonitor.ignorePlayerIId(message.arg1);
                            return;
                        case 52:
                            AudioService.this.onDispatchPreferredMixerAttributesChanged(message.getData(), message.arg1);
                            return;
                        case 53:
                            AudioService.this.onLowerVolumeToRs1();
                            return;
                        case 54:
                            AudioService.this.onConfigurationChanged();
                            return;
                        default:
                            switch (i) {
                                case 100:
                                    AudioService.this.mPlaybackMonitor.disableAudioForUid(message.arg1 == 1, message.arg2);
                                    AudioService.this.mAudioEventWakeLock.release();
                                    return;
                                case 101:
                                    AudioService.this.onInitStreamsAndVolumes();
                                    AudioService.this.mAudioEventWakeLock.release();
                                    return;
                                case 102:
                                    AudioService.this.onInitSpatializer();
                                    AudioService.this.mAudioEventWakeLock.release();
                                    return;
                                default:
                                    if (i > 2758) {
                                        AudioService.this.handleCustomMessage(message);
                                        return;
                                    } else {
                                        if (i >= 1000) {
                                            AudioService.this.mSoundDoseHelper.handleMessage(message);
                                            return;
                                        }
                                        return;
                                    }
                            }
                    }
            }
        }
    }

    public class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(new Handler());
            AudioService.this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("zen_mode"), false, this);
            AudioService.this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("zen_mode_config_etag"), false, this);
            AudioService.this.mContentResolver.registerContentObserver(Settings.System.getUriFor("mode_ringer_streams_affected"), false, this);
            AudioService.this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("dock_audio_media_enabled"), false, this);
            AudioService.this.mContentResolver.registerContentObserver(Settings.System.getUriFor("master_mono"), false, this, -1);
            AudioService.this.mContentResolver.registerContentObserver(Settings.System.getUriFor("master_balance"), false, this, -1);
            AudioService.this.mContentResolver.registerContentObserver(Settings.System.getUriFor("mono_audio_type"), false, this, -1);
            AudioService.this.mContentResolver.registerContentObserver(Settings.System.getUriFor("speaker_balance"), false, this, -1);
            AudioService.this.mEncodedSurroundMode = AudioService.this.mSettings.getGlobalInt(AudioService.this.mContentResolver, "encoded_surround_output", 0);
            AudioService.this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("encoded_surround_output"), false, this);
            AudioService.this.mEnabledSurroundFormats = AudioService.this.mSettings.getGlobalString(AudioService.this.mContentResolver, "encoded_surround_output_enabled_formats");
            AudioService.this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("encoded_surround_output_enabled_formats"), false, this);
            AudioService.this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("voice_interaction_service"), false, this);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            synchronized (AudioService.this.mSettingsLock) {
                if (AudioService.this.updateRingerAndZenModeAffectedStreams()) {
                    AudioService audioService = AudioService.this;
                    audioService.setRingerModeInt(audioService.getRingerModeInternal(), false);
                }
                AudioService audioService2 = AudioService.this;
                audioService2.readDockAudioSettings(audioService2.mContentResolver);
                AudioService audioService3 = AudioService.this;
                audioService3.updateMasterMono(audioService3.mContentResolver);
                AudioService audioService4 = AudioService.this;
                audioService4.updateMasterBalance(audioService4.mContentResolver);
                updateEncodedSurroundOutput();
                AudioService audioService5 = AudioService.this;
                audioService5.sendEnabledSurroundFormats(audioService5.mContentResolver, AudioService.this.mSurroundModeChanged);
                AudioService.this.updateAssistantUIdLocked(false);
            }
        }

        public final void updateEncodedSurroundOutput() {
            int globalInt = AudioService.this.mSettings.getGlobalInt(AudioService.this.mContentResolver, "encoded_surround_output", 0);
            if (AudioService.this.mEncodedSurroundMode != globalInt) {
                AudioService.this.sendEncodedSurroundMode(globalInt, "SettingsObserver");
                AudioService.this.mDeviceBroker.toggleHdmiIfConnected_Async();
                AudioService.this.mEncodedSurroundMode = globalInt;
                AudioService.this.mSurroundModeChanged = true;
                return;
            }
            AudioService.this.mSurroundModeChanged = false;
        }
    }

    public final void avrcpSupportsAbsoluteVolume(String str, boolean z) {
        sVolumeLogger.enqueue(new EventLogger.StringEvent("avrcpSupportsAbsoluteVolume addr=" + AudioManagerHelper.getAddressForLog(str) + " support=" + z).printLog("AS.AudioService"));
        boolean avrcpAbsoluteVolumeSupported = this.mDeviceBroker.setAvrcpAbsoluteVolumeSupported(z, str);
        if (Rune.SEC_AUDIO_TRANSITION_EFFECT) {
            postAvrcpSupportsAbsoluteVolumeToAudioServer(str, avrcpAbsoluteVolumeSupported);
        }
    }

    public void setAvrcpAbsoluteVolumeSupported(boolean z) {
        this.mAvrcpAbsVolSupported = z;
        sendMsg(this.mAudioHandler, 0, 2, 128, 0, this.mStreamStates[3], 0);
        sendSetDeviceAbsoluteVolume(128);
    }

    public boolean hasMediaDynamicPolicy() {
        synchronized (this.mAudioPolicies) {
            if (this.mAudioPolicies.isEmpty()) {
                return false;
            }
            Iterator it = this.mAudioPolicies.values().iterator();
            while (it.hasNext()) {
                if (((AudioPolicyProxy) it.next()).hasMixAffectingUsage(1, 3)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void checkMusicActive(int i, String str) {
        if (this.mSoundDoseHelper.safeDevicesContains(i)) {
            this.mSoundDoseHelper.scheduleMusicActiveCheck();
        }
    }

    public class AudioServiceBroadcastReceiver extends BroadcastReceiver {
        public AudioServiceBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int i = 0;
            if (action.equals("android.intent.action.DOCK_EVENT")) {
                int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                if (intExtra == 1) {
                    i = 7;
                } else if (intExtra == 2) {
                    i = 6;
                } else if (intExtra == 3) {
                    i = 8;
                } else if (intExtra == 4) {
                    i = 9;
                }
                if (intExtra != 3 && (intExtra != 0 || AudioService.this.mDockState != 3)) {
                    AudioService.this.mDeviceBroker.setForceUse_Async(3, i, "ACTION_DOCK_EVENT intent");
                }
                AudioService.this.mDesktopModeHelper.updateDexConnectionState(AudioService.this.mDockState, intExtra);
                AudioService.this.mDockState = intExtra;
                return;
            }
            if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED") || action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                AudioService.this.mDeviceBroker.receiveBtEvent(intent);
                return;
            }
            if (action.equals("android.intent.action.SCREEN_ON")) {
                if (AudioService.this.mMonitorRotation) {
                    RotationHelper.enable();
                }
                AudioSystem.setParameters("screen_state=on");
                return;
            }
            if (action.equals("android.intent.action.SCREEN_OFF")) {
                if (AudioService.this.mMonitorRotation) {
                    RotationHelper.disable();
                }
                AudioSystem.setParameters("screen_state=off");
                return;
            }
            if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                AudioService.sendMsg(AudioService.this.mAudioHandler, 54, 0, 0, 0, null, 0);
                return;
            }
            if (action.equals("android.intent.action.USER_SWITCHED")) {
                if (AudioService.this.mUserSwitchedReceived) {
                    AudioService.this.mDeviceBroker.postBroadcastBecomingNoisy();
                }
                AudioService.this.mUserSwitchedReceived = true;
                AudioService.this.mMediaFocusControl.discardAudioFocusOwner();
                if (AudioService.this.mSupportsMicPrivacyToggle) {
                    AudioService audioService = AudioService.this;
                    audioService.mMicMuteFromPrivacyToggle = audioService.mSensorPrivacyManagerInternal.isSensorPrivacyEnabled(AudioService.this.getCurrentUserId(), 1);
                    AudioService audioService2 = AudioService.this;
                    audioService2.setMicrophoneMuteNoCallerCheck(audioService2.getCurrentUserId());
                }
                AudioService.this.readAudioSettings(true);
                AudioService.sendMsg(AudioService.this.mAudioHandler, 10, 2, 0, 0, AudioService.this.mStreamStates[3], 0);
                return;
            }
            if (action.equals("android.intent.action.USER_BACKGROUND")) {
                int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                if (intExtra2 >= 0) {
                    AudioService.this.killBackgroundUserProcessesWithRecordAudioPermission(UserManagerService.getInstance().getUserInfo(intExtra2));
                }
                try {
                    UserManagerService.getInstance().setUserRestriction("no_record_audio", true, intExtra2);
                    return;
                } catch (IllegalArgumentException e) {
                    Slog.w("AS.AudioService", "Failed to apply DISALLOW_RECORD_AUDIO restriction: " + e);
                    return;
                }
            }
            if (action.equals("android.intent.action.USER_FOREGROUND")) {
                try {
                    UserManagerService.getInstance().setUserRestriction("no_record_audio", false, intent.getIntExtra("android.intent.extra.user_handle", -1));
                    return;
                } catch (IllegalArgumentException e2) {
                    Slog.w("AS.AudioService", "Failed to apply DISALLOW_RECORD_AUDIO restriction: " + e2);
                    return;
                }
            }
            if (action.equals("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION") || action.equals("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION")) {
                AudioService.this.handleAudioEffectBroadcast(context, intent);
                return;
            }
            if (action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                int[] intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                if (stringArrayExtra == null || intArrayExtra == null || stringArrayExtra.length != intArrayExtra.length) {
                    return;
                }
                while (i < intArrayExtra.length) {
                    if (!TextUtils.isEmpty(stringArrayExtra[i])) {
                        AudioService.this.mMediaFocusControl.noFocusForSuspendedApp(stringArrayExtra[i], intArrayExtra[i]);
                    }
                    i++;
                }
                return;
            }
            if (action.equals("com.android.server.audio.action.CHECK_MUSIC_ACTIVE")) {
                AudioService.this.mSoundDoseHelper.onCheckMusicActive("com.android.server.audio.action.CHECK_MUSIC_ACTIVE", AudioService.this.mAudioSystem.isStreamActive(3, 0));
            }
        }
    }

    public class AudioServiceUserRestrictionsListener implements UserManagerInternal.UserRestrictionsListener {
        public AudioServiceUserRestrictionsListener() {
        }

        @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
        public void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) {
            boolean z = bundle2.getBoolean("no_unmute_microphone");
            boolean z2 = bundle.getBoolean("no_unmute_microphone");
            if (z != z2) {
                AudioService.this.mMicMuteFromRestrictions = z2;
                AudioService.this.setMicrophoneMuteNoCallerCheck(i);
            }
            boolean z3 = true;
            boolean z4 = bundle2.getBoolean("no_adjust_volume") || bundle2.getBoolean("disallow_unmute_device");
            if (!bundle.getBoolean("no_adjust_volume") && !bundle.getBoolean("disallow_unmute_device")) {
                z3 = false;
            }
            if (z4 != z3) {
                AudioService.this.setMasterMuteInternalNoCallerCheck(z3, 0, i);
            }
        }
    }

    public final void handleAudioEffectBroadcast(Context context, Intent intent) {
        ResolveInfo resolveInfo;
        ActivityInfo activityInfo;
        String str;
        String str2 = intent.getPackage();
        if (str2 != null) {
            Log.w("AS.AudioService", "effect broadcast already targeted to " + str2);
            return;
        }
        intent.addFlags(32);
        List<ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers != null && queryBroadcastReceivers.size() != 0 && (resolveInfo = queryBroadcastReceivers.get(0)) != null && (activityInfo = resolveInfo.activityInfo) != null && (str = activityInfo.packageName) != null) {
            intent.setPackage(str);
            context.sendBroadcastAsUser(intent, UserHandle.ALL);
        } else {
            Log.w("AS.AudioService", "couldn't find receiver package for effect intent");
        }
    }

    public final void killBackgroundUserProcessesWithRecordAudioPermission(UserInfo userInfo) {
        PackageManager packageManager = this.mContext.getPackageManager();
        ComponentName homeActivityForUser = !userInfo.isManagedProfile() ? ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).getHomeActivityForUser(userInfo.id) : null;
        try {
            List list = AppGlobals.getPackageManager().getPackagesHoldingPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 0L, userInfo.id).getList();
            for (int size = list.size() - 1; size >= 0; size--) {
                PackageInfo packageInfo = (PackageInfo) list.get(size);
                if (UserHandle.getAppId(packageInfo.applicationInfo.uid) >= 10000 && packageManager.checkPermission("android.permission.INTERACT_ACROSS_USERS", packageInfo.packageName) != 0 && (homeActivityForUser == null || !packageInfo.packageName.equals(homeActivityForUser.getPackageName()) || !packageInfo.applicationInfo.isSystemApp())) {
                    try {
                        int i = packageInfo.applicationInfo.uid;
                        ActivityManager.getService().killUid(UserHandle.getAppId(i), UserHandle.getUserId(i), "killBackgroundUserProcessesWithAudioRecordPermission");
                    } catch (RemoteException e) {
                        Log.w("AS.AudioService", "Error calling killUid", e);
                    }
                }
            }
        } catch (RemoteException e2) {
            throw new AndroidRuntimeException(e2);
        }
    }

    public final boolean forceFocusDuckingForAccessibility(AudioAttributes audioAttributes, int i, int i2) {
        Bundle bundle;
        if (audioAttributes == null || audioAttributes.getUsage() != 11 || i != 3 || (bundle = audioAttributes.getBundle()) == null || !bundle.getBoolean("a11y_force_ducking")) {
            return false;
        }
        if (i2 == 0) {
            return true;
        }
        synchronized (this.mAccessibilityServiceUidsLock) {
            if (this.mAccessibilityServiceUids != null) {
                int callingUid = Binder.getCallingUid();
                int i3 = 0;
                while (true) {
                    int[] iArr = this.mAccessibilityServiceUids;
                    if (i3 >= iArr.length) {
                        break;
                    }
                    if (iArr[i3] == callingUid) {
                        return true;
                    }
                    i3++;
                }
            }
            return false;
        }
    }

    public final boolean isSupportedSystemUsage(int i) {
        synchronized (this.mSupportedSystemUsagesLock) {
            int i2 = 0;
            while (true) {
                int[] iArr = this.mSupportedSystemUsages;
                if (i2 >= iArr.length) {
                    return false;
                }
                if (iArr[i2] == i) {
                    return true;
                }
                i2++;
            }
        }
    }

    public final void validateAudioAttributesUsage(AudioAttributes audioAttributes) {
        int systemUsage = audioAttributes.getSystemUsage();
        if (AudioAttributes.isSystemUsage(systemUsage)) {
            if ((systemUsage == 17 && (audioAttributes.getAllFlags() & 65536) != 0 && callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) || callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING")) {
                if (isSupportedSystemUsage(systemUsage)) {
                    return;
                }
                throw new IllegalArgumentException("Unsupported usage " + AudioAttributes.usageToString(systemUsage));
            }
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING permission");
        }
    }

    public final boolean isValidAudioAttributesUsage(AudioAttributes audioAttributes) {
        int systemUsage = audioAttributes.getSystemUsage();
        if (AudioAttributes.isSystemUsage(systemUsage)) {
            return isSupportedSystemUsage(systemUsage) && ((systemUsage == 17 && (audioAttributes.getAllFlags() & 65536) != 0 && callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) || callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING"));
        }
        return true;
    }

    public int requestAudioFocus(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, String str3, int i2, IAudioPolicyCallback iAudioPolicyCallback, int i3) {
        if ((i2 & 8) != 0) {
            throw new IllegalArgumentException("Invalid test flag");
        }
        int callingUid = Binder.getCallingUid();
        MediaMetrics.Item item = new MediaMetrics.Item("audio.service.focus").setUid(callingUid).set(MediaMetrics.Property.CALLING_PACKAGE, str2).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "requestAudioFocus").set(MediaMetrics.Property.FLAGS, Integer.valueOf(i2));
        if (audioAttributes != null && !isValidAudioAttributesUsage(audioAttributes)) {
            Log.w("AS.AudioService", "Request using unsupported usage");
            item.set(MediaMetrics.Property.EARLY_RETURN, "Request using unsupported usage").record();
            return 0;
        }
        if ((i2 & 4) == 4) {
            if ("AudioFocus_For_Phone_Ring_And_Calls".equals(str)) {
                if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
                    Log.e("AS.AudioService", "Invalid permission to (un)lock audio focus", new Exception());
                    item.set(MediaMetrics.Property.EARLY_RETURN, "Invalid permission to (un)lock audio focus").record();
                    return 0;
                }
            } else {
                synchronized (this.mAudioPolicies) {
                    if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                        Log.e("AS.AudioService", "Invalid unregistered AudioPolicy to (un)lock audio focus");
                        item.set(MediaMetrics.Property.EARLY_RETURN, "Invalid unregistered AudioPolicy to (un)lock audio focus").record();
                        return 0;
                    }
                }
            }
        }
        if (str2 == null || str == null || audioAttributes == null) {
            Log.e("AS.AudioService", "Invalid null parameter to request audio focus");
            item.set(MediaMetrics.Property.EARLY_RETURN, "Invalid null parameter to request audio focus").record();
            return 0;
        }
        item.record();
        if (!this.mMultiSoundManager.isRemoteSubmixAppExist()) {
            this.mScreenSharingHelper.checkAndSetSplitSound(this.mDeviceBroker.checkDeviceConnected(67108988), audioAttributes, str2);
        }
        return this.mMediaFocusControl.requestAudioFocus(audioAttributes, i, iBinder, iAudioFocusDispatcher, str, str2, str3, i2, i3, forceFocusDuckingForAccessibility(audioAttributes, i, callingUid), -1);
    }

    public int requestAudioFocusForTest(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, int i2, int i3, int i4) {
        if (!enforceQueryAudioStateForTest("focus request")) {
            return 0;
        }
        if (str2 == null || str == null || audioAttributes == null) {
            Log.e("AS.AudioService", "Invalid null parameter to request audio focus");
            return 0;
        }
        return this.mMediaFocusControl.requestAudioFocus(audioAttributes, i, iBinder, iAudioFocusDispatcher, str, str2, null, i2, i4, false, i3);
    }

    public int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) {
        MediaMetrics.Item item = new MediaMetrics.Item("audio.service.focus").set(MediaMetrics.Property.CALLING_PACKAGE, str2).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "abandonAudioFocus");
        if (audioAttributes != null && !isValidAudioAttributesUsage(audioAttributes)) {
            Log.w("AS.AudioService", "Request using unsupported usage.");
            item.set(MediaMetrics.Property.EARLY_RETURN, "unsupported usage").record();
            return 0;
        }
        item.record();
        return this.mMediaFocusControl.abandonAudioFocus(iAudioFocusDispatcher, str, audioAttributes, str2);
    }

    public int abandonAudioFocusForTest(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) {
        if (enforceQueryAudioStateForTest("focus abandon")) {
            return this.mMediaFocusControl.abandonAudioFocus(iAudioFocusDispatcher, str, audioAttributes, str2);
        }
        return 0;
    }

    public void unregisterAudioFocusClient(String str) {
        new MediaMetrics.Item("audio.service.focus").set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "unregisterAudioFocusClient").record();
        this.mMediaFocusControl.unregisterAudioFocusClient(str);
    }

    public int getCurrentAudioFocus() {
        return this.mMediaFocusControl.getCurrentAudioFocus();
    }

    public int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) {
        return MediaFocusControl.getFocusRampTimeMs(i, audioAttributes);
    }

    public boolean hasAudioFocusUsers() {
        return this.mMediaFocusControl.hasAudioFocusUsers();
    }

    public long getFadeOutDurationOnFocusLossMillis(AudioAttributes audioAttributes) {
        if (enforceQueryAudioStateForTest("fade out duration")) {
            return this.mMediaFocusControl.getFadeOutDurationOnFocusLossMillis(audioAttributes);
        }
        return 0L;
    }

    public final boolean enforceQueryAudioStateForTest(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.QUERY_AUDIO_STATE") == 0) {
            return true;
        }
        Log.e("AS.AudioService", "Doesn't have QUERY_AUDIO_STATE permission for " + str + " test API", new Exception());
        return false;
    }

    public int getSpatializerImmersiveAudioLevel() {
        return this.mSpatializerHelper.getCapableImmersiveAudioLevel();
    }

    public boolean isSpatializerEnabled() {
        return this.mSpatializerHelper.isEnabled();
    }

    public boolean isSpatializerAvailable() {
        return this.mSpatializerHelper.isAvailable();
    }

    public boolean isSpatializerAvailableForDevice(AudioDeviceAttributes audioDeviceAttributes) {
        super.isSpatializerAvailableForDevice_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        Objects.requireNonNull(audioDeviceAttributes);
        return spatializerHelper.isAvailableForDevice(audioDeviceAttributes);
    }

    public boolean hasHeadTracker(AudioDeviceAttributes audioDeviceAttributes) {
        super.hasHeadTracker_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        Objects.requireNonNull(audioDeviceAttributes);
        return spatializerHelper.hasHeadTracker(audioDeviceAttributes);
    }

    public void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes) {
        super.setHeadTrackerEnabled_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        Objects.requireNonNull(audioDeviceAttributes);
        spatializerHelper.setHeadTrackerEnabled(z, audioDeviceAttributes);
    }

    public boolean isHeadTrackerEnabled(AudioDeviceAttributes audioDeviceAttributes) {
        super.isHeadTrackerEnabled_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        Objects.requireNonNull(audioDeviceAttributes);
        return spatializerHelper.isHeadTrackerEnabled(audioDeviceAttributes);
    }

    public boolean isHeadTrackerAvailable() {
        return this.mSpatializerHelper.isHeadTrackerAvailable();
    }

    public void setSpatializerEnabled(boolean z) {
        super.setSpatializerEnabled_enforcePermission();
        this.mSpatializerHelper.setFeatureEnabled(z);
    }

    public boolean canBeSpatialized(AudioAttributes audioAttributes, AudioFormat audioFormat) {
        Objects.requireNonNull(audioAttributes);
        Objects.requireNonNull(audioFormat);
        return this.mSpatializerHelper.canBeSpatialized(audioAttributes, audioFormat);
    }

    public void registerSpatializerCallback(ISpatializerCallback iSpatializerCallback) {
        Objects.requireNonNull(iSpatializerCallback);
        this.mSpatializerHelper.registerStateCallback(iSpatializerCallback);
    }

    public void unregisterSpatializerCallback(ISpatializerCallback iSpatializerCallback) {
        Objects.requireNonNull(iSpatializerCallback);
        this.mSpatializerHelper.unregisterStateCallback(iSpatializerCallback);
    }

    public void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) {
        super.registerSpatializerHeadTrackingCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerHeadTrackingModeCallback);
        this.mSpatializerHelper.registerHeadTrackingModeCallback(iSpatializerHeadTrackingModeCallback);
    }

    public void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) {
        super.unregisterSpatializerHeadTrackingCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerHeadTrackingModeCallback);
        this.mSpatializerHelper.unregisterHeadTrackingModeCallback(iSpatializerHeadTrackingModeCallback);
    }

    public void registerSpatializerHeadTrackerAvailableCallback(ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) {
        Objects.requireNonNull(iSpatializerHeadTrackerAvailableCallback);
        this.mSpatializerHelper.registerHeadTrackerAvailableCallback(iSpatializerHeadTrackerAvailableCallback, z);
    }

    public void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) {
        super.registerHeadToSoundstagePoseCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerHeadToSoundStagePoseCallback);
        this.mSpatializerHelper.registerHeadToSoundstagePoseCallback(iSpatializerHeadToSoundStagePoseCallback);
    }

    public void unregisterHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) {
        super.unregisterHeadToSoundstagePoseCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerHeadToSoundStagePoseCallback);
        this.mSpatializerHelper.unregisterHeadToSoundstagePoseCallback(iSpatializerHeadToSoundStagePoseCallback);
    }

    public List getSpatializerCompatibleAudioDevices() {
        super.getSpatializerCompatibleAudioDevices_enforcePermission();
        return this.mSpatializerHelper.getCompatibleAudioDevices();
    }

    public void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) {
        super.addSpatializerCompatibleAudioDevice_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        this.mSpatializerHelper.addCompatibleAudioDevice(audioDeviceAttributes);
    }

    public void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) {
        super.removeSpatializerCompatibleAudioDevice_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        this.mSpatializerHelper.removeCompatibleAudioDevice(audioDeviceAttributes);
    }

    public int[] getSupportedHeadTrackingModes() {
        super.getSupportedHeadTrackingModes_enforcePermission();
        return this.mSpatializerHelper.getSupportedHeadTrackingModes();
    }

    public int getActualHeadTrackingMode() {
        super.getActualHeadTrackingMode_enforcePermission();
        return this.mSpatializerHelper.getActualHeadTrackingMode();
    }

    public int getDesiredHeadTrackingMode() {
        super.getDesiredHeadTrackingMode_enforcePermission();
        return this.mSpatializerHelper.getDesiredHeadTrackingMode();
    }

    public void setSpatializerGlobalTransform(float[] fArr) {
        super.setSpatializerGlobalTransform_enforcePermission();
        Objects.requireNonNull(fArr);
        this.mSpatializerHelper.setGlobalTransform(fArr);
    }

    public void recenterHeadTracker() {
        super.recenterHeadTracker_enforcePermission();
        this.mSpatializerHelper.recenterHeadTracker();
    }

    public void setDesiredHeadTrackingMode(int i) {
        super.setDesiredHeadTrackingMode_enforcePermission();
        if (i == -1 || i == 1 || i == 2) {
            this.mSpatializerHelper.setDesiredHeadTrackingMode(i);
        }
    }

    public void setSpatializerParameter(int i, byte[] bArr) {
        super.setSpatializerParameter_enforcePermission();
        Objects.requireNonNull(bArr);
        this.mSpatializerHelper.setEffectParameter(i, bArr);
    }

    public void getSpatializerParameter(int i, byte[] bArr) {
        super.getSpatializerParameter_enforcePermission();
        Objects.requireNonNull(bArr);
        this.mSpatializerHelper.getEffectParameter(i, bArr);
    }

    public int getSpatializerOutput() {
        super.getSpatializerOutput_enforcePermission();
        return this.mSpatializerHelper.getOutput();
    }

    public void registerSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) {
        super.registerSpatializerOutputCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerOutputCallback);
        this.mSpatializerHelper.registerSpatializerOutputCallback(iSpatializerOutputCallback);
    }

    public void unregisterSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) {
        super.unregisterSpatializerOutputCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerOutputCallback);
        this.mSpatializerHelper.unregisterSpatializerOutputCallback(iSpatializerOutputCallback);
    }

    public void postInitSpatializerHeadTrackingSensors() {
        sendMsg(this.mAudioHandler, 42, 0, 0, 0, "AS.AudioService", 0);
    }

    public void postResetSpatializer() {
        sendMsg(this.mAudioHandler, 50, 0, 0, 0, "AS.AudioService", 0);
    }

    public void onInitSpatializer() {
        String secureStringForUser = this.mSettings.getSecureStringForUser(this.mContentResolver, "spatial_audio_enabled", -2);
        if (secureStringForUser == null) {
            Log.e("AS.AudioService", "error reading spatial audio device settings");
        }
        this.mSpatializerHelper.init(this.mHasSpatializerEffect, secureStringForUser);
        this.mSpatializerHelper.setFeatureEnabled(this.mHasSpatializerEffect);
    }

    public void persistSpatialAudioDeviceSettings() {
        sendMsg(this.mAudioHandler, 43, 0, 0, 0, "AS.AudioService", 1000);
    }

    public void onPersistSpatialAudioDeviceSettings() {
        String sADeviceSettings = this.mSpatializerHelper.getSADeviceSettings();
        Log.v("AS.AudioService", "saving spatial audio device settings: " + sADeviceSettings);
        if (this.mSettings.putSecureStringForUser(this.mContentResolver, "spatial_audio_enabled", sADeviceSettings, -2)) {
            return;
        }
        Log.e("AS.AudioService", "error saving spatial audio device settings: " + sADeviceSettings);
    }

    public final boolean readCameraSoundForced() {
        if (SystemProperties.getBoolean("audio.camerasound.force", false) || this.mContext.getResources().getBoolean(R.bool.config_batteryStatsResetOnUnplugAfterSignificantCharge)) {
            return true;
        }
        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        if (subscriptionManager == null) {
            Log.e("AS.AudioService", "readCameraSoundForced cannot create SubscriptionManager!");
            return false;
        }
        for (int i : subscriptionManager.getActiveSubscriptionIdList(false)) {
            if (SubscriptionManager.getResourcesForSubId(this.mContext, i).getBoolean(R.bool.config_batteryStatsResetOnUnplugAfterSignificantCharge)) {
                return true;
            }
        }
        return false;
    }

    public void muteAwaitConnection(final int[] iArr, final AudioDeviceAttributes audioDeviceAttributes, long j) {
        Objects.requireNonNull(iArr);
        Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        if (j <= 0 || iArr.length == 0) {
            throw new IllegalArgumentException("Invalid timeOutMs/usagesToMute");
        }
        Log.i("AS.AudioService", "muteAwaitConnection dev:" + audioDeviceAttributes + " timeOutMs:" + j + " usages:" + Arrays.toString(iArr));
        if (this.mDeviceBroker.isDeviceConnected(audioDeviceAttributes)) {
            Log.i("AS.AudioService", "muteAwaitConnection ignored, device (" + audioDeviceAttributes + ") already connected");
            return;
        }
        synchronized (this.mMuteAwaitConnectionLock) {
            if (this.mMutingExpectedDevice != null) {
                Log.e("AS.AudioService", "muteAwaitConnection ignored, another in progress for device:" + this.mMutingExpectedDevice);
                throw new IllegalStateException("muteAwaitConnection already in progress");
            }
            this.mMutingExpectedDevice = audioDeviceAttributes;
            this.mMutedUsagesAwaitingConnection = iArr;
            this.mPlaybackMonitor.muteAwaitConnection(iArr, audioDeviceAttributes, j);
        }
        dispatchMuteAwaitConnection(new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AudioService.lambda$muteAwaitConnection$12(audioDeviceAttributes, iArr, (IMuteAwaitConnectionCallback) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$muteAwaitConnection$12(AudioDeviceAttributes audioDeviceAttributes, int[] iArr, IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback) {
        try {
            iMuteAwaitConnectionCallback.dispatchOnMutedUntilConnection(audioDeviceAttributes, iArr);
        } catch (RemoteException unused) {
        }
    }

    public AudioDeviceAttributes getMutingExpectedDevice() {
        AudioDeviceAttributes audioDeviceAttributes;
        super.getMutingExpectedDevice_enforcePermission();
        synchronized (this.mMuteAwaitConnectionLock) {
            audioDeviceAttributes = this.mMutingExpectedDevice;
        }
        return audioDeviceAttributes;
    }

    public void cancelMuteAwaitConnection(final AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        Log.i("AS.AudioService", "cancelMuteAwaitConnection for device:" + audioDeviceAttributes);
        synchronized (this.mMuteAwaitConnectionLock) {
            AudioDeviceAttributes audioDeviceAttributes2 = this.mMutingExpectedDevice;
            if (audioDeviceAttributes2 == null) {
                Log.i("AS.AudioService", "cancelMuteAwaitConnection ignored, no expected device");
                return;
            }
            if (!audioDeviceAttributes.equalTypeAddress(audioDeviceAttributes2)) {
                Log.e("AS.AudioService", "cancelMuteAwaitConnection ignored, got " + audioDeviceAttributes + "] but expected device is" + this.mMutingExpectedDevice);
                throw new IllegalStateException("cancelMuteAwaitConnection for wrong device");
            }
            final int[] iArr = this.mMutedUsagesAwaitingConnection;
            this.mMutingExpectedDevice = null;
            this.mMutedUsagesAwaitingConnection = null;
            this.mPlaybackMonitor.cancelMuteAwaitConnection("cancelMuteAwaitConnection dev:" + audioDeviceAttributes);
            dispatchMuteAwaitConnection(new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AudioService.lambda$cancelMuteAwaitConnection$13(audioDeviceAttributes, iArr, (IMuteAwaitConnectionCallback) obj);
                }
            });
        }
    }

    public static /* synthetic */ void lambda$cancelMuteAwaitConnection$13(AudioDeviceAttributes audioDeviceAttributes, int[] iArr, IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback) {
        try {
            iMuteAwaitConnectionCallback.dispatchOnUnmutedEvent(3, audioDeviceAttributes, iArr);
        } catch (RemoteException unused) {
        }
    }

    public void registerMuteAwaitConnectionDispatcher(IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) {
        super.registerMuteAwaitConnectionDispatcher_enforcePermission();
        if (z) {
            this.mMuteAwaitConnectionDispatchers.register(iMuteAwaitConnectionCallback);
        } else {
            this.mMuteAwaitConnectionDispatchers.unregister(iMuteAwaitConnectionCallback);
        }
    }

    public void checkMuteAwaitConnection() {
        synchronized (this.mMuteAwaitConnectionLock) {
            final AudioDeviceAttributes audioDeviceAttributes = this.mMutingExpectedDevice;
            if (audioDeviceAttributes == null) {
                return;
            }
            final int[] iArr = this.mMutedUsagesAwaitingConnection;
            if (this.mDeviceBroker.isDeviceConnected(audioDeviceAttributes)) {
                this.mMutingExpectedDevice = null;
                this.mMutedUsagesAwaitingConnection = null;
                this.mPlaybackMonitor.cancelMuteAwaitConnection("checkMuteAwaitConnection device " + audioDeviceAttributes + " connected, unmuting");
                dispatchMuteAwaitConnection(new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda23
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AudioService.lambda$checkMuteAwaitConnection$14(audioDeviceAttributes, iArr, (IMuteAwaitConnectionCallback) obj);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void lambda$checkMuteAwaitConnection$14(AudioDeviceAttributes audioDeviceAttributes, int[] iArr, IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback) {
        try {
            iMuteAwaitConnectionCallback.dispatchOnUnmutedEvent(1, audioDeviceAttributes, iArr);
        } catch (RemoteException unused) {
        }
    }

    /* renamed from: onMuteAwaitConnectionTimeout, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1(final AudioDeviceAttributes audioDeviceAttributes) {
        synchronized (this.mMuteAwaitConnectionLock) {
            if (audioDeviceAttributes.equals(this.mMutingExpectedDevice)) {
                Log.i("AS.AudioService", "muteAwaitConnection timeout, clearing expected device " + this.mMutingExpectedDevice);
                final int[] iArr = this.mMutedUsagesAwaitingConnection;
                this.mMutingExpectedDevice = null;
                this.mMutedUsagesAwaitingConnection = null;
                dispatchMuteAwaitConnection(new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda17
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AudioService.lambda$onMuteAwaitConnectionTimeout$15(audioDeviceAttributes, iArr, (IMuteAwaitConnectionCallback) obj);
                    }
                });
            }
        }
    }

    public static /* synthetic */ void lambda$onMuteAwaitConnectionTimeout$15(AudioDeviceAttributes audioDeviceAttributes, int[] iArr, IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback) {
        try {
            iMuteAwaitConnectionCallback.dispatchOnUnmutedEvent(2, audioDeviceAttributes, iArr);
        } catch (RemoteException unused) {
        }
    }

    public final void dispatchMuteAwaitConnection(Consumer consumer) {
        int beginBroadcast = this.mMuteAwaitConnectionDispatchers.beginBroadcast();
        ArrayList arrayList = null;
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                consumer.accept(this.mMuteAwaitConnectionDispatchers.getBroadcastItem(i));
            } catch (Exception unused) {
                if (arrayList == null) {
                    arrayList = new ArrayList(1);
                }
                arrayList.add(this.mMuteAwaitConnectionDispatchers.getBroadcastItem(i));
            }
        }
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.mMuteAwaitConnectionDispatchers.unregister((IMuteAwaitConnectionCallback) it.next());
            }
        }
        this.mMuteAwaitConnectionDispatchers.finishBroadcast();
    }

    public void registerDeviceVolumeBehaviorDispatcher(boolean z, IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) {
        enforceQueryStateOrModifyRoutingPermission();
        Objects.requireNonNull(iDeviceVolumeBehaviorDispatcher);
        if (z) {
            this.mDeviceVolumeBehaviorDispatchers.register(iDeviceVolumeBehaviorDispatcher);
        } else {
            this.mDeviceVolumeBehaviorDispatchers.unregister(iDeviceVolumeBehaviorDispatcher);
        }
    }

    public final void dispatchDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i) {
        int beginBroadcast = this.mDeviceVolumeBehaviorDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mDeviceVolumeBehaviorDispatchers.getBroadcastItem(i2).dispatchDeviceVolumeBehaviorChanged(audioDeviceAttributes, i);
            } catch (RemoteException unused) {
            }
        }
        this.mDeviceVolumeBehaviorDispatchers.finishBroadcast();
    }

    public final void onConfigurationChanged() {
        try {
            Configuration configuration = this.mContext.getResources().getConfiguration();
            if (!Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY) {
                this.mSoundDoseHelper.configureSafeMedia(false, "AS.AudioService");
            }
            boolean readCameraSoundForced = readCameraSoundForced();
            synchronized (this.mSettingsLock) {
                boolean z = readCameraSoundForced != this.mCameraSoundForced;
                this.mCameraSoundForced = readCameraSoundForced;
                if (z) {
                    if (!this.mIsSingleVolume) {
                        synchronized (VolumeStreamState.class) {
                            VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                            VolumeStreamState volumeStreamState = volumeStreamStateArr[7];
                            if (readCameraSoundForced) {
                                volumeStreamState.setAllIndexesToMax();
                                this.mRingerModeAffectedStreams &= -129;
                            } else {
                                volumeStreamState.setAllIndexes(volumeStreamStateArr[1], "AS.AudioService");
                            }
                        }
                        setRingerModeInt(getRingerModeInternal(), false);
                    }
                    this.mDeviceBroker.setForceUse_Async(4, readCameraSoundForced ? 11 : 0, "onConfigurationChanged");
                    sendMsg(this.mAudioHandler, 10, 2, 0, 0, this.mStreamStates[7], 0);
                }
            }
            this.mVolumeController.setLayoutDirection(configuration.getLayoutDirection());
        } catch (Exception e) {
            Log.e("AS.AudioService", "Error handling configuration change: ", e);
        }
    }

    public void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.REMOTE_AUDIO_PLAYBACK", null);
        this.mRingtonePlayer = iRingtonePlayer;
    }

    public IRingtonePlayer getRingtonePlayer() {
        return this.mRingtonePlayer;
    }

    public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) {
        return this.mDeviceBroker.startWatchingRoutes(iAudioRoutesObserver);
    }

    public void disableSafeMediaVolume(String str) {
        enforceVolumeController("disable the safe media volume");
        this.mSoundDoseHelper.disableSafeMediaVolume(str);
    }

    public void lowerVolumeToRs1(String str) {
        enforceVolumeController("lowerVolumeToRs1");
        postLowerVolumeToRs1();
    }

    public void postLowerVolumeToRs1() {
        sendMsg(this.mAudioHandler, 53, 2, 0, 0, null, 0);
    }

    public final void onLowerVolumeToRs1() {
        AudioDeviceAttributes audioDeviceAttributes;
        int i;
        ArrayList devicesForAttributesInt = getDevicesForAttributesInt(new AudioAttributes.Builder().setUsage(1).build(), true);
        if (!devicesForAttributesInt.isEmpty()) {
            AudioDeviceAttributes audioDeviceAttributes2 = (AudioDeviceAttributes) devicesForAttributesInt.get(0);
            i = audioDeviceAttributes2.getInternalType();
            audioDeviceAttributes = audioDeviceAttributes2;
        } else {
            audioDeviceAttributes = new AudioDeviceAttributes(67108864, "");
            i = 67108864;
        }
        setStreamVolumeWithAttributionInt(3, this.mSoundDoseHelper.safeMediaVolumeIndex(i), 0, audioDeviceAttributes, "com.android.server.audio", "AudioService");
    }

    public float getOutputRs2UpperBound() {
        super.getOutputRs2UpperBound_enforcePermission();
        return this.mSoundDoseHelper.getOutputRs2UpperBound();
    }

    public void setOutputRs2UpperBound(float f) {
        super.setOutputRs2UpperBound_enforcePermission();
        this.mSoundDoseHelper.setOutputRs2UpperBound(f);
    }

    public float getCsd() {
        super.getCsd_enforcePermission();
        return this.mSoundDoseHelper.getCsd();
    }

    public void setCsd(float f) {
        super.setCsd_enforcePermission();
        if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            this.mSoundDoseHelper.resetCsdTimeouts();
        } else {
            this.mSoundDoseHelper.setCsd(f);
        }
    }

    public void forceUseFrameworkMel(boolean z) {
        super.forceUseFrameworkMel_enforcePermission();
        this.mSoundDoseHelper.forceUseFrameworkMel(z);
    }

    public void forceComputeCsdOnAllDevices(boolean z) {
        super.forceComputeCsdOnAllDevices_enforcePermission();
        this.mSoundDoseHelper.forceComputeCsdOnAllDevices(z);
    }

    public boolean isCsdEnabled() {
        super.isCsdEnabled_enforcePermission();
        return this.mSoundDoseHelper.isCsdEnabled();
    }

    public final void updateHdmiCecSinkLocked(boolean z) {
        if (hasDeviceVolumeBehavior(1024)) {
            return;
        }
        if (z) {
            Log.d("AS.AudioService", "CEC sink: setting HDMI as full vol device");
            setDeviceVolumeBehaviorInternal(new AudioDeviceAttributes(1024, ""), 1, "AudioService.updateHdmiCecSinkLocked()");
        } else {
            Log.d("AS.AudioService", "TV, no CEC: setting HDMI as regular vol device");
            setDeviceVolumeBehaviorInternal(new AudioDeviceAttributes(1024, ""), 0, "AudioService.updateHdmiCecSinkLocked()");
        }
        postUpdateVolumeStatesForAudioDevice(1024, "HdmiPlaybackClient.DisplayStatusCallback");
    }

    public class MyHdmiControlStatusChangeListenerCallback implements HdmiControlManager.HdmiControlStatusChangeListener {
        public MyHdmiControlStatusChangeListenerCallback() {
        }

        public void onStatusChange(int i, boolean z) {
            synchronized (AudioService.this.mHdmiClientLock) {
                if (AudioService.this.mHdmiManager == null) {
                    return;
                }
                boolean z2 = true;
                if (i != 1) {
                    z2 = false;
                }
                AudioService audioService = AudioService.this;
                if (!z2) {
                    z = false;
                }
                audioService.updateHdmiCecSinkLocked(z);
            }
        }
    }

    public class MyHdmiCecVolumeControlFeatureListener implements HdmiControlManager.HdmiCecVolumeControlFeatureListener {
        public MyHdmiCecVolumeControlFeatureListener() {
        }

        public void onHdmiCecVolumeControlFeature(int i) {
            synchronized (AudioService.this.mHdmiClientLock) {
                if (AudioService.this.mHdmiManager == null) {
                    return;
                }
                AudioService audioService = AudioService.this;
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                audioService.mHdmiCecVolumeControlEnabled = z;
            }
        }
    }

    public int setHdmiSystemAudioSupported(boolean z) {
        synchronized (this.mHdmiClientLock) {
            if (this.mHdmiManager != null) {
                if (this.mHdmiTvClient == null && this.mHdmiAudioSystemClient == null) {
                    Log.w("AS.AudioService", "Only Hdmi-Cec enabled TV or audio system device supportssystem audio mode.");
                    return 0;
                }
                if (this.mHdmiSystemAudioSupported != z) {
                    this.mHdmiSystemAudioSupported = z;
                    this.mDeviceBroker.setForceUse_Async(5, z ? 12 : 0, "setHdmiSystemAudioSupported");
                }
                r2 = getDeviceMaskForStream(3);
            }
            return r2;
        }
    }

    public boolean isHdmiSystemAudioSupported() {
        return this.mHdmiSystemAudioSupported;
    }

    public final void initA11yMonitoring() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        updateDefaultStreamOverrideDelay(accessibilityManager.isTouchExplorationEnabled());
        updateA11yVolumeAlias(accessibilityManager.isAccessibilityVolumeStreamActive());
        accessibilityManager.addTouchExplorationStateChangeListener(this, null);
        accessibilityManager.addAccessibilityServicesStateChangeListener(this);
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public void onTouchExplorationStateChanged(boolean z) {
        updateDefaultStreamOverrideDelay(z);
    }

    public final void updateDefaultStreamOverrideDelay(boolean z) {
        if (z) {
            sStreamOverrideDelayMs = 1000;
        } else {
            sStreamOverrideDelayMs = 0;
        }
        Log.d("AS.AudioService", "Touch exploration enabled=" + z + " stream override delay is now " + sStreamOverrideDelayMs + " ms");
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener
    public void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
        boolean semIsAccessibilityServiceEnabled = accessibilityManager.semIsAccessibilityServiceEnabled(32);
        this.mIsTalkBackEnabled = semIsAccessibilityServiceEnabled;
        this.mIsTalkBackEnabled = semIsAccessibilityServiceEnabled | accessibilityManager.semIsAccessibilityServiceEnabled(16);
        updateA11yVolumeAlias(accessibilityManager.isAccessibilityVolumeStreamActive());
    }

    public final void updateA11yVolumeAlias(boolean z) {
        Log.d("AS.AudioService", "Accessibility volume enabled = " + z);
        if (sIndependentA11yVolume != z) {
            sIndependentA11yVolume = z;
            updateStreamVolumeAlias(true, "AS.AudioService");
            this.mVolumeController.setA11yMode(sIndependentA11yVolume ? 1 : 0);
            this.mVolumeController.postVolumeChanged(10, 0);
        }
    }

    public boolean isCameraSoundForced() {
        boolean z;
        synchronized (this.mSettingsLock) {
            z = this.mCameraSoundForced;
        }
        return z;
    }

    public final void dumpRingerMode(PrintWriter printWriter) {
        printWriter.println("\nRinger mode: ");
        StringBuilder sb = new StringBuilder();
        sb.append("- mode (internal) = ");
        String[] strArr = RINGER_MODE_NAMES;
        sb.append(strArr[this.mRingerMode]);
        printWriter.println(sb.toString());
        printWriter.println("- mode (external) = " + strArr[this.mRingerModeExternal]);
        printWriter.println("- zen mode:" + Settings.Global.zenModeToString(this.mNm.getZenMode()));
        dumpRingerModeStreams(printWriter, "affected", this.mRingerModeAffectedStreams);
        dumpRingerModeStreams(printWriter, "muted", sRingerAndZenModeMutedStreams);
        printWriter.print("- delegate = ");
        printWriter.println(this.mRingerModeDelegate);
    }

    public final void dumpRingerModeStreams(PrintWriter printWriter, String str, int i) {
        printWriter.print("- ringer mode ");
        printWriter.print(str);
        printWriter.print(" streams = 0x");
        printWriter.print(Integer.toHexString(i));
        if (i != 0) {
            printWriter.print(" (");
            boolean z = true;
            for (int i2 = 0; i2 < AudioSystem.STREAM_NAMES.length; i2++) {
                int i3 = 1 << i2;
                if ((i & i3) != 0) {
                    if (!z) {
                        printWriter.print(',');
                    }
                    printWriter.print(AudioSystem.STREAM_NAMES[i2]);
                    i &= ~i3;
                    z = false;
                }
            }
            if (i != 0) {
                if (!z) {
                    printWriter.print(',');
                }
                printWriter.print(i);
            }
            printWriter.print(')');
        }
        printWriter.println();
    }

    public final Set getAbsoluteVolumeDevicesWithBehavior(final int i) {
        return (Set) this.mAbsoluteVolumeDeviceInfoMap.entrySet().stream().filter(new Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getAbsoluteVolumeDevicesWithBehavior$16;
                lambda$getAbsoluteVolumeDevicesWithBehavior$16 = AudioService.lambda$getAbsoluteVolumeDevicesWithBehavior$16(i, (Map.Entry) obj);
                return lambda$getAbsoluteVolumeDevicesWithBehavior$16;
            }
        }).map(new UiModeManagerService$15$$ExternalSyntheticLambda1()).collect(Collectors.toSet());
    }

    public static /* synthetic */ boolean lambda$getAbsoluteVolumeDevicesWithBehavior$16(int i, Map.Entry entry) {
        return ((AbsoluteVolumeDeviceInfo) entry.getValue()).mDeviceVolumeBehavior == i;
    }

    public final String dumpDeviceTypes(Set set) {
        Iterator it = set.iterator();
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0x" + Integer.toHexString(((Integer) it.next()).intValue()));
        while (it.hasNext()) {
            sb.append(",0x" + Integer.toHexString(((Integer) it.next()).intValue()));
        }
        return sb.toString();
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "AS.AudioService", printWriter)) {
            if (Rune.SEC_AUDIO_CUSTOM_SHELL_COMMAND && strArr.length != 0 && CommandUtils.handleCustomCommand(printWriter, strArr, this.mContext, new Function() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda16
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    String lambda$dump$17;
                    lambda$dump$17 = AudioService.this.lambda$dump$17((Integer) obj);
                    return lambda$dump$17;
                }
            })) {
                return;
            }
            sLifecycleLogger.dump(printWriter);
            if (this.mAudioHandler != null) {
                printWriter.println("\nMessage handler (watch for unhandled messages):");
                this.mAudioHandler.dump(new PrintWriterPrinter(printWriter), "  ");
            } else {
                printWriter.println("\nMessage handler is null");
            }
            this.mMediaFocusControl.dump(printWriter);
            dumpStreamStates(printWriter);
            dumpVolumeGroups(printWriter);
            dumpRingerMode(printWriter);
            dumpAudioMode(printWriter);
            printWriter.println("\nAudio routes:");
            printWriter.print("  mMainType=0x");
            printWriter.println(Integer.toHexString(this.mDeviceBroker.getCurAudioRoutes().mainType));
            printWriter.print("  mBluetoothName=");
            printWriter.println(this.mDeviceBroker.getCurAudioRoutes().bluetoothName);
            printWriter.println("\nOther state:");
            printWriter.print("  mUseVolumeGroupAliases=");
            printWriter.println(this.mUseVolumeGroupAliases);
            printWriter.print("  mVolumeController=");
            printWriter.println(this.mVolumeController);
            this.mSoundDoseHelper.dump(printWriter);
            printWriter.print("  sIndependentA11yVolume=");
            printWriter.println(sIndependentA11yVolume);
            printWriter.print("  mCameraSoundForced=");
            printWriter.println(isCameraSoundForced());
            printWriter.print("  mHasVibrator=");
            printWriter.println(this.mHasVibrator);
            printWriter.print("  mVolumePolicy=");
            printWriter.println(this.mVolumePolicy);
            printWriter.print("  mAvrcpAbsVolSupported=");
            printWriter.println(this.mAvrcpAbsVolSupported);
            printWriter.print("  mBtScoOnByApp=");
            printWriter.println(this.mBtScoOnByApp);
            printWriter.print("  mIsSingleVolume=");
            printWriter.println(this.mIsSingleVolume);
            printWriter.print("  mUseFixedVolume=");
            printWriter.println(this.mUseFixedVolume);
            printWriter.print("  mNotifAliasRing=");
            printWriter.println(this.mNotifAliasRing);
            printWriter.print("  mFixedVolumeDevices=");
            printWriter.println(dumpDeviceTypes(this.mFixedVolumeDevices));
            printWriter.print("  mFullVolumeDevices=");
            printWriter.println(dumpDeviceTypes(this.mFullVolumeDevices));
            printWriter.print("  absolute volume devices=");
            printWriter.println(dumpDeviceTypes(getAbsoluteVolumeDevicesWithBehavior(3)));
            printWriter.print("  adjust-only absolute volume devices=");
            printWriter.println(dumpDeviceTypes(getAbsoluteVolumeDevicesWithBehavior(5)));
            printWriter.print("  mExtVolumeController=");
            printWriter.println(this.mExtVolumeController);
            printWriter.print("  mHdmiAudioSystemClient=");
            printWriter.println(this.mHdmiAudioSystemClient);
            printWriter.print("  mHdmiPlaybackClient=");
            printWriter.println(this.mHdmiPlaybackClient);
            printWriter.print("  mHdmiTvClient=");
            printWriter.println(this.mHdmiTvClient);
            printWriter.print("  mHdmiSystemAudioSupported=");
            printWriter.println(this.mHdmiSystemAudioSupported);
            synchronized (this.mHdmiClientLock) {
                printWriter.print("  mHdmiCecVolumeControlEnabled=");
                printWriter.println(this.mHdmiCecVolumeControlEnabled);
            }
            printWriter.print("  mIsCallScreeningModeSupported=");
            printWriter.println(this.mIsCallScreeningModeSupported);
            printWriter.print("  mic mute FromSwitch=" + this.mMicMuteFromSwitch + " FromRestrictions=" + this.mMicMuteFromRestrictions + " FromApi=" + this.mMicMuteFromApi + " from system=" + this.mMicMuteFromSystemCached);
            dumpAccessibilityServiceUids(printWriter);
            dumpAssistantServicesUids(printWriter);
            printWriter.print("  supportsBluetoothVariableLatency=");
            printWriter.println(AudioSystem.supportsBluetoothVariableLatency());
            printWriter.print("  isBluetoothVariableLatencyEnabled=");
            printWriter.println(AudioSystem.isBluetoothVariableLatencyEnabled());
            dumpAudioPolicies(printWriter);
            this.mDynPolicyLogger.dump(printWriter);
            this.mPlaybackMonitor.dump(printWriter);
            this.mRecordMonitor.dump(printWriter);
            printWriter.println("\nAudioDeviceBroker:");
            this.mDeviceBroker.dump(printWriter, "  ");
            printWriter.println("\nSoundEffects:");
            this.mSfxHelper.dump(printWriter, "  ");
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.println("\nEvent logs:");
            this.mModeLogger.dump(printWriter);
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sDeviceLogger.dump(printWriter);
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sForceUseLogger.dump(printWriter);
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sVolumeLogger.dump(printWriter);
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sMuteLogger.dump(printWriter);
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            dumpSupportedSystemUsage(printWriter);
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            printWriter.println("\nSpatial audio:");
            printWriter.println("mHasSpatializerEffect:" + this.mHasSpatializerEffect + " (effect present)");
            printWriter.println("isSpatializerEnabled:" + isSpatializerEnabled() + " (routing dependent)");
            this.mSpatializerHelper.dump(printWriter);
            sSpatialLogger.dump(printWriter);
            this.mAudioSystem.dump(printWriter);
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            addSamsungExtraDump(printWriter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$dump$17(Integer num) {
        this.mSoundDoseHelper.setSafeMediaVolumeState(num.intValue());
        return SoundDoseHelper.safeMediaVolumeStateToString(this.mSoundDoseHelper.getSafeMediaVolumeState());
    }

    public final void dumpSupportedSystemUsage(PrintWriter printWriter) {
        printWriter.println("Supported System Usages:");
        synchronized (this.mSupportedSystemUsagesLock) {
            int i = 0;
            while (true) {
                int[] iArr = this.mSupportedSystemUsages;
                if (i < iArr.length) {
                    printWriter.printf("\t%s\n", AudioAttributes.usageToString(iArr[i]));
                    i++;
                }
            }
        }
    }

    public final void dumpAssistantServicesUids(PrintWriter printWriter) {
        synchronized (this.mSettingsLock) {
            if (this.mAssistantUids.size() > 0) {
                printWriter.println("  Assistant service UIDs:");
                Iterator it = this.mAssistantUids.iterator();
                while (it.hasNext()) {
                    printWriter.println("  - " + ((Integer) it.next()).intValue());
                }
            } else {
                printWriter.println("  No Assistant service Uids.");
            }
        }
    }

    public final void dumpAccessibilityServiceUids(PrintWriter printWriter) {
        synchronized (this.mSupportedSystemUsagesLock) {
            int[] iArr = this.mAccessibilityServiceUids;
            if (iArr != null && iArr.length > 0) {
                printWriter.println("  Accessibility service Uids:");
                for (int i : this.mAccessibilityServiceUids) {
                    printWriter.println("  - " + i);
                }
            } else {
                printWriter.println("  No accessibility service Uids.");
            }
        }
    }

    public static void readAndSetLowRamDevice() {
        long j;
        boolean isLowRamDeviceStatic = ActivityManager.isLowRamDeviceStatic();
        try {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ActivityManager.getService().getMemoryInfo(memoryInfo);
            j = memoryInfo.totalMem;
        } catch (RemoteException unused) {
            Log.w("AS.AudioService", "Cannot obtain MemoryInfo from ActivityManager, assume low memory device");
            j = 1073741824;
            isLowRamDeviceStatic = true;
        }
        int lowRamDevice = AudioSystem.setLowRamDevice(isLowRamDeviceStatic, j);
        if (lowRamDevice != 0) {
            Log.w("AS.AudioService", "AudioFlinger informed of device's low RAM attribute; status " + lowRamDevice);
        }
    }

    public final void enforceVolumeController(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE", "Only SystemUI can " + str);
    }

    public void setVolumeController(final IVolumeController iVolumeController) {
        enforceVolumeController("set the volume controller");
        if (this.mVolumeController.isSameBinder(iVolumeController)) {
            return;
        }
        this.mVolumeController.postDismiss();
        if (iVolumeController != null) {
            try {
                iVolumeController.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.audio.AudioService.6
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        if (AudioService.this.mVolumeController.isSameBinder(iVolumeController)) {
                            AudioService.this.mVolumeController.removeController(iVolumeController);
                        }
                    }
                }, 0);
            } catch (RemoteException unused) {
            }
        }
        this.mVolumeController.setController(iVolumeController);
        Log.d("AS.AudioService", "Volume controller: " + this.mVolumeController);
    }

    public IVolumeController getVolumeController() {
        enforceVolumeController("get the volume controller");
        Log.d("AS.AudioService", "Volume controller: " + this.mVolumeController);
        return this.mVolumeController.getController();
    }

    public void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean z) {
        enforceVolumeController("notify about volume controller visibility");
        if (this.mVolumeController.isSameBinder(iVolumeController)) {
            this.mVolumeController.setVisible(z);
            Log.d("AS.AudioService", "Volume controller visible: " + z);
        }
    }

    public void setVolumePolicy(VolumePolicy volumePolicy) {
        enforceVolumeController("set volume policy");
        if (volumePolicy == null || volumePolicy.equals(this.mVolumePolicy)) {
            return;
        }
        this.mVolumePolicy = volumePolicy;
        Log.d("AS.AudioService", "Volume policy changed: " + this.mVolumePolicy);
    }

    public class VolumeController implements ISafeHearingVolumeController {
        public IVolumeController mController;
        public int mLongPressTimeout;
        public boolean mSafetyDialogVisible;
        public boolean mVisible;

        public void removeController(IVolumeController iVolumeController) {
        }

        public VolumeController() {
        }

        public void setController(IVolumeController iVolumeController) {
            this.mController = iVolumeController;
            this.mVisible = false;
        }

        public IVolumeController getController() {
            return this.mController;
        }

        public void loadSettings(ContentResolver contentResolver) {
            this.mLongPressTimeout = AudioService.this.mSettings.getSecureIntForUser(contentResolver, "long_press_timeout", 500, -2);
        }

        public void setVisible(boolean z) {
            this.mVisible = z;
        }

        public boolean isSameBinder(IVolumeController iVolumeController) {
            return Objects.equals(asBinder(), binder(iVolumeController));
        }

        public IBinder asBinder() {
            return binder(this.mController);
        }

        public IBinder binder(IVolumeController iVolumeController) {
            if (iVolumeController == null) {
                return null;
            }
            return iVolumeController.asBinder();
        }

        public String toString() {
            return "VolumeController(" + asBinder() + ",mVisible=" + this.mVisible + ")";
        }

        @Override // com.android.server.audio.AudioService.ISafeHearingVolumeController
        public void postDisplaySafeVolumeWarning(int i) {
            IVolumeController iVolumeController = this.mController;
            if (iVolumeController == null) {
                return;
            }
            try {
                iVolumeController.displaySafeVolumeWarning(i | 1);
            } catch (RemoteException e) {
                Log.w("VolumeController", "Error calling displaySafeVolumeWarning", e);
            }
        }

        @Override // com.android.server.audio.AudioService.ISafeHearingVolumeController
        public void postDisplayCsdWarning(int i, int i2) {
            IVolumeController iVolumeController = this.mController;
            if (iVolumeController == null) {
                Log.e("VolumeController", "Unable to display CSD warning, no controller");
                return;
            }
            try {
                iVolumeController.displayCsdWarning(i, i2);
            } catch (RemoteException e) {
                Log.w("VolumeController", "Error calling displayCsdWarning for warning " + i, e);
            }
        }

        public void postVolumeChanged(int i, int i2) {
            IVolumeController iVolumeController = this.mController;
            if (iVolumeController == null) {
                return;
            }
            try {
                iVolumeController.volumeChanged(i, i2);
            } catch (RemoteException e) {
                Log.w("VolumeController", "Error calling volumeChanged", e);
            }
        }

        public void postMasterMuteChanged(int i) {
            IVolumeController iVolumeController = this.mController;
            if (iVolumeController == null) {
                return;
            }
            try {
                iVolumeController.masterMuteChanged(i);
            } catch (RemoteException e) {
                Log.w("VolumeController", "Error calling masterMuteChanged", e);
            }
        }

        public void setLayoutDirection(int i) {
            IVolumeController iVolumeController = this.mController;
            if (iVolumeController == null) {
                return;
            }
            try {
                iVolumeController.setLayoutDirection(i);
            } catch (RemoteException e) {
                Log.w("VolumeController", "Error calling setLayoutDirection", e);
            }
        }

        public void postDismiss() {
            IVolumeController iVolumeController = this.mController;
            if (iVolumeController == null) {
                return;
            }
            try {
                iVolumeController.dismiss();
            } catch (RemoteException e) {
                Log.w("VolumeController", "Error calling dismiss", e);
            }
        }

        public void setA11yMode(int i) {
            IVolumeController iVolumeController = this.mController;
            if (iVolumeController == null) {
                return;
            }
            try {
                iVolumeController.setA11yMode(i);
            } catch (RemoteException e) {
                Log.w("VolumeController", "Error calling setA11Mode", e);
            }
        }

        public void displayVolumeLimiterToast() {
            IVolumeController iVolumeController = this.mController;
            if (iVolumeController == null) {
                return;
            }
            try {
                iVolumeController.displayVolumeLimiterToast();
            } catch (RemoteException e) {
                Log.w("VolumeController", "Error calling displayVolumeLimiterToast", e);
            }
        }

        public boolean isVisible() {
            return this.mVisible;
        }

        public void setSafetyDialogVisible(boolean z) {
            this.mSafetyDialogVisible = z;
        }

        public boolean isSafeVolumeDialogShowing() {
            return this.mSafetyDialogVisible;
        }
    }

    public final class AudioServiceInternal extends AudioManagerInternal {
        public AudioServiceInternal() {
        }

        public void setRingerModeDelegate(AudioManagerInternal.RingerModeDelegate ringerModeDelegate) {
            AudioService.this.mRingerModeDelegate = ringerModeDelegate;
            if (AudioService.this.mRingerModeDelegate != null) {
                synchronized (AudioService.this.mSettingsLock) {
                    AudioService.this.updateRingerAndZenModeAffectedStreams();
                }
                setRingerModeInternal(getRingerModeInternal(), "AS.AudioService.setRingerModeDelegate");
            }
        }

        public int getRingerModeInternal() {
            return AudioService.this.getRingerModeInternal();
        }

        public void setRingerModeInternal(int i, String str) {
            AudioService.this.setRingerModeInternal(i, str);
        }

        public void silenceRingerModeInternal(String str) {
            AudioService.this.silenceRingerModeInternal(str);
        }

        public void updateRingerModeAffectedStreamsInternal() {
            synchronized (AudioService.this.mSettingsLock) {
                if (AudioService.this.updateRingerAndZenModeAffectedStreams()) {
                    AudioService.this.setRingerModeInt(getRingerModeInternal(), false);
                }
            }
        }

        public void addAssistantServiceUid(int i) {
            AudioService.sendMsg(AudioService.this.mAudioHandler, 44, 2, i, 0, null, 0);
        }

        public void removeAssistantServiceUid(int i) {
            AudioService.sendMsg(AudioService.this.mAudioHandler, 45, 2, i, 0, null, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0034 A[Catch: all -> 0x006d, LOOP:0: B:19:0x0034->B:23:0x004c, LOOP_START, PHI: r2
          0x0034: PHI (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:18:0x0032, B:23:0x004c] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000d, B:7:0x005b, B:12:0x0017, B:14:0x0021, B:19:0x0034, B:21:0x003d, B:27:0x0052, B:23:0x004c), top: B:3:0x0007 }] */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0052 A[Catch: all -> 0x006d, TryCatch #0 {, blocks: (B:4:0x0007, B:6:0x000d, B:7:0x005b, B:12:0x0017, B:14:0x0021, B:19:0x0034, B:21:0x003d, B:27:0x0052, B:23:0x004c), top: B:3:0x0007 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setActiveAssistantServicesUids(IntArray intArray) {
            boolean z;
            synchronized (AudioService.this.mSettingsLock) {
                if (intArray.size() == 0) {
                    AudioService.this.mActiveAssistantServiceUids = AudioService.NO_ACTIVE_ASSISTANT_SERVICE_UIDS;
                } else {
                    boolean z2 = true;
                    if (AudioService.this.mActiveAssistantServiceUids != null && AudioService.this.mActiveAssistantServiceUids.length == intArray.size()) {
                        z = false;
                        if (!z) {
                            for (int i = 0; i < AudioService.this.mActiveAssistantServiceUids.length; i++) {
                                if (intArray.get(i) != AudioService.this.mActiveAssistantServiceUids[i]) {
                                    break;
                                }
                            }
                        }
                        z2 = z;
                        if (z2) {
                            AudioService.this.mActiveAssistantServiceUids = intArray.toArray();
                        }
                    }
                    z = true;
                    if (!z) {
                    }
                    z2 = z;
                    if (z2) {
                    }
                }
            }
            AudioService.sendMsg(AudioService.this.mAudioHandler, 46, 0, 0, 0, null, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x003a A[Catch: all -> 0x0073, LOOP:0: B:22:0x003a->B:26:0x0052, LOOP_START, PHI: r2
          0x003a: PHI (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:21:0x0038, B:26:0x0052] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {, blocks: (B:8:0x0010, B:10:0x0016, B:11:0x0061, B:12:0x0071, B:15:0x001d, B:17:0x0027, B:22:0x003a, B:24:0x0043, B:30:0x0058, B:26:0x0052), top: B:7:0x0010 }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0058 A[Catch: all -> 0x0073, TryCatch #0 {, blocks: (B:8:0x0010, B:10:0x0016, B:11:0x0061, B:12:0x0071, B:15:0x001d, B:17:0x0027, B:22:0x003a, B:24:0x0043, B:30:0x0058, B:26:0x0052), top: B:7:0x0010 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setAccessibilityServiceUids(IntArray intArray) {
            boolean z;
            if (AudioService.this.isPlatformAutomotive()) {
                return;
            }
            synchronized (AudioService.this.mAccessibilityServiceUidsLock) {
                if (intArray.size() == 0) {
                    AudioService.this.mAccessibilityServiceUids = null;
                } else {
                    boolean z2 = true;
                    if (AudioService.this.mAccessibilityServiceUids != null && AudioService.this.mAccessibilityServiceUids.length == intArray.size()) {
                        z = false;
                        if (!z) {
                            for (int i = 0; i < AudioService.this.mAccessibilityServiceUids.length; i++) {
                                if (intArray.get(i) != AudioService.this.mAccessibilityServiceUids[i]) {
                                    break;
                                }
                            }
                        }
                        z2 = z;
                        if (z2) {
                            AudioService.this.mAccessibilityServiceUids = intArray.toArray();
                        }
                    }
                    z = true;
                    if (!z) {
                    }
                    z2 = z;
                    if (z2) {
                    }
                }
                AudioService.sendMsg(AudioService.this.mAudioHandler, 35, 0, 0, 0, null, 0);
            }
        }

        public void setInputMethodServiceUid(int i) {
            synchronized (AudioService.this.mInputMethodServiceUidLock) {
                if (AudioService.this.mInputMethodServiceUid != i) {
                    AudioService.this.mAudioSystem.setCurrentImeUid(i);
                    AudioService.this.mInputMethodServiceUid = i;
                }
            }
        }
    }

    public final void onUpdateAccessibilityServiceUids() {
        int[] iArr;
        synchronized (this.mAccessibilityServiceUidsLock) {
            iArr = this.mAccessibilityServiceUids;
        }
        AudioSystem.setA11yServicesUids(iArr);
    }

    public String registerAudioPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection) {
        boolean z5;
        AudioPolicyConfig audioPolicyConfig2;
        boolean z6;
        IMediaProjection iMediaProjection2;
        AudioSystem.setDynamicPolicyCallback(this.mDynPolicyCallback);
        if (z2 || z3 || z) {
            z5 = true;
            audioPolicyConfig2 = audioPolicyConfig;
            z6 = z4;
            iMediaProjection2 = iMediaProjection;
        } else {
            audioPolicyConfig2 = audioPolicyConfig;
            z6 = z4;
            iMediaProjection2 = iMediaProjection;
            z5 = false;
        }
        if (!isPolicyRegisterAllowed(audioPolicyConfig2, z5, z6, iMediaProjection2)) {
            Slog.w("AS.AudioService", "Permission denied to register audio policy for pid " + Binder.getCallingPid() + " / uid " + Binder.getCallingUid() + ", need system permission or a MediaProjection that can project audio");
            return null;
        }
        synchronized (this.mAudioPolicies) {
            if (this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                Slog.e("AS.AudioService", "Cannot re-register policy");
                return null;
            }
            try {
                AudioPolicyProxy audioPolicyProxy = new AudioPolicyProxy(audioPolicyConfig, iAudioPolicyCallback, z, z2, z3, z4, iMediaProjection);
                iAudioPolicyCallback.asBinder().linkToDeath(audioPolicyProxy, 0);
                this.mDynPolicyLogger.enqueue(new EventLogger.StringEvent("registerAudioPolicy for " + iAudioPolicyCallback.asBinder() + " u/pid:" + Binder.getCallingUid() + "/" + Binder.getCallingPid() + " with config:" + audioPolicyProxy.toCompactLogString()).printLog("AS.AudioService"));
                String registrationId = audioPolicyProxy.getRegistrationId();
                this.mAudioPolicies.put(iAudioPolicyCallback.asBinder(), audioPolicyProxy);
                return registrationId;
            } catch (RemoteException e) {
                Slog.w("AS.AudioService", "Audio policy registration failed, could not link to " + iAudioPolicyCallback + " binder death", e);
                return null;
            } catch (IllegalStateException e2) {
                Slog.w("AS.AudioService", "Audio policy registration failed for binder " + iAudioPolicyCallback, e2);
                return null;
            }
        }
    }

    public final void onPolicyClientDeath(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (this.mPlaybackMonitor.hasActiveMediaPlaybackOnSubmixWithAddress((String) it.next())) {
                this.mDeviceBroker.postBroadcastBecomingNoisy();
                return;
            }
        }
    }

    public final boolean isPolicyRegisterAllowed(AudioPolicyConfig audioPolicyConfig, boolean z, boolean z2, IMediaProjection iMediaProjection) {
        boolean z3 = z || z2 || audioPolicyConfig.getMixes().isEmpty();
        Iterator it = audioPolicyConfig.getMixes().iterator();
        ArrayList arrayList = null;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        while (it.hasNext()) {
            AudioMix audioMix = (AudioMix) it.next();
            if (audioMix.getRule().allowPrivilegedMediaPlaybackCapture()) {
                String canBeUsedForPrivilegedMediaCapture = AudioMix.canBeUsedForPrivilegedMediaCapture(audioMix.getFormat());
                if (canBeUsedForPrivilegedMediaCapture != null) {
                    Log.e("AS.AudioService", canBeUsedForPrivilegedMediaCapture);
                    return false;
                }
                z4 |= true;
            }
            if (audioMix.containsMatchAttributeRuleForUsage(2) && audioMix.getRouteFlags() == 3) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(audioMix);
            }
            if (audioMix.getRouteFlags() == 3 && iMediaProjection != null) {
                z5 |= true;
            } else if (audioMix.isForCallRedirection()) {
                z6 |= true;
            } else if (audioMix.containsMatchAttributeRuleForUsage(2)) {
                z3 |= true;
            }
        }
        if (z4 && !callerHasPermission("android.permission.CAPTURE_MEDIA_OUTPUT") && !callerHasPermission("android.permission.CAPTURE_AUDIO_OUTPUT")) {
            Log.e("AS.AudioService", "Privileged audio capture requires CAPTURE_MEDIA_OUTPUT or CAPTURE_AUDIO_OUTPUT system permission");
            return false;
        }
        if (arrayList != null && arrayList.size() > 0) {
            if (!callerHasPermission("android.permission.CAPTURE_VOICE_COMMUNICATION_OUTPUT")) {
                Log.e("AS.AudioService", "Audio capture for voice communication requires CAPTURE_VOICE_COMMUNICATION_OUTPUT system permission");
                return false;
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((AudioMix) it2.next()).getRule().setVoiceCommunicationCaptureAllowed(true);
            }
        }
        if (z5 && !canProjectAudio(iMediaProjection)) {
            return false;
        }
        if (z3 && !callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING")) {
            Log.e("AS.AudioService", "Can not capture audio without MODIFY_AUDIO_ROUTING");
            return false;
        }
        if (!z6 || callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) {
            return true;
        }
        Log.e("AS.AudioService", "Can not capture audio without CALL_AUDIO_INTERCEPTION");
        return false;
    }

    public final boolean callerHasPermission(String str) {
        return this.mContext.checkCallingPermission(str) == 0;
    }

    public final boolean canProjectAudio(IMediaProjection iMediaProjection) {
        if (iMediaProjection == null) {
            Log.e("AS.AudioService", "MediaProjection is null");
            return false;
        }
        IMediaProjectionManager projectionService = getProjectionService();
        if (projectionService == null) {
            Log.e("AS.AudioService", "Can't get service IMediaProjectionManager");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!projectionService.isCurrentProjection(iMediaProjection)) {
                Log.w("AS.AudioService", "App passed invalid MediaProjection token");
                return false;
            }
            try {
                if (iMediaProjection.canProjectAudio()) {
                    return true;
                }
                Log.w("AS.AudioService", "App passed MediaProjection that can not project audio");
                return false;
            } catch (RemoteException e) {
                Log.e("AS.AudioService", "Can't call .canProjectAudio() on valid IMediaProjection" + iMediaProjection.asBinder(), e);
                return false;
            }
        } catch (RemoteException e2) {
            Log.e("AS.AudioService", "Can't call .isCurrentProjection() on IMediaProjectionManager" + projectionService.asBinder(), e2);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IMediaProjectionManager getProjectionService() {
        if (this.mProjectionService == null) {
            this.mProjectionService = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));
        }
        return this.mProjectionService;
    }

    public void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        unregisterAudioPolicyInt(iAudioPolicyCallback, "unregisterAudioPolicyAsync");
    }

    public void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        unregisterAudioPolicyInt(iAudioPolicyCallback, "unregisterAudioPolicy");
    }

    public final void unregisterAudioPolicyInt(IAudioPolicyCallback iAudioPolicyCallback, String str) {
        this.mDynPolicyLogger.enqueue(new EventLogger.StringEvent(str + " for " + iAudioPolicyCallback.asBinder()).printLog("AS.AudioService"));
        synchronized (this.mAudioPolicies) {
            AudioPolicyProxy audioPolicyProxy = (AudioPolicyProxy) this.mAudioPolicies.remove(iAudioPolicyCallback.asBinder());
            if (audioPolicyProxy == null) {
                Slog.w("AS.AudioService", "Trying to unregister unknown audio policy for pid " + Binder.getCallingPid() + " / uid " + Binder.getCallingUid());
                return;
            }
            iAudioPolicyCallback.asBinder().unlinkToDeath(audioPolicyProxy, 0);
            audioPolicyProxy.release();
        }
    }

    public final AudioPolicyProxy checkUpdateForPolicy(IAudioPolicyCallback iAudioPolicyCallback, String str) {
        if (!(this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0)) {
            Slog.w("AS.AudioService", str + " for pid " + Binder.getCallingPid() + " / uid " + Binder.getCallingUid() + ", need MODIFY_AUDIO_ROUTING");
            return null;
        }
        AudioPolicyProxy audioPolicyProxy = (AudioPolicyProxy) this.mAudioPolicies.get(iAudioPolicyCallback.asBinder());
        if (audioPolicyProxy != null) {
            return audioPolicyProxy;
        }
        Slog.w("AS.AudioService", str + " for pid " + Binder.getCallingPid() + " / uid " + Binder.getCallingUid() + ", unregistered policy");
        return null;
    }

    public int addMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) {
        Log.d("AS.AudioService", "addMixForPolicy for " + iAudioPolicyCallback.asBinder() + " with config:" + audioPolicyConfig);
        synchronized (this.mAudioPolicies) {
            AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot add AudioMix in audio policy");
            if (checkUpdateForPolicy == null) {
                return -1;
            }
            return checkUpdateForPolicy.addMixes(audioPolicyConfig.getMixes()) == 0 ? 0 : -1;
        }
    }

    public int removeMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) {
        Log.d("AS.AudioService", "removeMixForPolicy for " + iAudioPolicyCallback.asBinder() + " with config:" + audioPolicyConfig);
        synchronized (this.mAudioPolicies) {
            AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot add AudioMix in audio policy");
            if (checkUpdateForPolicy == null) {
                return -1;
            }
            return checkUpdateForPolicy.removeMixes(audioPolicyConfig.getMixes()) == 0 ? 0 : -1;
        }
    }

    public int setUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) {
        Log.d("AS.AudioService", "setUidDeviceAffinity for " + iAudioPolicyCallback.asBinder() + " uid:" + i);
        synchronized (this.mAudioPolicies) {
            AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change device affinity in audio policy");
            if (checkUpdateForPolicy == null) {
                return -1;
            }
            if (!checkUpdateForPolicy.hasMixRoutedToDevices(iArr, strArr)) {
                return -1;
            }
            return checkUpdateForPolicy.setUidDeviceAffinities(i, iArr, strArr);
        }
    }

    public int setUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) {
        Log.d("AS.AudioService", "setUserIdDeviceAffinity for " + iAudioPolicyCallback.asBinder() + " user:" + i);
        synchronized (this.mAudioPolicies) {
            AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change device affinity in audio policy");
            if (checkUpdateForPolicy == null) {
                return -1;
            }
            if (!checkUpdateForPolicy.hasMixRoutedToDevices(iArr, strArr)) {
                return -1;
            }
            return checkUpdateForPolicy.setUserIdDeviceAffinities(i, iArr, strArr);
        }
    }

    public int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) {
        Log.d("AS.AudioService", "removeUidDeviceAffinity for " + iAudioPolicyCallback.asBinder() + " uid:" + i);
        synchronized (this.mAudioPolicies) {
            AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot remove device affinity in audio policy");
            if (checkUpdateForPolicy == null) {
                return -1;
            }
            return checkUpdateForPolicy.removeUidDeviceAffinities(i);
        }
    }

    public int removeUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) {
        Log.d("AS.AudioService", "removeUserIdDeviceAffinity for " + iAudioPolicyCallback.asBinder() + " userId:" + i);
        synchronized (this.mAudioPolicies) {
            AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot remove device affinity in audio policy");
            if (checkUpdateForPolicy == null) {
                return -1;
            }
            return checkUpdateForPolicy.removeUserIdDeviceAffinities(i);
        }
    }

    public int setFocusPropertiesForPolicy(int i, IAudioPolicyCallback iAudioPolicyCallback) {
        Log.d("AS.AudioService", "setFocusPropertiesForPolicy() duck behavior=" + i + " policy " + iAudioPolicyCallback.asBinder());
        synchronized (this.mAudioPolicies) {
            AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change audio policy focus properties");
            if (checkUpdateForPolicy == null) {
                return -1;
            }
            if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                Slog.e("AS.AudioService", "Cannot change audio policy focus properties, unregistered policy");
                return -1;
            }
            boolean z = true;
            if (i == 1) {
                Iterator it = this.mAudioPolicies.values().iterator();
                while (it.hasNext()) {
                    if (((AudioPolicyProxy) it.next()).mFocusDuckBehavior == 1) {
                        Slog.e("AS.AudioService", "Cannot change audio policy ducking behavior, already handled");
                        return -1;
                    }
                }
            }
            checkUpdateForPolicy.mFocusDuckBehavior = i;
            MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
            if (i != 1) {
                z = false;
            }
            mediaFocusControl.setDuckingInExtPolicyAvailable(z);
            return 0;
        }
    }

    public List getFocusStack() {
        super.getFocusStack_enforcePermission();
        return this.mMediaFocusControl.getFocusStack();
    }

    public boolean sendFocusLoss(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback) {
        Objects.requireNonNull(audioFocusInfo);
        Objects.requireNonNull(iAudioPolicyCallback);
        enforceModifyAudioRoutingPermission();
        if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
            throw new IllegalStateException("Only registered AudioPolicy can change focus");
        }
        if (!((AudioPolicyProxy) this.mAudioPolicies.get(iAudioPolicyCallback.asBinder())).mHasFocusListener) {
            throw new IllegalStateException("AudioPolicy must have focus listener to change focus");
        }
        return this.mMediaFocusControl.sendFocusLoss(audioFocusInfo);
    }

    public AudioHalVersionInfo getHalVersion() {
        for (AudioHalVersionInfo audioHalVersionInfo : AudioHalVersionInfo.VERSIONS) {
            try {
                HwBinder.getService(String.format("android.hardware.audio@%s::IDevicesFactory", audioHalVersionInfo.getMajorVersion() + "." + audioHalVersionInfo.getMinorVersion()), "default");
                return audioHalVersionInfo;
            } catch (RemoteException e) {
                Log.e("AS.AudioService", "Remote exception when getting hardware audio service:", e);
            } catch (NoSuchElementException unused) {
            }
        }
        return null;
    }

    public boolean hasRegisteredDynamicPolicy() {
        boolean z;
        synchronized (this.mAudioPolicies) {
            z = !this.mAudioPolicies.isEmpty();
        }
        return z;
    }

    public int setPreferredMixerAttributes(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) {
        Objects.requireNonNull(audioAttributes);
        Objects.requireNonNull(audioMixerAttributes);
        if (!checkAudioSettingsPermission("setPreferredMixerAttributes()")) {
            return -4;
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String formatSimple = TextUtils.formatSimple("setPreferredMixerAttributes u/pid:%d/%d attr:%s mixerAttributes:%s portId:%d", new Object[]{Integer.valueOf(callingUid), Integer.valueOf(callingPid), audioAttributes.toString(), audioMixerAttributes.toString(), Integer.valueOf(i)});
            sDeviceLogger.enqueue(new EventLogger.StringEvent(formatSimple).printLog("AS.AudioService"));
            int preferredMixerAttributes = this.mAudioSystem.setPreferredMixerAttributes(audioAttributes, i, callingUid, audioMixerAttributes);
            if (preferredMixerAttributes == 0) {
                dispatchPreferredMixerAttributesChanged(audioAttributes, i, audioMixerAttributes);
            } else {
                Log.e("AS.AudioService", TextUtils.formatSimple("Error %d in %s)", new Object[]{Integer.valueOf(preferredMixerAttributes), formatSimple}));
            }
            return preferredMixerAttributes;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int clearPreferredMixerAttributes(AudioAttributes audioAttributes, int i) {
        Objects.requireNonNull(audioAttributes);
        if (!checkAudioSettingsPermission("clearPreferredMixerAttributes()")) {
            return -4;
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String formatSimple = TextUtils.formatSimple("clearPreferredMixerAttributes u/pid:%d/%d attr:%s", new Object[]{Integer.valueOf(callingUid), Integer.valueOf(callingPid), audioAttributes.toString()});
            sDeviceLogger.enqueue(new EventLogger.StringEvent(formatSimple).printLog("AS.AudioService"));
            int clearPreferredMixerAttributes = this.mAudioSystem.clearPreferredMixerAttributes(audioAttributes, i, callingUid);
            if (clearPreferredMixerAttributes == 0) {
                dispatchPreferredMixerAttributesChanged(audioAttributes, i, null);
            } else {
                Log.e("AS.AudioService", TextUtils.formatSimple("Error %d in %s)", new Object[]{Integer.valueOf(clearPreferredMixerAttributes), formatSimple}));
            }
            return clearPreferredMixerAttributes;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void dispatchPreferredMixerAttributesChanged(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("audio_attributes", audioAttributes);
        bundle.putParcelable("audio_mixer_attributes", audioMixerAttributes);
        sendBundleMsg(this.mAudioHandler, 52, 2, i, 0, null, bundle, 0);
    }

    public void registerPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) {
        if (iPreferredMixerAttributesDispatcher == null) {
            return;
        }
        this.mPrefMixerAttrDispatcher.register(iPreferredMixerAttributesDispatcher);
    }

    public void unregisterPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) {
        if (iPreferredMixerAttributesDispatcher == null) {
            return;
        }
        this.mPrefMixerAttrDispatcher.unregister(iPreferredMixerAttributesDispatcher);
    }

    public void onDispatchPreferredMixerAttributesChanged(Bundle bundle, int i) {
        int beginBroadcast = this.mPrefMixerAttrDispatcher.beginBroadcast();
        AudioAttributes audioAttributes = (AudioAttributes) bundle.getParcelable("audio_attributes", AudioAttributes.class);
        AudioMixerAttributes audioMixerAttributes = (AudioMixerAttributes) bundle.getParcelable("audio_mixer_attributes", AudioMixerAttributes.class);
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mPrefMixerAttrDispatcher.getBroadcastItem(i2).dispatchPrefMixerAttributesChanged(audioAttributes, i, audioMixerAttributes);
            } catch (RemoteException e) {
                Log.e("AS.AudioService", "Can't call dispatchPrefMixerAttributesChanged() IPreferredMixerAttributesDispatcher " + this.mPrefMixerAttrDispatcher.getBroadcastItem(i2).asBinder(), e);
            }
        }
        this.mPrefMixerAttrDispatcher.finishBroadcast();
    }

    public boolean supportsBluetoothVariableLatency() {
        super.supportsBluetoothVariableLatency_enforcePermission();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            boolean supportsBluetoothVariableLatency = AudioSystem.supportsBluetoothVariableLatency();
            if (create != null) {
                create.close();
            }
            return supportsBluetoothVariableLatency;
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void setBluetoothVariableLatencyEnabled(boolean z) {
        super.setBluetoothVariableLatencyEnabled_enforcePermission();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            AudioSystem.setBluetoothVariableLatencyEnabled(z);
            if (create != null) {
                create.close();
            }
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public boolean isBluetoothVariableLatencyEnabled() {
        super.isBluetoothVariableLatencyEnabled_enforcePermission();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            boolean isBluetoothVariableLatencyEnabled = AudioSystem.isBluetoothVariableLatencyEnabled();
            if (create != null) {
                create.close();
            }
            return isBluetoothVariableLatencyEnabled;
        } catch (Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final void setExtVolumeController(IAudioPolicyCallback iAudioPolicyCallback) {
        if (!this.mContext.getResources().getBoolean(R.bool.config_enable_cellular_on_boot_default)) {
            Log.e("AS.AudioService", "Cannot set external volume controller: device not set for volume keys handled in PhoneWindowManager");
            return;
        }
        synchronized (this.mExtVolumeControllerLock) {
            IAudioPolicyCallback iAudioPolicyCallback2 = this.mExtVolumeController;
            if (iAudioPolicyCallback2 != null && !iAudioPolicyCallback2.asBinder().pingBinder()) {
                Log.e("AS.AudioService", "Cannot set external volume controller: existing controller");
            }
            this.mExtVolumeController = iAudioPolicyCallback;
        }
    }

    public final void dumpAudioPolicies(PrintWriter printWriter) {
        printWriter.println("\nAudio policies:");
        synchronized (this.mAudioPolicies) {
            Iterator it = this.mAudioPolicies.values().iterator();
            while (it.hasNext()) {
                printWriter.println(((AudioPolicyProxy) it.next()).toLogFriendlyString());
            }
        }
    }

    public final void onDynPolicyMixStateUpdate(String str, int i) {
        Log.d("AS.AudioService", "onDynamicPolicyMixStateUpdate(" + str + ", " + i + ")");
        synchronized (this.mAudioPolicies) {
            for (AudioPolicyProxy audioPolicyProxy : this.mAudioPolicies.values()) {
                Iterator it = audioPolicyProxy.getMixes().iterator();
                while (it.hasNext()) {
                    if (((AudioMix) it.next()).getRegistration().equals(str)) {
                        try {
                            audioPolicyProxy.mPolicyCallback.notifyMixStateUpdate(str, i);
                        } catch (RemoteException e) {
                            Log.e("AS.AudioService", "Can't call notifyMixStateUpdate() on IAudioPolicyCallback " + audioPolicyProxy.mPolicyCallback.asBinder(), e);
                        }
                        return;
                    }
                }
            }
        }
    }

    public void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        this.mRecordMonitor.registerRecordingCallback(iRecordingConfigDispatcher, this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        this.mRecordMonitor.unregisterRecordingCallback(iRecordingConfigDispatcher);
    }

    public List getActiveRecordingConfigurations() {
        return this.mRecordMonitor.getActiveRecordingConfigurations(Binder.getCallingUid() == 1000 || this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public int trackRecorder(IBinder iBinder) {
        return this.mRecordMonitor.trackRecorder(iBinder);
    }

    public void recorderEvent(int i, int i2) {
        this.mRecordMonitor.recorderEvent(i, i2);
    }

    public void releaseRecorder(int i) {
        this.mRecordMonitor.releaseRecorder(i);
    }

    public void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
        this.mPlaybackMonitor.registerPlaybackCallback(iPlaybackConfigDispatcher, this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
        this.mPlaybackMonitor.unregisterPlaybackCallback(iPlaybackConfigDispatcher);
    }

    public List getActivePlaybackConfigurations() {
        return this.mPlaybackMonitor.getActivePlaybackConfigurations(this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) {
        AudioAttributes audioAttributes;
        if (playerIdCard != null && (audioAttributes = playerIdCard.mAttributes) != null) {
            validateAudioAttributesUsage(audioAttributes);
        }
        return this.mPlaybackMonitor.trackPlayer(playerIdCard);
    }

    public void playerAttributes(int i, AudioAttributes audioAttributes) {
        if (audioAttributes != null) {
            validateAudioAttributesUsage(audioAttributes);
        }
        this.mPlaybackMonitor.playerAttributes(i, audioAttributes, Binder.getCallingUid());
    }

    public void playerSessionId(int i, int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("invalid session Id " + i2);
        }
        this.mPlaybackMonitor.playerSessionId(i, i2, Binder.getCallingUid());
    }

    public void playerEvent(int i, int i2, int i3) {
        this.mPlaybackMonitor.playerEvent(i, i2, i3, Binder.getCallingUid());
    }

    public void portEvent(int i, int i2, PersistableBundle persistableBundle) {
        this.mPlaybackMonitor.portEvent(i, i2, persistableBundle, Binder.getCallingUid());
    }

    public void playerHasOpPlayAudio(int i, boolean z) {
        this.mPlaybackMonitor.playerHasOpPlayAudio(i, z, Binder.getCallingUid());
    }

    public void releasePlayer(int i) {
        this.mPlaybackMonitor.releasePlayer(i, Binder.getCallingUid());
    }

    public int setAllowedCapturePolicy(int i) {
        int allowedCapturePolicy;
        int callingUid = Binder.getCallingUid();
        int capturePolicyToFlags = AudioAttributes.capturePolicyToFlags(i, 0);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mPlaybackMonitor) {
                allowedCapturePolicy = this.mAudioSystem.setAllowedCapturePolicy(callingUid, capturePolicyToFlags);
                if (allowedCapturePolicy == 0) {
                    this.mPlaybackMonitor.setAllowedCapturePolicy(callingUid, i);
                }
            }
            return allowedCapturePolicy;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getAllowedCapturePolicy() {
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mPlaybackMonitor.getAllowedCapturePolicy(callingUid);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isPlaybackActiveForUid(int i) {
        return this.mPlaybackMonitor.isPlaybackActiveForUid(i);
    }

    public boolean isRecordingActiveForUid(int i) {
        return this.mRecordMonitor.isRecordingActiveForUid(i);
    }

    public final class AudioDeviceArray {
        public final String[] mDeviceAddresses;
        public final int[] mDeviceTypes;

        public AudioDeviceArray(int[] iArr, String[] strArr) {
            this.mDeviceTypes = iArr;
            this.mDeviceAddresses = strArr;
        }
    }

    public class AudioPolicyProxy extends AudioPolicyConfig implements IBinder.DeathRecipient {
        public int mFocusDuckBehavior;
        public final boolean mHasFocusListener;
        public boolean mIsFocusPolicy;
        public boolean mIsTestFocusPolicy;
        public final boolean mIsVolumeController;
        public final IAudioPolicyCallback mPolicyCallback;
        public final IMediaProjection mProjection;
        public UnregisterOnStopCallback mProjectionCallback;
        public final HashMap mUidDeviceAffinities;
        public final HashMap mUserIdDeviceAffinities;

        public final class UnregisterOnStopCallback extends IMediaProjectionCallback.Stub {
            public void onCapturedContentResize(int i, int i2) {
            }

            public void onCapturedContentVisibilityChanged(boolean z) {
            }

            public UnregisterOnStopCallback() {
            }

            public void onStop() {
                AudioPolicyProxy audioPolicyProxy = AudioPolicyProxy.this;
                AudioService.this.unregisterAudioPolicyAsync(audioPolicyProxy.mPolicyCallback);
            }
        }

        public AudioPolicyProxy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection) {
            super(audioPolicyConfig);
            this.mUidDeviceAffinities = new HashMap();
            this.mUserIdDeviceAffinities = new HashMap();
            this.mFocusDuckBehavior = 0;
            this.mIsFocusPolicy = false;
            this.mIsTestFocusPolicy = false;
            StringBuilder sb = new StringBuilder();
            sb.append(audioPolicyConfig.hashCode());
            sb.append(":ap:");
            int i = AudioService.this.mAudioPolicyCounter;
            AudioService.this.mAudioPolicyCounter = i + 1;
            sb.append(i);
            setRegistration(new String(sb.toString()));
            this.mPolicyCallback = iAudioPolicyCallback;
            this.mHasFocusListener = z;
            this.mIsVolumeController = z4;
            this.mProjection = iMediaProjection;
            if (z) {
                AudioService.this.mMediaFocusControl.addFocusFollower(iAudioPolicyCallback);
                if (z2) {
                    this.mIsFocusPolicy = true;
                    this.mIsTestFocusPolicy = z3;
                    AudioService.this.mMediaFocusControl.setFocusPolicy(iAudioPolicyCallback, this.mIsTestFocusPolicy);
                }
            }
            if (z4) {
                AudioService.this.setExtVolumeController(iAudioPolicyCallback);
            }
            if (iMediaProjection != null) {
                UnregisterOnStopCallback unregisterOnStopCallback = new UnregisterOnStopCallback();
                this.mProjectionCallback = unregisterOnStopCallback;
                try {
                    iMediaProjection.registerCallback(unregisterOnStopCallback);
                } catch (RemoteException e) {
                    release();
                    throw new IllegalStateException("MediaProjection callback registration failed, could not link to " + iMediaProjection + " binder death", e);
                }
            }
            int connectMixes = connectMixes();
            if (connectMixes == 0) {
                return;
            }
            release();
            throw new IllegalStateException("Could not connect mix, error: " + connectMixes);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            AudioService.this.mDynPolicyLogger.enqueue(new EventLogger.StringEvent("AudioPolicy " + this.mPolicyCallback.asBinder() + " died").printLog("AudioPolicyProxy"));
            ArrayList arrayList = new ArrayList();
            Iterator it = ((AudioPolicyConfig) this).mMixes.iterator();
            while (it.hasNext()) {
                arrayList.add(((AudioMix) it.next()).getRegistration());
            }
            AudioService.this.onPolicyClientDeath(arrayList);
            release();
        }

        public String getRegistrationId() {
            return getRegistration();
        }

        public void release() {
            if (this.mIsFocusPolicy) {
                AudioService.this.mMediaFocusControl.unsetFocusPolicy(this.mPolicyCallback, this.mIsTestFocusPolicy);
            }
            if (this.mFocusDuckBehavior == 1) {
                AudioService.this.mMediaFocusControl.setDuckingInExtPolicyAvailable(false);
            }
            if (this.mHasFocusListener) {
                AudioService.this.mMediaFocusControl.removeFocusFollower(this.mPolicyCallback);
            }
            UnregisterOnStopCallback unregisterOnStopCallback = this.mProjectionCallback;
            if (unregisterOnStopCallback != null) {
                try {
                    this.mProjection.unregisterCallback(unregisterOnStopCallback);
                } catch (RemoteException unused) {
                    Log.e("AudioPolicyProxy", "Fail to unregister Audiopolicy callback from MediaProjection");
                }
            }
            if (this.mIsVolumeController) {
                synchronized (AudioService.this.mExtVolumeControllerLock) {
                    AudioService.this.mExtVolumeController = null;
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AudioService.this.mAudioSystem.registerPolicyMixes(((AudioPolicyConfig) this).mMixes, false);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                synchronized (AudioService.this.mAudioPolicies) {
                    AudioService.this.mAudioPolicies.remove(this.mPolicyCallback.asBinder());
                }
                try {
                    this.mPolicyCallback.notifyUnregistration();
                } catch (RemoteException unused2) {
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean hasMixAffectingUsage(int i, int i2) {
            Iterator it = ((AudioPolicyConfig) this).mMixes.iterator();
            while (it.hasNext()) {
                AudioMix audioMix = (AudioMix) it.next();
                if (audioMix.isAffectingUsage(i) && (audioMix.getRouteFlags() & i2) != i2) {
                    return true;
                }
            }
            return false;
        }

        public boolean hasMixRoutedToDevices(int[] iArr, String[] strArr) {
            int i = 0;
            while (true) {
                boolean z = true;
                if (i >= iArr.length) {
                    return true;
                }
                Iterator it = ((AudioPolicyConfig) this).mMixes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    if (((AudioMix) it.next()).isRoutedToDevice(iArr[i], strArr[i])) {
                        break;
                    }
                }
                if (!z) {
                    return false;
                }
                i++;
            }
        }

        public int addMixes(ArrayList arrayList) {
            int registerPolicyMixes;
            synchronized (((AudioPolicyConfig) this).mMixes) {
                add(arrayList);
                registerPolicyMixes = AudioService.this.mAudioSystem.registerPolicyMixes(arrayList, true);
            }
            return registerPolicyMixes;
        }

        public int removeMixes(ArrayList arrayList) {
            int registerPolicyMixes;
            synchronized (((AudioPolicyConfig) this).mMixes) {
                remove(arrayList);
                registerPolicyMixes = AudioService.this.mAudioSystem.registerPolicyMixes(arrayList, false);
            }
            return registerPolicyMixes;
        }

        public int connectMixes() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AudioService.this.mAudioSystem.registerPolicyMixes(((AudioPolicyConfig) this).mMixes, true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int setUidDeviceAffinities(int i, int[] iArr, String[] strArr) {
            Integer num = new Integer(i);
            if (this.mUidDeviceAffinities.remove(num) != null && removeUidDeviceAffinitiesFromSystem(i) != 0) {
                Log.e("AudioPolicyProxy", "AudioSystem. removeUidDeviceAffinities(" + i + ") failed,  cannot call AudioSystem.setUidDeviceAffinities");
                return -1;
            }
            AudioDeviceArray audioDeviceArray = new AudioDeviceArray(iArr, strArr);
            if (setUidDeviceAffinitiesOnSystem(i, audioDeviceArray) == 0) {
                this.mUidDeviceAffinities.put(num, audioDeviceArray);
                return 0;
            }
            Log.e("AudioPolicyProxy", "AudioSystem. setUidDeviceAffinities(" + i + ") failed");
            return -1;
        }

        public int removeUidDeviceAffinities(int i) {
            if (this.mUidDeviceAffinities.remove(new Integer(i)) != null && removeUidDeviceAffinitiesFromSystem(i) == 0) {
                return 0;
            }
            Log.e("AudioPolicyProxy", "AudioSystem. removeUidDeviceAffinities failed");
            return -1;
        }

        public final int removeUidDeviceAffinitiesFromSystem(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AudioService.this.mAudioSystem.removeUidDeviceAffinities(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int setUidDeviceAffinitiesOnSystem(int i, AudioDeviceArray audioDeviceArray) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AudioService.this.mAudioSystem.setUidDeviceAffinities(i, audioDeviceArray.mDeviceTypes, audioDeviceArray.mDeviceAddresses);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int setUserIdDeviceAffinities(int i, int[] iArr, String[] strArr) {
            Integer num = new Integer(i);
            if (this.mUserIdDeviceAffinities.remove(num) != null && removeUserIdDeviceAffinitiesFromSystem(i) != 0) {
                Log.e("AudioPolicyProxy", "AudioSystem. removeUserIdDeviceAffinities(" + num + ") failed,  cannot call AudioSystem.setUserIdDeviceAffinities");
                return -1;
            }
            AudioDeviceArray audioDeviceArray = new AudioDeviceArray(iArr, strArr);
            if (setUserIdDeviceAffinitiesOnSystem(i, audioDeviceArray) == 0) {
                this.mUserIdDeviceAffinities.put(num, audioDeviceArray);
                return 0;
            }
            Log.e("AudioPolicyProxy", "AudioSystem.setUserIdDeviceAffinities(" + i + ") failed");
            return -1;
        }

        public int removeUserIdDeviceAffinities(int i) {
            if (this.mUserIdDeviceAffinities.remove(new Integer(i)) != null && removeUserIdDeviceAffinitiesFromSystem(i) == 0) {
                return 0;
            }
            Log.e("AudioPolicyProxy", "AudioSystem.removeUserIdDeviceAffinities failed");
            return -1;
        }

        public final int removeUserIdDeviceAffinitiesFromSystem(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AudioService.this.mAudioSystem.removeUserIdDeviceAffinities(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int setUserIdDeviceAffinitiesOnSystem(int i, AudioDeviceArray audioDeviceArray) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AudioService.this.mAudioSystem.setUserIdDeviceAffinities(i, audioDeviceArray.mDeviceTypes, audioDeviceArray.mDeviceAddresses);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int setupDeviceAffinities() {
            for (Map.Entry entry : this.mUidDeviceAffinities.entrySet()) {
                int removeUidDeviceAffinitiesFromSystem = removeUidDeviceAffinitiesFromSystem(((Integer) entry.getKey()).intValue());
                if (removeUidDeviceAffinitiesFromSystem != 0) {
                    Log.e("AudioPolicyProxy", "setupDeviceAffinities failed to remove device affinity for uid " + entry.getKey());
                    return removeUidDeviceAffinitiesFromSystem;
                }
                int uidDeviceAffinitiesOnSystem = setUidDeviceAffinitiesOnSystem(((Integer) entry.getKey()).intValue(), (AudioDeviceArray) entry.getValue());
                if (uidDeviceAffinitiesOnSystem != 0) {
                    Log.e("AudioPolicyProxy", "setupDeviceAffinities failed to set device affinity for uid " + entry.getKey());
                    return uidDeviceAffinitiesOnSystem;
                }
            }
            for (Map.Entry entry2 : this.mUserIdDeviceAffinities.entrySet()) {
                int removeUserIdDeviceAffinitiesFromSystem = removeUserIdDeviceAffinitiesFromSystem(((Integer) entry2.getKey()).intValue());
                if (removeUserIdDeviceAffinitiesFromSystem != 0) {
                    Log.e("AudioPolicyProxy", "setupDeviceAffinities failed to remove device affinity for userId " + entry2.getKey());
                    return removeUserIdDeviceAffinitiesFromSystem;
                }
                int userIdDeviceAffinitiesOnSystem = setUserIdDeviceAffinitiesOnSystem(((Integer) entry2.getKey()).intValue(), (AudioDeviceArray) entry2.getValue());
                if (userIdDeviceAffinitiesOnSystem != 0) {
                    Log.e("AudioPolicyProxy", "setupDeviceAffinities failed to set device affinity for userId " + entry2.getKey());
                    return userIdDeviceAffinitiesOnSystem;
                }
            }
            return 0;
        }

        public String toLogFriendlyString() {
            String str = (((((super.toLogFriendlyString() + " Uid Device Affinities:\n") + logFriendlyAttributeDeviceArrayMap("Uid", this.mUidDeviceAffinities, "     ")) + " UserId Device Affinities:\n") + logFriendlyAttributeDeviceArrayMap("UserId", this.mUserIdDeviceAffinities, "     ")) + " Proxy:\n") + "   is focus policy= " + this.mIsFocusPolicy + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
            if (this.mIsFocusPolicy) {
                str = ((str + "     focus duck behaviour= " + this.mFocusDuckBehavior + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + "     is test focus policy= " + this.mIsTestFocusPolicy + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + "     has focus listener= " + this.mHasFocusListener + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
            }
            return str + "   media projection= " + this.mProjection + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
        }

        public final String logFriendlyAttributeDeviceArrayMap(String str, Map map, String str2) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : map.entrySet()) {
                sb.append(str2);
                sb.append(str);
                sb.append(": ");
                sb.append(entry.getKey());
                sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                AudioDeviceArray audioDeviceArray = (AudioDeviceArray) entry.getValue();
                String str3 = str2 + "   ";
                for (int i = 0; i < audioDeviceArray.mDeviceTypes.length; i++) {
                    sb.append(str3);
                    sb.append("Type: 0x");
                    sb.append(Integer.toHexString(audioDeviceArray.mDeviceTypes[i]));
                    sb.append(" Address: ");
                    sb.append(audioDeviceArray.mDeviceAddresses[i]);
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                }
            }
            return sb.toString();
        }
    }

    public int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) {
        int dispatchFocusChange;
        if (audioFocusInfo == null) {
            throw new IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (iAudioPolicyCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy callback");
        }
        synchronized (this.mAudioPolicies) {
            if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                throw new IllegalStateException("Unregistered AudioPolicy for focus dispatch");
            }
            dispatchFocusChange = this.mMediaFocusControl.dispatchFocusChange(audioFocusInfo, i);
        }
        return dispatchFocusChange;
    }

    public void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) {
        if (audioFocusInfo == null) {
            throw new IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (iAudioPolicyCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy callback");
        }
        synchronized (this.mAudioPolicies) {
            if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                throw new IllegalStateException("Unregistered AudioPolicy for external focus");
            }
            this.mMediaFocusControl.setFocusRequestResultFromExtPolicy(audioFocusInfo, i);
        }
    }

    public class AsdProxy implements IBinder.DeathRecipient {
        public final IAudioServerStateDispatcher mAsd;

        public AsdProxy(IAudioServerStateDispatcher iAudioServerStateDispatcher) {
            this.mAsd = iAudioServerStateDispatcher;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (AudioService.this.mAudioServerStateListeners) {
                AudioService.this.mAudioServerStateListeners.remove(this.mAsd.asBinder());
            }
        }

        public IAudioServerStateDispatcher callback() {
            return this.mAsd;
        }
    }

    public final void checkMonitorAudioServerStatePermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            throw new SecurityException("Not allowed to monitor audioserver state");
        }
    }

    public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) {
        checkMonitorAudioServerStatePermission();
        synchronized (this.mAudioServerStateListeners) {
            if (this.mAudioServerStateListeners.containsKey(iAudioServerStateDispatcher.asBinder())) {
                Slog.w("AS.AudioService", "Cannot re-register audio server state dispatcher");
                return;
            }
            AsdProxy asdProxy = new AsdProxy(iAudioServerStateDispatcher);
            try {
                iAudioServerStateDispatcher.asBinder().linkToDeath(asdProxy, 0);
            } catch (RemoteException unused) {
            }
            this.mAudioServerStateListeners.put(iAudioServerStateDispatcher.asBinder(), asdProxy);
        }
    }

    public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) {
        checkMonitorAudioServerStatePermission();
        synchronized (this.mAudioServerStateListeners) {
            AsdProxy asdProxy = (AsdProxy) this.mAudioServerStateListeners.remove(iAudioServerStateDispatcher.asBinder());
            if (asdProxy == null) {
                Slog.w("AS.AudioService", "Trying to unregister unknown audioserver state dispatcher for pid " + Binder.getCallingPid() + " / uid " + Binder.getCallingUid());
                return;
            }
            iAudioServerStateDispatcher.asBinder().unlinkToDeath(asdProxy, 0);
        }
    }

    public boolean isAudioServerRunning() {
        checkMonitorAudioServerStatePermission();
        return AudioSystem.checkAudioFlinger() == 0;
    }

    public final Set getAudioHalPids() {
        String str;
        try {
            ArrayList debugDump = IServiceManager.getService().debugDump();
            HashSet hashSet = new HashSet();
            Iterator it = debugDump.iterator();
            while (it.hasNext()) {
                IServiceManager.InstanceDebugInfo instanceDebugInfo = (IServiceManager.InstanceDebugInfo) it.next();
                if (instanceDebugInfo.pid != -1 && (str = instanceDebugInfo.interfaceName) != null && str.startsWith("android.hardware.audio")) {
                    hashSet.add(Integer.valueOf(instanceDebugInfo.pid));
                }
            }
            return hashSet;
        } catch (RemoteException | RuntimeException unused) {
            return new HashSet();
        }
    }

    public final void updateAudioHalPids() {
        Set audioHalPids = getAudioHalPids();
        if (audioHalPids.isEmpty()) {
            Slog.w("AS.AudioService", "Could not retrieve audio HAL service pids");
        } else {
            AudioSystem.setAudioHalPids(audioHalPids.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray());
        }
    }

    public void setMultiAudioFocusEnabled(boolean z) {
        super.setMultiAudioFocusEnabled_enforcePermission();
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        if (mediaFocusControl == null || mediaFocusControl.getMultiAudioFocusEnabled() == z) {
            return;
        }
        this.mMediaFocusControl.updateMultiAudioFocus(z);
        if (z) {
            return;
        }
        this.mDeviceBroker.postBroadcastBecomingNoisy();
    }

    public boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes, long j) {
        Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        enforceModifyAudioRoutingPermission();
        String str = "additional_output_device_delay=" + audioDeviceAttributes.getInternalType() + "," + audioDeviceAttributes.getAddress();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(",");
        sb.append(j);
        return this.mRestorableParameters.setParameters(str, sb.toString()) == 0;
    }

    public long getAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        try {
            return Long.parseLong(AudioSystem.getParameters("additional_output_device_delay=" + audioDeviceAttributes.getInternalType() + "," + audioDeviceAttributes.getAddress()).substring(31));
        } catch (NullPointerException unused) {
            return 0L;
        }
    }

    public long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        try {
            return Long.parseLong(AudioSystem.getParameters("max_additional_output_device_delay=" + audioDeviceAttributes.getInternalType() + "," + audioDeviceAttributes.getAddress()).substring(35));
        } catch (NullPointerException unused) {
            return 0L;
        }
    }

    public void addAssistantServicesUids(int[] iArr) {
        super.addAssistantServicesUids_enforcePermission();
        Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            addAssistantServiceUidsLocked(iArr);
        }
    }

    public void removeAssistantServicesUids(int[] iArr) {
        super.removeAssistantServicesUids_enforcePermission();
        Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            removeAssistantServiceUidsLocked(iArr);
        }
    }

    public int[] getAssistantServicesUids() {
        int[] array;
        super.getAssistantServicesUids_enforcePermission();
        synchronized (this.mSettingsLock) {
            array = this.mAssistantUids.stream().mapToInt(new AudioService$$ExternalSyntheticLambda0()).toArray();
        }
        return array;
    }

    public void setActiveAssistantServiceUids(int[] iArr) {
        super.setActiveAssistantServiceUids_enforcePermission();
        Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            this.mActiveAssistantServiceUids = iArr;
        }
        updateActiveAssistantServiceUids();
    }

    public int[] getActiveAssistantServiceUids() {
        int[] iArr;
        super.getActiveAssistantServiceUids_enforcePermission();
        synchronized (this.mSettingsLock) {
            iArr = (int[]) this.mActiveAssistantServiceUids.clone();
        }
        return iArr;
    }

    public UUID getDeviceSensorUuid(AudioDeviceAttributes audioDeviceAttributes) {
        return this.mDeviceBroker.getDeviceSensorUuid(audioDeviceAttributes);
    }

    public final boolean isFixedVolumeDevice(int i) {
        if (i == 32768 && this.mRecordMonitor.isLegacyRemoteSubmixActive()) {
            return false;
        }
        return this.mFixedVolumeDevices.contains(Integer.valueOf(i));
    }

    public final boolean isFullVolumeDevice(int i) {
        if (i == 32768 && this.mRecordMonitor.isLegacyRemoteSubmixActive()) {
            return false;
        }
        return this.mFullVolumeDevices.contains(Integer.valueOf(i));
    }

    public final boolean isAbsoluteVolumeDevice(int i) {
        return this.mAbsoluteVolumeDeviceInfoMap.containsKey(Integer.valueOf(i));
    }

    public final boolean isA2dpAbsoluteVolumeDevice(int i) {
        return this.mAvrcpAbsVolSupported && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i));
    }

    public static String getSettingsNameForDeviceVolumeBehavior(int i) {
        return "AudioService_DeviceVolumeBehavior_" + AudioSystem.getOutputDeviceName(i);
    }

    public final void persistDeviceVolumeBehavior(int i, int i2) {
        Log.d("AS.AudioService", "Persisting Volume Behavior for DeviceType: " + i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mSettings.putSystemIntForUser(this.mContentResolver, getSettingsNameForDeviceVolumeBehavior(i), i2, -2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int retrieveStoredDeviceVolumeBehavior(int i) {
        return this.mSettings.getSystemIntForUser(this.mContentResolver, getSettingsNameForDeviceVolumeBehavior(i), -1, -2);
    }

    public final void restoreDeviceVolumeBehavior() {
        Iterator it = AudioSystem.DEVICE_OUT_ALL_SET.iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            Log.d("AS.AudioService", "Retrieving Volume Behavior for DeviceType: " + intValue);
            int retrieveStoredDeviceVolumeBehavior = retrieveStoredDeviceVolumeBehavior(intValue);
            if (retrieveStoredDeviceVolumeBehavior == -1) {
                Log.d("AS.AudioService", "Skipping Setting Volume Behavior for DeviceType: " + intValue);
            } else {
                setDeviceVolumeBehaviorInternal(new AudioDeviceAttributes(intValue, ""), retrieveStoredDeviceVolumeBehavior, "AudioService.restoreDeviceVolumeBehavior()");
            }
        }
    }

    public final boolean hasDeviceVolumeBehavior(int i) {
        return retrieveStoredDeviceVolumeBehavior(i) != -1;
    }

    public final boolean addAudioSystemDeviceOutToFixedVolumeDevices(int i) {
        Log.d("AS.AudioService", "Adding DeviceType: 0x" + Integer.toHexString(i) + " to mFixedVolumeDevices");
        return this.mFixedVolumeDevices.add(Integer.valueOf(i));
    }

    public final boolean removeAudioSystemDeviceOutFromFixedVolumeDevices(int i) {
        Log.d("AS.AudioService", "Removing DeviceType: 0x" + Integer.toHexString(i) + " from mFixedVolumeDevices");
        return this.mFixedVolumeDevices.remove(Integer.valueOf(i));
    }

    public final boolean addAudioSystemDeviceOutToFullVolumeDevices(int i) {
        Log.d("AS.AudioService", "Adding DeviceType: 0x" + Integer.toHexString(i) + " to mFullVolumeDevices");
        return this.mFullVolumeDevices.add(Integer.valueOf(i));
    }

    public final boolean removeAudioSystemDeviceOutFromFullVolumeDevices(int i) {
        Log.d("AS.AudioService", "Removing DeviceType: 0x" + Integer.toHexString(i) + " from mFullVolumeDevices");
        return this.mFullVolumeDevices.remove(Integer.valueOf(i));
    }

    public final void addAudioSystemDeviceOutToAbsVolumeDevices(int i, AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo) {
        Log.d("AS.AudioService", "Adding DeviceType: 0x" + Integer.toHexString(i) + " to mAbsoluteVolumeDeviceInfoMap with behavior " + AudioDeviceVolumeManager.volumeBehaviorName(absoluteVolumeDeviceInfo.mDeviceVolumeBehavior));
        this.mAbsoluteVolumeDeviceInfoMap.put(Integer.valueOf(i), absoluteVolumeDeviceInfo);
    }

    public final AbsoluteVolumeDeviceInfo removeAudioSystemDeviceOutFromAbsVolumeDevices(int i) {
        Log.d("AS.AudioService", "Removing DeviceType: 0x" + Integer.toHexString(i) + " from mAbsoluteVolumeDeviceInfoMap");
        return (AbsoluteVolumeDeviceInfo) this.mAbsoluteVolumeDeviceInfoMap.remove(Integer.valueOf(i));
    }

    public final boolean checkNoteAppOp(int i, int i2, String str, String str2) {
        if ("com.android.server.media".equals(str)) {
            return true;
        }
        try {
            return this.mAppOps.noteOp(i, i2, str, str2, (String) null) == 0;
        } catch (Exception e) {
            Log.e("AS.AudioService", "Error noting op:" + i + " on uid:" + i2 + " for package:" + str, e);
            return false;
        }
    }

    public final void initCustomExternalEventReceivers() {
        IntentFilter intentFilter = new IntentFilter();
        addSamsungIntentFilter(intentFilter);
        this.mContext.registerReceiverAsUser(this.mSamsungReceiver, UserHandle.ALL, intentFilter, null, null, 2);
        IntentFilter intentFilter2 = new IntentFilter();
        addMultiSoundIntentFilter(intentFilter2);
        this.mContext.registerReceiverAsUser(this.mSamsungReceiver, UserHandle.ALL, intentFilter2, null, null, 2);
        IntentFilter intentFilter3 = new IntentFilter();
        addMultiSoundNotificationIntentFilter(intentFilter3);
        this.mContext.registerReceiverAsUser(this.mSamsungReceiver, UserHandle.ALL, intentFilter3, null, null, 2);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("androidx.car.app.connection.action.CAR_CONNECTION_UPDATED");
        this.mContext.registerReceiverAsUser(this.mSamsungReceiver, UserHandle.ALL, intentFilter4, null, null, 2);
    }

    public final void onBootCompleted() {
        sendMsg(this.mAudioHandler, 2771, 1, 0, 0, null, 30000);
        RecordingActivityMonitor recordingActivityMonitor = this.mRecordMonitor;
        if (recordingActivityMonitor != null) {
            recordingActivityMonitor.setRecordingEventChecker(this.mRecordEventChecker);
        }
        this.mAudioGameManager = new AudioGameManager(this.mContext);
        this.mSoundAppPolicyManager = SoundAppPolicyManager.getInstance(this.mContext, this.mSettingHelper);
        final int intValue = this.mSettingHelper.getIntValue("APP_LIST_VERSION", 0);
        final int intValue2 = this.mSettingHelper.getIntValue("LIVE_TRANSLATE_ALLOW_LIST_VERSION", -1);
        AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                AudioService.this.lambda$onBootCompleted$18(intValue, intValue2);
            }
        });
        PackageListHelper packageListHelper = PackageListHelper.getInstance(this.mContext);
        this.mPackageListHelper = packageListHelper;
        packageListHelper.initPackageList(this.mContext);
        if (Rune.SEC_AUDIO_FM_RADIO) {
            this.mAudioSystem.setParameters("g_fmradio_enable=false");
        }
        updateReceiverSupported();
        this.mDeviceBroker.updateDeviceQuickConnection(true, 2, "", "speaker", 0);
        if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
            this.mExt.getVolumeMonitorService().setPlaybackActivityMonitor(this.mPlaybackMonitor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootCompleted$18(int i, int i2) {
        if (2022070700 > i) {
            this.mSoundAppPolicyManager.setDefaultAllowList();
        }
        Log.i("AS.AudioService", "SoundAppPolicy APP_LIST_VERSION =" + i);
        SoundAppPolicyManager.setBtGameLatencyList(this.mSettingHelper.getAppList());
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            Log.i("AS.AudioService", "CallPplicy LIVE_TRANSLATE_ALLOW_LIST_VERSION =" + i2);
            if (i2 == -1 || !this.mSettingHelper.initCallPolicyAllowListFromDb()) {
                this.mSoundAppPolicyManager.setDefaultCallPolicyAllowList();
            }
        }
    }

    public final void handleCustomMessage(Message message) {
        switch (message.what) {
            case 2759:
                playSilentModeSound();
                return;
            case 2760:
                callVibrateMsg((String) message.obj, message.arg1);
                return;
            case 2761:
                this.mIsVibrate = false;
                return;
            case 2762:
                this.mExt.notifyDVFSToSoundAlive(this.mContext, message.arg1);
                return;
            case 2763:
                boolean booleanValue = ((Boolean) message.obj).booleanValue();
                boolean z = message.arg1 == 1;
                boolean z2 = message.arg2 == 1;
                Intent intent = new Intent("android.intent.action.MULTISOUND_STATE_CHANGE");
                Log.d("AS.AudioService", "MSG_SET_MULTI_DEVICE_SOUND_ON " + booleanValue);
                Settings.Global.putInt(this.mContentResolver, "multisound_state", booleanValue ? 1 : 0);
                if (!booleanValue) {
                    this.mMultiSoundManager.disable();
                } else if (z2) {
                    this.mMultiSoundManager.shouldEnable();
                } else {
                    this.mMultiSoundManager.enable(z);
                    int devicesForStream = AudioSystem.getDevicesForStream(3);
                    if (getPinDeviceInternal() == devicesForStream && z) {
                        this.mMultiSoundManager.showHeadUpNotification(devicesForStream, this.mDeviceBroker.getPriorityDevice(devicesForStream));
                    }
                }
                if (!this.mIsLeBroadCasting) {
                    sendBroadcastToAll(intent, null);
                }
                MediaSessionService.MediaSessionServiceInternal mediaSessionServiceInternal = this.mMediaSessionServiceInternal;
                if (mediaSessionServiceInternal != null) {
                    mediaSessionServiceInternal.updateMultiSoundInfo(-1, isMultiSoundOn());
                    return;
                }
                return;
            case 2764:
                onSetAppDevice((String) message.obj, message.arg1, message.arg2, true);
                return;
            case 2765:
                int i = message.arg1;
                if (isMultiSoundOn() && i != getPinDeviceInternal()) {
                    this.mMultiSoundManager.clearHeadUpNotification();
                    return;
                } else {
                    this.mMultiSoundManager.showNotification();
                    return;
                }
            case 2766:
                int i2 = message.arg1;
                if (i2 == 0) {
                    Settings.Global.putInt(this.mContentResolver, "mode_ringer_time_on", 0);
                    return;
                }
                Settings.Global.putInt(this.mContentResolver, "mode_ringer_time", i2);
                int i3 = i2 * 60000;
                this.mMuteIntervalMs = i3;
                if (i3 != 0) {
                    PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.sec.media.action.mute_interval"), 201326592);
                    long currentTimeMillis = System.currentTimeMillis() + this.mMuteIntervalMs;
                    this.mMuteTime = currentTimeMillis;
                    this.mAlarmManager.setExact(0, currentTimeMillis, broadcast);
                    return;
                }
                return;
            case 2767:
                MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
                if (mediaFocusControl != null) {
                    mediaFocusControl.setDevice(message.arg1);
                    MediaSessionService.MediaSessionServiceInternal mediaSessionServiceInternal2 = this.mMediaSessionServiceInternal;
                    if (mediaSessionServiceInternal2 != null) {
                        mediaSessionServiceInternal2.updateMultiSoundInfo(message.arg1, isMultiSoundOn());
                        return;
                    }
                    return;
                }
                return;
            case 2768:
                sendBroadcastToAll((Intent) message.obj, null);
                return;
            case 2769:
            default:
                return;
            case 2770:
                if (message.arg1 == 1) {
                    throw null;
                }
                return;
            case 2771:
                onInitSoundAssistant();
                return;
            case 2772:
                if (Rune.SEC_AUDIO_RECORDING_POPUP) {
                    int i4 = message.arg1;
                    int i5 = message.arg2;
                    Log.d("AS.AudioService", "[RECORDING POPUP] showRecordingPopup uid=" + i4 + ", type=" + i5);
                    String parameters = AudioSystem.getParameters("l_specify_recording_info");
                    if (parameters == null || parameters.isEmpty()) {
                        RecordingPopupHelper.showRecordingPopup(this.mContext, i4, i5);
                        return;
                    }
                    return;
                }
                return;
            case 2773:
                if (Rune.SEC_AUDIO_TRANSITION_EFFECT) {
                    onAvrcpSupportsAbsoluteVolumeToAudioServer((String) message.obj, message.arg1 == 1);
                    return;
                }
                return;
            case 2774:
                onSetAppDevice((String) message.obj, message.arg1, message.arg2, false);
                return;
            case 2775:
                setMultiSoundOn(isMultiSoundOn());
                setStreamMute(1, message.arg1 == 1);
                setStreamMute(5, message.arg1 == 1);
                return;
        }
    }

    public void setAudioServiceConfig(String str) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        String str9;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        AudioParameter audioParameter = new AudioParameter(str);
        if (audioParameter.hasLocalParameter()) {
            checkModifyPhoneStateOrRoutingPermission();
        }
        String str10 = audioParameter.get("l_forced_device");
        if (str10 != null) {
            setForceUseForMedia(Integer.parseInt(str10));
        } else if (audioParameter.hasKey("l_volume_limit_key")) {
            setVolumeLimiter(audioParameter);
        } else {
            String str11 = audioParameter.get("g_call_extra_volume_enable");
            if (str11 != null) {
                this.mExt.setExtraVolume("true".equals(str11));
            } else {
                if (audioParameter.hasKey("sound_assistant")) {
                    setSoundAssistant(audioParameter);
                    return;
                }
                String str12 = audioParameter.get("g_call_sar_backoff_enable");
                if (str12 != null) {
                    checkModifyPhoneStateOrRoutingPermission();
                    Log.d("AS.AudioService", "g_call_sar_backoff_enable=" + str12);
                    this.mDeviceBroker.mSarBackoffParam = "true".equalsIgnoreCase(str12);
                    this.mDeviceBroker.postSarControl();
                    return;
                }
                if (Rune.SEC_AUDIO_FM_RADIO && (str9 = audioParameter.get("g_fmradio_enable")) != null) {
                    this.mAudioSystem.setParameters(str);
                    if (this.mSystemReady) {
                        this.mAudioSystem.invalidateRoutingCache();
                        observeDevicesForMediaStream();
                        if ("true".equals(str9)) {
                            Log.d("AS.AudioService", "init fm radio volume when turning on fm radio, " + getPackageName(Binder.getCallingUid())[0]);
                            int fineVolume = getFineVolume(3, 0);
                            sendVolumeChangedIntent(3, fineVolume, fineVolume, 0);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String str13 = audioParameter.get("g_call_sim_slot");
                if (str13 != null) {
                    checkModifyPhoneStateOrRoutingPermission();
                    if (!"0x10".equals(str13)) {
                        this.mPhoneType = str13;
                    } else {
                        this.mPhoneType = "0x01".equals(this.mPhoneType) ? "0x02" : "0x01";
                    }
                } else if (audioParameter.get("g_sco_rvc_support") != null) {
                    checkModifyPhoneStateOrRoutingPermission();
                    SemAudioSystem.setPolicyParameters(str);
                } else {
                    String str14 = audioParameter.get("g_call_memo_state");
                    if (str14 != null) {
                        checkModifyPhoneStateOrRoutingPermission();
                        this.mCallMemoState = str14;
                    } else {
                        String str15 = audioParameter.get("g_call_forwarding_enable");
                        if (str15 != null) {
                            checkModifyPhoneStateOrRoutingPermission();
                            this.mCallForwardingEnable = str15;
                        } else {
                            String str16 = audioParameter.get("g_effect_dv_adapt_sound");
                            if (str16 != null) {
                                checkModifyPhoneStateOrRoutingPermission();
                                this.mDvDhaparam = str16;
                            } else {
                                String str17 = audioParameter.get("HACSetting");
                                if (str17 != null) {
                                    this.mHAC = str17;
                                } else if (audioParameter.hasKey("l_voice_tx_control_mode") && audioParameter.hasKey("l_voice_rx_control_mode") && audioParameter.hasKey("l_call_translation_mode")) {
                                    this.mExt.setVoiceTxControlMode(Integer.parseInt(audioParameter.get("l_voice_tx_control_mode")));
                                    this.mExt.setVoiceRxControlMode(Integer.parseInt(audioParameter.get("l_voice_rx_control_mode")));
                                    String str18 = audioParameter.get("l_call_translation_mode");
                                    this.mExt.setCallTranslationMode("on".equalsIgnoreCase(str18));
                                    this.mMicModeManager.updateCallTranslationState("on".equalsIgnoreCase(str18));
                                } else {
                                    if (audioParameter.hasKey("l_volume_prevent_overheat_key")) {
                                        setPreventOverheatForGame(audioParameter);
                                        return;
                                    }
                                    if (audioParameter.hasKey("sound_effect_volume")) {
                                        checkModifyPhoneStateOrRoutingPermission();
                                        AudioFxHelper.setSoundEffectVolume();
                                    } else if (audioParameter.get("g_ptt_mode") != null) {
                                        SemAudioSystem.setPolicyParameters(str);
                                    } else {
                                        String str19 = audioParameter.get("g_ptt_call_volume_enable");
                                        if (str19 != null) {
                                            this.mExt.setPttCallVolumeEnabled("true".equals(str19));
                                            Log.i("AS.AudioService", "mIsPttCallVolumeEnabled : " + this.mExt.isPttCallVolumeEnabled());
                                            return;
                                        }
                                        if (Rune.SEC_AUDIO_VIDEO_CALL_VOICE_EFFECT && audioParameter.hasKey("l_mic_input_control_mode")) {
                                            this.mExt.setVideoCallVoiceEffectMode(audioParameter.getInt("l_mic_input_control_mode", 0));
                                        } else if (Rune.SEC_AUDIO_VIDEO_CALL_VOICE_DEFAULT_EFFECT && audioParameter.hasKey("l_mic_input_control_mode")) {
                                            this.mExt.setVideoCallVoiceEffectMode(audioParameter.getInt("l_mic_input_control_mode", 100));
                                        } else if (Rune.SEC_AUDIO_CALL_MONSTER_SOUND && audioParameter.hasKey("l_call_nc_booster_enable")) {
                                            this.mExt.setVoiceCallMonsterSoundMode(audioParameter.getInt("l_call_nc_booster_enable", 0));
                                        } else if (Rune.SEC_AUDIO_CALL_MONSTER_SOUND && audioParameter.hasKey("l_mic_input_control_mode_2mic")) {
                                            this.mExt.setVideoCallMonsterSoundMode(audioParameter.getInt("l_mic_input_control_mode_2mic", 0));
                                        } else {
                                            if (Rune.SEC_AUDIO_VOLUME_MONITOR && (str8 = audioParameter.get("g_volume_monitor_warning_level")) != null) {
                                                safeMediaVolumeFromVolumeMonitor(str8);
                                                return;
                                            }
                                            if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3 && audioParameter.hasKey("l_set_safe_media_volume")) {
                                                this.mSoundDoseHelper.enforceSafeMediaVolume("safeMediaVolumeFromVolumeMonitor");
                                                return;
                                            }
                                            if (Rune.SEC_AUDIO_SCREEN_CALL && (str7 = audioParameter.get("l_screen_call")) != null) {
                                                this.mExt.setScreenCall("on".equals(str7));
                                                SemAudioSystem.setPolicyParameters(str);
                                            } else if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER && (str6 = audioParameter.get("l_call_voip_extra_volume_enable")) != null) {
                                                this.mExt.setVoipExtraVolume("true".equals(str6));
                                            } else if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING && (str5 = audioParameter.get("l_call_voip_extra_volume_enable")) != null) {
                                                this.mExt.setVoipAntiHowling("true".equals(str5));
                                            } else {
                                                String str20 = audioParameter.get("g_effect_headtracker_available");
                                                if (str20 != null) {
                                                    this.mSpatializerHelper.setSecHeadTrackerAvailable("true".equals(str20));
                                                } else if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI && (str4 = audioParameter.get("l_call_translation_mode")) != null) {
                                                    this.mMicModeManager.updateCallTranslationState("on".equalsIgnoreCase(str4));
                                                } else if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI && (str3 = audioParameter.get("g_call_state")) != null) {
                                                    int parseInt = Integer.parseInt(str3);
                                                    int i = parseInt & 32;
                                                    if (i != 0) {
                                                        this.mMicModeManager.updateWifiCallState(true);
                                                    } else if ((parseInt & 16) != 0) {
                                                        this.mMicModeManager.updateWifiCallState(false);
                                                    }
                                                    int micModeType = this.mMicModeManager.getMicModeType();
                                                    MicModeManager micModeManager = this.mMicModeManager;
                                                    if (micModeType == 4 || micModeManager.getMicModeType() == 5) {
                                                        int i2 = 33554432 & parseInt;
                                                        if (i2 != 0 || (parseInt & 1024) != 0) {
                                                            this.mMicModeManager.updateVideoCallState(true);
                                                        } else if ((parseInt & 16) != 0 || ((i != 0 && i2 == 0) || (parseInt & 256) != 0 || (parseInt & 512) != 0)) {
                                                            this.mMicModeManager.updateVideoCallState(false);
                                                        }
                                                    }
                                                } else if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE && (str2 = audioParameter.get("g_call_voip_package_name")) != null) {
                                                    this.mMicModeManager.updateRealPackageName(str2);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (str.startsWith("factory_test")) {
            sFactoryTestLogger.enqueue(new EventLogger.StringEvent("uid/pid = " + Binder.getCallingUid() + "/" + Binder.getCallingPid() + " : " + str));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss:SSS");
            if (str.contains("factory_test_loopback=on")) {
                String str21 = "on (" + simpleDateFormat.format(new Date(System.currentTimeMillis())) + " uid/pid = " + Binder.getCallingUid() + "/" + Binder.getCallingPid() + " : " + str + ")";
                this.mLoopbackState = str21;
                this.mLastLoopbackOn = str21;
            } else if (str.contains("factory_test_loopback=off")) {
                this.mLoopbackState = "off (" + simpleDateFormat.format(new Date(System.currentTimeMillis())) + " uid/pid = " + Binder.getCallingUid() + "/" + Binder.getCallingPid() + " : " + str + ")";
            }
        }
        if (str.contains("g_sco_samplerate")) {
            this.mBtScoDeviceInfo = str;
        }
        if (hasCallSetupParameters(str)) {
            this.mAudioSystem.setCallParameters(str);
        } else {
            this.mAudioSystem.setParameters(str);
        }
    }

    public String getAudioServiceConfig(String str) {
        if (str == null) {
            return null;
        }
        AudioParameter audioParameter = new AudioParameter(str);
        if (audioParameter.hasKey("l_smart_view_split_sound_enable")) {
            if (ScreenSharingHelper.isSplitSoundEnabled()) {
                return "true";
            }
        } else if (audioParameter.hasKey("g_call_extra_volume_enable")) {
            if (this.mExt.isExtraVolume()) {
                return "true";
            }
        } else {
            if (audioParameter.hasKey("g_sound_path_active_address")) {
                return this.mDeviceBroker.getAddressForDevice(getDeviceForStream(3));
            }
            if (audioParameter.hasKey("g_sound_path_available_devices")) {
                int makeDeviceBit = SemAudioSystem.makeDeviceBit(this.mDeviceBroker.getAvailableDeviceSetForQuickSoundPath());
                if (this.mMultiSoundManager.isRemoteSubmixAppExist()) {
                    Log.d("AS.AudioService", "TV app is exist, removed r-submix");
                    makeDeviceBit &= -32769;
                }
                return Integer.toHexString(makeDeviceBit);
            }
            if (audioParameter.hasKey("enable")) {
                if (this.mVolumeLimitOn) {
                    return "true";
                }
            } else {
                if (audioParameter.hasKey("level")) {
                    return Integer.toString(this.mVolumeLimitValue);
                }
                if (audioParameter.hasKey("sound_assistant")) {
                    return getSoundAssistant(str.substring(16));
                }
                if (str.contains("l_multi_sound_key") || str.contains("l_safe_media_volume_enable") || str.contains("g_multi_sound_pin_app_name") || str.contains("g_multi_sound_priority_device")) {
                    return getMultiSoundConfig(str);
                }
                if (audioParameter.hasKey("l_record_active_enable")) {
                    return String.valueOf(AudioUtils.isRecordActive(audioParameter.getInt("l_record_active_enable", -1)));
                }
                if (audioParameter.hasKey("l_stream_active")) {
                    return String.valueOf(isStreamActiveForExternal(audioParameter.getInt("l_stream_active", -1)));
                }
                if (Rune.SEC_AUDIO_SCREEN_CALL && audioParameter.hasKey("l_screen_call")) {
                    if (this.mExt.isScreenCall()) {
                        return "true";
                    }
                } else if (audioParameter.hasKey("l_call_translation_mode")) {
                    if (this.mExt.isCallTranslationMode()) {
                        return "true";
                    }
                } else {
                    if (audioParameter.hasKey("l_voice_tx_control_mode")) {
                        return String.valueOf(this.mExt.getVoiceTxControlMode());
                    }
                    if (audioParameter.hasKey("l_voice_rx_control_mode")) {
                        return String.valueOf(this.mExt.getVoiceRxControlMode());
                    }
                    return AudioSystem.getParameters(str);
                }
            }
        }
        return "false";
    }

    public final void checkModifyPhoneStateOrRoutingPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("com.sec.android.permission.IN_APP_SOUND") != 0) {
            throw new SecurityException("Not allowed to audio routing");
        }
    }

    public final void onCustomSystemReady() {
        int uidForPackage;
        Trace.traceBegin(256L, "onCustomSystemReady");
        Log.d("AS.AudioService", "onCustomSystemReady");
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        this.mPlaybackMonitor.setAudioHandler(this.mAudioHandler);
        if (Rune.SEC_AUDIO_RECORDING_POPUP) {
            this.mRecordMonitor.setAudioHandler(this.mAudioHandler);
        }
        this.mPhoneType = null;
        this.mCallMemoState = "false";
        this.mCallForwardingEnable = "false";
        this.mHAC = "off";
        this.mAudioSystem.setParameters("l_set_from_audioservice");
        this.mMediaSessionServiceInternal = (MediaSessionService.MediaSessionServiceInternal) LocalServices.getService(MediaSessionService.MediaSessionServiceInternal.class);
        this.mScreenSharingHelper = ScreenSharingHelper.getInstance(this.mContext, this.mMediaFocusControl);
        DesktopModeHelper desktopModeHelper = DesktopModeHelper.getInstance(this.mContext);
        this.mDesktopModeHelper = desktopModeHelper;
        desktopModeHelper.registerListener();
        MultiVolumeController multiVolumeController = new MultiVolumeController();
        this.mVolumeController = multiVolumeController;
        if (Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME) {
            this.mSoundDoseHelper.setSafeVolumeController(multiVolumeController);
            boolean z = Settings.System.getInt(this.mContentResolver, "ear_shock_condition", 0) != 0;
            this.mIsVolumeEffectOn = z;
            this.mSoundDoseHelper.initSafeMediaVolumeIndex(z);
        }
        AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                AudioService.this.lambda$onCustomSystemReady$19();
            }
        });
        ContentResolver contentResolver = this.mContentResolver;
        boolean z2 = Settings.Global.getInt(contentResolver, "multisound_state", 0) != 0;
        String string = Settings.System.getString(contentResolver, "multisound_app");
        int i = Settings.System.getInt(contentResolver, "multisound_devicetype", -1);
        this.mMultiSoundManager.updateAudioServiceNotificationChannel();
        setMultiSoundOn(z2, true);
        if (!TextUtils.isEmpty(string)) {
            for (String str : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                if (!TextUtils.isEmpty(str) && i != -1 && (uidForPackage = AudioUtils.getUidForPackage(this.mContext, str)) != 0) {
                    try {
                        setAppDevice(uidForPackage, i == 1 ? 8 : 2, true);
                    } catch (IllegalArgumentException e) {
                        Log.e("AS.AudioService", "set app device failed", e);
                    }
                }
            }
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCustomSystemReady$19() {
        AudioManagerInternal audioManagerInternal = (AudioManagerInternal) LocalServices.getService(AudioManagerInternal.class);
        if (audioManagerInternal != null) {
            audioManagerInternal.setRingerModeDelegate(new SamsungRingerModeDelegate(this.mContext));
        }
    }

    public final void setupCustomRoutine() {
        Trace.traceBegin(256L, "setupCustomRoutine");
        Log.i("AS.AudioService", "start setupCustomRoutine");
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        if (Rune.SEC_AUDIO_VOLUME_KEY_SUPPORT_SILENT) {
            this.mVolumePolicy = new VolumePolicy(true, true, true, 400);
        } else {
            this.mVolumePolicy = new VolumePolicy(!this.mHasVibrator, true, true, 400);
        }
        reloadVariableMediaVolumeSteps();
        this.mSavedSpeakerMediaIndex = this.mSettingHelper.getIntValue("speaker_media_index", -1);
        this.mMuteMediaByVibrateOrSilentMode = this.mSettingHelper.getBooleanValue("mute_media_by_vibrate_or_silent_mode");
        if (this.mSystemServer.isPrivileged()) {
            LocalServices.addService(SemAudioServiceInternal.class, new SemAudioServiceInternal(this));
        }
        Log.i("AS.AudioService", "end setupCustomRoutine");
        Trace.endSection();
    }

    public final void addMultiSoundIntentFilter(IntentFilter intentFilter) {
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
    }

    public final void addMultiSoundNotificationIntentFilter(IntentFilter intentFilter) {
        intentFilter.addAction("android.intent.action.SAS_NOTIFICATION_CLEAR");
        intentFilter.addAction("android.intent.action.TurnOff_MultiSound");
        intentFilter.addAction("com.samsung.android.audio.headup.changedevice");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
    }

    public final void addSamsungIntentFilter(IntentFilter intentFilter) {
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.ACTION_SUBINFO_RECORD_UPDATED");
        intentFilter.addAction("com.samsung.intent.action.DLNA_STATUS_CHANGED");
        intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE.Audio");
        intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE.voip-live-translate-allow-list-a7f6");
        }
        intentFilter.addAction("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE");
        intentFilter.addAction("com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED");
        intentFilter.addAction("com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE");
        intentFilter.addAction("com.sec.media.action.mute_interval");
        intentFilter.addAction("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED");
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
        intentFilter.addAction("com.sec.android.intent.action.SPLIT_SOUND");
        intentFilter.addAction("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
        if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
            intentFilter.addAction("com.samsung.bluetooth.device.action.AUDIO_TYPE_CHANGED");
        }
    }

    public final void onCustomAudioServerDied() {
        this.mMultiSoundManager.resetByAudioServerDied();
        this.mDesktopModeHelper.restoreDexState();
        if (this.mDvDhaparam != null) {
            this.mAudioSystem.setParameters("g_effect_dv_adapt_sound=" + this.mDvDhaparam);
            Log.d("AS.AudioService", "onRestoreAudioServerDied - setAdaptSound: gain dv dha Parameter : " + this.mDvDhaparam);
        }
        this.mAudioSystem.setParameters("HACSetting=" + this.mHAC);
        if (isInCommunication()) {
            StringBuilder sb = new StringBuilder();
            if (this.mMode.get() == 2) {
                sb.append("g_call_state=2;");
                if (this.mPhoneType != null) {
                    sb.append("g_call_sim_slot=");
                    sb.append(this.mPhoneType);
                    sb.append(KnoxVpnFirewallHelper.DELIMITER);
                }
                if (!"false".equals(this.mCallMemoState)) {
                    sb.append("g_call_memo_state=");
                    sb.append(this.mCallMemoState);
                    sb.append(KnoxVpnFirewallHelper.DELIMITER);
                }
                if ("true".equals(this.mCallForwardingEnable)) {
                    sb.append("g_call_forwarding_enable=");
                    sb.append(this.mCallForwardingEnable);
                    sb.append(KnoxVpnFirewallHelper.DELIMITER);
                }
                if (Rune.SEC_AUDIO_SCREEN_CALL && this.mExt.isScreenCall()) {
                    sb.append("l_screen_call=");
                    sb.append("on");
                    sb.append(KnoxVpnFirewallHelper.DELIMITER);
                }
            }
            Log.e("AS.AudioService", "onAudioServerDied(): " + ((Object) sb));
            this.mAudioSystem.setParameters(sb.toString());
        }
        BtUtils.recoveryAuracastAppListToNative();
        this.mExt.onAudioServerDied();
    }

    public final void readPersistedCustomSettings() {
        ContentResolver contentResolver = this.mContentResolver;
        this.mAdjustMediaVolumeOnly = Settings.System.getIntForUser(contentResolver, "adjust_media_volume_only", AudioServiceExt.getDefaultVolumeOption(), -2) > 0;
        int intValue = this.mSettingHelper.getIntValue("ring_through_headset", 32);
        this.mHeadsetOnlyStream = intValue;
        setHeadsetOnlyStream(intValue);
        int intValue2 = this.mSettingHelper.getIntValue("sound_lr_switch", 0);
        this.mLRSwitching = intValue2;
        if (intValue2 == 1) {
            this.mAudioSystem.setParameters("l_sound_assistant_lr_switch_enable=true");
        }
        this.mMediaVolumeStepIndex = this.mSettingHelper.getIntValue("media_volume_step_index", 10);
        Settings.Global.putInt(this.mContentResolver, "mode_ringer_time_on", 0);
        this.mVolumeLimitOn = Settings.System.getInt(contentResolver, "volumelimit_on", 0) != 0;
        this.mVolumeLimitValue = Settings.System.getInt(contentResolver, "volume_limiter_value", this.mVolumeLimitValue);
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            this.mLiveTranslateDuringCall = Settings.Global.getInt(this.mContentResolver, "translate_during_calls", 1) != 0;
        }
    }

    public class SamsungBroadcastReceiver extends BroadcastReceiver {
        public SamsungBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                handleIntent(context, intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handleIntent(Context context, final Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                AudioService.this.mMultiSoundManager.updateAudioServiceNotificationChannel();
                return;
            }
            String str = null;
            if ("android.intent.action.PACKAGE_REMOVED".equals(action) && !intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
                Log.d("AS.AudioService", schemeSpecificPart + "(" + intExtra + ") is removed");
                BtUtils.checkAndUpdateAuracastApp(intExtra, stringExtra, 0);
                if ("com.samsung.android.oneconnect".equals(schemeSpecificPart)) {
                    AudioService.this.mMultiSoundManager.resetPinDevice();
                } else {
                    if (AudioService.this.mMultiSoundManager.getAppDevice(intExtra, true) != 0 && AudioService.this.mMultiSoundManager.removeItem(intExtra)) {
                        String string = Settings.System.getString(AudioService.this.mContentResolver, "multisound_app");
                        Log.d("AS.AudioService", "SEC_AUDIO_MULTI_SOUND::ACTION_PACKAGE_REMOVED ( current Packagelist = " + string);
                        if (!TextUtils.isEmpty(string)) {
                            for (String str2 : string.split(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                                if (!TextUtils.isEmpty(str2) && !TextUtils.equals(str2, schemeSpecificPart)) {
                                    str = TextUtils.isEmpty(str) ? str2 : str + XmlUtils.STRING_ARRAY_SEPARATOR + str2;
                                }
                            }
                        }
                        if (TextUtils.isEmpty(str)) {
                            AudioService.sendMsg(AudioService.this.mAudioHandler, 2763, 2, 0, 0, Boolean.FALSE, 0);
                            AudioService.this.mMultiSoundManager.showToast();
                        }
                        Log.d("AS.AudioService", "SEC_AUDIO_MULTI_SOUND::ACTION_PACKAGE_REMOVED ( new Packagelist = " + str);
                        Settings.System.putString(AudioService.this.mContentResolver, "multisound_app", str);
                    }
                    if (AudioService.this.mMediaFocusControl.getIgnoredUid() == intExtra) {
                        AudioService.this.mMediaFocusControl.setIgnoreAudioFocus(intExtra, false);
                    }
                    AudioService.this.mPackageListHelper.removePackageForName(AudioService.this.mContext, schemeSpecificPart);
                    AudioService.this.mMultiSoundManager.setAppVolume(intExtra, 100);
                }
                if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
                    AudioService audioService = AudioService.this;
                    KaraokeUtils.checkAndBroadcastKaraokeInstalled(audioService.mContext, audioService.mSettingHelper, schemeSpecificPart, false);
                }
                AudioService.this.mAppVolumeFromSoundAssistant.delete(intExtra);
                if ("com.samsung.android.soundassistant".equals(schemeSpecificPart)) {
                    AudioService.this.clearSoundAssistantSettings();
                }
                AudioService.this.unSetSoundSettingEventBroadcastIntent(schemeSpecificPart);
                if (AudioService.this.mAudioGameManager == null || !AudioService.this.mAudioGameManager.isGamePackager(schemeSpecificPart)) {
                    return;
                }
                AudioService.this.mAudioGameManager.deleteGameUid(intExtra);
                return;
            }
            if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(action)) {
                String schemeSpecificPart2 = intent.getData().getSchemeSpecificPart();
                Log.d("AS.AudioService", schemeSpecificPart2 + "(" + intent.getIntExtra("android.intent.extra.UID", -1) + ") is data cleared");
                if ("com.samsung.android.soundassistant".equals(schemeSpecificPart2)) {
                    AudioService.this.resetVolumeStar();
                    AudioService.this.clearSoundAssistantSettings();
                    AudioService.this.unSetSoundSettingEventBroadcastIntent(schemeSpecificPart2);
                    AudioService.this.mExt.resetConcertHall();
                    return;
                }
                return;
            }
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                String schemeSpecificPart3 = intent.getData().getSchemeSpecificPart();
                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                if (AudioService.this.isInAllowedList(schemeSpecificPart3)) {
                    AudioService.this.mPackageListHelper.addPackage(AudioService.this.mContext, intExtra2, schemeSpecificPart3);
                }
                if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
                    AudioService audioService2 = AudioService.this;
                    KaraokeUtils.checkAndBroadcastKaraokeInstalled(audioService2.mContext, audioService2.mSettingHelper, schemeSpecificPart3, true);
                }
                if (AudioService.this.mAudioGameManager != null && AudioService.this.mAudioGameManager.isGamePackager(schemeSpecificPart3)) {
                    AudioService.this.mAudioGameManager.addGameUid(intExtra2, true);
                }
                BtUtils.checkAndUpdateAuracastApp(intExtra2, schemeSpecificPart3, 1);
                return;
            }
            if ("android.intent.action.TurnOff_MultiSound".equals(action)) {
                AudioService.sendMsg(AudioService.this.mAudioHandler, 2763, 2, 0, 0, Boolean.FALSE, 0);
                Intent intent2 = new Intent();
                intent2.setPackage("com.samsung.android.setting.multisound");
                intent2.setAction("com.samsung.intent.action.MULTISOUND_STATE_CHANGED");
                intent2.putExtra("enabled", false);
                AudioService.this.sendBroadcastToAll(intent2, null);
                return;
            }
            if ("android.intent.action.SAS_NOTIFICATION_CLEAR".equals(action)) {
                AudioService.this.mMultiSoundManager.clearNotification();
                return;
            }
            if ("com.samsung.android.audio.headup.changedevice".equals(action)) {
                int priorityDevice = AudioService.this.mDeviceBroker.getPriorityDevice(AudioService.this.getPinDeviceInternal());
                AudioService audioService3 = AudioService.this;
                audioService3.setDeviceToForceByUser(priorityDevice, audioService3.mDeviceBroker.getAddressForDevice(priorityDevice), false);
                AudioService.this.mMultiSoundManager.clearHeadUpNotification();
                return;
            }
            if ("com.samsung.intent.action.DLNA_STATUS_CHANGED".equals(action)) {
                AudioService.this.mScreenSharingHelper.setDLNAEnabled(intent.getIntExtra("status", 0) == 1);
                Log.d("AS.AudioService", "mIsDLNAEnabled:" + AudioService.this.mScreenSharingHelper.isDLNAEnabled());
                return;
            }
            if ("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE".equals(action)) {
                int intExtra3 = intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0);
                int deviceForStream = AudioService.this.getDeviceForStream(3);
                boolean isWifiDisplayConnected = ScreenSharingHelper.isWifiDisplayConnected();
                Log.i("AS.AudioService", "WifiDisplay device state:" + intExtra3 + " isConnected:" + isWifiDisplayConnected);
                AudioService.this.mDeviceBroker.checkSendBecomingNoisyIntent(32768, intExtra3, deviceForStream);
                AudioService.this.mDeviceBroker.updateDeviceQuickConnection(intExtra3 == 1, 32768, "0", "remote_submix", 0);
                if (intExtra3 == 1 && !isWifiDisplayConnected) {
                    Log.d("AS.AudioService", "WifiDisplay is connected.");
                    if (AudioService.this.mScreenSharingHelper.getLiveCaptionEnabled(AudioService.this.mContext)) {
                        AudioService.this.mFullVolumeDevices.remove(32768);
                        AudioService.this.mFixedVolumeDevices.remove(32768);
                        AudioService.this.checkAllFixedVolumeDevices(3);
                        AudioService.this.mStreamStates[3].applyAllVolumes();
                    }
                    ScreenSharingHelper.setWifiDisplayConnected(true);
                    AudioService.this.mScreenSharingHelper.registerDeviceStatusListener(AudioService.this.mContext);
                } else if (intExtra3 == 0 && isWifiDisplayConnected) {
                    Log.d("AS.AudioService", "WifiDisplay is disconnected.");
                    if (AudioService.this.mScreenSharingHelper.getLiveCaptionEnabled(AudioService.this.mContext)) {
                        AudioService.this.mFullVolumeDevices.add(32768);
                        AudioService.this.mFixedVolumeDevices.add(32768);
                        AudioService.this.checkAllFixedVolumeDevices(3);
                        AudioService.this.mStreamStates[3].applyAllVolumes();
                    }
                    ScreenSharingHelper.setWifiDisplayConnected(false);
                    if (ScreenSharingHelper.isSplitSoundEnabled() && AudioService.this.isInCommunication()) {
                        AudioService.this.mDeviceBroker.postBroadcastBecomingNoisy();
                    }
                    ScreenSharingHelper.setSplitSoundEnabled(false);
                    AudioService.this.mScreenSharingHelper.unregisterDeviceStatusListener(AudioService.this.mContext);
                }
                if (intExtra3 == 1) {
                    AudioService.this.mScreenSharingHelper.setSupportDisplayVolumeControl(intent.getBooleanExtra("isSupportDisplayVolumeControl", false));
                } else {
                    AudioService.this.mScreenSharingHelper.setSupportDisplayVolumeControl(false);
                }
                AudioService.this.mScreenSharingHelper.setScreenSharingStateResumed(AudioService.this.mScreenSharingHelper.isSupportDisplayVolumeControl());
                Log.d("AS.AudioService", "isSupportDisplayVolumeControl:" + AudioService.this.mScreenSharingHelper.isSupportDisplayVolumeControl());
                return;
            }
            if ("com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED".equals(action)) {
                if (intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0) == 1) {
                    AudioService.this.mScreenSharingHelper.setSupportDisplayVolumeControl(intent.getBooleanExtra("isSupportDisplayVolumeControl", false));
                } else {
                    AudioService.this.mScreenSharingHelper.setSupportDisplayVolumeControl(false);
                }
                AudioService.this.mScreenSharingHelper.setScreenSharingStateResumed(AudioService.this.mScreenSharingHelper.isSupportDisplayVolumeControl());
                Log.d("AS.AudioService", "onReceive SEM_WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED isSupportDisplayVolumeControl:" + AudioService.this.mScreenSharingHelper.isSupportDisplayVolumeControl());
                return;
            }
            if ("com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE".equals(action)) {
                int intExtra4 = intent.getIntExtra("CONNECTION_MODE", 0);
                StringBuilder sb = new StringBuilder();
                sb.append("l_smart_view_fixed_volume_enable=");
                sb.append(intExtra4 == 1 ? "true" : "false");
                SemAudioSystem.setPolicyParameters(sb.toString());
                return;
            }
            if ("com.samsung.android.scpm.policy.UPDATE.Audio".equals(action)) {
                AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("Receive SCPM update intent : Audio"));
                if (AudioService.this.mSoundAppPolicyManager != null) {
                    AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioService$SamsungBroadcastReceiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AudioService.SamsungBroadcastReceiver.this.lambda$handleIntent$0();
                        }
                    });
                    return;
                }
                return;
            }
            if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
                AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("Receive SCPM clear intent"));
                if (AudioService.this.mSoundAppPolicyManager != null) {
                    AudioService.this.mSettingHelper.setIntValue("APP_LIST_VERSION", -1);
                    if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                        AudioService.this.mSettingHelper.setIntValue("LIVE_TRANSLATE_ALLOW_LIST_VERSION", -1);
                    }
                    AudioService.this.mSoundAppPolicyManager.init(context, false);
                    return;
                }
                return;
            }
            if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE && "com.samsung.android.scpm.policy.UPDATE.voip-live-translate-allow-list-a7f6".equals(action)) {
                AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("Receive SCPM update intent : voip-live-translate-allow-list"));
                if (AudioService.this.mSoundAppPolicyManager != null) {
                    AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioService$SamsungBroadcastReceiver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AudioService.SamsungBroadcastReceiver.this.lambda$handleIntent$1();
                        }
                    });
                    return;
                }
                return;
            }
            if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
                AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioService$SamsungBroadcastReceiver$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioService.SamsungBroadcastReceiver.this.lambda$handleIntent$2(intent);
                    }
                });
                return;
            }
            if ("com.sec.media.action.mute_interval".equals(action)) {
                AudioService.this.checkMuteInterval();
                return;
            }
            if ("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED".equals(action)) {
                AudioService.this.mDeviceBroker.setDualA2dpMode(intent.getBooleanExtra("enable", false), null);
                return;
            }
            if ("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                AudioService.this.mDeviceBroker.postActiveBluetoothDeviceChanged(bluetoothDevice);
                AudioService.this.setSoundCraftEnable(bluetoothDevice);
                return;
            }
            if ("com.sec.android.intent.action.SPLIT_SOUND".equals(action)) {
                ScreenSharingHelper.setSplitSoundEnabled(intent.getBooleanExtra("enabled", false));
                return;
            }
            if ("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED".equals(action)) {
                int intExtra5 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
                int intExtra6 = intent.getIntExtra("com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0);
                int deviceForStream2 = AudioService.this.getDeviceForStream(3);
                Log.i("AS.AudioService", "BT cast device state:" + intExtra5 + " role : " + intExtra6);
                if (intExtra5 == 2 || intExtra5 == 0) {
                    AudioService.this.mIsBluetoothCastState = intExtra5 == 2;
                    AudioService.this.mDeviceBroker.checkSendBecomingNoisyIntent(32768, intExtra5, deviceForStream2);
                    if (intExtra6 == 2) {
                        AudioService.this.mDeviceBroker.updateDeviceQuickConnection(intExtra5 == 2, 32768, "0", "remote_submix", 0);
                        return;
                    }
                    return;
                }
                return;
            }
            if ("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED".equals(action)) {
                AudioService audioService4 = AudioService.this;
                if (audioService4.mGoodCatchManager == null) {
                    audioService4.mGoodCatchManager = new GoodCatchManager(AudioService.this.mContext, "AudioService");
                    return;
                }
                return;
            }
            if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR && "com.samsung.bluetooth.device.action.AUDIO_TYPE_CHANGED".equals(action)) {
                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BluetoothA2dp a2dp = AudioService.this.mDeviceBroker.getA2dp();
                if (bluetoothDevice2 == null || a2dp == null) {
                    return;
                }
                BluetoothDevice activeDevice = a2dp.getActiveDevice();
                if (intent.getIntExtra("com.samsung.bluetooth.device.extra.AUDIO_TYPE", 0) == 2) {
                    if (activeDevice == null || !bluetoothDevice2.getAddress().equals(activeDevice.getAddress())) {
                        return;
                    }
                    BtUtils.setBtVolumeMonitor(true);
                    return;
                }
                if (activeDevice == null || !bluetoothDevice2.getAddress().equals(activeDevice.getAddress())) {
                    return;
                }
                BtUtils.setBtVolumeMonitor(false);
                return;
            }
            if ("androidx.car.app.connection.action.CAR_CONNECTION_UPDATED".equals(action)) {
                boolean queryForState = AudioService.this.queryForState();
                Log.d("AS.AudioService", "received ACTION_CAR_CONNECTION_UPDATED : state = " + queryForState);
                AudioService.this.mConnectedAndroidAuto = queryForState;
                if (!queryForState || AudioService.this.mMediaFocusControl == null) {
                    return;
                }
                AudioService.this.mMediaFocusControl.clearMultiAudiofocusfromAndroidAuto();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleIntent$0() {
            AudioService.this.mSoundAppPolicyManager.checkAndUpdateAppList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleIntent$1() {
            AudioService.this.mSoundAppPolicyManager.checkAndUpdateLiveTranslateList(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleIntent$2(Intent intent) {
            AudioService.this.mDeviceBroker.receiveBtEvent(intent);
        }
    }

    public class SamsungSettingsObserver extends ContentObserver {
        public SamsungSettingsObserver() {
            super(new Handler());
            AudioService.this.mContentResolver.registerContentObserver(Settings.System.getUriFor("multi_audio_focus_enabled"), false, this, -1);
            AudioService.this.mContentResolver.registerContentObserver(Settings.System.getUriFor("ear_shock_condition"), false, this, -1);
            if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                AudioService.this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("translate_during_calls"), false, this);
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            int platformType;
            super.onChange(z);
            AudioService.this.setMultiAudioFocusEnabled(Settings.System.getInt(AudioService.this.mContentResolver, "multi_audio_focus_enabled", 0) != 0);
            if (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION && AudioService.this.mPlatformType != (platformType = PlatformTypeUtils.getPlatformType(AudioService.this.mContext))) {
                AudioService.this.mPlatformType = platformType;
                if (AudioService.this.mPlatformType == 1) {
                    Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION = false;
                }
                AudioService.this.updateStreamVolumeAlias(false, "AS.AudioService.CMC");
            }
            boolean z2 = Settings.System.getInt(AudioService.this.mContentResolver, "ear_shock_condition", 0) != 0;
            if (z2 != AudioService.this.mIsVolumeEffectOn) {
                AudioService.this.setVolumeEffectOn(z2);
            }
            if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                boolean z3 = Settings.Global.getInt(AudioService.this.mContentResolver, "translate_during_calls", 1) != 0;
                if (z3 != AudioService.this.mLiveTranslateDuringCall) {
                    if (AudioService.this.mMode.get() == 3) {
                        AudioService.this.mMicModeManager.updateAudioMode(AudioService.this.mExternalVoipModeOwner != null ? AudioService.this.mExternalVoipModeOwner.getPackage() : AudioService.this.getPackageNameModeOwner(), 3);
                    }
                    AudioService.this.mLiveTranslateDuringCall = z3;
                }
            }
        }
    }

    public final void observeDevicesForMediaStream() {
        synchronized (VolumeStreamState.class) {
            this.mStreamStates[3].observeDevicesForStream_syncVSS(false);
        }
    }

    public void addSamsungExtraDump(PrintWriter printWriter) {
        sRingerModeLogger.dump(printWriter);
        sMicrophoneLogger.dump(printWriter);
        sMasterMuteLogger.dump(printWriter);
        sUsingAudioLogger.dump(printWriter);
        sAppVolumeLogger.dump(printWriter);
        sRingtoneChangeLogger.dump(printWriter);
        printWriter.println("\nAudio Setting:");
        this.mExt.addAudioServiceExtDump(printWriter);
        printWriter.print("  mIsSupportDisplayVolumeControl=");
        printWriter.println(this.mScreenSharingHelper.isSupportDisplayVolumeControl());
        printWriter.print("  mIsDLNAEnabled=");
        printWriter.println(this.mScreenSharingHelper.isDLNAEnabled());
        printWriter.print("  isTalkBackEnabled=");
        printWriter.println(this.mIsTalkBackEnabled);
        printWriter.print("  mAdjustMediaVolumeOnly=");
        printWriter.println(this.mAdjustMediaVolumeOnly);
        printWriter.print("  mMediaVolumeStepIndex=");
        printWriter.println(this.mMediaVolumeStepIndex);
        printWriter.print("  mMediaVolumeSteps=");
        printWriter.println(Arrays.toString(this.mVolumeSteps));
        printWriter.print("  mHeadsetOnlyStream=");
        printWriter.println(this.mHeadsetOnlyStream);
        printWriter.print("  mLRSwitching=");
        printWriter.println(this.mLRSwitching);
        printWriter.print("  SEC_AUDIO_SUPPORT_DUAL_SPEAKER=");
        printWriter.println(false);
        printWriter.print("  mVolumeLimitOn=");
        printWriter.println(this.mVolumeLimitOn);
        printWriter.print("  mVolumeLimitValue=");
        printWriter.println(this.mVolumeLimitValue);
        printWriter.print("  OneUIVersion=");
        printWriter.println(PlatformTypeUtils.getOneUIVersion(this.mContext));
        printWriter.print("  SEC_AUDIO_SUPPORT_ACH_RINGTONE=");
        printWriter.println(Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE);
        this.mMultiSoundManager.dump(printWriter);
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            this.mMicModeManager.dump(printWriter);
        }
        this.mDeviceBroker.dumpA2dpDevicesVolume(printWriter);
        this.mDeviceAliasManager.dump(printWriter);
        sScpmLogger.dump(printWriter);
        this.mSettingHelper.dumpAppList(printWriter);
        printWriter.println();
        sFactoryTestLogger.dump(printWriter);
        printWriter.println();
        printWriter.print("  Last Loopback on = ");
        printWriter.println(this.mLastLoopbackOn);
        printWriter.print("  Current Loopback state = ");
        printWriter.println(this.mLoopbackState);
        sScoPreventionLogger.dump(printWriter);
        printWriter.print("  mIsLeBroadCasting=");
        printWriter.println(this.mIsLeBroadCasting);
        printWriter.println("");
        BtUtils.getAuracastAppLogger().dump(printWriter);
        printWriter.println("");
        printWriter.println("BLE broadcast app uids = " + BtUtils.getAuracastUids());
        printWriter.println("");
    }

    public void recordRingtoneChanger(String str) {
        if (str == null) {
            return;
        }
        sRingtoneChangeLogger.enqueue(new EventLogger.StringEvent(str.substring(0, Math.min(str.length(), 600))));
    }

    public void registerPlaybackCallbackWithPackage(IPlaybackConfigDispatcher iPlaybackConfigDispatcher, String str) {
        registerPlaybackCallback(iPlaybackConfigDispatcher);
        this.mPlaybackMonitor.setCallbackPackageName(iPlaybackConfigDispatcher, str);
        sUsingAudioLogger.enqueue(new EventLogger.StringEvent("register : " + iPlaybackConfigDispatcher + ", " + str));
    }

    public final void postAvrcpSupportsAbsoluteVolumeToAudioServer(String str, boolean z) {
        sendMsg(this.mAudioHandler, 2773, 2, z ? 1 : 0, 0, str, 0);
    }

    public final void onAvrcpSupportsAbsoluteVolumeToAudioServer(String str, boolean z) {
        this.mAudioSystem.setParameters(new AudioParameter.Builder().setParam("l_support_absolute_volume", z ? "true" : "false").setParam("address", str).build().toString());
    }

    public final void callVibrateMsg(String str, int i) {
        if (this.mIsVibrate) {
            return;
        }
        vibrateCall(str, i);
        this.mIsVibrate = true;
        sendMsg(this.mAudioHandler, 2761, 1, i, 0, str, 600);
    }

    public final void vibrateCall(String str, int i) {
        try {
            if (!hasVibrator() || this.mVibrator == null) {
                return;
            }
            VibrationEffect semCreateHaptic = VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(8), -1);
            StringBuilder sb = new StringBuilder();
            sb.append("Notification (");
            sb.append(str);
            sb.append(")");
            sb.append(i == 1 ? " (RingerMode)" : " (SafetyDialog)");
            this.mVibrator.vibrate(1000, "android", semCreateHaptic, sb.toString(), new VibrationAttributes.Builder().semAddTag("VIBRATE_CALL").setUsage(33).build());
        } catch (Exception e) {
            Log.i("AS.AudioService", "vibrateCall error", e);
        }
    }

    public final boolean hasVibrator() {
        return this.mHasVibrator;
    }

    public final BypassReason bypassAdjustStreamVolume(int i, int i2, int i3, String str, String str2, String str3, int i4, int i5) {
        if (!isInCommunication() && this.mScreenSharingHelper.tvVolumeShouldBeAdjusted(i, i3, i5, this.mMultiSoundManager.getRemoteSubmixApps(), this.mForegroundUid)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mDisplayManager.semSetWifiDisplayConfiguration("vkev", i2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                this.mVolumeController.postVolumeChanged(i, 4194304 | i3);
                return BypassReason.DISPLAY_VOLUME_CONTROL;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (this.mExt.getAllSoundMute() == 1 && (this.mRingerMode != 0 || i != 2 || i2 != 1)) {
            this.mVolumeController.postVolumeChanged(i, i3);
            return BypassReason.ALL_SOUND_MUTE;
        }
        if (!volumeAdjustmentAllowedByLeBroadcast(i5, i)) {
            this.mVolumeController.postVolumeChanged(i, i3);
            return BypassReason.LE_BROADCAST;
        }
        int i6 = mStreamVolumeAlias[i];
        if (i == 3 && isRaiseOrLowerOrSame(i2)) {
            int selectDevice = selectDevice(i5);
            if (i5 != selectDevice) {
                if (this.mVolumeController.isVisible() && this.mVolumeControllerStream != 10003) {
                    return BypassReason.NO_BYPASS;
                }
                if (i2 == 0) {
                    if ((i3 & IInstalld.FLAG_USE_QUOTA) != 0) {
                        return BypassReason.NO_BYPASS;
                    }
                    return BypassReason.CONSUME_ADJUST_SAME;
                }
                if (this.mMediaVolumeStepIndex == 10 && this.mVolumeSteps != null) {
                    setStreamVolumeWithAttribution(i, getStreamVolume(3, selectDevice) + i2, i3, null, str, str2, str3, i4, true, selectDevice);
                } else {
                    setStreamVolumeWithAttribution(i, getNextFineMediaVolume(selectDevice, i2), i3 | 1048576, null, str, str2, str3, i4, true, selectDevice);
                }
                return BypassReason.MULTISOUND;
            }
            if (this.mMediaVolumeStepIndex != 10 || this.mVolumeSteps != null) {
                if (i2 != 0) {
                    setStreamVolumeWithAttribution(i, getNextFineMediaVolume(i5, i2), i3 | 1048576, null, str, str2, str3, i4, true, 0);
                    return BypassReason.MEDIA_VOLUME_STEP_ON;
                }
                if ((i3 & IInstalld.FLAG_USE_QUOTA) != 0) {
                    return BypassReason.NO_BYPASS;
                }
                return BypassReason.CONSUME_ADJUST_SAME;
            }
        }
        if (this.mVolumeController.isSafeVolumeDialogShowing() && ((i2 == 1 || i2 == 0) && !isMedia(i6))) {
            Log.d("AS.AudioService", "bypassAdjustStreamVolume: stream(" + i + ") when ear safety pop-up is shown");
            return BypassReason.SKIP_WARNING_POPUP_VISIBLE;
        }
        if (isNotificationOrRinger(i) && !this.mVolumeController.isVisible() && (i3 & 1) != 0) {
            CustomDeviceManagerProxy customDeviceManagerProxy = CustomDeviceManagerProxy.getInstance();
            if (customDeviceManagerProxy != null && !customDeviceManagerProxy.getVolumePanelEnabledState()) {
                Log.d("AS.AudioService", "Allow volume increase action for stream: " + i + " when volume panel hidden by Knox Custom");
            } else {
                int index = this.mStreamStates[i].getIndex(i5);
                sendVolumeUpdate(i, index, index, i3, i5);
                return BypassReason.SKIP_VOLUME_PANEL_NOT_VISIBLE;
            }
        }
        if (this.mVolumeLimitOn && mStreamVolumeAlias[i] == 3) {
            int index2 = this.mStreamStates[i].getIndex(i5);
            if (i2 == 1 && getVolumeLimitValue() * 10 < index2 + 10 && this.mSoundDoseHelper.safeDevicesContains(i5)) {
                if (index2 != 150 && getVolumeLimitValue() != 15) {
                    this.mVolumeController.displayVolumeLimiterToast();
                }
                Log.i("AS.AudioService", "Volume index is already reached at volume limit value");
                sendVolumeUpdate(i, getVolumeLimitValue() * 10, 10 * getVolumeLimitValue(), i3, i5);
                return BypassReason.VOLUME_LIMITER;
            }
        }
        return BypassReason.NO_BYPASS;
    }

    public final void handleSetRingerMode(final int i, final String str, final boolean z) {
        Log.i("AS.AudioService", "RingerMode=" + i + ", caller=" + str);
        if (checkPlayRingerModeEffect(str, i, z)) {
            if (i == 1) {
                sendMsg(this.mAudioHandler, 2760, 1, 1, 0, str, 0);
            } else if (i == 2) {
                sendMsg(this.mAudioHandler, 2759, 1, 0, 0, null, 0);
            }
        }
        SystemProperties.set("persist.audio.ringermode", Integer.toString(i));
        if (i == 0 || i == 1) {
            SystemProperties.set("persist.sys.silent", "1");
        } else {
            SystemProperties.set("persist.sys.silent", isStreamMutedByRingerOrZenMode(1) ? "1" : "0");
        }
        sRingerModeLogger.enqueue(new EventLogger.Event(i, str, z) { // from class: com.samsung.android.server.audio.AudioEvents$RingerModeEvent
            public String mCaller;
            public boolean mExternal;
            public int mRingerMode;

            {
                this.mRingerMode = i;
                this.mCaller = str;
                this.mExternal = z;
            }

            @Override // com.android.server.utils.EventLogger.Event
            public String eventToString() {
                return "setRingerMode(mode:" + this.mRingerMode + " external:" + this.mExternal + ") from " + this.mCaller;
            }
        });
        GoodCatchManager goodCatchManager = this.mGoodCatchManager;
        if (goodCatchManager == null || !goodCatchManager.isRingerModeCatchEnabled()) {
            return;
        }
        if (str.startsWith("AS.AudioService.onSetStreamVolume(")) {
            try {
                str = str.substring(str.indexOf(40) + 1, str.indexOf(41));
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        this.mGoodCatchManager.updateRingerMode(str, i, this.mContext.getPackageName());
    }

    public final boolean checkPlayRingerModeEffect(String str, int i, boolean z) {
        if (str.contains("com.samsung.accessibility") || str.contains("com.samsung.android.app.routines") || str.contains("com.android.systemui") || str.contains("com.sec.android.emergencymode.service")) {
            return true;
        }
        if (i == 2 && (str.contains("checkForRingerModeChange") || str.contains("onSetStreamVolume"))) {
            return false;
        }
        return !z;
    }

    public final void playSilentModeSound() {
        if (getMode() == 3) {
            Log.i("AS.AudioService", "playSilentModeSound: skipping playSilentModeSound");
        } else if (getMode() == 1 && isBluetoothScoOn()) {
            Log.i("AS.AudioService", "playSilentModeSound: skipping while inband ringtone is playing");
        } else {
            playSoundEffectVolume(AudioFxHelper.getPlaySoundTypeForSEP(101), -1.0f);
        }
    }

    public final boolean skipAdjustStreamVolume(int i, int i2, int i3, String str, String str2, String str3, int i4, int i5) {
        BypassReason bypassAdjustStreamVolume = bypassAdjustStreamVolume(i, i2, i3, str, str2, str3, i4, i5);
        if (bypassAdjustStreamVolume == BypassReason.NO_BYPASS) {
            return false;
        }
        Log.d("AS.AudioService", "bypass adjustStreamVolume:" + bypassAdjustStreamVolume.name());
        if (bypassAdjustStreamVolume != BypassReason.CONSUME_ADJUST_SAME || (i3 & 1) == 0) {
            return true;
        }
        this.mVolumeController.postVolumeChanged(i, updateFlagsForSamsungVolume(i, i3, 0, i5));
        return true;
    }

    public final int updateVolumePanelFlags(int i, int i2, int i3) {
        int i4;
        if ((i3 & 4) != 0) {
            if (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION) {
                i3 &= -5;
                if ((i3 & 4) != 0 && "true".equalsIgnoreCase(AudioSystem.getParameters("l_record_active_enable"))) {
                    i3 &= -5;
                }
            } else {
                i3 &= -5;
                if ((i3 & 4) != 0) {
                    i3 &= -5;
                }
            }
        }
        if ((i3 & 512) != 0 && i2 != 0) {
            i3 |= 1;
        }
        if (i2 == -1) {
            i4 = 65536;
        } else {
            if (i2 != 1) {
                return i3;
            }
            i4 = IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
        }
        return i3 | i4;
    }

    public final int updateFlagsForSamsungVolume(int i, int i2, int i3, int i4) {
        if (FactoryUtils.isFactoryMode() && 2 == i && i2 == 0 && i3 > 0) {
            Log.e("AS.AudioService", "sendVolumeUpdate: enforce to FLAG_PLAY_SOUND volume index = " + i3);
            i2 |= 4;
        }
        if (this.mDeviceBroker.isDualA2dpMode()) {
            i2 |= 524288;
            Log.i("AS.AudioService", "Add isDualA2dpMode");
        }
        if (Rune.SEC_AUDIO_FIXED_SCO_VOLUME && isBluetoothScoOn() && !this.mDeviceBroker.isRemoteVolumeControlSupported()) {
            i2 |= 262144;
            Log.i("AS.AudioService", "this BT doesn't support Remote volume control. hence, SCO volume fixed");
        }
        if (Rune.SEC_AUDIO_REMOTE_MIC && this.mRemoteMic) {
            i2 |= 67108864;
            Log.i("AS.AudioService", "Add FLAG_REMOTE_MIC");
        }
        if (i != 3 || !AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i4)) || (i2 & 64) == 0 || !isInCommunication()) {
            return i2;
        }
        int i5 = i2 & (-2);
        Log.i("AS.AudioService", "The show UI flag is off in the incall state and A2DP BT Connection");
        return i5;
    }

    public void muteRingtoneDuringVibration() {
        if (getMode() == 1 && this.mRingerMode == 1) {
            Log.i("AS.AudioService", "SPK ringtone volume set to 0 !!!");
            AudioSystem.setStreamVolumeIndexAS(2, 0, 2);
        }
    }

    public final void initializeVolumeSteps() {
        int i = SystemProperties.getInt("ro.config.vc_call_vol_steps", -1);
        int i2 = MAX_STREAM_VOLUME[0];
        if (i != -1) {
            Log.i("AS.AudioService", "use default ro.config.vc_call_vol_steps " + i);
        } else {
            i = i2;
        }
        MAX_STREAM_VOLUME[0] = i;
    }

    public final void setSoundAssistant(AudioParameter audioParameter) {
        checkModifyPhoneStateOrRoutingPermission();
        String str = audioParameter.get("adjust_media_volume_only");
        if (str != null) {
            int intValueFromString = this.mExt.getIntValueFromString(str, 0);
            this.mAdjustMediaVolumeOnly = intValueFromString == 1;
            this.mExt.setSystemSettingForSoundAssistant("adjust_media_volume_only", intValueFromString);
            return;
        }
        String str2 = audioParameter.get("mute_media_by_vibrate_or_silent_mode");
        if (str2 != null) {
            boolean z = this.mExt.getIntValueFromString(str2, 0) == 1;
            this.mMuteMediaByVibrateOrSilentMode = z;
            this.mSettingHelper.setBooleanValue("mute_media_by_vibrate_or_silent_mode", z);
            muteMediaStreamOfSpeaker(this.mMuteMediaByVibrateOrSilentMode && this.mRingerMode != 2);
            return;
        }
        String str3 = audioParameter.get("remove_app_volume");
        if (str3 != null) {
            int intValueFromString2 = this.mExt.getIntValueFromString(str3, -1);
            if (intValueFromString2 == -1 || "".equals(getPackageName(intValueFromString2)[0])) {
                Log.w("AS.AudioService", "Invalid uid from SoundAssistant");
                return;
            } else {
                this.mAppVolumeFromSoundAssistant.delete(intValueFromString2);
                this.mMultiSoundManager.setAppVolume(intValueFromString2, 100);
                return;
            }
        }
        String str4 = audioParameter.get("sound_balance");
        if (str4 != null) {
            int intValueFromString3 = this.mExt.getIntValueFromString(str4, 50);
            if (intValueFromString3 < 0 || intValueFromString3 > 100) {
                throw new IllegalArgumentException("Invalid balance");
            }
            this.mExt.setSystemSettingForSoundAssistant("sound_balance", intValueFromString3);
            return;
        }
        String str5 = audioParameter.get("mono_sound");
        if (str5 != null) {
            int intValueFromString4 = this.mExt.getIntValueFromString(str5, 0);
            if (intValueFromString4 != 0 && intValueFromString4 != 1) {
                throw new IllegalArgumentException("Invalid balance");
            }
            this.mExt.setSystemSettingForSoundAssistant("mono_audio_db", intValueFromString4);
            return;
        }
        String str6 = audioParameter.get("ignore_audio_focus");
        if (str6 != null) {
            int intValueFromString5 = this.mExt.getIntValueFromString(audioParameter.get("uid_for_soundassistant"), -1);
            if (intValueFromString5 == -1) {
                Log.e("AS.AudioService", "invalid arguments");
                return;
            }
            String str7 = getPackageName(intValueFromString5)[0];
            if (TextUtils.isEmpty(str7)) {
                Log.e("AS.AudioService", "Invalid uid for ignoring audiofocus. uid : " + intValueFromString5);
                return;
            }
            int intValueFromString6 = this.mExt.getIntValueFromString(str6, 0);
            StringBuilder sb = new StringBuilder();
            sb.append("Set ignore audiofocus : ");
            sb.append(str7);
            sb.append(", uid : ");
            sb.append(intValueFromString5);
            sb.append(", enabled:");
            sb.append(intValueFromString6 == 1);
            Log.i("AS.AudioService", sb.toString());
            this.mSettingHelper.setIntValue("ignore_audio_focus", intValueFromString6 == 1 ? intValueFromString5 : -1);
            this.mMediaFocusControl.setIgnoreAudioFocus(intValueFromString5, intValueFromString6 == 1);
            return;
        }
        String str8 = audioParameter.get("media_volume_step_index");
        if (str8 != null) {
            int intValueFromString7 = this.mExt.getIntValueFromString(str8, 10);
            this.mMediaVolumeStepIndex = intValueFromString7;
            this.mSettingHelper.setIntValue("media_volume_step_index", intValueFromString7);
            setMediaVolumeSteps(null);
            return;
        }
        String str9 = audioParameter.get("ring_through_headset");
        if (str9 != null) {
            int intValueFromString8 = this.mExt.getIntValueFromString(str9, 0);
            this.mHeadsetOnlyStream = intValueFromString8;
            this.mSettingHelper.setIntValue("ring_through_headset", intValueFromString8);
            setHeadsetOnlyStream(this.mHeadsetOnlyStream);
            return;
        }
        String str10 = audioParameter.get("sound_lr_switch");
        if (str10 != null) {
            int intValueFromString9 = this.mExt.getIntValueFromString(str10, 0);
            this.mLRSwitching = intValueFromString9;
            this.mSettingHelper.setIntValue("sound_lr_switch", intValueFromString9);
            if (intValueFromString9 == 1) {
                this.mAudioSystem.setParameters("l_sound_assistant_lr_switch_enable=true");
                return;
            } else {
                this.mAudioSystem.setParameters("l_sound_assistant_lr_switch_enable=false");
                return;
            }
        }
        String str11 = audioParameter.get("volumestar_enable");
        if (str11 != null) {
            this.mVolumeController.setA11yMode(this.mExt.getIntValueFromString(str11, 0) != 1 ? 101 : 100);
        }
    }

    public final void createVariableMediaVolumeMap(int[] iArr) {
        int i = MAX_STREAM_VOLUME[3] * 10;
        this.mVolumeMap = new VolumeMap[i + 1];
        int i2 = 0;
        int i3 = 0;
        while (i2 < iArr.length) {
            int i4 = i2 == 0 ? 0 : iArr[i2 - 1];
            int i5 = i2 == iArr.length + (-1) ? i : iArr[i2 + 1];
            int i6 = iArr[i2];
            while (i3 < i6) {
                this.mVolumeMap[i3] = new VolumeMap((short) i4, (short) i6);
                i3++;
            }
            this.mVolumeMap[i6] = new VolumeMap((short) i4, (short) i5);
            i3 = i6 + 1;
            i2++;
        }
        int i7 = i3 - 1;
        while (i3 <= i) {
            this.mVolumeMap[i3] = new VolumeMap((short) i7, (short) i);
            i3++;
        }
    }

    public final void reloadVariableMediaVolumeSteps() {
        String string = Settings.System.getString(this.mContentResolver, "sec_volume_steps");
        if (string == null || string.length() == 0) {
            return;
        }
        try {
            String[] split = string.split(",");
            this.mVolumeSteps = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                this.mVolumeSteps[i] = Integer.parseInt(split[i]);
            }
            createVariableMediaVolumeMap(this.mVolumeSteps);
        } catch (Exception e) {
            Log.d("AS.AudioService", "reloadVariableMediaVolumeSteps", e);
        }
    }

    public final void resetVolumeStar() {
        Intent intent = new Intent("android.intent.action.VOLUMESTAR_SETTING_CHANGED");
        intent.putExtra("changed_setting", "volumestar_enabled");
        intent.setPackage("com.android.systemui");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, "com.samsung.systemui.permission.SPLUGIN");
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void clearSoundAssistantSettings() {
        this.mMediaVolumeStepIndex = 10;
        this.mSettingHelper.removeValue("media_volume_step_index");
        this.mHeadsetOnlyStream = 32;
        SemAudioSystem.setPolicyParameters("l_sound_assistant_ring_via_headset_enable=" + this.mHeadsetOnlyStream);
        this.mSettingHelper.removeValue("ring_through_headset");
        if (this.mLRSwitching == 1) {
            this.mAudioSystem.setParameters("l_sound_assistant_lr_switch_enable=false");
        }
        this.mLRSwitching = 0;
        this.mSettingHelper.removeValue("sound_lr_switch");
        setMediaVolumeSteps(null);
        this.mSettingHelper.removeValue("ignore_audio_focus");
        this.mMediaFocusControl.setIgnoreAudioFocus(-1, false);
        for (int i = 0; i < this.mAppVolumeFromSoundAssistant.size(); i++) {
            this.mMultiSoundManager.setAppVolume(this.mAppVolumeFromSoundAssistant.keyAt(i), 100);
        }
        this.mAppVolumeFromSoundAssistant.clear();
        if (this.mSettingHelper.removeValue("mono_audio_db") > 0) {
            Settings.System.putIntForUser(this.mContentResolver, "master_mono", 0, -2);
        }
        if (this.mSettingHelper.removeValue("sound_balance") > 0) {
            Settings.System.putFloatForUser(this.mContentResolver, "master_balance", this.mExt.getMainBalance(), -2);
        }
        muteMediaStreamOfSpeaker(false);
        this.mSettingHelper.removeValue("speaker_media_index");
        this.mSettingHelper.removeValue("mute_media_by_vibrate_or_silent_mode");
        this.mSavedSpeakerMediaIndex = -1;
        this.mMuteMediaByVibrateOrSilentMode = false;
        Settings.System.putInt(this.mContentResolver, "multi_audio_focus_enabled", 0);
    }

    public String getSoundAssistant(String str) {
        if ("adjust_media_volume_only".equals(str)) {
            return String.valueOf(this.mAdjustMediaVolumeOnly ? 1 : 0);
        }
        if ("version".equals(str)) {
            return "12";
        }
        if ("get_app_volume_list".equals(str)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.mAppVolumeFromSoundAssistant.size(); i++) {
                int keyAt = this.mAppVolumeFromSoundAssistant.keyAt(i);
                int valueAt = this.mAppVolumeFromSoundAssistant.valueAt(i);
                sb.append(keyAt);
                sb.append("=");
                sb.append(valueAt);
                if (i != this.mAppVolumeFromSoundAssistant.size() - 1) {
                    sb.append(KnoxVpnFirewallHelper.DELIMITER);
                }
            }
            return sb.toString();
        }
        if ("ignore_audio_focus".equals(str)) {
            return String.valueOf(this.mMediaFocusControl.getIgnoredUid());
        }
        if ("media_volume_step_index".equals(str)) {
            return String.valueOf(this.mMediaVolumeStepIndex);
        }
        if ("ring_through_headset".equals(str)) {
            return String.valueOf(this.mHeadsetOnlyStream);
        }
        if ("sound_lr_switch".equals(str)) {
            return String.valueOf(this.mLRSwitching);
        }
        if ("using_audio_uids".equals(str)) {
            return getApplicationUidListUsingAudio();
        }
        if ("media_button_package".equals(str)) {
            return getMediaKeyEventSessionPackageName();
        }
        if ("mute_media_by_vibrate_or_silent_mode".equals(str)) {
            return String.valueOf(this.mMuteMediaByVibrateOrSilentMode ? 1 : 0);
        }
        if ("get_mode_owner_uids".equals(str)) {
            return getApplicationUidListSetCallMode();
        }
        if (!"brand_sound_version".equals(str)) {
            return null;
        }
        TextUtils.isEmpty("");
        return "";
    }

    public String getMediaKeyEventSessionPackageName() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MEDIA_CONTENT_CONTROL") != 0) {
            Log.e("AS.AudioService", "media content control permission error");
            return "";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            MediaSessionManager mediaSessionManager = (MediaSessionManager) this.mContext.getSystemService("media_session");
            return mediaSessionManager.getMediaKeyEventSession() != null ? mediaSessionManager.getMediaKeyEventSessionPackageName() : "";
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public String getApplicationUidListUsingAudio() {
        int clientUid;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (AudioPlaybackConfiguration audioPlaybackConfiguration : this.mPlaybackMonitor.getActivePlaybackConfigurations(true)) {
            if (AudioAttributes.toLegacyStreamType(audioPlaybackConfiguration.getAudioAttributes()) == 3 && audioPlaybackConfiguration.getPlayerState() != 1 && (clientUid = audioPlaybackConfiguration.getClientUid()) >= 10000) {
                sb.append(clientUid + KnoxVpnFirewallHelper.DELIMITER);
            }
        }
        return sb.toString();
    }

    public String getApplicationUidListSetCallMode() {
        int appId;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return "";
        }
        StringBuilder sb = new StringBuilder();
        synchronized (this.mDeviceBroker.mSetModeLock) {
            Iterator it = this.mSetModeDeathHandlers.iterator();
            while (it.hasNext()) {
                SetModeDeathHandler setModeDeathHandler = (SetModeDeathHandler) it.next();
                if (setModeDeathHandler.mMode == 3 && (appId = UserHandle.getAppId(setModeDeathHandler.getUid())) >= 10000) {
                    sb.append(appId + KnoxVpnFirewallHelper.DELIMITER);
                }
            }
        }
        return sb.toString();
    }

    public final void sendBroadcastToSoundEventReceiver(int i, int i2, String str) {
        synchronized (this.mEventReceivers) {
            ArrayList arrayList = this.mEventReceivers;
            if (!arrayList.isEmpty() && i != 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    SoundEventReceiver soundEventReceiver = (SoundEventReceiver) it.next();
                    if (soundEventReceiver.hasEventType(i)) {
                        Log.i("AS.AudioService", "Send broadcast to " + soundEventReceiver.mEventReceiver);
                        Intent intent = new Intent("com.samsung.android.intent.action.SOUND_EVENT");
                        intent.putExtra("type", i);
                        intent.putExtra("value", i2);
                        intent.putExtra("package", str);
                        intent.setComponent(soundEventReceiver.mEventReceiver);
                        sendMsg(this.mAudioHandler, 2768, 2, 0, 0, intent, 0);
                    }
                }
            }
        }
    }

    public void setSoundSettingEventBroadcastIntent(int i, PendingIntent pendingIntent) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ComponentName component = pendingIntent.getIntent().getComponent();
            if (component == null) {
                Log.e("AS.AudioService", "Invalid argument");
            } else {
                setSoundSettingEventBroadcastIntent(i, component);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSoundSettingEventBroadcastIntent(int i, ComponentName componentName) {
        synchronized (this.mEventReceivers) {
            ArrayList arrayList = this.mEventReceivers;
            SoundEventReceiver soundEventReceiver = new SoundEventReceiver(i, componentName);
            int indexOf = arrayList.indexOf(soundEventReceiver);
            if (i == 0) {
                if (indexOf < 0) {
                    return;
                }
                if (componentName.equals(this.mMediaSessionServiceInternal.getVolumeLongPressReceiver())) {
                    this.mMediaSessionServiceInternal.setVolumeLongPressReceiver(null);
                }
                if (componentName.equals(this.mMediaSessionServiceInternal.getMediaKeyEventReceiver())) {
                    this.mMediaSessionServiceInternal.setMediaKeyEventReceiver(null);
                }
                arrayList.remove(indexOf);
                Log.d("AS.AudioService", "Receiver removed, size : " + arrayList.size());
                return;
            }
            if (indexOf < 0) {
                arrayList.add(soundEventReceiver);
            } else {
                ((SoundEventReceiver) arrayList.get(indexOf)).mEventType = i;
            }
            if ((i & 32) != 0) {
                Log.i("AS.AudioService", "Set long press receiver");
                this.mMediaSessionServiceInternal.setVolumeLongPressReceiver(componentName);
            }
            if ((i & 64) != 0) {
                Log.i("AS.AudioService", "Set media button receiver");
                this.mMediaSessionServiceInternal.setMediaKeyEventReceiver(componentName);
            }
            Log.d("AS.AudioService", "Success set receiver as type : " + i + ", size : " + arrayList.size());
        }
    }

    public final void unSetSoundSettingEventBroadcastIntent(String str) {
        SoundEventReceiver soundEventReceiver;
        synchronized (this.mEventReceivers) {
            Iterator it = this.mEventReceivers.iterator();
            while (true) {
                if (!it.hasNext()) {
                    soundEventReceiver = null;
                    break;
                } else {
                    soundEventReceiver = (SoundEventReceiver) it.next();
                    if (soundEventReceiver.samePackageName(str)) {
                        break;
                    }
                }
            }
            ComponentName volumeLongPressReceiver = this.mMediaSessionServiceInternal.getVolumeLongPressReceiver();
            ComponentName mediaKeyEventReceiver = this.mMediaSessionServiceInternal.getMediaKeyEventReceiver();
            if (soundEventReceiver != null) {
                if (volumeLongPressReceiver != null && volumeLongPressReceiver.getPackageName().equals(str)) {
                    this.mMediaSessionServiceInternal.setVolumeLongPressReceiver(null);
                }
                if (mediaKeyEventReceiver != null && mediaKeyEventReceiver.getPackageName().equals(str)) {
                    this.mMediaSessionServiceInternal.setMediaKeyEventReceiver(null);
                }
                this.mEventReceivers.remove(soundEventReceiver);
            } else if ("com.samsung.android.soundassistant".equals(str)) {
                if (volumeLongPressReceiver != null) {
                    this.mMediaSessionServiceInternal.setVolumeLongPressReceiver(null);
                }
                if (mediaKeyEventReceiver != null) {
                    this.mMediaSessionServiceInternal.setMediaKeyEventReceiver(null);
                }
                this.mEventReceivers.clear();
            }
        }
    }

    public boolean setMediaVolumeSteps(int[] iArr) {
        long clearCallingIdentity;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return false;
        }
        if (iArr == null) {
            this.mVolumeSteps = null;
            this.mVolumeMap = null;
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Settings.System.putString(this.mContentResolver, "sec_volume_steps", null);
                return true;
            } finally {
            }
        }
        if (iArr.length > getStreamMaxVolume(3) * 10 || iArr.length < 1) {
            Log.e("AS.AudioService", "Invalid parameter");
            return false;
        }
        int length = iArr.length;
        int i = -1;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            if (i3 <= i) {
                Log.e("AS.AudioService", "Steps have to be ordered as increasing");
                return false;
            }
            i2++;
            i = i3;
        }
        for (int i4 : iArr) {
            if (i4 < 0 || i4 > getStreamMaxVolume(3) * 10) {
                Log.e("AS.AudioService", "Invalid index");
                return false;
            }
        }
        this.mVolumeSteps = iArr;
        StringBuilder sb = new StringBuilder();
        for (int i5 : iArr) {
            sb.append(i5);
            sb.append(",");
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putString(this.mContentResolver, "sec_volume_steps", sb.toString());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            createVariableMediaVolumeMap(iArr);
            return true;
        } finally {
        }
    }

    public int[] getMediaVolumeSteps() {
        return this.mVolumeSteps;
    }

    public final void onInitSoundAssistant() {
        if (AudioUtils.getUidForPackage(this.mContext, "com.samsung.android.soundassistant") < 10000) {
            return;
        }
        Intent intent = new Intent("com.sec.android.soundassistant.SOUNDASSIST_INTENT_SERVICE");
        intent.setClassName("com.samsung.android.soundassistant", "com.sec.android.soundassistant.services.SoundAssistIntentService");
        intent.putExtra("type", 1003);
        try {
            this.mContext.startForegroundServiceAsUser(intent, UserHandle.CURRENT);
        } catch (Exception unused) {
        }
    }

    public class SoundEventReceiver {
        public ComponentName mEventReceiver;
        public int mEventType;

        public SoundEventReceiver(int i, ComponentName componentName) {
            this.mEventType = i;
            this.mEventReceiver = componentName;
        }

        public boolean equals(Object obj) {
            if (obj != null) {
                try {
                    ComponentName componentName = ((SoundEventReceiver) obj).mEventReceiver;
                    if (componentName == null) {
                        return false;
                    }
                    return componentName.equals(this.mEventReceiver);
                } catch (ClassCastException unused) {
                }
            }
            return false;
        }

        public boolean hasEventType(int i) {
            return (this.mEventType & i) == i;
        }

        public boolean samePackageName(String str) {
            ComponentName componentName = this.mEventReceiver;
            if (componentName == null) {
                return false;
            }
            return componentName.getPackageName().equals(str);
        }
    }

    public final class VolumeMap {
        public short lowerStep;
        public short raiseStep;

        public VolumeMap(short s, short s2) {
            this.raiseStep = s2;
            this.lowerStep = s;
        }
    }

    public final void setHeadsetOnlyStream(int i) {
        int i2;
        if ((i & 4) != 0) {
            SemAudioSystem.setPolicyParameters("l_sound_assistant_ring_via_headset_enable=52");
            this.mHeadsetOnlyStream = 49;
            return;
        }
        if ((i & 1) != 0) {
            this.mHeadsetOnlyStream |= 1;
            i2 = 4;
        } else {
            i2 = 0;
        }
        if ((i & 32) != 0) {
            i2 |= 32;
            this.mHeadsetOnlyStream |= 32;
        }
        if ((i & 16) != 0) {
            i2 |= 16;
            this.mHeadsetOnlyStream |= 16;
        }
        SemAudioSystem.setPolicyParameters("l_sound_assistant_ring_via_headset_enable=" + i2);
    }

    public void notifyCarLifeEvent(String str, int i) {
        sendBroadcastToSoundEventReceiver(512, i, str);
    }

    public final void muteMediaStreamOfSpeaker(boolean z) {
        int i;
        if (z) {
            i = MIN_STREAM_VOLUME[3];
        } else {
            i = this.mSavedSpeakerMediaIndex;
        }
        DeviceVolumeUpdate deviceVolumeUpdate = new DeviceVolumeUpdate(3, i, 2, "muteMediaStreamOfSpeaker");
        int index = this.mStreamStates[3].getIndex(2);
        if (z && this.mMuteMediaByVibrateOrSilentMode) {
            if (this.mSavedSpeakerMediaIndex == -1) {
                this.mSavedSpeakerMediaIndex = index;
                this.mSettingHelper.setIntValue("speaker_media_index", index);
            } else {
                r1 = false;
            }
        } else {
            r1 = this.mSavedSpeakerMediaIndex != -1 && index == MIN_STREAM_VOLUME[3];
            this.mSavedSpeakerMediaIndex = -1;
            this.mSettingHelper.setIntValue("speaker_media_index", -1);
        }
        if (r1) {
            sendMsg(this.mAudioHandler, 26, 2, index, deviceVolumeUpdate.getVolumeIndex(), deviceVolumeUpdate, 0);
        }
    }

    public int getIndexDividedBy10(int i, int i2) {
        if (i2 == 3) {
            return (i + 9) / 10;
        }
        return (i + 5) / 10;
    }

    public final int getNextFineMediaVolume(int i, int i2) {
        int index = this.mStreamStates[3].getIndex(i);
        if (index < 0 || index > this.mStreamStates[3].mIndexMax) {
            return index;
        }
        if (this.mVolumeSteps != null) {
            if (i2 > 0) {
                return this.mVolumeMap[index].raiseStep;
            }
            return this.mVolumeMap[index].lowerStep;
        }
        int i3 = index + (i2 * this.mMediaVolumeStepIndex);
        if (i3 < 0) {
            return 0;
        }
        return i3;
    }

    public void setFineVolume(int i, int i2, int i3, int i4, String str) {
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i2, i3, str + ".setFineVolume"));
        setStreamVolumeWithAttribution(i, i2, i3, null, str, str, null, Binder.getCallingUid(), true, i4);
    }

    public int getFineVolume(int i, int i2) {
        int index;
        ensureValidStreamType(i);
        if (i2 == 0) {
            if (isMultiSoundOn()) {
                i2 = getAppDevice(Binder.getCallingUid());
            }
            if (i2 == 0) {
                i2 = getDeviceForStream(i);
            }
        }
        synchronized (VolumeStreamState.class) {
            index = this.mStreamStates[i].getIndex(i2);
            if (this.mStreamStates[i].mIsMuted) {
                index = 0;
            }
            if (index != 0 && mStreamVolumeAlias[i] == 3 && isFixedVolumeDevice(i2)) {
                index = this.mStreamStates[i].getMaxIndex();
            }
        }
        return index;
    }

    public boolean shouldShowRingtoneVolume() {
        return !Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION;
    }

    public String[] getSelectedAppList() {
        return this.mPackageListHelper.getSelectedAppList();
    }

    public void addPackage(int i, String str) {
        enforceModifyAudioRoutingPermission();
        this.mPackageListHelper.addPackage(this.mContext, i, str);
    }

    public void removePackageForName(String str) {
        enforceModifyAudioRoutingPermission();
        this.mPackageListHelper.removePackageForName(this.mContext, str);
    }

    public boolean isAlreadyInDB(String str) {
        return this.mPackageListHelper.isAlreadyInDB(str);
    }

    public boolean isInAllowedList(String str) {
        return this.mPackageListHelper.isInAllowedList(str);
    }

    public int getObservedDevicesForMedia() {
        return AudioSystem.getDeviceMaskFromSet(this.mStreamStates[3].mObservedDeviceSet);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x022a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x022b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setStreamVolumeWithAttribution(int i, int i2, int i3, AudioDeviceAttributes audioDeviceAttributes, String str, String str2, String str3, int i4, boolean z, int i5) {
        int i6;
        boolean z2;
        int i7;
        int i8;
        int i9 = i2;
        int i10 = i4;
        int i11 = i5;
        Log.d("AS.AudioService", "setStreamVolume(stream=" + i + ", index=" + i9 + ", calling=" + str + ", device=" + i11 + ")");
        if (this.mUseFixedVolume) {
            return;
        }
        if ((i3 & 1048576) != 0) {
            i6 = i3 & (-1048577);
            z2 = true;
        } else {
            i6 = i3;
            z2 = false;
        }
        if (i == 3 && z2) {
            i7 = i9 > this.mStreamStates[i].mIndexMax ? 0 : i9 % 10;
            i9 /= 10;
        } else {
            i7 = 0;
        }
        ensureValidStreamType(i);
        int i12 = mStreamVolumeAlias[i];
        VolumeStreamState volumeStreamState = this.mStreamStates[i12];
        int deviceForStream = audioDeviceAttributes == null ? getDeviceForStream(i) : audioDeviceAttributes.getInternalType();
        if (i11 != 0) {
            if (isMultiSoundOnInternal() && deviceForStream != i11 && i == 3) {
                i6 |= 8388608;
                Log.d("AS.AudioService", "device=" + i11 + " add FLAG_MULTI_SOUND ");
            }
        } else {
            if (i == 3 && (i11 = getAppDevice(i10)) != 0) {
                i6 |= 8388608;
                Log.d("AS.AudioService", "add FLAG_MULTI_SOUND device=" + deviceForStream);
            }
            if (this.mScreenSharingHelper.isSupportDisplayVolumeControl() && (8388608 & i6) != 0) {
                i6 = (-8388609) & i6;
            }
            if ("com.sec.android.app.voicenote".equals(str)) {
                deviceForStream = 2;
            }
            if (!"com.android.bluetooth".equals(str) && i == 0 && isBluetoothScoOn() && (deviceForStream == 536870912 || deviceForStream == 536870913)) {
                int i13 = i9 * 10;
                this.mStreamStates[i].setIndex(i13, deviceForStream, str2, z);
                Log.i("AS.AudioService", "setStreamVolumeWithAttribution sync BLE call volume index = " + i13);
                sendMsg(this.mAudioHandler, 1, 2, deviceForStream, 0, volumeStreamState, 500);
                return;
            }
            if (!this.mVolumeLimitOn && mStreamVolumeAlias[i] == 3 && (i9 * 10) + i7 > getVolumeLimitValue() * 10 && this.mSoundDoseHelper.safeDevicesContains(deviceForStream)) {
                this.mVolumeController.displayVolumeLimiterToast();
                Log.i("AS.AudioService", "SetStreamVolume : already reached at volume limit value");
                int index = this.mStreamStates[3].getIndex(deviceForStream);
                sendVolumeChangedIntent(i, index, index, i6, deviceForStream);
                if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(deviceForStream))) {
                    this.mDeviceBroker.postSetAvrcpAbsoluteVolumeIndex(index);
                    return;
                }
                return;
            }
            boolean booleanValue = ((Boolean) this.mFMRadioRecordingChecker.runOrSkip()).booleanValue();
            if (Rune.SEC_AUDIO_FM_RADIO_RECORDING && booleanValue) {
                if (i6 != 33554432 || i6 == 17) {
                    return;
                }
                if (i6 == 0 && i != 3) {
                    return;
                }
            }
            if (Rune.SEC_AUDIO_FM_RADIO && i12 == 3 && "com.sec.android.app.fm".equals(str)) {
                i8 = this.mForcedUseForFMRadio != 1 ? 2 : deviceForStream;
                if (i8 != deviceForStream) {
                    Log.d("AS.AudioService", "setStreamVolume : correct device (" + deviceForStream + " -> " + i8 + ") because fm radio application calls");
                    deviceForStream = i8;
                }
            }
            if (!AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(deviceForStream)) || AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(deviceForStream)) || (i6 & 64) == 0) {
                if (i10 == 1000) {
                    i10 = UserHandle.getUid(getCurrentUserId(), UserHandle.getAppId(i4));
                }
                if (checkNoteAppOp(STREAM_VOLUME_OPS[i12], i10, str, str3)) {
                    return;
                }
                if (isAndroidNPlus(str) && wouldToggleZenMode(getNewRingerMode(i12, i9, i6)) && !this.mNm.isNotificationPolicyAccessGrantedForPackage(str) && !isExemptedPackage(str)) {
                    throw new SecurityException("Not allowed to change Do Not Disturb state");
                }
                if (!volumeAdjustmentAllowedByDnd(i12, i6)) {
                    if ((i6 & 1) != 0) {
                        this.mVolumeController.postVolumeChanged(i, i6);
                        return;
                    }
                    return;
                }
                if (!volumeAdjustmentAllowedByLeBroadcast(deviceForStream, i)) {
                    if ((i6 & 1) != 0) {
                        this.mVolumeController.postVolumeChanged(i, i6);
                        Log.d("AS.AudioService", "Not allowed to change the volume to " + i9 + ", stream=" + i + ", because of le broadcasting");
                        return;
                    }
                    return;
                }
                this.mSoundDoseHelper.invalidatPendingVolumeCommand();
                int index2 = volumeStreamState.getIndex(deviceForStream);
                int rescaleIndex = rescaleIndex(i9 * 10, i, i12);
                if (i == 3) {
                    rescaleIndex += i7;
                }
                if (i12 == 3 && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(deviceForStream)) && (i6 & 64) == 0) {
                    Log.d("AS.AudioService", "setStreamVolume postSetAvrcpAbsoluteVolumeIndex index=" + rescaleIndex + "stream=" + i);
                    if ((Rune.SEC_AUDIO_FM_RADIO_RECORDING && booleanValue) || !this.mSoundDoseHelper.checkSafeMediaVolume(i12, rescaleIndex, deviceForStream)) {
                        if (!str2.endsWith(".setA2dpDeviceVolume")) {
                            this.mDeviceBroker.updateIndividualA2dpVolumes(rescaleIndex);
                        }
                        this.mDeviceBroker.postSetAvrcpAbsoluteVolumeIndex(rescaleIndex);
                    }
                } else if (isAbsoluteVolumeDevice(deviceForStream) && (i6 & IInstalld.FLAG_FORCE) == 0) {
                    dispatchAbsoluteVolumeChanged(i, (AbsoluteVolumeDeviceInfo) this.mAbsoluteVolumeDeviceInfoMap.get(Integer.valueOf(deviceForStream)), rescaleIndex);
                }
                if (AudioSystem.isLeAudioDeviceType(deviceForStream) && i == getBluetoothContextualVolumeStream() && (i6 & 64) == 0) {
                    Log.d("AS.AudioService", "setStreamVolume postSetLeAudioVolumeIndex index=" + rescaleIndex + " stream=" + i);
                    this.mDeviceBroker.postSetLeAudioVolumeIndex(rescaleIndex, this.mStreamStates[i].getMaxIndex(), i);
                }
                if (deviceForStream == 134217728 && i == getBluetoothContextualVolumeStream()) {
                    Log.i("AS.AudioService", "setStreamVolume postSetHearingAidVolumeIndex index=" + rescaleIndex + " stream=" + i);
                    this.mDeviceBroker.postSetHearingAidVolumeIndex(rescaleIndex, i);
                }
                int i14 = i6 & (-33);
                if (i12 == 3 && isFixedVolumeDevice(deviceForStream)) {
                    i14 |= 32;
                    if (rescaleIndex != 0 && (rescaleIndex = this.mSoundDoseHelper.getSafeMediaVolumeIndex(deviceForStream)) < 0) {
                        rescaleIndex = volumeStreamState.getMaxIndex();
                    }
                }
                int i15 = rescaleIndex;
                int i16 = i14;
                if (!this.mSoundDoseHelper.willDisplayWarningAfterCheckVolume(i, i15, deviceForStream, i16)) {
                    onSetStreamVolume(i, i15, i16, deviceForStream, str2, z, audioDeviceAttributes == null);
                    i15 = this.mStreamStates[i].getIndex(deviceForStream);
                }
                int i17 = i15;
                synchronized (this.mHdmiClientLock) {
                    if (i12 == 3 && index2 != i17) {
                        maybeSendSystemAudioStatusCommand(false);
                    }
                }
                if (audioDeviceAttributes == null) {
                    sendVolumeUpdate(i, index2, i17, i16, deviceForStream);
                }
                GoodCatchManager goodCatchManager = this.mGoodCatchManager;
                if (goodCatchManager != null && goodCatchManager.isMediaVolumeCatchEnabled() && i == 3 && index2 > 0 && i17 == 0) {
                    this.mGoodCatchManager.updateMediaVolume(str2, this.mContext.getPackageName());
                    return;
                }
                return;
            }
            return;
        }
        deviceForStream = i11;
        if (this.mScreenSharingHelper.isSupportDisplayVolumeControl()) {
            i6 = (-8388609) & i6;
        }
        if ("com.sec.android.app.voicenote".equals(str)) {
        }
        if (!"com.android.bluetooth".equals(str)) {
        }
        if (!this.mVolumeLimitOn) {
        }
        boolean booleanValue2 = ((Boolean) this.mFMRadioRecordingChecker.runOrSkip()).booleanValue();
        if (Rune.SEC_AUDIO_FM_RADIO_RECORDING) {
            if (i6 != 33554432) {
                return;
            } else {
                return;
            }
        }
        if (Rune.SEC_AUDIO_FM_RADIO) {
            if (this.mForcedUseForFMRadio != 1) {
            }
            if (i8 != deviceForStream) {
            }
        }
        if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(deviceForStream))) {
        }
        if (i10 == 1000) {
        }
        if (checkNoteAppOp(STREAM_VOLUME_OPS[i12], i10, str, str3)) {
        }
    }

    public int getStreamVolume(int i, int i2) {
        int indexDividedBy10;
        ensureValidStreamType(i);
        if (i2 == 0 && isMultiSoundOn() && i == 3) {
            i2 = getAppDevice(Binder.getCallingUid());
        }
        if (i2 == 0) {
            i2 = getDeviceForStream(i);
        }
        synchronized (VolumeStreamState.class) {
            int index = this.mStreamStates[i].getIndex(i2);
            if (this.mStreamStates[i].mIsMuted) {
                index = 0;
            }
            if (index != 0 && mStreamVolumeAlias[i] == 3 && isFixedVolumeDevice(i2)) {
                index = this.mStreamStates[i].getMaxIndex();
            }
            indexDividedBy10 = getIndexDividedBy10(index, i);
        }
        return indexDividedBy10;
    }

    public final int selectDevice(int i) {
        int deviceMultiSoundUsingActually;
        if (!isMultiSoundOnInternal()) {
            return i;
        }
        Log.d("AS.AudioService", "selectDevice : mForegroundUid=" + this.mForegroundUid);
        int appDevice = this.mMultiSoundManager.getAppDevice(this.mForegroundUid);
        return (appDevice == 0 || (!AudioUtils.isUsingAudioForUid(this.mForegroundUid) && this.mAudioSystem.isStreamActive(3, 0))) ? (AudioUtils.isUsingAudioUponDevice(i) || (deviceMultiSoundUsingActually = this.mMultiSoundManager.getDeviceMultiSoundUsingActually()) == 0) ? i : deviceMultiSoundUsingActually : appDevice;
    }

    public void sendBecomingNoisyIntentToUnpinApps(int i) {
        this.mMultiSoundManager.sendBecomingNoisyIntentToUnpinApps(i);
    }

    public final String[] getPackageName(int i) {
        String[] strArr;
        if (i == 1000) {
            return SYSTEM_PACKAGE;
        }
        try {
            strArr = this.mPackageManager.getPackagesForUid(i);
        } catch (IllegalArgumentException unused) {
            Log.e("AS.AudioService", "getPackageName:Invalid uid " + i);
            strArr = null;
        }
        return strArr != null ? strArr : EMPTY_PACKAGE;
    }

    public int getUidForDevice(int i) {
        Log.d("AS.AudioService", "getUidForDevice, " + Integer.toHexString(i));
        return this.mMediaFocusControl.getUidForDevice(i);
    }

    public void setAppDevice(final int i, final int i2, boolean z) {
        enforceModifyAudioRoutingPermission();
        String[] packageName = getPackageName(i);
        if (TextUtils.isEmpty(packageName[0]) && mAppCastingDevice.contains(Integer.valueOf(i2))) {
            this.mScreenSharingHelper.updateAppCasting(-1002);
            this.mAudioHandler.post(new Runnable() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    AudioService.this.lambda$setAppDevice$20(i);
                }
            });
            Log.e("AS.AudioService", "invalid uid: " + i + ", deviceType: " + i2);
            return;
        }
        if (mAppCastingDevice.contains(Integer.valueOf(i2))) {
            this.mScreenSharingHelper.updateAppCasting(i2);
            this.mAudioHandler.post(new Runnable() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    AudioService.this.lambda$setAppDevice$21(i, i2);
                }
            });
            return;
        }
        int convertDeviceTypeToInternalDevice = AudioDeviceInfo.convertDeviceTypeToInternalDevice(i2);
        Log.d("AS.AudioService", "setAppDevice, uid:" + i + ", device:" + convertDeviceTypeToInternalDevice);
        sendMsg(this.mAudioHandler, z ? 2764 : 2774, 2, i, convertDeviceTypeToInternalDevice, packageName[0], convertDeviceTypeToInternalDevice == 0 ? sendBecomingNoisyIntent(packageName, i) : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAppDevice$20(int i) {
        this.mMultiSoundManager.setAppToRemoteSubmix(i, -1002);
        this.mAudioSystem.onRoutingUpdated();
        observeDevicesForMediaStream();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAppDevice$21(int i, int i2) {
        this.mMultiSoundManager.setAppToRemoteSubmix(i, i2);
        this.mAudioSystem.onRoutingUpdated();
        observeDevicesForMediaStream();
    }

    public int getAppDevice(int i) {
        return this.mMultiSoundManager.getAppDevice(i);
    }

    public void setAppVolume(int i, int i2, String str) {
        if (TextUtils.isEmpty(getPackageName(i)[0])) {
            throw new IllegalArgumentException("Bad uid " + i);
        }
        checkModifyPhoneStateOrRoutingPermission();
        sAppVolumeLogger.enqueue(new EventLogger.StringEvent("uid:" + i + ",volume:" + i2 + ",package:" + str).printLog("AS.AudioService"));
        this.mMultiSoundManager.setAppVolume(i, i2);
        if ("com.samsung.android.soundassistant".equals(str)) {
            this.mAppVolumeFromSoundAssistant.put(i, i2);
        }
    }

    public int getAppVolume(int i) {
        int appVolume = this.mMultiSoundManager.getAppVolume(i);
        Log.d("AS.AudioService", "getAppVolume, uid:" + i + ", volume=" + appVolume);
        return appVolume;
    }

    public void setAppMute(int i, boolean z, String str) {
        if (TextUtils.isEmpty(getPackageName(i)[0])) {
            throw new IllegalArgumentException("Bad uid " + i);
        }
        checkModifyPhoneStateOrRoutingPermission();
        sAppVolumeLogger.enqueue(new EventLogger.StringEvent("uid:" + i + ",shouldMute:" + z + ",package:" + str).printLog("AS.AudioService"));
        this.mMultiSoundManager.setAppMute(i, z);
    }

    public boolean isAppMute(int i) {
        boolean isAppMute = this.mMultiSoundManager.isAppMute(i);
        Log.d("AS.AudioService", "isAppMute, uid:" + i + ", mute=" + isAppMute);
        return isAppMute;
    }

    public String getPinAppInfo(int i) {
        String pinAppInfo = this.mMultiSoundManager.getPinAppInfo(i);
        Log.d("AS.AudioService", "getPinAppInfo, device=" + i + ", pinappinfo=" + pinAppInfo);
        return pinAppInfo;
    }

    public void setMultiSoundOn(boolean z, boolean z2) {
        if (checkAudioSettingsPermission("setMultiSoundOn")) {
            sendMsg(this.mAudioHandler, 2763, 2, z2 ? 1 : 0, 0, Boolean.valueOf(z), 0);
        }
    }

    public boolean isMultiSoundOn() {
        MultiSoundManager multiSoundManager = this.mMultiSoundManager;
        if (multiSoundManager == null) {
            return false;
        }
        return multiSoundManager.isEnabled();
    }

    public boolean isMultiSoundOnInternal() {
        if (this.mMultiSoundManager == null || isInCommunication()) {
            return false;
        }
        return this.mMultiSoundManager.isEnabled() || (this.mMultiSoundManager.isRemoteSubmixAppExist() && !this.mMediaSessionServiceInternal.isAudioMirroring());
    }

    public void sendBecomingNoisyToPinnedApp(int i) {
        this.mMultiSoundManager.sendBecomingNoisyToPinnedApp(i);
    }

    public void onSetAppDevice(String str, int i, int i2, boolean z) {
        this.mMultiSoundManager.setAppDevice(i, i2, z);
        this.mMediaFocusControl.updateFocusRequester(i);
        sendBroadcastToAll(new Intent("android.intent.action.MULTISOUND_STATE_CHANGE"), null);
        MediaSessionService.MediaSessionServiceInternal mediaSessionServiceInternal = this.mMediaSessionServiceInternal;
        if (mediaSessionServiceInternal != null) {
            mediaSessionServiceInternal.updateMultiSoundInfo(-1, isMultiSoundOn());
        }
        if (z) {
            this.mMultiSoundManager.showNotification();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:82:0x015b, code lost:
    
        if (r6.mDeviceBroker.checkDeviceConnected(8) != false) goto L68;
     */
    /* JADX WARN: Removed duplicated region for block: B:73:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:80:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getMultiSoundConfig(String str) {
        int i;
        int deviceForStream;
        AudioParameter audioParameter = new AudioParameter(str);
        String str2 = audioParameter.get("g_multi_sound_pin_app_name");
        if (str2 != null) {
            try {
                i = Integer.parseInt(str2);
            } catch (NumberFormatException unused) {
                Log.e("AS.AudioService", "invalid value " + str2);
                i = -1;
            }
            String pinAppInfo = getPinAppInfo(i);
            if (!this.mMultiSoundManager.isEnabled()) {
                return pinAppInfo;
            }
            Log.d("AS.AudioService", "device=0x" + Integer.toHexString(i) + ", pinappinfo=" + pinAppInfo);
            return pinAppInfo;
        }
        if (audioParameter.hasKey("g_multi_sound_priority_device")) {
            return Integer.toString(AudioDeviceInfo.convertInternalDeviceToDeviceType(this.mDeviceBroker.getPriorityDevice(AudioDeviceInfo.convertDeviceTypeToInternalDevice(25))));
        }
        String str3 = audioParameter.get("pin_device_name");
        int i2 = 8;
        String str4 = "";
        if (str3 != null) {
            int parseInt = Integer.parseInt(str3);
            if (parseInt == 4 || parseInt == 8) {
                return this.mContext.getString(R.string.fingerprint_or_screen_lock_dialog_default_subtitle);
            }
            if (parseInt != 128) {
                if (parseInt != 16384) {
                    if (parseInt == 32768) {
                        return this.mContext.getString(17043395);
                    }
                    if (parseInt != 67108864) {
                        return this.mContext.getString(R.string.fingerprint_error_vendor_unknown);
                    }
                }
                AudioDeviceInfo[] devicesStatic = AudioManager.getDevicesStatic(2);
                for (int i3 = 0; i3 < devicesStatic.length; i3++) {
                    if (devicesStatic[i3].semGetInternalType() == 16384 || devicesStatic[i3].semGetInternalType() == 67108864) {
                        str4 = devicesStatic[i3].getProductName().toString();
                    }
                }
            } else {
                BluetoothA2dp a2dp = this.mDeviceBroker.getA2dp();
                if (a2dp != null) {
                    if (a2dp.semIsDualPlayMode()) {
                        StringBuilder sb = new StringBuilder();
                        List<BluetoothDevice> connectedDevices = a2dp.getConnectedDevices();
                        if (connectedDevices != null && connectedDevices.size() >= 1) {
                            for (BluetoothDevice bluetoothDevice : connectedDevices) {
                                if (sb.length() > 0) {
                                    sb.append(",");
                                }
                                sb.append(bluetoothDevice.semGetAliasName());
                            }
                        }
                        return sb.toString();
                    }
                    BluetoothDevice activeDevice = a2dp.getActiveDevice();
                    if (activeDevice != null) {
                        return activeDevice.semGetAliasName();
                    }
                }
            }
        } else {
            String str5 = audioParameter.get("l_safe_media_volume_enable");
            if (str5 != null) {
                try {
                    deviceForStream = Integer.parseInt(str5);
                } catch (NumberFormatException unused2) {
                    deviceForStream = getDeviceForStream(3);
                }
                if (deviceForStream == 2) {
                    if (this.mDeviceBroker.checkDeviceConnected(4)) {
                        i2 = 4;
                    }
                    return !this.mSoundDoseHelper.safeDevicesContains(i2) ? "true" : ((i2 & 32768) == 0 || !this.mIsBluetoothCastState) ? "false" : "true";
                }
                i2 = deviceForStream;
                if (!this.mSoundDoseHelper.safeDevicesContains(i2)) {
                }
            }
        }
        return str4;
    }

    public void setStreamVolumeForDeviceWithAttribution(int i, int i2, int i3, String str, String str2, int i4) {
        if (i == 10 && !canChangeAccessibilityVolume()) {
            Log.w("AS.AudioService", "Trying to call setStreamVolume() for a11y without CHANGE_ACCESSIBILITY_VOLUME  callingPackage=" + str);
            return;
        }
        if (i == 0 && i2 == 0) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
                Log.w("AS.AudioService", "Trying to call setStreamVolume() for STREAM_VOICE_CALL and index 0 without MODIFY_PHONE_STATE  callingPackage=" + str);
                return;
            }
        }
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i2, i3, str));
        setStreamVolumeWithAttribution(i, i2, i3, null, str, str, str2, Binder.getCallingUid(), true, i4);
    }

    public int getStreamVolumeForDevice(int i, int i2) {
        return getStreamVolume(i, i2);
    }

    public final int sendBecomingNoisyIntent(int i) {
        return sendBecomingNoisyIntent(getPackageName(i), i);
    }

    public final int sendBecomingNoisyIntent(String[] strArr, int i) {
        if (strArr == null || TextUtils.isEmpty(strArr[0])) {
            return 0;
        }
        Intent intent = new Intent("android.media.AUDIO_BECOMING_NOISY");
        for (String str : strArr) {
            intent.setPackage(str);
            sendBroadcastToAll(intent, null);
            Log.d("AS.AudioService", "sendBecomingNoisyIntent to " + str);
        }
        this.mMediaFocusControl.handleExternalFocusGain(i);
        return 200;
    }

    public int getPinDevice() {
        if (this.mMultiSoundManager.isRemoteSubmixAppExist()) {
            return 32768;
        }
        return getPinDeviceInternal();
    }

    public int getPinDeviceInternal() {
        return this.mMultiSoundManager.getPinDevice();
    }

    public int secGetActiveStreamType(int i) {
        return getActiveStreamType(i);
    }

    public final boolean isStreamActiveForExternal(int i) {
        if (i == -1) {
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean isStreamActive = AudioSystem.isStreamActive(i, 0);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return isStreamActive;
    }

    public void setForceSpeakerOn(boolean z) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        this.mForceSpeaker = z ? 1 : 0;
        if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(getDeviceForStream(3)))) {
            return;
        }
        sendMsg(this.mAudioHandler, 8, 2, 1, this.mForceSpeaker, null, 0);
    }

    public boolean isForceSpeakerOn() {
        return this.mForceSpeaker == 1;
    }

    public void sendMsgForForceSpeaker() {
        sendMsg(this.mAudioHandler, 8, 2, 1, 1, null, 0);
    }

    public void updateDexState() {
        this.mDesktopModeHelper.updateDexState();
    }

    public void setRadioOutputPath(int i) {
        if (checkAudioSettingsPermission("setRadioOutputPath()")) {
            synchronized (this.mSettingsLock) {
                this.mStreamStates[3].mute(false, "setRadioOutputPath");
            }
            if (i == 2) {
                this.mAudioSystem.setForceUse(8, 1);
                this.mForcedUseForFMRadio = 1;
            } else if (i == 3) {
                this.mAudioSystem.setForceUse(8, 0);
                this.mForcedUseForFMRadio = 0;
            } else {
                Log.i("AS.AudioService", "FM radio app set wrong radio output path : " + i);
                return;
            }
            int streamVolume = getStreamVolume(3) * 10;
            sendVolumeChangedIntent(3, streamVolume, streamVolume, 0);
        }
    }

    public int getRadioOutputPath() {
        return this.mForcedUseForFMRadio == 1 ? 2 : 3;
    }

    public void notifySafetyVolumeDialogVisible(IVolumeController iVolumeController, boolean z) {
        enforceVolumeController("notify about volume controller visibility");
        if (this.mVolumeController.isSameBinder(iVolumeController)) {
            this.mVolumeController.setSafetyDialogVisible(z);
            Log.d("AS.AudioService", "Safety Volume controller visible: " + z);
        }
    }

    public boolean isSafeMediaVolumeStateActive() {
        return this.mSoundDoseHelper.isSafeMediaVolumeStateActive();
    }

    public final void callLcdOn() {
        PowerManager.WakeLock newWakeLock = this.mPowerManager.newWakeLock(805306378, "AS.AudioService");
        if (newWakeLock.isHeld()) {
            return;
        }
        newWakeLock.acquire(5000L);
    }

    public final void notifyVibrationForSafeMediaPopup() {
        if (this.mPowerManager.isScreenOn()) {
            return;
        }
        callLcdOn();
        callVibrateMsg("android", 0);
    }

    public void setBtOffloadEnable(int i) {
        this.mDeviceBroker.setBtOffloadEnable(i);
    }

    public void updateAvrcpAbsoluteVolumeSupported(boolean z) {
        if (this.mAvrcpAbsVolSupported != z) {
            sVolumeLogger.enqueue(new EventLogger.StringEvent("a2dp AVC : " + z));
        }
        this.mAvrcpAbsVolSupported = z;
    }

    public void setA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i, int i2, int i3, String str) {
        int i4;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        if (bluetoothDevice == null) {
            throw new IllegalArgumentException("Invalid device device is null");
        }
        if (i == 3) {
            if (this.mVolumeLimitOn && i2 > getVolumeLimitValue() * 10 && this.mSoundDoseHelper.safeDevicesContains(128)) {
                Log.i("AS.AudioService", "Over the volume limit value");
            } else {
                if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3 && bluetoothDevice.semGetAudioType() == 2 && this.mSoundDoseHelper.checkSafeMediaVolume(i, i2, 128)) {
                    this.mVolumeController.postDisplaySafeVolumeWarning(i3);
                    return;
                }
                this.mDeviceBroker.setA2dpDeviceVolume(bluetoothDevice, i2);
                int mainA2dpVolume = this.mDeviceBroker.getMainA2dpVolume();
                if (mainA2dpVolume == -1) {
                    mainA2dpVolume = i2;
                }
                i4 = mainA2dpVolume;
                sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i4, i3, str + ".setA2dpDeviceVolume"));
                setStreamVolumeWithAttribution(i, i4, i3, null, str, str + ".setA2dpDeviceVolume", null, Binder.getCallingUid(), true, 128);
            }
        }
        i4 = i2;
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i4, i3, str + ".setA2dpDeviceVolume"));
        setStreamVolumeWithAttribution(i, i4, i3, null, str, str + ".setA2dpDeviceVolume", null, Binder.getCallingUid(), true, 128);
    }

    public int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i) {
        int i2;
        if (bluetoothDevice == null) {
            throw new IllegalArgumentException("Invalid device: null ");
        }
        if (i == 3) {
            i2 = this.mDeviceBroker.getA2dpDeviceVolume(bluetoothDevice);
            if (this.mStreamStates[i].mIsMuted) {
                i2 = 0;
            }
        } else {
            i2 = -1;
        }
        if (i2 != -1) {
            return i2;
        }
        Log.w("AS.AudioService", "Cannot find bluetooth device address");
        return getFineVolume(i, 128);
    }

    public class MediaVolumeStreamState extends VolumeStreamState {
        public MediaVolumeStreamState(String str, int i) {
            super(str, i);
        }

        @Override // com.android.server.audio.AudioService.VolumeStreamState
        public boolean setIndex(int i, int i2, String str, boolean z) {
            if (isA2dpDevice(i2) || (isBleDevice(i2) && AudioService.this.isBluetoothDualModeActive())) {
                if (!str.endsWith("setA2dpDeviceVolume") && !str.equals("onSetA2dpSinkConnectionState") && !str.equals("enforceBluetoothSafeMediaVolume")) {
                    AudioService.this.mDeviceBroker.updateIndividualA2dpVolumes(i);
                }
                if (i > AudioService.this.mSoundDoseHelper.getSafeDeviceMediaVolumeIndex(i2)) {
                    AudioService.this.mSoundDoseHelper.setSafeMediaVolumeStateForBlueTooth(1);
                    AudioService.sVolumeLogger.enqueue(new EventLogger.StringEvent(str + " disable safe index  volIdx:" + i));
                }
            }
            if (AudioService.this.mMuteMediaByVibrateOrSilentMode && AudioService.this.mRingerMode != 2 && i2 == 2 && i > AudioService.MIN_STREAM_VOLUME[3] && !str.equals("muteMediaStreamOfSpeaker")) {
                AudioService.this.mSavedSpeakerMediaIndex = -1;
                AudioService.this.mSettingHelper.setIntValue("speaker_media_index", AudioService.this.mSavedSpeakerMediaIndex);
            }
            return super.setIndex(i, i2, str, z);
        }

        @Override // com.android.server.audio.AudioService.VolumeStreamState
        public void applyDeviceVolume_syncVSS(int i) {
            super.applyDeviceVolume_syncVSS(i);
            if (AudioService.this.mMultiSoundManager != null && AudioService.this.mMultiSoundManager.getPreventOverheatState() && (i & 2) != 0) {
                AudioService.this.mMultiSoundManager.setLimitedVolumeForOverheat();
            }
            if (Rune.SEC_AUDIO_VOLUME_MONITOR && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
                AudioService.this.mExt.getVolumeMonitorService().setDeviceVolumeForBluetooth(getIndexDividedBy10(i), AudioService.this.mAvrcpAbsVolSupported);
            }
        }

        public final boolean isA2dpDevice(int i) {
            return AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i));
        }

        public final boolean isBleDevice(int i) {
            return AudioSystem.DEVICE_OUT_ALL_BLE_SET.contains(Integer.valueOf(i));
        }
    }

    public float[] getFloatVolumeTable() {
        return DualA2dpVolumeManager.FINE_VOLUME_TABLE;
    }

    public void setRemoteMic(boolean z) {
        this.mRemoteMic = z;
    }

    public boolean getRemoteMic() {
        return this.mRemoteMic;
    }

    public class MultiVolumeController extends VolumeController {
        public ArrayList mVolumeControllerList;
        public boolean mVolumeStarEnable;

        public MultiVolumeController() {
            super();
            this.mVolumeControllerList = new ArrayList(2);
            this.mVolumeStarEnable = false;
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized void setController(IVolumeController iVolumeController) {
            super.setController(iVolumeController);
            if (iVolumeController != null) {
                this.mVolumeControllerList.add(iVolumeController);
                if (this.mVolumeStarEnable) {
                    super.setA11yMode(100);
                }
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized boolean isSameBinder(IVolumeController iVolumeController) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                if (Objects.equals(binder((IVolumeController) it.next()), binder(iVolumeController))) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                sb.append(super.toString());
            }
            return sb.toString();
        }

        @Override // com.android.server.audio.AudioService.VolumeController, com.android.server.audio.AudioService.ISafeHearingVolumeController
        public synchronized void postDisplaySafeVolumeWarning(int i) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.postDisplaySafeVolumeWarning(i);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized void postVolumeChanged(int i, int i2) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.postVolumeChanged(i, i2);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized void postMasterMuteChanged(int i) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.postMasterMuteChanged(i);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized void setLayoutDirection(int i) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.setLayoutDirection(i);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized void postDismiss() {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.postDismiss();
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized void setA11yMode(int i) {
            if (i == 100 || i == 101) {
                this.mVolumeStarEnable = i == 100;
            }
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.setA11yMode(i);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized void displayVolumeLimiterToast() {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.displayVolumeLimiterToast();
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public synchronized void removeController(IVolumeController iVolumeController) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (Objects.equals(binder((IVolumeController) it.next()), binder(iVolumeController))) {
                    it.remove();
                    break;
                }
            }
            if (this.mVolumeControllerList.isEmpty()) {
                super.setController(null);
            } else {
                super.setController((IVolumeController) this.mVolumeControllerList.get(0));
            }
        }
    }

    public boolean isUsingAudio(int i) {
        try {
            if (!isAudioServerRunning()) {
                sUsingAudioLogger.enqueue(new EventLogger.StringEvent("isUsingAudio audioserver is died").printLog("AS.AudioService"));
                return false;
            }
            if (!"true".equals(AudioSystem.getParameters(new AudioParameter.Builder().setParam("l_is_using_audio", i).build().toString()))) {
                return false;
            }
            sUsingAudioLogger.enqueue(new EventLogger.StringEvent("uid:" + i + "is using audio").printLog("AS.AudioService"));
            return true;
        } catch (SecurityException e) {
            sUsingAudioLogger.enqueue(new EventLogger.StringEvent("isUsingAudio permission error" + e).printLog("AS.AudioService"));
            return false;
        }
    }

    public List getActivePlaybackConfigurationsInternal() {
        return this.mPlaybackMonitor.getActivePlaybackConfigurations(true);
    }

    public List getActiveRecordingConfigurationsInternal() {
        return this.mRecordMonitor.getActiveRecordingConfigurations(true);
    }

    public final boolean isMuteIntervalEnabled() {
        return Settings.Global.getInt(this.mContentResolver, "mode_ringer_time_on", 0) == 1;
    }

    public void setMuteInterval(int i, String str) {
        Log.i("AS.AudioService", "setMuteInterval unmute timer=" + i + " from=" + str);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            Log.w("AS.AudioService", "WRITE_SECURE_SETTINGS Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        sendMsg(this.mAudioHandler, 2766, 0, i, 0, 0, 0);
    }

    public int getMuteInterval() {
        return Settings.Global.getInt(this.mContentResolver, "mode_ringer_time", 60);
    }

    public int getRemainingMuteIntervalMs() {
        if (this.mMuteIntervalMs != 0) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = this.mMuteTime;
            if (currentTimeMillis > j) {
                return 0;
            }
            this.mMuteIntervalMs = (((int) ((j - currentTimeMillis) / 60000)) * 60000) + 60000;
        }
        Log.i("AS.AudioService", "[Mute Interval] remaining time=" + (this.mMuteIntervalMs / 60000) + " mins");
        return this.mMuteIntervalMs;
    }

    public int getPrevRingerMode() {
        return this.mPrevRingerMode;
    }

    public final void checkMuteInterval() {
        if (isMuteIntervalEnabled()) {
            this.mMuteIntervalMs = 0;
            setRingerMode(this.mPrevRingerMode, "checkMuteInterval", false);
            Settings.Global.putInt(this.mContentResolver, "mode_ringer_time_on", 0);
        }
    }

    public int setDeviceToForceByUser(int i, String str, boolean z) {
        checkModifyPhoneStateOrRoutingPermission();
        return this.mDeviceBroker.setDeviceToForceByUser(i, str, z);
    }

    public final void setVolumeLimiter(AudioParameter audioParameter) {
        String str = audioParameter.get("enable");
        if (str != null) {
            this.mVolumeLimitOn = "true".equals(str);
            return;
        }
        String str2 = audioParameter.get("level");
        if (str2 != null) {
            this.mVolumeLimitValue = Integer.parseInt(str2);
            String str3 = audioParameter.get("package");
            if (this.mVolumeLimitOn) {
                setVolumeLevelToLimit(str3);
            }
        }
    }

    public final void setVolumeLevelToLimit(String str) {
        int volumeLimitValue = getVolumeLimitValue();
        int deviceForStream = getDeviceForStream(3);
        int i = volumeLimitValue * 10;
        if (this.mStreamStates[3].getIndex(deviceForStream) <= i || !this.mSoundDoseHelper.safeDevicesContains(deviceForStream)) {
            return;
        }
        showReduceVolumeToast();
        setStreamVolume(3, i, 1048576, str);
    }

    public final void setPreventOverheatForGame(AudioParameter audioParameter) {
        String str;
        try {
            int parseInt = Integer.parseInt(audioParameter.get("uid"));
            if (parseInt == -1 || (str = audioParameter.get(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)) == null) {
                return;
            }
            this.mMultiSoundManager.setPreventOverheatState(parseInt, "true".equals(str));
            this.mMultiSoundManager.setLimitedVolumeForOverheat();
        } catch (NumberFormatException e) {
            Log.e("AS.AudioService", "NumberFormatException", e);
        }
    }

    public String getCurrentAudioFocusPackageName() {
        return this.mMediaFocusControl.getCurrentAudioFocusPackageName();
    }

    public void dismissVolumePanel() {
        this.mVolumeController.postDismiss();
    }

    public List getExcludedRingtoneTitles(int i) {
        return this.mExt.getExcludedRingtoneTitles(i);
    }

    public void setForceUseForMedia(int i) {
        if (checkAudioSettingsPermission("setForceUseForMedia()")) {
            if (i == 10001) {
                this.mAudioSystem.setForceUse(1, 10001);
                this.mForcedUseForMedia = 10001;
            } else {
                this.mAudioSystem.setForceUse(1, 0);
                this.mForcedUseForMedia = 0;
            }
            sendMsg(this.mAudioHandler, 8, 2, 1, this.mForcedUseForMedia, null, 0);
        }
    }

    public final void sendVolumeChangedIntent(int i, int i2, int i3, int i4, int i5) {
        if ((i4 & 32) != 0) {
            return;
        }
        if (i2 == i3 && this.mLastVolumeChangedIntentDevice == i5 && !this.mDeviceBroker.shouldVolumeChangedIntent()) {
            return;
        }
        this.mLastVolumeChangedIntentDevice = i5;
        sendVolumeChangedIntent(i, i2, i3, i4);
    }

    public final void sendVolumeChangedIntent(final int i, int i2, int i3, final int i4) {
        final int indexDividedBy10 = getIndexDividedBy10(i2, i);
        final int indexDividedBy102 = getIndexDividedBy10(i3, i);
        this.mAudioHandler.post(new Runnable() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                AudioService.this.lambda$sendVolumeChangedIntent$22(i, indexDividedBy102, indexDividedBy10, i4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendVolumeChangedIntent$22(int i, int i2, int i3, int i4) {
        Intent intent = new Intent("android.media.VOLUME_CHANGED_ACTION");
        intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", i);
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setDeliveryGroupPolicy(1);
        makeBasic.setDeliveryGroupMatchingKey("android.media.STREAM_DEVICES_CHANGED_ACTION", String.valueOf(i));
        if (isStreamMute(i)) {
            intent.putExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
        } else {
            intent.putExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", i2);
        }
        intent.putExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", i3);
        intent.putExtra("android.media.EXTRA_VOLUME_SHOW_UI", (i4 & 1) != 0);
        sendBroadcastToAll(intent, makeBasic.toBundle());
    }

    public boolean isRingtoneMode() {
        return this.mMode.get() == 1;
    }

    public final void safeMediaVolumeFromVolumeMonitor(String str) {
        try {
            checkModifyPhoneStateOrRoutingPermission();
            sVolumeLogger.enqueue(new EventLogger.StringEvent("CSD warning " + str).printLog("AS.AudioService"));
            notifyVibrationForSafeMediaPopup();
            if (AudioParameter.VALUES_VM_CSD_100_WARNING.contains(str)) {
                this.mVolumeController.postDisplaySafeVolumeWarning(1073742848);
            } else if ("12".equals(str)) {
                this.mSoundDoseHelper.enforceSafeMediaVolume("safeMediaVolumeFromVolumeMonitor");
                this.mVolumeController.postDisplaySafeVolumeWarning(1024);
            }
        } catch (SecurityException unused) {
        }
    }

    public void nativeEvent(String str, String str2, int i) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING permission");
        }
        Log.i("AS.AudioService", "nativeEvent() action:" + str + ", key:" + str2 + ", value:" + i);
        Intent intent = new Intent(str);
        intent.putExtra(str2, i);
        sendBroadcastToAll(intent, null);
    }

    public int getModeInternal() {
        return this.mMode.get();
    }

    public boolean isBluetoothDualModeActive() {
        boolean isDualModeActive = this.mDeviceBroker.isDualModeActive();
        Log.d("AS.AudioService", "isBluetoothDualModeActive=" + isDualModeActive);
        return isDualModeActive;
    }

    public final boolean shouldSkipDeviceAlias(int i, boolean z) {
        return (i == 128 && !z) || (i == 536870914 && isLeBroadcastWithoutLeDevice());
    }

    public final void applyDeviceAlias(int i, int i2, DeviceAliasManager.DeviceAliasRunner deviceAliasRunner) {
        boolean z = false;
        if (i == 128 || AudioSystem.isBluetoothLeOutDevice(i)) {
            boolean isBluetoothDualModeActive = isBluetoothDualModeActive();
            if (AudioSystem.isBluetoothLeOutDevice(i) && !isBluetoothDualModeActive) {
                z = true;
            }
            if (shouldSkipDeviceAlias(i, isBluetoothDualModeActive)) {
                deviceAliasRunner = new DeviceAliasManager.DeviceAliasRunner() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda26
                    @Override // com.samsung.android.server.audio.DeviceAliasManager.DeviceAliasRunner
                    public final void run(int i3) {
                        AudioService.lambda$applyDeviceAlias$23(i3);
                    }
                };
            }
        }
        this.mDeviceAliasManager.apply(i, i2, deviceAliasRunner, z);
    }

    public final boolean isExemptedPackage(String str) {
        return "com.samsung.android.secsoundpicker".equals(str);
    }

    public final void updateReceiverSupported() {
        boolean z = false;
        for (AudioDeviceInfo audioDeviceInfo : AudioManager.getDevicesStatic(2)) {
            if (audioDeviceInfo.getType() == 1) {
                Log.i("AS.AudioService", "updateReceiverSupported RCV");
                z = true;
            }
        }
        this.mDeviceBroker.updateReceiverSupported(z);
    }

    public String getPackageNameModeOwner() {
        SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
        return audioModeOwnerHandler != null ? audioModeOwnerHandler.getPackage() : "";
    }

    public final boolean hasCallSetupParameters(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        AudioParameter audioParameter = new AudioParameter(str);
        return (Rune.SEC_AUDIO_SCREEN_CALL && audioParameter.hasKey("l_screen_call")) || audioParameter.hasKey("l_call_translation_mode");
    }

    public void resetBtScoOnByApp() {
        if (this.mBtScoOnByApp) {
            this.mBtScoOnByApp = false;
            Log.i("AS.AudioService", "resetBtScoOnByApp");
        }
    }

    public boolean isBikeMode() {
        return this.mExt.isBikeMode();
    }

    public RecordingActivityMonitor getRecordMonitor() {
        return this.mRecordMonitor;
    }

    public void resetRingerMode() {
        setRingerMode(2, "AS.AudioService.performSoftReset", false);
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        for (int i = 0; i < numStreamTypes; i++) {
            VolumeStreamState volumeStreamState = this.mStreamStates[i];
            if (mStreamVolumeAlias[i] != 3 || i == 3) {
                synchronized (volumeStreamState) {
                    for (int i2 = 0; i2 < volumeStreamState.mIndexMap.size(); i2++) {
                        int keyAt = volumeStreamState.mIndexMap.keyAt(i2);
                        int i3 = AudioSystem.DEFAULT_STREAM_VOLUME[i];
                        if (i == 3 && (67109004 & keyAt) != 0) {
                            i3 = 8;
                        }
                        if (i == 3 && (32768 & keyAt) != 0) {
                            i3 = 15;
                        }
                        volumeStreamState.mIndexMap.put(keyAt, i3 * 10);
                        volumeStreamState.applyDeviceVolume_syncVSS(keyAt);
                        for (int i4 = numStreamTypes - 1; i4 >= 0; i4--) {
                            if (i4 != volumeStreamState.mStreamType && mStreamVolumeAlias[i4] == volumeStreamState.mStreamType) {
                                this.mStreamStates[i4].applyDeviceVolume_syncVSS(getDeviceForStream(i4));
                            }
                        }
                        sendMsg(this.mAudioHandler, 1, 2, keyAt, 0, volumeStreamState, 500);
                    }
                }
            }
        }
    }

    public void checkAndPostSetAvrcpAbsoluteVolumeIndex(int i) {
        if (this.mAvrcpAbsVolSupported) {
            this.mDeviceBroker.postSetAvrcpAbsoluteVolumeIndex(i);
        }
    }

    public final void sendSetDeviceAbsoluteVolume(int i) {
        for (int i2 : this.AVC_AFFECTED_STREAMS) {
            sendMsg(this.mAudioHandler, 0, 2, i, 0, this.mStreamStates[i2], 0);
        }
    }

    public boolean isBluetoothCastState() {
        return this.mIsBluetoothCastState;
    }

    public void setBluetoothCastState(boolean z) {
        this.mIsBluetoothCastState = z;
    }

    public void setLeBroadcasting(boolean z) {
        this.mIsLeBroadCasting = z;
        sendMsg(this.mAudioHandler, 2775, 0, z ? 1 : 0, 0, null, 0);
    }

    public final void setStreamMute(int i, boolean z) {
        synchronized (this.mSettingsLock) {
            this.mStreamStates[i].mute(z, "setLeBroadcasting");
        }
    }

    public final boolean isLeBroadcastWithoutLeDevice() {
        BluetoothLeAudio leAudio;
        return this.mIsLeBroadCasting && (leAudio = this.mDeviceBroker.getLeAudio()) != null && leAudio.getConnectedDevices().size() == 0;
    }

    public final void setMultiSoundOn(boolean z) {
        sendMsg(this.mAudioHandler, 2763, 2, 0, 1, Boolean.valueOf(z), 0);
    }

    public final boolean volumeAdjustmentAllowedByLeBroadcast(int i, int i2) {
        if (i != 536870914) {
            return true;
        }
        boolean z = false;
        if (i2 == 1 || i2 == 5) {
            return false;
        }
        if ((i2 == 3 || i2 == 11) && isLeBroadcastWithoutLeDevice()) {
            z = true;
        }
        return !z;
    }

    public void setMicInputControlMode(int i) {
        this.mMicModeManager.setMicInputControlMode(i);
    }

    public int getMicModeType() {
        return this.mMicModeManager.getMicModeType();
    }

    public void setCommunicationDevice(int i) {
        this.mMicModeManager.setCommunicationDevice(i);
    }

    public boolean isConnectedAndroidAuto() {
        Log.d("AS.AudioService", "[Android Auto] isConnectedAndroidAuto = " + this.mConnectedAndroidAuto);
        return this.mConnectedAndroidAuto;
    }

    public boolean queryForState() {
        Cursor query = this.mContext.getContentResolver().query(this.PROJECTION_HOST_URI, new String[]{"CarConnectionState"}, null, null, null);
        if (query == null) {
            Log.w("AS.AudioService", "[Android Auto] Null response from content provider when checking connection to the car, treating as disconnected");
            return false;
        }
        if (query.getCount() > 0) {
            if (query.moveToFirst()) {
                int columnIndex = query.getColumnIndex("CarConnectionState");
                if (columnIndex < 0) {
                    Log.w("AS.AudioService", "[Android Auto] Connection to car response is missing the connection type, treating as disconnected");
                } else if (query.getInt(columnIndex) == 0) {
                    Log.i("AS.AudioService", "[Android Auto] disconnected");
                } else {
                    Log.i("AS.AudioService", "[Android Auto] connected");
                    query.close();
                    return true;
                }
            } else {
                Log.w("AS.AudioService", "[Android Auto] Connection to car response is empty, treating as disconnected");
            }
        } else {
            Log.w("AS.AudioService", "[Android Auto] Connection Count is 0, treating as disconnected");
        }
        query.close();
        return false;
    }

    public final void setSoundCraftEnable(BluetoothDevice bluetoothDevice) {
        this.mSoundCraftManager.setSoundCraftEnable(bluetoothDevice);
    }

    public final void setVolumeEffectOn(boolean z) {
        this.mIsVolumeEffectOn = z;
        this.mSoundDoseHelper.initSafeMediaVolumeIndex(z);
        if (this.mSoundDoseHelper.getSafeMediaVolumeState() == 3 && this.mIsVolumeEffectOn) {
            setVolumeLevelToEarShock();
        }
        if (this.mVolumeLimitOn) {
            setVolumeLevelToLimit("android");
        }
    }

    public void showReduceVolumeToast() {
        final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light);
        this.mAudioHandler.post(new Runnable() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                AudioService.lambda$showReduceVolumeToast$24(contextThemeWrapper);
            }
        });
    }

    public static /* synthetic */ void lambda$showReduceVolumeToast$24(Context context) {
        Toast.makeText(context, 17042578, 0).show();
    }

    public final void setVolumeLevelToEarShock() {
        int earProtectLimit = getEarProtectLimit() - 1;
        int deviceForStream = getDeviceForStream(3);
        int index = this.mStreamStates[3].getIndex(deviceForStream);
        int i = earProtectLimit * 10;
        if (index <= i || !this.mSoundDoseHelper.checkSafeMediaVolume(3, index, deviceForStream)) {
            return;
        }
        showReduceVolumeToast();
        setStreamVolume(3, i, 1048576, "android");
    }

    public int getEarProtectLimit() {
        if (this.mIsVolumeEffectOn) {
            return 7;
        }
        return Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3 ? 8 : 10;
    }

    public final int getVolumeLimitValue() {
        int i;
        if (this.mIsVolumeEffectOn && (i = (this.mVolumeLimitValue - 10) + 1) >= 0 && i < 7) {
            Log.d("AS.AudioService", "volume limit for effect change to " + VOLUME_LIMIT_INDEX_EFFECT_ON[i]);
            return VOLUME_LIMIT_INDEX_EFFECT_ON[i];
        }
        return Math.min(this.mVolumeLimitValue, 14);
    }
}
