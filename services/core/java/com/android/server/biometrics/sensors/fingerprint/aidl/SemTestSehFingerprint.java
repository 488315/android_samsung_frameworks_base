package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.util.SparseIntArray;

import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint;
import vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint$Stub$Proxy;
import vendor.samsung.hardware.biometrics.fingerprint.SehResult;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemTestSehFingerprint extends Binder implements ISehFingerprint {
    public final SparseIntArray mRequestActionTable;

    public SemTestSehFingerprint() {
        markVintfStability();
        attachInterface(this, ISehFingerprint.DESCRIPTOR);
        this.mRequestActionTable = new SparseIntArray();
    }

    public static ISehFingerprint asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface(ISehFingerprint.DESCRIPTOR);
        if (queryLocalInterface != null && (queryLocalInterface instanceof ISehFingerprint)) {
            return (ISehFingerprint) queryLocalInterface;
        }
        ISehFingerprint$Stub$Proxy iSehFingerprint$Stub$Proxy = new ISehFingerprint$Stub$Proxy();
        iSehFingerprint$Stub$Proxy.mRemote = iBinder;
        return iSehFingerprint$Stub$Proxy;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }

    public final int getMaxTransactionId() {
        return 16777214;
    }

    public final String getTransactionName(int i) {
        if (i == 1) {
            return "sehRequest";
        }
        switch (i) {
            case 16777214:
                return "getInterfaceHash";
            case 16777215:
                return "getInterfaceVersion";
            default:
                return null;
        }
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        String str = ISehFingerprint.DESCRIPTOR;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface(str);
        }
        if (i == 1598968902) {
            parcel2.writeString(str);
            return true;
        }
        if (i == 16777215) {
            parcel2.writeNoException();
            parcel2.writeInt(0);
            return true;
        }
        if (i == 16777214) {
            parcel2.writeNoException();
            parcel2.writeString("");
            return true;
        }
        if (i != 1) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        byte[] createByteArray = parcel.createByteArray();
        parcel.enforceNoDataAvail();
        SehResult sehRequest = sehRequest(readInt, readInt2, readInt3, createByteArray);
        parcel2.writeNoException();
        parcel2.writeTypedObject(sehRequest, 1);
        return true;
    }

    @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint
    public final SehResult sehRequest(int i, int i2, int i3, byte[] bArr) {
        SehResult sehResult = new SehResult();
        sehResult.retValue = this.mRequestActionTable.get(i2, 0);
        return sehResult;
    }
}
