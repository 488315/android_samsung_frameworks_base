package androidx.compose.ui.input.pointer;

import android.view.MotionEvent;
import java.util.List;

/* loaded from: classes.dex */
public final class PointerInputEvent {
    public final MotionEvent motionEvent;
    public final List pointers;

    public PointerInputEvent(long j, List<PointerInputEventData> list, MotionEvent motionEvent) {
        this.pointers = list;
        this.motionEvent = motionEvent;
    }
}
