package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class MostRecent<V> extends ResolutionMechanism<V> {
    public static final MostRecent<?> MOST_RECENT = new MostRecent<>();
    public static final Parcelable.Creator<MostRecent<?>> CREATOR = new Parcelable.Creator<MostRecent<?>>() { // from class: android.app.admin.MostRecent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MostRecent<?> createFromParcel(Parcel parcel) {
            return new MostRecent<>();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MostRecent<?>[] newArray(int i) {
            return new MostRecent[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }

    public String toString() {
        return "MostRecent {}";
    }
}
