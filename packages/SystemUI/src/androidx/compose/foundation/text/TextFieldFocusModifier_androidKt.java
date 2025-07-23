package androidx.compose.foundation.text;

import android.view.KeyEvent;
import androidx.compose.ui.input.key.Key_androidKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TextFieldFocusModifier_androidKt {
    /* renamed from: access$isKeyCode-YhN2O0w, reason: not valid java name */
    public static final boolean m205access$isKeyCodeYhN2O0w(int i, KeyEvent keyEvent) {
        return ((int) (Key_androidKt.Key(keyEvent.getKeyCode()) >> 32)) == i;
    }
}
