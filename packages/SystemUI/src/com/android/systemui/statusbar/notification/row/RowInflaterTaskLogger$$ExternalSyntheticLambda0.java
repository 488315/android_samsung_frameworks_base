package com.android.systemui.statusbar.notification.row;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class RowInflaterTaskLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "created row in " + logMessage.getLong1() + " ms for " + logMessage.getStr1();
            case 1:
                String str = logMessage.getBool1() ? "cancelled " : "";
                long long1 = logMessage.getLong1();
                String str1 = logMessage.getStr1();
                StringBuilder sb = new StringBuilder("finished ");
                sb.append(str);
                sb.append("row inflation in ");
                sb.append(long1);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb, " ms for ", str1);
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("started row inflation for ", logMessage.getStr1());
        }
    }
}
