package androidx.compose.ui;

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
