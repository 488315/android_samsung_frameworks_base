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
        SpaceVectorConverter spaceVectorConverterConverterOrNull = converterOrNull(Velocity.m880getXimpl(j), Velocity.m881getYimpl(j));
        if (spaceVectorConverterConverterOrNull == null) {
            return Unit.INSTANCE;
        }
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new BaseContentOverscrollEffect$applyToFling$3(function2, j, this, spaceVectorConverterConverterOrNull, null), continuationImpl);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (objCoroutineScope != coroutineSingletons) {
            objCoroutineScope = Unit.INSTANCE;
        }
        return objCoroutineScope == coroutineSingletons ? objCoroutineScope : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.OverscrollEffect
    /* renamed from: applyToScroll-Rhakbz0 */
    public final long mo20applyToScrollRhakbz0(int i, long j, Function1 function1) {
        SpaceVectorConverter spaceVectorConverterConverterOrNull = converterOrNull(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (4294967295L & j)));
        if (spaceVectorConverterConverterOrNull == null) {
            return ((Offset) function1.mo781invoke(Offset.m395boximpl(j))).packedValue;
        }
        float fMo917toFloatk4lQ0M$1 = spaceVectorConverterConverterOrNull.mo917toFloatk4lQ0M$1(j);
        float fFloatValue = ((Number) this.animatable.internalState.getValue()).floatValue();
        boolean z = Math.signum(fMo917toFloatk4lQ0M$1) == Math.signum(fFloatValue);
        double dAbs = Math.abs(fFloatValue);
        CoroutineScope coroutineScope = this.animationScope;
        if (dAbs <= 0.5d || z) {
            fMo917toFloatk4lQ0M$1 = 0.0f;
        } else {
            float f = fFloatValue + fMo917toFloatk4lQ0M$1;
            if (Math.signum(fFloatValue) == Math.signum(f)) {
                BuildersKt.launch$default(coroutineScope, null, null, new BaseContentOverscrollEffect$applyToScroll$consumedByPreScroll$2(this, f, null), 3);
            } else {
                BuildersKt.launch$default(coroutineScope, null, null, new BaseContentOverscrollEffect$applyToScroll$consumedByPreScroll$1(this, null), 3);
                fMo917toFloatk4lQ0M$1 = -fFloatValue;
            }
        }
        long jM402minusMKHz9U = Offset.m402minusMKHz9U(j, spaceVectorConverterConverterOrNull.mo919toOffsettuRUvjQ$1(fMo917toFloatk4lQ0M$1));
        long jM402minusMKHz9U2 = Offset.m402minusMKHz9U(jM402minusMKHz9U, ((Offset) function1.mo781invoke(Offset.m395boximpl(jM402minusMKHz9U))).packedValue);
        if (Math.abs(spaceVectorConverterConverterOrNull.mo917toFloatk4lQ0M$1(jM402minusMKHz9U2)) > 0.5d) {
            NestedScrollSource.Companion.getClass();
            if (i == NestedScrollSource.UserInput) {
                BuildersKt.launch$default(coroutineScope, null, null, new BaseContentOverscrollEffect$applyToScroll$1(this, fFloatValue, spaceVectorConverterConverterOrNull, jM402minusMKHz9U2, null), 3);
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
