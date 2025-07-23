package com.android.systemui.authentication.data.repository;

import android.content.pm.UserInfo;
import com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AuthenticationRepositoryImpl$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AuthenticationRepositoryImpl.AnonymousClass1.C00261 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationRepositoryImpl$1$1$emit$1(AuthenticationRepositoryImpl.AnonymousClass1.C00261 c00261, Continuation continuation) {
        super(continuation);
        this.this$0 = c00261;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((UserInfo) null, (Continuation) this);
    }
}
