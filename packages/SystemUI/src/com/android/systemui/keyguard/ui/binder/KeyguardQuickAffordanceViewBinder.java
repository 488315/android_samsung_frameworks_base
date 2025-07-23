package com.android.systemui.keyguard.ui.binder;

import android.os.VibrationEffect;
import android.util.Size;
import android.view.View;
import com.android.keyguard.logging.KeyguardQuickAffordancesLogger;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.animation.view.LaunchableImageView;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceHapticViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceViewBinder {
    public final FalsingManager falsingManager;
    public final KeyguardQuickAffordancesLogger logger;
    public final MSDLPlayer msdlPlayer;
    public final VibratorHelper vibratorHelper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ConfigurationBasedDimensions {
        public final Size buttonSizePx;

        public ConfigurationBasedDimensions(Size size) {
            this.buttonSizePx = size;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ConfigurationBasedDimensions) && Intrinsics.areEqual(this.buttonSizePx, ((ConfigurationBasedDimensions) obj).buttonSizePx);
        }

        public final int hashCode() {
            return this.buttonSizePx.hashCode();
        }

        public final String toString() {
            return "ConfigurationBasedDimensions(buttonSizePx=" + this.buttonSizePx + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OnClickListener implements View.OnClickListener {
        public final FalsingManager falsingManager;
        public final KeyguardQuickAffordanceViewModel viewModel;

        public OnClickListener(KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, FalsingManager falsingManager) {
            this.viewModel = keyguardQuickAffordanceViewModel;
            this.falsingManager = falsingManager;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel;
            String str;
            if (this.falsingManager.isFalseTap(1) || (str = (keyguardQuickAffordanceViewModel = this.viewModel).configKey) == null) {
                return;
            }
            Function1 function1 = keyguardQuickAffordanceViewModel.onClicked;
            Expandable.Companion.getClass();
            function1.mo779invoke(new KeyguardQuickAffordanceViewModel.OnClickedParameters(str, new Expandable$Companion$fromView$1(view), this.viewModel.slotId));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OnLongClickListener implements View.OnLongClickListener {
        public final FalsingManager falsingManager;
        public final KeyguardQuickAffordanceOnTouchListener onTouchListener;
        public final VibratorHelper vibratorHelper;
        public final KeyguardQuickAffordanceViewModel viewModel;

        public OnLongClickListener(FalsingManager falsingManager, KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, VibratorHelper vibratorHelper, KeyguardQuickAffordanceOnTouchListener keyguardQuickAffordanceOnTouchListener, MSDLPlayer mSDLPlayer) {
            this.falsingManager = falsingManager;
            this.viewModel = keyguardQuickAffordanceViewModel;
            this.vibratorHelper = vibratorHelper;
            this.onTouchListener = keyguardQuickAffordanceOnTouchListener;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(View view) {
            VibrationEffect vibrationEffect;
            FalsingManager falsingManager = this.falsingManager;
            if (falsingManager != null && falsingManager.isFalseLongTap(2)) {
                return true;
            }
            KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel = this.viewModel;
            String str = keyguardQuickAffordanceViewModel.configKey;
            if (str != null) {
                Function1 function1 = keyguardQuickAffordanceViewModel.onClicked;
                Expandable.Companion.getClass();
                function1.mo779invoke(new KeyguardQuickAffordanceViewModel.OnClickedParameters(str, new Expandable$Companion$fromView$1(view), this.viewModel.slotId));
                VibratorHelper vibratorHelper = this.vibratorHelper;
                if (vibratorHelper != null) {
                    if (this.viewModel.isActivated) {
                        KeyguardBottomAreaVibrations.INSTANCE.getClass();
                        vibrationEffect = KeyguardBottomAreaVibrations.Activated;
                    } else {
                        KeyguardBottomAreaVibrations.INSTANCE.getClass();
                        vibrationEffect = KeyguardBottomAreaVibrations.Deactivated;
                    }
                    vibratorHelper.vibrate(vibrationEffect);
                }
            }
            this.onTouchListener.cancel();
            return true;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClickUseDefaultHapticFeedback(View view) {
            return false;
        }
    }

    public KeyguardQuickAffordanceViewBinder(FalsingManager falsingManager, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, KeyguardQuickAffordancesLogger keyguardQuickAffordancesLogger, KeyguardQuickAffordanceHapticViewModel.Factory factory) {
        this.falsingManager = falsingManager;
        this.vibratorHelper = vibratorHelper;
        this.msdlPlayer = mSDLPlayer;
        this.logger = keyguardQuickAffordancesLogger;
    }

    public final KeyguardQuickAffordanceViewBinder$bind$1 bind(LaunchableImageView launchableImageView, Flow flow, Flow flow2, Function1 function1) {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new ConfigurationBasedDimensions(new Size(launchableImageView.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_width), launchableImageView.getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_fixed_height))));
        KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1 keyguardQuickAffordanceViewBinder$bind$disposableHandle$1 = new KeyguardQuickAffordanceViewBinder$bind$disposableHandle$1(flow, this, launchableImageView, function1, null, flow2, MutableStateFlow, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        return new KeyguardQuickAffordanceViewBinder$bind$1(MutableStateFlow, this, launchableImageView, RepeatWhenAttachedKt.repeatWhenAttached(launchableImageView, EmptyCoroutineContext.INSTANCE, keyguardQuickAffordanceViewBinder$bind$disposableHandle$1));
    }
}
