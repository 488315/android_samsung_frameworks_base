package com.android.systemui.statusbar.events;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class SystemStatusAnimationSchedulerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                int int1 = logMessage.getInt1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Scheduling event: ", str1, "(forceVisible=", ", priority=", bool1);
                sbM.append(int1);
                sbM.append(", showAnimation=");
                sbM.append(bool2);
                sbM.append(")");
                return sbM.toString();
            case 1:
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                int int12 = logMessage.getInt1();
                boolean bool22 = logMessage.getBool2();
                String str2 = logMessage.getStr2();
                StringBuilder sbM2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Updating current event from: ", str12, "(forceVisible=", ", priority=", bool12);
                sbM2.append(int12);
                sbM2.append(", showAnimation=");
                sbM2.append(bool22);
                sbM2.append("), animationState=");
                sbM2.append(str2);
                return sbM2.toString();
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("AnimationState update: ", logMessage.getStr1());
            default:
                String str13 = logMessage.getStr1();
                boolean bool13 = logMessage.getBool1();
                int int13 = logMessage.getInt1();
                boolean bool23 = logMessage.getBool2();
                StringBuilder sbM3 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("Ignore event: ", str13, "(forceVisible=", ", priority=", bool13);
                sbM3.append(int13);
                sbM3.append(", showAnimation=");
                sbM3.append(bool23);
                sbM3.append(")");
                return sbM3.toString();
        }
    }
}
