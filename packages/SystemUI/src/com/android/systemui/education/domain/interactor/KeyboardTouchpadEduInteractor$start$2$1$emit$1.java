package com.android.systemui.education.domain.interactor;

import com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor;
import com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
final class KeyboardTouchpadEduInteractor$start$2$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ KeyboardTouchpadEduInteractor.AnonymousClass2.AnonymousClass1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyboardTouchpadEduInteractor$start$2$1$emit$1(KeyboardTouchpadEduInteractor.AnonymousClass2.AnonymousClass1 anonymousClass1, Continuation continuation) {
        super(continuation);
        this.this$0 = anonymousClass1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.emit((UserDeviceConnectionStatus) null, (Continuation) this);
    }
}
