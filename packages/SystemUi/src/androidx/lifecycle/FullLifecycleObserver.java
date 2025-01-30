package androidx.lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface FullLifecycleObserver extends LifecycleObserver {
    void onCreate(LifecycleOwner lifecycleOwner);

    void onDestroy$1();

    void onPause$1();

    void onResume$1();
}
