package com.android.systemui.haptics.slider;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.haptics.slider.HapticSlider;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.time.SystemClock;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class HapticSliderPlugin {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final HapticSliderPlugin$dragVelocityProvider$1 dragVelocityProvider;
    public StandaloneCoroutine keyUpJob;
    public LifecycleCoroutineScopeImpl pluginScope;
    public final HapticSlider slider;
    public final SliderStateProducer sliderEventProducer;
    public final SliderHapticFeedbackProvider sliderHapticFeedbackProvider;
    public SliderStateTracker sliderTracker;
    public final SeekableSliderTrackerConfig sliderTrackerConfig;
    public final VelocityTracker velocityTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.haptics.slider.HapticSliderPlugin$onKeyDown$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return HapticSliderPlugin.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(60L, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            HapticSliderPlugin hapticSliderPlugin = HapticSliderPlugin.this;
            int i2 = HapticSliderPlugin.$r8$clinit;
            if (hapticSliderPlugin.isTracking()) {
                hapticSliderPlugin.sliderEventProducer.onStopTracking(false);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public HapticSliderPlugin(VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock, HapticSlider hapticSlider) {
        this(vibratorHelper, mSDLPlayer, systemClock, hapticSlider, null, null, 48, null);
    }

    public static float normalizeProgress(HapticSlider hapticSlider, int i) {
        HapticSlider.SeekBar seekBar = (HapticSlider.SeekBar) hapticSlider;
        if (seekBar.seekBar.getMax() == seekBar.seekBar.getMin()) {
            return 1.0f;
        }
        return (i - seekBar.seekBar.getMin()) / (seekBar.seekBar.getMax() - seekBar.seekBar.getMin());
    }

    public final boolean isTracking() {
        StandaloneCoroutine standaloneCoroutine;
        SliderStateTracker sliderStateTracker = this.sliderTracker;
        return (sliderStateTracker == null || (standaloneCoroutine = sliderStateTracker.job) == null || !standaloneCoroutine.isActive()) ? false : true;
    }

    public final void onKeyDown() {
        StandaloneCoroutine standaloneCoroutine;
        if (isTracking()) {
            StandaloneCoroutine standaloneCoroutine2 = this.keyUpJob;
            if (standaloneCoroutine2 != null && standaloneCoroutine2.isActive() && (standaloneCoroutine = this.keyUpJob) != null) {
                standaloneCoroutine.cancel(null);
            }
            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl = this.pluginScope;
            this.keyUpJob = lifecycleCoroutineScopeImpl != null ? CoroutineTracingKt.launchTraced$default(lifecycleCoroutineScopeImpl, null, null, new AnonymousClass1(null), 7) : null;
        }
    }

    public final void onProgressChanged(int i, boolean z) {
        if (isTracking()) {
            SliderStateTracker sliderStateTracker = this.sliderTracker;
            SliderState sliderState = sliderStateTracker != null ? sliderStateTracker.currentState : null;
            SliderState sliderState2 = SliderState.IDLE;
            HapticSlider hapticSlider = this.slider;
            SliderStateProducer sliderStateProducer = this.sliderEventProducer;
            if (sliderState != sliderState2 || z) {
                sliderStateProducer.onProgressChanged(normalizeProgress(hapticSlider, i), z);
                return;
            }
            float fNormalizeProgress = normalizeProgress(hapticSlider, i);
            sliderStateProducer.getClass();
            sliderStateProducer._currentEvent.updateState(null, new SliderEvent(SliderEventType.NOTHING, fNormalizeProgress));
            sliderStateProducer.onStartTracking(false);
        }
    }

    public final void onTouchEvent(MotionEvent motionEvent) {
        Integer numValueOf = motionEvent != null ? Integer.valueOf(motionEvent.getActionMasked()) : null;
        if ((numValueOf != null && numValueOf.intValue() == 1) || (numValueOf != null && numValueOf.intValue() == 3)) {
            this.velocityTracker.clear();
        } else if ((numValueOf != null && numValueOf.intValue() == 0) || (numValueOf != null && numValueOf.intValue() == 2)) {
            this.velocityTracker.addMovement(motionEvent);
        }
    }

    public HapticSliderPlugin(VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock, HapticSlider hapticSlider, SliderHapticFeedbackConfig sliderHapticFeedbackConfig) {
        this(vibratorHelper, mSDLPlayer, systemClock, hapticSlider, sliderHapticFeedbackConfig, null, 32, null);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.haptics.slider.HapticSliderPlugin$dragVelocityProvider$1, com.android.systemui.haptics.slider.SliderDragVelocityProvider] */
    public HapticSliderPlugin(VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock, HapticSlider hapticSlider, final SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SeekableSliderTrackerConfig seekableSliderTrackerConfig) {
        this.slider = hapticSlider;
        this.sliderTrackerConfig = seekableSliderTrackerConfig;
        this.velocityTracker = VelocityTracker.obtain();
        ?? r3 = new SliderDragVelocityProvider() { // from class: com.android.systemui.haptics.slider.HapticSliderPlugin$dragVelocityProvider$1
            @Override // com.android.systemui.haptics.slider.SliderDragVelocityProvider
            public final float getTrackedVelocity() {
                HapticSliderPlugin hapticSliderPlugin = this.this$0;
                VelocityTracker velocityTracker = hapticSliderPlugin.velocityTracker;
                SliderHapticFeedbackConfig sliderHapticFeedbackConfig2 = sliderHapticFeedbackConfig;
                velocityTracker.computeCurrentVelocity(1000, sliderHapticFeedbackConfig2.maxVelocityToScale);
                if (hapticSliderPlugin.velocityTracker.isAxisSupported(sliderHapticFeedbackConfig2.velocityAxis)) {
                    return hapticSliderPlugin.velocityTracker.getAxisVelocity(sliderHapticFeedbackConfig2.velocityAxis);
                }
                return 0.0f;
            }
        };
        this.dragVelocityProvider = r3;
        this.sliderEventProducer = new SliderStateProducer();
        this.sliderHapticFeedbackProvider = new SliderHapticFeedbackProvider(vibratorHelper, mSDLPlayer, r3, sliderHapticFeedbackConfig, systemClock);
    }

    public /* synthetic */ HapticSliderPlugin(VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock, HapticSlider hapticSlider, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SeekableSliderTrackerConfig seekableSliderTrackerConfig, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(vibratorHelper, mSDLPlayer, systemClock, hapticSlider, (i & 16) != 0 ? new SliderHapticFeedbackConfig(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 0, 0.0f, 0.0f, 0.0f, 0.0f, null, 32767, null) : sliderHapticFeedbackConfig, (i & 32) != 0 ? new SeekableSliderTrackerConfig(0L, 0.0f, 0.0f, 0.0f, 15, null) : seekableSliderTrackerConfig);
    }

    public static /* synthetic */ void isKeyUpTimerWaiting$annotations() {
    }
}
