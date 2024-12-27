package com.android.systemui.haptics.slider;

import androidx.lifecycle.LifecycleCoroutineScopeImpl;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

final class HapticSliderViewBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ SeekbarHapticPlugin $plugin;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HapticSliderViewBinder$bind$1(SeekbarHapticPlugin seekbarHapticPlugin, Continuation continuation) {
        super(3, continuation);
        this.$plugin = seekbarHapticPlugin;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        HapticSliderViewBinder$bind$1 hapticSliderViewBinder$bind$1 = new HapticSliderViewBinder$bind$1(this.$plugin, (Continuation) obj3);
        hapticSliderViewBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return hapticSliderViewBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                SeekbarHapticPlugin seekbarHapticPlugin = this.$plugin;
                LifecycleCoroutineScopeImpl lifecycleScope = LifecycleOwnerKt.getLifecycleScope(lifecycleOwner);
                SliderStateTracker sliderStateTracker = seekbarHapticPlugin.sliderTracker;
                if (sliderStateTracker != null && sliderStateTracker != null) {
                    Job job = sliderStateTracker.job;
                    if (job != null) {
                        JobKt.cancel$default("Stopped tracking slider state", job);
                    }
                    sliderStateTracker.job = null;
                    Job job2 = sliderStateTracker.timerJob;
                    if (job2 != null) {
                        job2.cancel(null);
                    }
                    sliderStateTracker.timerJob = null;
                    sliderStateTracker.currentState = SliderState.IDLE;
                    Unit unit = Unit.INSTANCE;
                }
                SliderStateTracker sliderStateTracker2 = new SliderStateTracker(seekbarHapticPlugin.sliderHapticFeedbackProvider, seekbarHapticPlugin.sliderEventProducer, lifecycleScope, seekbarHapticPlugin.sliderTrackerConfig);
                seekbarHapticPlugin.sliderTracker = sliderStateTracker2;
                seekbarHapticPlugin.pluginScope = lifecycleScope;
                sliderStateTracker2.job = BuildersKt.launch$default(sliderStateTracker2.scope, null, null, new SliderTracker$startTracking$1(sliderStateTracker2, null), 3);
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
                Job job3 = sliderStateTracker3.job;
                if (job3 != null) {
                    JobKt.cancel$default("Stopped tracking slider state", job3);
                }
                sliderStateTracker3.job = null;
                Job job4 = sliderStateTracker3.timerJob;
                if (job4 != null) {
                    job4.cancel(null);
                }
                sliderStateTracker3.timerJob = null;
                sliderStateTracker3.currentState = SliderState.IDLE;
                Unit unit2 = Unit.INSTANCE;
            }
            throw th;
        }
    }
}
