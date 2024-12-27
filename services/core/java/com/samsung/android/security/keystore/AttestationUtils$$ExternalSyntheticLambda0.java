package com.samsung.android.security.keystore;

import android.hardware.security.keymint.Certificate;
import android.hardware.security.keymint.KeyParameter;
import android.os.Parcel;
import android.security.samsungattestation.ISamsungAttestation;
import android.system.keystore2.KeyDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class AttestationUtils$$ExternalSyntheticLambda0 {
    public final /* synthetic */ KeyDescriptor f$0;
    public final /* synthetic */ KeyParameter[] f$1;

    public /* synthetic */ AttestationUtils$$ExternalSyntheticLambda0(KeyDescriptor keyDescriptor, KeyParameter[] keyParameterArr) {
        this.f$0 = keyDescriptor;
        this.f$1 = keyParameterArr;
    }

    public final Object execute(ISamsungAttestation iSamsungAttestation) {
        KeyDescriptor keyDescriptor = this.f$0;
        KeyParameter[] keyParameterArr = this.f$1;
        ISamsungAttestation.Stub.Proxy proxy = (ISamsungAttestation.Stub.Proxy) iSamsungAttestation;
        Parcel obtain = Parcel.obtain(proxy.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("android.security.samsungattestation.ISamsungAttestation");
            obtain.writeTypedObject(keyDescriptor, 0);
            obtain.writeTypedArray(keyParameterArr, 0);
            proxy.mRemote.transact(1, obtain, obtain2, 0);
            obtain2.readException();
            return (Certificate[]) obtain2.createTypedArray(Certificate.CREATOR);
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
