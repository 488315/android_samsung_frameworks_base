package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import kotlin.Unit;

public abstract class ModifierExtKt {
    public static final Modifier allowGestures(Modifier modifier, boolean z) {
        return z ? modifier : modifier.then(SuspendingPointerInputFilterKt.pointerInput(modifier, Unit.INSTANCE, new ModifierExtKt$allowGestures$1(null)));
    }
}
