package com.android.systemui.volume;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
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
import android.os.UserManager;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Log;
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
import com.android.systemui.volume.util.BluetoothAdapterWrapper;
import com.android.systemui.volume.util.BluetoothAudioCastWrapper;
import com.android.systemui.volume.util.BluetoothIconUtil;
import com.android.systemui.volume.util.BroadcastReceiverManager;
import com.android.systemui.volume.util.DesktopManagerWrapper;
import com.android.systemui.volume.util.DeviceStateManagerWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.SALoggingWrapper;
import com.android.systemui.volume.util.SoundAssistantManagerWrapper;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        if ("com.samsung.android.audiomirroring".equals(VolumeDialogControllerImpl.m3195$$Nest$mgetMediaControllerFromSessionId(VolumeDialogControllerImpl.this, media).getPackageName())) {
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
                    int intValue = ((Integer) this.mRemoteStreams.get(media)).intValue();
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    if (volumeDialogControllerImpl.mIsAudioMirroringEnabled && "com.samsung.android.audiomirroring".equals(VolumeDialogControllerImpl.m3195$$Nest$mgetMediaControllerFromSessionId(volumeDialogControllerImpl, media).getPackageName())) {
                        VolumeDialogControllerImpl.this.mIsAudioMirroringEnabled = false;
                        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(intValue, "onRemoteRemoved ", " - AudioMirroring is off", VolumeDialogControllerImpl.TAG);
                    }
                    VolumeDialogControllerImpl.this.mState.states.remove(intValue);
                    VolumeDialogControllerImpl.this.mVolumeManager.updateRemoteVolume(false, intValue, 0, 0, 0, media.token, false);
                    VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                    if (volumeDialogControllerImpl2.mState.activeStream == intValue) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Receiver extends BroadcastReceiver {
        public /* synthetic */ Receiver(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean z = false;
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
                    boolean checkRoutedToBluetoothW = volumeDialogControllerImpl.checkRoutedToBluetoothW(intExtra);
                    if (intExtra == 3) {
                        checkRoutedToBluetoothW = checkRoutedToBluetoothW | VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(21) | VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(22);
                    }
                    z = checkRoutedToBluetoothW | VolumeDialogControllerImpl.this.onVolumeChangedW(intExtra, 0);
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
                    z = volumeDialogControllerImpl2.updateStreamMuteW(intExtra4, booleanExtra);
                }
                VolumeDialogControllerImpl.m3197$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, intExtra4);
                if (intExtra4 == 3) {
                    VolumeDialogControllerImpl.this.updateStreamMuteW(21, booleanExtra);
                    VolumeDialogControllerImpl.m3197$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, 21);
                    ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("onReceive STREAM_MUTE_CHANGED_ACTION : stream=", intExtra4, ", muted=", booleanExtra, ", mState.dualAudio="), VolumeDialogControllerImpl.this.mState.dualAudio, VolumeDialogControllerImpl.TAG);
                    VolumeDialogControllerImpl.this.updateStreamMuteW(22, booleanExtra);
                    VolumeDialogControllerImpl.m3197$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, 22);
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
                z = volumeDialogControllerImpl4.updateEffectsSuppressorW(volumeDialogControllerImpl4.mNoMan.getEffectsSuppressor());
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
            if (z) {
                VolumeDialogControllerImpl volumeDialogControllerImpl5 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl5.mCallbacks.onStateChanged(volumeDialogControllerImpl5.mState);
            }
        }

        private Receiver() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RingerModeObservers {
        public final RingerModeLiveData mRingerMode;
        public final RingerModeLiveData mRingerModeInternal;
        public final AnonymousClass1 mRingerModeObserver = new AnonymousClass1();
        public final AnonymousClass2 mRingerModeInternalObserver = new AnonymousClass2();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$RingerModeObservers$1, reason: invalid class name */
        public class AnonymousClass1 implements Observer {
            public AnonymousClass1() {
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VolumeDialogControllerImpl.this.mWorker.post(new VolumeDialogControllerImpl$RingerModeObservers$1$$ExternalSyntheticLambda0(this, (Integer) obj, 0));
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            boolean z2;
            if (this.ZEN_MODE_URI.equals(uri)) {
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                String str = VolumeDialogControllerImpl.TAG;
                z2 = volumeDialogControllerImpl.updateZenModeW();
            } else {
                z2 = false;
            }
            if (this.ZEN_MODE_CONFIG_URI.equals(uri)) {
                VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                String str2 = VolumeDialogControllerImpl.TAG;
                z2 |= volumeDialogControllerImpl2.updateZenConfig();
            }
            if (this.ALL_SOUND_MUTE_URI.equals(uri)) {
                boolean z3 = Settings.Global.getInt(VolumeDialogControllerImpl.this.mContext.getContentResolver(), "all_sound_off", 0) != 0;
                VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                if (volumeDialogControllerImpl3.mAllSoundMute != z3) {
                    volumeDialogControllerImpl3.mAllSoundMute = z3;
                    z2 = true;
                }
            }
            if (z2) {
                VolumeDialogControllerImpl volumeDialogControllerImpl4 = VolumeDialogControllerImpl.this;
                String str3 = VolumeDialogControllerImpl.TAG;
                volumeDialogControllerImpl4.updateVolumeBar();
                VolumeDialogControllerImpl volumeDialogControllerImpl5 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl5.mCallbacks.onStateChanged(volumeDialogControllerImpl5.mState);
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        int intValue = num.intValue();
                        volumeDialogControllerImpl2.updateStreamLevelW(intValue, volumeDialogControllerImpl2.getLastAudibleStreamVolume(intValue));
                        volumeDialogControllerImpl2.streamStateW(intValue).levelMin = volumeDialogControllerImpl2.getAudioManagerStreamMinVolume(intValue);
                        volumeDialogControllerImpl2.streamStateW(intValue).levelMax = volumeDialogControllerImpl2.getAudioManagerStreamMaxVolume(intValue);
                        volumeDialogControllerImpl2.updateStreamMuteW(intValue, (intValue == 20 || intValue == 23) ? false : (intValue == 21 || intValue == 22) ? volumeDialogControllerImpl2.mAudio.isStreamMute(3) : volumeDialogControllerImpl2.mAudio.isStreamMute(intValue));
                        VolumeDialogController.StreamState streamStateW = volumeDialogControllerImpl2.streamStateW(intValue);
                        streamStateW.muteSupported = volumeDialogControllerImpl2.mAudio.isStreamMutableByUi(intValue);
                        streamStateW.name = ((Integer) VolumeDialogControllerImpl.STREAMS.get(num)).intValue();
                        volumeDialogControllerImpl2.checkRoutedToBluetoothW(intValue);
                        streamStateW.nameRes = volumeDialogControllerImpl2.mContext.getResources().getResourceName(streamStateW.name);
                    }
                    Integer value = volumeDialogControllerImpl2.mRingerModeObservers.mRingerMode.getValue();
                    int intValue2 = value.intValue();
                    VolumeDialogController.State state = volumeDialogControllerImpl2.mState;
                    if (intValue2 != state.ringerModeExternal) {
                        if (intValue2 == 1 && !volumeDialogControllerImpl2.mIsVibrating) {
                            volumeDialogControllerImpl2.mIsVibrating = true;
                            volumeDialogControllerImpl2.mWorker.postDelayed(new VolumeDialogControllerImpl$$ExternalSyntheticLambda6(volumeDialogControllerImpl2, 0), 800L);
                        }
                        state.ringerModeExternal = intValue2;
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
                    boolean booleanValue = ((Boolean) message.obj).booleanValue();
                    CaptioningManager captioningManager3 = (CaptioningManager) volumeDialogControllerImpl13.mCaptioningManager.get();
                    if (captioningManager3 == null) {
                        Log.e(VolumeDialogControllerImpl.TAG, "onGetCaptionsEnabledStateW(), null captioningManager");
                        return;
                    } else {
                        captioningManager3.setSystemAudioCaptioningEnabled(booleanValue);
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
                        StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i10, i11, "onSetStreamVolumeDualAudioW ", " level=", " btDeviceAddress=");
                        m.append(str9);
                        Log.d(VolumeDialogControllerImpl.TAG, m.toString());
                    }
                    volumeDialogControllerImpl14.setStreamVolume(i10, i11, str9);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: -$$Nest$mgetMediaControllerFromSessionId, reason: not valid java name */
    public static MediaController m3195$$Nest$mgetMediaControllerFromSessionId(VolumeDialogControllerImpl volumeDialogControllerImpl, MediaSessions.SessionId.Media media) {
        volumeDialogControllerImpl.getClass();
        return new MediaController(volumeDialogControllerImpl.mContext, media.token);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: -$$Nest$mupdateRemoteFixedVolumeSession, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m3196$$Nest$mupdateRemoteFixedVolumeSession(com.android.systemui.volume.VolumeDialogControllerImpl r1, int r2, android.media.session.MediaController.PlaybackInfo r3) {
        /*
            if (r3 == 0) goto Ld
            r1.getClass()
            int r3 = r3.getVolumeControl()
            if (r3 != 0) goto Ld
            r3 = 1
            goto Le
        Ld:
            r3 = 0
        Le:
            com.android.systemui.plugins.VolumeDialogController$StreamState r1 = r1.streamStateW(r2)
            boolean r0 = r1.remoteFixedVolume
            if (r0 != r3) goto L17
            goto L39
        L17:
            r1.remoteFixedVolume = r3
            boolean r1 = com.android.systemui.volume.D.BUG
            if (r1 == 0) goto L39
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r0 = "updateRemoteFixedVolumeSession stream="
            r1.<init>(r0)
            r1.append(r2)
            java.lang.String r2 = " remoteFixedVolume="
            r1.append(r2)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG
            android.util.Log.d(r2, r1)
        L39:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogControllerImpl.m3196$$Nest$mupdateRemoteFixedVolumeSession(com.android.systemui.volume.VolumeDialogControllerImpl, int, android.media.session.MediaController$PlaybackInfo):void");
    }

    /* renamed from: -$$Nest$mupdateStreamVolume, reason: not valid java name */
    public static void m3197$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
        volumeDialogControllerImpl.updateStreamLevelW(i, volumeDialogControllerImpl.getLastAudibleStreamVolume(i));
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        STREAMS = arrayMap;
        Integer valueOf = Integer.valueOf(R.string.volume_icon_description_incall);
        arrayMap.put(0, valueOf);
        arrayMap.put(1, Integer.valueOf(R.string.volumepanel_system));
        arrayMap.put(2, Integer.valueOf(R.string.volumepanel_ringtone));
        Integer valueOf2 = Integer.valueOf(R.string.volumepanel_media);
        arrayMap.put(3, valueOf2);
        arrayMap.put(4, Integer.valueOf(R.string.volume_alarm));
        arrayMap.put(5, Integer.valueOf(R.string.volumepanel_notification));
        arrayMap.put(6, valueOf);
        arrayMap.put(7, Integer.valueOf(R.string.stream_system_enforced));
        arrayMap.put(8, Integer.valueOf(R.string.stream_dtmf));
        arrayMap.put(9, Integer.valueOf(R.string.stream_tts));
        arrayMap.put(10, Integer.valueOf(R.string.stream_accessibility));
        arrayMap.put(20, valueOf2);
        arrayMap.put(21, valueOf2);
        arrayMap.put(11, Integer.valueOf(R.string.volumepanel_bixby_voice));
        arrayMap.put(22, valueOf2);
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
        Looper buildLooperOnNewThread = threadFactory.buildLooperOnNewThread("VolumeDialogControllerImpl");
        W w = new W(buildLooperOnNewThread);
        this.mWorker = w;
        this.mRouter2Manager = MediaRouter2Manager.getInstance(applicationContext);
        MediaSessionsCallbacks mediaSessionsCallbacks = new MediaSessionsCallbacks();
        this.mMediaSessionsCallbacksW = mediaSessionsCallbacks;
        this.mMediaSessions = new MediaSessions(applicationContext, buildLooperOnNewThread, mediaSessionsCallbacks);
        this.mAudioSharingInteractor = audioSharingInteractor;
        this.mJavaAdapter = javaAdapter;
        this.mVolumeLogger = volumeLogger;
        this.mAudio = audioManager;
        this.mNoMan = notificationManager;
        SettingObserver settingObserver = new SettingObserver(w);
        RingerModeObservers ringerModeObservers = new RingerModeObservers((RingerModeLiveData) ringerModeTracker.getRingerMode(), (RingerModeLiveData) ringerModeTracker.getRingerModeInternal());
        this.mRingerModeObservers = ringerModeObservers;
        RingerModeLiveData ringerModeLiveData = ringerModeObservers.mRingerMode;
        int intValue = ringerModeLiveData.getValue().intValue();
        VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
        if (intValue != -1) {
            volumeDialogControllerImpl.mState.ringerModeExternal = intValue;
        }
        ringerModeLiveData.observeForever(ringerModeObservers.mRingerModeObserver);
        RingerModeLiveData ringerModeLiveData2 = ringerModeObservers.mRingerModeInternal;
        int intValue2 = ringerModeLiveData2.getValue().intValue();
        if (intValue2 != -1) {
            volumeDialogControllerImpl.mState.ringerModeInternal = intValue2;
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
            int semGetFineVolume = audioManager.semGetFineVolume(3);
            int semGetCurrentDeviceType = audioManager.semGetCurrentDeviceType();
            boolean isBluetoothLeBroadcastEnabled = isBluetoothLeBroadcastEnabled();
            boolean z2 = this.mAllSoundMute;
            if (i2 == 1 && (notificationManager.getConsolidatedNotificationPolicy().priorityCategories & 64) == 0) {
                z = true;
            }
            VolumeModel volumeModel = new VolumeModel(semGetFineVolume, 0, 150, semGetCurrentDeviceType, true, isBluetoothLeBroadcastEnabled, z2, i2, z, isSmartViewEnabled(), isMusicShareEnabled(), this.mIsDisallowAdjustVolume);
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
            boolean isBluetoothLeBroadcastEnabled = isBluetoothLeBroadcastEnabled();
            VolumeDialogController.State state = this.mState;
            state.isLeBroadcasting = isBluetoothLeBroadcastEnabled;
            state.broadcastMode = isBluetoothLeBroadcastEnabled ? z2 ? 1 : 2 : 0;
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
        int semGetPinDevice = this.mAudio.semGetPinDevice();
        boolean z3 = (67108876 & semGetPinDevice) != 0;
        boolean z4 = (671089568 & semGetPinDevice) != 0;
        boolean z5 = (semGetPinDevice & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0;
        boolean updateStreamRoutedToBluetoothW = updateStreamRoutedToBluetoothW(i, z4) | updateStreamRoutedToHeadsetW(i, z3);
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.appMirroring == z5) {
            r0 = false;
        } else {
            streamStateW.appMirroring = z5;
            if (D.BUG) {
                Log.d(TAG, "updateStreamRoutedToAppMirroring stream=" + i + " appMirroring=" + z5);
            }
        }
        return updateStreamRoutedToBluetoothW | r0;
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
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
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
        boolean hasUserRestriction = ((UserManager) this.mContext.getSystemService("user")).hasUserRestriction("no_adjust_volume");
        Log.i(TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("getDisallowAdjustVolume enabled = ", hasUserRestriction));
        return hasUserRestriction;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getLastAudibleStreamVolume(int r7) {
        /*
            r6 = this;
            boolean r0 = isMediaStream(r7)
            r1 = 0
            if (r0 == 0) goto L9f
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.android.systemui.plugins.VolumeDialogController$State r2 = r6.mState
            boolean r3 = r2.dualAudio
            r4 = 1
            if (r3 == 0) goto L24
            com.android.systemui.volume.util.BluetoothAdapterWrapper r0 = r6.mBluetoothAdapterManager
            boolean r2 = r2.isLeBroadcasting
            java.util.List r0 = r0.getConnectedDevices(r2)
            int r2 = r0.size()
            r3 = 2
            if (r2 != r3) goto L24
            r2 = r4
            goto L25
        L24:
            r2 = r1
        L25:
            android.util.Pair r3 = new android.util.Pair
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            r3.<init>(r2, r0)
            java.lang.Object r0 = r3.first
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            r2 = 3
            r5 = 21
            if (r0 == 0) goto L87
            java.lang.Object r0 = r3.second
            java.util.List r0 = (java.util.List) r0
            boolean r3 = r6.isMultiSoundBT()
            if (r3 == 0) goto L63
            if (r7 != r2) goto L4e
            android.media.AudioManager r6 = r6.mAudio
            int r6 = r6.semGetFineVolume(r2)
            goto L9c
        L4e:
            android.media.AudioManager r6 = r6.mAudio
            if (r7 != r5) goto L59
            java.lang.Object r7 = r0.get(r1)
        L56:
            android.bluetooth.BluetoothDevice r7 = (android.bluetooth.BluetoothDevice) r7
            goto L5e
        L59:
            java.lang.Object r7 = r0.get(r4)
            goto L56
        L5e:
            int r6 = r6.semGetFineVolume(r7, r2)
            goto L9c
        L63:
            if (r7 != r5) goto L72
            android.media.AudioManager r7 = r6.mAudio
            int r7 = r7.semGetPinDevice()
            android.media.AudioManager r6 = r6.mAudio
            int r6 = r6.getFineVolume(r2, r7)
            goto L9c
        L72:
            android.media.AudioManager r6 = r6.mAudio
            if (r7 != r2) goto L7d
            java.lang.Object r7 = r0.get(r1)
        L7a:
            android.bluetooth.BluetoothDevice r7 = (android.bluetooth.BluetoothDevice) r7
            goto L82
        L7d:
            java.lang.Object r7 = r0.get(r4)
            goto L7a
        L82:
            int r6 = r6.semGetFineVolume(r7, r2)
            goto L9c
        L87:
            if (r7 != r5) goto L96
            android.media.AudioManager r7 = r6.mAudio
            int r7 = r7.semGetPinDevice()
            android.media.AudioManager r6 = r6.mAudio
            int r6 = r6.getFineVolume(r2, r7)
            goto L9c
        L96:
            android.media.AudioManager r6 = r6.mAudio
            int r6 = r6.semGetFineVolume(r2)
        L9c:
            int r6 = r6 * 10
            return r6
        L9f:
            r0 = 20
            if (r7 != r0) goto Lae
            boolean r7 = r6.isSmartViewEnabled()
            if (r7 == 0) goto Lc2
            com.android.systemui.volume.util.DisplayManagerWrapper r6 = r6.mDisplayManagerWrapper
            int r6 = r6.displayCurrentVolume
            return r6
        Lae:
            r0 = 23
            if (r7 != r0) goto Lc3
            boolean r7 = r6.mIsBudsTogetherEnabled
            if (r7 == 0) goto Lc2
            com.android.systemui.volume.util.BluetoothAudioCastWrapper r6 = r6.mBluetoothAudioCastWrapper
            com.samsung.android.bluetooth.SemBluetoothAudioCast r6 = r6.service
            if (r6 == 0) goto Lc2
            r7 = 0
            int r6 = r6.getAudioSharingDeviceVolume(r7)
            return r6
        Lc2:
            return r1
        Lc3:
            android.media.AudioManager r6 = r6.mAudio
            int r6 = r6.getStreamVolume(r7)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogControllerImpl.getLastAudibleStreamVolume(int):int");
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x03bb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x035b  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onVolumeChangedW(int r20, int r21) {
        /*
            Method dump skipped, instructions count: 977
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogControllerImpl.onVolumeChangedW(int, int):boolean");
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
        int semGetCurrentDeviceType = this.mAudio.semGetCurrentDeviceType();
        if (semGetCurrentDeviceType == 8) {
            return true;
        }
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.mSoundAssistantManagerWrapper;
        return (soundAssistantManagerWrapper == null || !soundAssistantManagerWrapper.satMananger.isMultiSoundOn() || semGetCurrentDeviceType == soundAssistantManagerWrapper.satMananger.getMultiSoundDevice()) ? false : true;
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002e, code lost:
    
        if (r3.length() > 0) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateEffectsSuppressorW(android.content.ComponentName r4) {
        /*
            r3 = this;
            com.android.systemui.plugins.VolumeDialogController$State r0 = r3.mState
            android.content.ComponentName r1 = r0.effectsSuppressor
            boolean r1 = java.util.Objects.equals(r1, r4)
            r2 = 0
            if (r1 == 0) goto Lc
            return r2
        Lc:
            r0.effectsSuppressor = r4
            android.content.pm.PackageManager r3 = r3.mPackageManager
            if (r4 != 0) goto L14
            r3 = 0
            goto L32
        L14:
            java.lang.String r4 = r4.getPackageName()
            android.content.pm.ApplicationInfo r1 = r3.getApplicationInfo(r4, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L31
            java.lang.CharSequence r3 = r1.loadLabel(r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L31
            java.lang.String r1 = ""
            java.lang.String r3 = java.util.Objects.toString(r3, r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L31
            java.lang.String r3 = r3.trim()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L31
            int r1 = r3.length()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L31
            if (r1 <= 0) goto L31
            goto L32
        L31:
            r3 = r4
        L32:
            r0.effectsSuppressorName = r3
            android.content.ComponentName r3 = r0.effectsSuppressor
            java.lang.String r4 = r0.effectsSuppressorName
            java.lang.Object[] r3 = new java.lang.Object[]{r3, r4}
            r4 = 14
            com.android.systemui.volume.Events.writeEvent(r4, r3)
            r3 = 1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogControllerImpl.updateEffectsSuppressorW(android.content.ComponentName):boolean");
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
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.level == i2) {
            return false;
        }
        streamStateW.level = i2;
        if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            Events.writeEvent(10, Integer.valueOf(i), Integer.valueOf(i2));
        }
        return true;
    }

    public final boolean updateStreamMuteW(int i, boolean z) {
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.muted == z) {
            return false;
        }
        streamStateW.muted = z;
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
        VolumeDialogController.StreamState streamStateW = streamStateW(3);
        streamStateW.nameRes = this.mContext.getResources().getResourceName(isMusicShareEnabled() ? R.string.volumepanel_music_share : streamStateW.name);
        if (D.BUG) {
            Log.d(TAG, "updateStreamNameMusicShare " + isMusicShareEnabled());
        }
    }

    public final boolean updateStreamRoutedToBluetoothW(int i, boolean z) {
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.routedToBluetooth == z) {
            return false;
        }
        streamStateW.routedToBluetooth = z;
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
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.routedToHeadset == z) {
            return false;
        }
        streamStateW.routedToHeadset = z;
        if (!D.BUG) {
            return true;
        }
        Log.d(TAG, "updateStreamRoutedToHeadsetW stream=" + i + " routedToHeadset=" + z);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVolumeBar() {
        /*
            r19 = this;
            r0 = r19
            com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager r1 = r0.mVolumeManager
            com.android.systemui.plugins.VolumeDialogController$State r2 = r0.mState
            boolean r3 = r2.isLeBroadcasting     // Catch: java.lang.Exception -> L89
            r4 = 8
            r5 = 1
            if (r3 == 0) goto L23
            int r3 = r2.broadcastMode     // Catch: java.lang.Exception -> L89
            if (r3 != r5) goto L23
            com.android.systemui.volume.util.BluetoothAdapterWrapper r3 = r0.mBluetoothAdapterManager     // Catch: java.lang.Exception -> L89
            java.util.List r3 = r3.getConnectedLeHearingAidDevice()     // Catch: java.lang.Exception -> L89
            boolean r3 = r3.isEmpty()     // Catch: java.lang.Exception -> L89
            if (r3 != 0) goto L21
            r3 = 23
        L1f:
            r10 = r3
            goto L2a
        L21:
            r10 = r4
            goto L2a
        L23:
            android.media.AudioManager r3 = r0.mAudio     // Catch: java.lang.Exception -> L89
            int r3 = r3.semGetCurrentDeviceType()     // Catch: java.lang.Exception -> L89
            goto L1f
        L2a:
            com.android.systemui.audio.soundcraft.model.common.VolumeModel r6 = new com.android.systemui.audio.soundcraft.model.common.VolumeModel     // Catch: java.lang.Exception -> L89
            r3 = 3
            if (r10 != r4) goto L3a
            android.bluetooth.BluetoothDevice r4 = r0.mActiveBtDevice     // Catch: java.lang.Exception -> L89
            if (r4 == 0) goto L3a
            android.media.AudioManager r7 = r0.mAudio     // Catch: java.lang.Exception -> L89
            int r4 = r7.semGetFineVolume(r4, r3)     // Catch: java.lang.Exception -> L89
            goto L40
        L3a:
            android.media.AudioManager r4 = r0.mAudio     // Catch: java.lang.Exception -> L89
            int r4 = r4.semGetFineVolume(r3)     // Catch: java.lang.Exception -> L89
        L40:
            com.android.systemui.plugins.VolumeDialogController$StreamState r3 = r0.streamStateW(r3)     // Catch: java.lang.Exception -> L89
            boolean r3 = r3.muted     // Catch: java.lang.Exception -> L89
            r7 = 0
            if (r3 == 0) goto L4a
            r4 = r7
        L4a:
            boolean r12 = r0.isBluetoothLeBroadcastEnabled()     // Catch: java.lang.Exception -> L89
            boolean r13 = r0.mAllSoundMute     // Catch: java.lang.Exception -> L89
            int r14 = r2.zenMode     // Catch: java.lang.Exception -> L89
            if (r14 != r5) goto L5a
            boolean r2 = r2.disallowMedia     // Catch: java.lang.Exception -> L89
            if (r2 == 0) goto L5a
            r15 = r5
            goto L5b
        L5a:
            r15 = r7
        L5b:
            boolean r16 = r0.isSmartViewEnabled()     // Catch: java.lang.Exception -> L89
            boolean r17 = r0.isMusicShareEnabled()     // Catch: java.lang.Exception -> L89
            boolean r0 = r0.mIsDisallowAdjustVolume     // Catch: java.lang.Exception -> L89
            r9 = 150(0x96, float:2.1E-43)
            r11 = 1
            r8 = 0
            r18 = r0
            r7 = r4
            r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)     // Catch: java.lang.Exception -> L89
            r1.volumeModel = r6     // Catch: java.lang.Exception -> L89
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L89
            java.lang.String r2 = "updateVolumeState "
            r0.<init>(r2)     // Catch: java.lang.Exception -> L89
            r0.append(r6)     // Catch: java.lang.Exception -> L89
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L89
            java.lang.String r2 = "SoundCraft.VolumeManager"
            android.util.Log.i(r2, r0)     // Catch: java.lang.Exception -> L89
            r1.updateCurrentVolume()     // Catch: java.lang.Exception -> L89
            return
        L89:
            r0 = move-exception
            java.lang.String r1 = com.android.systemui.volume.VolumeDialogControllerImpl.TAG
            java.lang.String r2 = "updateVolumeBar failed"
            android.util.Log.w(r1, r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogControllerImpl.updateVolumeBar():void");
    }

    public final boolean updateZenConfig() {
        NotificationManager.Policy consolidatedNotificationPolicy = this.mNoMan.getConsolidatedNotificationPolicy();
        int i = consolidatedNotificationPolicy.priorityCategories;
        boolean z = (i & 32) == 0;
        boolean z2 = (i & 64) == 0;
        boolean z3 = (i & 128) == 0;
        boolean areAllPriorityOnlyRingerSoundsMuted = ZenModeConfig.areAllPriorityOnlyRingerSoundsMuted(consolidatedNotificationPolicy);
        VolumeDialogController.State state = this.mState;
        if (state.disallowAlarms == z && state.disallowMedia == z2 && state.disallowRinger == areAllPriorityOnlyRingerSoundsMuted && state.disallowSystem == z3) {
            return false;
        }
        state.disallowAlarms = z;
        state.disallowMedia = z2;
        state.disallowSystem = z3;
        state.disallowRinger = areAllPriorityOnlyRingerSoundsMuted;
        Events.writeEvent(17, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("disallowAlarms=", " disallowMedia=", " disallowSystem=", z, z2), z3, " disallowRinger=", areAllPriorityOnlyRingerSoundsMuted));
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            final VolumeDialogController.State copy = state.copy();
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onStateChanged(copy);
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setStreamVolume(int r6, int r7, final java.lang.String r8) {
        /*
            r5 = this;
            r0 = 20
            if (r6 != r0) goto L6
            goto L9b
        L6:
            boolean r0 = isMediaStream(r6)
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L87
            int r7 = r7 / 10
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.android.systemui.plugins.VolumeDialogController$State r3 = r5.mState
            boolean r4 = r3.dualAudio
            if (r4 == 0) goto L33
            com.android.systemui.volume.util.BluetoothAdapterWrapper r0 = r5.mBluetoothAdapterManager
            boolean r3 = r3.isLeBroadcasting
            java.util.List r0 = r0.getConnectedDevices(r3)
            boolean r3 = r0.isEmpty()
            if (r3 != 0) goto L33
            if (r8 == 0) goto L33
            boolean r3 = r8.isEmpty()
            if (r3 != 0) goto L33
            r3 = 1
            goto L34
        L33:
            r3 = r1
        L34:
            android.util.Pair r4 = new android.util.Pair
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            r4.<init>(r3, r0)
            java.lang.Object r0 = r4.first
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            r3 = 3
            if (r0 == 0) goto L71
            java.lang.Object r6 = r4.second
            java.util.List r6 = (java.util.List) r6
            java.util.stream.Stream r6 = r6.stream()
            com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda5 r0 = new com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda5
            r0.<init>()
            java.util.stream.Stream r6 = r6.filter(r0)
            java.util.Optional r6 = r6.findFirst()
            java.lang.Object r6 = r6.orElse(r2)
            android.bluetooth.BluetoothDevice r6 = (android.bluetooth.BluetoothDevice) r6
            if (r6 == 0) goto L6b
            android.media.AudioManager r5 = r5.mAudio
            r5.semSetFineVolume(r6, r3, r7, r1)
            return
        L6b:
            android.media.AudioManager r5 = r5.mAudio
            r5.semSetFineVolume(r3, r7, r1)
            return
        L71:
            r8 = 21
            if (r6 != r8) goto L81
            android.media.AudioManager r6 = r5.mAudio
            int r6 = r6.semGetPinDevice()
            android.media.AudioManager r5 = r5.mAudio
            r5.setFineVolume(r3, r7, r1, r6)
            return
        L81:
            android.media.AudioManager r5 = r5.mAudio
            r5.semSetFineVolume(r3, r7, r1)
            return
        L87:
            r8 = 23
            if (r6 != r8) goto L9c
            boolean r8 = r5.mIsBudsTogetherEnabled
            if (r8 == 0) goto L9b
            com.android.systemui.volume.util.BluetoothAudioCastWrapper r8 = r5.mBluetoothAudioCastWrapper
            com.samsung.android.bluetooth.SemBluetoothAudioCast r8 = r8.service
            if (r8 == 0) goto L98
            r8.setAudioSharingDeviceVolume(r2, r7)
        L98:
            r5.onVolumeChangedW(r6, r1)
        L9b:
            return
        L9c:
            android.media.AudioManager r5 = r5.mAudio
            r5.setStreamVolume(r6, r7, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogControllerImpl.setStreamVolume(int, int, java.lang.String):void");
    }
}
