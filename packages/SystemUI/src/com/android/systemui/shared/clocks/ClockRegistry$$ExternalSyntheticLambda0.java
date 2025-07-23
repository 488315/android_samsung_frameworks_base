package com.android.systemui.shared.clocks;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ClockRegistry$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Rendering clock ", logMessage.getStr1());
            case 1:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Clock ", logMessage.getStr1(), " not loaded; using default");
            case 2:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Clock ", logMessage.getStr1(), " not found; using default");
            case 3:
                return MotionLayout$$ExternalSyntheticOutline0.m("Disconnected ", logMessage.getStr1(), " @", logMessage.getStr2(), logMessage.getBool1() ? " (Current Clock)" : "");
            case 4:
                return MotionLayout$$ExternalSyntheticOutline0.m("Unloaded ", logMessage.getStr1(), " @", logMessage.getStr2(), logMessage.getBool1() ? " (Current Clock)" : "");
            case 5:
                return MotionLayout$$ExternalSyntheticOutline0.m("Connected ", logMessage.getStr1(), " @", logMessage.getStr2(), logMessage.getBool1() ? " (Current Clock)" : "");
            case 6:
                return MotionLayout$$ExternalSyntheticOutline0.m("Loaded ", logMessage.getStr1(), " @", logMessage.getStr2(), logMessage.getBool1() ? " (Current Clock)" : "");
            case 7:
                return NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Clock Id conflict on load: ", logMessage.getStr1(), " is double registered by ", logMessage.getStr2(), " and "), logMessage.getStr3(), ". Using ", logMessage.getStr2(), " since it was attached first.");
            case 8:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Loading unrecognized clock package: ", logMessage.getStr1());
            case 9:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Skipping initial load of known clock package package: ", logMessage.getStr1());
            case 10:
                return NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Clock Id conflict on attach: ", logMessage.getStr1(), " is double registered by ", logMessage.getStr2(), " and "), logMessage.getStr3(), ". Using ", logMessage.getStr2(), " since it was attached first.");
            default:
                return NotificationController$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Clock Id conflict on unload: ", logMessage.getStr1(), " is double registered by ", logMessage.getStr2(), " and "), logMessage.getStr3(), ". Using ", logMessage.getStr2(), " since it was attached first.");
        }
    }
}
