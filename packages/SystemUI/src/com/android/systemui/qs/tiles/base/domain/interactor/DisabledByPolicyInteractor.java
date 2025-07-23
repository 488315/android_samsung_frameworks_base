package com.android.systemui.qs.tiles.base.domain.interactor;

import com.android.settingslib.RestrictedLockUtils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DisabledByPolicyInteractor {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PolicyResult {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class TileEnabled implements PolicyResult {
            public static final TileEnabled INSTANCE = new TileEnabled();

            private TileEnabled() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TileEnabled);
            }

            public final int hashCode() {
                return -1667683822;
            }

            public final String toString() {
                return "TileEnabled";
            }
        }
    }
}
