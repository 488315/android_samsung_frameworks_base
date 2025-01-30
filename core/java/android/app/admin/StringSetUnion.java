package android.app.admin;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Set;

/* loaded from: classes.dex */
public final class StringSetUnion extends ResolutionMechanism<Set<String>> {
    public static final StringSetUnion STRING_SET_UNION = new StringSetUnion();
    public static final Parcelable.Creator<StringSetUnion> CREATOR = new Parcelable.Creator<StringSetUnion>() { // from class: android.app.admin.StringSetUnion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringSetUnion createFromParcel(Parcel source) {
            return new StringSetUnion();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringSetUnion[] newArray(int size) {
            return new StringSetUnion[size];
        }
    };

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o != null && getClass() == o.getClass();
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "StringSetUnion {}";
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
    }
}
