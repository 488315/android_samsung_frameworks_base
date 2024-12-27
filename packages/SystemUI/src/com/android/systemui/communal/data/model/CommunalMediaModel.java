package com.android.systemui.communal.data.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CommunalMediaModel implements Diffable {
    public static final Companion Companion = new Companion(null);
    public static final CommunalMediaModel INACTIVE = new CommunalMediaModel(false, 0, 2, null);
    public final long createdTimestampMillis;
    public final boolean hasActiveMediaOrRecommendation;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CommunalMediaModel(boolean z, long j) {
        this.hasActiveMediaOrRecommendation = z;
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
        return this.hasActiveMediaOrRecommendation == communalMediaModel.hasActiveMediaOrRecommendation && this.createdTimestampMillis == communalMediaModel.createdTimestampMillis;
    }

    public final int hashCode() {
        return Long.hashCode(this.createdTimestampMillis) + (Boolean.hashCode(this.hasActiveMediaOrRecommendation) * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        CommunalMediaModel communalMediaModel = (CommunalMediaModel) obj;
        boolean z = communalMediaModel.hasActiveMediaOrRecommendation;
        boolean z2 = this.hasActiveMediaOrRecommendation;
        if (z2 != z) {
            tableRowLoggerImpl.logChange("isMediaActive", z2);
        }
        long j = this.createdTimestampMillis;
        if (j != communalMediaModel.createdTimestampMillis) {
            tableRowLoggerImpl.logChange("mediaCreationTimestamp", String.valueOf(j));
        }
    }

    public final String toString() {
        return "CommunalMediaModel(hasActiveMediaOrRecommendation=" + this.hasActiveMediaOrRecommendation + ", createdTimestampMillis=" + this.createdTimestampMillis + ")";
    }

    public /* synthetic */ CommunalMediaModel(boolean z, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? 0L : j);
    }
}
