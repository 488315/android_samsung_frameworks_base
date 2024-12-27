package com.android.systemui.qs.tiles.base.interactor;

import com.android.settingslib.RestrictedLockUtils;
import kotlin.jvm.internal.Intrinsics;

public interface DisabledByPolicyInteractor {

    public interface PolicyResult {

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
