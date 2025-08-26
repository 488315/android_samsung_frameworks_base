package android.media.soundtrigger_middleware;

import android.media.soundtrigger.Properties;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes3.dex */
public class SoundTriggerModuleDescriptor implements Parcelable {
    public static final Parcelable.Creator<SoundTriggerModuleDescriptor> CREATOR = new Parcelable.Creator<SoundTriggerModuleDescriptor>() { // from class: android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundTriggerModuleDescriptor createFromParcel(Parcel parcel) {
            SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = new SoundTriggerModuleDescriptor();
            soundTriggerModuleDescriptor.readFromParcel(parcel);
            return soundTriggerModuleDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SoundTriggerModuleDescriptor[] newArray(int i) {
            return new SoundTriggerModuleDescriptor[i];
        }
    };
    public int handle = 0;
    public Properties properties;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.handle);
        parcel.writeTypedObject(this.properties, i);
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
                this.handle = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.properties = (Properties) parcel.readTypedObject(Properties.CREATOR);
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("handle: " + this.handle);
        stringJoiner.add("properties: " + Objects.toString(this.properties));
        return "SoundTriggerModuleDescriptor" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SoundTriggerModuleDescriptor)) {
            return false;
        }
        SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = (SoundTriggerModuleDescriptor) obj;
        return Objects.deepEquals(Integer.valueOf(this.handle), Integer.valueOf(soundTriggerModuleDescriptor.handle)) && Objects.deepEquals(this.properties, soundTriggerModuleDescriptor.properties);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.handle), this.properties).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.properties);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
