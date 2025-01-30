package android.net;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ResolverOptionsParcel implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() { // from class: android.net.ResolverOptionsParcel.1
        @Override // android.os.Parcelable.Creator
        public ResolverOptionsParcel createFromParcel(Parcel parcel) {
            ResolverOptionsParcel resolverOptionsParcel = new ResolverOptionsParcel();
            resolverOptionsParcel.readFromParcel(parcel);
            return resolverOptionsParcel;
        }

        @Override // android.os.Parcelable.Creator
        public ResolverOptionsParcel[] newArray(int i) {
            return new ResolverOptionsParcel[i];
        }
    };
    public ResolverHostsParcel[] hosts = new ResolverHostsParcel[0];
    public int tcMode = 0;
    public boolean enforceDnsUid = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.hosts, i);
        parcel.writeInt(this.tcMode);
        parcel.writeBoolean(this.enforceDnsUid);
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
                this.hosts = (ResolverHostsParcel[]) parcel.createTypedArray(ResolverHostsParcel.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.tcMode = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.enforceDnsUid = parcel.readBoolean();
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                        parcel.setDataPosition(dataPosition + readInt);
                        return;
                    }
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
        return describeContents(this.hosts) | 0;
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
