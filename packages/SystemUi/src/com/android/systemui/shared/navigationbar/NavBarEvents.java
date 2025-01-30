package com.android.systemui.shared.navigationbar;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NavBarEvents implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public int appearance;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CREATOR implements Parcelable.Creator {
        private CREATOR() {
        }

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
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum EventType {
        ON_UPDATE_NAVBAR_REMOTEVIEWS,
        ON_UPDATE_ICON_BITMAP,
        ON_ROTATION_LOCKED_CHANGED,
        ON_TRANSIENT_SHOWING_CHANGED,
        ON_APPEARANCE_CHANGED,
        ON_UPDATE_SPLUGIN_BUNDLE,
        ON_UPDATE_TASKBAR_VIS_BY_KNOX,
        ON_UPDATE_SIDE_BACK_GESTURE_INSETS
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum IconType {
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_BACK,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_BACK_LAND,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_BACK_ALT,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_BACK_ALT_LAND,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_HOME,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_RECENT,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_DOCKED,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_IME,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_MENU,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_SHOW_PIN,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_HIDE_PIN,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_A11Y,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_BACK_CAR,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_BACK_LAND_CAR,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_HOME_CAR,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_HOME_CAR,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_HOME_CAR,
        TYPE_GESTURE_HINT,
        TYPE_GESTURE_HINT_VI,
        TYPE_GESTURE_HANDLE_HINT,
        /* JADX INFO: Fake field, exist only in values array */
        TYPE_SECONDARY_HOME_HANDLE
    }

    public NavBarEvents() {
        this(null, null, null, null, false, 0, false, false, 0, null, false, null, 4095, null);
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
        return this.eventType == navBarEvents.eventType && this.iconType == navBarEvents.iconType && Intrinsics.areEqual(this.remoteViewBundle, navBarEvents.remoteViewBundle) && Intrinsics.areEqual(this.iconBitmapBundle, navBarEvents.iconBitmapBundle) && this.orderDefault == navBarEvents.orderDefault && this.position == navBarEvents.position && this.rotationLocked == navBarEvents.rotationLocked && this.transientShowing == navBarEvents.transientShowing && this.appearance == navBarEvents.appearance && Intrinsics.areEqual(this.pluginBundle, navBarEvents.pluginBundle) && this.hiddenByKnox == navBarEvents.hiddenByKnox && Intrinsics.areEqual(this.insetsBundle, navBarEvents.insetsBundle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        EventType eventType = this.eventType;
        int hashCode = (eventType == null ? 0 : eventType.hashCode()) * 31;
        IconType iconType = this.iconType;
        int hashCode2 = (hashCode + (iconType == null ? 0 : iconType.hashCode())) * 31;
        Bundle bundle = this.remoteViewBundle;
        int hashCode3 = (hashCode2 + (bundle == null ? 0 : bundle.hashCode())) * 31;
        Bundle bundle2 = this.iconBitmapBundle;
        int hashCode4 = (hashCode3 + (bundle2 == null ? 0 : bundle2.hashCode())) * 31;
        boolean z = this.orderDefault;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.position, (hashCode4 + i) * 31, 31);
        boolean z2 = this.rotationLocked;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (m42m + i2) * 31;
        boolean z3 = this.transientShowing;
        int i4 = z3;
        if (z3 != 0) {
            i4 = 1;
        }
        int m42m2 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.appearance, (i3 + i4) * 31, 31);
        Bundle bundle3 = this.pluginBundle;
        int hashCode5 = (m42m2 + (bundle3 == null ? 0 : bundle3.hashCode())) * 31;
        boolean z4 = this.hiddenByKnox;
        int i5 = (hashCode5 + (z4 ? 1 : z4 ? 1 : 0)) * 31;
        Bundle bundle4 = this.insetsBundle;
        return i5 + (bundle4 != null ? bundle4.hashCode() : 0);
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
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(sb, z2, ", transientShowing=", z3, ", appearance=");
        sb.append(i2);
        sb.append(", pluginBundle=");
        sb.append(bundle3);
        sb.append(", hiddenByKnox=");
        sb.append(z4);
        sb.append(", insetsBundle=");
        sb.append(bundle4);
        sb.append(")");
        return sb.toString();
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
    }

    public NavBarEvents(EventType eventType, IconType iconType, Bundle bundle, Bundle bundle2, boolean z, int i, boolean z2, boolean z3, int i2, Bundle bundle3, boolean z4, Bundle bundle4) {
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
    }

    public /* synthetic */ NavBarEvents(EventType eventType, IconType iconType, Bundle bundle, Bundle bundle2, boolean z, int i, boolean z2, boolean z3, int i2, Bundle bundle3, boolean z4, Bundle bundle4, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : eventType, (i3 & 2) != 0 ? null : iconType, (i3 & 4) != 0 ? null : bundle, (i3 & 8) != 0 ? null : bundle2, (i3 & 16) != 0 ? true : z, (i3 & 32) != 0 ? 0 : i, (i3 & 64) != 0 ? false : z2, (i3 & 128) != 0 ? false : z3, (i3 & 256) != 0 ? 0 : i2, (i3 & 512) != 0 ? null : bundle3, (i3 & 1024) == 0 ? z4 : false, (i3 & 2048) == 0 ? bundle4 : null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NavBarEvents(Parcel parcel) {
        this(r3, r0 != null ? IconType.valueOf(r0) : null, parcel.readBundle(), parcel.readBundle(), parcel.readByte() != 0, parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt(), parcel.readBundle(), parcel.readBoolean(), parcel.readBundle());
        String readString = parcel.readString();
        EventType valueOf = readString != null ? EventType.valueOf(readString) : null;
        String readString2 = parcel.readString();
    }
}
