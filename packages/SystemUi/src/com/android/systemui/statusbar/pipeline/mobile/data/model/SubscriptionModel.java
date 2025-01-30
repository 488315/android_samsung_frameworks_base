package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.os.ParcelUuid;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscriptionModel {
    public final boolean bootstrap;
    public final boolean embedded;
    public final ParcelUuid groupUuid;
    public final boolean isOpportunistic;
    public final int simSlotId;
    public final int subscriptionId;

    public SubscriptionModel(int i, int i2, boolean z, ParcelUuid parcelUuid, boolean z2, boolean z3) {
        this.subscriptionId = i;
        this.simSlotId = i2;
        this.isOpportunistic = z;
        this.groupUuid = parcelUuid;
        this.bootstrap = z2;
        this.embedded = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubscriptionModel)) {
            return false;
        }
        SubscriptionModel subscriptionModel = (SubscriptionModel) obj;
        return this.subscriptionId == subscriptionModel.subscriptionId && this.simSlotId == subscriptionModel.simSlotId && this.isOpportunistic == subscriptionModel.isOpportunistic && Intrinsics.areEqual(this.groupUuid, subscriptionModel.groupUuid) && this.bootstrap == subscriptionModel.bootstrap && this.embedded == subscriptionModel.embedded;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.simSlotId, Integer.hashCode(this.subscriptionId) * 31, 31);
        boolean z = this.isOpportunistic;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (m42m + i) * 31;
        ParcelUuid parcelUuid = this.groupUuid;
        int hashCode = (i2 + (parcelUuid == null ? 0 : parcelUuid.hashCode())) * 31;
        boolean z2 = this.bootstrap;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (hashCode + i3) * 31;
        boolean z3 = this.embedded;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SubscriptionModel(subscriptionId=");
        sb.append(this.subscriptionId);
        sb.append(", simSlotId=");
        sb.append(this.simSlotId);
        sb.append(", isOpportunistic=");
        sb.append(this.isOpportunistic);
        sb.append(", groupUuid=");
        sb.append(this.groupUuid);
        sb.append(", bootstrap=");
        sb.append(this.bootstrap);
        sb.append(", embedded=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.embedded, ")");
    }

    public /* synthetic */ SubscriptionModel(int i, int i2, boolean z, ParcelUuid parcelUuid, boolean z2, boolean z3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i3 & 2) != 0 ? -1 : i2, (i3 & 4) != 0 ? false : z, (i3 & 8) != 0 ? null : parcelUuid, (i3 & 16) != 0 ? false : z2, (i3 & 32) != 0 ? false : z3);
    }
}
