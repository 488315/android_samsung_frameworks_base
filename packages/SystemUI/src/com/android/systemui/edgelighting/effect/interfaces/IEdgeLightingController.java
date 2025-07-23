package com.android.systemui.edgelighting.effect.interfaces;

import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface IEdgeLightingController {
    void registerEdgeWindowCallback(IEdgeLightingWindowCallback iEdgeLightingWindowCallback);

    void showPreview(EdgeEffectInfo edgeEffectInfo, boolean z);

    void stopPreview();

    void unRegisterEdgeWindowCallback();

    void updatePreview(EdgeEffectInfo edgeEffectInfo);
}
