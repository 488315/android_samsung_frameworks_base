package androidx.compose.ui.input.key;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class KeyEvent_androidKt {
    /* renamed from: getKey-ZmokQxo, reason: not valid java name */
    public static final long m578getKeyZmokQxo(android.view.KeyEvent keyEvent) {
        return Key_androidKt.Key(keyEvent.getKeyCode());
    }

    /* renamed from: getType-ZmokQxo, reason: not valid java name */
    public static final int m579getTypeZmokQxo(android.view.KeyEvent keyEvent) {
        int action = keyEvent.getAction();
        if (action == 0) {
            KeyEventType.Companion.getClass();
            return KeyEventType.KeyDown;
        }
        if (action != 1) {
            KeyEventType.Companion.getClass();
            return 0;
        }
        KeyEventType.Companion.getClass();
        return KeyEventType.KeyUp;
    }
}
