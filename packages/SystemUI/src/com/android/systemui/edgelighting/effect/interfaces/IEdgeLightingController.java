package com.android.systemui.edgelighting.effect.interfaces;

import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface IEdgeLightingController {
    void registerEdgeWindowCallback(IEdgeLightingWindowCallback iEdgeLightingWindowCallback);

    void showPreview(EdgeEffectInfo edgeEffectInfo, boolean z);

    void stopPreview();

    void unRegisterEdgeWindowCallback();

    void updatePreview(EdgeEffectInfo edgeEffectInfo);
}
