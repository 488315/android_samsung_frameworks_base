package com.android.media.permission;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public class UidPackageState implements Parcelable {
    public static final Parcelable.Creator<UidPackageState> CREATOR = new Parcelable.Creator<UidPackageState>() { // from class: com.android.media.permission.UidPackageState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UidPackageState createFromParcel(Parcel parcel) {
            UidPackageState uidPackageState = new UidPackageState();
            uidPackageState.readFromParcel(parcel);
            return uidPackageState;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UidPackageState[] newArray(int i) {
            return new UidPackageState[i];
        }
    };
    public List<String> packageNames;
    public int uid = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.uid);
        parcel.writeStringList(this.packageNames);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.uid = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.packageNames = parcel.createStringArrayList();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("uid: " + this.uid);
        stringJoiner.add("packageNames: " + Objects.toString(this.packageNames));
        return "UidPackageState" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UidPackageState)) {
            return false;
        }
        UidPackageState uidPackageState = (UidPackageState) obj;
        return Objects.deepEquals(Integer.valueOf(this.uid), Integer.valueOf(uidPackageState.uid)) && Objects.deepEquals(this.packageNames, uidPackageState.packageNames);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.uid), this.packageNames).toArray());
    }
}
