package com.android.systemui.communal.data.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalMediaModel implements Diffable {
    public static final Companion Companion = new Companion(null);
    public static final CommunalMediaModel INACTIVE = new CommunalMediaModel(false, 0, 2, null);
    public final long createdTimestampMillis;
    public final boolean hasAnyMediaOrRecommendation;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public CommunalMediaModel(boolean z, long j) {
        this.hasAnyMediaOrRecommendation = z;
        this.createdTimestampMillis = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommunalMediaModel)) {
            return false;
        }
        CommunalMediaModel communalMediaModel = (CommunalMediaModel) obj;
        return this.hasAnyMediaOrRecommendation == communalMediaModel.hasAnyMediaOrRecommendation && this.createdTimestampMillis == communalMediaModel.createdTimestampMillis;
    }

    public final int hashCode() {
        return Long.hashCode(this.createdTimestampMillis) + (Boolean.hashCode(this.hasAnyMediaOrRecommendation) * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        CommunalMediaModel communalMediaModel = (CommunalMediaModel) diffable;
        boolean z = communalMediaModel.hasAnyMediaOrRecommendation;
        boolean z2 = this.hasAnyMediaOrRecommendation;
        if (z2 != z) {
            tableRowLoggerImpl.logChange("isMediaActive", z2);
        }
        long j = communalMediaModel.createdTimestampMillis;
        long j2 = this.createdTimestampMillis;
        if (j2 != j) {
            tableRowLoggerImpl.logChange("mediaCreationTimestamp", String.valueOf(j2));
        }
    }

    public final String toString() {
        return "CommunalMediaModel(hasAnyMediaOrRecommendation=" + this.hasAnyMediaOrRecommendation + ", createdTimestampMillis=" + this.createdTimestampMillis + ")";
    }

    public /* synthetic */ CommunalMediaModel(boolean z, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? 0L : j);
    }
}
