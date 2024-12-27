package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class FakeNetworkEventModel$Mobile {
    public final Integer activity;
    public final Integer carrierId;
    public final boolean carrierNetworkChange;
    public final SignalIcon$MobileIconGroup dataType;
    public final boolean inflateStrength;
    public final Integer level;
    public final String name;
    public final boolean ntn;
    public final boolean roaming;
    public final boolean slice;
    public final Integer subId;

    public FakeNetworkEventModel$Mobile(Integer num, SignalIcon$MobileIconGroup signalIcon$MobileIconGroup, Integer num2, Integer num3, boolean z, Integer num4, boolean z2, boolean z3, String str, boolean z4, boolean z5) {
        this.level = num;
        this.dataType = signalIcon$MobileIconGroup;
        this.subId = num2;
        this.carrierId = num3;
        this.inflateStrength = z;
        this.activity = num4;
        this.carrierNetworkChange = z2;
        this.roaming = z3;
        this.name = str;
        this.slice = z4;
        this.ntn = z5;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FakeNetworkEventModel$Mobile)) {
            return false;
        }
        FakeNetworkEventModel$Mobile fakeNetworkEventModel$Mobile = (FakeNetworkEventModel$Mobile) obj;
        return Intrinsics.areEqual(this.level, fakeNetworkEventModel$Mobile.level) && Intrinsics.areEqual(this.dataType, fakeNetworkEventModel$Mobile.dataType) && Intrinsics.areEqual(this.subId, fakeNetworkEventModel$Mobile.subId) && Intrinsics.areEqual(this.carrierId, fakeNetworkEventModel$Mobile.carrierId) && this.inflateStrength == fakeNetworkEventModel$Mobile.inflateStrength && Intrinsics.areEqual(this.activity, fakeNetworkEventModel$Mobile.activity) && this.carrierNetworkChange == fakeNetworkEventModel$Mobile.carrierNetworkChange && this.roaming == fakeNetworkEventModel$Mobile.roaming && Intrinsics.areEqual(this.name, fakeNetworkEventModel$Mobile.name) && this.slice == fakeNetworkEventModel$Mobile.slice && this.ntn == fakeNetworkEventModel$Mobile.ntn;
    }

    public final int hashCode() {
        Integer num = this.level;
        int hashCode = (num == null ? 0 : num.hashCode()) * 31;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = this.dataType;
        int hashCode2 = (hashCode + (signalIcon$MobileIconGroup == null ? 0 : signalIcon$MobileIconGroup.hashCode())) * 31;
        Integer num2 = this.subId;
        int hashCode3 = (hashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.carrierId;
        int m = TransitionData$$ExternalSyntheticOutline0.m((hashCode3 + (num3 == null ? 0 : num3.hashCode())) * 31, 31, this.inflateStrength);
        Integer num4 = this.activity;
        return Boolean.hashCode(this.ntn) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((m + (num4 != null ? num4.hashCode() : 0)) * 31, 31, this.carrierNetworkChange), 31, this.roaming), 31, this.name), 31, this.slice);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Mobile(level=");
        sb.append(this.level);
        sb.append(", dataType=");
        sb.append(this.dataType);
        sb.append(", subId=");
        sb.append(this.subId);
        sb.append(", carrierId=");
        sb.append(this.carrierId);
        sb.append(", inflateStrength=");
        sb.append(this.inflateStrength);
        sb.append(", activity=");
        sb.append(this.activity);
        sb.append(", carrierNetworkChange=");
        sb.append(this.carrierNetworkChange);
        sb.append(", roaming=");
        sb.append(this.roaming);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", slice=");
        sb.append(this.slice);
        sb.append(", ntn=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.ntn, ")");
    }

    public /* synthetic */ FakeNetworkEventModel$Mobile(Integer num, SignalIcon$MobileIconGroup signalIcon$MobileIconGroup, Integer num2, Integer num3, boolean z, Integer num4, boolean z2, boolean z3, String str, boolean z4, boolean z5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(num, signalIcon$MobileIconGroup, num2, num3, (i & 16) != 0 ? false : z, num4, z2, z3, str, (i & 512) != 0 ? false : z4, (i & 1024) != 0 ? false : z5);
    }
}
