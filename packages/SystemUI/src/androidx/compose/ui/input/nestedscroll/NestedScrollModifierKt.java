package androidx.compose.ui.input.nestedscroll;

import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public abstract class NestedScrollModifierKt {
    public static final Modifier nestedScroll(Modifier modifier, NestedScrollConnection nestedScrollConnection, NestedScrollDispatcher nestedScrollDispatcher) {
        return modifier.then(new NestedScrollElement(nestedScrollConnection, nestedScrollDispatcher));
    }
}
