package com.android.systemui.statusbar.pipeline.wifi.ui.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
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
        return Integer.hashCode(this.activityId) + ReorderTile$$ExternalSyntheticOutline0.m(this.wifiId, Boolean.hashCode(this.isVisible) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DeXStatusBarWifiIconModel(isVisible=");
        sb.append(this.isVisible);
        sb.append(", wifiId=");
        sb.append(this.wifiId);
        sb.append(", activityId=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.activityId, ")", sb);
    }
}
