package com.samsung.android.knox.p046zt.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IServiceCertProvisionListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.service.IServiceCertProvisionListener";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class _Parcel {
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    boolean attestKey(String str, byte[] bArr);

    ParcelableCertificate[] getCertificateChain(String str);

    byte[] getSignature(String str, byte[] bArr);

    void onError(int i, String str);

    void onStatusChange(String str, String str2);

    void onSuccess(Bundle bundle);

    boolean setCertificateChain(String str, ParcelableCertificate[] parcelableCertificateArr);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IServiceCertProvisionListener {
        public static final int TRANSACTION_attestKey = 4;
        public static final int TRANSACTION_getCertificateChain = 5;
        public static final int TRANSACTION_getSignature = 7;
        public static final int TRANSACTION_onError = 2;
        public static final int TRANSACTION_onStatusChange = 3;
        public static final int TRANSACTION_onSuccess = 1;
        public static final int TRANSACTION_setCertificateChain = 6;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IServiceCertProvisionListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
            public final boolean attestKey(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
            public final ParcelableCertificate[] getCertificateChain(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParcelableCertificate[]) obtain2.createTypedArray(ParcelableCertificate.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IServiceCertProvisionListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
            public final byte[] getSignature(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
            public final void onError(int i, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
            public final void onStatusChange(String str, String str2) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
            public final void onSuccess(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
            public final boolean setCertificateChain(String str, ParcelableCertificate[] parcelableCertificateArr) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(parcelableCertificateArr, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IServiceCertProvisionListener.DESCRIPTOR);
        }

        public static IServiceCertProvisionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IServiceCertProvisionListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IServiceCertProvisionListener)) ? new Proxy(iBinder) : (IServiceCertProvisionListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IServiceCertProvisionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IServiceCertProvisionListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onSuccess((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    onError(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    onStatusChange(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean attestKey = attestKey(parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(attestKey ? 1 : 0);
                    return true;
                case 5:
                    ParcelableCertificate[] certificateChain = getCertificateChain(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(certificateChain, 1);
                    return true;
                case 6:
                    boolean certificateChain2 = setCertificateChain(parcel.readString(), (ParcelableCertificate[]) parcel.createTypedArray(ParcelableCertificate.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(certificateChain2 ? 1 : 0);
                    return true;
                case 7:
                    byte[] signature = getSignature(parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeByteArray(signature);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IServiceCertProvisionListener {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
        public final boolean attestKey(String str, byte[] bArr) {
            return false;
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
        public final ParcelableCertificate[] getCertificateChain(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
        public final byte[] getSignature(String str, byte[] bArr) {
            return null;
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
        public final boolean setCertificateChain(String str, ParcelableCertificate[] parcelableCertificateArr) {
            return false;
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
        public final void onSuccess(Bundle bundle) {
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
        public final void onError(int i, String str) {
        }

        @Override // com.samsung.android.knox.p046zt.service.IServiceCertProvisionListener
        public final void onStatusChange(String str, String str2) {
        }
    }
}
