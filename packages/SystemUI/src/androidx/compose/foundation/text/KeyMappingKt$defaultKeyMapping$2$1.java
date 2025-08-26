package androidx.compose.foundation.text;

import android.view.KeyEvent;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.Key_androidKt;

/* loaded from: classes.dex */
public final class KeyMappingKt$defaultKeyMapping$2$1 implements KeyMapping {
    public final /* synthetic */ KeyMapping $common;

    public KeyMappingKt$defaultKeyMapping$2$1(KeyMapping keyMapping) {
        this.$common = keyMapping;
    }

    @Override // androidx.compose.foundation.text.KeyMapping
    /* renamed from: map-ZmokQxo */
    public final KeyCommand mo199mapZmokQxo(KeyEvent keyEvent) {
        KeyCommand keyCommand = null;
        if (keyEvent.isShiftPressed() && keyEvent.isCtrlPressed()) {
            long jKey = Key_androidKt.Key(keyEvent.getKeyCode());
            MappedKeys.INSTANCE.getClass();
            if (Key.m578equalsimpl0(jKey, MappedKeys.DirectionLeft)) {
                keyCommand = KeyCommand.SELECT_LEFT_WORD;
            } else if (Key.m578equalsimpl0(jKey, MappedKeys.DirectionRight)) {
                keyCommand = KeyCommand.SELECT_RIGHT_WORD;
            } else if (Key.m578equalsimpl0(jKey, MappedKeys.DirectionUp)) {
                keyCommand = KeyCommand.SELECT_PREV_PARAGRAPH;
            } else if (Key.m578equalsimpl0(jKey, MappedKeys.DirectionDown)) {
                keyCommand = KeyCommand.SELECT_NEXT_PARAGRAPH;
            }
        } else if (keyEvent.isCtrlPressed()) {
            long jKey2 = Key_androidKt.Key(keyEvent.getKeyCode());
            MappedKeys.INSTANCE.getClass();
            if (Key.m578equalsimpl0(jKey2, MappedKeys.DirectionLeft)) {
                keyCommand = KeyCommand.LEFT_WORD;
            } else if (Key.m578equalsimpl0(jKey2, MappedKeys.DirectionRight)) {
                keyCommand = KeyCommand.RIGHT_WORD;
            } else if (Key.m578equalsimpl0(jKey2, MappedKeys.DirectionUp)) {
                keyCommand = KeyCommand.PREV_PARAGRAPH;
            } else if (Key.m578equalsimpl0(jKey2, MappedKeys.DirectionDown)) {
                keyCommand = KeyCommand.NEXT_PARAGRAPH;
            } else if (Key.m578equalsimpl0(jKey2, MappedKeys.H)) {
                keyCommand = KeyCommand.DELETE_PREV_CHAR;
            } else if (Key.m578equalsimpl0(jKey2, MappedKeys.Delete)) {
                keyCommand = KeyCommand.DELETE_NEXT_WORD;
            } else if (Key.m578equalsimpl0(jKey2, MappedKeys.Backspace)) {
                keyCommand = KeyCommand.DELETE_PREV_WORD;
            } else if (Key.m578equalsimpl0(jKey2, MappedKeys.Backslash)) {
                keyCommand = KeyCommand.DESELECT;
            }
        } else if (keyEvent.isShiftPressed()) {
            long jKey3 = Key_androidKt.Key(keyEvent.getKeyCode());
            MappedKeys.INSTANCE.getClass();
            if (Key.m578equalsimpl0(jKey3, MappedKeys.MoveHome)) {
                keyCommand = KeyCommand.SELECT_LINE_START;
            } else if (Key.m578equalsimpl0(jKey3, MappedKeys.MoveEnd)) {
                keyCommand = KeyCommand.SELECT_LINE_END;
            }
        } else if (keyEvent.isAltPressed()) {
            long jKey4 = Key_androidKt.Key(keyEvent.getKeyCode());
            MappedKeys.INSTANCE.getClass();
            if (Key.m578equalsimpl0(jKey4, MappedKeys.Backspace)) {
                keyCommand = KeyCommand.DELETE_FROM_LINE_START;
            } else if (Key.m578equalsimpl0(jKey4, MappedKeys.Delete)) {
                keyCommand = KeyCommand.DELETE_TO_LINE_END;
            }
        }
        return keyCommand == null ? this.$common.mo199mapZmokQxo(keyEvent) : keyCommand;
    }
}
