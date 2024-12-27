package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import kotlin.jvm.internal.Intrinsics;

public final class TapGestureDetector extends GenericGestureDetector {
    public final Context context;
    public GestureDetector gestureDetector;
    public final TapGestureDetector$gestureListener$1 gestureListener;

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.gesture.TapGestureDetector$gestureListener$1] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TapGestureDetector(android.content.Context r2, com.android.systemui.settings.DisplayTracker r3) {
        /*
            r1 = this;
            java.lang.Class<com.android.systemui.statusbar.gesture.TapGestureDetector> r3 = com.android.systemui.statusbar.gesture.TapGestureDetector.class
            kotlin.jvm.internal.ClassReference r3 = kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r3)
            java.lang.String r3 = r3.getSimpleName()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
            r0 = 0
            r1.<init>(r3, r0)
            r1.context = r2
            com.android.systemui.statusbar.gesture.TapGestureDetector$gestureListener$1 r2 = new com.android.systemui.statusbar.gesture.TapGestureDetector$gestureListener$1
            r2.<init>()
            r1.gestureListener = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.gesture.TapGestureDetector.<init>(android.content.Context, com.android.systemui.settings.DisplayTracker):void");
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void onInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            GestureDetector gestureDetector = this.gestureDetector;
            Intrinsics.checkNotNull(gestureDetector);
            gestureDetector.onTouchEvent((MotionEvent) inputEvent);
        }
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        super.startGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        this.gestureDetector = new GestureDetector(this.context, this.gestureListener);
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        super.stopGestureListening$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        this.gestureDetector = null;
    }
}
