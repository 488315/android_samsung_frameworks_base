package com.android.systemui.statusbar.phone;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$shouldPlayUnlockedScreenOffAnimation$1$1", m277f = "SecUnlockedScreenOffAnimationHelper.kt", m278l = {340}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.statusbar.phone.SecUnlockedScreenOffAnimationHelper$shouldPlayUnlockedScreenOffAnimation$1$1 */
/* loaded from: classes2.dex */
final class C3129x8ca75fa6 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SecUnlockedScreenOffAnimationHelper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3129x8ca75fa6(SecUnlockedScreenOffAnimationHelper secUnlockedScreenOffAnimationHelper, Continuation<? super C3129x8ca75fa6> continuation) {
        super(2, continuation);
        this.this$0 = secUnlockedScreenOffAnimationHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C3129x8ca75fa6(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C3129x8ca75fa6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(2000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.this$0.setSkipAnimationInOthers(false);
        this.this$0.job = null;
        return Unit.INSTANCE;
    }
}
