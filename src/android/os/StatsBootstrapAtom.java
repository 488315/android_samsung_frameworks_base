package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class StatsBootstrapAtom implements Parcelable {
    public static final Parcelable.Creator<StatsBootstrapAtom> CREATOR = new Parcelable.Creator<StatsBootstrapAtom>() { // from class: android.os.StatsBootstrapAtom.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatsBootstrapAtom createFromParcel(Parcel parcel) {
            StatsBootstrapAtom statsBootstrapAtom = new StatsBootstrapAtom();
            statsBootstrapAtom.readFromParcel(parcel);
            return statsBootstrapAtom;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatsBootstrapAtom[] newArray(int i) {
            return new StatsBootstrapAtom[i];
        }
    };
    public int atomId = 0;
    public StatsBootstrapAtomValue[] values;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.atomId);
        parcel.writeTypedArray(this.values, i);
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
                this.atomId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.values = (StatsBootstrapAtomValue[]) parcel.createTypedArray(StatsBootstrapAtomValue.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.values);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
