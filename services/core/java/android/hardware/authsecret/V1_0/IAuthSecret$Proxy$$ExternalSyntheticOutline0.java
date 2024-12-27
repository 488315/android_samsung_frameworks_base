package android.hardware.authsecret.V1_0;

import android.os.HwParcel;
import android.os.NativeHandle;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
