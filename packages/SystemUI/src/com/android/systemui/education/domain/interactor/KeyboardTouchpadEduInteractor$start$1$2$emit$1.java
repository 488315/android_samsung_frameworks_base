package com.android.systemui.education.domain.interactor;

import com.android.systemui.education.data.model.GestureEduModel;
import com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class KeyboardTouchpadEduInteractor$start$1$2$emit$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyboardTouchpadEduInteractor.C08671.AnonymousClass2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardTouchpadEduInteractor$start$1$2$emit$1(KeyboardTouchpadEduInteractor.C08671.AnonymousClass2 anonymousClass2, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((GestureEduModel) null, (Continuation) this);
    }
}
