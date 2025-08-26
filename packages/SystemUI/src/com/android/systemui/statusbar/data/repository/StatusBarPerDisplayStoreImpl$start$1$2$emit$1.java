package com.android.systemui.statusbar.data.repository;

import com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl;
import java.util.Set;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes3.dex */
final class StatusBarPerDisplayStoreImpl$start$1$2$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ StatusBarPerDisplayStoreImpl.AnonymousClass1.AnonymousClass2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarPerDisplayStoreImpl$start$1$2$emit$1(StatusBarPerDisplayStoreImpl.AnonymousClass1.AnonymousClass2 anonymousClass2, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((Set) null, (Continuation) this);
    }
}
