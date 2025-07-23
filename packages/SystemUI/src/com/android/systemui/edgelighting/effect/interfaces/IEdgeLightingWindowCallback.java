package com.android.systemui.edgelighting.effect.interfaces;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
