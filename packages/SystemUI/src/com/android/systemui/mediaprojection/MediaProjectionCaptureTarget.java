package com.android.systemui.mediaprojection;

import android.app.ActivityOptions;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaProjectionCaptureTarget implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public final ActivityOptions.LaunchCookie launchCookie;
    public final int taskId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CREATOR implements Parcelable.Creator {
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

        private CREATOR() {
        }
    }

    public MediaProjectionCaptureTarget(ActivityOptions.LaunchCookie launchCookie, int i) {
        this.launchCookie = launchCookie;
        this.taskId = i;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaProjectionCaptureTarget)) {
            return false;
        }
        MediaProjectionCaptureTarget mediaProjectionCaptureTarget = (MediaProjectionCaptureTarget) obj;
        return Intrinsics.areEqual(this.launchCookie, mediaProjectionCaptureTarget.launchCookie) && this.taskId == mediaProjectionCaptureTarget.taskId;
    }

    public final int hashCode() {
        ActivityOptions.LaunchCookie launchCookie = this.launchCookie;
        return Integer.hashCode(this.taskId) + ((launchCookie == null ? 0 : launchCookie.hashCode()) * 31);
    }

    public final String toString() {
        return "MediaProjectionCaptureTarget(launchCookie=" + this.launchCookie + ", taskId=" + this.taskId + ")";
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        ActivityOptions.LaunchCookie.writeToParcel(this.launchCookie, parcel);
        parcel.writeInt(this.taskId);
    }

    public MediaProjectionCaptureTarget(Parcel parcel) {
        this(ActivityOptions.LaunchCookie.readFromParcel(parcel), parcel.readInt());
    }
}
