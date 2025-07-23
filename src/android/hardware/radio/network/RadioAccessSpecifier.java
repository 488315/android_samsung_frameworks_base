package android.hardware.radio.network;

import android.hardware.radio.AccessNetwork$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class RadioAccessSpecifier implements Parcelable {
    public static final Parcelable.Creator<RadioAccessSpecifier> CREATOR = new Parcelable.Creator<RadioAccessSpecifier>() { // from class: android.hardware.radio.network.RadioAccessSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioAccessSpecifier createFromParcel(Parcel parcel) {
            RadioAccessSpecifier radioAccessSpecifier = new RadioAccessSpecifier();
            radioAccessSpecifier.readFromParcel(parcel);
            return radioAccessSpecifier;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioAccessSpecifier[] newArray(int i) {
            return new RadioAccessSpecifier[i];
        }
    };
    public int accessNetwork = 0;
    public RadioAccessSpecifierBands bands;
    public int[] channels;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.accessNetwork);
        parcel.writeTypedObject(this.bands, i);
        parcel.writeIntArray(this.channels);
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
                this.accessNetwork = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.bands = (RadioAccessSpecifierBands) parcel.readTypedObject(RadioAccessSpecifierBands.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.channels = parcel.createIntArray();
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
        stringJoiner.add("accessNetwork: " + AccessNetwork$$.toString(this.accessNetwork));
        stringJoiner.add("bands: " + Objects.toString(this.bands));
        stringJoiner.add("channels: " + Arrays.toString(this.channels));
        return "RadioAccessSpecifier" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.bands);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
