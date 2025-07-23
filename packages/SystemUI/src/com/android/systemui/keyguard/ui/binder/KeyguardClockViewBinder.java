package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.shared.model.ClockSize;
import com.android.systemui.keyguard.ui.view.layout.sections.AodBurnInLayer;
import com.android.systemui.keyguard.ui.view.layout.sections.ClockSection;
import com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.util.kotlin.DisposableHandles;
import java.util.Iterator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardClockViewBinder {
    public static final KeyguardClockViewBinder INSTANCE = new KeyguardClockViewBinder();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockSize.values().length];
            try {
                iArr[ClockSize.LARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockSize.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        Reflection.getOrCreateKotlinClass(KeyguardClockViewBinder.class).getSimpleName().getClass();
    }

    private KeyguardClockViewBinder() {
    }

    public static final DisposableHandles bind(ClockSection clockSection, ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, KeyguardClockInteractor keyguardClockInteractor, KeyguardBlueprintInteractor keyguardBlueprintInteractor, KeyguardRootViewModel keyguardRootViewModel, AodBurnInViewModel aodBurnInViewModel) {
        DisposableHandles disposableHandles = new DisposableHandles();
        KeyguardClockViewBinder$bind$1 keyguardClockViewBinder$bind$1 = new KeyguardClockViewBinder$bind$1(keyguardClockInteractor, constraintLayout, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, emptyCoroutineContext, keyguardClockViewBinder$bind$1));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, emptyCoroutineContext, new KeyguardClockViewBinder$bind$2(keyguardClockViewModel, constraintLayout, clockSection, keyguardBlueprintInteractor, keyguardRootViewModel, aodBurnInViewModel, null)));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, emptyCoroutineContext, new KeyguardClockViewBinder$bind$3(keyguardClockViewModel, null)));
        return disposableHandles;
    }

    public static void cleanupClockViews(ClockController clockController, ConstraintLayout constraintLayout, AodBurnInLayer aodBurnInLayer) {
        if (clockController != null) {
            for (View view : clockController.getSmallClock().getLayout().getViews()) {
                if (aodBurnInLayer != null) {
                    aodBurnInLayer.removeView(view);
                }
                constraintLayout.removeView(view);
            }
            Iterator<T> it = clockController.getLargeClock().getLayout().getViews().iterator();
            while (it.hasNext()) {
                constraintLayout.removeView((View) it.next());
            }
        }
    }

    public final void addClockViews(ClockController clockController, ConstraintLayout constraintLayout) {
        if (clockController != null) {
            for (View view : clockController.getSmallClock().getLayout().getViews()) {
                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                }
                constraintLayout.addView(view);
                Unit unit = Unit.INSTANCE;
                view.setVisibility(4);
            }
            for (View view2 : clockController.getLargeClock().getLayout().getViews()) {
                if (view2.getParent() != null) {
                    ((ViewGroup) view2.getParent()).removeView(view2);
                }
                constraintLayout.addView(view2);
                Unit unit2 = Unit.INSTANCE;
                view2.setVisibility(4);
            }
        }
    }

    public final void updateBurnInLayer(ConstraintLayout constraintLayout, KeyguardClockViewModel keyguardClockViewModel, ClockSize clockSize) {
        AodBurnInLayer aodBurnInLayer = keyguardClockViewModel.burnInLayer;
        ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
        if (clockController != null) {
            int i = WhenMappings.$EnumSwitchMapping$0[clockSize.ordinal()];
            if (i == 1) {
                for (View view : clockController.getSmallClock().getLayout().getViews()) {
                    if (aodBurnInLayer != null) {
                        aodBurnInLayer.removeView(view);
                    }
                }
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                for (View view2 : clockController.getSmallClock().getLayout().getViews()) {
                    if (aodBurnInLayer != null) {
                        aodBurnInLayer.addView(view2);
                    }
                }
            }
        }
        AodBurnInLayer aodBurnInLayer2 = keyguardClockViewModel.burnInLayer;
        if (aodBurnInLayer2 != null) {
            aodBurnInLayer2.updatePostLayout(constraintLayout);
        }
    }
}
