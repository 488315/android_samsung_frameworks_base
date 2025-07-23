package com.android.systemui.statusbar.events;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                int int1 = logMessage.getInt1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Scheduling event: ", str1, "(forceVisible=", ", priority=", bool1);
                m.append(int1);
                m.append(", showAnimation=");
                m.append(bool2);
                m.append(")");
                return m.toString();
            case 1:
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                int int12 = logMessage.getInt1();
                boolean bool22 = logMessage.getBool2();
                String str2 = logMessage.getStr2();
                StringBuilder m2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Updating current event from: ", str12, "(forceVisible=", ", priority=", bool12);
                m2.append(int12);
                m2.append(", showAnimation=");
                m2.append(bool22);
                m2.append("), animationState=");
                m2.append(str2);
                return m2.toString();
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("AnimationState update: ", logMessage.getStr1());
            default:
                String str13 = logMessage.getStr1();
                boolean bool13 = logMessage.getBool1();
                int int13 = logMessage.getInt1();
                boolean bool23 = logMessage.getBool2();
                StringBuilder m3 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Ignore event: ", str13, "(forceVisible=", ", priority=", bool13);
                m3.append(int13);
                m3.append(", showAnimation=");
                m3.append(bool23);
                m3.append(")");
                return m3.toString();
        }
    }
}
