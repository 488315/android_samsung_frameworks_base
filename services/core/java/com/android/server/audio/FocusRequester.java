package com.android.server.audio;

import android.media.AudioAttributes;
import android.media.AudioFocusInfo;
import android.media.IAudioFocusDispatcher;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class FocusRequester {
    public final AudioAttributes mAttributes;
    public final int mCallingUid;
    public final String mClientId;
    public MediaFocusControl.AudioFocusDeathHandler mDeathHandler;
    public final MediaFocusControl mFocusController;
    public IAudioFocusDispatcher mFocusDispatcher;
    public final int mFocusGainRequest;
    public final int mGrantFlags;
    public final String mPackageName;
    public final int mSdkTarget;
    public final IBinder mSourceRef;
    public int mDevice = 0;
    public int mFocusLossReceived = 0;
    public boolean mFocusLossWasNotified = true;
    public boolean mFocusLossFadeLimbo = false;

    public FocusRequester(
            AudioAttributes audioAttributes,
            int i,
            int i2,
            IAudioFocusDispatcher iAudioFocusDispatcher,
            IBinder iBinder,
            String str,
            MediaFocusControl.AudioFocusDeathHandler audioFocusDeathHandler,
            String str2,
            int i3,
            MediaFocusControl mediaFocusControl,
            int i4) {
        this.mAttributes = audioAttributes;
        this.mFocusDispatcher = iAudioFocusDispatcher;
        this.mSourceRef = iBinder;
        this.mClientId = str;
        this.mDeathHandler = audioFocusDeathHandler;
        this.mPackageName = str2;
        this.mCallingUid = i3;
        this.mFocusGainRequest = i;
        this.mGrantFlags = i2;
        this.mFocusController = mediaFocusControl;
        this.mSdkTarget = i4;
    }

    public FocusRequester(
            AudioFocusInfo audioFocusInfo,
            IAudioFocusDispatcher iAudioFocusDispatcher,
            IBinder iBinder,
            MediaFocusControl.AudioFocusDeathHandler audioFocusDeathHandler,
            MediaFocusControl mediaFocusControl) {
        this.mAttributes = audioFocusInfo.getAttributes();
        this.mClientId = audioFocusInfo.getClientId();
        this.mPackageName = audioFocusInfo.getPackageName();
        this.mCallingUid = audioFocusInfo.getClientUid();
        this.mFocusGainRequest = audioFocusInfo.getGainRequest();
        this.mGrantFlags = audioFocusInfo.getFlags();
        this.mSdkTarget = audioFocusInfo.getSdkTarget();
        this.mFocusDispatcher = iAudioFocusDispatcher;
        this.mSourceRef = iBinder;
        this.mDeathHandler = audioFocusDeathHandler;
        this.mFocusController = mediaFocusControl;
    }

    public static String focusChangeToString(int i) {
        switch (i) {
            case -3:
                return "LOSS_TRANSIENT_CAN_DUCK";
            case -2:
                return "LOSS_TRANSIENT";
            case -1:
                return "LOSS";
            case 0:
                return "none";
            case 1:
                return "GAIN";
            case 2:
                return "GAIN_TRANSIENT";
            case 3:
                return "GAIN_TRANSIENT_MAY_DUCK";
            case 4:
                return "GAIN_TRANSIENT_EXCLUSIVE";
            default:
                return BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        i, "[invalid focus change", "]");
        }
    }

    public final int dispatchFocusChange(int i) {
        int i2;
        String str = this.mClientId;
        IAudioFocusDispatcher iAudioFocusDispatcher = this.mFocusDispatcher;
        if (iAudioFocusDispatcher == null) {
            Log.e("FocusRequester", "dispatchFocusChange: no focus dispatcher");
            return 0;
        }
        if (i == 0) {
            Log.v("FocusRequester", "dispatchFocusChange: AUDIOFOCUS_NONE");
            return 0;
        }
        if ((i == 3 || i == 4 || i == 2 || i == 1) && (i2 = this.mFocusGainRequest) != i) {
            Log.w("FocusRequester", "focus gain was requested with " + i2 + ", dispatching " + i);
        } else if (i == -3 || i == -2 || i == -1) {
            this.mFocusLossReceived = i;
        }
        try {
            iAudioFocusDispatcher.dispatchAudioFocusChange(i, str);
            return 1;
        } catch (RemoteException e) {
            Log.e(
                    "FocusRequester",
                    "dispatchFocusChange: error talking to focus listener " + str,
                    e);
            return 0;
        }
    }

    public final int dispatchFocusChangeWithFadeLocked(int i, List list) {
        int i2 = 0;
        MediaFocusControl mediaFocusControl = this.mFocusController;
        if (i == 3 || i == 4 || i == 2 || i == 1) {
            this.mFocusLossFadeLimbo = false;
            mediaFocusControl.restoreVShapedPlayers(this);
        } else if (i == -1 && mediaFocusControl.shouldEnforceFade()) {
            while (true) {
                ArrayList arrayList = (ArrayList) list;
                if (i2 >= arrayList.size()) {
                    break;
                }
                if (mediaFocusControl.mFocusEnforcer.fadeOutPlayers(
                        (FocusRequester) arrayList.get(i2), this)) {
                    this.mFocusLossFadeLimbo = true;
                    Log.v(
                            "MediaFocusControl",
                            "postDelayedLossAfterFade loser=" + this.mPackageName);
                    MediaFocusControl.AnonymousClass4 anonymousClass4 =
                            mediaFocusControl.mFocusHandler;
                    anonymousClass4.sendMessageDelayed(anonymousClass4.obtainMessage(1, this), 0L);
                    return 2;
                }
                i2++;
            }
        }
        return dispatchFocusChange(i);
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder sb = new StringBuilder("  source:");
        sb.append(this.mSourceRef);
        sb.append(" -- pack: ");
        sb.append(this.mPackageName);
        sb.append(" -- client: ");
        sb.append(this.mClientId);
        sb.append(" -- gain: ");
        sb.append(focusChangeToString(this.mFocusGainRequest));
        sb.append(" -- flags: ");
        String str = new String();
        int i = this.mGrantFlags;
        if ((i & 1) != 0) {
            str = str.concat("DELAY_OK");
        }
        if ((i & 4) != 0) {
            if (!str.isEmpty()) {
                str = str.concat("|");
            }
            str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "LOCK");
        }
        if ((i & 2) != 0) {
            if (!str.isEmpty()) {
                str = str.concat("|");
            }
            str =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, "PAUSES_ON_DUCKABLE_LOSS");
        }
        sb.append(str);
        sb.append(" -- loss: ");
        sb.append(focusChangeToString(this.mFocusLossReceived));
        sb.append(" -- notified: ");
        sb.append(this.mFocusLossWasNotified);
        sb.append(" -- limbo");
        sb.append(this.mFocusLossFadeLimbo);
        sb.append(" -- uid: ");
        sb.append(this.mCallingUid);
        sb.append(" -- attr: ");
        sb.append(this.mAttributes);
        sb.append(" -- device: ");
        BatteryService$$ExternalSyntheticOutline0.m(this.mDevice, sb, " -- sdk:");
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(sb, this.mSdkTarget, printWriter);
    }

    public final void finalize() {
        release();
        super.finalize();
    }

    public final boolean frameworkHandleFocusLoss(int i, FocusRequester focusRequester, boolean z) {
        int i2 = focusRequester.mCallingUid;
        int i3 = this.mCallingUid;
        if (i2 == i3) {
            return false;
        }
        MediaFocusControl mediaFocusControl = this.mFocusController;
        if (i != -3) {
            if (i == -1) {
                MediaFocusControl.AnonymousClass4 anonymousClass4 = mediaFocusControl.mFocusHandler;
                anonymousClass4.sendMessageDelayed(
                        anonymousClass4.obtainMessage(
                                2, new MediaFocusControl.ForgetFadeUidInfo(i3)),
                        mediaFocusControl.getFadeInDelayForOffendersMillis(this.mAttributes));
            }
            return false;
        }
        if (!z && (2 & this.mGrantFlags) != 0) {
            Log.v("FocusRequester", "not ducking uid " + i3 + " - flags");
            return false;
        }
        if (z || this.mSdkTarget > 25) {
            return mediaFocusControl.mFocusEnforcer.duckPlayers(focusRequester, this, z);
        }
        Log.v("FocusRequester", "not ducking uid " + i3 + " - old SDK");
        return false;
    }

    public final void handleFocusGain() {
        MediaFocusControl mediaFocusControl = this.mFocusController;
        try {
            this.mFocusLossReceived = 0;
            this.mFocusLossFadeLimbo = false;
            mediaFocusControl.notifyExtPolicyFocusGrant_syncAf(toAudioFocusInfo(), 1);
            IAudioFocusDispatcher iAudioFocusDispatcher = this.mFocusDispatcher;
            if (iAudioFocusDispatcher != null && this.mFocusLossWasNotified) {
                iAudioFocusDispatcher.dispatchAudioFocusChange(1, this.mClientId);
            }
            mediaFocusControl.restoreVShapedPlayers(this);
        } catch (RemoteException e) {
            Log.e("FocusRequester", "Failure to signal gain of audio focus due to: ", e);
        }
    }

    public final void handleFocusLoss(int i, FocusRequester focusRequester, boolean z) {
        AudioService audioService;
        MediaFocusControl mediaFocusControl = this.mFocusController;
        try {
            if (i != this.mFocusLossReceived) {
                this.mFocusLossReceived = i;
                this.mFocusLossWasNotified = false;
                if (!mediaFocusControl.mNotifyFocusOwnerOnDuck
                        && i == -3
                        && (this.mGrantFlags & 2) == 0) {
                    mediaFocusControl.notifyExtPolicyFocusLoss_syncAf(toAudioFocusInfo(), false);
                    return;
                }
                boolean z2 = true;
                if (i != -3
                        || z
                        || focusRequester == null
                        || focusRequester.mAttributes.getUsage() != 12
                        || (audioService = mediaFocusControl.mAudioService) == null
                        || !audioService.isIgnoreDucking()) {
                    if (focusRequester != null
                            ? frameworkHandleFocusLoss(i, focusRequester, z)
                            : false) {
                        mediaFocusControl.notifyExtPolicyFocusLoss_syncAf(
                                toAudioFocusInfo(), false);
                        return;
                    }
                    IAudioFocusDispatcher iAudioFocusDispatcher = this.mFocusDispatcher;
                    if (iAudioFocusDispatcher != null) {
                        mediaFocusControl.notifyExtPolicyFocusLoss_syncAf(toAudioFocusInfo(), true);
                        this.mFocusLossWasNotified = true;
                        iAudioFocusDispatcher.dispatchAudioFocusChange(
                                this.mFocusLossReceived, this.mClientId);
                        return;
                    }
                    return;
                }
                StringBuilder sb = new StringBuilder("not dispatching - ConnectedAndroidAuto : ");
                AudioService audioService2 = mediaFocusControl.mAudioService;
                sb.append(audioService2 != null && audioService2.isConnectedAndroidAuto());
                sb.append(" , forceDuck : ");
                sb.append(z);
                sb.append(" , IgnoreDucking : ");
                AudioService audioService3 = mediaFocusControl.mAudioService;
                if (audioService3 == null || !audioService3.isIgnoreDucking()) {
                    z2 = false;
                }
                sb.append(z2);
                sb.append(" , winner usage : ");
                sb.append(focusRequester.mAttributes.getUsage());
                Log.d("FocusRequester", sb.toString());
                mediaFocusControl.notifyExtPolicyFocusLoss_syncAf(toAudioFocusInfo(), false);
            }
        } catch (RemoteException e) {
            Log.e("FocusRequester", "Failure to signal loss of audio focus due to:", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x002d, code lost:

       if (r7 != 0) goto L24;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0023, code lost:

       if (r7 != 0) goto L19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0019, code lost:

       if (r7 != 0) goto L14;
    */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x000e, code lost:

       if (r11 != 4) goto L24;
    */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x002f, code lost:

       com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(r11, "focusLossForGainRequest() for invalid focus request ", "FocusRequester");
       r6 = 0;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleFocusLossFromGain(
            int r11, com.android.server.audio.FocusRequester r12, boolean r13) {
        /*
            r10 = this;
            r0 = 0
            r1 = 4
            r2 = 3
            r3 = -2
            r4 = 1
            r5 = -1
            r6 = -3
            if (r11 == r4) goto L11
            r7 = 2
            if (r11 == r7) goto L1b
            if (r11 == r2) goto L25
            if (r11 == r1) goto L1b
            goto L2f
        L11:
            int r7 = r10.mFocusLossReceived
            if (r7 == r6) goto L39
            if (r7 == r3) goto L39
            if (r7 == r5) goto L39
            if (r7 == 0) goto L39
        L1b:
            int r7 = r10.mFocusLossReceived
            if (r7 == r6) goto L3b
            if (r7 == r3) goto L3b
            if (r7 == r5) goto L39
            if (r7 == 0) goto L3b
        L25:
            int r7 = r10.mFocusLossReceived
            if (r7 == r6) goto L3c
            if (r7 == r3) goto L3b
            if (r7 == r5) goto L39
            if (r7 == 0) goto L3c
        L2f:
            java.lang.String r6 = "focusLossForGainRequest() for invalid focus request "
            java.lang.String r7 = "FocusRequester"
            com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(r11, r6, r7)
            r6 = r0
            goto L3c
        L39:
            r6 = r5
            goto L3c
        L3b:
            r6 = r3
        L3c:
            if (r6 != r5) goto L83
            com.android.server.audio.MediaFocusControl r11 = r10.mFocusController
            com.samsung.android.server.audio.AudioSettingsHelper r7 = r11.mAudioSettingsHelper
            java.lang.String r8 = "delay_loss_audio_focus"
            java.lang.String r9 = r10.mPackageName
            boolean r7 = r7.checkAppCategory(r9, r8)
            if (r7 == 0) goto L83
            if (r12 == 0) goto L83
            java.lang.String r7 = "com.android.bluetooth"
            java.lang.String r8 = r12.mPackageName
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L83
            java.lang.String r7 = "com.samsung.android.mcfds"
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L83
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "postDelayedForLossAudioFocus: clientId = "
            r6.<init>(r7)
            java.lang.String r7 = r10.mClientId
            java.lang.String r8 = "MediaFocusControl"
            com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r6, r7, r8)
            com.android.server.audio.MediaFocusControl$4 r6 = r11.mFocusHandler
            r6.removeMessages(r1, r10)
            com.android.server.audio.MediaFocusControl$4 r11 = r11.mFocusHandler
            android.os.Message r1 = r11.obtainMessage(r2, r10)
            r6 = 15000(0x3a98, double:7.411E-320)
            r11.sendMessageDelayed(r1, r6)
            goto L84
        L83:
            r3 = r6
        L84:
            r10.handleFocusLoss(r3, r12, r13)
            if (r3 != r5) goto L8a
            r0 = r4
        L8a:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.FocusRequester.handleFocusLossFromGain(int,"
                    + " com.android.server.audio.FocusRequester, boolean):boolean");
    }

    public final boolean hasSameBinder(IBinder iBinder) {
        IBinder iBinder2 = this.mSourceRef;
        return iBinder2 != null && iBinder2.equals(iBinder);
    }

    public final boolean hasSameClient(String str) {
        return this.mClientId.compareTo(str) == 0;
    }

    public final boolean hasSameUid(int i) {
        return this.mCallingUid == i;
    }

    public final void release() {
        IBinder iBinder = this.mSourceRef;
        MediaFocusControl.AudioFocusDeathHandler audioFocusDeathHandler = this.mDeathHandler;
        if (iBinder != null && audioFocusDeathHandler != null) {
            try {
                iBinder.unlinkToDeath(audioFocusDeathHandler, 0);
            } catch (NoSuchElementException unused) {
            }
        }
        this.mDeathHandler = null;
        this.mFocusDispatcher = null;
    }

    public final AudioFocusInfo toAudioFocusInfo() {
        return new AudioFocusInfo(
                this.mAttributes,
                this.mCallingUid,
                this.mClientId,
                this.mPackageName,
                this.mFocusGainRequest,
                this.mFocusLossReceived,
                this.mGrantFlags,
                this.mSdkTarget);
    }
}
