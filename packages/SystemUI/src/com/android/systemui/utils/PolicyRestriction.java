package com.android.systemui.utils;

import com.android.settingslib.RestrictedLockUtils;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface PolicyRestriction {

    public final class NoRestriction implements PolicyRestriction {
        public static final NoRestriction INSTANCE = new NoRestriction();

        private NoRestriction() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NoRestriction);
        }

        public final int hashCode() {
            return 204915163;
        }

        public final String toString() {
            return "NoRestriction";
        }
    }

    public final class Restricted implements PolicyRestriction {
        public final RestrictedLockUtils.EnforcedAdmin admin;

        public Restricted(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
            this.admin = enforcedAdmin;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Restricted) && Intrinsics.areEqual(this.admin, ((Restricted) obj).admin);
        }

        public final int hashCode() {
            return this.admin.hashCode();
        }

        public final String toString() {
            return "Restricted(admin=" + this.admin + ")";
        }
    }
}
