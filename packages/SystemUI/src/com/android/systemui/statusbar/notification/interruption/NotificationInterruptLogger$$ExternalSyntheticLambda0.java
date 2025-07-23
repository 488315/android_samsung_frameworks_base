package com.android.systemui.statusbar.notification.interruption;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationInterruptLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "No heads up: no huns";
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No heads up: suppressed by DND: ", logMessage.getStr1());
            case 2:
                long long1 = logMessage.getLong1();
                long long2 = logMessage.getLong2();
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("Maybe heads up: old when ", long1, " (age=");
                m.append(long2);
                m.append(" ms) but ");
                m.append(str2);
                return TransitionKt$$ExternalSyntheticOutline0.m(m, ": ", str1);
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No heads up: unimportant notification: ", logMessage.getStr1());
            case 4:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("No FullScreenIntent: ", logMessage.getStr2(), ": ", logMessage.getStr1());
            case 5:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("FullScreenIntent: ", logMessage.getStr2(), ": ", logMessage.getStr1());
            case 6:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Heads up: package snooze bypassed because notification has full-screen intent: ", logMessage.getStr1());
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No heads up: in unlocked shade where notification is shown as a bubble: ", logMessage.getStr1());
            case 8:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No alerting: suppressed due to group alert behavior: ", logMessage.getStr1());
            case 9:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("No FullScreenIntent: WARNING: ", logMessage.getStr2(), ": ", logMessage.getStr1());
            case 10:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No alerting: notification hidden on lock screen: ", logMessage.getStr1());
            case 11:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No alerting: suppressed due to silent notification: ", logMessage.getStr1());
            case 12:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("No heads up: aborted by suppressor: ", logMessage.getStr2(), " sbnKey=", logMessage.getStr1());
            case 13:
                return "dismissing any existing heads up notification on disable event";
            case 14:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("heads up is enabled=", logMessage.getBool1());
            case 15:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("No bubble up: notification: ", logMessage.getStr1(), " doesn't have valid metadata");
            case 16:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("No heads up: qs open ", logMessage.getStr1(), " will be HUN soon.");
            case 17:
                long long12 = logMessage.getLong1();
                long long22 = logMessage.getLong2();
                String str12 = logMessage.getStr1();
                StringBuilder m2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("No heads up: old when ", long12, " (age=");
                m2.append(long22);
                m2.append(" ms): ");
                m2.append(str12);
                return m2.toString();
            case 18:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Heads up: ", logMessage.getStr1());
            case 19:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No heads up: not in use: ", logMessage.getStr1());
            case 20:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No alerting: recent fullscreen: ", logMessage.getStr1());
            case 21:
                String str22 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                String str13 = logMessage.getStr1();
                StringBuilder m3 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("No alerting: aborted by suppressor: ", str22, " awake=", " sbnKey=", bool1);
                m3.append(str13);
                return m3.toString();
            case 22:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No alerting: app is suspended: ", logMessage.getStr1());
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("No heads up: snoozed package: ", logMessage.getStr1());
        }
    }
}
