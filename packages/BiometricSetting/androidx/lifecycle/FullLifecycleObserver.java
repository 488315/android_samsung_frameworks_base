package androidx.lifecycle;

/* loaded from: classes.dex */
interface FullLifecycleObserver extends LifecycleObserver {
    void onCreate();

    void onDestroy();

    void onPause();

    void onResume();

    void onStart();

    void onStop();
}
