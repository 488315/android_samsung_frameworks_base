package androidx.compose.ui.focus;

import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public interface FocusProperties {
    boolean getCanFocus();

    void setCanFocus(boolean z);

    default void setOnEnter(Function1 function1) {
    }

    default void setOnExit(Function1 function1) {
    }
}
