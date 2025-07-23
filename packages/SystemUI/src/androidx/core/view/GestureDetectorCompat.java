package androidx.core.view;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GestureDetectorCompat {
    public final GestureDetector mDetector;

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        this.mDetector = new GestureDetector(context, onGestureListener, handler);
    }
}
