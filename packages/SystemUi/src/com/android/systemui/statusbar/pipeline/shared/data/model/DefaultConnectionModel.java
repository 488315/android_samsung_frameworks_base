package com.android.systemui.statusbar.pipeline.shared.data.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DefaultConnectionModel {
    public final BTTether btTether;
    public final CarrierMerged carrierMerged;
    public final Ethernet ethernet;
    public final boolean isValidated;
    public final Mobile mobile;
    public final Wifi wifi;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            boolean z = this.isDefault;
            if (z) {
                return 1;
            }
            return z ? 1 : 0;
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(new StringBuilder("BTTether(isDefault="), this.isDefault, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            boolean z = this.isDefault;
            if (z) {
                return 1;
            }
            return z ? 1 : 0;
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(new StringBuilder("CarrierMerged(isDefault="), this.isDefault, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            boolean z = this.isDefault;
            if (z) {
                return 1;
            }
            return z ? 1 : 0;
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(new StringBuilder("Ethernet(isDefault="), this.isDefault, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            boolean z = this.isDefault;
            if (z) {
                return 1;
            }
            return z ? 1 : 0;
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(new StringBuilder("Mobile(isDefault="), this.isDefault, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            boolean z = this.isDefault;
            if (z) {
                return 1;
            }
            return z ? 1 : 0;
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(new StringBuilder("Wifi(isDefault="), this.isDefault, ")");
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v2, types: [int] */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v4, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v7, types: [boolean] */
    public final int hashCode() {
        boolean z = this.wifi.isDefault;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        ?? r2 = this.mobile.isDefault;
        int i2 = r2;
        if (r2 != 0) {
            i2 = 1;
        }
        int i3 = (i + i2) * 31;
        ?? r22 = this.carrierMerged.isDefault;
        int i4 = r22;
        if (r22 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        ?? r23 = this.ethernet.isDefault;
        int i6 = r23;
        if (r23 != 0) {
            i6 = 1;
        }
        int i7 = (i5 + i6) * 31;
        ?? r24 = this.btTether.isDefault;
        int i8 = r24;
        if (r24 != 0) {
            i8 = 1;
        }
        int i9 = (i7 + i8) * 31;
        boolean z2 = this.isValidated;
        return i9 + (z2 ? 1 : z2 ? 1 : 0);
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
