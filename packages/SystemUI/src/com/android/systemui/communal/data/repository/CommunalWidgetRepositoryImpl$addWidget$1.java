package com.android.systemui.communal.data.repository;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class CommunalWidgetRepositoryImpl$addWidget$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WidgetConfigurator $configurator;
    final /* synthetic */ int $priority;
    final /* synthetic */ ComponentName $provider;
    final /* synthetic */ UserHandle $user;
    Object L$0;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryImpl$addWidget$1(CommunalWidgetRepositoryImpl communalWidgetRepositoryImpl, ComponentName componentName, UserHandle userHandle, WidgetConfigurator widgetConfigurator, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryImpl;
        this.$provider = componentName;
        this.$user = userHandle;
        this.$configurator = widgetConfigurator;
        this.$priority = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryImpl$addWidget$1(this.this$0, this.$provider, this.$user, this.$configurator, this.$priority, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryImpl$addWidget$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00f1  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl$addWidget$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
