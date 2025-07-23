package android.window;

/* loaded from: classes5.dex */
public interface OnBackAnimationCallback extends OnBackInvokedCallback {
    default void onBackCancelled() {
    }

    default void onBackProgressed(BackEvent backEvent) {
    }

    default void onBackStarted(BackEvent backEvent) {
    }
}
