package androidx.compose.foundation.text;

import android.view.KeyEvent;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.Key_androidKt;

/* loaded from: classes.dex */
public abstract class KeyMapping_androidKt {
    public static final KeyMapping_androidKt$platformDefaultKeyMapping$1 platformDefaultKeyMapping = new KeyMapping() { // from class: androidx.compose.foundation.text.KeyMapping_androidKt$platformDefaultKeyMapping$1
        @Override // androidx.compose.foundation.text.KeyMapping
        /* renamed from: map-ZmokQxo */
        public final KeyCommand mo199mapZmokQxo(KeyEvent keyEvent) {
            KeyCommand keyCommand = null;
            if (keyEvent.isShiftPressed() && keyEvent.isAltPressed()) {
                long jKey = Key_androidKt.Key(keyEvent.getKeyCode());
                MappedKeys.INSTANCE.getClass();
                if (Key.m578equalsimpl0(jKey, MappedKeys.DirectionLeft)) {
                    keyCommand = KeyCommand.SELECT_LINE_LEFT;
                } else if (Key.m578equalsimpl0(jKey, MappedKeys.DirectionRight)) {
                    keyCommand = KeyCommand.SELECT_LINE_RIGHT;
                } else if (Key.m578equalsimpl0(jKey, MappedKeys.DirectionUp)) {
                    keyCommand = KeyCommand.SELECT_HOME;
                } else if (Key.m578equalsimpl0(jKey, MappedKeys.DirectionDown)) {
                    keyCommand = KeyCommand.SELECT_END;
                }
            } else if (keyEvent.isAltPressed()) {
                long jKey2 = Key_androidKt.Key(keyEvent.getKeyCode());
                MappedKeys.INSTANCE.getClass();
                if (Key.m578equalsimpl0(jKey2, MappedKeys.DirectionLeft)) {
                    keyCommand = KeyCommand.LINE_LEFT;
                } else if (Key.m578equalsimpl0(jKey2, MappedKeys.DirectionRight)) {
                    keyCommand = KeyCommand.LINE_RIGHT;
                } else if (Key.m578equalsimpl0(jKey2, MappedKeys.DirectionUp)) {
                    keyCommand = KeyCommand.HOME;
                } else if (Key.m578equalsimpl0(jKey2, MappedKeys.DirectionDown)) {
                    keyCommand = KeyCommand.END;
                }
            }
            return keyCommand == null ? KeyMappingKt.defaultKeyMapping.mo199mapZmokQxo(keyEvent) : keyCommand;
        }
    };
}
