package com.android.systemui.communal.ui.viewmodel;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class CommunalAppWidgetViewModel$handleUpdateSize$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CommunalAppWidgetViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalAppWidgetViewModel$handleUpdateSize$1(CommunalAppWidgetViewModel communalAppWidgetViewModel, Continuation continuation) {
        super(continuation);
        this.this$0 = communalAppWidgetViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return CommunalAppWidgetViewModel.access$handleUpdateSize(this.this$0, null, null, this);
    }
}
