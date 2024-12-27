package com.samsung.vekit.Listener;

public interface AnimationStatusListener extends NativeInterfaceListener {
    void onAnimationCanceled(Object obj);

    void onAnimationFinished(Object obj);

    void onAnimationStarted(Object obj);

    void onAnimationUpdated(Object obj);
}
