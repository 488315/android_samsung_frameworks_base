package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetProviderInfo;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.BuildersKt;

final /* synthetic */ class CommunalWidgetHost$addListener$1 extends FunctionReferenceImpl implements Function2 {
    public CommunalWidgetHost$addListener$1(Object obj) {
        super(2, obj, CommunalWidgetHost.class, "onProviderInfoUpdated", "onProviderInfoUpdated(ILandroid/appwidget/AppWidgetProviderInfo;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        CommunalWidgetHost communalWidgetHost = (CommunalWidgetHost) this.receiver;
        CommunalWidgetHost.Companion companion = CommunalWidgetHost.Companion;
        communalWidgetHost.getClass();
        BuildersKt.launch$default(communalWidgetHost.bgScope, null, null, new CommunalWidgetHost$onProviderInfoUpdated$1(communalWidgetHost, ((Number) obj).intValue(), (AppWidgetProviderInfo) obj2, null), 3);
        return Unit.INSTANCE;
    }
}
