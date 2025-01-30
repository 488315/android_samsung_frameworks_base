package androidx.lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface DefaultLifecycleObserver extends FullLifecycleObserver {
    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onCreate(LifecycleOwner lifecycleOwner) {
    }

    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onDestroy$1() {
    }

    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onPause$1() {
    }

    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onResume$1() {
    }
}
