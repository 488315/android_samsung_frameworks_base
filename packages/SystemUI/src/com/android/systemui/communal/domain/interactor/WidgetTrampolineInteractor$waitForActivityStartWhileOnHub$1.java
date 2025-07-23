package com.android.systemui.communal.domain.interactor;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ WidgetTrampolineInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WidgetTrampolineInteractor$waitForActivityStartWhileOnHub$1(WidgetTrampolineInteractor widgetTrampolineInteractor, Continuation continuation) {
        super(continuation);
        this.this$0 = widgetTrampolineInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        WidgetTrampolineInteractor widgetTrampolineInteractor = this.this$0;
        int i = WidgetTrampolineInteractor.$r8$clinit;
        return widgetTrampolineInteractor.waitForActivityStartWhileOnHub(this);
    }
}
