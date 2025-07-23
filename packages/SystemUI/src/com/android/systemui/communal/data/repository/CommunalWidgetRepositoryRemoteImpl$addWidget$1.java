package com.android.systemui.communal.data.repository;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.communal.widgets.GlanceableHubWidgetManager;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalWidgetRepositoryRemoteImpl$addWidget$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ WidgetConfigurator $configurator;
    final /* synthetic */ ComponentName $provider;
    final /* synthetic */ Integer $rank;
    final /* synthetic */ UserHandle $user;
    int label;
    final /* synthetic */ CommunalWidgetRepositoryRemoteImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetRepositoryRemoteImpl$addWidget$1(CommunalWidgetRepositoryRemoteImpl communalWidgetRepositoryRemoteImpl, ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetRepositoryRemoteImpl;
        this.$provider = componentName;
        this.$user = userHandle;
        this.$rank = num;
        this.$configurator = widgetConfigurator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetRepositoryRemoteImpl$addWidget$1(this.this$0, this.$provider, this.$user, this.$rank, this.$configurator, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetRepositoryRemoteImpl$addWidget$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final GlanceableHubWidgetManager glanceableHubWidgetManager = this.this$0.glanceableHubWidgetManager;
        final ComponentName componentName = this.$provider;
        final UserHandle userHandle = this.$user;
        final Integer num = this.$rank;
        final WidgetConfigurator widgetConfigurator = this.$configurator;
        glanceableHubWidgetManager.getClass();
        glanceableHubWidgetManager.runOnService(new Function1() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManager$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                ComponentName componentName2 = componentName;
                UserHandle userHandle2 = userHandle;
                IGlanceableHubWidgetManagerService iGlanceableHubWidgetManagerService = (IGlanceableHubWidgetManagerService) obj2;
                int i = GlanceableHubWidgetManager.$r8$clinit;
                Integer num2 = num;
                int intValue = num2 != null ? num2.intValue() : -1;
                GlanceableHubWidgetManager glanceableHubWidgetManager2 = glanceableHubWidgetManager;
                glanceableHubWidgetManager2.getClass();
                WidgetConfigurator widgetConfigurator2 = widgetConfigurator;
                iGlanceableHubWidgetManagerService.addWidget(componentName2, userHandle2, intValue, widgetConfigurator2 != null ? new GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1(glanceableHubWidgetManager2, widgetConfigurator2) : null);
                return Unit.INSTANCE;
            }
        });
        return Unit.INSTANCE;
    }
}
