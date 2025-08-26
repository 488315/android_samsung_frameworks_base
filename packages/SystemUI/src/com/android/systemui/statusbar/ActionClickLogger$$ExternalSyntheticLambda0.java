package com.android.systemui.statusbar;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class ActionClickLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                String str3 = logMessage.getStr3();
                int int1 = logMessage.getInt1();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ACTION CLICK ", str1, " for pending intent ", str3, " at index ");
                sbM.append(int1);
                return sbM.toString();
            case 1:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "  [Action click] Triggered remote input (for ", logMessage.getStr1(), ") at index ");
            case 2:
                return CarrierTextManagerLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "  [Action click] Keyguard dismissed, calling default handler for intent ", logMessage.getStr1(), " at index ");
            case 3:
                return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "  [Action click] Intent ", logMessage.getStr1(), " at index ", " launches an activity, dismissing keyguard first...");
            default:
                return ReorderTile$$ExternalSyntheticOutline0.m(logMessage.getInt1(), ")", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("  [Action click] Launching intent ", logMessage.getStr2(), " via default handler (for ", logMessage.getStr1(), " at index "));
        }
    }
}
