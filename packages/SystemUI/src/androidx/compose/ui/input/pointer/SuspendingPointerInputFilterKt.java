package androidx.compose.ui.input.pointer;

import androidx.compose.ui.Modifier;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SuspendingPointerInputFilterKt {
    public static final PointerEvent EmptyPointerEvent = new PointerEvent(EmptyList.INSTANCE);

    public static final SuspendingPointerInputModifierNodeImpl SuspendingPointerInputModifierNode(PointerInputEventHandler pointerInputEventHandler) {
        return new SuspendingPointerInputModifierNodeImpl((Object) null, (Object) null, (Object[]) null, pointerInputEventHandler);
    }

    public static final Modifier pointerInput(Modifier modifier, Object obj, PointerInputEventHandler pointerInputEventHandler) {
        return modifier.then(new SuspendPointerInputElement(obj, null, null, pointerInputEventHandler, 6, null));
    }

    public static final Modifier pointerInput(Modifier modifier, Object[] objArr, PointerInputEventHandler pointerInputEventHandler) {
        return modifier.then(new SuspendPointerInputElement(null, null, objArr, pointerInputEventHandler, 3, null));
    }
}
