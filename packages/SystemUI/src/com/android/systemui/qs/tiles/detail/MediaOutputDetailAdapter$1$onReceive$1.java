package com.android.systemui.qs.tiles.detail;

import android.content.Intent;
import com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MediaOutputDetailAdapter$1$onReceive$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent;
    int label;
    final /* synthetic */ MediaOutputDetailAdapter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputDetailAdapter$1$onReceive$1(MediaOutputDetailAdapter mediaOutputDetailAdapter, Intent intent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaOutputDetailAdapter;
        this.$intent = intent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaOutputDetailAdapter$1$onReceive$1(this.this$0, this.$intent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaOutputDetailAdapter$1$onReceive$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(100L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        MediaOutputDetailAdapter mediaOutputDetailAdapter = this.this$0;
        Integer num = new Integer(this.$intent.getIntExtra("extra_from", -1));
        if (num.intValue() == -1) {
            num = null;
        }
        mediaOutputDetailAdapter.from = num;
        this.this$0.deviceIds = this.$intent.getStringArrayListExtra("extra_device_ids");
        MediaOutputDetailAdapter.Callback callback = this.this$0.callback;
        (callback != null ? callback : null).showAdapter();
        return Unit.INSTANCE;
    }
}
