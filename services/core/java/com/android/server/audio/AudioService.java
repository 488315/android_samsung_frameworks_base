package com.android.server.audio;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UidObserver;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.AttributionSource;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.SensorPrivacyManager;
import android.hardware.SensorPrivacyManagerInternal;
import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.hdmi.HdmiAudioSystemClient;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiPlaybackClient;
import android.hardware.hdmi.HdmiTvClient;
import android.hardware.input.InputManager;
import android.hidl.manager.V1_0.IServiceManager;
import android.media.AudioAttributes;
import android.media.AudioDescriptor;
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
import android.media.FadeManagerConfiguration;
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
import android.media.ILoudnessCodecUpdatesDispatcher;
import android.media.IMuteAwaitConnectionCallback;
import android.media.IPlaybackConfigDispatcher;
import android.media.IPreferredMixerAttributesDispatcher;
import android.media.IRecordingConfigDispatcher;
import android.media.IRingtonePlayer;
import android.media.ISoundDose;
import android.media.ISpatializerCallback;
import android.media.ISpatializerHeadToSoundStagePoseCallback;
import android.media.ISpatializerHeadTrackerAvailableCallback;
import android.media.ISpatializerHeadTrackingModeCallback;
import android.media.ISpatializerOutputCallback;
import android.media.IStrategyNonDefaultDevicesDispatcher;
import android.media.IStrategyPreferredDevicesDispatcher;
import android.media.IStreamAliasingDispatcher;
import android.media.IVolumeController;
import android.media.LoudnessCodecInfo;
import android.media.MediaMetrics;
import android.media.PlayerBase;
import android.media.SoundDoseRecord;
import android.media.Utils;
import android.media.VolumeInfo;
import android.media.VolumePolicy;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.media.audiopolicy.AudioPolicyConfig;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.audiopolicy.AudioVolumeGroup;
import android.media.audiopolicy.Flags;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.permission.ClearCallingIdentityContext;
import android.media.permission.SafeCloseable;
import android.media.projection.IMediaProjection;
import android.media.projection.IMediaProjectionCallback;
import android.media.projection.IMediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.HwBinder;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.PermissionEnforcer;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceDebugInfo;
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
import android.provider.Settings;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.ContextThemeWrapper;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.UiModeManagerService$Stub$$ExternalSyntheticLambda3;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceBroker;
import com.android.server.audio.AudioDeviceInventory;
import com.android.server.audio.AudioService;
import com.android.server.audio.AudioSystemAdapter;
import com.android.server.audio.CurrentDeviceManager;
import com.android.server.audio.LoudnessCodecHelper;
import com.android.server.audio.MusicFxHelper;
import com.android.server.audio.PlaybackActivityMonitor;
import com.android.server.audio.RecordingActivityMonitor;
import com.android.server.audio.RotationHelper;
import com.android.server.audio.SoundDoseHelper;
import com.android.server.audio.SoundEffectsHelper;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.media.MediaSessionService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.android.server.utils.EventLogger;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.Task;
import com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda2;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.knox.custom.CustomDeviceManagerProxy;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.media.AudioFxHelper;
import com.samsung.android.media.AudioParameter;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.media.audiofx.SemVolumeMonitor;
import com.samsung.android.server.audio.AppCategorizer;
import com.samsung.android.server.audio.AudioEvents$MicrophoneEvent;
import com.samsung.android.server.audio.AudioExecutor;
import com.samsung.android.server.audio.AudioGameManager;
import com.samsung.android.server.audio.AudioHqmHelper;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.CoverHelper;
import com.samsung.android.server.audio.DesktopModeHelper;
import com.samsung.android.server.audio.DeviceAliasManager;
import com.samsung.android.server.audio.DualA2dpVolumeManager;
import com.samsung.android.server.audio.FactoryUtils;
import com.samsung.android.server.audio.FrequentWorker;
import com.samsung.android.server.audio.GoodCatchManager;
import com.samsung.android.server.audio.LiveTranslatorManager;
import com.samsung.android.server.audio.MicModeManager;
import com.samsung.android.server.audio.MicModeType;
import com.samsung.android.server.audio.MultiSoundManager;
import com.samsung.android.server.audio.MultiSoundManager$$ExternalSyntheticLambda0;
import com.samsung.android.server.audio.OmcRingtoneManager;
import com.samsung.android.server.audio.PackageListHelper;
import com.samsung.android.server.audio.ScreenSharingHelper;
import com.samsung.android.server.audio.SemAudioServiceInternal;
import com.samsung.android.server.audio.SensorHandleThread;
import com.samsung.android.server.audio.SensorHandleThread$$ExternalSyntheticLambda0;
import com.samsung.android.server.audio.SoundAppPolicyManager;
import com.samsung.android.server.audio.SoundAppPolicyManager$$ExternalSyntheticLambda0;
import com.samsung.android.server.audio.VolumeMonitorService;
import com.samsung.android.server.audio.utils.AudioStreamUtils;
import com.samsung.android.server.audio.utils.AudioUtils;
import com.samsung.android.server.audio.utils.BtUtils;
import com.samsung.android.server.audio.utils.CoreFxUtils;
import com.samsung.android.server.audio.utils.KaraokeUtils;
import com.samsung.android.server.audio.utils.PlatformTypeUtils;
import com.samsung.android.server.audio.utils.PlaybackUtils;
import com.samsung.android.server.audio.utils.SoundAliveUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioService extends IAudioService.Stub implements AccessibilityManager.TouchExplorationStateChangeListener, AccessibilityManager.AccessibilityServicesStateChangeListener, AudioSystemAdapter.OnRoutingUpdatedListener, AudioSystemAdapter.OnVolRangeInitRequestListener {
    public static final int BECOMING_NOISY_DELAY_MS = 500;
    public static final Set DEVICE_MEDIA_UNMUTED_ON_PLUG_SET;
    public static final String[] EMPTY_PACKAGE;
    public static final String[] RINGER_MODE_NAMES;
    public static final String[] SYSTEM_PACKAGE;
    public static final int[] VOLUME_LIMIT_INDEX_EFFECT_ON;
    public static final Set mAppCastingDevice;
    public static AudioDeviceBroker.BtDeviceChangedData mBtDeviceChangedData;
    public static int[] mStreamVolumeAlias;
    public static final EventLogger sAppCastingLogger;
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
    public final Uri PROJECTION_HOST_URI;
    public final int[] STREAM_VOLUME_ALIAS_DEFAULT;
    public final int[] STREAM_VOLUME_ALIAS_NONE;
    public final int[] STREAM_VOLUME_ALIAS_TELEVISION;
    public final int[] STREAM_VOLUME_ALIAS_VOICE;
    public final Set mAbsVolumeMultiModeCaseDevices;
    public final Map mAbsoluteVolumeDeviceInfoMap;
    public int[] mAccessibilityServiceUids;
    public final Object mAccessibilityServiceUidsLock;
    public int[] mActiveAssistantServiceUids;
    public final ActivityManagerInternal mActivityManagerInternal;
    public boolean mAdjustMediaVolumeOnly;
    public final AlarmManager mAlarmManager;
    public String mAppMode;
    public final AppOpsManager mAppOps;
    public final SparseIntArray mAppVolumeFromSoundAssistant;
    public final ArraySet mAssistantUids;
    public final PowerManager.WakeLock mAudioEventWakeLock;
    public AudioGameManager mAudioGameManager;
    public AudioHandler mAudioHandler;
    public final HashMap mAudioPolicies;
    public final DefaultAudioPolicyFacade mAudioPolicy;
    public int mAudioPolicyCounter;
    public final HashMap mAudioServerStateListeners;
    public final AudioSystemAdapter mAudioSystem;
    public final AnonymousClass1 mAudioSystemCallback;
    public final AudioVolumeGroupHelperBase mAudioVolumeGroupHelper;
    public volatile boolean mAvrcpAbsVolSupported;
    public final HandlerThread mBroadcastHandlerThread;
    public String mBtScoDeviceInfo;
    public boolean mBtScoOnByApp;
    public final HashMap mCachedAbsVolDrivingStreams;
    public final Object mCachedAbsVolDrivingStreamsLock;
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
    public final AnonymousClass3 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public boolean mDockAudioMediaEnabled;
    public int mDockState;
    public String mDvDhaparam;
    public final AnonymousClass10 mDynPolicyCallback;
    public final EventLogger mDynPolicyLogger;
    public String mEnabledSurroundFormats;
    public int mEncodedSurroundMode;
    public final ArrayList mEventReceivers;
    public final AudioServiceExt mExt;
    public IAudioPolicyCallback mExtVolumeController;
    public final Object mExtVolumeControllerLock;
    public SetModeDeathHandler mExternalVoipModeOwner;
    public final AnonymousClass13 mFMRadioRecordingChecker;
    public final Set mFixedVolumeDevices;
    public AnonymousClass9 mForceControlStreamClient;
    public final Object mForceControlStreamLock;
    public int mForceSpeaker;
    public int mForcedUseForFMRadio;
    public int mForegroundUid;
    public final Set mFullVolumeDevices;
    public GoodCatchManager mGoodCatchManager;
    public String mHAC;
    public final HardeningEnforcer mHardeningEnforcer;
    public final boolean mHasSpatializerEffect;
    public final boolean mHasVibrator;
    public HdmiAudioSystemClient mHdmiAudioSystemClient;
    public boolean mHdmiCecVolumeControlEnabled;
    public final Object mHdmiClientLock;
    public final MyHdmiControlStatusChangeListenerCallback mHdmiControlStatusChangeListenerCallback;
    public HdmiControlManager mHdmiManager;
    public HdmiPlaybackClient mHdmiPlaybackClient;
    public boolean mHdmiSystemAudioSupported;
    public HdmiTvClient mHdmiTvClient;
    public int mHeadsetOnlyStream;
    public boolean mHomeSoundEffectEnabled;
    public boolean mIgnoreDucking;
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
    public String mLiveTranslatorAllowApps;
    public boolean mLiveTranslatorDuringCall;
    public final LiveTranslatorManager mLiveTranslatorManager;
    public String mLoopbackState;
    public final LoudnessCodecHelper mLoudnessCodecHelper;
    public long mLoweredFromNormalToVibrateTime;
    public final AtomicBoolean mMasterMute;
    public final MediaFocusControl mMediaFocusControl;
    public final AtomicBoolean mMediaPlaybackActive;
    public volatile MediaSessionManager mMediaSessionManager;
    public MediaSessionService.MediaSessionServiceInternal mMediaSessionServiceInternal;
    public int mMediaVolumeStepIndex;
    public final MicModeManager mMicModeManager;
    public boolean mMicMuteFromApi;
    public boolean mMicMuteFromPrivacyToggle;
    public boolean mMicMuteFromRestrictions;
    public boolean mMicMuteFromSwitch;
    public boolean mMicMuteFromSystemCached;
    public final AtomicInteger mMode;
    public final RemoteCallbackList mModeDispatchers;
    public final EventLogger mModeLogger;
    public final boolean mMonitorRotation;
    public final AnonymousClass11 mMultiSoundInterface;
    public final MultiSoundManager mMultiSoundManager;
    public final MusicFxHelper mMusicFxHelper;
    public int mMuteAffectedStreams;
    public final RemoteCallbackList mMuteAwaitConnectionDispatchers;
    public final Object mMuteAwaitConnectionLock;
    public int mMuteIntervalMs;
    public boolean mMuteMediaByVibrateOrSilentMode;
    public long mMuteTime;
    public int[] mMutedUsagesAwaitingConnection;
    public AudioDeviceAttributes mMutingExpectedDevice;
    public final MyHdmiCecVolumeControlFeatureListener mMyHdmiCecVolumeControlFeatureListener;
    public boolean mNavigationRepeatSoundEffectsEnabled;
    public NotificationManager mNm;
    public boolean mNotifAliasRing;
    public PackageListHelper mPackageListHelper;
    public final PackageManager mPackageManager;
    public String mPhoneType;
    public int mPlatformType;
    public final AnonymousClass5 mPlaybackActivityMonitor;
    public final PlaybackActivityMonitor mPlaybackMonitor;
    public final RemoteCallbackList mPrefMixerAttrDispatcher;
    public int mPrevRingerMode;
    public int mPrevVolDirection;
    public int mPrimaryAssistantUid;
    public IMediaProjectionManager mProjectionService;
    public final SamsungBroadcastReceiver mReceiver;
    public final AnonymousClass11 mRecordEventChecker;
    public final RecordingActivityMonitor mRecordMonitor;
    public boolean mRemoteMic;
    public final RestorableParameters mRestorableParameters;
    public int mRingerMode;
    public int mRingerModeAffectedStreams;
    public final boolean mRingerModeAffectsAlarm;
    public AudioManagerInternal.RingerModeDelegate mRingerModeDelegate;
    public int mRingerModeExternal;
    public volatile IRingtonePlayer mRingtonePlayer;
    public final ArrayList mRmtSbmxFullVolDeathHandlers;
    public int mRmtSbmxFullVolRefCount;
    public RoleObserver mRoleObserver;
    public boolean mRttEnabled;
    public final SamsungBroadcastReceiver mSamsungReceiver;
    public int mSavedSpeakerMediaIndex;
    public ScreenSharingHelper mScreenSharingHelper;
    public final SensorPrivacyManagerInternal mSensorPrivacyManagerInternal;
    public SensorHandleThread mSensorThread;
    public final ArrayList mSetModeDeathHandlers;
    public final AudioSettingsHelper mSettingHelper;
    public final SettingsAdapter mSettings;
    public final Object mSettingsLock;
    public final SoundEffectsHelper mSfxHelper;
    public SoundAppPolicyManager mSoundAppPolicyManager;
    public final SoundDoseHelper mSoundDoseHelper;
    public final SpatializerHelper mSpatializerHelper;
    public final RemoteCallbackList mStreamAliasingDispatchers;
    public VolumeStreamState[] mStreamStates;
    public final AnonymousClass4 mSubscriptionChangedListener;
    public int[] mSupportedSystemUsages;
    public final Object mSupportedSystemUsagesLock;
    public final boolean mSupportsMicPrivacyToggle;
    public boolean mSurroundModeChanged;
    public boolean mSystemReady;
    public final SystemServerAdapter mSystemServer;
    public final boolean mUseFixedVolume;
    public final boolean mUseVolumeGroupAliases;
    public final UserManagerInternal mUserManagerInternal;
    public int mUserMutableStreams;
    public final AnonymousClass11 mUserRestrictionsListener;
    public boolean mUserSelectedVolumeControlStream;
    public boolean mUserSwitchedReceived;
    public int mVibrateSetting;
    public final Vibrator mVibrator;
    public final AtomicBoolean mVoicePlaybackActive;
    public final AnonymousClass6 mVoiceRecordingActivityMonitor;
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
    public static final int[] MAX_STREAM_VOLUME = {5, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
    public static final int[] MIN_STREAM_VOLUME = {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
    public static final int[] STREAM_VOLUME_OPS = {34, 36, 35, 36, 37, 38, 39, 36, 36, 36, 64, 36};
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);
    public static final byte[] DEFAULT_ARC_AUDIO_DESCRIPTOR = {9, Byte.MAX_VALUE, 7};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.audio.AudioService$11, reason: invalid class name */
    public final class AnonymousClass11 implements UserManagerInternal.UserRestrictionsListener {
        public /* synthetic */ AnonymousClass11() {
        }

        public boolean isSmartViewEnabled() {
            ScreenSharingHelper screenSharingHelper = AudioService.this.mScreenSharingHelper;
            return (screenSharingHelper.mIsSupportDisplayVolumeControl || screenSharingHelper.mIsDLNAEnabled) && !screenSharingHelper.mIsAppCasting;
        }

        @Override // com.android.server.pm.UserManagerInternal.UserRestrictionsListener
        public void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) {
            boolean z = bundle2.getBoolean("no_unmute_microphone");
            boolean z2 = bundle.getBoolean("no_unmute_microphone");
            AudioService audioService = AudioService.this;
            if (z != z2) {
                audioService.mMicMuteFromRestrictions = z2;
                audioService.setMicrophoneMuteNoCallerCheck(i);
            }
            boolean z3 = true;
            boolean z4 = bundle2.getBoolean("no_adjust_volume") || bundle2.getBoolean("disallow_unmute_device");
            if (!bundle.getBoolean("no_adjust_volume") && !bundle.getBoolean("disallow_unmute_device")) {
                z3 = false;
            }
            if (z4 != z3) {
                int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                audioService.setMasterMuteInternalNoCallerCheck(0, i, "onUserRestrictionsChanged", z3);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.audio.AudioService$13, reason: invalid class name */
    public final class AnonymousClass13 extends FrequentWorker {
        @Override // com.samsung.android.server.audio.FrequentWorker
        public final Object func() {
            return Boolean.valueOf("true".equals(SemAudioSystem.getPolicyParameters("l_fmradio_record_active")));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.audio.AudioService$9, reason: invalid class name */
    public final class AnonymousClass9 implements IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId = 0;
        public Object val$controller;

        public AnonymousClass9(IVolumeController iVolumeController) {
            this.val$controller = iVolumeController;
        }

        public AnonymousClass9(IBinder iBinder) {
            if (iBinder != null) {
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    Log.w("AS.AudioService", "ForceControlStreamClient() could not link to " + iBinder + " binder death");
                    iBinder = null;
                }
            }
            this.val$controller = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            switch (this.$r8$classId) {
                case 0:
                    if (AudioService.this.mVolumeController.isSameBinder((IVolumeController) this.val$controller)) {
                        AudioService.this.mVolumeController.removeController((IVolumeController) this.val$controller);
                        return;
                    }
                    return;
                default:
                    synchronized (AudioService.this.mForceControlStreamLock) {
                        try {
                            Log.w("AS.AudioService", "SCO client died");
                            AudioService audioService = AudioService.this;
                            if (audioService.mForceControlStreamClient != this) {
                                Log.w("AS.AudioService", "unregistered control stream client died");
                            } else {
                                audioService.mForceControlStreamClient = null;
                                audioService.mVolumeControlStream = -1;
                                audioService.mUserSelectedVolumeControlStream = false;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AbsoluteVolumeDeviceInfo {
        public final IAudioDeviceVolumeDispatcher mCallback;
        public final AudioDeviceAttributes mDevice;
        public final int mDeviceVolumeBehavior;
        public final boolean mHandlesVolumeAdjustment;
        public final List mVolumeInfos;

        /* renamed from: -$$Nest$mgetMatchingVolumeInfoForStream, reason: not valid java name */
        public static VolumeInfo m282$$Nest$mgetMatchingVolumeInfoForStream(AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo, final int i) {
            for (VolumeInfo volumeInfo : absoluteVolumeDeviceInfo.mVolumeInfos) {
                boolean z = false;
                boolean z2 = volumeInfo.hasStreamType() && volumeInfo.getStreamType() == i;
                if (volumeInfo.hasVolumeGroup() && Arrays.stream(volumeInfo.getVolumeGroup().getLegacyStreamTypes()).anyMatch(new IntPredicate() { // from class: com.android.server.audio.AudioService$AbsoluteVolumeDeviceInfo$$ExternalSyntheticLambda0
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i2) {
                        return i2 == i;
                    }
                })) {
                    z = true;
                }
                if (z2 || z) {
                    return volumeInfo;
                }
            }
            return null;
        }

        public AbsoluteVolumeDeviceInfo(AudioDeviceAttributes audioDeviceAttributes, List list, IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, boolean z, int i) {
            this.mDevice = audioDeviceAttributes;
            this.mVolumeInfos = list;
            this.mCallback = iAudioDeviceVolumeDispatcher;
            this.mHandlesVolumeAdjustment = z;
            this.mDeviceVolumeBehavior = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AsdProxy implements IBinder.DeathRecipient {
        public final IAudioServerStateDispatcher mAsd;

        public AsdProxy(IAudioServerStateDispatcher iAudioServerStateDispatcher) {
            this.mAsd = iAudioServerStateDispatcher;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (AudioService.this.mAudioServerStateListeners) {
                AudioService.this.mAudioServerStateListeners.remove(this.mAsd.asBinder());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioDeviceArray {
        public final String[] mDeviceAddresses;
        public final int[] mDeviceTypes;

        public AudioDeviceArray(int[] iArr, String[] strArr) {
            this.mDeviceTypes = iArr;
            this.mDeviceAddresses = strArr;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioHandler extends Handler {
        public AudioHandler() {
        }

        public static void onNotifyVolumeEvent(IAudioPolicyCallback iAudioPolicyCallback, int i) {
            try {
                iAudioPolicyCallback.notifyVolumeAdjust(i);
            } catch (Exception unused) {
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            MicModeManager micModeManager;
            String string;
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
                VolumeGroupState.m283$$Nest$mpersistVolumeGroup((VolumeGroupState) message.obj, message.arg1);
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
            if (i == 1101) {
                MusicFxHelper musicFxHelper = AudioService.this.mMusicFxHelper;
                musicFxHelper.getClass();
                if (message.what != 1101) {
                    Log.e("AS.MusicFxHelper", "Unexpected msg to handle in MusicFxHelper: " + message.what);
                    return;
                }
                Log.w("AS.MusicFxHelper", " handle MSG_EFFECT_CLIENT_GONE");
                int i4 = message.arg1;
                synchronized (musicFxHelper.mClientUidMapLock) {
                    try {
                        Log.d("AS.MusicFxHelper", "handle MSG_EFFECT_CLIENT_GONE uid: " + i4 + " mapSize: " + musicFxHelper.mClientUidSessionMap.size());
                        MusicFxHelper.PackageSessions packageSessions = (MusicFxHelper.PackageSessions) musicFxHelper.mClientUidSessionMap.get(i4);
                        if (packageSessions != null) {
                            Log.i("AS.MusicFxHelper", "UID " + i4 + " gone, closing all sessions");
                            Iterator it = ((ArrayList) packageSessions.mSessions).iterator();
                            while (it.hasNext()) {
                                Integer num = (Integer) it.next();
                                Intent intent = new Intent("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION");
                                intent.putExtra("android.media.extra.PACKAGE_NAME", packageSessions.mPackageName);
                                intent.putExtra("android.media.extra.AUDIO_SESSION", num);
                                intent.addFlags(32);
                                intent.setPackage("com.android.musicfx");
                                musicFxHelper.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                            }
                            musicFxHelper.mClientUidSessionMap.remove(i4);
                        }
                    } finally {
                    }
                }
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
            if (i == 54) {
                AudioService.m269$$Nest$monConfigurationChanged(AudioService.this);
                return;
            }
            if (i == 55) {
                SystemServerAdapter systemServerAdapter = AudioService.this.mSystemServer;
                z = message.arg1 == 1;
                systemServerAdapter.getClass();
                Intent intent2 = new Intent("android.media.MASTER_MUTE_CHANGED_ACTION");
                intent2.putExtra("android.media.EXTRA_MASTER_VOLUME_MUTED", z);
                intent2.addFlags(872415232);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    systemServerAdapter.mContext.sendStickyBroadcastAsUser(intent2, UserHandle.ALL);
                    return;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            FocusRequester focusRequester = null;
            switch (i) {
                case 18:
                    AudioService.m278$$Nest$monUnmuteStreamOnSingleVolDevice(AudioService.this, message.arg1, message.arg2);
                    return;
                case 19:
                    AudioService.m271$$Nest$monDynPolicyMixStateUpdate(message.arg1, AudioService.this, (String) message.obj);
                    return;
                case 20:
                    AudioService.this.onIndicateSystemReady();
                    return;
                case 21:
                    AudioService.m266$$Nest$monAccessoryPlugMediaUnmute(AudioService.this, message.arg1);
                    return;
                case 22:
                    onNotifyVolumeEvent((IAudioPolicyCallback) message.obj, message.arg1);
                    return;
                case 23:
                    AudioService.m270$$Nest$monDispatchAudioServerStateChange(AudioService.this, message.arg1 == 1);
                    return;
                case 24:
                    AudioService.m272$$Nest$monEnableSurroundFormats(AudioService.this, (ArrayList) message.obj);
                    return;
                case 25:
                    AudioService audioService = AudioService.this;
                    int i5 = AudioService.BECOMING_NOISY_DELAY_MS;
                    audioService.setRingerModeInt(audioService.getRingerModeInternal(), false);
                    return;
                case 26:
                    AudioService.m277$$Nest$monSetVolumeIndexOnDevice(AudioService.this, (DeviceVolumeUpdate) message.obj);
                    if ("muteMediaStreamOfSpeaker".equals(((DeviceVolumeUpdate) message.obj).mCaller)) {
                        AudioService.this.sendVolumeUpdate(3, message.arg1, message.arg2, 0, 2);
                        return;
                    }
                    return;
                case 27:
                    AudioService.m274$$Nest$monObserveDevicesForAllStreams(AudioService.this, message.arg1);
                    return;
                case 28:
                    AudioService audioService2 = AudioService.this;
                    int i6 = message.arg1;
                    AudioService.m268$$Nest$monCheckVolumeCecOnHdmiConnection(audioService2, i6);
                    return;
                case 29:
                    AudioService.m275$$Nest$monPlaybackConfigChange(AudioService.this, (List) message.obj);
                    return;
                case 30:
                    AudioService.this.mSystemServer.mContext.sendBroadcastAsUser(new Intent("android.media.action.MICROPHONE_MUTE_CHANGED").setFlags(1073741824), UserHandle.ALL);
                    return;
                case 31:
                    synchronized (AudioService.this.mDeviceBroker.mSetModeLock) {
                        try {
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
                            setModeDeathHandler.mPlaybackActive = AudioService.this.isPlaybackActiveForUid(setModeDeathHandler.mUid);
                            setModeDeathHandler.mRecordingActive = AudioService.this.mRecordMonitor.isRecordingActiveForUid(setModeDeathHandler.mUid);
                            if (isActive != setModeDeathHandler.isActive()) {
                                AudioService.this.onUpdateAudioMode(-1, Process.myPid(), AudioService.this.mContext.getPackageName(), false);
                            }
                            return;
                        } finally {
                        }
                    }
                case 32:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    Intent intent3 = (Intent) someArgs.arg1;
                    Bundle bundle = (Bundle) someArgs.arg2;
                    someArgs.recycle();
                    AudioService.this.sendBroadcastToAll(intent3.putExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", message.arg1).putExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", message.arg2), bundle);
                    if (!Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI || (micModeManager = AudioService.this.mMicModeManager) == null) {
                        return;
                    }
                    final Intent putExtra = intent3.putExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", message.arg1).putExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", message.arg2);
                    if (MicModeManager.AVAILABLE_STREAM_TYPES.stream().noneMatch(new Predicate() { // from class: com.samsung.android.server.audio.MicModeManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj2) {
                            return ((Integer) obj2).intValue() == putExtra.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                        }
                    })) {
                        return;
                    }
                    int intExtra = putExtra.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", 0);
                    int intExtra2 = putExtra.getIntExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", 0);
                    micModeManager.mCurCallDevice = intExtra2;
                    AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(intExtra, intExtra2, "streamDevicesChanged() prevDevice=", ", curDevice=", "MicModeManager");
                    micModeManager.updateAudioDevice();
                    return;
                case 33:
                    AudioService.m280$$Nest$monUpdateVolumeStatesForAudioDevice(message.arg1, AudioService.this, (String) message.obj);
                    return;
                case 34:
                    AudioService audioService3 = AudioService.this;
                    String str2 = (String) message.obj;
                    int i7 = AudioService.BECOMING_NOISY_DELAY_MS;
                    audioService3.onReinitVolumes(str2);
                    return;
                case 35:
                    AudioService.m279$$Nest$monUpdateAccessibilityServiceUids(AudioService.this);
                    return;
                case 36:
                    synchronized (AudioService.this.mDeviceBroker.mSetModeLock) {
                        AudioService.this.onUpdateAudioMode(message.arg1, message.arg2, (String) message.obj, false);
                    }
                    return;
                case 37:
                    AudioService audioService4 = AudioService.this;
                    List list = (List) message.obj;
                    int i8 = AudioService.BECOMING_NOISY_DELAY_MS;
                    audioService4.updateAudioModeHandlers(null, list);
                    audioService4.mDeviceBroker.updateCommunicationRouteClientsActivity(null, list);
                    return;
                case 38:
                    AudioService.this.mDeviceBroker.queueOnBluetoothActiveDeviceChanged((AudioDeviceBroker.BtDeviceChangedData) message.obj);
                    return;
                default:
                    switch (i) {
                        case 40:
                            AudioService audioService5 = AudioService.this;
                            int i9 = message.arg1;
                            int beginBroadcast = audioService5.mModeDispatchers.beginBroadcast();
                            for (int i10 = 0; i10 < beginBroadcast; i10++) {
                                try {
                                    audioService5.mModeDispatchers.getBroadcastItem(i10).dispatchAudioModeChanged(i9);
                                } catch (RemoteException unused) {
                                }
                            }
                            audioService5.mModeDispatchers.finishBroadcast();
                            return;
                        case 41:
                            AudioService.this.onRoutingUpdatedFromAudioThread();
                            return;
                        case 42:
                            AudioService.this.mSpatializerHelper.onInitSensors();
                            return;
                        default:
                            switch (i) {
                                case 44:
                                    AudioService.m267$$Nest$monAddAssistantServiceUids(AudioService.this, new int[]{message.arg1});
                                    return;
                                case 45:
                                    AudioService.m276$$Nest$monRemoveAssistantServiceUids(AudioService.this, new int[]{message.arg1});
                                    return;
                                case 46:
                                    AudioService audioService6 = AudioService.this;
                                    int i11 = AudioService.BECOMING_NOISY_DELAY_MS;
                                    audioService6.updateActiveAssistantServiceUids();
                                    return;
                                case 47:
                                    AudioService audioService7 = AudioService.this;
                                    AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) message.obj;
                                    int i12 = message.arg1;
                                    int i13 = AudioService.BECOMING_NOISY_DELAY_MS;
                                    audioService7.dispatchDeviceVolumeBehavior(audioDeviceAttributes, i12);
                                    return;
                                case 48:
                                    AudioService.this.mAudioSystem.setParameters((String) message.obj);
                                    return;
                                case 49:
                                    AudioService.this.mAudioSystem.setParameters((String) message.obj);
                                    return;
                                case 50:
                                    AudioService audioService8 = AudioService.this;
                                    audioService8.mSpatializerHelper.reset(audioService8.mHasSpatializerEffect);
                                    return;
                                case 51:
                                    AudioService.this.mPlaybackMonitor.ignorePlayerIId(message.arg1);
                                    return;
                                case 52:
                                    AudioService audioService9 = AudioService.this;
                                    Bundle data = message.getData();
                                    int i14 = message.arg1;
                                    int beginBroadcast2 = audioService9.mPrefMixerAttrDispatcher.beginBroadcast();
                                    AudioAttributes audioAttributes = (AudioAttributes) data.getParcelable("audio_attributes", AudioAttributes.class);
                                    AudioMixerAttributes audioMixerAttributes = (AudioMixerAttributes) data.getParcelable("audio_mixer_attributes", AudioMixerAttributes.class);
                                    for (int i15 = 0; i15 < beginBroadcast2; i15++) {
                                        try {
                                            audioService9.mPrefMixerAttrDispatcher.getBroadcastItem(i15).dispatchPrefMixerAttributesChanged(audioAttributes, i14, audioMixerAttributes);
                                        } catch (RemoteException e) {
                                            Log.e("AS.AudioService", "Can't call dispatchPrefMixerAttributesChanged() IPreferredMixerAttributesDispatcher " + audioService9.mPrefMixerAttrDispatcher.getBroadcastItem(i15).asBinder(), e);
                                        }
                                    }
                                    audioService9.mPrefMixerAttrDispatcher.finishBroadcast();
                                    return;
                                default:
                                    switch (i) {
                                        case 100:
                                            AudioService.this.mPlaybackMonitor.disableAudioForUid(message.arg2, message.arg1 == 1);
                                            AudioService.this.mAudioEventWakeLock.release();
                                            return;
                                        case 101:
                                            AudioService.m273$$Nest$monInitStreamsAndVolumes(AudioService.this);
                                            AudioService.this.mAudioEventWakeLock.release();
                                            return;
                                        case 102:
                                            AudioService audioService10 = AudioService.this;
                                            audioService10.mSpatializerHelper.init(audioService10.mHasSpatializerEffect);
                                            audioService10.mSpatializerHelper.setFeatureEnabled(audioService10.mHasSpatializerEffect);
                                            AudioService.this.mDeviceBroker.sendLMsgNoDelay(58, 2, null);
                                            AudioService.this.mAudioEventWakeLock.release();
                                            return;
                                        case 103:
                                            AudioService.this.onInitAdiDeviceStates();
                                            AudioService.this.mAudioEventWakeLock.release();
                                            return;
                                        default:
                                            switch (i) {
                                                case 1001:
                                                case 1002:
                                                case 1003:
                                                case 1004:
                                                case 1005:
                                                case 1006:
                                                case 1007:
                                                    AudioService.this.mSoundDoseHelper.handleMessage(message);
                                                    return;
                                                default:
                                                    if (i > 2758) {
                                                        AudioService audioService11 = AudioService.this;
                                                        int i16 = AudioService.BECOMING_NOISY_DELAY_MS;
                                                        audioService11.getClass();
                                                        switch (message.what) {
                                                            case 2759:
                                                                if (audioService11.mMode.get() == 3) {
                                                                    Log.i("AS.AudioService", "playSilentModeSound: skipping playSilentModeSound");
                                                                    return;
                                                                } else if (audioService11.mMode.get() == 1 && audioService11.isBluetoothScoOn()) {
                                                                    Log.i("AS.AudioService", "playSilentModeSound: skipping while inband ringtone is playing");
                                                                    return;
                                                                } else {
                                                                    audioService11.playSoundEffectVolume(AudioFxHelper.getPlaySoundTypeForSEP(101), -1.0f);
                                                                    return;
                                                                }
                                                            case 2760:
                                                                String str3 = (String) message.obj;
                                                                int i17 = message.arg1;
                                                                if (audioService11.mIsVibrate) {
                                                                    return;
                                                                }
                                                                try {
                                                                    if (audioService11.mHasVibrator && audioService11.mVibrator != null) {
                                                                        VibrationEffect semCreateHaptic = VibrationEffect.semCreateHaptic(HapticFeedbackConstants.semGetVibrationIndex(8), -1);
                                                                        StringBuilder sb = new StringBuilder("Notification (");
                                                                        sb.append(str3);
                                                                        sb.append(")");
                                                                        sb.append(i17 == 1 ? " (RingerMode)" : " (SafetyDialog)");
                                                                        audioService11.mVibrator.vibrate(1000, "android", semCreateHaptic, sb.toString(), new VibrationAttributes.Builder().semAddTag("VIBRATE_CALL").setUsage(33).build());
                                                                    }
                                                                } catch (Exception e2) {
                                                                    Log.i("AS.AudioService", "vibrateCall error", e2);
                                                                }
                                                                audioService11.mIsVibrate = true;
                                                                AudioService.sendMsg(audioService11.mAudioHandler, 2761, 1, i17, 0, str3, 600);
                                                                return;
                                                            case 2761:
                                                                audioService11.mIsVibrate = false;
                                                                return;
                                                            case 2762:
                                                                final int i18 = message.arg1;
                                                                final AudioServiceExt audioServiceExt = audioService11.mExt;
                                                                final Context context = audioService11.mContext;
                                                                audioServiceExt.getClass();
                                                                AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioServiceExt$$ExternalSyntheticLambda0
                                                                    @Override // java.lang.Runnable
                                                                    public final void run() {
                                                                        SoundAliveUtils.notifyDVFSToSoundAlive(context, i18, AudioServiceExt.this.mDvfsHelper.mIsScreenOn);
                                                                    }
                                                                });
                                                                return;
                                                            case 2763:
                                                                boolean booleanValue = ((Boolean) message.obj).booleanValue();
                                                                byte b = message.arg1 == 1;
                                                                byte b2 = message.arg2 == 1;
                                                                Intent intent4 = new Intent("android.intent.action.MULTISOUND_STATE_CHANGE");
                                                                AccessibilityManagerService$$ExternalSyntheticOutline0.m("MSG_SET_MULTI_DEVICE_SOUND_ON ", "AS.AudioService", booleanValue);
                                                                Settings.Global.putInt(audioService11.mContentResolver, "multisound_state", booleanValue ? 1 : 0);
                                                                if (!booleanValue) {
                                                                    MultiSoundManager multiSoundManager = audioService11.mMultiSoundManager;
                                                                    if (multiSoundManager.mEnabled) {
                                                                        Log.d("AS.MultiSoundManager", "disable");
                                                                        multiSoundManager.mEnabled = false;
                                                                        multiSoundManager.resetByEnableDisable();
                                                                        AudioService.this.mMultiSoundManager.mNm.cancel(1004);
                                                                    }
                                                                } else if (b2 == true) {
                                                                    MultiSoundManager multiSoundManager2 = audioService11.mMultiSoundManager;
                                                                    multiSoundManager2.getClass();
                                                                    Log.d("AS.MultiSoundManager", "shouldEnable");
                                                                    multiSoundManager2.mEnabled = true;
                                                                    multiSoundManager2.resetByEnableDisable();
                                                                } else {
                                                                    MultiSoundManager multiSoundManager3 = audioService11.mMultiSoundManager;
                                                                    if (!multiSoundManager3.mEnabled) {
                                                                        Log.d("AS.MultiSoundManager", "enable");
                                                                        multiSoundManager3.mEnabled = true;
                                                                        multiSoundManager3.resetByEnableDisable();
                                                                        if (b != false) {
                                                                            AudioService.this.mMultiSoundManager.showNotification();
                                                                        }
                                                                    }
                                                                    int devicesForStream = AudioSystem.getDevicesForStream(3);
                                                                    if (audioService11.getPinDeviceInternal() == devicesForStream && b != false) {
                                                                        audioService11.mMultiSoundManager.showHeadUpNotification(devicesForStream, audioService11.mDeviceBroker.getPriorityDevice(devicesForStream));
                                                                    }
                                                                }
                                                                if (!audioService11.mIsLeBroadCasting) {
                                                                    ScreenSharingHelper screenSharingHelper = audioService11.mScreenSharingHelper;
                                                                    if ((!screenSharingHelper.mIsSupportDisplayVolumeControl && !screenSharingHelper.mIsDLNAEnabled) || screenSharingHelper.mIsAppCasting) {
                                                                        audioService11.sendBroadcastToAll(intent4, null);
                                                                    }
                                                                }
                                                                MediaSessionService.MediaSessionServiceInternal mediaSessionServiceInternal = audioService11.mMediaSessionServiceInternal;
                                                                if (mediaSessionServiceInternal != null) {
                                                                    mediaSessionServiceInternal.updateMultiSoundInfo(-1, audioService11.isMultiSoundOn());
                                                                    return;
                                                                }
                                                                return;
                                                            case 2764:
                                                                audioService11.onSetAppDevice(message.arg1, message.arg2, true);
                                                                return;
                                                            case 2765:
                                                                int i19 = message.arg1;
                                                                if (!audioService11.isMultiSoundOn() || i19 == audioService11.getPinDeviceInternal()) {
                                                                    audioService11.mMultiSoundManager.showNotification();
                                                                    return;
                                                                } else {
                                                                    audioService11.mMultiSoundManager.mNm.cancel(1005);
                                                                    return;
                                                                }
                                                            case 2766:
                                                                int i20 = message.arg1;
                                                                if (i20 == 0) {
                                                                    Settings.Global.putInt(audioService11.mContentResolver, "mode_ringer_time_on", 0);
                                                                    return;
                                                                }
                                                                Settings.Global.putInt(audioService11.mContentResolver, "mode_ringer_time", i20);
                                                                int i21 = i20 * 60000;
                                                                audioService11.mMuteIntervalMs = i21;
                                                                if (i21 != 0) {
                                                                    PendingIntent broadcast = PendingIntent.getBroadcast(audioService11.mContext, 0, new Intent("com.sec.media.action.mute_interval"), 201326592);
                                                                    long currentTimeMillis = System.currentTimeMillis() + audioService11.mMuteIntervalMs;
                                                                    audioService11.mMuteTime = currentTimeMillis;
                                                                    audioService11.mAlarmManager.setExact(0, currentTimeMillis, broadcast);
                                                                    return;
                                                                }
                                                                return;
                                                            case 2767:
                                                                MediaFocusControl mediaFocusControl = audioService11.mMediaFocusControl;
                                                                if (mediaFocusControl != null) {
                                                                    int i22 = message.arg1;
                                                                    if (i22 == 0) {
                                                                        Log.d("MediaFocusControl", "incorrect parameter");
                                                                    } else {
                                                                        synchronized (MediaFocusControl.mAudioFocusLock) {
                                                                            try {
                                                                                if ((67125261 & i22) != 0) {
                                                                                    Log.d("MediaFocusControl", "force change device " + i22 + " to 2");
                                                                                    i22 = 2;
                                                                                } else if (AudioSystem.DEVICE_OUT_ALL_SCO_SET.contains(Integer.valueOf(i22)) || AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i22))) {
                                                                                    i22 = 128;
                                                                                }
                                                                                int i23 = mediaFocusControl.mDevice;
                                                                                if (i23 == i22) {
                                                                                    Log.d("MediaFocusControl", "setDevice, device doesn't change");
                                                                                } else {
                                                                                    Log.d("MediaFocusControl", "setDevice, " + Integer.toHexString(i22));
                                                                                    mediaFocusControl.mFocusStack = mediaFocusControl.mMultiFocusStack.getStackForDevice(0);
                                                                                    Log.d("MediaFocusControl", "move from default to " + Integer.toHexString(i23));
                                                                                    Stack stackForDevice = mediaFocusControl.mMultiFocusStack.getStackForDevice(i23);
                                                                                    Iterator it2 = mediaFocusControl.mFocusStack.iterator();
                                                                                    while (it2.hasNext()) {
                                                                                        FocusRequester focusRequester2 = (FocusRequester) it2.next();
                                                                                        int appDevice = mediaFocusControl.getAppDevice(focusRequester2.mCallingUid);
                                                                                        focusRequester2.mDevice = appDevice;
                                                                                        if (appDevice == i23) {
                                                                                            it2.remove();
                                                                                            stackForDevice.push(focusRequester2);
                                                                                        }
                                                                                    }
                                                                                    Log.d("MediaFocusControl", "move from " + Integer.toHexString(i22) + " to default");
                                                                                    Stack stackForDevice2 = mediaFocusControl.mMultiFocusStack.getStackForDevice(i22);
                                                                                    if (mediaFocusControl.mFocusStack.isEmpty() ? false : mediaFocusControl.mAudioService.isPlaybackActiveForUid(((FocusRequester) mediaFocusControl.mFocusStack.peek()).mCallingUid)) {
                                                                                        focusRequester = (FocusRequester) mediaFocusControl.mFocusStack.pop();
                                                                                    } else if (!stackForDevice2.isEmpty()) {
                                                                                        mediaFocusControl.propagateFocusLossFromGain_syncAf(1, null, true);
                                                                                    }
                                                                                    Iterator it3 = stackForDevice2.iterator();
                                                                                    while (it3.hasNext()) {
                                                                                        FocusRequester focusRequester3 = (FocusRequester) it3.next();
                                                                                        it3.remove();
                                                                                        mediaFocusControl.mFocusStack.push(focusRequester3);
                                                                                    }
                                                                                    if (focusRequester != null) {
                                                                                        int i24 = focusRequester.mFocusGainRequest;
                                                                                        if (i24 != 2) {
                                                                                            mediaFocusControl.propagateFocusLossFromGain_syncAf(i24, focusRequester, true);
                                                                                        }
                                                                                        mediaFocusControl.mFocusStack.push(focusRequester);
                                                                                    }
                                                                                    mediaFocusControl.mDevice = i22;
                                                                                }
                                                                            } finally {
                                                                            }
                                                                        }
                                                                    }
                                                                    MediaSessionService.MediaSessionServiceInternal mediaSessionServiceInternal2 = audioService11.mMediaSessionServiceInternal;
                                                                    if (mediaSessionServiceInternal2 != null) {
                                                                        mediaSessionServiceInternal2.updateMultiSoundInfo(message.arg1, audioService11.isMultiSoundOn());
                                                                        return;
                                                                    }
                                                                    return;
                                                                }
                                                                return;
                                                            case 2768:
                                                                audioService11.sendBroadcastToAll((Intent) message.obj, null);
                                                                return;
                                                            case 2769:
                                                                if (message.arg1 == 0) {
                                                                    audioService11.mSensorThread.stopSensor();
                                                                    return;
                                                                } else {
                                                                    audioService11.mSensorThread.startSensor();
                                                                    return;
                                                                }
                                                            case 2770:
                                                                if (message.arg1 == 1 && audioService11.mSensorThread.mIsClosed) {
                                                                    AudioSystem.setParameters("l_hw_proximity_sensor_state=1");
                                                                    return;
                                                                }
                                                                return;
                                                            case 2771:
                                                                if (AudioUtils.getUidForPackage(audioService11.mContext, "com.samsung.android.soundassistant") < 10000) {
                                                                    return;
                                                                }
                                                                Intent intent5 = new Intent("com.sec.android.soundassistant.SOUNDASSIST_INTENT_SERVICE");
                                                                intent5.setClassName("com.samsung.android.soundassistant", "com.sec.android.soundassistant.services.SoundAssistIntentService");
                                                                intent5.putExtra("type", 1003);
                                                                try {
                                                                    audioService11.mContext.startForegroundServiceAsUser(intent5, UserHandle.CURRENT);
                                                                    return;
                                                                } catch (Exception unused2) {
                                                                    return;
                                                                }
                                                            case 2772:
                                                                if (Rune.SEC_AUDIO_RECORDING_POPUP) {
                                                                    int i25 = message.arg1;
                                                                    int i26 = message.arg2;
                                                                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(i25, i26, "[RECORDING POPUP] showRecordingPopup uid=", ", type=", "AS.AudioService");
                                                                    Context context2 = audioService11.mContext;
                                                                    PackageManager packageManager = context2.getPackageManager();
                                                                    String nameForUid = packageManager.getNameForUid(i25);
                                                                    try {
                                                                        string = packageManager.getApplicationLabel(packageManager.getApplicationInfo(nameForUid, 0)).toString();
                                                                    } catch (PackageManager.NameNotFoundException unused3) {
                                                                        Log.e("AS.RecordingPopupHelper", "[RECORDING POPUP] getAppName can't find the name of " + nameForUid);
                                                                        Resources system = Resources.getSystem();
                                                                        string = i25 == 1000 ? system.getString(R.string.capability_title_canPerformGestures) : system.getString(R.string.unknownName);
                                                                    }
                                                                    Toast.makeText(context2, (i26 == 22 || i26 == 3) ? context2.getString(17042532, string, context2.getString(R.string.miniresolver_private_space_phone_information)) : i26 == 7 ? context2.getString(17042531, string) : i26 == 15 ? context2.getString(17042532, string, context2.getString(17042530)) : context2.getString(17042529, string), 0).show();
                                                                    return;
                                                                }
                                                                return;
                                                            case 2773:
                                                                audioService11.mAudioSystem.setParameters(new AudioParameter.Builder().setParam("l_support_absolute_volume", message.arg1 == 1 ? "true" : "false").setParam("address", (String) message.obj).build().toString());
                                                                return;
                                                            case 2774:
                                                                audioService11.onSetAppDevice(message.arg1, message.arg2, false);
                                                                return;
                                                            case 2775:
                                                                audioService11.setMultiSoundOn(audioService11.isMultiSoundOn());
                                                                boolean z2 = message.arg1 == 1;
                                                                synchronized (audioService11.mSettingsLock) {
                                                                    audioService11.mStreamStates[1].mute("setLeBroadcasting", z2);
                                                                }
                                                                z = message.arg1 == 1;
                                                                synchronized (audioService11.mSettingsLock) {
                                                                    audioService11.mStreamStates[5].mute("setLeBroadcasting", z);
                                                                }
                                                                return;
                                                            default:
                                                                return;
                                                        }
                                                    }
                                                    return;
                                            }
                                    }
                            }
                    }
            }
        }

        public final void persistRingerMode(int i) {
            AudioService audioService = AudioService.this;
            if (audioService.mUseFixedVolume) {
                return;
            }
            SettingsAdapter settingsAdapter = audioService.mSettings;
            ContentResolver contentResolver = audioService.mContentResolver;
            settingsAdapter.getClass();
            Settings.Global.putInt(contentResolver, "mode_ringer", i);
        }

        public final void persistVolume(final VolumeStreamState volumeStreamState, int i) {
            String str;
            AudioService audioService = AudioService.this;
            if (audioService.mUseFixedVolume) {
                return;
            }
            if ((audioService.mIsSingleVolume && volumeStreamState.mStreamType != 3) || volumeStreamState.mStreamType == 7 || (str = volumeStreamState.mVolumeIndexSettingName) == null || str.isEmpty()) {
                return;
            }
            int indexDividedBy10 = volumeStreamState.getIndexDividedBy10(i);
            int i2 = volumeStreamState.mStreamType;
            if (i2 == 1) {
                Set set = AudioUtils.DEVICE_OUT_WIRED_DEVICE_SET;
                String num = Integer.toString(indexDividedBy10);
                if (i == 2) {
                    SystemProperties.set("persist.audio.sysvolume", num);
                } else if (AudioUtils.isWiredDeviceType(i)) {
                    if (i == 4) {
                        SystemProperties.set("persist.audio.headsetsysvolume", num);
                    } else if (i == 8) {
                        SystemProperties.set("persist.audio.hphonesysvolume", num);
                    }
                }
            }
            AudioService.m264$$Nest$mapplyDeviceAlias(audioService, i, i2, new DeviceAliasManager.DeviceAliasRunner() { // from class: com.android.server.audio.AudioService$AudioHandler$$ExternalSyntheticLambda0
                @Override // com.samsung.android.server.audio.DeviceAliasManager.DeviceAliasRunner
                public final void run(int i3) {
                    AudioService audioService2 = AudioService.this;
                    SettingsAdapter settingsAdapter = audioService2.mSettings;
                    ContentResolver contentResolver = audioService2.mContentResolver;
                    AudioService.VolumeStreamState volumeStreamState2 = volumeStreamState;
                    String settingNameForDevice = volumeStreamState2.getSettingNameForDevice(i3);
                    int indexDividedBy102 = volumeStreamState2.getIndexDividedBy10(i3);
                    settingsAdapter.getClass();
                    Settings.System.putIntForUser(contentResolver, settingNameForDevice, indexDividedBy102, -2);
                }
            });
            SettingsAdapter settingsAdapter = audioService.mSettings;
            ContentResolver contentResolver = audioService.mContentResolver;
            String settingNameForDevice = volumeStreamState.getSettingNameForDevice(i);
            int indexDividedBy102 = volumeStreamState.getIndexDividedBy10(i);
            settingsAdapter.getClass();
            Settings.System.putIntForUser(contentResolver, settingNameForDevice, indexDividedBy102, -2);
        }

        public final void setAllVolumes(VolumeStreamState volumeStreamState) {
            volumeStreamState.applyAllVolumes();
            for (int numStreamTypes = AudioSystem.getNumStreamTypes() - 1; numStreamTypes >= 0; numStreamTypes--) {
                int i = volumeStreamState.mStreamType;
                if (numStreamTypes != i && AudioService.mStreamVolumeAlias[numStreamTypes] == i) {
                    AudioService.this.mStreamStates[numStreamTypes].applyAllVolumes();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioPolicyProxy extends AudioPolicyConfig implements IBinder.DeathRecipient {
        public final AttributionSource mAttributionSource;
        public int mFocusDuckBehavior;
        public final boolean mHasFocusListener;
        public final boolean mIsFocusPolicy;
        public final boolean mIsTestFocusPolicy;
        public final boolean mIsVolumeController;
        public final IAudioPolicyCallback mPolicyCallback;
        public final IMediaProjection mProjection;
        public final UnregisterOnStopCallback mProjectionCallback;
        public final HashMap mUidDeviceAffinities;
        public final HashMap mUserIdDeviceAffinities;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class UnregisterOnStopCallback extends IMediaProjectionCallback.Stub {
            public UnregisterOnStopCallback() {
            }

            public final void onCapturedContentResize(int i, int i2) {
            }

            public final void onCapturedContentVisibilityChanged(boolean z) {
            }

            public final void onStop() {
                AudioPolicyProxy audioPolicyProxy = AudioPolicyProxy.this;
                AudioService.this.unregisterAudioPolicyAsync(audioPolicyProxy.mPolicyCallback);
            }
        }

        public AudioPolicyProxy(AudioPolicyConfig audioPolicyConfig, final IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection, AttributionSource attributionSource) {
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
            this.mAttributionSource = attributionSource;
            this.mHasFocusListener = z;
            this.mIsVolumeController = z4;
            this.mProjection = iMediaProjection;
            if (z) {
                final MediaFocusControl mediaFocusControl = AudioService.this.mMediaFocusControl;
                mediaFocusControl.getClass();
                if (iAudioPolicyCallback != null) {
                    synchronized (MediaFocusControl.mAudioFocusLock) {
                        try {
                            Iterator it = mediaFocusControl.mFocusFollowers.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    mediaFocusControl.mFocusFollowers.add(iAudioPolicyCallback);
                                    new Thread() { // from class: com.android.server.audio.MediaFocusControl.1
                                        public final /* synthetic */ IAudioPolicyCallback val$pcb2;

                                        public AnonymousClass1(final IAudioPolicyCallback iAudioPolicyCallback2) {
                                            r2 = iAudioPolicyCallback2;
                                        }

                                        @Override // java.lang.Thread, java.lang.Runnable
                                        public final void run() {
                                            synchronized (MediaFocusControl.mAudioFocusLock) {
                                                if (MediaFocusControl.this.mFocusStack.isEmpty()) {
                                                    return;
                                                }
                                                try {
                                                    r2.notifyAudioFocusGrant(((FocusRequester) MediaFocusControl.this.mFocusStack.peek()).toAudioFocusInfo(), 1);
                                                } catch (RemoteException e) {
                                                    Log.e("MediaFocusControl", "Can't call notifyAudioFocusGrant() on IAudioPolicyCallback " + r2.asBinder(), e);
                                                }
                                            }
                                        }
                                    }.start();
                                    break;
                                } else if (((IAudioPolicyCallback) it.next()).asBinder().equals(iAudioPolicyCallback2.asBinder())) {
                                }
                            }
                        } finally {
                        }
                    }
                }
                if (z2) {
                    this.mIsFocusPolicy = true;
                    this.mIsTestFocusPolicy = z3;
                    MediaFocusControl mediaFocusControl2 = AudioService.this.mMediaFocusControl;
                    IAudioPolicyCallback iAudioPolicyCallback2 = this.mPolicyCallback;
                    mediaFocusControl2.getClass();
                    if (iAudioPolicyCallback2 != null) {
                        synchronized (MediaFocusControl.mAudioFocusLock) {
                            if (z3) {
                                try {
                                    mediaFocusControl2.mPreviousFocusPolicy = mediaFocusControl2.mFocusPolicy;
                                } finally {
                                }
                            }
                            mediaFocusControl2.mFocusPolicy = iAudioPolicyCallback2;
                        }
                    }
                }
            }
            if (this.mIsVolumeController) {
                IAudioPolicyCallback iAudioPolicyCallback3 = this.mPolicyCallback;
                if (AudioService.this.mContext.getResources().getBoolean(R.bool.config_isCompatFakeFocusEnabled)) {
                    synchronized (AudioService.this.mExtVolumeControllerLock) {
                        try {
                            IAudioPolicyCallback iAudioPolicyCallback4 = AudioService.this.mExtVolumeController;
                            if (iAudioPolicyCallback4 != null && !iAudioPolicyCallback4.asBinder().pingBinder()) {
                                Log.e("AS.AudioService", "Cannot set external volume controller: existing controller");
                            }
                            AudioService.this.mExtVolumeController = iAudioPolicyCallback3;
                        } finally {
                        }
                    }
                } else {
                    Log.e("AS.AudioService", "Cannot set external volume controller: device not set for volume keys handled in PhoneWindowManager");
                }
            }
            if (this.mProjection != null) {
                UnregisterOnStopCallback unregisterOnStopCallback = new UnregisterOnStopCallback();
                this.mProjectionCallback = unregisterOnStopCallback;
                try {
                    this.mProjection.registerCallback(unregisterOnStopCallback);
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
            throw new IllegalStateException(VibrationParam$1$$ExternalSyntheticOutline0.m(connectMixes, "Could not connect mix, error: "));
        }

        public static String logFriendlyAttributeDeviceArrayMap(String str, Map map) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : ((HashMap) map).entrySet()) {
                sb.append("     ");
                sb.append(str);
                sb.append(": ");
                sb.append(entry.getKey());
                sb.append("\n");
                AudioDeviceArray audioDeviceArray = (AudioDeviceArray) entry.getValue();
                for (int i = 0; i < audioDeviceArray.mDeviceTypes.length; i++) {
                    sb.append("        Type: 0x");
                    BatteryService$$ExternalSyntheticOutline0.m(audioDeviceArray.mDeviceTypes[i], sb, " Address: ");
                    sb.append(audioDeviceArray.mDeviceAddresses[i]);
                    sb.append("\n");
                }
            }
            return sb.toString();
        }

        public final int addMixes(ArrayList arrayList) {
            synchronized (((AudioPolicyConfig) this).mMixes) {
                try {
                    if (!Flags.audioMixOwnership()) {
                        add(arrayList);
                        AudioService.this.mAudioSystem.invalidateRoutingCache();
                        return AudioSystem.registerPolicyMixes(arrayList, true);
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        AudioMix audioMix = (AudioMix) it.next();
                        setMixRegistration(audioMix);
                        audioMix.setVirtualDeviceId(this.mAttributionSource.getDeviceId());
                    }
                    AudioService.this.mAudioSystem.invalidateRoutingCache();
                    int registerPolicyMixes = AudioSystem.registerPolicyMixes(arrayList, true);
                    if (registerPolicyMixes == 0) {
                        add(arrayList);
                    }
                    return registerPolicyMixes;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:30:0x00a5, code lost:
        
            r1.mDeviceBroker.sendIILMsg(12, 0, 0, 0, null, 0);
         */
        @Override // android.os.IBinder.DeathRecipient
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void binderDied() {
            /*
                r13 = this;
                com.android.server.audio.AudioService r0 = com.android.server.audio.AudioService.this
                com.android.server.utils.EventLogger r0 = r0.mDynPolicyLogger
                com.android.server.utils.EventLogger$StringEvent r1 = new com.android.server.utils.EventLogger$StringEvent
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "AudioPolicy "
                r2.<init>(r3)
                android.media.audiopolicy.IAudioPolicyCallback r3 = r13.mPolicyCallback
                android.os.IBinder r3 = r3.asBinder()
                r2.append(r3)
                java.lang.String r3 = " died"
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                r1.<init>(r2)
                java.lang.String r2 = "AudioPolicyProxy"
                r3 = 0
                r1.printLog(r3, r2)
                r0.enqueue(r1)
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                java.util.ArrayList r1 = r13.mMixes
                java.util.Iterator r1 = r1.iterator()
            L36:
                boolean r2 = r1.hasNext()
                if (r2 == 0) goto L4a
                java.lang.Object r2 = r1.next()
                android.media.audiopolicy.AudioMix r2 = (android.media.audiopolicy.AudioMix) r2
                java.lang.String r2 = r2.getRegistration()
                r0.add(r2)
                goto L36
            L4a:
                com.android.server.audio.AudioService r1 = com.android.server.audio.AudioService.this
                r1.getClass()
                java.util.Iterator r0 = r0.iterator()
            L53:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto Lb8
                java.lang.Object r2 = r0.next()
                java.lang.String r2 = (java.lang.String) r2
                com.android.server.audio.PlaybackActivityMonitor r3 = r1.mPlaybackMonitor
                java.lang.Object r4 = r3.mPlayerLock
                monitor-enter(r4)
                java.util.HashMap r3 = r3.mPlayers     // Catch: java.lang.Throwable -> Lb2
                java.util.Collection r3 = r3.values()     // Catch: java.lang.Throwable -> Lb2
                java.util.Iterator r3 = r3.iterator()     // Catch: java.lang.Throwable -> Lb2
            L6e:
                boolean r5 = r3.hasNext()     // Catch: java.lang.Throwable -> Lb2
                if (r5 == 0) goto Lb4
                java.lang.Object r5 = r3.next()     // Catch: java.lang.Throwable -> Lb2
                android.media.AudioPlaybackConfiguration r5 = (android.media.AudioPlaybackConfiguration) r5     // Catch: java.lang.Throwable -> Lb2
                android.media.AudioDeviceInfo r6 = r5.getAudioDeviceInfo()     // Catch: java.lang.Throwable -> Lb2
                android.media.AudioAttributes r7 = r5.getAudioAttributes()     // Catch: java.lang.Throwable -> Lb2
                int r7 = r7.getUsage()     // Catch: java.lang.Throwable -> Lb2
                r8 = 1
                if (r7 != r8) goto L6e
                boolean r5 = r5.isActive()     // Catch: java.lang.Throwable -> Lb2
                if (r5 == 0) goto L6e
                if (r6 == 0) goto L6e
                int r5 = r6.getInternalType()     // Catch: java.lang.Throwable -> Lb2
                r7 = 32768(0x8000, float:4.5918E-41)
                if (r5 != r7) goto L6e
                java.lang.String r5 = r6.getAddress()     // Catch: java.lang.Throwable -> Lb2
                boolean r5 = r2.equals(r5)     // Catch: java.lang.Throwable -> Lb2
                if (r5 == 0) goto L6e
                monitor-exit(r4)     // Catch: java.lang.Throwable -> Lb2
                com.android.server.audio.AudioDeviceBroker r6 = r1.mDeviceBroker
                r9 = 0
                r10 = 0
                r7 = 12
                r8 = 0
                r11 = 0
                r12 = 0
                r6.sendIILMsg(r7, r8, r9, r10, r11, r12)
                goto Lb8
            Lb2:
                r13 = move-exception
                goto Lb6
            Lb4:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> Lb2
                goto L53
            Lb6:
                monitor-exit(r4)     // Catch: java.lang.Throwable -> Lb2
                throw r13
            Lb8:
                r13.release()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.AudioPolicyProxy.binderDied():void");
        }

        public final int connectMixes() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Iterator it = ((AudioPolicyConfig) this).mMixes.iterator();
                while (it.hasNext()) {
                    ((AudioMix) it.next()).setVirtualDeviceId(this.mAttributionSource.getDeviceId());
                }
                AudioSystemAdapter audioSystemAdapter = AudioService.this.mAudioSystem;
                ArrayList arrayList = ((AudioPolicyConfig) this).mMixes;
                audioSystemAdapter.invalidateRoutingCache();
                int registerPolicyMixes = AudioSystem.registerPolicyMixes(arrayList, true);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return registerPolicyMixes;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final String getRegistrationId() {
            return getRegistration();
        }

        public final boolean hasMixAffectingUsage() {
            Iterator it = ((AudioPolicyConfig) this).mMixes.iterator();
            while (it.hasNext()) {
                AudioMix audioMix = (AudioMix) it.next();
                if (audioMix.isAffectingUsage(1) && (audioMix.getRouteFlags() & 3) != 3) {
                    return true;
                }
            }
            return false;
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
        
            r1 = r1 + 1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean hasMixRoutedToDevices(int[] r7, java.lang.String[] r8) {
            /*
                r6 = this;
                r0 = 0
                r1 = r0
            L2:
                int r2 = r7.length
                if (r1 >= r2) goto L25
                java.util.ArrayList r2 = r6.mMixes
                java.util.Iterator r2 = r2.iterator()
            Lb:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L24
                java.lang.Object r3 = r2.next()
                android.media.audiopolicy.AudioMix r3 = (android.media.audiopolicy.AudioMix) r3
                r4 = r7[r1]
                r5 = r8[r1]
                boolean r3 = r3.isRoutedToDevice(r4, r5)
                if (r3 == 0) goto Lb
                int r1 = r1 + 1
                goto L2
            L24:
                return r0
            L25:
                r6 = 1
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.AudioPolicyProxy.hasMixRoutedToDevices(int[], java.lang.String[]):boolean");
        }

        /* JADX WARN: Finally extract failed */
        public final void release() {
            if (this.mIsFocusPolicy) {
                MediaFocusControl mediaFocusControl = AudioService.this.mMediaFocusControl;
                IAudioPolicyCallback iAudioPolicyCallback = this.mPolicyCallback;
                boolean z = this.mIsTestFocusPolicy;
                mediaFocusControl.getClass();
                if (iAudioPolicyCallback != null) {
                    synchronized (MediaFocusControl.mAudioFocusLock) {
                        try {
                            if (mediaFocusControl.mFocusPolicy == iAudioPolicyCallback) {
                                if (z) {
                                    mediaFocusControl.mFocusPolicy = mediaFocusControl.mPreviousFocusPolicy;
                                } else {
                                    mediaFocusControl.mFocusPolicy = null;
                                }
                            }
                        } finally {
                        }
                    }
                }
            }
            if (this.mFocusDuckBehavior == 1) {
                AudioService.this.mMediaFocusControl.mNotifyFocusOwnerOnDuck = true;
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
                if (Flags.audioMixOwnership()) {
                    synchronized (((AudioPolicyConfig) this).mMixes) {
                        removeMixes(new ArrayList(((AudioPolicyConfig) this).mMixes));
                    }
                } else {
                    AudioSystemAdapter audioSystemAdapter = AudioService.this.mAudioSystem;
                    ArrayList arrayList = ((AudioPolicyConfig) this).mMixes;
                    audioSystemAdapter.invalidateRoutingCache();
                    AudioSystem.registerPolicyMixes(arrayList, false);
                }
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

        public final int removeMixes(ArrayList arrayList) {
            int registerPolicyMixes;
            synchronized (((AudioPolicyConfig) this).mMixes) {
                remove(arrayList);
                AudioService.this.mAudioSystem.invalidateRoutingCache();
                registerPolicyMixes = AudioSystem.registerPolicyMixes(arrayList, false);
            }
            return registerPolicyMixes;
        }

        public final int removeUidDeviceAffinitiesFromSystem(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AudioService.this.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.removeUidDeviceAffinities(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int removeUserIdDeviceAffinitiesFromSystem(int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AudioService.this.mAudioSystem.invalidateRoutingCache();
                return AudioSystem.removeUserIdDeviceAffinities(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int setUidDeviceAffinities(int i, int[] iArr, String[] strArr) {
            Integer num = new Integer(i);
            if (this.mUidDeviceAffinities.remove(num) != null && removeUidDeviceAffinitiesFromSystem(i) != 0) {
                AudioDeviceInventory$$ExternalSyntheticOutline0.m(i, "AudioSystem. removeUidDeviceAffinities(", ") failed,  cannot call AudioSystem.setUidDeviceAffinities", "AudioPolicyProxy");
                return -1;
            }
            AudioDeviceArray audioDeviceArray = new AudioDeviceArray(iArr, strArr);
            if (setUidDeviceAffinitiesOnSystem(i, audioDeviceArray) == 0) {
                this.mUidDeviceAffinities.put(num, audioDeviceArray);
                return 0;
            }
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(i, "AudioSystem. setUidDeviceAffinities(", ") failed", "AudioPolicyProxy");
            return -1;
        }

        public final int setUidDeviceAffinitiesOnSystem(int i, AudioDeviceArray audioDeviceArray) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AudioSystemAdapter audioSystemAdapter = AudioService.this.mAudioSystem;
                int[] iArr = audioDeviceArray.mDeviceTypes;
                String[] strArr = audioDeviceArray.mDeviceAddresses;
                audioSystemAdapter.invalidateRoutingCache();
                return AudioSystem.setUidDeviceAffinities(i, iArr, strArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int setUserIdDeviceAffinities(int i, int[] iArr, String[] strArr) {
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
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(i, "AudioSystem.setUserIdDeviceAffinities(", ") failed", "AudioPolicyProxy");
            return -1;
        }

        public final int setUserIdDeviceAffinitiesOnSystem(int i, AudioDeviceArray audioDeviceArray) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AudioSystemAdapter audioSystemAdapter = AudioService.this.mAudioSystem;
                int[] iArr = audioDeviceArray.mDeviceTypes;
                String[] strArr = audioDeviceArray.mDeviceAddresses;
                audioSystemAdapter.invalidateRoutingCache();
                return AudioSystem.setUserIdDeviceAffinities(i, iArr, strArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int setupDeviceAffinities() {
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

        public final String toLogFriendlyString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(super.toLogFriendlyString(), " Uid Device Affinities:\n"));
            m.append(logFriendlyAttributeDeviceArrayMap("Uid", this.mUidDeviceAffinities));
            StringBuilder m2 = BootReceiver$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m.toString(), " UserId Device Affinities:\n"));
            m2.append(logFriendlyAttributeDeviceArrayMap("UserId", this.mUserIdDeviceAffinities));
            String m3 = OptionalBool$$ExternalSyntheticOutline0.m("\n", Preconditions$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m2.toString(), " Proxy:\n"), "   is focus policy= "), this.mIsFocusPolicy);
            if (this.mIsFocusPolicy) {
                m3 = OptionalBool$$ExternalSyntheticOutline0.m("\n", Preconditions$$ExternalSyntheticOutline0.m(OptionalBool$$ExternalSyntheticOutline0.m("\n", Preconditions$$ExternalSyntheticOutline0.m(AmFmBandRange$$ExternalSyntheticOutline0.m(this.mFocusDuckBehavior, Preconditions$$ExternalSyntheticOutline0.m(m3, "     focus duck behaviour= "), "\n"), "     is test focus policy= "), this.mIsTestFocusPolicy), "     has focus listener= "), this.mHasFocusListener);
            }
            StringBuilder m4 = Preconditions$$ExternalSyntheticOutline0.m(m3, "   media projection= ");
            m4.append(this.mProjection);
            m4.append("\n");
            return m4.toString();
        }

        public final int updateMixingRules(AudioMix[] audioMixArr, AudioMixingRule[] audioMixingRuleArr) {
            int updatePolicyMixes;
            Objects.requireNonNull(audioMixArr);
            Objects.requireNonNull(audioMixingRuleArr);
            for (AudioMix audioMix : audioMixArr) {
                audioMix.setVirtualDeviceId(this.mAttributionSource.getDeviceId());
            }
            if (audioMixArr.length != audioMixingRuleArr.length) {
                Log.e("AudioPolicyProxy", "Provided list of audio mixes to update and corresponding mixing rules have mismatching length (mixesToUpdate.length = " + audioMixArr.length + ", updatedMixingRules.length = " + audioMixingRuleArr.length + ").");
                return -2;
            }
            synchronized (((AudioPolicyConfig) this).mMixes) {
                try {
                    SafeCloseable create = ClearCallingIdentityContext.create();
                    try {
                        AudioService.this.mAudioSystem.invalidateRoutingCache();
                        updatePolicyMixes = AudioSystem.updatePolicyMixes(audioMixArr, audioMixingRuleArr);
                        if (updatePolicyMixes == 0) {
                            for (int i = 0; i < audioMixArr.length; i++) {
                                final AudioMix audioMix2 = audioMixArr[i];
                                AudioMixingRule audioMixingRule = audioMixingRuleArr[i];
                                Stream stream = ((AudioPolicyConfig) this).mMixes.stream();
                                Objects.requireNonNull(audioMix2);
                                stream.filter(new Predicate() { // from class: com.android.server.audio.AudioService$AudioPolicyProxy$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return audioMix2.equals((AudioMix) obj);
                                    }
                                }).findAny().ifPresent(new AudioService$$ExternalSyntheticLambda16(4, audioMixingRule));
                            }
                        }
                        if (create != null) {
                            create.close();
                        }
                    } finally {
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return updatePolicyMixes;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioServiceInternal extends AudioManagerInternal {
        public AudioServiceInternal() {
        }

        public final void addAssistantServiceUid(int i) {
            AudioService.sendMsg(AudioService.this.mAudioHandler, 44, 2, i, 0, null, 0);
        }

        public final int getRingerModeInternal() {
            return AudioService.this.getRingerModeInternal();
        }

        public final void removeAssistantServiceUid(int i) {
            AudioService.sendMsg(AudioService.this.mAudioHandler, 45, 2, i, 0, null, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x002e A[Catch: all -> 0x0017, LOOP:0: B:22:0x002e->B:26:0x0042, LOOP_START, PHI: r3
          0x002e: PHI (r3v1 int) = (r3v0 int), (r3v2 int) binds: [B:21:0x002c, B:26:0x0042] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {all -> 0x0017, blocks: (B:8:0x000b, B:10:0x0011, B:11:0x0050, B:12:0x005e, B:15:0x0019, B:17:0x0021, B:22:0x002e, B:24:0x0035, B:30:0x0048, B:26:0x0042), top: B:7:0x000b }] */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0048 A[Catch: all -> 0x0017, TryCatch #0 {all -> 0x0017, blocks: (B:8:0x000b, B:10:0x0011, B:11:0x0050, B:12:0x005e, B:15:0x0019, B:17:0x0021, B:22:0x002e, B:24:0x0035, B:30:0x0048, B:26:0x0042), top: B:7:0x000b }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setAccessibilityServiceUids(android.util.IntArray r9) {
            /*
                r8 = this;
                com.android.server.audio.AudioService r0 = com.android.server.audio.AudioService.this
                int r1 = r0.mPlatformType
                r2 = 3
                if (r1 != r2) goto L8
                return
            L8:
                java.lang.Object r0 = r0.mAccessibilityServiceUidsLock
                monitor-enter(r0)
                int r1 = r9.size()     // Catch: java.lang.Throwable -> L17
                if (r1 != 0) goto L19
                com.android.server.audio.AudioService r9 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L17
                r1 = 0
                r9.mAccessibilityServiceUids = r1     // Catch: java.lang.Throwable -> L17
                goto L50
            L17:
                r8 = move-exception
                goto L60
            L19:
                com.android.server.audio.AudioService r1 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L17
                int[] r1 = r1.mAccessibilityServiceUids     // Catch: java.lang.Throwable -> L17
                r2 = 1
                r3 = 0
                if (r1 == 0) goto L2b
                int r1 = r1.length     // Catch: java.lang.Throwable -> L17
                int r4 = r9.size()     // Catch: java.lang.Throwable -> L17
                if (r1 == r4) goto L29
                goto L2b
            L29:
                r1 = r3
                goto L2c
            L2b:
                r1 = r2
            L2c:
                if (r1 != 0) goto L45
            L2e:
                com.android.server.audio.AudioService r4 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L17
                int[] r4 = r4.mAccessibilityServiceUids     // Catch: java.lang.Throwable -> L17
                int r4 = r4.length     // Catch: java.lang.Throwable -> L17
                if (r3 >= r4) goto L45
                int r4 = r9.get(r3)     // Catch: java.lang.Throwable -> L17
                com.android.server.audio.AudioService r5 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L17
                int[] r5 = r5.mAccessibilityServiceUids     // Catch: java.lang.Throwable -> L17
                r5 = r5[r3]     // Catch: java.lang.Throwable -> L17
                if (r4 == r5) goto L42
                goto L46
            L42:
                int r3 = r3 + 1
                goto L2e
            L45:
                r2 = r1
            L46:
                if (r2 == 0) goto L50
                com.android.server.audio.AudioService r1 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L17
                int[] r9 = r9.toArray()     // Catch: java.lang.Throwable -> L17
                r1.mAccessibilityServiceUids = r9     // Catch: java.lang.Throwable -> L17
            L50:
                com.android.server.audio.AudioService r8 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L17
                com.android.server.audio.AudioService$AudioHandler r1 = r8.mAudioHandler     // Catch: java.lang.Throwable -> L17
                r6 = 0
                r7 = 0
                r2 = 35
                r3 = 0
                r4 = 0
                r5 = 0
                com.android.server.audio.AudioService.sendMsg(r1, r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L17
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L17
                return
            L60:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L17
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.AudioServiceInternal.setAccessibilityServiceUids(android.util.IntArray):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0029 A[Catch: all -> 0x0012, LOOP:0: B:19:0x0029->B:23:0x003d, LOOP_START, PHI: r2
          0x0029: PHI (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:18:0x0027, B:23:0x003d] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {all -> 0x0012, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x004b, B:12:0x0014, B:14:0x001c, B:19:0x0029, B:21:0x0030, B:27:0x0043, B:23:0x003d), top: B:3:0x0005 }] */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0043 A[Catch: all -> 0x0012, TryCatch #0 {all -> 0x0012, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x004b, B:12:0x0014, B:14:0x001c, B:19:0x0029, B:21:0x0030, B:27:0x0043, B:23:0x003d), top: B:3:0x0005 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setActiveAssistantServicesUids(android.util.IntArray r8) {
            /*
                r7 = this;
                com.android.server.audio.AudioService r0 = com.android.server.audio.AudioService.this
                java.lang.Object r0 = r0.mSettingsLock
                monitor-enter(r0)
                int r1 = r8.size()     // Catch: java.lang.Throwable -> L12
                if (r1 != 0) goto L14
                com.android.server.audio.AudioService r8 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L12
                int[] r1 = com.android.server.audio.AudioService.NO_ACTIVE_ASSISTANT_SERVICE_UIDS     // Catch: java.lang.Throwable -> L12
                r8.mActiveAssistantServiceUids = r1     // Catch: java.lang.Throwable -> L12
                goto L4b
            L12:
                r7 = move-exception
                goto L5b
            L14:
                com.android.server.audio.AudioService r1 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L12
                int[] r1 = r1.mActiveAssistantServiceUids     // Catch: java.lang.Throwable -> L12
                r2 = 0
                r3 = 1
                if (r1 == 0) goto L26
                int r1 = r1.length     // Catch: java.lang.Throwable -> L12
                int r4 = r8.size()     // Catch: java.lang.Throwable -> L12
                if (r1 == r4) goto L24
                goto L26
            L24:
                r1 = r2
                goto L27
            L26:
                r1 = r3
            L27:
                if (r1 != 0) goto L40
            L29:
                com.android.server.audio.AudioService r4 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L12
                int[] r4 = r4.mActiveAssistantServiceUids     // Catch: java.lang.Throwable -> L12
                int r4 = r4.length     // Catch: java.lang.Throwable -> L12
                if (r2 >= r4) goto L40
                int r4 = r8.get(r2)     // Catch: java.lang.Throwable -> L12
                com.android.server.audio.AudioService r5 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L12
                int[] r5 = r5.mActiveAssistantServiceUids     // Catch: java.lang.Throwable -> L12
                r5 = r5[r2]     // Catch: java.lang.Throwable -> L12
                if (r4 == r5) goto L3d
                goto L41
            L3d:
                int r2 = r2 + 1
                goto L29
            L40:
                r3 = r1
            L41:
                if (r3 == 0) goto L4b
                com.android.server.audio.AudioService r1 = com.android.server.audio.AudioService.this     // Catch: java.lang.Throwable -> L12
                int[] r8 = r8.toArray()     // Catch: java.lang.Throwable -> L12
                r1.mActiveAssistantServiceUids = r8     // Catch: java.lang.Throwable -> L12
            L4b:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L12
                com.android.server.audio.AudioService r7 = com.android.server.audio.AudioService.this
                com.android.server.audio.AudioService$AudioHandler r0 = r7.mAudioHandler
                r1 = 46
                r2 = 0
                r3 = 0
                r4 = 0
                r5 = 0
                r6 = 0
                com.android.server.audio.AudioService.sendMsg(r0, r1, r2, r3, r4, r5, r6)
                return
            L5b:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L12
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.AudioServiceInternal.setActiveAssistantServicesUids(android.util.IntArray):void");
        }

        public final void setInputMethodServiceUid(int i) {
            synchronized (AudioService.this.mInputMethodServiceUidLock) {
                try {
                    AudioService audioService = AudioService.this;
                    if (audioService.mInputMethodServiceUid != i) {
                        audioService.mAudioSystem.getClass();
                        AudioSystem.setCurrentImeUid(i);
                        AudioService.this.mInputMethodServiceUid = i;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setRingerModeDelegate(AudioManagerInternal.RingerModeDelegate ringerModeDelegate) {
            AudioService audioService = AudioService.this;
            audioService.mRingerModeDelegate = ringerModeDelegate;
            if (ringerModeDelegate != null) {
                synchronized (audioService.mSettingsLock) {
                    AudioService.this.updateRingerAndZenModeAffectedStreams();
                }
                setRingerModeInternal(AudioService.this.getRingerModeInternal(), "AS.AudioService.setRingerModeDelegate");
            }
        }

        public final void setRingerModeInternal(int i, String str) {
            AudioService.this.setRingerModeInternal(i, str);
        }

        public final void silenceRingerModeInternal(String str) {
            int i;
            int i2;
            VibrationEffect vibrationEffect;
            AudioService audioService = AudioService.this;
            if (audioService.mContext.getResources().getBoolean(R.bool.config_wirelessConsentRequired)) {
                SettingsAdapter settingsAdapter = audioService.mSettings;
                ContentResolver contentResolver = audioService.mContentResolver;
                settingsAdapter.getClass();
                i = Settings.Secure.getIntForUser(contentResolver, "volume_hush_gesture", 0, -2);
            } else {
                i = 0;
            }
            int i3 = 1;
            if (i == 1) {
                i2 = 17043448;
                vibrationEffect = VibrationEffect.get(5);
            } else if (i != 2) {
                vibrationEffect = null;
                i3 = 0;
                i2 = 0;
            } else {
                vibrationEffect = VibrationEffect.get(1);
                i2 = 17043447;
                i3 = 0;
            }
            if (audioService.mHasVibrator && vibrationEffect != null) {
                audioService.mVibrator.vibrate(Binder.getCallingUid(), audioService.mContext.getOpPackageName(), vibrationEffect, str, AudioService.TOUCH_VIBRATION_ATTRIBUTES);
            }
            audioService.setRingerModeInternal(i3, str);
            Toast.makeText(audioService.mContext, i2, 0).show();
        }

        public final void updateRingerModeAffectedStreamsInternal() {
            synchronized (AudioService.this.mSettingsLock) {
                try {
                    if (AudioService.this.updateRingerAndZenModeAffectedStreams()) {
                        AudioService audioService = AudioService.this;
                        audioService.setRingerModeInt(audioService.getRingerModeInternal(), false);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioSystemThread extends Thread {
        public AudioSystemThread() {
            super("AudioService");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Looper.prepare();
            synchronized (AudioService.this) {
                AudioService.this.mAudioHandler = AudioService.this.new AudioHandler();
                AudioService.this.notify();
            }
            Looper.loop();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioVolumeGroupHelper extends AudioVolumeGroupHelperBase {
        @Override // com.android.server.audio.AudioVolumeGroupHelperBase
        public final List getAudioVolumeGroups() {
            return AudioVolumeGroup.getAudioVolumeGroups();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class BypassReason {
        public static final /* synthetic */ BypassReason[] $VALUES;
        public static final BypassReason ALL_SOUND_MUTE;
        public static final BypassReason CANNOT_UNMUTE_SILENT_VIBRATE;
        public static final BypassReason CONSUME_ADJUST_SAME;
        public static final BypassReason DISPLAY_VOLUME_CONTROL;
        public static final BypassReason LE_BROADCAST;
        public static final BypassReason MEDIA_VOLUME_STEP_ON;
        public static final BypassReason MULTISOUND;
        public static final BypassReason NO_BYPASS;
        public static final BypassReason SKIP_VOLUME_PANEL_NOT_VISIBLE;
        public static final BypassReason SKIP_WARNING_POPUP_VISIBLE;
        public static final BypassReason VOLUME_LIMITER;

        static {
            BypassReason bypassReason = new BypassReason("NO_BYPASS", 0);
            NO_BYPASS = bypassReason;
            BypassReason bypassReason2 = new BypassReason("DISPLAY_VOLUME_CONTROL", 1);
            DISPLAY_VOLUME_CONTROL = bypassReason2;
            BypassReason bypassReason3 = new BypassReason("CANNOT_UNMUTE_SILENT_VIBRATE", 2);
            CANNOT_UNMUTE_SILENT_VIBRATE = bypassReason3;
            BypassReason bypassReason4 = new BypassReason("ALL_SOUND_MUTE", 3);
            ALL_SOUND_MUTE = bypassReason4;
            BypassReason bypassReason5 = new BypassReason("TMS_CONNECT", 4);
            BypassReason bypassReason6 = new BypassReason("CONSUME_ADJUST_SAME", 5);
            CONSUME_ADJUST_SAME = bypassReason6;
            BypassReason bypassReason7 = new BypassReason("MULTISOUND", 6);
            MULTISOUND = bypassReason7;
            BypassReason bypassReason8 = new BypassReason("MEDIA_VOLUME_STEP_ON", 7);
            MEDIA_VOLUME_STEP_ON = bypassReason8;
            BypassReason bypassReason9 = new BypassReason("SKIP_WARNING_POPUP_VISIBLE", 8);
            SKIP_WARNING_POPUP_VISIBLE = bypassReason9;
            BypassReason bypassReason10 = new BypassReason("SKIP_VOLUME_PANEL_NOT_VISIBLE", 9);
            SKIP_VOLUME_PANEL_NOT_VISIBLE = bypassReason10;
            BypassReason bypassReason11 = new BypassReason("VOLUME_LIMITER", 10);
            VOLUME_LIMITER = bypassReason11;
            BypassReason bypassReason12 = new BypassReason("LE_BROADCAST", 11);
            LE_BROADCAST = bypassReason12;
            $VALUES = new BypassReason[]{bypassReason, bypassReason2, bypassReason3, bypassReason4, bypassReason5, bypassReason6, bypassReason7, bypassReason8, bypassReason9, bypassReason10, bypassReason11, bypassReason12};
        }

        public static BypassReason valueOf(String str) {
            return (BypassReason) Enum.valueOf(BypassReason.class, str);
        }

        public static BypassReason[] values() {
            return (BypassReason[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final AudioService mService;

        public Lifecycle(Context context) {
            super(context);
            DefaultAudioPolicyFacade defaultAudioPolicyFacade = new DefaultAudioPolicyFacade(Executors.newSingleThreadExecutor());
            AudioSystemAdapter defaultAdapter = AudioSystemAdapter.getDefaultAdapter();
            Objects.requireNonNull(context);
            SystemServerAdapter systemServerAdapter = new SystemServerAdapter(context);
            SettingsAdapter settingsAdapter = new SettingsAdapter();
            AudioVolumeGroupHelper audioVolumeGroupHelper = new AudioVolumeGroupHelper();
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
            PermissionEnforcer fromContext = PermissionEnforcer.fromContext(context);
            com.android.media.audio.Flags.audioserverPermissions();
            this.mService = new AudioService(context, defaultAdapter, systemServerAdapter, settingsAdapter, audioVolumeGroupHelper, defaultAudioPolicyFacade, appOpsManager, fromContext);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            MicModeManager micModeManager;
            final AudioService audioService = this.mService;
            if (i == 550) {
                AudioService.sendMsg(audioService.mAudioHandler, 16, 2, 0, 0, null, 0);
                return;
            }
            if (i == 1000) {
                AudioService.sendMsg(audioService.mAudioHandler, 2771, 1, 0, 0, null, 30000);
                RecordingActivityMonitor recordingActivityMonitor = audioService.mRecordMonitor;
                if (recordingActivityMonitor != null) {
                    recordingActivityMonitor.mChecker = audioService.mRecordEventChecker;
                }
                audioService.mAudioGameManager = new AudioGameManager(audioService.mContext);
                Context context = audioService.mContext;
                AudioSettingsHelper audioSettingsHelper = audioService.mSettingHelper;
                if (SoundAppPolicyManager.sInstance == null) {
                    SoundAppPolicyManager.sInstance = new SoundAppPolicyManager(context, audioSettingsHelper);
                }
                audioService.mSoundAppPolicyManager = SoundAppPolicyManager.sInstance;
                final int intValue = audioService.mSettingHelper.getIntValue(0, "APP_LIST_VERSION");
                AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioService audioService2 = AudioService.this;
                        if (2022070700 > intValue) {
                            SoundAppPolicyManager soundAppPolicyManager = audioService2.mSoundAppPolicyManager;
                            AudioSettingsHelper audioSettingsHelper2 = soundAppPolicyManager.mSettingHelper;
                            Trace.traceBegin(256L, "setDefaultAllowList");
                            try {
                                Log.i("SoundAppPolicyManager", "SoundAppPolicy setDefaultWhiteList()");
                                audioSettingsHelper2.resetAllowedListTable();
                                soundAppPolicyManager.loadDefaultAllowedPackageList(R.array.common_nicknames, "bt_game_latency_deny");
                                soundAppPolicyManager.loadDefaultAllowedPackageList(R.array.config_allowedGlobalInstantAppSettings, "delay_loss_audio_focus");
                                soundAppPolicyManager.loadDefaultAllowedPackageList(R.array.config_allowedSecureInstantAppSettings, "karaoke_allow");
                                soundAppPolicyManager.loadDefaultAllowedPackageList(R.array.config_allowedSystemInstantAppSettings, "karaoke_listenback_allow");
                                soundAppPolicyManager.loadDefaultAllowedPackageList(R.array.config_ambientBrighteningThresholds, "media_button_deny");
                                soundAppPolicyManager.loadDefaultAllowedPackageList(R.array.config_ambientDarkeningThresholds, "virtual_vibration_sound_allowance");
                                soundAppPolicyManager.loadDefaultAllowedPackageList(R.array.config_ambientThresholdLevels, "voip_live_translate_allow");
                                audioSettingsHelper2.setIntValue(2022070700, "APP_LIST_VERSION");
                            } finally {
                            }
                        } else {
                            int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                        }
                        Log.i("AS.AudioService", "SoundAppPolicy APP_LIST_VERSION =" + audioService2.mSettingHelper.getIntValue(0, "APP_LIST_VERSION"));
                        SoundAppPolicyManager.setBtGameLatencyList(audioService2.mSettingHelper.getAppList());
                        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                            int intValue2 = audioService2.mSettingHelper.getIntValue(-1, "LIVE_TRANSLATE_ALLOW_LIST_VERSION");
                            DirEncryptService$$ExternalSyntheticOutline0.m(intValue2, "CallPplicy LIVE_TRANSLATE_ALLOW_LIST_VERSION =", "AS.AudioService");
                            if (intValue2 != -1) {
                                AudioSettingsHelper audioSettingsHelper3 = audioService2.mSettingHelper;
                                if (audioSettingsHelper3.mCallPolicyAllowList.isEmpty()) {
                                    try {
                                        Cursor query = audioSettingsHelper3.mDatabase.query("call_policy_category_packages", new String[]{"_package", "_category"}, null, null, null, null, null);
                                        try {
                                            if (query.moveToFirst()) {
                                                do {
                                                    audioSettingsHelper3.mCallPolicyAllowList.add(new Pair(query.getString(0), query.getString(1)));
                                                } while (query.moveToNext());
                                            }
                                            query.close();
                                        } finally {
                                        }
                                    } catch (Exception e) {
                                        RCPManagerService$$ExternalSyntheticOutline0.m(e, new StringBuilder("initCallPolicyAllowListFromDb error "), "AudioService.DB");
                                    }
                                }
                                if (!audioSettingsHelper3.mCallPolicyAllowList.isEmpty()) {
                                    return;
                                }
                            }
                            SoundAppPolicyManager soundAppPolicyManager2 = audioService2.mSoundAppPolicyManager;
                            AudioSettingsHelper audioSettingsHelper4 = soundAppPolicyManager2.mSettingHelper;
                            Trace.traceBegin(256L, "setDefaultCallPolicyAllowList");
                            try {
                                Log.i("SoundAppPolicyManager", "setDefaultCallPolicyAllowList()");
                                audioSettingsHelper4.resetCallPolicyTable();
                                for (String str : Resources.getSystem().getStringArray(R.array.config_ambientThresholdLevels)) {
                                    audioSettingsHelper4.putCallPolicyAllowList(str, "voip_live_translate_allow");
                                }
                                audioSettingsHelper4.setIntValue(0, "LIVE_TRANSLATE_ALLOW_LIST_VERSION");
                                soundAppPolicyManager2.mLiveTranslateAllowListVersion = 0;
                            } finally {
                            }
                        }
                    }
                });
                Context context2 = audioService.mContext;
                if (PackageListHelper.sInstance == null) {
                    PackageListHelper.sInstance = new PackageListHelper(context2);
                }
                PackageListHelper packageListHelper = PackageListHelper.sInstance;
                audioService.mPackageListHelper = packageListHelper;
                Context context3 = audioService.mContext;
                packageListHelper.getClass();
                Log.d("PackageListHelper", "initPackageList");
                for (int i2 = 0; i2 < packageListHelper.mAllowedPackageList.size(); i2++) {
                    int uidForPackage = AudioUtils.getUidForPackage(context3, (String) packageListHelper.mAllowedPackageList.get(i2));
                    if (uidForPackage != 0) {
                        PackageListHelper.sCategorizer.putPackage(uidForPackage, (String) packageListHelper.mAllowedPackageList.get(i2));
                    }
                }
                if (Rune.SEC_AUDIO_FM_RADIO) {
                    audioService.mAudioSystem.setParameters("g_fmradio_enable=false");
                }
                boolean z = false;
                for (AudioDeviceInfo audioDeviceInfo : AudioManager.getDevicesStatic(2)) {
                    if (audioDeviceInfo.getType() == 1) {
                        Log.i("AS.AudioService", "updateReceiverSupported RCV");
                        z = true;
                    }
                }
                audioService.mDeviceBroker.mReceiverSupported = Boolean.valueOf(z);
                audioService.mDeviceBroker.updateDeviceQuickConnection(2, "", "speaker", true);
                if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
                    VolumeMonitorService.getInstance(audioService.mExt.mContext).mPlaybackActivityMonitor = audioService.mPlaybackMonitor;
                }
                AudioServiceExt audioServiceExt = audioService.mExt;
                audioServiceExt.getClass();
                boolean z2 = Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER;
                AudioSystemAdapter audioSystemAdapter = audioServiceExt.mAudioSystem;
                if (z2) {
                    audioSystemAdapter.setParameters("l_call_voip_extra_volume_enable=" + audioServiceExt.mVoipExtraVolume);
                }
                if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING) {
                    audioSystemAdapter.setParameters("l_call_voip_extra_volume_enable=" + audioServiceExt.mVoipAntiHowling);
                }
                Context context4 = audioServiceExt.mContext;
                CoverHelper coverHelper = audioServiceExt.mCoverHelper;
                coverHelper.getClass();
                coverHelper.mCoverManager = new CoverManager(context4);
                Context context5 = audioServiceExt.mContext;
                ((AlarmManager) context5.getSystemService("alarm")).setRepeating(3, SystemClock.elapsedRealtime() + BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, PendingIntent.getBroadcast(context5, 0, new Intent("com.sec.media.action.AUDIOCORE_LOGGING"), 67108864));
                CoreFxUtils.setAdaptSound(audioServiceExt.mContext, audioServiceExt.mAdaptSoundEnabled);
                CoreFxUtils.setUpScalerMode(audioServiceExt.mUpscalerEnabled);
                audioServiceExt.setNbQualityMode(0);
                if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI && (micModeManager = audioServiceExt.mMicModeManager) != null) {
                    micModeManager.mMicModeType.restoreMicMode(MicModeManager.mCr);
                }
                if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                    audioServiceExt.restoreLiveTranslator();
                }
            }
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishBinderService("audio", this.mService);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LoadSoundEffectReply implements SoundEffectsHelper.OnEffectsLoadCompleteHandler {
        public int mStatus;

        @Override // com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler
        public final synchronized void run(boolean z) {
            this.mStatus = z ? 0 : -1;
            notify();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MediaVolumeStreamState extends VolumeStreamState {
        public final /* synthetic */ AudioService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MediaVolumeStreamState(int i, AudioService audioService, String str) {
            super(i, audioService, str);
            this.this$0 = audioService;
        }

        @Override // com.android.server.audio.AudioService.VolumeStreamState
        public final void applyDeviceVolume_syncVSS(int i) {
            boolean z;
            float f;
            super.applyDeviceVolume_syncVSS(i);
            MultiSoundManager multiSoundManager = this.this$0.mMultiSoundManager;
            if (multiSoundManager != null) {
                MultiSoundManager.PreventOverheatState preventOverheatState = multiSoundManager.mPreventOverheatState;
                if (preventOverheatState.mState && (i & 2) != 0) {
                    preventOverheatState.getClass();
                    try {
                        f = Float.parseFloat(SemAudioSystem.getPolicyParameters("l_volume_prevent_overheat_key;gain"));
                    } catch (NullPointerException | NumberFormatException unused) {
                        f = 1.0f;
                    }
                    preventOverheatState.mLimitedVolumeForOverheat = f;
                    MultiSoundManager.this.setAppVolumeToNative(preventOverheatState.mUid);
                }
            }
            if (Rune.SEC_AUDIO_VOLUME_MONITOR && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
                VolumeMonitorService volumeMonitorService = VolumeMonitorService.getInstance(this.this$0.mExt.mContext);
                int indexDividedBy10 = getIndexDividedBy10(i);
                boolean z2 = this.this$0.mAvrcpAbsVolSupported;
                volumeMonitorService.mBluetoothVolumeIndex = indexDividedBy10;
                if (volumeMonitorService.mAvrcpAbsVolSupported != z2) {
                    volumeMonitorService.mAvrcpAbsVolSupported = z2;
                    z = true;
                } else {
                    z = false;
                }
                synchronized (volumeMonitorService) {
                    try {
                        if (volumeMonitorService.mEnabled) {
                            volumeMonitorService.mSemVolumeMonitor.setBluetoothVolume(indexDividedBy10);
                            if (z) {
                                volumeMonitorService.mSemVolumeMonitor.setAbsoluteVolumeState(z2);
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:6:0x0028, code lost:
        
            if (r0 != false) goto L8;
         */
        @Override // com.android.server.audio.AudioService.VolumeStreamState
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean setIndex(int r4, int r5, java.lang.String r6, boolean r7) {
            /*
                r3 = this;
                java.util.Set r0 = android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET
                java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
                boolean r0 = r0.contains(r1)
                if (r0 != 0) goto L2a
                java.util.Set r0 = android.media.AudioSystem.DEVICE_OUT_ALL_BLE_SET
                java.lang.Integer r1 = java.lang.Integer.valueOf(r5)
                boolean r0 = r0.contains(r1)
                if (r0 == 0) goto L75
                com.android.server.audio.AudioService r0 = r3.this$0
                com.android.server.audio.AudioDeviceBroker r0 = r0.mDeviceBroker
                boolean r0 = r0.isDualModeActive()
                java.lang.String r1 = "isBluetoothDualModeActive="
                java.lang.String r2 = "AS.AudioService"
                com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0.m(r1, r2, r0)
                if (r0 == 0) goto L75
            L2a:
                java.lang.String r0 = "setA2dpDeviceVolume"
                boolean r0 = r6.endsWith(r0)
                if (r0 != 0) goto L4c
                java.lang.String r0 = "onSetA2dpSinkConnectionState"
                boolean r0 = r6.equals(r0)
                if (r0 != 0) goto L4c
                java.lang.String r0 = "enforceBluetoothSafeMediaVolume"
                boolean r0 = r6.equals(r0)
                if (r0 != 0) goto L4c
                com.android.server.audio.AudioService r0 = r3.this$0
                com.android.server.audio.AudioDeviceBroker r0 = r0.mDeviceBroker
                r0.updateIndividualA2dpVolumes(r4)
            L4c:
                com.android.server.audio.AudioService r0 = r3.this$0
                com.android.server.audio.SoundDoseHelper r0 = r0.mSoundDoseHelper
                int r0 = r0.getSafeDeviceMediaVolumeIndex(r5)
                if (r4 <= r0) goto L75
                com.android.server.audio.AudioService r0 = r3.this$0
                com.android.server.audio.SoundDoseHelper r0 = r0.mSoundDoseHelper
                java.lang.Object r1 = r0.mSafeMediaVolumeStateLock
                monitor-enter(r1)
                r2 = 1
                r0.mSafeMediaVolumeStateForBlueTooth = r2     // Catch: java.lang.Throwable -> L72
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L72
                com.android.server.utils.EventLogger r0 = com.android.server.audio.AudioService.sVolumeLogger
                com.android.server.utils.EventLogger$StringEvent r1 = new com.android.server.utils.EventLogger$StringEvent
                java.lang.String r2 = " disable safe index  volIdx:"
                java.lang.String r2 = com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r4, r6, r2)
                r1.<init>(r2)
                r0.enqueue(r1)
                goto L75
            L72:
                r3 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L72
                throw r3
            L75:
                com.android.server.audio.AudioService r0 = r3.this$0
                boolean r1 = r0.mMuteMediaByVibrateOrSilentMode
                if (r1 == 0) goto L9f
                int r0 = r0.mRingerMode
                r1 = 2
                if (r0 == r1) goto L9f
                if (r5 != r1) goto L9f
                int[] r0 = com.android.server.audio.AudioService.MIN_STREAM_VOLUME
                r1 = 3
                r0 = r0[r1]
                if (r4 <= r0) goto L9f
                java.lang.String r0 = "muteMediaStreamOfSpeaker"
                boolean r0 = r6.equals(r0)
                if (r0 != 0) goto L9f
                com.android.server.audio.AudioService r0 = r3.this$0
                r1 = -1
                r0.mSavedSpeakerMediaIndex = r1
                com.samsung.android.server.audio.AudioSettingsHelper r0 = r0.mSettingHelper
                java.lang.String r2 = "speaker_media_index"
                r0.setIntValue(r1, r2)
            L9f:
                boolean r3 = super.setIndex(r4, r5, r6, r7)
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.MediaVolumeStreamState.setIndex(int, int, java.lang.String, boolean):boolean");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MultiVolumeController extends VolumeController {
        public ArrayList mVolumeControllerList;
        public boolean mVolumeStarEnable;

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized void displayVolumeLimiterToast() {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.displayVolumeLimiterToast();
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized boolean isSameBinder(IVolumeController iVolumeController) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                if (Objects.equals(VolumeController.binder((IVolumeController) it.next()), VolumeController.binder(iVolumeController))) {
                    return true;
                }
            }
            return false;
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized void postDismiss() {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.postDismiss();
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized void postDisplaySafeVolumeWarning(int i) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.postDisplaySafeVolumeWarning(i);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized void postMasterMuteChanged(int i) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.postMasterMuteChanged(i);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized void postVolumeChanged(int i, int i2) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.postVolumeChanged(i, i2);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized void removeController(IVolumeController iVolumeController) {
            try {
                Iterator it = this.mVolumeControllerList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (Objects.equals(VolumeController.binder((IVolumeController) it.next()), VolumeController.binder(iVolumeController))) {
                        it.remove();
                        break;
                    }
                }
                if (this.mVolumeControllerList.isEmpty()) {
                    super.setController(null);
                } else {
                    super.setController((IVolumeController) this.mVolumeControllerList.get(0));
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized void setA11yMode(int i) {
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
        public final synchronized void setController(IVolumeController iVolumeController) {
            super.setController(iVolumeController);
            if (iVolumeController != null) {
                this.mVolumeControllerList.add(iVolumeController);
                if (this.mVolumeStarEnable) {
                    super.setA11yMode(100);
                }
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final synchronized void setLayoutDirection(int i) {
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                super.setLayoutDirection(i);
            }
        }

        @Override // com.android.server.audio.AudioService.VolumeController
        public final String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator it = this.mVolumeControllerList.iterator();
            while (it.hasNext()) {
                this.mController = (IVolumeController) it.next();
                sb.append(super.toString());
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHdmiCecVolumeControlFeatureListener implements HdmiControlManager.HdmiCecVolumeControlFeatureListener {
        public MyHdmiCecVolumeControlFeatureListener() {
        }

        public final void onHdmiCecVolumeControlFeature(int i) {
            synchronized (AudioService.this.mHdmiClientLock) {
                try {
                    AudioService audioService = AudioService.this;
                    if (audioService.mHdmiManager == null) {
                        return;
                    }
                    boolean z = true;
                    if (i != 1) {
                        z = false;
                    }
                    audioService.mHdmiCecVolumeControlEnabled = z;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHdmiControlStatusChangeListenerCallback implements HdmiControlManager.HdmiControlStatusChangeListener {
        public MyHdmiControlStatusChangeListenerCallback() {
        }

        public final void onStatusChange(int i, boolean z) {
            synchronized (AudioService.this.mHdmiClientLock) {
                try {
                    AudioService audioService = AudioService.this;
                    if (audioService.mHdmiManager == null) {
                        return;
                    }
                    boolean z2 = true;
                    if (i != 1) {
                        z2 = false;
                    }
                    if (!z2) {
                        z = false;
                    }
                    audioService.updateHdmiCecSinkLocked(z);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RestorableParameters {
        public Map mMap;

        public final void restoreAll() {
            synchronized (this.mMap) {
                ((LinkedHashMap) this.mMap).values().removeIf(new AudioService$$ExternalSyntheticLambda3(3));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RmtSbmxFullVolDeathHandler implements IBinder.DeathRecipient {
        public final IBinder mICallback;

        public RmtSbmxFullVolDeathHandler(IBinder iBinder) {
            this.mICallback = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (RemoteException e) {
                Log.e("AS.AudioService", "can't link to death", e);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.w("AS.AudioService", "Recorder with remote submix at full volume died " + this.mICallback);
            AudioService.this.forceRemoteSubmixFullVolume(false, this.mICallback);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RoleObserver implements OnRoleHoldersChangedListener {
        public final Executor mExecutor;
        public RoleManager mRm;

        public RoleObserver() {
            this.mExecutor = AudioService.this.mContext.getMainExecutor();
        }

        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
            if ("android.app.role.ASSISTANT".equals(str)) {
                synchronized (AudioService.this.mSettingsLock) {
                    AudioService.this.updateAssistantUIdLocked(false);
                }
            }
        }

        public final void register() {
            RoleManager roleManager = (RoleManager) AudioService.this.mContext.getSystemService("role");
            this.mRm = roleManager;
            if (roleManager != null) {
                roleManager.addOnRoleHoldersChangedListenerAsUser(this.mExecutor, this, UserHandle.ALL);
                synchronized (AudioService.this.mSettingsLock) {
                    AudioService.this.updateAssistantUIdLocked(true);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SamsungBroadcastReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AudioService this$0;

        public /* synthetic */ SamsungBroadcastReceiver(AudioService audioService, int i) {
            this.$r8$classId = i;
            this.this$0 = audioService;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void handleIntent(Context context, Intent intent) {
            MediaFocusControl mediaFocusControl;
            String str;
            boolean z = true;
            String action = intent.getAction();
            if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                this.this$0.mMultiSoundManager.updateAudioServiceNotificationChannel();
                return;
            }
            String str2 = null;
            if ("android.intent.action.PACKAGE_REMOVED".equals(action) && !intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
                Log.d("AS.AudioService", schemeSpecificPart + "(" + intExtra + ") is removed");
                BtUtils.checkAndUpdateAuracastApp(intExtra, 0, stringExtra);
                if ("com.samsung.android.oneconnect".equals(schemeSpecificPart)) {
                    MultiSoundManager multiSoundManager = this.this$0.mMultiSoundManager;
                    multiSoundManager.getClass();
                    Iterator it = new ArrayList(multiSoundManager.mPinAppInfoList.values()).iterator();
                    while (it.hasNext()) {
                        MultiSoundManager.MultiSoundItem multiSoundItem = (MultiSoundManager.MultiSoundItem) it.next();
                        if (multiSoundItem.mDevice != 0) {
                            multiSoundManager.setAppDevice(multiSoundItem.mUid, 0, true);
                        }
                    }
                    if (multiSoundManager.mEnabled) {
                        Log.d("AS.MultiSoundManager", "disable");
                        multiSoundManager.mEnabled = false;
                        multiSoundManager.resetByEnableDisable();
                        AudioService.this.mMultiSoundManager.mNm.cancel(1004);
                    }
                } else {
                    if (this.this$0.mMultiSoundManager.getAppDevice(intExtra, true) != 0) {
                        MultiSoundManager multiSoundManager2 = this.this$0.mMultiSoundManager;
                        synchronized (multiSoundManager2.mMultiSoundLock) {
                            if (multiSoundManager2.mPinAppInfoList.get(Integer.valueOf(intExtra)) != null) {
                                Log.d("AS.MultiSoundManager", "removeItem, " + intExtra);
                                multiSoundManager2.mPinAppInfoList.remove(Integer.valueOf(intExtra));
                                multiSoundManager2.setDeviceToNative(intExtra, 0);
                                String string = Settings.System.getString(this.this$0.mContentResolver, "multisound_app");
                                Log.d("AS.AudioService", "SEC_AUDIO_MULTI_SOUND::ACTION_PACKAGE_REMOVED ( current Packagelist = " + string);
                                if (!TextUtils.isEmpty(string)) {
                                    for (String str3 : string.split(":")) {
                                        if (!TextUtils.isEmpty(str3) && !TextUtils.equals(str3, schemeSpecificPart)) {
                                            str2 = TextUtils.isEmpty(str2) ? str3 : AnyMotionDetector$$ExternalSyntheticOutline0.m(str2, ":", str3);
                                        }
                                    }
                                }
                                if (TextUtils.isEmpty(str2)) {
                                    AudioService.sendMsg(this.this$0.mAudioHandler, 2763, 2, 0, 0, Boolean.FALSE, 0);
                                    MultiSoundManager multiSoundManager3 = this.this$0.mMultiSoundManager;
                                    multiSoundManager3.getClass();
                                    ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(multiSoundManager3.mContext, R.style.Theme.DeviceDefault.Light);
                                    Context context2 = multiSoundManager3.mContext;
                                    Toast.makeText(contextThemeWrapper, context2.getString(R.string.restr_pin_enter_old_pin, context2.getResources().getText(R.string.roamingText0)), 0).show();
                                }
                                DualAppManagerService$$ExternalSyntheticOutline0.m("SEC_AUDIO_MULTI_SOUND::ACTION_PACKAGE_REMOVED ( new Packagelist = ", str2, "AS.AudioService");
                                Settings.System.putString(this.this$0.mContentResolver, "multisound_app", str2);
                            }
                        }
                    }
                    MediaFocusControl mediaFocusControl2 = this.this$0.mMediaFocusControl;
                    if (mediaFocusControl2.mIgnoredUid == intExtra) {
                        mediaFocusControl2.setIgnoreAudioFocus(intExtra, false);
                    }
                    AudioService audioService = this.this$0;
                    PackageListHelper packageListHelper = audioService.mPackageListHelper;
                    Context context3 = audioService.mContext;
                    packageListHelper.getClass();
                    PackageListHelper.removePackageForName(context3, schemeSpecificPart);
                    this.this$0.mMultiSoundManager.setAppVolume(intExtra, 100);
                }
                if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
                    AudioService audioService2 = this.this$0;
                    KaraokeUtils.checkAndBroadcastKaraokeInstalled(audioService2.mContext, audioService2.mSettingHelper, schemeSpecificPart, false);
                }
                this.this$0.mAppVolumeFromSoundAssistant.delete(intExtra);
                if ("com.samsung.android.soundassistant".equals(schemeSpecificPart)) {
                    AudioService.m265$$Nest$mclearSoundAssistantSettings(this.this$0);
                }
                AudioService.m281$$Nest$munSetSoundSettingEventBroadcastIntent(this.this$0, schemeSpecificPart);
                if (this.this$0.mAudioGameManager == null || !AudioGameManager.isGamePackager(schemeSpecificPart)) {
                    return;
                }
                AudioGameManager audioGameManager = this.this$0.mAudioGameManager;
                synchronized (audioGameManager.mUidList) {
                    try {
                        if (audioGameManager.mUidList.contains(Integer.valueOf(intExtra))) {
                            audioGameManager.mUidList.remove(Integer.valueOf(intExtra));
                            audioGameManager.setParamGameUidList();
                        }
                    } finally {
                    }
                }
                return;
            }
            if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(action)) {
                String schemeSpecificPart2 = intent.getData().getSchemeSpecificPart();
                Log.d("AS.AudioService", schemeSpecificPart2 + "(" + intent.getIntExtra("android.intent.extra.UID", -1) + ") is data cleared");
                if ("com.samsung.android.soundassistant".equals(schemeSpecificPart2)) {
                    AudioService audioService3 = this.this$0;
                    int i = AudioService.BECOMING_NOISY_DELAY_MS;
                    audioService3.getClass();
                    Intent intent2 = new Intent("android.intent.action.VOLUMESTAR_SETTING_CHANGED");
                    intent2.putExtra("changed_setting", "volumestar_enabled");
                    intent2.setPackage(Constants.SYSTEMUI_PACKAGE_NAME);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        audioService3.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL, "com.samsung.systemui.permission.SPLUGIN");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        AudioService.m265$$Nest$mclearSoundAssistantSettings(this.this$0);
                        AudioService.m281$$Nest$munSetSoundSettingEventBroadcastIntent(this.this$0, schemeSpecificPart2);
                        AudioServiceExt audioServiceExt = this.this$0.mExt;
                        audioServiceExt.getClass();
                        AudioExecutor.execute(new AudioServiceExt$$ExternalSyntheticLambda1(0 == true ? 1 : 0, audioServiceExt));
                        return;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                return;
            }
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                String schemeSpecificPart3 = intent.getData().getSchemeSpecificPart();
                int intExtra2 = intent.getIntExtra("android.intent.extra.UID", -1);
                if (this.this$0.isInAllowedList(schemeSpecificPart3)) {
                    AudioService audioService4 = this.this$0;
                    PackageListHelper packageListHelper2 = audioService4.mPackageListHelper;
                    Context context4 = audioService4.mContext;
                    packageListHelper2.getClass();
                    PackageListHelper.addPackage(context4, schemeSpecificPart3);
                }
                if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
                    AudioService audioService5 = this.this$0;
                    KaraokeUtils.checkAndBroadcastKaraokeInstalled(audioService5.mContext, audioService5.mSettingHelper, schemeSpecificPart3, true);
                }
                if (this.this$0.mAudioGameManager != null && AudioGameManager.isGamePackager(schemeSpecificPart3)) {
                    this.this$0.mAudioGameManager.addGameUid(intExtra2, true);
                }
                BtUtils.checkAndUpdateAuracastApp(intExtra2, 1, schemeSpecificPart3);
                return;
            }
            if ("android.intent.action.TurnOff_MultiSound".equals(action)) {
                AudioService.sendMsg(this.this$0.mAudioHandler, 2763, 2, 0, 0, Boolean.FALSE, 0);
                Intent intent3 = new Intent();
                intent3.setPackage("com.samsung.android.setting.multisound");
                intent3.setAction("com.samsung.intent.action.MULTISOUND_STATE_CHANGED");
                intent3.putExtra("enabled", false);
                this.this$0.sendBroadcastToAll(intent3, null);
                return;
            }
            if ("android.intent.action.SAS_NOTIFICATION_CLEAR".equals(action)) {
                this.this$0.mMultiSoundManager.mNm.cancel(1004);
                return;
            }
            if ("com.samsung.android.audio.headup.changedevice".equals(action)) {
                AudioService audioService6 = this.this$0;
                int priorityDevice = audioService6.mDeviceBroker.getPriorityDevice(audioService6.getPinDeviceInternal());
                AudioService audioService7 = this.this$0;
                audioService7.setDeviceToForceByUser(priorityDevice, audioService7.mDeviceBroker.getAddressForDevice(priorityDevice), false);
                this.this$0.mMultiSoundManager.mNm.cancel(1005);
                return;
            }
            if ("com.samsung.intent.action.DLNA_STATUS_CHANGED".equals(action)) {
                int intExtra3 = intent.getIntExtra(Constants.JSON_CLIENT_DATA_STATUS, 0);
                ScreenSharingHelper screenSharingHelper = this.this$0.mScreenSharingHelper;
                boolean z2 = intExtra3 == 1;
                screenSharingHelper.mIsDLNAEnabled = z2;
                screenSharingHelper.setMirroringPolicyParameter(!z2);
                RCPManagerService$$ExternalSyntheticOutline0.m("AS.AudioService", new StringBuilder("mIsDLNAEnabled:"), this.this$0.mScreenSharingHelper.mIsDLNAEnabled);
                return;
            }
            if ("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE".equals(action)) {
                int intExtra4 = intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0);
                int deviceForStream = this.this$0.getDeviceForStream(3);
                boolean z3 = ScreenSharingHelper.sIsWifiDisplayConnected;
                Log.i("AS.AudioService", "WifiDisplay device state:" + intExtra4 + " isConnected:" + z3);
                AudioDeviceInventory audioDeviceInventory = this.this$0.mDeviceBroker.mDeviceInventory;
                synchronized (audioDeviceInventory.mDevicesLock) {
                    audioDeviceInventory.checkSendBecomingNoisyIntentInt(32768, intExtra4, deviceForStream);
                }
                this.this$0.mDeviceBroker.updateDeviceQuickConnection(32768, "0", "remote_submix", intExtra4 == 1);
                if (intExtra4 == 1 && !z3) {
                    Log.d("AS.AudioService", "WifiDisplay is connected.");
                    AudioService audioService8 = this.this$0;
                    ScreenSharingHelper screenSharingHelper2 = audioService8.mScreenSharingHelper;
                    Context context5 = audioService8.mContext;
                    screenSharingHelper2.getClass();
                    if (Settings.Secure.getInt(context5.getContentResolver(), "odi_captions_enabled", 0) == 1) {
                        ((HashSet) this.this$0.mFullVolumeDevices).remove(32768);
                        ((HashSet) this.this$0.mFixedVolumeDevices).remove(32768);
                        this.this$0.mStreamStates[3].checkFixedVolumeDevices();
                        this.this$0.mStreamStates[3].applyAllVolumes();
                    }
                    ScreenSharingHelper.sIsWifiDisplayConnected = true;
                    AudioService audioService9 = this.this$0;
                    ScreenSharingHelper screenSharingHelper3 = audioService9.mScreenSharingHelper;
                    Context context6 = audioService9.mContext;
                    screenSharingHelper3.getClass();
                    if (!Rune.SEC_AUDIO_VOIP_VIA_SMART_VIEW || screenSharingHelper3.mDisplayManager.getWifiDisplayStatus().getActiveDisplay() == null) {
                        str = "l_smart_view_enable=true";
                    } else {
                        byte b = screenSharingHelper3.mDisplayManager.getWifiDisplayStatus().getConnectedState() == 0;
                        str = "l_smart_view_enable=true;support_voip=".concat(b != false ? "true" : "false");
                        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI && b != false) {
                            MicModeManager.getInstance(screenSharingHelper3.mContext).updateState(8, true);
                        }
                    }
                    SemAudioSystem.setPolicyParameters(str);
                    screenSharingHelper3.mDisplayManager.semRegisterDeviceStatusListener(screenSharingHelper3.mSemDeviceStatusListener, null);
                    IntentFilter intentFilter = new IntentFilter("com.samsung.intent.action.SEC_PRESENTATION_START_SMARTVIEW");
                    intentFilter.addAction("com.samsung.intent.action.SEC_PRESENTATION_STOP_SMARTVIEW");
                    context6.registerReceiver(screenSharingHelper3.mPresentationModeReceiver, intentFilter, 4);
                } else if (intExtra4 == 0 && z3) {
                    Log.d("AS.AudioService", "WifiDisplay is disconnected.");
                    AudioService audioService10 = this.this$0;
                    ScreenSharingHelper screenSharingHelper4 = audioService10.mScreenSharingHelper;
                    Context context7 = audioService10.mContext;
                    screenSharingHelper4.getClass();
                    if (Settings.Secure.getInt(context7.getContentResolver(), "odi_captions_enabled", 0) == 1) {
                        ((HashSet) this.this$0.mFullVolumeDevices).add(32768);
                        ((HashSet) this.this$0.mFixedVolumeDevices).add(32768);
                        this.this$0.mStreamStates[3].checkFixedVolumeDevices();
                        this.this$0.mStreamStates[3].applyAllVolumes();
                    }
                    ScreenSharingHelper.sIsWifiDisplayConnected = false;
                    if (ScreenSharingHelper.sSplitSoundEnabled && this.this$0.isInCommunication()) {
                        this.this$0.mDeviceBroker.sendIILMsg(12, 0, 0, 0, null, 0);
                    }
                    ScreenSharingHelper.sSplitSoundEnabled = false;
                    AudioService audioService11 = this.this$0;
                    ScreenSharingHelper screenSharingHelper5 = audioService11.mScreenSharingHelper;
                    Context context8 = audioService11.mContext;
                    screenSharingHelper5.getClass();
                    SemAudioSystem.setPolicyParameters("l_smart_view_enable=false");
                    SemAudioSystem.setPolicyParameters("l_smart_view_split_sound_enable=" + ScreenSharingHelper.sSplitSoundEnabled);
                    screenSharingHelper5.mDisplayManager.semUnregisterDeviceStatusListener(screenSharingHelper5.mSemDeviceStatusListener);
                    context8.unregisterReceiver(screenSharingHelper5.mPresentationModeReceiver);
                    if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
                        MicModeManager.getInstance(screenSharingHelper5.mContext).updateState(8, false);
                    }
                }
                if (intExtra4 == 1) {
                    this.this$0.mScreenSharingHelper.setSupportDisplayVolumeControl(intent.getBooleanExtra("isSupportDisplayVolumeControl", false));
                } else {
                    this.this$0.mScreenSharingHelper.setSupportDisplayVolumeControl(false);
                }
                ScreenSharingHelper screenSharingHelper6 = this.this$0.mScreenSharingHelper;
                screenSharingHelper6.mScreenSharingStateResumed = screenSharingHelper6.mIsSupportDisplayVolumeControl;
                RCPManagerService$$ExternalSyntheticOutline0.m("AS.AudioService", new StringBuilder("isSupportDisplayVolumeControl:"), this.this$0.mScreenSharingHelper.mIsSupportDisplayVolumeControl);
                AudioService audioService12 = this.this$0;
                audioService12.setMultiSoundOn(audioService12.isMultiSoundOn());
                return;
            }
            if ("com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED".equals(action)) {
                if (intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, 0) == 1) {
                    this.this$0.mScreenSharingHelper.setSupportDisplayVolumeControl(intent.getBooleanExtra("isSupportDisplayVolumeControl", false));
                } else {
                    this.this$0.mScreenSharingHelper.setSupportDisplayVolumeControl(false);
                }
                ScreenSharingHelper screenSharingHelper7 = this.this$0.mScreenSharingHelper;
                screenSharingHelper7.mScreenSharingStateResumed = screenSharingHelper7.mIsSupportDisplayVolumeControl;
                RCPManagerService$$ExternalSyntheticOutline0.m("AS.AudioService", new StringBuilder("onReceive SEM_WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED isSupportDisplayVolumeControl:"), this.this$0.mScreenSharingHelper.mIsSupportDisplayVolumeControl);
                AudioService audioService13 = this.this$0;
                audioService13.setMultiSoundOn(audioService13.isMultiSoundOn());
                return;
            }
            if ("com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE".equals(action)) {
                SemAudioSystem.setPolicyParameters("l_smart_view_fixed_volume_enable=".concat(intent.getIntExtra("CONNECTION_MODE", 0) == 1 ? "true" : "false"));
                return;
            }
            if ("com.samsung.android.scpm.policy.UPDATE.Audio".equals(action)) {
                AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("Receive SCPM update intent : Audio"));
                if (this.this$0.mSoundAppPolicyManager != null) {
                    final byte b2 = 0 == true ? 1 : 0;
                    AudioExecutor.execute(new Runnable(this) { // from class: com.android.server.audio.AudioService$SamsungBroadcastReceiver$$ExternalSyntheticLambda0
                        public final /* synthetic */ AudioService.SamsungBroadcastReceiver f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i2 = b2;
                            AudioService.SamsungBroadcastReceiver samsungBroadcastReceiver = this.f$0;
                            switch (i2) {
                                case 0:
                                    samsungBroadcastReceiver.this$0.mSoundAppPolicyManager.checkAndUpdateAppList();
                                    break;
                                default:
                                    samsungBroadcastReceiver.this$0.mSoundAppPolicyManager.checkAndUpdateLiveTranslateList();
                                    break;
                            }
                        }
                    });
                    return;
                }
                return;
            }
            if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
                AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("Receive SCPM clear intent"));
                AudioService audioService14 = this.this$0;
                if (audioService14.mSoundAppPolicyManager != null) {
                    audioService14.mSettingHelper.setIntValue(-1, "APP_LIST_VERSION");
                    if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                        this.this$0.mSettingHelper.setIntValue(-1, "LIVE_TRANSLATE_ALLOW_LIST_VERSION");
                    }
                    SoundAppPolicyManager soundAppPolicyManager = this.this$0.mSoundAppPolicyManager;
                    soundAppPolicyManager.mToken = null;
                    Executors.newSingleThreadScheduledExecutor().schedule(new SoundAppPolicyManager$$ExternalSyntheticLambda0(soundAppPolicyManager, context), 60L, TimeUnit.SECONDS);
                    return;
                }
                return;
            }
            if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE && "com.samsung.android.scpm.policy.UPDATE.voip-live-translate-allow-list-a7f6".equals(action)) {
                AudioService.sScpmLogger.enqueue(new EventLogger.StringEvent("Receive SCPM update intent : voip-live-translate-allow-list"));
                if (this.this$0.mSoundAppPolicyManager != null) {
                    final int i2 = true ? 1 : 0;
                    AudioExecutor.execute(new Runnable(this) { // from class: com.android.server.audio.AudioService$SamsungBroadcastReceiver$$ExternalSyntheticLambda0
                        public final /* synthetic */ AudioService.SamsungBroadcastReceiver f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i22 = i2;
                            AudioService.SamsungBroadcastReceiver samsungBroadcastReceiver = this.f$0;
                            switch (i22) {
                                case 0:
                                    samsungBroadcastReceiver.this$0.mSoundAppPolicyManager.checkAndUpdateAppList();
                                    break;
                                default:
                                    samsungBroadcastReceiver.this$0.mSoundAppPolicyManager.checkAndUpdateLiveTranslateList();
                                    break;
                            }
                        }
                    });
                    return;
                }
                return;
            }
            if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
                this.this$0.mDeviceBroker.sendLMsgNoDelay(55, 2, intent);
                return;
            }
            if ("com.sec.media.action.mute_interval".equals(action)) {
                AudioService audioService15 = this.this$0;
                if (Settings.Global.getInt(audioService15.mContentResolver, "mode_ringer_time_on", 0) == 1) {
                    audioService15.mMuteIntervalMs = 0;
                    audioService15.setRingerMode(audioService15.mPrevRingerMode, "checkMuteInterval", false);
                    Settings.Global.putInt(audioService15.mContentResolver, "mode_ringer_time_on", 0);
                    return;
                }
                return;
            }
            if ("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED".equals(action)) {
                this.this$0.mDeviceBroker.setDualA2dpMode(null, intent.getBooleanExtra("enable", false));
                return;
            }
            if ("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED".equals(action)) {
                this.this$0.mDeviceBroker.sendLMsgNoDelay(2761, 2, (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                return;
            }
            if ("com.sec.android.intent.action.SPLIT_SOUND".equals(action)) {
                ScreenSharingHelper.sSplitSoundEnabled = intent.getBooleanExtra("enabled", false);
                return;
            }
            if ("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED".equals(action)) {
                int intExtra5 = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
                int intExtra6 = intent.getIntExtra("com.samsung.android.bluetooth.cast.device.extra.REMOTEROLE", 0);
                int deviceForStream2 = this.this$0.getDeviceForStream(3);
                AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(intExtra5, intExtra6, "BT cast device state:", " role : ", "AS.AudioService");
                if (intExtra5 == 2 || intExtra5 == 0) {
                    AudioService audioService16 = this.this$0;
                    audioService16.mIsBluetoothCastState = intExtra5 == 2;
                    AudioDeviceInventory audioDeviceInventory2 = audioService16.mDeviceBroker.mDeviceInventory;
                    synchronized (audioDeviceInventory2.mDevicesLock) {
                        audioDeviceInventory2.checkSendBecomingNoisyIntentInt(32768, intExtra5, deviceForStream2);
                    }
                    if (intExtra6 == 2) {
                        this.this$0.mDeviceBroker.updateDeviceQuickConnection(32768, "0", "remote_submix", intExtra5 == 2);
                        return;
                    }
                    return;
                }
                return;
            }
            if ("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED".equals(action)) {
                AudioService audioService17 = this.this$0;
                if (audioService17.mGoodCatchManager == null) {
                    audioService17.mGoodCatchManager = new GoodCatchManager(audioService17.mContext, "AudioService");
                    return;
                }
                return;
            }
            if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR && "com.samsung.bluetooth.device.action.AUDIO_TYPE_CHANGED".equals(action)) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                BluetoothA2dp a2dp = this.this$0.mDeviceBroker.getA2dp();
                if (bluetoothDevice == null || a2dp == null) {
                    return;
                }
                BluetoothDevice activeDevice = a2dp.getActiveDevice();
                if (intent.getIntExtra("com.samsung.bluetooth.device.extra.AUDIO_TYPE", 0) == 2) {
                    if (activeDevice == null || !bluetoothDevice.getAddress().equals(activeDevice.getAddress())) {
                        return;
                    }
                    BtUtils.setBtVolumeMonitor(true);
                    return;
                }
                if (activeDevice == null || !bluetoothDevice.getAddress().equals(activeDevice.getAddress())) {
                    return;
                }
                BtUtils.setBtVolumeMonitor(false);
                return;
            }
            if ("androidx.car.app.connection.action.CAR_CONNECTION_UPDATED".equals(action)) {
                AudioService audioService18 = this.this$0;
                Cursor query = audioService18.mContext.getContentResolver().query(audioService18.PROJECTION_HOST_URI, new String[]{"CarConnectionState"}, null, null, null);
                if (query == null) {
                    Log.w("AS.AudioService", "[Android Auto] Null response from content provider when checking connection to the car, treating as disconnected");
                } else {
                    if (query.getCount() <= 0) {
                        Log.w("AS.AudioService", "[Android Auto] Connection Count is 0, treating as disconnected");
                    } else if (query.moveToFirst()) {
                        int columnIndex = query.getColumnIndex("CarConnectionState");
                        if (columnIndex < 0) {
                            Log.w("AS.AudioService", "[Android Auto] Connection to car response is missing the connection type, treating as disconnected");
                        } else {
                            if (query.getInt(columnIndex) != 0) {
                                Log.i("AS.AudioService", "[Android Auto] connected");
                                query.close();
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m("received ACTION_CAR_CONNECTION_UPDATED : state = ", "AS.AudioService", z);
                                AudioService audioService19 = this.this$0;
                                audioService19.mConnectedAndroidAuto = z;
                                if (z || (mediaFocusControl = audioService19.mMediaFocusControl) == null) {
                                }
                                mediaFocusControl.clearMultiAudiofocusfromAndroidAuto();
                                return;
                            }
                            Log.i("AS.AudioService", "[Android Auto] disconnected");
                        }
                    } else {
                        Log.w("AS.AudioService", "[Android Auto] Connection to car response is empty, treating as disconnected");
                    }
                    query.close();
                }
                z = false;
                AccessibilityManagerService$$ExternalSyntheticOutline0.m("received ACTION_CAR_CONNECTION_UPDATED : state = ", "AS.AudioService", z);
                AudioService audioService192 = this.this$0;
                audioService192.mConnectedAndroidAuto = z;
                if (z) {
                }
            }
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            char c;
            char c2;
            ActivityRecord activity;
            switch (this.$r8$classId) {
                case 0:
                    try {
                        handleIntent(context, intent);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                default:
                    String action = intent.getAction();
                    if (action.equals("android.intent.action.DOCK_EVENT")) {
                        int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
                        int i = intExtra != 1 ? intExtra != 2 ? intExtra != 3 ? intExtra != 4 ? 0 : 9 : 8 : 6 : 7;
                        if (intExtra != 3 && (intExtra != 0 || this.this$0.mDockState != 3)) {
                            this.this$0.mDeviceBroker.setForceUse_Async(3, i, "ACTION_DOCK_EVENT intent");
                        }
                        AudioService audioService = this.this$0;
                        DesktopModeHelper desktopModeHelper = audioService.mDesktopModeHelper;
                        int i2 = audioService.mDockState;
                        desktopModeHelper.getClass();
                        if (i2 == 0 && intExtra == 114) {
                            desktopModeHelper.mDexPadConnectedState = true;
                            desktopModeHelper.setDexParameter("pad", true);
                        } else if (i2 == 114 && intExtra == 0) {
                            desktopModeHelper.mDexPadConnectedState = false;
                            desktopModeHelper.setDexParameter("pad", false);
                        } else if (i2 == 0 && intExtra == 110) {
                            desktopModeHelper.mDexConnectedState = true;
                            desktopModeHelper.setDexParameter("station", true);
                        } else if (i2 == 110 && intExtra == 0) {
                            desktopModeHelper.mDexConnectedState = false;
                            desktopModeHelper.setDexParameter("station", false);
                        }
                        this.this$0.mDockState = intExtra;
                        return;
                    }
                    if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED") || action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                        this.this$0.mDeviceBroker.sendLMsgNoDelay(55, 2, intent);
                        return;
                    }
                    if (action.equals("android.intent.action.SCREEN_ON")) {
                        if (this.this$0.mMonitorRotation) {
                            RotationHelper.enable();
                        }
                        AudioSystem.setParameters("screen_state=on");
                        return;
                    }
                    if (action.equals("android.intent.action.SCREEN_OFF")) {
                        if (this.this$0.mMonitorRotation) {
                            RotationHelper.disable();
                        }
                        AudioSystem.setParameters("screen_state=off");
                        return;
                    }
                    if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                        AudioService.sendMsg(this.this$0.mAudioHandler, 54, 0, 0, 0, null, 0);
                        return;
                    }
                    char c3 = 65535;
                    ComponentName componentName = null;
                    if (action.equals("android.intent.action.USER_SWITCHED")) {
                        MediaFocusControl mediaFocusControl = this.this$0.mMediaFocusControl;
                        mediaFocusControl.getClass();
                        synchronized (MediaFocusControl.mAudioFocusLock) {
                            try {
                                if (!mediaFocusControl.mFocusStack.empty()) {
                                    FocusRequester focusRequester = (FocusRequester) mediaFocusControl.mFocusStack.peek();
                                    focusRequester.getClass();
                                    UserProperties userProperties = ((UserManagerInternal) LocalServices.getService(UserManagerInternal.class)).getUserProperties(UserHandle.getUserId(focusRequester.mCallingUid));
                                    if (userProperties == null || !userProperties.getAlwaysVisible()) {
                                        mediaFocusControl.mFocusStack.pop();
                                        focusRequester.handleFocusLoss(-1, null, false);
                                        focusRequester.release();
                                    }
                                }
                                AudioService audioService2 = this.this$0;
                                if (audioService2.mUserSwitchedReceived) {
                                    audioService2.mDeviceBroker.sendIILMsg(12, 0, 0, 0, null, 0);
                                }
                            } finally {
                            }
                        }
                        AudioService audioService3 = this.this$0;
                        audioService3.mUserSwitchedReceived = true;
                        if (audioService3.mSupportsMicPrivacyToggle) {
                            audioService3.mMicMuteFromPrivacyToggle = audioService3.mSensorPrivacyManagerInternal.isSensorPrivacyEnabled(AudioService.getCurrentUserId(), 1);
                            AudioService audioService4 = this.this$0;
                            audioService4.getClass();
                            audioService4.setMicrophoneMuteNoCallerCheck(AudioService.getCurrentUserId());
                        }
                        this.this$0.readAudioSettings(true);
                        AudioService audioService5 = this.this$0;
                        AudioService.sendMsg(audioService5.mAudioHandler, 10, 2, 0, 0, audioService5.mStreamStates[3], 0);
                        return;
                    }
                    if (action.equals("android.intent.action.USER_BACKGROUND")) {
                        int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", -1);
                        if (intExtra2 >= 0) {
                            UserInfo userInfo = UserManagerService.getInstance().getUserInfo(intExtra2);
                            PackageManager packageManager = this.this$0.mContext.getPackageManager();
                            if (!userInfo.isManagedProfile()) {
                                ActivityTaskManagerInternal activityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
                                int i3 = userInfo.id;
                                ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
                                WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock) {
                                    try {
                                        Task task = ActivityTaskManagerService.this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().mRootHomeTask;
                                        if (task == null) {
                                            activity = null;
                                        } else {
                                            PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new TaskDisplayArea$$ExternalSyntheticLambda2(), PooledLambda.__(ActivityRecord.class), Integer.valueOf(i3));
                                            activity = task.getActivity(obtainPredicate);
                                            obtainPredicate.recycle();
                                        }
                                        if (activity != null) {
                                            componentName = activity.mActivityComponent;
                                        }
                                    } catch (Throwable th) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        throw th;
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                            try {
                                List list = AppGlobals.getPackageManager().getPackagesHoldingPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 0L, userInfo.id).getList();
                                for (int size = list.size() - 1; size >= 0; size--) {
                                    PackageInfo packageInfo = (PackageInfo) list.get(size);
                                    if (UserHandle.getAppId(packageInfo.applicationInfo.uid) >= 10000 && packageManager.checkPermission("android.permission.INTERACT_ACROSS_USERS", packageInfo.packageName) != 0 && (componentName == null || !packageInfo.packageName.equals(componentName.getPackageName()) || !packageInfo.applicationInfo.isSystemApp())) {
                                        try {
                                            int i4 = packageInfo.applicationInfo.uid;
                                            ActivityManager.getService().killUid(UserHandle.getAppId(i4), UserHandle.getUserId(i4), "killBackgroundUserProcessesWithAudioRecordPermission");
                                        } catch (RemoteException e2) {
                                            Log.w("AS.AudioService", "Error calling killUid", e2);
                                        }
                                    }
                                }
                            } catch (RemoteException e3) {
                                throw new AndroidRuntimeException(e3);
                            }
                        }
                        try {
                            UserManagerService.getInstance().setUserRestriction("no_record_audio", true, intExtra2);
                            return;
                        } catch (IllegalArgumentException e4) {
                            Slog.w("AS.AudioService", "Failed to apply DISALLOW_RECORD_AUDIO restriction: " + e4);
                            return;
                        }
                    }
                    if (action.equals("android.intent.action.USER_FOREGROUND")) {
                        try {
                            UserManagerService.getInstance().setUserRestriction("no_record_audio", false, intent.getIntExtra("android.intent.extra.user_handle", -1));
                            return;
                        } catch (IllegalArgumentException e5) {
                            Slog.w("AS.AudioService", "Failed to apply DISALLOW_RECORD_AUDIO restriction: " + e5);
                            return;
                        }
                    }
                    if (action.equals("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION") || action.equals("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION")) {
                        MusicFxHelper musicFxHelper = this.this$0.mMusicFxHelper;
                        musicFxHelper.getClass();
                        String str = intent.getPackage();
                        if (str != null) {
                            Log.w("AS.MusicFxHelper", "effect broadcast already targeted to ".concat(str));
                            return;
                        }
                        PackageManager packageManager2 = context.getPackageManager();
                        List<ResolveInfo> queryBroadcastReceivers = packageManager2.queryBroadcastReceivers(intent, 0);
                        if (queryBroadcastReceivers != null && queryBroadcastReceivers.size() != 0) {
                            ResolveInfo resolveInfo = queryBroadcastReceivers.get(0);
                            String stringExtra = intent.getStringExtra("android.media.extra.PACKAGE_NAME");
                            if (stringExtra == null) {
                                Log.w("AS.MusicFxHelper", "Intent package name must not be null");
                                return;
                            }
                            if (resolveInfo != null) {
                                try {
                                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                                    if (activityInfo != null && activityInfo.packageName != null) {
                                        int packageUidAsUser = packageManager2.getPackageUidAsUser(stringExtra, PackageManager.PackageInfoFlags.of(4194304L), MusicFxHelper.getCurrentUserId());
                                        intent.addFlags(32);
                                        intent.setPackage(resolveInfo.activityInfo.packageName);
                                        if (musicFxHelper.setMusicFxServiceWithObserver(intent, stringExtra, packageUidAsUser)) {
                                            context.sendBroadcastAsUser(intent, UserHandle.ALL);
                                            return;
                                        }
                                        return;
                                    }
                                } catch (PackageManager.NameNotFoundException e6) {
                                    Log.e("AS.MusicFxHelper", "Not able to find UID from package: " + stringExtra + " error: " + e6);
                                }
                            }
                        }
                        Log.w("AS.MusicFxHelper", "couldn't find receiver package for effect intent");
                        return;
                    }
                    if (!action.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                        if (action.equals("com.android.server.audio.action.CHECK_MUSIC_ACTIVE")) {
                            AudioService audioService6 = this.this$0;
                            SoundDoseHelper soundDoseHelper = audioService6.mSoundDoseHelper;
                            audioService6.mAudioSystem.getClass();
                            boolean isStreamActive = AudioSystem.isStreamActive(3, 0);
                            synchronized (soundDoseHelper.mSafeMediaVolumeStateLock) {
                                try {
                                    if (soundDoseHelper.mSafeMediaVolumeState == 2) {
                                        int deviceForStream = soundDoseHelper.mAudioService.getDeviceForStream(3);
                                        if (soundDoseHelper.safeDevicesContains(deviceForStream) && isStreamActive) {
                                            soundDoseHelper.scheduleMusicActiveCheck();
                                            if (soundDoseHelper.mAudioService.getVssVolumeForDevice(3, deviceForStream) > soundDoseHelper.safeMediaVolumeIndex(deviceForStream)) {
                                                long elapsedRealtime = SystemClock.elapsedRealtime();
                                                long j = soundDoseHelper.mLastMusicActiveTimeMs;
                                                if (j != 0) {
                                                    soundDoseHelper.mMusicActiveMs += (int) (elapsedRealtime - j);
                                                }
                                                soundDoseHelper.mLastMusicActiveTimeMs = elapsedRealtime;
                                                Log.i("AS.SoundDoseHelper", "onCheckMusicActive() mMusicActiveMs: " + soundDoseHelper.mMusicActiveMs);
                                                if (soundDoseHelper.mMusicActiveMs > 72000000) {
                                                    soundDoseHelper.setSafeMediaVolumeEnabled("com.android.server.audio.action.CHECK_MUSIC_ACTIVE", true);
                                                    soundDoseHelper.mMusicActiveMs = 0;
                                                }
                                                soundDoseHelper.mAudioHandler.obtainMessage(1004, soundDoseHelper.mMusicActiveMs, 0).sendToTarget();
                                            }
                                        } else {
                                            PendingIntent pendingIntent = soundDoseHelper.mMusicActiveIntent;
                                            if (pendingIntent != null) {
                                                soundDoseHelper.mAlarmManager.cancel(pendingIntent);
                                                soundDoseHelper.mMusicActiveIntent = null;
                                            }
                                            soundDoseHelper.mLastMusicActiveTimeMs = 0L;
                                        }
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        return;
                    }
                    int[] intArrayExtra = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
                    String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                    if (stringArrayExtra == null || intArrayExtra == null || stringArrayExtra.length != intArrayExtra.length) {
                        return;
                    }
                    int i5 = 0;
                    while (i5 < intArrayExtra.length) {
                        if (TextUtils.isEmpty(stringArrayExtra[i5])) {
                            c = c3;
                        } else {
                            MediaFocusControl mediaFocusControl2 = this.this$0.mMediaFocusControl;
                            String str2 = stringArrayExtra[i5];
                            int i6 = intArrayExtra[i5];
                            mediaFocusControl2.getClass();
                            synchronized (MediaFocusControl.mAudioFocusLock) {
                                try {
                                    Iterator it = mediaFocusControl2.mFocusStack.iterator();
                                    ArrayList arrayList = new ArrayList();
                                    while (it.hasNext()) {
                                        FocusRequester focusRequester2 = (FocusRequester) it.next();
                                        if (focusRequester2.hasSameUid(i6) && focusRequester2.mPackageName.compareTo(str2) == 0) {
                                            arrayList.add(focusRequester2.mClientId);
                                            EventLogger eventLogger = MediaFocusControl.mEventLogger;
                                            EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("focus owner:" + focusRequester2.mClientId + " in uid:" + i6 + " pack: " + str2 + " getting AUDIOFOCUS_LOSS due to app suspension");
                                            stringEvent.printLog(0, "MediaFocusControl");
                                            eventLogger.enqueue(stringEvent);
                                            c2 = 65535;
                                            focusRequester2.dispatchFocusChange(-1);
                                        } else {
                                            c2 = c3;
                                        }
                                        c3 = c2;
                                    }
                                    c = c3;
                                    Iterator it2 = arrayList.iterator();
                                    while (it2.hasNext()) {
                                        mediaFocusControl2.removeFocusStackEntry((String) it2.next(), false, true);
                                    }
                                } finally {
                                }
                            }
                        }
                        i5++;
                        c3 = c;
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SetModeDeathHandler implements IBinder.DeathRecipient {
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
        public final void binderDied() {
            synchronized (AudioService.this.mDeviceBroker.mSetModeLock) {
                try {
                    Log.w("AS.AudioService", "SetModeDeathHandler client died");
                    int indexOf = AudioService.this.mSetModeDeathHandlers.indexOf(this);
                    if (indexOf < 0) {
                        Log.w("AS.AudioService", "unregistered SetModeDeathHandler client died");
                    } else {
                        SetModeDeathHandler setModeDeathHandler = (SetModeDeathHandler) AudioService.this.mSetModeDeathHandlers.get(indexOf);
                        AudioService.this.mSetModeDeathHandlers.remove(indexOf);
                        AudioService.this.mExt.updateCallGuardInfo(-1, setModeDeathHandler.mPid, false);
                        AudioService.sendMsg(AudioService.this.mAudioHandler, 36, 2, -1, Process.myPid(), AudioService.this.mContext.getPackageName(), 0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void dump(int i, PrintWriter printWriter) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss:SSS");
            if (i >= 0) {
                printWriter.println("  Requester # " + (i + 1) + ":");
            }
            printWriter.println("  - Mode: " + AudioSystem.modeToString(this.mMode));
            printWriter.println("  - Binder: " + this.mCb);
            StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, this.mPackage, "  - Privileged: ", BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  - Pid: "), this.mPid, printWriter, "  - Uid: "), this.mUid, printWriter, "  - Package: ")), this.mIsPrivileged, printWriter, "  - Active: ");
            m.append(isActive());
            printWriter.println(m.toString());
            StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("    Playback active: "), this.mPlaybackActive, printWriter, "    Recording active: "), this.mRecordingActive, printWriter, "  - update time: ");
            m2.append(simpleDateFormat.format(new Date(this.mUpdateTime)));
            printWriter.println(m2.toString());
        }

        public final boolean isActive() {
            if (this.mIsPrivileged) {
                return true;
            }
            int i = this.mMode;
            return (i == 3 && (this.mRecordingActive || this.mPlaybackActive)) || i == 1 || i == 4;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AudioService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SettingsObserver(AudioService audioService, int i) {
            super(new Handler());
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = audioService;
                    super(new Handler());
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("multi_audio_focus_enabled"), false, this, -1);
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("ear_shock_condition"), false, this, -1);
                    if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                        audioService.mContentResolver.registerContentObserver(Settings.Global.getUriFor("translate_during_calls"), false, this);
                        audioService.mContentResolver.registerContentObserver(Settings.Global.getUriFor("translate_during_allow_apps"), false, this);
                        break;
                    }
                    break;
                default:
                    this.this$0 = audioService;
                    audioService.mContentResolver.registerContentObserver(Settings.Global.getUriFor("zen_mode"), false, this);
                    audioService.mContentResolver.registerContentObserver(Settings.Global.getUriFor("zen_mode_config_etag"), false, this);
                    audioService.mContentResolver.registerContentObserver(Settings.Global.getUriFor("mute_alarm_stream_with_ringer_mode"), false, this);
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("mode_ringer_streams_affected"), false, this);
                    audioService.mContentResolver.registerContentObserver(Settings.Global.getUriFor("dock_audio_media_enabled"), false, this);
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("master_mono"), false, this, -1);
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("master_balance"), false, this, -1);
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("master_mono"), false, this, -1);
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("master_balance"), false, this, -1);
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("mono_audio_type"), false, this, -1);
                    audioService.mContentResolver.registerContentObserver(Settings.System.getUriFor("speaker_balance"), false, this, -1);
                    SettingsAdapter settingsAdapter = audioService.mSettings;
                    ContentResolver contentResolver = audioService.mContentResolver;
                    settingsAdapter.getClass();
                    audioService.mEncodedSurroundMode = Settings.Global.getInt(contentResolver, "encoded_surround_output", 0);
                    audioService.mContentResolver.registerContentObserver(Settings.Global.getUriFor("encoded_surround_output"), false, this);
                    SettingsAdapter settingsAdapter2 = audioService.mSettings;
                    ContentResolver contentResolver2 = audioService.mContentResolver;
                    settingsAdapter2.getClass();
                    audioService.mEnabledSurroundFormats = Settings.Global.getString(contentResolver2, "encoded_surround_output_enabled_formats");
                    audioService.mContentResolver.registerContentObserver(Settings.Global.getUriFor("encoded_surround_output_enabled_formats"), false, this);
                    audioService.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("voice_interaction_service"), false, this);
                    break;
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            String str;
            switch (this.$r8$classId) {
                case 0:
                    super.onChange(z);
                    synchronized (this.this$0.mSettingsLock) {
                        try {
                            if (this.this$0.updateRingerAndZenModeAffectedStreams()) {
                                AudioService audioService = this.this$0;
                                audioService.setRingerModeInt(audioService.getRingerModeInternal(), false);
                            }
                            AudioService audioService2 = this.this$0;
                            audioService2.readDockAudioSettings(audioService2.mContentResolver);
                            AudioService audioService3 = this.this$0;
                            audioService3.updateMasterMono(audioService3.mContentResolver);
                            AudioService audioService4 = this.this$0;
                            audioService4.updateMasterBalance(audioService4.mContentResolver);
                            updateEncodedSurroundOutput();
                            AudioService audioService5 = this.this$0;
                            audioService5.sendEnabledSurroundFormats(audioService5.mContentResolver, audioService5.mSurroundModeChanged);
                            this.this$0.updateAssistantUIdLocked(false);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                default:
                    super.onChange(z);
                    this.this$0.setMultiAudioFocusEnabled(Settings.System.getInt(this.this$0.mContentResolver, "multi_audio_focus_enabled", 0) != 0);
                    if (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION) {
                        int platformType = PlatformTypeUtils.getPlatformType(this.this$0.mContext);
                        AudioService audioService6 = this.this$0;
                        if (audioService6.mPlatformType != platformType) {
                            audioService6.mPlatformType = platformType;
                            if (platformType == 1) {
                                Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION = false;
                            }
                            audioService6.updateStreamVolumeAlias("AS.AudioService.CMC", false);
                        }
                    }
                    boolean z2 = Settings.System.getInt(this.this$0.mContentResolver, "ear_shock_condition", 0) != 0;
                    AudioService audioService7 = this.this$0;
                    if (z2 != audioService7.mIsVolumeEffectOn) {
                        audioService7.mIsVolumeEffectOn = z2;
                        audioService7.mSoundDoseHelper.initSafeMediaVolumeIndex(z2);
                        if (audioService7.mSoundDoseHelper.mSafeMediaVolumeState == 3 && audioService7.mIsVolumeEffectOn) {
                            int earProtectLimit = audioService7.getEarProtectLimit() - 1;
                            int deviceForStream = audioService7.getDeviceForStream(3);
                            int index = audioService7.mStreamStates[3].getIndex(deviceForStream);
                            int i = earProtectLimit * 10;
                            if (index > i && audioService7.mSoundDoseHelper.checkSafeMediaVolume(3, index, deviceForStream)) {
                                audioService7.mAudioHandler.post(new AudioService$$ExternalSyntheticLambda4(0, new ContextThemeWrapper(audioService7.mContext, R.style.Theme.DeviceDefault.Light)));
                                audioService7.setStreamVolumeWithAttribution(3, i, 1048576, "android", null);
                            }
                        }
                        if (audioService7.mVolumeLimitOn) {
                            audioService7.setVolumeLevelToLimit("android");
                        }
                    }
                    if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                        boolean z3 = Settings.Global.getInt(this.this$0.mContentResolver, "translate_during_calls", 1) != 0;
                        String string = Settings.Global.getString(this.this$0.mContentResolver, "translate_during_allow_apps");
                        AudioService audioService8 = this.this$0;
                        if (z3 == audioService8.mLiveTranslatorDuringCall && (string == null || string.equals(audioService8.mLiveTranslatorAllowApps))) {
                            return;
                        }
                        if (this.this$0.mMode.get() == 3) {
                            AudioService audioService9 = this.this$0;
                            SetModeDeathHandler setModeDeathHandler = audioService9.mExternalVoipModeOwner;
                            if (setModeDeathHandler != null) {
                                str = setModeDeathHandler.mPackage;
                            } else {
                                SetModeDeathHandler audioModeOwnerHandler = audioService9.getAudioModeOwnerHandler();
                                str = audioModeOwnerHandler != null ? audioModeOwnerHandler.mPackage : "";
                            }
                            this.this$0.mLiveTranslatorManager.updateAudioMode(3, str);
                        }
                        AudioService audioService10 = this.this$0;
                        audioService10.mLiveTranslatorDuringCall = z3;
                        audioService10.mLiveTranslatorAllowApps = string;
                        return;
                    }
                    return;
            }
        }

        public void updateEncodedSurroundOutput() {
            AudioService audioService = this.this$0;
            SettingsAdapter settingsAdapter = audioService.mSettings;
            ContentResolver contentResolver = audioService.mContentResolver;
            settingsAdapter.getClass();
            int i = Settings.Global.getInt(contentResolver, "encoded_surround_output", 0);
            AudioService audioService2 = this.this$0;
            if (audioService2.mEncodedSurroundMode == i) {
                audioService2.mSurroundModeChanged = false;
                return;
            }
            audioService2.sendEncodedSurroundMode(i, "SettingsObserver");
            this.this$0.mDeviceBroker.sendIILMsg(6, 2, 0, 0, null, 0);
            AudioService audioService3 = this.this$0;
            audioService3.mEncodedSurroundMode = i;
            audioService3.mSurroundModeChanged = true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundEventReceiver {
        public ComponentName mEventReceiver;
        public int mEventType;

        public final boolean equals(Object obj) {
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

        public final int hashCode() {
            ComponentName componentName = this.mEventReceiver;
            if (componentName == null) {
                return 0;
            }
            return componentName.hashCode();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class VolumeController {
        public IVolumeController mController;
        public boolean mSafetyDialogVisible;
        public boolean mVisible;

        public VolumeController() {
        }

        public static IBinder binder(IVolumeController iVolumeController) {
            if (iVolumeController == null) {
                return null;
            }
            return iVolumeController.asBinder();
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

        public boolean isSameBinder(IVolumeController iVolumeController) {
            return Objects.equals(binder(this.mController), binder(iVolumeController));
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

        public final void postDisplayCsdWarning(int i, int i2) {
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

        public void removeController(IVolumeController iVolumeController) {
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

        public void setController(IVolumeController iVolumeController) {
            this.mController = iVolumeController;
            this.mVisible = false;
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

        public String toString() {
            StringBuilder sb = new StringBuilder("VolumeController(");
            sb.append(binder(this.mController));
            sb.append(",mVisible=");
            return OptionalBool$$ExternalSyntheticOutline0.m(")", sb, this.mVisible);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VolumeGroupState {
        public final AudioAttributes mAudioAttributes;
        public final AudioVolumeGroup mAudioVolumeGroup;
        public final boolean mHasValidStreamType;
        public final int mIndexMax;
        public final int mIndexMin;
        public final int mPublicStreamType;
        public String mSettingName;
        public final SparseIntArray mIndexMap = new SparseIntArray(8);
        public boolean mIsMuted = false;

        /* renamed from: -$$Nest$mpersistVolumeGroup, reason: not valid java name */
        public static void m283$$Nest$mpersistVolumeGroup(VolumeGroupState volumeGroupState, int i) {
            AudioService audioService = AudioService.this;
            if (audioService.mUseFixedVolume || volumeGroupState.mHasValidStreamType) {
                return;
            }
            Log.v("AS.AudioService", "persistVolumeGroup: storing index " + volumeGroupState.getIndex(i) + " for group " + volumeGroupState.mAudioVolumeGroup.name() + ", device " + AudioSystem.getOutputDeviceName(i) + " and User=" + AudioService.getCurrentUserId() + " mSettingName: " + volumeGroupState.mSettingName);
            SettingsAdapter settingsAdapter = audioService.mSettings;
            ContentResolver contentResolver = audioService.mContentResolver;
            String settingNameForDevice = volumeGroupState.getSettingNameForDevice(i);
            int index = volumeGroupState.getIndex(i);
            int i2 = (volumeGroupState.mHasValidStreamType && volumeGroupState.mPublicStreamType == 3) ? 0 : -2;
            settingsAdapter.getClass();
            if (Settings.System.putIntForUser(contentResolver, settingNameForDevice, index, i2)) {
                return;
            }
            Log.e("AS.AudioService", "persistVolumeGroup failed for group " + volumeGroupState.mAudioVolumeGroup.name());
        }

        public VolumeGroupState(AudioVolumeGroup audioVolumeGroup) {
            int i = 0;
            this.mHasValidStreamType = false;
            this.mPublicStreamType = 3;
            this.mAudioAttributes = AudioProductStrategy.getDefaultAttributes();
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
            } else {
                if (audioVolumeGroup.getAudioAttributes().isEmpty()) {
                    throw new IllegalArgumentException("volume group: " + this.mAudioVolumeGroup.name() + " has neither valid attributes nor valid stream types assigned");
                }
                this.mIndexMin = AudioSystem.getMinVolumeIndexForAttributes(this.mAudioAttributes);
                this.mIndexMax = AudioSystem.getMaxVolumeIndexForAttributes(this.mAudioAttributes);
            }
            if (str.isEmpty()) {
                str = "volume_" + this.mAudioVolumeGroup.name();
            }
            this.mSettingName = str;
            readSettings();
        }

        public final void applyAllVolumes(boolean z) {
            int i;
            int i2;
            int i3;
            synchronized (VolumeStreamState.class) {
                int i4 = 0;
                while (true) {
                    try {
                        int i5 = -1;
                        i = 1073741824;
                        if (i4 >= this.mIndexMap.size()) {
                            break;
                        }
                        int keyAt = this.mIndexMap.keyAt(i4);
                        int valueAt = this.mIndexMap.valueAt(i4);
                        if (keyAt != 1073741824) {
                            int[] legacyStreamTypes = this.mAudioVolumeGroup.getLegacyStreamTypes();
                            int length = legacyStreamTypes.length;
                            int i6 = 0;
                            boolean z2 = false;
                            while (i6 < length) {
                                int i7 = legacyStreamTypes[i6];
                                if (i7 != i5) {
                                    AudioService audioService = AudioService.this;
                                    VolumeStreamState[] volumeStreamStateArr = audioService.mStreamStates;
                                    if (i7 < volumeStreamStateArr.length) {
                                        boolean z3 = volumeStreamStateArr[i7].mIsMuted;
                                        int deviceForStream = audioService.getDeviceForStream(i7);
                                        int index = (AudioService.this.mStreamStates[i7].getIndex(deviceForStream) + 5) / 10;
                                        if (keyAt == deviceForStream) {
                                            if (index == valueAt && this.mIsMuted == z3 && isVssMuteBijective(i7)) {
                                                z2 = true;
                                            } else {
                                                com.android.media.audio.Flags.vgsVssSyncMuteOrder();
                                                if (this.mIsMuted != z3 && isVssMuteBijective(i7)) {
                                                    AudioService.this.mStreamStates[i7].mute("VGS.applyAllVolumes#1", this.mIsMuted);
                                                }
                                                if (index != valueAt) {
                                                    AudioService.this.mStreamStates[i7].setIndex(valueAt * 10, keyAt, "from vgs", true);
                                                }
                                                com.android.media.audio.Flags.vgsVssSyncMuteOrder();
                                            }
                                        }
                                    }
                                }
                                i6++;
                                i5 = -1;
                            }
                            if (!z2) {
                                Log.d("AS.AudioService", "applyAllVolumes: apply index " + valueAt + ", group " + this.mAudioVolumeGroup.name() + " and device " + AudioSystem.getOutputDeviceName(keyAt));
                                if (this.mIsMuted) {
                                    valueAt = 0;
                                }
                                setVolumeIndexInt(valueAt, keyAt);
                            }
                        }
                        i4++;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                int index2 = getIndex(1073741824);
                int deviceForStream2 = AudioService.this.getDeviceForStream(this.mPublicStreamType);
                boolean z4 = z && this.mIndexMap.indexOfKey(deviceForStream2) < 0;
                int[] legacyStreamTypes2 = this.mAudioVolumeGroup.getLegacyStreamTypes();
                int length2 = legacyStreamTypes2.length;
                int i8 = 0;
                boolean z5 = false;
                while (i8 < length2) {
                    int i9 = legacyStreamTypes2[i8];
                    if (i9 != -1) {
                        VolumeStreamState[] volumeStreamStateArr2 = AudioService.this.mStreamStates;
                        if (i9 < volumeStreamStateArr2.length) {
                            VolumeStreamState volumeStreamState = volumeStreamStateArr2[i9];
                            boolean z6 = volumeStreamState.mIsMuted;
                            int index3 = (volumeStreamState.getIndex(i) + 5) / 10;
                            if (z4) {
                                AudioService.this.mStreamStates[i9].setIndex(index2 * 10, deviceForStream2, "from vgs", true);
                            }
                            if (index3 == index2 && this.mIsMuted == z6 && isVssMuteBijective(i9)) {
                                z5 = true;
                            } else {
                                if (index3 != index2) {
                                    AudioService.this.mStreamStates[i9].setIndex(index2 * 10, 1073741824, "from vgs", true);
                                }
                                if (this.mIsMuted != z6 && isVssMuteBijective(i9)) {
                                    AudioService.this.mStreamStates[i9].mute("VGS.applyAllVolumes#2", this.mIsMuted);
                                }
                            }
                        }
                    }
                    i8++;
                    i = 1073741824;
                }
                if (!z5) {
                    Log.d("AS.AudioService", "applyAllVolumes: apply default device index " + index2 + ", group " + this.mAudioVolumeGroup.name());
                    if (this.mIsMuted) {
                        i3 = 1073741824;
                        i2 = 0;
                    } else {
                        i2 = index2;
                        i3 = 1073741824;
                    }
                    setVolumeIndexInt(i2, i3);
                }
                if (z4) {
                    Log.d("AS.AudioService", "applyAllVolumes: forceDeviceSync index " + index2 + ", device " + AudioSystem.getOutputDeviceName(deviceForStream2) + ", group " + this.mAudioVolumeGroup.name());
                    if (this.mIsMuted) {
                        index2 = 0;
                    }
                    setVolumeIndexInt(index2, deviceForStream2);
                }
            }
        }

        public final int getIndex(int i) {
            int i2 = this.mIndexMap.get(i, -1);
            return i2 != -1 ? i2 : this.mIndexMap.get(1073741824);
        }

        public final String getSettingNameForDevice(int i) {
            if (AudioSystem.getOutputDeviceName(i).isEmpty()) {
                return this.mSettingName;
            }
            return this.mSettingName + "_" + AudioSystem.getOutputDeviceName(i);
        }

        public final int getValidIndex(int i) {
            int i2 = this.mIndexMin;
            if (i < i2) {
                return i2;
            }
            boolean z = AudioService.this.mUseFixedVolume;
            int i3 = this.mIndexMax;
            return (z || i > i3) ? i3 : i;
        }

        public final boolean isVssMuteBijective(int i) {
            AudioService audioService = AudioService.this;
            if (audioService.isStreamAffectedByMute(i)) {
                int i2 = (audioService.mStreamStates[i].mIndexMin + 5) / 10;
                int i3 = this.mIndexMin;
                if (i3 == i2 && (i3 == 0 || i == 0 || i == 6)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean mute(boolean z) {
            if (this.mIndexMin == 0 || (this.mHasValidStreamType && isVssMuteBijective(this.mPublicStreamType))) {
                boolean z2 = this.mIsMuted != z;
                if (z2) {
                    this.mIsMuted = z;
                }
                return z2;
            }
            Log.d("AS.AudioService", "invalid mute on unmutable volume group " + this.mAudioVolumeGroup.name());
            return false;
        }

        public final void readSettings() {
            synchronized (VolumeStreamState.class) {
                try {
                    if (AudioService.this.mUseFixedVolume) {
                        this.mIndexMap.put(1073741824, this.mIndexMax);
                        return;
                    }
                    Iterator it = AudioSystem.DEVICE_OUT_ALL_SET.iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        int i = intValue == 1073741824 ? AudioSystem.DEFAULT_STREAM_VOLUME[this.mPublicStreamType] : -1;
                        String settingNameForDevice = getSettingNameForDevice(intValue);
                        AudioService audioService = AudioService.this;
                        SettingsAdapter settingsAdapter = audioService.mSettings;
                        ContentResolver contentResolver = audioService.mContentResolver;
                        int i2 = (this.mHasValidStreamType && this.mPublicStreamType == 3) ? 0 : -2;
                        settingsAdapter.getClass();
                        int intForUser = Settings.System.getIntForUser(contentResolver, settingNameForDevice, i, i2);
                        if (intForUser != -1) {
                            if (this.mPublicStreamType == 7 && AudioService.this.mCameraSoundForced) {
                                intForUser = this.mIndexMax;
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append("readSettings: found stored index ");
                            sb.append(getValidIndex(intForUser));
                            sb.append(" for group ");
                            sb.append(this.mAudioVolumeGroup.name());
                            sb.append(", device: ");
                            sb.append(settingNameForDevice);
                            sb.append(", User=");
                            AudioService.this.getClass();
                            sb.append(AudioService.getCurrentUserId());
                            Log.v("AS.AudioService", sb.toString());
                            this.mIndexMap.put(intValue, getValidIndex(intForUser));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setVolumeIndex(int i, int i2, int i3) {
            updateVolumeIndex(i, i2);
            if (mute(i == 0)) {
                return;
            }
            setVolumeIndexInt(getValidIndex(i), i2);
        }

        public final void setVolumeIndexInt(int i, int i2) {
            boolean z = this.mHasValidStreamType;
            int i3 = this.mPublicStreamType;
            AudioService audioService = AudioService.this;
            if (z && isVssMuteBijective(i3) && audioService.mStreamStates[i3].isFullyMuted()) {
                i = 0;
            } else if (i3 == 6 && i == 0) {
                i = 1;
            }
            AudioSystemAdapter audioSystemAdapter = audioService.mAudioSystem;
            AudioAttributes audioAttributes = this.mAudioAttributes;
            audioSystemAdapter.getClass();
            AudioSystem.setVolumeIndexForAttributes(audioAttributes, i, i2);
        }

        public final void updateVolumeIndex(int i, int i2) {
            if (this.mIndexMap.indexOfKey(i2) < 0 || this.mIndexMap.get(i2) != i) {
                this.mIndexMap.put(i2, getValidIndex(i));
                AudioService.sendMsg(AudioService.this.mAudioHandler, 2, 2, i2, 0, this, 500);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VolumeMap {
        public final short lowerStep;
        public final short raiseStep;

        public VolumeMap(short s, short s2) {
            this.raiseStep = s2;
            this.lowerStep = s;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class VolumeStreamState {
        public final CurrentDeviceManager mCurrentDeviceManager;
        public final int mIndexMax;
        public final int mIndexMin;
        public int mIndexMinNoPerm;
        public final Intent mStreamDevicesChanged;
        public final Bundle mStreamDevicesChangedOptions;
        public final int mStreamType;
        public String mVolumeIndexSettingName;
        public final /* synthetic */ AudioService this$0;
        public VolumeGroupState mVolumeGroupState = null;
        public boolean mIsMuted = false;
        public boolean mIsMutedInternally = false;
        public Set mObservedDeviceSet = new TreeSet();
        public final AnonymousClass1 mIndexMap = new AnonymousClass1();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.audio.AudioService$VolumeStreamState$1, reason: invalid class name */
        public final class AnonymousClass1 extends SparseIntArray {
            public AnonymousClass1() {
                super(8);
            }

            @Override // android.util.SparseIntArray
            public final void put(int i, int i2) {
                super.put(i, i2);
                record(i, i2, "put");
            }

            public final void record(int i, int i2, String str) {
                new MediaMetrics.Item("audio.volume." + AudioSystem.streamToString(VolumeStreamState.this.mStreamType) + "." + (i == 1073741824 ? "default" : AudioSystem.getOutputDeviceName(i))).set(MediaMetrics.Property.EVENT, str).set(MediaMetrics.Property.INDEX, Integer.valueOf(i2)).set(MediaMetrics.Property.MIN_INDEX, Integer.valueOf(VolumeStreamState.this.mIndexMin)).set(MediaMetrics.Property.MAX_INDEX, Integer.valueOf(VolumeStreamState.this.mIndexMax)).record();
            }

            @Override // android.util.SparseIntArray
            public final void setValueAt(int i, int i2) {
                super.setValueAt(i, i2);
                record(keyAt(i), i2, "setValueAt");
            }
        }

        public VolumeStreamState(int i, AudioService audioService, String str) {
            this.this$0 = audioService;
            new Intent("android.media.VOLUME_CHANGED_ACTION");
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
                EventLogger eventLogger = AudioService.sLifecycleLogger;
                EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(ArrayUtils$$ExternalSyntheticOutline0.m(i, initStreamVolume, "VSS() stream:", " initStreamVolume="));
                stringEvent.printLog(1, "AS.AudioService");
                eventLogger.enqueue(stringEvent);
                AudioService.sendMsg(audioService.mAudioHandler, 34, 1, 0, 0, "VSS()", 2000);
            }
            readSettings();
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setDeliveryGroupPolicy(1);
            makeBasic.setDeliveryGroupMatchingKey("android.media.VOLUME_CHANGED_ACTION", String.valueOf(i));
            makeBasic.setDeferralPolicy(2);
            makeBasic.toBundle();
            Intent intent = new Intent("android.media.STREAM_DEVICES_CHANGED_ACTION");
            this.mStreamDevicesChanged = intent;
            intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", i);
            BroadcastOptions makeBasic2 = BroadcastOptions.makeBasic();
            makeBasic2.setDeliveryGroupPolicy(1);
            makeBasic2.setDeliveryGroupMatchingKey("android.media.STREAM_DEVICES_CHANGED_ACTION", String.valueOf(i));
            makeBasic2.setDeferralPolicy(2);
            this.mStreamDevicesChangedOptions = makeBasic2.toBundle();
        }

        public final void applyAllVolumes() {
            int absoluteVolumeIndex;
            int i;
            synchronized (VolumeStreamState.class) {
                int i2 = 0;
                int i3 = 0;
                for (int i4 = 0; i4 < this.mIndexMap.size(); i4++) {
                    try {
                        int keyAt = this.mIndexMap.keyAt(i4);
                        if (keyAt != 1073741824) {
                            if (isFullyMuted()) {
                                absoluteVolumeIndex = 0;
                            } else if (!AudioSystem.isLeAudioDeviceType(keyAt) || this.this$0.mMode.get() == 0 || (i = this.mStreamType) == 0) {
                                AudioService audioService = this.this$0;
                                int i5 = AudioService.BECOMING_NOISY_DELAY_MS;
                                if (!audioService.isAbsoluteVolumeDevice(keyAt) && !this.this$0.isA2dpAbsoluteVolumeDevice(keyAt) && !AudioSystem.isLeAudioDeviceType(keyAt)) {
                                    if (this.this$0.isFullVolumeDevice(keyAt)) {
                                        absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
                                    } else if (keyAt == 134217728) {
                                        com.android.media.audio.Flags.absVolumeIndexFix();
                                        absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
                                    } else {
                                        absoluteVolumeIndex = this.mStreamType == 3 ? this.mIndexMap.valueAt(i4) / 10 : (this.mIndexMap.valueAt(i4) + 5) / 10;
                                    }
                                }
                                com.android.media.audio.Flags.absVolumeIndexFix();
                                if (this.mStreamType == 2) {
                                    absoluteVolumeIndex = (this.mIndexMap.valueAt(i4) + 5) / 10;
                                    i3 = 0;
                                } else {
                                    absoluteVolumeIndex = getAbsoluteVolumeIndex(getIndexDividedBy10(keyAt));
                                    i3 = 1;
                                }
                            } else {
                                absoluteVolumeIndex = i == 3 ? getIndex(keyAt) / 10 : (getIndex(keyAt) + 5) / 10;
                            }
                            AudioService.sendMsg(this.this$0.mAudioHandler, 1006, 0, keyAt, i3, this, 0);
                            setStreamVolumeIndex(absoluteVolumeIndex, keyAt);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (!isFullyMuted()) {
                    i2 = this.mStreamType == 3 ? getIndex(1073741824) / 10 : (getIndex(1073741824) + 5) / 10;
                }
                setStreamVolumeIndex(i2, 1073741824);
            }
        }

        public void applyDeviceVolume_syncVSS(int i) {
            int absoluteVolumeIndex;
            int i2;
            if (isFullyMuted()) {
                absoluteVolumeIndex = 0;
            } else if (!AudioSystem.isLeAudioDeviceType(i) || this.this$0.mMode.get() == 0 || (i2 = this.mStreamType) == 0) {
                AudioService audioService = this.this$0;
                int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
                if (audioService.isAbsoluteVolumeDevice(i) || this.this$0.isA2dpAbsoluteVolumeDevice(i) || AudioSystem.isLeAudioDeviceType(i)) {
                    com.android.media.audio.Flags.absVolumeIndexFix();
                    absoluteVolumeIndex = getAbsoluteVolumeIndex(getIndexDividedBy10(i));
                } else if (this.this$0.isFullVolumeDevice(i)) {
                    absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
                } else if (i == 134217728) {
                    com.android.media.audio.Flags.absVolumeIndexFix();
                    absoluteVolumeIndex = (this.mIndexMax + 5) / 10;
                } else {
                    absoluteVolumeIndex = getIndex(i) / 10;
                    if (i == 536870912 && this.this$0.mAvrcpAbsVolSupported) {
                        absoluteVolumeIndex = getAbsoluteVolumeIndex(getIndexDividedBy10(i));
                    }
                }
            } else {
                absoluteVolumeIndex = i2 == 3 ? getIndex(i) / 10 : (getIndex(i) + 5) / 10;
            }
            setStreamVolumeIndex(absoluteVolumeIndex, i);
            AudioService.m264$$Nest$mapplyDeviceAlias(this.this$0, i, this.mStreamType, new AudioService$VolumeStreamState$$ExternalSyntheticLambda1(this, absoluteVolumeIndex, 0));
        }

        public final void checkFixedVolumeDevices() {
            synchronized (VolumeStreamState.class) {
                try {
                    if (AudioService.mStreamVolumeAlias[this.mStreamType] == 3) {
                        for (int i = 0; i < this.mIndexMap.size(); i++) {
                            int keyAt = this.mIndexMap.keyAt(i);
                            int valueAt = this.mIndexMap.valueAt(i);
                            if (!this.this$0.isFullVolumeDevice(keyAt)) {
                                if (this.this$0.isFixedVolumeDevice(keyAt) && valueAt != 0) {
                                }
                                applyDeviceVolume_syncVSS(keyAt);
                            }
                            this.mIndexMap.put(keyAt, this.mIndexMax);
                            applyDeviceVolume_syncVSS(keyAt);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void doMute() {
            synchronized (VolumeStreamState.class) {
                updateVolumeGroupIndex(this.this$0.getDeviceForStream(this.mStreamType), true);
                AudioService.sendMsg(this.this$0.mAudioHandler, 10, 2, 0, 0, this, 0);
            }
        }

        public final int getAbsoluteVolumeIndex(int i) {
            com.android.media.audio.Flags.absVolumeIndexFix();
            if (i == 0) {
                return 0;
            }
            return (this.mIndexMax + 5) / 10;
        }

        public final int getIndex(int i) {
            int i2;
            synchronized (VolumeStreamState.class) {
                try {
                    i2 = this.mIndexMap.get(i, -1);
                    if (i2 == -1) {
                        i2 = this.mIndexMap.get(1073741824);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return i2;
        }

        public final int getIndexDividedBy10(int i) {
            int index = getIndex(i);
            return this.mStreamType == 3 ? (index + 9) / 10 : (index + 5) / 10;
        }

        public final String getSettingNameForDevice(int i) {
            String str = this.mVolumeIndexSettingName;
            if (str == null || str.isEmpty()) {
                return null;
            }
            String outputDeviceName = AudioSystem.getOutputDeviceName(i);
            return outputDeviceName.isEmpty() ? this.mVolumeIndexSettingName : BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mVolumeIndexSettingName, "_", outputDeviceName);
        }

        public final boolean isFullyMuted() {
            return this.mIsMuted || this.mIsMutedInternally;
        }

        public final boolean mute(String str, boolean z) {
            boolean mute;
            synchronized (VolumeStreamState.class) {
                mute = mute(str, z, true);
            }
            if (mute) {
                AudioService audioService = this.this$0;
                int i = this.mStreamType;
                int i2 = AudioService.BECOMING_NOISY_DELAY_MS;
                audioService.getClass();
                Intent intent = new Intent("android.media.STREAM_MUTE_CHANGED_ACTION");
                intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", i);
                intent.putExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", z);
                audioService.sendBroadcastToAll(intent, null);
            }
            return mute;
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0060 A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0007, B:8:0x0012, B:10:0x0018, B:12:0x0020, B:15:0x0052, B:17:0x0060, B:19:0x006d, B:20:0x009f, B:22:0x00a3, B:23:0x002d, B:25:0x0031, B:29:0x0038, B:30:0x0050, B:33:0x00a6), top: B:3:0x0007 }] */
        /* JADX WARN: Removed duplicated region for block: B:22:0x00a3 A[Catch: all -> 0x002a, TryCatch #0 {all -> 0x002a, blocks: (B:4:0x0007, B:8:0x0012, B:10:0x0018, B:12:0x0020, B:15:0x0052, B:17:0x0060, B:19:0x006d, B:20:0x009f, B:22:0x00a3, B:23:0x002d, B:25:0x0031, B:29:0x0038, B:30:0x0050, B:33:0x00a6), top: B:3:0x0007 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean mute(final java.lang.String r8, final boolean r9, boolean r10) {
            /*
                r7 = this;
                java.lang.String r0 = "Unmuting stream "
                java.lang.String r1 = "Do not unmuting stream "
                java.lang.Class<com.android.server.audio.AudioService$VolumeStreamState> r2 = com.android.server.audio.AudioService.VolumeStreamState.class
                monitor-enter(r2)
                boolean r3 = r7.mIsMuted     // Catch: java.lang.Throwable -> L2a
                r4 = 0
                r5 = 1
                if (r9 == r3) goto Lf
                r3 = r5
                goto L10
            Lf:
                r3 = r4
            L10:
                if (r3 == 0) goto La6
                com.android.server.audio.AudioService r6 = r7.this$0     // Catch: java.lang.Throwable -> L2a
                boolean r6 = r6.mIsLeBroadCasting     // Catch: java.lang.Throwable -> L2a
                if (r6 != 0) goto L2d
                int r6 = r7.mStreamType     // Catch: java.lang.Throwable -> L2a
                boolean r6 = com.android.server.audio.AudioService.isStreamMutedByRingerOrZenMode(r6)     // Catch: java.lang.Throwable -> L2a
                if (r6 == 0) goto L52
                java.lang.String r6 = "setLeBroadcasting"
                boolean r6 = r6.equals(r8)     // Catch: java.lang.Throwable -> L2a
                if (r6 == 0) goto L52
                goto L2d
            L2a:
                r7 = move-exception
                goto La8
            L2d:
                boolean r6 = r7.mIsMuted     // Catch: java.lang.Throwable -> L2a
                if (r6 == 0) goto L52
                int r6 = r7.mStreamType     // Catch: java.lang.Throwable -> L2a
                if (r6 == r5) goto L38
                r5 = 5
                if (r6 != r5) goto L52
            L38:
                java.lang.String r8 = "AS.AudioService"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2a
                r9.<init>(r1)     // Catch: java.lang.Throwable -> L2a
                int r7 = r7.mStreamType     // Catch: java.lang.Throwable -> L2a
                r9.append(r7)     // Catch: java.lang.Throwable -> L2a
                java.lang.String r7 = " because of le broadcasting or ringer, zen mode"
                r9.append(r7)     // Catch: java.lang.Throwable -> L2a
                java.lang.String r7 = r9.toString()     // Catch: java.lang.Throwable -> L2a
                android.util.Log.e(r8, r7)     // Catch: java.lang.Throwable -> L2a
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L2a
                return r4
            L52:
                com.android.server.utils.EventLogger r1 = com.android.server.audio.AudioService.sMuteLogger     // Catch: java.lang.Throwable -> L2a
                com.android.server.audio.AudioServiceEvents$StreamMuteEvent r4 = new com.android.server.audio.AudioServiceEvents$StreamMuteEvent     // Catch: java.lang.Throwable -> L2a
                int r5 = r7.mStreamType     // Catch: java.lang.Throwable -> L2a
                r4.<init>(r5, r8, r9)     // Catch: java.lang.Throwable -> L2a
                r1.enqueue(r4)     // Catch: java.lang.Throwable -> L2a
                if (r9 != 0) goto L9f
                com.android.server.audio.AudioService r8 = r7.this$0     // Catch: java.lang.Throwable -> L2a
                int r4 = r7.mStreamType     // Catch: java.lang.Throwable -> L2a
                r8.getClass()     // Catch: java.lang.Throwable -> L2a
                boolean r8 = com.android.server.audio.AudioService.isStreamMutedByRingerOrZenMode(r4)     // Catch: java.lang.Throwable -> L2a
                if (r8 == 0) goto L9f
                java.lang.String r8 = "AS.AudioService"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2a
                r4.<init>(r0)     // Catch: java.lang.Throwable -> L2a
                int r0 = r7.mStreamType     // Catch: java.lang.Throwable -> L2a
                r4.append(r0)     // Catch: java.lang.Throwable -> L2a
                java.lang.String r0 = " despite ringer-zen muted stream 0x"
                r4.append(r0)     // Catch: java.lang.Throwable -> L2a
                int r0 = com.android.server.audio.AudioService.sRingerAndZenModeMutedStreams     // Catch: java.lang.Throwable -> L2a
                java.lang.String r0 = java.lang.Integer.toHexString(r0)     // Catch: java.lang.Throwable -> L2a
                r4.append(r0)     // Catch: java.lang.Throwable -> L2a
                java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L2a
                java.lang.Exception r4 = new java.lang.Exception     // Catch: java.lang.Throwable -> L2a
                r4.<init>()     // Catch: java.lang.Throwable -> L2a
                android.util.Log.e(r8, r0, r4)     // Catch: java.lang.Throwable -> L2a
                com.android.server.audio.AudioServiceEvents$StreamUnmuteErrorEvent r8 = new com.android.server.audio.AudioServiceEvents$StreamUnmuteErrorEvent     // Catch: java.lang.Throwable -> L2a
                int r0 = r7.mStreamType     // Catch: java.lang.Throwable -> L2a
                int r4 = com.android.server.audio.AudioService.sRingerAndZenModeMutedStreams     // Catch: java.lang.Throwable -> L2a
                r8.<init>(r0, r4)     // Catch: java.lang.Throwable -> L2a
                r1.enqueue(r8)     // Catch: java.lang.Throwable -> L2a
            L9f:
                r7.mIsMuted = r9     // Catch: java.lang.Throwable -> L2a
                if (r10 == 0) goto La6
                r7.doMute()     // Catch: java.lang.Throwable -> L2a
            La6:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L2a
                return r3
            La8:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L2a
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.VolumeStreamState.mute(java.lang.String, boolean, boolean):boolean");
        }

        public final Set observeDevicesForStream_syncVSS(boolean z) {
            float f;
            this.this$0.mSystemServer.getClass();
            AudioService audioService = this.this$0;
            int i = this.mStreamType;
            audioService.getClass();
            Set generateAudioDeviceTypesSet = AudioSystem.generateAudioDeviceTypesSet(audioService.getDevicesForAttributesInt(AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i), true));
            if (generateAudioDeviceTypesSet.equals(this.mObservedDeviceSet)) {
                return this.mObservedDeviceSet;
            }
            int deviceMaskFromSet = AudioSystem.getDeviceMaskFromSet(generateAudioDeviceTypesSet);
            int deviceMaskFromSet2 = AudioSystem.getDeviceMaskFromSet(this.mObservedDeviceSet);
            this.mObservedDeviceSet = generateAudioDeviceTypesSet;
            if (z) {
                this.this$0.postObserveDevicesForAllStreams(this.mStreamType);
            }
            int[] iArr = AudioService.mStreamVolumeAlias;
            int i2 = this.mStreamType;
            if (iArr[i2] == i2) {
                EventLog.writeEvent(40001, Integer.valueOf(i2), Integer.valueOf(deviceMaskFromSet2), Integer.valueOf(deviceMaskFromSet));
            }
            MultiSoundManager multiSoundManager = this.this$0.mMultiSoundManager;
            if (multiSoundManager != null && multiSoundManager.mPreventOverheatState.mState && this.mStreamType == 3) {
                int deviceMaskFromSet3 = AudioSystem.getDeviceMaskFromSet(this.mObservedDeviceSet);
                MultiSoundManager.PreventOverheatState preventOverheatState = multiSoundManager.mPreventOverheatState;
                if (preventOverheatState.mCurDevice != deviceMaskFromSet3) {
                    preventOverheatState.mCurDevice = deviceMaskFromSet3;
                    try {
                        f = Float.parseFloat(SemAudioSystem.getPolicyParameters("l_volume_prevent_overheat_key;gain"));
                    } catch (NullPointerException | NumberFormatException unused) {
                        f = 1.0f;
                    }
                    preventOverheatState.mLimitedVolumeForOverheat = f;
                    MultiSoundManager.this.setAppVolumeToNative(preventOverheatState.mUid);
                }
            }
            if (this.mStreamType == 3 && this.this$0.mMode.get() == 0) {
                AudioService.sendMsg(this.this$0.mAudioHandler, 2765, 0, deviceMaskFromSet, 0, null, 0);
                AudioService.sendMsg(this.this$0.mAudioHandler, 2767, 0, deviceMaskFromSet, 0, null, 0);
            }
            CurrentDeviceManager currentDeviceManager = this.mCurrentDeviceManager;
            final Set unmodifiableSet = Collections.unmodifiableSet(this.mObservedDeviceSet);
            currentDeviceManager.getClass();
            synchronized (CurrentDeviceManager.lock) {
                try {
                    if (!((HashSet) currentDeviceManager.callbacks).isEmpty()) {
                        currentDeviceManager.callbacks.forEach(new Consumer() { // from class: com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Set set = unmodifiableSet;
                                CurrentDeviceManager.CallbackRecord callbackRecord = (CurrentDeviceManager.CallbackRecord) obj;
                                Executor executor = callbackRecord.executor;
                                if (executor == null) {
                                    return;
                                }
                                executor.execute(new CurrentDeviceManager$CallbackRecord$$ExternalSyntheticLambda0(callbackRecord, set));
                            }
                        });
                    }
                } finally {
                }
            }
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = this.mStreamDevicesChanged;
            obtain.arg2 = this.mStreamDevicesChangedOptions;
            AudioService.sendMsg(this.this$0.mAudioHandler, 32, 2, deviceMaskFromSet2, deviceMaskFromSet, obtain, 0);
            return this.mObservedDeviceSet;
        }

        public final void readSettings() {
            synchronized (this.this$0.mSettingsLock) {
                synchronized (VolumeStreamState.class) {
                    if (this.this$0.mUseFixedVolume) {
                        this.mIndexMap.put(1073741824, this.mIndexMax);
                        return;
                    }
                    synchronized (VolumeStreamState.class) {
                        try {
                            Iterator it = AudioSystem.DEVICE_OUT_ALL_SET.iterator();
                            while (it.hasNext()) {
                                int intValue = ((Integer) it.next()).intValue();
                                int i = intValue == 1073741824 ? AudioSystem.DEFAULT_STREAM_VOLUME[this.mStreamType] : -1;
                                String str = this.mVolumeIndexSettingName;
                                if (str != null && !str.isEmpty()) {
                                    String settingNameForDevice = getSettingNameForDevice(intValue);
                                    AudioService audioService = this.this$0;
                                    SettingsAdapter settingsAdapter = audioService.mSettings;
                                    ContentResolver contentResolver = audioService.mContentResolver;
                                    settingsAdapter.getClass();
                                    i = Settings.System.getIntForUser(contentResolver, settingNameForDevice, i, -2);
                                }
                                if (i != -1) {
                                    AnonymousClass1 anonymousClass1 = this.mIndexMap;
                                    int i2 = i * 10;
                                    int i3 = this.mIndexMin;
                                    if (i2 < i3) {
                                        i2 = i3;
                                    } else {
                                        boolean z = this.this$0.mUseFixedVolume;
                                        int i4 = this.mIndexMax;
                                        if (z || i2 > i4) {
                                            i2 = i4;
                                        }
                                    }
                                    anonymousClass1.put(intValue, i2);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        }

        public final void setAllIndexes(VolumeStreamState volumeStreamState, String str) {
            int i = volumeStreamState.mStreamType;
            int i2 = this.mStreamType;
            if (i2 == i) {
                return;
            }
            int index = volumeStreamState.getIndex(1073741824);
            int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
            AudioService audioService = this.this$0;
            int rescaleIndex = audioService.rescaleIndex(index, i, i2);
            int i4 = 0;
            int i5 = 0;
            while (true) {
                AnonymousClass1 anonymousClass1 = this.mIndexMap;
                if (i5 >= anonymousClass1.size()) {
                    break;
                }
                anonymousClass1.put(anonymousClass1.keyAt(i5), rescaleIndex);
                i5++;
            }
            while (true) {
                AnonymousClass1 anonymousClass12 = volumeStreamState.mIndexMap;
                if (i4 >= anonymousClass12.size()) {
                    return;
                }
                setIndex(audioService.rescaleIndex(anonymousClass12.valueAt(i4), i, i2), anonymousClass12.keyAt(i4), str, true);
                i4++;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x0082, code lost:
        
            if (r9 != false) goto L108;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean setIndex(int r18, int r19, java.lang.String r20, boolean r21) {
            /*
                Method dump skipped, instructions count: 337
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.VolumeStreamState.setIndex(int, int, java.lang.String, boolean):boolean");
        }

        public final void setStreamVolumeIndex(int i, int i2) {
            AudioService audioService = this.this$0;
            int i3 = this.mStreamType;
            if (i3 == 6 && i == 0 && !isFullyMuted()) {
                if (Rune.SEC_AUDIO_REMOTE_MIC && audioService.mRemoteMic) {
                    Log.w("AS.AudioService", "SCO stream volume can be muted while remote mic is active");
                } else {
                    i = 1;
                }
            }
            AudioService$$ExternalSyntheticOutline0.m(ArrayUtils$$ExternalSyntheticOutline0.m(i3, i, "setStreamVolumeIndexAS(", ", ", ", "), i2, ")", "AS.AudioService");
            if (i3 != 3 || i2 == 1073741824) {
                audioService.mAudioSystem.getClass();
                AudioSystem.setStreamVolumeIndexAS(i3, i, i2);
                return;
            }
            int index = getIndex(i2) % 10;
            if (this.mIsMuted) {
                index = 0;
            }
            audioService.mAudioHandler.post(new AudioService$$ExternalSyntheticLambda4(2, ArrayUtils$$ExternalSyntheticOutline0.m((i * 10) + index, i2, "l_volume_fine_key;index=", ";device=")));
        }

        public final void updateVolumeGroupIndex(int i, boolean z) {
            synchronized (this.this$0.mSettingsLock) {
                synchronized (VolumeStreamState.class) {
                    try {
                        if (this.mVolumeGroupState != null) {
                            int index = (getIndex(i) + 5) / 10;
                            Log.d("AS.AudioService", "updateVolumeGroupIndex for stream " + this.mStreamType + ", muted=" + this.mIsMuted + ", device=" + i + ", index=" + getIndex(i) + ", group " + this.mVolumeGroupState.mAudioVolumeGroup.name() + " Muted=" + this.mVolumeGroupState.mIsMuted + ", Index=" + index + ", forceMuteState=" + z);
                            this.mVolumeGroupState.updateVolumeIndex(index, i);
                            AudioService audioService = this.this$0;
                            int i2 = this.mStreamType;
                            if (audioService.isStreamAffectedByMute(i2) && (this.mIndexMin == 0 || i2 == 0 || i2 == 6)) {
                                this.mVolumeGroupState.mute(this.mIsMuted);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0073 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x004f  */
    /* renamed from: -$$Nest$mapplyDeviceAlias, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m264$$Nest$mapplyDeviceAlias(com.android.server.audio.AudioService r6, int r7, int r8, com.samsung.android.server.audio.DeviceAliasManager.DeviceAliasRunner r9) {
        /*
            r0 = 1
            r1 = 0
            r2 = 128(0x80, float:1.794E-43)
            if (r7 == r2) goto L12
            r6.getClass()
            boolean r3 = android.media.AudioSystem.isBluetoothLeOutDevice(r7)
            if (r3 == 0) goto L10
            goto L12
        L10:
            r4 = r1
            goto L4b
        L12:
            com.android.server.audio.AudioDeviceBroker r3 = r6.mDeviceBroker
            boolean r3 = r3.isDualModeActive()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "isBluetoothDualModeActive="
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "AS.AudioService"
            android.util.Log.d(r5, r4)
            boolean r4 = android.media.AudioSystem.isBluetoothLeOutDevice(r7)
            if (r4 == 0) goto L36
            if (r3 != 0) goto L36
            r4 = r0
            goto L37
        L36:
            r4 = r1
        L37:
            if (r7 != r2) goto L3b
            if (r3 == 0) goto L46
        L3b:
            r2 = 536870914(0x20000002, float:1.0842024E-19)
            if (r7 != r2) goto L4b
            boolean r2 = r6.isLeBroadcastWithoutLeDevice()
            if (r2 == 0) goto L4b
        L46:
            com.android.server.audio.AudioService$$ExternalSyntheticLambda29 r9 = new com.android.server.audio.AudioService$$ExternalSyntheticLambda29
            r9.<init>()
        L4b:
            com.samsung.android.server.audio.DeviceAliasManager r6 = r6.mDeviceAliasManager
            if (r4 == 0) goto L58
            android.util.SparseArray r6 = r6.mLeOnlyDevices
        L51:
            java.lang.Object r6 = r6.get(r7)
            com.samsung.android.server.audio.DeviceAliasManager$DeviceAlias r6 = (com.samsung.android.server.audio.DeviceAliasManager.DeviceAlias) r6
            goto L5b
        L58:
            android.util.SparseArray r6 = r6.mDevices
            goto L51
        L5b:
            if (r6 != 0) goto L5e
            goto L73
        L5e:
            int[] r7 = r6.mAliases
            int r2 = r7.length
        L61:
            if (r1 >= r2) goto L73
            r3 = r7[r1]
            int r4 = r0 << r8
            int r5 = r6.mExcludeStreams
            r4 = r4 & r5
            if (r4 <= 0) goto L6d
            goto L70
        L6d:
            r9.run(r3)
        L70:
            int r1 = r1 + 1
            goto L61
        L73:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.m264$$Nest$mapplyDeviceAlias(com.android.server.audio.AudioService, int, int, com.samsung.android.server.audio.DeviceAliasManager$DeviceAliasRunner):void");
    }

    /* renamed from: -$$Nest$mclearSoundAssistantSettings, reason: not valid java name */
    public static void m265$$Nest$mclearSoundAssistantSettings(AudioService audioService) {
        audioService.mMediaVolumeStepIndex = 10;
        audioService.mSettingHelper.removeValue("media_volume_step_index");
        audioService.mHeadsetOnlyStream = 32;
        SemAudioSystem.setPolicyParameters("l_sound_assistant_ring_via_headset_enable=" + audioService.mHeadsetOnlyStream);
        audioService.mSettingHelper.removeValue("ring_through_headset");
        if (audioService.mLRSwitching == 1) {
            audioService.mAudioSystem.setParameters("l_sound_assistant_lr_switch_enable=false");
        }
        audioService.mLRSwitching = 0;
        audioService.mSettingHelper.removeValue("sound_lr_switch");
        audioService.setMediaVolumeSteps(null);
        audioService.mSettingHelper.removeValue("ignore_audio_focus");
        audioService.mMediaFocusControl.setIgnoreAudioFocus(-1, false);
        for (int i = 0; i < audioService.mAppVolumeFromSoundAssistant.size(); i++) {
            audioService.mMultiSoundManager.setAppVolume(audioService.mAppVolumeFromSoundAssistant.keyAt(i), 100);
        }
        audioService.mAppVolumeFromSoundAssistant.clear();
        if (audioService.mSettingHelper.removeValue("mono_audio_db") > 0) {
            Settings.System.putIntForUser(audioService.mContentResolver, "master_mono", 0, -2);
        }
        if (audioService.mSettingHelper.removeValue("sound_balance") > 0) {
            Settings.System.putFloatForUser(audioService.mContentResolver, "master_balance", audioService.mExt.mMainBalance, -2);
        }
        audioService.muteMediaStreamOfSpeaker(false);
        audioService.mSettingHelper.removeValue("speaker_media_index");
        audioService.mSettingHelper.removeValue("mute_media_by_vibrate_or_silent_mode");
        audioService.mSavedSpeakerMediaIndex = -1;
        audioService.mMuteMediaByVibrateOrSilentMode = false;
        Settings.System.putInt(audioService.mContentResolver, "multi_audio_focus_enabled", 0);
        audioService.mIgnoreDucking = false;
    }

    /* renamed from: -$$Nest$monAccessoryPlugMediaUnmute, reason: not valid java name */
    public static void m266$$Nest$monAccessoryPlugMediaUnmute(AudioService audioService, int i) {
        audioService.getClass();
        Log.i("AS.AudioService", String.format("onAccessoryPlugMediaUnmute newDevice=%d [%s]", Integer.valueOf(i), AudioSystem.getOutputDeviceName(i)));
        if (audioService.mNm.getZenMode() == 2 || isStreamMutedByRingerOrZenMode(3)) {
            return;
        }
        if (((HashSet) DEVICE_MEDIA_UNMUTED_ON_PLUG_SET).contains(Integer.valueOf(i))) {
            VolumeStreamState volumeStreamState = audioService.mStreamStates[3];
            if (volumeStreamState.mIsMuted && volumeStreamState.getIndex(i) != 0 && AudioSystem.generateAudioDeviceTypesSet(audioService.getDevicesForAttributesInt(AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(3), true)).contains(Integer.valueOf(i))) {
                Log.i("AS.AudioService", String.format("onAccessoryPlugMediaUnmute unmuting device=%d [%s]", Integer.valueOf(i), AudioSystem.getOutputDeviceName(i)));
                synchronized (audioService.mSettingsLock) {
                    audioService.mStreamStates[3].mute("onAccessoryPlugMediaUnmute", false);
                }
            }
        }
    }

    /* renamed from: -$$Nest$monAddAssistantServiceUids, reason: not valid java name */
    public static void m267$$Nest$monAddAssistantServiceUids(AudioService audioService, int[] iArr) {
        synchronized (audioService.mSettingsLock) {
            audioService.addAssistantServiceUidsLocked(iArr);
        }
    }

    /* renamed from: -$$Nest$monCheckVolumeCecOnHdmiConnection, reason: not valid java name */
    public static void m268$$Nest$monCheckVolumeCecOnHdmiConnection(AudioService audioService, int i) {
        if (i != 1) {
            if (audioService.mPlatformType == 2) {
                synchronized (audioService.mHdmiClientLock) {
                    try {
                        if (audioService.mHdmiManager != null) {
                            audioService.updateHdmiCecSinkLocked(((HashSet) audioService.mFullVolumeDevices).contains(1024));
                        }
                    } finally {
                    }
                }
                return;
            }
            return;
        }
        if (audioService.mSoundDoseHelper.safeDevicesContains(1024)) {
            audioService.mSoundDoseHelper.scheduleMusicActiveCheck();
        }
        if (audioService.mPlatformType == 2) {
            synchronized (audioService.mHdmiClientLock) {
                try {
                    if (audioService.mHdmiManager != null && audioService.mHdmiPlaybackClient != null) {
                        audioService.updateHdmiCecSinkLocked(((HashSet) audioService.mFullVolumeDevices).contains(1024));
                    }
                } finally {
                }
            }
        }
        audioService.sendEnabledSurroundFormats(audioService.mContentResolver, true);
    }

    /* renamed from: -$$Nest$monConfigurationChanged, reason: not valid java name */
    public static void m269$$Nest$monConfigurationChanged(AudioService audioService) {
        audioService.getClass();
        try {
            Configuration configuration = audioService.mContext.getResources().getConfiguration();
            if (!Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY) {
                audioService.mSoundDoseHelper.configureSafeMedia(false);
            }
            boolean readCameraSoundForced = audioService.readCameraSoundForced();
            synchronized (audioService.mSettingsLock) {
                boolean z = readCameraSoundForced != audioService.mCameraSoundForced;
                audioService.mCameraSoundForced = readCameraSoundForced;
                if (z) {
                    if (!audioService.mIsSingleVolume) {
                        synchronized (VolumeStreamState.class) {
                            try {
                                VolumeStreamState[] volumeStreamStateArr = audioService.mStreamStates;
                                VolumeStreamState volumeStreamState = volumeStreamStateArr[7];
                                if (readCameraSoundForced) {
                                    for (int i = 0; i < volumeStreamState.mIndexMap.size(); i++) {
                                        VolumeStreamState.AnonymousClass1 anonymousClass1 = volumeStreamState.mIndexMap;
                                        anonymousClass1.put(anonymousClass1.keyAt(i), volumeStreamState.mIndexMax);
                                    }
                                    audioService.mRingerModeAffectedStreams &= -129;
                                } else {
                                    volumeStreamState.setAllIndexes(volumeStreamStateArr[1], "AS.AudioService");
                                }
                            } finally {
                            }
                        }
                        audioService.setRingerModeInt(audioService.getRingerModeInternal(), false);
                    }
                    audioService.mDeviceBroker.setForceUse_Async(4, readCameraSoundForced ? 11 : 0, "onConfigurationChanged");
                    sendMsg(audioService.mAudioHandler, 10, 2, 0, 0, audioService.mStreamStates[7], 0);
                }
            }
            audioService.mVolumeController.setLayoutDirection(configuration.getLayoutDirection());
        } catch (Exception e) {
            Log.e("AS.AudioService", "Error handling configuration change: ", e);
        }
    }

    /* renamed from: -$$Nest$monDispatchAudioServerStateChange, reason: not valid java name */
    public static void m270$$Nest$monDispatchAudioServerStateChange(AudioService audioService, boolean z) {
        synchronized (audioService.mAudioServerStateListeners) {
            Iterator it = audioService.mAudioServerStateListeners.values().iterator();
            while (it.hasNext()) {
                try {
                    ((AsdProxy) it.next()).mAsd.dispatchAudioServerStateChange(z);
                } catch (RemoteException e) {
                    Log.w("AS.AudioService", "Could not call dispatchAudioServerStateChange()", e);
                }
            }
        }
    }

    /* renamed from: -$$Nest$monDynPolicyMixStateUpdate, reason: not valid java name */
    public static void m271$$Nest$monDynPolicyMixStateUpdate(int i, AudioService audioService, String str) {
        audioService.getClass();
        StringBuilder sb = new StringBuilder("onDynamicPolicyMixStateUpdate(");
        sb.append(str);
        sb.append(", ");
        sb.append(i);
        VpnManagerService$$ExternalSyntheticOutline0.m(sb, ")", "AS.AudioService");
        synchronized (audioService.mAudioPolicies) {
            for (AudioPolicyProxy audioPolicyProxy : audioService.mAudioPolicies.values()) {
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

    /* renamed from: -$$Nest$monEnableSurroundFormats, reason: not valid java name */
    public static void m272$$Nest$monEnableSurroundFormats(AudioService audioService, ArrayList arrayList) {
        audioService.getClass();
        for (int i : AudioFormat.SURROUND_SOUND_ENCODING) {
            boolean contains = arrayList.contains(Integer.valueOf(i));
            Log.i("AS.AudioService", "enable surround format:" + i + " " + contains + " " + AudioSystem.setSurroundFormatEnabled(i, contains));
        }
    }

    /* renamed from: -$$Nest$monInitStreamsAndVolumes, reason: not valid java name */
    public static void m273$$Nest$monInitStreamsAndVolumes(AudioService audioService) {
        synchronized (audioService.mSettingsLock) {
            boolean readCameraSoundForced = audioService.readCameraSoundForced();
            audioService.mCameraSoundForced = readCameraSoundForced;
            sendMsg(audioService.mAudioHandler, 8, 2, 4, readCameraSoundForced ? 11 : 0, new String("AudioService ctor"), 0);
        }
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        VolumeStreamState[] volumeStreamStateArr = new VolumeStreamState[numStreamTypes];
        audioService.mStreamStates = volumeStreamStateArr;
        for (int i = 0; i < numStreamTypes; i++) {
            if (i == 3) {
                volumeStreamStateArr[i] = new MediaVolumeStreamState(i, audioService, Settings.System.VOLUME_SETTINGS_INT[mStreamVolumeAlias[i]]);
            } else {
                volumeStreamStateArr[i] = new VolumeStreamState(i, audioService, Settings.System.VOLUME_SETTINGS_INT[mStreamVolumeAlias[i]]);
            }
        }
        int numStreamTypes2 = AudioSystem.getNumStreamTypes();
        for (int i2 = 0; i2 < numStreamTypes2; i2++) {
            audioService.mStreamStates[i2].checkFixedVolumeDevices();
        }
        audioService.checkAllAliasStreamVolumes();
        audioService.checkMuteAffectedStreams();
        audioService.updateDefaultVolumes();
        for (AudioVolumeGroup audioVolumeGroup : audioService.getAudioVolumeGroups()) {
            try {
                ensureValidAttributes(audioVolumeGroup);
                sVolumeGroupStates.append(audioVolumeGroup.getId(), audioService.new VolumeGroupState(audioVolumeGroup));
            } catch (IllegalArgumentException unused) {
                Log.d("AS.AudioService", "volume group " + audioVolumeGroup.name() + " for internal policy needs");
            }
        }
        synchronized (audioService.mSettingsLock) {
            int i3 = 0;
            while (true) {
                try {
                    SparseArray sparseArray = sVolumeGroupStates;
                    if (i3 >= sparseArray.size()) {
                        break;
                    }
                    ((VolumeGroupState) sparseArray.valueAt(i3)).applyAllVolumes(false);
                    i3++;
                } finally {
                }
            }
        }
        audioService.mSoundDoseHelper.initSafeMediaVolumeIndex();
        int numStreamTypes3 = AudioSystem.getNumStreamTypes();
        synchronized (VolumeStreamState.class) {
            for (int i4 = numStreamTypes3 - 1; i4 >= 0; i4--) {
                try {
                    VolumeStreamState volumeStreamState = audioService.mStreamStates[i4];
                    AudioAttributes audioAttributesForStrategyWithLegacyStreamType = AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i4);
                    int volumeGroupIdForAudioAttributes = audioAttributesForStrategyWithLegacyStreamType.equals(new AudioAttributes.Builder().build()) ? -1 : AudioProductStrategy.getVolumeGroupIdForAudioAttributes(audioAttributesForStrategyWithLegacyStreamType, false);
                    if (volumeGroupIdForAudioAttributes != -1) {
                        SparseArray sparseArray2 = sVolumeGroupStates;
                        if (sparseArray2.indexOfKey(volumeGroupIdForAudioAttributes) >= 0) {
                            VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray2.get(volumeGroupIdForAudioAttributes);
                            volumeStreamState.mVolumeGroupState = volumeGroupState;
                            if (volumeGroupState != null) {
                                volumeGroupState.mSettingName = volumeStreamState.mVolumeIndexSettingName;
                            }
                        }
                    }
                } finally {
                }
            }
        }
        sRingerAndZenModeMutedStreams = 0;
        sMuteLogger.enqueue(new AudioServiceEvents$RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "onInitStreamsAndVolumes"));
        audioService.setRingerModeInt(audioService.getRingerModeInternal(), false);
        com.android.media.audio.Flags.disablePrescaleAbsoluteVolume();
        new SettingsObserver(audioService, 0);
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        if (!audioService.mDeviceBroker.mScoManagedByAudio) {
            intentFilter.addAction("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED");
        }
        intentFilter.addAction("android.intent.action.DOCK_EVENT");
        if (audioService.mDisplayManager == null) {
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
        }
        ActivityManagerService$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.USER_SWITCHED", "android.intent.action.USER_BACKGROUND", "android.intent.action.USER_FOREGROUND", "android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        if (audioService.mMonitorRotation) {
            Context context = audioService.mContext;
            AudioHandler audioHandler = audioService.mAudioHandler;
            AudioService$$ExternalSyntheticLambda16 audioService$$ExternalSyntheticLambda16 = new AudioService$$ExternalSyntheticLambda16(2, audioService);
            AudioService$$ExternalSyntheticLambda16 audioService$$ExternalSyntheticLambda162 = new AudioService$$ExternalSyntheticLambda16(3, audioService);
            if (context == null) {
                throw new IllegalArgumentException("Invalid null context");
            }
            RotationHelper.sContext = context;
            RotationHelper.sHandler = audioHandler;
            RotationHelper.sDisplayListener = new RotationHelper.AudioDisplayListener();
            RotationHelper.sFoldStateListener = new DeviceStateManager.FoldStateListener(RotationHelper.sContext, new RotationHelper$$ExternalSyntheticLambda0());
            RotationHelper.sRotationCallback = audioService$$ExternalSyntheticLambda16;
            RotationHelper.sFoldStateCallback = audioService$$ExternalSyntheticLambda162;
            RotationHelper.enable();
        }
        intentFilter.addAction("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION");
        intentFilter.addAction("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION");
        intentFilter.addAction("com.android.server.audio.action.CHECK_MUSIC_ACTIVE");
        intentFilter.setPriority(1000);
        Context context2 = audioService.mContext;
        SamsungBroadcastReceiver samsungBroadcastReceiver = audioService.mReceiver;
        UserHandle userHandle = UserHandle.ALL;
        context2.registerReceiverAsUser(samsungBroadcastReceiver, userHandle, intentFilter, null, audioService.mBroadcastHandlerThread.getThreadHandler(), 2);
        SubscriptionManager subscriptionManager = (SubscriptionManager) audioService.mContext.getSystemService(SubscriptionManager.class);
        if (subscriptionManager == null) {
            Log.e("AS.AudioService", "initExternalEventReceivers cannot create SubscriptionManager!");
        } else {
            subscriptionManager.addOnSubscriptionsChangedListener(audioService.mSubscriptionChangedListener);
        }
        DisplayManager displayManager = audioService.mDisplayManager;
        if (displayManager != null) {
            displayManager.registerDisplayListener(audioService.mDisplayListener, audioService.mAudioHandler);
        }
        new SettingsObserver(audioService, 1);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_STARTED");
        intentFilter2.addAction("android.intent.action.ACTION_SUBINFO_RECORD_UPDATED");
        intentFilter2.addAction("com.samsung.intent.action.DLNA_STATUS_CHANGED");
        intentFilter2.addAction("com.samsung.android.scpm.policy.UPDATE.Audio");
        intentFilter2.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            intentFilter2.addAction("com.samsung.android.scpm.policy.UPDATE.voip-live-translate-allow-list-a7f6");
        }
        ActivityManagerService$$ExternalSyntheticOutline0.m(intentFilter2, "com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE", "com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED", "com.samsung.intent.action.WIFIDISPLAY_NOTI_CONNECTION_MODE", "com.sec.media.action.mute_interval");
        ActivityManagerService$$ExternalSyntheticOutline0.m(intentFilter2, "com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED", "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED", "com.sec.android.intent.action.SPLIT_SOUND", "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
        intentFilter2.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter2.addAction("com.android.server.sepunion.semgoodcatchservice.GOOD_CATCH_STATE_CHANGED");
        if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR) {
            intentFilter2.addAction("com.samsung.bluetooth.device.action.AUDIO_TYPE_CHANGED");
        }
        audioService.mContext.registerReceiverAsUser(audioService.mSamsungReceiver, userHandle, intentFilter2, null, audioService.mBroadcastHandlerThread.getThreadHandler(), 2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter3.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter3.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter3.addDataScheme("package");
        audioService.mContext.registerReceiverAsUser(audioService.mSamsungReceiver, userHandle, intentFilter3, null, audioService.mBroadcastHandlerThread.getThreadHandler(), 2);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("android.intent.action.SAS_NOTIFICATION_CLEAR");
        intentFilter4.addAction("android.intent.action.TurnOff_MultiSound");
        intentFilter4.addAction("com.samsung.android.audio.headup.changedevice");
        intentFilter4.addAction("android.intent.action.LOCALE_CHANGED");
        audioService.mContext.registerReceiverAsUser(audioService.mSamsungReceiver, userHandle, intentFilter4, null, audioService.mBroadcastHandlerThread.getThreadHandler(), 2);
        audioService.mContext.registerReceiverAsUser(audioService.mSamsungReceiver, userHandle, BatteryService$$ExternalSyntheticOutline0.m("androidx.car.app.connection.action.CAR_CONNECTION_UPDATED"), null, audioService.mBroadcastHandlerThread.getThreadHandler(), 2);
        audioService.checkVolumeRangeInitialization("AudioService()");
        synchronized (audioService.mCachedAbsVolDrivingStreamsLock) {
            audioService.mCachedAbsVolDrivingStreams.forEach(new AudioService$$ExternalSyntheticLambda23(audioService, 1));
        }
    }

    /* renamed from: -$$Nest$monObserveDevicesForAllStreams, reason: not valid java name */
    public static void m274$$Nest$monObserveDevicesForAllStreams(AudioService audioService, int i) {
        synchronized (audioService.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                int i2 = 0;
                while (true) {
                    try {
                        VolumeStreamState[] volumeStreamStateArr = audioService.mStreamStates;
                        if (i2 < volumeStreamStateArr.length) {
                            if (i2 != i) {
                                volumeStreamStateArr[i2].observeDevicesForStream_syncVSS(false);
                            }
                            i2++;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$monPlaybackConfigChange, reason: not valid java name */
    public static void m275$$Nest$monPlaybackConfigChange(AudioService audioService, List list) {
        audioService.getClass();
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
                if (usage == 1 || usage == 14 || usage == 0) {
                    z2 = true;
                }
            }
        }
        if (audioService.mVoicePlaybackActive.getAndSet(z) != z) {
            int bluetoothContextualVolumeStream = audioService.getBluetoothContextualVolumeStream();
            int streamVolume = audioService.getStreamVolume(bluetoothContextualVolumeStream);
            sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(bluetoothContextualVolumeStream, streamVolume, audioService.mVoicePlaybackActive.get()));
            audioService.mDeviceBroker.sendIILMsg(14, 0, streamVolume * 10, bluetoothContextualVolumeStream, null, 0);
        }
        if (audioService.mMediaPlaybackActive.getAndSet(z2) != z2 && z2) {
            audioService.mSoundDoseHelper.scheduleMusicActiveCheck();
        }
        final LoudnessCodecHelper loudnessCodecHelper = audioService.mLoudnessCodecHelper;
        loudnessCodecHelper.getClass();
        ArrayList arrayList = new ArrayList();
        synchronized (loudnessCodecHelper.mLock) {
            try {
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    AudioPlaybackConfiguration audioPlaybackConfiguration2 = (AudioPlaybackConfiguration) it2.next();
                    int playerInterfaceId = audioPlaybackConfiguration2.getPlayerInterfaceId();
                    int i = loudnessCodecHelper.mPiidToDeviceIdCache.get(playerInterfaceId, 0);
                    AudioDeviceInfo audioDeviceInfo = audioPlaybackConfiguration2.getAudioDeviceInfo();
                    if (audioDeviceInfo == null) {
                        if (i != 0) {
                            loudnessCodecHelper.mPiidToDeviceIdCache.delete(playerInterfaceId);
                        }
                    } else if (i != audioDeviceInfo.getId()) {
                        loudnessCodecHelper.mPiidToDeviceIdCache.put(playerInterfaceId, audioDeviceInfo.getId());
                        LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new LoudnessCodecHelper.LoudnessTrackId(audioPlaybackConfiguration2.getSessionId(), audioPlaybackConfiguration2.getClientPid());
                        if (loudnessCodecHelper.mStartedConfigInfo.containsKey(loudnessTrackId) && loudnessCodecHelper.mStartedConfigPiids.containsKey(loudnessTrackId)) {
                            arrayList.add(audioPlaybackConfiguration2);
                            ((Set) loudnessCodecHelper.mStartedConfigPiids.get(loudnessTrackId)).add(Integer.valueOf(playerInterfaceId));
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        arrayList.forEach(new Consumer() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LoudnessCodecHelper loudnessCodecHelper2 = LoudnessCodecHelper.this;
                AudioPlaybackConfiguration audioPlaybackConfiguration3 = (AudioPlaybackConfiguration) obj;
                loudnessCodecHelper2.getClass();
                PersistableBundle persistableBundle = new PersistableBundle();
                synchronized (loudnessCodecHelper2.mLock) {
                    try {
                        Set<LoudnessCodecInfo> set = (Set) loudnessCodecHelper2.mStartedConfigInfo.get(new LoudnessCodecHelper.LoudnessTrackId(audioPlaybackConfiguration3.getSessionId(), audioPlaybackConfiguration3.getClientPid()));
                        AudioDeviceInfo audioDeviceInfo2 = audioPlaybackConfiguration3.getAudioDeviceInfo();
                        if (set != null && audioDeviceInfo2 != null) {
                            for (LoudnessCodecInfo loudnessCodecInfo : set) {
                                if (loudnessCodecInfo != null) {
                                    persistableBundle.putPersistableBundle(Integer.toString(loudnessCodecInfo.hashCode()), loudnessCodecHelper2.getCodecBundle_l(audioDeviceInfo2.getInternalType(), audioDeviceInfo2.getAddress(), loudnessCodecInfo));
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                if (persistableBundle.isDefinitelyEmpty()) {
                    return;
                }
                loudnessCodecHelper2.dispatchNewLoudnessParameters(audioPlaybackConfiguration3.getSessionId(), persistableBundle);
            }
        });
        audioService.updateAudioModeHandlers(list, null);
        audioService.mDeviceBroker.updateCommunicationRouteClientsActivity(list, null);
    }

    /* renamed from: -$$Nest$monRemoveAssistantServiceUids, reason: not valid java name */
    public static void m276$$Nest$monRemoveAssistantServiceUids(AudioService audioService, int[] iArr) {
        synchronized (audioService.mSettingsLock) {
            audioService.removeAssistantServiceUidsLocked(iArr);
        }
    }

    /* renamed from: -$$Nest$monSetVolumeIndexOnDevice, reason: not valid java name */
    public static void m277$$Nest$monSetVolumeIndexOnDevice(AudioService audioService, DeviceVolumeUpdate deviceVolumeUpdate) {
        int i;
        VolumeStreamState volumeStreamState = audioService.mStreamStates[deviceVolumeUpdate.mStreamType];
        int i2 = 0;
        int i3 = deviceVolumeUpdate.mVssVolIndex;
        String str = deviceVolumeUpdate.mCaller;
        int i4 = deviceVolumeUpdate.mDevice;
        if (i3 != -2049) {
            Preconditions.checkState(i3 != -2049);
            if (audioService.mSoundDoseHelper.checkSafeMediaVolume(deviceVolumeUpdate.mStreamType, i3, i4)) {
                i3 = audioService.mSoundDoseHelper.safeMediaVolumeIndex(i4);
            }
            volumeStreamState.setIndex(i3, i4, str, true);
            EventLogger eventLogger = sVolumeLogger;
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " dev:0x");
            m.append(Integer.toHexString(i4));
            m.append(" volIdx:");
            m.append(i3);
            eventLogger.enqueue(new EventLogger.StringEvent(m.toString()));
        } else {
            if (!"makeLeAudioDeviceAvailable".equals(str)) {
                i = i4;
                sVolumeLogger.enqueue(new EventLogger.StringEvent(AudioChannelMask$$ExternalSyntheticOutline0.m(Preconditions$$ExternalSyntheticOutline0.m(str, " update vol on dev:0x"), i)));
                audioService.setDeviceVolume(volumeStreamState, i);
            }
            if (i4 == 536870914 && audioService.isLeBroadcastWithoutLeDevice()) {
                volumeStreamState.setIndex(150, i4, str, true);
                EventLogger eventLogger2 = sVolumeLogger;
                StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, " dev:0x");
                m2.append(Integer.toHexString(i4));
                m2.append(" broadcast volIdx to 150");
                eventLogger2.enqueue(new EventLogger.StringEvent(m2.toString()));
            }
            int[] iArr = audioService.AVC_AFFECTED_STREAMS;
            int length = iArr.length;
            while (i2 < length) {
                sendMsg(audioService.mAudioHandler, 0, 2, i4, 0, audioService.mStreamStates[iArr[i2]], 0);
                i2++;
                i4 = i4;
            }
        }
        i = i4;
        audioService.setDeviceVolume(volumeStreamState, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x003b, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0042, code lost:
    
        throw r10;
     */
    /* renamed from: -$$Nest$monUnmuteStreamOnSingleVolDevice, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m278$$Nest$monUnmuteStreamOnSingleVolDevice(com.android.server.audio.AudioService r10, int r11, int r12) {
        /*
            java.lang.Object r0 = r10.mSettingsLock
            monitor-enter(r0)
            java.lang.Class<com.android.server.audio.AudioService$VolumeStreamState> r1 = com.android.server.audio.AudioService.VolumeStreamState.class
            monitor-enter(r1)     // Catch: java.lang.Throwable -> L3b
            com.android.server.audio.AudioService$VolumeStreamState[] r2 = r10.mStreamStates     // Catch: java.lang.Throwable -> L18
            r2 = r2[r11]     // Catch: java.lang.Throwable -> L18
            java.lang.String r3 = "onUnmuteStreamOnSingleVolDevice"
            r4 = 0
            boolean r3 = r2.mute(r3, r4)     // Catch: java.lang.Throwable -> L18
            if (r3 == 0) goto L1a
            r10.muteAliasStreams(r11, r4)     // Catch: java.lang.Throwable -> L18
            goto L1a
        L18:
            r10 = move-exception
            goto L3f
        L1a:
            int r9 = r10.getDeviceForStream(r11)     // Catch: java.lang.Throwable -> L18
            int r7 = r2.getIndex(r9)     // Catch: java.lang.Throwable -> L18
            r4 = r10
            r5 = r11
            r6 = r7
            r8 = r12
            r4.sendVolumeUpdate(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L18
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L18
            r12 = 3
            if (r11 != r12) goto L3d
            if (r3 == 0) goto L3d
            java.lang.Object r11 = r10.mHdmiClientLock     // Catch: java.lang.Throwable -> L3b
            monitor-enter(r11)     // Catch: java.lang.Throwable -> L3b
            r12 = 1
            r10.maybeSendSystemAudioStatusCommand(r12)     // Catch: java.lang.Throwable -> L38
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L38
            goto L3d
        L38:
            r10 = move-exception
            monitor-exit(r11)     // Catch: java.lang.Throwable -> L38
            throw r10     // Catch: java.lang.Throwable -> L3b
        L3b:
            r10 = move-exception
            goto L41
        L3d:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3b
            return
        L3f:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L18
            throw r10     // Catch: java.lang.Throwable -> L3b
        L41:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3b
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.m278$$Nest$monUnmuteStreamOnSingleVolDevice(com.android.server.audio.AudioService, int, int):void");
    }

    /* renamed from: -$$Nest$monUpdateAccessibilityServiceUids, reason: not valid java name */
    public static void m279$$Nest$monUpdateAccessibilityServiceUids(AudioService audioService) {
        int[] iArr;
        synchronized (audioService.mAccessibilityServiceUidsLock) {
            iArr = audioService.mAccessibilityServiceUids;
        }
        AudioSystem.setA11yServicesUids(iArr);
    }

    /* renamed from: -$$Nest$monUpdateVolumeStatesForAudioDevice, reason: not valid java name */
    public static void m280$$Nest$monUpdateVolumeStatesForAudioDevice(int i, AudioService audioService, String str) {
        audioService.getClass();
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        synchronized (audioService.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                for (int i2 = 0; i2 < numStreamTypes; i2++) {
                    try {
                        audioService.updateVolumeStates(i, i2, str);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$munSetSoundSettingEventBroadcastIntent, reason: not valid java name */
    public static void m281$$Nest$munSetSoundSettingEventBroadcastIntent(AudioService audioService, String str) {
        SoundEventReceiver soundEventReceiver;
        synchronized (audioService.mEventReceivers) {
            try {
                Iterator it = audioService.mEventReceivers.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        soundEventReceiver = null;
                        break;
                    }
                    soundEventReceiver = (SoundEventReceiver) it.next();
                    ComponentName componentName = soundEventReceiver.mEventReceiver;
                    if (componentName == null ? false : componentName.getPackageName().equals(str)) {
                        break;
                    }
                }
                MediaSessionService mediaSessionService = MediaSessionService.this;
                ComponentName componentName2 = mediaSessionService.mVolumeKeyLongPressReceiver;
                ComponentName componentName3 = mediaSessionService.mHighPriorityMediaKeyReceiver;
                if (soundEventReceiver != null) {
                    if (componentName2 != null && componentName2.getPackageName().equals(str)) {
                        audioService.mMediaSessionServiceInternal.setVolumeLongPressReceiver(null);
                    }
                    if (componentName3 != null && componentName3.getPackageName().equals(str)) {
                        audioService.mMediaSessionServiceInternal.setMediaKeyEventReceiver(null);
                    }
                    audioService.mEventReceivers.remove(soundEventReceiver);
                } else if ("com.samsung.android.soundassistant".equals(str)) {
                    if (componentName2 != null) {
                        audioService.mMediaSessionServiceInternal.setVolumeLongPressReceiver(null);
                    }
                    if (componentName3 != null) {
                        audioService.mMediaSessionServiceInternal.setMediaKeyEventReceiver(null);
                    }
                    audioService.mEventReceivers.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static {
        HashSet hashSet = new HashSet();
        DEVICE_MEDIA_UNMUTED_ON_PLUG_SET = hashSet;
        hashSet.add(4);
        hashSet.add(8);
        hashSet.add(131072);
        hashSet.add(134217728);
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_BLE_SET);
        hashSet.addAll(AudioSystem.DEVICE_OUT_ALL_USB_SET);
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
        sAppCastingLogger = new EventLogger(30, "App Casting history");
        SYSTEM_PACKAGE = new String[]{"android"};
        EMPTY_PACKAGE = new String[]{""};
        VOLUME_LIMIT_INDEX_EFFECT_ON = new int[]{6, 7, 8, 9, 11, 13, 13};
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.server.audio.AudioService$1] */
    /* JADX WARN: Type inference failed for: r8v19, types: [com.android.server.audio.AudioService$3] */
    /* JADX WARN: Type inference failed for: r8v20, types: [com.android.server.audio.AudioService$4] */
    /* JADX WARN: Type inference failed for: r8v23, types: [com.android.server.audio.AudioService$5] */
    /* JADX WARN: Type inference failed for: r8v24, types: [com.android.server.audio.AudioService$6] */
    /* JADX WARN: Type inference failed for: r8v38, types: [com.android.server.audio.AudioService$10] */
    public AudioService(Context context, AudioSystemAdapter audioSystemAdapter, SystemServerAdapter systemServerAdapter, SettingsAdapter settingsAdapter, AudioVolumeGroupHelper audioVolumeGroupHelper, DefaultAudioPolicyFacade defaultAudioPolicyFacade, AppOpsManager appOpsManager, PermissionEnforcer permissionEnforcer) {
        super(permissionEnforcer);
        AudioHandler audioHandler;
        LiveTranslatorManager liveTranslatorManager;
        this.mNotifAliasRing = false;
        this.mVolumeController = new VolumeController();
        this.mMode = new AtomicInteger(0);
        this.mSettingsLock = new Object();
        int i = 1;
        this.STREAM_VOLUME_ALIAS_VOICE = new int[]{0, 1, 2, 3, 4, 2, 6, 7, 2, 3, 3, 3};
        this.STREAM_VOLUME_ALIAS_TELEVISION = new int[]{3, 3, 3, 3, 3, 3, 6, 3, 3, 3, 3, 3};
        this.STREAM_VOLUME_ALIAS_NONE = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        this.STREAM_VOLUME_ALIAS_DEFAULT = new int[]{0, 1, 2, 3, 4, 2, 6, 7, 2, 3, 3, 3};
        this.mAvrcpAbsVolSupported = false;
        this.mCachedAbsVolDrivingStreamsLock = new Object();
        this.mCachedAbsVolDrivingStreams = new HashMap(Map.of(536870912, 3, 536870913, 3, 536870914, 3, 134217728, 3));
        this.mAudioSystemCallback = new AudioSystem.ErrorCallback() { // from class: com.android.server.audio.AudioService.1
            public final void onError(int i2) {
                List activeRecordingConfigurations;
                if (i2 != 100) {
                    return;
                }
                RecordingActivityMonitor recordingActivityMonitor = AudioService.this.mRecordMonitor;
                if (recordingActivityMonitor != null) {
                    synchronized (recordingActivityMonitor.mRecordStates) {
                        try {
                            Iterator it = ((ArrayList) recordingActivityMonitor.mRecordStates).iterator();
                            boolean z = false;
                            while (it.hasNext()) {
                                RecordingActivityMonitor.RecordingState recordingState = (RecordingActivityMonitor.RecordingState) it.next();
                                if (recordingState.mDeathHandler == null) {
                                    if (recordingState.isActiveConfiguration()) {
                                        RecordingActivityMonitor.sEventLogger.enqueue(new RecordingActivityMonitor.RecordingEvent(3, recordingState.mRiid, recordingState.mConfig));
                                        z = true;
                                    }
                                    it.remove();
                                }
                            }
                            activeRecordingConfigurations = z ? recordingActivityMonitor.getActiveRecordingConfigurations(true) : null;
                        } finally {
                        }
                    }
                    recordingActivityMonitor.dispatchCallbacks(activeRecordingConfigurations);
                }
                PlaybackActivityMonitor playbackActivityMonitor = AudioService.this.mPlaybackMonitor;
                if (playbackActivityMonitor != null) {
                    PlaybackActivityMonitor.sEventLogger.enqueue(new EventLogger.StringEvent("clear port id to piid map"));
                    synchronized (playbackActivityMonitor.mPlayerLock) {
                        com.android.media.audio.Flags.portToPiidSimplification();
                        Log.v("AS.PlaybackActivityMon", "clear port id to piid map:\n" + playbackActivityMonitor.mPortIdToPiid);
                        playbackActivityMonitor.mPortIdToPiid.clear();
                    }
                }
                AudioService.sendMsg(AudioService.this.mAudioHandler, 4, 1, 0, 0, null, 0);
                AudioService.sendMsg(AudioService.this.mAudioHandler, 23, 2, 0, 0, null, 0);
            }
        };
        this.mRingerModeExternal = -1;
        this.mRingerModeAffectedStreams = 0;
        this.mZenModeAffectedStreams = 0;
        this.mReceiver = new SamsungBroadcastReceiver(this, i);
        this.mUserRestrictionsListener = new AnonymousClass11();
        this.mSetModeDeathHandlers = new ArrayList();
        this.mPrevVolDirection = 0;
        this.mVolumeControlStream = -1;
        this.mUserSelectedVolumeControlStream = false;
        this.mForceControlStreamLock = new Object();
        this.mForceControlStreamClient = null;
        this.mFixedVolumeDevices = new HashSet(Arrays.asList(2048, 2097152));
        this.mFullVolumeDevices = new HashSet(Arrays.asList(262144, 262145));
        this.mAbsoluteVolumeDeviceInfoMap = new ArrayMap();
        this.mAbsVolumeMultiModeCaseDevices = new HashSet(Arrays.asList(134217728));
        this.mDockAudioMediaEnabled = true;
        final RestorableParameters restorableParameters = new RestorableParameters();
        restorableParameters.mMap = new LinkedHashMap() { // from class: com.android.server.audio.AudioService.RestorableParameters.1
            @Override // java.util.LinkedHashMap
            public final boolean removeEldestEntry(Map.Entry entry) {
                if (size() <= 1000) {
                    return false;
                }
                Log.w("AS.AudioService", "Parameter map exceeds 1000 removing " + entry.getKey());
                return true;
            }
        };
        this.mRestorableParameters = restorableParameters;
        this.mDockState = 0;
        this.mVolumePolicy = VolumePolicy.DEFAULT;
        this.mAssistantUids = new ArraySet();
        this.mPrimaryAssistantUid = -1;
        this.mActiveAssistantServiceUids = NO_ACTIVE_ASSISTANT_SERVICE_UIDS;
        this.mAccessibilityServiceUidsLock = new Object();
        this.mInputMethodServiceUid = -1;
        this.mInputMethodServiceUidLock = new Object();
        this.mSupportedSystemUsagesLock = new Object();
        this.mSupportedSystemUsages = new int[]{17};
        new UidObserver() { // from class: com.android.server.audio.AudioService.2
            public final void onUidCachedChanged(int i2, boolean z) {
                AudioService audioService = AudioService.this;
                audioService.queueMsgUnderWakeLock(audioService.mAudioHandler, 100, z ? 1 : 0, i2);
            }

            public final void onUidGone(int i2, boolean z) {
                AudioService audioService = AudioService.this;
                audioService.queueMsgUnderWakeLock(audioService.mAudioHandler, 100, 0, i2);
            }
        };
        this.mRttEnabled = false;
        this.mMasterMute = new AtomicBoolean(false);
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.android.server.audio.AudioService.3
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i2) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i2) {
                if (i2 != 0) {
                    return;
                }
                if (AudioService.this.mDisplayManager.getDisplay(0).getState() == 2) {
                    if (AudioService.this.mMonitorRotation) {
                        RotationHelper.enable();
                    }
                    AudioSystem.setParameters("screen_state=on");
                } else {
                    if (AudioService.this.mMonitorRotation) {
                        RotationHelper.disable();
                    }
                    AudioSystem.setParameters("screen_state=off");
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i2) {
            }
        };
        this.mSubscriptionChangedListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.audio.AudioService.4
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                Log.i("AS.AudioService", "onSubscriptionsChanged()");
                AudioService.sendMsg(AudioService.this.mAudioHandler, 54, 0, 0, 0, null, 0);
            }
        };
        this.mVoicePlaybackActive = new AtomicBoolean(false);
        this.mMediaPlaybackActive = new AtomicBoolean(false);
        this.mPlaybackActivityMonitor = new IPlaybackConfigDispatcher.Stub() { // from class: com.android.server.audio.AudioService.5
            public final void dispatchPlaybackConfigChange(List list, boolean z) {
                AudioService.sendMsg(AudioService.this.mAudioHandler, 29, 0, 0, 0, list, 0);
            }
        };
        this.mVoiceRecordingActivityMonitor = new IRecordingConfigDispatcher.Stub() { // from class: com.android.server.audio.AudioService.6
            public final void dispatchRecordingConfigChange(List list) {
                AudioService.sendMsg(AudioService.this.mAudioHandler, 37, 0, 0, 0, list, 0);
            }
        };
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
        this.mDynPolicyCallback = new AudioSystem.DynamicPolicyCallback() { // from class: com.android.server.audio.AudioService.10
            public final void onDynamicPolicyMixStateUpdate(String str, int i2) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                AudioService.sendMsg(AudioService.this.mAudioHandler, 19, 2, i2, 0, str, 0);
            }
        };
        this.mAudioServerStateListeners = new HashMap();
        this.mAudioPolicies = new HashMap();
        this.mAudioPolicyCounter = 0;
        this.mSamsungReceiver = new SamsungBroadcastReceiver(this, 0 == true ? 1 : 0);
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
        this.mSensorThread = null;
        this.mMediaSessionServiceInternal = null;
        this.mIsLeBroadCasting = false;
        this.mGoodCatchManager = null;
        this.mMicModeManager = null;
        this.mDeviceAliasManager = new DeviceAliasManager();
        this.mExternalVoipModeOwner = null;
        this.mLiveTranslatorManager = null;
        this.mLiveTranslatorDuringCall = false;
        this.mLiveTranslatorAllowApps = null;
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
        this.mRecordEventChecker = new AnonymousClass11();
        this.mForegroundUid = -1;
        this.mVolumeControllerStream = -1;
        this.mMultiSoundInterface = new AnonymousClass11();
        this.mKeyguardManager = null;
        AnonymousClass13 anonymousClass13 = new AnonymousClass13();
        anonymousClass13.mPeriodMs = 500;
        anonymousClass13.mCachedValue = Boolean.FALSE;
        this.mFMRadioRecordingChecker = anonymousClass13;
        this.mLastVolumeChangedIntentDevice = -1;
        this.PROJECTION_HOST_URI = new Uri.Builder().scheme("content").authority("androidx.car.app.connection").build();
        this.mConnectedAndroidAuto = false;
        this.mIgnoreDucking = false;
        sLifecycleLogger.enqueue(new EventLogger.StringEvent("AudioService()"));
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mAppOps = appOpsManager;
        this.mAudioSystem = audioSystemAdapter;
        this.mSystemServer = systemServerAdapter;
        this.mAudioVolumeGroupHelper = audioVolumeGroupHelper;
        this.mSettings = settingsAdapter;
        this.mAudioPolicy = defaultAudioPolicyFacade;
        HandlerThread handlerThread = new HandlerThread("AudioService Broadcast");
        this.mBroadcastHandlerThread = handlerThread;
        handlerThread.start();
        int platformType = PlatformTypeUtils.getPlatformType(context);
        this.mPlatformType = platformType;
        Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION |= platformType != 1;
        if (platformType == 1) {
            Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION = false;
        }
        AudioDeviceBroker audioDeviceBroker = new AudioDeviceBroker(context, this, audioSystemAdapter);
        this.mDeviceBroker = audioDeviceBroker;
        this.mIsSingleVolume = AudioSystem.isSingleVolume(context);
        this.mUserManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mSensorPrivacyManagerInternal = (SensorPrivacyManagerInternal) LocalServices.getService(SensorPrivacyManagerInternal.class);
        this.mAudioEventWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "handleAudioEvent");
        this.mSfxHelper = new SoundEffectsHelper(context, new AudioService$$ExternalSyntheticLambda16(0 == true ? 1 : 0, this));
        this.mSpatializerHelper = new SpatializerHelper(this, audioSystemAdapter, audioDeviceBroker, SystemProperties.getBoolean("ro.audio.spatializer_binaural_enabled_default", true), SystemProperties.getBoolean("ro.audio.spatializer_transaural_enabled_default", true), context.getResources().getBoolean(R.bool.config_supportTelephonyTimeZoneFallback));
        Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
        this.mVibrator = vibrator;
        this.mHasVibrator = vibrator == null ? false : vibrator.hasVibrator();
        this.mSupportsMicPrivacyToggle = ((SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class)).supportsSensorToggle(1);
        this.mUseVolumeGroupAliases = context.getResources().getBoolean(R.bool.config_isCameraCompatControlForStretchedIssuesEnabled);
        this.mPackageManager = context.getPackageManager();
        int i2 = SystemProperties.getInt("ro.config.vc_call_vol_steps", -1);
        int[] iArr = MAX_STREAM_VOLUME;
        int i3 = iArr[0];
        if (i2 != -1) {
            DirEncryptService$$ExternalSyntheticOutline0.m(i2, "use default ro.config.vc_call_vol_steps ", "AS.AudioService");
        } else {
            i2 = i3;
        }
        iArr[0] = i2;
        new AudioSystemThread().start();
        synchronized (this) {
            while (true) {
                audioHandler = this.mAudioHandler;
                if (audioHandler != null) {
                    break;
                }
                try {
                    wait();
                } catch (InterruptedException unused) {
                    Log.e("AS.AudioService", "Interrupted while waiting on volume handler.");
                }
            }
        }
        this.mSoundDoseHelper = new SoundDoseHelper(this, this.mContext, audioHandler, this.mSettings, this.mVolumeController);
        AudioSystem.setErrorCallback(this.mAudioSystemCallback);
        updateAudioHalPids();
        AudioSettingsHelper audioSettingsHelper = AudioSettingsHelper.getInstance(this.mContext);
        this.mSettingHelper = audioSettingsHelper;
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            this.mMicModeManager = MicModeManager.getInstance(this.mContext);
        }
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            Context context2 = this.mContext;
            ContentResolver contentResolver = LiveTranslatorManager.mCr;
            synchronized (LiveTranslatorManager.class) {
                try {
                    if (LiveTranslatorManager.sInstance == null) {
                        LiveTranslatorManager.sInstance = new LiveTranslatorManager(context2);
                    }
                    liveTranslatorManager = LiveTranslatorManager.sInstance;
                } finally {
                }
            }
            this.mLiveTranslatorManager = liveTranslatorManager;
        }
        this.mExt = new AudioServiceExt(context, this, this.mAudioSystem, audioSettingsHelper, this.mSfxHelper, this.mMicModeManager, this.mLiveTranslatorManager);
        this.mUseFixedVolume = this.mContext.getResources().getBoolean(R.bool.config_voice_capable);
        this.mRingerModeAffectsAlarm = this.mContext.getResources().getBoolean(R.bool.config_autoPowerModePrefetchLocation);
        RecordingActivityMonitor recordingActivityMonitor = new RecordingActivityMonitor(this.mContext);
        this.mRecordMonitor = recordingActivityMonitor;
        recordingActivityMonitor.registerRecordingCallback(this.mVoiceRecordingActivityMonitor, true);
        ((HashSet) this.mFixedVolumeDevices).clear();
        this.mMultiSoundManager = new MultiSoundManager(this.mContext, this.mMultiSoundInterface, this.mAudioHandler, this.mAudioSystem);
        updateStreamVolumeAlias("AS.AudioService", false);
        readPersistedSettings();
        readUserRestrictions();
        this.mLoudnessCodecHelper = new LoudnessCodecHelper(this);
        PlaybackActivityMonitor playbackActivityMonitor = new PlaybackActivityMonitor(context, MAX_STREAM_VOLUME[4], new AudioService$$ExternalSyntheticLambda16(i, this), this);
        this.mPlaybackMonitor = playbackActivityMonitor;
        playbackActivityMonitor.registerPlaybackCallback(this.mPlaybackActivityMonitor, true);
        this.mMediaFocusControl = new MediaFocusControl(this.mContext, playbackActivityMonitor, this);
        readAndSetLowRamDevice();
        this.mIsCallScreeningModeSupported = AudioSystem.isCallScreeningModeSupported();
        this.mSystemServer.getClass();
        LocalServices.addService(AudioManagerInternal.class, new AudioServiceInternal());
        this.mUserManagerInternal.addUserRestrictionsListener(this.mUserRestrictionsListener);
        AudioSystem.setRecordingCallback(recordingActivityMonitor);
        this.mMonitorRotation = SystemProperties.getBoolean("ro.audio.monitorRotation", false);
        this.mHasSpatializerEffect = SystemProperties.getBoolean("ro.audio.spatializer_enabled", false);
        synchronized (AudioSystemAdapter.sRoutingListenerLock) {
            AudioSystemAdapter.sRoutingListener = this;
        }
        synchronized (AudioSystemAdapter.sVolRangeInitReqListenerLock) {
            AudioSystemAdapter.sVolRangeInitReqListener = this;
        }
        queueMsgUnderWakeLock(this.mAudioHandler, 101, 0, 0);
        queueMsgUnderWakeLock(this.mAudioHandler, 103, 0, 0);
        queueMsgUnderWakeLock(this.mAudioHandler, 102, 0, 0);
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        this.mMusicFxHelper = new MusicFxHelper(this.mContext, this.mAudioHandler);
        this.mHardeningEnforcer = new HardeningEnforcer(this.mContext, this.mPlatformType == 3, this.mAppOps, context.getPackageManager());
        Trace.traceBegin(256L, "setupCustomRoutine");
        Log.i("AS.AudioService", "start setupCustomRoutine");
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        if (Rune.SEC_AUDIO_VOLUME_KEY_SUPPORT_SILENT) {
            this.mVolumePolicy = new VolumePolicy(true, true, true, 400);
        } else {
            this.mVolumePolicy = new VolumePolicy(!this.mHasVibrator, true, true, 400);
        }
        String string = Settings.System.getString(this.mContentResolver, "sec_volume_steps");
        if (string != null && string.length() != 0) {
            try {
                String[] split = string.split(",");
                this.mVolumeSteps = new int[split.length];
                for (int i4 = 0; i4 < split.length; i4++) {
                    this.mVolumeSteps[i4] = Integer.parseInt(split[i4]);
                }
                createVariableMediaVolumeMap(this.mVolumeSteps);
            } catch (Exception e) {
                Log.d("AS.AudioService", "reloadVariableMediaVolumeSteps", e);
            }
        }
        this.mSavedSpeakerMediaIndex = this.mSettingHelper.getIntValue(-1, "speaker_media_index");
        AudioSettingsHelper audioSettingsHelper2 = this.mSettingHelper;
        audioSettingsHelper2.getClass();
        this.mMuteMediaByVibrateOrSilentMode = audioSettingsHelper2.getInt("audio_settings", new String[]{"_key", "_value"}, "_key='mute_media_by_vibrate_or_silent_mode'", -1) > 0;
        this.mSystemServer.getClass();
        SemAudioServiceInternal semAudioServiceInternal = new SemAudioServiceInternal();
        semAudioServiceInternal.mAudioService = new WeakReference(this);
        LocalServices.addService(SemAudioServiceInternal.class, semAudioServiceInternal);
        Log.i("AS.AudioService", "end setupCustomRoutine");
        Trace.endSection();
    }

    public static List anonymizeAudioDeviceAttributesListUnchecked(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(anonymizeAudioDeviceAttributesUnchecked((AudioDeviceAttributes) it.next()));
        }
        return arrayList;
    }

    public static AudioDeviceAttributes anonymizeAudioDeviceAttributesUnchecked(AudioDeviceAttributes audioDeviceAttributes) {
        if (!AudioSystem.isBluetoothDevice(audioDeviceAttributes.getInternalType())) {
            return audioDeviceAttributes;
        }
        AudioDeviceAttributes audioDeviceAttributes2 = new AudioDeviceAttributes(audioDeviceAttributes);
        audioDeviceAttributes2.setAddress(Utils.anonymizeBluetoothAddress(audioDeviceAttributes.getAddress()));
        return audioDeviceAttributes2;
    }

    public static String dumpDeviceTypes(Set set) {
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

    public static void dumpRingerModeStreams(PrintWriter printWriter, String str, int i) {
        printWriter.print("- ringer mode ");
        printWriter.print(str);
        printWriter.print(" streams = 0x");
        printWriter.print(Integer.toHexString(i));
        if (i != 0) {
            printWriter.print(" (");
            boolean z = true;
            int i2 = 0;
            while (true) {
                String[] strArr = AudioSystem.STREAM_NAMES;
                if (i2 >= strArr.length) {
                    break;
                }
                int i3 = 1 << i2;
                if ((i & i3) != 0) {
                    if (!z) {
                        printWriter.print(',');
                    }
                    printWriter.print(strArr[i2]);
                    i &= ~i3;
                    z = false;
                }
                i2++;
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

    public static void ensureValidAttributes(AudioVolumeGroup audioVolumeGroup) {
        if (audioVolumeGroup.getAudioAttributes().stream().anyMatch(new AudioService$$ExternalSyntheticLambda3(1))) {
            return;
        }
        throw new IllegalArgumentException("Volume Group " + audioVolumeGroup.name() + " has no valid audio attributes");
    }

    public static void ensureValidDirection(int i) {
        if (i != -100 && i != -1 && i != 0 && i != 1 && i != 100 && i != 101) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad direction "));
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

    public static int getIndexDividedBy10(int i, int i2) {
        return i2 == 3 ? (i + 9) / 10 : (i + 5) / 10;
    }

    public static boolean isMuteAdjust(int i) {
        return i == -100 || i == 100 || i == 101;
    }

    public static boolean isStreamMutedByRingerOrZenMode(int i) {
        return ((1 << i) & sRingerAndZenModeMutedStreams) != 0;
    }

    public static boolean isSurroundFormat(int i) {
        for (int i2 : AudioFormat.SURROUND_SOUND_ENCODING) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
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
            NetworkScoreService$$ExternalSyntheticOutline0.m(lowRamDevice, "AudioFlinger informed of device's low RAM attribute; status ", "AS.AudioService");
        }
    }

    public static int rescaleIndex(int i, int i2, int i3, int i4, int i5) {
        int i6 = i3 - i2;
        int i7 = i5 - i4;
        if (i6 == 0) {
            Log.e("AS.AudioService", "rescaleIndex : index range should not be zero");
            return i4;
        }
        return (((i6 / 2) + ((i - i2) * i7)) / i6) + i4;
    }

    public static void sendMsg(Handler handler, int i, int i2, int i3, int i4, Object obj, int i5) {
        if (i2 == 0) {
            handler.removeMessages(i);
        } else if (i2 == 1 && handler.hasMessages(i)) {
            return;
        }
        handler.sendMessageAtTime(handler.obtainMessage(i, i3, i4, obj), SystemClock.uptimeMillis() + i5);
    }

    public static void updateAudioHalPids() {
        String str;
        HashSet hashSet = new HashSet();
        try {
            ServiceDebugInfo[] serviceDebugInfo = ServiceManager.getServiceDebugInfo();
            if (serviceDebugInfo != null) {
                for (ServiceDebugInfo serviceDebugInfo2 : serviceDebugInfo) {
                    if (serviceDebugInfo2.debugPid > 0 && serviceDebugInfo2.name.startsWith("android.hardware.audio")) {
                        hashSet.add(Integer.valueOf(serviceDebugInfo2.debugPid));
                    }
                }
            }
        } catch (RuntimeException unused) {
        }
        try {
            Iterator it = IServiceManager.getService().debugDump().iterator();
            while (it.hasNext()) {
                IServiceManager.InstanceDebugInfo instanceDebugInfo = (IServiceManager.InstanceDebugInfo) it.next();
                if (instanceDebugInfo.pid != -1 && (str = instanceDebugInfo.interfaceName) != null && str.startsWith("android.hardware.audio")) {
                    hashSet.add(Integer.valueOf(instanceDebugInfo.pid));
                }
            }
        } catch (RemoteException | RuntimeException unused2) {
        }
        if (hashSet.isEmpty()) {
            Slog.w("AS.AudioService", "Could not retrieve audio HAL service pids");
        } else {
            AudioSystem.setAudioHalPids(hashSet.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray());
        }
    }

    public static void updateDefaultStreamOverrideDelay(boolean z) {
        if (z) {
            sStreamOverrideDelayMs = 1000;
        } else {
            sStreamOverrideDelayMs = 0;
        }
        AudioService$$ExternalSyntheticOutline0.m(BatteryService$$ExternalSyntheticOutline0.m("Touch exploration enabled=", " stream override delay is now ", z), sStreamOverrideDelayMs, " ms", "AS.AudioService");
    }

    public final int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) {
        MediaMetrics.Item item = new MediaMetrics.Item("audio.service.focus").set(MediaMetrics.Property.CALLING_PACKAGE, str2).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "abandonAudioFocus");
        if (audioAttributes == null || isValidAudioAttributesUsage(audioAttributes)) {
            item.record();
            this.mMediaFocusControl.abandonAudioFocus(str, audioAttributes, str2);
            return 1;
        }
        Log.w("AS.AudioService", "Request using unsupported usage.");
        item.set(MediaMetrics.Property.EARLY_RETURN, "unsupported usage").record();
        return 0;
    }

    public final int abandonAudioFocusForTest(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) {
        if (!enforceQueryAudioStateForTest("focus abandon")) {
            return 0;
        }
        this.mMediaFocusControl.abandonAudioFocus(str, audioAttributes, str2);
        return 1;
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
            AudioSystem.setAssistantServicesUids(this.mAssistantUids.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray());
        }
    }

    public final void addAssistantServicesUids(int[] iArr) {
        addAssistantServicesUids_enforcePermission();
        Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            addAssistantServiceUidsLocked(iArr);
        }
    }

    public final void addLoudnessCodecInfo(int i, int i2, LoudnessCodecInfo loudnessCodecInfo) {
        LoudnessCodecHelper loudnessCodecHelper = this.mLoudnessCodecHelper;
        loudnessCodecHelper.getClass();
        int callingPid = Binder.getCallingPid();
        LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new LoudnessCodecHelper.LoudnessTrackId(i, callingPid);
        synchronized (loudnessCodecHelper.mLock) {
            if (loudnessCodecHelper.mStartedConfigInfo.containsKey(loudnessTrackId) && loudnessCodecHelper.mStartedConfigPiids.containsKey(loudnessTrackId)) {
                Set set = (Set) loudnessCodecHelper.mStartedConfigPiids.get(loudnessTrackId);
                ((Set) loudnessCodecHelper.mStartedConfigInfo.get(loudnessTrackId)).add(loudnessCodecInfo);
                SafeCloseable create = ClearCallingIdentityContext.create();
                try {
                    PersistableBundle persistableBundle = new PersistableBundle();
                    Optional findFirst = loudnessCodecHelper.mAudioService.getActivePlaybackConfigurations().stream().filter(new LoudnessCodecHelper$$ExternalSyntheticLambda1(i, callingPid, 1)).findFirst();
                    if (findFirst.isEmpty()) {
                        persistableBundle.putPersistableBundle(Integer.toString(i2), loudnessCodecHelper.getLoudnessParams(loudnessCodecInfo));
                    } else {
                        AudioDeviceInfo audioDeviceInfo = ((AudioPlaybackConfiguration) findFirst.get()).getAudioDeviceInfo();
                        if (audioDeviceInfo != null) {
                            synchronized (loudnessCodecHelper.mLock) {
                                set.add(Integer.valueOf(((AudioPlaybackConfiguration) findFirst.get()).getPlayerInterfaceId()));
                                persistableBundle.putPersistableBundle(Integer.toString(i2), loudnessCodecHelper.getCodecBundle_l(audioDeviceInfo.getInternalType(), audioDeviceInfo.getAddress(), loudnessCodecInfo));
                            }
                        }
                    }
                    if (!persistableBundle.isDefinitelyEmpty()) {
                        loudnessCodecHelper.dispatchNewLoudnessParameters(i, persistableBundle);
                    }
                    if (create != null) {
                        create.close();
                        return;
                    }
                    return;
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
            Log.w("AS.LoudnessCodecHelper", "Cannot add new loudness info for stopped config " + loudnessTrackId);
        }
    }

    public final int addMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) {
        Log.d("AS.AudioService", "addMixForPolicy for " + iAudioPolicyCallback.asBinder() + " with config:" + audioPolicyConfig);
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot add AudioMix in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                return checkUpdateForPolicy.addMixes(audioPolicyConfig.getMixes()) == 0 ? 0 : -1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addOnDevicesForAttributesChangedListener(AudioAttributes audioAttributes, IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        AudioSystemAdapter audioSystemAdapter = this.mAudioSystem;
        audioSystemAdapter.getClass();
        Pair pair = new Pair(audioAttributes, Boolean.FALSE);
        synchronized (audioSystemAdapter.mRegisteredAttributesMap) {
            try {
                List list = (List) audioSystemAdapter.mRegisteredAttributesMap.get(iDevicesForAttributesCallback.asBinder());
                if (list == null) {
                    list = new ArrayList();
                    audioSystemAdapter.mRegisteredAttributesMap.put(iDevicesForAttributesCallback.asBinder(), list);
                    audioSystemAdapter.mDevicesForAttributesCallbacks.register(iDevicesForAttributesCallback);
                }
                if (!list.contains(pair)) {
                    list.add(pair);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        audioSystemAdapter.getDevicesForAttributes(audioAttributes, false);
    }

    public final void addPackage(int i, String str) {
        enforceModifyAudioRoutingPermission();
        PackageListHelper packageListHelper = this.mPackageListHelper;
        Context context = this.mContext;
        packageListHelper.getClass();
        PackageListHelper.addPackage(context, str);
    }

    public final void addSamsungExtraDump(PrintWriter printWriter) {
        sRingerModeLogger.dump(printWriter);
        sMicrophoneLogger.dump(printWriter);
        sMasterMuteLogger.dump(printWriter);
        sUsingAudioLogger.dump(printWriter);
        sAppVolumeLogger.dump(printWriter);
        sRingtoneChangeLogger.dump(printWriter);
        printWriter.println("\nAudio Setting:");
        AudioServiceExt audioServiceExt = this.mExt;
        audioServiceExt.getClass();
        printWriter.print("  mNbQualityMode=");
        printWriter.println(audioServiceExt.mNbQualityMode);
        if (Rune.SEC_AUDIO_SCREEN_CALL) {
            printWriter.print("  mScreenCall=");
            printWriter.println(audioServiceExt.mScreenCall);
        }
        printWriter.print("  mMasterMono=");
        printWriter.println(audioServiceExt.mMainMono);
        printWriter.print("  mMainBalance=");
        printWriter.println(audioServiceExt.mMainBalance);
        printWriter.print("  mAdaptSoundEnabled=");
        printWriter.println(audioServiceExt.mAdaptSoundEnabled);
        printWriter.print("  mUpscalerEnabled=");
        printWriter.println(audioServiceExt.mUpscalerEnabled);
        printWriter.print("  mIsPttCallVolumeEnabled=");
        printWriter.println(audioServiceExt.mIsPttCallVolumeEnabled);
        printWriter.print("  CPUBoostValueForVoIP=");
        audioServiceExt.mDvfsHelper.getClass();
        printWriter.println(0);
        printWriter.print("  mAllSoundMute=");
        printWriter.println(audioServiceExt.mAllSoundMute);
        if (Rune.SEC_AUDIO_KARAOKE_LISTENBACK) {
            printWriter.print("  mKaraokeListenbackEnabled=");
            printWriter.println(AudioServiceExt.mKaraokeListenbackEnabled);
        }
        if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
            printWriter.print("  mVolumeMonitorValue=");
            printWriter.println(audioServiceExt.mVolumeMonitorValue);
        }
        printWriter.print("  AudioHqmHelper.ResetCount=");
        printWriter.println(AudioHqmHelper.mAudioServerResetCountMaxLimit ? Integer.MAX_VALUE : AudioHqmHelper.mAudioServerResetCount);
        printWriter.print("  mIsSupportDisplayVolumeControl=");
        printWriter.println(this.mScreenSharingHelper.mIsSupportDisplayVolumeControl);
        printWriter.print("  mIsDLNAEnabled=");
        printWriter.println(this.mScreenSharingHelper.mIsDLNAEnabled);
        printWriter.print("  isTalkBackEnabled=");
        printWriter.println(this.mIsTalkBackEnabled);
        printWriter.print("  mAdjustMediaVolumeOnly=");
        printWriter.println(this.mAdjustMediaVolumeOnly);
        printWriter.print("  mMuteMediaByVibrateOrSilentMode=");
        printWriter.println(this.mMuteMediaByVibrateOrSilentMode);
        printWriter.print("  mMediaVolumeStepIndex=");
        printWriter.println(this.mMediaVolumeStepIndex);
        printWriter.print("  mMediaVolumeSteps=");
        printWriter.println(Arrays.toString(this.mVolumeSteps));
        printWriter.print("  mHeadsetOnlyStream=");
        printWriter.println(this.mHeadsetOnlyStream);
        printWriter.print("  mLRSwitching=");
        printWriter.println(this.mLRSwitching);
        printWriter.print("  mIgnoreDucking=");
        printWriter.println(this.mIgnoreDucking);
        printWriter.print("  SEC_AUDIO_SUPPORT_DUAL_SPEAKER=");
        printWriter.println(true);
        printWriter.print("  mVolumeLimitOn=");
        printWriter.println(this.mVolumeLimitOn);
        printWriter.print("  mVolumeLimitValue=");
        printWriter.println(this.mVolumeLimitValue);
        printWriter.print("  OneUIVersion=");
        Context context = this.mContext;
        int i = Build.VERSION.SEM_PLATFORM_INT;
        printWriter.println((context.getPackageManager().hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite") ? "core" : "") + ((i - KnoxCustomManagerService.ONE_UI_VERSION_SEP_VERSION_GAP) / 10000) + "." + ((i % 10000) / 100));
        printWriter.print("  SEC_AUDIO_SUPPORT_ACH_RINGTONE=");
        printWriter.println(Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE);
        MultiSoundManager multiSoundManager = this.mMultiSoundManager;
        synchronized (multiSoundManager.mMultiSoundLock) {
            printWriter.println("\nMultiSound, size:" + multiSoundManager.mPinAppInfoList.size() + ", isEnabled:" + multiSoundManager.mEnabled);
        }
        Iterator it = multiSoundManager.mPinAppInfoList.entrySet().iterator();
        while (it.hasNext()) {
            MultiSoundManager.MultiSoundItem multiSoundItem = (MultiSoundManager.MultiSoundItem) ((Map.Entry) it.next()).getValue();
            AnonymousClass11 anonymousClass11 = multiSoundManager.mInterface;
            int i2 = multiSoundItem.mUid;
            anonymousClass11.getClass();
            String str = AudioService.this.getPackageName(i2)[0];
            int i3 = multiSoundItem.mDevice;
            StringBuilder sb = new StringBuilder("  uid = ");
            AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(multiSoundItem.mUid, "(", str, "), device=0x", sb);
            sb.append(Integer.toHexString(i3));
            sb.append(", ");
            printWriter.print(sb.toString());
            printWriter.print("name= " + AudioSystem.getOutputDeviceName(i3) + ", ");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("volume="), multiSoundItem.mRatio, printWriter);
        }
        int deviceMaskFromSet = AudioSystem.getDeviceMaskFromSet(AudioService.this.mStreamStates[3].mObservedDeviceSet);
        printWriter.print("  Current Default Device: 0x" + Integer.toHexString(deviceMaskFromSet));
        printWriter.println(" : " + AudioSystem.getOutputDeviceName(deviceMaskFromSet));
        int pinDevice = multiSoundManager.getPinDevice(true);
        printWriter.print("  Pin Device (IgnoreConnection): 0x" + pinDevice);
        printWriter.println(" : " + AudioSystem.getOutputDeviceName(multiSoundManager.getPinDevice(true)));
        printWriter.println("  Pin Apps: " + multiSoundManager.getPinAppInfo(pinDevice));
        printWriter.println("  SEC_AUDIO_MULTI_SOUND=true");
        AnonymousClass11 anonymousClass112 = multiSoundManager.mInterface;
        anonymousClass112.getClass();
        try {
            if (AudioService.this.mContext.getPackageManager().getApplicationInfo("com.samsung.android.oneconnect", 0) != null) {
                printWriter.println("  Smart Things=install");
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        printWriter.print("  mPreventOverheatState=");
        printWriter.println(multiSoundManager.mPreventOverheatState);
        printWriter.print("  Dual App : ");
        synchronized (multiSoundManager.mRemoteSubmixApps) {
            try {
                Iterator it2 = ((HashSet) multiSoundManager.mRemoteSubmixApps).iterator();
                while (it2.hasNext()) {
                    printWriter.print(((Integer) it2.next()) + " ");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println("");
        sAppCastingLogger.dump(printWriter);
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            MicModeManager micModeManager = this.mMicModeManager;
            micModeManager.getClass();
            printWriter.println("\nMicModeManager dump:");
            MicModeType micModeType = micModeManager.mMicModeType;
            printWriter.println("  mMicModeType:".concat(micModeType.getTypeToString()));
            printWriter.println("  mCallMicMode:" + micModeType.getCallModeToString(micModeType.getCallMicMode()));
            printWriter.println("  mVoipCallMicMode:" + micModeType.getCallModeToString(micModeType.getVoipCallMicMode()));
            MicModeManager.sMicModeLogger.dump(printWriter);
            printWriter.println();
        }
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        synchronized (audioDeviceBroker.mDeviceStateLock) {
            audioDeviceBroker.mDualA2dpManager.dump(printWriter);
        }
        DeviceAliasManager deviceAliasManager = this.mDeviceAliasManager;
        deviceAliasManager.getClass();
        printWriter.println("\nDevice Aliases:");
        for (int i4 = 0; i4 < deviceAliasManager.mDevices.size(); i4++) {
            int keyAt = deviceAliasManager.mDevices.keyAt(i4);
            printWriter.println("- " + AudioSystem.getDeviceName(keyAt) + ":");
            DeviceAliasManager.DeviceAlias deviceAlias = (DeviceAliasManager.DeviceAlias) deviceAliasManager.mDevices.get(keyAt);
            if (deviceAlias != null) {
                StringBuilder sb2 = new StringBuilder("  Alias: ");
                for (int i5 : deviceAlias.mAliases) {
                    sb2.append(AudioSystem.getDeviceName(i5));
                    sb2.append(" ");
                }
                sb2.append("\n  Exclude Streams: ");
                for (int i6 = 0; i6 < AudioSystem.getNumStreamTypes(); i6++) {
                    if (((1 << i6) & deviceAlias.mExcludeStreams) > 0) {
                        sb2.append(i6);
                        sb2.append(" ");
                    }
                }
                printWriter.println(sb2.toString());
            }
        }
        printWriter.println();
        sScpmLogger.dump(printWriter);
        AudioSettingsHelper audioSettingsHelper = this.mSettingHelper;
        audioSettingsHelper.getClass();
        printWriter.println();
        printWriter.print("  SCPM App List version = ");
        printWriter.println(audioSettingsHelper.getIntValue(0, "APP_LIST_VERSION"));
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
        BtUtils.sAuracastLogger.dump(printWriter);
        printWriter.println("");
        StringBuilder sb3 = new StringBuilder("BLE broadcast app uids = ");
        ArrayList arrayList = BtUtils.sBtAppUidList;
        String str2 = "";
        if (arrayList != null) {
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                str2 = str2 + ((Integer) it3.next()).intValue() + ";";
            }
        }
        sb3.append(str2);
        printWriter.println(sb3.toString());
        printWriter.println("");
    }

    public final void addSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) {
        addSpatializerCompatibleAudioDevice_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.addCompatibleAudioDevice(audioDeviceAttributes, true, false);
        }
    }

    public final void adjustStreamVolume(int i, int i2, int i3, String str) {
        adjustStreamVolumeWithAttribution(i, i2, i3, str, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x02ba, code lost:
    
        if (r4[r13] == r4[2]) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x02cc, code lost:
    
        if (isStreamMutedByRingerOrZenMode(r13) == false) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x02d0, code lost:
    
        if (r6.mIsMuted == false) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x02d2, code lost:
    
        r4 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x02d4, code lost:
    
        if (r4 == 101) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x02d6, code lost:
    
        if (r4 == 100) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x02d9, code lost:
    
        if (r4 != 1) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x02e9, code lost:
    
        r30 = r6;
        r8 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x02e1, code lost:
    
        r12 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x06fc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x0796, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:423:0x02dd, code lost:
    
        r30 = r6;
        r8 = r10;
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x02e6, code lost:
    
        r4 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:427:0x02c2, code lost:
    
        if (r13 == com.android.server.audio.AudioService.mStreamVolumeAlias[2]) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:502:0x039a, code lost:
    
        if (r4 == 100) goto L236;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0413  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x041f  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0436 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x046c  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x047d  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x04d6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:220:0x064e  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0692  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x06e3  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x078a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:273:0x07ab  */
    /* JADX WARN: Removed duplicated region for block: B:293:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:310:0x0745 A[Catch: all -> 0x06fc, TRY_LEAVE, TryCatch #6 {all -> 0x06fc, all -> 0x0769, blocks: (B:242:0x06e6, B:244:0x06ea, B:248:0x06f4, B:251:0x0700, B:253:0x0704, B:255:0x0708, B:257:0x070e, B:268:0x078e, B:269:0x0793, B:294:0x0720, B:310:0x0745, B:319:0x077d, B:326:0x0782, B:327:0x0785, B:317:0x0751, B:321:0x076b, B:322:0x0770, B:323:0x0775), top: B:241:0x06e6 }] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0797  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x0677  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:438:0x030e  */
    /* JADX WARN: Removed duplicated region for block: B:447:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x03fe  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:464:0x040b  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:505:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01b7 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01b8  */
    /* JADX WARN: Type inference failed for: r2v60 */
    /* JADX WARN: Type inference failed for: r2v61 */
    /* JADX WARN: Type inference failed for: r2v62, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v93 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void adjustStreamVolume(int r28, int r29, int r30, java.lang.String r31, java.lang.String r32, int r33, int r34, java.lang.String r35, boolean r36, int r37) {
        /*
            Method dump skipped, instructions count: 2043
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.adjustStreamVolume(int, int, int, java.lang.String, java.lang.String, int, int, java.lang.String, boolean, int):void");
    }

    public final void adjustStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Should only be called from system process");
        }
        if (i2 != 0) {
            sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(5, i, i2, i3, str + " uid:" + i4));
        }
        adjustStreamVolume(i, i2, i3, str, str, i4, i5, null, this.mContext.checkPermission("android.permission.MODIFY_AUDIO_SETTINGS", i5, i4) == 0, 0);
    }

    public final void adjustStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) {
        if (this.mHardeningEnforcer.blockVolumeMethod(103)) {
            return;
        }
        if (i == 10 && !canChangeAccessibilityVolume()) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Trying to call adjustStreamVolume() for a11y withoutCHANGE_ACCESSIBILITY_VOLUME / callingPackage=", str, "AS.AudioService");
            return;
        }
        AudioServiceEvents$VolumeEvent audioServiceEvents$VolumeEvent = new AudioServiceEvents$VolumeEvent(1, i, i2, i3, str);
        sVolumeLogger.enqueue(audioServiceEvents$VolumeEvent);
        if (isMuteAdjust(i2)) {
            sMuteLogger.enqueue(audioServiceEvents$VolumeEvent);
        }
        adjustStreamVolume(i, i2, i3, str, str, Binder.getCallingUid(), Binder.getCallingPid(), str2, this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0, 0);
    }

    public final void adjustSuggestedStreamVolume(int i, int i2, int i3) {
        if (this.mHardeningEnforcer.blockVolumeMethod(102)) {
            return;
        }
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (MediaSessionManager) this.mContext.getSystemService("media_session");
        }
        this.mMediaSessionManager.dispatchAdjustVolume(i2, i, i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00e6 A[Catch: all -> 0x00c9, TryCatch #0 {all -> 0x00c9, blocks: (B:25:0x00c1, B:27:0x00c5, B:29:0x00ec, B:33:0x00cb, B:38:0x00d6, B:40:0x00e6, B:43:0x00e0), top: B:24:0x00c1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void adjustSuggestedStreamVolume(int r15, int r16, int r17, java.lang.String r18, java.lang.String r19, int r20, int r21, boolean r22, int r23) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.adjustSuggestedStreamVolume(int, int, int, java.lang.String, java.lang.String, int, int, boolean, int):void");
    }

    public final void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Should only be called from system process");
        }
        adjustSuggestedStreamVolume(i2, i, i3, str, str, i4, i5, this.mContext.checkPermission("android.permission.MODIFY_AUDIO_SETTINGS", i5, i4) == 0, 0);
    }

    public final void adjustVolume(int i, int i2) {
        if (this.mHardeningEnforcer.blockVolumeMethod(101)) {
            return;
        }
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (MediaSessionManager) this.mContext.getSystemService("media_session");
        }
        this.mMediaSessionManager.dispatchAdjustVolume(Integer.MIN_VALUE, i, i2);
    }

    public final void adjustVolumeGroupVolume(int i, int i2, int i3, String str) {
        ensureValidDirection(i2);
        SparseArray sparseArray = sVolumeGroupStates;
        if (sparseArray.indexOfKey(i) < 0) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, ": no volume group found for id ", "AS.AudioService");
            return;
        }
        VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray.get(i);
        boolean z = false;
        for (int i4 : volumeGroupState.mAudioVolumeGroup.getLegacyStreamTypes()) {
            try {
                ensureValidStreamType(i4);
                if (volumeGroupState.isVssMuteBijective(i4)) {
                    adjustStreamVolumeWithAttribution(i4, i2, i3, str, null);
                    if (isMuteAdjust(i2)) {
                        return;
                    } else {
                        z = true;
                    }
                } else {
                    continue;
                }
            } catch (IllegalArgumentException unused) {
                Log.d("AS.AudioService", DualAppManagerService$$ExternalSyntheticOutline0.m(i, i4, "volume group ", " has internal streams (", "), do not change associated stream volume"));
            }
        }
        if (z) {
            return;
        }
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(11, i2, i3, volumeGroupState.mAudioVolumeGroup.name(), str));
        synchronized (AudioService.this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                int deviceForStream = AudioService.this.getDeviceForStream(volumeGroupState.mPublicStreamType);
                int index = volumeGroupState.getIndex(deviceForStream);
                AudioService.this.getClass();
                if (isMuteAdjust(i2) && volumeGroupState.mIndexMin != 0 && (!volumeGroupState.mHasValidStreamType || !volumeGroupState.isVssMuteBijective(volumeGroupState.mPublicStreamType))) {
                    Log.d("AS.AudioService", "invalid mute on unmutable volume group " + volumeGroupState.mAudioVolumeGroup.name());
                    return;
                }
                if (i2 == -100) {
                    if (index != 0) {
                        volumeGroupState.mute(true);
                    }
                    volumeGroupState.mIsMuted = true;
                } else if (i2 != -1) {
                    if (i2 == 1) {
                        volumeGroupState.setVolumeIndex(Math.min(index + 1, volumeGroupState.mIndexMax), deviceForStream, i3);
                    } else if (i2 == 100) {
                        volumeGroupState.mute(false);
                    } else if (i2 == 101) {
                        volumeGroupState.mute(!volumeGroupState.mIsMuted);
                    }
                } else if (!volumeGroupState.mIsMuted || index == 0) {
                    volumeGroupState.setVolumeIndex(Math.max(index - 1, volumeGroupState.mIndexMin), deviceForStream, i3);
                } else {
                    volumeGroupState.mute(false);
                }
            }
        }
    }

    public final boolean areNavigationRepeatSoundEffectsEnabled() {
        return this.mNavigationRepeatSoundEffectsEnabled;
    }

    public final void broadcastRingerMode(int i, String str) {
        this.mSystemServer.getClass();
        Intent intent = new Intent(str);
        intent.putExtra("android.media.EXTRA_RINGER_MODE", i);
        intent.addFlags(603979776);
        intent.addFlags(268435456);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void broadcastVibrateSetting(int i) {
        this.mSystemServer.getClass();
        if (this.mActivityManagerInternal.isSystemReady()) {
            Intent intent = new Intent("android.media.VIBRATE_SETTING_CHANGED");
            intent.putExtra("android.media.EXTRA_VIBRATE_TYPE", i);
            intent.putExtra("android.media.EXTRA_VIBRATE_SETTING", getVibrateSetting(i));
            sendBroadcastToAll(intent, null);
        }
    }

    public final boolean callerHasPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str) == 0;
    }

    public final boolean canBeSpatialized(AudioAttributes audioAttributes, AudioFormat audioFormat) {
        boolean z;
        Objects.requireNonNull(audioAttributes);
        Objects.requireNonNull(audioFormat);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            int i = spatializerHelper.mState;
            z = false;
            if (i == 0 || i == 1 || i == 3 || i == 4) {
                Log.i("AS.SpatializerHelper", "canBeSpatialized false due to state:" + spatializerHelper.mState);
            } else {
                int usage = audioAttributes.getUsage();
                if (usage == 1 || usage == 14) {
                    ArrayList routingDevices = spatializerHelper.getRoutingDevices(audioAttributes);
                    if (routingDevices.isEmpty()) {
                        SpatializerHelper.logloge("canBeSpatialized got no device for " + audioAttributes);
                    } else {
                        z = spatializerHelper.canBeSpatializedOnDevice(audioAttributes, audioFormat, routingDevices);
                        Log.i("AS.SpatializerHelper", "canBeSpatialized usage:" + audioAttributes.getUsage() + " format:" + audioFormat.toLogFriendlyString() + " returning " + z);
                    }
                } else {
                    Log.i("AS.SpatializerHelper", "canBeSpatialized false due to usage:" + audioAttributes.getUsage());
                }
            }
        }
        return z;
    }

    public final boolean canChangeAccessibilityVolume() {
        synchronized (this.mAccessibilityServiceUidsLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cancelMuteAwaitConnection(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        Log.i("AS.AudioService", "cancelMuteAwaitConnection for device:" + audioDeviceAttributes);
        synchronized (this.mMuteAwaitConnectionLock) {
            try {
                AudioDeviceAttributes audioDeviceAttributes2 = this.mMutingExpectedDevice;
                if (audioDeviceAttributes2 == null) {
                    Log.i("AS.AudioService", "cancelMuteAwaitConnection ignored, no expected device");
                    return;
                }
                if (!retrieveBluetoothAddress.equalTypeAddress(audioDeviceAttributes2)) {
                    Log.e("AS.AudioService", "cancelMuteAwaitConnection ignored, got " + audioDeviceAttributes + "] but expected device is" + this.mMutingExpectedDevice);
                    throw new IllegalStateException("cancelMuteAwaitConnection for wrong device");
                }
                int[] iArr = this.mMutedUsagesAwaitingConnection;
                this.mMutingExpectedDevice = null;
                this.mMutedUsagesAwaitingConnection = null;
                this.mPlaybackMonitor.cancelMuteAwaitConnection("cancelMuteAwaitConnection dev:" + audioDeviceAttributes);
                dispatchMuteAwaitConnection(new AudioService$$ExternalSyntheticLambda12(this, retrieveBluetoothAddress, iArr, 0));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void checkAllAliasStreamVolumes() {
        synchronized (this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                try {
                    int numStreamTypes = AudioSystem.getNumStreamTypes();
                    for (int i = 0; i < numStreamTypes; i++) {
                        VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                        volumeStreamStateArr[i].setAllIndexes(volumeStreamStateArr[mStreamVolumeAlias[i]], "AS.AudioService");
                        VolumeStreamState volumeStreamState = this.mStreamStates[i];
                        if (!volumeStreamState.mIsMuted) {
                            volumeStreamState.applyAllVolumes();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final boolean checkAudioSettingsPermission(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0) {
            return true;
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Audio Settings Permission Denial: ", str, " from pid=");
        m.append(Binder.getCallingPid());
        m.append(", uid=");
        m.append(Binder.getCallingUid());
        Log.w("AS.AudioService", m.toString());
        return false;
    }

    public final void checkModifyPhoneStateOrRoutingPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("com.sec.android.permission.IN_APP_SOUND") != 0) {
            throw new SecurityException("Not allowed to audio routing");
        }
    }

    public final void checkMonitorAudioServerStatePermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            throw new SecurityException("Not allowed to monitor audioserver state");
        }
    }

    public void checkMusicActive(int i, String str) {
        if (this.mSoundDoseHelper.safeDevicesContains(i)) {
            this.mSoundDoseHelper.scheduleMusicActiveCheck();
        }
    }

    public final void checkMuteAffectedStreams() {
        int i;
        int i2 = 0;
        while (true) {
            VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
            if (i2 >= volumeStreamStateArr.length) {
                this.mUserMutableStreams = this.mMuteAffectedStreams & (-66);
                return;
            }
            VolumeStreamState volumeStreamState = volumeStreamStateArr[i2];
            if (volumeStreamState.mIndexMin > 0 && (i = volumeStreamState.mStreamType) != 0 && i != 6) {
                this.mMuteAffectedStreams = (~(1 << i)) & this.mMuteAffectedStreams;
            }
            i2++;
        }
    }

    public final boolean checkNoteAppOp(int i, int i2, String str, String str2) {
        if ("com.android.server.media".equals(str)) {
            return true;
        }
        try {
            return this.mAppOps.noteOp(i, i2, str, str2, (String) null) == 0;
        } catch (Exception e) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "Error noting op:", " on uid:", " for package:");
            m.append(str);
            Log.e("AS.AudioService", m.toString(), e);
            return false;
        }
    }

    public final AudioPolicyProxy checkUpdateForPolicy(IAudioPolicyCallback iAudioPolicyCallback, String str) {
        if (this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " for pid ");
            m.append(Binder.getCallingPid());
            m.append(" / uid ");
            m.append(Binder.getCallingUid());
            m.append(", need MODIFY_AUDIO_ROUTING");
            Slog.w("AS.AudioService", m.toString());
            return null;
        }
        AudioPolicyProxy audioPolicyProxy = (AudioPolicyProxy) this.mAudioPolicies.get(iAudioPolicyCallback.asBinder());
        if (audioPolicyProxy != null) {
            return audioPolicyProxy;
        }
        StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str, " for pid ");
        m2.append(Binder.getCallingPid());
        m2.append(" / uid ");
        m2.append(Binder.getCallingUid());
        m2.append(", unregistered policy");
        Slog.w("AS.AudioService", m2.toString());
        return null;
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
            EventLogger eventLogger = sLifecycleLogger;
            EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ": initStreamVolume succeeded but invalid mix/max levels, will retry"));
            stringEvent.printLog(2, "AS.AudioService");
            eventLogger.enqueue(stringEvent);
            sendMsg(this.mAudioHandler, 34, 1, 0, 0, str, 2000);
        }
        return z;
    }

    public final int clearFadeManagerConfigurationForFocusLoss() {
        int i;
        clearFadeManagerConfigurationForFocusLoss_enforcePermission();
        Preconditions.checkState(Flags.enableFadeManagerConfiguration(), "Fade manager configuration not supported");
        FadeOutManager fadeOutManager = this.mPlaybackMonitor.mFadeOutManager;
        synchronized (fadeOutManager.mLock) {
            FadeConfigurations fadeConfigurations = fadeOutManager.mFadeConfigurations;
            fadeConfigurations.getClass();
            if (Flags.enableFadeManagerConfiguration()) {
                synchronized (fadeConfigurations.mLock) {
                    fadeConfigurations.mUpdatedFadeManagerConfig = null;
                    fadeConfigurations.mActiveFadeManagerConfig = fadeConfigurations.getActiveFadeMgrConfigLocked();
                }
                i = 0;
            } else {
                i = -1;
            }
        }
        return i;
    }

    public final int clearPreferredDevicesForCapturePreset(int i) {
        int clearDevicesRoleForCapturePreset;
        clearPreferredDevicesForCapturePreset_enforcePermission();
        String format = String.format("removePreferredDeviceForCapturePreset source:%d", Integer.valueOf(i));
        EventLogger eventLogger = sDeviceLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(format);
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        audioDeviceInventory.getClass();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            ArrayMap arrayMap = audioDeviceInventory.mAppliedPresetRoles;
            synchronized (arrayMap) {
                try {
                    Pair pair = new Pair(Integer.valueOf(i), 1);
                    if (arrayMap.containsKey(pair)) {
                        audioDeviceInventory.mAudioSystem.invalidateRoutingCache();
                        clearDevicesRoleForCapturePreset = AudioSystem.clearDevicesRoleForCapturePreset(i, 1);
                        if (clearDevicesRoleForCapturePreset == 0) {
                            arrayMap.remove(pair);
                        }
                    } else {
                        clearDevicesRoleForCapturePreset = -2;
                    }
                } finally {
                }
            }
            if (create != null) {
                create.close();
            }
            if (clearDevicesRoleForCapturePreset == 0) {
                audioDeviceInventory.mDeviceBroker.sendIMsgNoDelay(38, 2, i);
            }
            if (clearDevicesRoleForCapturePreset != 0 && clearDevicesRoleForCapturePreset != -2) {
                Log.e("AS.AudioService", String.format("Error %d in %s", Integer.valueOf(clearDevicesRoleForCapturePreset), format));
            }
            return clearDevicesRoleForCapturePreset;
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

    public final int clearPreferredMixerAttributes(AudioAttributes audioAttributes, int i) {
        Objects.requireNonNull(audioAttributes);
        if (!checkAudioSettingsPermission("clearPreferredMixerAttributes()")) {
            return -4;
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String formatSimple = TextUtils.formatSimple("clearPreferredMixerAttributes u/pid:%d/%d attr:%s", new Object[]{Integer.valueOf(callingUid), Integer.valueOf(callingPid), audioAttributes.toString()});
            EventLogger eventLogger = sDeviceLogger;
            EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(formatSimple);
            stringEvent.printLog(0, "AS.AudioService");
            eventLogger.enqueue(stringEvent);
            this.mAudioSystem.getClass();
            int clearPreferredMixerAttributes = AudioSystem.clearPreferredMixerAttributes(audioAttributes, i, callingUid);
            if (clearPreferredMixerAttributes == 0) {
                dispatchPreferredMixerAttributesChanged(audioAttributes, i, null);
            } else {
                Log.e("AS.AudioService", TextUtils.formatSimple("Error %d in %s)", new Object[]{Integer.valueOf(clearPreferredMixerAttributes), formatSimple}));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return clearPreferredMixerAttributes;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
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

    public final void disableSafeMediaVolume(String str) {
        enforceVolumeController("disable the safe media volume");
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        synchronized (soundDoseHelper.mSafeMediaVolumeStateLock) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                soundDoseHelper.setSafeMediaVolumeEnabled(str, false);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                SoundDoseHelper.StreamVolumeCommand streamVolumeCommand = soundDoseHelper.mPendingVolumeCommand;
                if (streamVolumeCommand != null) {
                    if (streamVolumeCommand.mStreamType == 3 && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(streamVolumeCommand.mDevice))) {
                        AudioService audioService = soundDoseHelper.mAudioService;
                        int i = soundDoseHelper.mPendingVolumeCommand.mIndex;
                        if (audioService.mAvrcpAbsVolSupported) {
                            audioService.mDeviceBroker.sendIMsgNoDelay(15, 0, i);
                        }
                    }
                    AudioService audioService2 = soundDoseHelper.mAudioService;
                    SoundDoseHelper.StreamVolumeCommand streamVolumeCommand2 = soundDoseHelper.mPendingVolumeCommand;
                    audioService2.onSetStreamVolume(streamVolumeCommand2.mStreamType, streamVolumeCommand2.mIndex, str, streamVolumeCommand2.mFlags, streamVolumeCommand2.mDevice, true);
                    Intent intent = new Intent("android.media.VOLUME_CHANGED_ACTION");
                    intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", soundDoseHelper.mPendingVolumeCommand.mStreamType);
                    intent.putExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", soundDoseHelper.mPendingVolumeCommand.mIndex / 10);
                    intent.putExtra("android.media.EXTRA_VOLUME_SHOW_UI", (soundDoseHelper.mPendingVolumeCommand.mFlags & 1) != 0);
                    soundDoseHelper.mAudioService.sendBroadcastToAll(intent, null);
                    VolumeController volumeController = soundDoseHelper.mAudioService.mVolumeController;
                    SoundDoseHelper.StreamVolumeCommand streamVolumeCommand3 = soundDoseHelper.mPendingVolumeCommand;
                    volumeController.postVolumeChanged(streamVolumeCommand3.mStreamType, streamVolumeCommand3.mFlags);
                    soundDoseHelper.mPendingVolumeCommand = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dismissVolumePanel() {
        this.mVolumeController.postDismiss();
    }

    public final void dispatchAbsoluteVolumeChanged(int i, AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo, int i2) {
        VolumeInfo m282$$Nest$mgetMatchingVolumeInfoForStream = AbsoluteVolumeDeviceInfo.m282$$Nest$mgetMatchingVolumeInfoForStream(absoluteVolumeDeviceInfo, i);
        if (m282$$Nest$mgetMatchingVolumeInfoForStream != null) {
            try {
                IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher = absoluteVolumeDeviceInfo.mCallback;
                AudioDeviceAttributes audioDeviceAttributes = absoluteVolumeDeviceInfo.mDevice;
                VolumeInfo.Builder builder = new VolumeInfo.Builder(m282$$Nest$mgetMatchingVolumeInfoForStream);
                int minVolumeIndex = m282$$Nest$mgetMatchingVolumeInfoForStream.getMinVolumeIndex();
                int maxVolumeIndex = m282$$Nest$mgetMatchingVolumeInfoForStream.getMaxVolumeIndex();
                if (minVolumeIndex != -100 && maxVolumeIndex != -100) {
                    VolumeStreamState volumeStreamState = this.mStreamStates[i];
                    i2 = rescaleIndex(i2, volumeStreamState.mIndexMin, volumeStreamState.mIndexMax, minVolumeIndex, maxVolumeIndex);
                }
                iAudioDeviceVolumeDispatcher.dispatchDeviceVolumeChanged(audioDeviceAttributes, builder.setVolumeIndex(i2).build());
            } catch (RemoteException unused) {
                Log.w("AS.AudioService", "Couldn't dispatch absolute volume behavior volume change");
            }
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

    public final int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) {
        int dispatchFocusChange;
        if (audioFocusInfo == null) {
            throw new IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (iAudioPolicyCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy callback");
        }
        synchronized (this.mAudioPolicies) {
            try {
                if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                    throw new IllegalStateException("Unregistered AudioPolicy for focus dispatch");
                }
                dispatchFocusChange = this.mMediaFocusControl.dispatchFocusChange(audioFocusInfo, i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return dispatchFocusChange;
    }

    public final int dispatchFocusChangeWithFade(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback, List list, FadeManagerConfiguration fadeManagerConfiguration) {
        int dispatchFocusChangeWithFade;
        dispatchFocusChangeWithFade_enforcePermission();
        Preconditions.checkState(Flags.enableFadeManagerConfiguration(), "Fade manager configuration not supported");
        Objects.requireNonNull(audioFocusInfo, "AudioFocusInfo cannot be null");
        Objects.requireNonNull(iAudioPolicyCallback, "AudioPolicy callback cannot be null");
        Objects.requireNonNull(list, "Other active AudioFocusInfo list cannot be null");
        if (fadeManagerConfiguration != null) {
            List audioAttributesWithVolumeShaperConfigs = fadeManagerConfiguration.getAudioAttributesWithVolumeShaperConfigs();
            for (int i2 = 0; i2 < audioAttributesWithVolumeShaperConfigs.size(); i2++) {
                validateAudioAttributesUsage((AudioAttributes) audioAttributesWithVolumeShaperConfigs.get(i2));
            }
        }
        synchronized (this.mAudioPolicies) {
            try {
                Preconditions.checkState(this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder()), "Unregistered AudioPolicy for focus dispatch with fade");
                if (fadeManagerConfiguration != null) {
                    this.mPlaybackMonitor.setTransientFadeManagerConfiguration(fadeManagerConfiguration);
                }
                dispatchFocusChangeWithFade = this.mMediaFocusControl.dispatchFocusChangeWithFade(audioFocusInfo, i, list);
                if (fadeManagerConfiguration != null) {
                    this.mPlaybackMonitor.clearTransientFadeManagerConfiguration();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return dispatchFocusChangeWithFade;
    }

    public final void dispatchMuteAwaitConnection(BiConsumer biConsumer) {
        int beginBroadcast = this.mMuteAwaitConnectionDispatchers.beginBroadcast();
        ArrayList arrayList = null;
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                biConsumer.accept(this.mMuteAwaitConnectionDispatchers.getBroadcastItem(i), (Boolean) this.mMuteAwaitConnectionDispatchers.getBroadcastCookie(i));
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

    public final void dispatchPreferredMixerAttributesChanged(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("audio_attributes", audioAttributes);
        bundle.putParcelable("audio_mixer_attributes", audioMixerAttributes);
        AudioHandler audioHandler = this.mAudioHandler;
        long uptimeMillis = SystemClock.uptimeMillis() + 0;
        Message obtainMessage = audioHandler.obtainMessage(52, i, 0, null);
        obtainMessage.setData(bundle);
        audioHandler.sendMessageAtTime(obtainMessage, uptimeMillis);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00bc A[Catch: IndexOutOfBoundsException | NumberFormatException -> 0x01b9, IndexOutOfBoundsException | NumberFormatException -> 0x01b9, TryCatch #6 {IndexOutOfBoundsException | NumberFormatException -> 0x01b9, blocks: (B:11:0x0025, B:14:0x0036, B:14:0x0036, B:24:0x006f, B:24:0x006f, B:26:0x0074, B:26:0x0074, B:28:0x0079, B:28:0x0079, B:37:0x009e, B:37:0x009e, B:39:0x00bc, B:39:0x00bc, B:41:0x00cb, B:41:0x00cb, B:43:0x00e4, B:43:0x00e4, B:45:0x0087, B:45:0x0087, B:48:0x008f, B:48:0x008f, B:51:0x0100, B:51:0x0100, B:60:0x0125, B:60:0x0125, B:61:0x012c, B:61:0x012c, B:70:0x0163, B:70:0x0163, B:72:0x0169, B:72:0x0169, B:73:0x0179, B:73:0x0179, B:77:0x0183, B:77:0x0183, B:83:0x0197, B:83:0x0197, B:86:0x0130, B:86:0x0130, B:89:0x013a, B:89:0x013a, B:92:0x0144, B:92:0x0144, B:95:0x014e, B:95:0x014e, B:98:0x0198, B:98:0x0198, B:99:0x01a1, B:99:0x01a1, B:102:0x01a3, B:102:0x01a3, B:107:0x01b8, B:107:0x01b8, B:108:0x010e, B:108:0x010e, B:111:0x0116, B:111:0x0116, B:114:0x003e, B:114:0x003e, B:117:0x0046, B:117:0x0046, B:120:0x0050, B:120:0x0050), top: B:10:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0198 A[Catch: IndexOutOfBoundsException | NumberFormatException -> 0x01b9, IndexOutOfBoundsException | NumberFormatException -> 0x01b9, TryCatch #6 {IndexOutOfBoundsException | NumberFormatException -> 0x01b9, blocks: (B:11:0x0025, B:14:0x0036, B:14:0x0036, B:24:0x006f, B:24:0x006f, B:26:0x0074, B:26:0x0074, B:28:0x0079, B:28:0x0079, B:37:0x009e, B:37:0x009e, B:39:0x00bc, B:39:0x00bc, B:41:0x00cb, B:41:0x00cb, B:43:0x00e4, B:43:0x00e4, B:45:0x0087, B:45:0x0087, B:48:0x008f, B:48:0x008f, B:51:0x0100, B:51:0x0100, B:60:0x0125, B:60:0x0125, B:61:0x012c, B:61:0x012c, B:70:0x0163, B:70:0x0163, B:72:0x0169, B:72:0x0169, B:73:0x0179, B:73:0x0179, B:77:0x0183, B:77:0x0183, B:83:0x0197, B:83:0x0197, B:86:0x0130, B:86:0x0130, B:89:0x013a, B:89:0x013a, B:92:0x0144, B:92:0x0144, B:95:0x014e, B:95:0x014e, B:98:0x0198, B:98:0x0198, B:99:0x01a1, B:99:0x01a1, B:102:0x01a3, B:102:0x01a3, B:107:0x01b8, B:107:0x01b8, B:108:0x010e, B:108:0x010e, B:111:0x0116, B:111:0x0116, B:114:0x003e, B:114:0x003e, B:117:0x0046, B:117:0x0046, B:120:0x0050, B:120:0x0050), top: B:10:0x0025 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dump(java.io.FileDescriptor r17, java.io.PrintWriter r18, java.lang.String[] r19) {
        /*
            Method dump skipped, instructions count: 2460
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.dump(java.io.FileDescriptor, java.io.PrintWriter, java.lang.String[]):void");
    }

    public final void dumpAssistantServicesUids(PrintWriter printWriter) {
        synchronized (this.mSettingsLock) {
            try {
                if (this.mAssistantUids.size() > 0) {
                    printWriter.println("  Assistant service UIDs:");
                    Iterator it = this.mAssistantUids.iterator();
                    while (it.hasNext()) {
                        printWriter.println("  - " + ((Integer) it.next()).intValue());
                    }
                } else {
                    printWriter.println("  No Assistant service Uids.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpAudioPolicies(PrintWriter printWriter) {
        printWriter.println("\nAudio policies:");
        synchronized (this.mAudioPolicies) {
            try {
                Iterator it = this.mAudioPolicies.values().iterator();
                while (it.hasNext()) {
                    printWriter.println(((AudioPolicyProxy) it.next()).toLogFriendlyString());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpSupportedSystemUsage(PrintWriter printWriter) {
        printWriter.println("Supported System Usages:");
        synchronized (this.mSupportedSystemUsagesLock) {
            int i = 0;
            while (true) {
                try {
                    int[] iArr = this.mSupportedSystemUsages;
                    if (i < iArr.length) {
                        printWriter.printf("\t%s\n", AudioAttributes.usageToString(iArr[i]));
                        i++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void enforceModifyAudioRoutingPermission() {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING permission");
        }
    }

    public final boolean enforceQueryAudioStateForTest(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.QUERY_AUDIO_STATE") == 0) {
            return true;
        }
        Log.e("AS.AudioService", XmlUtils$$ExternalSyntheticOutline0.m("Doesn't have QUERY_AUDIO_STATE permission for ", str, " test API"), new Exception());
        return false;
    }

    public final void enforceVolumeController(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE", "Only SystemUI can ".concat(str));
    }

    public final void ensureValidStreamType(int i) {
        if (i < 0 || i >= this.mStreamStates.length) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad stream type "));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.os.IBinder$DeathRecipient, com.android.server.audio.MediaFocusControl$2] */
    public final boolean enterAudioFocusFreezeForTest(IBinder iBinder, int[] iArr) {
        enterAudioFocusFreezeForTest_enforcePermission();
        Objects.requireNonNull(iArr);
        Objects.requireNonNull(iBinder);
        final MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        Log.i("MediaFocusControl", "enterAudioFocusFreezeForTest UIDs exempt:" + Arrays.toString(iArr));
        synchronized (MediaFocusControl.mAudioFocusLock) {
            if (mediaFocusControl.mFocusFreezerForTest != null) {
                Log.e("MediaFocusControl", "Error enterAudioFocusFreezeForTest: focus already frozen");
                return false;
            }
            try {
                ?? anonymousClass2 = new IBinder.DeathRecipient() { // from class: com.android.server.audio.MediaFocusControl.2
                    public AnonymousClass2() {
                    }

                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        Log.i("MediaFocusControl", "Audio focus freezer died, exiting focus freeze for test");
                        MediaFocusControl mediaFocusControl2 = MediaFocusControl.this;
                        mediaFocusControl2.getClass();
                        synchronized (MediaFocusControl.mAudioFocusLock) {
                            mediaFocusControl2.mFocusFreezerDeathHandler = null;
                            mediaFocusControl2.mFocusFreezeExemptUids = null;
                            mediaFocusControl2.mFocusFreezerForTest = null;
                        }
                    }
                };
                mediaFocusControl.mFocusFreezerDeathHandler = anonymousClass2;
                iBinder.linkToDeath(anonymousClass2, 0);
                mediaFocusControl.mFocusFreezerForTest = iBinder;
                mediaFocusControl.mFocusFreezeExemptUids = (int[]) iArr.clone();
                return true;
            } catch (RemoteException unused) {
                mediaFocusControl.mFocusFreezerForTest = null;
                mediaFocusControl.mFocusFreezeExemptUids = null;
                return false;
            }
        }
    }

    public final boolean exitAudioFocusFreezeForTest(IBinder iBinder) {
        exitAudioFocusFreezeForTest_enforcePermission();
        Objects.requireNonNull(iBinder);
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        Object obj = MediaFocusControl.mAudioFocusLock;
        synchronized (obj) {
            try {
                IBinder iBinder2 = mediaFocusControl.mFocusFreezerForTest;
                if (iBinder2 != iBinder) {
                    Log.e("MediaFocusControl", "Error exitAudioFocusFreezeForTest: ".concat(iBinder2 == null ? "call to exit while not frozen" : "call to exit not coming from freeze owner"));
                    return false;
                }
                iBinder2.unlinkToDeath(mediaFocusControl.mFocusFreezerDeathHandler, 0);
                synchronized (obj) {
                    mediaFocusControl.mFocusFreezerDeathHandler = null;
                    mediaFocusControl.mFocusFreezeExemptUids = null;
                    mediaFocusControl.mFocusFreezerForTest = null;
                }
                return true;
            } finally {
            }
        }
    }

    public final void forceComputeCsdOnAllDevices(boolean z) {
        forceComputeCsdOnAllDevices_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        if (soundDoseHelper.mEnableCsd.get() || soundDoseHelper.updateCsdForTestApi()) {
            ISoundDose iSoundDose = (ISoundDose) soundDoseHelper.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                iSoundDose.forceComputeCsdOnAllDevices(z);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while forcing CSD computation on all devices", e);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0087, code lost:
    
        r4.mICallback.unlinkToDeath(r4, 0);
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00bd A[Catch: all -> 0x0065, TryCatch #1 {all -> 0x0065, blocks: (B:13:0x001f, B:14:0x0025, B:16:0x002b, B:21:0x00bd, B:22:0x00cc, B:27:0x003b, B:29:0x0049, B:31:0x004d, B:32:0x0067, B:33:0x006d, B:34:0x0073, B:36:0x0079, B:39:0x0087, B:40:0x0096, B:42:0x009f, B:44:0x00a4, B:47:0x008e), top: B:11:0x001d, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void forceRemoteSubmixFullVolume(boolean r7, android.os.IBinder r8) {
        /*
            r6 = this;
            if (r8 != 0) goto L3
            return
        L3:
            android.content.Context r0 = r6.mContext
            java.lang.String r1 = "android.permission.CAPTURE_AUDIO_OUTPUT"
            int r0 = r0.checkCallingOrSelfPermission(r1)
            if (r0 == 0) goto L15
            java.lang.String r6 = "AS.AudioService"
            java.lang.String r7 = "Trying to call forceRemoteSubmixFullVolume() without CAPTURE_AUDIO_OUTPUT"
            android.util.Log.w(r6, r7)
            return
        L15:
            java.util.ArrayList r0 = r6.mRmtSbmxFullVolDeathHandlers
            monitor-enter(r0)
            r1 = 0
            r2 = 1
            r3 = 32768(0x8000, float:4.5918E-41)
            if (r7 == 0) goto L6d
            java.util.ArrayList r7 = r6.mRmtSbmxFullVolDeathHandlers     // Catch: java.lang.Throwable -> L65
            java.util.Iterator r7 = r7.iterator()     // Catch: java.lang.Throwable -> L65
        L25:
            boolean r4 = r7.hasNext()     // Catch: java.lang.Throwable -> L65
            if (r4 == 0) goto L3b
            java.lang.Object r4 = r7.next()     // Catch: java.lang.Throwable -> L65
            com.android.server.audio.AudioService$RmtSbmxFullVolDeathHandler r4 = (com.android.server.audio.AudioService.RmtSbmxFullVolDeathHandler) r4     // Catch: java.lang.Throwable -> L65
            android.os.IBinder r4 = r4.mICallback     // Catch: java.lang.Throwable -> L65
            boolean r4 = r4.equals(r8)     // Catch: java.lang.Throwable -> L65
            if (r4 == 0) goto L25
            goto Lbb
        L3b:
            java.util.ArrayList r7 = r6.mRmtSbmxFullVolDeathHandlers     // Catch: java.lang.Throwable -> L65
            com.android.server.audio.AudioService$RmtSbmxFullVolDeathHandler r4 = new com.android.server.audio.AudioService$RmtSbmxFullVolDeathHandler     // Catch: java.lang.Throwable -> L65
            r4.<init>(r8)     // Catch: java.lang.Throwable -> L65
            r7.add(r4)     // Catch: java.lang.Throwable -> L65
            boolean r7 = com.samsung.android.server.audio.ScreenSharingHelper.sIsWifiDisplayConnected     // Catch: java.lang.Throwable -> L65
            if (r7 != 0) goto L67
            int r7 = r6.mRmtSbmxFullVolRefCount     // Catch: java.lang.Throwable -> L65
            if (r7 != 0) goto L67
            java.util.Set r7 = r6.mFullVolumeDevices     // Catch: java.lang.Throwable -> L65
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L65
            java.util.HashSet r7 = (java.util.HashSet) r7     // Catch: java.lang.Throwable -> L65
            r7.add(r8)     // Catch: java.lang.Throwable -> L65
            java.util.Set r7 = r6.mFixedVolumeDevices     // Catch: java.lang.Throwable -> L65
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L65
            java.util.HashSet r7 = (java.util.HashSet) r7     // Catch: java.lang.Throwable -> L65
            r7.add(r8)     // Catch: java.lang.Throwable -> L65
            r1 = r2
            goto L67
        L65:
            r6 = move-exception
            goto Lce
        L67:
            int r7 = r6.mRmtSbmxFullVolRefCount     // Catch: java.lang.Throwable -> L65
            int r7 = r7 + r2
            r6.mRmtSbmxFullVolRefCount = r7     // Catch: java.lang.Throwable -> L65
            goto Lbb
        L6d:
            java.util.ArrayList r7 = r6.mRmtSbmxFullVolDeathHandlers     // Catch: java.lang.Throwable -> L65
            java.util.Iterator r7 = r7.iterator()     // Catch: java.lang.Throwable -> L65
        L73:
            boolean r4 = r7.hasNext()     // Catch: java.lang.Throwable -> L65
            if (r4 == 0) goto Lbb
            java.lang.Object r4 = r7.next()     // Catch: java.lang.Throwable -> L65
            com.android.server.audio.AudioService$RmtSbmxFullVolDeathHandler r4 = (com.android.server.audio.AudioService.RmtSbmxFullVolDeathHandler) r4     // Catch: java.lang.Throwable -> L65
            android.os.IBinder r5 = r4.mICallback     // Catch: java.lang.Throwable -> L65
            boolean r5 = r5.equals(r8)     // Catch: java.lang.Throwable -> L65
            if (r5 == 0) goto L73
            android.os.IBinder r7 = r4.mICallback     // Catch: java.lang.Throwable -> L65 java.util.NoSuchElementException -> L8d
            r7.unlinkToDeath(r4, r1)     // Catch: java.lang.Throwable -> L65 java.util.NoSuchElementException -> L8d
            goto L96
        L8d:
            r7 = move-exception
            java.lang.String r8 = "AS.AudioService"
            java.lang.String r5 = "error unlinking to death"
            android.util.Log.e(r8, r5, r7)     // Catch: java.lang.Throwable -> L65
        L96:
            java.util.ArrayList r7 = r6.mRmtSbmxFullVolDeathHandlers     // Catch: java.lang.Throwable -> L65
            r7.remove(r4)     // Catch: java.lang.Throwable -> L65
            int r7 = r6.mRmtSbmxFullVolRefCount     // Catch: java.lang.Throwable -> L65
            if (r7 <= 0) goto Lbb
            int r7 = r7 - r2
            r6.mRmtSbmxFullVolRefCount = r7     // Catch: java.lang.Throwable -> L65
            if (r7 != 0) goto Lbb
            java.util.Set r7 = r6.mFullVolumeDevices     // Catch: java.lang.Throwable -> L65
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L65
            java.util.HashSet r7 = (java.util.HashSet) r7     // Catch: java.lang.Throwable -> L65
            r7.remove(r8)     // Catch: java.lang.Throwable -> L65
            java.util.Set r7 = r6.mFixedVolumeDevices     // Catch: java.lang.Throwable -> L65
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L65
            java.util.HashSet r7 = (java.util.HashSet) r7     // Catch: java.lang.Throwable -> L65
            r7.remove(r8)     // Catch: java.lang.Throwable -> L65
            r1 = r2
        Lbb:
            if (r1 == 0) goto Lcc
            com.android.server.audio.AudioService$VolumeStreamState[] r7 = r6.mStreamStates     // Catch: java.lang.Throwable -> L65
            r8 = 3
            r7 = r7[r8]     // Catch: java.lang.Throwable -> L65
            r7.checkFixedVolumeDevices()     // Catch: java.lang.Throwable -> L65
            com.android.server.audio.AudioService$VolumeStreamState[] r6 = r6.mStreamStates     // Catch: java.lang.Throwable -> L65
            r6 = r6[r8]     // Catch: java.lang.Throwable -> L65
            r6.applyAllVolumes()     // Catch: java.lang.Throwable -> L65
        Lcc:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L65
            return
        Lce:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L65
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.forceRemoteSubmixFullVolume(boolean, android.os.IBinder):void");
    }

    public final void forceUseFrameworkMel(boolean z) {
        forceUseFrameworkMel_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        if (soundDoseHelper.mEnableCsd.get() || soundDoseHelper.updateCsdForTestApi()) {
            ISoundDose iSoundDose = (ISoundDose) soundDoseHelper.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                iSoundDose.forceUseFrameworkMel(z);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while forcing the internal MEL computation", e);
            }
        }
    }

    public final void forceVolumeControlStream(int i, IBinder iBinder) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            return;
        }
        Log.d("AS.AudioService", String.format("forceVolumeControlStream(%d)", Integer.valueOf(i)));
        synchronized (this.mForceControlStreamLock) {
            try {
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
                    AnonymousClass9 anonymousClass9 = this.mForceControlStreamClient;
                    if (anonymousClass9 != null) {
                        IBinder iBinder2 = (IBinder) anonymousClass9.val$controller;
                        if (iBinder2 != null) {
                            iBinder2.unlinkToDeath(anonymousClass9, 0);
                            anonymousClass9.val$controller = null;
                        }
                        this.mForceControlStreamClient = null;
                    }
                    this.mUserSelectedVolumeControlStream = false;
                } else {
                    AnonymousClass9 anonymousClass92 = this.mForceControlStreamClient;
                    if (anonymousClass92 == null) {
                        this.mForceControlStreamClient = new AnonymousClass9(iBinder);
                    } else {
                        IBinder iBinder3 = (IBinder) anonymousClass92.val$controller;
                        if (iBinder3 == iBinder) {
                            Log.d("AS.AudioService", "forceVolumeControlStream cb:" + iBinder + " is already linked.");
                        } else {
                            if (iBinder3 != null) {
                                iBinder3.unlinkToDeath(anonymousClass92, 0);
                                anonymousClass92.val$controller = null;
                            }
                            this.mForceControlStreamClient = new AnonymousClass9(iBinder);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i) {
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

    public final Set getAbsoluteVolumeDevicesWithBehavior(final int i) {
        return (Set) ((ArrayMap) this.mAbsoluteVolumeDeviceInfoMap).entrySet().stream().filter(new Predicate() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i2 = i;
                int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
                return ((AudioService.AbsoluteVolumeDeviceInfo) ((Map.Entry) obj).getValue()).mDeviceVolumeBehavior == i2;
            }
        }).map(new UiModeManagerService$Stub$$ExternalSyntheticLambda3()).collect(Collectors.toSet());
    }

    public final int[] getActiveAssistantServiceUids() {
        int[] iArr;
        getActiveAssistantServiceUids_enforcePermission();
        synchronized (this.mSettingsLock) {
            iArr = (int[]) this.mActiveAssistantServiceUids.clone();
        }
        return iArr;
    }

    public final List getActivePlaybackConfigurations() {
        return this.mPlaybackMonitor.getActivePlaybackConfigurations(this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public final List getActiveRecordingConfigurations() {
        return this.mRecordMonitor.getActiveRecordingConfigurations(Binder.getCallingUid() == 1000 || this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
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
            if (!isInCommunication()) {
                this.mAudioSystem.getClass();
                if (!AudioSystem.isStreamActive(0, 0)) {
                    if (Rune.SEC_AUDIO_REMOTE_MIC && this.mRemoteMic && this.mDeviceBroker.isDeviceOnForCommunication(7)) {
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
            }
            return this.mDeviceBroker.isDeviceActiveForCommunication(7) ? 6 : 0;
        }
        if (isInCommunication) {
            if (this.mDeviceBroker.isDeviceActiveForCommunication(7)) {
                Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_BLUETOOTH_SCO");
                return 6;
            }
            Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_VOICE_CALL");
            return 0;
        }
        if (Rune.SEC_AUDIO_REMOTE_MIC && this.mRemoteMic && this.mDeviceBroker.isDeviceOnForCommunication(7)) {
            Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_BLUETOOTH_SCO for remote mic");
            return 6;
        }
        AudioSystemAdapter audioSystemAdapter = this.mAudioSystem;
        int i2 = sStreamOverrideDelayMs;
        audioSystemAdapter.getClass();
        if (AudioSystem.isStreamActive(5, i2)) {
            Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_NOTIFICATION");
            return 5;
        }
        AudioSystemAdapter audioSystemAdapter2 = this.mAudioSystem;
        int i3 = sStreamOverrideDelayMs;
        audioSystemAdapter2.getClass();
        if (AudioSystem.isStreamActive(2, i3)) {
            Log.v("AS.AudioService", "getActiveStreamType: Forcing STREAM_RING");
            return 2;
        }
        if (i == Integer.MIN_VALUE) {
            return AudioStreamUtils.getActiveStreamTypeInternal(this.mPlatformType, this.mAdjustMediaVolumeOnly);
        }
        Log.v("AS.AudioService", "getActiveStreamType: Returning suggested type " + i);
        return i;
    }

    public final int getActualHeadTrackingMode() {
        int i;
        getActualHeadTrackingMode_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            i = spatializerHelper.mActualHeadTrackingMode;
        }
        return i;
    }

    public final long getAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        try {
            return Long.parseLong(AudioSystem.getParameters("additional_output_device_delay=" + retrieveBluetoothAddress.getInternalType() + "," + retrieveBluetoothAddress.getAddress()).substring(31));
        } catch (NullPointerException unused) {
            return 0L;
        }
    }

    public final int getAllowedCapturePolicy() {
        int callingUid = Binder.getCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((Integer) this.mPlaybackMonitor.mAllowedCapturePolicies.getOrDefault(Integer.valueOf(callingUid), 1)).intValue();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getAppDevice(int i) {
        return this.mMultiSoundManager.getAppDevice(i);
    }

    public final int getAppVolume(int i) {
        int i2;
        MultiSoundManager multiSoundManager = this.mMultiSoundManager;
        synchronized (multiSoundManager.mMultiSoundLock) {
            try {
                MultiSoundManager.MultiSoundItem multiSoundItem = (MultiSoundManager.MultiSoundItem) multiSoundManager.mPinAppInfoList.get(Integer.valueOf(i));
                i2 = multiSoundItem != null ? multiSoundItem.mRatio : 100;
            } finally {
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, i2, "getAppVolume, uid:", ", volume=", "AS.AudioService");
        return i2;
    }

    public final int[] getAssistantServicesUids() {
        int[] array;
        getAssistantServicesUids_enforcePermission();
        synchronized (this.mSettingsLock) {
            array = this.mAssistantUids.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray();
        }
        return array;
    }

    public final SetModeDeathHandler getAudioModeOwnerHandler() {
        Iterator it = this.mSetModeDeathHandlers.iterator();
        SetModeDeathHandler setModeDeathHandler = null;
        SetModeDeathHandler setModeDeathHandler2 = null;
        while (it.hasNext()) {
            SetModeDeathHandler setModeDeathHandler3 = (SetModeDeathHandler) it.next();
            if (setModeDeathHandler3.isActive()) {
                if (setModeDeathHandler3.mIsPrivileged) {
                    if (setModeDeathHandler == null || setModeDeathHandler3.mUpdateTime > setModeDeathHandler.mUpdateTime) {
                        setModeDeathHandler = setModeDeathHandler3;
                    }
                } else if (setModeDeathHandler2 == null || setModeDeathHandler3.mUpdateTime > setModeDeathHandler2.mUpdateTime) {
                    setModeDeathHandler2 = setModeDeathHandler3;
                }
            }
        }
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            this.mExternalVoipModeOwner = null;
            if (setModeDeathHandler != null && setModeDeathHandler2 != null && setModeDeathHandler.mMode == 3 && setModeDeathHandler2.mMode == 3 && setModeDeathHandler2.mUpdateTime > setModeDeathHandler.mUpdateTime) {
                this.mExternalVoipModeOwner = setModeDeathHandler2;
            }
        }
        return setModeDeathHandler != null ? setModeDeathHandler : setModeDeathHandler2;
    }

    public final List getAudioProductStrategies() {
        getAudioProductStrategies_enforcePermission();
        return AudioProductStrategy.getAudioProductStrategies();
    }

    /* JADX WARN: Code restructure failed: missing block: B:323:0x0557, code lost:
    
        if (r9.mDeviceBroker.checkDeviceConnected(8) != false) goto L273;
     */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0568  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getAudioServiceConfig(java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 1399
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.getAudioServiceConfig(java.lang.String):java.lang.String");
    }

    public final List getAudioVolumeGroups() {
        getAudioVolumeGroups_enforcePermission();
        return this.mAudioVolumeGroupHelper.getAudioVolumeGroups();
    }

    public final int[] getAvailableCommunicationDeviceIds() {
        return AudioDeviceBroker.getAvailableCommunicationDevices().stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(0)).toArray();
    }

    public final int getBluetoothAudioDeviceCategory(String str) {
        int i;
        getBluetoothAudioDeviceCategory_enforcePermission();
        boolean z = false;
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return 0;
        }
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        AdiDeviceState findBtDeviceStateForAddress = audioDeviceInventory.findBtDeviceStateForAddress(str, 536870912);
        if (findBtDeviceStateForAddress != null) {
            audioDeviceInventory.addOrUpdateAudioDeviceCategoryInInventory(findBtDeviceStateForAddress, true);
            i = findBtDeviceStateForAddress.getAudioDeviceCategory();
            z = true;
        } else {
            i = 0;
        }
        AdiDeviceState findBtDeviceStateForAddress2 = audioDeviceInventory.findBtDeviceStateForAddress(str, 128);
        if (findBtDeviceStateForAddress2 == null) {
            return i;
        }
        audioDeviceInventory.addOrUpdateAudioDeviceCategoryInInventory(findBtDeviceStateForAddress2, true);
        int audioDeviceCategory = findBtDeviceStateForAddress2.getAudioDeviceCategory();
        if (z && audioDeviceCategory != i) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Found different audio device category for A2DP and BLE profiles with address ", str, "AS.AudioDeviceInventory");
        }
        return audioDeviceCategory;
    }

    public final int getBluetoothAudioDeviceCategory_legacy(String str, boolean z) {
        getBluetoothAudioDeviceCategory_legacy_enforcePermission();
        if (android.media.audio.Flags.automaticBtDeviceType()) {
            return 0;
        }
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        Objects.requireNonNull(str);
        AdiDeviceState findBtDeviceStateForAddress = audioDeviceBroker.mDeviceInventory.findBtDeviceStateForAddress(str, z ? 536870912 : 128);
        if (findBtDeviceStateForAddress == null) {
            return 0;
        }
        return findBtDeviceStateForAddress.getAudioDeviceCategory();
    }

    public int getBluetoothContextualVolumeStream() {
        return this.mMode.get() == 0 ? 3 : 0;
    }

    public final int getCommunicationDevice() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AudioDeviceInfo communicationDevice = this.mDeviceBroker.getCommunicationDevice();
            return communicationDevice != null ? communicationDevice.getId() : 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final float getCsd() {
        getCsd_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        if (!soundDoseHelper.mEnableCsd.get() && !soundDoseHelper.updateCsdForTestApi()) {
            return -1.0f;
        }
        ISoundDose iSoundDose = (ISoundDose) soundDoseHelper.mSoundDose.get();
        if (iSoundDose == null) {
            Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
            return -1.0f;
        }
        try {
            return iSoundDose.getCsd();
        } catch (RemoteException e) {
            Log.e("AS.SoundDoseHelper", "Exception while getting the CSD value", e);
            return -1.0f;
        }
    }

    public final int getCurrentAudioFocus() {
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        synchronized (MediaFocusControl.mAudioFocusLock) {
            try {
                if (mediaFocusControl.mFocusStack.empty()) {
                    return 0;
                }
                return ((FocusRequester) mediaFocusControl.mFocusStack.peek()).mFocusGainRequest;
            } finally {
            }
        }
    }

    public final String getCurrentAudioFocusPackageName() {
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        synchronized (MediaFocusControl.mAudioFocusLock) {
            try {
                if (!mediaFocusControl.mFocusStack.empty()) {
                    return ((FocusRequester) mediaFocusControl.mFocusStack.peek()).mPackageName;
                }
                for (int i = 0; i < mediaFocusControl.mMultiFocusStack.size(); i++) {
                    Stack stack = (Stack) mediaFocusControl.mMultiFocusStack.valueAt(i);
                    if (!stack.isEmpty()) {
                        return ((FocusRequester) stack.peek()).mPackageName;
                    }
                }
                return null;
            } finally {
            }
        }
    }

    public final VolumeInfo getDefaultVolumeInfo() {
        if (sDefaultVolumeInfo == null) {
            sDefaultVolumeInfo = new VolumeInfo.Builder(3).setMinVolumeIndex(getStreamMinVolume(3)).setMaxVolumeIndex(getStreamMaxVolume(3)).build();
        }
        return sDefaultVolumeInfo;
    }

    public final int getDesiredHeadTrackingMode() {
        int i;
        getDesiredHeadTrackingMode_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            i = spatializerHelper.mDesiredHeadTrackingMode;
        }
        return i;
    }

    public int getDeviceForStream(int i) {
        Set<Integer> deviceSetForStream = getDeviceSetForStream(i);
        if (i == 3 && this.mMode.get() == 0) {
            for (Integer num : deviceSetForStream) {
                num.intValue();
                if (AudioSystem.DEVICE_OUT_ALL_SCO_SET.contains(num)) {
                    return 128;
                }
            }
        }
        if (deviceSetForStream.isEmpty()) {
            return 0;
        }
        if (deviceSetForStream.size() == 1) {
            return ((Integer) deviceSetForStream.iterator().next()).intValue();
        }
        int i2 = 4096;
        if (!deviceSetForStream.contains(4096)) {
            i2 = 2;
            if (!deviceSetForStream.contains(2)) {
                i2 = 4194304;
                if (!deviceSetForStream.contains(4194304)) {
                    i2 = 262144;
                    if (!deviceSetForStream.contains(262144)) {
                        i2 = 262145;
                        if (!deviceSetForStream.contains(262145)) {
                            i2 = 2097152;
                            if (!deviceSetForStream.contains(2097152)) {
                                i2 = 524288;
                                if (!deviceSetForStream.contains(524288)) {
                                    for (Integer num2 : deviceSetForStream) {
                                        int intValue = num2.intValue();
                                        if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(num2)) {
                                            return intValue;
                                        }
                                    }
                                    Log.w("AS.AudioService", "selectOneAudioDevice returning DEVICE_NONE from invalid device combination " + AudioSystem.deviceSetToString(deviceSetForStream));
                                    return 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        return i2;
    }

    public final int getDeviceMaskForStream(int i) {
        ensureValidStreamType(i);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return AudioSystem.getDeviceMaskFromSet(AudioSystem.generateAudioDeviceTypesSet(getDevicesForAttributesInt(AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i), true)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final Set getDeviceSetForStream(int i) {
        Set observeDevicesForStream_syncVSS;
        ensureValidStreamType(i);
        synchronized (VolumeStreamState.class) {
            observeDevicesForStream_syncVSS = this.mStreamStates[i].observeDevicesForStream_syncVSS(true);
        }
        return observeDevicesForStream_syncVSS;
    }

    public final VolumeInfo getDeviceVolume(VolumeInfo volumeInfo, AudioDeviceAttributes audioDeviceAttributes, String str) {
        VolumeInfo build;
        getDeviceVolume_enforcePermission();
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
            try {
                builder.setVolumeIndex(isFixedVolumeDevice(audioDeviceAttributes.getInternalType()) ? (this.mStreamStates[streamType].mIndexMax + 5) / 10 : (this.mStreamStates[streamType].getIndex(audioDeviceAttributes.getInternalType()) + 5) / 10);
                if (this.mStreamStates[streamType].mIsMuted) {
                    builder.setMuted(true);
                }
                build = builder.build();
            } catch (Throwable th) {
                throw th;
            }
        }
        return build;
    }

    public final int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) {
        getDeviceVolumeBehavior_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        return getDeviceVolumeBehaviorInt(retrieveBluetoothAddress(audioDeviceAttributes));
    }

    public final int getDeviceVolumeBehaviorInt(AudioDeviceAttributes audioDeviceAttributes) {
        int internalType = audioDeviceAttributes.getInternalType();
        if (((HashSet) this.mFullVolumeDevices).contains(Integer.valueOf(internalType))) {
            return 1;
        }
        if (((HashSet) this.mFixedVolumeDevices).contains(Integer.valueOf(internalType))) {
            return 2;
        }
        if (((HashSet) this.mAbsVolumeMultiModeCaseDevices).contains(Integer.valueOf(internalType))) {
            return 4;
        }
        if (((ArrayMap) this.mAbsoluteVolumeDeviceInfoMap).containsKey(Integer.valueOf(internalType))) {
            return ((AbsoluteVolumeDeviceInfo) ((ArrayMap) this.mAbsoluteVolumeDeviceInfoMap).get(Integer.valueOf(internalType))).mDeviceVolumeBehavior;
        }
        return (isA2dpAbsoluteVolumeDevice(internalType) || AudioSystem.isLeAudioDeviceType(internalType)) ? 3 : 0;
    }

    public final List getDevicesForAttributes(AudioAttributes audioAttributes) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.QUERY_AUDIO_STATE") != 0) {
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING or QUERY_AUDIO_STATE permissions");
        }
        List devicesForAttributesInt = getDevicesForAttributesInt(audioAttributes, false);
        if (!isBluetoothPrividged()) {
            devicesForAttributesInt = anonymizeAudioDeviceAttributesListUnchecked(devicesForAttributesInt);
        }
        return new ArrayList(devicesForAttributesInt);
    }

    public final ArrayList getDevicesForAttributesInt(AudioAttributes audioAttributes, boolean z) {
        Objects.requireNonNull(audioAttributes);
        return this.mAudioSystem.getDevicesForAttributes(audioAttributes, z);
    }

    public final List getDevicesForAttributesUnprotected(AudioAttributes audioAttributes) {
        List devicesForAttributesInt = getDevicesForAttributesInt(audioAttributes, false);
        if (!isBluetoothPrividged()) {
            devicesForAttributesInt = anonymizeAudioDeviceAttributesListUnchecked(devicesForAttributesInt);
        }
        return new ArrayList(devicesForAttributesInt);
    }

    public final int getEarProtectLimit() {
        if (this.mIsVolumeEffectOn) {
            return 7;
        }
        return Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3 ? 8 : 10;
    }

    public final HashSet getEnabledFormats() {
        HashSet hashSet = new HashSet();
        SettingsAdapter settingsAdapter = this.mSettings;
        ContentResolver contentResolver = this.mContentResolver;
        settingsAdapter.getClass();
        String string = Settings.Global.getString(contentResolver, "encoded_surround_output_enabled_formats");
        if (string != null) {
            try {
                Arrays.stream(TextUtils.split(string, ",")).mapToInt(new AudioService$$ExternalSyntheticLambda1(1)).forEach(new AudioService$$ExternalSyntheticLambda11(0, hashSet));
            } catch (NumberFormatException e) {
                Log.w("AS.AudioService", "ENCODED_SURROUND_OUTPUT_ENABLED_FORMATS misformatted.", e);
            }
        }
        return hashSet;
    }

    public final int getEncodedSurroundMode(int i) {
        int i2;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                SettingsAdapter settingsAdapter = this.mSettings;
                ContentResolver contentResolver = this.mContentResolver;
                settingsAdapter.getClass();
                i2 = 0;
                int i3 = Settings.Global.getInt(contentResolver, "encoded_surround_output", 0);
                if (i > 31 || i3 <= 3) {
                    if (i3 != 0) {
                        i2 = 1;
                        if (i3 != 1) {
                            i2 = 2;
                            if (i3 != 2) {
                                if (i3 == 3) {
                                    i2 = 3;
                                }
                            }
                        }
                    }
                }
                i2 = -1;
            }
            return i2;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getExcludedRingtoneTitles(int i) {
        OmcRingtoneManager omcRingtoneManager = this.mExt.mOmcRingtoneManager;
        return i == 2 ? omcRingtoneManager.mExcludeNotifications : omcRingtoneManager.mExcludeRingtones;
    }

    public final FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() {
        FadeManagerConfiguration fadeManagerConfiguration;
        getFadeManagerConfigurationForFocusLoss_enforcePermission();
        Preconditions.checkState(Flags.enableFadeManagerConfiguration(), "Fade manager configuration not supported");
        FadeConfigurations fadeConfigurations = this.mPlaybackMonitor.mFadeOutManager.mFadeConfigurations;
        fadeConfigurations.getClass();
        if (!Flags.enableFadeManagerConfiguration()) {
            return null;
        }
        synchronized (fadeConfigurations.mLock) {
            fadeManagerConfiguration = fadeConfigurations.mActiveFadeManagerConfig;
        }
        return fadeManagerConfiguration;
    }

    public final long getFadeOutDurationOnFocusLossMillis(AudioAttributes audioAttributes) {
        if (!enforceQueryAudioStateForTest("fade out duration")) {
            return 0L;
        }
        this.mMediaFocusControl.getClass();
        return 0L;
    }

    public final int getFineVolume(int i, int i2) {
        int index;
        ensureValidStreamType(i);
        if (i2 == 0) {
            if (isMultiSoundOn()) {
                i2 = this.mMultiSoundManager.getAppDevice(Binder.getCallingUid());
            }
            if (i2 == 0) {
                i2 = getDeviceForStream(i);
            }
        }
        synchronized (VolumeStreamState.class) {
            try {
                index = this.mStreamStates[i].getIndex(i2);
                if (this.mStreamStates[i].mIsMuted) {
                    index = 0;
                }
                if (index != 0 && mStreamVolumeAlias[i] == 3 && isFixedVolumeDevice(i2)) {
                    index = this.mStreamStates[i].mIndexMax;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return index;
    }

    public final float[] getFloatVolumeTable() {
        return DualA2dpVolumeManager.FINE_VOLUME_TABLE;
    }

    public final List getFocusDuckedUidsForTest() {
        ArrayList arrayList;
        getFocusDuckedUidsForTest_enforcePermission();
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        synchronized (playbackActivityMonitor.mPlayerLock) {
            arrayList = new ArrayList(playbackActivityMonitor.mDuckingManager.mDuckers.keySet());
        }
        Log.i("AS.PlaybackActivityMon", "current ducked UIDs: " + arrayList);
        return arrayList;
    }

    public final long getFocusFadeOutDurationForTest() {
        getFocusFadeOutDurationForTest_enforcePermission();
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        return mediaFocusControl.getFadeOutDurationMillis(new AudioAttributes.Builder().setUsage(1).build());
    }

    public final int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) {
        return MediaFocusControl.getFocusRampTimeMs(audioAttributes);
    }

    public final List getFocusStack() {
        ArrayList arrayList;
        getFocusStack_enforcePermission();
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        synchronized (MediaFocusControl.mAudioFocusLock) {
            try {
                arrayList = new ArrayList(mediaFocusControl.mFocusStack.size());
                Iterator it = mediaFocusControl.mFocusStack.iterator();
                while (it.hasNext()) {
                    arrayList.add(((FocusRequester) it.next()).toAudioFocusInfo());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final long getFocusUnmuteDelayAfterFadeOutForTest() {
        getFocusUnmuteDelayAfterFadeOutForTest_enforcePermission();
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        return mediaFocusControl.getFadeInDelayForOffendersMillis(new AudioAttributes.Builder().setUsage(1).build());
    }

    public final AudioHalVersionInfo getHalVersion() {
        for (AudioHalVersionInfo audioHalVersionInfo : AudioHalVersionInfo.VERSIONS) {
            try {
                String str = "android.hardware.audio@" + (audioHalVersionInfo.getMajorVersion() + "." + audioHalVersionInfo.getMinorVersion()) + "::IDevicesFactory";
                if (ServiceManager.checkService("android.hardware.audio.core.IModule/default") != null) {
                    return audioHalVersionInfo;
                }
                HwBinder.getService(str, "default");
                return audioHalVersionInfo;
            } catch (RemoteException e) {
                Log.e("AS.AudioService", "Remote exception when getting hardware audio service:", e);
            } catch (NoSuchElementException unused) {
            }
        }
        return null;
    }

    public final List getIndependentStreamTypes() {
        getIndependentStreamTypes_enforcePermission();
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

    public final int getLastAudibleStreamVolume(int i) {
        getLastAudibleStreamVolume_enforcePermission();
        ensureValidStreamType(i);
        return getIndexDividedBy10(this.mStreamStates[i].getIndex(getDeviceForStream(i)), i);
    }

    public final int getLastAudibleVolumeForVolumeGroup(int i) {
        int index;
        getLastAudibleVolumeForVolumeGroup_enforcePermission();
        synchronized (VolumeStreamState.class) {
            try {
                SparseArray sparseArray = sVolumeGroupStates;
                if (sparseArray.indexOfKey(i) < 0) {
                    Log.e("AS.AudioService", ": no volume group found for id " + i);
                    return 0;
                }
                VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray.get(i);
                volumeGroupState.getClass();
                synchronized (VolumeStreamState.class) {
                    index = volumeGroupState.getIndex(AudioService.this.getDeviceForStream(volumeGroupState.mPublicStreamType));
                }
                return index;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final PersistableBundle getLoudnessParams(LoudnessCodecInfo loudnessCodecInfo) {
        return this.mLoudnessCodecHelper.getLoudnessParams(loudnessCodecInfo);
    }

    public final long getMaxAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        try {
            return Long.parseLong(AudioSystem.getParameters("max_additional_output_device_delay=" + retrieveBluetoothAddress.getInternalType() + "," + retrieveBluetoothAddress.getAddress()).substring(35));
        } catch (NullPointerException unused) {
            return 0L;
        }
    }

    public final int[] getMediaVolumeSteps() {
        return this.mVolumeSteps;
    }

    public final int getMicModeType() {
        return this.mMicModeManager.mMicModeType.getType();
    }

    public final int getMode() {
        synchronized (this.mDeviceBroker.mSetModeLock) {
            try {
                SetModeDeathHandler audioModeOwnerHandler = getAudioModeOwnerHandler();
                if (audioModeOwnerHandler == null) {
                    return 0;
                }
                return audioModeOwnerHandler.mMode;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getModeInternal() {
        return this.mMode.get();
    }

    public MusicFxHelper getMusicFxHelper() {
        return this.mMusicFxHelper;
    }

    public final int getMuteInterval() {
        return Settings.Global.getInt(this.mContentResolver, "mode_ringer_time", 60);
    }

    public final AudioDeviceAttributes getMutingExpectedDevice() {
        AudioDeviceAttributes audioDeviceAttributes;
        getMutingExpectedDevice_enforcePermission();
        synchronized (this.mMuteAwaitConnectionLock) {
            audioDeviceAttributes = this.mMutingExpectedDevice;
            if (!isBluetoothPrividged()) {
                audioDeviceAttributes = anonymizeAudioDeviceAttributesUnchecked(audioDeviceAttributes);
            }
        }
        return audioDeviceAttributes;
    }

    public final int getNewRingerMode(int i, int i2, int i3) {
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
            return 1;
        }
        return this.mVolumePolicy.volumeDownToEnterSilent ? 0 : 2;
    }

    public final int getNextFineMediaVolume(int i, int i2) {
        int index = this.mStreamStates[3].getIndex(i);
        if (index < 0 || index > this.mStreamStates[3].mIndexMax) {
            return index;
        }
        if (this.mVolumeSteps != null) {
            return i2 > 0 ? this.mVolumeMap[index].raiseStep : this.mVolumeMap[index].lowerStep;
        }
        int i3 = (i2 * this.mMediaVolumeStepIndex) + index;
        if (i3 < 0) {
            return 0;
        }
        return i3;
    }

    public final List getNonDefaultDevicesForStrategy(int i) {
        getNonDefaultDevicesForStrategy_enforcePermission();
        ArrayList arrayList = new ArrayList();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            int devicesForRoleAndStrategy = AudioSystem.getDevicesForRoleAndStrategy(i, 2, arrayList);
            if (create != null) {
                create.close();
            }
            if (devicesForRoleAndStrategy == 0) {
                return isBluetoothPrividged() ? arrayList : anonymizeAudioDeviceAttributesListUnchecked(arrayList);
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

    public final float getOutputRs2UpperBound() {
        getOutputRs2UpperBound_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        if (!soundDoseHelper.mEnableCsd.get()) {
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        ISoundDose iSoundDose = (ISoundDose) soundDoseHelper.mSoundDose.get();
        if (iSoundDose == null) {
            Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
        try {
            return iSoundDose.getOutputRs2UpperBound();
        } catch (RemoteException e) {
            Log.e("AS.SoundDoseHelper", "Exception while getting the RS2 exposure value", e);
            return FullScreenMagnificationGestureHandler.MAX_SCALE;
        }
    }

    public final String[] getPackageName(int i) {
        String[] strArr;
        if (i == 1000) {
            return SYSTEM_PACKAGE;
        }
        try {
            strArr = this.mPackageManager.getPackagesForUid(i);
        } catch (IllegalArgumentException unused) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "getPackageName:Invalid uid ", "AS.AudioService");
            strArr = null;
        }
        return strArr != null ? strArr : EMPTY_PACKAGE;
    }

    public final String getPinAppInfo(int i) {
        String pinAppInfo = this.mMultiSoundManager.getPinAppInfo(i);
        Log.d("AS.AudioService", "getPinAppInfo, device=" + i + ", pinappinfo=" + pinAppInfo);
        return pinAppInfo;
    }

    public final int getPinDevice() {
        if (this.mMultiSoundManager.isRemoteSubmixAppExist()) {
            return 32768;
        }
        return getPinDeviceInternal();
    }

    public final int getPinDeviceInternal() {
        return this.mMultiSoundManager.getPinDevice(false);
    }

    public final List getPreferredDevicesForCapturePreset(int i) {
        getPreferredDevicesForCapturePreset_enforcePermission();
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int devicesForRoleAndCapturePreset = AudioSystem.getDevicesForRoleAndCapturePreset(i, 1, arrayList);
            if (devicesForRoleAndCapturePreset == 0) {
                return isBluetoothPrividged() ? arrayList : anonymizeAudioDeviceAttributesListUnchecked(arrayList);
            }
            Log.e("AS.AudioService", String.format("Error %d in getPreferredDeviceForCapturePreset(%d)", Integer.valueOf(devicesForRoleAndCapturePreset), Integer.valueOf(i)));
            return new ArrayList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getPreferredDevicesForStrategy(int i) {
        getPreferredDevicesForStrategy_enforcePermission();
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int devicesForRoleAndStrategy = AudioSystem.getDevicesForRoleAndStrategy(i, 1, arrayList);
            if (devicesForRoleAndStrategy == 0) {
                return isBluetoothPrividged() ? arrayList : anonymizeAudioDeviceAttributesListUnchecked(arrayList);
            }
            Log.e("AS.AudioService", String.format("Error %d in getPreferredDeviceForStrategy(%d)", Integer.valueOf(devicesForRoleAndStrategy), Integer.valueOf(i)));
            return new ArrayList();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int getPrevRingerMode() {
        return this.mPrevRingerMode;
    }

    public final int getRadioOutputPath() {
        return this.mForcedUseForFMRadio == 1 ? 2 : 3;
    }

    public final List getRegisteredPolicyMixes() {
        List unmodifiableList;
        if (!Flags.audioMixTestApi()) {
            return Collections.emptyList();
        }
        synchronized (this.mAudioPolicies) {
            try {
                this.mAudioSystem.getClass();
                if (Flags.audioMixTestApi()) {
                    ArrayList arrayList = new ArrayList();
                    int registeredPolicyMixes = AudioSystem.getRegisteredPolicyMixes(arrayList);
                    if (registeredPolicyMixes != 0) {
                        throw new IllegalStateException(VibrationParam$1$$ExternalSyntheticOutline0.m(registeredPolicyMixes, "Cannot fetch registered policy mixes. Result: "));
                    }
                    unmodifiableList = Collections.unmodifiableList(arrayList);
                } else {
                    unmodifiableList = Collections.emptyList();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return unmodifiableList;
    }

    public final int getRemainingMuteIntervalMs() {
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

    public final List getReportedSurroundFormats() {
        ArrayList arrayList = new ArrayList();
        int reportedSurroundFormats = AudioSystem.getReportedSurroundFormats(arrayList);
        if (reportedSurroundFormats == 0) {
            return arrayList;
        }
        Log.e("AS.AudioService", "getReportedSurroundFormats failed:" + reportedSurroundFormats);
        return new ArrayList();
    }

    public final int getRingerModeExternal() {
        int i;
        synchronized (this.mSettingsLock) {
            i = this.mRingerModeExternal;
        }
        return i;
    }

    public final int getRingerModeInternal() {
        int i;
        synchronized (this.mSettingsLock) {
            i = this.mRingerMode;
        }
        return i;
    }

    public final IRingtonePlayer getRingtonePlayer() {
        return this.mRingtonePlayer;
    }

    public final String[] getSelectedAppList() {
        this.mPackageListHelper.getClass();
        AppCategorizer appCategorizer = PackageListHelper.sCategorizer;
        appCategorizer.getClass();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(appCategorizer.appList.values());
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public SettingsAdapter getSettings() {
        return this.mSettings;
    }

    public final List getSpatializerCompatibleAudioDevices() {
        ArrayList arrayList;
        getSpatializerCompatibleAudioDevices_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            arrayList = new ArrayList();
            Iterator it = ((ArrayList) spatializerHelper.mDeviceBroker.getImmutableDeviceInventory()).iterator();
            while (it.hasNext()) {
                AdiDeviceState adiDeviceState = (AdiDeviceState) it.next();
                if (adiDeviceState.isSAEnabled() && spatializerHelper.isSADevice(adiDeviceState)) {
                    arrayList.add(adiDeviceState.getAudioDeviceAttributes());
                }
            }
        }
        return arrayList;
    }

    public final int getSpatializerImmersiveAudioLevel() {
        int i;
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            i = spatializerHelper.mCapableSpatLevel;
        }
        return i;
    }

    public final int getSpatializerOutput() {
        int output;
        getSpatializerOutput_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            int i = spatializerHelper.mState;
            if (i == 0 || i == 1) {
                throw new IllegalStateException("Can't get output without a spatializer");
            }
            if ((i == 3 || i == 4 || i == 5 || i == 6) && spatializerHelper.mSpat == null) {
                throw new IllegalStateException("null Spatializer for getOutput");
            }
            try {
                output = spatializerHelper.mSpat.getOutput();
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Error in getOutput", e);
                return 0;
            }
        }
        return output;
    }

    public final void getSpatializerParameter(int i, byte[] bArr) {
        getSpatializerParameter_enforcePermission();
        Objects.requireNonNull(bArr);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            int i2 = spatializerHelper.mState;
            if (i2 == 0 || i2 == 1) {
                throw new IllegalStateException("Can't get parameter key:" + i + " without a spatializer");
            }
            if ((i2 == 3 || i2 == 4 || i2 == 5 || i2 == 6) && spatializerHelper.mSpat == null) {
                Log.e("AS.SpatializerHelper", "getParameter(" + i + "): null spatializer in state: " + spatializerHelper.mState);
                return;
            }
            try {
                spatializerHelper.mSpat.getParameter(i, bArr);
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Error in getParameter for key:" + i, e);
            }
        }
    }

    public final int getStreamMaxVolume(int i) {
        ensureValidStreamType(i);
        return (this.mStreamStates[i].mIndexMax + 5) / 10;
    }

    public final int getStreamMinVolume(int i) {
        ensureValidStreamType(i);
        boolean z = Binder.getCallingUid() == 1000 || this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0 || this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0;
        VolumeStreamState volumeStreamState = this.mStreamStates[i];
        return ((z ? volumeStreamState.mIndexMin : volumeStreamState.mIndexMinNoPerm) + 5) / 10;
    }

    public final int getStreamTypeAlias(int i) {
        getStreamTypeAlias_enforcePermission();
        ensureValidStreamType(i);
        return mStreamVolumeAlias[i];
    }

    public final int getStreamVolume(int i) {
        int i2;
        int indexDividedBy10;
        ensureValidStreamType(i);
        int i3 = 0;
        if (isMultiSoundOn() && i == 3) {
            i2 = this.mMultiSoundManager.getAppDevice(Binder.getCallingUid());
        } else {
            i2 = 0;
        }
        if (i2 == 0) {
            i2 = getDeviceForStream(i);
        }
        synchronized (VolumeStreamState.class) {
            try {
                int index = this.mStreamStates[i].getIndex(i2);
                if (!this.mStreamStates[i].mIsMuted) {
                    i3 = index;
                }
                if (i3 != 0 && mStreamVolumeAlias[i] == 3 && isFixedVolumeDevice(i2)) {
                    i3 = this.mStreamStates[i].mIndexMax;
                }
                indexDividedBy10 = getIndexDividedBy10(i3, i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return indexDividedBy10;
    }

    public final int getStreamVolume(int i, int i2) {
        int i3;
        synchronized (VolumeStreamState.class) {
            try {
                int index = this.mStreamStates[i].getIndex(i2);
                if (this.mStreamStates[i].mIsMuted) {
                    index = 0;
                }
                if (index != 0 && mStreamVolumeAlias[i] == 3 && isFixedVolumeDevice(i2)) {
                    index = this.mStreamStates[i].mIndexMax;
                }
                i3 = (index + 5) / 10;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i3;
    }

    public final int getStreamVolumeForDevice(int i, int i2) {
        return getStreamVolume(i, i2);
    }

    public final int[] getSupportedHeadTrackingModes() {
        int[] iArr;
        getSupportedHeadTrackingModes_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            iArr = spatializerHelper.mSupportedHeadTrackingModes;
        }
        return iArr;
    }

    public final int[] getSupportedSystemUsages() {
        int[] copyOf;
        getSupportedSystemUsages_enforcePermission();
        synchronized (this.mSupportedSystemUsagesLock) {
            int[] iArr = this.mSupportedSystemUsages;
            copyOf = Arrays.copyOf(iArr, iArr.length);
        }
        return copyOf;
    }

    public final Map getSurroundFormats() {
        HashMap hashMap = new HashMap();
        int surroundFormats = AudioSystem.getSurroundFormats(hashMap);
        if (surroundFormats == 0) {
            return hashMap;
        }
        Log.e("AS.AudioService", "getSurroundFormats failed:" + surroundFormats);
        return new HashMap();
    }

    public final int getUiSoundsStreamType() {
        return this.mUseVolumeGroupAliases ? this.STREAM_VOLUME_ALIAS_VOICE[2] : mStreamVolumeAlias[2];
    }

    public final int getUidForDevice(int i) {
        Log.d("AS.AudioService", "getUidForDevice, " + Integer.toHexString(i));
        Stack stackForDevice = this.mMediaFocusControl.mMultiFocusStack.getStackForDevice(i);
        int i2 = !stackForDevice.isEmpty() ? ((FocusRequester) stackForDevice.peek()).mCallingUid : -1;
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "getUidForDevice, uid:", "MediaFocusControl");
        return i2;
    }

    public final int getVibrateSetting(int i) {
        if (this.mHasVibrator) {
            return (this.mVibrateSetting >> (i * 2)) & 3;
        }
        return 0;
    }

    public final IVolumeController getVolumeController() {
        enforceVolumeController("get the volume controller");
        Log.d("AS.AudioService", "Volume controller: " + this.mVolumeController);
        return this.mVolumeController.mController;
    }

    public final int getVolumeGroupMaxVolumeIndex(int i) {
        int i2;
        getVolumeGroupMaxVolumeIndex_enforcePermission();
        synchronized (VolumeStreamState.class) {
            try {
                SparseArray sparseArray = sVolumeGroupStates;
                if (sparseArray.indexOfKey(i) < 0) {
                    throw new IllegalArgumentException("No volume group for id " + i);
                }
                i2 = ((VolumeGroupState) sparseArray.get(i)).mIndexMax;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    public final int getVolumeGroupMinVolumeIndex(int i) {
        int i2;
        getVolumeGroupMinVolumeIndex_enforcePermission();
        synchronized (VolumeStreamState.class) {
            try {
                SparseArray sparseArray = sVolumeGroupStates;
                if (sparseArray.indexOfKey(i) < 0) {
                    throw new IllegalArgumentException("No volume group for id " + i);
                }
                i2 = ((VolumeGroupState) sparseArray.get(i)).mIndexMin;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    public final int getVolumeGroupVolumeIndex(int i) {
        int index;
        getVolumeGroupVolumeIndex_enforcePermission();
        synchronized (VolumeStreamState.class) {
            SparseArray sparseArray = sVolumeGroupStates;
            if (sparseArray.indexOfKey(i) < 0) {
                throw new IllegalArgumentException("No volume group for id " + i);
            }
            VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray.get(i);
            if (volumeGroupState.mIsMuted) {
                index = 0;
            } else {
                synchronized (VolumeStreamState.class) {
                    index = volumeGroupState.getIndex(AudioService.this.getDeviceForStream(volumeGroupState.mPublicStreamType));
                }
            }
        }
        return index;
    }

    public final int getVolumeLimitValue() {
        int i;
        if (!this.mIsVolumeEffectOn || this.mVolumeLimitValue - 9 < 0 || i >= 7) {
            return Math.min(this.mVolumeLimitValue, 14);
        }
        StringBuilder sb = new StringBuilder("volume limit for effect change to ");
        int[] iArr = VOLUME_LIMIT_INDEX_EFFECT_ON;
        GestureWakeup$$ExternalSyntheticOutline0.m(sb, iArr[i], "AS.AudioService");
        return iArr[i];
    }

    public final int getVssVolumeForDevice(int i, int i2) {
        return this.mStreamStates[i].getIndex(i2);
    }

    public final void handleBluetoothActiveDeviceChanged(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) {
        handleBluetoothActiveDeviceChanged_enforcePermission();
        if (bluetoothProfileConnectionInfo == null) {
            throw new IllegalArgumentException("Illegal null BluetoothProfileConnectionInfo for device " + bluetoothDevice2 + " -> " + bluetoothDevice);
        }
        int profile = bluetoothProfileConnectionInfo.getProfile();
        if (profile != 2 && profile != 11 && profile != 22 && profile != 26 && profile != 21 && (!this.mDeviceBroker.mScoManagedByAudio || profile != 1)) {
            throw new IllegalArgumentException("Illegal BluetoothProfile profile for device " + bluetoothDevice2 + " -> " + bluetoothDevice + ". Got: " + profile);
        }
        EventLogger eventLogger = sDeviceLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("BluetoothActiveDeviceChanged for " + BluetoothProfile.getProfileName(profile) + ", device update " + bluetoothDevice2 + " -> " + bluetoothDevice);
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        AudioDeviceBroker.BtDeviceChangedData btDeviceChangedData = new AudioDeviceBroker.BtDeviceChangedData(bluetoothDevice, bluetoothDevice2, bluetoothProfileConnectionInfo, "AudioService");
        if (profile == 2 && bluetoothDevice2 != null && bluetoothDevice2.equals(bluetoothDevice)) {
            sendMsg(this.mAudioHandler, 38, 2, 0, 0, btDeviceChangedData, 10);
        } else {
            sendMsg(this.mAudioHandler, 38, 2, 0, 0, btDeviceChangedData, 0);
        }
        if (bluetoothDevice != null && bluetoothDevice2 == null && this.mMode.get() == 1) {
            this.mSensorThread.stopSensor();
        }
    }

    public final void handleSetRingerMode(int i, String str, boolean z) {
        String str2;
        Log.i("AS.AudioService", "RingerMode=" + i + ", caller=" + str);
        if ((str.contains("com.samsung.accessibility") || str.contains("com.samsung.android.app.routines") || str.contains(Constants.SYSTEMUI_PACKAGE_NAME) || str.contains("com.sec.android.emergencymode.service")) ? true : (i == 2 && (str.contains("checkForRingerModeChange") || str.contains("onSetStreamVolume"))) ? false : !z) {
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
        EventLogger eventLogger = sRingerModeLogger;
        AudioEvents$MicrophoneEvent audioEvents$MicrophoneEvent = new AudioEvents$MicrophoneEvent();
        audioEvents$MicrophoneEvent.mRequesterPid = i;
        audioEvents$MicrophoneEvent.mPackage = str;
        audioEvents$MicrophoneEvent.mIsEnableMute = z;
        eventLogger.enqueue(audioEvents$MicrophoneEvent);
        GoodCatchManager goodCatchManager = this.mGoodCatchManager;
        if (goodCatchManager != null) {
            if (TextUtils.equals("AudioService", goodCatchManager.mModule) ? goodCatchManager.mSoundFunc[0] : false) {
                if (str.startsWith("AS.AudioService.onSetStreamVolume(")) {
                    try {
                        str2 = str.substring(str.indexOf(40) + 1, str.indexOf(41));
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    this.mGoodCatchManager.mSemGoodCatchManager.update(GoodCatchManager.SOUND_FUNC[0], str2, i, "", this.mContext.getPackageName());
                }
                str2 = str;
                this.mGoodCatchManager.mSemGoodCatchManager.update(GoodCatchManager.SOUND_FUNC[0], str2, i, "", this.mContext.getPackageName());
            }
        }
    }

    public final void handleVolumeKey(KeyEvent keyEvent, boolean z, String str, String str2) {
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

    public boolean hasAudioFocusUsers() {
        boolean z;
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        synchronized (MediaFocusControl.mAudioFocusLock) {
            z = !mediaFocusControl.mFocusStack.empty();
        }
        return z;
    }

    public final boolean hasHapticChannels(Uri uri) {
        return AudioManager.hasHapticChannelsImpl(this.mContext, uri);
    }

    public final boolean hasHeadTracker(AudioDeviceAttributes audioDeviceAttributes) {
        boolean z;
        hasHeadTracker_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        Objects.requireNonNull(audioDeviceAttributes);
        synchronized (spatializerHelper) {
            z = false;
            if (spatializerHelper.mIsHeadTrackingSupported) {
                AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = spatializerHelper.findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
                if (findSACompatibleDeviceStateForAudioDeviceAttributes != null && findSACompatibleDeviceStateForAudioDeviceAttributes.hasHeadTracker()) {
                    z = true;
                }
            } else {
                Log.v("AS.SpatializerHelper", "no headtracking support, hasHeadTracker always false for " + audioDeviceAttributes);
            }
        }
        return z;
    }

    public boolean hasMediaDynamicPolicy() {
        synchronized (this.mAudioPolicies) {
            try {
                if (this.mAudioPolicies.isEmpty()) {
                    return false;
                }
                Iterator it = this.mAudioPolicies.values().iterator();
                while (it.hasNext()) {
                    if (((AudioPolicyProxy) it.next()).hasMixAffectingUsage()) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean hasRegisteredDynamicPolicy() {
        boolean z;
        synchronized (this.mAudioPolicies) {
            z = !this.mAudioPolicies.isEmpty();
        }
        return z;
    }

    public final boolean isA2dpAbsoluteVolumeDevice(int i) {
        return this.mAvrcpAbsVolSupported && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i));
    }

    public final boolean isAbsoluteVolumeDevice(int i) {
        return this.mAbsoluteVolumeDeviceInfoMap.containsKey(Integer.valueOf(i));
    }

    public final boolean isAlreadyInDB(String str) {
        PackageListHelper packageListHelper = this.mPackageListHelper;
        for (int i = 0; i < packageListHelper.mRestrictedPackageList.size(); i++) {
            if (TextUtils.equals(str, (CharSequence) packageListHelper.mRestrictedPackageList.get(i))) {
                return true;
            }
        }
        return PackageListHelper.sCategorizer.appList.containsValue(str);
    }

    public final boolean isAndroidNPlus(String str) {
        try {
            return this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, UserHandle.getUserId(Binder.getCallingUid())).targetSdkVersion >= 24;
        } catch (PackageManager.NameNotFoundException unused) {
            return true;
        }
    }

    public final boolean isAppMute(int i) {
        boolean booleanValue;
        MultiSoundManager multiSoundManager = this.mMultiSoundManager;
        synchronized (multiSoundManager.mMultiSoundLock) {
            booleanValue = ((Boolean) Optional.ofNullable((MultiSoundManager.MultiSoundItem) multiSoundManager.mPinAppInfoList.get(Integer.valueOf(i))).map(new MultiSoundManager$$ExternalSyntheticLambda0()).orElse(Boolean.FALSE)).booleanValue();
        }
        Log.d("AS.AudioService", "isAppMute, uid:" + i + ", mute=" + booleanValue);
        return booleanValue;
    }

    public final boolean isAudioServerRunning() {
        checkMonitorAudioServerStatePermission();
        return AudioSystem.checkAudioFlinger() == 0;
    }

    public final boolean isBluetoothA2dpOn() {
        return this.mDeviceBroker.mBluetoothA2dpEnabled.get();
    }

    public final boolean isBluetoothAudioDeviceCategoryFixed(String str) {
        isBluetoothAudioDeviceCategoryFixed_enforcePermission();
        boolean z = false;
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return false;
        }
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        AdiDeviceState findBtDeviceStateForAddress = audioDeviceInventory.findBtDeviceStateForAddress(str, 536870912);
        if (findBtDeviceStateForAddress != null) {
            synchronized (findBtDeviceStateForAddress) {
                if (android.media.audio.Flags.automaticBtDeviceType()) {
                    findBtDeviceStateForAddress.updateAudioDeviceCategory();
                    z = findBtDeviceStateForAddress.mAutoBtCategorySet;
                }
            }
        } else {
            AdiDeviceState findBtDeviceStateForAddress2 = audioDeviceInventory.findBtDeviceStateForAddress(str, 128);
            if (findBtDeviceStateForAddress2 != null) {
                synchronized (findBtDeviceStateForAddress2) {
                    if (android.media.audio.Flags.automaticBtDeviceType()) {
                        findBtDeviceStateForAddress2.updateAudioDeviceCategory();
                        z = findBtDeviceStateForAddress2.mAutoBtCategorySet;
                    }
                }
            }
        }
        return z;
    }

    public final boolean isBluetoothPrividged() {
        return this.mContext.checkCallingOrSelfPermission("android.permission.BLUETOOTH_CONNECT") == 0 || Binder.getCallingUid() == 1000;
    }

    public final boolean isBluetoothScoOn() {
        return this.mBtScoOnByApp || this.mDeviceBroker.isDeviceOnForCommunication(7);
    }

    public final boolean isBluetoothVariableLatencyEnabled() {
        isBluetoothVariableLatencyEnabled_enforcePermission();
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

    public final boolean isCallScreeningModeSupported() {
        return this.mIsCallScreeningModeSupported;
    }

    public final boolean isCameraSoundForced() {
        boolean z;
        synchronized (this.mSettingsLock) {
            z = this.mCameraSoundForced;
        }
        return z;
    }

    public final boolean isConnectedAndroidAuto() {
        RCPManagerService$$ExternalSyntheticOutline0.m("AS.AudioService", new StringBuilder("[Android Auto] isConnectedAndroidAuto = "), this.mConnectedAndroidAuto);
        return this.mConnectedAndroidAuto;
    }

    public final boolean isCsdAsAFeatureAvailable() {
        boolean z;
        isCsdAsAFeatureAvailable_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        synchronized (soundDoseHelper.mCsdAsAFeatureLock) {
            z = soundDoseHelper.mIsCsdAsAFeatureAvailable;
        }
        return z;
    }

    public final boolean isCsdAsAFeatureEnabled() {
        boolean z;
        isCsdAsAFeatureEnabled_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        synchronized (soundDoseHelper.mCsdAsAFeatureLock) {
            z = soundDoseHelper.mIsCsdAsAFeatureEnabled;
        }
        return z;
    }

    public final boolean isCsdEnabled() {
        isCsdEnabled_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        if (!soundDoseHelper.mEnableCsd.get()) {
            return false;
        }
        ISoundDose iSoundDose = (ISoundDose) soundDoseHelper.mSoundDose.get();
        if (iSoundDose == null) {
            Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
            return false;
        }
        try {
            return iSoundDose.isSoundDoseHalSupported();
        } catch (RemoteException e) {
            Log.e("AS.SoundDoseHelper", "Exception while querying the csd enabled status", e);
            return false;
        }
    }

    public final boolean isFixedVolumeDevice(int i) {
        if (i == 32768 && this.mRecordMonitor.mLegacyRemoteSubmixActive.get()) {
            return false;
        }
        return this.mFixedVolumeDevices.contains(Integer.valueOf(i));
    }

    public final boolean isForceSpeakerOn() {
        return this.mForceSpeaker == 1;
    }

    public final boolean isFullVolumeDevice(int i) {
        if (i == 32768 && this.mRecordMonitor.mLegacyRemoteSubmixActive.get()) {
            return false;
        }
        return this.mFullVolumeDevices.contains(Integer.valueOf(i));
    }

    public final boolean isHdmiSystemAudioSupported() {
        return this.mHdmiSystemAudioSupported;
    }

    public final boolean isHeadTrackerAvailable() {
        boolean z;
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            z = spatializerHelper.mGlobalHeadTrackerAvailable;
        }
        return z;
    }

    public final boolean isHeadTrackerEnabled(AudioDeviceAttributes audioDeviceAttributes) {
        boolean z;
        isHeadTrackerEnabled_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        Objects.requireNonNull(audioDeviceAttributes);
        synchronized (spatializerHelper) {
            z = false;
            if (spatializerHelper.mIsHeadTrackingSupported) {
                AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = spatializerHelper.findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
                if (findSACompatibleDeviceStateForAudioDeviceAttributes != null && findSACompatibleDeviceStateForAudioDeviceAttributes.hasHeadTracker() && findSACompatibleDeviceStateForAudioDeviceAttributes.isHeadTrackerEnabled()) {
                    z = true;
                }
            } else {
                Log.v("AS.SpatializerHelper", "no headtracking support, isHeadTrackerEnabled always false for " + audioDeviceAttributes);
            }
        }
        return z;
    }

    public final boolean isHomeSoundEffectEnabled() {
        return this.mHomeSoundEffectEnabled;
    }

    public final boolean isHotwordStreamSupported(boolean z) {
        isHotwordStreamSupported_enforcePermission();
        try {
            return this.mAudioPolicy.isHotwordStreamSupported(z);
        } catch (IllegalStateException e) {
            Log.e("AS.AudioService", "Suppressing exception calling into AudioPolicy", e);
            return false;
        }
    }

    public final boolean isIgnoreDucking() {
        RCPManagerService$$ExternalSyntheticOutline0.m("AS.AudioService", new StringBuilder("[Android Auto] isIgnoreDucking = "), this.mIgnoreDucking);
        return this.mIgnoreDucking;
    }

    public final boolean isInAllowedList(String str) {
        PackageListHelper packageListHelper = this.mPackageListHelper;
        for (int i = 0; i < packageListHelper.mAllowedPackageList.size(); i++) {
            if (TextUtils.equals(str, (CharSequence) packageListHelper.mAllowedPackageList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean isInCommunication() {
        if (this.mExt.mIsPttCallVolumeEnabled) {
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

    public final boolean isLeBroadcastWithoutLeDevice() {
        BluetoothLeAudio bluetoothLeAudio;
        if (!this.mIsLeBroadCasting) {
            return false;
        }
        BtHelper btHelper = this.mDeviceBroker.mBtHelper;
        synchronized (btHelper) {
            bluetoothLeAudio = btHelper.mLeAudio;
        }
        return bluetoothLeAudio != null && bluetoothLeAudio.getConnectedDevices().size() == 0;
    }

    public final boolean isMasterMute() {
        return this.mMasterMute.get();
    }

    public final boolean isMicrophoneMuted() {
        return this.mMicMuteFromSystemCached && (!this.mMicMuteFromPrivacyToggle || this.mMicMuteFromApi || this.mMicMuteFromRestrictions || this.mMicMuteFromSwitch);
    }

    public final boolean isMultiSoundOn() {
        MultiSoundManager multiSoundManager = this.mMultiSoundManager;
        if (multiSoundManager == null) {
            return false;
        }
        return multiSoundManager.mEnabled;
    }

    public final boolean isMultiSoundOnInternal() {
        if (this.mMultiSoundManager == null || isInCommunication()) {
            return false;
        }
        MultiSoundManager multiSoundManager = this.mMultiSoundManager;
        return multiSoundManager.mEnabled || (multiSoundManager.isRemoteSubmixAppExist() && !this.mMediaSessionServiceInternal.isAudioMirroring());
    }

    public final boolean isMusicActive(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return z ? AudioSystem.isStreamActiveRemotely(3, 0) : AudioSystem.isStreamActive(3, 0);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isPlaybackActiveForUid(int i) {
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        synchronized (playbackActivityMonitor.mPlayerLock) {
            try {
                for (AudioPlaybackConfiguration audioPlaybackConfiguration : playbackActivityMonitor.mPlayers.values()) {
                    if (audioPlaybackConfiguration.isActive() && audioPlaybackConfiguration.getClientUid() == i) {
                        return true;
                    }
                }
                return false;
            } finally {
            }
        }
    }

    public final boolean isPstnCallAudioInterceptable() {
        isPstnCallAudioInterceptable_enforcePermission();
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

    public final boolean isSafeMediaVolumeStateActive() {
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        soundDoseHelper.getClass();
        return Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME && soundDoseHelper.mSafeMediaVolumeStateForBlueTooth == 3;
    }

    public final boolean isSpatializerAvailable() {
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            int i = spatializerHelper.mState;
            return (i == 0 || i == 1 || i == 3 || i == 4) ? false : true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x002f, code lost:
    
        if (r4.getAudioDeviceCategory() != 3) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSpatializerAvailableForDevice(android.media.AudioDeviceAttributes r4) {
        /*
            r3 = this;
            r3.isSpatializerAvailableForDevice_enforcePermission()
            com.android.server.audio.SpatializerHelper r3 = r3.mSpatializerHelper
            java.util.Objects.requireNonNull(r4)
            monitor-enter(r3)
            int r0 = r4.getRole()     // Catch: java.lang.Throwable -> L35
            r1 = 2
            r2 = 0
            if (r0 == r1) goto L13
            monitor-exit(r3)
            goto L34
        L13:
            com.android.server.audio.AdiDeviceState r4 = r3.findSACompatibleDeviceStateForAudioDeviceAttributes(r4)     // Catch: java.lang.Throwable -> L35
            if (r4 != 0) goto L1a
            goto L33
        L1a:
            int r0 = r4.getInternalDeviceType()     // Catch: java.lang.Throwable -> L35
            boolean r0 = android.media.AudioSystem.isBluetoothDevice(r0)     // Catch: java.lang.Throwable -> L35
            if (r0 == 0) goto L32
            int r0 = r4.getAudioDeviceCategory()     // Catch: java.lang.Throwable -> L35
            if (r0 == 0) goto L32
            int r4 = r4.getAudioDeviceCategory()     // Catch: java.lang.Throwable -> L35
            r0 = 3
            if (r4 == r0) goto L32
            goto L33
        L32:
            r2 = 1
        L33:
            monitor-exit(r3)
        L34:
            return r2
        L35:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.isSpatializerAvailableForDevice(android.media.AudioDeviceAttributes):boolean");
    }

    public final boolean isSpatializerEnabled() {
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            int i = spatializerHelper.mState;
            return (i == 0 || i == 1 || i == 3 || i == 6) ? false : true;
        }
    }

    public final boolean isSpeakerphoneOn() {
        return this.mDeviceBroker.isDeviceOnForCommunication(2);
    }

    public final boolean isStreamAffectedByMute(int i) {
        return (this.mMuteAffectedStreams & (1 << i)) != 0;
    }

    public final boolean isStreamAffectedByRingerMode(int i) {
        return (this.mRingerModeAffectedStreams & (1 << i)) != 0;
    }

    public final boolean isStreamMutableByUi(int i) {
        return (this.mUserMutableStreams & (1 << i)) != 0;
    }

    public final boolean isStreamMute(int i) {
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

    public final boolean isSupportedSystemUsage(int i) {
        synchronized (this.mSupportedSystemUsagesLock) {
            int i2 = 0;
            while (true) {
                try {
                    int[] iArr = this.mSupportedSystemUsages;
                    if (i2 >= iArr.length) {
                        return false;
                    }
                    if (iArr[i2] == i) {
                        return true;
                    }
                    i2++;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final boolean isSurroundFormatEnabled(int i) {
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

    public final boolean isUltrasoundSupported() {
        isUltrasoundSupported_enforcePermission();
        return AudioSystem.isUltrasoundSupported();
    }

    public final boolean isUsingAudio(int i) {
        try {
            if (!isAudioServerRunning()) {
                EventLogger eventLogger = sUsingAudioLogger;
                EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("isUsingAudio audioserver is died");
                stringEvent.printLog(0, "AS.AudioService");
                eventLogger.enqueue(stringEvent);
                return false;
            }
            if (!"true".equals(AudioSystem.getParameters(new AudioParameter.Builder().setParam("l_is_using_audio", i).build().toString()))) {
                return false;
            }
            EventLogger eventLogger2 = sUsingAudioLogger;
            EventLogger.StringEvent stringEvent2 = new EventLogger.StringEvent(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "uid: ", " is using audio"));
            stringEvent2.printLog(0, "AS.AudioService");
            eventLogger2.enqueue(stringEvent2);
            return true;
        } catch (SecurityException e) {
            EventLogger eventLogger3 = sUsingAudioLogger;
            EventLogger.StringEvent stringEvent3 = new EventLogger.StringEvent("isUsingAudio permission error" + e);
            stringEvent3.printLog(0, "AS.AudioService");
            eventLogger3.enqueue(stringEvent3);
            return false;
        }
    }

    public final boolean isValidAudioAttributesUsage(AudioAttributes audioAttributes) {
        int systemUsage = audioAttributes.getSystemUsage();
        if (AudioAttributes.isSystemUsage(systemUsage)) {
            return isSupportedSystemUsage(systemUsage) && ((systemUsage == 17 && (audioAttributes.getAllFlags() & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) != 0 && callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) || callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING"));
        }
        return true;
    }

    public final boolean isValidRingerMode(int i) {
        return i >= 0 && i <= 2;
    }

    public final boolean isVolumeControlUsingVolumeGroups() {
        isVolumeControlUsingVolumeGroups_enforcePermission();
        return this.mUseVolumeGroupAliases;
    }

    public final boolean isVolumeFixed() {
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

    public final boolean isVolumeGroupMuted(int i) {
        synchronized (VolumeStreamState.class) {
            try {
                SparseArray sparseArray = sVolumeGroupStates;
                if (sparseArray.indexOfKey(i) >= 0) {
                    return ((VolumeGroupState) sparseArray.get(i)).mIsMuted;
                }
                Log.e("AS.AudioService", ": no volume group found for id " + i);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean loadSoundEffects() {
        int i;
        boolean z;
        LoadSoundEffectReply loadSoundEffectReply = new LoadSoundEffectReply();
        loadSoundEffectReply.mStatus = 1;
        sendMsg(this.mAudioHandler, 7, 2, 0, 0, loadSoundEffectReply, 0);
        synchronized (loadSoundEffectReply) {
            int i2 = 3;
            while (true) {
                i = loadSoundEffectReply.mStatus;
                if (i != 1) {
                    break;
                }
                int i3 = i2 - 1;
                if (i2 <= 0) {
                    break;
                }
                try {
                    loadSoundEffectReply.wait(5000L);
                } catch (InterruptedException unused) {
                    Log.w("AS.AudioService", "Interrupted while waiting sound pool loaded.");
                }
                i2 = i3;
            }
            z = i == 0;
        }
        return z;
    }

    public final void lowerVolumeToRs1(String str) {
        enforceVolumeController("lowerVolumeToRs1");
        sendMsg(this.mAudioHandler, 1007, 2, 0, 0, null, 0);
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

    public final void muteAliasStreams(int i, final boolean z) {
        synchronized (this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                try {
                    ArrayList arrayList = new ArrayList();
                    int i2 = 0;
                    while (true) {
                        VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                        if (i2 < volumeStreamStateArr.length) {
                            VolumeStreamState volumeStreamState = volumeStreamStateArr[i2];
                            if (i == mStreamVolumeAlias[i2]) {
                                AudioService audioService = volumeStreamState.this$0;
                                int i3 = volumeStreamState.mStreamType;
                                if (audioService.isStreamAffectedByMute(i3) && ((volumeStreamState.mIndexMin == 0 || i3 == 0 || i3 == 6) && ((!this.mCameraSoundForced || volumeStreamState.mStreamType != 7) && volumeStreamState.mute("muteAliasStreams", z, false)))) {
                                    arrayList.add(Integer.valueOf(i2));
                                }
                            }
                            i2++;
                        } else {
                            arrayList.forEach(new Consumer() { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda15
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    AudioService audioService2 = AudioService.this;
                                    boolean z2 = z;
                                    Integer num = (Integer) obj;
                                    audioService2.mStreamStates[num.intValue()].doMute();
                                    int intValue = num.intValue();
                                    Intent intent = new Intent("android.media.STREAM_MUTE_CHANGED_ACTION");
                                    intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", intValue);
                                    intent.putExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", z2);
                                    audioService2.sendBroadcastToAll(intent, null);
                                }
                            });
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void muteAwaitConnection(int[] iArr, AudioDeviceAttributes audioDeviceAttributes, long j) {
        Objects.requireNonNull(iArr);
        Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        if (j <= 0 || iArr.length == 0) {
            throw new IllegalArgumentException("Invalid timeOutMs/usagesToMute");
        }
        Log.i("AS.AudioService", "muteAwaitConnection dev:" + audioDeviceAttributes + " timeOutMs:" + j + " usages:" + Arrays.toString(iArr));
        if (this.mDeviceBroker.isDeviceConnected(retrieveBluetoothAddress)) {
            Log.i("AS.AudioService", "muteAwaitConnection ignored, device (" + audioDeviceAttributes + ") already connected");
            return;
        }
        synchronized (this.mMuteAwaitConnectionLock) {
            if (this.mMutingExpectedDevice != null) {
                Log.e("AS.AudioService", "muteAwaitConnection ignored, another in progress for device:" + this.mMutingExpectedDevice);
                throw new IllegalStateException("muteAwaitConnection already in progress");
            }
            this.mMutingExpectedDevice = retrieveBluetoothAddress;
            this.mMutedUsagesAwaitingConnection = iArr;
            this.mPlaybackMonitor.muteAwaitConnection(iArr, retrieveBluetoothAddress, j);
        }
        dispatchMuteAwaitConnection(new AudioService$$ExternalSyntheticLambda12(this, retrieveBluetoothAddress, iArr, 1));
    }

    public final void muteMediaStreamOfSpeaker(boolean z) {
        boolean z2;
        int[] iArr = MIN_STREAM_VOLUME;
        int i = z ? iArr[3] : this.mSavedSpeakerMediaIndex;
        DeviceVolumeUpdate deviceVolumeUpdate = new DeviceVolumeUpdate(3, i, 2, "muteMediaStreamOfSpeaker");
        int index = this.mStreamStates[3].getIndex(2);
        if (!z || !this.mMuteMediaByVibrateOrSilentMode) {
            z2 = this.mSavedSpeakerMediaIndex != -1 && index == iArr[3];
            this.mSavedSpeakerMediaIndex = -1;
            this.mSettingHelper.setIntValue(-1, "speaker_media_index");
        } else if (this.mSavedSpeakerMediaIndex == -1) {
            this.mSavedSpeakerMediaIndex = index;
            this.mSettingHelper.setIntValue(index, "speaker_media_index");
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            AudioHandler audioHandler = this.mAudioHandler;
            Preconditions.checkState(i != -2049);
            sendMsg(audioHandler, 26, 2, index, i, deviceVolumeUpdate, 0);
        }
    }

    public final void muteRingerModeStreams() {
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        if (this.mNm == null) {
            this.mNm = (NotificationManager) this.mContext.getSystemService("notification");
        }
        int i = this.mRingerMode;
        boolean z = i == 1 || i == 0;
        boolean z2 = i == 1 && this.mDeviceBroker.isDeviceActiveForCommunication(7);
        String str = "muteRingerModeStreams() from u/pid:" + Binder.getCallingUid() + "/" + Binder.getCallingPid();
        if (z2 && !this.mDeviceBroker.mBtHelper.isBluetoothScoOn()) {
            Log.i("AS.AudioService", "shouldRingSco set to false. because, BT SCO state is not connected.");
            z2 = false;
        }
        sendMsg(this.mAudioHandler, 8, 2, 7, z2 ? 3 : 0, str, 0);
        int i2 = numStreamTypes - 1;
        while (i2 >= 0) {
            boolean isStreamMutedByRingerOrZenMode = isStreamMutedByRingerOrZenMode(i2);
            boolean z3 = (z2 && i2 == 2) ? false : true;
            int i3 = 1 << i2;
            boolean z4 = (this.mZenModeAffectedStreams & i3) != 0;
            boolean z5 = z4 || (z && isStreamAffectedByRingerMode(i2) && z3);
            if (isStreamMutedByRingerOrZenMode == z5) {
                if (z4) {
                    this.mStreamStates[i2].mute("muteRingerModeStreams", true);
                }
            } else if (z5) {
                sRingerAndZenModeMutedStreams |= i3;
                sMuteLogger.enqueue(new AudioServiceEvents$RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "muteRingerModeStreams"));
                this.mStreamStates[i2].mute("muteRingerModeStreams", true);
            } else {
                if (mStreamVolumeAlias[i2] == (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION ? 5 : 2)) {
                    synchronized (VolumeStreamState.class) {
                        try {
                            VolumeStreamState volumeStreamState = this.mStreamStates[i2];
                            for (int i4 = 0; i4 < volumeStreamState.mIndexMap.size(); i4++) {
                                int keyAt = volumeStreamState.mIndexMap.keyAt(i4);
                                if (volumeStreamState.mIndexMap.valueAt(i4) == 0) {
                                    volumeStreamState.setIndex(10, keyAt, "AS.AudioService", true);
                                }
                            }
                            sendMsg(this.mAudioHandler, 1, 2, getDeviceForStream(i2), 0, this.mStreamStates[i2], 500);
                        } finally {
                        }
                    }
                }
                sRingerAndZenModeMutedStreams &= ~i3;
                sMuteLogger.enqueue(new AudioServiceEvents$RingerZenMutedStreamsEvent(sRingerAndZenModeMutedStreams, "muteRingerModeStreams"));
                this.mStreamStates[i2].mute("muteRingerModeStreams", false);
            }
            i2--;
        }
        SystemProperties.set("persist.sys.silent", isStreamMutedByRingerOrZenMode(1) ? "1" : "0");
    }

    public final void notifySafetyVolumeDialogVisible(IVolumeController iVolumeController, boolean z) {
        enforceVolumeController("notify about volume controller visibility");
        if (this.mVolumeController.isSameBinder(iVolumeController)) {
            this.mVolumeController.mSafetyDialogVisible = z;
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("Safety Volume controller visible: ", "AS.AudioService", z);
        }
    }

    public final void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean z) {
        enforceVolumeController("notify about volume controller visibility");
        if (this.mVolumeController.isSameBinder(iVolumeController)) {
            this.mVolumeController.mVisible = z;
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("Volume controller visible: ", "AS.AudioService", z);
        }
    }

    public final void observeDevicesForMediaStream() {
        synchronized (VolumeStreamState.class) {
            this.mStreamStates[3].observeDevicesForStream_syncVSS(false);
        }
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener
    public final void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
        boolean semIsAccessibilityServiceEnabled = accessibilityManager.semIsAccessibilityServiceEnabled(32);
        this.mIsTalkBackEnabled = semIsAccessibilityServiceEnabled;
        this.mIsTalkBackEnabled = semIsAccessibilityServiceEnabled | accessibilityManager.semIsAccessibilityServiceEnabled(16);
        updateA11yVolumeAlias(accessibilityManager.isAccessibilityVolumeStreamActive());
    }

    public final void onAudioServerDied() {
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
        this.mDeviceBroker.sendIILMsg(1, 0, 0, 0, null, 0);
        AudioDeviceBroker.BtDeviceChangedData btDeviceChangedData = mBtDeviceChangedData;
        if (btDeviceChangedData != null) {
            this.mDeviceBroker.updateBluetoothA2dpDeviceConfigChange(btDeviceChangedData);
        }
        if (Rune.SEC_AUDIO_FM_RADIO && AudioManagerHelper.isFMPlayerActive()) {
            this.mAudioSystem.setForceUse(8, this.mForcedUseForFMRadio);
            this.mDeviceBroker.handleFmRadioDeviceConnection();
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
            ContentResolver contentResolver = this.mContentResolver;
            this.mSettings.getClass();
            sendEncodedSurroundMode(Settings.Global.getInt(contentResolver, "encoded_surround_output", 0), "onAudioServerDied");
            sendEnabledSurroundFormats(this.mContentResolver, true);
            AudioSystem.setRttEnabled(this.mRttEnabled);
            this.mAssistantUids.clear();
            updateAssistantUIdLocked(true);
        }
        synchronized (this.mAccessibilityServiceUidsLock) {
            AudioSystem.setA11yServicesUids(this.mAccessibilityServiceUids);
        }
        synchronized (this.mInputMethodServiceUidLock) {
            AudioSystemAdapter audioSystemAdapter = this.mAudioSystem;
            int i2 = this.mInputMethodServiceUid;
            audioSystemAdapter.getClass();
            AudioSystem.setCurrentImeUid(i2);
        }
        synchronized (this.mHdmiClientLock) {
            try {
                if (this.mHdmiManager != null && this.mHdmiTvClient != null) {
                    setHdmiSystemAudioSupported(this.mHdmiSystemAudioSupported);
                }
            } finally {
            }
        }
        synchronized (this.mSupportedSystemUsagesLock) {
            AudioSystem.setSupportedSystemUsages(this.mSupportedSystemUsages);
        }
        synchronized (this.mAudioPolicies) {
            try {
                ArrayList arrayList = new ArrayList();
                for (AudioPolicyProxy audioPolicyProxy : this.mAudioPolicies.values()) {
                    int connectMixes = audioPolicyProxy.connectMixes();
                    if (connectMixes != 0) {
                        Log.e("AS.AudioService", "onAudioServerDied: error " + AudioSystem.audioSystemErrorToString(connectMixes) + " when connecting mixes for policy " + audioPolicyProxy.toLogFriendlyString());
                        arrayList.add(audioPolicyProxy);
                    } else {
                        int i3 = audioPolicyProxy.setupDeviceAffinities();
                        if (i3 != 0) {
                            Log.e("AS.AudioService", "onAudioServerDied: error " + AudioSystem.audioSystemErrorToString(i3) + " when connecting device affinities for policy " + audioPolicyProxy.toLogFriendlyString());
                            arrayList.add(audioPolicyProxy);
                        }
                    }
                }
                arrayList.forEach(new AudioService$$ExternalSyntheticLambda22());
            } finally {
            }
        }
        synchronized (this.mPlaybackMonitor) {
            try {
                for (Map.Entry entry : this.mPlaybackMonitor.getAllAllowedCapturePolicies().entrySet()) {
                    AudioSystemAdapter audioSystemAdapter2 = this.mAudioSystem;
                    int intValue = ((Integer) entry.getKey()).intValue();
                    int capturePolicyToFlags = AudioAttributes.capturePolicyToFlags(((Integer) entry.getValue()).intValue(), 0);
                    audioSystemAdapter2.getClass();
                    int allowedCapturePolicy = AudioSystem.setAllowedCapturePolicy(intValue, capturePolicyToFlags);
                    if (allowedCapturePolicy != 0) {
                        Log.e("AS.AudioService", "Failed to restore capture policy, uid: " + entry.getKey() + ", capture policy: " + entry.getValue() + ", result: " + allowedCapturePolicy);
                        this.mPlaybackMonitor.setAllowedCapturePolicy(((Integer) entry.getKey()).intValue(), 1);
                    }
                }
            } finally {
            }
        }
        this.mSpatializerHelper.reset(this.mHasSpatializerEffect);
        if (this.mMonitorRotation) {
            RotationHelper.forceUpdate();
        }
        onIndicateSystemReady();
        synchronized (this.mCachedAbsVolDrivingStreamsLock) {
            this.mCachedAbsVolDrivingStreams.forEach(new AudioService$$ExternalSyntheticLambda23(this, 0));
        }
        AudioSystem.setParameters("restarting=false");
        this.mSoundDoseHelper.reset(true);
        int intValue2 = this.mSettingHelper.getIntValue(32, "ring_through_headset");
        this.mHeadsetOnlyStream = intValue2;
        setHeadsetOnlyStream(intValue2);
        sendMsg(this.mAudioHandler, 23, 2, 1, 0, null, 0);
        setMicrophoneMuteNoCallerCheck(getCurrentUserId());
        setMicMuteFromSwitchInput();
        updateVibratorInfos();
    }

    public final void onCustomAudioServerDied() {
        int i;
        String str;
        boolean z;
        MicModeManager micModeManager;
        MultiSoundManager multiSoundManager = this.mMultiSoundManager;
        synchronized (multiSoundManager.mMultiSoundLock) {
            try {
                Log.d("AS.MultiSoundManager", "resetByAudioServerDied");
                Iterator it = multiSoundManager.mPinAppInfoList.entrySet().iterator();
                while (it.hasNext()) {
                    MultiSoundManager.MultiSoundItem multiSoundItem = (MultiSoundManager.MultiSoundItem) ((Map.Entry) it.next()).getValue();
                    multiSoundManager.setDeviceToNative(multiSoundItem.mUid, multiSoundItem.mDevice);
                    multiSoundManager.setAppVolumeToNative(multiSoundItem.mUid);
                }
                multiSoundManager.setStateToNative();
            } finally {
            }
        }
        synchronized (multiSoundManager.mRemoteSubmixApps) {
            try {
                Iterator it2 = ((HashSet) multiSoundManager.mRemoteSubmixApps).iterator();
                int i2 = -1;
                while (it2.hasNext()) {
                    i2 = ((Integer) it2.next()).intValue();
                    multiSoundManager.setRemoteSubmixAppToNative(i2, 32768);
                }
                if (!((HashSet) multiSoundManager.mRemoteSubmixApps).isEmpty()) {
                    multiSoundManager.enableSeparateRemoteSubmix(i2, true);
                }
            } finally {
            }
        }
        DesktopModeHelper desktopModeHelper = this.mDesktopModeHelper;
        desktopModeHelper.setDexPolicyParameter(desktopModeHelper.mDexState ? "dex" : "none");
        desktopModeHelper.setDexParameter("station", desktopModeHelper.mDexConnectedState);
        desktopModeHelper.setDexParameter("pad", desktopModeHelper.mDexPadConnectedState);
        if (this.mDvDhaparam != null) {
            this.mAudioSystem.setParameters("g_effect_dv_adapt_sound=" + this.mDvDhaparam);
            VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("onRestoreAudioServerDied - setAdaptSound: gain dv dha Parameter : "), this.mDvDhaparam, "AS.AudioService");
        }
        this.mAudioSystem.setParameters("HACSetting=" + this.mHAC);
        if (isInCommunication()) {
            StringBuilder sb = new StringBuilder();
            if (this.mMode.get() == 2) {
                sb.append("g_call_state=2;");
                if (this.mPhoneType != null) {
                    sb.append("g_call_sim_slot=");
                    sb.append(this.mPhoneType);
                    sb.append(";");
                }
                if (!"false".equals(this.mCallMemoState)) {
                    sb.append("g_call_memo_state=");
                    sb.append(this.mCallMemoState);
                    sb.append(";");
                }
                if ("true".equals(this.mCallForwardingEnable)) {
                    sb.append("g_call_forwarding_enable=");
                    sb.append(this.mCallForwardingEnable);
                    sb.append(";");
                }
                if (Rune.SEC_AUDIO_SCREEN_CALL && this.mExt.mScreenCall) {
                    sb.append("l_screen_call=on;");
                }
            }
            if ("GAMEVOIP".equals(this.mAppMode)) {
                this.mSensorThread.getClass();
                AudioSystem.setParameters("l_hw_proximity_sensor_state=0");
            } else if (this.mSensorThread.mIsClosed) {
                AudioSystem.setParameters("l_hw_proximity_sensor_state=1");
            } else {
                AudioSystem.setParameters("l_hw_proximity_sensor_state=0");
            }
            Log.e("AS.AudioService", "onAudioServerDied(): " + ((Object) sb));
            this.mAudioSystem.setParameters(sb.toString());
        }
        ParcelUuid parcelUuid = BtUtils.SAP_UUID;
        StringBuilder sb2 = new StringBuilder("recoveryAuracastAppListToNative sBtAppUidList size = ");
        ArrayList arrayList = BtUtils.sBtAppUidList;
        sb2.append(arrayList.size());
        Log.i("AS.BtUtils", sb2.toString());
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("AudioServer died recoveryAuracastAppList start ! sBtAppUidList size = " + arrayList.size());
        EventLogger eventLogger = BtUtils.sAuracastLogger;
        eventLogger.enqueue(stringEvent);
        BtUtils.sSetParamCnt = 0;
        Iterator it3 = arrayList.iterator();
        loop2: while (true) {
            i = 0;
            str = "";
            while (it3.hasNext()) {
                str = str + ((Integer) it3.next()).intValue() + ";";
                i++;
                if (i == 100) {
                    break;
                }
            }
            BtUtils.sendAuracastAppListToNative(BtUtils.sSetParamCnt, str);
        }
        if (i != 0 && i < 100) {
            BtUtils.sendAuracastAppListToNative(BtUtils.sSetParamCnt, str);
        }
        eventLogger.enqueue(new EventLogger.StringEvent("AudioServer died recoveryAuracastAppList done !"));
        AudioServiceExt audioServiceExt = this.mExt;
        audioServiceExt.setAllSoundMute();
        if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER) {
            audioServiceExt.mAudioSystem.setParameters("l_call_voip_extra_volume_enable=" + audioServiceExt.mVoipExtraVolume);
        }
        if (Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING) {
            audioServiceExt.mAudioSystem.setParameters("l_call_voip_extra_volume_enable=" + audioServiceExt.mVoipAntiHowling);
        }
        boolean z2 = Rune.SEC_AUDIO_VIDEO_CALL_VOICE_EFFECT;
        if (z2 && ((z2 && audioServiceExt.mVideoCallVoiceEffectMode != -1) || (Rune.SEC_AUDIO_VIDEO_CALL_VOICE_DEFAULT_EFFECT && audioServiceExt.mVideoCallVoiceEffectMode != 100))) {
            audioServiceExt.mAudioSystem.setParameters("l_mic_input_control_mode=" + audioServiceExt.mVideoCallVoiceEffectMode);
        }
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI && (micModeManager = audioServiceExt.mMicModeManager) != null) {
            micModeManager.mMicModeType.restoreMicMode(MicModeManager.mCr);
        }
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            audioServiceExt.restoreLiveTranslator();
        }
        if (Rune.SEC_AUDIO_CALL_MONSTER_SOUND) {
            int i3 = audioServiceExt.mVoiceCallMonsterSoundMode;
            AudioSystemAdapter audioSystemAdapter = audioServiceExt.mAudioSystem;
            if (i3 != 1) {
                audioSystemAdapter.setParameters("l_call_nc_booster_enable=" + audioServiceExt.mVoiceCallMonsterSoundMode);
            }
            if (audioServiceExt.mVideoCallMonsterSoundMode != -1) {
                audioSystemAdapter.setParameters("l_mic_input_control_mode_2mic=" + audioServiceExt.mVideoCallMonsterSoundMode);
            }
        }
        DesktopModeHelper desktopModeHelper2 = audioServiceExt.mDesktopModeHelper;
        desktopModeHelper2.setDexPolicyParameter(desktopModeHelper2.mDexState ? "dex" : "none");
        desktopModeHelper2.setDexParameter("station", desktopModeHelper2.mDexConnectedState);
        desktopModeHelper2.setDexParameter("pad", desktopModeHelper2.mDexPadConnectedState);
        CoreFxUtils.setUpScalerMode(audioServiceExt.mUpscalerEnabled);
        CoreFxUtils.setAdaptSound(audioServiceExt.mContext, audioServiceExt.mAdaptSoundEnabled);
        Intent intent = new Intent("com.samsung.intent.action.MEDIA_SERVER_REBOOTED");
        Context context = audioServiceExt.mContext;
        UserHandle userHandle = UserHandle.CURRENT;
        Set set = AudioUtils.DEVICE_OUT_WIRED_DEVICE_SET;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            context.sendBroadcastAsUser(intent, userHandle, null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            audioServiceExt.setNbQualityMode(audioServiceExt.mNbQualityMode);
            if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
                VolumeMonitorService volumeMonitorService = VolumeMonitorService.getInstance(audioServiceExt.mContext);
                synchronized (volumeMonitorService) {
                    try {
                        SemVolumeMonitor semVolumeMonitor = volumeMonitorService.mSemVolumeMonitor;
                        if (semVolumeMonitor != null) {
                            semVolumeMonitor.release();
                            volumeMonitorService.mSemVolumeMonitor = null;
                        }
                        z = volumeMonitorService.mEnabled;
                        volumeMonitorService.mEnabled = false;
                    } finally {
                    }
                }
                volumeMonitorService.setVolumeMonitorOnOff(z);
            }
            int i4 = AudioHqmHelper.mAudioServerResetCount + 1;
            AudioHqmHelper.mAudioServerResetCount = i4;
            if (i4 == Integer.MAX_VALUE) {
                AudioHqmHelper.mAudioServerResetCount = i4 - AudioHqmHelper.mPreAudioServerResetCount;
                AudioHqmHelper.mPreAudioServerResetCount = 0;
                AudioHqmHelper.mAudioServerResetCountMaxLimit = true;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void onIndicateSystemReady() {
        if (AudioSystem.systemReady() == 0) {
            SemAudioSystem.setPolicyParameters("l_system_ready");
        } else {
            sendMsg(this.mAudioHandler, 20, 0, 0, 0, null, 1000);
        }
    }

    public final void onInitAdiDeviceStates() {
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        SettingsAdapter settings = audioDeviceBroker.mAudioService.getSettings();
        ContentResolver contentResolver = audioDeviceBroker.mAudioService.mContentResolver;
        String readDeviceSettings = audioDeviceBroker.readDeviceSettings();
        if (readDeviceSettings == null) {
            Log.i("AS.AudioDeviceBroker", "reading AdiDeviceState from legacy keyspatial_audio_enabled");
            settings.getClass();
            readDeviceSettings = Settings.Secure.getStringForUser(contentResolver, "spatial_audio_enabled", -2);
            if (readDeviceSettings == null) {
                Log.i("AS.AudioDeviceBroker", "no AdiDeviceState stored with legacy key");
            } else if (!readDeviceSettings.equals("")) {
                if (!Settings.Secure.putStringForUser(contentResolver, "spatial_audio_enabled", "", -2)) {
                    Log.w("AS.AudioDeviceBroker", "cannot erase the legacy AdiDeviceState with key spatial_audio_enabled");
                }
                if (!Settings.Secure.putStringForUser(contentResolver, "audio_device_inventory", readDeviceSettings, -2)) {
                    Log.e("AS.AudioDeviceBroker", "error updating the new AdiDeviceState with key audio_device_inventory");
                }
            }
        }
        if (readDeviceSettings != null && !readDeviceSettings.equals("")) {
            AudioDeviceInventory audioDeviceInventory = audioDeviceBroker.mDeviceInventory;
            synchronized (audioDeviceInventory.mDeviceInventoryLock) {
                audioDeviceInventory.mDeviceInventory.clear();
            }
            for (String str : TextUtils.split(readDeviceSettings, "\\|")) {
                AdiDeviceState adiDeviceState = null;
                if (str != null && !str.isEmpty()) {
                    String[] split = TextUtils.split(str, ",");
                    if (split.length >= 5 && split.length <= 7) {
                        try {
                            int parseInt = Integer.parseInt(split[0]);
                            int parseInt2 = split.length >= 6 ? Integer.parseInt(split[5]) : -1;
                            int parseInt3 = split.length == 7 ? Integer.parseInt(split[6]) : 0;
                            AdiDeviceState adiDeviceState2 = new AdiDeviceState(parseInt, parseInt2, split[1]);
                            adiDeviceState2.setSAEnabled(Integer.parseInt(split[2]) == 1);
                            adiDeviceState2.setHasHeadTracker(Integer.parseInt(split[3]) == 1);
                            adiDeviceState2.setHeadTrackerEnabled(Integer.parseInt(split[4]) == 1);
                            adiDeviceState2.setAudioDeviceCategory(parseInt3);
                            adiDeviceState2.updateAudioDeviceCategory();
                            adiDeviceState = adiDeviceState2;
                        } catch (NumberFormatException e) {
                            Log.e("AS.AdiDeviceState", "unable to parse setting for AdiDeviceState: ".concat(str), e);
                        }
                    }
                }
                if (adiDeviceState != null) {
                    audioDeviceInventory.addOrUpdateDeviceSAStateInInventory(adiDeviceState, false);
                    audioDeviceInventory.addOrUpdateAudioDeviceCategoryInInventory(adiDeviceState, false);
                }
            }
        }
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        Collection immutableDeviceInventory = this.mDeviceBroker.getImmutableDeviceInventory();
        soundDoseHelper.getClass();
        Iterator it = ((ArrayList) immutableDeviceInventory).iterator();
        while (it.hasNext()) {
            AdiDeviceState adiDeviceState3 = (AdiDeviceState) it.next();
            if (adiDeviceState3.getAudioDeviceCategory() != 0) {
                ISoundDose.AudioDeviceCategory audioDeviceCategory = new ISoundDose.AudioDeviceCategory();
                audioDeviceCategory.address = adiDeviceState3.getDeviceAddress();
                audioDeviceCategory.internalAudioType = adiDeviceState3.getInternalDeviceType();
                audioDeviceCategory.csdCompatible = adiDeviceState3.getAudioDeviceCategory() == 3;
                soundDoseHelper.mCachedAudioDeviceCategories.add(audioDeviceCategory);
            }
        }
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
        if (i == 0) {
            if (checkVolumeRangeInitialization(str)) {
                EventLogger eventLogger = sLifecycleLogger;
                EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ": initStreamVolume succeeded"));
                stringEvent.printLog(0, "AS.AudioService");
                eventLogger.enqueue(stringEvent);
                return;
            }
            return;
        }
        EventLogger eventLogger2 = sLifecycleLogger;
        EventLogger.StringEvent stringEvent2 = new EventLogger.StringEvent(str + ": initStreamVolume failed with " + i + " will retry");
        stringEvent2.printLog(1, "AS.AudioService");
        eventLogger2.enqueue(stringEvent2);
        sendMsg(this.mAudioHandler, 34, 1, 0, 0, str, 2000);
    }

    public final void onRoutingUpdatedFromAudioThread() {
        if (this.mHasSpatializerEffect) {
            this.mSpatializerHelper.onRoutingUpdated();
        }
        synchronized (this.mMuteAwaitConnectionLock) {
            try {
                AudioDeviceAttributes audioDeviceAttributes = this.mMutingExpectedDevice;
                if (audioDeviceAttributes == null) {
                    return;
                }
                int[] iArr = this.mMutedUsagesAwaitingConnection;
                if (this.mDeviceBroker.isDeviceConnected(audioDeviceAttributes)) {
                    this.mMutingExpectedDevice = null;
                    this.mMutedUsagesAwaitingConnection = null;
                    this.mPlaybackMonitor.cancelMuteAwaitConnection("checkMuteAwaitConnection device " + audioDeviceAttributes + " connected, unmuting");
                    dispatchMuteAwaitConnection(new AudioService$$ExternalSyntheticLambda12(this, audioDeviceAttributes, iArr, 2));
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.audio.AudioSystemAdapter.OnRoutingUpdatedListener
    public final void onRoutingUpdatedFromNative() {
        sendMsg(this.mAudioHandler, 41, 0, 0, 0, null, 0);
    }

    public final void onSetAppDevice(int i, int i2, boolean z) {
        this.mMultiSoundManager.setAppDevice(i, i2, z);
        this.mMediaFocusControl.updateFocusRequester(i, false);
        sendBroadcastToAll(new Intent("android.intent.action.MULTISOUND_STATE_CHANGE"), null);
        MediaSessionService.MediaSessionServiceInternal mediaSessionServiceInternal = this.mMediaSessionServiceInternal;
        if (mediaSessionServiceInternal != null) {
            mediaSessionServiceInternal.updateMultiSoundInfo(-1, isMultiSoundOn());
        }
        if (z) {
            this.mMultiSoundManager.showNotification();
        }
    }

    public final void onSetStreamVolume(int i, int i2, String str, int i3, int i4, boolean z) {
        int i5 = mStreamVolumeAlias[i];
        if ((i3 & 2) != 0 || i5 == getUiSoundsStreamType()) {
            str = XmlUtils$$ExternalSyntheticOutline0.m("AS.AudioService.onSetStreamVolume(", str, ")");
            setRingerMode(getNewRingerMode(i5, i2, i3), str, false);
        }
        setStreamVolumeInt(str, i5, i2, i4, false, z);
        if (i2 != 0) {
            synchronized (this.mSettingsLock) {
                int i6 = 0;
                while (true) {
                    try {
                        VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
                        if (i6 < volumeStreamStateArr.length) {
                            if (i5 == mStreamVolumeAlias[i6]) {
                                volumeStreamStateArr[i6].mute("onSetStreamVolume", false);
                            }
                            i6++;
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MANAGE_AUDIO_POLICY") != 0) {
            throw new SecurityException("Missing MANAGE_AUDIO_POLICY permission");
        }
        new AudioManagerShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    /* JADX WARN: Removed duplicated region for block: B:136:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onSystemReady() {
        /*
            Method dump skipped, instructions count: 878
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.onSystemReady():void");
    }

    @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
    public final void onTouchExplorationStateChanged(boolean z) {
        updateDefaultStreamOverrideDelay(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x013c A[Catch: all -> 0x00fa, TryCatch #1 {all -> 0x00fa, blocks: (B:112:0x00e9, B:116:0x00f1, B:120:0x0118, B:123:0x011e, B:124:0x0131, B:30:0x0138, B:32:0x013c, B:34:0x0140, B:35:0x0143, B:125:0x00ff), top: B:111:0x00e9 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0355  */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUpdateAudioMode(int r32, final int r33, final java.lang.String r34, boolean r35) {
        /*
            Method dump skipped, instructions count: 901
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.onUpdateAudioMode(int, int, java.lang.String, boolean):void");
    }

    public void onUpdatedAdiDeviceState(AdiDeviceState adiDeviceState, boolean z) {
        if (adiDeviceState == null) {
            return;
        }
        this.mSpatializerHelper.refreshDevice(adiDeviceState.getAudioDeviceAttributes(), z);
        this.mSoundDoseHelper.setAudioDeviceCategory(adiDeviceState.getInternalDeviceType(), adiDeviceState.getDeviceAddress(), adiDeviceState.getAudioDeviceCategory() == 3);
    }

    @Override // com.android.server.audio.AudioSystemAdapter.OnVolRangeInitRequestListener
    public final void onVolumeRangeInitRequestFromNative() {
        sendMsg(this.mAudioHandler, 34, 0, 0, 0, "onVolumeRangeInitRequestFromNative", 0);
    }

    public final void playSoundEffect(int i, int i2) {
        SettingsAdapter settingsAdapter = this.mSettings;
        ContentResolver contentResolver = this.mContentResolver;
        settingsAdapter.getClass();
        if (Settings.System.getIntForUser(contentResolver, "sound_effects_enabled", 0, i2) != 0) {
            playSoundEffectVolume(i, -1.0f);
        }
    }

    public final void playSoundEffectVolume(int i, float f) {
        if (isStreamMute(1)) {
            return;
        }
        if (i < 23 && i >= 0) {
            sendMsg(this.mAudioHandler, 5, 2, i, (int) (f * 1000.0f), null, 0);
            return;
        }
        Log.w("AS.AudioService", "AudioService effectType value " + i + " out of range");
    }

    public final void playerAttributes(int i, AudioAttributes audioAttributes) {
        boolean z;
        if (audioAttributes != null) {
            validateAudioAttributesUsage(audioAttributes);
        }
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        int callingUid = Binder.getCallingUid();
        synchronized (playbackActivityMonitor.mAllowedCapturePolicies) {
            try {
                if (playbackActivityMonitor.mAllowedCapturePolicies.containsKey(Integer.valueOf(callingUid)) && audioAttributes.getAllowedCapturePolicy() < ((Integer) playbackActivityMonitor.mAllowedCapturePolicies.get(Integer.valueOf(callingUid))).intValue()) {
                    audioAttributes = new AudioAttributes.Builder(audioAttributes).setAllowedCapturePolicy(((Integer) playbackActivityMonitor.mAllowedCapturePolicies.get(Integer.valueOf(callingUid))).intValue()).build();
                }
            } finally {
            }
        }
        synchronized (playbackActivityMonitor.mPlayerLock) {
            try {
                AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) playbackActivityMonitor.mPlayers.get(new Integer(i));
                if (PlaybackActivityMonitor.checkConfigurationCaller(i, audioPlaybackConfiguration, callingUid)) {
                    PlaybackActivityMonitor.sEventLogger.enqueue(new PlaybackActivityMonitor.AudioAttrEvent(i, audioAttributes));
                    z = audioPlaybackConfiguration.handleAudioAttributesEvent(audioAttributes);
                } else {
                    Log.e("AS.PlaybackActivityMon", "Error updating audio attributes");
                    z = false;
                }
            } finally {
            }
        }
        if (z) {
            playbackActivityMonitor.dispatchPlaybackChange(false);
        }
    }

    public final void playerEvent(int i, int i2, int i3) {
        boolean z;
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        int callingUid = Binder.getCallingUid();
        playbackActivityMonitor.getClass();
        Log.v("AS.PlaybackActivityMon", TextUtils.formatSimple("playerEvent(piid=%d, event=%s, eventValue=%d)", new Object[]{Integer.valueOf(i), AudioPlaybackConfiguration.playerStateToString(i2), Integer.valueOf(i3)}));
        synchronized (playbackActivityMonitor.mPlayerLock) {
            try {
                AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) playbackActivityMonitor.mPlayers.get(new Integer(i));
                if (audioPlaybackConfiguration == null) {
                    return;
                }
                boolean contains = playbackActivityMonitor.mDoNotLogPiidList.contains(Integer.valueOf(i));
                if (!contains || i2 == 0) {
                    PlaybackActivityMonitor.sEventLogger.enqueue(new PlaybackActivityMonitor.PlayerEvent(i, i2, i3));
                    if (i2 == 6) {
                        com.android.media.audio.Flags.portToPiidSimplification();
                        playbackActivityMonitor.mPortIdToPiid.put(i3, i);
                        return;
                    }
                    if (i2 == 2) {
                        Iterator it = playbackActivityMonitor.mBannedUids.iterator();
                        while (it.hasNext()) {
                            if (PlaybackActivityMonitor.checkBanPlayer(audioPlaybackConfiguration, ((Integer) it.next()).intValue())) {
                                PlaybackActivityMonitor.sEventLogger.enqueue(new EventLogger.StringEvent("not starting piid:" + i + ", is banned"));
                                return;
                            }
                        }
                        GoodCatchManager goodCatchManager = playbackActivityMonitor.mAudioService.mGoodCatchManager;
                        if (goodCatchManager != null) {
                            if (TextUtils.equals("AudioService", goodCatchManager.mModule) ? goodCatchManager.mSoundFunc[3] : false) {
                                playbackActivityMonitor.updateGoodCatch(audioPlaybackConfiguration);
                            }
                        }
                    }
                    if (audioPlaybackConfiguration.getPlayerType() != 3 || i2 == 0) {
                        if (PlaybackActivityMonitor.checkConfigurationCaller(i, audioPlaybackConfiguration, callingUid)) {
                            playbackActivityMonitor.checkVolumeForPrivilegedAlarm(audioPlaybackConfiguration, i2);
                            z = audioPlaybackConfiguration.handleStateEvent(i2, i3);
                        } else {
                            Log.e("AS.PlaybackActivityMon", "Error handling event " + i2);
                            z = false;
                        }
                        if (z) {
                            if (i2 == 2) {
                                playbackActivityMonitor.mDuckingManager.checkDuck(audioPlaybackConfiguration);
                                playbackActivityMonitor.mFadeOutManager.checkFade(audioPlaybackConfiguration);
                            }
                            if (PlaybackUtils.isMusicActive(audioPlaybackConfiguration)) {
                                playbackActivityMonitor.mNotifierSoundAliveForDVFS.runOrSkip();
                                if (Rune.SEC_AUDIO_VOLUME_MONITOR) {
                                    VolumeMonitorService.getInstance(playbackActivityMonitor.mContext).triggerMonitoring();
                                }
                            }
                            if (contains) {
                                z = false;
                            }
                        }
                        if (z) {
                            playbackActivityMonitor.dispatchPlaybackChange(i2 == 0);
                        }
                    }
                }
            } finally {
            }
        }
    }

    public final void playerHasOpPlayAudio(int i, boolean z) {
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        int callingUid = Binder.getCallingUid();
        playbackActivityMonitor.getClass();
        PlaybackActivityMonitor.sEventLogger.enqueue(new PlaybackActivityMonitor.PlayerOpPlayAudioEvent(i, callingUid, z));
    }

    public final void playerSessionId(int i, int i2) {
        boolean z;
        if (i2 <= 0) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "invalid session Id "));
        }
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        int callingUid = Binder.getCallingUid();
        synchronized (playbackActivityMonitor.mPlayerLock) {
            try {
                AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) playbackActivityMonitor.mPlayers.get(new Integer(i));
                if (PlaybackActivityMonitor.checkConfigurationCaller(i, audioPlaybackConfiguration, callingUid)) {
                    z = audioPlaybackConfiguration.handleSessionIdEvent(i2);
                } else {
                    Log.e("AS.PlaybackActivityMon", "Error updating audio session");
                    z = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            playbackActivityMonitor.dispatchPlaybackChange(false);
        }
    }

    public final void portEvent(int i, int i2, PersistableBundle persistableBundle) {
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        int callingUid = Binder.getCallingUid();
        playbackActivityMonitor.getClass();
        if (!UserHandle.isCore(callingUid)) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(callingUid, "Forbidden operation from uid ", "AS.PlaybackActivityMon");
            return;
        }
        Log.v("AS.PlaybackActivityMon", TextUtils.formatSimple("BLA portEvent(portId=%d, event=%s, extras=%s)", new Object[]{Integer.valueOf(i), AudioPlaybackConfiguration.playerStateToString(i2), persistableBundle}));
        synchronized (playbackActivityMonitor.mPlayerLock) {
            try {
                com.android.media.audio.Flags.portToPiidSimplification();
                int i3 = playbackActivityMonitor.mPortIdToPiid.get(i, -1);
                if (i3 == -1) {
                    Log.w("AS.PlaybackActivityMon", "No piid assigned for invalid/internal port id " + i);
                    return;
                }
                AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) playbackActivityMonitor.mPlayers.get(Integer.valueOf(i3));
                if (audioPlaybackConfiguration == null) {
                    Log.w("AS.PlaybackActivityMon", "No AudioPlaybackConfiguration assigned for piid " + i3);
                } else {
                    if (audioPlaybackConfiguration.getPlayerType() == 3) {
                        return;
                    }
                    if (i2 == 7) {
                        PlaybackActivityMonitor.AnonymousClass1 anonymousClass1 = playbackActivityMonitor.mEventHandler;
                        anonymousClass1.sendMessage(anonymousClass1.obtainMessage(2, i3, i, persistableBundle));
                    } else if (i2 == 8) {
                        PlaybackActivityMonitor.AnonymousClass1 anonymousClass12 = playbackActivityMonitor.mEventHandler;
                        anonymousClass12.sendMessage(anonymousClass12.obtainMessage(3, i3, i, persistableBundle));
                    }
                }
            } finally {
            }
        }
    }

    public void postAccessoryPlugMediaUnmute(int i) {
        sendMsg(this.mAudioHandler, 21, 2, i, 0, null, 0);
    }

    public void postObserveDevicesForAllStreams() {
        postObserveDevicesForAllStreams(-1);
    }

    public void postObserveDevicesForAllStreams(int i) {
        sendMsg(this.mAudioHandler, 27, 2, i, 0, null, 0);
    }

    public void postSetVolumeIndexOnDevice(int i, int i2, int i3, String str) {
        sendMsg(this.mAudioHandler, 26, 2, 0, 0, new DeviceVolumeUpdate(i, i2, i3, str), 0);
    }

    public final void queueMsgUnderWakeLock(Handler handler, int i, int i2, int i3) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAudioEventWakeLock.acquire();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            sendMsg(handler, i, 2, i2, i3, null, 0);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0037, code lost:
    
        if (r7.mUseFixedVolume != false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readAudioSettings(boolean r8) {
        /*
            Method dump skipped, instructions count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.readAudioSettings(boolean):void");
    }

    public final boolean readCameraSoundForced() {
        if (SystemProperties.getBoolean("audio.camerasound.force", false) || this.mContext.getResources().getBoolean(R.bool.config_carrier_volte_available)) {
            return true;
        }
        SubscriptionManager subscriptionManager = (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
        if (subscriptionManager == null) {
            Log.e("AS.AudioService", "readCameraSoundForced cannot create SubscriptionManager!");
            return false;
        }
        for (int i : subscriptionManager.getActiveSubscriptionIdList(false)) {
            if (SubscriptionManager.getResourcesForSubId(this.mContext, i).getBoolean(R.bool.config_carrier_volte_available)) {
                return true;
            }
        }
        return false;
    }

    public final void readDockAudioSettings(ContentResolver contentResolver) {
        this.mSettings.getClass();
        boolean z = Settings.Global.getInt(contentResolver, "dock_audio_media_enabled", 0) == 1;
        this.mDockAudioMediaEnabled = z;
        sendMsg(this.mAudioHandler, 8, 2, 3, z ? 9 : 0, new String("readDockAudioSettings"), 0);
    }

    public final void readPersistedSettings() {
        this.mSystemServer.getClass();
        ContentResolver contentResolver = this.mContentResolver;
        this.mSettings.getClass();
        int i = Settings.Global.getInt(contentResolver, "mode_ringer", 2);
        int i2 = !isValidRingerMode(i) ? 2 : i;
        if (i2 == 1 && !this.mHasVibrator) {
            i2 = 0;
        }
        if (i2 != i) {
            this.mSettings.getClass();
            Settings.Global.putInt(contentResolver, "mode_ringer", i2);
        }
        if (this.mUseFixedVolume || this.mIsSingleVolume) {
            i2 = 2;
        }
        synchronized (this.mSettingsLock) {
            try {
                this.mRingerMode = i2;
                if (this.mRingerModeExternal == -1) {
                    this.mRingerModeExternal = i2;
                }
                int valueForVibrateSetting = AudioSystem.getValueForVibrateSetting(0, 1, this.mHasVibrator ? 2 : 0);
                this.mVibrateSetting = valueForVibrateSetting;
                this.mVibrateSetting = AudioSystem.getValueForVibrateSetting(valueForVibrateSetting, 0, this.mHasVibrator ? 2 : 0);
                updateRingerAndZenModeAffectedStreams();
                readDockAudioSettings(contentResolver);
                this.mSettings.getClass();
                sendEncodedSurroundMode(Settings.Global.getInt(contentResolver, "encoded_surround_output", 0), "readPersistedSettings");
                sendEnabledSurroundFormats(contentResolver, true);
                updateAssistantUIdLocked(true);
                this.mActiveAssistantServiceUids = NO_ACTIVE_ASSISTANT_SERVICE_UIDS;
                updateActiveAssistantServiceUids();
                AudioSystem.setRttEnabled(this.mRttEnabled);
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mSettings.getClass();
        int intForUser = Settings.System.getIntForUser(contentResolver, "mute_streams_affected", 111, -2);
        this.mMuteAffectedStreams = intForUser;
        this.mUserMutableStreams = intForUser & (-66);
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
        broadcastRingerMode(this.mRingerModeExternal, "android.media.RINGER_MODE_CHANGED");
        broadcastRingerMode(this.mRingerMode, "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
        broadcastVibrateSetting(0);
        broadcastVibrateSetting(1);
        AudioService.this.mSettings.getClass();
        Settings.Secure.getIntForUser(contentResolver, "long_press_timeout", 500, -2);
        ContentResolver contentResolver2 = this.mContentResolver;
        this.mAdjustMediaVolumeOnly = Settings.System.getIntForUser(contentResolver2, "adjust_media_volume_only", !FactoryUtils.isFactoryMode() ? 1 : 0, -2) > 0;
        int intValue = this.mSettingHelper.getIntValue(32, "ring_through_headset");
        this.mHeadsetOnlyStream = intValue;
        setHeadsetOnlyStream(intValue);
        int intValue2 = this.mSettingHelper.getIntValue(0, "sound_lr_switch");
        this.mLRSwitching = intValue2;
        if (intValue2 == 1) {
            this.mAudioSystem.setParameters("l_sound_assistant_lr_switch_enable=true");
        }
        this.mMediaVolumeStepIndex = this.mSettingHelper.getIntValue(10, "media_volume_step_index");
        Settings.Global.putInt(this.mContentResolver, "mode_ringer_time_on", 0);
        this.mVolumeLimitOn = Settings.System.getInt(contentResolver2, "volumelimit_on", 0) != 0;
        this.mVolumeLimitValue = Settings.System.getInt(contentResolver2, "volume_limiter_value", this.mVolumeLimitValue);
        if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
            this.mLiveTranslatorDuringCall = Settings.Global.getInt(this.mContentResolver, "translate_during_calls", 1) != 0;
            this.mLiveTranslatorAllowApps = Settings.Global.getString(this.mContentResolver, "translate_during_allow_apps");
        }
    }

    public final void readUserRestrictions() {
        this.mSystemServer.getClass();
        int currentUserId = getCurrentUserId();
        if (this.mUseFixedVolume) {
            AudioSystem.setMasterVolume(1.0f);
        }
        setMasterMuteInternalNoCallerCheck(0, currentUserId, "readUserRestrictions", this.mUserManagerInternal.getUserRestriction(currentUserId, "disallow_unmute_device") || this.mUserManagerInternal.getUserRestriction(currentUserId, "no_adjust_volume"));
        boolean userRestriction = this.mUserManagerInternal.getUserRestriction(currentUserId, "no_unmute_microphone");
        this.mMicMuteFromRestrictions = userRestriction;
        Log.d("AS.AudioService", String.format("Mic mute %b, user=%d", Boolean.valueOf(userRestriction), Integer.valueOf(currentUserId)));
        setMicrophoneMuteNoCallerCheck(currentUserId);
        AudioServiceExt audioServiceExt = this.mExt;
        audioServiceExt.mAllSoundMute = Settings.System.getIntForUser(audioServiceExt.mCr, "all_sound_off", 0, currentUserId);
        GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("readUserRestrictions mAllSoundMute = "), audioServiceExt.mAllSoundMute, "AS.AudioServiceExt");
    }

    public final void recenterHeadTracker() {
        recenterHeadTracker_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            if (spatializerHelper.checkSpatializer("recenterHeadTracker") && spatializerHelper.mIsHeadTrackingSupported) {
                try {
                    spatializerHelper.mSpat.recenterHeadTracker();
                } catch (RemoteException e) {
                    Log.e("AS.SpatializerHelper", "Error calling recenterHeadTracker", e);
                }
            }
        }
    }

    public final void recordRingtoneChanger(String str) {
        if (str == null) {
            return;
        }
        sRingtoneChangeLogger.enqueue(new EventLogger.StringEvent(str.substring(0, Math.min(str.length(), 600))));
    }

    public final void recorderEvent(int i, int i2) {
        RecordingActivityMonitor recordingActivityMonitor = this.mRecordMonitor;
        int i3 = 1;
        if (recordingActivityMonitor.mLegacyRemoteSubmixRiid.get() == i) {
            recordingActivityMonitor.mLegacyRemoteSubmixActive.set(i2 == 0);
        }
        if (i2 == 0) {
            i3 = 0;
        } else if (i2 != 1) {
            i3 = -1;
        }
        if (i != -1 && i3 != -1) {
            recordingActivityMonitor.dispatchCallbacks(recordingActivityMonitor.updateSnapshot(i3, i, null));
            return;
        }
        EventLogger eventLogger = RecordingActivityMonitor.sEventLogger;
        RecordingActivityMonitor.RecordingEvent recordingEvent = new RecordingActivityMonitor.RecordingEvent(i2, i, null);
        recordingEvent.printLog(0, "AudioService.RecordingActivityMonitor");
        eventLogger.enqueue(recordingEvent);
    }

    public final String registerAudioPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection, AttributionSource attributionSource) {
        Objects.requireNonNull(attributionSource);
        AudioSystem.setDynamicPolicyCallback(this.mDynPolicyCallback);
        boolean z5 = z2 || z3 || z || z4 || audioPolicyConfig.getMixes().isEmpty();
        Iterator it = audioPolicyConfig.getMixes().iterator();
        boolean z6 = false;
        boolean z7 = false;
        boolean z8 = false;
        ArrayList arrayList = null;
        while (true) {
            if (it.hasNext()) {
                AudioMix audioMix = (AudioMix) it.next();
                if (audioMix.getRule().allowPrivilegedMediaPlaybackCapture()) {
                    String canBeUsedForPrivilegedMediaCapture = AudioMix.canBeUsedForPrivilegedMediaCapture(audioMix.getFormat());
                    if (canBeUsedForPrivilegedMediaCapture != null) {
                        Log.e("AS.AudioService", canBeUsedForPrivilegedMediaCapture);
                        break;
                    }
                    z6 = true;
                }
                if (audioMix.containsMatchAttributeRuleForUsage(2) && audioMix.getRouteFlags() == 3) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(audioMix);
                }
                if (audioMix.getRouteFlags() == 3 && iMediaProjection != null) {
                    z7 = true;
                } else if (audioMix.isForCallRedirection()) {
                    z8 = true;
                } else if (audioMix.containsMatchAttributeRuleForUsage(2)) {
                    z5 = true;
                }
            } else if (!z6 || callerHasPermission("android.permission.CAPTURE_MEDIA_OUTPUT") || callerHasPermission("android.permission.CAPTURE_AUDIO_OUTPUT")) {
                if (arrayList != null && arrayList.size() > 0) {
                    if (callerHasPermission("android.permission.CAPTURE_VOICE_COMMUNICATION_OUTPUT")) {
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            ((AudioMix) it2.next()).getRule().setVoiceCommunicationCaptureAllowed(true);
                        }
                    } else {
                        Log.e("AS.AudioService", "Audio capture for voice communication requires CAPTURE_VOICE_COMMUNICATION_OUTPUT system permission");
                    }
                }
                if (z7) {
                    if (iMediaProjection == null) {
                        Log.e("AS.AudioService", "MediaProjection is null");
                    } else {
                        if (this.mProjectionService == null) {
                            this.mProjectionService = IMediaProjectionManager.Stub.asInterface(ServiceManager.getService("media_projection"));
                        }
                        IMediaProjectionManager iMediaProjectionManager = this.mProjectionService;
                        if (iMediaProjectionManager == null) {
                            Log.e("AS.AudioService", "Can't get service IMediaProjectionManager");
                        } else {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                try {
                                } catch (RemoteException e) {
                                    Log.e("AS.AudioService", "Can't call .isCurrentProjection() on IMediaProjectionManager" + iMediaProjectionManager.asBinder(), e);
                                }
                                if (iMediaProjectionManager.isCurrentProjection(iMediaProjection)) {
                                    try {
                                        if (!iMediaProjection.canProjectAudio()) {
                                            Log.w("AS.AudioService", "App passed MediaProjection that can not project audio");
                                        }
                                    } catch (RemoteException e2) {
                                        Log.e("AS.AudioService", "Can't call .canProjectAudio() on valid IMediaProjection" + iMediaProjection.asBinder(), e2);
                                    }
                                } else {
                                    Log.w("AS.AudioService", "App passed invalid MediaProjection token");
                                }
                            } finally {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }
                }
                if (z5 && !callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING")) {
                    Log.e("AS.AudioService", "Can not capture audio without MODIFY_AUDIO_ROUTING");
                } else {
                    if (!z8 || callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) {
                        synchronized (this.mAudioPolicies) {
                            if (this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                                Slog.e("AS.AudioService", "Cannot re-register policy");
                                return null;
                            }
                            try {
                                AudioPolicyProxy audioPolicyProxy = new AudioPolicyProxy(audioPolicyConfig, iAudioPolicyCallback, z, z2, z3, z4, iMediaProjection, attributionSource);
                                iAudioPolicyCallback.asBinder().linkToDeath(audioPolicyProxy, 0);
                                EventLogger eventLogger = this.mDynPolicyLogger;
                                EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("registerAudioPolicy for " + iAudioPolicyCallback.asBinder() + " u/pid:" + Binder.getCallingUid() + "/" + Binder.getCallingPid() + " with config:" + audioPolicyProxy.toCompactLogString());
                                stringEvent.printLog(0, "AS.AudioService");
                                eventLogger.enqueue(stringEvent);
                                String registrationId = audioPolicyProxy.getRegistrationId();
                                this.mAudioPolicies.put(iAudioPolicyCallback.asBinder(), audioPolicyProxy);
                                return registrationId;
                            } catch (RemoteException e3) {
                                Slog.w("AS.AudioService", "Audio policy registration failed, could not link to " + iAudioPolicyCallback + " binder death", e3);
                                return null;
                            } catch (IllegalStateException e4) {
                                Slog.w("AS.AudioService", "Audio policy registration failed for binder " + iAudioPolicyCallback, e4);
                                return null;
                            }
                        }
                    }
                    Log.e("AS.AudioService", "Can not capture audio without CALL_AUDIO_INTERCEPTION");
                }
            } else {
                Log.e("AS.AudioService", "Privileged audio capture requires CAPTURE_MEDIA_OUTPUT or CAPTURE_AUDIO_OUTPUT system permission");
            }
        }
        Slog.w("AS.AudioService", "Permission denied to register audio policy for pid " + Binder.getCallingPid() + " / uid " + Binder.getCallingUid() + ", need system permission or a MediaProjection that can project audio");
        return null;
    }

    public final void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) {
        checkMonitorAudioServerStatePermission();
        synchronized (this.mAudioServerStateListeners) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        if (iCapturePresetDevicesRoleDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.mDeviceInventory.mDevRoleCapturePresetDispatchers.register(iCapturePresetDevicesRoleDispatcher, Boolean.valueOf(isBluetoothPrividged()));
    }

    public final void registerCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
        if (iCommunicationDeviceDispatcher == null) {
            return;
        }
        this.mDeviceBroker.mCommDevDispatchers.register(iCommunicationDeviceDispatcher);
    }

    public final void registerCurrentDeviceChangedCallback(int i, SensorHandleThread$$ExternalSyntheticLambda0 sensorHandleThread$$ExternalSyntheticLambda0, Executor executor) {
        VolumeStreamState volumeStreamState;
        VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
        if (volumeStreamStateArr == null || (volumeStreamState = volumeStreamStateArr[i]) == null) {
            return;
        }
        CurrentDeviceManager currentDeviceManager = volumeStreamState.mCurrentDeviceManager;
        Set unmodifiableSet = Collections.unmodifiableSet(volumeStreamState.mObservedDeviceSet);
        currentDeviceManager.getClass();
        synchronized (CurrentDeviceManager.lock) {
            CurrentDeviceManager.CallbackRecord callbackRecord = new CurrentDeviceManager.CallbackRecord(sensorHandleThread$$ExternalSyntheticLambda0, executor);
            ((HashSet) currentDeviceManager.callbacks).add(callbackRecord);
            Executor executor2 = callbackRecord.executor;
            if (executor2 != null) {
                executor2.execute(new CurrentDeviceManager$CallbackRecord$$ExternalSyntheticLambda0(callbackRecord, unmodifiableSet));
            }
        }
    }

    public final void registerDeviceVolumeBehaviorDispatcher(boolean z, IDeviceVolumeBehaviorDispatcher iDeviceVolumeBehaviorDispatcher) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.QUERY_AUDIO_STATE") != 0) {
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING or QUERY_AUDIO_STATE permissions");
        }
        Objects.requireNonNull(iDeviceVolumeBehaviorDispatcher);
        if (z) {
            this.mDeviceVolumeBehaviorDispatchers.register(iDeviceVolumeBehaviorDispatcher);
        } else {
            this.mDeviceVolumeBehaviorDispatchers.unregister(iDeviceVolumeBehaviorDispatcher);
        }
    }

    public final void registerDeviceVolumeDispatcherForAbsoluteVolume(boolean z, IAudioDeviceVolumeDispatcher iAudioDeviceVolumeDispatcher, String str, AudioDeviceAttributes audioDeviceAttributes, List list, boolean z2, int i) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0 && this.mContext.checkCallingOrSelfPermission("android.permission.BLUETOOTH_PRIVILEGED") != 0) {
            throw new SecurityException("Missing MODIFY_AUDIO_ROUTING or BLUETOOTH_PRIVILEGED permissions");
        }
        Objects.requireNonNull(audioDeviceAttributes);
        Objects.requireNonNull(list);
        int internalType = audioDeviceAttributes.getInternalType();
        if (!z) {
            if (removeAudioSystemDeviceOutFromAbsVolumeDevices(internalType) != null) {
                dispatchDeviceVolumeBehavior(audioDeviceAttributes, 0);
                return;
            }
            return;
        }
        AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo = new AbsoluteVolumeDeviceInfo(audioDeviceAttributes, list, iAudioDeviceVolumeDispatcher, z2, i);
        AbsoluteVolumeDeviceInfo absoluteVolumeDeviceInfo2 = (AbsoluteVolumeDeviceInfo) ((ArrayMap) this.mAbsoluteVolumeDeviceInfoMap).get(Integer.valueOf(internalType));
        if (absoluteVolumeDeviceInfo2 == null || absoluteVolumeDeviceInfo2.mDeviceVolumeBehavior != i) {
            removeAudioSystemDeviceOutFromFullVolumeDevices(internalType);
            removeAudioSystemDeviceOutFromFixedVolumeDevices(internalType);
            Log.d("AS.AudioService", "Adding DeviceType: 0x" + Integer.toHexString(internalType) + " to mAbsoluteVolumeDeviceInfoMap with behavior " + AudioDeviceVolumeManager.volumeBehaviorName(i));
            ((ArrayMap) this.mAbsoluteVolumeDeviceInfoMap).put(Integer.valueOf(internalType), absoluteVolumeDeviceInfo);
            dispatchDeviceVolumeBehavior(audioDeviceAttributes, i);
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            VolumeInfo volumeInfo = (VolumeInfo) it.next();
            if (volumeInfo.getVolumeIndex() != -100 && volumeInfo.getMinVolumeIndex() != -100 && volumeInfo.getMaxVolumeIndex() != -100) {
                if (volumeInfo.hasStreamType()) {
                    setStreamVolumeInt(str, volumeInfo.getStreamType(), rescaleIndex(volumeInfo, volumeInfo.getStreamType()), internalType, false, true);
                } else {
                    for (int i2 : volumeInfo.getVolumeGroup().getLegacyStreamTypes()) {
                        setStreamVolumeInt(str, i2, rescaleIndex(volumeInfo, i2), internalType, false, true);
                    }
                }
            }
        }
    }

    public final void registerHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) {
        registerHeadToSoundstagePoseCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerHeadToSoundStagePoseCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.mHeadPoseCallbacks.register(iSpatializerHeadToSoundStagePoseCallback);
        }
    }

    public final void registerLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) {
        LoudnessCodecHelper loudnessCodecHelper = this.mLoudnessCodecHelper;
        loudnessCodecHelper.getClass();
        loudnessCodecHelper.mLoudnessUpdateDispatchers.register(iLoudnessCodecUpdatesDispatcher, Integer.valueOf(Binder.getCallingPid()));
    }

    public final void registerModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) {
        this.mModeDispatchers.register(iAudioModeDispatcher);
    }

    public final void registerMuteAwaitConnectionDispatcher(IMuteAwaitConnectionCallback iMuteAwaitConnectionCallback, boolean z) {
        registerMuteAwaitConnectionDispatcher_enforcePermission();
        if (z) {
            this.mMuteAwaitConnectionDispatchers.register(iMuteAwaitConnectionCallback, Boolean.valueOf(isBluetoothPrividged()));
        } else {
            this.mMuteAwaitConnectionDispatchers.unregister(iMuteAwaitConnectionCallback);
        }
    }

    public final void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
        this.mPlaybackMonitor.registerPlaybackCallback(iPlaybackConfigDispatcher, this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public final void registerPlaybackCallbackWithPackage(IPlaybackConfigDispatcher iPlaybackConfigDispatcher, String str) {
        registerPlaybackCallback(iPlaybackConfigDispatcher);
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        if (iPlaybackConfigDispatcher != null) {
            Iterator it = playbackActivityMonitor.mClients.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                PlaybackActivityMonitor.PlayMonitorClient playMonitorClient = (PlaybackActivityMonitor.PlayMonitorClient) it.next();
                if (iPlaybackConfigDispatcher.equals(playMonitorClient.mDispatcherCb)) {
                    synchronized (playMonitorClient) {
                        break;
                    }
                }
            }
        } else {
            playbackActivityMonitor.getClass();
        }
        sUsingAudioLogger.enqueue(new EventLogger.StringEvent("register : " + iPlaybackConfigDispatcher + ", " + str));
    }

    public final void registerPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) {
        if (iPreferredMixerAttributesDispatcher == null) {
            return;
        }
        this.mPrefMixerAttrDispatcher.register(iPreferredMixerAttributesDispatcher);
    }

    public final void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        this.mRecordMonitor.registerRecordingCallback(iRecordingConfigDispatcher, this.mContext.checkCallingPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0);
    }

    public final void registerSpatializerCallback(ISpatializerCallback iSpatializerCallback) {
        Objects.requireNonNull(iSpatializerCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.mStateCallbacks.register(iSpatializerCallback);
        }
    }

    public final void registerSpatializerHeadTrackerAvailableCallback(ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) {
        Objects.requireNonNull(iSpatializerHeadTrackerAvailableCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            try {
                if (z) {
                    spatializerHelper.mHeadTrackerCallbacks.register(iSpatializerHeadTrackerAvailableCallback);
                } else {
                    spatializerHelper.mHeadTrackerCallbacks.unregister(iSpatializerHeadTrackerAvailableCallback);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) {
        registerSpatializerHeadTrackingCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerHeadTrackingModeCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.mHeadTrackingModeCallbacks.register(iSpatializerHeadTrackingModeCallback);
        }
    }

    public final void registerSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) {
        registerSpatializerOutputCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerOutputCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.mOutputCallbacks.register(iSpatializerOutputCallback);
        }
    }

    public final void registerStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        if (iStrategyNonDefaultDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.mDeviceInventory.mNonDefDevDispatchers.register(iStrategyNonDefaultDevicesDispatcher, Boolean.valueOf(isBluetoothPrividged()));
    }

    public final void registerStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        if (iStrategyPreferredDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.mDeviceInventory.mPrefDevDispatchers.register(iStrategyPreferredDevicesDispatcher, Boolean.valueOf(isBluetoothPrividged()));
    }

    public final void registerStreamAliasingDispatcher(IStreamAliasingDispatcher iStreamAliasingDispatcher, boolean z) {
        registerStreamAliasingDispatcher_enforcePermission();
        Objects.requireNonNull(iStreamAliasingDispatcher);
        if (z) {
            this.mStreamAliasingDispatchers.register(iStreamAliasingDispatcher);
        } else {
            this.mStreamAliasingDispatchers.unregister(iStreamAliasingDispatcher);
        }
    }

    public final void releasePlayer(int i) {
        this.mPlaybackMonitor.releasePlayer(i, Binder.getCallingUid());
    }

    public final void releaseRecorder(int i) {
        RecordingActivityMonitor recordingActivityMonitor = this.mRecordMonitor;
        recordingActivityMonitor.dispatchCallbacks(recordingActivityMonitor.updateSnapshot(3, i, null));
    }

    public final void reloadAudioSettings() {
        readAudioSettings(false);
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
            AudioSystem.setAssistantServicesUids(this.mAssistantUids.stream().mapToInt(new AudioService$$ExternalSyntheticLambda1(2)).toArray());
        }
    }

    public final void removeAssistantServicesUids(int[] iArr) {
        removeAssistantServicesUids_enforcePermission();
        Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            removeAssistantServiceUidsLocked(iArr);
        }
    }

    public final AbsoluteVolumeDeviceInfo removeAudioSystemDeviceOutFromAbsVolumeDevices(int i) {
        Log.d("AS.AudioService", "Removing DeviceType: 0x" + Integer.toHexString(i) + " from mAbsoluteVolumeDeviceInfoMap");
        return (AbsoluteVolumeDeviceInfo) ((ArrayMap) this.mAbsoluteVolumeDeviceInfoMap).remove(Integer.valueOf(i));
    }

    public final boolean removeAudioSystemDeviceOutFromFixedVolumeDevices(int i) {
        Log.d("AS.AudioService", "Removing DeviceType: 0x" + Integer.toHexString(i) + " from mFixedVolumeDevices");
        return ((HashSet) this.mFixedVolumeDevices).remove(Integer.valueOf(i));
    }

    public final boolean removeAudioSystemDeviceOutFromFullVolumeDevices(int i) {
        Log.d("AS.AudioService", "Removing DeviceType: 0x" + Integer.toHexString(i) + " from mFullVolumeDevices");
        return ((HashSet) this.mFullVolumeDevices).remove(Integer.valueOf(i));
    }

    public final int removeDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) {
        removeDeviceAsNonDefaultForStrategy_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        String format = String.format("removeDeviceAsNonDefaultForStrategy strat:%d dev:%s", Integer.valueOf(i), retrieveBluetoothAddress.toString());
        EventLogger eventLogger = sDeviceLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(format);
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        if (retrieveBluetoothAddress.getRole() == 1) {
            Log.e("AS.AudioService", "Unsupported input routing in ".concat(format));
            return -1;
        }
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        audioDeviceInventory.getClass();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(retrieveBluetoothAddress);
            int removeDevicesRole = AudioDeviceInventory.removeDevicesRole(audioDeviceInventory.mAppliedStrategyRoles, new AudioDeviceInventory$$ExternalSyntheticLambda2(audioDeviceInventory, 7), i, arrayList);
            if (create != null) {
                create.close();
            }
            if (removeDevicesRole == 0) {
                audioDeviceInventory.mDeviceBroker.sendILMsgNoDelay(48, i, retrieveBluetoothAddress);
            }
            if (removeDevicesRole != 0 && removeDevicesRole != -2) {
                Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(removeDevicesRole), format));
            }
            return removeDevicesRole;
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

    public final void removeLoudnessCodecInfo(int i, LoudnessCodecInfo loudnessCodecInfo) {
        LoudnessCodecHelper loudnessCodecHelper = this.mLoudnessCodecHelper;
        loudnessCodecHelper.getClass();
        LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new LoudnessCodecHelper.LoudnessTrackId(i, Binder.getCallingPid());
        synchronized (loudnessCodecHelper.mLock) {
            try {
                if (loudnessCodecHelper.mStartedConfigInfo.containsKey(loudnessTrackId) && loudnessCodecHelper.mStartedConfigPiids.containsKey(loudnessTrackId)) {
                    if (!((Set) loudnessCodecHelper.mStartedConfigInfo.get(loudnessTrackId)).remove(loudnessCodecInfo)) {
                        Log.w("AS.LoudnessCodecHelper", "Could not find to remove codecInfo " + loudnessCodecInfo);
                    }
                    return;
                }
                Log.w("AS.LoudnessCodecHelper", "Cannot remove loudness info for stopped config " + loudnessTrackId);
            } finally {
            }
        }
    }

    public final int removeMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) {
        Log.d("AS.AudioService", "removeMixForPolicy for " + iAudioPolicyCallback.asBinder() + " with config:" + audioPolicyConfig);
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot add AudioMix in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (Flags.audioMixOwnership()) {
                    Iterator it = audioPolicyConfig.getMixes().iterator();
                    while (it.hasNext()) {
                        if (!checkUpdateForPolicy.getMixes().contains((AudioMix) it.next())) {
                            Slog.e("AS.AudioService", "removeMixForPolicy attempted to unregister AudioMix(es) not belonging to the AudioPolicy");
                            return -1;
                        }
                    }
                }
                return checkUpdateForPolicy.removeMixes(audioPolicyConfig.getMixes()) == 0 ? 0 : -1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        AudioSystemAdapter audioSystemAdapter = this.mAudioSystem;
        synchronized (audioSystemAdapter.mRegisteredAttributesMap) {
            try {
                if (!audioSystemAdapter.mRegisteredAttributesMap.containsKey(iDevicesForAttributesCallback.asBinder())) {
                    Log.w("AudioSystemAdapter", "listener to be removed is not found.");
                } else {
                    audioSystemAdapter.mRegisteredAttributesMap.remove(iDevicesForAttributesCallback.asBinder());
                    audioSystemAdapter.mDevicesForAttributesCallbacks.unregister(iDevicesForAttributesCallback);
                }
            } finally {
            }
        }
    }

    public final void removePackageForName(String str) {
        enforceModifyAudioRoutingPermission();
        PackageListHelper packageListHelper = this.mPackageListHelper;
        Context context = this.mContext;
        packageListHelper.getClass();
        PackageListHelper.removePackageForName(context, str);
    }

    public final int removePreferredDevicesForStrategy(int i) {
        removePreferredDevicesForStrategy_enforcePermission();
        String format = String.format("removePreferredDevicesForStrategy strat:%d", Integer.valueOf(i));
        EventLogger eventLogger = sDeviceLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(format);
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        audioDeviceInventory.getClass();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            int clearDevicesRoleForStrategy = audioDeviceInventory.clearDevicesRoleForStrategy(i, false);
            if (create != null) {
                create.close();
            }
            if (clearDevicesRoleForStrategy == 0) {
                audioDeviceInventory.mDeviceBroker.sendIMsgNoDelay(33, 2, i);
            }
            if (clearDevicesRoleForStrategy != 0 && clearDevicesRoleForStrategy != -2) {
                Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(clearDevicesRoleForStrategy), format));
            }
            return clearDevicesRoleForStrategy;
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

    public final void removeSpatializerCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) {
        removeSpatializerCompatibleAudioDevice_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        this.mSpatializerHelper.removeCompatibleAudioDevice(audioDeviceAttributes);
    }

    public final int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) {
        Log.d("AS.AudioService", "removeUidDeviceAffinity for " + iAudioPolicyCallback.asBinder() + " uid:" + i);
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot remove device affinity in audio policy");
                int i2 = -1;
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (checkUpdateForPolicy.mUidDeviceAffinities.remove(new Integer(i)) == null || checkUpdateForPolicy.removeUidDeviceAffinitiesFromSystem(i) != 0) {
                    Log.e("AudioPolicyProxy", "AudioSystem. removeUidDeviceAffinities failed");
                } else {
                    i2 = 0;
                }
                return i2;
            } finally {
            }
        }
    }

    public final int removeUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) {
        Log.d("AS.AudioService", "removeUserIdDeviceAffinity for " + iAudioPolicyCallback.asBinder() + " userId:" + i);
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot remove device affinity in audio policy");
                int i2 = -1;
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (checkUpdateForPolicy.mUserIdDeviceAffinities.remove(new Integer(i)) == null || checkUpdateForPolicy.removeUserIdDeviceAffinitiesFromSystem(i) != 0) {
                    Log.e("AudioPolicyProxy", "AudioSystem.removeUserIdDeviceAffinities failed");
                } else {
                    i2 = 0;
                }
                return i2;
            } finally {
            }
        }
    }

    public final int requestAudioFocus(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, String str3, int i2, IAudioPolicyCallback iAudioPolicyCallback, int i3) {
        Bundle bundle;
        int usage;
        if ((i2 & 8) != 0) {
            throw new IllegalArgumentException("Invalid test flag");
        }
        int callingUid = Binder.getCallingUid();
        MediaMetrics.Item item = new MediaMetrics.Item("audio.service.focus").setUid(callingUid).set(MediaMetrics.Property.CALLING_PACKAGE, str2).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "requestAudioFocus").set(MediaMetrics.Property.FLAGS, Integer.valueOf(i2));
        boolean z = false;
        if (audioAttributes != null && !isValidAudioAttributesUsage(audioAttributes)) {
            Log.w("AS.AudioService", "Request using unsupported usage");
            item.set(MediaMetrics.Property.EARLY_RETURN, "Request using unsupported usage").record();
            return 0;
        }
        if ((i2 & 4) == 4) {
            if (!"AudioFocus_For_Phone_Ring_And_Calls".equals(str)) {
                synchronized (this.mAudioPolicies) {
                    try {
                        if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                            Log.e("AS.AudioService", "Invalid unregistered AudioPolicy to (un)lock audio focus");
                            item.set(MediaMetrics.Property.EARLY_RETURN, "Invalid unregistered AudioPolicy to (un)lock audio focus").record();
                            return 0;
                        }
                    } finally {
                    }
                }
            } else if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
                Log.e("AS.AudioService", "Invalid permission to (un)lock audio focus", new Exception());
                item.set(MediaMetrics.Property.EARLY_RETURN, "Invalid permission to (un)lock audio focus").record();
                return 0;
            }
        }
        if (str2 == null || str == null || audioAttributes == null) {
            Log.e("AS.AudioService", "Invalid null parameter to request audio focus");
            item.set(MediaMetrics.Property.EARLY_RETURN, "Invalid null parameter to request audio focus").record();
            return 0;
        }
        boolean z2 = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS_PRIVILEGED") == 0 || this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0 || callingUid < 10000;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        if (!z2) {
            try {
                if (this.mHardeningEnforcer.blockFocusMethod(callingUid, i, str, str2, str3, i3)) {
                    Log.w("AS.AudioService", "Audio focus request blocked by hardening");
                    item.set(MediaMetrics.Property.EARLY_RETURN, "Audio focus request blocked by hardening").record();
                    return 0;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        item.record();
        if (!this.mMultiSoundManager.isRemoteSubmixAppExist()) {
            boolean checkDeviceConnected = this.mDeviceBroker.checkDeviceConnected(67108988);
            this.mScreenSharingHelper.getClass();
            if ("com.android.server.telecom".equals(str2) || (usage = audioAttributes.getUsage()) == 4 || usage == 5 || usage == 6) {
                if (ScreenSharingHelper.sIsWifiDisplayConnected && !checkDeviceConnected && AudioSystem.isStreamActiveRemotely(3, 0)) {
                    ScreenSharingHelper.sSplitSoundEnabled = true;
                } else {
                    ScreenSharingHelper.sSplitSoundEnabled = false;
                }
                SemAudioSystem.setPolicyParameters("l_smart_view_split_sound_enable=" + ScreenSharingHelper.sSplitSoundEnabled);
            }
        }
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        if (audioAttributes.getUsage() == 11 && i == 3 && (bundle = audioAttributes.getBundle()) != null && bundle.getBoolean("a11y_force_ducking")) {
            if (callingUid != 0) {
                synchronized (this.mAccessibilityServiceUidsLock) {
                    try {
                        if (this.mAccessibilityServiceUids != null) {
                            int callingUid2 = Binder.getCallingUid();
                            int i4 = 0;
                            while (true) {
                                int[] iArr = this.mAccessibilityServiceUids;
                                if (i4 >= iArr.length) {
                                    break;
                                }
                                if (iArr[i4] == callingUid2) {
                                    break;
                                }
                                i4++;
                            }
                        }
                    } finally {
                    }
                }
            }
            z = true;
        }
        return mediaFocusControl.requestAudioFocus(audioAttributes, i, iBinder, iAudioFocusDispatcher, str, str2, i2, i3, z, -1);
    }

    public final int requestAudioFocusForTest(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, int i2, int i3, int i4) {
        if (!enforceQueryAudioStateForTest("focus request")) {
            return 0;
        }
        if (str2 != null && str != null && audioAttributes != null) {
            return this.mMediaFocusControl.requestAudioFocus(audioAttributes, i, iBinder, iAudioFocusDispatcher, str, str2, i2, i4, false, i3);
        }
        Log.e("AS.AudioService", "Invalid null parameter to request audio focus");
        return 0;
    }

    public final int rescaleIndex(int i, int i2, int i3) {
        VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
        VolumeStreamState volumeStreamState = volumeStreamStateArr[i2];
        int i4 = volumeStreamState.mIndexMin;
        int i5 = volumeStreamState.mIndexMax;
        VolumeStreamState volumeStreamState2 = volumeStreamStateArr[i3];
        return rescaleIndex(i, i4, i5, volumeStreamState2.mIndexMin, volumeStreamState2.mIndexMax);
    }

    public final int rescaleIndex(VolumeInfo volumeInfo, int i) {
        if (volumeInfo.getVolumeIndex() == -100 || volumeInfo.getMinVolumeIndex() == -100 || volumeInfo.getMaxVolumeIndex() == -100) {
            Log.e("AS.AudioService", "rescaleIndex: volumeInfo has invalid index or range");
            return this.mStreamStates[i].mIndexMin;
        }
        int volumeIndex = volumeInfo.getVolumeIndex();
        int minVolumeIndex = volumeInfo.getMinVolumeIndex();
        int maxVolumeIndex = volumeInfo.getMaxVolumeIndex();
        VolumeStreamState volumeStreamState = this.mStreamStates[i];
        return rescaleIndex(volumeIndex, minVolumeIndex, maxVolumeIndex, volumeStreamState.mIndexMin, volumeStreamState.mIndexMax);
    }

    public final void restoreVolumeGroups() {
        Log.v("AS.AudioService", "restoreVolumeGroups");
        synchronized (this.mSettingsLock) {
            int i = 0;
            while (true) {
                try {
                    SparseArray sparseArray = sVolumeGroupStates;
                    if (i < sparseArray.size()) {
                        ((VolumeGroupState) sparseArray.valueAt(i)).applyAllVolumes(false);
                        i++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final AudioDeviceAttributes retrieveBluetoothAddress(AudioDeviceAttributes audioDeviceAttributes) {
        if (isBluetoothPrividged()) {
            return audioDeviceAttributes;
        }
        retrieveBluetoothAddressUncheked(audioDeviceAttributes);
        return audioDeviceAttributes;
    }

    public final void retrieveBluetoothAddressUncheked(AudioDeviceAttributes audioDeviceAttributes) {
        Objects.requireNonNull(audioDeviceAttributes);
        if (AudioSystem.isBluetoothDevice(audioDeviceAttributes.getInternalType())) {
            String anonymizeBluetoothAddress = Utils.anonymizeBluetoothAddress(audioDeviceAttributes.getAddress());
            Iterator it = ((ArrayList) this.mDeviceBroker.getImmutableDeviceInventory()).iterator();
            while (it.hasNext()) {
                AdiDeviceState adiDeviceState = (AdiDeviceState) it.next();
                if (AudioSystem.isBluetoothDevice(adiDeviceState.getInternalDeviceType()) && audioDeviceAttributes.getInternalType() == adiDeviceState.getInternalDeviceType() && anonymizeBluetoothAddress.equals(Utils.anonymizeBluetoothAddress(adiDeviceState.getDeviceAddress()))) {
                    audioDeviceAttributes.setAddress(adiDeviceState.getDeviceAddress());
                    return;
                }
            }
        }
    }

    public final List retrieveBluetoothAddresses(List list) {
        if (isBluetoothPrividged()) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) it.next();
            if (audioDeviceAttributes != null) {
                retrieveBluetoothAddressUncheked(audioDeviceAttributes);
                arrayList.add(audioDeviceAttributes);
            }
        }
        return arrayList;
    }

    public final int secGetActiveStreamType(int i) {
        return getActiveStreamType(i);
    }

    public final int sendBecomingNoisyIntent(int i, String[] strArr) {
        if (strArr == null || TextUtils.isEmpty(strArr[0])) {
            return 0;
        }
        Intent intent = new Intent("android.media.AUDIO_BECOMING_NOISY");
        for (String str : strArr) {
            intent.setPackage(str);
            sendBroadcastToAll(intent, null);
            Log.d("AS.AudioService", "sendBecomingNoisyIntent to " + str);
        }
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        Log.d("MediaFocusControl", "handleExternalFocusGain, " + i);
        ArrayList focusRequester = mediaFocusControl.mMultiFocusStack.getFocusRequester(i, false);
        if (focusRequester.size() == 0) {
            return 200;
        }
        Iterator it = focusRequester.iterator();
        while (it.hasNext()) {
            ((FocusRequester) it.next()).handleFocusLossFromGain(1, null, true);
        }
        return 200;
    }

    public final void sendBroadcastToAll(Intent intent, Bundle bundle) {
        this.mSystemServer.getClass();
        intent.addFlags(67108864);
        intent.addFlags(268435456);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL, null, bundle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendBroadcastToSoundEventReceiver(int i, int i2, String str) {
        synchronized (this.mEventReceivers) {
            try {
                ArrayList arrayList = this.mEventReceivers;
                if (!arrayList.isEmpty() && i != 0) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        SoundEventReceiver soundEventReceiver = (SoundEventReceiver) it.next();
                        if ((soundEventReceiver.mEventType & i) == i) {
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
            } finally {
            }
        }
    }

    public final void sendEnabledSurroundFormats(ContentResolver contentResolver, boolean z) {
        if (this.mEncodedSurroundMode != 3) {
            return;
        }
        this.mSettings.getClass();
        String string = Settings.Global.getString(contentResolver, "encoded_surround_output_enabled_formats");
        if (string == null) {
            string = "";
        }
        if (z || !TextUtils.equals(string, this.mEnabledSurroundFormats)) {
            this.mEnabledSurroundFormats = string;
            String[] split = TextUtils.split(string, ",");
            ArrayList arrayList = new ArrayList();
            for (String str : split) {
                try {
                    Integer valueOf = Integer.valueOf(str);
                    if (isSurroundFormat(valueOf.intValue()) && !arrayList.contains(valueOf)) {
                        arrayList.add(valueOf);
                    }
                } catch (Exception unused) {
                    StorageManagerService$$ExternalSyntheticOutline0.m("Invalid enabled surround format:", str, "AS.AudioService");
                }
            }
            SettingsAdapter settingsAdapter = this.mSettings;
            ContentResolver contentResolver2 = this.mContext.getContentResolver();
            String join = TextUtils.join(",", arrayList);
            settingsAdapter.getClass();
            Settings.Global.putString(contentResolver2, "encoded_surround_output_enabled_formats", join);
            sendMsg(this.mAudioHandler, 24, 2, 0, 0, arrayList, 0);
        }
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
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "updateSurroundSoundSettings: illegal value ", "AS.AudioService");
            i2 = 16;
        } else {
            i2 = 15;
        }
        if (i2 != 16) {
            this.mDeviceBroker.setForceUse_Async(6, i2, str);
        }
    }

    public final boolean sendFocusLoss(AudioFocusInfo audioFocusInfo, IAudioPolicyCallback iAudioPolicyCallback) {
        FocusRequester focusRequester;
        Objects.requireNonNull(audioFocusInfo);
        Objects.requireNonNull(iAudioPolicyCallback);
        enforceModifyAudioRoutingPermission();
        if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
            throw new IllegalStateException("Only registered AudioPolicy can change focus");
        }
        if (!((AudioPolicyProxy) this.mAudioPolicies.get(iAudioPolicyCallback.asBinder())).mHasFocusListener) {
            throw new IllegalStateException("AudioPolicy must have focus listener to change focus");
        }
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        synchronized (MediaFocusControl.mAudioFocusLock) {
            try {
                Iterator it = mediaFocusControl.mFocusStack.iterator();
                while (true) {
                    focusRequester = null;
                    if (!it.hasNext()) {
                        break;
                    }
                    FocusRequester focusRequester2 = (FocusRequester) it.next();
                    if (focusRequester2.mClientId.equals(audioFocusInfo.getClientId())) {
                        focusRequester2.handleFocusLoss(-1, null, false);
                        focusRequester = focusRequester2;
                        break;
                    }
                }
                if (focusRequester == null) {
                    return false;
                }
                mediaFocusControl.mFocusStack.remove(focusRequester);
                focusRequester.release();
                return true;
            } finally {
            }
        }
    }

    public final void sendVolumeChangedIntent(int i, int i2, int i3, int i4) {
        int indexDividedBy10 = getIndexDividedBy10(i2, i);
        int indexDividedBy102 = getIndexDividedBy10(i3, i);
        Intent intent = new Intent("android.media.VOLUME_CHANGED_ACTION");
        intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", i);
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setDeliveryGroupPolicy(1);
        makeBasic.setDeliveryGroupMatchingKey("android.media.STREAM_DEVICES_CHANGED_ACTION", String.valueOf(i));
        if (isStreamMute(i)) {
            intent.putExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
        } else {
            intent.putExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", indexDividedBy102);
        }
        intent.putExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", indexDividedBy10);
        intent.putExtra("android.media.EXTRA_VOLUME_SHOW_UI", (i4 & 1) != 0);
        sendBroadcastToAll(intent, makeBasic.toBundle());
    }

    public final void sendVolumeChangedIntent(int i, int i2, int i3, int i4, int i5) {
        if ((i4 & 32) != 0) {
            return;
        }
        if (i2 == i3 && this.mLastVolumeChangedIntentDevice == i5) {
            DualA2dpVolumeManager dualA2dpVolumeManager = this.mDeviceBroker.mDualA2dpManager;
            if (!dualA2dpVolumeManager.mDualModeEnabled || !dualA2dpVolumeManager.needVolumeChangedIntent) {
                return;
            }
        }
        this.mLastVolumeChangedIntentDevice = i5;
        sendVolumeChangedIntent(i, i2, i3, i4);
    }

    public final void sendVolumeUpdate(int i, int i2, int i3, int i4, int i5) {
        int i6 = mStreamVolumeAlias[i];
        if (i6 == 3 && isFullVolumeDevice(i5)) {
            i4 &= -2;
        }
        int updateFlagsForSamsungVolume = updateFlagsForSamsungVolume(i6, i4, i3, i5);
        this.mVolumeController.postVolumeChanged(i6, updateFlagsForSamsungVolume);
        sendVolumeChangedIntent(i6, i2, i3, updateFlagsForSamsungVolume, i5);
    }

    public final void setA2dpDeviceVolume(BluetoothDevice bluetoothDevice, int i, int i2, int i3, String str) {
        int i4;
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        if (bluetoothDevice == null) {
            throw new IllegalArgumentException("Invalid device device is null");
        }
        if (i == 3) {
            if (!this.mVolumeLimitOn || i2 <= getVolumeLimitValue() * 10 || !this.mSoundDoseHelper.safeDevicesContains(128)) {
                if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3 && bluetoothDevice.semGetAudioType() == 2 && this.mSoundDoseHelper.checkSafeMediaVolume(i, i2, 128)) {
                    this.mVolumeController.postDisplaySafeVolumeWarning(i3);
                    return;
                }
                DualA2dpVolumeManager dualA2dpVolumeManager = this.mDeviceBroker.mDualA2dpManager;
                synchronized (dualA2dpVolumeManager.mConnectedDevicesVolume) {
                    try {
                        dualA2dpVolumeManager.mVolumeLogger.enqueue(new EventLogger.StringEvent("setVolume:" + AudioManagerHelper.getAddressForLog(bluetoothDevice) + ",idx:" + i2));
                        if (dualA2dpVolumeManager.mConnectedDevicesVolume.containsKey(bluetoothDevice)) {
                            dualA2dpVolumeManager.needVolumeChangedIntent = dualA2dpVolumeManager.setVolume(bluetoothDevice, i2);
                            dualA2dpVolumeManager.updateMainVolume(i2);
                        } else {
                            dualA2dpVolumeManager.postSaveVolumeDB(dualA2dpVolumeManager.getValidIndex(i2), bluetoothDevice.getAddress());
                        }
                    } finally {
                    }
                }
                int mainVolume = this.mDeviceBroker.mDualA2dpManager.getMainVolume();
                if (mainVolume == -1) {
                    mainVolume = i2;
                }
                i4 = mainVolume;
                sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i4, i3, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ".setA2dpDeviceVolume")));
                setStreamVolumeWithAttribution(i, i4, i3, null, str, str + ".setA2dpDeviceVolume", null, Binder.getCallingUid(), true, true, 128);
            }
            Log.i("AS.AudioService", "Over the volume limit value");
        }
        i4 = i2;
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i4, i3, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ".setA2dpDeviceVolume")));
        setStreamVolumeWithAttribution(i, i4, i3, null, str, str + ".setA2dpDeviceVolume", null, Binder.getCallingUid(), true, true, 128);
    }

    public final void setA2dpSuspended(boolean z) {
        setA2dpSuspended_enforcePermission();
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("setA2dpSuspended(", ") from u/pid:", z);
        m.append(Binder.getCallingUid());
        m.append("/");
        m.append(Binder.getCallingPid());
        this.mDeviceBroker.setA2dpSuspended(m.toString(), z, false);
    }

    public final void setActiveAssistantServiceUids(int[] iArr) {
        setActiveAssistantServiceUids_enforcePermission();
        Objects.requireNonNull(iArr);
        synchronized (this.mSettingsLock) {
            this.mActiveAssistantServiceUids = iArr;
        }
        updateActiveAssistantServiceUids();
    }

    public final boolean setAdditionalOutputDeviceDelay(AudioDeviceAttributes audioDeviceAttributes, long j) {
        int parameters;
        Objects.requireNonNull(audioDeviceAttributes, "device must not be null");
        enforceModifyAudioRoutingPermission();
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        String str = "additional_output_device_delay=" + retrieveBluetoothAddress.getInternalType() + "," + retrieveBluetoothAddress.getAddress();
        final String str2 = str + "," + j;
        RestorableParameters restorableParameters = this.mRestorableParameters;
        restorableParameters.getClass();
        Objects.requireNonNull(str, "id must not be null");
        Objects.requireNonNull(str2, "parameter must not be null");
        synchronized (restorableParameters.mMap) {
            try {
                parameters = AudioSystem.setParameters(str2);
                if (parameters == 0) {
                    BooleanSupplier booleanSupplier = new BooleanSupplier() { // from class: com.android.server.audio.AudioService$RestorableParameters$$ExternalSyntheticLambda0
                        @Override // java.util.function.BooleanSupplier
                        public final boolean getAsBoolean() {
                            return AudioSystem.setParameters(str2) != 0;
                        }
                    };
                    synchronized (restorableParameters.mMap) {
                        ((HashMap) restorableParameters.mMap).put(str, booleanSupplier);
                    }
                }
            } finally {
            }
        }
        return parameters == 0;
    }

    public final int setAllowedCapturePolicy(int i) {
        int allowedCapturePolicy;
        int callingUid = Binder.getCallingUid();
        int capturePolicyToFlags = AudioAttributes.capturePolicyToFlags(i, 0);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mPlaybackMonitor) {
                try {
                    this.mAudioSystem.getClass();
                    allowedCapturePolicy = AudioSystem.setAllowedCapturePolicy(callingUid, capturePolicyToFlags);
                    if (allowedCapturePolicy == 0) {
                        this.mPlaybackMonitor.setAllowedCapturePolicy(callingUid, i);
                    }
                } finally {
                }
            }
            return allowedCapturePolicy;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setAppDevice(final int i, final int i2, boolean z) {
        enforceModifyAudioRoutingPermission();
        String[] packageName = getPackageName(i);
        if (TextUtils.isEmpty(packageName[0])) {
            if (((HashSet) mAppCastingDevice).contains(Integer.valueOf(i2))) {
                this.mScreenSharingHelper.updateAppCasting(-1002);
                final int i3 = 0;
                this.mAudioHandler.post(new Runnable(this) { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda8
                    public final /* synthetic */ AudioService f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                AudioService audioService = this.f$0;
                                int i4 = i;
                                int i5 = i2;
                                int i6 = AudioService.BECOMING_NOISY_DELAY_MS;
                                audioService.getClass();
                                EventLogger eventLogger = AudioService.sAppCastingLogger;
                                StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i4, i5, "uid:", ", device:", ", package:");
                                m.append(audioService.mContext.getOpPackageName());
                                EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(m.toString());
                                stringEvent.printLog(0, "AS.AudioService");
                                eventLogger.enqueue(stringEvent);
                                audioService.mMultiSoundManager.setAppToRemoteSubmix(i4, -1002);
                                audioService.mAudioSystem.onRoutingUpdated();
                                audioService.observeDevicesForMediaStream();
                                break;
                            default:
                                AudioService audioService2 = this.f$0;
                                int i7 = i;
                                int i8 = i2;
                                int i9 = AudioService.BECOMING_NOISY_DELAY_MS;
                                audioService2.getClass();
                                EventLogger eventLogger2 = AudioService.sAppCastingLogger;
                                StringBuilder m2 = ArrayUtils$$ExternalSyntheticOutline0.m(i7, i8, "uid:", ", device:", ", package:");
                                m2.append(audioService2.mContext.getOpPackageName());
                                EventLogger.StringEvent stringEvent2 = new EventLogger.StringEvent(m2.toString());
                                stringEvent2.printLog(0, "AS.AudioService");
                                eventLogger2.enqueue(stringEvent2);
                                audioService2.mMultiSoundManager.setAppToRemoteSubmix(i7, i8);
                                audioService2.mAudioSystem.onRoutingUpdated();
                                audioService2.observeDevicesForMediaStream();
                                break;
                        }
                    }
                });
                Log.e("AS.AudioService", "invalid uid: " + i + ", deviceType: " + i2);
                return;
            }
        }
        if (((HashSet) mAppCastingDevice).contains(Integer.valueOf(i2))) {
            this.mScreenSharingHelper.updateAppCasting(i2);
            final int i4 = 1;
            this.mAudioHandler.post(new Runnable(this) { // from class: com.android.server.audio.AudioService$$ExternalSyntheticLambda8
                public final /* synthetic */ AudioService f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i4) {
                        case 0:
                            AudioService audioService = this.f$0;
                            int i42 = i;
                            int i5 = i2;
                            int i6 = AudioService.BECOMING_NOISY_DELAY_MS;
                            audioService.getClass();
                            EventLogger eventLogger = AudioService.sAppCastingLogger;
                            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i42, i5, "uid:", ", device:", ", package:");
                            m.append(audioService.mContext.getOpPackageName());
                            EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(m.toString());
                            stringEvent.printLog(0, "AS.AudioService");
                            eventLogger.enqueue(stringEvent);
                            audioService.mMultiSoundManager.setAppToRemoteSubmix(i42, -1002);
                            audioService.mAudioSystem.onRoutingUpdated();
                            audioService.observeDevicesForMediaStream();
                            break;
                        default:
                            AudioService audioService2 = this.f$0;
                            int i7 = i;
                            int i8 = i2;
                            int i9 = AudioService.BECOMING_NOISY_DELAY_MS;
                            audioService2.getClass();
                            EventLogger eventLogger2 = AudioService.sAppCastingLogger;
                            StringBuilder m2 = ArrayUtils$$ExternalSyntheticOutline0.m(i7, i8, "uid:", ", device:", ", package:");
                            m2.append(audioService2.mContext.getOpPackageName());
                            EventLogger.StringEvent stringEvent2 = new EventLogger.StringEvent(m2.toString());
                            stringEvent2.printLog(0, "AS.AudioService");
                            eventLogger2.enqueue(stringEvent2);
                            audioService2.mMultiSoundManager.setAppToRemoteSubmix(i7, i8);
                            audioService2.mAudioSystem.onRoutingUpdated();
                            audioService2.observeDevicesForMediaStream();
                            break;
                    }
                }
            });
        } else {
            int convertDeviceTypeToInternalDevice = AudioDeviceInfo.convertDeviceTypeToInternalDevice(i2);
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, convertDeviceTypeToInternalDevice, "setAppDevice, uid:", ", device:", "AS.AudioService");
            sendMsg(this.mAudioHandler, z ? 2764 : 2774, 2, i, convertDeviceTypeToInternalDevice, packageName[0], convertDeviceTypeToInternalDevice == 0 ? sendBecomingNoisyIntent(i, packageName) : 0);
        }
    }

    public final void setAppMute(int i, boolean z, String str) {
        if (TextUtils.isEmpty(getPackageName(i)[0])) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad uid "));
        }
        checkModifyPhoneStateOrRoutingPermission();
        EventLogger eventLogger = sAppVolumeLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("uid:" + i + ",shouldMute:" + z + ",package:" + str);
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        MultiSoundManager multiSoundManager = this.mMultiSoundManager;
        synchronized (multiSoundManager.mMultiSoundLock) {
            try {
                MultiSoundManager.MultiSoundItem multiSoundItem = (MultiSoundManager.MultiSoundItem) multiSoundManager.mPinAppInfoList.get(Integer.valueOf(i));
                if (multiSoundItem == null) {
                    multiSoundItem = new MultiSoundManager.MultiSoundItem(i, 0, 100);
                }
                multiSoundItem.mShouldMute = z;
                if (multiSoundItem.removable()) {
                    multiSoundManager.mPinAppInfoList.remove(Integer.valueOf(i));
                } else {
                    multiSoundManager.mPinAppInfoList.put(Integer.valueOf(i), multiSoundItem);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        multiSoundManager.setAppVolumeToNative(i);
    }

    public final void setAppVolume(int i, int i2, String str) {
        if (TextUtils.isEmpty(getPackageName(i)[0])) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad uid "));
        }
        checkModifyPhoneStateOrRoutingPermission();
        EventLogger eventLogger = sAppVolumeLogger;
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "uid:", ",volume:", ",package:");
        m.append(str);
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(m.toString());
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        this.mMultiSoundManager.setAppVolume(i, i2);
        if ("com.samsung.android.soundassistant".equals(str)) {
            this.mAppVolumeFromSoundAssistant.put(i, i2);
        }
    }

    /* JADX WARN: Type inference failed for: r0v74 */
    /* JADX WARN: Type inference failed for: r0v75, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r0v78 */
    public final void setAudioServiceConfig(String str) {
        String str2;
        float f;
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
        if (audioParameter.hasKey("l_volume_limit_key")) {
            String str10 = audioParameter.get("enable");
            if (str10 != null) {
                this.mVolumeLimitOn = "true".equals(str10);
            } else {
                String str11 = audioParameter.get("level");
                if (str11 != null) {
                    this.mVolumeLimitValue = Integer.parseInt(str11);
                    String str12 = audioParameter.get("package");
                    if (this.mVolumeLimitOn) {
                        setVolumeLevelToLimit(str12);
                    }
                }
            }
        } else {
            String str13 = audioParameter.get("g_call_extra_volume_enable");
            if (str13 != null) {
                this.mExt.mExtraVolume = "true".equals(str13);
            } else {
                if (audioParameter.hasKey("sound_assistant")) {
                    checkModifyPhoneStateOrRoutingPermission();
                    String str14 = audioParameter.get("adjust_media_volume_only");
                    if (str14 != null) {
                        this.mExt.getClass();
                        int intValueFromString = AudioServiceExt.getIntValueFromString(0, str14);
                        this.mAdjustMediaVolumeOnly = intValueFromString == 1;
                        this.mExt.setSystemSettingForSoundAssistant(intValueFromString, "adjust_media_volume_only");
                        return;
                    }
                    String str15 = audioParameter.get("mute_media_by_vibrate_or_silent_mode");
                    if (str15 != null) {
                        this.mExt.getClass();
                        ?? r0 = AudioServiceExt.getIntValueFromString(0, str15) == 1 ? 1 : 0;
                        this.mMuteMediaByVibrateOrSilentMode = r0;
                        AudioSettingsHelper audioSettingsHelper = this.mSettingHelper;
                        audioSettingsHelper.getClass();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("_key", "mute_media_by_vibrate_or_silent_mode");
                        contentValues.put("_value", Integer.valueOf((int) r0));
                        audioSettingsHelper.set(contentValues, "audio_settings", "_key='mute_media_by_vibrate_or_silent_mode'");
                        muteMediaStreamOfSpeaker(this.mMuteMediaByVibrateOrSilentMode && this.mRingerMode != 2);
                        return;
                    }
                    String str16 = audioParameter.get("remove_app_volume");
                    if (str16 != null) {
                        this.mExt.getClass();
                        int intValueFromString2 = AudioServiceExt.getIntValueFromString(-1, str16);
                        if (intValueFromString2 == -1 || "".equals(getPackageName(intValueFromString2)[0])) {
                            Log.w("AS.AudioService", "Invalid uid from SoundAssistant");
                            return;
                        } else {
                            this.mAppVolumeFromSoundAssistant.delete(intValueFromString2);
                            this.mMultiSoundManager.setAppVolume(intValueFromString2, 100);
                            return;
                        }
                    }
                    String str17 = audioParameter.get("sound_balance");
                    if (str17 != null) {
                        this.mExt.getClass();
                        int intValueFromString3 = AudioServiceExt.getIntValueFromString(50, str17);
                        if (intValueFromString3 < 0 || intValueFromString3 > 100) {
                            throw new IllegalArgumentException("Invalid balance");
                        }
                        this.mExt.setSystemSettingForSoundAssistant(intValueFromString3, "sound_balance");
                        return;
                    }
                    String str18 = audioParameter.get("mono_sound");
                    if (str18 != null) {
                        this.mExt.getClass();
                        int intValueFromString4 = AudioServiceExt.getIntValueFromString(0, str18);
                        if (intValueFromString4 != 0 && intValueFromString4 != 1) {
                            throw new IllegalArgumentException("Invalid balance");
                        }
                        this.mExt.setSystemSettingForSoundAssistant(intValueFromString4, "mono_audio_db");
                        return;
                    }
                    String str19 = audioParameter.get("ignore_audio_focus");
                    if (str19 != null) {
                        String str20 = audioParameter.get("uid_for_soundassistant");
                        this.mExt.getClass();
                        int intValueFromString5 = AudioServiceExt.getIntValueFromString(-1, str20);
                        if (intValueFromString5 == -1) {
                            Log.e("AS.AudioService", "invalid arguments");
                            return;
                        }
                        String str21 = getPackageName(intValueFromString5)[0];
                        if (TextUtils.isEmpty(str21)) {
                            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(intValueFromString5, "Invalid uid for ignoring audiofocus. uid : ", "AS.AudioService");
                            return;
                        }
                        this.mExt.getClass();
                        int intValueFromString6 = AudioServiceExt.getIntValueFromString(0, str19);
                        FlashNotificationsController$$ExternalSyntheticOutline0.m("AS.AudioService", StorageManagerService$$ExternalSyntheticOutline0.m(intValueFromString5, "Set ignore audiofocus : ", str21, ", uid : ", ", enabled:"), intValueFromString6 == 1);
                        this.mSettingHelper.setIntValue(intValueFromString6 == 1 ? intValueFromString5 : -1, "ignore_audio_focus");
                        this.mMediaFocusControl.setIgnoreAudioFocus(intValueFromString5, intValueFromString6 == 1);
                        return;
                    }
                    String str22 = audioParameter.get("media_volume_step_index");
                    if (str22 != null) {
                        this.mExt.getClass();
                        int intValueFromString7 = AudioServiceExt.getIntValueFromString(10, str22);
                        this.mMediaVolumeStepIndex = intValueFromString7;
                        this.mSettingHelper.setIntValue(intValueFromString7, "media_volume_step_index");
                        setMediaVolumeSteps(null);
                        return;
                    }
                    String str23 = audioParameter.get("ring_through_headset");
                    if (str23 != null) {
                        this.mExt.getClass();
                        int intValueFromString8 = AudioServiceExt.getIntValueFromString(0, str23);
                        this.mHeadsetOnlyStream = intValueFromString8;
                        this.mSettingHelper.setIntValue(intValueFromString8, "ring_through_headset");
                        setHeadsetOnlyStream(this.mHeadsetOnlyStream);
                        return;
                    }
                    String str24 = audioParameter.get("sound_lr_switch");
                    if (str24 != null) {
                        this.mExt.getClass();
                        int intValueFromString9 = AudioServiceExt.getIntValueFromString(0, str24);
                        this.mLRSwitching = intValueFromString9;
                        this.mSettingHelper.setIntValue(intValueFromString9, "sound_lr_switch");
                        if (intValueFromString9 == 1) {
                            this.mAudioSystem.setParameters("l_sound_assistant_lr_switch_enable=true");
                            return;
                        } else {
                            this.mAudioSystem.setParameters("l_sound_assistant_lr_switch_enable=false");
                            return;
                        }
                    }
                    String str25 = audioParameter.get("volumestar_enable");
                    if (str25 != null) {
                        this.mExt.getClass();
                        this.mVolumeController.setA11yMode(AudioServiceExt.getIntValueFromString(0, str25) != 1 ? 101 : 100);
                        return;
                    }
                    String str26 = audioParameter.get("ignore_ducking");
                    if (str26 != null) {
                        this.mExt.getClass();
                        this.mIgnoreDucking = AudioServiceExt.getIntValueFromString(0, str26) == 1;
                        return;
                    }
                    return;
                }
                String str27 = audioParameter.get("g_call_sar_backoff_enable");
                if (str27 != null) {
                    checkModifyPhoneStateOrRoutingPermission();
                    Log.d("AS.AudioService", "g_call_sar_backoff_enable=".concat(str27));
                    this.mDeviceBroker.mSarBackoffParam = "true".equalsIgnoreCase(str27);
                    this.mDeviceBroker.sendIILMsg(2763, 0, 0, 0, null, 500);
                    return;
                }
                if (Rune.SEC_AUDIO_FM_RADIO && (str9 = audioParameter.get("g_fmradio_enable")) != null) {
                    this.mAudioSystem.setParameters(str);
                    if (this.mSystemReady) {
                        this.mAudioSystem.invalidateRoutingCache();
                        observeDevicesForMediaStream();
                        if ("true".equals(str9)) {
                            DualAppManagerService$$ExternalSyntheticOutline0.m("init fm radio volume when turning on fm radio, ", getPackageName(Binder.getCallingUid())[0], "AS.AudioService");
                            int fineVolume = getFineVolume(3, 0);
                            sendVolumeChangedIntent(3, fineVolume, fineVolume, 0);
                            return;
                        }
                        return;
                    }
                    return;
                }
                String str28 = audioParameter.get("g_call_sim_slot");
                if (str28 != null) {
                    checkModifyPhoneStateOrRoutingPermission();
                    if ("0x10".equals(str28)) {
                        this.mPhoneType = "0x01".equals(this.mPhoneType) ? "0x02" : "0x01";
                    } else {
                        this.mPhoneType = str28;
                    }
                } else if (audioParameter.get("g_sco_rvc_support") != null) {
                    checkModifyPhoneStateOrRoutingPermission();
                    SemAudioSystem.setPolicyParameters(str);
                } else {
                    String str29 = audioParameter.get("g_call_memo_state");
                    if (str29 != null) {
                        checkModifyPhoneStateOrRoutingPermission();
                        this.mCallMemoState = str29;
                    } else {
                        String str30 = audioParameter.get("g_call_forwarding_enable");
                        if (str30 != null) {
                            checkModifyPhoneStateOrRoutingPermission();
                            this.mCallForwardingEnable = str30;
                        } else {
                            String str31 = audioParameter.get("g_effect_dv_adapt_sound");
                            if (str31 != null) {
                                checkModifyPhoneStateOrRoutingPermission();
                                this.mDvDhaparam = str31;
                            } else {
                                String str32 = audioParameter.get("HACSetting");
                                if (str32 != null) {
                                    this.mHAC = str32;
                                } else if (audioParameter.hasKey("l_voice_tx_control_mode") && audioParameter.hasKey("l_voice_rx_control_mode") && audioParameter.hasKey("l_call_translation_mode")) {
                                    this.mExt.mVoiceTxControlMode = Integer.parseInt(audioParameter.get("l_voice_tx_control_mode"));
                                    this.mExt.mVoiceRxControlMode = Integer.parseInt(audioParameter.get("l_voice_rx_control_mode"));
                                    String str33 = audioParameter.get("l_call_translation_mode");
                                    this.mExt.mCallTranslationMode = "on".equalsIgnoreCase(str33);
                                    this.mMicModeManager.updateState(16, "on".equalsIgnoreCase(str33));
                                } else if (audioParameter.hasKey("l_voice_tx_control_mode")) {
                                    this.mExt.mVoiceTxControlMode = Integer.parseInt(audioParameter.get("l_voice_tx_control_mode"));
                                } else if (audioParameter.hasKey("l_voice_rx_control_mode")) {
                                    this.mExt.mVoiceRxControlMode = Integer.parseInt(audioParameter.get("l_voice_rx_control_mode"));
                                } else {
                                    if (audioParameter.hasKey("l_volume_prevent_overheat_key")) {
                                        try {
                                            int parseInt = Integer.parseInt(audioParameter.get("uid"));
                                            if (parseInt == -1 || (str2 = audioParameter.get(LauncherConfigurationInternal.KEY_STATE_BOOLEAN)) == null) {
                                                return;
                                            }
                                            boolean equals = "true".equals(str2);
                                            MultiSoundManager.PreventOverheatState preventOverheatState = this.mMultiSoundManager.mPreventOverheatState;
                                            int i = preventOverheatState.mUid;
                                            if (i == -1 || i == parseInt) {
                                                i = -1;
                                            }
                                            preventOverheatState.mUid = parseInt;
                                            preventOverheatState.mState = equals;
                                            if (i != -1) {
                                                MultiSoundManager.this.setAppVolumeToNative(i);
                                            }
                                            MultiSoundManager.PreventOverheatState preventOverheatState2 = this.mMultiSoundManager.mPreventOverheatState;
                                            preventOverheatState2.getClass();
                                            try {
                                                f = Float.parseFloat(SemAudioSystem.getPolicyParameters("l_volume_prevent_overheat_key;gain"));
                                            } catch (NullPointerException | NumberFormatException unused) {
                                                f = 1.0f;
                                            }
                                            preventOverheatState2.mLimitedVolumeForOverheat = f;
                                            MultiSoundManager.this.setAppVolumeToNative(preventOverheatState2.mUid);
                                            return;
                                        } catch (NumberFormatException e) {
                                            Log.e("AS.AudioService", "NumberFormatException", e);
                                            return;
                                        }
                                    }
                                    if (audioParameter.hasKey("sound_effect_volume")) {
                                        checkModifyPhoneStateOrRoutingPermission();
                                        AudioFxHelper.setSoundEffectVolume();
                                    } else if (audioParameter.get("g_ptt_mode") != null) {
                                        SemAudioSystem.setPolicyParameters(str);
                                    } else {
                                        String str34 = audioParameter.get("g_ptt_call_volume_enable");
                                        if (str34 != null) {
                                            this.mExt.mIsPttCallVolumeEnabled = "true".equals(str34);
                                            FlashNotificationsController$$ExternalSyntheticOutline0.m("AS.AudioService", new StringBuilder("mIsPttCallVolumeEnabled : "), this.mExt.mIsPttCallVolumeEnabled);
                                            return;
                                        }
                                        if (Rune.SEC_AUDIO_VIDEO_CALL_VOICE_EFFECT && audioParameter.hasKey("l_mic_input_control_mode")) {
                                            this.mExt.mVideoCallVoiceEffectMode = audioParameter.getInt("l_mic_input_control_mode", 0);
                                        } else if (Rune.SEC_AUDIO_VIDEO_CALL_VOICE_DEFAULT_EFFECT && audioParameter.hasKey("l_mic_input_control_mode")) {
                                            this.mExt.mVideoCallVoiceEffectMode = audioParameter.getInt("l_mic_input_control_mode", 100);
                                        } else {
                                            boolean z = Rune.SEC_AUDIO_CALL_MONSTER_SOUND;
                                            if (z && audioParameter.hasKey("l_call_nc_booster_enable")) {
                                                this.mExt.mVoiceCallMonsterSoundMode = audioParameter.getInt("l_call_nc_booster_enable", 0);
                                            } else if (z && audioParameter.hasKey("l_mic_input_control_mode_2mic")) {
                                                this.mExt.mVideoCallMonsterSoundMode = audioParameter.getInt("l_mic_input_control_mode_2mic", 0);
                                            } else {
                                                if (Rune.SEC_AUDIO_VOLUME_MONITOR && (str8 = audioParameter.get("g_volume_monitor_warning_level")) != null) {
                                                    try {
                                                        checkModifyPhoneStateOrRoutingPermission();
                                                        EventLogger eventLogger = sVolumeLogger;
                                                        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("CSD warning ".concat(str8));
                                                        stringEvent.printLog(0, "AS.AudioService");
                                                        eventLogger.enqueue(stringEvent);
                                                        throw null;
                                                    } catch (SecurityException unused2) {
                                                        return;
                                                    }
                                                }
                                                if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3 && audioParameter.hasKey("l_set_safe_media_volume")) {
                                                    this.mSoundDoseHelper.enforceSafeMediaVolume("safeMediaVolumeFromVolumeMonitor");
                                                    return;
                                                }
                                                if (Rune.SEC_AUDIO_SCREEN_CALL && (str7 = audioParameter.get("l_screen_call")) != null) {
                                                    this.mExt.mScreenCall = "on".equals(str7);
                                                    SemAudioSystem.setPolicyParameters(str);
                                                } else if (Rune.SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER && (str6 = audioParameter.get("l_call_voip_extra_volume_enable")) != null) {
                                                    this.mExt.mVoipExtraVolume = "true".equals(str6);
                                                } else if (!Rune.SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING || (str5 = audioParameter.get("l_call_voip_extra_volume_enable")) == null) {
                                                    String str35 = audioParameter.get("g_effect_headtracker_available");
                                                    if (str35 != null) {
                                                        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
                                                        boolean equals2 = "true".equals(str35);
                                                        synchronized (spatializerHelper) {
                                                            try {
                                                                spatializerHelper.mSecHeadTrackerAvailable = equals2;
                                                                if (!equals2 && !spatializerHelper.mHeadTrackerAvailable) {
                                                                    r6 = false;
                                                                }
                                                                Log.i("AS.SpatializerHelper", "setSecHeadTrackerAvailable:" + spatializerHelper.mSecHeadTrackerAvailable + " mHeadTrackerAvailable:" + spatializerHelper.mHeadTrackerAvailable);
                                                                if (spatializerHelper.mGlobalHeadTrackerAvailable != r6) {
                                                                    spatializerHelper.mGlobalHeadTrackerAvailable = r6;
                                                                    spatializerHelper.dispatchHeadTrackerAvailable(r6);
                                                                }
                                                            } catch (Throwable th) {
                                                                throw th;
                                                            }
                                                        }
                                                    } else {
                                                        boolean z2 = Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI;
                                                        if (!z2 || (str4 = audioParameter.get("l_call_translation_mode")) == null) {
                                                            String str36 = audioParameter.get("g_call_state");
                                                            if (str36 != null) {
                                                                int parseInt2 = Integer.parseInt(str36);
                                                                int i2 = parseInt2 & 32;
                                                                boolean z3 = i2 != 0;
                                                                if (z2) {
                                                                    this.mMicModeManager.updateState(1, z3);
                                                                    if (this.mMicModeManager.mMicModeType.getType() == MicModeType.TYPE_2MIC_VOICE.getType() || this.mMicModeManager.mMicModeType.getType() == MicModeType.TYPE_VOICE.getType()) {
                                                                        int i3 = 33554432 & parseInt2;
                                                                        if (i3 != 0 || (parseInt2 & 1024) != 0) {
                                                                            this.mMicModeManager.updateState(2, true);
                                                                        } else if ((parseInt2 & 16) != 0 || ((i2 != 0 && i3 == 0) || (parseInt2 & 256) != 0 || (parseInt2 & 512) != 0)) {
                                                                            this.mMicModeManager.updateState(2, false);
                                                                        }
                                                                    }
                                                                }
                                                                if (Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE) {
                                                                    LiveTranslatorManager liveTranslatorManager = this.mLiveTranslatorManager;
                                                                    liveTranslatorManager.mStates = z3 ? liveTranslatorManager.mStates | 1 : liveTranslatorManager.mStates & (-2);
                                                                    liveTranslatorManager.updateVoipTranslator();
                                                                }
                                                            } else if (!Rune.SEC_AUDIO_VOIP_LIVE_TRANSLATE || (str3 = audioParameter.get("g_call_voip_package_name")) == null) {
                                                                String str37 = audioParameter.get("g_call_satellite_enable");
                                                                if (str37 != null) {
                                                                    if ("true".equals(str37)) {
                                                                        this.mMicModeManager.updateState(64, true);
                                                                    } else if ("false".equals(str37)) {
                                                                        this.mMicModeManager.updateState(64, false);
                                                                    }
                                                                }
                                                            } else {
                                                                LiveTranslatorManager liveTranslatorManager2 = this.mLiveTranslatorManager;
                                                                liveTranslatorManager2.getClass();
                                                                LiveTranslatorManager.sVoipLiveTranslateLogger.enqueueAndLog(0, "update connection service package to " + str3 + " from " + liveTranslatorManager2.mPackageName, "LiveTranslatorManager");
                                                                liveTranslatorManager2.mVoipPackageName = str3;
                                                                liveTranslatorManager2.updateVoipTranslator();
                                                            }
                                                        } else {
                                                            this.mMicModeManager.updateState(16, "on".equalsIgnoreCase(str4));
                                                        }
                                                    }
                                                } else {
                                                    this.mExt.mVoipAntiHowling = "true".equals(str5);
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
                StringBuilder sb = new StringBuilder("on (");
                sb.append(simpleDateFormat.format(new Date(System.currentTimeMillis())));
                sb.append(" uid/pid = ");
                sb.append(Binder.getCallingUid());
                sb.append("/");
                sb.append(Binder.getCallingPid());
                String m = BootReceiver$$ExternalSyntheticOutline0.m(sb, " : ", str, ")");
                this.mLoopbackState = m;
                this.mLastLoopbackOn = m;
            } else if (str.contains("factory_test_loopback=off")) {
                StringBuilder sb2 = new StringBuilder("off (");
                sb2.append(simpleDateFormat.format(new Date(System.currentTimeMillis())));
                sb2.append(" uid/pid = ");
                sb2.append(Binder.getCallingUid());
                sb2.append("/");
                sb2.append(Binder.getCallingPid());
                this.mLoopbackState = BootReceiver$$ExternalSyntheticOutline0.m(sb2, " : ", str, ")");
            }
        }
        if (str.contains("g_sco_samplerate")) {
            this.mBtScoDeviceInfo = str;
        }
        if (!TextUtils.isEmpty(str)) {
            AudioParameter audioParameter2 = new AudioParameter(str);
            if ((Rune.SEC_AUDIO_SCREEN_CALL && audioParameter2.hasKey("l_screen_call")) || audioParameter2.hasKey("l_call_translation_mode")) {
                this.mAudioSystem.getClass();
                AudioSystem.setParameters(str);
                return;
            }
        }
        this.mAudioSystem.setParameters(str);
    }

    public final void setBluetoothA2dpOn(boolean z) {
        if (checkAudioSettingsPermission("setBluetoothA2dpOn()")) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            StringBuilder m = AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "setBluetoothA2dpOn(", ") from u/pid:", "/", z);
            m.append(callingPid);
            String sb = m.toString();
            new MediaMetrics.Item("audio.device.setBluetoothA2dpOn").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.STATE, z ? "on" : "off").record();
            AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
            audioDeviceBroker.sendLMsgNoDelay(audioDeviceBroker.mBluetoothA2dpEnabled.getAndSet(z) == z ? 60 : 5, 0, sb);
            AudioService audioService = audioDeviceBroker.mAudioService;
            if (!audioService.isForceSpeakerOn() || z) {
                return;
            }
            sendMsg(audioService.mAudioHandler, 8, 2, 1, 1, null, 0);
        }
    }

    public final boolean setBluetoothAudioDeviceCategory(String str, int i) {
        setBluetoothAudioDeviceCategory_enforcePermission();
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return false;
        }
        Objects.requireNonNull(str);
        if (isBluetoothAudioDeviceCategoryFixed(str)) {
            Log.w("AS.AudioService", "Cannot set fixed audio device type for address " + Utils.anonymizeBluetoothAddress(str));
            return false;
        }
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        audioDeviceInventory.addAudioDeviceInInventoryIfNeeded(str, 536870912, true, i, "");
        audioDeviceInventory.addAudioDeviceInInventoryIfNeeded(str, 128, true, i, "");
        return true;
    }

    public final void setBluetoothAudioDeviceCategory_legacy(String str, boolean z, int i) {
        setBluetoothAudioDeviceCategory_legacy_enforcePermission();
        if (android.media.audio.Flags.automaticBtDeviceType()) {
            return;
        }
        Objects.requireNonNull(str);
        AdiDeviceState findBtDeviceStateForAddress = this.mDeviceBroker.mDeviceInventory.findBtDeviceStateForAddress(str, z ? 536870912 : 128);
        int i2 = z ? i == 3 ? 536870912 : 536870913 : 128;
        int i3 = !z ? 8 : i == 3 ? 26 : 27;
        if (findBtDeviceStateForAddress == null) {
            findBtDeviceStateForAddress = new AdiDeviceState(i3, i2, str);
        }
        findBtDeviceStateForAddress.setAudioDeviceCategory(i);
        this.mDeviceBroker.mDeviceInventory.addOrUpdateAudioDeviceCategoryInInventory(findBtDeviceStateForAddress, true);
        this.mDeviceBroker.postPersistAudioDeviceSettings();
        this.mSpatializerHelper.refreshDevice(findBtDeviceStateForAddress.getAudioDeviceAttributes(), false);
        this.mSoundDoseHelper.setAudioDeviceCategory(i2, str, i == 3);
    }

    public final void setBluetoothScoOn(boolean z) {
        boolean isBluetoothScoSupported;
        if (checkAudioSettingsPermission("setBluetoothScoOn()")) {
            if (z) {
                AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
                synchronized (audioDeviceBroker.mDeviceStateLock) {
                    isBluetoothScoSupported = audioDeviceBroker.mBtHelper.isBluetoothScoSupported();
                }
                if (!isBluetoothScoSupported) {
                    return;
                }
            }
            if (UserHandle.getCallingAppId() >= 10000) {
                this.mBtScoOnByApp = z;
                EventLogger eventLogger = sDeviceLogger;
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("setBluetoothScoOn update mBtScoOnByApp = ", " uid = ", z);
                m.append(Binder.getCallingUid());
                m.append(" pid = ");
                m.append(Binder.getCallingPid());
                eventLogger.enqueue(new EventLogger.StringEvent(m.toString()));
                return;
            }
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            StringBuilder m2 = AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "setBluetoothScoOn(", ") from u/pid:", "/", z);
            m2.append(callingPid);
            String sb = m2.toString();
            new MediaMetrics.Item("audio.device.setBluetoothScoOn").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.STATE, z ? "on" : "off").record();
            this.mDeviceBroker.setBluetoothScoOn(sb, z);
        }
    }

    public final void setBluetoothVariableLatencyEnabled(boolean z) {
        setBluetoothVariableLatencyEnabled_enforcePermission();
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

    public final void setBtOffloadEnable(int i) {
        BtHelper btHelper = this.mDeviceBroker.mBtHelper;
        synchronized (btHelper) {
            btHelper.mIsBtOffloadEnabled = i;
        }
    }

    public final boolean setCommunicationDevice(IBinder iBinder, int i) {
        AudioDeviceInfo audioDeviceInfo;
        int i2;
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        String str = null;
        if (i != 0) {
            audioDeviceInfo = AudioManager.getDeviceForPortId(i, 2);
            if (audioDeviceInfo == null) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(i, "setCommunicationDevice: invalid portID ", "AS.AudioService");
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
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, audioDeviceInfo == null ? "clearCommunicationDevice(" : "setCommunicationDevice(", ") from u/pid:", "/", sb);
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

    public final void setCsd(float f) {
        SoundDoseRecord[] soundDoseRecordArr;
        setCsd_enforcePermission();
        if (f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
            if (soundDoseHelper.mEnableCsd.get() || soundDoseHelper.updateCsdForTestApi()) {
                synchronized (soundDoseHelper.mCsdStateLock) {
                    soundDoseHelper.mLastMomentaryExposureTimeMs = -1L;
                }
                return;
            }
            return;
        }
        SoundDoseHelper soundDoseHelper2 = this.mSoundDoseHelper;
        if (soundDoseHelper2.mEnableCsd.get() || soundDoseHelper2.updateCsdForTestApi()) {
            synchronized (soundDoseHelper2.mCsdStateLock) {
                try {
                    soundDoseHelper2.mCurrentCsd = f;
                    soundDoseHelper2.mNextCsdWarning = (float) Math.floor(f + 1.0d);
                    ((ArrayList) soundDoseHelper2.mDoseRecords).clear();
                    if (soundDoseHelper2.mCurrentCsd > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        SoundDoseRecord soundDoseRecord = new SoundDoseRecord();
                        soundDoseRecord.timestamp = SystemClock.elapsedRealtime() / 1000;
                        soundDoseRecord.value = f;
                        ((ArrayList) soundDoseHelper2.mDoseRecords).add(soundDoseRecord);
                    }
                    soundDoseRecordArr = (SoundDoseRecord[]) ((ArrayList) soundDoseHelper2.mDoseRecords).toArray(new SoundDoseRecord[0]);
                } finally {
                }
            }
            ISoundDose iSoundDose = (ISoundDose) soundDoseHelper2.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                iSoundDose.resetCsd(f, soundDoseRecordArr);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while setting the CSD value", e);
            }
        }
    }

    public final void setCsdAsAFeatureEnabled(boolean z) {
        boolean z2;
        setCsdAsAFeatureEnabled_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        synchronized (soundDoseHelper.mCsdAsAFeatureLock) {
            try {
                z2 = soundDoseHelper.mIsCsdAsAFeatureEnabled != z && soundDoseHelper.mIsCsdAsAFeatureAvailable;
                soundDoseHelper.mIsCsdAsAFeatureEnabled = z;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    SettingsAdapter settingsAdapter = soundDoseHelper.mSettings;
                    ContentResolver contentResolver = soundDoseHelper.mAudioService.mContentResolver;
                    boolean z3 = soundDoseHelper.mIsCsdAsAFeatureEnabled;
                    settingsAdapter.getClass();
                    Settings.Secure.putIntForUser(contentResolver, "audio_safe_csd_as_a_feature_enabled", z3 ? 1 : 0, -2);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            soundDoseHelper.updateCsdEnabled("setCsdAsAFeatureEnabled");
        }
    }

    public final void setDesiredHeadTrackingMode(int i) {
        setDesiredHeadTrackingMode_enforcePermission();
        if (i == -1 || i == 1 || i == 2) {
            this.mSpatializerHelper.setDesiredHeadTrackingMode(i);
        }
    }

    public final int setDeviceAsNonDefaultForStrategy(int i, AudioDeviceAttributes audioDeviceAttributes) {
        setDeviceAsNonDefaultForStrategy_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        String format = String.format("setDeviceAsNonDefaultForStrategy u/pid:%d/%d strat:%d dev:%s", Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(i), retrieveBluetoothAddress.toString());
        EventLogger eventLogger = sDeviceLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(format);
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        if (retrieveBluetoothAddress.getRole() == 1) {
            Log.e("AS.AudioService", "Unsupported input routing in ".concat(format));
            return -1;
        }
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        audioDeviceInventory.getClass();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(retrieveBluetoothAddress);
            int addDevicesRoleForStrategy = audioDeviceInventory.addDevicesRoleForStrategy(i, false, arrayList);
            if (create != null) {
                create.close();
            }
            if (addDevicesRoleForStrategy == 0) {
                audioDeviceInventory.mDeviceBroker.sendILMsgNoDelay(47, i, retrieveBluetoothAddress);
            }
            if (addDevicesRoleForStrategy != 0) {
                Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(addDevicesRoleForStrategy), format));
            }
            return addDevicesRoleForStrategy;
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

    public final int setDeviceToForceByUser(int i, String str, boolean z) {
        int deviceToForceByUser;
        checkModifyPhoneStateOrRoutingPermission();
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        synchronized (audioDeviceBroker.mDeviceStateLock) {
            deviceToForceByUser = audioDeviceBroker.mDeviceInventory.setDeviceToForceByUser(i, str, z);
        }
        return deviceToForceByUser;
    }

    public final void setDeviceVolume(VolumeInfo volumeInfo, final AudioDeviceAttributes audioDeviceAttributes, final String str) {
        setDeviceVolume_enforcePermission();
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
        this.mAudioSystem.invalidateRoutingCache();
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
            public final String eventToString() {
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
            setStreamVolumeWithAttributionInt(volumeInfo.getStreamType(), this.mStreamStates[volumeInfo.getStreamType()].mIndexMin, 0, audioDeviceAttributes, str, null, false);
            return;
        }
        eventLogger.enqueueAndLog(0, "setDeviceVolume from:" + str + " " + volumeInfo + " " + audioDeviceAttributes, "AS.AudioService");
        if (volumeInfo.getMinVolumeIndex() == -100 || volumeInfo.getMaxVolumeIndex() == -100) {
            int i = volumeIndex * 10;
            if (i < this.mStreamStates[volumeInfo.getStreamType()].mIndexMin || i > this.mStreamStates[volumeInfo.getStreamType()].mIndexMax) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(volumeIndex, "invalid volume index ", " not between min/max for stream ");
                m.append(volumeInfo.getStreamType());
                throw new IllegalArgumentException(m.toString());
            }
        } else {
            int i2 = (this.mStreamStates[volumeInfo.getStreamType()].mIndexMin + 5) / 10;
            int i3 = (this.mStreamStates[volumeInfo.getStreamType()].mIndexMax + 5) / 10;
            if (volumeInfo.getMinVolumeIndex() != i2 || volumeInfo.getMaxVolumeIndex() != i3) {
                volumeIndex = rescaleIndex(volumeIndex, volumeInfo.getMinVolumeIndex(), volumeInfo.getMaxVolumeIndex(), i2, i3);
            }
        }
        setStreamVolumeWithAttributionInt(volumeInfo.getStreamType(), volumeIndex, 0, audioDeviceAttributes, str, null, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0032 A[Catch: all -> 0x001c, TryCatch #0 {all -> 0x001c, blocks: (B:4:0x0003, B:6:0x000c, B:8:0x0012, B:12:0x001f, B:14:0x0032, B:16:0x0036, B:18:0x003c, B:20:0x0042, B:22:0x0048, B:24:0x004e, B:26:0x0054, B:27:0x005b, B:30:0x006a, B:32:0x0071, B:38:0x0074), top: B:3:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setDeviceVolume(com.android.server.audio.AudioService.VolumeStreamState r10, int r11) {
        /*
            r9 = this;
            java.lang.Class<com.android.server.audio.AudioService$VolumeStreamState> r0 = com.android.server.audio.AudioService.VolumeStreamState.class
            monitor-enter(r0)
            com.android.server.audio.AudioService$AudioHandler r1 = r9.mAudioHandler     // Catch: java.lang.Throwable -> L1c
            boolean r2 = r9.isAbsoluteVolumeDevice(r11)     // Catch: java.lang.Throwable -> L1c
            r8 = 1
            if (r2 != 0) goto L1e
            boolean r2 = r9.isA2dpAbsoluteVolumeDevice(r11)     // Catch: java.lang.Throwable -> L1c
            if (r2 != 0) goto L1e
            boolean r2 = android.media.AudioSystem.isLeAudioDeviceType(r11)     // Catch: java.lang.Throwable -> L1c
            if (r2 == 0) goto L19
            goto L1e
        L19:
            r2 = 0
            r5 = r2
            goto L1f
        L1c:
            r9 = move-exception
            goto L82
        L1e:
            r5 = r8
        L1f:
            r7 = 0
            r2 = 1006(0x3ee, float:1.41E-42)
            r3 = 0
            r4 = r11
            r6 = r10
            sendMsg(r1, r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L1c
            r10.applyDeviceVolume_syncVSS(r11)     // Catch: java.lang.Throwable -> L1c
            int r1 = android.media.AudioSystem.getNumStreamTypes()     // Catch: java.lang.Throwable -> L1c
            int r1 = r1 - r8
        L30:
            if (r1 < 0) goto L74
            int r2 = r10.mStreamType     // Catch: java.lang.Throwable -> L1c
            if (r1 == r2) goto L71
            int[] r3 = com.android.server.audio.AudioService.mStreamVolumeAlias     // Catch: java.lang.Throwable -> L1c
            r3 = r3[r1]     // Catch: java.lang.Throwable -> L1c
            if (r3 != r2) goto L71
            int r2 = r9.getDeviceForStream(r1)     // Catch: java.lang.Throwable -> L1c
            if (r11 == r2) goto L5b
            boolean r3 = r9.isAbsoluteVolumeDevice(r11)     // Catch: java.lang.Throwable -> L1c
            if (r3 != 0) goto L54
            boolean r3 = r9.isA2dpAbsoluteVolumeDevice(r11)     // Catch: java.lang.Throwable -> L1c
            if (r3 != 0) goto L54
            boolean r3 = android.media.AudioSystem.isLeAudioDeviceType(r11)     // Catch: java.lang.Throwable -> L1c
            if (r3 == 0) goto L5b
        L54:
            com.android.server.audio.AudioService$VolumeStreamState[] r3 = r9.mStreamStates     // Catch: java.lang.Throwable -> L1c
            r3 = r3[r1]     // Catch: java.lang.Throwable -> L1c
            r3.applyDeviceVolume_syncVSS(r11)     // Catch: java.lang.Throwable -> L1c
        L5b:
            com.android.server.audio.AudioService$VolumeStreamState[] r3 = r9.mStreamStates     // Catch: java.lang.Throwable -> L1c
            r3 = r3[r1]     // Catch: java.lang.Throwable -> L1c
            r3.applyDeviceVolume_syncVSS(r2)     // Catch: java.lang.Throwable -> L1c
            com.samsung.android.server.audio.MultiSoundManager r3 = r9.mMultiSoundManager     // Catch: java.lang.Throwable -> L1c
            boolean r3 = r3.mEnabled     // Catch: java.lang.Throwable -> L1c
            if (r3 == 0) goto L71
            if (r11 == r2) goto L71
            com.android.server.audio.AudioService$VolumeStreamState[] r2 = r9.mStreamStates     // Catch: java.lang.Throwable -> L1c
            r2 = r2[r1]     // Catch: java.lang.Throwable -> L1c
            r2.applyDeviceVolume_syncVSS(r11)     // Catch: java.lang.Throwable -> L1c
        L71:
            int r1 = r1 + (-1)
            goto L30
        L74:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1c
            com.android.server.audio.AudioService$AudioHandler r1 = r9.mAudioHandler
            r5 = 0
            r7 = 500(0x1f4, float:7.0E-43)
            r2 = 1
            r3 = 2
            r4 = r11
            r6 = r10
            sendMsg(r1, r2, r3, r4, r5, r6, r7)
            return
        L82:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1c
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.setDeviceVolume(com.android.server.audio.AudioService$VolumeStreamState, int):void");
    }

    public final void setDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
        boolean isAvrcpAbsoluteVolumeSupportedForActiveDevice;
        String str2 = str;
        setDeviceVolumeBehavior_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        AudioManager.enforceValidVolumeBehavior(i);
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        EventLogger eventLogger = sVolumeLogger;
        StringBuilder sb = new StringBuilder("setDeviceVolumeBehavior: dev:");
        sb.append(AudioSystem.getOutputDeviceName(retrieveBluetoothAddress.getInternalType()));
        sb.append(" addr:");
        sb.append(Utils.anonymizeBluetoothAddress(retrieveBluetoothAddress.getAddress()));
        sb.append(" behavior:");
        sb.append(AudioDeviceVolumeManager.volumeBehaviorName(i));
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, " pack:", str2));
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        if (str2 == null) {
            str2 = "";
        }
        if (retrieveBluetoothAddress.getType() != 8) {
            setDeviceVolumeBehaviorInternal(retrieveBluetoothAddress, i, str2);
            int internalType = retrieveBluetoothAddress.getInternalType();
            Log.d("AS.AudioService", "Persisting Volume Behavior for DeviceType: " + internalType);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SettingsAdapter settingsAdapter = this.mSettings;
                ContentResolver contentResolver = this.mContentResolver;
                String str3 = "AudioService_DeviceVolumeBehavior_" + AudioSystem.getOutputDeviceName(internalType);
                settingsAdapter.getClass();
                Settings.System.putIntForUser(contentResolver, str3, i, -2);
                return;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        String address = retrieveBluetoothAddress.getAddress();
        boolean z = i == 3;
        EventLogger.StringEvent stringEvent2 = new EventLogger.StringEvent("avrcpSupportsAbsoluteVolume addr=" + Utils.anonymizeBluetoothAddress(address) + " support=" + z);
        stringEvent2.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent2);
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        synchronized (audioDeviceBroker.mDeviceStateLock) {
            audioDeviceBroker.mDualA2dpManager.setAvrcpAbsoluteVolumeSupported(address, z);
            isAvrcpAbsoluteVolumeSupportedForActiveDevice = audioDeviceBroker.mDualA2dpManager.isAvrcpAbsoluteVolumeSupportedForActiveDevice();
            audioDeviceBroker.mBtHelper.setAvrcpAbsoluteVolumeSupported(isAvrcpAbsoluteVolumeSupportedForActiveDevice);
            AudioService audioService = audioDeviceBroker.mAudioService;
            audioService.mAvrcpAbsVolSupported = isAvrcpAbsoluteVolumeSupportedForActiveDevice;
            com.android.media.audio.Flags.absVolumeIndexFix();
            sendMsg(audioService.mAudioHandler, 0, 2, 128, 0, audioService.mStreamStates[3], 0);
            for (int i2 : audioService.AVC_AFFECTED_STREAMS) {
                sendMsg(audioService.mAudioHandler, 0, 2, 128, 0, audioService.mStreamStates[i2], 0);
            }
        }
        sendMsg(this.mAudioHandler, 2773, 2, isAvrcpAbsoluteVolumeSupportedForActiveDevice ? 1 : 0, 0, address, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0051, code lost:
    
        if (removeAudioSystemDeviceOutFromAbsVolumeDevices(r3) != null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0054, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0055, code lost:
    
        r1 = r2 | r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0085, code lost:
    
        if (removeAudioSystemDeviceOutFromAbsVolumeDevices(r3) != null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0095, code lost:
    
        if (removeAudioSystemDeviceOutFromAbsVolumeDevices(r3) != null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setDeviceVolumeBehaviorInternal(android.media.AudioDeviceAttributes r12, int r13, java.lang.String r14) {
        /*
            r11 = this;
            int r3 = r12.getInternalType()
            r0 = 1
            r1 = 0
            if (r13 == 0) goto L88
            java.lang.String r2 = "AS.AudioService"
            java.lang.String r4 = "Adding DeviceType: 0x"
            if (r13 == r0) goto L58
            r5 = 2
            if (r13 == r5) goto L24
            r0 = 3
            if (r13 == r0) goto L1c
            r0 = 4
            if (r13 == r0) goto L1c
            r0 = 5
            if (r13 == r0) goto L1c
            goto L98
        L1c:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "Absolute volume unsupported for now"
            r11.<init>(r12)
            throw r11
        L24:
            boolean r5 = r11.removeAudioSystemDeviceOutFromFullVolumeDevices(r3)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r4)
            java.lang.String r4 = java.lang.Integer.toHexString(r3)
            r6.append(r4)
            java.lang.String r4 = " to mFixedVolumeDevices"
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            android.util.Log.d(r2, r4)
            java.util.Set r2 = r11.mFixedVolumeDevices
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            java.util.HashSet r2 = (java.util.HashSet) r2
            boolean r2 = r2.add(r4)
            r2 = r2 | r5
            com.android.server.audio.AudioService$AbsoluteVolumeDeviceInfo r4 = r11.removeAudioSystemDeviceOutFromAbsVolumeDevices(r3)
            if (r4 == 0) goto L54
            goto L55
        L54:
            r0 = r1
        L55:
            r1 = r2 | r0
            goto L98
        L58:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = java.lang.Integer.toHexString(r3)
            r5.append(r4)
            java.lang.String r4 = " to mFullVolumeDevices"
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            android.util.Log.d(r2, r4)
            java.util.Set r2 = r11.mFullVolumeDevices
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            java.util.HashSet r2 = (java.util.HashSet) r2
            boolean r2 = r2.add(r4)
            boolean r4 = r11.removeAudioSystemDeviceOutFromFixedVolumeDevices(r3)
            r2 = r2 | r4
            com.android.server.audio.AudioService$AbsoluteVolumeDeviceInfo r4 = r11.removeAudioSystemDeviceOutFromAbsVolumeDevices(r3)
            if (r4 == 0) goto L54
            goto L55
        L88:
            boolean r2 = r11.removeAudioSystemDeviceOutFromFullVolumeDevices(r3)
            boolean r4 = r11.removeAudioSystemDeviceOutFromFixedVolumeDevices(r3)
            r2 = r2 | r4
            com.android.server.audio.AudioService$AbsoluteVolumeDeviceInfo r4 = r11.removeAudioSystemDeviceOutFromAbsVolumeDevices(r3)
            if (r4 == 0) goto L54
            goto L55
        L98:
            if (r1 == 0) goto La6
            com.android.server.audio.AudioService$AudioHandler r4 = r11.mAudioHandler
            r5 = 47
            r6 = 2
            r8 = 0
            r10 = 0
            r7 = r13
            r9 = r12
            sendMsg(r4, r5, r6, r7, r8, r9, r10)
        La6:
            com.android.server.utils.EventLogger r12 = com.android.server.audio.AudioService.sDeviceLogger
            com.android.server.utils.EventLogger$StringEvent r0 = new com.android.server.utils.EventLogger$StringEvent
            java.lang.String r1 = "Volume behavior "
            java.lang.String r2 = " for dev=0x"
            java.lang.StringBuilder r13 = com.android.server.BatteryService$$ExternalSyntheticOutline0.m(r13, r1, r2)
            java.lang.String r1 = java.lang.Integer.toHexString(r3)
            r13.append(r1)
            java.lang.String r1 = " from:"
            r13.append(r1)
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r0.<init>(r13)
            r12.enqueue(r0)
            java.lang.String r12 = "setDeviceVolumeBehavior:"
            java.lang.String r5 = r12.concat(r14)
            com.android.server.audio.AudioService$AudioHandler r0 = r11.mAudioHandler
            r1 = 33
            r2 = 2
            r4 = 0
            r6 = 0
            sendMsg(r0, r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.setDeviceVolumeBehaviorInternal(android.media.AudioDeviceAttributes, int, java.lang.String):void");
    }

    public final boolean setEncodedSurroundMode(int i) {
        int i2;
        setEncodedSurroundMode_enforcePermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mSettingsLock) {
                SettingsAdapter settingsAdapter = this.mSettings;
                ContentResolver contentResolver = this.mContentResolver;
                if (i != 1) {
                    i2 = 2;
                    if (i != 2) {
                        i2 = 3;
                        if (i != 3) {
                            i2 = 0;
                        }
                    }
                } else {
                    i2 = 1;
                }
                settingsAdapter.getClass();
                Settings.Global.putInt(contentResolver, "encoded_surround_output", i2);
            }
            return true;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int setFadeManagerConfigurationForFocusLoss(FadeManagerConfiguration fadeManagerConfiguration) {
        setFadeManagerConfigurationForFocusLoss_enforcePermission();
        Preconditions.checkState(Flags.enableFadeManagerConfiguration(), "Fade manager configuration not supported");
        Objects.requireNonNull(fadeManagerConfiguration, "Fade manager config for focus loss cannot be null");
        List audioAttributesWithVolumeShaperConfigs = fadeManagerConfiguration.getAudioAttributesWithVolumeShaperConfigs();
        int i = 0;
        for (int i2 = 0; i2 < audioAttributesWithVolumeShaperConfigs.size(); i2++) {
            validateAudioAttributesUsage((AudioAttributes) audioAttributesWithVolumeShaperConfigs.get(i2));
        }
        FadeOutManager fadeOutManager = this.mPlaybackMonitor.mFadeOutManager;
        synchronized (fadeOutManager.mLock) {
            FadeConfigurations fadeConfigurations = fadeOutManager.mFadeConfigurations;
            fadeConfigurations.getClass();
            if (Flags.enableFadeManagerConfiguration()) {
                synchronized (fadeConfigurations.mLock) {
                    fadeConfigurations.mUpdatedFadeManagerConfig = fadeManagerConfiguration;
                    fadeConfigurations.mActiveFadeManagerConfig = fadeConfigurations.getActiveFadeMgrConfigLocked();
                }
            } else {
                i = -1;
            }
        }
        return i;
    }

    public final void setFineVolume(int i, int i2, int i3, int i4, String str) {
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i2, i3, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ".setFineVolume")));
        setStreamVolumeWithAttribution(i, i2, i3, null, str, str, null, Binder.getCallingUid(), true, true, i4);
    }

    public final int setFocusPropertiesForPolicy(int i, IAudioPolicyCallback iAudioPolicyCallback) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "setFocusPropertiesForPolicy() duck behavior=", " policy ");
        m.append(iAudioPolicyCallback.asBinder());
        Log.d("AS.AudioService", m.toString());
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change audio policy focus properties");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                    Slog.e("AS.AudioService", "Cannot change audio policy focus properties, unregistered policy");
                    return -1;
                }
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
                this.mMediaFocusControl.mNotifyFocusOwnerOnDuck = !(i == 1);
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) {
        if (audioFocusInfo == null) {
            throw new IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (iAudioPolicyCallback == null) {
            throw new IllegalArgumentException("Illegal null AudioPolicy callback");
        }
        synchronized (this.mAudioPolicies) {
            try {
                if (!this.mAudioPolicies.containsKey(iAudioPolicyCallback.asBinder())) {
                    throw new IllegalStateException("Unregistered AudioPolicy for external focus");
                }
                this.mMediaFocusControl.setFocusRequestResultFromExtPolicy(audioFocusInfo, i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setForceSpeakerOn(boolean z) {
        if (Binder.getCallingUid() != 1000) {
            return;
        }
        this.mForceSpeaker = z ? 1 : 0;
        if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(getDeviceForStream(3)))) {
            return;
        }
        sendMsg(this.mAudioHandler, 8, 2, 1, this.mForceSpeaker, null, 0);
    }

    public final int setHdmiSystemAudioSupported(boolean z) {
        synchronized (this.mHdmiClientLock) {
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes) {
        setHeadTrackerEnabled_enforcePermission();
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        Objects.requireNonNull(audioDeviceAttributes);
        spatializerHelper.setHeadTrackerEnabled(z, audioDeviceAttributes);
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

    public final void setHomeSoundEffectEnabled(boolean z) {
        this.mHomeSoundEffectEnabled = z;
    }

    public final void setLeAudioSuspended(boolean z) {
        setLeAudioSuspended_enforcePermission();
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("setLeAudioSuspended(", ") from u/pid:", z);
        m.append(Binder.getCallingUid());
        m.append("/");
        m.append(Binder.getCallingPid());
        this.mDeviceBroker.setLeAudioSuspended(m.toString(), z, false);
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
                    StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i2, i, "setLeAudioVolumeOnModeUpdate ignoring invalid device=", ", mode=", ", index=");
                    ServiceKeeper$$ExternalSyntheticOutline0.m(i4, i5, " maxIndex=", " streamType=", m);
                    AudioService$$ExternalSyntheticOutline0.m(m, i3, "AS.AudioService");
                    break;
                } else {
                    StringBuilder m2 = ArrayUtils$$ExternalSyntheticOutline0.m(i2, i, "setLeAudioVolumeOnModeUpdate postSetLeAudioVolumeIndex device=", ", mode=", ", index=");
                    ServiceKeeper$$ExternalSyntheticOutline0.m(i4, i5, " maxIndex=", " streamType=", m2);
                    GestureWakeup$$ExternalSyntheticOutline0.m(m2, i3, "AS.AudioService");
                    this.mDeviceBroker.postSetLeAudioVolumeIndex(i4, i5, i3);
                    this.mDeviceBroker.postApplyVolumeOnDevice(i3, i2, "setLeAudioVolumeOnModeUpdate");
                    break;
                }
        }
    }

    public final void setMasterMute(boolean z, final int i, final String str, int i2, String str2) {
        setMasterMute_enforcePermission();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        if (callingUid == 1000) {
            callingUid = UserHandle.getUid(i2, UserHandle.getAppId(callingUid));
        }
        if (z || checkNoteAppOp(33, callingUid, str, str2)) {
            if (i2 == UserHandle.getCallingUserId() || this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", callingPid, callingUid) == 0) {
                EventLogger eventLogger = sMasterMuteLogger;
                final int i3 = z ? 1 : 0;
                eventLogger.enqueue(new EventLogger.Event(i3, i, str) { // from class: com.samsung.android.server.audio.AudioEvents$MasterMuteEvent
                    public final String mCaller;
                    public final int mFlags;
                    public final int mIsMute;

                    {
                        this.mIsMute = i3;
                        this.mFlags = i;
                        this.mCaller = str;
                    }

                    @Override // com.android.server.utils.EventLogger.Event
                    public final String eventToString() {
                        StringBuilder sb = new StringBuilder("setMasterMute(mute:");
                        sb.append(this.mIsMute);
                        sb.append(" flags:0x");
                        BatteryService$$ExternalSyntheticOutline0.m(this.mFlags, sb, ") from ");
                        sb.append(this.mCaller);
                        return sb.toString();
                    }
                });
                setMasterMuteInternalNoCallerCheck(i, i2, "setMasterMute", z);
            }
        }
    }

    public final void setMasterMuteInternalNoCallerCheck(int i, int i2, String str, boolean z) {
        Log.d("AS.AudioService", TextUtils.formatSimple("Master mute %s, flags 0x%x, userId=%d from %s", new Object[]{Boolean.valueOf(z), Integer.valueOf(i), Integer.valueOf(i2), str}));
        int i3 = this.mPlatformType;
        if (i3 != 3 && this.mUseFixedVolume) {
            z = false;
        }
        boolean z2 = z;
        if (!((i3 == 3 && i2 == 0) || getCurrentUserId() == i2) || z2 == this.mMasterMute.getAndSet(z2)) {
            return;
        }
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(z2));
        this.mAudioSystem.getClass();
        AudioSystem.setMasterMute(z2);
        VolumeController volumeController = this.mVolumeController;
        synchronized (this.mHdmiClientLock) {
            try {
                if (this.mHdmiTvClient != null && this.mHdmiSystemAudioSupported && this.mHdmiCecVolumeControlEnabled) {
                    i &= -2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        volumeController.postMasterMuteChanged(i);
        sendMsg(this.mAudioHandler, 55, 2, z2 ? 1 : 0, 0, null, 0);
    }

    public final boolean setMediaVolumeSteps(int[] iArr) {
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

    public final void setMicInputControlMode(int i) {
        MicModeManager micModeManager = this.mMicModeManager;
        micModeManager.getClass();
        ArrayMap arrayMap = MicModeType.sMicModeParamTable;
        boolean containsKey = arrayMap.containsKey(Integer.valueOf(i));
        EventLogger eventLogger = MicModeManager.sMicModeLogger;
        if (!containsKey) {
            eventLogger.enqueueAndLog(1, "attempt to call setMicInputControlMode() invalid mode :" + i, "MicModeManager");
            return;
        }
        eventLogger.enqueueAndLog(0, "setMicInputControlMode: " + i, "MicModeManager");
        String str = (String) arrayMap.get(Integer.valueOf(i));
        Log.i("MicModeManager", "setMicInputControlMode setparam : " + str);
        AudioSystem.setParameters(str);
        micModeManager.mMicModeType.setMicInputControlMode(MicModeManager.mCr, new AudioParameter(str));
    }

    public final void setMicMuteFromSwitchInput() {
        InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
        if (inputManager.isMicMuted() != -1) {
            setMicrophoneMuteFromSwitch(inputManager.isMicMuted() != 0);
        }
    }

    public final void setMicrophoneMute(boolean z, String str, int i, String str2) {
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
        sMicrophoneLogger.enqueue(new AudioEvents$MicrophoneEvent(Binder.getCallingPid(), str, z));
    }

    public final void setMicrophoneMuteFromSwitch(boolean z) {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000) {
            Log.e("AS.AudioService", "setMicrophoneMuteFromSwitch() called from non system user!");
            return;
        }
        this.mMicMuteFromSwitch = z;
        new MediaMetrics.Item("audio.mic").setUid(callingUid).set(MediaMetrics.Property.EVENT, "setMicrophoneMuteFromSwitch").set(MediaMetrics.Property.REQUEST, z ? "mute" : "unmute").record();
        setMicrophoneMuteNoCallerCheck(UserHandle.getCallingUserId());
    }

    public final void setMicrophoneMuteNoCallerCheck(int i) {
        boolean z = this.mMicMuteFromSwitch || this.mMicMuteFromRestrictions || this.mMicMuteFromApi || this.mMicMuteFromPrivacyToggle;
        Log.d("AS.AudioService", String.format("Mic mute %b, user=%d", Boolean.valueOf(z), Integer.valueOf(i)));
        if (getCurrentUserId() == i || i == 0) {
            this.mAudioSystem.getClass();
            boolean isMicrophoneMuted = AudioSystem.isMicrophoneMuted();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mAudioSystem.getClass();
                int muteMicrophone = AudioSystem.muteMicrophone(z);
                this.mAudioSystem.getClass();
                this.mMicMuteFromSystemCached = AudioSystem.isMicrophoneMuted();
                if (muteMicrophone != 0) {
                    Log.e("AS.AudioService", "Error changing mic mute state to " + z + " current:" + this.mMicMuteFromSystemCached);
                }
                new MediaMetrics.Item("audio.mic").setUid(callingUid).set(MediaMetrics.Property.EVENT, "setMicrophoneMuteNoCallerCheck").set(MediaMetrics.Property.MUTE, this.mMicMuteFromSystemCached ? "on" : "off").set(MediaMetrics.Property.REQUEST, z ? "mute" : "unmute").set(MediaMetrics.Property.STATUS, Integer.valueOf(muteMicrophone)).record();
                if (z != isMicrophoneMuted) {
                    sendMsg(this.mAudioHandler, 30, 1, 0, 0, null, 0);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void setMode(int i, IBinder iBinder, String str) {
        Object obj;
        SetModeDeathHandler setModeDeathHandler;
        SetModeDeathHandler setModeDeathHandler2;
        int i2;
        int i3 = i;
        int callingPid = Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i3, callingPid, "setMode(mode=", ", pid=", ", uid=");
        m.append(callingUid);
        m.append(", caller=");
        m.append(str);
        m.append(")");
        Log.v("AS.AudioService", m.toString());
        if (checkAudioSettingsPermission("setMode()")) {
            if (iBinder == null) {
                Log.e("AS.AudioService", "setMode() called with null binder");
                return;
            }
            if (i3 < -1 || i3 >= 7) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(i3, "setMode() invalid mode: ", "AS.AudioService");
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
            Object obj2 = this.mDeviceBroker.mSetModeLock;
            synchronized (obj2) {
                try {
                    Iterator it = this.mSetModeDeathHandlers.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            setModeDeathHandler = null;
                            break;
                        } else {
                            setModeDeathHandler = (SetModeDeathHandler) it.next();
                            if (setModeDeathHandler.mPid == callingPid) {
                                break;
                            }
                        }
                    }
                    if (i4 == 0) {
                        if (setModeDeathHandler != null) {
                            if (!setModeDeathHandler.mIsPrivileged && setModeDeathHandler.mMode == 3) {
                                this.mAudioHandler.removeEqualMessages(31, setModeDeathHandler);
                                this.mDeviceBroker.sendIILMsg(2764, 0, Process.getUidForPid(callingPid), 0, null, 6000);
                            }
                            this.mSetModeDeathHandlers.remove(setModeDeathHandler);
                            Log.v("AS.AudioService", "setMode(" + i4 + ") removing hldr for pid: " + callingPid);
                            try {
                                setModeDeathHandler.mCb.unlinkToDeath(setModeDeathHandler, 0);
                            } catch (NoSuchElementException unused) {
                                Log.w("AS.AudioService", "setMode link does not exist ...");
                            }
                        }
                        i2 = 3;
                        obj = obj2;
                    } else {
                        if (setModeDeathHandler != null) {
                            setModeDeathHandler.mMode = i4;
                            setModeDeathHandler.mUpdateTime = System.currentTimeMillis();
                            Log.v("AS.AudioService", "setMode(" + i4 + ") updating hldr for pid: " + callingPid);
                            obj = obj2;
                            setModeDeathHandler2 = setModeDeathHandler;
                            i2 = 3;
                        } else {
                            obj = obj2;
                            try {
                                SetModeDeathHandler setModeDeathHandler3 = new SetModeDeathHandler(iBinder, callingPid, callingUid, z, str, i4);
                                try {
                                    iBinder.linkToDeath(setModeDeathHandler3, 0);
                                    try {
                                        if (this.mAudioGameManager != null && AudioGameManager.isGamePackager(str)) {
                                            setModeDeathHandler3.mPackageType = "GAMEVOIP";
                                        }
                                    } catch (IllegalStateException e) {
                                        Log.e("AS.AudioService", "setPackageType", e);
                                    }
                                    this.mSetModeDeathHandlers.add(setModeDeathHandler3);
                                    Log.v("AS.AudioService", "setMode(" + i4 + ") adding handler for pid=" + callingPid);
                                    setModeDeathHandler2 = setModeDeathHandler3;
                                    i2 = 3;
                                } catch (RemoteException unused2) {
                                    Log.w("AS.AudioService", "setMode() could not link to " + iBinder + " binder death");
                                    return;
                                }
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                        if (i4 == i2 && !setModeDeathHandler2.mIsPrivileged) {
                            setModeDeathHandler2.mPlaybackActive = true;
                            setModeDeathHandler2.mRecordingActive = true;
                            sendMsg(this.mAudioHandler, 31, 2, 0, 0, setModeDeathHandler2, 6000);
                        }
                    }
                    Iterator it2 = this.mSetModeDeathHandlers.iterator();
                    this.mAppMode = "NORMAL";
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        SetModeDeathHandler setModeDeathHandler4 = (SetModeDeathHandler) it2.next();
                        int i5 = setModeDeathHandler4.mMode;
                        if (i5 == 2) {
                            this.mAppMode = "CALL";
                            break;
                        } else if (i5 == i2) {
                            if ("NORMAL".equals(this.mAppMode) && "GAMEVOIP".equals(setModeDeathHandler4.mPackageType)) {
                                this.mAppMode = "GAMEVOIP";
                            } else {
                                this.mAppMode = "VOIP";
                            }
                        }
                    }
                    String str2 = "GAMEVOIP".equals(this.mAppMode) ? "GAME" : "NORMAL";
                    this.mAudioSystem.setParameters("l_dual_speaker_calling_package=" + str2);
                    Log.i("AS.AudioService", "setModeCallingPackage=" + str2);
                    this.mExt.updateCallGuardInfo(i4, callingPid, false);
                    sendMsg(this.mAudioHandler, 36, 0, i4, callingPid, str, 0);
                } catch (Throwable th2) {
                    th = th2;
                    obj = obj2;
                    throw th;
                }
            }
        }
    }

    public final void setMultiAudioFocusEnabled(boolean z) {
        setMultiAudioFocusEnabled_enforcePermission();
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        if (mediaFocusControl == null || mediaFocusControl.mMultiAudioFocusEnabled == z) {
            return;
        }
        Log.d("MediaFocusControl", "updateMultiAudioFocus( " + z + " )");
        mediaFocusControl.mMultiAudioFocusEnabled = z;
        if (!z) {
            if (!mediaFocusControl.mFocusStack.isEmpty()) {
                FocusRequester focusRequester = (FocusRequester) mediaFocusControl.mFocusStack.peek();
                VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("updateMultiAudioFocus[mFocusStack] : send Loss :: Tasks ="), focusRequester.mPackageName, "MediaFocusControl");
                focusRequester.handleFocusLoss(-1, null, false);
            }
            if (!mediaFocusControl.mMultiAudioFocusList.isEmpty()) {
                Iterator it = mediaFocusControl.mMultiAudioFocusList.iterator();
                while (it.hasNext()) {
                    ((FocusRequester) it.next()).handleFocusLoss(-1, null, false);
                }
                mediaFocusControl.mMultiAudioFocusList.clear();
            }
            int i = 0;
            while (true) {
                MultiFocusStack multiFocusStack = mediaFocusControl.mMultiFocusStack;
                if (i >= multiFocusStack.size()) {
                    break;
                }
                Iterator it2 = ((Stack) multiFocusStack.valueAt(i)).iterator();
                while (it2.hasNext()) {
                    FocusRequester focusRequester2 = (FocusRequester) it2.next();
                    focusRequester2.handleFocusLoss(-1, null, false);
                    focusRequester2.release();
                    it2.remove();
                }
                i++;
            }
        } else {
            if (!mediaFocusControl.mFocusStack.isEmpty()) {
                FocusRequester focusRequester3 = (FocusRequester) mediaFocusControl.mFocusStack.peek();
                if (AudioUtils.checkRunningBackground(focusRequester3.mPackageName)) {
                    RCPManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("updateMultiAudioFocus( send AUDIOFOCUS_LOSS [ "), focusRequester3.mPackageName, " ] )", "MediaFocusControl");
                    focusRequester3.handleFocusLoss(-1, null, false);
                }
            }
            FocusRequester focusRequester4 = mediaFocusControl.mIgnoredFocus;
            if (focusRequester4 != null && AudioUtils.checkRunningBackground(focusRequester4.mPackageName)) {
                RCPManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("updateMultiAudioFocus( send AUDIOFOCUS_LOSS mIgnoredFocus [ "), mediaFocusControl.mIgnoredFocus.mPackageName, " ] )", "MediaFocusControl");
                mediaFocusControl.mIgnoredFocus.handleFocusLoss(-1, null, false);
            }
        }
        if (z) {
            return;
        }
        this.mDeviceBroker.sendIILMsg(12, 0, 0, 0, null, 0);
    }

    public final void setMultiSoundOn(boolean z) {
        sendMsg(this.mAudioHandler, 2763, 2, 0, 1, Boolean.valueOf(z), 0);
    }

    public final void setMultiSoundOn(boolean z, boolean z2) {
        if (checkAudioSettingsPermission("setMultiSoundOn")) {
            sendMsg(this.mAudioHandler, 2763, 2, z2 ? 1 : 0, 0, Boolean.valueOf(z), 0);
        }
    }

    public void setMusicMute(boolean z) {
        boolean z2;
        VolumeStreamState volumeStreamState = this.mStreamStates[3];
        volumeStreamState.getClass();
        synchronized (VolumeStreamState.class) {
            try {
                if (z != volumeStreamState.mIsMutedInternally) {
                    volumeStreamState.mIsMutedInternally = z;
                    volumeStreamState.applyAllVolumes();
                    z2 = true;
                } else {
                    z2 = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(volumeStreamState.mStreamType, z));
        }
    }

    public final void setMuteInterval(int i, String str) {
        Log.i("AS.AudioService", "setMuteInterval unmute timer=" + i + " from=" + str);
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") == 0) {
            sendMsg(this.mAudioHandler, 2766, 0, i, 0, 0, 0);
            return;
        }
        Log.w("AS.AudioService", "WRITE_SECURE_SETTINGS Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
    }

    public final void setNavigationRepeatSoundEffectsEnabled(boolean z) {
        this.mNavigationRepeatSoundEffectsEnabled = z;
    }

    public final void setNotifAliasRingForTest(boolean z) {
        setNotifAliasRingForTest_enforcePermission();
        boolean z2 = this.mNotifAliasRing != z;
        this.mNotifAliasRing = z;
        if (z2) {
            updateStreamVolumeAlias("AudioServiceTest", true);
        }
    }

    public final void setOutputRs2UpperBound(float f) {
        setOutputRs2UpperBound_enforcePermission();
        SoundDoseHelper soundDoseHelper = this.mSoundDoseHelper;
        if (soundDoseHelper.mEnableCsd.get()) {
            ISoundDose iSoundDose = (ISoundDose) soundDoseHelper.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                iSoundDose.setOutputRs2UpperBound(f);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while setting the RS2 exposure value", e);
            }
        }
    }

    public final int setPreferredDevicesForCapturePreset(int i, List list) {
        if (list == null) {
            return -1;
        }
        enforceModifyAudioRoutingPermission();
        String format = String.format("setPreferredDevicesForCapturePreset u/pid:%d/%d source:%d dev:%s", Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(i), list.stream().map(new AudioService$$ExternalSyntheticLambda2(0)).collect(Collectors.joining(",")));
        EventLogger eventLogger = sDeviceLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(format);
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        if (list.stream().anyMatch(new AudioService$$ExternalSyntheticLambda3(0))) {
            Log.e("AS.AudioService", "Unsupported output routing in ".concat(format));
            return -1;
        }
        List retrieveBluetoothAddresses = retrieveBluetoothAddresses(list);
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        audioDeviceInventory.getClass();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            int devicesRole = AudioDeviceInventory.setDevicesRole(audioDeviceInventory.mAppliedPresetRoles, new AudioDeviceInventory$$ExternalSyntheticLambda2(audioDeviceInventory, 8), new AudioDeviceInventory$$ExternalSyntheticLambda2(audioDeviceInventory, 9), i, retrieveBluetoothAddresses);
            if (create != null) {
                create.close();
            }
            if (devicesRole == 0) {
                audioDeviceInventory.mDeviceBroker.sendILMsgNoDelay(37, i, retrieveBluetoothAddresses);
            }
            if (devicesRole != 0) {
                Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(devicesRole), format));
            }
            return devicesRole;
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

    public final int setPreferredDevicesForStrategy(int i, List list) {
        setPreferredDevicesForStrategy_enforcePermission();
        if (list == null) {
            return -1;
        }
        List retrieveBluetoothAddresses = retrieveBluetoothAddresses(list);
        String format = String.format("setPreferredDevicesForStrategy u/pid:%d/%d strat:%d dev:%s", Integer.valueOf(Binder.getCallingUid()), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(i), retrieveBluetoothAddresses.stream().map(new AudioService$$ExternalSyntheticLambda2(1)).collect(Collectors.joining(",")));
        EventLogger eventLogger = sDeviceLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(format);
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        if (retrieveBluetoothAddresses.stream().anyMatch(new AudioService$$ExternalSyntheticLambda3(2))) {
            Log.e("AS.AudioService", "Unsupported input routing in ".concat(format));
            return -1;
        }
        AudioDeviceInventory audioDeviceInventory = this.mDeviceBroker.mDeviceInventory;
        audioDeviceInventory.getClass();
        SafeCloseable create = ClearCallingIdentityContext.create();
        try {
            int devicesRoleForStrategy = audioDeviceInventory.setDevicesRoleForStrategy(i, false, retrieveBluetoothAddresses);
            if (create != null) {
                create.close();
            }
            if (devicesRoleForStrategy == 0) {
                audioDeviceInventory.mDeviceBroker.sendILMsgNoDelay(32, i, retrieveBluetoothAddresses);
            }
            if (devicesRoleForStrategy != 0) {
                Log.e("AS.AudioService", String.format("Error %d in %s)", Integer.valueOf(devicesRoleForStrategy), format));
            }
            return devicesRoleForStrategy;
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

    public final int setPreferredMixerAttributes(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) {
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
            EventLogger eventLogger = sDeviceLogger;
            EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(formatSimple);
            stringEvent.printLog(0, "AS.AudioService");
            eventLogger.enqueue(stringEvent);
            this.mAudioSystem.getClass();
            int preferredMixerAttributes = AudioSystem.setPreferredMixerAttributes(audioAttributes, i, callingUid, audioMixerAttributes);
            if (preferredMixerAttributes == 0) {
                dispatchPreferredMixerAttributesChanged(audioAttributes, i, audioMixerAttributes);
            } else {
                Log.e("AS.AudioService", TextUtils.formatSimple("Error %d in %s)", new Object[]{Integer.valueOf(preferredMixerAttributes), formatSimple}));
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return preferredMixerAttributes;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setRadioOutputPath(int i) {
        if (checkAudioSettingsPermission("setRadioOutputPath()")) {
            synchronized (this.mSettingsLock) {
                this.mStreamStates[3].mute("setRadioOutputPath", false);
            }
            if (i == 2) {
                this.mAudioSystem.setForceUse(8, 1);
                this.mForcedUseForFMRadio = 1;
            } else if (i != 3) {
                DirEncryptService$$ExternalSyntheticOutline0.m(i, "FM radio app set wrong radio output path : ", "AS.AudioService");
                return;
            } else {
                this.mAudioSystem.setForceUse(8, 0);
                this.mForcedUseForFMRadio = 0;
            }
            int streamVolume = getStreamVolume(3) * 10;
            sendVolumeChangedIntent(3, streamVolume, streamVolume, 0);
        }
    }

    public final void setRemoteMic(boolean z) {
        this.mRemoteMic = z;
    }

    public final void setRingerMode(int i, String str, boolean z) {
        if (this.mUseFixedVolume || this.mIsSingleVolume || this.mUseVolumeGroupAliases) {
            return;
        }
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Bad caller: ", str));
        }
        if (!isValidRingerMode(i)) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Bad ringer mode "));
        }
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
                try {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setRingerModeExt(int i) {
        synchronized (this.mSettingsLock) {
            try {
                if (i == this.mRingerModeExternal) {
                    return;
                }
                this.mRingerModeExternal = i;
                broadcastRingerMode(i, "android.media.RINGER_MODE_CHANGED");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setRingerModeExternal(int i, String str) {
        if (this.mHardeningEnforcer.blockVolumeMethod(200)) {
            return;
        }
        if (isAndroidNPlus(str) && wouldToggleZenMode(i) && !this.mNm.isNotificationPolicyAccessGrantedForPackage(str)) {
            throw new SecurityException("Not allowed to change Do Not Disturb state");
        }
        setRingerMode(i, str, true);
    }

    public final void setRingerModeInt(int i, boolean z) {
        boolean z2;
        synchronized (this.mSettingsLock) {
            try {
                int i2 = this.mRingerMode;
                z2 = i2 != i;
                if (z2) {
                    this.mPrevRingerMode = i2;
                    sendMsg(this.mAudioHandler, 2766, 0, 0, 0, 0, 0);
                }
                this.mRingerMode = i;
                muteRingerModeStreams();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            sendMsg(this.mAudioHandler, 3, 0, 0, 0, null, 500);
        }
        if (z2) {
            broadcastRingerMode(i, "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION");
            if (this.mMuteMediaByVibrateOrSilentMode) {
                if (this.mPrevRingerMode == 2 || i == 2) {
                    muteMediaStreamOfSpeaker(i != 2);
                }
            }
        }
    }

    public final void setRingerModeInternal(int i, String str) {
        enforceVolumeController("setRingerModeInternal");
        setRingerMode(i, str, false);
    }

    public final void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) {
        setRingtonePlayer_enforcePermission();
        this.mRingtonePlayer = iRingtonePlayer;
    }

    public final void setRttEnabled(boolean z) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("AS.AudioService", "MODIFY_PHONE_STATE Permission Denial: setRttEnabled from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        synchronized (this.mSettingsLock) {
            try {
                this.mRttEnabled = z;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AudioSystem.setRttEnabled(z);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setSoundSettingEventBroadcastIntent(int i, PendingIntent pendingIntent) {
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
            try {
                ArrayList arrayList = this.mEventReceivers;
                SoundEventReceiver soundEventReceiver = new SoundEventReceiver();
                soundEventReceiver.mEventType = i;
                soundEventReceiver.mEventReceiver = componentName;
                int indexOf = arrayList.indexOf(soundEventReceiver);
                if (i == 0) {
                    if (indexOf < 0) {
                        return;
                    }
                    if (componentName.equals(MediaSessionService.this.mVolumeKeyLongPressReceiver)) {
                        this.mMediaSessionServiceInternal.setVolumeLongPressReceiver(null);
                    }
                    if (componentName.equals(MediaSessionService.this.mHighPriorityMediaKeyReceiver)) {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setSpatializerEnabled(boolean z) {
        setSpatializerEnabled_enforcePermission();
        this.mSpatializerHelper.setFeatureEnabled(z);
    }

    public final void setSpatializerGlobalTransform(float[] fArr) {
        setSpatializerGlobalTransform_enforcePermission();
        Objects.requireNonNull(fArr);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            if (fArr.length != 6) {
                throw new IllegalArgumentException("invalid array size" + fArr.length);
            }
            if (spatializerHelper.checkSpatializer("setGlobalTransform") && spatializerHelper.mIsHeadTrackingSupported) {
                try {
                    spatializerHelper.mSpat.setGlobalTransform(fArr);
                } catch (RemoteException e) {
                    Log.e("AS.SpatializerHelper", "Error calling setGlobalTransform", e);
                }
            }
        }
    }

    public final void setSpatializerParameter(int i, byte[] bArr) {
        setSpatializerParameter_enforcePermission();
        Objects.requireNonNull(bArr);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            int i2 = spatializerHelper.mState;
            if (i2 == 0 || i2 == 1) {
                throw new IllegalStateException("Can't set parameter key:" + i + " without a spatializer");
            }
            if ((i2 == 3 || i2 == 4 || i2 == 5 || i2 == 6) && spatializerHelper.mSpat == null) {
                Log.e("AS.SpatializerHelper", "setParameter(" + i + "): null spatializer in state: " + spatializerHelper.mState);
                return;
            }
            try {
                spatializerHelper.mSpat.setParameter(i, bArr);
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Error in setParameter for key:" + i, e);
            }
        }
    }

    public final void setSpeakerphoneOn(IBinder iBinder, boolean z) {
        if (checkAudioSettingsPermission("setSpeakerphoneOn()")) {
            boolean z2 = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            String str = getPackageName(callingUid)[0];
            StringBuilder m = AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "setSpeakerphoneOn(", ") from u/pid:", "/", z);
            m.append(callingPid);
            m.append(" Package Name:");
            m.append(str);
            String sb = m.toString();
            new MediaMetrics.Item("audio.device.setSpeakerphoneOn").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.STATE, z ? "on" : "off").record();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mDeviceBroker.setSpeakerphoneOn(iBinder, callingUid, z, z2, sb);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void setStreamVolume(int i, int i2, int i3, String str) {
        setStreamVolumeWithAttribution(i, i2, i3, str, null);
    }

    public final void setStreamVolumeForDeviceWithAttribution(int i, int i2, int i3, String str, String str2, int i4) {
        if (i == 10 && !canChangeAccessibilityVolume()) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Trying to call setStreamVolume() for a11y without CHANGE_ACCESSIBILITY_VOLUME  callingPackage=", str, "AS.AudioService");
            return;
        }
        if (i == 0 && i2 == 0) {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Trying to call setStreamVolume() for STREAM_VOICE_CALL and index 0 without MODIFY_PHONE_STATE  callingPackage=", str, "AS.AudioService");
                return;
            }
        }
        sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(2, i, i2, i3, str));
        setStreamVolumeWithAttribution(i, i2, i3, null, str, str, str2, Binder.getCallingUid(), true, true, i4);
    }

    public final void setStreamVolumeForUid(int i, int i2, int i3, String str, int i4, int i5, UserHandle userHandle, int i6) {
        if (Binder.getCallingUid() != 1000) {
            throw new SecurityException("Should only be called from system process");
        }
        setStreamVolumeWithAttribution(i, i2, i3, null, str, str, null, i4, this.mContext.checkPermission("android.permission.MODIFY_AUDIO_SETTINGS", i5, i4) == 0, true, 0);
    }

    public final void setStreamVolumeInt(String str, int i, int i2, int i3, boolean z, boolean z2) {
        if (isFullVolumeDevice(i3)) {
            return;
        }
        VolumeStreamState volumeStreamState = this.mStreamStates[i];
        if (volumeStreamState.setIndex(i2, i3, str, z2) || z) {
            sendMsg(this.mAudioHandler, 0, 2, i3, 0, volumeStreamState, 0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:168:0x0335 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01d5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setStreamVolumeWithAttribution(int r18, int r19, int r20, android.media.AudioDeviceAttributes r21, java.lang.String r22, java.lang.String r23, java.lang.String r24, int r25, boolean r26, boolean r27, int r28) {
        /*
            Method dump skipped, instructions count: 969
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.setStreamVolumeWithAttribution(int, int, int, android.media.AudioDeviceAttributes, java.lang.String, java.lang.String, java.lang.String, int, boolean, boolean, int):void");
    }

    public final void setStreamVolumeWithAttribution(int i, int i2, int i3, String str, String str2) {
        if (this.mHardeningEnforcer.blockVolumeMethod(100)) {
            return;
        }
        setStreamVolumeWithAttributionInt(i, i2, i3, null, str, str2, true);
    }

    public final void setStreamVolumeWithAttributionInt(int i, int i2, int i3, AudioDeviceAttributes audioDeviceAttributes, String str, String str2, boolean z) {
        AudioDeviceAttributes audioDeviceAttributes2;
        if (i == 10 && !canChangeAccessibilityVolume()) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Trying to call setStreamVolume() for a11y without CHANGE_ACCESSIBILITY_VOLUME  callingPackage=", str, "AS.AudioService");
            return;
        }
        if (i == 0 && i2 == 0 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Trying to call setStreamVolume() for STREAM_VOICE_CALL and index 0 without MODIFY_PHONE_STATE  callingPackage=", str, "AS.AudioService");
            return;
        }
        if (i == 11 && this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") != 0) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Trying to call setStreamVolume() for STREAM_ASSISTANT without MODIFY_AUDIO_ROUTING  callingPackage=", str, "AS.AudioService");
            return;
        }
        if (audioDeviceAttributes == null) {
            int deviceForStream = getDeviceForStream(i);
            sVolumeLogger.enqueue(new AudioServiceEvents$VolumeEvent(i, i2, i3, getStreamVolume(i, deviceForStream), 0, str));
            audioDeviceAttributes2 = new AudioDeviceAttributes(deviceForStream, "");
        } else {
            audioDeviceAttributes2 = audioDeviceAttributes;
        }
        setStreamVolumeWithAttribution(i, i2, i3, audioDeviceAttributes2, str, str, str2, Binder.getCallingUid(), this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0, z, 0);
    }

    public final void setSupportedSystemUsages(int[] iArr) {
        setSupportedSystemUsages_enforcePermission();
        for (int i = 0; i < iArr.length; i++) {
            if (!AudioAttributes.isSystemUsage(iArr[i])) {
                throw new IllegalArgumentException("Non-system usage provided: " + iArr[i]);
            }
        }
        synchronized (this.mSupportedSystemUsagesLock) {
            AudioSystem.setSupportedSystemUsages(iArr);
            this.mSupportedSystemUsages = iArr;
        }
    }

    public final boolean setSurroundFormatEnabled(int i, boolean z) {
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
                SettingsAdapter settingsAdapter = this.mSettings;
                ContentResolver contentResolver = this.mContentResolver;
                String join = TextUtils.join(",", enabledFormats);
                settingsAdapter.getClass();
                Settings.Global.putString(contentResolver, "encoded_surround_output_enabled_formats", join);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void setTestDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        Objects.requireNonNull(audioDeviceAttributes);
        enforceModifyAudioRoutingPermission();
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        synchronized (audioDeviceBroker.mDeviceStateLock) {
            AudioDeviceInventory audioDeviceInventory = audioDeviceBroker.mDeviceInventory;
            audioDeviceInventory.getClass();
            AudioDeviceInventory.WiredDeviceConnectionState wiredDeviceConnectionState = new AudioDeviceInventory.WiredDeviceConnectionState(retrieveBluetoothAddress, z ? 1 : 0, "com.android.server.audio");
            wiredDeviceConnectionState.mForTest = true;
            audioDeviceInventory.onSetWiredDeviceConnectionState(wiredDeviceConnectionState);
        }
        sendMsg(this.mAudioHandler, 41, 0, 0, 0, null, 0);
    }

    public final int setUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) {
        Log.d("AS.AudioService", "setUidDeviceAffinity for " + iAudioPolicyCallback.asBinder() + " uid:" + i);
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change device affinity in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (!checkUpdateForPolicy.hasMixRoutedToDevices(iArr, strArr)) {
                    return -1;
                }
                return checkUpdateForPolicy.setUidDeviceAffinities(i, iArr, strArr);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int setUserIdDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) {
        Log.d("AS.AudioService", "setUserIdDeviceAffinity for " + iAudioPolicyCallback.asBinder() + " user:" + i);
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot change device affinity in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                if (!checkUpdateForPolicy.hasMixRoutedToDevices(iArr, strArr)) {
                    return -1;
                }
                return checkUpdateForPolicy.setUserIdDeviceAffinities(i, iArr, strArr);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setVibrateSetting(int i, int i2) {
        if (this.mHasVibrator) {
            this.mVibrateSetting = AudioSystem.getValueForVibrateSetting(this.mVibrateSetting, i, i2);
            broadcastVibrateSetting(i);
        }
    }

    public final void setVolumeController(IVolumeController iVolumeController) {
        enforceVolumeController("set the volume controller");
        if (this.mVolumeController.isSameBinder(iVolumeController)) {
            return;
        }
        this.mVolumeController.postDismiss();
        if (iVolumeController != null) {
            try {
                iVolumeController.asBinder().linkToDeath(new AnonymousClass9(iVolumeController), 0);
            } catch (RemoteException unused) {
            }
        }
        this.mVolumeController.setController(iVolumeController);
        Log.d("AS.AudioService", "Volume controller: " + this.mVolumeController);
    }

    public final void setVolumeGroupVolumeIndex(int i, int i2, int i3, String str, String str2) {
        setVolumeGroupVolumeIndex_enforcePermission();
        SparseArray sparseArray = sVolumeGroupStates;
        if (sparseArray.indexOfKey(i) < 0) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, ": no volume group found for id ", "AS.AudioService");
            return;
        }
        VolumeGroupState volumeGroupState = (VolumeGroupState) sparseArray.get(i);
        EventLogger eventLogger = sVolumeLogger;
        String name = volumeGroupState.mAudioVolumeGroup.name();
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ", user ");
        m.append(getCurrentUserId());
        eventLogger.enqueue(new AudioServiceEvents$VolumeEvent(8, i2, i3, name, m.toString()));
        synchronized (AudioService.this.mSettingsLock) {
            synchronized (VolumeStreamState.class) {
                AudioService audioService = AudioService.this;
                if (!audioService.mUseFixedVolume) {
                    volumeGroupState.setVolumeIndex(i2, audioService.getDeviceForStream(volumeGroupState.mPublicStreamType), i3);
                }
            }
        }
        for (int i4 : volumeGroupState.mAudioVolumeGroup.getLegacyStreamTypes()) {
            try {
                ensureValidStreamType(i4);
                setStreamVolumeWithAttribution(i4, i2, i3, null, str, str, str2, Binder.getCallingUid(), true, true, 0);
            } catch (IllegalArgumentException unused) {
                Log.d("AS.AudioService", DualAppManagerService$$ExternalSyntheticOutline0.m(i, i4, "volume group ", " has internal streams (", "), do not change associated stream volume"));
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
        this.mAudioHandler.post(new AudioService$$ExternalSyntheticLambda4(0, new ContextThemeWrapper(this.mContext, R.style.Theme.DeviceDefault.Light)));
        setStreamVolumeWithAttribution(3, i, 1048576, str, null);
    }

    public final void setVolumePolicy(VolumePolicy volumePolicy) {
        enforceVolumeController("set volume policy");
        if (volumePolicy == null || volumePolicy.equals(this.mVolumePolicy)) {
            return;
        }
        this.mVolumePolicy = volumePolicy;
        Log.d("AS.AudioService", "Volume policy changed: " + this.mVolumePolicy);
    }

    public final void setWiredDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, String str) {
        setWiredDeviceConnectionState_enforcePermission();
        Objects.requireNonNull(audioDeviceAttributes);
        AudioDeviceAttributes retrieveBluetoothAddress = retrieveBluetoothAddress(audioDeviceAttributes);
        if (retrieveBluetoothAddress.getType() == 10 && retrieveBluetoothAddress.getRole() == 2 && retrieveBluetoothAddress.getAudioDescriptors().isEmpty()) {
            retrieveBluetoothAddress = new AudioDeviceAttributes(retrieveBluetoothAddress.getRole(), retrieveBluetoothAddress.getType(), retrieveBluetoothAddress.getAddress(), retrieveBluetoothAddress.getName(), retrieveBluetoothAddress.getAudioProfiles(), new ArrayList(Collections.singletonList(new AudioDescriptor(1, 0, DEFAULT_ARC_AUDIO_DESCRIPTOR))));
        }
        if (i != 1 && i != 0) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid state "));
        }
        new MediaMetrics.Item("audio.service.setWiredDeviceConnectionState").set(MediaMetrics.Property.ADDRESS, retrieveBluetoothAddress.getAddress()).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.DEVICE, AudioSystem.getDeviceName(retrieveBluetoothAddress.getInternalType())).set(MediaMetrics.Property.NAME, retrieveBluetoothAddress.getName()).set(MediaMetrics.Property.STATE, i == 1 ? "connected" : "disconnected").record();
        if (this.mMode.get() == 1) {
            if (i == 1) {
                this.mSensorThread.stopSensor();
            } else {
                this.mSensorThread.startSensor();
            }
        }
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        synchronized (audioDeviceBroker.mDeviceStateLock) {
            audioDeviceBroker.mDeviceInventory.setWiredDeviceConnectionState(retrieveBluetoothAddress, i, str);
        }
        if (retrieveBluetoothAddress.getInternalType() == -2013265920) {
            Slog.d("AS.AudioService", "Hdmi Audio System Client is updated");
            synchronized (this.mHdmiClientLock) {
                this.mHdmiAudioSystemClient = this.mHdmiManager.getAudioSystemClient();
            }
        }
    }

    public final boolean shouldNotificationSoundPlay(AudioAttributes audioAttributes) {
        int i;
        shouldNotificationSoundPlay_enforcePermission();
        Objects.requireNonNull(audioAttributes);
        int legacyStreamType = AudioAttributes.toLegacyStreamType(audioAttributes);
        if (getStreamVolume(legacyStreamType) == 0) {
            Slog.i("AS.AudioService", "shouldNotificationSoundPlay false: muted stream:" + legacyStreamType + " attr:" + audioAttributes);
            return false;
        }
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        synchronized (MediaFocusControl.mAudioFocusLock) {
            try {
                if (!mediaFocusControl.mFocusStack.empty()) {
                    FocusRequester focusRequester = (FocusRequester) mediaFocusControl.mFocusStack.peek();
                    if (focusRequester.mFocusGainRequest == 4) {
                        i = focusRequester.mCallingUid;
                    }
                }
                i = -1;
            } finally {
            }
        }
        if (i == -1 || !this.mRecordMonitor.isRecordingActiveForUid(i)) {
            return true;
        }
        Slog.i("AS.AudioService", "shouldNotificationSoundPlay false: exclusive focus owner recording  uid:" + i + " attr:" + audioAttributes);
        return false;
    }

    public final boolean shouldShowRingtoneVolume() {
        return !Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION;
    }

    public final boolean shouldVibrate(int i) {
        if (!this.mHasVibrator) {
            return false;
        }
        int vibrateSetting = getVibrateSetting(i);
        return vibrateSetting != 1 ? vibrateSetting == 2 && getRingerModeExternal() == 1 : getRingerModeExternal() != 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:112:0x01d3, code lost:
    
        if (android.media.AudioSystem.isStreamActive(3, 0) == false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00e8, code lost:
    
        if (r8 == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0106, code lost:
    
        if (r20 == 3) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0198, code lost:
    
        if (r21 != 1) goto L157;
     */
    /* JADX WARN: Removed duplicated region for block: B:48:0x03c9  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x03f4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x018b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean skipAdjustStreamVolume(int r20, int r21, int r22, int r23, int r24, java.lang.String r25, java.lang.String r26, java.lang.String r27) {
        /*
            Method dump skipped, instructions count: 1014
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.skipAdjustStreamVolume(int, int, int, int, int, java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public final void startBluetoothSco(IBinder iBinder, int i) {
        if (checkAudioSettingsPermission("startBluetoothSco()")) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int i2 = i < 18 ? 0 : -1;
            String m = ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, callingPid, "startBluetoothSco()) from u/pid:", "/");
            new MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.EVENT, "startBluetoothSco").set(MediaMetrics.Property.SCO_AUDIO_MODE, BtHelper.scoAudioModeToString(i2)).record();
            startBluetoothScoInt(callingUid, iBinder, m, i2);
        }
    }

    public final void startBluetoothScoInt(int i, IBinder iBinder, String str, int i2) {
        MediaMetrics.Item item = new MediaMetrics.Item("audio.bluetooth").set(MediaMetrics.Property.EVENT, "startBluetoothScoInt").set(MediaMetrics.Property.SCO_AUDIO_MODE, BtHelper.scoAudioModeToString(i2));
        if (!checkAudioSettingsPermission("startBluetoothSco()") || !this.mSystemReady) {
            item.set(MediaMetrics.Property.EARLY_RETURN, "permission or systemReady").record();
            return;
        }
        boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
            audioDeviceBroker.getClass();
            Log.v("AS.AudioDeviceBroker", "startBluetoothScoForClient, uid: " + i);
            audioDeviceBroker.sendLMsgNoDelay(42, 2, new AudioDeviceBroker.CommunicationDeviceInfo(iBinder, i, new AudioDeviceAttributes(16, ""), true, i2, str, z));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            item.record();
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void startBluetoothScoVirtualCall(IBinder iBinder) {
        if (checkAudioSettingsPermission("startBluetoothScoVirtualCall()")) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            String m = ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, callingPid, "startBluetoothScoVirtualCall()) from u/pid:", "/");
            new MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.EVENT, "startBluetoothScoVirtualCall").set(MediaMetrics.Property.SCO_AUDIO_MODE, BtHelper.scoAudioModeToString(0)).record();
            startBluetoothScoInt(callingUid, iBinder, m, 0);
        }
    }

    public final void startLoudnessCodecUpdates(int i) {
        final LoudnessCodecHelper loudnessCodecHelper = this.mLoudnessCodecHelper;
        loudnessCodecHelper.getClass();
        final int callingPid = Binder.getCallingPid();
        LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new LoudnessCodecHelper.LoudnessTrackId(i, callingPid);
        synchronized (loudnessCodecHelper.mLock) {
            try {
                if (loudnessCodecHelper.mStartedConfigInfo.containsKey(loudnessTrackId)) {
                    Log.w("AS.LoudnessCodecHelper", "Already started loudness updates for config: " + loudnessTrackId);
                    return;
                }
                loudnessCodecHelper.mStartedConfigInfo.put(loudnessTrackId, new HashSet());
                final HashSet hashSet = new HashSet();
                loudnessCodecHelper.mStartedConfigPiids.put(loudnessTrackId, hashSet);
                SafeCloseable create = ClearCallingIdentityContext.create();
                try {
                    loudnessCodecHelper.mAudioService.getActivePlaybackConfigurations().stream().filter(new LoudnessCodecHelper$$ExternalSyntheticLambda1(i, callingPid, 0)).forEach(new Consumer() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            LoudnessCodecHelper loudnessCodecHelper2 = LoudnessCodecHelper.this;
                            HashSet hashSet2 = hashSet;
                            int i2 = callingPid;
                            loudnessCodecHelper2.getClass();
                            int playerInterfaceId = ((AudioPlaybackConfiguration) obj).getPlayerInterfaceId();
                            synchronized (loudnessCodecHelper2.mLock) {
                                hashSet2.add(Integer.valueOf(playerInterfaceId));
                                loudnessCodecHelper2.mPiidToPidCache.put(playerInterfaceId, i2);
                                LoudnessCodecHelper.sLogger.enqueue(new AudioServiceEvents$LoudnessEvent(0, playerInterfaceId, i2));
                            }
                        }
                    });
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
            } finally {
            }
        }
    }

    public final AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) {
        AudioRoutesInfo audioRoutesInfo;
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        synchronized (audioDeviceBroker.mDeviceStateLock) {
            AudioDeviceInventory audioDeviceInventory = audioDeviceBroker.mDeviceInventory;
            synchronized (audioDeviceInventory.mCurAudioRoutes) {
                audioRoutesInfo = new AudioRoutesInfo(audioDeviceInventory.mCurAudioRoutes);
                audioDeviceInventory.mRoutesObservers.register(iAudioRoutesObserver);
            }
        }
        return audioRoutesInfo;
    }

    public final void stopBluetoothSco(IBinder iBinder) {
        if (checkAudioSettingsPermission("stopBluetoothSco()") && this.mSystemReady) {
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            String m = ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, callingPid, "stopBluetoothSco()) from u/pid:", "/");
            boolean z = this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
                audioDeviceBroker.getClass();
                Log.v("AS.AudioDeviceBroker", "stopBluetoothScoForClient, uid: " + callingUid);
                audioDeviceBroker.sendLMsgNoDelay(42, 2, new AudioDeviceBroker.CommunicationDeviceInfo(iBinder, callingUid, new AudioDeviceAttributes(16, ""), false, -1, m, z));
                Binder.restoreCallingIdentity(clearCallingIdentity);
                new MediaMetrics.Item("audio.bluetooth").setUid(callingUid).setPid(callingPid).set(MediaMetrics.Property.EVENT, "stopBluetoothSco").set(MediaMetrics.Property.SCO_AUDIO_MODE, "SCO_MODE_UNDEFINED").record();
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final void stopLoudnessCodecUpdates(int i) {
        LoudnessCodecHelper loudnessCodecHelper = this.mLoudnessCodecHelper;
        loudnessCodecHelper.getClass();
        LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new LoudnessCodecHelper.LoudnessTrackId(i, Binder.getCallingPid());
        synchronized (loudnessCodecHelper.mLock) {
            try {
                if (!loudnessCodecHelper.mStartedConfigInfo.containsKey(loudnessTrackId)) {
                    Log.w("AS.LoudnessCodecHelper", "Loudness updates are already stopped config: " + loudnessTrackId);
                    return;
                }
                Set<Integer> set = (Set) loudnessCodecHelper.mStartedConfigPiids.get(loudnessTrackId);
                if (set == null) {
                    Log.e("AS.LoudnessCodecHelper", "Loudness updates are already stopped config: " + loudnessTrackId);
                    return;
                }
                for (Integer num : set) {
                    LoudnessCodecHelper.sLogger.enqueue(new AudioServiceEvents$LoudnessEvent(1, num.intValue(), loudnessCodecHelper.mPiidToPidCache.get(num.intValue(), -1)));
                    loudnessCodecHelper.mPiidToDeviceIdCache.delete(num.intValue());
                    loudnessCodecHelper.mPiidToPidCache.delete(num.intValue());
                }
                loudnessCodecHelper.mStartedConfigPiids.remove(loudnessTrackId);
                loudnessCodecHelper.mStartedConfigInfo.remove(loudnessTrackId);
            } finally {
            }
        }
    }

    public final boolean supportsBluetoothVariableLatency() {
        supportsBluetoothVariableLatency_enforcePermission();
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

    public final int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) {
        int intValue;
        AudioAttributes audioAttributes;
        if (playerIdCard != null && (audioAttributes = playerIdCard.mAttributes) != null) {
            validateAudioAttributesUsage(audioAttributes);
        }
        PlaybackActivityMonitor playbackActivityMonitor = this.mPlaybackMonitor;
        playbackActivityMonitor.getClass();
        int newAudioPlayerId = AudioSystem.newAudioPlayerId();
        Log.v("AS.PlaybackActivityMon", "trackPlayer() new piid=" + newAudioPlayerId);
        if (newAudioPlayerId == -1) {
            Log.w("AS.PlaybackActivityMon", "invalid piid assigned from AudioSystem");
        } else {
            AudioPlaybackConfiguration audioPlaybackConfiguration = new AudioPlaybackConfiguration(playerIdCard, newAudioPlayerId, Binder.getCallingUid(), Binder.getCallingPid());
            if (audioPlaybackConfiguration.getAudioAttributes().getTags().contains("FAST_PRE_OPEN")) {
                Log.i("AS.PlaybackActivityMon", "Skip piid " + newAudioPlayerId + " by dummy audio for fast pre open");
            } else {
                audioPlaybackConfiguration.init();
                synchronized (playbackActivityMonitor.mAllowedCapturePolicies) {
                    try {
                        int clientUid = audioPlaybackConfiguration.getClientUid();
                        if (playbackActivityMonitor.mAllowedCapturePolicies.containsKey(Integer.valueOf(clientUid)) && audioPlaybackConfiguration.getAudioAttributes().getAllowedCapturePolicy() < (intValue = ((Integer) playbackActivityMonitor.mAllowedCapturePolicies.get(Integer.valueOf(clientUid))).intValue())) {
                            audioPlaybackConfiguration.handleAudioAttributesEvent(new AudioAttributes.Builder(audioPlaybackConfiguration.getAudioAttributes()).setAllowedCapturePolicy(intValue).build());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                String[] packagesForUid = playbackActivityMonitor.mContext.getPackageManager().getPackagesForUid(audioPlaybackConfiguration.getClientUid());
                PlaybackActivityMonitor.sEventLogger.enqueue(new PlaybackActivityMonitor.NewPlayerEvent(audioPlaybackConfiguration, (packagesForUid == null || packagesForUid.length <= 0) ? null : packagesForUid[0]));
                synchronized (playbackActivityMonitor.mPlayerLock) {
                    playbackActivityMonitor.mPlayers.put(Integer.valueOf(newAudioPlayerId), audioPlaybackConfiguration);
                    playbackActivityMonitor.maybeMutePlayerAwaitingConnection(audioPlaybackConfiguration);
                }
            }
        }
        return newAudioPlayerId;
    }

    public final int trackRecorder(IBinder iBinder) {
        RecordingActivityMonitor recordingActivityMonitor = this.mRecordMonitor;
        recordingActivityMonitor.getClass();
        if (iBinder == null) {
            Log.e("AudioService.RecordingActivityMonitor", "trackRecorder called with null token");
            return -1;
        }
        int newAudioRecorderId = AudioSystem.newAudioRecorderId();
        RecordingActivityMonitor.RecorderDeathHandler recorderDeathHandler = new RecordingActivityMonitor.RecorderDeathHandler(newAudioRecorderId, iBinder);
        try {
            iBinder.linkToDeath(recorderDeathHandler, 0);
            synchronized (recordingActivityMonitor.mRecordStates) {
                ((ArrayList) recordingActivityMonitor.mRecordStates).add(new RecordingActivityMonitor.RecordingState(newAudioRecorderId, recorderDeathHandler));
            }
            return newAudioRecorderId;
        } catch (RemoteException e) {
            Log.w("AudioService.RecordingActivityMonitor", "Could not link to recorder death", e);
            return -1;
        }
    }

    public final void unloadSoundEffects() {
        sendMsg(this.mAudioHandler, 15, 2, 0, 0, null, 0);
    }

    public final void unregisterAudioFocusClient(String str) {
        new MediaMetrics.Item("audio.service.focus").set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "unregisterAudioFocusClient").record();
        MediaFocusControl mediaFocusControl = this.mMediaFocusControl;
        mediaFocusControl.getClass();
        synchronized (MediaFocusControl.mAudioFocusLock) {
            mediaFocusControl.removeFocusStackEntry(str, false, true);
        }
    }

    public final void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        unregisterAudioPolicyInt(iAudioPolicyCallback, "unregisterAudioPolicy");
    }

    public final void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        unregisterAudioPolicyInt(iAudioPolicyCallback, "unregisterAudioPolicyAsync");
    }

    public final void unregisterAudioPolicyInt(IAudioPolicyCallback iAudioPolicyCallback, String str) {
        EventLogger eventLogger = this.mDynPolicyLogger;
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " for ");
        m.append(iAudioPolicyCallback.asBinder());
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(m.toString());
        stringEvent.printLog(0, "AS.AudioService");
        eventLogger.enqueue(stringEvent);
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy audioPolicyProxy = (AudioPolicyProxy) this.mAudioPolicies.remove(iAudioPolicyCallback.asBinder());
                if (audioPolicyProxy != null) {
                    iAudioPolicyCallback.asBinder().unlinkToDeath(audioPolicyProxy, 0);
                    audioPolicyProxy.release();
                    return;
                }
                Slog.w("AS.AudioService", "Trying to unregister unknown audio policy for pid " + Binder.getCallingPid() + " / uid " + Binder.getCallingUid());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) {
        checkMonitorAudioServerStatePermission();
        synchronized (this.mAudioServerStateListeners) {
            try {
                AsdProxy asdProxy = (AsdProxy) this.mAudioServerStateListeners.remove(iAudioServerStateDispatcher.asBinder());
                if (asdProxy != null) {
                    iAudioServerStateDispatcher.asBinder().unlinkToDeath(asdProxy, 0);
                    return;
                }
                Slog.w("AS.AudioService", "Trying to unregister unknown audioserver state dispatcher for pid " + Binder.getCallingPid() + " / uid " + Binder.getCallingUid());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterCapturePresetDevicesRoleDispatcher(ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        if (iCapturePresetDevicesRoleDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.mDeviceInventory.mDevRoleCapturePresetDispatchers.unregister(iCapturePresetDevicesRoleDispatcher);
    }

    public final void unregisterCommunicationDeviceDispatcher(ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
        if (iCommunicationDeviceDispatcher == null) {
            return;
        }
        this.mDeviceBroker.mCommDevDispatchers.unregister(iCommunicationDeviceDispatcher);
    }

    public final void unregisterCurrentDeviceChangedCallback(int i, SensorHandleThread$$ExternalSyntheticLambda0 sensorHandleThread$$ExternalSyntheticLambda0) {
        VolumeStreamState volumeStreamState;
        VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
        if (volumeStreamStateArr == null || (volumeStreamState = volumeStreamStateArr[i]) == null) {
            return;
        }
        CurrentDeviceManager currentDeviceManager = volumeStreamState.mCurrentDeviceManager;
        currentDeviceManager.getClass();
        synchronized (CurrentDeviceManager.lock) {
            ((HashSet) currentDeviceManager.callbacks).remove(new CurrentDeviceManager.CallbackRecord(sensorHandleThread$$ExternalSyntheticLambda0));
        }
    }

    public final void unregisterHeadToSoundstagePoseCallback(ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) {
        unregisterHeadToSoundstagePoseCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerHeadToSoundStagePoseCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.mHeadPoseCallbacks.unregister(iSpatializerHeadToSoundStagePoseCallback);
        }
    }

    public final void unregisterLoudnessCodecUpdatesDispatcher(ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) {
        this.mLoudnessCodecHelper.mLoudnessUpdateDispatchers.unregister(iLoudnessCodecUpdatesDispatcher);
    }

    public final void unregisterModeDispatcher(IAudioModeDispatcher iAudioModeDispatcher) {
        this.mModeDispatchers.unregister(iAudioModeDispatcher);
    }

    public final void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) {
        this.mPlaybackMonitor.unregisterPlaybackCallback(iPlaybackConfigDispatcher);
    }

    public final void unregisterPreferredMixerAttributesDispatcher(IPreferredMixerAttributesDispatcher iPreferredMixerAttributesDispatcher) {
        if (iPreferredMixerAttributesDispatcher == null) {
            return;
        }
        this.mPrefMixerAttrDispatcher.unregister(iPreferredMixerAttributesDispatcher);
    }

    public final void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        this.mRecordMonitor.unregisterRecordingCallback(iRecordingConfigDispatcher);
    }

    public final void unregisterSpatializerCallback(ISpatializerCallback iSpatializerCallback) {
        Objects.requireNonNull(iSpatializerCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.mStateCallbacks.unregister(iSpatializerCallback);
        }
    }

    public final void unregisterSpatializerHeadTrackingCallback(ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) {
        unregisterSpatializerHeadTrackingCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerHeadTrackingModeCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.mHeadTrackingModeCallbacks.unregister(iSpatializerHeadTrackingModeCallback);
        }
    }

    public final void unregisterSpatializerOutputCallback(ISpatializerOutputCallback iSpatializerOutputCallback) {
        unregisterSpatializerOutputCallback_enforcePermission();
        Objects.requireNonNull(iSpatializerOutputCallback);
        SpatializerHelper spatializerHelper = this.mSpatializerHelper;
        synchronized (spatializerHelper) {
            spatializerHelper.mOutputCallbacks.unregister(iSpatializerOutputCallback);
        }
    }

    public final void unregisterStrategyNonDefaultDevicesDispatcher(IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        if (iStrategyNonDefaultDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.mDeviceInventory.mNonDefDevDispatchers.unregister(iStrategyNonDefaultDevicesDispatcher);
    }

    public final void unregisterStrategyPreferredDevicesDispatcher(IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        if (iStrategyPreferredDevicesDispatcher == null) {
            return;
        }
        enforceModifyAudioRoutingPermission();
        this.mDeviceBroker.mDeviceInventory.mPrefDevDispatchers.unregister(iStrategyPreferredDevicesDispatcher);
    }

    public final void updateA11yVolumeAlias(boolean z) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("Accessibility volume enabled = ", "AS.AudioService", z);
        if (this.mIsSingleVolume) {
            Log.d("AS.AudioService", "Accessibility volume is not set on single volume device");
        } else if (sIndependentA11yVolume != z) {
            sIndependentA11yVolume = z;
            updateStreamVolumeAlias("AS.AudioService", true);
            this.mVolumeController.setA11yMode(sIndependentA11yVolume ? 1 : 0);
            this.mVolumeController.postVolumeChanged(10, 0);
        }
    }

    public final void updateActiveAssistantServiceUids() {
        int[] iArr;
        synchronized (this.mSettingsLock) {
            iArr = this.mActiveAssistantServiceUids;
        }
        AudioSystem.setActiveAssistantServicesUids(iArr);
    }

    public final void updateAssistantUIdLocked(boolean z) {
        int i;
        int i2;
        RoleManager roleManager;
        RoleObserver roleObserver = this.mRoleObserver;
        String str = "";
        if (roleObserver != null && (roleManager = roleObserver.mRm) != null) {
            List roleHolders = roleManager.getRoleHolders("android.app.role.ASSISTANT");
            if (roleHolders.size() != 0) {
                str = (String) roleHolders.get(0);
            }
        }
        if (TextUtils.isEmpty(str)) {
            SettingsAdapter settingsAdapter = this.mSettings;
            ContentResolver contentResolver = this.mContentResolver;
            settingsAdapter.getClass();
            String stringForUser = Settings.Secure.getStringForUser(contentResolver, "voice_interaction_service", -2);
            if (TextUtils.isEmpty(stringForUser)) {
                SettingsAdapter settingsAdapter2 = this.mSettings;
                ContentResolver contentResolver2 = this.mContentResolver;
                settingsAdapter2.getClass();
                stringForUser = Settings.Secure.getStringForUser(contentResolver2, "assistant", -2);
            }
            if (!TextUtils.isEmpty(stringForUser)) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(stringForUser);
                if (unflattenFromString == null) {
                    HeimdAllFsService$$ExternalSyntheticOutline0.m("Invalid service name for voice_interaction_service: ", stringForUser, "AS.AudioService");
                    return;
                }
                str = unflattenFromString.getPackageName();
            }
        }
        if (!TextUtils.isEmpty(str)) {
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager.checkPermission("android.permission.CAPTURE_AUDIO_HOTWORD", str) == 0) {
                try {
                    i = packageManager.getPackageUidAsUser(str, getCurrentUserId());
                } catch (PackageManager.NameNotFoundException unused) {
                    StorageManagerService$$ExternalSyntheticOutline0.m("updateAssistantUId() could not find UID for package: ", str, "AS.AudioService");
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

    public final void updateAudioModeHandlers(List list, List list2) {
        synchronized (this.mDeviceBroker.mSetModeLock) {
            try {
                Iterator it = this.mSetModeDeathHandlers.iterator();
                int i = 2;
                int i2 = 6000;
                boolean z = false;
                while (it.hasNext()) {
                    SetModeDeathHandler setModeDeathHandler = (SetModeDeathHandler) it.next();
                    boolean isActive = setModeDeathHandler.isActive();
                    if (list != null) {
                        setModeDeathHandler.mPlaybackActive = false;
                        Iterator it2 = list.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it2.next();
                            int usage = audioPlaybackConfiguration.getAudioAttributes().getUsage();
                            if (audioPlaybackConfiguration.getClientUid() == setModeDeathHandler.mUid && (usage == 2 || usage == 3)) {
                                if (audioPlaybackConfiguration.isActive()) {
                                    setModeDeathHandler.mPlaybackActive = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (list2 != null) {
                        setModeDeathHandler.mRecordingActive = false;
                        Iterator it3 = list2.iterator();
                        while (true) {
                            if (!it3.hasNext()) {
                                break;
                            }
                            AudioRecordingConfiguration audioRecordingConfiguration = (AudioRecordingConfiguration) it3.next();
                            if (audioRecordingConfiguration.getClientUid() == setModeDeathHandler.mUid && !audioRecordingConfiguration.isClientSilenced() && audioRecordingConfiguration.getAudioSource() == 7) {
                                setModeDeathHandler.mRecordingActive = true;
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateDefaultVolumes() {
        for (int i = 0; i < this.mStreamStates.length; i++) {
            int i2 = mStreamVolumeAlias[i];
            if (this.mUseVolumeGroupAliases) {
                int[] iArr = AudioSystem.DEFAULT_STREAM_VOLUME;
                if (iArr[i] == -1) {
                    int rescaleIndex = (rescaleIndex(iArr[3] * 10, 3, i) + 5) / 10;
                    if (rescaleIndex < MIN_STREAM_VOLUME[i] || rescaleIndex > MAX_STREAM_VOLUME[i]) {
                        i2 = 3;
                    } else {
                        AudioSystem.DEFAULT_STREAM_VOLUME[i] = rescaleIndex;
                    }
                }
            }
            if (i != i2) {
                int[] iArr2 = AudioSystem.DEFAULT_STREAM_VOLUME;
                iArr2[i] = (rescaleIndex(iArr2[i2] * 10, i2, i) + 5) / 10;
            }
        }
    }

    public final int updateFlagsForSamsungVolume(int i, int i2, int i3, int i4) {
        if (FactoryUtils.isFactoryMode() && 2 == i && i2 == 0 && i3 > 0) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i3, "sendVolumeUpdate: enforce to FLAG_PLAY_SOUND volume index = ", "AS.AudioService");
            i2 |= 4;
        }
        if (this.mDeviceBroker.mDualA2dpManager.mDualModeEnabled) {
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

    public final void updateHdmiCecSinkLocked(boolean z) {
        SettingsAdapter settingsAdapter = this.mSettings;
        ContentResolver contentResolver = this.mContentResolver;
        String str = "AudioService_DeviceVolumeBehavior_" + AudioSystem.getOutputDeviceName(1024);
        settingsAdapter.getClass();
        if (Settings.System.getIntForUser(contentResolver, str, -1, -2) != -1) {
            return;
        }
        if (z) {
            Log.d("AS.AudioService", "CEC sink: setting HDMI as full vol device");
            setDeviceVolumeBehaviorInternal(new AudioDeviceAttributes(1024, ""), 1, "AudioService.updateHdmiCecSinkLocked()");
        } else {
            Log.d("AS.AudioService", "TV, no CEC: setting HDMI as regular vol device");
            setDeviceVolumeBehaviorInternal(new AudioDeviceAttributes(1024, ""), 0, "AudioService.updateHdmiCecSinkLocked()");
        }
        sendMsg(this.mAudioHandler, 33, 2, 1024, 0, "HdmiPlaybackClient.DisplayStatusCallback", 0);
    }

    public final void updateMasterBalance(ContentResolver contentResolver) {
        float floatForUser = Settings.System.getFloatForUser(contentResolver, "master_balance", FullScreenMagnificationGestureHandler.MAX_SCALE, -2);
        Log.d("AS.AudioService", String.format("Master balance %f", Float.valueOf(floatForUser)));
        if (AudioSystem.setMasterBalance(floatForUser) != 0) {
            Log.e("AS.AudioService", String.format("setMasterBalance failed for %f", Float.valueOf(floatForUser)));
        }
        AudioServiceExt audioServiceExt = this.mExt;
        audioServiceExt.mAudioSystem.setParameters("l_speaker_balance=" + Settings.System.getFloatForUser(audioServiceExt.mCr, "speaker_balance", FullScreenMagnificationGestureHandler.MAX_SCALE, -2));
        audioServiceExt.mMainBalance = floatForUser;
        AudioSettingsHelper audioSettingsHelper = audioServiceExt.mSettingsHelper;
        if (Float.compare(audioServiceExt.mMainBalance, (audioSettingsHelper.getIntValue(50, "sound_balance") - 50) / 50.0f) != 0) {
            audioSettingsHelper.removeValue("sound_balance");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v2 */
    public final void updateMasterMono(ContentResolver contentResolver) {
        this.mSettings.getClass();
        ?? r3 = Settings.System.getIntForUser(contentResolver, "master_mono", 0, -2) != 1 ? 0 : 1;
        Log.d("AS.AudioService", "Master mono " + ((boolean) r3));
        AudioSystem.setMasterMono((boolean) r3);
        this.mSettings.getClass();
        int intForUser = Settings.System.getIntForUser(contentResolver, "mono_audio_type", 0, -2);
        this.mAudioSystem.setParameters("l_mono_type=" + intForUser);
        AudioServiceExt audioServiceExt = this.mExt;
        audioServiceExt.mMainMono = r3;
        AudioSettingsHelper audioSettingsHelper = audioServiceExt.mSettingsHelper;
        if (r3 != audioSettingsHelper.getIntValue(0, "mono_audio_db")) {
            audioSettingsHelper.removeValue("mono_audio_db");
        }
    }

    public final int updateMixingRulesForPolicy(AudioMix[] audioMixArr, AudioMixingRule[] audioMixingRuleArr, IAudioPolicyCallback iAudioPolicyCallback) {
        updateMixingRulesForPolicy_enforcePermission();
        Objects.requireNonNull(audioMixArr);
        Objects.requireNonNull(audioMixingRuleArr);
        Objects.requireNonNull(iAudioPolicyCallback);
        if (audioMixArr.length != audioMixingRuleArr.length) {
            Log.e("AS.AudioService", "Provided list of audio mixes to update and corresponding mixing rules have mismatching length (mixesToUpdate.length = " + audioMixArr.length + ", updatedMixingRules.length = " + audioMixingRuleArr.length + ").");
            return -1;
        }
        Log.d("AS.AudioService", "updateMixingRules for " + iAudioPolicyCallback.asBinder() + "with mix rules: ");
        synchronized (this.mAudioPolicies) {
            try {
                AudioPolicyProxy checkUpdateForPolicy = checkUpdateForPolicy(iAudioPolicyCallback, "Cannot add AudioMix in audio policy");
                if (checkUpdateForPolicy == null) {
                    return -1;
                }
                return checkUpdateForPolicy.updateMixingRules(audioMixArr, audioMixingRuleArr) == 0 ? 0 : -1;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a8 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateRingerAndZenModeAffectedStreams() {
        /*
            r10 = this;
            boolean r0 = r10.mSystemReady
            r1 = 1
            r2 = 2
            r3 = 0
            if (r0 != 0) goto L9
        L7:
            r0 = r3
            goto L4d
        L9:
            android.app.NotificationManager r0 = r10.mNm
            int r0 = r0.getZenMode()
            if (r0 != r2) goto L14
            r0 = 62
            goto L46
        L14:
            r4 = 3
            if (r0 != r4) goto L1a
            r0 = 38
            goto L46
        L1a:
            if (r0 != r1) goto L45
            android.app.NotificationManager r0 = r10.mNm
            android.app.NotificationManager$Policy r0 = r0.getConsolidatedNotificationPolicy()
            int r4 = r0.priorityCategories
            r5 = r4 & 32
            if (r5 != 0) goto L2b
            r5 = 16
            goto L2c
        L2b:
            r5 = r3
        L2c:
            r6 = r4 & 64
            if (r6 != 0) goto L32
            r5 = r5 | 8
        L32:
            r4 = r4 & 128(0x80, float:1.794E-43)
            if (r4 != 0) goto L39
            r4 = r5 | 2
            goto L3a
        L39:
            r4 = r5
        L3a:
            boolean r0 = android.service.notification.ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(r0)
            if (r0 == 0) goto L43
            r0 = r4 | 36
            goto L46
        L43:
            r0 = r4
            goto L46
        L45:
            r0 = r3
        L46:
            int r4 = r10.mZenModeAffectedStreams
            if (r4 == r0) goto L7
            r10.mZenModeAffectedStreams = r0
            r0 = r1
        L4d:
            com.android.server.audio.SettingsAdapter r4 = r10.mSettings
            android.content.ContentResolver r5 = r10.mContentResolver
            r4.getClass()
            java.lang.String r4 = "mode_ringer_streams_affected"
            r6 = 166(0xa6, float:2.33E-43)
            r7 = -2
            int r5 = android.provider.Settings.System.getIntForUser(r5, r4, r6, r7)
            boolean r6 = r10.mIsSingleVolume
            if (r6 == 0) goto L64
            r5 = r3
            goto L6c
        L64:
            android.media.AudioManagerInternal$RingerModeDelegate r6 = r10.mRingerModeDelegate
            if (r6 == 0) goto L6c
            int r5 = r6.getRingerModeAffectedStreams(r5)
        L6c:
            r6 = r5 & (-137(0xffffffffffffff77, float:NaN))
            int[] r8 = com.android.server.audio.AudioService.mStreamVolumeAlias
            r9 = 8
            r8 = r8[r9]
            if (r8 != r2) goto L79
            r2 = r6 | 256(0x100, float:3.59E-43)
            goto L7b
        L79:
            r2 = r5 & (-393(0xfffffffffffffe77, float:NaN))
        L7b:
            com.android.media.audio.Flags.ringerModeAffectsAlarm()
            boolean r5 = r10.mRingerModeAffectsAlarm
            if (r5 == 0) goto L97
            com.android.server.audio.SettingsAdapter r5 = r10.mSettings
            android.content.ContentResolver r6 = r10.mContentResolver
            r5.getClass()
            java.lang.String r5 = "mute_alarm_stream_with_ringer_mode"
            int r3 = android.provider.Settings.Global.getInt(r6, r5, r3)
            if (r3 == 0) goto L95
            r2 = r2 | 16
            goto L97
        L95:
            r2 = r2 & (-17)
        L97:
            int r3 = r10.mRingerModeAffectedStreams
            if (r2 == r3) goto La8
            com.android.server.audio.SettingsAdapter r0 = r10.mSettings
            android.content.ContentResolver r3 = r10.mContentResolver
            r0.getClass()
            android.provider.Settings.System.putIntForUser(r3, r4, r2, r7)
            r10.mRingerModeAffectedStreams = r2
            return r1
        La8:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioService.updateRingerAndZenModeAffectedStreams():boolean");
    }

    public final void updateStreamVolumeAlias(String str, boolean z) {
        int i = 3;
        int i2 = sIndependentA11yVolume ? 10 : 3;
        int i3 = this.mContext.getResources().getBoolean(R.bool.config_use_strict_phone_number_comparation_for_russia) ? 11 : 3;
        if (this.mIsSingleVolume) {
            mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_TELEVISION.clone();
        } else if (this.mUseVolumeGroupAliases) {
            mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_NONE.clone();
            i = 8;
        } else {
            if (this.mPlatformType != 1) {
                mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_DEFAULT.clone();
                if (Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION) {
                    i = 5;
                }
            } else {
                mStreamVolumeAlias = (int[]) this.STREAM_VOLUME_ALIAS_VOICE.clone();
                i = 2;
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
                    VolumeStreamState[] volumeStreamStateArr2 = this.mStreamStates;
                    VolumeStreamState volumeStreamState = volumeStreamStateArr2[10];
                    String str2 = Settings.System.VOLUME_SETTINGS_INT[i2];
                    volumeStreamState.mVolumeIndexSettingName = str2;
                    VolumeGroupState volumeGroupState = volumeStreamState.mVolumeGroupState;
                    if (volumeGroupState != null) {
                        volumeGroupState.mSettingName = str2;
                    }
                    volumeStreamState.setAllIndexes(volumeStreamStateArr2[i2], str);
                }
            }
            if (sIndependentA11yVolume) {
                this.mStreamStates[10].readSettings();
            }
            setRingerModeInt(getRingerModeInternal(), false);
            sendMsg(this.mAudioHandler, 10, 2, 0, 0, this.mStreamStates[8], 0);
            sendMsg(this.mAudioHandler, 10, 2, 0, 0, this.mStreamStates[10], 0);
        }
        int beginBroadcast = this.mStreamAliasingDispatchers.beginBroadcast();
        for (int i4 = 0; i4 < beginBroadcast; i4++) {
            try {
                this.mStreamAliasingDispatchers.getBroadcastItem(i4).dispatchStreamAliasingChanged();
            } catch (RemoteException e) {
                Log.e("AS.AudioService", "Error on stream alias update dispatch", e);
            }
        }
        this.mStreamAliasingDispatchers.finishBroadcast();
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
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "Vibrator(", ") is not found", "AS.AudioService");
            }
        }
        if (arrayList.isEmpty()) {
            Slog.w("AS.AudioService", "Cannot find any available vibrator");
        } else {
            AudioSystem.setVibratorInfos(arrayList);
        }
    }

    public final void updateVolumeStates(int i, int i2, String str) {
        boolean z;
        if (i == 4194304) {
            i = 2;
        }
        VolumeStreamState volumeStreamState = this.mStreamStates[i2];
        volumeStreamState.getClass();
        synchronized (VolumeStreamState.class) {
            z = volumeStreamState.mIndexMap.get(i, -1) != -1;
        }
        if (!z) {
            VolumeStreamState[] volumeStreamStateArr = this.mStreamStates;
            volumeStreamStateArr[i2].setIndex(volumeStreamStateArr[mStreamVolumeAlias[i2]].getIndex(1073741824), i, str, true);
        }
        Iterator it = getDevicesForAttributesInt(new AudioAttributes.Builder().setInternalLegacyStreamType(i2).build(), true).iterator();
        while (it.hasNext()) {
            if (((AudioDeviceAttributes) it.next()).getType() == AudioDeviceInfo.convertInternalDeviceToDeviceType(i)) {
                this.mStreamStates[i2].checkFixedVolumeDevices();
                if (isStreamMute(i2)) {
                    if (((HashSet) this.mFullVolumeDevices).contains(Integer.valueOf(i))) {
                        this.mStreamStates[i2].mute("updateVolumeStates(" + str, false);
                    }
                }
            }
        }
    }

    public final void validateAudioAttributesUsage(AudioAttributes audioAttributes) {
        int systemUsage = audioAttributes.getSystemUsage();
        if (AudioAttributes.isSystemUsage(systemUsage)) {
            if ((systemUsage != 17 || (audioAttributes.getAllFlags() & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT) == 0 || !callerHasPermission("android.permission.CALL_AUDIO_INTERCEPTION")) && !callerHasPermission("android.permission.MODIFY_AUDIO_ROUTING")) {
                throw new SecurityException("Missing MODIFY_AUDIO_ROUTING permission");
            }
            if (isSupportedSystemUsage(systemUsage)) {
                return;
            }
            throw new IllegalArgumentException("Unsupported usage " + AudioAttributes.usageToString(systemUsage));
        }
    }

    public final boolean volumeAdjustmentAllowedByDnd(int i, int i2) {
        int zenMode = this.mNm.getZenMode();
        if ((zenMode != 1 && zenMode != 2 && zenMode != 3) || !isStreamMutedByRingerOrZenMode(i)) {
            return true;
        }
        if (this.mUseVolumeGroupAliases) {
            int[] iArr = this.STREAM_VOLUME_ALIAS_VOICE;
            if (iArr[i] == iArr[2]) {
                return true;
            }
        } else if (i == mStreamVolumeAlias[2]) {
            return true;
        }
        return (i2 & 2) != 0;
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

    public final boolean wasStreamActiveRecently(int i, int i2) {
        this.mAudioSystem.getClass();
        if (!AudioSystem.isStreamActive(i, i2)) {
            this.mAudioSystem.getClass();
            if (!AudioSystem.isStreamActiveRemotely(i, i2)) {
                return false;
            }
        }
        return true;
    }

    public final boolean wouldToggleZenMode(int i) {
        if (getRingerModeExternal() != 0 || i == 0) {
            return getRingerModeExternal() != 0 && i == 0;
        }
        return true;
    }
}
