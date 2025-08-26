package androidx.lifecycle;

/* loaded from: classes.dex */
public abstract class LifecycleOwnerKt {
    public static final LifecycleCoroutineScopeImpl getLifecycleScope(LifecycleOwner lifecycleOwner) {
        return LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle());
    }
}
