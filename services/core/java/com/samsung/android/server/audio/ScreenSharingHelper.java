package com.samsung.android.server.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemDeviceStatusListener;
import android.hardware.display.SemDlnaDevice;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioSystem;
import android.os.Binder;
import android.provider.Settings;
import android.util.Log;
import com.android.server.audio.MediaFocusControl;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.SemAudioSystem;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class ScreenSharingHelper {
    public static ScreenSharingHelper sInstance = null;
    public static boolean sIsWifiDisplayConnected = false;
    public static boolean sSplitSoundEnabled = false;
    public AudioManager mAudioManager;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final MediaFocusControl mMediaFocusControl;
    public boolean mIsSupportDisplayVolumeControl = false;
    public boolean mIsAppCasting = false;
    public boolean mScreenSharingStateResumed = false;
    public boolean mIsDLNAEnabled = false;
    public final SemDeviceStatusListener mSemDeviceStatusListener = new SemDeviceStatusListener() { // from class: com.samsung.android.server.audio.ScreenSharingHelper.1
        public void onConnectionStatusChanged(int i) {
        }

        public void onDlnaConnectionStatusChanged(boolean z) {
        }

        public void onQosLevelChanged(int i) {
        }

        public void onScreenSharingStatusChanged(int i) {
            if (i == 6) {
                if (ScreenSharingHelper.this.mIsSupportDisplayVolumeControl) {
                    ScreenSharingHelper.this.mScreenSharingStateResumed = true;
                }
                ScreenSharingHelper.this.setMirroringPolicyParameter(true);
            } else if (i == 7) {
                if (ScreenSharingHelper.this.mIsSupportDisplayVolumeControl) {
                    ScreenSharingHelper.this.mScreenSharingStateResumed = false;
                }
                ScreenSharingHelper.this.setMirroringPolicyParameter(false);
                if (ScreenSharingHelper.isSplitSoundEnabled()) {
                    ScreenSharingHelper.setSplitSoundEnabled(false);
                    SemAudioSystem.setPolicyParameters("l_smart_view_split_sound_enable=" + ScreenSharingHelper.isSplitSoundEnabled());
                    ScreenSharingHelper.this.mMediaFocusControl.checkSplitSoundAudioFocus();
                }
            }
            Log.d("AS.ScreenSharingHelper", "onScreenSharingStatusChanged, status = " + ScreenSharingHelper.this.mScreenSharingStateResumed);
        }
    };
    public final FrequentWorker mDisplayVolumeControlChecker = new FrequentWorker() { // from class: com.samsung.android.server.audio.ScreenSharingHelper.2
        {
            this.mPeriodMs = 1000;
            this.mCachedValue = Boolean.FALSE;
        }

        @Override // com.samsung.android.server.audio.FrequentWorker
        public Boolean func() {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SemDlnaDevice semGetActiveDlnaDevice = ScreenSharingHelper.this.mDisplayManager.semGetActiveDlnaDevice();
                int dlnaType = semGetActiveDlnaDevice != null ? semGetActiveDlnaDevice.getDlnaType() : -1;
                return Boolean.valueOf(ScreenSharingHelper.this.mIsDLNAEnabled && (dlnaType == 0 || dlnaType == 2 || dlnaType == 3));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    };
    public boolean mIsPresentationMode = false;
    public final BroadcastReceiver mPresentationModeReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.audio.ScreenSharingHelper.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            if (intent.getAction().equals("com.samsung.intent.action.SEC_PRESENTATION_START_SMARTVIEW")) {
                Log.d("AS.ScreenSharingHelper", "Enable presentation mode");
                ScreenSharingHelper.this.mIsPresentationMode = true;
                ScreenSharingHelper.this.setMirroringPolicyParameter(false);
            } else if (intent.getAction().equals("com.samsung.intent.action.SEC_PRESENTATION_STOP_SMARTVIEW")) {
                Log.d("AS.ScreenSharingHelper", "Disable presentation mode");
                ScreenSharingHelper.this.mIsPresentationMode = false;
                ScreenSharingHelper.this.setMirroringPolicyParameter(true);
            }
        }
    };

    public ScreenSharingHelper(Context context, MediaFocusControl mediaFocusControl) {
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mMediaFocusControl = mediaFocusControl;
    }

    public static ScreenSharingHelper getInstance(Context context, MediaFocusControl mediaFocusControl) {
        if (sInstance == null) {
            sInstance = new ScreenSharingHelper(context, mediaFocusControl);
        }
        return sInstance;
    }

    public static boolean isAllowed(AudioAttributes audioAttributes) {
        int usage = audioAttributes.getUsage();
        return usage == 4 || usage == 5 || usage == 6;
    }

    public static boolean isSplitSoundEnabled() {
        return sSplitSoundEnabled;
    }

    public static void setSplitSoundEnabled(boolean z) {
        sSplitSoundEnabled = z;
    }

    public static boolean isWifiDisplayConnected() {
        return sIsWifiDisplayConnected;
    }

    public static void setWifiDisplayConnected(boolean z) {
        sIsWifiDisplayConnected = z;
    }

    public boolean isSupportDisplayVolumeControl() {
        return this.mIsSupportDisplayVolumeControl;
    }

    public void setSupportDisplayVolumeControl(boolean z) {
        this.mIsSupportDisplayVolumeControl = z;
        Log.d("AS.ScreenSharingHelper", "setSupportDisplayVolumeControl : supportDisplayVolumeControl=" + this.mIsSupportDisplayVolumeControl);
    }

    public void updateAppCasting(int i) {
        switch (i) {
            case -1002:
            case -1001:
                this.mIsAppCasting = false;
                break;
            case -1000:
                this.mIsAppCasting = true;
                break;
        }
        Log.d("AS.ScreenSharingHelper", "updateAppCasting : deviceType=" + i + ", mIsAppCasting=" + this.mIsAppCasting);
    }

    public void setScreenSharingStateResumed(boolean z) {
        this.mScreenSharingStateResumed = z;
    }

    public boolean isDLNAEnabled() {
        return this.mIsDLNAEnabled;
    }

    public void setDLNAEnabled(boolean z) {
        this.mIsDLNAEnabled = z;
        setMirroringPolicyParameter(!z);
    }

    public void checkAndSetSplitSound(boolean z, AudioAttributes audioAttributes, String str) {
        if ("com.android.server.telecom".equals(str) || isAllowed(audioAttributes)) {
            if (sIsWifiDisplayConnected && !z && AudioSystem.isStreamActiveRemotely(3, 0)) {
                setSplitSoundEnabled(true);
            } else {
                setSplitSoundEnabled(false);
            }
            SemAudioSystem.setPolicyParameters("l_smart_view_split_sound_enable=" + isSplitSoundEnabled());
        }
    }

    public boolean tvVolumeShouldBeAdjusted(int i, int i2, int i3, final Set set, int i4) {
        if (((Boolean) this.mDisplayVolumeControlChecker.runOrSkip()).booleanValue()) {
            return true;
        }
        if (this.mIsSupportDisplayVolumeControl) {
            List<AudioPlaybackConfiguration> activePlaybackConfigurations = this.mAudioManager.getActivePlaybackConfigurations();
            boolean anyMatch = activePlaybackConfigurations.stream().anyMatch(new Predicate() { // from class: com.samsung.android.server.audio.ScreenSharingHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$tvVolumeShouldBeAdjusted$0;
                    lambda$tvVolumeShouldBeAdjusted$0 = ScreenSharingHelper.lambda$tvVolumeShouldBeAdjusted$0(set, (AudioPlaybackConfiguration) obj);
                    return lambda$tvVolumeShouldBeAdjusted$0;
                }
            });
            List list = (List) activePlaybackConfigurations.stream().filter(new Predicate() { // from class: com.samsung.android.server.audio.ScreenSharingHelper$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$tvVolumeShouldBeAdjusted$1;
                    lambda$tvVolumeShouldBeAdjusted$1 = ScreenSharingHelper.lambda$tvVolumeShouldBeAdjusted$1(set, (AudioPlaybackConfiguration) obj);
                    return lambda$tvVolumeShouldBeAdjusted$1;
                }
            }).collect(Collectors.toList());
            boolean z = !list.isEmpty();
            int intValue = ((Integer) list.stream().findFirst().map(new Function() { // from class: com.samsung.android.server.audio.ScreenSharingHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((AudioPlaybackConfiguration) obj).getClientUid());
                }
            }).orElse(-1)).intValue();
            boolean z2 = anyMatch && z && i4 != intValue;
            int semGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
            Log.d("AS.ScreenSharingHelper", "tvVolumeShouldBeAdjusted : stream=" + i + ", isDlna=" + this.mDisplayVolumeControlChecker.runOrSkip() + ", mScreenSharingStateResumed=" + this.mScreenSharingStateResumed + ", mIsAppCasting=" + this.mIsAppCasting + ", isLocalPlaying=" + anyMatch + ", isRemotePlaying=" + z + ", remotePlayingAppUid=" + intValue + ", foregroundUid=" + i4 + ", localHasFocus=" + z2 + ", currDeviceType=" + semGetCurrentDeviceType);
            if (!this.mIsAppCasting && semGetCurrentDeviceType == 25) {
                return true;
            }
            if (!z || z2) {
                return false;
            }
        }
        if (!this.mScreenSharingStateResumed || (i2 & 1) == 0) {
            return false;
        }
        if (AudioSystem.isStreamActiveRemotely(3, 0)) {
            return true;
        }
        return (32768 & i3) != 0 && i == 3;
    }

    public static /* synthetic */ boolean lambda$tvVolumeShouldBeAdjusted$0(Set set, AudioPlaybackConfiguration audioPlaybackConfiguration) {
        return !set.contains(Integer.valueOf(audioPlaybackConfiguration.getClientUid())) && audioPlaybackConfiguration.getPlayerState() == 2;
    }

    public static /* synthetic */ boolean lambda$tvVolumeShouldBeAdjusted$1(Set set, AudioPlaybackConfiguration audioPlaybackConfiguration) {
        return set.contains(Integer.valueOf(audioPlaybackConfiguration.getClientUid())) && audioPlaybackConfiguration.getPlayerState() == 2;
    }

    public void registerDeviceStatusListener(Context context) {
        String str = "l_smart_view_enable=true";
        if (Rune.SEC_AUDIO_VOIP_VIA_SMART_VIEW && this.mDisplayManager.getWifiDisplayStatus().getActiveDisplay() != null) {
            boolean z = this.mDisplayManager.getWifiDisplayStatus().getConnectedState() == 0;
            StringBuilder sb = new StringBuilder();
            sb.append("l_smart_view_enable=true");
            sb.append(";support_voip=");
            sb.append(z ? "true" : "false");
            str = sb.toString();
            if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI && z) {
                MicModeManager.getInstance(this.mContext).updateSmartViewState(true);
            }
        }
        SemAudioSystem.setPolicyParameters(str);
        this.mDisplayManager.semRegisterDeviceStatusListener(this.mSemDeviceStatusListener, null);
        IntentFilter intentFilter = new IntentFilter("com.samsung.intent.action.SEC_PRESENTATION_START_SMARTVIEW");
        intentFilter.addAction("com.samsung.intent.action.SEC_PRESENTATION_STOP_SMARTVIEW");
        context.registerReceiver(this.mPresentationModeReceiver, intentFilter);
    }

    public void unregisterDeviceStatusListener(Context context) {
        SemAudioSystem.setPolicyParameters("l_smart_view_enable=false");
        SemAudioSystem.setPolicyParameters("l_smart_view_split_sound_enable=" + isSplitSoundEnabled());
        this.mDisplayManager.semUnregisterDeviceStatusListener(this.mSemDeviceStatusListener);
        context.unregisterReceiver(this.mPresentationModeReceiver);
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            MicModeManager.getInstance(this.mContext).updateSmartViewState(false);
        }
    }

    public boolean getLiveCaptionEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "odi_captions_enabled", 0) == 1;
    }

    public final void setMirroringPolicyParameter(boolean z) {
        if (Rune.SEC_AUDIO_VOIP_VIA_SMART_VIEW) {
            if (this.mIsPresentationMode || this.mIsDLNAEnabled) {
                z = false;
            }
            if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
                Log.i("AS.ScreenSharingHelper", "setMirroringPolicyParameter updateSmartViewState active : " + z);
                MicModeManager.getInstance(this.mContext).updateSmartViewState(z);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("l_smart_view_mirroring_active=");
            sb.append(z ? "true" : "false");
            SemAudioSystem.setPolicyParameters(sb.toString());
        }
    }
}
