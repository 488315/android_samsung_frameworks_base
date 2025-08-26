package androidx.compose.runtime;

/* loaded from: classes.dex */
public interface RememberObserver {
    void onAbandoned();

    void onForgotten();

    void onRemembered();
}
