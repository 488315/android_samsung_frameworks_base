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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.atomId);
        parcel.writeTypedArray(this.values, i);
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
                this.atomId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.values = (StatsBootstrapAtomValue[]) parcel.createTypedArray(StatsBootstrapAtomValue.CREATOR);
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.values);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
