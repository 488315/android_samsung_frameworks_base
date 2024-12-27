package com.android.systemui.user.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class GuestUserInteractor$onDeviceBootCompleted$1 extends SuspendLambda implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ GuestUserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuestUserInteractor$onDeviceBootCompleted$1(GuestUserInteractor guestUserInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = guestUserInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GuestUserInteractor$onDeviceBootCompleted$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GuestUserInteractor$onDeviceBootCompleted$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0071  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L27
            if (r1 == r4) goto L23
            if (r1 == r3) goto L1b
            if (r1 != r2) goto L13
            kotlin.ResultKt.throwOnFailure(r6)
            goto L7f
        L13:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L1b:
            java.lang.Object r1 = r5.L$0
            com.android.systemui.user.domain.interactor.GuestUserInteractor r1 = (com.android.systemui.user.domain.interactor.GuestUserInteractor) r1
            kotlin.ResultKt.throwOnFailure(r6)
            goto L67
        L23:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L3f
        L27:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.user.domain.interactor.GuestUserInteractor r6 = r5.this$0
            int r1 = com.android.systemui.user.domain.interactor.GuestUserInteractor.$r8$clinit
            boolean r6 = r6.isDeviceAllowedToAddGuest()
            if (r6 == 0) goto L42
            com.android.systemui.user.domain.interactor.GuestUserInteractor r6 = r5.this$0
            r5.label = r4
            java.lang.Object r5 = r6.guaranteePresent(r5)
            if (r5 != r0) goto L3f
            return r0
        L3f:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L42:
            com.android.systemui.user.domain.interactor.GuestUserInteractor r6 = r5.this$0
            r5.L$0 = r6
            r5.label = r3
            kotlinx.coroutines.CancellableContinuationImpl r1 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r3 = kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt.intercepted(r5)
            r1.<init>(r3, r4)
            r1.initCancellability()
            com.android.systemui.user.domain.interactor.GuestUserInteractor$onDeviceBootCompleted$1$1$callback$1 r3 = new com.android.systemui.user.domain.interactor.GuestUserInteractor$onDeviceBootCompleted$1$1$callback$1
            r3.<init>()
            com.android.systemui.statusbar.policy.DeviceProvisionedController r6 = r6.deviceProvisionedController
            com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl r6 = (com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl) r6
            r6.addCallback(r3)
            java.lang.Object r6 = r1.getResult()
            if (r6 != r0) goto L67
            return r0
        L67:
            com.android.systemui.user.domain.interactor.GuestUserInteractor r6 = r5.this$0
            int r1 = com.android.systemui.user.domain.interactor.GuestUserInteractor.$r8$clinit
            boolean r6 = r6.isDeviceAllowedToAddGuest()
            if (r6 == 0) goto L7f
            com.android.systemui.user.domain.interactor.GuestUserInteractor r6 = r5.this$0
            r1 = 0
            r5.L$0 = r1
            r5.label = r2
            java.lang.Object r5 = r6.guaranteePresent(r5)
            if (r5 != r0) goto L7f
            return r0
        L7f:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.user.domain.interactor.GuestUserInteractor$onDeviceBootCompleted$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
