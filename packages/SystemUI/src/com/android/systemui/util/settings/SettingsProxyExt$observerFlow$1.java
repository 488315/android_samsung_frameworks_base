package com.android.systemui.util.settings;

import android.database.ContentObserver;
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

final class SettingsProxyExt$observerFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $names;
    final /* synthetic */ UserSettingsProxy $this_observerFlow;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;

    public SettingsProxyExt$observerFlow$1(String[] strArr, UserSettingsProxy userSettingsProxy, int i, Continuation continuation) {
        super(2, continuation);
        this.$names = strArr;
        this.$this_observerFlow = userSettingsProxy;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SettingsProxyExt$observerFlow$1 settingsProxyExt$observerFlow$1 = new SettingsProxyExt$observerFlow$1(this.$names, this.$this_observerFlow, this.$userId, continuation);
        settingsProxyExt$observerFlow$1.L$0 = obj;
        return settingsProxyExt$observerFlow$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ContentObserver() { // from class: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$observer$1
                {
                    super(null);
                }

                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            String[] strArr = this.$names;
            UserSettingsProxy userSettingsProxy = this.$this_observerFlow;
            int i2 = this.$userId;
            for (String str : strArr) {
                userSettingsProxy.registerContentObserverForUserSync(str, (ContentObserver) r1, i2);
            }
            final UserSettingsProxy userSettingsProxy2 = this.$this_observerFlow;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1.2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m2354invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m2354invoke() {
                    UserSettingsProxy.this.unregisterContentObserverSync(r1);
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((SettingsProxyExt$observerFlow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
