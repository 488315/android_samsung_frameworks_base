package com.android.systemui.statusbar.pipeline.shared.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class DefaultConnectionModel {
    public final BTTether btTether;
    public final CarrierMerged carrierMerged;
    public final Ethernet ethernet;
    public final boolean isValidated;
    public final Mobile mobile;
    public final Wifi wifi;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class BTTether {
        public final boolean isDefault;

        public BTTether(boolean z) {
            this.isDefault = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof BTTether) && this.isDefault == ((BTTether) obj).isDefault;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isDefault);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("BTTether(isDefault="), this.isDefault, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CarrierMerged {
        public final boolean isDefault;

        public CarrierMerged(boolean z) {
            this.isDefault = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof CarrierMerged) && this.isDefault == ((CarrierMerged) obj).isDefault;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isDefault);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("CarrierMerged(isDefault="), this.isDefault, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Ethernet {
        public final boolean isDefault;

        public Ethernet(boolean z) {
            this.isDefault = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Ethernet) && this.isDefault == ((Ethernet) obj).isDefault;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isDefault);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("Ethernet(isDefault="), this.isDefault, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Mobile {
        public final boolean isDefault;

        public Mobile(boolean z) {
            this.isDefault = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Mobile) && this.isDefault == ((Mobile) obj).isDefault;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isDefault);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("Mobile(isDefault="), this.isDefault, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Wifi {
        public final boolean isDefault;

        public Wifi(boolean z) {
            this.isDefault = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Wifi) && this.isDefault == ((Wifi) obj).isDefault;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isDefault);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("Wifi(isDefault="), this.isDefault, ")");
        }
    }

    public DefaultConnectionModel() {
        this(null, null, null, null, null, false, 63, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultConnectionModel)) {
            return false;
        }
        DefaultConnectionModel defaultConnectionModel = (DefaultConnectionModel) obj;
        return Intrinsics.areEqual(this.wifi, defaultConnectionModel.wifi) && Intrinsics.areEqual(this.mobile, defaultConnectionModel.mobile) && Intrinsics.areEqual(this.carrierMerged, defaultConnectionModel.carrierMerged) && Intrinsics.areEqual(this.ethernet, defaultConnectionModel.ethernet) && Intrinsics.areEqual(this.btTether, defaultConnectionModel.btTether) && this.isValidated == defaultConnectionModel.isValidated;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isValidated) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Boolean.hashCode(this.wifi.isDefault) * 31, 31, this.mobile.isDefault), 31, this.carrierMerged.isDefault), 31, this.ethernet.isDefault), 31, this.btTether.isDefault);
    }

    public final String toString() {
        return "DefaultConnectionModel(wifi=" + this.wifi + ", mobile=" + this.mobile + ", carrierMerged=" + this.carrierMerged + ", ethernet=" + this.ethernet + ", btTether=" + this.btTether + ", isValidated=" + this.isValidated + ")";
    }

    public DefaultConnectionModel(Wifi wifi, Mobile mobile, CarrierMerged carrierMerged, Ethernet ethernet, BTTether bTTether, boolean z) {
        this.wifi = wifi;
        this.mobile = mobile;
        this.carrierMerged = carrierMerged;
        this.ethernet = ethernet;
        this.btTether = bTTether;
        this.isValidated = z;
    }

    public /* synthetic */ DefaultConnectionModel(Wifi wifi, Mobile mobile, CarrierMerged carrierMerged, Ethernet ethernet, BTTether bTTether, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Wifi(false) : wifi, (i & 2) != 0 ? new Mobile(false) : mobile, (i & 4) != 0 ? new CarrierMerged(false) : carrierMerged, (i & 8) != 0 ? new Ethernet(false) : ethernet, (i & 16) != 0 ? new BTTether(false) : bTTether, (i & 32) == 0 ? z : false);
    }
}
