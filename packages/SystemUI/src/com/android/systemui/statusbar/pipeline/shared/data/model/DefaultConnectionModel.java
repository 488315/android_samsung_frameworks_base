package com.android.systemui.statusbar.pipeline.shared.data.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class DefaultConnectionModel {
    public final BTTether btTether;
    public final CarrierMerged carrierMerged;
    public final Ethernet ethernet;
    public final boolean isValidated;
    public final Mobile mobile;
    public final Wifi wifi;

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
            return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("BTTether(isDefault="), this.isDefault, ")");
        }
    }

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
            return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("CarrierMerged(isDefault="), this.isDefault, ")");
        }
    }

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
            return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("Ethernet(isDefault="), this.isDefault, ")");
        }
    }

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
            return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("Mobile(isDefault="), this.isDefault, ")");
        }
    }

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
            return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("Wifi(isDefault="), this.isDefault, ")");
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

    /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getPhiList()" because "resultVar" is null
        	at jadx.core.dex.visitors.InitCodeVariables.collectConnectedVars(InitCodeVariables.java:119)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:82)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:74)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:29)
        */
    public /* synthetic */ DefaultConnectionModel(com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel.Wifi r2, com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel.Mobile r3, com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel.CarrierMerged r4, com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel.Ethernet r5, com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel.BTTether r6, boolean r7, int r8, kotlin.jvm.internal.DefaultConstructorMarker r9) {
        /*
            r1 = this;
            r9 = r8 & 1
            r0 = 0
            if (r9 == 0) goto La
            com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Wifi r2 = new com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Wifi
            r2.<init>(r0)
        La:
            r9 = r8 & 2
            if (r9 == 0) goto L13
            com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Mobile r3 = new com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Mobile
            r3.<init>(r0)
        L13:
            r9 = r8 & 4
            if (r9 == 0) goto L1c
            com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$CarrierMerged r4 = new com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$CarrierMerged
            r4.<init>(r0)
        L1c:
            r9 = r8 & 8
            if (r9 == 0) goto L25
            com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Ethernet r5 = new com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Ethernet
            r5.<init>(r0)
        L25:
            r9 = r8 & 16
            if (r9 == 0) goto L2e
            com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$BTTether r6 = new com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$BTTether
            r6.<init>(r0)
        L2e:
            r8 = r8 & 32
            if (r8 == 0) goto L3a
            r9 = r0
            r7 = r5
            r8 = r6
            r5 = r3
            r6 = r4
            r3 = r1
            r4 = r2
            goto L41
        L3a:
            r9 = r7
            r8 = r6
            r6 = r4
            r7 = r5
            r4 = r2
            r5 = r3
            r3 = r1
        L41:
            r3.<init>(r4, r5, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel.<init>(com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Wifi, com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Mobile, com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$CarrierMerged, com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$Ethernet, com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel$BTTether, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
