package com.android.systemui.mediaprojection;

import android.app.ActivityOptions;
import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaProjectionCaptureTarget implements Parcelable {
    public static final CREATOR CREATOR = new CREATOR(null);
    public final ActivityOptions.LaunchCookie launchCookie;
    public final int taskId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CREATOR implements Parcelable.Creator {
        private CREATOR() {
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new MediaProjectionCaptureTarget(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MediaProjectionCaptureTarget[i];
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
