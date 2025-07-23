package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Set;

/* loaded from: classes.dex */
public final class StringSetIntersection extends ResolutionMechanism<Set<String>> {
    public static final StringSetIntersection STRING_SET_INTERSECTION = new StringSetIntersection();
    public static final Parcelable.Creator<StringSetIntersection> CREATOR = new Parcelable.Creator<StringSetIntersection>() { // from class: android.app.admin.StringSetIntersection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringSetIntersection createFromParcel(Parcel parcel) {
            return new StringSetIntersection();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringSetIntersection[] newArray(int i) {
            return new StringSetIntersection[i];
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
        return "StringSetIntersection {}";
    }
}
