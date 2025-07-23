package com.android.compose.gesture.effect;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.unit.Velocity;
import com.android.compose.ui.util.HorizontalSpaceVectorConverter;
import com.android.compose.ui.util.SpaceVectorConverter;
import com.android.compose.ui.util.VerticalSpaceVectorConverter;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BaseContentOverscrollEffect implements OverscrollEffect {
    public final Animatable animatable = AnimatableKt.Animatable(0.0f, 0.01f);
    public final CoroutineScope animationScope;
    public final AnimationSpec animationSpec;
    public SpaceVectorConverter lastConverter;

    public BaseContentOverscrollEffect(CoroutineScope coroutineScope, AnimationSpec<Float> animationSpec) {
        this.animationScope = coroutineScope;
        this.animationSpec = animationSpec;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToFling-BMRW4eQ */
    public final Object mo19applyToFlingBMRW4eQ(long j, Function2 function2, ContinuationImpl continuationImpl) {
        SpaceVectorConverter converterOrNull = converterOrNull(Velocity.m878getXimpl(j), Velocity.m879getYimpl(j));
        if (converterOrNull == null) {
            return Unit.INSTANCE;
        }
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new BaseContentOverscrollEffect$applyToFling$3(function2, j, this, converterOrNull, null), continuationImpl);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (coroutineScope != coroutineSingletons) {
            coroutineScope = Unit.INSTANCE;
        }
        return coroutineScope == coroutineSingletons ? coroutineScope : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToScroll-Rhakbz0 */
    public final long mo20applyToScrollRhakbz0(int i, long j, Function1 function1) {
        SpaceVectorConverter converterOrNull = converterOrNull(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (4294967295L & j)));
        if (converterOrNull == null) {
            return ((Offset) function1.mo779invoke(Offset.m393boximpl(j))).packedValue;
        }
        float mo915toFloatk4lQ0M$1 = converterOrNull.mo915toFloatk4lQ0M$1(j);
        float floatValue = ((Number) this.animatable.internalState.getValue()).floatValue();
        boolean z = Math.signum(mo915toFloatk4lQ0M$1) == Math.signum(floatValue);
        double abs = Math.abs(floatValue);
        CoroutineScope coroutineScope = this.animationScope;
        if (abs <= 0.5d || z) {
            mo915toFloatk4lQ0M$1 = 0.0f;
        } else {
            float f = floatValue + mo915toFloatk4lQ0M$1;
            if (Math.signum(floatValue) == Math.signum(f)) {
                BuildersKt.launch$default(coroutineScope, null, null, new BaseContentOverscrollEffect$applyToScroll$consumedByPreScroll$2(this, f, null), 3);
            } else {
                BuildersKt.launch$default(coroutineScope, null, null, new BaseContentOverscrollEffect$applyToScroll$consumedByPreScroll$1(this, null), 3);
                mo915toFloatk4lQ0M$1 = -floatValue;
            }
        }
        long m400minusMKHz9U = Offset.m400minusMKHz9U(j, converterOrNull.mo917toOffsettuRUvjQ$1(mo915toFloatk4lQ0M$1));
        long m400minusMKHz9U2 = Offset.m400minusMKHz9U(m400minusMKHz9U, ((Offset) function1.mo779invoke(Offset.m393boximpl(m400minusMKHz9U))).packedValue);
        if (Math.abs(converterOrNull.mo915toFloatk4lQ0M$1(m400minusMKHz9U2)) > 0.5d) {
            NestedScrollSource.Companion.getClass();
            if (i == NestedScrollSource.UserInput) {
                BuildersKt.launch$default(coroutineScope, null, null, new BaseContentOverscrollEffect$applyToScroll$1(this, floatValue, converterOrNull, m400minusMKHz9U2, null), 3);
            }
        }
        return j;
    }

    public final SpaceVectorConverter converterOrNull(float f, float f2) {
        SpaceVectorConverter spaceVectorConverter;
        if (f != 0.0f && f2 != 0.0f) {
            throw new IllegalStateException("BaseContentOverscrollEffect only supports single orientation scrolls and velocities");
        }
        if (f == 0.0f && f2 == 0.0f) {
            spaceVectorConverter = this.lastConverter;
            if (spaceVectorConverter == null) {
                return null;
            }
        } else {
            spaceVectorConverter = f == 0.0f ? VerticalSpaceVectorConverter.INSTANCE : HorizontalSpaceVectorConverter.INSTANCE;
        }
        SpaceVectorConverter spaceVectorConverter2 = this.lastConverter;
        if (spaceVectorConverter2 == null) {
            this.lastConverter = spaceVectorConverter;
            return spaceVectorConverter;
        }
        if (Intrinsics.areEqual(spaceVectorConverter2, spaceVectorConverter)) {
            return spaceVectorConverter;
        }
        throw new IllegalStateException("BaseContentOverscrollEffect should always be used in the same orientation");
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    public final boolean isInProgress() {
        Animatable animatable = this.animatable;
        return ((Number) animatable.internalState.getValue()).floatValue() != 0.0f || animatable.isRunning();
    }
}
