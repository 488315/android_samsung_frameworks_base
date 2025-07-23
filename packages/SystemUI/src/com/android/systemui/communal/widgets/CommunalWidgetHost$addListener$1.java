package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetProviderInfo;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class CommunalWidgetHost$addListener$1 extends FunctionReferenceImpl implements Function2 {
    public CommunalWidgetHost$addListener$1(Object obj) {
        super(2, obj, CommunalWidgetHost.class, "onProviderInfoUpdated", "onProviderInfoUpdated(ILandroid/appwidget/AppWidgetProviderInfo;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        CommunalWidgetHost communalWidgetHost = (CommunalWidgetHost) this.receiver;
        CommunalWidgetHost.Companion companion = CommunalWidgetHost.Companion;
        communalWidgetHost.getClass();
        CoroutineTracingKt.launchTraced$default(communalWidgetHost.bgScope, null, null, new CommunalWidgetHost$onProviderInfoUpdated$1(communalWidgetHost, ((Number) obj).intValue(), (AppWidgetProviderInfo) obj2, null), 7);
        return Unit.INSTANCE;
    }
}
