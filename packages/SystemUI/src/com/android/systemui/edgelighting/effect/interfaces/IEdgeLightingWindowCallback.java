package com.android.systemui.edgelighting.effect.interfaces;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
