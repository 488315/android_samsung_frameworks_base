package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Binder;
import android.provider.Settings;
import android.util.Log;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

import com.samsung.android.server.audio.utils.AudioUtils;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MicModeManager {
    public static ContentResolver mCr;
    public static MicModeManager sInstance;
    public boolean mIsMicModeOn;
    public final MicModeType mMicModeType;
    public static final List AVAILABLE_STREAM_TYPES = Arrays.asList(0, 2);
    public static final EventLogger sMicModeLogger = new EventLogger(30, "Mic mode history");
    public String mPackageName = "";
    public int mCurCallDevice = 2;
    public int mCurAudioMode = 0;
    public int mStates = 0;

    public MicModeManager(Context context) {
        mCr = context.getContentResolver();
        try {
            MicModeType micModeType = MicModeType.getMicModeType();
            this.mMicModeType = micModeType;
            Log.i("MicModeManager", "initMicModeType() mMicModeType : " + micModeType);
        } catch (NullPointerException unused) {
            Log.w("MicModeManager", "initMicModeType: NullPointerException");
        } catch (RuntimeException unused2) {
            Log.w("MicModeManager", "initMicModeType: RuntimeException");
        }
    }

    public static synchronized MicModeManager getInstance(Context context) {
        MicModeManager micModeManager;
        synchronized (MicModeManager.class) {
            try {
                if (sInstance == null) {
                    sInstance = new MicModeManager(context);
                }
                micModeManager = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return micModeManager;
    }

    public final boolean isMicModeSupported() {
        StringBuilder sb = new StringBuilder("isMicModeSupported() MicModeType: ");
        MicModeType micModeType = this.mMicModeType;
        sb.append(micModeType);
        sb.append(", audioMode: ");
        sb.append(this.mCurAudioMode);
        sb.append(", curDevice: ");
        sb.append(this.mCurCallDevice);
        sb.append(", translationState: ");
        sb.append(isStateEnabled(16));
        sb.append(", dexState: ");
        sb.append(isStateEnabled(4));
        sb.append(", smartViewState: ");
        sb.append(isStateEnabled(8));
        sb.append(", gameChatState: ");
        sb.append(isStateEnabled(32));
        sb.append(", wifiCallState: ");
        sb.append(isStateEnabled(1));
        sb.append(", videoCallState: ");
        sb.append(isStateEnabled(2));
        sb.append(", satelliteCallState: ");
        sb.append(isStateEnabled(64));
        Log.i("MicModeManager", sb.toString());
        if (!micModeType.isMicModeSupported(this.mCurAudioMode, this.mCurCallDevice, this.mStates)
                || isStateEnabled(16)) {
            return false;
        }
        if (this.mCurCallDevice == 2) {
            if (isStateEnabled(4)) {
                return false;
            }
            if (this.mCurAudioMode == 3
                    && !isStateEnabled(1)
                    && (isStateEnabled(8) || isStateEnabled(32))) {
                return false;
            }
        }
        if (isStateEnabled(64)) {
            return false;
        }
        Log.i("MicModeManager", "isMicModeSupported() MicMode ON");
        return true;
    }

    public final boolean isStateEnabled(int i) {
        return (this.mStates & i) > 0;
    }

    public final void setMicMode(boolean z) {
        if (!z) {
            updateMicModeSettings(0, "", false);
            return;
        }
        int i = this.mCurAudioMode;
        MicModeType micModeType = this.mMicModeType;
        if (i == 2 || (i == 3 && isStateEnabled(1))) {
            updateMicModeSettings(micModeType.getCallMicMode(), this.mPackageName, true);
        } else if (this.mCurAudioMode == 3) {
            updateMicModeSettings(micModeType.getVoipCallMicMode(), this.mPackageName, true);
        }
    }

    public final void updateAudioDevice() {
        Log.i(
                "MicModeManager",
                "updateAudioDevice() audioMode: "
                        + this.mCurAudioMode
                        + ", curDevice: "
                        + this.mCurCallDevice);
        setMicMode(isMicModeSupported());
    }

    public final void updateAudioMode(int i, String str) {
        this.mPackageName = str;
        this.mCurAudioMode = i;
        StringBuilder sb = new StringBuilder("updateAudioMode() audioMode=");
        AlarmManagerService$DeliveryTracker$$ExternalSyntheticOutline0.m(
                this.mCurAudioMode, ", caller=", str, ", curCallDevice=", sb);
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                sb, this.mCurCallDevice, "MicModeManager");
        if (this.mCurAudioMode != 0) {
            setMicMode(isMicModeSupported());
        } else {
            updateMicModeSettings(0, "", false);
            this.mPackageName = "";
        }
    }

    public final void updateMicModeSettings(int i, String str, boolean z) {
        if (this.mIsMicModeOn != z) {
            this.mIsMicModeOn = z;
            StringBuilder m =
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            i, "updateMicModeSettings is enabled ", ", effect:", ", ", z);
            m.append(this.mPackageName);
            sMicModeLogger.enqueueAndLog(0, m.toString(), "MicModeManager");
        }
        if (!z) {
            Log.i("MicModeManager", "updateMicModeSettings() disable");
            AudioUtils.setSettingsInt(mCr, "mic_mode_enable", 0);
            return;
        }
        Log.i("MicModeManager", "updateMicModeSettings() enable, effect:" + i + ", caller:" + str);
        AudioUtils.setSettingsInt(mCr, "mic_mode_enable", 1);
        AudioUtils.setSettingsInt(mCr, "mic_mode_effect", i);
        ContentResolver contentResolver = mCr;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.System.putString(contentResolver, "mic_mode_package", str);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateState(int i, boolean z) {
        int i2 = this.mStates;
        this.mStates = z ? i2 | i : i2 & (~i);
        if (i == 1) {
            AudioUtils.setSettingsInt(mCr, "mic_mode_wificall", z ? 1 : 0);
            setMicMode(isMicModeSupported());
        } else if (!isMicModeSupported()) {
            updateMicModeSettings(0, "", false);
        } else {
            if (z) {
                return;
            }
            setMicMode(true);
        }
    }
}
