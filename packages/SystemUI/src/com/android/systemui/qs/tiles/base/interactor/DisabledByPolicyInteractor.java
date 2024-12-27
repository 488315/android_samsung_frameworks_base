package com.android.systemui.qs.tiles.base.interactor;

import com.android.settingslib.RestrictedLockUtils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DisabledByPolicyInteractor {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface PolicyResult {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TileDisabled implements PolicyResult {
            public final RestrictedLockUtils.EnforcedAdmin admin;

            public TileDisabled(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
                this.admin = enforcedAdmin;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof TileDisabled) && Intrinsics.areEqual(this.admin, ((TileDisabled) obj).admin);
            }

            public final int hashCode() {
                return this.admin.hashCode();
            }

            public final String toString() {
                return "TileDisabled(admin=" + this.admin + ")";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TileEnabled implements PolicyResult {
            public static final TileEnabled INSTANCE = new TileEnabled();

            private TileEnabled() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TileEnabled);
            }

            public final int hashCode() {
                return -1086222378;
            }

            public final String toString() {
                return "TileEnabled";
            }
        }
    }
}
