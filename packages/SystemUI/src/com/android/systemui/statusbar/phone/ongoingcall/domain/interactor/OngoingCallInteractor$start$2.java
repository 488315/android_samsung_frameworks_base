package com.android.systemui.statusbar.phone.ongoingcall.domain.interactor;

import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OngoingCallInteractor$start$2 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ OngoingCallInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingCallInteractor$start$2(OngoingCallInteractor ongoingCallInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = ongoingCallInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OngoingCallInteractor$start$2 ongoingCallInteractor$start$2 = new OngoingCallInteractor$start$2(this.this$0, continuation);
        ongoingCallInteractor$start$2.Z$0 = ((Boolean) obj).booleanValue();
        return ongoingCallInteractor$start$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((OngoingCallInteractor$start$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        OngoingCallInteractor ongoingCallInteractor = this.this$0;
        ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) ongoingCallInteractor.statusBarModeRepositoryStore.getDefaultDisplay()))._ongoingProcessRequiresStatusBarVisible.updateState(null, Boolean.valueOf(z));
        StatusBarWindowControllerImpl statusBarWindowControllerImpl = (StatusBarWindowControllerImpl) ((StatusBarWindowController) ongoingCallInteractor.statusBarWindowControllerStore.getDefaultDisplay());
        StatusBarWindowControllerImpl.State state = statusBarWindowControllerImpl.mCurrentState;
        state.mOngoingProcessRequiresStatusBarVisible = z;
        statusBarWindowControllerImpl.apply(state);
        return Unit.INSTANCE;
    }
}
