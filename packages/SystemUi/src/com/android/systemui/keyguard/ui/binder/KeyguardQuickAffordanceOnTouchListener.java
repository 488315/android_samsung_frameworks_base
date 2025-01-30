package com.android.systemui.keyguard.ui.binder;

import android.graphics.PointF;
import android.os.VibrationEffect;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewPropertyAnimator;
import androidx.core.animation.CycleInterpolator;
import androidx.core.animation.ObjectAnimator;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardQuickAffordanceViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceOnTouchListener implements View.OnTouchListener {
    public static final Companion Companion = new Companion(null);
    public final FalsingManager falsingManager;
    public ViewPropertyAnimator longPressAnimator;
    public final Function1 messageDisplayer;
    public final VibratorHelper vibratorHelper;
    public final View view;
    public final KeyguardQuickAffordanceViewModel viewModel;
    public final long longPressDurationMs = ViewConfiguration.getLongPressTimeout();
    public final Lazy downDisplayCoords$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceOnTouchListener$downDisplayCoords$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new PointF();
        }
    });

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean isUsingAccurateTool$default(Companion companion, MotionEvent motionEvent) {
            companion.getClass();
            int toolType = motionEvent.getToolType(0);
            return toolType == 2 || toolType == 3;
        }
    }

    public KeyguardQuickAffordanceOnTouchListener(View view, KeyguardQuickAffordanceViewModel keyguardQuickAffordanceViewModel, Function1 function1, VibratorHelper vibratorHelper, FalsingManager falsingManager) {
        this.view = view;
        this.viewModel = keyguardQuickAffordanceViewModel;
        this.messageDisplayer = function1;
        this.vibratorHelper = vibratorHelper;
        this.falsingManager = falsingManager;
    }

    public final void cancel(Runnable runnable) {
        ViewPropertyAnimator viewPropertyAnimator = this.longPressAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        this.longPressAnimator = null;
        this.view.animate().scaleX(1.0f).scaleY(1.0f).withEndAction(runnable);
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            if (this.viewModel.configKey == null) {
                return false;
            }
            ((PointF) this.downDisplayCoords$delegate.getValue()).set(motionEvent.getRawX(), motionEvent.getRawY());
            if (Companion.isUsingAccurateTool$default(Companion, motionEvent)) {
                return false;
            }
            this.longPressAnimator = this.view.animate().scaleX(1.5f).scaleY(1.5f).setDuration(this.longPressDurationMs);
            return false;
        }
        if (actionMasked != 1) {
            if (actionMasked != 2) {
                if (actionMasked != 3) {
                    return false;
                }
                cancel(null);
                return true;
            }
            if (Companion.isUsingAccurateTool$default(Companion, motionEvent)) {
                return false;
            }
            if (MathUtils.dist(motionEvent.getRawX(), motionEvent.getRawY(), ((PointF) this.downDisplayCoords$delegate.getValue()).x, ((PointF) this.downDisplayCoords$delegate.getValue()).y) <= ViewConfiguration.getTouchSlop()) {
                return false;
            }
            cancel(null);
            return false;
        }
        if (!Companion.isUsingAccurateTool$default(Companion, motionEvent)) {
            cancel(motionEvent.getEventTime() - motionEvent.getDownTime() < this.longPressDurationMs ? new Runnable() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceOnTouchListener$onTouch$1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardQuickAffordanceOnTouchListener.this.messageDisplayer.invoke(Integer.valueOf(R.string.keyguard_affordance_press_too_short));
                    float dimensionPixelSize = KeyguardQuickAffordanceOnTouchListener.this.view.getContext().getResources().getDimensionPixelSize(R.dimen.keyguard_affordance_shake_amplitude);
                    float f = 2;
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(KeyguardQuickAffordanceOnTouchListener.this.view, (-dimensionPixelSize) / f, dimensionPixelSize / f);
                    KeyguardBottomAreaVibrations.INSTANCE.getClass();
                    ofFloat.m309setDuration(Duration.m2863getInWholeMillisecondsimpl(KeyguardBottomAreaVibrations.ShakeAnimationDuration));
                    ofFloat.setInterpolator(new CycleInterpolator(5.0f));
                    ofFloat.start();
                    VibratorHelper vibratorHelper = KeyguardQuickAffordanceOnTouchListener.this.vibratorHelper;
                    if (vibratorHelper != null) {
                        vibratorHelper.vibrate(KeyguardBottomAreaVibrations.Shake);
                    }
                }
            } : null);
            return false;
        }
        if (this.viewModel.configKey == null) {
            return false;
        }
        if (MathUtils.dist(motionEvent.getRawX(), motionEvent.getRawY(), ((PointF) this.downDisplayCoords$delegate.getValue()).x, ((PointF) this.downDisplayCoords$delegate.getValue()).y) > ViewConfiguration.getTouchSlop()) {
            return false;
        }
        FalsingManager falsingManager = this.falsingManager;
        if (!((falsingManager == null || falsingManager.isFalseTap(0)) ? false : true)) {
            return false;
        }
        final String str = this.viewModel.configKey;
        this.view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardQuickAffordanceOnTouchListener$dispatchClick$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                VibrationEffect vibrationEffect;
                KeyguardQuickAffordanceOnTouchListener keyguardQuickAffordanceOnTouchListener = KeyguardQuickAffordanceOnTouchListener.this;
                VibratorHelper vibratorHelper = keyguardQuickAffordanceOnTouchListener.vibratorHelper;
                if (vibratorHelper != null) {
                    if (keyguardQuickAffordanceOnTouchListener.viewModel.isActivated) {
                        KeyguardBottomAreaVibrations.INSTANCE.getClass();
                        vibrationEffect = KeyguardBottomAreaVibrations.Activated;
                    } else {
                        KeyguardBottomAreaVibrations.INSTANCE.getClass();
                        vibrationEffect = KeyguardBottomAreaVibrations.Deactivated;
                    }
                    vibratorHelper.vibrate(vibrationEffect);
                }
                KeyguardQuickAffordanceOnTouchListener keyguardQuickAffordanceOnTouchListener2 = KeyguardQuickAffordanceOnTouchListener.this;
                Function1 function1 = keyguardQuickAffordanceOnTouchListener2.viewModel.onClicked;
                String str2 = str;
                Expandable.Companion companion = Expandable.Companion;
                View view3 = keyguardQuickAffordanceOnTouchListener2.view;
                companion.getClass();
                function1.invoke(new KeyguardQuickAffordanceViewModel.OnClickedParameters(str2, new Expandable$Companion$fromView$1(view3), KeyguardQuickAffordanceOnTouchListener.this.viewModel.slotId));
            }
        });
        this.view.performClick();
        this.view.setOnClickListener(null);
        return false;
    }
}
