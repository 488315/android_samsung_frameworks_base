package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import com.android.systemui.deviceentry.shared.model.FaceFailureMessage;
import com.android.systemui.deviceentry.shared.model.FaceLockoutMessage;
import com.android.systemui.deviceentry.shared.model.FaceMessage;
import com.android.systemui.deviceentry.shared.model.FaceTimeoutMessage;
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
final class BouncerMessageViewModel$listenForFaceMessages$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BouncerMessageViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageViewModel$listenForFaceMessages$2(BouncerMessageViewModel bouncerMessageViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerMessageViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerMessageViewModel$listenForFaceMessages$2 bouncerMessageViewModel$listenForFaceMessages$2 = new BouncerMessageViewModel$listenForFaceMessages$2(this.this$0, continuation);
        bouncerMessageViewModel$listenForFaceMessages$2.L$0 = obj;
        return bouncerMessageViewModel$listenForFaceMessages$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageViewModel$listenForFaceMessages$2) create((Triple) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00b6, code lost:
    
        if (r11.emit(r1, r10) == r0) goto L27;
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
            FaceMessage faceMessage = (FaceMessage) triple.component1();
            AuthenticationMethodModel authenticationMethodModel = (AuthenticationMethodModel) triple.component2();
            boolean zBooleanValue = ((Boolean) triple.component3()).booleanValue();
            boolean zIsFaceAuthStrong = this.this$0.faceAuthInteractor.isFaceAuthStrong();
            BouncerMessageViewModel bouncerMessageViewModel = this.this$0;
            BouncerMessageStrings.INSTANCE.getClass();
            String string = bouncerMessageViewModel.applicationContext.getString(((Number) BouncerMessageStrings.defaultMessage(authenticationMethodModel, zBooleanValue).getFirst()).intValue());
            BouncerMessageViewModel bouncerMessageViewModel2 = this.this$0;
            bouncerMessageViewModel2.message.updateState(null, faceMessage instanceof FaceTimeoutMessage ? new MessageViewModel(string, faceMessage.message, true) : faceMessage instanceof FaceLockoutMessage ? zIsFaceAuthStrong ? bouncerMessageViewModel2.toMessage(BouncerMessageStrings.class3AuthLockedOut(authenticationMethodModel)) : bouncerMessageViewModel2.toMessage(BouncerMessageStrings.faceLockedOut(authenticationMethodModel, zBooleanValue)) : faceMessage instanceof FaceFailureMessage ? bouncerMessageViewModel2.toMessage(BouncerMessageStrings.incorrectFaceInput(authenticationMethodModel, zBooleanValue)) : new MessageViewModel(string, faceMessage.message, false));
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
