package com.android.systemui.media;

import android.content.Context;
import android.provider.Settings;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CustomComposeView$onAttachedToWindow$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CustomComposeView this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomComposeView$onAttachedToWindow$1(CustomComposeView customComposeView, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customComposeView;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CustomComposeView$onAttachedToWindow$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomComposeView$onAttachedToWindow$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0.setKeepScreenOn(true);
            DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
            Context context = this.this$0.getContext();
            deviceUtils.getClass();
            long j = Settings.System.getLong(context.getContentResolver(), "screen_off_timeout", 10000L) - 5000;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.this$0.setKeepScreenOn(false);
        return Unit.INSTANCE;
    }
}
