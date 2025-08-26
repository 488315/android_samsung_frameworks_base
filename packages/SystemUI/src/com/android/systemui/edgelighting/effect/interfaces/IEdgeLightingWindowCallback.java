package com.android.systemui.edgelighting.effect.interfaces;

/* loaded from: classes2.dex */
public interface IEdgeLightingWindowCallback {
    void doActionNotification();

    void onClickExpandButton(String str);

    void onClickToastInWindow();

    void onDismissEdgeWindow();

    void onExtendLightingDuration();

    void onFling(boolean z);

    void onFlingDownInWindow(boolean z);

    void onShowEdgeWindow();

    void onSwipeToastInWindow();
}
