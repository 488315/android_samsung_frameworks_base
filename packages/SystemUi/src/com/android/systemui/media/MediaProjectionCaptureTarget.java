package com.android.systemui.media;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaProjectionCaptureTarget implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public final IBinder launchCookie;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CREATOR implements Parcelable.Creator {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MediaProjectionCaptureTarget(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MediaProjectionCaptureTarget[i];
        }
    }

    public MediaProjectionCaptureTarget(IBinder iBinder) {
        this.launchCookie = iBinder;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MediaProjectionCaptureTarget) && Intrinsics.areEqual(this.launchCookie, ((MediaProjectionCaptureTarget) obj).launchCookie);
    }

    public final int hashCode() {
        IBinder iBinder = this.launchCookie;
        if (iBinder == null) {
            return 0;
        }
        return iBinder.hashCode();
    }

    public final String toString() {
        return "MediaProjectionCaptureTarget(launchCookie=" + this.launchCookie + ")";
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.launchCookie);
    }

    public MediaProjectionCaptureTarget(Parcel parcel) {
        this(parcel.readStrongBinder());
    }
}
