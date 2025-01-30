package com.android.systemui.keyguard.domain.interactor;

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
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardLongPressInteractor$scheduleAutomaticMenuHiding$1", m277f = "KeyguardLongPressInteractor.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class KeyguardLongPressInteractor$scheduleAutomaticMenuHiding$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardLongPressInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardLongPressInteractor$scheduleAutomaticMenuHiding$1(KeyguardLongPressInteractor keyguardLongPressInteractor, Continuation<? super KeyguardLongPressInteractor$scheduleAutomaticMenuHiding$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardLongPressInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardLongPressInteractor$scheduleAutomaticMenuHiding$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardLongPressInteractor$scheduleAutomaticMenuHiding$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long recommendedTimeoutMillis = this.this$0.accessibilityManager.getRecommendedTimeoutMillis(5000, 3);
            this.label = 1;
            if (DelayKt.delay(recommendedTimeoutMillis, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        KeyguardLongPressInteractor keyguardLongPressInteractor = this.this$0;
        int i2 = KeyguardLongPressInteractor.$r8$clinit;
        keyguardLongPressInteractor.hideMenu();
        return Unit.INSTANCE;
    }
}
