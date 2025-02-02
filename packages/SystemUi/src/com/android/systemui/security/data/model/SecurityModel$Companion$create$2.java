package com.android.systemui.security.data.model;

import com.android.systemui.statusbar.policy.SecurityController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.security.data.model.SecurityModel$Companion$create$2", m277f = "SecurityModel.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SecurityModel$Companion$create$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ SecurityController $securityController;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecurityModel$Companion$create$2(SecurityController securityController, Continuation<? super SecurityModel$Companion$create$2> continuation) {
        super(2, continuation);
        this.$securityController = securityController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SecurityModel$Companion$create$2(this.$securityController, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SecurityModel$Companion$create$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return SecurityModel.Companion.create(this.$securityController);
    }
}
