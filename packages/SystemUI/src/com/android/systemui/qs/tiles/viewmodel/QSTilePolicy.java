package com.android.systemui.qs.tiles.viewmodel;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface QSTilePolicy {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NoRestrictions implements QSTilePolicy {
        public static final NoRestrictions INSTANCE = new NoRestrictions();

        private NoRestrictions() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NoRestrictions);
        }

        public final int hashCode() {
            return 1544781172;
        }

        public final String toString() {
            return "NoRestrictions";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            return "Restricted(userRestrictions=" + this.userRestrictions + ")";
        }
    }
}
