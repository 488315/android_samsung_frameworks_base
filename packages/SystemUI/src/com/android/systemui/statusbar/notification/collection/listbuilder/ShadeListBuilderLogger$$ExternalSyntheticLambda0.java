package com.android.systemui.statusbar.notification.collection.listbuilder;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeListBuilderLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "(Build " + logMessage.getLong1() + ") Build complete (" + logMessage.getInt1() + " top-level entries, " + logMessage.getInt2() + " children) enforcedVisualStability=" + logMessage.getBool1();
            case 1:
                if (logMessage.getStr1() == null && logMessage.getStr2() != null) {
                    return "(Build " + logMessage.getLong1() + ")     Parent is {" + logMessage.getStr2() + "}";
                }
                if (logMessage.getStr1() == null || logMessage.getStr2() != null) {
                    long long1 = logMessage.getLong1();
                    String str1 = logMessage.getStr1();
                    String str2 = logMessage.getStr2();
                    StringBuilder sb = new StringBuilder("(Build ");
                    sb.append(long1);
                    sb.append(")     Reparent: {");
                    sb.append(str1);
                    return MutablePreferences$$ExternalSyntheticOutline0.m(sb, "} -> {", str2, "}");
                }
                return "(Build " + logMessage.getLong1() + ")     Parent was {" + logMessage.getStr1() + "}";
            case 2:
                long long12 = logMessage.getLong1();
                String str12 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                StringBuilder sb2 = new StringBuilder("(Build ");
                sb2.append(long12);
                sb2.append(")     Pruned reason changed: ");
                sb2.append(str12);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb2, " -> ", str22);
            case 3:
                return "(Build " + logMessage.getLong1() + ")     Group pruning suppressed; keeping parent '" + logMessage.getStr1() + "'";
            case 4:
                long long13 = logMessage.getLong1();
                String str13 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                StringBuilder sb3 = new StringBuilder("(Build ");
                sb3.append(long13);
                sb3.append(")     Change of parent to '");
                sb3.append(str13);
                return MutablePreferences$$ExternalSyntheticOutline0.m(sb3, "' no longer suppressed; replaced parent '", str23, "'");
            case 5:
                String str = (logMessage.getStr2() != null || logMessage.getStr3() == null) ? (logMessage.getStr2() == null || logMessage.getStr3() != null) ? (logMessage.getStr2() == null && logMessage.getStr3() == null) ? "MODIFIED (DETACHED)" : "MODIFIED (ATTACHED)" : "DETACHED" : "ATTACHED";
                long long14 = logMessage.getLong1();
                String str14 = logMessage.getStr1();
                StringBuilder sb4 = new StringBuilder("(Build ");
                sb4.append(long14);
                sb4.append(") ");
                sb4.append(str);
                return MutablePreferences$$ExternalSyntheticOutline0.m(sb4, " {", str14, "}");
            case 6:
                if (logMessage.getStr1() == null) {
                    return "(Build " + logMessage.getLong1() + ")     Section assigned: " + logMessage.getStr2();
                }
                long long15 = logMessage.getLong1();
                String str15 = logMessage.getStr1();
                String str24 = logMessage.getStr2();
                StringBuilder sb5 = new StringBuilder("(Build ");
                sb5.append(long15);
                sb5.append(")     Section changed: ");
                sb5.append(str15);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb5, " -> ", str24);
            case 7:
                long long16 = logMessage.getLong1();
                String str16 = logMessage.getStr1();
                String str25 = logMessage.getStr2();
                StringBuilder sb6 = new StringBuilder("(Build ");
                sb6.append(long16);
                sb6.append(")     Promoter changed: ");
                sb6.append(str16);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb6, " -> ", str25);
            case 8:
                long long17 = logMessage.getLong1();
                String str17 = logMessage.getStr1();
                String str26 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sb7 = new StringBuilder("(Build ");
                sb7.append(long17);
                sb7.append(") Duplicate summary for group \"");
                sb7.append(str17);
                MoveResult$$ExternalSyntheticOutline0.m(sb7, "\": \"", str26, "\" vs. \"", str3);
                sb7.append("\"");
                return sb7.toString();
            case 9:
                return MutablePreferences$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Invalidated while ", PipelineState.getStateName(logMessage.getInt1()), " by ", logMessage.getStr1(), " \""), logMessage.getStr2(), "\" because ", logMessage.getStr3());
            case 10:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Request received from NotifCollection for ", logMessage.getStr1());
            case 11:
                long long18 = logMessage.getLong1();
                String str18 = logMessage.getStr1();
                String str27 = logMessage.getStr2();
                StringBuilder sb8 = new StringBuilder("(Build ");
                sb8.append(long18);
                sb8.append(")     Suppressing section change to ");
                sb8.append(str18);
                return MutablePreferences$$ExternalSyntheticOutline0.m(sb8, " (staying at ", str27, ")");
            case 12:
                long long19 = logMessage.getLong1();
                String str19 = logMessage.getStr1();
                String str28 = logMessage.getStr2();
                StringBuilder sb9 = new StringBuilder("(Build ");
                sb9.append(long19);
                sb9.append(")     Filter changed: ");
                sb9.append(str19);
                return TransitionKt$$ExternalSyntheticOutline0.m(sb9, " -> ", str28);
            case 13:
                return "(Build " + logMessage.getLong1() + ") Duplicate top-level key: " + logMessage.getStr1();
            case 14:
                return "(empty list)";
            case 15:
                String m = BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "[", "] ", logMessage.getStr1());
                if (!logMessage.getBool1()) {
                    return m;
                }
                return m + " rank=" + logMessage.getInt2();
            case 16:
                String m2 = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("  [*] ", logMessage.getStr1(), " (summary)");
                if (!logMessage.getBool1()) {
                    return m2;
                }
                return m2 + " rank=" + logMessage.getInt2();
            case 17:
                String m3 = BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "  [", "] ", logMessage.getStr1());
                if (!logMessage.getBool1()) {
                    return m3;
                }
                return m3 + " rank=" + logMessage.getInt2();
            case 18:
                long long110 = logMessage.getLong1();
                String str110 = logMessage.getStr1();
                String str29 = logMessage.getStr2();
                StringBuilder sb10 = new StringBuilder("(Build ");
                sb10.append(long110);
                sb10.append(")     Change of parent to '");
                sb10.append(str110);
                return MutablePreferences$$ExternalSyntheticOutline0.m(sb10, "' suppressed; keeping parent '", str29, "'");
            default:
                return "Suppressing pipeline run during animation.";
        }
    }
}
