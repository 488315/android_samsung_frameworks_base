package com.android.systemui.toast;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class ToastLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return FakeFeatures$$ExternalSyntheticOutline0.m("Orientation change for toast. msg='", logMessage.getStr1(), "' isPortrait=", logMessage.getBool1());
            case 1:
                return MotionLayout$$ExternalSyntheticOutline0.m("[", logMessage.getStr2(), "] Hide toast for [", logMessage.getStr1(), "]");
            case 2:
                String str2 = logMessage.getStr2();
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str2, "] Skip toast for [", str1, "] scheduled on unavailable display #");
                sbM.append(int1);
                return sbM.toString();
            default:
                String str3 = logMessage.getStr3();
                String str12 = logMessage.getStr1();
                int int12 = logMessage.getInt1();
                String str22 = logMessage.getStr2();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str3, "] Show toast for (", str12, ", ");
                sbM2.append(int12);
                sbM2.append("). msg='");
                sbM2.append(str22);
                sbM2.append("'");
                return sbM2.toString();
        }
    }
}
