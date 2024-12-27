package android.hardware.broadcastradio.V2_0;

import android.os.HwParcel;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract /* synthetic */ class ITunerSession$Proxy$$ExternalSyntheticOutline0 {
    public static HwParcel m(int i, String str) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken(str);
        hwParcel.writeInt32(i);
        return hwParcel;
    }
}
