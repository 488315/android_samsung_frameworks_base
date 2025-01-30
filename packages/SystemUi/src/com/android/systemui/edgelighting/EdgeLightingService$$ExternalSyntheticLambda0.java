package com.android.systemui.edgelighting;

import com.android.systemui.edgelighting.EdgeLightingService;
import com.samsung.android.edge.SemEdgeLightingInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class EdgeLightingService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ SemEdgeLightingInfo f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ EdgeLightingService$$ExternalSyntheticLambda0(Object obj, String str, SemEdgeLightingInfo semEdgeLightingInfo, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = str;
        this.f$2 = semEdgeLightingInfo;
        this.f$3 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                EdgeLightingService edgeLightingService = (EdgeLightingService) this.f$0;
                String str = this.f$1;
                SemEdgeLightingInfo semEdgeLightingInfo = this.f$2;
                int i = this.f$3;
                boolean z = EdgeLightingService.sConfigured;
                edgeLightingService.startEdgeLighting(str, semEdgeLightingInfo, i);
                break;
            default:
                EdgeLightingService.C12983 c12983 = (EdgeLightingService.C12983) this.f$0;
                String str2 = this.f$1;
                SemEdgeLightingInfo semEdgeLightingInfo2 = this.f$2;
                int i2 = this.f$3;
                EdgeLightingService edgeLightingService2 = c12983.this$0;
                boolean z2 = EdgeLightingService.sConfigured;
                edgeLightingService2.startEdgeLighting(str2, semEdgeLightingInfo2, i2);
                break;
        }
    }
}
