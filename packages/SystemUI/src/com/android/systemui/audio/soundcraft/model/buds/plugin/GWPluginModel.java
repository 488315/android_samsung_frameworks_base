package com.android.systemui.audio.soundcraft.model.buds.plugin;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class GWPluginModel {
    public final String deviceId;
    public final int isConnected;
    public final boolean isEnabled;
    public final String packageName;
    public final String pluginType;

    public GWPluginModel(String str, String str2, String str3, boolean z, int i) {
        this.deviceId = str;
        this.packageName = str2;
        this.pluginType = str3;
        this.isEnabled = z;
        this.isConnected = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GWPluginModel)) {
            return false;
        }
        GWPluginModel gWPluginModel = (GWPluginModel) obj;
        return Intrinsics.areEqual(this.deviceId, gWPluginModel.deviceId) && Intrinsics.areEqual(this.packageName, gWPluginModel.packageName) && Intrinsics.areEqual(this.pluginType, gWPluginModel.pluginType) && this.isEnabled == gWPluginModel.isEnabled && this.isConnected == gWPluginModel.isConnected;
    }

    public final int hashCode() {
        return Integer.hashCode(this.isConnected) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.deviceId.hashCode() * 31, 31, this.packageName), 31, this.pluginType), 31, this.isEnabled);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("GWPluginModel(deviceId=");
        sb.append(this.deviceId);
        sb.append(", packageName=");
        sb.append(this.packageName);
        sb.append(", pluginType=");
        sb.append(this.pluginType);
        sb.append(", isEnabled=");
        sb.append(this.isEnabled);
        sb.append(", isConnected=");
        return Anchor$$ExternalSyntheticOutline0.m(this.isConnected, ")", sb);
    }
}
