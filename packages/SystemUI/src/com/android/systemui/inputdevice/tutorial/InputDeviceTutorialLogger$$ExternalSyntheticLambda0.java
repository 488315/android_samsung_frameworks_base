package com.android.systemui.inputdevice.tutorial;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class InputDeviceTutorialLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "Received connection state: touchpad connected: " + logMessage.getBool1() + " keyboard connected: " + logMessage.getBool2();
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Opening ", logMessage.getStr1());
            case 2:
                return MotionLayout$$ExternalSyntheticOutline0.m("Moving from ", logMessage.getStr1(), " screen to ", logMessage.getStr2(), " screen");
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Closing ", logMessage.getStr1());
            case 4:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Emitting new screen ", logMessage.getStr1(), " in ", logMessage.getStr2());
            case 5:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Launching ", logMessage.getStr1(), " tutorial");
            case 6:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Going back to ", logMessage.getStr1(), " screen");
            case 7:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("next screen should be ", logMessage.getStr1(), " but required hardware is missing");
            case 8:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("going to ", logMessage.getStr1(), " screen");
            default:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(logMessage.getStr1(), " has connected for the first time");
        }
    }
}
