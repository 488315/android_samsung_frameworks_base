package com.android.systemui.shared.system.smartspace;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SmartspaceState implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public final Rect boundsOnScreen;
    public final int selectedPage;
    public final boolean visibleOnScreen;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CREATOR implements Parcelable.Creator {
        private CREATOR() {
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new SmartspaceState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SmartspaceState[i];
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        parcel.writeParcelable(this.boundsOnScreen, 0);
        parcel.writeInt(this.selectedPage);
        parcel.writeBoolean(this.visibleOnScreen);
    }

    public SmartspaceState(Parcel parcel) {
        this();
        Rect rect = (Rect) parcel.readParcelable(new PropertyReference1Impl() { // from class: com.android.systemui.shared.system.smartspace.SmartspaceState.1
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj) {
                return obj.getClass();
            }
        }.getClass().getClassLoader());
        this.boundsOnScreen = rect == null ? new Rect() : rect;
        this.selectedPage = parcel.readInt();
        this.visibleOnScreen = parcel.readBoolean();
    }
}
