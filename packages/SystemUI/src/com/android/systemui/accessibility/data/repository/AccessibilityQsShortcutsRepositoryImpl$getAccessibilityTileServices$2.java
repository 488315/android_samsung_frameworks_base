package com.android.systemui.accessibility.data.repository;

import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.DeferredCoroutine;

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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Deferred deferredAsyncTraced$default;
        Set set;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            AccessibilityQsShortcutsRepositoryImpl accessibilityQsShortcutsRepositoryImpl = this.this$0;
            DeferredCoroutine deferredCoroutineAsyncTraced$default = CoroutineTracingKt.asyncTraced$default(coroutineScope, accessibilityQsShortcutsRepositoryImpl.backgroundDispatcher, null, new AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yServiceTileServices$1(accessibilityQsShortcutsRepositoryImpl, null), 5);
            AccessibilityQsShortcutsRepositoryImpl accessibilityQsShortcutsRepositoryImpl2 = this.this$0;
            deferredAsyncTraced$default = CoroutineTracingKt.asyncTraced$default(coroutineScope, accessibilityQsShortcutsRepositoryImpl2.backgroundDispatcher, null, new AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yShortcutInfoTileServices$1(accessibilityQsShortcutsRepositoryImpl2, this.$context, null), 5);
            this.L$0 = deferredAsyncTraced$default;
            this.label = 1;
            obj = deferredCoroutineAsyncTraced$default.awaitInternal(this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            set = (Set) this.L$0;
            ResultKt.throwOnFailure(obj);
            return SetsKt___SetsKt.plus(set, (Iterable) obj);
        }
        deferredAsyncTraced$default = (Deferred) this.L$0;
        ResultKt.throwOnFailure(obj);
        Set set2 = (Set) obj;
        this.L$0 = set2;
        this.label = 2;
        Object objAwait = deferredAsyncTraced$default.await(this);
        if (objAwait != coroutineSingletons) {
            obj = objAwait;
            set = set2;
            return SetsKt___SetsKt.plus(set, (Iterable) obj);
        }
        return coroutineSingletons;
    }
}
