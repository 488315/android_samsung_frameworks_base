package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class PcoDataInfo implements Parcelable {
    public static final Parcelable.Creator<PcoDataInfo> CREATOR = new Parcelable.Creator<PcoDataInfo>() { // from class: android.hardware.radio.data.PcoDataInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PcoDataInfo createFromParcel(Parcel parcel) {
            PcoDataInfo pcoDataInfo = new PcoDataInfo();
            pcoDataInfo.readFromParcel(parcel);
            return pcoDataInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PcoDataInfo[] newArray(int i) {
            return new PcoDataInfo[i];
        }
    };
    public String bearerProto;
    public byte[] contents;
    public int cid = 0;
    public int pcoId = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.cid);
        parcel.writeString(this.bearerProto);
        parcel.writeInt(this.pcoId);
        parcel.writeByteArray(this.contents);
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
                this.cid = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.bearerProto = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.pcoId = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.contents = parcel.createByteArray();
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
        stringJoiner.add("cid: " + this.cid);
        stringJoiner.add("bearerProto: " + Objects.toString(this.bearerProto));
        stringJoiner.add("pcoId: " + this.pcoId);
        stringJoiner.add("contents: " + Arrays.toString(this.contents));
        return "PcoDataInfo" + stringJoiner.toString();
    }
}
