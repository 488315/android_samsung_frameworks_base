package com.android.systemui.statusbar.notification.stack;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.DumpUtilsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StackStateLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Animation End, type: ", str2, ", notif key: ", str1, ", isHeadsUp: ");
                m.append(bool1);
                return m.toString();
            case 1:
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("ProcessAnimationEvents ANIMATION_TYPE_REMOVE_SWIPED_OUT for: ", str12, ", isFullySwipedOut: ", ", isHeadsUp: ", bool12);
                m2.append(bool2);
                return m2.toString();
            case 2:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Heads up notification appear animation ended ", logMessage.getStr1(), " ");
            case 3:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Heads up notification view appearing ", logMessage.getStr1(), " ");
            case 4:
                String str13 = logMessage.getStr1();
                String visibilityString = DumpUtilsKt.visibilityString(logMessage.getInt1());
                boolean bool13 = logMessage.getBool1();
                StringBuilder m3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ProcessAnimationEvents ANIMATION_TYPE_REMOVE for: ", str13, ", changingViewVisibility: ", visibilityString, ", isHeadsUp: ");
                m3.append(bool13);
                return m3.toString();
            case 5:
                String str22 = logMessage.getStr2();
                String str14 = logMessage.getStr1();
                boolean bool14 = logMessage.getBool1();
                StringBuilder m4 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Animation Start, type: ", str22, ", notif key: ", str14, ", isHeadsUp: ");
                m4.append(bool14);
                return m4.toString();
            default:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Heads up view appearing ", logMessage.getStr1(), " for ANIMATION_TYPE_ADD");
        }
    }
}
