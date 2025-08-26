package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes3.dex */
public final class NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $traceName$inlined;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NotificationLockscreenScrimViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1(Continuation continuation, String str, NotificationLockscreenScrimViewModel notificationLockscreenScrimViewModel) {
        super(2, continuation);
        this.$traceName$inlined = str;
        this.this$0 = notificationLockscreenScrimViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1 notificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1 = new NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1(continuation, this.$traceName$inlined, this.this$0);
        notificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1.L$0 = obj;
        return notificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationLockscreenScrimViewModel$onActivated$$inlined$coroutineScopeTraced$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new NotificationLockscreenScrimViewModel$onActivated$2$1(this.this$0, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new NotificationLockscreenScrimViewModel$onActivated$2$2(this.this$0, null), 3);
            this.label = 1;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
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
