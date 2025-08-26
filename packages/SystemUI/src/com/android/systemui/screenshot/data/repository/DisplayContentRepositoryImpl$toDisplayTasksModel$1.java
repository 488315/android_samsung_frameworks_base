package com.android.systemui.screenshot.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class DisplayContentRepositoryImpl$toDisplayTasksModel$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DisplayContentRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayContentRepositoryImpl$toDisplayTasksModel$1(DisplayContentRepositoryImpl displayContentRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = displayContentRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return DisplayContentRepositoryImpl.access$toDisplayTasksModel(this.this$0, 0, null, this);
    }
}
