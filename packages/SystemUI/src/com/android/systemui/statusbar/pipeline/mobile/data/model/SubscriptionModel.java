package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.os.ParcelUuid;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubscriptionModel {
    public final boolean bootstrap;
    public final String carrierName;
    public final boolean embedded;
    public final ParcelUuid groupUuid;
    public final boolean isExclusivelyNonTerrestrial;
    public final boolean isOpportunistic;
    public final int profileClass;
    public final int subscriptionId;

    public SubscriptionModel(int i, boolean z, boolean z2, ParcelUuid parcelUuid, String str, int i2, boolean z3, boolean z4) {
        this.subscriptionId = i;
        this.isOpportunistic = z;
        this.isExclusivelyNonTerrestrial = z2;
        this.groupUuid = parcelUuid;
        this.carrierName = str;
        this.profileClass = i2;
        this.embedded = z3;
        this.bootstrap = z4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubscriptionModel)) {
            return false;
        }
        SubscriptionModel subscriptionModel = (SubscriptionModel) obj;
        return this.subscriptionId == subscriptionModel.subscriptionId && this.isOpportunistic == subscriptionModel.isOpportunistic && this.isExclusivelyNonTerrestrial == subscriptionModel.isExclusivelyNonTerrestrial && Intrinsics.areEqual(this.groupUuid, subscriptionModel.groupUuid) && Intrinsics.areEqual(this.carrierName, subscriptionModel.carrierName) && this.profileClass == subscriptionModel.profileClass && this.embedded == subscriptionModel.embedded && this.bootstrap == subscriptionModel.bootstrap;
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.subscriptionId) * 31, 31, this.isOpportunistic), 31, this.isExclusivelyNonTerrestrial);
        ParcelUuid parcelUuid = this.groupUuid;
        return Boolean.hashCode(this.bootstrap) + TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.profileClass, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((m + (parcelUuid == null ? 0 : parcelUuid.hashCode())) * 31, 31, this.carrierName), 31), 31, this.embedded);
    }

    public final String toString() {
        ParcelUuid parcelUuid = this.groupUuid;
        StringBuilder sb = new StringBuilder("SubscriptionModel(subscriptionId=");
        sb.append(this.subscriptionId);
        sb.append(", isOpportunistic=");
        sb.append(this.isOpportunistic);
        sb.append(", isExclusivelyNonTerrestrial=");
        sb.append(this.isExclusivelyNonTerrestrial);
        sb.append(", groupUuid=");
        sb.append(parcelUuid);
        sb.append(", carrierName=");
        sb.append(this.carrierName);
        sb.append(", profileClass=");
        sb.append(this.profileClass);
        sb.append(", embedded=");
        sb.append(this.embedded);
        sb.append(", bootstrap=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.bootstrap, ")");
    }

    public /* synthetic */ SubscriptionModel(int i, boolean z, boolean z2, ParcelUuid parcelUuid, String str, int i2, boolean z3, boolean z4, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i3 & 2) != 0 ? false : z, (i3 & 4) != 0 ? false : z2, (i3 & 8) != 0 ? null : parcelUuid, str, i2, (i3 & 64) != 0 ? false : z3, (i3 & 128) != 0 ? false : z4);
    }
}
