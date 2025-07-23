package androidx.compose.ui.input.pointer;

import android.view.MotionEvent;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerInputEvent {
    public final MotionEvent motionEvent;
    public final List pointers;

    public PointerInputEvent(long j, List<PointerInputEventData> list, MotionEvent motionEvent) {
        this.pointers = list;
        this.motionEvent = motionEvent;
    }
}
