package com.android.server.locksettings;

import android.hardware.authsecret.IAuthSecret;
import android.hardware.authsecret.V1_0.IAuthSecret$Proxy;
import android.os.HwParcel;
import android.os.IBinder;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AuthSecretHidlAdapter implements IAuthSecret {
    public final IAuthSecret$Proxy mImpl;

    public AuthSecretHidlAdapter(IAuthSecret$Proxy iAuthSecret$Proxy) {
        this.mImpl = iAuthSecret$Proxy;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        throw new UnsupportedOperationException("AuthSecretHidlAdapter does not support asBinder");
    }

    @Override // android.hardware.authsecret.IAuthSecret
    public final void setPrimaryUserCredential(byte[] bArr) {
        ArrayList arrayList = new ArrayList(bArr.length);
        for (byte b : bArr) {
            arrayList.add(Byte.valueOf(b));
        }
        IAuthSecret$Proxy iAuthSecret$Proxy = this.mImpl;
        iAuthSecret$Proxy.getClass();
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.authsecret@1.0::IAuthSecret");
        hwParcel.writeInt8Vector(arrayList);
        HwParcel hwParcel2 = new HwParcel();
        try {
            iAuthSecret$Proxy.mRemote.transact(1, hwParcel, hwParcel2, 1);
            hwParcel.releaseTemporaryStorage();
        } finally {
            hwParcel2.release();
        }
    }
}
