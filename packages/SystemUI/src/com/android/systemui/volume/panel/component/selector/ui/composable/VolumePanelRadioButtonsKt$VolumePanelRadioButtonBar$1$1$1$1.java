package com.android.systemui.volume.panel.component.selector.ui.composable;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationVector1D;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
final class VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $it;
    final /* synthetic */ Animatable<Integer, AnimationVector1D> $offsetAnimatable;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1(Animatable<Integer, AnimationVector1D> animatable, int i, Continuation continuation) {
        super(2, continuation);
        this.$offsetAnimatable = animatable;
        this.$it = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1(this.$offsetAnimatable, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumePanelRadioButtonsKt$VolumePanelRadioButtonBar$1$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003f, code lost:
    
        if (r9.snapTo(r2, r8) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005b, code lost:
    
        if (androidx.compose.animation.core.Animatable.animateTo$default(r1, r2, null, null, null, r8, 14) == r0) goto L18;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (((Number) this.$offsetAnimatable.internalState.getValue()).intValue() == -1) {
                Animatable<Integer, AnimationVector1D> animatable = this.$offsetAnimatable;
                Integer num = new Integer(this.$it);
                this.label = 1;
            } else {
                Animatable<Integer, AnimationVector1D> animatable2 = this.$offsetAnimatable;
                Integer num2 = new Integer(this.$it);
                this.label = 2;
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            ResultKt.throwOnFailure(obj);
            Unit unit = Unit.INSTANCE;
        } else {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
