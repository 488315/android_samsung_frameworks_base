package androidx.compose.foundation.text;

import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEvent;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.Key_androidKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.PropertyReference1Impl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class KeyMappingKt {
    public static final KeyMappingKt$defaultKeyMapping$2$1 defaultKeyMapping;

    static {
        final KeyMappingKt$defaultKeyMapping$1 keyMappingKt$defaultKeyMapping$1 = new PropertyReference1Impl() { // from class: androidx.compose.foundation.text.KeyMappingKt$defaultKeyMapping$1
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj) {
                return Boolean.valueOf(((KeyEvent) obj).nativeKeyEvent.isCtrlPressed());
            }
        };
        defaultKeyMapping = new KeyMappingKt$defaultKeyMapping$2$1(new KeyMapping() { // from class: androidx.compose.foundation.text.KeyMappingKt$commonKeyMapping$1
            @Override // androidx.compose.foundation.text.KeyMapping
            /* renamed from: map-ZmokQxo */
            public final KeyCommand mo198mapZmokQxo(android.view.KeyEvent keyEvent) {
                KeyEvent m577boximpl = KeyEvent.m577boximpl(keyEvent);
                Function1 function1 = Function1.this;
                if (((Boolean) function1.mo779invoke(m577boximpl)).booleanValue() && keyEvent.isShiftPressed()) {
                    long Key = Key_androidKt.Key(keyEvent.getKeyCode());
                    MappedKeys.INSTANCE.getClass();
                    if (Key.m576equalsimpl0(Key, MappedKeys.Z)) {
                        return KeyCommand.REDO;
                    }
                    return null;
                }
                boolean z = true;
                if (((Boolean) function1.mo779invoke(KeyEvent.m577boximpl(keyEvent))).booleanValue()) {
                    long m578getKeyZmokQxo = KeyEvent_androidKt.m578getKeyZmokQxo(keyEvent);
                    MappedKeys mappedKeys = MappedKeys.INSTANCE;
                    mappedKeys.getClass();
                    if (!Key.m576equalsimpl0(m578getKeyZmokQxo, MappedKeys.C)) {
                        mappedKeys.getClass();
                        z = Key.m576equalsimpl0(m578getKeyZmokQxo, MappedKeys.Insert);
                    }
                    if (z) {
                        return KeyCommand.COPY;
                    }
                    mappedKeys.getClass();
                    if (Key.m576equalsimpl0(m578getKeyZmokQxo, MappedKeys.V)) {
                        return KeyCommand.PASTE;
                    }
                    mappedKeys.getClass();
                    if (Key.m576equalsimpl0(m578getKeyZmokQxo, MappedKeys.X)) {
                        return KeyCommand.CUT;
                    }
                    mappedKeys.getClass();
                    if (Key.m576equalsimpl0(m578getKeyZmokQxo, MappedKeys.A)) {
                        return KeyCommand.SELECT_ALL;
                    }
                    mappedKeys.getClass();
                    if (Key.m576equalsimpl0(m578getKeyZmokQxo, MappedKeys.Y)) {
                        return KeyCommand.REDO;
                    }
                    mappedKeys.getClass();
                    if (Key.m576equalsimpl0(m578getKeyZmokQxo, MappedKeys.Z)) {
                        return KeyCommand.UNDO;
                    }
                    return null;
                }
                if (keyEvent.isCtrlPressed()) {
                    return null;
                }
                if (keyEvent.isShiftPressed()) {
                    long Key2 = Key_androidKt.Key(keyEvent.getKeyCode());
                    MappedKeys mappedKeys2 = MappedKeys.INSTANCE;
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionLeft)) {
                        return KeyCommand.SELECT_LEFT_CHAR;
                    }
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionRight)) {
                        return KeyCommand.SELECT_RIGHT_CHAR;
                    }
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionUp)) {
                        return KeyCommand.SELECT_UP;
                    }
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.DirectionDown)) {
                        return KeyCommand.SELECT_DOWN;
                    }
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.PageUp)) {
                        return KeyCommand.SELECT_PAGE_UP;
                    }
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.PageDown)) {
                        return KeyCommand.SELECT_PAGE_DOWN;
                    }
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.MoveHome)) {
                        return KeyCommand.SELECT_LINE_START;
                    }
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.MoveEnd)) {
                        return KeyCommand.SELECT_LINE_END;
                    }
                    mappedKeys2.getClass();
                    if (Key.m576equalsimpl0(Key2, MappedKeys.Insert)) {
                        return KeyCommand.PASTE;
                    }
                    return null;
                }
                long Key3 = Key_androidKt.Key(keyEvent.getKeyCode());
                MappedKeys mappedKeys3 = MappedKeys.INSTANCE;
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.DirectionLeft)) {
                    return KeyCommand.LEFT_CHAR;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.DirectionRight)) {
                    return KeyCommand.RIGHT_CHAR;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.DirectionUp)) {
                    return KeyCommand.UP;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.DirectionDown)) {
                    return KeyCommand.DOWN;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.PageUp)) {
                    return KeyCommand.PAGE_UP;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.PageDown)) {
                    return KeyCommand.PAGE_DOWN;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.MoveHome)) {
                    return KeyCommand.LINE_START;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.MoveEnd)) {
                    return KeyCommand.LINE_END;
                }
                mappedKeys3.getClass();
                if (!Key.m576equalsimpl0(Key3, MappedKeys.Enter)) {
                    mappedKeys3.getClass();
                    z = Key.m576equalsimpl0(Key3, MappedKeys.NumPadEnter);
                }
                if (z) {
                    return KeyCommand.NEW_LINE;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.Backspace)) {
                    return KeyCommand.DELETE_PREV_CHAR;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.Delete)) {
                    return KeyCommand.DELETE_NEXT_CHAR;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.Paste)) {
                    return KeyCommand.PASTE;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.Cut)) {
                    return KeyCommand.CUT;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.Copy)) {
                    return KeyCommand.COPY;
                }
                mappedKeys3.getClass();
                if (Key.m576equalsimpl0(Key3, MappedKeys.Tab)) {
                    return KeyCommand.TAB;
                }
                return null;
            }
        });
    }
}
