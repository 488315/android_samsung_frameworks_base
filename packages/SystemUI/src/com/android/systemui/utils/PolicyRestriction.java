package com.android.systemui.utils;

import com.android.settingslib.RestrictedLockUtils;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface PolicyRestriction {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
