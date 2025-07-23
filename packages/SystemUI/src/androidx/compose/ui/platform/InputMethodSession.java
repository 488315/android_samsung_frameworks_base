package androidx.compose.ui.platform;

import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.node.WeakReference;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class InputMethodSession {
    public boolean disposed;
    public final Function0 onAllConnectionsClosed;
    public final PlatformTextInputMethodRequest request;
    public final Object lock = new Object();
    public final MutableVector connections = new MutableVector(new WeakReference[16], 0);

    public InputMethodSession(PlatformTextInputMethodRequest platformTextInputMethodRequest, Function0 function0) {
        this.request = platformTextInputMethodRequest;
        this.onAllConnectionsClosed = function0;
    }
}
