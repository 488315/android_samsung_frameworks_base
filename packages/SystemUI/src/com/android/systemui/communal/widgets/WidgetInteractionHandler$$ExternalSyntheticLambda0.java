package com.android.systemui.communal.widgets;

import android.view.View;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class WidgetInteractionHandler$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        int i = WidgetInteractionHandler.$r8$clinit;
        return Boolean.valueOf(((View) obj) instanceof CommunalAppWidgetHostView);
    }
}
