package androidx.compose.ui.focus;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CancelIndicatingFocusBoundaryScope implements FocusEnterExitScope {
    public boolean isCanceled;
    public final int requestedFocusDirection;

    public /* synthetic */ CancelIndicatingFocusBoundaryScope(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    private CancelIndicatingFocusBoundaryScope(int i) {
        this.requestedFocusDirection = i;
    }
}
