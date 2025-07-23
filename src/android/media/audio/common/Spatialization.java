package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class Spatialization implements Parcelable {
    public static final Parcelable.Creator<Spatialization> CREATOR = new Parcelable.Creator<Spatialization>() { // from class: android.media.audio.common.Spatialization.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Spatialization createFromParcel(Parcel parcel) {
            Spatialization spatialization = new Spatialization();
            spatialization.readFromParcel(parcel);
            return spatialization;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Spatialization[] newArray(int i) {
            return new Spatialization[i];
        }
    };

    public @interface Level {
        public static final byte BED_PLUS_OBJECTS = 2;
        public static final byte MULTICHANNEL = 1;
        public static final byte NONE = 0;
    }

    public @interface Mode {
        public static final byte BINAURAL = 0;
        public static final byte TRANSAURAL = 1;
    }

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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (readInt >= 4) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } else {
            try {
                throw new BadParcelableException("Parcelable too small");
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public String toString() {
        return "Spatialization" + new StringJoiner(", ", "{", "}").toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Spatialization)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(new Object[0]).toArray());
    }
}
