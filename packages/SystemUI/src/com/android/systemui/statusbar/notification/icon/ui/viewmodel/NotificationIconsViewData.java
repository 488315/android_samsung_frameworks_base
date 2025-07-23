package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationIconsViewData {
    public static final Companion Companion = null;
    public final int iconLimit;
    public final LimitType limitType;
    public final List visibleIcons;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LimitType {
        public static final /* synthetic */ LimitType[] $VALUES;
        public static final LimitType MaximumAmount;
        public static final LimitType MaximumIndex;

        static {
            LimitType limitType = new LimitType("MaximumAmount", 0);
            MaximumAmount = limitType;
            LimitType limitType2 = new LimitType("MaximumIndex", 1);
            MaximumIndex = limitType2;
            LimitType[] limitTypeArr = {limitType, limitType2};
            $VALUES = limitTypeArr;
            EnumEntriesKt.enumEntries(limitTypeArr);
        }

        private LimitType(String str, int i) {
        }

        public static LimitType valueOf(String str) {
            return (LimitType) Enum.valueOf(LimitType.class, str);
        }

        public static LimitType[] values() {
            return (LimitType[]) $VALUES.clone();
        }
    }

    static {
        new Companion(null);
    }

    public NotificationIconsViewData() {
        this(null, 0, null, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationIconsViewData)) {
            return false;
        }
        NotificationIconsViewData notificationIconsViewData = (NotificationIconsViewData) obj;
        return Intrinsics.areEqual(this.visibleIcons, notificationIconsViewData.visibleIcons) && this.iconLimit == notificationIconsViewData.iconLimit && this.limitType == notificationIconsViewData.limitType;
    }

    public final int hashCode() {
        return this.limitType.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.iconLimit, this.visibleIcons.hashCode() * 31, 31);
    }

    public final String toString() {
        return "NotificationIconsViewData(visibleIcons=" + this.visibleIcons + ", iconLimit=" + this.iconLimit + ", limitType=" + this.limitType + ")";
    }

    public NotificationIconsViewData(List<NotificationIconInfo> list, int i, LimitType limitType) {
        this.visibleIcons = list;
        this.iconLimit = i;
        this.limitType = limitType;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NotificationIconsViewData(java.util.List r1, int r2, com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData.LimitType r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
        /*
            r0 = this;
            r5 = r4 & 1
            if (r5 == 0) goto L6
            kotlin.collections.EmptyList r1 = kotlin.collections.EmptyList.INSTANCE
        L6:
            r5 = r4 & 2
            if (r5 == 0) goto Le
            int r2 = r1.size()
        Le:
            r4 = r4 & 4
            if (r4 == 0) goto L14
            com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData$LimitType r3 = com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData.LimitType.MaximumAmount
        L14:
            r0.<init>(r1, r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData.<init>(java.util.List, int, com.android.systemui.statusbar.notification.icon.ui.viewmodel.NotificationIconsViewData$LimitType, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
