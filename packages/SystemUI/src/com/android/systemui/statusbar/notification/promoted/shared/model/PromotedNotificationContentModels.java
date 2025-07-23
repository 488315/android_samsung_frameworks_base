package com.android.systemui.statusbar.notification.promoted.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PromotedNotificationContentModels {
    public final PromotedNotificationContentModel privateVersion;
    public final PromotedNotificationContentModel publicVersion;

    public PromotedNotificationContentModels(PromotedNotificationContentModel promotedNotificationContentModel, PromotedNotificationContentModel promotedNotificationContentModel2) {
        this.publicVersion = promotedNotificationContentModel;
        this.privateVersion = promotedNotificationContentModel2;
        if (!Intrinsics.areEqual(promotedNotificationContentModel.identity.key, promotedNotificationContentModel2.identity.key)) {
            throw new IllegalStateException("public and private models must have the same key");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PromotedNotificationContentModels)) {
            return false;
        }
        PromotedNotificationContentModels promotedNotificationContentModels = (PromotedNotificationContentModels) obj;
        return Intrinsics.areEqual(this.publicVersion, promotedNotificationContentModels.publicVersion) && Intrinsics.areEqual(this.privateVersion, promotedNotificationContentModels.privateVersion);
    }

    public final int hashCode() {
        return this.privateVersion.hashCode() + (this.publicVersion.hashCode() * 31);
    }

    public final String toString() {
        return "PromotedNotificationContentModels(publicVersion=" + this.publicVersion + ", privateVersion=" + this.privateVersion + ")";
    }
}
