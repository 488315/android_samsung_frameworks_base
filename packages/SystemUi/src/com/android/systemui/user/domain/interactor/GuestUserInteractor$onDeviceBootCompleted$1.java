package com.android.systemui.user.domain.interactor;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.GuestUserInteractor$onDeviceBootCompleted$1", m277f = "GuestUserInteractor.kt", m278l = {81, 349, 98}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class GuestUserInteractor$onDeviceBootCompleted$1 extends SuspendLambda implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ GuestUserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GuestUserInteractor$onDeviceBootCompleted$1(GuestUserInteractor guestUserInteractor, Continuation<? super GuestUserInteractor$onDeviceBootCompleted$1> continuation) {
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
    */
    public final Object invokeSuspend(Object obj) {
        GuestUserInteractor guestUserInteractor;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            GuestUserInteractor guestUserInteractor2 = this.this$0;
            int i2 = GuestUserInteractor.$r8$clinit;
            if (guestUserInteractor2.isDeviceAllowedToAddGuest()) {
                GuestUserInteractor guestUserInteractor3 = this.this$0;
                this.label = 1;
                if (guestUserInteractor3.guaranteePresent(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            final GuestUserInteractor guestUserInteractor4 = this.this$0;
            this.L$0 = guestUserInteractor4;
            this.label = 2;
            final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
            cancellableContinuationImpl.initCancellability();
            ((DeviceProvisionedControllerImpl) guestUserInteractor4.deviceProvisionedController).addCallback(new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.user.domain.interactor.GuestUserInteractor$onDeviceBootCompleted$1$1$callback$1
                @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
                public final void onDeviceProvisionedChanged() {
                    int i3 = Result.$r8$clinit;
                    ((CancellableContinuationImpl) CancellableContinuation.this).resumeWith(Unit.INSTANCE);
                    ((DeviceProvisionedControllerImpl) guestUserInteractor4.deviceProvisionedController).removeCallback(this);
                }
            });
            if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
                return coroutineSingletons;
            }
            guestUserInteractor = this.this$0;
            int i3 = GuestUserInteractor.$r8$clinit;
            if (guestUserInteractor.isDeviceAllowedToAddGuest()) {
            }
        } else {
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            if (i == 2) {
                ResultKt.throwOnFailure(obj);
                guestUserInteractor = this.this$0;
                int i32 = GuestUserInteractor.$r8$clinit;
                if (guestUserInteractor.isDeviceAllowedToAddGuest()) {
                    GuestUserInteractor guestUserInteractor5 = this.this$0;
                    this.L$0 = null;
                    this.label = 3;
                    if (guestUserInteractor5.guaranteePresent(this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
        }
        return Unit.INSTANCE;
    }
}
