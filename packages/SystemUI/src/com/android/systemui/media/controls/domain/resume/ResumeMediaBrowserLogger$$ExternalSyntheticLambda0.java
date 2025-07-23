package com.android.systemui.media.controls.domain.resume;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ResumeMediaBrowserLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Connecting browser for component ", logMessage.getStr1(), " due to ", logMessage.getStr2());
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Disconnecting browser for component ", logMessage.getStr1());
            default:
                return "Session destroyed. Active browser = " + logMessage.getBool1() + ". Browser component = " + logMessage.getStr1() + ".";
        }
    }
}
