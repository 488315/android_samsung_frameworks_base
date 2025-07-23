package androidx.compose.foundation.text.input.internal;

import androidx.compose.ui.platform.PlatformTextInputModifierNodeKt;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LegacyAdaptingPlatformTextInputModifierNode$launchTextInputSession$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    int label;
    final /* synthetic */ LegacyAdaptingPlatformTextInputModifierNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacyAdaptingPlatformTextInputModifierNode$launchTextInputSession$1(LegacyAdaptingPlatformTextInputModifierNode legacyAdaptingPlatformTextInputModifierNode, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = legacyAdaptingPlatformTextInputModifierNode;
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LegacyAdaptingPlatformTextInputModifierNode$launchTextInputSession$1(this.this$0, this.$block, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LegacyAdaptingPlatformTextInputModifierNode$launchTextInputSession$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LegacyAdaptingPlatformTextInputModifierNode legacyAdaptingPlatformTextInputModifierNode = this.this$0;
            Function2 function2 = this.$block;
            this.label = 1;
            if (PlatformTextInputModifierNodeKt.establishTextInputSession(legacyAdaptingPlatformTextInputModifierNode, function2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
