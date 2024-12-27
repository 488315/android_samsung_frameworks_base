package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer.Binding $binding;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceLegacySettingSyncer this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6(KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer, KeyguardQuickAffordanceLegacySettingSyncer.Binding binding, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceLegacySettingSyncer;
        this.$binding = binding;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6 keyguardQuickAffordanceLegacySettingSyncer$startSyncing$6 = new KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6(this.this$0, this.$binding, continuation);
        keyguardQuickAffordanceLegacySettingSyncer$startSyncing$6.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardQuickAffordanceLegacySettingSyncer$startSyncing$6;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((KeyguardQuickAffordanceLegacySettingSyncer$startSyncing$6) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            if ((this.this$0.secureSettings.getIntForUser(this.$binding.settingsKey, 0, -2) != 0) != z) {
                KeyguardQuickAffordanceLegacySettingSyncer keyguardQuickAffordanceLegacySettingSyncer = this.this$0;
                String str = this.$binding.settingsKey;
                this.label = 1;
                keyguardQuickAffordanceLegacySettingSyncer.getClass();
                Object withContext = BuildersKt.withContext(keyguardQuickAffordanceLegacySettingSyncer.backgroundDispatcher, new KeyguardQuickAffordanceLegacySettingSyncer$set$2(keyguardQuickAffordanceLegacySettingSyncer, str, z, null), this);
                if (withContext != obj2) {
                    withContext = Unit.INSTANCE;
                }
                if (withContext == obj2) {
                    return obj2;
                }
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
