package com.samsung.android.server.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemDeviceStatusListener;
import android.hardware.display.SemDlnaDevice;
import android.media.AudioManager;
import android.os.Binder;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.FocusRequester;
import com.android.server.audio.MediaFocusControl;

import com.samsung.android.audio.Rune;
import com.samsung.android.media.SemAudioSystem;

public final class ScreenSharingHelper {
    public static ScreenSharingHelper sInstance;
    public static boolean sIsWifiDisplayConnected;
    public static boolean sSplitSoundEnabled;
    public final AudioManager mAudioManager;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final MediaFocusControl mMediaFocusControl;
    public boolean mIsSupportDisplayVolumeControl = false;
    public boolean mIsAppCasting = false;
    public boolean mScreenSharingStateResumed = false;
    public boolean mIsDLNAEnabled = false;
    public final AnonymousClass1 mDisplayVolumeControlChecker =
            new FrequentWorker() { // from class:
                                   // com.samsung.android.server.audio.ScreenSharingHelper.1
                {
                    this.mPeriodMs = 1000;
                    this.mCachedValue = Boolean.FALSE;
                }

                @Override // com.samsung.android.server.audio.FrequentWorker
                public final Object func() {
                    ScreenSharingHelper screenSharingHelper = ScreenSharingHelper.this;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        SemDlnaDevice semGetActiveDlnaDevice =
                                screenSharingHelper.mDisplayManager.semGetActiveDlnaDevice();
                        int dlnaType =
                                semGetActiveDlnaDevice != null
                                        ? semGetActiveDlnaDevice.getDlnaType()
                                        : -1;
                        Boolean valueOf =
                                Boolean.valueOf(
                                        screenSharingHelper.mIsDLNAEnabled
                                                && (dlnaType == 0
                                                        || dlnaType == 2
                                                        || dlnaType == 3));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return valueOf;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
            };
    public boolean mIsPresentationMode = false;
    public final AnonymousClass2 mSemDeviceStatusListener =
            new SemDeviceStatusListener() { // from class:
                                            // com.samsung.android.server.audio.ScreenSharingHelper.2
                public final void onConnectionStatusChanged(int i) {}

                public final void onDlnaConnectionStatusChanged(boolean z) {}

                public final void onQosLevelChanged(int i) {}

                public final void onScreenSharingStatusChanged(int i) {
                    if (i == 6) {
                        ScreenSharingHelper screenSharingHelper = ScreenSharingHelper.this;
                        if (screenSharingHelper.mIsSupportDisplayVolumeControl) {
                            screenSharingHelper.mScreenSharingStateResumed = true;
                        }
                        screenSharingHelper.setMirroringPolicyParameter(true);
                    } else if (i == 7) {
                        ScreenSharingHelper screenSharingHelper2 = ScreenSharingHelper.this;
                        if (screenSharingHelper2.mIsSupportDisplayVolumeControl) {
                            screenSharingHelper2.mScreenSharingStateResumed = false;
                        }
                        screenSharingHelper2.setMirroringPolicyParameter(false);
                        if (ScreenSharingHelper.sSplitSoundEnabled) {
                            ScreenSharingHelper.sSplitSoundEnabled = false;
                            SemAudioSystem.setPolicyParameters(
                                    "l_smart_view_split_sound_enable="
                                            + ScreenSharingHelper.sSplitSoundEnabled);
                            MediaFocusControl mediaFocusControl =
                                    ScreenSharingHelper.this.mMediaFocusControl;
                            FocusRequester focusRequester = mediaFocusControl.mSplitSoundFR;
                            if (focusRequester != null) {
                                mediaFocusControl.requestAudioFocus(
                                        focusRequester.mAttributes,
                                        focusRequester.mFocusGainRequest,
                                        mediaFocusControl.mSplitSoundCb,
                                        null,
                                        focusRequester.mClientId,
                                        focusRequester.mPackageName,
                                        focusRequester.mGrantFlags,
                                        focusRequester.mSdkTarget,
                                        true,
                                        -1);
                                mediaFocusControl.mSplitSoundFR = null;
                                mediaFocusControl.mSplitSoundCb = null;
                            }
                        }
                    }
                    RCPManagerService$$ExternalSyntheticOutline0.m(
                            "AS.ScreenSharingHelper",
                            new StringBuilder("onScreenSharingStatusChanged, status = "),
                            ScreenSharingHelper.this.mScreenSharingStateResumed);
                }
            };
    public final AnonymousClass3 mPresentationModeReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.server.audio.ScreenSharingHelper.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent == null) {
                        return;
                    }
                    if (intent.getAction()
                            .equals("com.samsung.intent.action.SEC_PRESENTATION_START_SMARTVIEW")) {
                        Log.d("AS.ScreenSharingHelper", "Enable presentation mode");
                        ScreenSharingHelper screenSharingHelper = ScreenSharingHelper.this;
                        screenSharingHelper.mIsPresentationMode = true;
                        screenSharingHelper.setMirroringPolicyParameter(false);
                        return;
                    }
                    if (intent.getAction()
                            .equals("com.samsung.intent.action.SEC_PRESENTATION_STOP_SMARTVIEW")) {
                        Log.d("AS.ScreenSharingHelper", "Disable presentation mode");
                        ScreenSharingHelper screenSharingHelper2 = ScreenSharingHelper.this;
                        screenSharingHelper2.mIsPresentationMode = false;
                        screenSharingHelper2.setMirroringPolicyParameter(true);
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.server.audio.ScreenSharingHelper$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.server.audio.ScreenSharingHelper$3] */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.server.audio.ScreenSharingHelper$1] */
    public ScreenSharingHelper(Context context, MediaFocusControl mediaFocusControl) {
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mMediaFocusControl = mediaFocusControl;
    }

    public final void setMirroringPolicyParameter(boolean z) {
        if (Rune.SEC_AUDIO_VOIP_VIA_SMART_VIEW) {
            if (this.mIsPresentationMode || this.mIsDLNAEnabled) {
                z = false;
            }
            if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
                ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                        "setMirroringPolicyParameter updateSmartViewState active : ",
                        "AS.ScreenSharingHelper",
                        z);
                MicModeManager.getInstance(this.mContext).updateState(8, z);
            }
            SemAudioSystem.setPolicyParameters(
                    "l_smart_view_mirroring_active=".concat(z ? "true" : "false"));
        }
    }

    public final void setSupportDisplayVolumeControl(boolean z) {
        this.mIsSupportDisplayVolumeControl = z;
        RCPManagerService$$ExternalSyntheticOutline0.m(
                "AS.ScreenSharingHelper",
                new StringBuilder("setSupportDisplayVolumeControl : supportDisplayVolumeControl="),
                this.mIsSupportDisplayVolumeControl);
    }

    public final void updateAppCasting(int i) {
        switch (i) {
            case -1002:
            case -1001:
                this.mIsAppCasting = false;
                break;
            case -1000:
                this.mIsAppCasting = true;
                break;
        }
        RCPManagerService$$ExternalSyntheticOutline0.m(
                "AS.ScreenSharingHelper",
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "updateAppCasting : deviceType=", ", mIsAppCasting="),
                this.mIsAppCasting);
    }
}
