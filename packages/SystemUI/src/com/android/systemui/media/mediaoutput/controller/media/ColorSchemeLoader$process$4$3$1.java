package com.android.systemui.media.mediaoutput.controller.media;

import android.graphics.drawable.Drawable;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import com.android.systemui.monet.ColorScheme;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class ColorSchemeLoader$process$4$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $callback;
    final /* synthetic */ Drawable $drawable;
    final /* synthetic */ ColorScheme $it;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorSchemeLoader$process$4$3$1(Function2 function2, Drawable drawable, ColorScheme colorScheme, Continuation continuation) {
        super(2, continuation);
        this.$callback = function2;
        this.$drawable = drawable;
        this.$it = colorScheme;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ColorSchemeLoader$process$4$3$1(this.$callback, this.$drawable, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ColorSchemeLoader$process$4$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Function2 function2 = this.$callback;
        TintDrawablePainter.Companion companion = TintDrawablePainter.Companion;
        Drawable drawable = this.$drawable;
        companion.getClass();
        function2.invoke(TintDrawablePainter.Companion.toConverter(drawable), this.$it);
        return Unit.INSTANCE;
    }
}
