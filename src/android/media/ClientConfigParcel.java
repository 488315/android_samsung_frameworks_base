package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ClientConfigParcel implements Parcelable {
    public static final Parcelable.Creator<ClientConfigParcel> CREATOR = new Parcelable.Creator<ClientConfigParcel>() { // from class: android.media.ClientConfigParcel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientConfigParcel createFromParcel(Parcel parcel) {
            ClientConfigParcel clientConfigParcel = new ClientConfigParcel();
            clientConfigParcel.readFromParcel(parcel);
            return clientConfigParcel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientConfigParcel[] newArray(int i) {
            return new ClientConfigParcel[i];
        }
    };
    public ClientInfoParcel clientInfo;
    public int codecType = 0;
    public boolean isEncoder = false;
    public int width = 0;
    public int height = 0;
    public long timeStamp = 0;
    public long id = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.clientInfo, i);
        parcel.writeInt(this.codecType);
        parcel.writeBoolean(this.isEncoder);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeLong(this.timeStamp);
        parcel.writeLong(this.id);
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
                this.clientInfo = (ClientInfoParcel) parcel.readTypedObject(ClientInfoParcel.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.codecType = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isEncoder = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.width = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.height = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.timeStamp = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.id = parcel.readLong();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.clientInfo);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
