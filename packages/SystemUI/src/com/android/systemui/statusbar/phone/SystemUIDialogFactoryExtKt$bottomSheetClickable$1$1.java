package com.android.systemui.statusbar.phone;

import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

final class SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function0 $onClick;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1(Function0 function0, Continuation continuation) {
        super(2, continuation);
        this.$onClick = function0;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1 systemUIDialogFactoryExtKt$bottomSheetClickable$1$1 = new SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1(this.$onClick, continuation);
        systemUIDialogFactoryExtKt$bottomSheetClickable$1$1.L$0 = obj;
        return systemUIDialogFactoryExtKt$bottomSheetClickable$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            final Function0 function0 = this.$onClick;
            Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt$bottomSheetClickable$1$1.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    long j = ((Offset) obj2).packedValue;
                    Function0.this.invoke();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, null, function1, this, 7) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
