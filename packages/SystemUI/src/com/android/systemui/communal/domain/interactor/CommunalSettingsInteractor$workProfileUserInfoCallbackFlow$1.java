package com.android.systemui.communal.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CommunalSettingsInteractor this$0;

    public CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1(CommunalSettingsInteractor communalSettingsInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSettingsInteractor;
    }

    public static final void invokeSuspend$send(ProducerScope producerScope, List list) {
        Object obj;
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (((UserInfo) obj).isManagedProfile()) {
                    break;
                }
            }
        }
        ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(obj);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1 communalSettingsInteractor$workProfileUserInfoCallbackFlow$1 = new CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1(this.this$0, continuation);
        communalSettingsInteractor$workProfileUserInfoCallbackFlow$1.L$0 = obj;
        return communalSettingsInteractor$workProfileUserInfoCallbackFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1$callback$1
                @Override // com.android.systemui.settings.UserTracker.Callback
                public final void onProfilesChanged(List list) {
                    CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1.invokeSuspend$send(ProducerScope.this, list);
                }
            };
            CommunalSettingsInteractor communalSettingsInteractor = this.this$0;
            ((UserTrackerImpl) communalSettingsInteractor.userTracker).addCallback(r1, communalSettingsInteractor.bgExecutor);
            invokeSuspend$send(producerScope, ((UserTrackerImpl) this.this$0.userTracker).getUserProfiles());
            final CommunalSettingsInteractor communalSettingsInteractor2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor$workProfileUserInfoCallbackFlow$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((UserTrackerImpl) CommunalSettingsInteractor.this.userTracker).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
