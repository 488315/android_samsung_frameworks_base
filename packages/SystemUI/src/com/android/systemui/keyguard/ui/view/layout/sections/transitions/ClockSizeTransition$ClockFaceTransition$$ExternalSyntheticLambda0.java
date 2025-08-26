package com.android.systemui.keyguard.ui.view.layout.sections.transitions;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.clocks.ClockLogger;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ClockSizeTransition$ClockFaceTransition$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = ClockSizeTransition.ClockFaceTransition.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Adding large clock views: ", logMessage.getStr1());
            case 1:
                int i2 = ClockSizeTransition.SmartspaceMoveTransition.$r8$clinit;
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Holding position of ");
            case 2:
                String[] strArr = ClockSizeTransition.VisibilityBoundsTransition.TRANSITION_PROPERTIES;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Failed to find smartspace equivalent target under ", logMessage.getStr1());
            case 3:
                String[] strArr2 = ClockSizeTransition.VisibilityBoundsTransition.TRANSITION_PROPERTIES;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Couldn't create animator: startValues=", logMessage.getStr1(), "; endValues=", logMessage.getStr2());
            case 4:
                String[] strArr3 = ClockSizeTransition.VisibilityBoundsTransition.TRANSITION_PROPERTIES;
                String str1 = logMessage.getStr1();
                ClockLogger.Companion companion = ClockLogger.Companion;
                String visText = companion.getVisText(logMessage.getInt1());
                String visText2 = companion.getVisText(logMessage.getInt2());
                String str2 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Skipping no-op transition: ", str1, "; vis: ", visText, " -> ");
                MoveResult$$ExternalSyntheticOutline0.m(sbM, visText2, "; alpha: ", str2, "; bounds: ");
                return TransitionKt$$ExternalSyntheticOutline0.m(sbM, str3, "; ");
            case 5:
                String[] strArr4 = ClockSizeTransition.VisibilityBoundsTransition.TRANSITION_PROPERTIES;
                String str12 = logMessage.getStr1();
                ClockLogger.Companion companion2 = ClockLogger.Companion;
                String visText3 = companion2.getVisText(logMessage.getInt1());
                String visText4 = companion2.getVisText(logMessage.getInt2());
                String str22 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("transitioning: ", str12, "; vis: ", visText3, " -> ");
                MoveResult$$ExternalSyntheticOutline0.m(sbM2, visText4, "; alpha: ", str22, "; bounds: ");
                return TransitionKt$$ExternalSyntheticOutline0.m(sbM2, str32, ";");
            default:
                String[] strArr5 = ClockSizeTransition.VisibilityBoundsTransition.TRANSITION_PROPERTIES;
                String str13 = logMessage.getStr1();
                String str23 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                double double1 = logMessage.getDouble1();
                String visText5 = ClockLogger.Companion.getVisText(logMessage.getInt2());
                String str33 = logMessage.getStr3();
                StringBuilder sb = new StringBuilder();
                sb.append(str13);
                sb.append(": ");
                sb.append(str23);
                sb.append("; fract=");
                sb.append(int1);
                sb.append("%; alpha=");
                sb.append(double1);
                sb.append("; vis=");
                return NotificationController$$ExternalSyntheticOutline0.m(sb, visText5, "; bounds=", str33, ";");
        }
    }
}
