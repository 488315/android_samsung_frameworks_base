package com.android.systemui.media.controls.domain.pipeline;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.systemui.globalactions.presentation.features.FakeFeatures$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaTimeoutLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("session destroyed ", logMessage.getStr1());
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("deliver delayed playback state for ", logMessage.getStr1());
            case 2:
                return FakeFeatures$$ExternalSyntheticOutline0.m("updating ", logMessage.getStr1(), ", was playing? ", logMessage.getBool1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("execute timeout for ", logMessage.getStr1());
            case 4:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("timeout logging for ", logMessage.getStr1(), ", reason: ", logMessage.getStr2());
            case 5:
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("schedule timeout ", str1, ", playing=", " resumption=", bool1);
                sbM.append(bool2);
                return sbM.toString();
            case 6:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("state update: key=", logMessage.getStr1(), " state=", logMessage.getStr2());
            case 7:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("timeout cancelled for ", logMessage.getStr1(), ", reason: ", logMessage.getStr2());
            case 8:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("cancellation already exists for ", logMessage.getStr1());
            case 9:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("reuse listener: ", logMessage.getStr1());
            default:
                String str12 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                boolean bool12 = logMessage.getBool1();
                StringBuilder sbM2 = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("migrate from ", str12, " to ", str2, ", had listener? ");
                sbM2.append(bool12);
                return sbM2.toString();
        }
    }
}
