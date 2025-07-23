package androidx.compose.ui;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ComposeUiFlags {
    public static final boolean NewNestedScrollFlingDispatchingEnabled;
    public static final boolean isRectTrackingEnabled;
    public static final boolean isTrackFocusEnabled;
    public static final boolean isViewFocusFixEnabled;

    static {
        new ComposeUiFlags();
        isRectTrackingEnabled = true;
        NewNestedScrollFlingDispatchingEnabled = true;
        isViewFocusFixEnabled = true;
        isTrackFocusEnabled = true;
    }

    private ComposeUiFlags() {
    }
}
