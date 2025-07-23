package com.android.systemui.communal.widgets;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = GlanceableHubWidgetManagerService.$r8$clinit;
                return "Error getting intent sender for configure activity";
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error configuring widget: ", logMessage.getStr1());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error pushing widget update: ", logMessage.getStr1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error updating app widget: ", logMessage.getStr1());
            case 4:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error pushing on update provider info: ", logMessage.getStr1());
            case 5:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error pushing on view data changed: ", logMessage.getStr1());
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error updating app widget deferred: ", logMessage.getStr1());
        }
    }
}
