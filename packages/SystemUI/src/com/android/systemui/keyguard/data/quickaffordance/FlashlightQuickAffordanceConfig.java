package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FlashlightQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Context context;
    public final FlashlightController flashlightController;
    public final Flow lockScreenState = FlowConflatedKt.conflatedCallbackFlow(new FlashlightQuickAffordanceConfig$lockScreenState$1(this, null));

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class FlashlightState {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class OffAvailable extends FlashlightState {
            public static final OffAvailable INSTANCE = new OffAvailable();

            private OffAvailable() {
                super(null);
            }

            public final KeyguardQuickAffordanceConfig.LockScreenState toLockScreenState() {
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R.drawable.qs_flashlight_icon_off, new ContentDescription.Resource(R.string.quick_settings_flashlight_label)), ActivationState.Inactive.INSTANCE);
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class On extends FlashlightState {
            public static final On INSTANCE = new On();

            private On() {
                super(null);
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Unavailable extends FlashlightState {
            public static final Unavailable INSTANCE = new Unavailable();

            private Unavailable() {
                super(null);
            }
        }

        public /* synthetic */ FlashlightState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private FlashlightState() {
        }
    }

    static {
        new Companion(null);
    }

    public FlashlightQuickAffordanceConfig(Context context, FlashlightController flashlightController) {
        this.context = context;
        this.flashlightController = flashlightController;
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
    public final int getPickerIconResourceId() {
        return R.drawable.ic_flashlight_off;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Object getPickerScreenState(Continuation continuation) {
        return ((FlashlightControllerImpl) this.flashlightController).isAvailable() ? new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null) : KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        FlashlightController flashlightController = this.flashlightController;
        ((FlashlightControllerImpl) flashlightController).setFlashlight(((FlashlightControllerImpl) flashlightController).isAvailable() && !((FlashlightControllerImpl) flashlightController).isEnabled());
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(false);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.quick_settings_flashlight_label);
    }
}
