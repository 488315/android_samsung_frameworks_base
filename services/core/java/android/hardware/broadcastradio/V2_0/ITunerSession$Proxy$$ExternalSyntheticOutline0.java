package android.hardware.broadcastradio.V2_0;

import android.os.HwParcel;

public abstract /* synthetic */ class ITunerSession$Proxy$$ExternalSyntheticOutline0 {
    public static HwParcel m(int i, String str) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken(str);
        hwParcel.writeInt32(i);
        return hwParcel;
    }
}
