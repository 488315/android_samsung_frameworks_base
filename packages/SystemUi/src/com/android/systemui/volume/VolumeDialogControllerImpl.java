package com.android.systemui.volume;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
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
import android.media.MediaMetadata;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.media.VolumePolicy;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.VibrationEffect;
import android.provider.Settings;
import android.service.notification.Condition;
import android.service.notification.ZenModeConfig;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.volume.C0934D;
import com.android.settingslib.volume.MediaSessions;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.RingerModeLiveData;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.concurrency.ThreadFactoryImpl;
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
import com.samsung.android.media.SemSoundAssistantManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeDialogControllerImpl implements VolumeDialogController, Dumpable {
    public static final int DEFAULT_MAX_LEVEL;
    public static final int FLAG_SMART_VIEW_NONE;
    public static final ArrayMap STREAMS;
    public static boolean mIsVolumeStarEnabled;
    public final ActivityManager mActivityManager;
    public final AudioManager mAudio;
    public final IAudioService mAudioService;
    public final BluetoothAdapterWrapper mBluetoothAdapterManager;
    public final BluetoothAudioCastWrapper mBluetoothAudioCastWrapper;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final BroadcastReceiverManager mBroadcastReceiverManager;
    public final C3606C mCallbacks;
    public final CaptioningManager mCaptioningManager;
    public final Context mContext;
    public final C36041 mCurrentUserTrackerCallback;
    public final DesktopManagerWrapper mDesktopManagerWrapper;
    public boolean mDeviceInteractive;
    public final DeviceStateManagerWrapper mDeviceStateManagerWrapper;
    public final DisplayManagerWrapper mDisplayManagerWrapper;
    public final DumpManager mDumpManager;
    public final boolean mHasVibrator;
    public boolean mIsAudioMirroringEnabled;
    public boolean mIsBudsTogetherEnabled;
    public Boolean mIsDLNAEnabled;
    public boolean mIsMusicShareEnabled;
    public Boolean mIsSupportTvVolumeControl;
    public boolean mIsVibrating;
    public boolean mIsVolumeDialogShowing;
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
    public final SALoggingWrapper mSALoggingWrapper;
    public boolean mShowA11yStream;
    public boolean mShowSafetyWarning;
    public boolean mShowVolumeDialog;
    public int mSmartViewFlag;
    public final SoundAssistantChecker mSoundAssistantChecker;
    public final SoundAssistantManagerWrapper mSoundAssistantManagerWrapper;
    public final VolumeDialogController.State mState;
    public UserActivityListener mUserActivityListener;
    public final UserTracker mUserTracker;
    public final VibratorHelper mVibrator;
    public final C3609VC mVolumeController;
    public VolumePolicy mVolumePolicy;
    public final C36052 mWakefullnessLifecycleObserver;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final HandlerC3610W mWorker;
    public static final String TAG = Util.logTag(VolumeDialogControllerImpl.class);
    public static final AudioAttributes SONIFICIATION_VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaSessionsCallbacks implements MediaSessions.Callbacks {
        public final boolean mVolumeAdjustmentForRemoteGroupSessions;
        public final HashMap mRemoteStreams = new HashMap();
        public int mNextStream = 100;

        public MediaSessionsCallbacks(Context context) {
            this.mVolumeAdjustmentForRemoteGroupSessions = context.getResources().getBoolean(R.bool.config_sustainedPerformanceModeSupported);
        }

        public final void addStream(MediaSession.Token token, String str) {
            synchronized (this.mRemoteStreams) {
                if (!this.mRemoteStreams.containsKey(token)) {
                    this.mRemoteStreams.put(token, Integer.valueOf(this.mNextStream));
                    String str2 = VolumeDialogControllerImpl.TAG;
                    Log.d(str2, str + ": added stream " + this.mNextStream + " from token + " + token.toString());
                    this.mNextStream = this.mNextStream + 1;
                    if ("com.samsung.android.audiomirroring".equals(new MediaController(VolumeDialogControllerImpl.this.mContext, token).getPackageName())) {
                        VolumeDialogControllerImpl.this.mIsAudioMirroringEnabled = true;
                        Log.d(str2, str.concat(": - AudioMirroring is on"));
                    }
                }
            }
        }

        public final void onRemoteRemoved(MediaSession.Token token) {
            if (showForSession(token)) {
                synchronized (this.mRemoteStreams) {
                    if (!this.mRemoteStreams.containsKey(token)) {
                        Log.d(VolumeDialogControllerImpl.TAG, "onRemoteRemoved: stream doesn't exist, aborting remote removed for token:" + token.toString());
                        return;
                    }
                    int intValue = ((Integer) this.mRemoteStreams.get(token)).intValue();
                    if (VolumeDialogControllerImpl.this.mIsAudioMirroringEnabled && "com.samsung.android.audiomirroring".equals(new MediaController(VolumeDialogControllerImpl.this.mContext, token).getPackageName())) {
                        VolumeDialogControllerImpl.this.mIsAudioMirroringEnabled = false;
                        AbstractC0147x487e7be7.m26m("onRemoteRemoved ", intValue, " - AudioMirroring is off", VolumeDialogControllerImpl.TAG);
                    }
                    VolumeDialogControllerImpl.this.mState.states.remove(intValue);
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    if (volumeDialogControllerImpl.mState.activeStream == intValue) {
                        volumeDialogControllerImpl.updateActiveStreamW(-1);
                    }
                    VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                    volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
                }
            }
        }

        public final boolean showForSession(MediaSession.Token token) {
            if (this.mVolumeAdjustmentForRemoteGroupSessions) {
                return true;
            }
            VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
            String packageName = new MediaController(volumeDialogControllerImpl.mContext, token).getPackageName();
            for (RoutingSessionInfo routingSessionInfo : volumeDialogControllerImpl.mRouter2Manager.getRoutingSessions(packageName)) {
                if (!routingSessionInfo.isSystemSession() && routingSessionInfo.getVolumeHandling() != 0) {
                    return true;
                }
            }
            AbstractC0000x2c234b15.m3m("No routing session for ", packageName, VolumeDialogControllerImpl.TAG);
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                if (C3599D.BUG) {
                    RecyclerView$$ExternalSyntheticOutline0.m46m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("onReceive STREAM_DEVICES_CHANGED_ACTION stream=", intExtra, " devices=", intExtra2, " oldDevices="), intExtra3, VolumeDialogControllerImpl.TAG);
                }
                VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                String str = VolumeDialogControllerImpl.TAG;
                boolean checkRoutedToBluetoothW = volumeDialogControllerImpl.checkRoutedToBluetoothW(intExtra);
                if (intExtra == 3) {
                    checkRoutedToBluetoothW = checkRoutedToBluetoothW | VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(21) | VolumeDialogControllerImpl.this.checkRoutedToBluetoothW(22);
                }
                z = checkRoutedToBluetoothW | VolumeDialogControllerImpl.this.onVolumeChangedW(intExtra, 0);
            } else if (action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                int intExtra4 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                boolean booleanExtra = intent.getBooleanExtra("android.media.EXTRA_STREAM_VOLUME_MUTED", false);
                if (C3599D.BUG) {
                    AbstractC0731x5bb8a836.m72m("onReceive STREAM_MUTE_CHANGED_ACTION stream=", intExtra4, " muted=", booleanExtra, VolumeDialogControllerImpl.TAG);
                }
                VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
                String str2 = VolumeDialogControllerImpl.TAG;
                z = volumeDialogControllerImpl2.updateStreamMuteW(intExtra4, booleanExtra);
                VolumeDialogControllerImpl.m2729$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, intExtra4);
                if (intExtra4 == 3) {
                    VolumeDialogControllerImpl.this.updateStreamMuteW(21, booleanExtra);
                    VolumeDialogControllerImpl.m2729$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, 21);
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("onReceive STREAM_MUTE_CHANGED_ACTION : stream=", intExtra4, ", muted=", booleanExtra, ", mState.dualAudio="), VolumeDialogControllerImpl.this.mState.dualAudio, VolumeDialogControllerImpl.TAG);
                    VolumeDialogControllerImpl.this.updateStreamMuteW(22, booleanExtra);
                    VolumeDialogControllerImpl.m2729$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl.this, 22);
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
                if (C3599D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_EFFECTS_SUPPRESSOR_CHANGED");
                }
                VolumeDialogControllerImpl volumeDialogControllerImpl4 = VolumeDialogControllerImpl.this;
                z = volumeDialogControllerImpl4.updateEffectsSuppressorW(volumeDialogControllerImpl4.mNoMan.getEffectsSuppressor());
            } else if (action.equals("android.intent.action.CONFIGURATION_CHANGED")) {
                if (C3599D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_CONFIGURATION_CHANGED");
                }
                VolumeDialogControllerImpl.this.mCallbacks.onConfigurationChanged();
            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                if (C3599D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_SCREEN_OFF");
                }
                VolumeDialogControllerImpl.this.mCallbacks.onScreenOff();
            } else if (action.equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                if (C3599D.BUG) {
                    Log.d(VolumeDialogControllerImpl.TAG, "onReceive ACTION_CLOSE_SYSTEM_DIALOGS");
                }
                VolumeDialogControllerImpl.this.mCallbacks.onDismissRequested(2);
            }
            if (z) {
                VolumeDialogControllerImpl volumeDialogControllerImpl5 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl5.mCallbacks.onStateChanged(volumeDialogControllerImpl5.mState);
            }
        }

        private Receiver() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RingerModeObservers {
        public final RingerModeLiveData mRingerMode;
        public final RingerModeLiveData mRingerModeInternal;
        public final C36071 mRingerModeObserver = new C36071();
        public final C36082 mRingerModeInternalObserver = new C36082();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$RingerModeObservers$1 */
        public final class C36071 implements Observer {
            public C36071() {
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VolumeDialogControllerImpl.this.mWorker.post(new RunnableC3603x7eae9893(this, (Integer) obj, 0));
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$RingerModeObservers$2 */
        public final class C36082 implements Observer {
            public C36082() {
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                VolumeDialogControllerImpl.this.mWorker.post(new RunnableC3603x7eae9893(this, (Integer) obj, 1));
            }
        }

        public RingerModeObservers(RingerModeLiveData ringerModeLiveData, RingerModeLiveData ringerModeLiveData2) {
            this.mRingerMode = ringerModeLiveData;
            this.mRingerModeInternal = ringerModeLiveData2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingObserver extends ContentObserver {
        public final Uri ZEN_MODE_CONFIG_URI;
        public final Uri ZEN_MODE_URI;

        public SettingObserver(Handler handler) {
            super(handler);
            this.ZEN_MODE_URI = Settings.Global.getUriFor("zen_mode");
            this.ZEN_MODE_CONFIG_URI = Settings.Global.getUriFor("zen_mode_config_etag");
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
            if (z2) {
                VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                volumeDialogControllerImpl3.mCallbacks.onStateChanged(volumeDialogControllerImpl3.mState);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface UserActivityListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$VC */
    public final class C3609VC extends IVolumeController.Stub {
        public final String TAG;

        public /* synthetic */ C3609VC(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
            this();
        }

        public final void dismiss() {
            if (C3599D.BUG) {
                Log.d(this.TAG, "dismiss requested");
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(2, 2, 0).sendToTarget();
            VolumeDialogControllerImpl.this.mWorker.sendEmptyMessage(2);
        }

        public final void displayCsdWarning(int i, int i2) {
            if (C3599D.BUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("displayCsdWarning durMs=", i2, this.TAG);
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(17, i, i2).sendToTarget();
        }

        public final void displaySafeVolumeWarning(int i) {
            if (C3599D.BUG) {
                String str = this.TAG;
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("displaySafeVolumeWarning "), com.android.settingslib.volume.Util.bitFieldToString(i, com.android.settingslib.volume.Util.AUDIO_MANAGER_FLAG_NAMES, com.android.settingslib.volume.Util.AUDIO_MANAGER_FLAGS), str);
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(14, i, 0).sendToTarget();
        }

        public final void displayVolumeLimiterToast() {
            if (C3599D.BUG) {
                Log.d(this.TAG, "displayVolumeLimiterWarning");
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(18).sendToTarget();
        }

        public final void masterMuteChanged(int i) {
            if (C3599D.BUG) {
                Log.d(this.TAG, "masterMuteChanged");
            }
        }

        public final void setA11yMode(int i) {
            if (C3599D.BUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("setA11yMode to ", i, this.TAG);
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
                NestedScrollView$$ExternalSyntheticOutline0.m34m("Invalid accessibility mode ", i, this.TAG);
            }
            VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
            volumeDialogControllerImpl.mWorker.obtainMessage(15, Boolean.valueOf(volumeDialogControllerImpl.mShowA11yStream)).sendToTarget();
        }

        public final void setLayoutDirection(int i) {
            if (C3599D.BUG) {
                Log.d(this.TAG, "setLayoutDirection");
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(8, i, 0).sendToTarget();
        }

        public final void volumeChanged(int i, int i2) {
            if (C3599D.BUG) {
                String str = this.TAG;
                StringBuilder sb = new StringBuilder("volumeChanged ");
                sb.append(AudioSystem.streamToString(i));
                sb.append(" ");
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, com.android.settingslib.volume.Util.bitFieldToString(i2, Util.SAMSUNG_AUDIO_MANAGER_FLAG_NAMES, Util.SAMSUNG_AUDIO_MANAGER_FLAGS), str);
            }
            VolumeDialogControllerImpl.this.mWorker.obtainMessage(1, i, i2).sendToTarget();
        }

        private C3609VC() {
            this.TAG = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), VolumeDialogControllerImpl.TAG, ".VC");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$W */
    public final class HandlerC3610W extends Handler {
        public HandlerC3610W(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            MediaSession.Token token = null;
            switch (message.what) {
                case 1:
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    int i = message.arg1;
                    int i2 = message.arg2;
                    String str = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl.getClass();
                    if ((i == 3 && (4194304 & i2) != 0) && volumeDialogControllerImpl.mIsVolumeDialogShowing) {
                        volumeDialogControllerImpl.mSmartViewFlag = i2;
                        return;
                    } else {
                        volumeDialogControllerImpl.onVolumeChangedW(i, i2);
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
                    ArrayMap arrayMap = VolumeDialogControllerImpl.STREAMS;
                    Iterator it = arrayMap.keySet().iterator();
                    while (it.hasNext()) {
                        int intValue = ((Integer) it.next()).intValue();
                        volumeDialogControllerImpl2.updateStreamLevelW(intValue, volumeDialogControllerImpl2.getLastAudibleStreamVolume(intValue));
                        volumeDialogControllerImpl2.streamStateW(intValue).levelMin = volumeDialogControllerImpl2.getAudioManagerStreamMinVolume(intValue);
                        volumeDialogControllerImpl2.streamStateW(intValue).levelMax = volumeDialogControllerImpl2.getAudioManagerStreamMaxVolume(intValue);
                        AudioManager audioManager = volumeDialogControllerImpl2.mAudio;
                        volumeDialogControllerImpl2.updateStreamMuteW(intValue, (intValue == 20 || intValue == 23) ? false : (intValue == 21 || intValue == 22) ? audioManager.isStreamMute(3) : audioManager.isStreamMute(intValue));
                        VolumeDialogController.StreamState streamStateW = volumeDialogControllerImpl2.streamStateW(intValue);
                        streamStateW.muteSupported = audioManager.isStreamAffectedByMute(intValue);
                        streamStateW.name = ((Integer) arrayMap.get(Integer.valueOf(intValue))).intValue();
                        volumeDialogControllerImpl2.checkRoutedToBluetoothW(intValue);
                        streamStateW.nameRes = volumeDialogControllerImpl2.mContext.getResources().getResourceName(streamStateW.name);
                    }
                    int intValue2 = volumeDialogControllerImpl2.mRingerModeObservers.mRingerMode.getValue().intValue();
                    VolumeDialogController.State state = volumeDialogControllerImpl2.mState;
                    if (intValue2 != state.ringerModeExternal) {
                        state.ringerModeExternal = intValue2;
                        Events.writeEvent(12, Integer.valueOf(intValue2));
                    }
                    volumeDialogControllerImpl2.updateZenModeW();
                    volumeDialogControllerImpl2.updateZenConfig();
                    volumeDialogControllerImpl2.updateEffectsSuppressorW(volumeDialogControllerImpl2.mNoMan.getEffectsSuppressor());
                    volumeDialogControllerImpl2.mCallbacks.onStateChanged(volumeDialogControllerImpl2.mState);
                    return;
                case 4:
                    VolumeDialogControllerImpl volumeDialogControllerImpl3 = VolumeDialogControllerImpl.this;
                    int i3 = message.arg1;
                    z = message.arg2 != 0;
                    AudioManager audioManager2 = volumeDialogControllerImpl3.mAudio;
                    if (z) {
                        audioManager2.setRingerMode(i3);
                        return;
                    } else {
                        audioManager2.setRingerModeInternal(i3);
                        return;
                    }
                case 5:
                    VolumeDialogControllerImpl volumeDialogControllerImpl4 = VolumeDialogControllerImpl.this;
                    int i4 = message.arg1;
                    String str3 = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl4.getClass();
                    boolean z2 = C3599D.BUG;
                    String str4 = VolumeDialogControllerImpl.TAG;
                    if (z2) {
                        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onSetZenModeW ", i4, str4);
                    }
                    volumeDialogControllerImpl4.mNoMan.setZenMode(i4, null, str4);
                    return;
                case 6:
                    VolumeDialogControllerImpl volumeDialogControllerImpl5 = VolumeDialogControllerImpl.this;
                    Condition condition = (Condition) message.obj;
                    volumeDialogControllerImpl5.mNoMan.setZenMode(volumeDialogControllerImpl5.mState.zenMode, condition != null ? condition.id : null, VolumeDialogControllerImpl.TAG);
                    return;
                case 7:
                    VolumeDialogControllerImpl volumeDialogControllerImpl6 = VolumeDialogControllerImpl.this;
                    int i5 = message.arg1;
                    z = message.arg2 != 0;
                    String str5 = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl6.getClass();
                    volumeDialogControllerImpl6.mAudio.adjustStreamVolume(i5, z ? -100 : 100, 0);
                    return;
                case 8:
                    VolumeDialogControllerImpl.this.mCallbacks.onLayoutDirectionChanged(message.arg1);
                    return;
                case 9:
                    VolumeDialogControllerImpl.this.mCallbacks.onConfigurationChanged();
                    return;
                case 10:
                    VolumeDialogControllerImpl volumeDialogControllerImpl7 = VolumeDialogControllerImpl.this;
                    int i6 = message.arg1;
                    int i7 = message.arg2;
                    String str6 = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl7.getClass();
                    if (C3599D.BUG) {
                        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onSetStreamVolume ", i6, " level=", i7, VolumeDialogControllerImpl.TAG);
                    }
                    if (i6 < 100) {
                        volumeDialogControllerImpl7.setStreamVolume(i6, i7, null);
                        return;
                    }
                    MediaSessionsCallbacks mediaSessionsCallbacks = volumeDialogControllerImpl7.mMediaSessionsCallbacksW;
                    synchronized (mediaSessionsCallbacks.mRemoteStreams) {
                        Iterator it2 = mediaSessionsCallbacks.mRemoteStreams.entrySet().iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                Map.Entry entry = (Map.Entry) it2.next();
                                if (((Integer) entry.getValue()).equals(Integer.valueOf(i6))) {
                                    token = (MediaSession.Token) entry.getKey();
                                }
                            }
                        }
                    }
                    if (token == null) {
                        IconCompat$$ExternalSyntheticOutline0.m30m("setStreamVolume: No token found for stream: ", i6, VolumeDialogControllerImpl.TAG);
                        return;
                    }
                    if (mediaSessionsCallbacks.showForSession(token)) {
                        MediaSessions.MediaControllerRecord mediaControllerRecord = (MediaSessions.MediaControllerRecord) ((HashMap) VolumeDialogControllerImpl.this.mMediaSessions.mRecords).get(token);
                        String str7 = MediaSessions.TAG;
                        if (mediaControllerRecord == null) {
                            Log.w(str7, "setVolume: No record found for token " + token);
                            return;
                        } else {
                            if (C0934D.BUG) {
                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("Setting level to ", i7, str7);
                            }
                            mediaControllerRecord.controller.setVolumeTo(i7, 0);
                            return;
                        }
                    }
                    return;
                case 11:
                    VolumeDialogControllerImpl volumeDialogControllerImpl8 = VolumeDialogControllerImpl.this;
                    int i8 = message.arg1;
                    String str8 = VolumeDialogControllerImpl.TAG;
                    if (volumeDialogControllerImpl8.updateActiveStreamW(i8)) {
                        volumeDialogControllerImpl8.mCallbacks.onStateChanged(volumeDialogControllerImpl8.mState);
                        return;
                    }
                    return;
                case 12:
                    VolumeDialogControllerImpl volumeDialogControllerImpl9 = VolumeDialogControllerImpl.this;
                    z = message.arg1 != 0;
                    volumeDialogControllerImpl9.mAudio.notifyVolumeControllerVisible(volumeDialogControllerImpl9.mVolumeController, z);
                    if (z || !volumeDialogControllerImpl9.updateActiveStreamW(-1)) {
                        return;
                    }
                    volumeDialogControllerImpl9.mCallbacks.onStateChanged(volumeDialogControllerImpl9.mState);
                    return;
                case 13:
                    VolumeDialogControllerImpl volumeDialogControllerImpl10 = VolumeDialogControllerImpl.this;
                    String str9 = VolumeDialogControllerImpl.TAG;
                    synchronized (volumeDialogControllerImpl10) {
                        UserActivityListener userActivityListener = volumeDialogControllerImpl10.mUserActivityListener;
                        if (userActivityListener != null) {
                            ((VolumeDialogComponent) userActivityListener).mKeyguardViewMediator.userActivity();
                        }
                    }
                    return;
                case 14:
                    VolumeDialogControllerImpl volumeDialogControllerImpl11 = VolumeDialogControllerImpl.this;
                    int i9 = message.arg1;
                    if (volumeDialogControllerImpl11.mShowSafetyWarning) {
                        volumeDialogControllerImpl11.mCallbacks.onShowSafetyWarning(i9);
                        return;
                    }
                    return;
                case 15:
                    VolumeDialogControllerImpl.this.mCallbacks.onAccessibilityModeChanged((Boolean) message.obj);
                    return;
                case 16:
                    VolumeDialogControllerImpl volumeDialogControllerImpl12 = VolumeDialogControllerImpl.this;
                    volumeDialogControllerImpl12.mCallbacks.onCaptionComponentStateChanged(Boolean.valueOf(volumeDialogControllerImpl12.mCaptioningManager.isSystemAudioCaptioningUiEnabled()), Boolean.valueOf(((Boolean) message.obj).booleanValue()));
                    return;
                case 17:
                    VolumeDialogControllerImpl.this.mCallbacks.onShowCsdWarning(message.arg1, message.arg2);
                    return;
                case 18:
                    VolumeDialogControllerImpl.this.mCallbacks.onShowVolumeLimiterToast();
                    return;
                case 19:
                    VolumeDialogControllerImpl volumeDialogControllerImpl13 = VolumeDialogControllerImpl.this;
                    int i10 = message.arg1;
                    int i11 = message.arg2;
                    String str10 = (String) message.obj;
                    String str11 = VolumeDialogControllerImpl.TAG;
                    volumeDialogControllerImpl13.getClass();
                    if (C3599D.BUG) {
                        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onSetStreamVolumeDualAudioW ", i10, " level=", i11, " btDeviceAddress=");
                        m45m.append(str10);
                        Log.d(VolumeDialogControllerImpl.TAG, m45m.toString());
                    }
                    volumeDialogControllerImpl13.setStreamVolume(i10, i11, str10);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: -$$Nest$mupdateRemoteFixedVolumeSession, reason: not valid java name */
    public static void m2728$$Nest$mupdateRemoteFixedVolumeSession(VolumeDialogControllerImpl volumeDialogControllerImpl, int i, MediaController.PlaybackInfo playbackInfo) {
        if (playbackInfo == null) {
            volumeDialogControllerImpl.streamStateW(i).remoteFixedVolume = false;
            return;
        }
        volumeDialogControllerImpl.getClass();
        boolean z = playbackInfo.getVolumeControl() == 0;
        boolean z2 = playbackInfo.getPlaybackType() == 2 && playbackInfo.getVolumeControl() == 1;
        volumeDialogControllerImpl.streamStateW(i).remoteFixedVolume = z || z2;
        StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("updateRemoteVolumeRelativeOnly : stream=", i, ", isFixedVolume=", z, ", isRemoteRelativeVolume=");
        m76m.append(z2);
        Log.d(TAG, m76m.toString());
    }

    /* renamed from: -$$Nest$mupdateStreamVolume, reason: not valid java name */
    public static void m2729$$Nest$mupdateStreamVolume(VolumeDialogControllerImpl volumeDialogControllerImpl, int i) {
        volumeDialogControllerImpl.updateStreamLevelW(i, volumeDialogControllerImpl.getLastAudibleStreamVolume(i));
    }

    static {
        ArrayMap arrayMap = new ArrayMap();
        STREAMS = arrayMap;
        Integer valueOf = Integer.valueOf(com.android.systemui.R.string.volume_icon_description_incall);
        arrayMap.put(0, valueOf);
        arrayMap.put(1, Integer.valueOf(com.android.systemui.R.string.volumepanel_system));
        arrayMap.put(2, Integer.valueOf(com.android.systemui.R.string.volumepanel_ringtone));
        Integer valueOf2 = Integer.valueOf(com.android.systemui.R.string.volumepanel_media);
        arrayMap.put(3, valueOf2);
        arrayMap.put(4, Integer.valueOf(com.android.systemui.R.string.volume_alarm));
        arrayMap.put(5, Integer.valueOf(com.android.systemui.R.string.volumepanel_notification));
        arrayMap.put(6, valueOf);
        arrayMap.put(7, Integer.valueOf(com.android.systemui.R.string.stream_system_enforced));
        arrayMap.put(8, Integer.valueOf(com.android.systemui.R.string.stream_dtmf));
        arrayMap.put(9, Integer.valueOf(com.android.systemui.R.string.stream_tts));
        arrayMap.put(10, Integer.valueOf(com.android.systemui.R.string.stream_accessibility));
        arrayMap.put(20, valueOf2);
        arrayMap.put(21, valueOf2);
        arrayMap.put(11, Integer.valueOf(com.android.systemui.R.string.volumepanel_bixby_voice));
        arrayMap.put(22, valueOf2);
        arrayMap.put(23, Integer.valueOf(com.android.systemui.R.string.volumepanel_music_share));
        DEFAULT_MAX_LEVEL = 15;
        FLAG_SMART_VIEW_NONE = -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.settings.UserTracker$Callback, com.android.systemui.volume.VolumeDialogControllerImpl$1] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.android.systemui.volume.VolumeDialogControllerImpl$2, java.lang.Object] */
    public VolumeDialogControllerImpl(Context context, BroadcastDispatcher broadcastDispatcher, RingerModeTracker ringerModeTracker, ThreadFactory threadFactory, AudioManager audioManager, NotificationManager notificationManager, VibratorHelper vibratorHelper, IAudioService iAudioService, AccessibilityManager accessibilityManager, PackageManager packageManager, WakefulnessLifecycle wakefulnessLifecycle, CaptioningManager captioningManager, KeyguardManager keyguardManager, ActivityManager activityManager, UserTracker userTracker, DumpManager dumpManager, SALoggingWrapper sALoggingWrapper, BroadcastReceiverManager broadcastReceiverManager, DisplayManagerWrapper displayManagerWrapper, DesktopManagerWrapper desktopManagerWrapper, KnoxStateMonitor knoxStateMonitor, BluetoothAdapterWrapper bluetoothAdapterWrapper, SoundAssistantManagerWrapper soundAssistantManagerWrapper, DeviceStateManagerWrapper deviceStateManagerWrapper, LocalBluetoothManager localBluetoothManager, VolumeDependency volumeDependency) {
        int i = 0;
        Receiver receiver = new Receiver(this, i);
        this.mCallbacks = new C3606C();
        this.mState = new VolumeDialogController.State();
        this.mDeviceInteractive = true;
        C3609VC c3609vc = new C3609VC(this, i);
        this.mVolumeController = c3609vc;
        Boolean bool = Boolean.FALSE;
        this.mIsSupportTvVolumeControl = bool;
        this.mIsDLNAEnabled = bool;
        ?? r6 = new UserTracker.Callback() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i2, Context context2) {
                int i3 = 0;
                while (true) {
                    VolumeDialogControllerImpl volumeDialogControllerImpl = VolumeDialogControllerImpl.this;
                    if (i3 >= volumeDialogControllerImpl.mState.states.size()) {
                        return;
                    }
                    volumeDialogControllerImpl.onVolumeChangedW(volumeDialogControllerImpl.mState.states.keyAt(i3), 0);
                    i3++;
                }
            }
        };
        this.mCurrentUserTrackerCallback = r6;
        ?? r7 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.2
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                VolumeDialogControllerImpl.this.mDeviceInteractive = false;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onStartedWakingUp() {
                VolumeDialogControllerImpl.this.mDeviceInteractive = true;
            }
        };
        this.mWakefullnessLifecycleObserver = r7;
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mPackageManager = packageManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        Events.writeEvent(5, new Object[0]);
        ((ThreadFactoryImpl) threadFactory).getClass();
        HandlerThread handlerThread = new HandlerThread("VolumeDialogControllerImpl");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        HandlerC3610W handlerC3610W = new HandlerC3610W(looper);
        this.mWorker = handlerC3610W;
        this.mRouter2Manager = MediaRouter2Manager.getInstance(applicationContext);
        MediaSessionsCallbacks mediaSessionsCallbacks = new MediaSessionsCallbacks(applicationContext);
        this.mMediaSessionsCallbacksW = mediaSessionsCallbacks;
        this.mMediaSessions = new MediaSessions(applicationContext, looper, mediaSessionsCallbacks);
        this.mAudio = audioManager;
        this.mNoMan = notificationManager;
        SettingObserver settingObserver = new SettingObserver(handlerC3610W);
        RingerModeTrackerImpl ringerModeTrackerImpl = (RingerModeTrackerImpl) ringerModeTracker;
        RingerModeObservers ringerModeObservers = new RingerModeObservers(ringerModeTrackerImpl.ringerMode, ringerModeTrackerImpl.ringerModeInternal);
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
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
        intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
        intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
        intentFilter.addAction("android.os.action.ACTION_EFFECTS_SUPPRESSOR_CHANGED");
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        VolumeDialogControllerImpl volumeDialogControllerImpl2 = VolumeDialogControllerImpl.this;
        volumeDialogControllerImpl2.mBroadcastDispatcher.registerReceiverWithHandler(receiver, intentFilter, volumeDialogControllerImpl2.mWorker);
        this.mVibrator = vibratorHelper;
        this.mHasVibrator = vibratorHelper.hasVibrator();
        this.mAudioService = iAudioService;
        this.mCaptioningManager = captioningManager;
        this.mKeyguardManager = keyguardManager;
        this.mActivityManager = activityManager;
        this.mUserTracker = userTracker;
        this.mDumpManager = dumpManager;
        c3609vc.setA11yMode(accessibilityManager.isAccessibilityVolumeStreamActive() ? 1 : 0);
        wakefulnessLifecycle.addObserver(r7);
        this.mSALoggingWrapper = sALoggingWrapper;
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
        ((UserTrackerImpl) userTracker).addCallback(r6, new HandlerExecutor(new Handler(Looper.getMainLooper())));
        this.mSoundAssistantChecker = (SoundAssistantChecker) volumeDependency.get(SoundAssistantChecker.class);
    }

    public static boolean isMediaStream(int i) {
        return i == 3 || i == 21 || i == 22;
    }

    public static void updateStreamRoutedToHomeMiniW(BluetoothDevice bluetoothDevice, VolumeDialogController.StreamState streamState) {
        if (bluetoothDevice != null) {
            BluetoothIconUtil bluetoothIconUtil = BluetoothIconUtil.INSTANCE;
            BluetoothIconUtil.SamsungStandard.Companion.getClass();
            ArrayList arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(Short.valueOf(BluetoothIconUtil.SamsungStandard.Companion.AI_SPEAKER_GALAXY_HOME_MINI));
            BluetoothIconUtil.INSTANCE.getClass();
            if (BluetoothIconUtil.isSameDeviceIconType(bluetoothDevice, arrayListOf)) {
                streamState.routedToHomeMini = true;
                return;
            }
        }
        streamState.routedToHomeMini = false;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void addCallback(VolumeDialogController.Callbacks callbacks, Handler handler) {
        C3606C c3606c = this.mCallbacks;
        c3606c.getClass();
        if (callbacks == null || handler == null) {
            throw new IllegalArgumentException();
        }
        ((ConcurrentHashMap) c3606c.mCallbackMap).put(callbacks, handler);
        callbacks.onAccessibilityModeChanged(Boolean.valueOf(this.mShowA11yStream));
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final boolean areCaptionsEnabled() {
        return this.mCaptioningManager.isSystemAudioCaptioningEnabled();
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002e, code lost:
    
        if (r0 == null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0036, code lost:
    
        if (r0.size() > 0) goto L19;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkRoutedToBluetoothW(int i) {
        boolean z;
        LocalBluetoothProfileManager localBluetoothProfileManager;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
        List list;
        AudioManager audioManager = this.mAudio;
        if (i == 3) {
            int devicesForStream = audioManager.getDevicesForStream(3);
            boolean z2 = (67108876 & devicesForStream) != 0;
            if ((671089568 & devicesForStream) != 0) {
                if (devicesForStream == 536870914) {
                    BluetoothLeAudio bluetoothLeAudio = this.mBluetoothAdapterManager.leAudio;
                    if (bluetoothLeAudio != null) {
                        BluetoothCommonUtil.INSTANCE.getClass();
                        list = BluetoothCommonUtil.connectedDevices(bluetoothLeAudio);
                    }
                    list = EmptyList.INSTANCE;
                }
                z = true;
                LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
                boolean isEnabled = (localBluetoothManager != null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (localBluetoothLeBroadcast = localBluetoothProfileManager.mLeAudioBroadcast) == null) ? false : localBluetoothLeBroadcast.isEnabled();
                VolumeDialogController.State state = this.mState;
                state.isLeBroadcasting = isEnabled;
                state.broadcastMode = isEnabled ? z ? 1 : 2 : 0;
                return updateStreamRoutedToHeadsetW(i, z2) | (!isMusicShareEnabled() ? updateStreamRoutedToBluetoothW(i, true) : updateStreamRoutedToBluetoothW(i, z));
            }
            z = false;
            LocalBluetoothManager localBluetoothManager2 = this.mLocalBluetoothManager;
            if (localBluetoothManager2 != null) {
            }
            VolumeDialogController.State state2 = this.mState;
            state2.isLeBroadcasting = isEnabled;
            state2.broadcastMode = isEnabled ? z ? 1 : 2 : 0;
            return updateStreamRoutedToHeadsetW(i, z2) | (!isMusicShareEnabled() ? updateStreamRoutedToBluetoothW(i, true) : updateStreamRoutedToBluetoothW(i, z));
        }
        if (i != 21) {
            if (i == 23 || i == 22) {
                return false | updateStreamRoutedToBluetoothW(i, true);
            }
            if (i == 0) {
                return false | updateStreamRoutedToBluetoothW(i, (audioManager.getDevicesForStream(0) & 671089568) != 0);
            }
            return false;
        }
        int semGetPinDevice = audioManager.semGetPinDevice();
        boolean z3 = (67108876 & semGetPinDevice) != 0;
        boolean z4 = (671089568 & semGetPinDevice) != 0;
        boolean z5 = (semGetPinDevice & 32768) != 0;
        boolean updateStreamRoutedToBluetoothW = updateStreamRoutedToBluetoothW(i, z4) | updateStreamRoutedToHeadsetW(i, z3);
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.appMirroring == z5) {
            r1 = false;
        } else {
            streamStateW.appMirroring = z5;
            if (C3599D.BUG) {
                Log.d(TAG, "updateStreamRoutedToAppMirroring stream=" + i + " appMirroring=" + z5);
            }
        }
        return updateStreamRoutedToBluetoothW | r1;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("VolumeDialogControllerImpl state:");
        printWriter.print("  mVolumePolicy: ");
        printWriter.println(this.mVolumePolicy);
        printWriter.print("  mState: ");
        printWriter.println(this.mState.toString(4));
        printWriter.print("  mHasVibrator: ");
        printWriter.println(this.mHasVibrator);
        synchronized (this.mMediaSessionsCallbacksW.mRemoteStreams) {
            printWriter.print("  mRemoteStreams: ");
            printWriter.println(this.mMediaSessionsCallbacksW.mRemoteStreams.values());
        }
        printWriter.print("  mShowA11yStream: ");
        printWriter.println(this.mShowA11yStream);
        printWriter.println();
        MediaSessions mediaSessions = this.mMediaSessions;
        mediaSessions.getClass();
        printWriter.println("MediaSessions state:");
        printWriter.print("  mInit: ");
        printWriter.println(mediaSessions.mInit);
        printWriter.print("  mRecords.size: ");
        HashMap hashMap = (HashMap) mediaSessions.mRecords;
        printWriter.println(hashMap.size());
        Iterator it = hashMap.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            MediaController mediaController = ((MediaSessions.MediaControllerRecord) it.next()).controller;
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("  Controller ", i, ": ");
            m1m.append(mediaController.getPackageName());
            printWriter.println(m1m.toString());
            Bundle extras = mediaController.getExtras();
            long flags = mediaController.getFlags();
            MediaMetadata metadata = mediaController.getMetadata();
            MediaController.PlaybackInfo playbackInfo = mediaController.getPlaybackInfo();
            PlaybackState playbackState = mediaController.getPlaybackState();
            List<MediaSession.QueueItem> queue = mediaController.getQueue();
            CharSequence queueTitle = mediaController.getQueueTitle();
            int ratingType = mediaController.getRatingType();
            PendingIntent sessionActivity = mediaController.getSessionActivity();
            printWriter.println("    PlaybackState: " + com.android.settingslib.volume.Util.playbackStateToString(playbackState));
            printWriter.println("    PlaybackInfo: " + com.android.settingslib.volume.Util.playbackInfoToString(playbackInfo));
            if (metadata != null) {
                printWriter.println("  MediaMetadata.desc=" + metadata.getDescription());
            }
            printWriter.println("    RatingType: " + ratingType);
            printWriter.println("    Flags: " + flags);
            if (extras != null) {
                printWriter.println("    Extras:");
                for (String str : extras.keySet()) {
                    StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("      ", str, "=");
                    m4m.append(extras.getString(str));
                    printWriter.println(m4m.toString());
                }
            }
            if (queueTitle != null) {
                printWriter.println("    QueueTitle: " + ((Object) queueTitle));
            }
            if (queue != null && !queue.isEmpty()) {
                printWriter.println("    Queue:");
                Iterator<MediaSession.QueueItem> it2 = queue.iterator();
                while (it2.hasNext()) {
                    printWriter.println("      " + it2.next());
                }
            }
            if (playbackInfo != null) {
                printWriter.println("    sessionActivity: " + sessionActivity);
            }
        }
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final AudioManager getAudioManager() {
        return this.mAudio;
    }

    public final int getAudioManagerStreamMaxVolume(int i) {
        if (i != 20) {
            AudioManager audioManager = this.mAudio;
            if (i == 21 || i == 22) {
                return audioManager.getStreamMaxVolume(3);
            }
            if (i != 23) {
                return audioManager.getStreamMaxVolume(i);
            }
        } else if (isSmartViewEnabled()) {
            DisplayManagerWrapper displayManagerWrapper = this.mDisplayManagerWrapper;
            if (displayManagerWrapper.maxSmartViewVol == -1) {
                SystemServiceExtension.INSTANCE.getClass();
                displayManagerWrapper.maxSmartViewVol = ((Integer) SystemServiceExtension.getDisplayManager(displayManagerWrapper.context).semGetWifiDisplayConfiguration("mivo")).intValue();
            }
            return displayManagerWrapper.maxSmartViewVol;
        }
        return DEFAULT_MAX_LEVEL;
    }

    public final int getAudioManagerStreamMinVolume(int i) {
        if (i != 20) {
            AudioManager audioManager = this.mAudio;
            if (i == 21 || i == 22) {
                return audioManager.getStreamMinVolumeInt(3);
            }
            if (i != 23) {
                return audioManager.getStreamMinVolumeInt(i);
            }
        } else if (isSmartViewEnabled()) {
            DisplayManagerWrapper displayManagerWrapper = this.mDisplayManagerWrapper;
            if (displayManagerWrapper.minSmartViewVol == -1) {
                SystemServiceExtension.INSTANCE.getClass();
                displayManagerWrapper.minSmartViewVol = ((Integer) SystemServiceExtension.getDisplayManager(displayManagerWrapper.context).semGetWifiDisplayConfiguration("mavo")).intValue();
            }
            return displayManagerWrapper.minSmartViewVol;
        }
        return 0;
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void getCaptionsComponentState(boolean z) {
        this.mWorker.obtainMessage(16, Boolean.valueOf(z)).sendToTarget();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getLastAudibleStreamVolume(int i) {
        SemBluetoothAudioCast semBluetoothAudioCast;
        boolean z;
        Pair pair;
        int fineVolume;
        boolean isMediaStream = isMediaStream(i);
        AudioManager audioManager = this.mAudio;
        if (!isMediaStream) {
            if (i == 20) {
                if (isSmartViewEnabled()) {
                    return this.mDisplayManagerWrapper.displayCurrentVolume;
                }
                return 0;
            }
            if (i != 23) {
                return audioManager.getStreamVolume(i);
            }
            if (!this.mIsBudsTogetherEnabled || (semBluetoothAudioCast = this.mBluetoothAudioCastWrapper.service) == null) {
                return 0;
            }
            return semBluetoothAudioCast.getAudioSharingDeviceVolume((SemBluetoothCastDevice) null);
        }
        List arrayList = new ArrayList();
        if (this.mState.dualAudio) {
            arrayList = this.mBluetoothAdapterManager.getConnectedDevices();
            if (arrayList.size() == 2) {
                z = true;
                pair = new Pair(Boolean.valueOf(z), arrayList);
                if (((Boolean) pair.first).booleanValue()) {
                    fineVolume = i == 21 ? audioManager.getFineVolume(3, audioManager.semGetPinDevice()) : audioManager.semGetFineVolume(3);
                } else {
                    List list = (List) pair.second;
                    if (isMultiSoundBT()) {
                        if (i == 3) {
                            fineVolume = audioManager.semGetFineVolume(3);
                        } else {
                            fineVolume = audioManager.semGetFineVolume((BluetoothDevice) (i == 21 ? list.get(0) : list.get(1)), 3);
                        }
                    } else if (i == 21) {
                        fineVolume = audioManager.getFineVolume(3, audioManager.semGetPinDevice());
                    } else {
                        fineVolume = audioManager.semGetFineVolume((BluetoothDevice) (i == 3 ? list.get(0) : list.get(1)), 3);
                    }
                }
                return fineVolume * 10;
            }
        }
        z = false;
        pair = new Pair(Boolean.valueOf(z), arrayList);
        if (((Boolean) pair.first).booleanValue()) {
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
        LocalBluetoothProfileManager localBluetoothProfileManager;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast;
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (localBluetoothLeBroadcast = localBluetoothProfileManager.mLeAudioBroadcast) == null) {
            return false;
        }
        return localBluetoothLeBroadcast.isEnabled();
    }

    public final boolean isMultiSoundBT() {
        int multiSoundDevice;
        SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.mSoundAssistantManagerWrapper;
        if (soundAssistantManagerWrapper != null) {
            SemSoundAssistantManager semSoundAssistantManager = soundAssistantManagerWrapper.satMananger;
            if (semSoundAssistantManager.isMultiSoundOn() && this.mAudio.semGetCurrentDeviceType() != (multiSoundDevice = semSoundAssistantManager.getMultiSoundDevice()) && multiSoundDevice == 8) {
                return true;
            }
        }
        return false;
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

    /* JADX WARN: Code restructure failed: missing block: B:214:0x0105, code lost:
    
        if (r5 == null) goto L77;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x031b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0380 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02cf  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onVolumeChangedW(int i, int i2) {
        List list;
        VolumeDialogController.StreamState streamStateW;
        List list2;
        String str;
        String str2;
        List list3;
        boolean z;
        boolean z2;
        int lastAudibleStreamVolume;
        boolean updateStreamLevelW;
        VolumeDialogController.StreamState streamStateW2;
        int i3;
        boolean isMediaStream;
        char c;
        boolean z3 = false;
        DesktopManagerWrapper desktopManagerWrapper = this.mDesktopManagerWrapper;
        if (desktopManagerWrapper != null && ((DesktopManagerImpl) desktopManagerWrapper.desktopManager).isStandalone() && !mIsVolumeStarEnabled) {
            return false;
        }
        boolean shouldShowUI = shouldShowUI(i2);
        boolean z4 = (i2 & 4096) != 0;
        boolean z5 = (i2 & 2048) != 0;
        boolean z6 = (i2 & 128) != 0;
        boolean z7 = (262144 & i2) != 0;
        VolumeDialogController.State state = this.mState;
        state.fixedSCOVolume = z7;
        state.remoteMic = (67108864 & i2) != 0;
        int i4 = (8388608 & i2) != 0 ? 21 : i;
        if ((4194304 & i2) != 0) {
            this.mSmartViewFlag = FLAG_SMART_VIEW_NONE;
            i4 = 20;
            streamStateW(20).levelMin = getAudioManagerStreamMinVolume(20);
            streamStateW(20).levelMax = getAudioManagerStreamMaxVolume(20);
        }
        int i5 = 524288 & i2;
        int i6 = 2;
        BluetoothAdapterWrapper bluetoothAdapterWrapper = this.mBluetoothAdapterManager;
        state.dualAudio = (i5 != 0 && bluetoothAdapterWrapper.getConnectedDevices().size() == 2) && shouldDualAudioUIEnabled();
        boolean isMultiSoundBT = isMultiSoundBT();
        int i7 = StreamUtil.$r8$clinit;
        VolumeDialogController.StreamState streamStateW3 = streamStateW(isMultiSoundBT ? 21 : 3);
        VolumeDialogController.StreamState streamStateW4 = streamStateW(22);
        List connectedDevices = bluetoothAdapterWrapper.getConnectedDevices();
        if (!connectedDevices.isEmpty()) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.get(0);
            BluetoothDevice bluetoothDevice2 = (state.dualAudio && connectedDevices.size() == 2) ? (BluetoothDevice) connectedDevices.get(1) : null;
            updateStreamRoutedToBudsW(bluetoothDevice, streamStateW3);
            updateStreamRoutedToBudsW(bluetoothDevice2, streamStateW4);
            updateStreamRoutedToHomeMiniW(bluetoothDevice, streamStateW3);
            updateStreamRoutedToHomeMiniW(bluetoothDevice2, streamStateW4);
            BluetoothHearingAid bluetoothHearingAid = bluetoothAdapterWrapper.hearingAid;
            if (bluetoothHearingAid != null) {
                BluetoothCommonUtil.INSTANCE.getClass();
                list = BluetoothCommonUtil.connectedDevices(bluetoothHearingAid);
                if (list != null) {
                    if (!(!list.isEmpty())) {
                        list = null;
                    }
                }
            }
            list = EmptyList.INSTANCE;
            if (list.isEmpty()) {
                streamStateW3.routedToHearingAid = false;
            } else {
                streamStateW3.routedToHearingAid = true;
            }
            String address = bluetoothDevice != null ? bluetoothDevice.getAddress() : null;
            String address2 = bluetoothDevice2 != null ? bluetoothDevice2.getAddress() : null;
            if (Objects.equals(address, streamStateW3.bluetoothDeviceAddress) && Objects.equals(address2, streamStateW4.bluetoothDeviceAddress)) {
                z3 = false;
                boolean z8 = z3 | false;
                streamStateW = streamStateW(isMultiSoundBT() ? 21 : 3);
                VolumeDialogController.StreamState streamStateW5 = streamStateW(22);
                if (isMusicShareEnabled()) {
                }
                str2 = null;
                if (Objects.equals(str, streamStateW.bluetoothDeviceName)) {
                }
                streamStateW.bluetoothDeviceName = str;
                streamStateW5.bluetoothDeviceName = str2;
                z = true;
                boolean updateStreamLevelW2 = z8 | z | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                if (shouldShowUI) {
                }
                z2 = BasicRune.VOLUME_HOME_IOT;
                if (z2) {
                }
                i6 = 0;
                if (shouldShowUI) {
                }
                lastAudibleStreamVolume = getLastAudibleStreamVolume(i4);
                updateStreamLevelW = updateStreamLevelW2 | updateStreamLevelW(i4, lastAudibleStreamVolume);
                StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onVolumeChangedW stream = ", i4, ", flags = ", i2, ", lastAudibleStreamVolume = ");
                m45m.append(lastAudibleStreamVolume);
                m45m.append(", changed = ");
                m45m.append(updateStreamLevelW);
                m45m.append(", showUI = ");
                m45m.append(shouldShowUI);
                m45m.append(", dualAudio = ");
                m45m.append(state.dualAudio);
                Log.d(TAG, m45m.toString());
                C3606C c3606c = this.mCallbacks;
                if (updateStreamLevelW) {
                }
                if (shouldShowUI) {
                }
                if (z5) {
                }
                if (z6) {
                }
                if ((i2 & 4) == 0) {
                }
                if (z2) {
                }
                if (z4) {
                }
                streamStateW2 = streamStateW(i4);
                i3 = streamStateW2.level;
                if (i3 == streamStateW2.levelMin) {
                }
                isMediaStream = isMediaStream(i4);
                int i8 = streamStateW2.levelMax;
                if (isMediaStream) {
                }
                if (i3 == i8) {
                }
                if (z4) {
                }
                if (updateStreamLevelW) {
                }
                return updateStreamLevelW;
            }
            streamStateW3.bluetoothDeviceAddress = address;
            streamStateW4.bluetoothDeviceAddress = address2;
            z3 = true;
            boolean z82 = z3 | false;
            streamStateW = streamStateW(isMultiSoundBT() ? 21 : 3);
            VolumeDialogController.StreamState streamStateW52 = streamStateW(22);
            if (isMusicShareEnabled()) {
            }
            str2 = null;
            if (Objects.equals(str, streamStateW.bluetoothDeviceName)) {
            }
            streamStateW.bluetoothDeviceName = str;
            streamStateW52.bluetoothDeviceName = str2;
            z = true;
            boolean updateStreamLevelW22 = z82 | z | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
            if (shouldShowUI) {
            }
            z2 = BasicRune.VOLUME_HOME_IOT;
            if (z2) {
            }
            i6 = 0;
            if (shouldShowUI) {
            }
            lastAudibleStreamVolume = getLastAudibleStreamVolume(i4);
            updateStreamLevelW = updateStreamLevelW22 | updateStreamLevelW(i4, lastAudibleStreamVolume);
            StringBuilder m45m2 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onVolumeChangedW stream = ", i4, ", flags = ", i2, ", lastAudibleStreamVolume = ");
            m45m2.append(lastAudibleStreamVolume);
            m45m2.append(", changed = ");
            m45m2.append(updateStreamLevelW);
            m45m2.append(", showUI = ");
            m45m2.append(shouldShowUI);
            m45m2.append(", dualAudio = ");
            m45m2.append(state.dualAudio);
            Log.d(TAG, m45m2.toString());
            C3606C c3606c2 = this.mCallbacks;
            if (updateStreamLevelW) {
            }
            if (shouldShowUI) {
            }
            if (z5) {
            }
            if (z6) {
            }
            if ((i2 & 4) == 0) {
            }
            if (z2) {
            }
            if (z4) {
            }
            streamStateW2 = streamStateW(i4);
            i3 = streamStateW2.level;
            if (i3 == streamStateW2.levelMin) {
            }
            isMediaStream = isMediaStream(i4);
            int i82 = streamStateW2.levelMax;
            if (isMediaStream) {
            }
            if (i3 == i82) {
            }
            if (z4) {
            }
            if (updateStreamLevelW) {
            }
            return updateStreamLevelW;
        }
        if (streamStateW3.bluetoothDeviceAddress != null) {
            streamStateW3.bluetoothDeviceAddress = null;
            streamStateW4.bluetoothDeviceAddress = null;
            z3 = true;
        }
        boolean z822 = z3 | false;
        streamStateW = streamStateW(isMultiSoundBT() ? 21 : 3);
        VolumeDialogController.StreamState streamStateW522 = streamStateW(22);
        if (isMusicShareEnabled()) {
            str = this.mBluetoothAudioCastWrapper.getCastDeviceConnectedName();
        } else {
            BluetoothA2dp bluetoothA2dp = bluetoothAdapterWrapper.a2dp;
            if (bluetoothA2dp != null) {
                BluetoothA2dpUtil.INSTANCE.getClass();
                BluetoothCommonUtil bluetoothCommonUtil = BluetoothCommonUtil.INSTANCE;
                List orderConnectedDevices = BluetoothA2dpUtil.getOrderConnectedDevices(bluetoothA2dp);
                bluetoothCommonUtil.getClass();
                list2 = BluetoothCommonUtil.mapNames(orderConnectedDevices);
            } else {
                list2 = null;
            }
            if (!(list2 != null ? !list2.isEmpty() : false)) {
                list2 = null;
            }
            if (list2 == null) {
                BluetoothLeAudio bluetoothLeAudio = bluetoothAdapterWrapper.leAudio;
                if (bluetoothLeAudio != null) {
                    BluetoothCommonUtil.INSTANCE.getClass();
                    list2 = BluetoothCommonUtil.mapNames(BluetoothCommonUtil.connectedDevices(bluetoothLeAudio));
                } else {
                    list2 = null;
                }
                if (!(list2 != null ? !list2.isEmpty() : false)) {
                    list2 = null;
                }
                if (list2 == null) {
                    BluetoothHearingAid bluetoothHearingAid2 = bluetoothAdapterWrapper.hearingAid;
                    if (bluetoothHearingAid2 != null) {
                        BluetoothCommonUtil.INSTANCE.getClass();
                        list3 = BluetoothCommonUtil.mapNames(BluetoothCommonUtil.connectedDevices(bluetoothHearingAid2));
                    } else {
                        list3 = null;
                    }
                    if (!(list3 != null ? !list3.isEmpty() : false)) {
                        list3 = null;
                    }
                    list2 = list3;
                    if (list2 == null) {
                        list2 = EmptyList.INSTANCE;
                    }
                }
            }
            if (list2.isEmpty()) {
                if (streamStateW.bluetoothDeviceName != null) {
                    streamStateW.bluetoothDeviceName = null;
                    streamStateW522.bluetoothDeviceName = null;
                    z = true;
                    boolean updateStreamLevelW222 = z822 | z | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                    if (shouldShowUI) {
                        updateStreamLevelW222 |= checkRoutedToBluetoothW(i4);
                        if (i4 == 3) {
                            updateStreamLevelW222 |= checkRoutedToBluetoothW(21);
                        }
                    }
                    z2 = BasicRune.VOLUME_HOME_IOT;
                    if (z2) {
                        VolumeDialogController.StreamState streamStateW6 = streamStateW(i4);
                        int lastAudibleStreamVolume2 = getLastAudibleStreamVolume(i4);
                        if (streamStateW6.levelMin != (isMediaStream(i4) ? lastAudibleStreamVolume2 / 100 : lastAudibleStreamVolume2) || streamStateW6.level != lastAudibleStreamVolume2) {
                            if (streamStateW6.levelMax == (isMediaStream(i4) ? lastAudibleStreamVolume2 / 100 : lastAudibleStreamVolume2) && streamStateW6.level == lastAudibleStreamVolume2) {
                                i6 = 3;
                            } else if (streamStateW6.level < lastAudibleStreamVolume2) {
                                i6 = 1;
                            }
                        }
                        if (shouldShowUI) {
                            updateStreamLevelW222 |= updateActiveStreamW(i4);
                        }
                        lastAudibleStreamVolume = getLastAudibleStreamVolume(i4);
                        updateStreamLevelW = updateStreamLevelW222 | updateStreamLevelW(i4, lastAudibleStreamVolume);
                        StringBuilder m45m22 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onVolumeChangedW stream = ", i4, ", flags = ", i2, ", lastAudibleStreamVolume = ");
                        m45m22.append(lastAudibleStreamVolume);
                        m45m22.append(", changed = ");
                        m45m22.append(updateStreamLevelW);
                        m45m22.append(", showUI = ");
                        m45m22.append(shouldShowUI);
                        m45m22.append(", dualAudio = ");
                        m45m22.append(state.dualAudio);
                        Log.d(TAG, m45m22.toString());
                        C3606C c3606c22 = this.mCallbacks;
                        if (updateStreamLevelW) {
                            c3606c22.onStateChanged(state);
                        }
                        if (shouldShowUI) {
                            c3606c22.onShowRequested(1, this.mKeyguardManager.isKeyguardLocked(), this.mActivityManager.getLockTaskModeState());
                        }
                        if (z5) {
                            c3606c22.onShowVibrateHint();
                        }
                        if (z6) {
                            c3606c22.onShowSilentHint();
                        }
                        boolean z9 = (i2 & 4) == 0;
                        if (z2) {
                            if (z9) {
                                c3606c22.onPlaySound(i4, z4);
                            }
                        } else if (shouldShowUI) {
                            if (z9 || i6 == 3 || i6 == 2) {
                                c3606c22.onPlaySound(i4, z4, i6);
                            }
                            int i9 = isMediaStream(i4) ? lastAudibleStreamVolume / 100 : lastAudibleStreamVolume;
                            Intent intent = new Intent("com.android.server.LightsService.action.LED_CONTROL_WHITE_LED_PATTERN");
                            intent.putExtra("details", "Volume:Light");
                            intent.putExtra("mode", 10);
                            intent.putExtra("extra", i9);
                            this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                        }
                        if (z4 && shouldShowUI) {
                            SALoggingWrapper.Event event = SALoggingWrapper.Event.VOLUME_KEY;
                            this.mSALoggingWrapper.getClass();
                            SALoggingWrapper.sendEventLog(event);
                        }
                        streamStateW2 = streamStateW(i4);
                        i3 = streamStateW2.level;
                        if (i3 == streamStateW2.levelMin || (65536 & i2) == 0) {
                            isMediaStream = isMediaStream(i4);
                            int i822 = streamStateW2.levelMax;
                            if (isMediaStream) {
                                i822 *= 100;
                            }
                            c = (i3 == i822 || (i2 & 131072) == 0) ? (char) 0 : (char) 1;
                        } else {
                            c = 65535;
                        }
                        if (z4) {
                            if (c == 0 || !shouldShowUI) {
                                if (this.mKeyDown) {
                                    this.mKeyDown = false;
                                    c3606c22.onKeyEvent(false, this.mIsVibrating);
                                }
                            } else if (!this.mKeyDown) {
                                this.mKeyDown = true;
                                c3606c22.onKeyEvent(true, this.mIsVibrating || (this.mAudio.getRingerModeInternal() == 1 && state.ringerModeInternal == 2));
                            }
                        }
                        if (updateStreamLevelW && z4) {
                            Events.writeEvent(4, Integer.valueOf(i4), Integer.valueOf(lastAudibleStreamVolume));
                        }
                        return updateStreamLevelW;
                    }
                    i6 = 0;
                    if (shouldShowUI) {
                    }
                    lastAudibleStreamVolume = getLastAudibleStreamVolume(i4);
                    updateStreamLevelW = updateStreamLevelW222 | updateStreamLevelW(i4, lastAudibleStreamVolume);
                    StringBuilder m45m222 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onVolumeChangedW stream = ", i4, ", flags = ", i2, ", lastAudibleStreamVolume = ");
                    m45m222.append(lastAudibleStreamVolume);
                    m45m222.append(", changed = ");
                    m45m222.append(updateStreamLevelW);
                    m45m222.append(", showUI = ");
                    m45m222.append(shouldShowUI);
                    m45m222.append(", dualAudio = ");
                    m45m222.append(state.dualAudio);
                    Log.d(TAG, m45m222.toString());
                    C3606C c3606c222 = this.mCallbacks;
                    if (updateStreamLevelW) {
                    }
                    if (shouldShowUI) {
                    }
                    if (z5) {
                    }
                    if (z6) {
                    }
                    if ((i2 & 4) == 0) {
                    }
                    if (z2) {
                    }
                    if (z4) {
                        SALoggingWrapper.Event event2 = SALoggingWrapper.Event.VOLUME_KEY;
                        this.mSALoggingWrapper.getClass();
                        SALoggingWrapper.sendEventLog(event2);
                    }
                    streamStateW2 = streamStateW(i4);
                    i3 = streamStateW2.level;
                    if (i3 == streamStateW2.levelMin) {
                    }
                    isMediaStream = isMediaStream(i4);
                    int i8222 = streamStateW2.levelMax;
                    if (isMediaStream) {
                    }
                    if (i3 == i8222) {
                    }
                    if (z4) {
                    }
                    if (updateStreamLevelW) {
                        Events.writeEvent(4, Integer.valueOf(i4), Integer.valueOf(lastAudibleStreamVolume));
                    }
                    return updateStreamLevelW;
                }
                z = false;
                boolean updateStreamLevelW2222 = z822 | z | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                if (shouldShowUI) {
                }
                z2 = BasicRune.VOLUME_HOME_IOT;
                if (z2) {
                }
                i6 = 0;
                if (shouldShowUI) {
                }
                lastAudibleStreamVolume = getLastAudibleStreamVolume(i4);
                updateStreamLevelW = updateStreamLevelW2222 | updateStreamLevelW(i4, lastAudibleStreamVolume);
                StringBuilder m45m2222 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onVolumeChangedW stream = ", i4, ", flags = ", i2, ", lastAudibleStreamVolume = ");
                m45m2222.append(lastAudibleStreamVolume);
                m45m2222.append(", changed = ");
                m45m2222.append(updateStreamLevelW);
                m45m2222.append(", showUI = ");
                m45m2222.append(shouldShowUI);
                m45m2222.append(", dualAudio = ");
                m45m2222.append(state.dualAudio);
                Log.d(TAG, m45m2222.toString());
                C3606C c3606c2222 = this.mCallbacks;
                if (updateStreamLevelW) {
                }
                if (shouldShowUI) {
                }
                if (z5) {
                }
                if (z6) {
                }
                if ((i2 & 4) == 0) {
                }
                if (z2) {
                }
                if (z4) {
                }
                streamStateW2 = streamStateW(i4);
                i3 = streamStateW2.level;
                if (i3 == streamStateW2.levelMin) {
                }
                isMediaStream = isMediaStream(i4);
                int i82222 = streamStateW2.levelMax;
                if (isMediaStream) {
                }
                if (i3 == i82222) {
                }
                if (z4) {
                }
                if (updateStreamLevelW) {
                }
                return updateStreamLevelW;
            }
            str = (String) list2.get(0);
            if (state.dualAudio && list2.size() == 2) {
                str2 = (String) list2.get(1);
                if (Objects.equals(str, streamStateW.bluetoothDeviceName) || !Objects.equals(str2, streamStateW522.bluetoothDeviceName)) {
                    streamStateW.bluetoothDeviceName = str;
                    streamStateW522.bluetoothDeviceName = str2;
                    z = true;
                    boolean updateStreamLevelW22222 = z822 | z | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                    if (shouldShowUI) {
                    }
                    z2 = BasicRune.VOLUME_HOME_IOT;
                    if (z2) {
                    }
                    i6 = 0;
                    if (shouldShowUI) {
                    }
                    lastAudibleStreamVolume = getLastAudibleStreamVolume(i4);
                    updateStreamLevelW = updateStreamLevelW22222 | updateStreamLevelW(i4, lastAudibleStreamVolume);
                    StringBuilder m45m22222 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onVolumeChangedW stream = ", i4, ", flags = ", i2, ", lastAudibleStreamVolume = ");
                    m45m22222.append(lastAudibleStreamVolume);
                    m45m22222.append(", changed = ");
                    m45m22222.append(updateStreamLevelW);
                    m45m22222.append(", showUI = ");
                    m45m22222.append(shouldShowUI);
                    m45m22222.append(", dualAudio = ");
                    m45m22222.append(state.dualAudio);
                    Log.d(TAG, m45m22222.toString());
                    C3606C c3606c22222 = this.mCallbacks;
                    if (updateStreamLevelW) {
                    }
                    if (shouldShowUI) {
                    }
                    if (z5) {
                    }
                    if (z6) {
                    }
                    if ((i2 & 4) == 0) {
                    }
                    if (z2) {
                    }
                    if (z4) {
                    }
                    streamStateW2 = streamStateW(i4);
                    i3 = streamStateW2.level;
                    if (i3 == streamStateW2.levelMin) {
                    }
                    isMediaStream = isMediaStream(i4);
                    int i822222 = streamStateW2.levelMax;
                    if (isMediaStream) {
                    }
                    if (i3 == i822222) {
                    }
                    if (z4) {
                    }
                    if (updateStreamLevelW) {
                    }
                    return updateStreamLevelW;
                }
                z = false;
                boolean updateStreamLevelW222222 = z822 | z | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
                if (shouldShowUI) {
                }
                z2 = BasicRune.VOLUME_HOME_IOT;
                if (z2) {
                }
                i6 = 0;
                if (shouldShowUI) {
                }
                lastAudibleStreamVolume = getLastAudibleStreamVolume(i4);
                updateStreamLevelW = updateStreamLevelW222222 | updateStreamLevelW(i4, lastAudibleStreamVolume);
                StringBuilder m45m222222 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onVolumeChangedW stream = ", i4, ", flags = ", i2, ", lastAudibleStreamVolume = ");
                m45m222222.append(lastAudibleStreamVolume);
                m45m222222.append(", changed = ");
                m45m222222.append(updateStreamLevelW);
                m45m222222.append(", showUI = ");
                m45m222222.append(shouldShowUI);
                m45m222222.append(", dualAudio = ");
                m45m222222.append(state.dualAudio);
                Log.d(TAG, m45m222222.toString());
                C3606C c3606c222222 = this.mCallbacks;
                if (updateStreamLevelW) {
                }
                if (shouldShowUI) {
                }
                if (z5) {
                }
                if (z6) {
                }
                if ((i2 & 4) == 0) {
                }
                if (z2) {
                }
                if (z4) {
                }
                streamStateW2 = streamStateW(i4);
                i3 = streamStateW2.level;
                if (i3 == streamStateW2.levelMin) {
                }
                isMediaStream = isMediaStream(i4);
                int i8222222 = streamStateW2.levelMax;
                if (isMediaStream) {
                }
                if (i3 == i8222222) {
                }
                if (z4) {
                }
                if (updateStreamLevelW) {
                }
                return updateStreamLevelW;
            }
        }
        str2 = null;
        if (Objects.equals(str, streamStateW.bluetoothDeviceName)) {
        }
        streamStateW.bluetoothDeviceName = str;
        streamStateW522.bluetoothDeviceName = str2;
        z = true;
        boolean updateStreamLevelW2222222 = z822 | z | updateStreamLevelW(22, getLastAudibleStreamVolume(22)) | checkRoutedToBluetoothW(22);
        if (shouldShowUI) {
        }
        z2 = BasicRune.VOLUME_HOME_IOT;
        if (z2) {
        }
        i6 = 0;
        if (shouldShowUI) {
        }
        lastAudibleStreamVolume = getLastAudibleStreamVolume(i4);
        updateStreamLevelW = updateStreamLevelW2222222 | updateStreamLevelW(i4, lastAudibleStreamVolume);
        StringBuilder m45m2222222 = GridLayoutManager$$ExternalSyntheticOutline0.m45m("onVolumeChangedW stream = ", i4, ", flags = ", i2, ", lastAudibleStreamVolume = ");
        m45m2222222.append(lastAudibleStreamVolume);
        m45m2222222.append(", changed = ");
        m45m2222222.append(updateStreamLevelW);
        m45m2222222.append(", showUI = ");
        m45m2222222.append(shouldShowUI);
        m45m2222222.append(", dualAudio = ");
        m45m2222222.append(state.dualAudio);
        Log.d(TAG, m45m2222222.toString());
        C3606C c3606c2222222 = this.mCallbacks;
        if (updateStreamLevelW) {
        }
        if (shouldShowUI) {
        }
        if (z5) {
        }
        if (z6) {
        }
        if ((i2 & 4) == 0) {
        }
        if (z2) {
        }
        if (z4) {
        }
        streamStateW2 = streamStateW(i4);
        i3 = streamStateW2.level;
        if (i3 == streamStateW2.levelMin) {
        }
        isMediaStream = isMediaStream(i4);
        int i82222222 = streamStateW2.levelMax;
        if (isMediaStream) {
        }
        if (i3 == i82222222) {
        }
        if (z4) {
        }
        if (updateStreamLevelW) {
        }
        return updateStreamLevelW;
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
    public final void setCaptionsEnabled(boolean z) {
        this.mCaptioningManager.setSystemAudioCaptioningEnabled(z);
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
        HandlerC3610W handlerC3610W = this.mWorker;
        handlerC3610W.removeMessages(10);
        handlerC3610W.obtainMessage(10, i, i2).sendToTarget();
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void setStreamVolumeDualAudio(int i, int i2, String str) {
        HandlerC3610W handlerC3610W = this.mWorker;
        handlerC3610W.removeMessages(19);
        handlerC3610W.obtainMessage(19, i, i2, str).sendToTarget();
    }

    public boolean shouldDualAudioUIEnabled() {
        int semGetCurrentDeviceType = this.mAudio.semGetCurrentDeviceType();
        if (semGetCurrentDeviceType != 8) {
            SoundAssistantManagerWrapper soundAssistantManagerWrapper = this.mSoundAssistantManagerWrapper;
            if (soundAssistantManagerWrapper != null) {
                SemSoundAssistantManager semSoundAssistantManager = soundAssistantManagerWrapper.satMananger;
                if (!semSoundAssistantManager.isMultiSoundOn() || semGetCurrentDeviceType == semSoundAssistantManager.getMultiSoundDevice()) {
                }
            }
            return false;
        }
        return true;
    }

    public final boolean shouldShowUI(int i) {
        DeviceStateManagerWrapper deviceStateManagerWrapper;
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (knoxStateMonitor != null) {
            CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mCustomSdkMonitor;
            if (!(customSdkMonitor != null && customSdkMonitor.mVolumePanelEnabledState)) {
                Log.d(TAG, "KnoxStateMonitor : Disable VolumeDialog");
                return false;
            }
        }
        if (BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG && (deviceStateManagerWrapper = this.mDeviceStateManagerWrapper) != null && deviceStateManagerWrapper.isFolded && this.mShowVolumeDialog && (i & 1) != 0) {
            return true;
        }
        int i2 = this.mWakefulnessLifecycle.mWakefulness;
        return this.mState.aodEnabled ? this.mShowVolumeDialog && (i & 1) != 0 : (i2 == 0 || i2 == 3 || !this.mDeviceInteractive || (i & 1) == 0 || !this.mShowVolumeDialog) ? false : true;
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
        boolean z = C3599D.BUG;
        String str = TAG;
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("updateActiveStreamW ", i, str);
        }
        if (i >= 100) {
            i = -1;
        }
        if (i == 21) {
            i = 10003;
        }
        if (i == 23 || i == 20 || i == 22) {
            i = 3;
        }
        if (z) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("forceVolumeControlStream ", i, str);
        }
        this.mAudio.forceVolumeControlStream(i);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002e, code lost:
    
        if (r3.length() > 0) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateEffectsSuppressorW(ComponentName componentName) {
        String packageName;
        String str;
        VolumeDialogController.State state = this.mState;
        if (Objects.equals(state.effectsSuppressor, componentName)) {
            return false;
        }
        state.effectsSuppressor = componentName;
        PackageManager packageManager = this.mPackageManager;
        if (componentName == null) {
            str = null;
        } else {
            packageName = componentName.getPackageName();
            try {
                str = Objects.toString(packageManager.getApplicationInfo(packageName, 0).loadLabel(packageManager), "").trim();
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        state.effectsSuppressorName = str;
        Events.writeEvent(14, state.effectsSuppressor, state.effectsSuppressorName);
        return true;
        str = packageName;
        state.effectsSuppressorName = str;
        Events.writeEvent(14, state.effectsSuppressor, state.effectsSuppressorName);
        return true;
    }

    public final boolean updateRingerModeInternalW(int i) {
        VolumeDialogController.State state = this.mState;
        if (i == state.ringerModeInternal) {
            return false;
        }
        if (i == 1) {
            this.mIsVibrating = true;
            this.mWorker.postDelayed(new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(this, 0), 700L);
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
        boolean z = false;
        if (streamStateW.level == i2) {
            return false;
        }
        streamStateW.level = i2;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                z = true;
                break;
        }
        if (z) {
            Events.writeEvent(10, Integer.valueOf(i), Integer.valueOf(i2));
        }
        return true;
    }

    public final boolean updateStreamMuteW(int i, boolean z) {
        boolean z2;
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.muted == z) {
            return false;
        }
        streamStateW.muted = z;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                z2 = true;
                break;
            default:
                z2 = false;
                break;
        }
        if (z2) {
            Events.writeEvent(15, Integer.valueOf(i), Boolean.valueOf(z));
        }
        if (z) {
            if (i == 2 || i == 5) {
                updateRingerModeInternalW(this.mRingerModeObservers.mRingerModeInternal.getValue().intValue());
            }
        }
        return true;
    }

    public final void updateStreamNameMusicShare() {
        VolumeDialogController.StreamState streamStateW = streamStateW(3);
        streamStateW.nameRes = this.mContext.getResources().getResourceName(isMusicShareEnabled() ? com.android.systemui.R.string.volumepanel_music_share : streamStateW.name);
        if (C3599D.BUG) {
            Log.d(TAG, "updateStreamNameMusicShare " + isMusicShareEnabled());
        }
    }

    public final boolean updateStreamRoutedToBluetoothW(int i, boolean z) {
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.routedToBluetooth == z) {
            return false;
        }
        streamStateW.routedToBluetooth = z;
        if (!C3599D.BUG) {
            return true;
        }
        Log.d(TAG, "updateStreamRoutedToBluetoothW stream=" + i + " routedToBluetooth=" + z);
        return true;
    }

    public final void updateStreamRoutedToBudsW(BluetoothDevice bluetoothDevice, VolumeDialogController.StreamState streamState) {
        if (bluetoothDevice != null) {
            BluetoothIconUtil bluetoothIconUtil = BluetoothIconUtil.INSTANCE;
            BluetoothIconUtil.SamsungStandard.Companion.getClass();
            ArrayList arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(Short.valueOf(BluetoothIconUtil.SamsungStandard.Companion.GALAXY_BUDS), Short.valueOf(BluetoothIconUtil.SamsungStandard.Companion.GALAXY_BUDS_LIVE));
            BluetoothIconUtil.INSTANCE.getClass();
            if (BluetoothIconUtil.isSameDeviceIconType(bluetoothDevice, arrayListOf)) {
                streamState.routedToBuds = true;
                streamState.routedToBuds3 = false;
                return;
            } else if (BluetoothIconUtil.isSameDeviceIconType(bluetoothDevice, CollectionsKt__CollectionsKt.arrayListOf(Short.valueOf(BluetoothIconUtil.SamsungStandard.Companion.GALAXY_BUDS3))) && !this.mSoundAssistantChecker.isNeedToChangeBuds3IconToBtIcon) {
                streamState.routedToBuds = false;
                streamState.routedToBuds3 = true;
                return;
            }
        }
        streamState.routedToBuds = false;
        streamState.routedToBuds3 = false;
    }

    public final boolean updateStreamRoutedToHeadsetW(int i, boolean z) {
        VolumeDialogController.StreamState streamStateW = streamStateW(i);
        if (streamStateW.routedToHeadset == z) {
            return false;
        }
        streamStateW.routedToHeadset = z;
        if (!C3599D.BUG) {
            return true;
        }
        Log.d(TAG, "updateStreamRoutedToHeadsetW stream=" + i + " routedToHeadset=" + z);
        return true;
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
        Events.writeEvent(17, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m74m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("disallowAlarms=", z, " disallowMedia=", z2, " disallowSystem="), z3, " disallowRinger=", areAllPriorityOnlyRingerSoundsMuted));
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
        HandlerC3610W handlerC3610W = this.mWorker;
        handlerC3610W.removeMessages(13);
        handlerC3610W.sendEmptyMessage(13);
    }

    @Override // com.android.systemui.plugins.VolumeDialogController
    public final void vibrate(VibrationEffect vibrationEffect) {
        this.mVibrator.vibrate(vibrationEffect, SONIFICIATION_VIBRATION_ATTRIBUTES);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.volume.VolumeDialogControllerImpl$C */
    public final class C3606C implements VolumeDialogController.Callbacks {
        public final Map mCallbackMap = new ConcurrentHashMap();

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onAccessibilityModeChanged(Boolean bool) {
            final boolean z = bool != null && bool.booleanValue();
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable(this) { // from class: com.android.systemui.volume.VolumeDialogControllerImpl.C.11
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((VolumeDialogController.Callbacks) entry.getKey()).onAccessibilityModeChanged(Boolean.valueOf(z));
                    }
                });
            }
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onCaptionComponentStateChanged(Boolean bool, final Boolean bool2) {
            final boolean z = bool != null && bool.booleanValue();
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$C$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Map.Entry entry2 = entry;
                        boolean z2 = z;
                        ((VolumeDialogController.Callbacks) entry2.getKey()).onCaptionComponentStateChanged(Boolean.valueOf(z2), bool2);
                    }
                });
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
                ((Handler) entry.getValue()).post(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$C$$ExternalSyntheticLambda0
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
                ((Handler) entry.getValue()).post(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$C$$ExternalSyntheticLambda3
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
                ((Handler) entry.getValue()).post(new VolumeDialogControllerImpl$$ExternalSyntheticLambda1(entry, 3));
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
        public final void onPlaySound(final int i, final boolean z, final int i2) {
            for (final Map.Entry entry : ((ConcurrentHashMap) this.mCallbackMap).entrySet()) {
                ((Handler) entry.getValue()).post(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$C$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Map.Entry entry2 = entry;
                        ((VolumeDialogController.Callbacks) entry2.getKey()).onPlaySound(i, z, i2);
                    }
                });
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setStreamVolume(int i, int i2, final String str) {
        boolean z;
        Pair pair;
        if (i == 20) {
            return;
        }
        boolean isMediaStream = isMediaStream(i);
        AudioManager audioManager = this.mAudio;
        if (!isMediaStream) {
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
            audioManager.setStreamVolume(i, i2, 0);
            return;
        }
        int i3 = i2 / 10;
        List arrayList = new ArrayList();
        if (this.mState.dualAudio) {
            arrayList = this.mBluetoothAdapterManager.getConnectedDevices();
            if (!arrayList.isEmpty() && str != null && !str.isEmpty()) {
                z = true;
                pair = new Pair(Boolean.valueOf(z), arrayList);
                if (((Boolean) pair.first).booleanValue()) {
                    if (i == 21) {
                        audioManager.setFineVolume(3, i3, 0, audioManager.semGetPinDevice());
                        return;
                    } else {
                        audioManager.semSetFineVolume(3, i3, 0);
                        return;
                    }
                }
                BluetoothDevice bluetoothDevice = (BluetoothDevice) ((List) pair.second).stream().filter(new Predicate() { // from class: com.android.systemui.volume.VolumeDialogControllerImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return str.equals(((BluetoothDevice) obj).getAddress());
                    }
                }).findFirst().orElse(null);
                if (bluetoothDevice != null) {
                    audioManager.semSetFineVolume(bluetoothDevice, 3, i3, 0);
                    return;
                } else {
                    audioManager.semSetFineVolume(3, i3, 0);
                    return;
                }
            }
        }
        z = false;
        pair = new Pair(Boolean.valueOf(z), arrayList);
        if (((Boolean) pair.first).booleanValue()) {
        }
    }
}
