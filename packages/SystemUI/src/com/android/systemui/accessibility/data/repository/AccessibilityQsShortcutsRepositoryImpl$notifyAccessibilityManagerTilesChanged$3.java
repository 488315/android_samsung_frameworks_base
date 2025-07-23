package com.android.systemui.accessibility.data.repository;

import android.content.ComponentName;
import android.content.Context;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AccessibilityQsShortcutsRepositoryImpl$notifyAccessibilityManagerTilesChanged$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<ComponentName> $newTiles;
    final /* synthetic */ Context $userContext;
    int label;
    final /* synthetic */ AccessibilityQsShortcutsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessibilityQsShortcutsRepositoryImpl$notifyAccessibilityManagerTilesChanged$3(AccessibilityQsShortcutsRepositoryImpl accessibilityQsShortcutsRepositoryImpl, Context context, List<ComponentName> list, Continuation continuation) {
        super(2, continuation);
        this.this$0 = accessibilityQsShortcutsRepositoryImpl;
        this.$userContext = context;
        this.$newTiles = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AccessibilityQsShortcutsRepositoryImpl$notifyAccessibilityManagerTilesChanged$3(this.this$0, this.$userContext, this.$newTiles, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AccessibilityQsShortcutsRepositoryImpl$notifyAccessibilityManagerTilesChanged$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.manager.notifyQuickSettingsTilesChanged(this.$userContext.getUserId(), this.$newTiles);
        return Unit.INSTANCE;
    }
}
