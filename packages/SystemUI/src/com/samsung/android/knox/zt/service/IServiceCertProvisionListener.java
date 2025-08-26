package com.samsung.android.knox.zt.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IServiceCertProvisionListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.service.IServiceCertProvisionListener";

    public class _Parcel {
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

    boolean attestKey(String str, byte[] bArr) throws RemoteException;

    ParcelableCertificate[] getCertificateChain(String str) throws RemoteException;

    byte[] getSignature(String str, byte[] bArr) throws RemoteException;

    void onError(int i, String str) throws RemoteException;

    void onStatusChange(String str, String str2) throws RemoteException;

    void onSuccess(Bundle bundle) throws RemoteException;

    boolean setCertificateChain(String str, ParcelableCertificate[] parcelableCertificateArr) throws RemoteException;

    public abstract class Stub extends Binder implements IServiceCertProvisionListener {
        public static final int TRANSACTION_attestKey = 4;
        public static final int TRANSACTION_getCertificateChain = 5;
        public static final int TRANSACTION_getSignature = 7;
        public static final int TRANSACTION_onError = 2;
        public static final int TRANSACTION_onStatusChange = 3;
        public static final int TRANSACTION_onSuccess = 1;
        public static final int TRANSACTION_setCertificateChain = 6;

        class Proxy implements IServiceCertProvisionListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public boolean attestKey(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public ParcelableCertificate[] getCertificateChain(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelableCertificate[]) parcelObtain2.createTypedArray(ParcelableCertificate.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IServiceCertProvisionListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public byte[] getSignature(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public void onError(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public void onStatusChange(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public void onSuccess(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
            public boolean setCertificateChain(String str, ParcelableCertificate[] parcelableCertificateArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IServiceCertProvisionListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedArray(parcelableCertificateArr, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IServiceCertProvisionListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IServiceCertProvisionListener)) ? new Proxy(iBinder) : (IServiceCertProvisionListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    boolean zAttestKey = attestKey(parcel.readString(), parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(zAttestKey ? 1 : 0);
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
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IServiceCertProvisionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
        public boolean attestKey(String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
        public ParcelableCertificate[] getCertificateChain(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
        public byte[] getSignature(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
        public boolean setCertificateChain(String str, ParcelableCertificate[] parcelableCertificateArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
        public void onSuccess(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
        public void onError(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.service.IServiceCertProvisionListener
        public void onStatusChange(String str, String str2) throws RemoteException {
        }
    }
}
