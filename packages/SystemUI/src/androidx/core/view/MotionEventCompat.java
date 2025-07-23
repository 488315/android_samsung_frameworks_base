package androidx.core.view;

import android.view.MotionEvent;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MotionEventCompat {
    private MotionEventCompat() {
    }

    public static boolean isFromSource(MotionEvent motionEvent, int i) {
        return (motionEvent.getSource() & i) == i;
    }
}
