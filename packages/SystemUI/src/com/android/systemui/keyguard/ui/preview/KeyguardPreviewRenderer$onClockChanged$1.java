package com.android.systemui.keyguard.ui.preview;

import android.widget.FrameLayout;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.systemui.Flags;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockFaceEvents;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class KeyguardPreviewRenderer$onClockChanged$1 extends SuspendLambda implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ KeyguardPreviewRenderer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardPreviewRenderer$onClockChanged$1(KeyguardPreviewRenderer keyguardPreviewRenderer, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardPreviewRenderer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardPreviewRenderer$onClockChanged$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardPreviewRenderer$onClockChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ClockController clockController;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ClockController createCurrentClock = this.this$0.clockRegistry.createCurrentClock();
            this.this$0.clockController.setClock(createCurrentClock);
            KeyguardPreviewRenderer keyguardPreviewRenderer = this.this$0;
            this.L$0 = createCurrentClock;
            this.label = 1;
            if (KeyguardPreviewRenderer.access$updateClockAppearance(keyguardPreviewRenderer, createCurrentClock, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            clockController = createCurrentClock;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            clockController = (ClockController) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        KeyguardPreviewRenderer keyguardPreviewRenderer2 = this.this$0;
        int i2 = KeyguardPreviewRenderer.$r8$clinit;
        keyguardPreviewRenderer2.getClass();
        Flags.migrateClocksToBlueprint();
        ClockFaceEvents events = clockController.getLargeClock().getEvents();
        FrameLayout frameLayout = keyguardPreviewRenderer2.largeClockHostView;
        if (frameLayout == null) {
            frameLayout = null;
        }
        events.onTargetRegionChanged(KeyguardClockSwitch.getLargeClockRegion(frameLayout));
        if (keyguardPreviewRenderer2.shouldHighlightSelectedAffordance) {
            clockController.getLargeClock().getView().setAlpha(0.3f);
        }
        FrameLayout frameLayout2 = keyguardPreviewRenderer2.largeClockHostView;
        if (frameLayout2 == null) {
            frameLayout2 = null;
        }
        frameLayout2.removeAllViews();
        FrameLayout frameLayout3 = keyguardPreviewRenderer2.largeClockHostView;
        if (frameLayout3 == null) {
            frameLayout3 = null;
        }
        frameLayout3.addView(clockController.getLargeClock().getView());
        KeyguardPreviewRenderer keyguardPreviewRenderer3 = this.this$0;
        keyguardPreviewRenderer3.getClass();
        Flags.migrateClocksToBlueprint();
        ClockFaceEvents events2 = clockController.getSmallClock().getEvents();
        FrameLayout frameLayout4 = keyguardPreviewRenderer3.smallClockHostView;
        if (frameLayout4 == null) {
            frameLayout4 = null;
        }
        events2.onTargetRegionChanged(KeyguardClockSwitch.getSmallClockRegion(frameLayout4));
        if (keyguardPreviewRenderer3.shouldHighlightSelectedAffordance) {
            clockController.getSmallClock().getView().setAlpha(0.3f);
        }
        FrameLayout frameLayout5 = keyguardPreviewRenderer3.smallClockHostView;
        if (frameLayout5 == null) {
            frameLayout5 = null;
        }
        frameLayout5.removeAllViews();
        FrameLayout frameLayout6 = keyguardPreviewRenderer3.smallClockHostView;
        (frameLayout6 != null ? frameLayout6 : null).addView(clockController.getSmallClock().getView());
        return Unit.INSTANCE;
    }
}
