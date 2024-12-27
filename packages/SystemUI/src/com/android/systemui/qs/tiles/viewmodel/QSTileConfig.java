package com.android.systemui.qs.tiles.viewmodel;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.viewmodel.QSTilePolicy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSTileConfig {
    public final InstanceId instanceId;
    public final String metricsSpec;
    public final QSTilePolicy policy;
    public final TileSpec tileSpec;
    public final QSTileUIConfig uiConfig;

    public QSTileConfig(TileSpec tileSpec, QSTileUIConfig qSTileUIConfig, InstanceId instanceId, String str, QSTilePolicy qSTilePolicy) {
        this.tileSpec = tileSpec;
        this.uiConfig = qSTileUIConfig;
        this.instanceId = instanceId;
        this.metricsSpec = str;
        this.policy = qSTilePolicy;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QSTileConfig)) {
            return false;
        }
        QSTileConfig qSTileConfig = (QSTileConfig) obj;
        return Intrinsics.areEqual(this.tileSpec, qSTileConfig.tileSpec) && Intrinsics.areEqual(this.uiConfig, qSTileConfig.uiConfig) && Intrinsics.areEqual(this.instanceId, qSTileConfig.instanceId) && Intrinsics.areEqual(this.metricsSpec, qSTileConfig.metricsSpec) && Intrinsics.areEqual(this.policy, qSTileConfig.policy);
    }

    public final int hashCode() {
        return this.policy.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.instanceId.hashCode() + ((this.uiConfig.hashCode() + (this.tileSpec.hashCode() * 31)) * 31)) * 31, 31, this.metricsSpec);
    }

    public final String toString() {
        return "QSTileConfig(tileSpec=" + this.tileSpec + ", uiConfig=" + this.uiConfig + ", instanceId=" + this.instanceId + ", metricsSpec=" + this.metricsSpec + ", policy=" + this.policy + ")";
    }

    public /* synthetic */ QSTileConfig(TileSpec tileSpec, QSTileUIConfig qSTileUIConfig, InstanceId instanceId, String str, QSTilePolicy qSTilePolicy, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(tileSpec, qSTileUIConfig, instanceId, (i & 8) != 0 ? tileSpec.getSpec() : str, (i & 16) != 0 ? QSTilePolicy.NoRestrictions.INSTANCE : qSTilePolicy);
    }
}
