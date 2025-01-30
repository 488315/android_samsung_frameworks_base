package com.android.systemui.keyguard.animator;

import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$initAnimatorSet$1$1$onAnimationEnd$1", m277f = "KeyguardEditModeAnimatorController.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getUsbConnectionTypeInternal}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.animator.KeyguardEditModeAnimatorController$initAnimatorSet$1$1$onAnimationEnd$1 */
/* loaded from: classes.dex */
final class C1526x3f2deed0 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardEditModeAnimatorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1526x3f2deed0(KeyguardEditModeAnimatorController keyguardEditModeAnimatorController, Continuation<? super C1526x3f2deed0> continuation) {
        super(2, continuation);
        this.this$0 = keyguardEditModeAnimatorController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1526x3f2deed0(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1526x3f2deed0) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = this.this$0;
            ((KeyguardEditModeControllerImpl) keyguardEditModeAnimatorController.keyguardEditModeController).startEditActivity(keyguardEditModeAnimatorController.getParentView().getContext(), false);
            long j = ((KeyguardEditModeControllerImpl) this.this$0.keyguardEditModeController).settingsHelper.isUltraPowerSavingMode() ? 1000L : 4000L;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController2 = this.this$0;
        int i2 = KeyguardEditModeAnimatorController.$r8$clinit;
        keyguardEditModeAnimatorController2.startCancelAnimation();
        return Unit.INSTANCE;
    }
}
