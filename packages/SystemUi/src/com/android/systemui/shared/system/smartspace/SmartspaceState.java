package com.android.systemui.shared.system.smartspace;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SmartspaceState implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public final Rect boundsOnScreen;
    public final int selectedPage;
    public final boolean visibleOnScreen;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CREATOR implements Parcelable.Creator {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new SmartspaceState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SmartspaceState[i];
        }
    }

    public SmartspaceState() {
        this.boundsOnScreen = new Rect();
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return "boundsOnScreen: " + this.boundsOnScreen + ", selectedPage: " + this.selectedPage + ", visibleOnScreen: " + this.visibleOnScreen;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeParcelable(this.boundsOnScreen, 0);
        }
        if (parcel != null) {
            parcel.writeInt(this.selectedPage);
        }
        if (parcel != null) {
            parcel.writeBoolean(this.visibleOnScreen);
        }
    }

    public SmartspaceState(Parcel parcel) {
        this();
        this.boundsOnScreen = (Rect) parcel.readParcelable(new PropertyReference1Impl() { // from class: com.android.systemui.shared.system.smartspace.SmartspaceState.1
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj) {
                return obj.getClass();
            }
        }.getClass().getClassLoader());
        this.selectedPage = parcel.readInt();
        this.visibleOnScreen = parcel.readBoolean();
    }
}
