package com.android.systemui.haptics.slider;

import android.view.View;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class HapticSliderViewBinder {

    /* renamed from: com.android.systemui.haptics.slider.HapticSliderViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ HapticSliderPlugin $plugin;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(HapticSliderPlugin hapticSliderPlugin, Continuation continuation) {
            super(3, continuation);
            this.$plugin = hapticSliderPlugin;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$plugin, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                    HapticSliderPlugin hapticSliderPlugin = this.$plugin;
                    LifecycleCoroutineScopeImpl lifecycleScope = LifecycleOwnerKt.getLifecycleScope(lifecycleOwner);
                    SliderStateTracker sliderStateTracker = hapticSliderPlugin.sliderTracker;
                    if (sliderStateTracker != null && sliderStateTracker != null) {
                        StandaloneCoroutine standaloneCoroutine = sliderStateTracker.job;
                        if (standaloneCoroutine != null) {
                            standaloneCoroutine.cancel(ExceptionsKt.CancellationException("Stopped tracking slider state", null));
                        }
                        sliderStateTracker.job = null;
                        sliderStateTracker.resetState();
                        Unit unit = Unit.INSTANCE;
                    }
                    SliderStateTracker sliderStateTracker2 = new SliderStateTracker(hapticSliderPlugin.sliderHapticFeedbackProvider, hapticSliderPlugin.sliderEventProducer, lifecycleScope, hapticSliderPlugin.sliderTrackerConfig);
                    hapticSliderPlugin.sliderTracker = sliderStateTracker2;
                    hapticSliderPlugin.pluginScope = lifecycleScope;
                    sliderStateTracker2.startTracking();
                    this.label = 1;
                    if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                throw new KotlinNothingValueException();
            } catch (Throwable th) {
                SliderStateTracker sliderStateTracker3 = this.$plugin.sliderTracker;
                if (sliderStateTracker3 != null) {
                    StandaloneCoroutine standaloneCoroutine2 = sliderStateTracker3.job;
                    if (standaloneCoroutine2 != null) {
                        standaloneCoroutine2.cancel(ExceptionsKt.CancellationException("Stopped tracking slider state", null));
                    }
                    sliderStateTracker3.job = null;
                    sliderStateTracker3.resetState();
                    Unit unit2 = Unit.INSTANCE;
                }
                throw th;
            }
        }
    }

    static {
        new HapticSliderViewBinder();
    }

    private HapticSliderViewBinder() {
    }

    public static final void bind(View view, HapticSliderPlugin hapticSliderPlugin) {
        if (view != null) {
            RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(hapticSliderPlugin, null));
        }
    }
}
