package android.view;

public interface WindowInsetsAnimationControlListener {
    void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController);

    void onFinished(WindowInsetsAnimationController windowInsetsAnimationController);

    void onReady(WindowInsetsAnimationController windowInsetsAnimationController, int i);
}
