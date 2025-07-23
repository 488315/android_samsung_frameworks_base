package com.android.systemui.statusbar.phone.ongoingcall.domain.interactor;

import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OngoingCallInteractor$createOngoingCallStateFlow$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ OngoingCallInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingCallInteractor$createOngoingCallStateFlow$1(OngoingCallInteractor ongoingCallInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = ongoingCallInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        OngoingCallInteractor$createOngoingCallStateFlow$1 ongoingCallInteractor$createOngoingCallStateFlow$1 = new OngoingCallInteractor$createOngoingCallStateFlow$1(this.this$0, (Continuation) obj3);
        ongoingCallInteractor$createOngoingCallStateFlow$1.L$0 = (ActiveNotificationModel) obj;
        ongoingCallInteractor$createOngoingCallStateFlow$1.Z$0 = booleanValue;
        return ongoingCallInteractor$createOngoingCallStateFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) this.L$0;
        boolean z = this.Z$0;
        Logger logger = this.this$0.logger;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, new OngoingCallInteractor$$ExternalSyntheticLambda0(), null);
        obtain.setInt1(activeNotificationModel.uid);
        obtain.setLong1(activeNotificationModel.whenTime);
        obtain.setBool1(activeNotificationModel.statusBarChipIconView != null);
        obtain.setBool2(z);
        logger.getBuffer().commit(obtain);
        return new OngoingCallModel.InCall(activeNotificationModel.whenTime, activeNotificationModel.statusBarChipIconView, activeNotificationModel.contentIntent, activeNotificationModel.key, activeNotificationModel.appName, activeNotificationModel.promotedContent, z, activeNotificationModel.instanceId);
    }
}
