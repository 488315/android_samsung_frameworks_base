package com.android.systemui.util.settings;

import android.database.ContentObserver;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1", m277f = "SettingsProxyExt.kt", m278l = {44}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SettingsProxyExt$observerFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $names;
    final /* synthetic */ SettingsProxy $this_observerFlow;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsProxyExt$observerFlow$1(String[] strArr, SettingsProxy settingsProxy, int i, Continuation<? super SettingsProxyExt$observerFlow$1> continuation) {
        super(2, continuation);
        this.$names = strArr;
        this.$this_observerFlow = settingsProxy;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SettingsProxyExt$observerFlow$1 settingsProxyExt$observerFlow$1 = new SettingsProxyExt$observerFlow$1(this.$names, this.$this_observerFlow, this.$userId, continuation);
        settingsProxyExt$observerFlow$1.L$0 = obj;
        return settingsProxyExt$observerFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SettingsProxyExt$observerFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.database.ContentObserver, com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$observer$1] */
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
                public final void onChange(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2872trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            String[] strArr = this.$names;
            SettingsProxy settingsProxy = this.$this_observerFlow;
            int i2 = this.$userId;
            for (String str : strArr) {
                settingsProxy.registerContentObserverForUser(str, r1, i2);
            }
            final SettingsProxy settingsProxy2 = this.$this_observerFlow;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SettingsProxy.this.unregisterContentObserver(r1);
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
