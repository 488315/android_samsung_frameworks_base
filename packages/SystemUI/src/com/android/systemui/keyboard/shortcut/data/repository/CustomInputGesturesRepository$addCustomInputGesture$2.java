package com.android.systemui.keyboard.shortcut.data.repository;

import android.hardware.input.InputGestureData;
import android.hardware.input.InputSettings;
import android.util.Log;
import com.android.systemui.keyboard.shared.model.ShortcutCustomizationRequestResult;
import java.util.List;
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
final class CustomInputGesturesRepository$addCustomInputGesture$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ InputGestureData $inputGesture;
    int label;
    final /* synthetic */ CustomInputGesturesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomInputGesturesRepository$addCustomInputGesture$2(CustomInputGesturesRepository customInputGesturesRepository, InputGestureData inputGestureData, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customInputGesturesRepository;
        this.$inputGesture = inputGestureData;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomInputGesturesRepository$addCustomInputGesture$2(this.this$0, this.$inputGesture, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomInputGesturesRepository$addCustomInputGesture$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CustomInputGesturesRepository customInputGesturesRepository = this.this$0;
        int i = CustomInputGesturesRepository.$r8$clinit;
        int addCustomInputGesture = customInputGesturesRepository.getInputManager().addCustomInputGesture(this.$inputGesture);
        if (addCustomInputGesture == 1) {
            CustomInputGesturesRepository customInputGesturesRepository2 = this.this$0;
            customInputGesturesRepository2.getClass();
            if (InputSettings.isCustomizableInputGesturesFeatureFlagEnabled()) {
                list = customInputGesturesRepository2.getInputManager().getCustomInputGestures(InputGestureData.Filter.KEY);
                list.getClass();
            } else {
                list = EmptyList.INSTANCE;
            }
            customInputGesturesRepository2._customInputGesture.setValue(list);
            return ShortcutCustomizationRequestResult.SUCCESS;
        }
        if (addCustomInputGesture == 2) {
            return ShortcutCustomizationRequestResult.ERROR_RESERVED_COMBINATION;
        }
        if (addCustomInputGesture == 4) {
            return ShortcutCustomizationRequestResult.ERROR_RESERVED_COMBINATION;
        }
        Log.w("CustomInputGesturesRepository", "Attempted to add inputGesture: " + this.$inputGesture + " but ran into an error with code: " + addCustomInputGesture);
        return ShortcutCustomizationRequestResult.ERROR_OTHER;
    }
}
