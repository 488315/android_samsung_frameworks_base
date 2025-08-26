package com.android.systemui.qs.tiles.base.shared.model;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTilePolicy;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class QSTileConfig {
    public final boolean autoRemoveOnUnavailable;
    public final TileCategory category;
    public final InstanceId instanceId;
    public final String metricsSpec;
    public final QSTilePolicy policy;
    public final TileSpec tileSpec;
    public final QSTileUIConfig uiConfig;

    public QSTileConfig(TileSpec tileSpec, QSTileUIConfig qSTileUIConfig, InstanceId instanceId, TileCategory tileCategory) {
        this(tileSpec, qSTileUIConfig, instanceId, tileCategory, null, null, false, 112, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QSTileConfig)) {
            return false;
        }
        QSTileConfig qSTileConfig = (QSTileConfig) obj;
        return Intrinsics.areEqual(this.tileSpec, qSTileConfig.tileSpec) && Intrinsics.areEqual(this.uiConfig, qSTileConfig.uiConfig) && Intrinsics.areEqual(this.instanceId, qSTileConfig.instanceId) && this.category == qSTileConfig.category && Intrinsics.areEqual(this.metricsSpec, qSTileConfig.metricsSpec) && Intrinsics.areEqual(this.policy, qSTileConfig.policy) && this.autoRemoveOnUnavailable == qSTileConfig.autoRemoveOnUnavailable;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.autoRemoveOnUnavailable) + ((this.policy.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.category.hashCode() + ((this.instanceId.hashCode() + ((this.uiConfig.hashCode() + (this.tileSpec.hashCode() * 31)) * 31)) * 31)) * 31, 31, this.metricsSpec)) * 31);
    }

    public final String toString() {
        InstanceId instanceId = this.instanceId;
        StringBuilder sb = new StringBuilder("QSTileConfig(tileSpec=");
        sb.append(this.tileSpec);
        sb.append(", uiConfig=");
        sb.append(this.uiConfig);
        sb.append(", instanceId=");
        sb.append(instanceId);
        sb.append(", category=");
        sb.append(this.category);
        sb.append(", metricsSpec=");
        sb.append(this.metricsSpec);
        sb.append(", policy=");
        sb.append(this.policy);
        sb.append(", autoRemoveOnUnavailable=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.autoRemoveOnUnavailable, ")");
    }

    public QSTileConfig(TileSpec tileSpec, QSTileUIConfig qSTileUIConfig, InstanceId instanceId, TileCategory tileCategory, String str) {
        this(tileSpec, qSTileUIConfig, instanceId, tileCategory, str, null, false, 96, null);
    }

    public QSTileConfig(TileSpec tileSpec, QSTileUIConfig qSTileUIConfig, InstanceId instanceId, TileCategory tileCategory, String str, QSTilePolicy qSTilePolicy) {
        this(tileSpec, qSTileUIConfig, instanceId, tileCategory, str, qSTilePolicy, false, 64, null);
    }

    public QSTileConfig(TileSpec tileSpec, QSTileUIConfig qSTileUIConfig, InstanceId instanceId, TileCategory tileCategory, String str, QSTilePolicy qSTilePolicy, boolean z) {
        this.tileSpec = tileSpec;
        this.uiConfig = qSTileUIConfig;
        this.instanceId = instanceId;
        this.category = tileCategory;
        this.metricsSpec = str;
        this.policy = qSTilePolicy;
        this.autoRemoveOnUnavailable = z;
    }

    public /* synthetic */ QSTileConfig(TileSpec tileSpec, QSTileUIConfig qSTileUIConfig, InstanceId instanceId, TileCategory tileCategory, String str, QSTilePolicy qSTilePolicy, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(tileSpec, qSTileUIConfig, instanceId, tileCategory, (i & 16) != 0 ? tileSpec.getSpec() : str, (i & 32) != 0 ? QSTilePolicy.NoRestrictions.INSTANCE : qSTilePolicy, (i & 64) != 0 ? true : z);
    }
}
