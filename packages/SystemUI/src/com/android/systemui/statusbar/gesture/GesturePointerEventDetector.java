package com.android.systemui.statusbar.gesture;

import android.content.Context;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.systemui.settings.DisplayTracker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public final class GesturePointerEventDetector extends GenericGestureDetector {
    /* JADX WARN: Illegal instructions before constructor call */
    public GesturePointerEventDetector(Context context, DisplayTracker displayTracker) {
        String simpleName = Reflection.getOrCreateKotlinClass(GesturePointerEventDetector.class).getSimpleName();
        simpleName.getClass();
        displayTracker.getClass();
        super(simpleName, 0);
    }

    @Override // com.android.systemui.statusbar.gesture.GenericGestureDetector
    public final void onInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            onGestureDetected$frameworks__base__packages__SystemUI__android_common__SystemUI_core((MotionEvent) inputEvent);
        }
    }
}
