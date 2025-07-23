package com.airbnb.lottie.compose;

import androidx.compose.foundation.MutatorMutex;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.airbnb.lottie.LottieComposition;
import kotlin.jvm.functions.Function0;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LottieAnimatableImpl implements LottieAnimatable {
    public final MutatorMutex mutex;
    public final MutableState isPlaying$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
    public final MutableState progress$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(0.0f));
    public final MutableState iteration$delegate = SnapshotStateKt.mutableStateOf$default(1);
    public final MutableState iterations$delegate = SnapshotStateKt.mutableStateOf$default(1);
    public final MutableState clipSpec$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final MutableState speed$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(1.0f));
    public final MutableState composition$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final MutableState lastFrameNanos$delegate = SnapshotStateKt.mutableStateOf$default(Long.MIN_VALUE);
    public final State endProgress$delegate = SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.airbnb.lottie.compose.LottieAnimatableImpl$endProgress$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            float f = 0.0f;
            if (((LottieComposition) ((SnapshotMutableStateImpl) LottieAnimatableImpl.this.composition$delegate).getValue()) != null) {
                if (LottieAnimatableImpl.this.getSpeed() < 0.0f) {
                    LottieClipSpec lottieClipSpec = (LottieClipSpec) ((SnapshotMutableStateImpl) LottieAnimatableImpl.this.clipSpec$delegate).getValue();
                    if (lottieClipSpec != null) {
                        f = lottieClipSpec.getMinProgress$lottie_compose_release();
                    }
                } else {
                    LottieClipSpec lottieClipSpec2 = (LottieClipSpec) ((SnapshotMutableStateImpl) LottieAnimatableImpl.this.clipSpec$delegate).getValue();
                    f = lottieClipSpec2 == null ? 1.0f : lottieClipSpec2.getMaxProgress$lottie_compose_release();
                }
            }
            return Float.valueOf(f);
        }
    });

    public LottieAnimatableImpl() {
        SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.airbnb.lottie.compose.LottieAnimatableImpl$isAtEnd$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(((Number) ((SnapshotMutableStateImpl) LottieAnimatableImpl.this.iteration$delegate).getValue()).intValue() == ((Number) ((SnapshotMutableStateImpl) LottieAnimatableImpl.this.iterations$delegate).getValue()).intValue() && LottieAnimatableImpl.this.getProgress() == ((Number) LottieAnimatableImpl.this.endProgress$delegate.getValue()).floatValue());
            }
        });
        this.mutex = new MutatorMutex();
    }

    public static final boolean access$onFrame(LottieAnimatableImpl lottieAnimatableImpl, int i, long j) {
        LottieComposition lottieComposition = (LottieComposition) ((SnapshotMutableStateImpl) lottieAnimatableImpl.composition$delegate).getValue();
        if (lottieComposition == null) {
            return true;
        }
        MutableState mutableState = lottieAnimatableImpl.lastFrameNanos$delegate;
        long longValue = ((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).longValue() == Long.MIN_VALUE ? 0L : j - ((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).longValue();
        ((SnapshotMutableStateImpl) mutableState).setValue(Long.valueOf(j));
        MutableState mutableState2 = lottieAnimatableImpl.clipSpec$delegate;
        LottieClipSpec lottieClipSpec = (LottieClipSpec) ((SnapshotMutableStateImpl) mutableState2).getValue();
        float minProgress$lottie_compose_release = lottieClipSpec == null ? 0.0f : lottieClipSpec.getMinProgress$lottie_compose_release();
        LottieClipSpec lottieClipSpec2 = (LottieClipSpec) ((SnapshotMutableStateImpl) mutableState2).getValue();
        float maxProgress$lottie_compose_release = lottieClipSpec2 == null ? 1.0f : lottieClipSpec2.getMaxProgress$lottie_compose_release();
        float speed = lottieAnimatableImpl.getSpeed() * ((longValue / 1000000) / lottieComposition.getDuration());
        float progress = lottieAnimatableImpl.getSpeed() < 0.0f ? minProgress$lottie_compose_release - (lottieAnimatableImpl.getProgress() + speed) : (lottieAnimatableImpl.getProgress() + speed) - maxProgress$lottie_compose_release;
        if (progress < 0.0f) {
            lottieAnimatableImpl.setProgress$1(RangesKt___RangesKt.coerceIn(lottieAnimatableImpl.getProgress(), minProgress$lottie_compose_release, maxProgress$lottie_compose_release) + speed);
            return true;
        }
        float f = maxProgress$lottie_compose_release - minProgress$lottie_compose_release;
        int i2 = (int) (progress / f);
        int i3 = i2 + 1;
        MutableState mutableState3 = lottieAnimatableImpl.iteration$delegate;
        if (((Number) ((SnapshotMutableStateImpl) mutableState3).getValue()).intValue() + i3 > i) {
            lottieAnimatableImpl.setProgress$1(((Number) lottieAnimatableImpl.endProgress$delegate.getValue()).floatValue());
            lottieAnimatableImpl.setIteration(i);
            return false;
        }
        lottieAnimatableImpl.setIteration(((Number) ((SnapshotMutableStateImpl) mutableState3).getValue()).intValue() + i3);
        float f2 = progress - (i2 * f);
        lottieAnimatableImpl.setProgress$1(lottieAnimatableImpl.getSpeed() < 0.0f ? maxProgress$lottie_compose_release - f2 : minProgress$lottie_compose_release + f2);
        return true;
    }

    public final float getProgress() {
        return ((Number) ((SnapshotMutableStateImpl) this.progress$delegate).getValue()).floatValue();
    }

    public final float getSpeed() {
        return ((Number) ((SnapshotMutableStateImpl) this.speed$delegate).getValue()).floatValue();
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return Float.valueOf(getProgress());
    }

    public final void setIteration(int i) {
        ((SnapshotMutableStateImpl) this.iteration$delegate).setValue(Integer.valueOf(i));
    }

    public final void setProgress$1(float f) {
        ((SnapshotMutableStateImpl) this.progress$delegate).setValue(Float.valueOf(f));
    }
}
