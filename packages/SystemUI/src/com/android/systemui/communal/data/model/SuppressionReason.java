package com.android.systemui.communal.data.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface SuppressionReason {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReasonDevicePolicy implements SuppressionReason {
        public static final ReasonDevicePolicy INSTANCE = new ReasonDevicePolicy();
        public static final int suppressedFeatures = 7;

        private ReasonDevicePolicy() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ReasonDevicePolicy);
        }

        @Override // com.android.systemui.communal.data.model.SuppressionReason
        public final int getSuppressedFeatures() {
            return suppressedFeatures;
        }

        public final int hashCode() {
            return -265435027;
        }

        public final String toString() {
            return "ReasonDevicePolicy";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReasonFlagDisabled implements SuppressionReason {
        public static final ReasonFlagDisabled INSTANCE = new ReasonFlagDisabled();
        public static final int suppressedFeatures = 7;

        private ReasonFlagDisabled() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ReasonFlagDisabled);
        }

        @Override // com.android.systemui.communal.data.model.SuppressionReason
        public final int getSuppressedFeatures() {
            return suppressedFeatures;
        }

        public final int hashCode() {
            return 1502028621;
        }

        public final String toString() {
            return "ReasonFlagDisabled";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReasonSecondaryUser implements SuppressionReason {
        public static final ReasonSecondaryUser INSTANCE = new ReasonSecondaryUser();
        public static final int suppressedFeatures = 7;

        private ReasonSecondaryUser() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ReasonSecondaryUser);
        }

        @Override // com.android.systemui.communal.data.model.SuppressionReason
        public final int getSuppressedFeatures() {
            return suppressedFeatures;
        }

        public final int hashCode() {
            return 1008627386;
        }

        public final String toString() {
            return "ReasonSecondaryUser";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReasonSettingDisabled implements SuppressionReason {
        public static final ReasonSettingDisabled INSTANCE = new ReasonSettingDisabled();
        public static final int suppressedFeatures = 7;

        private ReasonSettingDisabled() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ReasonSettingDisabled);
        }

        @Override // com.android.systemui.communal.data.model.SuppressionReason
        public final int getSuppressedFeatures() {
            return suppressedFeatures;
        }

        public final int hashCode() {
            return 12806535;
        }

        public final String toString() {
            return "ReasonSettingDisabled";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReasonUnknown implements SuppressionReason {
        public final int suppressedFeatures;

        public ReasonUnknown() {
            this(0, 1, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ReasonUnknown) && this.suppressedFeatures == ((ReasonUnknown) obj).suppressedFeatures;
        }

        @Override // com.android.systemui.communal.data.model.SuppressionReason
        public final int getSuppressedFeatures() {
            return this.suppressedFeatures;
        }

        public final int hashCode() {
            return Integer.hashCode(this.suppressedFeatures);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.suppressedFeatures, ")", new StringBuilder("ReasonUnknown(suppressedFeatures="));
        }

        public ReasonUnknown(int i) {
            this.suppressedFeatures = i;
        }

        public /* synthetic */ ReasonUnknown(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this((i2 & 1) != 0 ? 7 : i);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReasonUserLocked implements SuppressionReason {
        public static final ReasonUserLocked INSTANCE = new ReasonUserLocked();
        public static final int suppressedFeatures = 7;

        private ReasonUserLocked() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ReasonUserLocked);
        }

        @Override // com.android.systemui.communal.data.model.SuppressionReason
        public final int getSuppressedFeatures() {
            return suppressedFeatures;
        }

        public final int hashCode() {
            return 1302529338;
        }

        public final String toString() {
            return "ReasonUserLocked";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReasonWhenToAutoShow implements SuppressionReason {
        public final int suppressedFeatures;

        public ReasonWhenToAutoShow(int i) {
            this.suppressedFeatures = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ReasonWhenToAutoShow) && this.suppressedFeatures == ((ReasonWhenToAutoShow) obj).suppressedFeatures;
        }

        @Override // com.android.systemui.communal.data.model.SuppressionReason
        public final int getSuppressedFeatures() {
            return this.suppressedFeatures;
        }

        public final int hashCode() {
            return Integer.hashCode(this.suppressedFeatures);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.suppressedFeatures, ")", new StringBuilder("ReasonWhenToAutoShow(suppressedFeatures="));
        }
    }

    int getSuppressedFeatures();
}
