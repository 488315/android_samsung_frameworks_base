package com.android.systemui.communal.data.repository;

import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalWidgetRepositoryLocalImpl$restoreWidgets$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Map<Integer, Integer> $oldToNewWidgetIdMap;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryLocalImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryLocalImpl$restoreWidgets$1(CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl, Map<Integer, Integer> map, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryLocalImpl;
        this.$oldToNewWidgetIdMap = map;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryLocalImpl$restoreWidgets$1(this.this$0, this.$oldToNewWidgetIdMap, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryLocalImpl$restoreWidgets$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01f9 A[SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r23) {
        /*
            Method dump skipped, instructions count: 836
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
