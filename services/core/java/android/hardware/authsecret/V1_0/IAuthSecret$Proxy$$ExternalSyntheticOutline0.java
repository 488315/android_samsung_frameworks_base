package android.hardware.authsecret.V1_0;

import android.os.HwParcel;
import android.os.NativeHandle;

import java.util.ArrayList;

public abstract /* synthetic */ class IAuthSecret$Proxy$$ExternalSyntheticOutline0 {
    public static HwParcel m(String str) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken(str);
        return hwParcel;
    }

    public static HwParcel m(String str, NativeHandle nativeHandle, ArrayList arrayList) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken(str);
        hwParcel.writeNativeHandle(nativeHandle);
        hwParcel.writeStringVector(arrayList);
        return hwParcel;
    }
}
