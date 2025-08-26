package com.android.systemui.statusbar.notification.promoted.shared.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.internal.widget.NotificationProgressModel;
import com.android.systemui.statusbar.notification.row.shared.ImageModel;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class PromotedNotificationContentModel {
    public static final Companion Companion = new Companion(null);
    public final CharSequence appName;
    public final Colors colors;
    public final int iconLevel;
    public final Identity identity;
    public final long lastAudiblyAlertedMs;
    public final NotificationProgressModel newProgress;
    public final OldProgress oldProgress;
    public final Integer profileBadgeResId;
    public final String shortCriticalText;
    public final ImageModel skeletonLargeIcon;
    public final ImageModel smallIcon;
    public final Style style;
    public final CharSequence subText;
    public final CharSequence text;
    public final When time;
    public final CharSequence title;
    public final ImageModel verificationIcon;
    public final CharSequence verificationText;
    public final boolean wasPromotedAutomatically;

    public final class Colors {
        public final int backgroundColor;
        public final int primaryTextColor;

        public Colors(int i, int i2) {
            this.backgroundColor = i;
            this.primaryTextColor = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Colors)) {
                return false;
            }
            Colors colors = (Colors) obj;
            return this.backgroundColor == colors.backgroundColor && this.primaryTextColor == colors.primaryTextColor;
        }

        public final int hashCode() {
            return Integer.hashCode(this.primaryTextColor) + (Integer.hashCode(this.backgroundColor) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Colors(backgroundColor=");
            sb.append(this.backgroundColor);
            sb.append(", primaryTextColor=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.primaryTextColor, ")", sb);
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Identity {
        public final String key;
        public final Style style;

        public Identity(String str, Style style) {
            this.key = str;
            this.style = style;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Identity)) {
                return false;
            }
            Identity identity = (Identity) obj;
            return Intrinsics.areEqual(this.key, identity.key) && this.style == identity.style;
        }

        public final int hashCode() {
            return this.style.hashCode() + (this.key.hashCode() * 31);
        }

        public final String toString() {
            return "Identity(key=" + this.key + ", style=" + this.style + ")";
        }
    }

    public final class OldProgress {
        public final boolean isIndeterminate;
        public final int max;
        public final int progress;

        public OldProgress(int i, int i2, boolean z) {
            this.progress = i;
            this.max = i2;
            this.isIndeterminate = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OldProgress)) {
                return false;
            }
            OldProgress oldProgress = (OldProgress) obj;
            return this.progress == oldProgress.progress && this.max == oldProgress.max && this.isIndeterminate == oldProgress.isIndeterminate;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isIndeterminate) + ReorderTile$$ExternalSyntheticOutline0.m(this.max, Integer.hashCode(this.progress) * 31, 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("OldProgress(progress=");
            sb.append(this.progress);
            sb.append(", max=");
            sb.append(this.max);
            sb.append(", isIndeterminate=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isIndeterminate, ")");
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Style {
        public static final /* synthetic */ Style[] $VALUES;
        public static final Style Base = null;
        public static final Style BigPicture = null;
        public static final Style BigText = null;
        public static final Style Call = null;
        public static final Style CollapsedBase = null;
        public static final Style CollapsedCall = null;
        public static final Style Ineligible;
        public static final Style Progress = null;

        static {
            Style style = new Style("Base", 0);
            Style style2 = new Style("CollapsedBase", 1);
            Style style3 = new Style("BigPicture", 2);
            Style style4 = new Style("BigText", 3);
            Style style5 = new Style("Call", 4);
            Style style6 = new Style("CollapsedCall", 5);
            Style style7 = new Style("Progress", 6);
            Style style8 = new Style("Ineligible", 7);
            Ineligible = style8;
            Style[] styleArr = {style, style2, style3, style4, style5, style6, style7, style8};
            $VALUES = styleArr;
            EnumEntriesKt.enumEntries(styleArr);
        }

        private Style(String str, int i) {
        }

        public static Style valueOf(String str) {
            return (Style) Enum.valueOf(Style.class, str);
        }

        public static Style[] values() {
            return (Style[]) $VALUES.clone();
        }
    }

    public abstract class When {
        public /* synthetic */ When(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private When() {
        }
    }

    public PromotedNotificationContentModel(Identity identity, boolean z, ImageModel imageModel, int i, CharSequence charSequence, CharSequence charSequence2, String str, When when, long j, Integer num, CharSequence charSequence3, CharSequence charSequence4, ImageModel imageModel2, OldProgress oldProgress, Colors colors, Style style, ImageModel imageModel3, CharSequence charSequence5, NotificationProgressModel notificationProgressModel) {
        this.identity = identity;
        this.wasPromotedAutomatically = z;
        this.smallIcon = imageModel;
        this.iconLevel = i;
        this.appName = charSequence;
        this.subText = charSequence2;
        this.shortCriticalText = str;
        this.time = when;
        this.lastAudiblyAlertedMs = j;
        this.profileBadgeResId = num;
        this.title = charSequence3;
        this.text = charSequence4;
        this.skeletonLargeIcon = imageModel2;
        this.oldProgress = oldProgress;
        this.colors = colors;
        this.style = style;
        this.verificationIcon = imageModel3;
        this.verificationText = charSequence5;
        this.newProgress = notificationProgressModel;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PromotedNotificationContentModel)) {
            return false;
        }
        PromotedNotificationContentModel promotedNotificationContentModel = (PromotedNotificationContentModel) obj;
        return Intrinsics.areEqual(this.identity, promotedNotificationContentModel.identity) && this.wasPromotedAutomatically == promotedNotificationContentModel.wasPromotedAutomatically && Intrinsics.areEqual(this.smallIcon, promotedNotificationContentModel.smallIcon) && this.iconLevel == promotedNotificationContentModel.iconLevel && Intrinsics.areEqual(this.appName, promotedNotificationContentModel.appName) && Intrinsics.areEqual(this.subText, promotedNotificationContentModel.subText) && Intrinsics.areEqual(this.shortCriticalText, promotedNotificationContentModel.shortCriticalText) && Intrinsics.areEqual(this.time, promotedNotificationContentModel.time) && this.lastAudiblyAlertedMs == promotedNotificationContentModel.lastAudiblyAlertedMs && Intrinsics.areEqual(this.profileBadgeResId, promotedNotificationContentModel.profileBadgeResId) && Intrinsics.areEqual(this.title, promotedNotificationContentModel.title) && Intrinsics.areEqual(this.text, promotedNotificationContentModel.text) && Intrinsics.areEqual(this.skeletonLargeIcon, promotedNotificationContentModel.skeletonLargeIcon) && Intrinsics.areEqual(this.oldProgress, promotedNotificationContentModel.oldProgress) && Intrinsics.areEqual(this.colors, promotedNotificationContentModel.colors) && this.style == promotedNotificationContentModel.style && Intrinsics.areEqual(this.verificationIcon, promotedNotificationContentModel.verificationIcon) && Intrinsics.areEqual(this.verificationText, promotedNotificationContentModel.verificationText) && Intrinsics.areEqual(this.newProgress, promotedNotificationContentModel.newProgress);
    }

    public final int hashCode() {
        int iM = TransitionData$$ExternalSyntheticOutline0.m(this.identity.hashCode() * 31, 31, this.wasPromotedAutomatically);
        ImageModel imageModel = this.smallIcon;
        int iM2 = ReorderTile$$ExternalSyntheticOutline0.m(this.iconLevel, (iM + (imageModel == null ? 0 : imageModel.hashCode())) * 31, 31);
        CharSequence charSequence = this.appName;
        int iHashCode = (iM2 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        CharSequence charSequence2 = this.subText;
        int iHashCode2 = (iHashCode + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        String str = this.shortCriticalText;
        int iHashCode3 = (iHashCode2 + (str == null ? 0 : str.hashCode())) * 31;
        When when = this.time;
        int iM3 = MoveResult$$ExternalSyntheticOutline0.m((iHashCode3 + (when == null ? 0 : when.hashCode())) * 31, 31, this.lastAudiblyAlertedMs);
        Integer num = this.profileBadgeResId;
        int iHashCode4 = (iM3 + (num == null ? 0 : num.hashCode())) * 31;
        CharSequence charSequence3 = this.title;
        int iHashCode5 = (iHashCode4 + (charSequence3 == null ? 0 : charSequence3.hashCode())) * 31;
        CharSequence charSequence4 = this.text;
        int iHashCode6 = (iHashCode5 + (charSequence4 == null ? 0 : charSequence4.hashCode())) * 31;
        ImageModel imageModel2 = this.skeletonLargeIcon;
        int iHashCode7 = (iHashCode6 + (imageModel2 == null ? 0 : imageModel2.hashCode())) * 31;
        OldProgress oldProgress = this.oldProgress;
        int iHashCode8 = (this.style.hashCode() + ((this.colors.hashCode() + ((iHashCode7 + (oldProgress == null ? 0 : oldProgress.hashCode())) * 31)) * 31)) * 31;
        ImageModel imageModel3 = this.verificationIcon;
        int iHashCode9 = (iHashCode8 + (imageModel3 == null ? 0 : imageModel3.hashCode())) * 31;
        CharSequence charSequence5 = this.verificationText;
        int iHashCode10 = (iHashCode9 + (charSequence5 == null ? 0 : charSequence5.hashCode())) * 31;
        NotificationProgressModel notificationProgressModel = this.newProgress;
        return iHashCode10 + (notificationProgressModel != null ? notificationProgressModel.hashCode() : 0);
    }

    public final String toString() {
        CharSequence charSequence = this.appName;
        CharSequence charSequence2 = this.subText;
        CharSequence charSequence3 = this.title;
        CharSequence charSequence4 = this.text;
        CharSequence charSequence5 = this.verificationText;
        return "PromotedNotificationContentModel(identity=" + this.identity + ", wasPromotedAutomatically=" + this.wasPromotedAutomatically + ", smallIcon=" + this.smallIcon + ", iconLevel=" + this.iconLevel + ", appName=" + ((Object) charSequence) + ", subText=" + ((Object) charSequence2) + ", shortCriticalText=" + this.shortCriticalText + ", time=" + this.time + ", lastAudiblyAlertedMs=" + this.lastAudiblyAlertedMs + ", profileBadgeResId=" + this.profileBadgeResId + ", title=" + ((Object) charSequence3) + ", text=" + ((Object) charSequence4) + ", skeletonLargeIcon=" + this.skeletonLargeIcon + ", oldProgress=" + this.oldProgress + ", colors=" + this.colors + ", style=" + this.style + ", verificationIcon=" + this.verificationIcon + ", verificationText=" + ((Object) charSequence5) + ", newProgress=" + this.newProgress + ")";
    }
}
