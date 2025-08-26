package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public final class TapGestureDetector extends GenericGestureDetector {
    public final Context context;
    public GestureDetector gestureDetector;
    public final TapGestureDetector$gestureListener$1 gestureListener;

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.statusbar.gesture.TapGestureDetector$gestureListener$1] */
    public TapGestureDetector(Context context, DisplayTracker displayTracker) {
        String simpleName = Reflection.getOrCreateKotlinClass(TapGestureDetector.class).getSimpleName();
        simpleName.getClass();
        displayTracker.getClass();
        super(simpleName, 0);
        this.context = context;
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.statusbar.gesture.TapGestureDetector$gestureListener$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onSingleTapUp(MotionEvent motionEvent) {
                this.this$0.onGestureDetected$frameworks__base__packages__SystemUI__android_common__SystemUI_core(motionEvent);
                return true;
            }
        };
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void onInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            GestureDetector gestureDetector = this.gestureDetector;
            gestureDetector.getClass();
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
