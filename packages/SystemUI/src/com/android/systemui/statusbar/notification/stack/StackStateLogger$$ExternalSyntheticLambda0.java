package com.android.systemui.statusbar.notification.stack;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.DumpUtilsKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class StackStateLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Animation End, type: ", str2, ", notif key: ", str1, ", isHeadsUp: ");
                sbM.append(bool1);
                return sbM.toString();
            case 1:
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder sbM2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("ProcessAnimationEvents ANIMATION_TYPE_REMOVE_SWIPED_OUT for: ", str12, ", isFullySwipedOut: ", ", isHeadsUp: ", bool12);
                sbM2.append(bool2);
                return sbM2.toString();
            case 2:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Heads up notification appear animation ended ", logMessage.getStr1(), " ");
            case 3:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Heads up notification view appearing ", logMessage.getStr1(), " ");
            case 4:
                String str13 = logMessage.getStr1();
                String strVisibilityString = DumpUtilsKt.visibilityString(logMessage.getInt1());
                boolean bool13 = logMessage.getBool1();
                StringBuilder sbM3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ProcessAnimationEvents ANIMATION_TYPE_REMOVE for: ", str13, ", changingViewVisibility: ", strVisibilityString, ", isHeadsUp: ");
                sbM3.append(bool13);
                return sbM3.toString();
            case 5:
                String str22 = logMessage.getStr2();
                String str14 = logMessage.getStr1();
                boolean bool14 = logMessage.getBool1();
                StringBuilder sbM4 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Animation Start, type: ", str22, ", notif key: ", str14, ", isHeadsUp: ");
                sbM4.append(bool14);
                return sbM4.toString();
            default:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Heads up view appearing ", logMessage.getStr1(), " for ANIMATION_TYPE_ADD");
        }
    }
}
