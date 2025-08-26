package com.android.systemui.statusbar.notification.icon.ui.viewmodel;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class NotificationIconsViewData {
    public static final Companion Companion = null;
    public final int iconLimit;
    public final LimitType limitType;
    public final List visibleIcons;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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
    public NotificationIconsViewData(List list, int i, LimitType limitType, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        list = (i2 & 1) != 0 ? EmptyList.INSTANCE : list;
        this(list, (i2 & 2) != 0 ? list.size() : i, (i2 & 4) != 0 ? LimitType.MaximumAmount : limitType);
    }
}
