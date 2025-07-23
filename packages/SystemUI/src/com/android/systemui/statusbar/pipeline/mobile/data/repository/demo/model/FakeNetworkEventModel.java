package com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface FakeNetworkEventModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class MobileDisabled implements FakeNetworkEventModel {
        public final Integer subId;

        public MobileDisabled(Integer num) {
            this.subId = num;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof MobileDisabled) && Intrinsics.areEqual(this.subId, ((MobileDisabled) obj).subId);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel
        public final Integer getSubId() {
            return this.subId;
        }

        public final int hashCode() {
            Integer num = this.subId;
            if (num == null) {
                return 0;
            }
            return num.hashCode();
        }

        public final String toString() {
            return "MobileDisabled(subId=" + this.subId + ")";
        }
    }

    Integer getSubId();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Mobile implements FakeNetworkEventModel {
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

        public Mobile(Integer num, SignalIcon$MobileIconGroup signalIcon$MobileIconGroup, Integer num2, Integer num3, boolean z, Integer num4, boolean z2, boolean z3, String str, boolean z4, boolean z5) {
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
            if (!(obj instanceof Mobile)) {
                return false;
            }
            Mobile mobile = (Mobile) obj;
            return Intrinsics.areEqual(this.level, mobile.level) && Intrinsics.areEqual(this.dataType, mobile.dataType) && Intrinsics.areEqual(this.subId, mobile.subId) && Intrinsics.areEqual(this.carrierId, mobile.carrierId) && this.inflateStrength == mobile.inflateStrength && Intrinsics.areEqual(this.activity, mobile.activity) && this.carrierNetworkChange == mobile.carrierNetworkChange && this.roaming == mobile.roaming && Intrinsics.areEqual(this.name, mobile.name) && this.slice == mobile.slice && this.ntn == mobile.ntn;
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.model.FakeNetworkEventModel
        public final Integer getSubId() {
            return this.subId;
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
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.ntn, ")");
        }

        public /* synthetic */ Mobile(Integer num, SignalIcon$MobileIconGroup signalIcon$MobileIconGroup, Integer num2, Integer num3, boolean z, Integer num4, boolean z2, boolean z3, String str, boolean z4, boolean z5, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(num, signalIcon$MobileIconGroup, num2, num3, (i & 16) != 0 ? false : z, num4, z2, z3, str, (i & 512) != 0 ? false : z4, (i & 1024) != 0 ? false : z5);
        }
    }
}
