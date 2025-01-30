package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FlashlightQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Context context;
    public final FlashlightController flashlightController;
    public final Flow lockScreenState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class FlashlightState {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class OffAvailable extends FlashlightState {
            public static final OffAvailable INSTANCE = new OffAvailable();

            private OffAvailable() {
                super(null);
            }

            public final KeyguardQuickAffordanceConfig.LockScreenState toLockScreenState() {
                KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class);
                Drawable blendingFgIcon = keyguardShortcutManager.getBlendingFgIcon(null, keyguardShortcutManager.mContext.getResources().getDrawable(R.drawable.fg_flash_off), true, false);
                int i = keyguardShortcutManager.mIconSize;
                BitmapDrawable drawableToScaledBitmapDrawable = keyguardShortcutManager.drawableToScaledBitmapDrawable(blendingFgIcon, i, i);
                Intrinsics.checkNotNull(drawableToScaledBitmapDrawable);
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawableToScaledBitmapDrawable, new ContentDescription.Resource(R.string.quick_settings_flashlight_label)), ActivationState.Inactive.INSTANCE);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$FlashlightState$On */
        public final class C1554On extends FlashlightState {
            public static final C1554On INSTANCE = new C1554On();

            private C1554On() {
                super(null);
            }

            public final KeyguardQuickAffordanceConfig.LockScreenState toLockScreenState() {
                KeyguardShortcutManager keyguardShortcutManager = (KeyguardShortcutManager) Dependency.get(KeyguardShortcutManager.class);
                Drawable blendingFgIcon = keyguardShortcutManager.getBlendingFgIcon(null, keyguardShortcutManager.mContext.getResources().getDrawable(R.drawable.fg_flash_on), true, true);
                int i = keyguardShortcutManager.mIconSize;
                BitmapDrawable drawableToScaledBitmapDrawable = keyguardShortcutManager.drawableToScaledBitmapDrawable(blendingFgIcon, i, i);
                Intrinsics.checkNotNull(drawableToScaledBitmapDrawable);
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Loaded(drawableToScaledBitmapDrawable, new ContentDescription.Resource(R.string.quick_settings_flashlight_label)), ActivationState.Active.INSTANCE);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.lockScreenState = ConflatedCallbackFlow.conflatedCallbackFlow(flashlightQuickAffordanceConfig$lockScreenState$1);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return "Flashlight";
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return this.lockScreenState;
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
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        KeyguardBatteryStatus keyguardBatteryStatus = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getKeyguardBatteryStatus();
        int i = keyguardBatteryStatus.level;
        boolean z = false;
        FlashlightController flashlightController = this.flashlightController;
        if (i > 5 || keyguardBatteryStatus.isPluggedIn() || ((FlashlightControllerImpl) flashlightController).isEnabled()) {
            if (((FlashlightControllerImpl) flashlightController).isAvailable() && !((FlashlightControllerImpl) flashlightController).isEnabled()) {
                z = true;
            }
            ((FlashlightControllerImpl) flashlightController).setFlashlight(z);
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
