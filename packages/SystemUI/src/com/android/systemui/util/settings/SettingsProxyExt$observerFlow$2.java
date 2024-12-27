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

final class SettingsProxyExt$observerFlow$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $names;
    final /* synthetic */ SettingsProxy $this_observerFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsProxyExt$observerFlow$2(String[] strArr, SettingsProxy settingsProxy, Continuation continuation) {
        super(2, continuation);
        this.$names = strArr;
        this.$this_observerFlow = settingsProxy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SettingsProxyExt$observerFlow$2 settingsProxyExt$observerFlow$2 = new SettingsProxyExt$observerFlow$2(this.$names, this.$this_observerFlow, continuation);
        settingsProxyExt$observerFlow$2.L$0 = obj;
        return settingsProxyExt$observerFlow$2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.database.ContentObserver, com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2$observer$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ContentObserver() { // from class: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2$observer$1
                {
                    super(null);
                }

                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            String[] strArr = this.$names;
            SettingsProxy settingsProxy = this.$this_observerFlow;
            for (String str : strArr) {
                settingsProxy.registerContentObserverSync(str, (ContentObserver) r1);
            }
            final SettingsProxy settingsProxy2 = this.$this_observerFlow;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m2355invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m2355invoke() {
                    SettingsProxy.this.unregisterContentObserverSync(r1);
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
        return ((SettingsProxyExt$observerFlow$2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
