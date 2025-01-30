package com.android.systemui.edgelighting.effect.interfaces;

import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface IEdgeLightingController {
    boolean isUsingELPlusEffect();

    void registerEdgeWindowCallback(IEdgeLightingWindowCallback iEdgeLightingWindowCallback);

    void showPreview(EdgeEffectInfo edgeEffectInfo, boolean z);

    void stopPreview();

    void unRegisterEdgeWindowCallback();

    void updatePreview(EdgeEffectInfo edgeEffectInfo);
}
