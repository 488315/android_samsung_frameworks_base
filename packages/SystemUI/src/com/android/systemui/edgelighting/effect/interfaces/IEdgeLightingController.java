package com.android.systemui.edgelighting.effect.interfaces;

import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;

public interface IEdgeLightingController {
    void registerEdgeWindowCallback(IEdgeLightingWindowCallback iEdgeLightingWindowCallback);

    void showPreview(EdgeEffectInfo edgeEffectInfo, boolean z);

    void stopPreview();

    void unRegisterEdgeWindowCallback();

    void updatePreview(EdgeEffectInfo edgeEffectInfo);
}
