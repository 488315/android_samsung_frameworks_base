package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy;
import android.hardware.broadcastradio.V2_0.VendorKeyValue;
import android.os.HwBlob;
import android.os.HwParcel;
import android.util.SparseArray;

import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final /* synthetic */ class TunerSession$$ExternalSyntheticLambda0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TunerSession$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public Object exec() {
        ArrayList arrayList;
        HwParcel hwParcel;
        Object obj = this.f$0;
        Object obj2 = this.f$1;
        switch (this.$r8$classId) {
            case 1:
                Map map = (Map) obj2;
                ITunerSession$Proxy iTunerSession$Proxy = ((TunerSession) obj).mHwSession;
                SparseArray sparseArray = Convert.METADATA_KEYS;
                if (map == null) {
                    arrayList = new ArrayList();
                } else {
                    arrayList = new ArrayList();
                    for (Map.Entry entry : map.entrySet()) {
                        VendorKeyValue vendorKeyValue = new VendorKeyValue();
                        vendorKeyValue.key = (String) entry.getKey();
                        String str = (String) entry.getValue();
                        vendorKeyValue.value = str;
                        if (vendorKeyValue.key == null || str == null) {
                            Slogf.w("BcRadio2Srv.convert", "VendorKeyValue contains null pointers");
                        } else {
                            arrayList.add(vendorKeyValue);
                        }
                    }
                }
                iTunerSession$Proxy.getClass();
                HwParcel hwParcel2 = new HwParcel();
                hwParcel2.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
                HwBlob hwBlob = new HwBlob(16);
                int size = arrayList.size();
                hwBlob.putInt32(8L, size);
                hwBlob.putBool(12L, false);
                HwBlob hwBlob2 = new HwBlob(size * 32);
                for (int i = 0; i < size; i++) {
                    VendorKeyValue vendorKeyValue2 = (VendorKeyValue) arrayList.get(i);
                    long j = i * 32;
                    hwBlob2.putString(j, vendorKeyValue2.key);
                    hwBlob2.putString(j + 16, vendorKeyValue2.value);
                }
                hwBlob.putBlob(0L, hwBlob2);
                hwParcel2.writeBuffer(hwBlob);
                hwParcel = new HwParcel();
                try {
                    iTunerSession$Proxy.mRemote.transact(9, hwParcel2, hwParcel, 0);
                    hwParcel.verifySuccess();
                    hwParcel2.releaseTemporaryStorage();
                    return VendorKeyValue.readVectorFromParcel(hwParcel);
                } finally {
                }
            default:
                List list = (List) obj2;
                ITunerSession$Proxy iTunerSession$Proxy2 = ((TunerSession) obj).mHwSession;
                SparseArray sparseArray2 = Convert.METADATA_KEYS;
                ArrayList arrayList2 =
                        list == null
                                ? null
                                : list instanceof ArrayList
                                        ? (ArrayList) list
                                        : new ArrayList(list);
                iTunerSession$Proxy2.getClass();
                HwParcel hwParcel3 = new HwParcel();
                hwParcel3.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
                hwParcel3.writeStringVector(arrayList2);
                hwParcel = new HwParcel();
                try {
                    iTunerSession$Proxy2.mRemote.transact(10, hwParcel3, hwParcel, 0);
                    hwParcel.verifySuccess();
                    hwParcel3.releaseTemporaryStorage();
                    return VendorKeyValue.readVectorFromParcel(hwParcel);
                } finally {
                }
        }
    }
}
