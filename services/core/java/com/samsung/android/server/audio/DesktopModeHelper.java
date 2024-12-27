package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioSystem;
import android.util.Log;

import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.DesktopModeSettings;

import com.samsung.android.audio.Rune;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.media.SemAudioSystem;

public final class DesktopModeHelper {
    public static DesktopModeHelper sInstance;
    public final Context mContext;
    public final AnonymousClass1 mDesktopModeListener =
            new SemDesktopModeManager
                    .DesktopModeListener() { // from class:
                                             // com.samsung.android.server.audio.DesktopModeHelper.1
                public final void onDesktopModeStateChanged(
                        SemDesktopModeState semDesktopModeState) {
                    int i = semDesktopModeState.enabled;
                    if (i == 1) {
                        return;
                    }
                    boolean z = i == 4;
                    boolean settingsAsUser =
                            DesktopModeSettings.getSettingsAsUser(
                                    DesktopModeHelper.this.mResolver,
                                    "audio_output_to_display",
                                    false,
                                    DesktopModeSettings.sCurrentUserId);
                    Log.d(
                            "AS.DesktopModeHelper",
                            "DEX enabled : " + z + ", isAudioOutputToDisplay : " + settingsAsUser);
                    if (z && settingsAsUser) {
                        z = !z;
                    }
                    DesktopModeHelper desktopModeHelper = DesktopModeHelper.this;
                    if (desktopModeHelper.mDexState != z) {
                        desktopModeHelper.mDexState = z;
                        desktopModeHelper.mIsDesktopMode =
                                semDesktopModeState.getDisplayType() != 0;
                        DesktopModeHelper desktopModeHelper2 = DesktopModeHelper.this;
                        desktopModeHelper2.setDexPolicyParameter(
                                desktopModeHelper2.mDexState ? "dex" : "none");
                    }
                }
            };
    public final SemDesktopModeManager mDesktopModeManager;
    public boolean mDexConnectedState;
    public boolean mDexPadConnectedState;
    public boolean mDexState;
    public boolean mIsDesktopMode;
    public final ContentResolver mResolver;

    public DesktopModeHelper(Context context) {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
    }

    public static synchronized DesktopModeHelper getInstance(Context context) {
        DesktopModeHelper desktopModeHelper;
        synchronized (DesktopModeHelper.class) {
            try {
                if (sInstance == null) {
                    sInstance = new DesktopModeHelper(context);
                }
                desktopModeHelper = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return desktopModeHelper;
    }

    public final void registerListener() {
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager != null) {
            semDesktopModeManager.registerListener(this.mDesktopModeListener);
            Log.d("AS.DesktopModeHelper", "DEX registration is successful");
        } else {
            Log.d("AS.DesktopModeHelper", "DEX registration is failed");
        }
        this.mDexState = false;
        this.mDexConnectedState = false;
        this.mDexPadConnectedState = false;
    }

    public final void setDexParameter(String str, boolean z) {
        AudioSystem.setParameters("l_dex_key;type=" + str + ";connected=" + z);
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "updateDexState to micModeManager : ", "AS.DesktopModeHelper", z);
            MicModeManager.getInstance(this.mContext).updateState(4, z);
        }
    }

    public final void setDexPolicyParameter(String str) {
        SemAudioSystem.setPolicyParameters("l_dex_key;path=".concat(str));
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            Log.i("AS.DesktopModeHelper", "updateDexState to micModeManager : ".concat(str));
            MicModeManager.getInstance(this.mContext).updateState(4, "dex".equalsIgnoreCase(str));
        }
    }
}
