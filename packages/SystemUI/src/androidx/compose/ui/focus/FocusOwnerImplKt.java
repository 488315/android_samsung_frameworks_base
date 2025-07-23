package androidx.compose.ui.focus;

import androidx.compose.ui.focus.FocusDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FocusOwnerImplKt {
    /* renamed from: is1dFocusSearch-3ESFkO8, reason: not valid java name */
    public static final boolean m375is1dFocusSearch3ESFkO8(int i) {
        FocusDirection.Companion companion = FocusDirection.Companion;
        companion.getClass();
        if (i == FocusDirection.Next) {
            return true;
        }
        companion.getClass();
        return i == FocusDirection.Previous;
    }
}
