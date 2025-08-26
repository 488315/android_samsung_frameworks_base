package com.android.systemui.temporarydisplay;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.temporarydisplay.TemporaryViewLogger;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class TemporaryViewLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ TemporaryViewLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                TemporaryViewLogger.Companion companion = TemporaryViewLogger.Companion;
                return "View's appearance animation failed. Forcing view display manually.";
            case 1:
                TemporaryViewLogger.Companion companion2 = TemporaryViewLogger.Companion;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("View with id=", logMessage.getStr2(), " is removed due to: ", logMessage.getStr1());
            case 2:
                TemporaryViewLogger.Companion companion3 = TemporaryViewLogger.Companion;
                return NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Adding view to window manager. id=", logMessage.getStr1(), " window=", logMessage.getStr2(), " view="), logMessage.getStr3(), "(id=", Integer.toHexString(logMessage.getInt1()), ")");
            case 3:
                TemporaryViewLogger.Companion companion4 = TemporaryViewLogger.Companion;
                String str = logMessage.getBool1() ? " due to reinflation" : "";
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                String hexString = Integer.toHexString(logMessage.getInt1());
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Removing view from window manager", str, ". id=", str1, " window=");
                MoveResult$$ExternalSyntheticOutline0.m(sbM, str2, " view=", str3, "(id=");
                return TransitionKt$$ExternalSyntheticOutline0.m(sbM, hexString, ")");
            case 4:
                TemporaryViewLogger.Companion companion5 = TemporaryViewLogger.Companion;
                return "View's disappearance animation failed.";
            case 5:
                TemporaryViewLogger.Companion companion6 = TemporaryViewLogger.Companion;
                String str12 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("View timeout has already expired; removing. id=", str12, " window=", str22, " priority=");
                sbM2.append(str32);
                return sbM2.toString();
            case 6:
                TemporaryViewLogger.Companion companion7 = TemporaryViewLogger.Companion;
                String str13 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                String str33 = logMessage.getStr3();
                StringBuilder sbM3 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("New view can't be displayed because higher priority view is currently displayed. New view id=", str13, " window=", str23, " priority=");
                sbM3.append(str33);
                return sbM3.toString();
            case 7:
                TemporaryViewLogger.Companion companion8 = TemporaryViewLogger.Companion;
                String str14 = logMessage.getStr1();
                String str24 = logMessage.getStr2();
                String str34 = logMessage.getStr3();
                StringBuilder sbM4 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("View hidden in favor of newer view. Hidden view id=", str14, " window=", str24, " priority=");
                sbM4.append(str34);
                return sbM4.toString();
            case 8:
                TemporaryViewLogger.Companion companion9 = TemporaryViewLogger.Companion;
                String str15 = logMessage.getStr1();
                String str25 = logMessage.getStr2();
                String str35 = logMessage.getStr3();
                StringBuilder sbM5 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Existing view updated with new data. id=", str15, " window=", str25, " priority=");
                sbM5.append(str35);
                return sbM5.toString();
            case 9:
                TemporaryViewLogger.Companion companion10 = TemporaryViewLogger.Companion;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Removal of view with id=", logMessage.getStr2(), " is ignored because ", logMessage.getStr1());
            default:
                TemporaryViewLogger.Companion companion11 = TemporaryViewLogger.Companion;
                String str16 = logMessage.getStr1();
                String str26 = logMessage.getStr2();
                String str36 = logMessage.getStr3();
                StringBuilder sbM6 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("View added. id=", str16, " window=", str26, " priority=");
                sbM6.append(str36);
                return sbM6.toString();
        }
    }
}
