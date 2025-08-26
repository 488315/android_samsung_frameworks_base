package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ClientInfoParcel implements Parcelable {
    public static final Parcelable.Creator<ClientInfoParcel> CREATOR = new Parcelable.Creator<ClientInfoParcel>() { // from class: android.media.ClientInfoParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientInfoParcel createFromParcel(Parcel parcel) {
            ClientInfoParcel clientInfoParcel = new ClientInfoParcel();
            clientInfoParcel.readFromParcel(parcel);
            return clientInfoParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientInfoParcel[] newArray(int i) {
            return new ClientInfoParcel[i];
        }
    };
    public String name;
    public int pid = -1;
    public int uid = -1;
    public long id = 0;
    public int importance = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.pid);
        parcel.writeInt(this.uid);
        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeInt(this.importance);
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
                this.pid = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.uid = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.id = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.name = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.importance = parcel.readInt();
                                if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
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
}
