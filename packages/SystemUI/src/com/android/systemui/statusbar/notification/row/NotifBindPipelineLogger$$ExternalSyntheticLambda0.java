package com.android.systemui.statusbar.notification.row;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotifBindPipelineLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Row is not set so pipeline will not run. notif = ", logMessage.getStr1());
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Row set for notif: ", logMessage.getStr1());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Request pipeline run for notif: ", logMessage.getStr1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Start pipeline for notif: ", logMessage.getStr1());
            case 4:
                return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Finished pipeline for notif ", logMessage.getStr1(), " with ", " callbacks");
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Stage set: ", logMessage.getStr1());
        }
    }
}
