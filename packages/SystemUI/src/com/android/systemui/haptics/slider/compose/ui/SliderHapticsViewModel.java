package com.android.systemui.haptics.slider.compose.ui;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.util.VelocityTracker;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import com.android.systemui.haptics.slider.SeekableSliderTrackerConfig;
import com.android.systemui.haptics.slider.SliderDragVelocityProvider;
import com.android.systemui.haptics.slider.SliderEvent;
import com.android.systemui.haptics.slider.SliderEventType;
import com.android.systemui.haptics.slider.SliderHapticFeedbackConfig;
import com.android.systemui.haptics.slider.SliderHapticFeedbackProvider;
import com.android.systemui.haptics.slider.SliderStateProducer;
import com.android.systemui.haptics.slider.SliderStateTracker;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.time.SystemClock;
import com.google.android.msdl.domain.MSDLPlayer;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SliderHapticsViewModel extends ExclusiveActivatable {
    public final SliderHapticsViewModel$dragVelocityProvider$1 dragVelocityProvider;
    public final InteractionSource interactionSource;
    public final long maxVelocity;
    public final Orientation orientation;
    public final SliderHapticFeedbackProvider sliderHapticFeedbackProvider;
    public final ClosedFloatingPointRange sliderRange;
    public final SliderStateProducer sliderStateProducer;
    public SliderStateTracker sliderTracker;
    public final SeekableSliderTrackerConfig sliderTrackerConfig;
    public float startingProgress;
    public SliderEventType currentSliderEventType = SliderEventType.NOTHING;
    public final VelocityTracker velocityTracker = new VelocityTracker();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        SliderHapticsViewModel create(InteractionSource interactionSource, ClosedFloatingPointRange closedFloatingPointRange, Orientation orientation, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SeekableSliderTrackerConfig seekableSliderTrackerConfig);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[SliderEventType.values().length];
            try {
                iArr[SliderEventType.NOTHING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SliderEventType.STARTED_TRACKING_TOUCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SliderEventType.PROGRESS_CHANGE_BY_USER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SliderEventType.STARTED_TRACKING_PROGRAM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[SliderEventType.PROGRESS_CHANGE_BY_PROGRAM.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Orientation.values().length];
            try {
                iArr2[Orientation.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[Orientation.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.haptics.slider.SliderDragVelocityProvider, com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$dragVelocityProvider$1] */
    public SliderHapticsViewModel(InteractionSource interactionSource, ClosedFloatingPointRange closedFloatingPointRange, Orientation orientation, SliderHapticFeedbackConfig sliderHapticFeedbackConfig, SeekableSliderTrackerConfig seekableSliderTrackerConfig, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock) {
        this.interactionSource = interactionSource;
        this.sliderRange = closedFloatingPointRange;
        this.orientation = orientation;
        this.sliderTrackerConfig = seekableSliderTrackerConfig;
        float f = sliderHapticFeedbackConfig.maxVelocityToScale;
        this.maxVelocity = VelocityKt.Velocity(f, f);
        ?? r6 = new SliderDragVelocityProvider() { // from class: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$dragVelocityProvider$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public abstract /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[Orientation.values().length];
                    try {
                        iArr[Orientation.Horizontal.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[Orientation.Vertical.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            @Override // com.android.systemui.haptics.slider.SliderDragVelocityProvider
            public final float getTrackedVelocity() {
                float m878getXimpl;
                SliderHapticsViewModel sliderHapticsViewModel = SliderHapticsViewModel.this;
                int i = WhenMappings.$EnumSwitchMapping$0[sliderHapticsViewModel.orientation.ordinal()];
                if (i == 1) {
                    m878getXimpl = Velocity.m878getXimpl(sliderHapticsViewModel.velocityTracker.m600calculateVelocityAH228Gc(sliderHapticsViewModel.maxVelocity));
                } else {
                    if (i != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    m878getXimpl = Velocity.m879getYimpl(sliderHapticsViewModel.velocityTracker.m600calculateVelocityAH228Gc(sliderHapticsViewModel.maxVelocity));
                }
                return Math.abs(m878getXimpl);
            }
        };
        this.dragVelocityProvider = r6;
        this.sliderStateProducer = new SliderStateProducer();
        this.sliderHapticFeedbackProvider = new SliderHapticFeedbackProvider(vibratorHelper, mSDLPlayer, r6, sliderHapticFeedbackConfig, systemClock);
    }

    public final void addVelocityDataPoint(float f) {
        long floatToRawIntBits;
        float normalize = normalize(f);
        long currentTimeMillis = System.currentTimeMillis();
        int i = WhenMappings.$EnumSwitchMapping$1[this.orientation.ordinal()];
        if (i == 1) {
            floatToRawIntBits = (Float.floatToRawIntBits(normalize - this.startingProgress) << 32) | (4294967295L & Float.floatToRawIntBits(0.0f));
            Offset.Companion companion = Offset.Companion;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            float f2 = normalize - this.startingProgress;
            floatToRawIntBits = (Float.floatToRawIntBits(0.0f) << 32) | (4294967295L & Float.floatToRawIntBits(f2));
            Offset.Companion companion2 = Offset.Companion;
        }
        this.velocityTracker.m599addPositionUv8p0NA(currentTimeMillis, floatToRawIntBits);
    }

    public final float normalize(float f) {
        ClosedFloatingPointRange closedFloatingPointRange = this.sliderRange;
        float floatValue = f - Float.valueOf(((ClosedFloatRange) closedFloatingPointRange)._start).floatValue();
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) closedFloatingPointRange;
        return RangesKt___RangesKt.coerceIn(floatValue / (closedFloatRange._endInclusive - closedFloatRange._start), 0.0f, 1.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$1 r0 = (com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$1 r0 = new com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$2 r5 = new com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void onValueChange(float f) {
        float normalize = normalize(f);
        int i = WhenMappings.$EnumSwitchMapping$0[this.currentSliderEventType.ordinal()];
        SliderStateProducer sliderStateProducer = this.sliderStateProducer;
        if (i == 1) {
            this.currentSliderEventType = SliderEventType.STARTED_TRACKING_PROGRAM;
            this.startingProgress = normalize;
            sliderStateProducer.getClass();
            sliderStateProducer._currentEvent.updateState(null, new SliderEvent(SliderEventType.NOTHING, normalize));
            sliderStateProducer.onStartTracking(false);
            return;
        }
        if (i == 2) {
            this.startingProgress = normalize;
            this.currentSliderEventType = SliderEventType.PROGRESS_CHANGE_BY_USER;
            sliderStateProducer.onProgressChanged(normalize, true);
            return;
        }
        if (i == 3) {
            addVelocityDataPoint(f);
            this.currentSliderEventType = SliderEventType.PROGRESS_CHANGE_BY_USER;
            sliderStateProducer.onProgressChanged(normalize, true);
        } else if (i == 4) {
            this.startingProgress = normalize;
            this.currentSliderEventType = SliderEventType.PROGRESS_CHANGE_BY_PROGRAM;
            sliderStateProducer.onProgressChanged(normalize, false);
        } else {
            if (i != 5) {
                return;
            }
            addVelocityDataPoint(f);
            this.currentSliderEventType = SliderEventType.PROGRESS_CHANGE_BY_PROGRAM;
            sliderStateProducer.onProgressChanged(normalize, false);
        }
    }

    public final void onValueChangeEnded() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.currentSliderEventType.ordinal()];
        SliderStateProducer sliderStateProducer = this.sliderStateProducer;
        if (i == 2 || i == 3) {
            sliderStateProducer.onStopTracking(true);
        } else if (i == 4 || i == 5) {
            sliderStateProducer.onStopTracking(false);
        }
        this.currentSliderEventType = SliderEventType.NOTHING;
        this.velocityTracker.resetTracking();
    }
}
