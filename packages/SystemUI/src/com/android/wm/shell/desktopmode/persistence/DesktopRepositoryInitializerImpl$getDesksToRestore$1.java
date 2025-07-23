package com.android.wm.shell.desktopmode.persistence;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopRepositoryInitializerImpl$getDesksToRestore$1 extends ContinuationImpl {
    int I$0;
    int I$1;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ DesktopRepositoryInitializerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopRepositoryInitializerImpl$getDesksToRestore$1(DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = desktopRepositoryInitializerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return DesktopRepositoryInitializerImpl.access$getDesksToRestore(this.this$0, null, 0, this);
    }
}
