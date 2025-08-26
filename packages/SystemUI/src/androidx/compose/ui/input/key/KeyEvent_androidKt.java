package androidx.compose.ui.input.key;

/* loaded from: classes.dex */
public abstract class KeyEvent_androidKt {
    /* renamed from: getKey-ZmokQxo, reason: not valid java name */
    public static final long m580getKeyZmokQxo(android.view.KeyEvent keyEvent) {
        return Key_androidKt.Key(keyEvent.getKeyCode());
    }

    /* renamed from: getType-ZmokQxo, reason: not valid java name */
    public static final int m581getTypeZmokQxo(android.view.KeyEvent keyEvent) {
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
