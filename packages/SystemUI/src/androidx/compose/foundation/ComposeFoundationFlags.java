package androidx.compose.foundation;

/* loaded from: classes.dex */
public final class ComposeFoundationFlags {
    public static final boolean DragGesturePickUpEnabled;
    public static final boolean NewNestedFlingPropagationEnabled;
    public static final boolean isDetectTapGesturesImmediateCoroutineDispatchEnabled;

    static {
        new ComposeFoundationFlags();
        NewNestedFlingPropagationEnabled = true;
        DragGesturePickUpEnabled = true;
        isDetectTapGesturesImmediateCoroutineDispatchEnabled = true;
    }

    private ComposeFoundationFlags() {
    }
}
