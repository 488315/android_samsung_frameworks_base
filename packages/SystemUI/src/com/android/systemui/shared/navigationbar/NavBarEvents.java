package com.android.systemui.shared.navigationbar;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NavBarEvents implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public int appearance;
    public int displayId;
    public EventType eventType;
    public boolean hiddenByKnox;
    public Bundle iconBitmapBundle;
    public final IconType iconType;
    public Bundle insetsBundle;
    public final boolean orderDefault;
    public Bundle pluginBundle;
    public final int position;
    public Bundle remoteViewBundle;
    public boolean rotationLocked;
    public boolean transientShowing;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CREATOR implements Parcelable.Creator {
        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new NavBarEvents(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NavBarEvents[i];
        }

        private CREATOR() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EventType {
        public static final /* synthetic */ EventType[] $VALUES;
        public static final EventType ON_APPEARANCE_CHANGED;
        public static final EventType ON_ROTATION_LOCKED_CHANGED;
        public static final EventType ON_TRANSIENT_SHOWING_CHANGED;
        public static final EventType ON_UPDATE_ICON_BITMAP;
        public static final EventType ON_UPDATE_NAVBAR_REMOTEVIEWS;
        public static final EventType ON_UPDATE_SIDE_BACK_GESTURE_INSETS;
        public static final EventType ON_UPDATE_SPLUGIN_BUNDLE;
        public static final EventType ON_UPDATE_TASKBAR_VIS_BY_KNOX;

        static {
            EventType eventType = new EventType("ON_UPDATE_NAVBAR_REMOTEVIEWS", 0);
            ON_UPDATE_NAVBAR_REMOTEVIEWS = eventType;
            EventType eventType2 = new EventType("ON_UPDATE_ICON_BITMAP", 1);
            ON_UPDATE_ICON_BITMAP = eventType2;
            EventType eventType3 = new EventType("ON_ROTATION_LOCKED_CHANGED", 2);
            ON_ROTATION_LOCKED_CHANGED = eventType3;
            EventType eventType4 = new EventType("ON_TRANSIENT_SHOWING_CHANGED", 3);
            ON_TRANSIENT_SHOWING_CHANGED = eventType4;
            EventType eventType5 = new EventType("ON_APPEARANCE_CHANGED", 4);
            ON_APPEARANCE_CHANGED = eventType5;
            EventType eventType6 = new EventType("ON_UPDATE_SPLUGIN_BUNDLE", 5);
            ON_UPDATE_SPLUGIN_BUNDLE = eventType6;
            EventType eventType7 = new EventType("ON_UPDATE_TASKBAR_VIS_BY_KNOX", 6);
            ON_UPDATE_TASKBAR_VIS_BY_KNOX = eventType7;
            EventType eventType8 = new EventType("ON_UPDATE_SIDE_BACK_GESTURE_INSETS", 7);
            ON_UPDATE_SIDE_BACK_GESTURE_INSETS = eventType8;
            EventType[] eventTypeArr = {eventType, eventType2, eventType3, eventType4, eventType5, eventType6, eventType7, eventType8};
            $VALUES = eventTypeArr;
            EnumEntriesKt.enumEntries(eventTypeArr);
        }

        private EventType(String str, int i) {
        }

        public static EventType valueOf(String str) {
            return (EventType) Enum.valueOf(EventType.class, str);
        }

        public static EventType[] values() {
            return (EventType[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IconType {
        public static final /* synthetic */ IconType[] $VALUES;
        public static final IconType TYPE_GESTURE_HANDLE_HINT;
        public static final IconType TYPE_GESTURE_HINT;
        public static final IconType TYPE_GESTURE_HINT_VI;

        static {
            IconType iconType = new IconType("TYPE_BACK", 0);
            IconType iconType2 = new IconType("TYPE_BACK_LAND", 1);
            IconType iconType3 = new IconType("TYPE_BACK_ALT", 2);
            IconType iconType4 = new IconType("TYPE_BACK_ALT_LAND", 3);
            IconType iconType5 = new IconType("TYPE_HOME", 4);
            IconType iconType6 = new IconType("TYPE_RECENT", 5);
            IconType iconType7 = new IconType("TYPE_DOCKED", 6);
            IconType iconType8 = new IconType("TYPE_IME", 7);
            IconType iconType9 = new IconType("TYPE_MENU", 8);
            IconType iconType10 = new IconType("TYPE_SHOW_PIN", 9);
            IconType iconType11 = new IconType("TYPE_HIDE_PIN", 10);
            IconType iconType12 = new IconType("TYPE_A11Y", 11);
            IconType iconType13 = new IconType("TYPE_BACK_CAR", 12);
            IconType iconType14 = new IconType("TYPE_BACK_LAND_CAR", 13);
            IconType iconType15 = new IconType("TYPE_BACK_ALT_CAR", 14);
            IconType iconType16 = new IconType("TYPE_BACK_ALT_LAND_CAR", 15);
            IconType iconType17 = new IconType("TYPE_HOME_CAR", 16);
            IconType iconType18 = new IconType("TYPE_GESTURE_HINT", 17);
            TYPE_GESTURE_HINT = iconType18;
            IconType iconType19 = new IconType("TYPE_GESTURE_HINT_VI", 18);
            TYPE_GESTURE_HINT_VI = iconType19;
            IconType iconType20 = new IconType("TYPE_GESTURE_HANDLE_HINT", 19);
            TYPE_GESTURE_HANDLE_HINT = iconType20;
            IconType[] iconTypeArr = {iconType, iconType2, iconType3, iconType4, iconType5, iconType6, iconType7, iconType8, iconType9, iconType10, iconType11, iconType12, iconType13, iconType14, iconType15, iconType16, iconType17, iconType18, iconType19, iconType20, new IconType("TYPE_SECONDARY_HOME_HANDLE", 20)};
            $VALUES = iconTypeArr;
            EnumEntriesKt.enumEntries(iconTypeArr);
        }

        private IconType(String str, int i) {
        }

        public static IconType valueOf(String str) {
            return (IconType) Enum.valueOf(IconType.class, str);
        }

        public static IconType[] values() {
            return (IconType[]) $VALUES.clone();
        }
    }

    public NavBarEvents() {
        this(null, null, null, null, false, 0, false, false, 0, null, false, null, 0, 8191, null);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NavBarEvents)) {
            return false;
        }
        NavBarEvents navBarEvents = (NavBarEvents) obj;
        return this.eventType == navBarEvents.eventType && this.iconType == navBarEvents.iconType && Intrinsics.areEqual(this.remoteViewBundle, navBarEvents.remoteViewBundle) && Intrinsics.areEqual(this.iconBitmapBundle, navBarEvents.iconBitmapBundle) && this.orderDefault == navBarEvents.orderDefault && this.position == navBarEvents.position && this.rotationLocked == navBarEvents.rotationLocked && this.transientShowing == navBarEvents.transientShowing && this.appearance == navBarEvents.appearance && Intrinsics.areEqual(this.pluginBundle, navBarEvents.pluginBundle) && this.hiddenByKnox == navBarEvents.hiddenByKnox && Intrinsics.areEqual(this.insetsBundle, navBarEvents.insetsBundle) && this.displayId == navBarEvents.displayId;
    }

    public final int hashCode() {
        EventType eventType = this.eventType;
        int hashCode = (eventType == null ? 0 : eventType.hashCode()) * 31;
        IconType iconType = this.iconType;
        int hashCode2 = (hashCode + (iconType == null ? 0 : iconType.hashCode())) * 31;
        Bundle bundle = this.remoteViewBundle;
        int hashCode3 = (hashCode2 + (bundle == null ? 0 : bundle.hashCode())) * 31;
        Bundle bundle2 = this.iconBitmapBundle;
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.appearance, TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.position, TransitionData$$ExternalSyntheticOutline0.m((hashCode3 + (bundle2 == null ? 0 : bundle2.hashCode())) * 31, 31, this.orderDefault), 31), 31, this.rotationLocked), 31, this.transientShowing), 31);
        Bundle bundle3 = this.pluginBundle;
        int m2 = TransitionData$$ExternalSyntheticOutline0.m((m + (bundle3 == null ? 0 : bundle3.hashCode())) * 31, 31, this.hiddenByKnox);
        Bundle bundle4 = this.insetsBundle;
        return Integer.hashCode(this.displayId) + ((m2 + (bundle4 != null ? bundle4.hashCode() : 0)) * 31);
    }

    public final String toString() {
        EventType eventType = this.eventType;
        IconType iconType = this.iconType;
        Bundle bundle = this.remoteViewBundle;
        Bundle bundle2 = this.iconBitmapBundle;
        boolean z = this.orderDefault;
        int i = this.position;
        boolean z2 = this.rotationLocked;
        boolean z3 = this.transientShowing;
        int i2 = this.appearance;
        Bundle bundle3 = this.pluginBundle;
        boolean z4 = this.hiddenByKnox;
        Bundle bundle4 = this.insetsBundle;
        int i3 = this.displayId;
        StringBuilder sb = new StringBuilder("NavBarEvents(eventType=");
        sb.append(eventType);
        sb.append(", iconType=");
        sb.append(iconType);
        sb.append(", remoteViewBundle=");
        sb.append(bundle);
        sb.append(", iconBitmapBundle=");
        sb.append(bundle2);
        sb.append(", orderDefault=");
        sb.append(z);
        sb.append(", position=");
        sb.append(i);
        sb.append(", rotationLocked=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z2, ", transientShowing=", z3, ", appearance=");
        sb.append(i2);
        sb.append(", pluginBundle=");
        sb.append(bundle3);
        sb.append(", hiddenByKnox=");
        sb.append(z4);
        sb.append(", insetsBundle=");
        sb.append(bundle4);
        sb.append(", displayId=");
        return ReorderTile$$ExternalSyntheticOutline0.m(i3, ")", sb);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        EventType eventType = this.eventType;
        parcel.writeString(eventType != null ? eventType.name() : null);
        IconType iconType = this.iconType;
        parcel.writeString(iconType != null ? iconType.name() : null);
        parcel.writeBundle(this.remoteViewBundle);
        parcel.writeBundle(this.iconBitmapBundle);
        parcel.writeByte(this.orderDefault ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.position);
        parcel.writeBoolean(this.rotationLocked);
        parcel.writeBoolean(this.transientShowing);
        parcel.writeInt(this.appearance);
        parcel.writeBundle(this.pluginBundle);
        parcel.writeBoolean(this.hiddenByKnox);
        parcel.writeBundle(this.insetsBundle);
        parcel.writeInt(this.displayId);
    }

    public NavBarEvents(EventType eventType, IconType iconType, Bundle bundle, Bundle bundle2, boolean z, int i, boolean z2, boolean z3, int i2, Bundle bundle3, boolean z4, Bundle bundle4, int i3) {
        this.eventType = eventType;
        this.iconType = iconType;
        this.remoteViewBundle = bundle;
        this.iconBitmapBundle = bundle2;
        this.orderDefault = z;
        this.position = i;
        this.rotationLocked = z2;
        this.transientShowing = z3;
        this.appearance = i2;
        this.pluginBundle = bundle3;
        this.hiddenByKnox = z4;
        this.insetsBundle = bundle4;
        this.displayId = i3;
    }

    public /* synthetic */ NavBarEvents(EventType eventType, IconType iconType, Bundle bundle, Bundle bundle2, boolean z, int i, boolean z2, boolean z3, int i2, Bundle bundle3, boolean z4, Bundle bundle4, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? null : eventType, (i4 & 2) != 0 ? null : iconType, (i4 & 4) != 0 ? null : bundle, (i4 & 8) != 0 ? null : bundle2, (i4 & 16) != 0 ? true : z, (i4 & 32) != 0 ? 0 : i, (i4 & 64) != 0 ? false : z2, (i4 & 128) != 0 ? false : z3, (i4 & 256) != 0 ? 0 : i2, (i4 & 512) != 0 ? null : bundle3, (i4 & 1024) != 0 ? false : z4, (i4 & 2048) == 0 ? bundle4 : null, (i4 & 4096) != 0 ? 0 : i3);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public NavBarEvents(android.os.Parcel r17) {
        /*
            r16 = this;
            java.lang.String r0 = r17.readString()
            r1 = 0
            if (r0 == 0) goto Ld
            com.android.systemui.shared.navigationbar.NavBarEvents$EventType r0 = com.android.systemui.shared.navigationbar.NavBarEvents.EventType.valueOf(r0)
            r3 = r0
            goto Le
        Ld:
            r3 = r1
        Le:
            java.lang.String r0 = r17.readString()
            if (r0 == 0) goto L18
            com.android.systemui.shared.navigationbar.NavBarEvents$IconType r1 = com.android.systemui.shared.navigationbar.NavBarEvents.IconType.valueOf(r0)
        L18:
            r4 = r1
            android.os.Bundle r5 = r17.readBundle()
            android.os.Bundle r6 = r17.readBundle()
            byte r0 = r17.readByte()
            if (r0 == 0) goto L2a
            r0 = 1
        L28:
            r7 = r0
            goto L2c
        L2a:
            r0 = 0
            goto L28
        L2c:
            int r8 = r17.readInt()
            boolean r9 = r17.readBoolean()
            boolean r10 = r17.readBoolean()
            int r11 = r17.readInt()
            android.os.Bundle r12 = r17.readBundle()
            boolean r13 = r17.readBoolean()
            android.os.Bundle r14 = r17.readBundle()
            int r15 = r17.readInt()
            r2 = r16
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shared.navigationbar.NavBarEvents.<init>(android.os.Parcel):void");
    }
}
