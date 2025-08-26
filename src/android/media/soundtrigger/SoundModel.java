package android.media.soundtrigger;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class SoundModel implements Parcelable {
    public static final Parcelable.Creator<SoundModel> CREATOR = new Parcelable.Creator<SoundModel>() { // from class: android.media.soundtrigger.SoundModel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundModel createFromParcel(Parcel parcel) {
            SoundModel soundModel = new SoundModel();
            soundModel.readFromParcel(parcel);
            return soundModel;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundModel[] newArray(int i) {
            return new SoundModel[i];
        }
    };
    public ParcelFileDescriptor data;
    public String uuid;
    public String vendorUuid;
    public int type = -1;
    public int dataSize = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.type);
        parcel.writeString(this.uuid);
        parcel.writeString(this.vendorUuid);
        parcel.writeTypedObject(this.data, i);
        parcel.writeInt(this.dataSize);
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
                this.type = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.uuid = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.vendorUuid = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.data = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.dataSize = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("uuid: " + Objects.toString(this.uuid));
        stringJoiner.add("vendorUuid: " + Objects.toString(this.vendorUuid));
        stringJoiner.add("data: " + Objects.toString(this.data));
        stringJoiner.add("dataSize: " + this.dataSize);
        return "SoundModel" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SoundModel)) {
            return false;
        }
        SoundModel soundModel = (SoundModel) obj;
        return Objects.deepEquals(Integer.valueOf(this.type), Integer.valueOf(soundModel.type)) && Objects.deepEquals(this.uuid, soundModel.uuid) && Objects.deepEquals(this.vendorUuid, soundModel.vendorUuid) && Objects.deepEquals(this.data, soundModel.data) && Objects.deepEquals(Integer.valueOf(this.dataSize), Integer.valueOf(soundModel.dataSize));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.type), this.uuid, this.vendorUuid, this.data, Integer.valueOf(this.dataSize)).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.data);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
