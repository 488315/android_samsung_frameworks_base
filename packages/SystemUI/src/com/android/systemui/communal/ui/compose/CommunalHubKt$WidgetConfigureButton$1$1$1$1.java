package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalHubKt$WidgetConfigureButton$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CommunalContentModel.WidgetContent.Widget $model;
    final /* synthetic */ WidgetConfigurator $widgetConfigurator;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalHubKt$WidgetConfigureButton$1$1$1$1(WidgetConfigurator widgetConfigurator, CommunalContentModel.WidgetContent.Widget widget, Continuation continuation) {
        super(2, continuation);
        this.$widgetConfigurator = widgetConfigurator;
        this.$model = widget;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalHubKt$WidgetConfigureButton$1$1$1$1(this.$widgetConfigurator, this.$model, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalHubKt$WidgetConfigureButton$1$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WidgetConfigurator widgetConfigurator = this.$widgetConfigurator;
            int i2 = this.$model.appWidgetId;
            this.label = 1;
            if (widgetConfigurator.configureWidget(i2, this) == coroutineSingletons) {
                return coroutineSingletons;
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
