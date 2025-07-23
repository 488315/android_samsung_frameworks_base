package com.android.systemui.keyguard.ui.viewmodel;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardBlueprintViewModel$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardBlueprintViewModel$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                StringBuilder m888m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int1, "runTransition: skipping ", str1, ": currentPriority=", "; config=");
                m888m.append(str2);
                return m888m.toString();
            case 1:
                String str12 = logMessage.getStr1();
                int int12 = logMessage.getInt1();
                String str22 = logMessage.getStr2();
                StringBuilder m888m2 = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(int12, "runTransition: running ", str12, ": currentPriority=", "; config=");
                m888m2.append(str22);
                return m888m2.toString();
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onTransitionPause: ", logMessage.getStr1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onTransitionEnd: ", logMessage.getStr1());
            case 4:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onTransitionResume: ", logMessage.getStr1());
            case 5:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onTransitionStart: ", logMessage.getStr1());
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onTransitionCancel: ", logMessage.getStr1());
        }
    }
}
