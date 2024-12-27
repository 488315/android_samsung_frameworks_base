package com.android.systemui.statusbar.gesture;

import android.view.InputEvent;
import android.view.MotionEvent;

public final class GesturePointerEventDetector extends GenericGestureDetector {
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public GesturePointerEventDetector(android.content.Context r1, com.android.systemui.settings.DisplayTracker r2) {
        /*
            r0 = this;
            java.lang.Class<com.android.systemui.statusbar.gesture.GesturePointerEventDetector> r1 = com.android.systemui.statusbar.gesture.GesturePointerEventDetector.class
            kotlin.jvm.internal.ClassReference r1 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r1)
            java.lang.String r1 = r1.getSimpleName()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            r2 = 0
            r0.<init>(r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.gesture.GesturePointerEventDetector.<init>(android.content.Context, com.android.systemui.settings.DisplayTracker):void");
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void onInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            onGestureDetected$frameworks__base__packages__SystemUI__android_common__SystemUI_core((MotionEvent) inputEvent);
        }
    }
}
