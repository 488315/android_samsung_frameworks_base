package com.android.systemui.util.settings;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SettingsProxyExt$observerFlow$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $names;
    final /* synthetic */ SettingsProxy $this_observerFlow;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsProxyExt$observerFlow$2(String[] strArr, SettingsProxy settingsProxy, Continuation continuation) {
        super(2, continuation);
        this.$names = strArr;
        this.$this_observerFlow = settingsProxy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$1(SettingsProxy settingsProxy, SettingsProxyExt$observerFlow$2$observer$1 settingsProxyExt$observerFlow$2$observer$1) {
        settingsProxy.unregisterContentObserverAsync(settingsProxyExt$observerFlow$2$observer$1);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SettingsProxyExt$observerFlow$2 settingsProxyExt$observerFlow$2 = new SettingsProxyExt$observerFlow$2(this.$names, this.$this_observerFlow, continuation);
        settingsProxyExt$observerFlow$2.L$0 = obj;
        return settingsProxyExt$observerFlow$2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x005f, code lost:
    
        if (r10 == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x007a, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r9) == r0) goto L18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0064  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x005f -> B:12:0x0062). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L31
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r10)
            goto L7d
        L11:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L19:
            int r1 = r9.I$1
            int r4 = r9.I$0
            java.lang.Object r5 = r9.L$3
            com.android.systemui.util.settings.SettingsProxy r5 = (com.android.systemui.util.settings.SettingsProxy) r5
            java.lang.Object r6 = r9.L$2
            java.lang.String[] r6 = (java.lang.String[]) r6
            java.lang.Object r7 = r9.L$1
            com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2$observer$1 r7 = (com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2$observer$1) r7
            java.lang.Object r8 = r9.L$0
            kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
            kotlin.ResultKt.throwOnFailure(r10)
            goto L62
        L31:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.channels.ProducerScope r10 = (kotlinx.coroutines.channels.ProducerScope) r10
            com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2$observer$1 r1 = new com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2$observer$1
            r1.<init>()
            java.lang.String[] r4 = r9.$names
            com.android.systemui.util.settings.SettingsProxy r5 = r9.$this_observerFlow
            int r6 = r4.length
            r7 = 0
            r8 = r7
            r7 = r1
            r1 = r6
            r6 = r4
            r4 = r8
            r8 = r10
        L49:
            if (r4 >= r1) goto L64
            r10 = r6[r4]
            r9.L$0 = r8
            r9.L$1 = r7
            r9.L$2 = r6
            r9.L$3 = r5
            r9.I$0 = r4
            r9.I$1 = r1
            r9.label = r3
            java.lang.Object r10 = r5.registerContentObserver(r10, r7, r9)
            if (r10 != r0) goto L62
            goto L7c
        L62:
            int r4 = r4 + r3
            goto L49
        L64:
            com.android.systemui.util.settings.SettingsProxy r10 = r9.$this_observerFlow
            com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0 r1 = new com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0
            r1.<init>(r10, r7)
            r10 = 0
            r9.L$0 = r10
            r9.L$1 = r10
            r9.L$2 = r10
            r9.L$3 = r10
            r9.label = r2
            java.lang.Object r9 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r9)
            if (r9 != r0) goto L7d
        L7c:
            return r0
        L7d:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((SettingsProxyExt$observerFlow$2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
