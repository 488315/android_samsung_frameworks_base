package android.media.permission;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class Identity implements Parcelable {
    public static final Parcelable.Creator<Identity> CREATOR = new Parcelable.Creator<Identity>() { // from class: android.media.permission.Identity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Identity createFromParcel(Parcel parcel) {
            Identity identity = new Identity();
            identity.readFromParcel(parcel);
            return identity;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Identity[] newArray(int i) {
            return new Identity[i];
        }
    };
    public String attributionTag;
    public String packageName;
    public int uid = -1;
    public int pid = -1;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.pid);
        parcel.writeString(this.packageName);
        parcel.writeString(this.attributionTag);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.uid = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.pid = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.packageName = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.attributionTag = parcel.readString();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("uid: " + this.uid);
        stringJoiner.add("pid: " + this.pid);
        stringJoiner.add("packageName: " + Objects.toString(this.packageName));
        stringJoiner.add("attributionTag: " + Objects.toString(this.attributionTag));
        return "Identity" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Identity)) {
            return false;
        }
        Identity identity = (Identity) obj;
        return Objects.deepEquals(Integer.valueOf(this.uid), Integer.valueOf(identity.uid)) && Objects.deepEquals(Integer.valueOf(this.pid), Integer.valueOf(identity.pid)) && Objects.deepEquals(this.packageName, identity.packageName) && Objects.deepEquals(this.attributionTag, identity.attributionTag);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.uid), Integer.valueOf(this.pid), this.packageName, this.attributionTag).toArray());
    }
}
