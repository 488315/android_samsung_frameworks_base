package androidx.lifecycle;

/* loaded from: classes.dex */
public interface DefaultLifecycleObserver extends FullLifecycleObserver {
    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onCreate() {
    }

    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onDestroy() {
    }

    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onPause() {
    }

    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onStart() {
    }

    @Override // androidx.lifecycle.FullLifecycleObserver
    default void onStop() {
    }
}
