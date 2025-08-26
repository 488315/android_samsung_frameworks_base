package com.android.systemui.qs.tiles.base.shared.model;

import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface QSTilePolicy {

    public final class NoRestrictions implements QSTilePolicy {
        public static final NoRestrictions INSTANCE = new NoRestrictions();

        private NoRestrictions() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NoRestrictions);
        }

        public final int hashCode() {
            return 1144776877;
        }

        public final String toString() {
            return "NoRestrictions";
        }
    }

    public final class Restricted implements QSTilePolicy {
        public final List userRestrictions;

        public Restricted(List<String> list) {
            this.userRestrictions = list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Restricted) && Intrinsics.areEqual(this.userRestrictions, ((Restricted) obj).userRestrictions);
        }

        public final int hashCode() {
            return this.userRestrictions.hashCode();
        }

        public final String toString() {
            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("Restricted(userRestrictions=", this.userRestrictions, ")");
        }
    }
}
