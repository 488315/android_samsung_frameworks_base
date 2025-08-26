package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.runtime.MutableState;
import com.android.systemui.qs.panels.ui.compose.selection.SelectionKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class SelectionKt$animateAngle$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Animatable<Float, AnimationVector1D> $animatable;
    final /* synthetic */ MutableState<Boolean> $animate$delegate;
    final /* synthetic */ TileState $tileState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SelectionKt$animateAngle$1$1(TileState tileState, Animatable<Float, AnimationVector1D> animatable, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$tileState = tileState;
        this.$animatable = animatable;
        this.$animate$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SelectionKt$animateAngle$1$1(this.$tileState, this.$animatable, this.$animate$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SelectionKt$animateAngle$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0078, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r4, r5, null, null, null, r9, 14) == r0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0084, code lost:
    
        if (r11.snapTo(r5, r9) == r0) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005c  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Float fValueOf;
        Float f;
        SelectionKt$animateAngle$1$1 selectionKt$animateAngle$1$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            int i2 = SelectionKt.WhenMappings.$EnumSwitchMapping$0[this.$tileState.ordinal()];
            if (i2 == 1 || i2 == 2) {
                fValueOf = null;
                f = fValueOf;
                if (f == null) {
                    if (((Boolean) this.$animate$delegate.getValue()).booleanValue()) {
                        Animatable<Float, AnimationVector1D> animatable = this.$animatable;
                        this.label = 1;
                        selectionKt$animateAngle$1$1 = this;
                    } else {
                        selectionKt$animateAngle$1$1 = this;
                        Animatable<Float, AnimationVector1D> animatable2 = selectionKt$animateAngle$1$1.$animatable;
                        selectionKt$animateAngle$1$1.label = 2;
                    }
                    return coroutineSingletons;
                }
                this.$animate$delegate.setValue(Boolean.FALSE);
            } else {
                if (i2 == 3) {
                    fValueOf = Float.valueOf(-0.8f);
                } else if (i2 != 4) {
                    if (i2 != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                    fValueOf = null;
                } else {
                    fValueOf = Float.valueOf(0.0f);
                }
                f = fValueOf;
                if (f == null) {
                }
            }
        } else if (i == 1) {
            ResultKt.throwOnFailure(obj);
            selectionKt$animateAngle$1$1 = this;
            selectionKt$animateAngle$1$1.$animate$delegate.setValue(Boolean.TRUE);
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            selectionKt$animateAngle$1$1 = this;
            Unit unit = Unit.INSTANCE;
            selectionKt$animateAngle$1$1.$animate$delegate.setValue(Boolean.TRUE);
        }
        return Unit.INSTANCE;
    }
}
