package com.android.systemui.toast;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ToastLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
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
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str2, "] Skip toast for [", str1, "] scheduled on unavailable display #");
                m.append(int1);
                return m.toString();
            default:
                String str3 = logMessage.getStr3();
                String str12 = logMessage.getStr1();
                int int12 = logMessage.getInt1();
                String str22 = logMessage.getStr2();
                StringBuilder m2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("[", str3, "] Show toast for (", str12, ", ");
                m2.append(int12);
                m2.append("). msg='");
                m2.append(str22);
                m2.append("'");
                return m2.toString();
        }
    }
}
