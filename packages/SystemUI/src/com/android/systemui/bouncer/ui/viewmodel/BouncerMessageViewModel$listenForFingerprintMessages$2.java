package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import com.android.systemui.deviceentry.shared.model.FingerprintFailureMessage;
import com.android.systemui.deviceentry.shared.model.FingerprintLockoutMessage;
import com.android.systemui.deviceentry.shared.model.FingerprintMessage;
import com.android.systemui.util.DelayableMarqueeTextView;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes.dex */
final class BouncerMessageViewModel$listenForFingerprintMessages$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$listenForFingerprintMessages$2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerMessageViewModel$listenForFingerprintMessages$2 bouncerMessageViewModel$listenForFingerprintMessages$2 = new BouncerMessageViewModel$listenForFingerprintMessages$2(this.this$0, continuation);
        bouncerMessageViewModel$listenForFingerprintMessages$2.L$0 = obj;
        return bouncerMessageViewModel$listenForFingerprintMessages$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageViewModel$listenForFingerprintMessages$2) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0097, code lost:
    
        if (r9.emit(r1, r8) == r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Triple triple = (Triple) this.L$0;
            FingerprintMessage fingerprintMessage = (FingerprintMessage) triple.component1();
            AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) triple.component2();
            boolean zBooleanValue = ((Boolean) triple.component3()).booleanValue();
            BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
            BouncerMessageStrings.INSTANCE.getClass();
            String string = bouncerMessageViewModel.applicationContext.getString(((Number) BouncerMessageStrings.defaultMessage(authenticationMethodModel, zBooleanValue).getFirst()).intValue());
            BouncerMessageViewModel bouncerMessageViewModel2 = this.this$0;
            bouncerMessageViewModel2.message.updateState(null, fingerprintMessage instanceof FingerprintLockoutMessage ? bouncerMessageViewModel2.toMessage(BouncerMessageStrings.class3AuthLockedOut(authenticationMethodModel)) : fingerprintMessage instanceof FingerprintFailureMessage ? bouncerMessageViewModel2.toMessage(BouncerMessageStrings.incorrectFingerprintInput(authenticationMethodModel)) : new MessageViewModel(string, fingerprintMessage.message, false));
            this.label = 1;
            if (DelayKt.delay(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        SharedFlowImpl sharedFlowImpl = this.this$0.resetToDefault;
        Boolean bool = Boolean.TRUE;
        this.label = 2;
    }
}
