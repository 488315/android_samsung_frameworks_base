package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<String> $affordanceIds;
    final /* synthetic */ CustomizationProviderClient $client;
    final /* synthetic */ String $slotId;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    public KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1(CustomizationProviderClient customizationProviderClient, String str, List<String> list, Continuation continuation) {
        super(2, continuation);
        this.$client = customizationProviderClient;
        this.$slotId = str;
        this.$affordanceIds = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1(this.$client, this.$slotId, this.$affordanceIds, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L29
            if (r1 == r3) goto L25
            if (r1 != r2) goto L1d
            java.lang.Object r1 = r6.L$2
            java.util.Iterator r1 = (java.util.Iterator) r1
            java.lang.Object r3 = r6.L$1
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r4 = r6.L$0
            com.android.systemui.shared.customization.data.content.CustomizationProviderClient r4 = (com.android.systemui.shared.customization.data.content.CustomizationProviderClient) r4
            kotlin.ResultKt.throwOnFailure(r7)
            r7 = r4
            goto L4a
        L1d:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L25:
            kotlin.ResultKt.throwOnFailure(r7)
            goto L3b
        L29:
            kotlin.ResultKt.throwOnFailure(r7)
            com.android.systemui.shared.customization.data.content.CustomizationProviderClient r7 = r6.$client
            java.lang.String r1 = r6.$slotId
            r6.label = r3
            com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl r7 = (com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl) r7
            java.lang.Object r7 = r7.deleteAllSelections(r1, r6)
            if (r7 != r0) goto L3b
            return r0
        L3b:
            java.util.List<java.lang.String> r7 = r6.$affordanceIds
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            com.android.systemui.shared.customization.data.content.CustomizationProviderClient r1 = r6.$client
            java.lang.String r3 = r6.$slotId
            java.util.Iterator r7 = r7.iterator()
            r5 = r1
            r1 = r7
            r7 = r5
        L4a:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L67
            java.lang.Object r4 = r1.next()
            java.lang.String r4 = (java.lang.String) r4
            r6.L$0 = r7
            r6.L$1 = r3
            r6.L$2 = r1
            r6.label = r2
            com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl r7 = (com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl) r7
            java.lang.Object r4 = r7.insertSelection(r3, r4, r6)
            if (r4 != r0) goto L4a
            return r0
        L67:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager$setSelections$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
