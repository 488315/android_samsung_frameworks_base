package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.wallpaper.WallpaperUtils;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

public final class FlashlightQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Context context;
    public final FlashlightController flashlightController;
    public final Flow lockScreenState;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract class FlashlightState {

        public final class OffAvailable extends FlashlightState {
            public static final OffAvailable INSTANCE = new OffAvailable();

            private OffAvailable() {
                super(null);
            }

            public final KeyguardQuickAffordanceConfig.LockScreenState toLockScreenState() {
                KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
                Intrinsics.checkNotNull(keyguardShortcutManager);
                Drawable convertTaskDrawable = keyguardShortcutManager.convertTaskDrawable(keyguardShortcutManager.context.getDrawable(R.drawable.fg_flash_off), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), false, false);
                Intrinsics.checkNotNull(convertTaskDrawable);
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(convertTaskDrawable, new ContentDescription.Resource(R.string.quick_settings_flashlight_label)), ActivationState.Inactive.INSTANCE);
            }
        }

        public final class On extends FlashlightState {
            public static final On INSTANCE = new On();

            private On() {
                super(null);
            }

            public final KeyguardQuickAffordanceConfig.LockScreenState toLockScreenState() {
                KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class);
                Intrinsics.checkNotNull(keyguardShortcutManager);
                Drawable convertTaskDrawable = keyguardShortcutManager.convertTaskDrawable(keyguardShortcutManager.context.getDrawable(R.drawable.fg_flash_on), WallpaperUtils.isWhiteKeyguardWallpaper("navibar"), true, false);
                Intrinsics.checkNotNull(convertTaskDrawable);
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(convertTaskDrawable, new ContentDescription.Resource(R.string.quick_settings_flashlight_label)), ActivationState.Active.INSTANCE);
            }
        }

        public final class Unavailable extends FlashlightState {
            public static final Unavailable INSTANCE = new Unavailable();

            private Unavailable() {
                super(null);
            }
        }

        private FlashlightState() {
        }

        public /* synthetic */ FlashlightState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public FlashlightQuickAffordanceConfig(Context context, FlashlightController flashlightController) {
        this.context = context;
        this.flashlightController = flashlightController;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        FlashlightQuickAffordanceConfig$lockScreenState$1 flashlightQuickAffordanceConfig$lockScreenState$1 = new FlashlightQuickAffordanceConfig$lockScreenState$1(this, null);
        conflatedCallbackFlow.getClass();
        this.lockScreenState = FlowConflatedKt.conflatedCallbackFlow(flashlightQuickAffordanceConfig$lockScreenState$1);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Drawable getDrawable() {
        return this.context.getDrawable(getPickerIconResourceId());
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return PluginLockShortcutTask.FLASH_LIGHT_TASK;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Drawable getPanelIconTransitionDrawable(boolean z) {
        return this.context.getDrawable(z ? R.drawable.fg_flash_off : R.drawable.fg_flash_on);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return ((FlashlightControllerImpl) this.flashlightController).isEnabled() ? R.drawable.fg_flash_on : R.drawable.fg_flash_off;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Object getPickerScreenState(Continuation continuation) {
        return ((FlashlightControllerImpl) this.flashlightController).isAvailable() ? new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null) : KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final boolean isAvailable() {
        return ((FlashlightControllerImpl) this.flashlightController).mHasFlashlight;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final boolean isTaskEnabled() {
        return ((FlashlightControllerImpl) this.flashlightController).isEnabled();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        KeyguardBatteryStatus keyguardBatteryStatus = ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).getKeyguardBatteryStatus();
        int i = keyguardBatteryStatus.level;
        boolean z = false;
        FlashlightController flashlightController = this.flashlightController;
        if (i > 5 || keyguardBatteryStatus.isPluggedIn() || ((FlashlightControllerImpl) flashlightController).isEnabled()) {
            FlashlightControllerImpl flashlightControllerImpl = (FlashlightControllerImpl) flashlightController;
            if (flashlightControllerImpl.isAvailable() && !flashlightControllerImpl.isEnabled()) {
                z = true;
            }
            flashlightControllerImpl.setFlashlight(z);
        } else {
            Context context = this.context;
            Toast.makeText(context, context.getString(R.string.flash_light_disabled_by_low_battery), 0).show();
        }
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.quick_settings_flashlight_label);
    }
}
