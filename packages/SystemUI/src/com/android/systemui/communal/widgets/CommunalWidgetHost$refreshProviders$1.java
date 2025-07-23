package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalWidgetHost$refreshProviders$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalWidgetHost this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalWidgetHost$refreshProviders$1(CommunalWidgetHost communalWidgetHost, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalWidgetHost;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalWidgetHost$refreshProviders$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalWidgetHost$refreshProviders$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int[] appWidgetIds = this.this$0.appWidgetHost.getAppWidgetIds();
        CommunalWidgetHost communalWidgetHost = this.this$0;
        for (int i : appWidgetIds) {
            communalWidgetHost.getClass();
            communalWidgetHost.appWidgetHost.setListener(i, new CommunalWidgetHost.CommunalAppWidgetHostListener(i, new CommunalWidgetHost$addListener$1(communalWidgetHost)));
            Integer num = new Integer(i);
            AppWidgetProviderInfo appWidgetProviderInfo = null;
            AppWidgetManager appWidgetManager = (AppWidgetManager) communalWidgetHost.appWidgetManager.orElse(null);
            if (appWidgetManager != null) {
                appWidgetProviderInfo = appWidgetManager.getAppWidgetInfo(i);
            }
            linkedHashMap.put(num, appWidgetProviderInfo);
        }
        this.this$0._appWidgetProviders.setValue(MapsKt__MapsKt.toMap(linkedHashMap));
        return Unit.INSTANCE;
    }
}
