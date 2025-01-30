package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FakeNetworkEventModel$Mobile {
    public final Integer activity;
    public final Integer carrierId;
    public final boolean carrierNetworkChange;
    public final SignalIcon$MobileIconGroup dataType;
    public final Boolean inflateStrength;
    public final Integer level;
    public final String name;
    public final boolean ntn;
    public final boolean roaming;
    public final Integer subId;

    public FakeNetworkEventModel$Mobile(Integer num, SignalIcon$MobileIconGroup signalIcon$MobileIconGroup, Integer num2, Integer num3, Boolean bool, Integer num4, boolean z, boolean z2, String str, boolean z3) {
        this.level = num;
        this.dataType = signalIcon$MobileIconGroup;
        this.subId = num2;
        this.carrierId = num3;
        this.inflateStrength = bool;
        this.activity = num4;
        this.carrierNetworkChange = z;
        this.roaming = z2;
        this.name = str;
        this.ntn = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FakeNetworkEventModel$Mobile)) {
            return false;
        }
        FakeNetworkEventModel$Mobile fakeNetworkEventModel$Mobile = (FakeNetworkEventModel$Mobile) obj;
        return Intrinsics.areEqual(this.level, fakeNetworkEventModel$Mobile.level) && Intrinsics.areEqual(this.dataType, fakeNetworkEventModel$Mobile.dataType) && Intrinsics.areEqual(this.subId, fakeNetworkEventModel$Mobile.subId) && Intrinsics.areEqual(this.carrierId, fakeNetworkEventModel$Mobile.carrierId) && Intrinsics.areEqual(this.inflateStrength, fakeNetworkEventModel$Mobile.inflateStrength) && Intrinsics.areEqual(this.activity, fakeNetworkEventModel$Mobile.activity) && this.carrierNetworkChange == fakeNetworkEventModel$Mobile.carrierNetworkChange && this.roaming == fakeNetworkEventModel$Mobile.roaming && Intrinsics.areEqual(this.name, fakeNetworkEventModel$Mobile.name) && this.ntn == fakeNetworkEventModel$Mobile.ntn;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        Integer num = this.level;
        int hashCode = (num == null ? 0 : num.hashCode()) * 31;
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = this.dataType;
        int hashCode2 = (hashCode + (signalIcon$MobileIconGroup == null ? 0 : signalIcon$MobileIconGroup.hashCode())) * 31;
        Integer num2 = this.subId;
        int hashCode3 = (hashCode2 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.carrierId;
        int hashCode4 = (hashCode3 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Boolean bool = this.inflateStrength;
        int hashCode5 = (hashCode4 + (bool == null ? 0 : bool.hashCode())) * 31;
        Integer num4 = this.activity;
        int hashCode6 = (hashCode5 + (num4 != null ? num4.hashCode() : 0)) * 31;
        boolean z = this.carrierNetworkChange;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode6 + i) * 31;
        boolean z2 = this.roaming;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int m41m = AppInfo$$ExternalSyntheticOutline0.m41m(this.name, (i2 + i3) * 31, 31);
        boolean z3 = this.ntn;
        return m41m + (z3 ? 1 : z3 ? 1 : 0);
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
        sb.append(", ntn=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.ntn, ")");
    }

    public /* synthetic */ FakeNetworkEventModel$Mobile(Integer num, SignalIcon$MobileIconGroup signalIcon$MobileIconGroup, Integer num2, Integer num3, Boolean bool, Integer num4, boolean z, boolean z2, String str, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(num, signalIcon$MobileIconGroup, num2, num3, bool, num4, z, z2, str, (i & 512) != 0 ? false : z3);
    }
}
