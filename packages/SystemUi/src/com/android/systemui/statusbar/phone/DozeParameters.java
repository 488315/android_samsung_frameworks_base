package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;
import android.util.MathUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.doze.AODParameters;
import com.android.systemui.doze.AlwaysOnDisplayPolicy;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isAodAvailable$1$callback$1;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.clockpack.PluginClockPack;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DozeParameters implements TunerService.Tunable, com.android.systemui.plugins.statusbar.DozeParameters, Dumpable, ConfigurationController.ConfigurationListener, StatusBarStateController.StateListener, FoldAodAnimationController.FoldAodAnimationStatus {
    public final AODParameters mAODParameters;
    public final AlwaysOnDisplayPolicy mAlwaysOnPolicy;
    public final AmbientDisplayConfiguration mAmbientDisplayConfiguration;
    public boolean mControlScreenOffAnimation;
    public boolean mIsQuickPickupEnabled;
    public boolean mKeyguardVisible;
    public final PowerManager mPowerManager;
    public final Resources mResources;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public final UserTracker mUserTracker;
    public static final boolean FORCE_NO_BLANKING = SystemProperties.getBoolean("debug.force_no_blanking", false);
    public static final boolean FORCE_BLANKING = SystemProperties.getBoolean("debug.force_blanking", false);
    public final Set mCallbacks = new HashSet();
    final KeyguardUpdateMonitorCallback mKeyguardVisibilityCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.phone.DozeParameters.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            DozeParameters dozeParameters = DozeParameters.this;
            dozeParameters.mKeyguardVisible = z;
            dozeParameters.updateControlScreenOff();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onShadeExpandedChanged(boolean z) {
            DozeParameters.this.updateControlScreenOff();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            DozeParameters dozeParameters = DozeParameters.this;
            dozeParameters.mIsQuickPickupEnabled = dozeParameters.mAmbientDisplayConfiguration.quickPickupSensorEnabled(((UserTrackerImpl) dozeParameters.mUserTracker).getUserId());
        }
    };

    public DozeParameters(SettingsHelper settingsHelper, Lazy lazy, LockPatternUtils lockPatternUtils, Context context, Handler handler, Resources resources, AmbientDisplayConfiguration ambientDisplayConfiguration, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, PowerManager powerManager, BatteryController batteryController, TunerService tunerService, DumpManager dumpManager, ScreenOffAnimationController screenOffAnimationController, Optional<SysUIUnfoldComponent> optional, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, StatusBarStateController statusBarStateController, UserTracker userTracker) {
        this.mResources = resources;
        this.mAmbientDisplayConfiguration = ambientDisplayConfiguration;
        this.mAlwaysOnPolicy = alwaysOnDisplayPolicy;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "DozeParameters", this);
        boolean z = true;
        this.mControlScreenOffAnimation = !getDisplayNeedsBlanking();
        this.mPowerManager = powerManager;
        boolean equals = Process.myUserHandle().equals(UserHandle.SYSTEM);
        if (!LsRune.AOD_DISABLE_CLOCK_TRANSITION && !LsRune.AOD_SAFEMODE) {
            boolean isLockScreenDisabled = lockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser());
            Log.i("DozeParameters", " mControlScreenOffAnimation=" + this.mControlScreenOffAnimation + " isLockScreenDisabled=" + isLockScreenDisabled);
            if (equals) {
                if (this.mControlScreenOffAnimation && !isLockScreenDisabled) {
                    z = false;
                }
                powerManager.setDozeAfterScreenOff(z);
            }
        } else if (this.mControlScreenOffAnimation) {
            setControlScreenOffAnimation(false);
        } else {
            powerManager.setDozeAfterScreenOff(true);
        }
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mUserTracker = userTracker;
        this.mAODParameters = new AODParameters(settingsHelper, lazy, statusBarStateController);
    }

    public final boolean canControlUnlockedScreenOff() {
        return getAlwaysOn() && !getDisplayNeedsBlanking();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("getAlwaysOn(): ");
        printWriter.println(getAlwaysOn());
        printWriter.print("getDisplayStateSupported(): ");
        Resources resources = this.mResources;
        printWriter.println(SystemProperties.getBoolean("doze.display.supported", resources.getBoolean(R.bool.doze_display_state_supported)));
        printWriter.print("getPulseDuration(): ");
        printWriter.println(getInt("doze.pulse.duration.out", R.integer.doze_pulse_duration_out) + getInt("doze.pulse.duration.visible", R.integer.doze_pulse_duration_visible) + getInt("doze.pulse.duration.in", R.integer.doze_pulse_duration_in));
        printWriter.print("getPulseInDuration(): ");
        printWriter.println(getInt("doze.pulse.duration.in", R.integer.doze_pulse_duration_in));
        printWriter.print("getPulseInVisibleDuration(): ");
        printWriter.println(getInt("doze.pulse.duration.visible", R.integer.doze_pulse_duration_visible));
        printWriter.print("getPulseOutDuration(): ");
        printWriter.println(getInt("doze.pulse.duration.out", R.integer.doze_pulse_duration_out));
        printWriter.print("getPulseOnSigMotion(): ");
        printWriter.println(SystemProperties.getBoolean("doze.pulse.sigmotion", resources.getBoolean(R.bool.doze_pulse_on_significant_motion)));
        printWriter.print("getVibrateOnSigMotion(): ");
        printWriter.println(SystemProperties.getBoolean("doze.vibrate.sigmotion", false));
        printWriter.print("getVibrateOnPickup(): ");
        printWriter.println(SystemProperties.getBoolean("doze.vibrate.pickup", false));
        printWriter.print("getProxCheckBeforePulse(): ");
        printWriter.println(SystemProperties.getBoolean("doze.pulse.proxcheck", resources.getBoolean(R.bool.doze_proximity_check_before_pulse)));
        printWriter.print("getPickupVibrationThreshold(): ");
        printWriter.println(getInt("doze.pickup.vibration.threshold", R.integer.doze_pickup_vibration_threshold));
        printWriter.print("getSelectivelyRegisterSensorsUsingProx(): ");
        printWriter.println(SystemProperties.getBoolean("doze.prox.selectively_register", resources.getBoolean(R.bool.doze_selectively_register_prox)));
        printWriter.print("isQuickPickupEnabled(): ");
        printWriter.println(this.mIsQuickPickupEnabled);
    }

    public final boolean getAlwaysOn() {
        boolean needDozeAlwaysOn;
        AODParameters aODParameters = this.mAODParameters;
        if (aODParameters == null) {
            return false;
        }
        PluginAODManager pluginAODManager = (PluginAODManager) aODParameters.mPluginAODManagerLazy.get();
        PluginAOD pluginAOD = pluginAODManager.mAODPlugin;
        if (pluginAOD != null) {
            needDozeAlwaysOn = pluginAOD.needDozeAlwaysOn();
        } else {
            PluginClockPack pluginClockPack = pluginAODManager.mClockPackPlugin;
            needDozeAlwaysOn = pluginClockPack != null ? pluginClockPack.needDozeAlwaysOn() : false;
        }
        if (!needDozeAlwaysOn) {
            needDozeAlwaysOn = aODParameters.mDozeAlwaysOn;
        }
        return needDozeAlwaysOn;
    }

    public final boolean getDisplayNeedsBlanking() {
        return FORCE_BLANKING || (!FORCE_NO_BLANKING && this.mResources.getBoolean(android.R.bool.config_defaultAdasGnssLocationEnabled));
    }

    public final int getInt(String str, int i) {
        return MathUtils.constrain(SystemProperties.getInt(str, this.mResources.getInteger(i)), 0, VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        updateControlScreenOff();
    }

    @Override // com.android.systemui.unfold.FoldAodAnimationController.FoldAodAnimationStatus
    public final void onFoldToAodAnimationChanged() {
        updateControlScreenOff();
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStatePostChange() {
        updateControlScreenOff();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        this.mAmbientDisplayConfiguration.alwaysOnEnabled(((UserTrackerImpl) this.mUserTracker).getUserId());
        if (str.equals("doze_always_on")) {
            updateControlScreenOff();
        }
        Iterator it = ((HashSet) this.mCallbacks).iterator();
        while (it.hasNext()) {
            KeyguardRepositoryImpl$isAodAvailable$1$callback$1 keyguardRepositoryImpl$isAodAvailable$1$callback$1 = (KeyguardRepositoryImpl$isAodAvailable$1$callback$1) it.next();
            keyguardRepositoryImpl$isAodAvailable$1$callback$1.getClass();
            ChannelExt channelExt = ChannelExt.INSTANCE;
            Boolean valueOf = Boolean.valueOf(keyguardRepositoryImpl$isAodAvailable$1$callback$1.this$0.dozeParameters.getAlwaysOn());
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(keyguardRepositoryImpl$isAodAvailable$1$callback$1.$$this$conflatedCallbackFlow, valueOf, "KeyguardRepositoryImpl", "updated isAodAvailable");
        }
        boolean alwaysOn = getAlwaysOn();
        Iterator it2 = ((ArrayList) this.mScreenOffAnimationController.animations).iterator();
        while (it2.hasNext()) {
            ((ScreenOffAnimation) it2.next()).onAlwaysOnChanged(alwaysOn);
        }
    }

    public final void setControlScreenOffAnimation(boolean z) {
        if (this.mControlScreenOffAnimation == z) {
            return;
        }
        this.mControlScreenOffAnimation = z;
        this.mPowerManager.setDozeAfterScreenOff(!z);
        StringBuilder sb = new StringBuilder("setDozeAfterScreenOff ");
        sb.append(!z);
        com.android.systemui.keyguard.Log.m138d("DozeParameters", sb.toString());
    }

    public final boolean shouldAnimateDozingChange() {
        List list = this.mScreenOffAnimationController.animations;
        if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                if (!((ScreenOffAnimation) it.next()).shouldAnimateDozingChange()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.android.systemui.plugins.statusbar.DozeParameters
    public final boolean shouldControlScreenOff() {
        return this.mControlScreenOffAnimation;
    }

    public final boolean shouldShowLightRevealScrim() {
        List list = this.mScreenOffAnimationController.animations;
        if (!(list instanceof Collection) || !((ArrayList) list).isEmpty()) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                if (((ScreenOffAnimation) it.next()).shouldPlayAnimation()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void updateControlScreenOff() {
        if (getDisplayNeedsBlanking()) {
            return;
        }
        setControlScreenOffAnimation(getAlwaysOn() && (this.mKeyguardVisible || this.mUnlockedScreenOffAnimationController.shouldPlayUnlockedScreenOffAnimation()));
    }
}
