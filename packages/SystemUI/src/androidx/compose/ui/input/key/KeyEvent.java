package androidx.compose.ui.input.key;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyEvent {
    public final android.view.KeyEvent nativeKeyEvent;

    private /* synthetic */ KeyEvent(android.view.KeyEvent keyEvent) {
        this.nativeKeyEvent = keyEvent;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ KeyEvent m577boximpl(android.view.KeyEvent keyEvent) {
        return new KeyEvent(keyEvent);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof KeyEvent) && Intrinsics.areEqual(this.nativeKeyEvent, ((KeyEvent) obj).nativeKeyEvent);
    }

    public final int hashCode() {
        return this.nativeKeyEvent.hashCode();
    }

    public final String toString() {
        return "KeyEvent(nativeKeyEvent=" + this.nativeKeyEvent + ')';
    }
}
