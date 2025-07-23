package android.hardware.usb.gadget;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class GadgetFunction implements Parcelable {
    public static final long ACCESSORY = 2;
    public static final long ADB = 1;
    public static final long AUDIO_SOURCE = 64;
    public static final Parcelable.Creator<GadgetFunction> CREATOR = new Parcelable.Creator<GadgetFunction>() { // from class: android.hardware.usb.gadget.GadgetFunction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GadgetFunction createFromParcel(Parcel parcel) {
            GadgetFunction gadgetFunction = new GadgetFunction();
            gadgetFunction.readFromParcel(parcel);
            return gadgetFunction;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GadgetFunction[] newArray(int i) {
            return new GadgetFunction[i];
        }
    };
    public static final long MIDI = 8;
    public static final long MTP = 4;
    public static final long NCM = 1024;
    public static final long NONE = 0;
    public static final long PTP = 16;
    public static final long RNDIS = 32;
    public static final long UVC = 128;

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
}
