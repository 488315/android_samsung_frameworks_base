package com.samsung.android.server.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioSystem;
import android.util.Log;
import com.android.server.LocalServices;
import com.android.server.desktopmode.DesktopModeSettings;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import com.samsung.android.media.SemAudioSystem;

/* loaded from: classes2.dex */
public class DesktopModeHelper {
    public static DesktopModeHelper sInstance;
    public final Context mContext;
    public SemDesktopModeManager.DesktopModeListener mDesktopModeListener = new SemDesktopModeManager.DesktopModeListener() { // from class: com.samsung.android.server.audio.DesktopModeHelper.1
        public void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
            int i = semDesktopModeState.enabled;
            if (i == 1) {
                return;
            }
            boolean z = i == 4;
            boolean settings = DesktopModeSettings.getSettings(DesktopModeHelper.this.mResolver, "audio_output_to_display", false);
            Log.d("AS.DesktopModeHelper", "DEX enabled : " + z + ", isAudioOutputToDisplay : " + settings);
            if (z && settings) {
                z = !z;
            }
            if (DesktopModeHelper.this.mDexState != z) {
                DesktopModeHelper.this.mDexState = z;
                DesktopModeHelper.this.mIsDesktopMode = semDesktopModeState.getDisplayType() != 0;
                DesktopModeHelper desktopModeHelper = DesktopModeHelper.this;
                desktopModeHelper.setDexPolicyParameter(desktopModeHelper.mDexState ? "dex" : "none");
            }
        }
    };
    public SemDesktopModeManager mDesktopModeManager;
    public boolean mDexConnectedState;
    public boolean mDexPadConnectedState;
    public boolean mDexState;
    public boolean mIsDesktopMode;
    public ContentResolver mResolver;

    public DesktopModeHelper(Context context) {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
    }

    public static synchronized DesktopModeHelper getInstance(Context context) {
        DesktopModeHelper desktopModeHelper;
        synchronized (DesktopModeHelper.class) {
            if (sInstance == null) {
                sInstance = new DesktopModeHelper(context);
            }
            desktopModeHelper = sInstance;
        }
        return desktopModeHelper;
    }

    public void registerListener() {
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

    public void updateDexState() {
        boolean settings = DesktopModeSettings.getSettings(this.mResolver, "audio_output_to_display", false);
        if (((DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class)).isDesktopModeAvailableEx(false, true) && !settings && !this.mDexState) {
            this.mDexState = true;
            setDexPolicyParameter("dex");
            Log.i("AS.DesktopModeHelper", "The dex mode is available. " + this.mDexState);
            return;
        }
        if (this.mDexState && isDexMirroringMode()) {
            Log.i("AS.DesktopModeHelper", "The dex mode changed to mirrored mode from tablet mode");
            setDexPolicyParameter(settings ? "none" : "dex");
        }
    }

    public final boolean isDexMirroringMode() {
        boolean equals = "dual".equals(DesktopModeSettings.getSettings(this.mResolver, "external_display_mode", "dual"));
        SemDesktopModeManager semDesktopModeManager = this.mDesktopModeManager;
        if (semDesktopModeManager == null || semDesktopModeManager.getDesktopModeState() == null) {
            return false;
        }
        int displayType = this.mDesktopModeManager.getDesktopModeState().getDisplayType();
        if (equals || displayType != 101) {
            return false;
        }
        Log.i("AS.DesktopModeHelper", "isDexMirroringMode mirroring mode.");
        return true;
    }

    public boolean isDesktopMode() {
        return this.mIsDesktopMode;
    }

    public void restoreDexState() {
        setDexPolicyParameter(this.mDexState ? "dex" : "none");
        setDexParameter("station", this.mDexConnectedState);
        setDexParameter("pad", this.mDexPadConnectedState);
    }

    public void updateDexConnectionState(int i, int i2) {
        if (i == 0 && i2 == 114) {
            this.mDexPadConnectedState = true;
            setDexParameter("pad", true);
            return;
        }
        if (i == 114 && i2 == 0) {
            this.mDexPadConnectedState = false;
            setDexParameter("pad", false);
        } else if (i == 0 && i2 == 110) {
            this.mDexConnectedState = true;
            setDexParameter("station", true);
        } else if (i == 110 && i2 == 0) {
            this.mDexConnectedState = false;
            setDexParameter("station", false);
        }
    }

    public final void setDexParameter(String str, boolean z) {
        AudioSystem.setParameters("l_dex_key;type=" + str + KnoxVpnFirewallHelper.DELIMITER + "connected=" + z);
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            Log.i("AS.DesktopModeHelper", "updateDexState to micModeManager : " + z);
            MicModeManager.getInstance(this.mContext).updateDexState(z);
        }
    }

    public final void setDexPolicyParameter(String str) {
        SemAudioSystem.setPolicyParameters("l_dex_key;path=" + str);
        if (Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI) {
            Log.i("AS.DesktopModeHelper", "updateDexState to micModeManager : " + str);
            MicModeManager.getInstance(this.mContext).updateDexState("dex".equalsIgnoreCase(str));
        }
    }
}
