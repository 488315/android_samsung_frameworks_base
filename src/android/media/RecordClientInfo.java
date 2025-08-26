package android.media;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class RecordClientInfo implements Parcelable {
    public static final Parcelable.Creator<RecordClientInfo> CREATOR = new Parcelable.Creator<RecordClientInfo>() { // from class: android.media.RecordClientInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecordClientInfo createFromParcel(Parcel parcel) {
            RecordClientInfo recordClientInfo = new RecordClientInfo();
            recordClientInfo.readFromParcel(parcel);
            return recordClientInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecordClientInfo[] newArray(int i) {
            return new RecordClientInfo[i];
        }
    };
    public int source;
    public int riid = 0;
    public int uid = 0;
    public int session = 0;
    public int portId = 0;
    public boolean silenced = false;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.riid);
        parcel.writeInt(this.uid);
        parcel.writeInt(this.session);
        parcel.writeInt(this.source);
        parcel.writeInt(this.portId);
        parcel.writeBoolean(this.silenced);
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
                this.riid = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.uid = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.session = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.source = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.portId = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.silenced = parcel.readBoolean();
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
