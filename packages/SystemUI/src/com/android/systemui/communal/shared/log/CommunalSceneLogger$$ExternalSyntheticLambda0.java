package com.android.systemui.communal.shared.log;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalSceneLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = CommunalSceneLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Scene change committed: ", logMessage.getStr1(), " → ", logMessage.getStr2());
            case 1:
                int i2 = CommunalSceneLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Scene transition started: ", logMessage.getStr1(), " → ", logMessage.getStr2());
            default:
                int i3 = CommunalSceneLogger.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Scene transition idle on: ", logMessage.getStr1());
        }
    }
}
