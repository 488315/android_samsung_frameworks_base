package androidx.compose.foundation.text;

import android.view.KeyEvent;
import androidx.compose.ui.input.key.Key_androidKt;

/* loaded from: classes.dex */
public abstract class TextFieldFocusModifier_androidKt {
    /* renamed from: access$isKeyCode-YhN2O0w, reason: not valid java name */
    public static final boolean m206access$isKeyCodeYhN2O0w(int i, KeyEvent keyEvent) {
        return ((int) (Key_androidKt.Key(keyEvent.getKeyCode()) >> 32)) == i;
    }
}
