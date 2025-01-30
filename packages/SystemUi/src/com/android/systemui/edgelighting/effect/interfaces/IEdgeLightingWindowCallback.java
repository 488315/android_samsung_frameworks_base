package com.android.systemui.edgelighting.effect.interfaces;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
