package com.android.systemui.communal.widgets;

import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class GlanceableHubWidgetManager$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ GlanceableHubWidgetManager$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Object obj2 = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                Map map = (Map) obj2;
                int i = GlanceableHubWidgetManager.$r8$clinit;
                ((IGlanceableHubWidgetManagerService) obj).updateWidgetOrder(CollectionsKt___CollectionsKt.toIntArray(map.keySet()), CollectionsKt___CollectionsKt.toIntArray(map.values()));
                break;
            case 1:
                ((IGlanceableHubWidgetManagerService) obj).addWidgetsListener((GlanceableHubWidgetManager$widgets$1$callback$1) obj2);
                break;
            default:
                ((IGlanceableHubWidgetManagerService) obj).removeWidgetsListener((GlanceableHubWidgetManager$widgets$1$callback$1) obj2);
                break;
        }
        return Unit.INSTANCE;
    }
}
