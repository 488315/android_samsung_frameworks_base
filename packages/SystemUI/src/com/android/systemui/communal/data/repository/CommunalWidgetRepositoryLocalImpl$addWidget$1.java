package com.android.systemui.communal.data.repository;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalWidgetRepositoryLocalImpl$addWidget$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WidgetConfigurator $configurator;
    final /* synthetic */ ComponentName $provider;
    final /* synthetic */ Integer $rank;
    final /* synthetic */ UserHandle $user;
    Object L$0;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryLocalImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryLocalImpl$addWidget$1(CommunalWidgetRepositoryLocalImpl communalWidgetRepositoryLocalImpl, ComponentName componentName, UserHandle userHandle, WidgetConfigurator widgetConfigurator, Integer num, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryLocalImpl;
        this.$provider = componentName;
        this.$user = userHandle;
        this.$configurator = widgetConfigurator;
        this.$rank = num;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryLocalImpl$addWidget$1(this.this$0, this.$provider, this.$user, this.$configurator, this.$rank, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryLocalImpl$addWidget$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00e8  */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v8 */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r14) {
        /*
            Method dump skipped, instructions count: 349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$addWidget$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
