package com.android.systemui.accessibility.data.repository;

import android.content.Context;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AccessibilityQsShortcutsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2(AccessibilityQsShortcutsRepositoryImpl accessibilityQsShortcutsRepositoryImpl, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = accessibilityQsShortcutsRepositoryImpl;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2 accessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2 = new AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2(this.this$0, this.$context, continuation);
        accessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2.L$0 = obj;
        return accessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0051, code lost:
    
        if (r12 == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r11.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L24
            if (r1 == r3) goto L1c
            if (r1 != r2) goto L14
            java.lang.Object r11 = r11.L$0
            java.util.Set r11 = (java.util.Set) r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto L64
        L14:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L1c:
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.Deferred r1 = (kotlinx.coroutines.Deferred) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L54
        L24:
            kotlin.ResultKt.throwOnFailure(r12)
            java.lang.Object r12 = r11.L$0
            kotlinx.coroutines.CoroutineScope r12 = (kotlinx.coroutines.CoroutineScope) r12
            com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl r1 = r11.this$0
            kotlinx.coroutines.CoroutineDispatcher r4 = r1.backgroundDispatcher
            com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yServiceTileServices$1 r5 = new com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yServiceTileServices$1
            r6 = 0
            r5.<init>(r1, r6)
            r1 = 5
            kotlinx.coroutines.DeferredCoroutine r4 = com.android.app.tracing.coroutines.CoroutineTracingKt.asyncTraced$default(r12, r4, r6, r5, r1)
            com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl r5 = r11.this$0
            kotlinx.coroutines.CoroutineDispatcher r7 = r5.backgroundDispatcher
            com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yShortcutInfoTileServices$1 r8 = new com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yShortcutInfoTileServices$1
            android.content.Context r9 = r11.$context
            r8.<init>(r5, r9, r6)
            kotlinx.coroutines.DeferredCoroutine r1 = com.android.app.tracing.coroutines.CoroutineTracingKt.asyncTraced$default(r12, r7, r6, r8, r1)
            r11.L$0 = r1
            r11.label = r3
            java.lang.Object r12 = r4.awaitInternal(r11)
            if (r12 != r0) goto L54
            goto L60
        L54:
            java.util.Set r12 = (java.util.Set) r12
            r11.L$0 = r12
            r11.label = r2
            java.lang.Object r11 = r1.await(r11)
            if (r11 != r0) goto L61
        L60:
            return r0
        L61:
            r10 = r12
            r12 = r11
            r11 = r10
        L64:
            java.lang.Iterable r12 = (java.lang.Iterable) r12
            java.util.Set r11 = kotlin.collections.SetsKt___SetsKt.plus(r11, r12)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
