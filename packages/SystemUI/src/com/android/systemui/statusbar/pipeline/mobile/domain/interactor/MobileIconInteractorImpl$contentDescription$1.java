package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.statusbar.pipeline.mobile.ui.util.SamsungMobileIcons;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class MobileIconInteractorImpl$contentDescription$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;
    final /* synthetic */ MobileIconInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileIconInteractorImpl$contentDescription$1(MobileIconInteractorImpl mobileIconInteractorImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = mobileIconInteractorImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        MobileIconInteractorImpl$contentDescription$1 mobileIconInteractorImpl$contentDescription$1 = new MobileIconInteractorImpl$contentDescription$1(this.this$0, (Continuation) obj3);
        mobileIconInteractorImpl$contentDescription$1.I$0 = intValue;
        mobileIconInteractorImpl$contentDescription$1.I$1 = intValue2;
        return mobileIconInteractorImpl$contentDescription$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int[] iArr;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        int i2 = this.I$1;
        this.this$0.signalIconResource.getClass();
        if (i2 == 5) {
            iArr = AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH;
        } else {
            SamsungMobileIcons.Companion.getClass();
            iArr = SamsungMobileIcons.PHONE_SIGNAL_STRENGTH_5LEVEL;
        }
        return new ContentDescription.Resource(i >= iArr.length ? iArr[iArr.length - 1] : iArr[i]);
    }
}
