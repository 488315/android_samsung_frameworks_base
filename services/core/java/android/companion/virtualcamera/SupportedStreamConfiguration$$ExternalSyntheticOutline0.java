package android.companion.virtualcamera;

import android.os.Parcel;

public abstract /* synthetic */ class SupportedStreamConfiguration$$ExternalSyntheticOutline0 {
    public static int m(Parcel parcel, int i, int i2) {
        parcel.writeInt(i);
        int dataPosition = parcel.dataPosition();
        parcel.setDataPosition(i2);
        return dataPosition;
    }

    public static void m(int i, int i2, Parcel parcel, int i3) {
        parcel.writeInt(i - i2);
        parcel.setDataPosition(i3);
    }
}
