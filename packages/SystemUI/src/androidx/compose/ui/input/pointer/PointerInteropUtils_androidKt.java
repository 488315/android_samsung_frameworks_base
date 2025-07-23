package androidx.compose.ui.input.pointer;

import android.view.MotionEvent;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PointerInteropUtils_androidKt {
    /* renamed from: toMotionEventScope-ubNVwUQ, reason: not valid java name */
    public static final void m595toMotionEventScopeubNVwUQ(PointerEvent pointerEvent, long j, Function1 function1, boolean z) {
        MotionEvent motionEvent$ui_release = pointerEvent.getMotionEvent$ui_release();
        if (motionEvent$ui_release == null) {
            throw new IllegalArgumentException("The PointerEvent receiver cannot have a null MotionEvent.");
        }
        int action = motionEvent$ui_release.getAction();
        if (z) {
            motionEvent$ui_release.setAction(3);
        }
        int i = (int) (j >> 32);
        int i2 = (int) (j & 4294967295L);
        motionEvent$ui_release.offsetLocation(-Float.intBitsToFloat(i), -Float.intBitsToFloat(i2));
        function1.mo779invoke(motionEvent$ui_release);
        motionEvent$ui_release.offsetLocation(Float.intBitsToFloat(i), Float.intBitsToFloat(i2));
        motionEvent$ui_release.setAction(action);
    }
}
