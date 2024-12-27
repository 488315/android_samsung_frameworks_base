package com.android.systemui.edgelighting.effect.interfaces;

public interface IEdgeLightingWindowCallback {
    void doActionNotification();

    void onClickExpandButton(String str);

    void onClickToastInWindow();

    void onDismissEdgeWindow();

    void onExtendLightingDuration();

    void onFling(boolean z, boolean z2);

    void onFlingDownInWindow(boolean z);

    void onShowEdgeWindow();

    void onSwipeToastInWindow();
}
