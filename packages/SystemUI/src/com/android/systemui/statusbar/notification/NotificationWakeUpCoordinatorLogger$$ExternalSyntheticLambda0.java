package com.android.systemui.statusbar.notification;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.StatusBarState;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationWakeUpCoordinatorLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "setVisibilityAmount(" + logMessage.getDouble1() + ")";
            case 1:
                return "setDozeAmountOverride(dozing=" + logMessage.getBool1() + ", source=\"" + logMessage.getStr1() + "\")";
            case 2:
                return "onDozeAmountChanged(linear=" + logMessage.getDouble1() + ", eased=" + logMessage.getStr2() + ")";
            case 3:
                return "setHideAmount(" + logMessage.getDouble1() + ")";
            case 4:
                double double1 = logMessage.getDouble1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String statusBarState = StatusBarState.toString(logMessage.getInt1());
                boolean bool1 = logMessage.getBool1();
                StringBuilder sb = new StringBuilder("updateDozeAmount() inputLinear=");
                sb.append(double1);
                sb.append(" hardOverride=");
                sb.append(str1);
                MoveResult$$ExternalSyntheticOutline0.m(sb, " outputLinear=", str2, " state=", statusBarState);
                sb.append(" changed=");
                sb.append(bool1);
                return sb.toString();
            case 5:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("maybeClearHardDozeAmountOverrideHidingNotifs() ", logMessage.getStr1());
            case 6:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("onStateChanged(newState=", StatusBarState.toString(logMessage.getInt1()), ") stored=", StatusBarState.toString(logMessage.getInt2()));
            case 7:
                return "onPanelExpansionChanged(" + logMessage.getDouble1() + "): collapsedEnoughToHide: " + logMessage.getBool1() + " -> " + logMessage.getBool2() + ", canShowPulsingHuns: " + logMessage.getBool3() + " -> " + logMessage.getBool4();
            default:
                return "setWakingUp(wakingUp=" + logMessage.getBool1() + ")";
        }
    }
}
