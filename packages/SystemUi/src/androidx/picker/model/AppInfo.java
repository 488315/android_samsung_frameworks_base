package androidx.picker.model;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppInfo implements Parcelable {
    public final String activityName;
    public final String packageName;
    public final int user;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<AppInfo> CREATOR = new Creator();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new AppInfo(parcel.readString(), parcel.readString(), parcel.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new AppInfo[i];
        }
    }

    public AppInfo(String str, String str2, int i) {
        this.packageName = str;
        this.activityName = str2;
        this.user = i;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppInfo)) {
            return false;
        }
        AppInfo appInfo = (AppInfo) obj;
        return Intrinsics.areEqual(this.packageName, appInfo.packageName) && Intrinsics.areEqual(this.activityName, appInfo.activityName) && this.user == appInfo.user;
    }

    public final int hashCode() {
        return Integer.hashCode(this.user) + AppInfo$$ExternalSyntheticOutline0.m41m(this.activityName, this.packageName.hashCode() * 31, 31);
    }

    public final String toString() {
        return "AppInfo(packageName=" + this.packageName + ", activityName=" + this.activityName + ", user=" + this.user + ')';
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.activityName);
        parcel.writeInt(this.user);
    }
}
