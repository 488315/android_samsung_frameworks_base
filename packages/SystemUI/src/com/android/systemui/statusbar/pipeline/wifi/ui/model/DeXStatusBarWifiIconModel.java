package com.android.systemui.statusbar.pipeline.wifi.ui.model;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DeXStatusBarWifiIconModel {
    public final int activityId;
    public final boolean isVisible;
    public final int wifiId;

    public DeXStatusBarWifiIconModel(boolean z, int i, int i2) {
        this.isVisible = z;
        this.wifiId = i;
        this.activityId = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DeXStatusBarWifiIconModel)) {
            return false;
        }
        DeXStatusBarWifiIconModel deXStatusBarWifiIconModel = (DeXStatusBarWifiIconModel) obj;
        return this.isVisible == deXStatusBarWifiIconModel.isVisible && this.wifiId == deXStatusBarWifiIconModel.wifiId && this.activityId == deXStatusBarWifiIconModel.activityId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.activityId) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.wifiId, Boolean.hashCode(this.isVisible) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DeXStatusBarWifiIconModel(isVisible=");
        sb.append(this.isVisible);
        sb.append(", wifiId=");
        sb.append(this.wifiId);
        sb.append(", activityId=");
        return Anchor$$ExternalSyntheticOutline0.m(this.activityId, ")", sb);
    }
}
