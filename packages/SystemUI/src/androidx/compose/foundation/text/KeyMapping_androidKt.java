package androidx.compose.foundation.text;

import android.view.KeyEvent;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.Key_androidKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class KeyMapping_androidKt {
    public static final KeyMapping_androidKt$platformDefaultKeyMapping$1 platformDefaultKeyMapping = new KeyMapping() { // from class: androidx.compose.foundation.text.KeyMapping_androidKt$platformDefaultKeyMapping$1
        @Override // androidx.compose.foundation.text.KeyMapping
        /* renamed from: map-ZmokQxo */
        public final KeyCommand mo198mapZmokQxo(KeyEvent keyEvent) {
            KeyCommand keyCommand = null;
            if (keyEvent.isShiftPressed() && keyEvent.isAltPressed()) {
                long Key = Key_androidKt.Key(keyEvent.getKeyCode());
                MappedKeys.INSTANCE.getClass();
                if (Key.m576equalsimpl0(Key, MappedKeys.DirectionLeft)) {
                    keyCommand = KeyCommand.SELECT_LINE_LEFT;
                } else if (Key.m576equalsimpl0(Key, MappedKeys.DirectionRight)) {
                    keyCommand = KeyCommand.SELECT_LINE_RIGHT;
                } else if (Key.m576equalsimpl0(Key, MappedKeys.DirectionUp)) {
                    keyCommand = KeyCommand.SELECT_HOME;
                } else if (Key.m576equalsimpl0(Key, MappedKeys.DirectionDown)) {
                    keyCommand = KeyCommand.SELECT_END;
                }
            } else if (keyEvent.isAltPressed()) {
                long Key2 = Key_androidKt.Key(keyEvent.getKeyCode());
                MappedKeys.INSTANCE.getClass();
                if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionLeft)) {
                    keyCommand = KeyCommand.LINE_LEFT;
                } else if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionRight)) {
                    keyCommand = KeyCommand.LINE_RIGHT;
                } else if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionUp)) {
                    keyCommand = KeyCommand.HOME;
                } else if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionDown)) {
                    keyCommand = KeyCommand.END;
                }
            }
            return keyCommand == null ? KeyMappingKt.defaultKeyMapping.mo198mapZmokQxo(keyEvent) : keyCommand;
        }
    };
}
