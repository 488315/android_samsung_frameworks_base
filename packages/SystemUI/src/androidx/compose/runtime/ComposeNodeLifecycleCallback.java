package androidx.compose.runtime;

/* loaded from: classes.dex */
public interface ComposeNodeLifecycleCallback {
    void onDeactivate();

    void onRelease();

    void onReuse();
}
