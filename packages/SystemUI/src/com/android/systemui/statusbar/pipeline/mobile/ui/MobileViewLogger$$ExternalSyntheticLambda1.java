package com.android.systemui.statusbar.pipeline.mobile.ui;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileViewLogger$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                MobileViewLogger.Companion companion = MobileViewLogger.Companion;
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Collection stopped. viewId=", str1, ", viewModelId=", str2, ", viewModelLocation=");
                sbM.append(str3);
                return sbM.toString();
            case 1:
                MobileViewLogger.Companion companion2 = MobileViewLogger.Companion;
                String str12 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("New view binding. viewId=", str12, ", viewModelId=", str22, ", viewModelLocation=");
                sbM2.append(str32);
                return sbM2.toString();
            case 2:
                MobileViewLogger.Companion companion3 = MobileViewLogger.Companion;
                return FakeFeatures$$ExternalSyntheticOutline0.m("Sub IDs in MobileUiAdapter being sent to icon controller: ", logMessage.getStr1(), ", isStackable=", logMessage.getBool1());
            default:
                MobileViewLogger.Companion companion4 = MobileViewLogger.Companion;
                String str13 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                String str33 = logMessage.getStr3();
                StringBuilder sbM3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Collection started. viewId=", str13, ", viewModelId=", str23, ", viewModelLocation=");
                sbM3.append(str33);
                return sbM3.toString();
        }
    }
}
