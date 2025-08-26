package com.android.keyguard.logging;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = KeyguardQuickAffordancesLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("QuickAffordance selected with id: ", logMessage.getStr1(), ", in slot: ", logMessage.getStr2());
            case 1:
                int i2 = KeyguardQuickAffordancesLogger.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("QuickAffordance updated: ", logMessage.getStr1());
            default:
                int i3 = KeyguardQuickAffordancesLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("QuickAffordance tapped with id: ", logMessage.getStr1(), ", in slot: ", logMessage.getStr2());
        }
    }
}
