package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class Capability implements Parcelable {
    public static final Parcelable.Creator<Capability> CREATOR = new Parcelable.Creator<Capability>() { // from class: android.content.pm.Capability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Capability[] newArray(int i) {
            return new Capability[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Capability createFromParcel(Parcel parcel) {
            return new Capability(parcel);
        }
    };
    private final String mName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Capability(String str) {
        Objects.requireNonNull(str);
        if (str.contains("/")) {
            throw new IllegalArgumentException("'/' is not permitted in the capability name");
        }
        this.mName = str;
    }

    Capability(Capability capability) {
        this(capability.mName);
    }

    private Capability(Builder builder) {
        this(builder.mName);
    }

    private Capability(Parcel parcel) {
        this.mName = parcel.readString();
    }

    public String getName() {
        return this.mName;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Capability) {
            return this.mName.equals(((Capability) obj).mName);
        }
        return false;
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
    }

    public static final class Builder {
        private final String mName;

        public Builder(String str) {
            Objects.requireNonNull(str);
            if (str.contains("/")) {
                throw new IllegalArgumentException("'/' is not permitted in the capability name");
            }
            this.mName = str;
        }

        public Capability build() {
            return new Capability(this);
        }
    }
}
