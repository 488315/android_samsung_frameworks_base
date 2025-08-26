package com.android.systemui.volume;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.IAudioService;
import android.media.IVolumeController;
import android.media.MediaRouter2Manager;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.volume.MediaSessions;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.interfaces.connectivity.SoundCraftManager;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.audio.soundcraft.model.common.VolumeModel;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.RingerModeLiveData;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import com.android.systemui.volume.shared.VolumeLogger;
import com.android.systemui.volume.soundassistant.SoundAssistantChecker;
import com.android.systemui.volume.util.BluetoothA2dpUtil;
import com.android.systemui.volume.util.BluetoothAdapterWrapper;
import com.android.systemui.volume.util.BluetoothAudioCastWrapper;
import com.android.systemui.volume.util.BluetoothCommonUtil;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.android.systemui.volume.util.BroadcastReceiverManager;
import com.android.systemui.volume.util.DesktopManagerWrapper;
import com.android.systemui.volume.util.DeviceStateManagerWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.SALoggingWrapper;
import com.android.systemui.volume.util.SoundAssistantManagerWrapper;
import com.android.systemui.volume.util.StreamUtil;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.EmptyList;

/* loaded from: classes3.dex */
public class VolumeDialogControllerImpl implements VolumeDialogController, Dumpable {
    public static final int DEFAULT_MAX_LEVEL;
    public static final int FLAG_SMART_VIEW_NONE;
    public static final ArrayMap STREAMS;
    public static boolean mIsVolumeStarEnabled;
    public BluetoothDevice mActiveBtDevice;
    public final ActivityManager mActivityManager;
    public boolean mAllSoundMute;
    public final AudioManager mAudio;
    public final IAudioService mAudioService;
    public final AudioSharingInteractor mAudioSharingInteractor;
    public final BluetoothAdapterWrapper mBluetoothAdapterManager;
    public final BluetoothAudioCastWrapper mBluetoothAudioCastWrapper;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final BroadcastReceiverManager mBroadcastReceiverManager;
    public final C mCallbacks;
    public final AtomicReference mCaptioningManager;
    public final Context mContext;
    public UserTracker.Callback mCurrentUserTrackerCallback;
    public final DesktopManagerWrapper mDesktopManagerWrapper;
    public boolean mDeviceInteractive;
    public final DeviceStateManagerWrapper mDeviceStateManagerWrapper;
    public final DisplayManagerWrapper mDisplayManagerWrapper;
    public final boolean mHasVibrator;
    public boolean mIsAudioMirroringEnabled;
    public boolean mIsBudsTogetherEnabled;
    public Boolean mIsDLNAEnabled;
    public boolean mIsDisallowAdjustVolume;
    public boolean mIsMusicShareEnabled;
    public Boolean mIsSupportTvVolumeControl;
    public boolean mIsVibrating;
    public boolean mIsVolumeDialogShowing;
    public final JavaAdapter mJavaAdapter;
    public boolean mKeyDown;
    public final KeyguardManager mKeyguardManager;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public long mLastToggledRingerOn;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final MediaSessions mMediaSessions;
    public final MediaSessionsCallbacks mMediaSessionsCallbacksW;
    public final NotificationManager mNoMan;
    public final PackageManager mPackageManager;
    public final RingerModeObservers mRingerModeObservers;
    public final MediaRouter2Manager mRouter2Manager;
    public boolean mShowA11yStream;
    public boolean mShowSafetyWarning;
    public boolean mShowVolumeDialog;
    public int mSmartViewFlag;
    public final SoundAssistantChecker mSoundAssistantChecker;
    public final SoundAssistantManagerWrapper mSoundAssistantManagerWrapper;
    public final VolumeDialogController.State mState;
    public VolumeDialogComponent mUserActivityListener;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final VibratorHelper mVibrator;
    public final VC mVolumeController;
    public final VolumeLogger mVolumeLogger;
    public final VolumeManager mVolumeManager;
    public final AnonymousClass2 mWakefullnessLifecycleObserver;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final W mWorker;
    public static final String TAG = Util.logTag(VolumeDialogControllerImpl.class);
    public static final AudioAttributes SONIFICIATION_VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();

    public final class MediaSessionsCallbacks implements MediaSessions.Callbacks {
        public final HashMap mRemoteStreams = new HashMap();
        public int mNextStream = 100;

        public MediaSessionsCallbacks() {
        }

        public final void addStream(MediaSessions.SessionId.Media media, String str) {
            synchronized (this.mRemoteStreams) {
                try {
                    if (!this.mRemoteStreams.containsKey(media)) {
                        this.mRemoteStreams.put(media, Integer.valueOf(this.mNextStream));
                        String str2 = VolumeDialogControllerImpl.TAG;
                        Log.d(str2, str + ": added stream " + this.mNextStream + " from token + " + media.toString());
                        this.mNextStream = this.mNextStream + 1;
                        if ("com.samsung.android.audiomirroring".equals(VolumeDialogControllerImpl.m3212$$Nest$mgetMediaControllerFromSessionId(VolumeDialogControllerImpl.this, media).getPackageName())) {
                            VolumeDialogControllerImpl.this.mIsAudioMirroringEnabled = true;
                            Log.d(str2, str.concat(": - AudioMirroring is on"));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onRemoteRemoved(MediaSessions.SessionId.Media media) {
            synchronized (this.mRemoteStreams) {
                try {
                    if (!this.mRemoteStreams.containsKey(media)) {
                        Log.d(VolumeDialogControllerImpl.TAG, "onRemoteRemoved: stream doesn't exist, aborting remote removed for token:" + media.toString());
                        return;
                    }
                    int iIntValue = ((Integer) this.mRemoteStreams.get(media)).intValue();
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    if (volumeDialogControllerImpl.mIsAudioMirroringEnabled && "com.samsung.android.audiomirroring".equals(VolumeDialogControllerImpl.m3212$$Nest$mgetMediaControllerFromSessionId(volumeDialogControllerImpl, media).getPackageName())) {
                        VolumeDialogControllerImpl.this.mIsAudioMirroringEnabled = false;
                        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(iIntValue, "onRemoteRemoved ", " - AudioMirroring is off", VolumeDialogControllerImpl.TAG);
                    }
                    VolumeDialogControllerImpl.this.mState.states.remove(iIntValue);
                    VolumeDialogControllerImpl.this.mVolumeManager.updateRemoteVolume(false, iIntValue, 0, 0, 0, media.token, false);
                    VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                    if (volumeDialogControllerImpl2.mState.activeStream == iIntValue) {
                        volumeDialogControllerImpl2.updateActiveStreamW(-1);
                    }
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                    volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class Receiver extends BroadcastReceiver {
        public /* synthetic */ Receiver(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean zUpdateEffectsSuppressorW = false;
            if (action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", -1);
                int intExtra3 = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES", -1);
                if (D.BUG) {
                    RecyclerView$$ExternalSyntheticOutline0.m(intExtra3, VolumeDialogControllerImpl.TAG, MutableObjectList$$ExternalSyntheticOutline0.m(intExtra, intExtra2, "onReceive STREAM_DEVICES_CHANGED_ACTION stream=", " devices=", " oldDevices="));
                }
                if (intExtra != -1) {
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    String str = VolumeDialogControllerImpl.TAG;
                    boolean zCheckRoutedToBluetoothW = volumeDialogControllerImpl.checkRoutedToBluetoothW(intExtra);
                    if (intExtra == 3) {
                        zCheckRoutedToBluetoothW = zCheckRoutedToBluetoothW | VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(21) | VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(22);
                    }
                    zUpdateEffectsSuppressorW = zCheckRoutedToBluetoothW | VolumeDialogControllerImpl.this.onVolumeChangedW(intExtra, 0);
                }
            } else if (action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                int intExtra4 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                boolean booleanExtra = intent.getBooleanExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", false);
                if (D.BUG) {
                    KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("onReceive STREAM_MUTE_CHANGED_ACTION stream=", intExtra4, " muted=", booleanExtra, VolumeDialogControllerImpl.TAG);
                }
                if (intExtra4 != -1) {
                    VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                    String str2 = VolumeDialogControllerImpl.TAG;
                    zUpdateEffectsSuppressorW = volumeDialogControllerImpl2.updateStreamMuteW(intExtra4, booleanExtra);
                }
                VolumeDialogControllerImpl.m3214$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, intExtra4);
                if (intExtra4 == 3) {
                    VolumeDialogControllerImpl.this.updateStreamMuteW(21, booleanExtra);
                    VolumeDialogControllerImpl.m3214$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, 21);
                    ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("onReceive STREAM_MUTE_CHANGED_ACTION : stream=", intExtra4, ", muted=", booleanExtra, ", mState.dualAudio="), VolumeDialogControllerImpl.this.mState.dualAudio, VolumeDialogControllerImpl.TAG);
                    VolumeDialogControllerImpl.this.updateStreamMuteW(22, booleanExtra);
                    VolumeDialogControllerImpl.m3214$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, 22);
                }
            } else if (action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                if (!volumeDialogControllerImpl3.mIsVolumeDialogShowing || volumeDialogControllerImpl3.mState.activeStream != 6) {
                    return;
                }
                if (intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1) == 10) {
                    VolumeDialogControllerImpl.this.mCallbacks.onDismissRequested(2);
                }
            } else if (action.equals("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED")) {
                if (D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_EFFECTS_SUPPRESSOR_CHANGED");
                }
                VolumeDialogControllerImpl volumeDialogControllerImpl4 = VolumeDialogControllerImpl.this;
                zUpdateEffectsSuppressorW = volumeDialogControllerImpl4.updateEffectsSuppressorW(volumeDialogControllerImpl4.mNoMan.getEffectsSuppressor());
            } else if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                if (D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_CONFIGURATION_CHANGED");
                }
                VolumeDialogControllerImpl.this.mCallbacks.onConfigurationChanged();
            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                if (D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_SCREEN_OFF");
                }
                VolumeDialogControllerImpl.this.mCallbacks.onScreenOff();
            } else if (action.equals(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                if (D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_CLOSE_SYSTEM_DIALOGS");
                }
                VolumeDialogControllerImpl.this.mCallbacks.onDismissRequested(2);
            } else if (action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED") || action.equals("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED") || action.equals("android.intent.action.HEADSET_PLUG") || action.equals("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE")) {
                Log.d(VolumeDialogControllerImpl.TAG, "onReceive action = " + action + " updateVolumeBar");
                if (action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                    VolumeDialogControllerImpl.this.mActiveBtDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                }
                VolumeDialogControllerImpl.this.updateVolumeBar();
            }
            if (zUpdateEffectsSuppressorW) {
                VolumeDialogControllerImpl volumeDialogControllerImpl5 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl5.mCallbacks.onStateChanged(volumeDialogControllerImpl5.mState);
            }
        }

        private Receiver() {
        }
    }

    public final class RingerModeObservers {
        public final RingerModeLiveData mRingerMode;
        public final RingerModeLiveData mRingerModeInternal;
        public final AnonymousClass1 mRingerModeObserver = new AnonymousClass1();
        public final AnonymousClass2 mRingerModeInternalObserver = new AnonymousClass2();

        /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$RingerModeObservers$1, reason: invalid class name */
        public class AnonymousClass1 implements Observer {
            public AnonymousClass1() {
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VolumeDialogControllerImpl.this.mWorker.post(new VolumeDialogControllerImpl$RingerModeObservers$1$$ExternalSyntheticLambda0(this, (Integer) obj, 0));
            }
        }

        /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$RingerModeObservers$2, reason: invalid class name */
        public class AnonymousClass2 implements Observer {
            public AnonymousClass2() {
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VolumeDialogControllerImpl.this.mWorker.post(new VolumeDialogControllerImpl$RingerModeObservers$1$$ExternalSyntheticLambda0(this, (Integer) obj, 1));
            }
        }

        public RingerModeObservers(RingerModeLiveData ringerModeLiveData, RingerModeLiveData ringerModeLiveData2) {
            this.mRingerMode = ringerModeLiveData;
            this.mRingerModeInternal = ringerModeLiveData2;
        }
    }

    public final class SettingObserver extends ContentObserver {
        public final Uri ALL_SOUND_MUTE_URI;
        public final Uri ZEN_MODE_CONFIG_URI;
        public final Uri ZEN_MODE_URI;

        public SettingObserver(Handler handler) {
            super(handler);
            this.ZEN_MODE_URI = Settings.Global.getUriFor("zen_mode");
            this.ZEN_MODE_CONFIG_URI = Settings.Global.getUriFor("zen_mode_config_etag");
            this.ALL_SOUND_MUTE_URI = Settings.Global.getUriFor("all_sound_off");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            boolean zUpdateZenConfig;
            if (this.ZEN_MODE_URI.equals(uri)) {
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                String str = VolumeDialogControllerImpl.TAG;
                zUpdateZenConfig = volumeDialogControllerImpl.updateZenModeW();
            } else {
                zUpdateZenConfig = false;
            }
            if (this.ZEN_MODE_CONFIG_URI.equals(uri)) {
                VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                String str2 = VolumeDialogControllerImpl.TAG;
                zUpdateZenConfig |= volumeDialogControllerImpl2.updateZenConfig();
            }
            if (this.ALL_SOUND_MUTE_URI.equals(uri)) {
                boolean z2 = Settings.Global.getInt(VolumeDialogControllerImpl.this.mContext.getContentResolver(), "all_sound_off", 0) != 0;
                VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                if (volumeDialogControllerImpl3.mAllSoundMute != z2) {
                    volumeDialogControllerImpl3.mAllSoundMute = z2;
                    zUpdateZenConfig = true;
                }
            }
            if (zUpdateZenConfig) {
                VolumeDialogControllerImpl volumeDialogControllerImpl4 = VolumeDialogControllerImpl.this;
                String str3 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl4.updateVolumeBar();
                VolumeDialogControllerImpl volumeDialogControllerImpl5 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl5.mCallbacks.onStateChanged(volumeDialogControllerImpl5.mState);
            }
        }
    }

    public final class VC extends IVolumeController.Stub {
        public final String TAG;

        public /* synthetic */ VC(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
            this();
        }

        public final void dismiss() {
            if (D.BUG) {
                Log.d(this.TAG, "dismiss requested");
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(2, 2, 0).sendToTarget();
            VolumeDialogControllerImpl.this.mWorker.sendEmptyMessage(2);
        }

        public final void displayCsdWarning(int i, int i2) {
            if (D.BUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "displayCsdWarning durMs=", this.TAG);
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(17, i, i2).sendToTarget();
        }

        public final void displaySafeVolumeWarning(int i) {
            if (D.BUG) {
                String str = this.TAG;
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("displaySafeVolumeWarning "), com.android.settingslib.volume.Util.bitFieldToString(i, com.android.settingslib.volume.Util.AUDIO_MANAGER_FLAG_NAMES, com.android.settingslib.volume.Util.AUDIO_MANAGER_FLAGS), str);
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(14, i, 0).sendToTarget();
        }

        public final void displayVolumeLimiterToast() {
            if (D.BUG) {
                Log.d(this.TAG, "displayVolumeLimiterWarning");
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(20).sendToTarget();
        }

        public final void masterMuteChanged(int i) {
            if (D.BUG) {
                Log.d(this.TAG, "masterMuteChanged");
            }
            VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
            String str = VolumeDialogControllerImpl.TAG;
            volumeDialogControllerImpl.mIsDisallowAdjustVolume = volumeDialogControllerImpl.getDisallowAdjustVolume();
            VolumeDialogControllerImpl.this.updateVolumeBar();
        }

        public final void setA11yMode(int i) {
            if (D.BUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setA11yMode to ", this.TAG);
            }
            if (i == 0) {
                VolumeDialogControllerImpl.this.mShowA11yStream = false;
            } else if (i == 1) {
                VolumeDialogControllerImpl.this.mShowA11yStream = true;
            } else if (i == 100) {
                VolumeDialogControllerImpl.mIsVolumeStarEnabled = true;
                return;
            } else {
                if (i == 101) {
                    VolumeDialogControllerImpl.mIsVolumeStarEnabled = false;
                    return;
                }
                ClockEventController$$ExternalSyntheticOutline0.m(i, "Invalid accessibility mode ", this.TAG);
            }
            VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
            volumeDialogControllerImpl.mWorker.obtainMessage(15, Boolean.valueOf(volumeDialogControllerImpl.mShowA11yStream)).sendToTarget();
        }

        public final void setLayoutDirection(int i) {
            if (D.BUG) {
                Log.d(this.TAG, "setLayoutDirection");
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(8, i, 0).sendToTarget();
        }

        public final void volumeChanged(int i, int i2) {
            if (D.BUG) {
                String str = this.TAG;
                StringBuilder sb = new StringBuilder("volumeChanged ");
                sb.append(AudioSystem.streamToString(i));
                sb.append(" ");
                ExifInterface$$ExternalSyntheticOutline0.m(sb, com.android.settingslib.volume.Util.bitFieldToString(i2, Util.SAMSUNG_AUDIO_MANAGER_FLAG_NAMES, Util.SAMSUNG_AUDIO_MANAGER_FLAGS), str);
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(1, i, i2).sendToTarget();
        }

        private VC() {
            this.TAG = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), VolumeDialogControllerImpl.TAG, ".VC");
        }
    }

    public final class W extends Handler {
        public W(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            MediaSessions.SessionId sessionId = null;
            int i = 0;
            switch (message.what) {
                case 1:
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    int i2 = message.arg1;
                    int i3 = message.arg2;
                    String str = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl.getClass();
                    if (i2 == 3 && (4194304 & i3) != 0 && volumeDialogControllerImpl.mIsVolumeDialogShowing) {
                        volumeDialogControllerImpl.mSmartViewFlag = i3;
                        return;
                    } else {
                        volumeDialogControllerImpl.onVolumeChangedW(i2, i3);
                        volumeDialogControllerImpl.mSmartViewFlag = VolumeDialogControllerImpl.FLAG_SMART_VIEW_NONE;
                        return;
                    }
                case 2:
                    VolumeDialogControllerImpl.this.mCallbacks.onDismissRequested(message.arg1);
                    return;
                case 3:
                    VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                    String str2 = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl2.getClass();
                    for (Integer num : VolumeDialogControllerImpl.STREAMS.keySet()) {
                        int iIntValue = num.intValue();
                        volumeDialogControllerImpl2.updateStreamLevelW(iIntValue, volumeDialogControllerImpl2.getLastAudibleStreamVolume(iIntValue));
                        volumeDialogControllerImpl2.streamStateW(iIntValue).levelMin = volumeDialogControllerImpl2.getAudioManagerStreamMinVolume(iIntValue);
                        volumeDialogControllerImpl2.streamStateW(iIntValue).levelMax = volumeDialogControllerImpl2.getAudioManagerStreamMaxVolume(iIntValue);
                        volumeDialogControllerImpl2.updateStreamMuteW(iIntValue, (iIntValue == 20 || iIntValue == 23) ? false : (iIntValue == 21 || iIntValue == 22) ? volumeDialogControllerImpl2.mAudio.isStreamMute(3) : volumeDialogControllerImpl2.mAudio.isStreamMute(iIntValue));
                        VolumeDialogController.StreamState streamStateStreamStateW = volumeDialogControllerImpl2.streamStateW(iIntValue);
                        streamStateStreamStateW.muteSupported = volumeDialogControllerImpl2.mAudio.isStreamMutableByUi(iIntValue);
                        streamStateStreamStateW.name = ((Integer) VolumeDialogControllerImpl.STREAMS.get(num)).intValue();
                        volumeDialogControllerImpl2.checkRoutedToBluetoothW(iIntValue);
                        streamStateStreamStateW.nameRes = volumeDialogControllerImpl2.mContext.getResources().getResourceName(streamStateStreamStateW.name);
                    }
                    Integer value = volumeDialogControllerImpl2.mRingerModeObservers.mRingerMode.getValue();
                    int iIntValue2 = value.intValue();
                    VolumeDialogController.State state = volumeDialogControllerImpl2.mState;
                    if (iIntValue2 != state.ringerModeExternal) {
                        if (iIntValue2 == 1 && !volumeDialogControllerImpl2.mIsVibrating) {
                            volumeDialogControllerImpl2.mIsVibrating = true;
                            volumeDialogControllerImpl2.mWorker.postDelayed(new VolumeDialogControllerImpl$$ExternalSyntheticLambda6(volumeDialogControllerImpl2, 0), 800L);
                        }
                        state.ringerModeExternal = iIntValue2;
                        Events.writeEvent(12, value);
                    }
                    volumeDialogControllerImpl2.updateZenModeW();
                    volumeDialogControllerImpl2.updateZenConfig();
                    volumeDialogControllerImpl2.updateEffectsSuppressorW(volumeDialogControllerImpl2.mNoMan.getEffectsSuppressor());
                    volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
                    return;
                case 4:
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                    int i4 = message.arg1;
                    if (message.arg2 != 0) {
                        volumeDialogControllerImpl3.mAudio.setRingerMode(i4);
                        return;
                    } else {
                        volumeDialogControllerImpl3.mAudio.setRingerModeInternal(i4);
                        return;
                    }
                case 5:
                    VolumeDialogControllerImpl volumeDialogControllerImpl4 = VolumeDialogControllerImpl.this;
                    int i5 = message.arg1;
                    String str3 = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl4.getClass();
                    boolean z = D.BUG;
                    String str4 = VolumeDialogControllerImpl.TAG;
                    if (z) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i5, "onSetZenModeW ", str4);
                    }
                    volumeDialogControllerImpl4.mNoMan.setZenMode(i5, null, str4);
                    return;
                case 6:
                    VolumeDialogControllerImpl volumeDialogControllerImpl5 = VolumeDialogControllerImpl.this;
                    Condition condition = (Condition) message.obj;
                    volumeDialogControllerImpl5.mNoMan.setZenMode(volumeDialogControllerImpl5.mState.zenMode, condition != null ? condition.id : null, VolumeDialogControllerImpl.TAG);
                    return;
                case 7:
                    VolumeDialogControllerImpl.this.mAudio.adjustStreamVolume(message.arg1, message.arg2 != 0 ? -100 : 100, 0);
                    return;
                case 8:
                    VolumeDialogControllerImpl.this.mCallbacks.onLayoutDirectionChanged(message.arg1);
                    return;
                case 9:
                    VolumeDialogControllerImpl.this.mCallbacks.onConfigurationChanged();
                    return;
                case 10:
                    VolumeDialogControllerImpl volumeDialogControllerImpl6 = VolumeDialogControllerImpl.this;
                    int i6 = message.arg1;
                    int i7 = message.arg2;
                    String str5 = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl6.getClass();
                    if (D.BUG) {
                        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i6, i7, "onSetStreamVolumeW ", " level=", VolumeDialogControllerImpl.TAG);
                    }
                    if (i6 == 99) {
                        VolumeDialogControllerImpl$$ExternalSyntheticLambda3 volumeDialogControllerImpl$$ExternalSyntheticLambda3 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda3(volumeDialogControllerImpl6, i);
                        JavaAdapter javaAdapter = volumeDialogControllerImpl6.mJavaAdapter;
                        AudioSharingInteractor audioSharingInteractor = volumeDialogControllerImpl6.mAudioSharingInteractor;
                        Objects.requireNonNull(audioSharingInteractor);
                        javaAdapter.callSuspend(new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(audioSharingInteractor), volumeDialogControllerImpl6.mContext, new VolumeDialogControllerImpl$$ExternalSyntheticLambda0(volumeDialogControllerImpl6, i7, 2), volumeDialogControllerImpl$$ExternalSyntheticLambda3, volumeDialogControllerImpl$$ExternalSyntheticLambda3);
                        return;
                    }
                    if (i6 < 100) {
                        volumeDialogControllerImpl6.setStreamVolume(i6, i7, null);
                        return;
                    }
                    MediaSessionsCallbacks mediaSessionsCallbacks = volumeDialogControllerImpl6.mMediaSessionsCallbacksW;
                    synchronized (mediaSessionsCallbacks.mRemoteStreams) {
                        try {
                            Iterator it = mediaSessionsCallbacks.mRemoteStreams.entrySet().iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    Map.Entry entry = (Map.Entry) it.next();
                                    if (((Integer) entry.getValue()).equals(Integer.valueOf(i6))) {
                                        sessionId = (MediaSessions.SessionId) entry.getKey();
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                    if (sessionId == null) {
                        RecordingInputConnection$$ExternalSyntheticOutline0.m(i6, "setStreamVolume: No token found for stream: ", VolumeDialogControllerImpl.TAG);
                        return;
                    }
                    MediaSessions mediaSessions = VolumeDialogControllerImpl.this.mMediaSessions;
                    mediaSessions.getClass();
                    if (!(sessionId instanceof MediaSessions.SessionId.Media)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    MediaSession.Token token = ((MediaSessions.SessionId.Media) sessionId).token;
                    MediaSessions.MediaControllerRecord mediaControllerRecord = (MediaSessions.MediaControllerRecord) ((HashMap) mediaSessions.mRecords).get(token);
                    String str6 = MediaSessions.TAG;
                    if (mediaControllerRecord == null) {
                        Log.w(str6, "setVolume: No record found for token " + token);
                        return;
                    }
                    if (com.android.settingslib.volume.D.BUG) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m(i7, "Setting level to ", str6);
                    }
                    mediaControllerRecord.controller.setVolumeTo(i7, 0);
                    return;
                case 11:
                    VolumeDialogControllerImpl volumeDialogControllerImpl7 = VolumeDialogControllerImpl.this;
                    int i8 = message.arg1;
                    String str7 = VolumeDialogControllerImpl.TAG;
                    if (volumeDialogControllerImpl7.updateActiveStreamW(i8)) {
                        volumeDialogControllerImpl7.mCallbacks.onStateChanged(volumeDialogControllerImpl7.mState);
                        return;
                    }
                    return;
                case 12:
                    VolumeDialogControllerImpl volumeDialogControllerImpl8 = VolumeDialogControllerImpl.this;
                    boolean z2 = message.arg1 != 0;
                    volumeDialogControllerImpl8.mAudio.notifyVolumeControllerVisible(volumeDialogControllerImpl8.mVolumeController, z2);
                    if (z2 || !volumeDialogControllerImpl8.updateActiveStreamW(-1)) {
                        return;
                    }
                    volumeDialogControllerImpl8.mCallbacks.onStateChanged(volumeDialogControllerImpl8.mState);
                    return;
                case 13:
                    VolumeDialogControllerImpl volumeDialogControllerImpl9 = VolumeDialogControllerImpl.this;
                    String str8 = VolumeDialogControllerImpl.TAG;
                    synchronized (volumeDialogControllerImpl9) {
                        VolumeDialogComponent volumeDialogComponent = volumeDialogControllerImpl9.mUserActivityListener;
                        if (volumeDialogComponent != null) {
                            volumeDialogComponent.mKeyguardViewMediator.userActivity();
                        }
                    }
                    return;
                case 14:
                    VolumeDialogControllerImpl volumeDialogControllerImpl10 = VolumeDialogControllerImpl.this;
                    int i9 = message.arg1;
                    if (volumeDialogControllerImpl10.mShowSafetyWarning) {
                        volumeDialogControllerImpl10.mCallbacks.onShowSafetyWarning(i9);
                        return;
                    }
                    return;
                case 15:
                    VolumeDialogControllerImpl.this.mCallbacks.onAccessibilityModeChanged((Boolean) message.obj);
                    return;
                case 16:
                    VolumeDialogControllerImpl volumeDialogControllerImpl11 = VolumeDialogControllerImpl.this;
                    Boolean bool = (Boolean) message.obj;
                    bool.getClass();
                    CaptioningManager captioningManager = (CaptioningManager) volumeDialogControllerImpl11.mCaptioningManager.get();
                    if (captioningManager != null) {
                        volumeDialogControllerImpl11.mCallbacks.onCaptionComponentStateChanged(Boolean.valueOf(captioningManager.isSystemAudioCaptioningUiEnabled()), bool);
                        return;
                    } else {
                        Log.e(VolumeDialogControllerImpl.TAG, "onGetCaptionsComponentStateW(), null captioningManager");
                        return;
                    }
                case 17:
                    VolumeDialogControllerImpl.this.mCallbacks.onShowCsdWarning(message.arg1, message.arg2);
                    return;
                case 18:
                    VolumeDialogControllerImpl volumeDialogControllerImpl12 = VolumeDialogControllerImpl.this;
                    Boolean bool2 = (Boolean) message.obj;
                    bool2.getClass();
                    CaptioningManager captioningManager2 = (CaptioningManager) volumeDialogControllerImpl12.mCaptioningManager.get();
                    if (captioningManager2 != null) {
                        volumeDialogControllerImpl12.mCallbacks.onCaptionEnabledStateChanged(Boolean.valueOf(captioningManager2.isSystemAudioCaptioningEnabled()), bool2);
                        return;
                    } else {
                        Log.e(VolumeDialogControllerImpl.TAG, "onGetCaptionsEnabledStateW(), null captioningManager");
                        return;
                    }
                case 19:
                    VolumeDialogControllerImpl volumeDialogControllerImpl13 = VolumeDialogControllerImpl.this;
                    boolean zBooleanValue = ((Boolean) message.obj).booleanValue();
                    CaptioningManager captioningManager3 = (CaptioningManager) volumeDialogControllerImpl13.mCaptioningManager.get();
                    if (captioningManager3 == null) {
                        Log.e(VolumeDialogControllerImpl.TAG, "onGetCaptionsEnabledStateW(), null captioningManager");
                        return;
                    } else {
                        captioningManager3.setSystemAudioCaptioningEnabled(zBooleanValue);
                        volumeDialogControllerImpl13.mCallbacks.onCaptionEnabledStateChanged(Boolean.valueOf(captioningManager3.isSystemAudioCaptioningEnabled()), Boolean.FALSE);
                        return;
                    }
                case 20:
                    VolumeDialogControllerImpl.this.mCallbacks.onShowVolumeLimiterToast();
                    return;
                case 21:
                    VolumeDialogControllerImpl volumeDialogControllerImpl14 = VolumeDialogControllerImpl.this;
                    int i10 = message.arg1;
                    int i11 = message.arg2;
                    String str9 = (String) message.obj;
                    String str10 = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl14.getClass();
                    if (D.BUG) {
                        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i10, i11, "onSetStreamVolumeDualAudioW ", " level=", " btDeviceAddress=");
                        sbM.append(str9);
                        Log.d(VolumeDialogControllerImpl.TAG, sbM.toString());
                    }
                    volumeDialogControllerImpl14.setStreamVolume(i10, i11, str9);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: -$$Nest$mgetMediaControllerFromSessionId, reason: not valid java name */
    public static MediaController m3212$$Nest$mgetMediaControllerFromSessionId(VolumeDialogControllerImpl volumeDialogControllerImpl, MediaSessions.SessionId.Media media) {
        volumeDialogControllerImpl.getClass();
        return new MediaController(volumeDialogControllerImpl.mContext, media.token);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x000d  */
    /* renamed from: -$$Nest$mupdateRemoteFixedVolumeSession, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m3213$$Nest$mupdateRemoteFixedVolumeSession(VolumeDialogControllerImpl volumeDialogControllerImpl, int i, MediaController.PlaybackInfo playbackInfo) {
        boolean z;
        if (playbackInfo != null) {
            volumeDialogControllerImpl.getClass();
            z = playbackInfo.getVolumeControl() == 0;
        }
        VolumeDialogController.StreamState streamStateStreamStateW = volumeDialogControllerImpl.streamStateW(i);
        if (streamStateStreamStateW.remoteFixedVolume == z) {
            return;
        }
        streamStateStreamStateW.remoteFixedVolume = z;
        if (D.BUG) {
            Log.d(TAG, "updateRemoteFixedVolumeSession stream=" + i + " remoteFixedVolume=" + z);
        }
    }

    /* renamed from: -$$Nest$mupdateStreamVolume, reason: not valid java name */
    public static void m3214$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
        volumeDialogControllerImpl.updateStreamLevelW(i, volumeDialogControllerImpl.getLastAudibleStreamVolume(i));
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        STREAMS = arrayMap;
        Integer numValueOf = Integer.valueOf(R.string.volume_icon_description_incall);
        arrayMap.put(0, numValueOf);
        arrayMap.put(1, Integer.valueOf(R.string.volumepanel_system));
        arrayMap.put(2, Integer.valueOf(R.string.volumepanel_ringtone));
        Integer numValueOf2 = Integer.valueOf(R.string.volumepanel_media);
        arrayMap.put(3, numValueOf2);
        arrayMap.put(4, Integer.valueOf(R.string.volume_alarm));
        arrayMap.put(5, Integer.valueOf(R.string.volumepanel_notification));
        arrayMap.put(6, numValueOf);
        arrayMap.put(7, Integer.valueOf(R.string.stream_system_enforced));
        arrayMap.put(8, Integer.valueOf(R.string.stream_dtmf));
        arrayMap.put(9, Integer.valueOf(R.string.stream_tts));
        arrayMap.put(10, Integer.valueOf(R.string.stream_accessibility));
        arrayMap.put(20, numValueOf2);
        arrayMap.put(21, numValueOf2);
        arrayMap.put(11, Integer.valueOf(R.string.volumepanel_bixby_voice));
        arrayMap.put(22, numValueOf2);
        arrayMap.put(23, Integer.valueOf(R.string.volumepanel_music_share));
        DEFAULT_MAX_LEVEL = 15;
        FLAG_SMART_VIEW_NONE = -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1, types: [com.android.systemui.volume.VolumeDialogControllerImpl$2, java.lang.Object] */
    public VolumeDialogControllerImpl(Context context, BroadcastDispatcher broadcastDispatcher, RingerModeTracker ringerModeTracker, ThreadFactory threadFactory, AudioManager audioManager, NotificationManager notificationManager, VibratorHelper vibratorHelper, IAudioService iAudioService, VolumeControllerAdapter volumeControllerAdapter, AccessibilityManager accessibilityManager, PackageManager packageManager, WakefulnessLifecycle wakefulnessLifecycle, KeyguardManager keyguardManager, ActivityManager activityManager, UserTracker userTracker, DumpManager dumpManager, AudioSharingInteractor audioSharingInteractor, JavaAdapter javaAdapter, VolumeLogger volumeLogger, SALoggingWrapper sALoggingWrapper, BroadcastReceiverManager broadcastReceiverManager, DisplayManagerWrapper displayManagerWrapper, DesktopManagerWrapper desktopManagerWrapper, KnoxStateMonitor knoxStateMonitor, BluetoothAdapterWrapper bluetoothAdapterWrapper, SoundAssistantManagerWrapper soundAssistantManagerWrapper, DeviceStateManagerWrapper deviceStateManagerWrapper, LocalBluetoothManager localBluetoothManager, VolumeManager volumeManager, VolumeDependency volumeDependency, SoundCraftManager soundCraftManager, CommandQueue commandQueue) {
        int i = 0;
        Receiver receiver = new Receiver(this, i);
        AtomicReference atomicReference = new AtomicReference();
        this.mCaptioningManager = atomicReference;
        this.mCallbacks = new C();
        this.mState = new VolumeDialogController.State();
        this.mDeviceInteractive = true;
        VC vc = new VC(this, i);
        this.mVolumeController = vc;
        Boolean bool = Boolean.FALSE;
        this.mIsSupportTvVolumeControl = bool;
        this.mIsDLNAEnabled = bool;
        this.mActiveBtDevice = null;
        this.mCurrentUserTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i2, Context context2) {
                int i3 = 0;
                while (true) {
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    if (i3 >= volumeDialogControllerImpl.mState.states.size()) {
                        return;
                    }
                    VolumeDialogController.State state = volumeDialogControllerImpl.mState;
                    if (state.states.keyAt(i3) < 100) {
                        volumeDialogControllerImpl.onVolumeChangedW(state.states.keyAt(i3), 0);
                    }
                    i3++;
                }
            }
        };
        ?? r11 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.2
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                VolumeDialogControllerImpl.this.mDeviceInteractive = false;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                VolumeDialogControllerImpl.this.mDeviceInteractive = true;
            }
        };
        this.mWakefullnessLifecycleObserver = r11;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.4
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i2, Context context2) {
                String str = VolumeDialogControllerImpl.TAG;
                VolumeDialogControllerImpl.this.mCaptioningManager.set((CaptioningManager) context2.getSystemService(CaptioningManager.class));
            }
        };
        this.mUserChangedCallback = callback;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mPackageManager = packageManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        Events.writeEvent(5, new Object[0]);
        Looper looperBuildLooperOnNewThread = threadFactory.buildLooperOnNewThread("VolumeDialogControllerImpl");
        W w = new W(looperBuildLooperOnNewThread);
        this.mWorker = w;
        this.mRouter2Manager = MediaRouter2Manager.getInstance(applicationContext);
        MediaSessionsCallbacks mediaSessionsCallbacks = new MediaSessionsCallbacks();
        this.mMediaSessionsCallbacksW = mediaSessionsCallbacks;
        this.mMediaSessions = new MediaSessions(applicationContext, looperBuildLooperOnNewThread, mediaSessionsCallbacks);
        this.mAudioSharingInteractor = audioSharingInteractor;
        this.mJavaAdapter = javaAdapter;
        this.mVolumeLogger = volumeLogger;
        this.mAudio = audioManager;
        this.mNoMan = notificationManager;
        SettingObserver settingObserver = new SettingObserver(w);
        RingerModeObservers ringerModeObservers = new RingerModeObservers((RingerModeLiveData) ringerModeTracker.getRingerMode(), (RingerModeLiveData) ringerModeTracker.getRingerModeInternal());
        this.mRingerModeObservers = ringerModeObservers;
        RingerModeLiveData ringerModeLiveData = ringerModeObservers.mRingerMode;
        int iIntValue = ringerModeLiveData.getValue().intValue();
        VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
        if (iIntValue != -1) {
            volumeDialogControllerImpl.mState.ringerModeExternal = iIntValue;
        }
        ringerModeLiveData.observeForever(ringerModeObservers.mRingerModeObserver);
        RingerModeLiveData ringerModeLiveData2 = ringerModeObservers.mRingerModeInternal;
        int iIntValue2 = ringerModeLiveData2.getValue().intValue();
        if (iIntValue2 != -1) {
            volumeDialogControllerImpl.mState.ringerModeInternal = iIntValue2;
        }
        ringerModeLiveData2.observeForever(ringerModeObservers.mRingerModeInternalObserver);
        this.mBroadcastDispatcher = broadcastDispatcher;
        VolumeDialogControllerImpl.this.mContext.getContentResolver().registerContentObserver(settingObserver.ZEN_MODE_URI, false, settingObserver);
        VolumeDialogControllerImpl.this.mContext.getContentResolver().registerContentObserver(settingObserver.ZEN_MODE_CONFIG_URI, false, settingObserver);
        VolumeDialogControllerImpl.this.mContext.getContentResolver().registerContentObserver(settingObserver.ALL_SOUND_MUTE_URI, false, settingObserver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.media.STREAM_DEVICES_CHANGED_ACTION", "android.media.STREAM_MUTE_CHANGED_ACTION", "android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED", "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.HEADSET_PLUG", "com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE", "com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED", "android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
        volumeDialogControllerImpl2.mBroadcastDispatcher.registerReceiverWithHandler(receiver, intentFilter, volumeDialogControllerImpl2.mWorker);
        this.mVibrator = vibratorHelper;
        this.mHasVibrator = vibratorHelper.hasVibrator();
        this.mAudioService = iAudioService;
        this.mKeyguardManager = keyguardManager;
        this.mActivityManager = activityManager;
        this.mUserTracker = userTracker;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        userTrackerImpl.addCallback(callback, new HandlerExecutor(w));
        atomicReference.set((CaptioningManager) userTrackerImpl.getUserContext().getSystemService(CaptioningManager.class));
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "VolumeDialogControllerImpl", this);
        vc.setA11yMode(accessibilityManager.isAccessibilityVolumeStreamActive() ? 1 : 0);
        wakefulnessLifecycle.addObserver(r11);
        this.mDesktopManagerWrapper = desktopManagerWrapper;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mBroadcastReceiverManager = broadcastReceiverManager;
        this.mDisplayManagerWrapper = displayManagerWrapper;
        this.mKnoxStateMonitor = knoxStateMonitor;
        this.mBluetoothAudioCastWrapper = new BluetoothAudioCastWrapper(applicationContext);
        this.mBluetoothAdapterManager = bluetoothAdapterWrapper;
        this.mSoundAssistantManagerWrapper = soundAssistantManagerWrapper;
        if (BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG) {
            this.mDeviceStateManagerWrapper = deviceStateManagerWrapper;
        } else {
            this.mDeviceStateManagerWrapper = null;
        }
        userTrackerImpl.addCallback(this.mCurrentUserTrackerCallback, new HandlerExecutor(new Handler(Looper.getMainLooper())));
        this.mSoundAssistantChecker = (SoundAssistantChecker) volumeDependency.get(SoundAssistantChecker.class);
        this.mVolumeManager = volumeManager;
        this.mAllSoundMute = Settings.Global.getInt(applicationContext.getContentResolver(), "all_sound_off", 0) != 0;
        this.mIsDisallowAdjustVolume = getDisallowAdjustVolume();
        try {
            boolean z = false;
            int i2 = Settings.Global.getInt(applicationContext.getContentResolver(), "zen_mode", 0);
            int iSemGetFineVolume = audioManager.semGetFineVolume(3);
            int iSemGetCurrentDeviceType = audioManager.semGetCurrentDeviceType();
            boolean zIsBluetoothLeBroadcastEnabled = isBluetoothLeBroadcastEnabled();
            boolean z2 = this.mAllSoundMute;
            if (i2 == 1 && (notificationManager.getConsolidatedNotificationPolicy().priorityCategories & 64) == 0) {
                z = true;
            }
            VolumeModel volumeModel = new VolumeModel(iSemGetFineVolume, 0, 150, iSemGetCurrentDeviceType, true, zIsBluetoothLeBroadcastEnabled, z2, i2, z, isSmartViewEnabled(), isMusicShareEnabled(), this.mIsDisallowAdjustVolume);
            volumeManager.volumeModel = volumeModel;
            Log.i("SoundCraft.VolumeManager", "updateVolumeState " + volumeModel);
            volumeManager.updateCurrentVolume();
        } catch (Exception e) {
            Log.w(TAG, "updateVolumeBar failed on init", e);
        }
        commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.3
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onDisplayRemoveSystemDecorations(int i3) {
                Log.d(VolumeDialogControllerImpl.TAG, "onDisplayRemoveSystemDecorations");
                VolumeDialogControllerImpl.this.setVolumeController();
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void onDisplayRemoved(int i3) {
                Log.d(VolumeDialogControllerImpl.TAG, "onDisplayRemoved");
                VolumeDialogControllerImpl.this.setVolumeController();
            }
        });
    }

    public static boolean isMediaStream(int i) {
        return i == 3 || i == 21 || i == 22;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void addCallback(VolumeDialogController.Callbacks callbacks, Handler handler) {
        C c = this.mCallbacks;
        c.getClass();
        if (callbacks == null || handler == null) {
            throw new IllegalArgumentException();
        }
        ((ConcurrentHashMap) c.mCallbackMap).put(callbacks, handler);
        callbacks.onAccessibilityModeChanged(Boolean.valueOf(this.mShowA11yStream));
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean areCaptionsEnabled() {
        CaptioningManager captioningManager = (CaptioningManager) this.mCaptioningManager.get();
        if (captioningManager != null) {
            return captioningManager.isSystemAudioCaptioningEnabled();
        }
        return false;
    }

    public final boolean checkRoutedToBluetoothW(int i) {
        if (i == 3) {
            int devicesForStream = this.mAudio.getDevicesForStream(3);
            boolean z = (67108876 & devicesForStream) != 0;
            boolean z2 = ((671089568 & devicesForStream) == 0 || (devicesForStream == 536870914 && this.mBluetoothAdapterManager.getConnectedLeDevices().isEmpty())) ? false : true;
            boolean zIsBluetoothLeBroadcastEnabled = isBluetoothLeBroadcastEnabled();
            VolumeDialogController.State state = this.mState;
            state.isLeBroadcasting = zIsBluetoothLeBroadcastEnabled;
            state.broadcastMode = zIsBluetoothLeBroadcastEnabled ? z2 ? 1 : 2 : 0;
            return (isMusicShareEnabled() ? updateStreamRoutedToBluetoothW(i, true) : updateStreamRoutedToBluetoothW(i, z2)) | updateStreamRoutedToHeadsetW(i, z);
        }
        if (i != 21) {
            if (i == 23 || i == 22) {
                return updateStreamRoutedToBluetoothW(i, true);
            }
            if (i == 0) {
                return updateStreamRoutedToBluetoothW(i, (671089568 & this.mAudio.getDevicesForStream(0)) != 0);
            }
            return false;
        }
        int iSemGetPinDevice = this.mAudio.semGetPinDevice();
        boolean z3 = (67108876 & iSemGetPinDevice) != 0;
        boolean z4 = (671089568 & iSemGetPinDevice) != 0;
        boolean z5 = (iSemGetPinDevice & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0;
        boolean zUpdateStreamRoutedToBluetoothW = updateStreamRoutedToBluetoothW(i, z4) | updateStreamRoutedToHeadsetW(i, z3);
        VolumeDialogController.StreamState streamStateStreamStateW = streamStateW(i);
        if (streamStateStreamStateW.appMirroring == z5) {
            z = false;
        } else {
            streamStateStreamStateW.appMirroring = z5;
            if (D.BUG) {
                Log.d(TAG, "updateStreamRoutedToAppMirroring stream=" + i + " appMirroring=" + z5);
            }
        }
        return zUpdateStreamRoutedToBluetoothW | z;
    }

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("VolumeDialogControllerImpl state:");
        printWriter.print("  mVolumePolicy: ");
        printWriter.println((Object) null);
        printWriter.print("  mState: ");
        printWriter.println(this.mState.toString(4));
        printWriter.print("  mHasVibrator: ");
        printWriter.println(this.mHasVibrator);
        synchronized (this.mMediaSessionsCallbacksW.mRemoteStreams) {
            printWriter.print("  mRemoteStreams: ");
            try {
                printWriter.println(this.mMediaSessionsCallbacksW.mRemoteStreams.values());
            } catch (Exception unused) {
            }
        }
        printWriter.print("  mShowA11yStream: ");
        printWriter.println(this.mShowA11yStream);
        printWriter.println();
        try {
            this.mMediaSessions.dump(printWriter);
        } catch (Exception unused2) {
        }
    }

    public final void forceVolumeControlStreamW(final int i, boolean z) {
        int i2 = z ? 99 : 100;
        if (i == 21) {
            i = 10003;
        }
        if (i == 23 || i == 20 || i == 22) {
            i = 3;
        }
        if (i >= i2 && i != 10003) {
            i = -1;
        }
        if (D.BUG) {
            Log.d(TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "forceVolumeControlStream "));
        }
        this.mWorker.post(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                VolumeDialogControllerImpl volumeDialogControllerImpl = this.f$0;
                volumeDialogControllerImpl.mAudio.forceVolumeControlStream(i);
            }
        });
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final AudioManager getAudioManager() {
        return this.mAudio;
    }

    public final int getAudioManagerStreamMaxVolume(int i) {
        if (i == 20) {
            if (isSmartViewEnabled()) {
                return this.mDisplayManagerWrapper.getDisplayMaxVolume();
            }
        } else {
            if (i == 21 || i == 22) {
                return this.mAudio.getStreamMaxVolume(3);
            }
            if (i != 23) {
                return this.mAudio.getStreamMaxVolume(i);
            }
        }
        return DEFAULT_MAX_LEVEL;
    }

    public final int getAudioManagerStreamMinVolume(int i) {
        if (i != 20) {
            if (i == 21 || i == 22) {
                return this.mAudio.getStreamMinVolumeInt(3);
            }
            if (i == 23) {
                return 0;
            }
            return this.mAudio.getStreamMinVolumeInt(i);
        }
        if (!isSmartViewEnabled()) {
            return 0;
        }
        DisplayManagerWrapper displayManagerWrapper = this.mDisplayManagerWrapper;
        if (displayManagerWrapper.minSmartViewVol == -1) {
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context = displayManagerWrapper.context;
            systemServiceExtension.getClass();
            displayManagerWrapper.minSmartViewVol = ((Integer) SystemServiceExtension.getDisplayManager(context).semGetWifiDisplayConfiguration("mavo")).intValue();
        }
        return displayManagerWrapper.minSmartViewVol;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void getCaptionsComponentState(boolean z) {
        this.mWorker.obtainMessage(16, Boolean.valueOf(z)).sendToTarget();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void getCaptionsEnabledState(boolean z) {
        this.mWorker.obtainMessage(18, Boolean.valueOf(z)).sendToTarget();
    }

    public final boolean getDisallowAdjustVolume() {
        boolean zHasUserRestriction = ((UserManager) this.mContext.getSystemService("user")).hasUserRestriction("no_adjust_volume");
        Log.i(TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("getDisallowAdjustVolume enabled = ", zHasUserRestriction));
        return zHasUserRestriction;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0024 A[PHI: r0
      0x0024: PHI (r0v4 java.util.List) = (r0v3 java.util.List), (r0v12 java.util.List) binds: [B:5:0x0011, B:7:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getLastAudibleStreamVolume(int i) {
        SemBluetoothAudioCast semBluetoothAudioCast;
        boolean z;
        int fineVolume;
        if (!isMediaStream(i)) {
            if (i == 20) {
                if (isSmartViewEnabled()) {
                    return this.mDisplayManagerWrapper.displayCurrentVolume;
                }
            } else {
                if (i != 23) {
                    return this.mAudio.getStreamVolume(i);
                }
                if (this.mIsBudsTogetherEnabled && (semBluetoothAudioCast = this.mBluetoothAudioCastWrapper.service) != null) {
                    return semBluetoothAudioCast.getAudioSharingDeviceVolume((SemBluetoothCastDevice) null);
                }
            }
            return 0;
        }
        List arrayList = new ArrayList();
        VolumeDialogController.State state = this.mState;
        if (state.dualAudio) {
            arrayList = this.mBluetoothAdapterManager.getConnectedDevices(state.isLeBroadcasting);
            z = arrayList.size() == 2;
        }
        Pair pair = new Pair(Boolean.valueOf(z), arrayList);
        if (((Boolean) pair.first).booleanValue()) {
            List list = (List) pair.second;
            if (isMultiSoundBT()) {
                if (i == 3) {
                    fineVolume = this.mAudio.semGetFineVolume(3);
                } else {
                    fineVolume = this.mAudio.semGetFineVolume((BluetoothDevice) (i == 21 ? list.get(0) : list.get(1)), 3);
                }
            } else if (i == 21) {
                fineVolume = this.mAudio.getFineVolume(3, this.mAudio.semGetPinDevice());
            } else {
                fineVolume = this.mAudio.semGetFineVolume((BluetoothDevice) (i == 3 ? list.get(0) : list.get(1)), 3);
            }
        } else {
            fineVolume = i == 21 ? this.mAudio.getFineVolume(3, this.mAudio.semGetPinDevice()) : this.mAudio.semGetFineVolume(3);
        }
        return fineVolume * 10;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void getState() {
        this.mWorker.sendEmptyMessage(3);
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean hasVibrator() {
        return this.mHasVibrator;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean isAODVolumePanel() {
        return this.mState.aodEnabled;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean isAudioMirroring() {
        return this.mIsAudioMirroringEnabled;
    }

    public final boolean isBluetoothLeBroadcastEnabled() {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (localBluetoothLeBroadcast = localBluetoothProfileManager.mLeAudioBroadcast) == null) {
            return false;
        }
        return localBluetoothLeBroadcast.isEnabled(null);
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean isBudsTogetherEnabled() {
        return this.mIsBudsTogetherEnabled;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean isDLNAEnabled() {
        return this.mIsDLNAEnabled.booleanValue();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean isLeBroadcasting() {
        return isBluetoothLeBroadcastEnabled();
    }

    public final boolean isMultiSoundBT() {
        int multiSoundDevice;
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.mSoundAssistantManagerWrapper;
        return soundAssistantManagerWrapper != null && soundAssistantManagerWrapper.satMananger.isMultiSoundOn() && this.mAudio.semGetCurrentDeviceType() != (multiSoundDevice = soundAssistantManagerWrapper.satMananger.getMultiSoundDevice()) && multiSoundDevice == 8;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean isMusicShareEnabled() {
        return this.mIsMusicShareEnabled && !this.mIsBudsTogetherEnabled;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean isSmartViewEnabled() {
        return this.mIsDLNAEnabled.booleanValue() || this.mIsSupportTvVolumeControl.booleanValue();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean isVolumeStarEnabled() {
        return mIsVolumeStarEnabled;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void notifyVisible(boolean z) {
        this.mIsVolumeDialogShowing = z;
        VolumeDialogController.State state = this.mState;
        if (state.aodEnabled && !z) {
            state.aodEnabled = false;
        }
        this.mWorker.obtainMessage(12, z ? 1 : 0, 0).sendToTarget();
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x029c  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0372  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0386  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x03bb A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onVolumeChangedW(int i, int i2) {
        boolean z;
        List listMapNames;
        boolean z2;
        String castDeviceConnectedName;
        String str;
        boolean z3;
        boolean z4;
        int i3;
        int lastAudibleStreamVolume;
        boolean zUpdateStreamLevelW;
        C c;
        VolumeDialogController.StreamState streamStateStreamStateW;
        int i4;
        boolean zIsMediaStream;
        char c2;
        boolean zShouldShowUI = shouldShowUI(i2);
        boolean z5 = (i2 & 4096) != 0;
        boolean z6 = (i2 & 2048) != 0;
        boolean z7 = (i2 & 128) != 0;
        boolean z8 = (262144 & i2) != 0;
        VolumeDialogController.State state = this.mState;
        state.fixedSCOVolume = z8;
        state.remoteMic = (67108864 & i2) != 0;
        int i5 = (8388608 & i2) != 0 ? 21 : i;
        if ((4194304 & i2) != 0) {
            this.mSmartViewFlag = FLAG_SMART_VIEW_NONE;
            i5 = 20;
            streamStateW(20).levelMin = getAudioManagerStreamMinVolume(20);
            streamStateW(20).levelMax = getAudioManagerStreamMaxVolume(20);
        }
        boolean zIsBluetoothLeBroadcastEnabled = isBluetoothLeBroadcastEnabled();
        state.isLeBroadcasting = zIsBluetoothLeBroadcastEnabled;
        BluetoothAdapterWrapper bluetoothAdapterWrapper = this.mBluetoothAdapterManager;
        List connectedDevices = bluetoothAdapterWrapper.getConnectedDevices(zIsBluetoothLeBroadcastEnabled);
        state.dualAudio = (524288 & i2) != 0 && connectedDevices.size() == 2 && shouldDualAudioUIEnabled();
        BluetoothDevice bluetoothDevice = connectedDevices.isEmpty() ? null : (BluetoothDevice) connectedDevices.get(0);
        BluetoothDevice bluetoothDevice2 = (!state.dualAudio || connectedDevices.size() < 2) ? null : (BluetoothDevice) connectedDevices.get(1);
        VolumeDialogController.StreamState streamStateStreamStateW2 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
        VolumeDialogController.StreamState streamStateStreamStateW3 = streamStateW(22);
        int iSemGetCurrentDeviceType = this.mAudio.semGetCurrentDeviceType();
        if ((iSemGetCurrentDeviceType != 23 || bluetoothAdapterWrapper.getHearingAidDevices().isEmpty()) && (!(iSemGetCurrentDeviceType == 26 || state.isLeBroadcasting) || bluetoothAdapterWrapper.getConnectedLeHearingAidDevice().isEmpty())) {
            streamStateStreamStateW2.routedToHearingAid = false;
        } else {
            streamStateStreamStateW2.routedToHearingAid = true;
        }
        String address = bluetoothDevice != null ? bluetoothDevice.getAddress() : null;
        String address2 = bluetoothDevice2 != null ? bluetoothDevice2.getAddress() : null;
        if (Objects.equals(address, streamStateStreamStateW2.bluetoothDeviceAddress) && Objects.equals(address2, streamStateStreamStateW3.bluetoothDeviceAddress)) {
            z = false;
        } else {
            streamStateStreamStateW2.bluetoothDeviceAddress = address;
            streamStateStreamStateW3.bluetoothDeviceAddress = address2;
            z = true;
        }
        VolumeDialogController.StreamState streamStateStreamStateW4 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
        VolumeDialogController.StreamState streamStateStreamStateW5 = streamStateW(22);
        if (isMusicShareEnabled()) {
            castDeviceConnectedName = this.mBluetoothAudioCastWrapper.getCastDeviceConnectedName();
            z2 = z;
        } else {
            if (state.isLeBroadcasting) {
                BluetoothCommonUtil bluetoothCommonUtil = BluetoothCommonUtil.INSTANCE;
                List connectedLeDevices = bluetoothAdapterWrapper.getConnectedLeDevices();
                bluetoothCommonUtil.getClass();
                listMapNames = BluetoothCommonUtil.mapNames(connectedLeDevices);
            } else {
                int iSemGetCurrentDeviceType2 = bluetoothAdapterWrapper.audioManager.am.semGetCurrentDeviceType();
                if (iSemGetCurrentDeviceType2 == 23) {
                    BluetoothCommonUtil bluetoothCommonUtil2 = BluetoothCommonUtil.INSTANCE;
                    List hearingAidDevices = bluetoothAdapterWrapper.getHearingAidDevices();
                    bluetoothCommonUtil2.getClass();
                    listMapNames = BluetoothCommonUtil.mapNames(hearingAidDevices);
                } else if (iSemGetCurrentDeviceType2 == 26 || iSemGetCurrentDeviceType2 == 27) {
                    BluetoothCommonUtil bluetoothCommonUtil3 = BluetoothCommonUtil.INSTANCE;
                    List connectedLeDevices2 = bluetoothAdapterWrapper.getConnectedLeDevices();
                    bluetoothCommonUtil3.getClass();
                    listMapNames = BluetoothCommonUtil.mapNames(connectedLeDevices2);
                } else {
                    BluetoothA2dp bluetoothA2dp = bluetoothAdapterWrapper.a2dp;
                    if (bluetoothA2dp != null) {
                        BluetoothA2dpUtil.INSTANCE.getClass();
                        BluetoothCommonUtil bluetoothCommonUtil4 = BluetoothCommonUtil.INSTANCE;
                        List orderConnectedDevices = BluetoothA2dpUtil.getOrderConnectedDevices(bluetoothA2dp);
                        bluetoothCommonUtil4.getClass();
                        listMapNames = BluetoothCommonUtil.mapNames(orderConnectedDevices);
                    } else {
                        listMapNames = null;
                    }
                    if (!(listMapNames != null ? !listMapNames.isEmpty() : false)) {
                        listMapNames = null;
                    }
                    if (listMapNames == null) {
                        listMapNames = EmptyList.INSTANCE;
                    }
                }
            }
            if (listMapNames.isEmpty()) {
                if (streamStateStreamStateW4.bluetoothDeviceName == null) {
                    z2 = z;
                    z3 = false;
                    boolean z9 = z2 | z3;
                    VolumeDialogController.StreamState streamStateStreamStateW6 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
                    VolumeDialogController.StreamState streamStateStreamStateW7 = streamStateW(22);
                    updateStreamRoutedToBudsW(bluetoothDevice, streamStateStreamStateW6);
                    updateStreamRoutedToBudsW(bluetoothDevice2, streamStateStreamStateW7);
                    streamStateStreamStateW6.routedToHomeMini = bluetoothDevice != null && BluetoothIconUtil.isHomeMini(bluetoothDevice);
                    streamStateStreamStateW7.routedToHomeMini = bluetoothDevice2 != null && BluetoothIconUtil.isHomeMini(bluetoothDevice2);
                    streamStateStreamStateW6.routedToMusicFrame = bluetoothDevice != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice);
                    streamStateStreamStateW7.routedToMusicFrame = bluetoothDevice2 != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice2);
                    boolean zUpdateStreamLevelW2 = z9 | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                    if (zShouldShowUI) {
                    }
                    z4 = BasicRune.VOLUME_HOME_IOT;
                    if (z4) {
                    }
                    if (zShouldShowUI) {
                    }
                    lastAudibleStreamVolume = getLastAudibleStreamVolume(i5);
                    zUpdateStreamLevelW = zUpdateStreamLevelW2 | updateStreamLevelW(i5, lastAudibleStreamVolume);
                    VolumeDialogController.StreamState streamStateStreamStateW8 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
                    StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i5, i2, "onVolumeChangedW stream = ", ", flags = ", ", lastAudibleStreamVolume = ");
                    sbM.append(lastAudibleStreamVolume);
                    sbM.append(", changed = ");
                    sbM.append(zUpdateStreamLevelW);
                    sbM.append(", showUI = ");
                    sbM.append(zShouldShowUI);
                    sbM.append(", dualAudio = ");
                    sbM.append(state.dualAudio);
                    sbM.append(", musicStreamState=");
                    sbM.append(streamStateStreamStateW8);
                    Log.d(TAG, sbM.toString());
                    c = this.mCallbacks;
                    if (zUpdateStreamLevelW) {
                    }
                    if (zShouldShowUI) {
                    }
                    if (z6) {
                    }
                    if (z7) {
                    }
                    if ((i2 & 4) != 0) {
                    }
                    if (z4) {
                    }
                    streamStateStreamStateW = streamStateW(i5);
                    i4 = streamStateStreamStateW.level;
                    if (i4 != streamStateStreamStateW.levelMin) {
                        zIsMediaStream = isMediaStream(i5);
                        int i6 = streamStateStreamStateW.levelMax;
                        if (zIsMediaStream) {
                        }
                        if (i4 == i6) {
                        }
                    }
                    if (z5) {
                    }
                    if (zUpdateStreamLevelW) {
                        Events.writeEvent(4, Integer.valueOf(i5), Integer.valueOf(lastAudibleStreamVolume));
                        c.onVolumeChangedFromKey();
                    }
                    return zUpdateStreamLevelW;
                }
                streamStateStreamStateW4.bluetoothDeviceName = null;
                streamStateStreamStateW5.bluetoothDeviceName = null;
                z2 = z;
                z3 = true;
                boolean z92 = z2 | z3;
                VolumeDialogController.StreamState streamStateStreamStateW62 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
                VolumeDialogController.StreamState streamStateStreamStateW72 = streamStateW(22);
                updateStreamRoutedToBudsW(bluetoothDevice, streamStateStreamStateW62);
                updateStreamRoutedToBudsW(bluetoothDevice2, streamStateStreamStateW72);
                streamStateStreamStateW62.routedToHomeMini = bluetoothDevice != null && BluetoothIconUtil.isHomeMini(bluetoothDevice);
                streamStateStreamStateW72.routedToHomeMini = bluetoothDevice2 != null && BluetoothIconUtil.isHomeMini(bluetoothDevice2);
                streamStateStreamStateW62.routedToMusicFrame = bluetoothDevice != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice);
                streamStateStreamStateW72.routedToMusicFrame = bluetoothDevice2 != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice2);
                boolean zUpdateStreamLevelW22 = z92 | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                if (zShouldShowUI) {
                    zUpdateStreamLevelW22 |= checkRoutedToBluetoothW(i5);
                    if (i5 == 3) {
                        zUpdateStreamLevelW22 |= checkRoutedToBluetoothW(21);
                    }
                }
                z4 = BasicRune.VOLUME_HOME_IOT;
                if (z4) {
                    VolumeDialogController.StreamState streamStateStreamStateW9 = streamStateW(i5);
                    int lastAudibleStreamVolume2 = getLastAudibleStreamVolume(i5);
                    if (streamStateStreamStateW9.levelMin == (isMediaStream(i5) ? lastAudibleStreamVolume2 / 100 : lastAudibleStreamVolume2) && streamStateStreamStateW9.level == lastAudibleStreamVolume2) {
                        i3 = 2;
                    } else {
                        i3 = (streamStateStreamStateW9.levelMax == (isMediaStream(i5) ? lastAudibleStreamVolume2 / 100 : lastAudibleStreamVolume2) && streamStateStreamStateW9.level == lastAudibleStreamVolume2) ? 3 : streamStateStreamStateW9.level < lastAudibleStreamVolume2 ? 1 : 0;
                    }
                }
                if (zShouldShowUI) {
                    zUpdateStreamLevelW22 |= updateActiveStreamW(i5);
                }
                lastAudibleStreamVolume = getLastAudibleStreamVolume(i5);
                zUpdateStreamLevelW = zUpdateStreamLevelW22 | updateStreamLevelW(i5, lastAudibleStreamVolume);
                VolumeDialogController.StreamState streamStateStreamStateW82 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
                StringBuilder sbM2 = MutableObjectList$$ExternalSyntheticOutline0.m(i5, i2, "onVolumeChangedW stream = ", ", flags = ", ", lastAudibleStreamVolume = ");
                sbM2.append(lastAudibleStreamVolume);
                sbM2.append(", changed = ");
                sbM2.append(zUpdateStreamLevelW);
                sbM2.append(", showUI = ");
                sbM2.append(zShouldShowUI);
                sbM2.append(", dualAudio = ");
                sbM2.append(state.dualAudio);
                sbM2.append(", musicStreamState=");
                sbM2.append(streamStateStreamStateW82);
                Log.d(TAG, sbM2.toString());
                c = this.mCallbacks;
                if (zUpdateStreamLevelW) {
                    c.onStateChanged(state);
                    updateVolumeBar();
                }
                if (zShouldShowUI) {
                    this.mCallbacks.onShowRequested(1, this.mKeyguardManager.isKeyguardLocked(), this.mActivityManager.getLockTaskModeState());
                }
                if (z6) {
                    c.onShowVibrateHint();
                }
                if (z7) {
                    c.onShowSilentHint();
                }
                boolean z10 = (i2 & 4) != 0;
                if (z4) {
                    if (zShouldShowUI) {
                        if (z10 || i3 == 3 || i3 == 2) {
                            c.onPlaySound(i5, z5, i3);
                        }
                        int i7 = isMediaStream(i5) ? lastAudibleStreamVolume / 100 : lastAudibleStreamVolume;
                        Intent intent = new Intent("com.android.server.LightsService.action.LED_CONTROL_WHITE_LED_PATTERN");
                        intent.putExtra("details", "Volume:Light");
                        intent.putExtra("mode", 10);
                        intent.putExtra("extra", i7);
                        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                    }
                } else if (z10) {
                    c.onPlaySound(i5, z5);
                }
                streamStateStreamStateW = streamStateW(i5);
                i4 = streamStateStreamStateW.level;
                if (i4 != streamStateStreamStateW.levelMin || (i2 & 65536) == 0) {
                    zIsMediaStream = isMediaStream(i5);
                    int i62 = streamStateStreamStateW.levelMax;
                    if (zIsMediaStream) {
                        i62 *= 100;
                    }
                    c2 = (i4 == i62 || (i2 & 131072) == 0) ? (char) 0 : (char) 1;
                } else {
                    c2 = 65535;
                }
                if (z5) {
                    if (c2 == 0 || !zShouldShowUI) {
                        if (this.mKeyDown) {
                            this.mKeyDown = false;
                            c.onKeyEvent(false, this.mIsVibrating);
                        }
                    } else if (!this.mKeyDown) {
                        this.mKeyDown = true;
                        c.onKeyEvent(true, this.mIsVibrating || (this.mAudio.getRingerModeInternal() == 1 && state.ringerModeInternal == 2));
                    }
                }
                if (zUpdateStreamLevelW && z5) {
                    Events.writeEvent(4, Integer.valueOf(i5), Integer.valueOf(lastAudibleStreamVolume));
                    c.onVolumeChangedFromKey();
                }
                return zUpdateStreamLevelW;
            }
            String str2 = (String) listMapNames.get(0);
            if (state.dualAudio) {
                z2 = z;
                if (listMapNames.size() == 2) {
                    String str3 = (String) listMapNames.get(1);
                    castDeviceConnectedName = str2;
                    str = str3;
                    if (Objects.equals(castDeviceConnectedName, streamStateStreamStateW4.bluetoothDeviceName) || !Objects.equals(str, streamStateStreamStateW5.bluetoothDeviceName)) {
                        streamStateStreamStateW4.bluetoothDeviceName = castDeviceConnectedName;
                        streamStateStreamStateW5.bluetoothDeviceName = str;
                        z3 = true;
                        boolean z922 = z2 | z3;
                        VolumeDialogController.StreamState streamStateStreamStateW622 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
                        VolumeDialogController.StreamState streamStateStreamStateW722 = streamStateW(22);
                        updateStreamRoutedToBudsW(bluetoothDevice, streamStateStreamStateW622);
                        updateStreamRoutedToBudsW(bluetoothDevice2, streamStateStreamStateW722);
                        streamStateStreamStateW622.routedToHomeMini = bluetoothDevice != null && BluetoothIconUtil.isHomeMini(bluetoothDevice);
                        streamStateStreamStateW722.routedToHomeMini = bluetoothDevice2 != null && BluetoothIconUtil.isHomeMini(bluetoothDevice2);
                        streamStateStreamStateW622.routedToMusicFrame = bluetoothDevice != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice);
                        streamStateStreamStateW722.routedToMusicFrame = bluetoothDevice2 != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice2);
                        boolean zUpdateStreamLevelW222 = z922 | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                        if (zShouldShowUI) {
                        }
                        z4 = BasicRune.VOLUME_HOME_IOT;
                        if (z4) {
                        }
                        if (zShouldShowUI) {
                        }
                        lastAudibleStreamVolume = getLastAudibleStreamVolume(i5);
                        zUpdateStreamLevelW = zUpdateStreamLevelW222 | updateStreamLevelW(i5, lastAudibleStreamVolume);
                        VolumeDialogController.StreamState streamStateStreamStateW822 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
                        StringBuilder sbM22 = MutableObjectList$$ExternalSyntheticOutline0.m(i5, i2, "onVolumeChangedW stream = ", ", flags = ", ", lastAudibleStreamVolume = ");
                        sbM22.append(lastAudibleStreamVolume);
                        sbM22.append(", changed = ");
                        sbM22.append(zUpdateStreamLevelW);
                        sbM22.append(", showUI = ");
                        sbM22.append(zShouldShowUI);
                        sbM22.append(", dualAudio = ");
                        sbM22.append(state.dualAudio);
                        sbM22.append(", musicStreamState=");
                        sbM22.append(streamStateStreamStateW822);
                        Log.d(TAG, sbM22.toString());
                        c = this.mCallbacks;
                        if (zUpdateStreamLevelW) {
                        }
                        if (zShouldShowUI) {
                        }
                        if (z6) {
                        }
                        if (z7) {
                        }
                        if ((i2 & 4) != 0) {
                        }
                        if (z4) {
                        }
                        streamStateStreamStateW = streamStateW(i5);
                        i4 = streamStateStreamStateW.level;
                        if (i4 != streamStateStreamStateW.levelMin) {
                        }
                        if (z5) {
                        }
                        if (zUpdateStreamLevelW) {
                        }
                        return zUpdateStreamLevelW;
                    }
                    z3 = false;
                    boolean z9222 = z2 | z3;
                    VolumeDialogController.StreamState streamStateStreamStateW6222 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
                    VolumeDialogController.StreamState streamStateStreamStateW7222 = streamStateW(22);
                    updateStreamRoutedToBudsW(bluetoothDevice, streamStateStreamStateW6222);
                    updateStreamRoutedToBudsW(bluetoothDevice2, streamStateStreamStateW7222);
                    streamStateStreamStateW6222.routedToHomeMini = bluetoothDevice != null && BluetoothIconUtil.isHomeMini(bluetoothDevice);
                    streamStateStreamStateW7222.routedToHomeMini = bluetoothDevice2 != null && BluetoothIconUtil.isHomeMini(bluetoothDevice2);
                    streamStateStreamStateW6222.routedToMusicFrame = bluetoothDevice != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice);
                    streamStateStreamStateW7222.routedToMusicFrame = bluetoothDevice2 != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice2);
                    boolean zUpdateStreamLevelW2222 = z9222 | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                    if (zShouldShowUI) {
                    }
                    z4 = BasicRune.VOLUME_HOME_IOT;
                    if (z4) {
                    }
                    if (zShouldShowUI) {
                    }
                    lastAudibleStreamVolume = getLastAudibleStreamVolume(i5);
                    zUpdateStreamLevelW = zUpdateStreamLevelW2222 | updateStreamLevelW(i5, lastAudibleStreamVolume);
                    VolumeDialogController.StreamState streamStateStreamStateW8222 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
                    StringBuilder sbM222 = MutableObjectList$$ExternalSyntheticOutline0.m(i5, i2, "onVolumeChangedW stream = ", ", flags = ", ", lastAudibleStreamVolume = ");
                    sbM222.append(lastAudibleStreamVolume);
                    sbM222.append(", changed = ");
                    sbM222.append(zUpdateStreamLevelW);
                    sbM222.append(", showUI = ");
                    sbM222.append(zShouldShowUI);
                    sbM222.append(", dualAudio = ");
                    sbM222.append(state.dualAudio);
                    sbM222.append(", musicStreamState=");
                    sbM222.append(streamStateStreamStateW8222);
                    Log.d(TAG, sbM222.toString());
                    c = this.mCallbacks;
                    if (zUpdateStreamLevelW) {
                    }
                    if (zShouldShowUI) {
                    }
                    if (z6) {
                    }
                    if (z7) {
                    }
                    if ((i2 & 4) != 0) {
                    }
                    if (z4) {
                    }
                    streamStateStreamStateW = streamStateW(i5);
                    i4 = streamStateStreamStateW.level;
                    if (i4 != streamStateStreamStateW.levelMin) {
                    }
                    if (z5) {
                    }
                    if (zUpdateStreamLevelW) {
                    }
                    return zUpdateStreamLevelW;
                }
            } else {
                z2 = z;
            }
            castDeviceConnectedName = str2;
        }
        str = null;
        if (Objects.equals(castDeviceConnectedName, streamStateStreamStateW4.bluetoothDeviceName)) {
        }
        streamStateStreamStateW4.bluetoothDeviceName = castDeviceConnectedName;
        streamStateStreamStateW5.bluetoothDeviceName = str;
        z3 = true;
        boolean z92222 = z2 | z3;
        VolumeDialogController.StreamState streamStateStreamStateW62222 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
        VolumeDialogController.StreamState streamStateStreamStateW72222 = streamStateW(22);
        updateStreamRoutedToBudsW(bluetoothDevice, streamStateStreamStateW62222);
        updateStreamRoutedToBudsW(bluetoothDevice2, streamStateStreamStateW72222);
        streamStateStreamStateW62222.routedToHomeMini = bluetoothDevice != null && BluetoothIconUtil.isHomeMini(bluetoothDevice);
        streamStateStreamStateW72222.routedToHomeMini = bluetoothDevice2 != null && BluetoothIconUtil.isHomeMini(bluetoothDevice2);
        streamStateStreamStateW62222.routedToMusicFrame = bluetoothDevice != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice);
        streamStateStreamStateW72222.routedToMusicFrame = bluetoothDevice2 != null && BluetoothIconUtil.isMusicFrame(bluetoothDevice2);
        boolean zUpdateStreamLevelW22222 = z92222 | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
        if (zShouldShowUI) {
        }
        z4 = BasicRune.VOLUME_HOME_IOT;
        if (z4) {
        }
        if (zShouldShowUI) {
        }
        lastAudibleStreamVolume = getLastAudibleStreamVolume(i5);
        zUpdateStreamLevelW = zUpdateStreamLevelW22222 | updateStreamLevelW(i5, lastAudibleStreamVolume);
        VolumeDialogController.StreamState streamStateStreamStateW82222 = streamStateW(StreamUtil.getMusicStream(isMultiSoundBT()));
        StringBuilder sbM2222 = MutableObjectList$$ExternalSyntheticOutline0.m(i5, i2, "onVolumeChangedW stream = ", ", flags = ", ", lastAudibleStreamVolume = ");
        sbM2222.append(lastAudibleStreamVolume);
        sbM2222.append(", changed = ");
        sbM2222.append(zUpdateStreamLevelW);
        sbM2222.append(", showUI = ");
        sbM2222.append(zShouldShowUI);
        sbM2222.append(", dualAudio = ");
        sbM2222.append(state.dualAudio);
        sbM2222.append(", musicStreamState=");
        sbM2222.append(streamStateStreamStateW82222);
        Log.d(TAG, sbM2222.toString());
        c = this.mCallbacks;
        if (zUpdateStreamLevelW) {
        }
        if (zShouldShowUI) {
        }
        if (z6) {
        }
        if (z7) {
        }
        if ((i2 & 4) != 0) {
        }
        if (z4) {
        }
        streamStateStreamStateW = streamStateW(i5);
        i4 = streamStateStreamStateW.level;
        if (i4 != streamStateStreamStateW.levelMin) {
        }
        if (z5) {
        }
        if (zUpdateStreamLevelW) {
        }
        return zUpdateStreamLevelW;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void removeCallback(VolumeDialogController.Callbacks callbacks) {
        ((ConcurrentHashMap) this.mCallbacks.mCallbackMap).remove(callbacks);
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void scheduleTouchFeedback() {
        this.mLastToggledRingerOn = System.currentTimeMillis();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void setActiveStream(int i) {
        this.mWorker.obtainMessage(11, i, 0).sendToTarget();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void setCaptionsEnabledState(boolean z) {
        this.mWorker.obtainMessage(19, Boolean.valueOf(z)).sendToTarget();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void setRingerMode(int i, boolean z) {
        this.mWorker.obtainMessage(4, i, z ? 1 : 0).sendToTarget();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void setSafeVolumeDialogShowing(boolean z) {
        try {
            this.mAudioService.notifySafetyVolumeDialogVisible(this.mVolumeController, z);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void setStreamVolume(int i, int i2) {
        W w = this.mWorker;
        w.removeMessages(10);
        w.obtainMessage(10, i, i2).sendToTarget();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void setStreamVolumeDualAudio(int i, int i2, String str) {
        W w = this.mWorker;
        w.removeMessages(21);
        w.obtainMessage(21, i, i2, str).sendToTarget();
    }

    public final void setVolumeController() {
        try {
            this.mAudio.setVolumeController(this.mVolumeController);
        } catch (SecurityException e) {
            Log.w(TAG, "Unable to set the volume controller", e);
        }
    }

    public boolean shouldDualAudioUIEnabled() {
        int iSemGetCurrentDeviceType = this.mAudio.semGetCurrentDeviceType();
        if (iSemGetCurrentDeviceType == 8) {
            return true;
        }
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.mSoundAssistantManagerWrapper;
        return (soundAssistantManagerWrapper == null || !soundAssistantManagerWrapper.satMananger.isMultiSoundOn() || iSemGetCurrentDeviceType == soundAssistantManagerWrapper.satMananger.getMultiSoundDevice()) ? false : true;
    }

    public final boolean shouldShowUI(int i) {
        DeviceStateManagerWrapper deviceStateManagerWrapper;
        CustomSdkMonitor customSdkMonitor;
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (knoxStateMonitor != null && ((customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor) == null || !customSdkMonitor.mVolumePanelEnabledState)) {
            Log.d(TAG, "KnoxStateMonitor : Disable VolumeDialog");
            return false;
        }
        if (!BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG || (deviceStateManagerWrapper = this.mDeviceStateManagerWrapper) == null || !deviceStateManagerWrapper.isFolded || !this.mShowVolumeDialog || (i & 1) == 0) {
            int i2 = this.mWakefulnessLifecycle.mWakefulness;
            if (!this.mState.aodEnabled ? i2 == 0 || i2 == 3 || !this.mDeviceInteractive || (i & 1) == 0 || !this.mShowVolumeDialog : !this.mShowVolumeDialog || (i & 1) == 0) {
                return false;
            }
        }
        return true;
    }

    public final VolumeDialogController.StreamState streamStateW(int i) {
        VolumeDialogController.State state = this.mState;
        VolumeDialogController.StreamState streamState = state.states.get(i);
        if (streamState != null) {
            return streamState;
        }
        VolumeDialogController.StreamState streamState2 = new VolumeDialogController.StreamState();
        state.states.put(i, streamState2);
        return streamState2;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean supportTvVolumeControl() {
        return this.mIsSupportTvVolumeControl.booleanValue();
    }

    public final boolean updateActiveStreamW(int i) {
        VolumeDialogController.State state = this.mState;
        if (i == state.activeStream) {
            return false;
        }
        state.activeStream = i;
        Events.writeEvent(2, Integer.valueOf(i));
        if (D.BUG) {
            Log.d(TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateActiveStreamW "));
        }
        VolumeDialogControllerImpl$$ExternalSyntheticLambda0 volumeDialogControllerImpl$$ExternalSyntheticLambda0 = new VolumeDialogControllerImpl$$ExternalSyntheticLambda0(this, i, 0);
        AudioSharingInteractor audioSharingInteractor = this.mAudioSharingInteractor;
        Objects.requireNonNull(audioSharingInteractor);
        this.mJavaAdapter.callSuspend(new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(audioSharingInteractor), this.mContext, new VolumeDialogControllerImpl$$ExternalSyntheticLambda0(this, i, 1), volumeDialogControllerImpl$$ExternalSyntheticLambda0, volumeDialogControllerImpl$$ExternalSyntheticLambda0);
        return true;
    }

    public final boolean updateEffectsSuppressorW(ComponentName componentName) {
        String strTrim;
        VolumeDialogController.State state = this.mState;
        if (Objects.equals(state.effectsSuppressor, componentName)) {
            return false;
        }
        state.effectsSuppressor = componentName;
        PackageManager packageManager = this.mPackageManager;
        if (componentName == null) {
            strTrim = null;
        } else {
            String packageName = componentName.getPackageName();
            try {
                strTrim = Objects.toString(packageManager.getApplicationInfo(packageName, 0).loadLabel(packageManager), "").trim();
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (strTrim.length() <= 0) {
                strTrim = packageName;
            }
        }
        state.effectsSuppressorName = strTrim;
        Events.writeEvent(14, state.effectsSuppressor, state.effectsSuppressorName);
        return true;
    }

    public final boolean updateRingerModeInternalW(int i) {
        VolumeDialogController.State state = this.mState;
        if (i == state.ringerModeInternal) {
            return false;
        }
        if (i == 1 && !this.mIsVibrating) {
            this.mIsVibrating = true;
            this.mWorker.postDelayed(new VolumeDialogControllerImpl$$ExternalSyntheticLambda6(this, 0), 800L);
        }
        state.ringerModeInternal = i;
        Events.writeEvent(11, Integer.valueOf(i));
        if (state.ringerModeInternal == 2 && System.currentTimeMillis() - this.mLastToggledRingerOn < 1000) {
            try {
                this.mAudioService.playSoundEffect(5, ((UserTrackerImpl) this.mUserTracker).getUserId());
            } catch (RemoteException unused) {
            }
        }
        return true;
    }

    public final boolean updateStreamLevelW(int i, int i2) {
        VolumeDialogController.StreamState streamStateStreamStateW = streamStateW(i);
        if (streamStateStreamStateW.level == i2) {
            return false;
        }
        streamStateStreamStateW.level = i2;
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            Events.writeEvent(10, Integer.valueOf(i), Integer.valueOf(i2));
        }
        return true;
    }

    public final boolean updateStreamMuteW(int i, boolean z) {
        VolumeDialogController.StreamState streamStateStreamStateW = streamStateW(i);
        if (streamStateStreamStateW.muted == z) {
            return false;
        }
        streamStateStreamStateW.muted = z;
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            Events.writeEvent(15, Integer.valueOf(i), Boolean.valueOf(z));
        }
        if (z && (i == 2 || i == 5)) {
            updateRingerModeInternalW(this.mRingerModeObservers.mRingerModeInternal.getValue().intValue());
        }
        updateVolumeBar();
        return true;
    }

    public final void updateStreamNameMusicShare() {
        VolumeDialogController.StreamState streamStateStreamStateW = streamStateW(3);
        streamStateStreamStateW.nameRes = this.mContext.getResources().getResourceName(isMusicShareEnabled() ? R.string.volumepanel_music_share : streamStateStreamStateW.name);
        if (D.BUG) {
            Log.d(TAG, "updateStreamNameMusicShare " + isMusicShareEnabled());
        }
    }

    public final boolean updateStreamRoutedToBluetoothW(int i, boolean z) {
        VolumeDialogController.StreamState streamStateStreamStateW = streamStateW(i);
        if (streamStateStreamStateW.routedToBluetooth == z) {
            return false;
        }
        streamStateStreamStateW.routedToBluetooth = z;
        if (!D.BUG) {
            return true;
        }
        Log.d(TAG, "updateStreamRoutedToBluetoothW stream=" + i + " routedToBluetooth=" + z);
        return true;
    }

    public final void updateStreamRoutedToBudsW(BluetoothDevice bluetoothDevice, VolumeDialogController.StreamState streamState) {
        boolean z = false;
        streamState.routedToBuds = bluetoothDevice != null && BluetoothIconUtil.isBuds(bluetoothDevice);
        if (bluetoothDevice != null && BluetoothIconUtil.isBuds3(bluetoothDevice) && !this.mSoundAssistantChecker.isNeedToChangeBuds3IconToBtIcon) {
            z = true;
        }
        streamState.routedToBuds3 = z;
    }

    public final boolean updateStreamRoutedToHeadsetW(int i, boolean z) {
        VolumeDialogController.StreamState streamStateStreamStateW = streamStateW(i);
        if (streamStateStreamStateW.routedToHeadset == z) {
            return false;
        }
        streamStateStreamStateW.routedToHeadset = z;
        if (!D.BUG) {
            return true;
        }
        Log.d(TAG, "updateStreamRoutedToHeadsetW stream=" + i + " routedToHeadset=" + z);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003a A[Catch: Exception -> 0x0089, TryCatch #0 {Exception -> 0x0089, blocks: (B:3:0x0006, B:5:0x000d, B:7:0x0011, B:13:0x002a, B:15:0x002f, B:17:0x0033, B:19:0x0040, B:22:0x004a, B:24:0x0054, B:28:0x005b, B:18:0x003a, B:12:0x0023), top: B:33:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateVolumeBar() {
        int iSemGetCurrentDeviceType;
        int i;
        BluetoothDevice bluetoothDevice;
        VolumeManager volumeManager = this.mVolumeManager;
        VolumeDialogController.State state = this.mState;
        try {
            if (!state.isLeBroadcasting || state.broadcastMode != 1) {
                iSemGetCurrentDeviceType = this.mAudio.semGetCurrentDeviceType();
            } else {
                if (this.mBluetoothAdapterManager.getConnectedLeHearingAidDevice().isEmpty()) {
                    i = 8;
                    int iSemGetFineVolume = (i == 8 || (bluetoothDevice = this.mActiveBtDevice) == null) ? this.mAudio.semGetFineVolume(3) : this.mAudio.semGetFineVolume(bluetoothDevice, 3);
                    if (streamStateW(3).muted) {
                        iSemGetFineVolume = 0;
                    }
                    boolean zIsBluetoothLeBroadcastEnabled = isBluetoothLeBroadcastEnabled();
                    boolean z = this.mAllSoundMute;
                    int i2 = state.zenMode;
                    VolumeModel volumeModel = new VolumeModel(iSemGetFineVolume, 0, 150, i, true, zIsBluetoothLeBroadcastEnabled, z, i2, i2 != 1 && state.disallowMedia, isSmartViewEnabled(), isMusicShareEnabled(), this.mIsDisallowAdjustVolume);
                    volumeManager.volumeModel = volumeModel;
                    Log.i("SoundCraft.VolumeManager", "updateVolumeState " + volumeModel);
                    volumeManager.updateCurrentVolume();
                }
                iSemGetCurrentDeviceType = 23;
            }
            i = iSemGetCurrentDeviceType;
            if (i == 8) {
            }
            if (streamStateW(3).muted) {
            }
            boolean zIsBluetoothLeBroadcastEnabled2 = isBluetoothLeBroadcastEnabled();
            boolean z2 = this.mAllSoundMute;
            int i22 = state.zenMode;
            VolumeModel volumeModel2 = new VolumeModel(iSemGetFineVolume, 0, 150, i, true, zIsBluetoothLeBroadcastEnabled2, z2, i22, i22 != 1 && state.disallowMedia, isSmartViewEnabled(), isMusicShareEnabled(), this.mIsDisallowAdjustVolume);
            volumeManager.volumeModel = volumeModel2;
            Log.i("SoundCraft.VolumeManager", "updateVolumeState " + volumeModel2);
            volumeManager.updateCurrentVolume();
        } catch (Exception e) {
            Log.w(TAG, "updateVolumeBar failed", e);
        }
    }

    public final boolean updateZenConfig() {
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        int i = consolidatedNotificationPolicy.priorityCategories;
        boolean z = (i & 32) == 0;
        boolean z2 = (i & 64) == 0;
        boolean z3 = (i & 128) == 0;
        boolean zAreAllPriorityOnlyRingerSoundsMuted = ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(consolidatedNotificationPolicy);
        VolumeDialogController.State state = this.mState;
        if (state.disallowAlarms == z && state.disallowMedia == z2 && state.disallowRinger == zAreAllPriorityOnlyRingerSoundsMuted && state.disallowSystem == z3) {
            return false;
        }
        state.disallowAlarms = z;
        state.disallowMedia = z2;
        state.disallowSystem = z3;
        state.disallowRinger = zAreAllPriorityOnlyRingerSoundsMuted;
        Events.writeEvent(17, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("disallowAlarms=", " disallowMedia=", " disallowSystem=", z, z2), z3, " disallowRinger=", zAreAllPriorityOnlyRingerSoundsMuted));
        return true;
    }

    public final boolean updateZenModeW() {
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "zen_mode", 0);
        VolumeDialogController.State state = this.mState;
        if (state.zenMode == i) {
            return false;
        }
        state.zenMode = i;
        Events.writeEvent(13, Integer.valueOf(i));
        return true;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void userActivity() {
        W w = this.mWorker;
        w.removeMessages(13);
        w.sendEmptyMessage(13);
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void vibrate(VibrationEffect vibrationEffect) {
        this.mVibrator.vibrate(vibrationEffect, SONIFICIATION_VIBRATION_ATTRIBUTES);
    }

    public class C implements VolumeDialogController.Callbacks {
        public final Map mCallbackMap = new ConcurrentHashMap();

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onAccessibilityModeChanged(Boolean bool) {
            final boolean z = bool != null && bool.booleanValue();
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.12
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onAccessibilityModeChanged(Boolean.valueOf(z));
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onCaptionComponentStateChanged(Boolean bool, Boolean bool2) {
            boolean z = bool != null && bool.booleanValue();
            for (Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0(entry, z, bool2, 1));
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onCaptionEnabledStateChanged(Boolean bool, Boolean bool2) {
            boolean z = bool != null && bool.booleanValue();
            for (Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0(entry, z, bool2, 0));
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onConfigurationChanged() {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.5
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onConfigurationChanged();
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onDismissRequested(final int i) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onDismissRequested(i);
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onKeyEvent(final boolean z, final boolean z2) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$C$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        Map.Entry entry2 = entry;
                        ((VolumeDialogController.Callbacks) entry2.getKey()).onKeyEvent(z, z2);
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onLayoutDirectionChanged(final int i) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onLayoutDirectionChanged(i);
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onPlaySound(final int i, final boolean z) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$C$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Map.Entry entry2 = entry;
                        ((VolumeDialogController.Callbacks) entry2.getKey()).onPlaySound(i, z);
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onScreenOff() {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.8
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onScreenOff();
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowCsdWarning(final int i, final int i2) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.10
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowCsdWarning(i, i2);
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowRequested(final int i, final boolean z, final int i2) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowRequested(i, z, i2);
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSafetyWarning(final int i) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.9
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowSafetyWarning(i);
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSilentHint() {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.7
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowSilentHint();
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowVibrateHint() {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onShowVibrateHint();
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowVolumeLimiterToast() {
            for (Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new VolumeDialogControllerImpl$$ExternalSyntheticLambda6(entry, 4));
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onStateChanged(VolumeDialogController.State state) {
            System.currentTimeMillis();
            final VolumeDialogController.State stateCopy = state.copy();
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onStateChanged(stateCopy);
                    }
                });
            }
            String str = Events.TAG;
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onVolumeChangedFromKey() {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.11
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onVolumeChangedFromKey();
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onPlaySound(final int i, final boolean z, final int i2) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$C$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        Map.Entry entry2 = entry;
                        ((VolumeDialogController.Callbacks) entry2.getKey()).onPlaySound(i, z, i2);
                    }
                });
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033 A[PHI: r0
      0x0033: PHI (r0v3 java.util.List) = (r0v2 java.util.List), (r0v10 java.util.List), (r0v10 java.util.List), (r0v10 java.util.List) binds: [B:8:0x0019, B:10:0x0027, B:11:0x0029, B:13:0x002f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setStreamVolume(int i, int i2, final String str) {
        boolean z;
        if (i == 20) {
            return;
        }
        if (!isMediaStream(i)) {
            if (i == 23) {
                if (this.mIsBudsTogetherEnabled) {
                    SemBluetoothAudioCast semBluetoothAudioCast = this.mBluetoothAudioCastWrapper.service;
                    if (semBluetoothAudioCast != null) {
                        semBluetoothAudioCast.setAudioSharingDeviceVolume((SemBluetoothCastDevice) null, i2);
                    }
                    onVolumeChangedW(i, 0);
                    return;
                }
                return;
            }
            this.mAudio.setStreamVolume(i, i2, 0);
            return;
        }
        int i3 = i2 / 10;
        List arrayList = new ArrayList();
        VolumeDialogController.State state = this.mState;
        if (state.dualAudio) {
            arrayList = this.mBluetoothAdapterManager.getConnectedDevices(state.isLeBroadcasting);
            z = (arrayList.isEmpty() || str == null || str.isEmpty()) ? false : true;
        }
        Pair pair = new Pair(Boolean.valueOf(z), arrayList);
        if (!((Boolean) pair.first).booleanValue()) {
            if (i == 21) {
                this.mAudio.setFineVolume(3, i3, 0, this.mAudio.semGetPinDevice());
                return;
            } else {
                this.mAudio.semSetFineVolume(3, i3, 0);
                return;
            }
        }
        BluetoothDevice bluetoothDevice = (BluetoothDevice) ((List) pair.second).stream().filter(new Predicate() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                String str2 = str;
                String str3 = VolumeDialogControllerImpl.TAG;
                return str2.equals(((BluetoothDevice) obj).getAddress());
            }
        }).findFirst().orElse(null);
        if (bluetoothDevice != null) {
            this.mAudio.semSetFineVolume(bluetoothDevice, 3, i3, 0);
        } else {
            this.mAudio.semSetFineVolume(3, i3, 0);
        }
    }
}
