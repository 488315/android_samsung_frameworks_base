package com.android.systemui.statusbar.pipeline.wifi.p029ui.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public final int hashCode() {
        boolean z = this.isVisible;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return Integer.hashCode(this.activityId) + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.wifiId, r0 * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DeXStatusBarWifiIconModel(isVisible=");
        sb.append(this.isVisible);
        sb.append(", wifiId=");
        sb.append(this.wifiId);
        sb.append(", activityId=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.activityId, ")");
    }
}
