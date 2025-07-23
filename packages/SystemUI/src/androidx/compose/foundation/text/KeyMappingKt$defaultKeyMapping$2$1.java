package androidx.compose.foundation.text;

import android.view.KeyEvent;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.Key_androidKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class KeyMappingKt$defaultKeyMapping$2$1 implements KeyMapping {
    public final /* synthetic */ KeyMapping $common;

    public KeyMappingKt$defaultKeyMapping$2$1(KeyMapping keyMapping) {
        this.$common = keyMapping;
    }

    @Override // androidx.compose.foundation.text.KeyMapping
    /* renamed from: map-ZmokQxo */
    public final KeyCommand mo198mapZmokQxo(KeyEvent keyEvent) {
        KeyCommand keyCommand = null;
        if (keyEvent.isShiftPressed() && keyEvent.isCtrlPressed()) {
            long Key = Key_androidKt.Key(keyEvent.getKeyCode());
            MappedKeys.INSTANCE.getClass();
            if (Key.m576equalsimpl0(Key, MappedKeys.DirectionLeft)) {
                keyCommand = KeyCommand.SELECT_LEFT_WORD;
            } else if (Key.m576equalsimpl0(Key, MappedKeys.DirectionRight)) {
                keyCommand = KeyCommand.SELECT_RIGHT_WORD;
            } else if (Key.m576equalsimpl0(Key, MappedKeys.DirectionUp)) {
                keyCommand = KeyCommand.SELECT_PREV_PARAGRAPH;
            } else if (Key.m576equalsimpl0(Key, MappedKeys.DirectionDown)) {
                keyCommand = KeyCommand.SELECT_NEXT_PARAGRAPH;
            }
        } else if (keyEvent.isCtrlPressed()) {
            long Key2 = Key_androidKt.Key(keyEvent.getKeyCode());
            MappedKeys.INSTANCE.getClass();
            if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionLeft)) {
                keyCommand = KeyCommand.LEFT_WORD;
            } else if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionRight)) {
                keyCommand = KeyCommand.RIGHT_WORD;
            } else if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionUp)) {
                keyCommand = KeyCommand.PREV_PARAGRAPH;
            } else if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionDown)) {
                keyCommand = KeyCommand.NEXT_PARAGRAPH;
            } else if (Key.m576equalsimpl0(Key2, MappedKeys.H)) {
                keyCommand = KeyCommand.DELETE_PREV_CHAR;
            } else if (Key.m576equalsimpl0(Key2, MappedKeys.Delete)) {
                keyCommand = KeyCommand.DELETE_NEXT_WORD;
            } else if (Key.m576equalsimpl0(Key2, MappedKeys.Backspace)) {
                keyCommand = KeyCommand.DELETE_PREV_WORD;
            } else if (Key.m576equalsimpl0(Key2, MappedKeys.Backslash)) {
                keyCommand = KeyCommand.DESELECT;
            }
        } else if (keyEvent.isShiftPressed()) {
            long Key3 = Key_androidKt.Key(keyEvent.getKeyCode());
            MappedKeys.INSTANCE.getClass();
            if (Key.m576equalsimpl0(Key3, MappedKeys.MoveHome)) {
                keyCommand = KeyCommand.SELECT_LINE_START;
            } else if (Key.m576equalsimpl0(Key3, MappedKeys.MoveEnd)) {
                keyCommand = KeyCommand.SELECT_LINE_END;
            }
        } else if (keyEvent.isAltPressed()) {
            long Key4 = Key_androidKt.Key(keyEvent.getKeyCode());
            MappedKeys.INSTANCE.getClass();
            if (Key.m576equalsimpl0(Key4, MappedKeys.Backspace)) {
                keyCommand = KeyCommand.DELETE_FROM_LINE_START;
            } else if (Key.m576equalsimpl0(Key4, MappedKeys.Delete)) {
                keyCommand = KeyCommand.DELETE_TO_LINE_END;
            }
        }
        return keyCommand == null ? this.$common.mo198mapZmokQxo(keyEvent) : keyCommand;
    }
}
