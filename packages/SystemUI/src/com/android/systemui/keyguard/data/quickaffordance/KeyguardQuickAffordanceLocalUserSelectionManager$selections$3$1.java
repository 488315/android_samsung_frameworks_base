package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ KeyguardQuickAffordanceLocalUserSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1(KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardQuickAffordanceLocalUserSelectionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1 keyguardQuickAffordanceLocalUserSelectionManager$selections$3$1 = new KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1(this.this$0, continuation);
        keyguardQuickAffordanceLocalUserSelectionManager$selections$3$1.L$0 = obj;
        return keyguardQuickAffordanceLocalUserSelectionManager$selections$3$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x007c, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r8, r7) == r0) goto L16;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L24
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r8)
            goto L7f
        L10:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L18:
            java.lang.Object r1 = r7.L$1
            android.content.SharedPreferences$OnSharedPreferenceChangeListener r1 = (android.content.SharedPreferences.OnSharedPreferenceChangeListener) r1
            java.lang.Object r3 = r7.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r8)
            goto L6a
        L24:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager r1 = r7.this$0
            int r4 = com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager.$r8$clinit
            com.android.systemui.settings.UserTracker r4 = r1.userTracker
            com.android.systemui.settings.UserTrackerImpl r4 = (com.android.systemui.settings.UserTrackerImpl) r4
            int r4 = r4.getUserId()
            com.android.systemui.settings.UserFileManager r5 = r1.userFileManager
            com.android.systemui.settings.UserFileManagerImpl r5 = (com.android.systemui.settings.UserFileManagerImpl) r5
            java.lang.String r6 = "quick_affordance_selections"
            android.content.SharedPreferences r4 = r5.getSharedPreferences$1(r4, r6)
            r1.sharedPrefs = r4
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1$listener$1 r1 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1$listener$1
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager r4 = r7.this$0
            r1.<init>()
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager r4 = r7.this$0
            android.content.SharedPreferences r4 = r4.sharedPrefs
            r4.registerOnSharedPreferenceChangeListener(r1)
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager r4 = r7.this$0
            java.util.Map r4 = r4.getSelections()
            r7.L$0 = r8
            r7.L$1 = r1
            r7.label = r3
            r3 = r8
            kotlinx.coroutines.channels.ChannelCoroutine r3 = (kotlinx.coroutines.channels.ChannelCoroutine) r3
            kotlinx.coroutines.channels.Channel r3 = r3._channel
            java.lang.Object r3 = r3.send(r4, r7)
            if (r3 != r0) goto L69
            goto L7e
        L69:
            r3 = r8
        L6a:
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1$1 r8 = new com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1$1
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager r4 = r7.this$0
            r8.<init>()
            r1 = 0
            r7.L$0 = r1
            r7.L$1 = r1
            r7.label = r2
            java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r8, r7)
            if (r7 != r0) goto L7f
        L7e:
            return r0
        L7f:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
