package com.android.systemui.util.settings;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SettingsProxyExt$observerFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $names;
    final /* synthetic */ UserSettingsProxy $this_observerFlow;
    final /* synthetic */ int $userId;
    int I$0;
    int I$1;
    int I$2;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingsProxyExt$observerFlow$1(String[] strArr, UserSettingsProxy userSettingsProxy, int i, Continuation continuation) {
        super(2, continuation);
        this.$names = strArr;
        this.$this_observerFlow = userSettingsProxy;
        this.$userId = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$1(UserSettingsProxy userSettingsProxy, SettingsProxyExt$observerFlow$1$observer$1 settingsProxyExt$observerFlow$1$observer$1) {
        userSettingsProxy.unregisterContentObserverAsync(settingsProxyExt$observerFlow$1$observer$1);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SettingsProxyExt$observerFlow$1 settingsProxyExt$observerFlow$1 = new SettingsProxyExt$observerFlow$1(this.$names, this.$this_observerFlow, this.$userId, continuation);
        settingsProxyExt$observerFlow$1.L$0 = obj;
        return settingsProxyExt$observerFlow$1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0068, code lost:
    
        if (r11 == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0083, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r9, r1, r10) == r0) goto L18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0068 -> B:12:0x006b). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L33
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r11)
            goto L86
        L11:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L19:
            int r1 = r10.I$2
            int r4 = r10.I$1
            int r5 = r10.I$0
            java.lang.Object r6 = r10.L$3
            com.android.systemui.util.settings.UserSettingsProxy r6 = (com.android.systemui.util.settings.UserSettingsProxy) r6
            java.lang.Object r7 = r10.L$2
            java.lang.String[] r7 = (java.lang.String[]) r7
            java.lang.Object r8 = r10.L$1
            com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$observer$1 r8 = (com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$observer$1) r8
            java.lang.Object r9 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r9 = (kotlinx.coroutines.channels.ProducerScope) r9
            kotlin.ResultKt.throwOnFailure(r11)
            goto L6b
        L33:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.channels.ProducerScope r11 = (kotlinx.coroutines.channels.ProducerScope) r11
            com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$observer$1 r1 = new com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$observer$1
            r1.<init>()
            java.lang.String[] r4 = r10.$names
            com.android.systemui.util.settings.UserSettingsProxy r5 = r10.$this_observerFlow
            int r6 = r10.$userId
            int r7 = r4.length
            r8 = 0
            r9 = r8
            r8 = r1
            r1 = r7
            r7 = r4
            r4 = r9
            r9 = r6
            r6 = r5
            r5 = r9
            r9 = r11
        L50:
            if (r4 >= r1) goto L6d
            r11 = r7[r4]
            r10.L$0 = r9
            r10.L$1 = r8
            r10.L$2 = r7
            r10.L$3 = r6
            r10.I$0 = r5
            r10.I$1 = r4
            r10.I$2 = r1
            r10.label = r3
            java.lang.Object r11 = r6.registerContentObserverForUser(r11, r8, r5, r10)
            if (r11 != r0) goto L6b
            goto L85
        L6b:
            int r4 = r4 + r3
            goto L50
        L6d:
            com.android.systemui.util.settings.UserSettingsProxy r11 = r10.$this_observerFlow
            com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0 r1 = new com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0
            r1.<init>(r11, r8)
            r11 = 0
            r10.L$0 = r11
            r10.L$1 = r11
            r10.L$2 = r11
            r10.L$3 = r11
            r10.label = r2
            java.lang.Object r10 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r9, r1, r10)
            if (r10 != r0) goto L86
        L85:
            return r0
        L86:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((SettingsProxyExt$observerFlow$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
