package com.android.systemui.statusbar.notification.row;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationRowLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("addTransientRow to row: childKey: ", str1, " -- containerKey: ", str2, " -- index: ");
                sbM.append(int1);
                return sbM.toString();
            case 1:
                return FakeFeatures$$ExternalSyntheticOutline0.m("Skipped an appear animation childKey: ", logMessage.getStr1(), " isAppear:", logMessage.getBool1());
            case 2:
                return FakeFeatures$$ExternalSyntheticOutline0.m("onAppearAnimationStarted childKey: ", logMessage.getStr1(), " isAppear:", logMessage.getBool1());
            case 3:
                String str12 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder sbM2 = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("onAppearAnimationFinished childKey: ", str12, " isAppear:", " cancelled:", bool1);
                sbM2.append(bool2);
                return sbM2.toString();
            case 4:
                return FakeFeatures$$ExternalSyntheticOutline0.m("cancelAppearDrawing childKey: ", logMessage.getStr1(), " wasDrawing:", logMessage.getBool1());
            case 5:
                return MotionLayout$$ExternalSyntheticOutline0.m("Skipping to attach ", logMessage.getStr1(), " to ", logMessage.getStr2(), ", because it still flagged to keep in parent");
            case 6:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("RemoveTransientRow from ChildrenContainer: childKey: ", logMessage.getStr1(), " -- containerKey: ", logMessage.getStr2());
            case 7:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("resetAllContentAlphas: ", logMessage.getStr1());
            case 8:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("RemoveTransientRow from Nssl: childKey: ", logMessage.getStr1());
            case 9:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("RemoveTransientRow from other ViewGroup: childKey: ", logMessage.getStr1(), " -- ViewGroup: ", logMessage.getStr2());
            case 10:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("removeTransientRow from row: childKey: ", logMessage.getStr1(), " -- containerKey: ", logMessage.getStr2());
            case 11:
                return MotionLayout$$ExternalSyntheticOutline0.m("Failed to set magnetic and roundable targets for ", logMessage.getStr1(), " on state ", logMessage.getStr2(), ".");
            case 12:
                return FakeFeatures$$ExternalSyntheticOutline0.m("startAppearAnimation childKey: ", logMessage.getStr1(), " isAppear:", logMessage.getBool1());
            case 13:
                return MotionLayout$$ExternalSyntheticOutline0.m("Failed to set magnetic row translation for ", logMessage.getStr1(), " on state ", logMessage.getStr2(), ".");
            default:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Detach child ", logMessage.getStr1(), " kept in parent ", logMessage.getStr2());
        }
    }
}
