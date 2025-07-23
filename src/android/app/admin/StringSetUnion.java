package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Set;

/* loaded from: classes.dex */
public final class StringSetUnion extends ResolutionMechanism<Set<String>> {
    public static final StringSetUnion STRING_SET_UNION = new StringSetUnion();
    public static final Parcelable.Creator<StringSetUnion> CREATOR = new Parcelable.Creator<StringSetUnion>() { // from class: android.app.admin.StringSetUnion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringSetUnion createFromParcel(Parcel parcel) {
            return new StringSetUnion();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringSetUnion[] newArray(int i) {
            return new StringSetUnion[i];
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
        return "StringSetUnion {}";
    }
}
