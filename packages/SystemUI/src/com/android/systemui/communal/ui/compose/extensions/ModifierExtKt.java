package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import kotlin.Unit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class ModifierExtKt {
    public static final Modifier allowGestures(Modifier modifier, boolean z) {
        return z ? modifier : modifier.then(SuspendingPointerInputFilterKt.pointerInput(modifier, Unit.INSTANCE, new ModifierExtKt$allowGestures$1(null)));
    }
}
