package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputGestureData;
import android.util.Log;
import com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomInputGesturesRepository$resetAllCustomInputGestures$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CustomInputGesturesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomInputGesturesRepository$resetAllCustomInputGestures$2(CustomInputGesturesRepository customInputGesturesRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customInputGesturesRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomInputGesturesRepository$resetAllCustomInputGestures$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomInputGesturesRepository$resetAllCustomInputGestures$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            CustomInputGesturesRepository customInputGesturesRepository = this.this$0;
            int i = CustomInputGesturesRepository.$r8$clinit;
            customInputGesturesRepository.getInputManager().removeAllCustomInputGestures(InputGestureData.Filter.KEY);
            this.this$0._customInputGesture.setValue(EmptyList.INSTANCE);
            return ShortcutCustomizationRequestResult.SUCCESS;
        } catch (Exception e) {
            Log.w("CustomInputGesturesRepository", "Attempted to remove all custom shortcut but ran into a remote error: " + e);
            return ShortcutCustomizationRequestResult.ERROR_OTHER;
        }
    }
}
