package com.android.systemui.statusbar.notification.interruption;

import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VisualInterruptionDecisionLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str = logMessage.getBool1() ? "allowed" : "suppressed";
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sb = new StringBuilder();
                sb.append(str1);
                sb.append(" ");
                sb.append(str);
                sb.append(": ");
                sb.append(str2);
                return MutablePreferences$$ExternalSyntheticOutline0.m(sb, " (key=", str3, ")");
            default:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Cooldown enabled: ", logMessage.getBool1());
        }
    }
}
