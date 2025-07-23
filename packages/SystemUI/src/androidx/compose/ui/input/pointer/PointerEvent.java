package androidx.compose.ui.input.pointer;

import android.view.MotionEvent;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PointerEvent {
    public final int buttons;
    public final List changes;
    public final InternalPointerEvent internalPointerEvent;
    public int type;

    public PointerEvent(List<PointerInputChange> list, InternalPointerEvent internalPointerEvent) {
        this.changes = list;
        this.internalPointerEvent = internalPointerEvent;
        MotionEvent motionEvent$ui_release = getMotionEvent$ui_release();
        int i = 0;
        this.buttons = motionEvent$ui_release != null ? motionEvent$ui_release.getButtonState() : 0;
        MotionEvent motionEvent$ui_release2 = getMotionEvent$ui_release();
        if (motionEvent$ui_release2 != null) {
            motionEvent$ui_release2.getMetaState();
        }
        MotionEvent motionEvent$ui_release3 = getMotionEvent$ui_release();
        if (motionEvent$ui_release3 == null) {
            int size = list.size();
            while (true) {
                if (i >= size) {
                    PointerEventType.Companion.getClass();
                    i = PointerEventType.Move;
                    break;
                }
                PointerInputChange pointerInputChange = list.get(i);
                if (PointerEventKt.changedToUpIgnoreConsumed(pointerInputChange)) {
                    PointerEventType.Companion.getClass();
                    i = PointerEventType.Release;
                    break;
                } else {
                    if (PointerEventKt.changedToDownIgnoreConsumed(pointerInputChange)) {
                        PointerEventType.Companion.getClass();
                        i = PointerEventType.Press;
                        break;
                    }
                    i++;
                }
            }
        } else {
            int actionMasked = motionEvent$ui_release3.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                        switch (actionMasked) {
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                PointerEventType.Companion.getClass();
                                i = PointerEventType.Scroll;
                                break;
                            case 9:
                                PointerEventType.Companion.getClass();
                                i = PointerEventType.Enter;
                                break;
                            case 10:
                                PointerEventType.Companion.getClass();
                                i = PointerEventType.Exit;
                                break;
                            default:
                                PointerEventType.Companion.getClass();
                                break;
                        }
                    }
                    PointerEventType.Companion.getClass();
                    i = PointerEventType.Move;
                }
                PointerEventType.Companion.getClass();
                i = PointerEventType.Release;
            }
            PointerEventType.Companion.getClass();
            i = PointerEventType.Press;
        }
        this.type = i;
    }

    public final MotionEvent getMotionEvent$ui_release() {
        InternalPointerEvent internalPointerEvent = this.internalPointerEvent;
        if (internalPointerEvent != null) {
            return internalPointerEvent.pointerInputEvent.motionEvent;
        }
        return null;
    }

    public PointerEvent(List<PointerInputChange> list) {
        this(list, null);
    }
}
