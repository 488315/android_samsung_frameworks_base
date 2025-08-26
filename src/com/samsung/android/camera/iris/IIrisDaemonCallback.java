package com.samsung.android.camera.iris;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIrisDaemonCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisDaemonCallback";

    public static class Default implements IIrisDaemonCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onAcquired(long j, EyeInfo eyeInfo) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onAuthenticated(long j, int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onEnrollResult(long j, int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onEnumerate(long j, int[] iArr, int[] iArr2) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onError(long j, int i) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onGeneralParameterChanged(long j, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onIRPropertyChanged(long j, String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onImageProcessed(long j, int i, byte[] bArr, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
        public void onRemoved(long j, int i, int i2) throws RemoteException {
        }
    }

    void onAcquired(long j, EyeInfo eyeInfo) throws RemoteException;

    void onAuthenticated(long j, int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    void onEnrollResult(long j, int i, int i2, int i3) throws RemoteException;

    void onEnumerate(long j, int[] iArr, int[] iArr2) throws RemoteException;

    void onError(long j, int i) throws RemoteException;

    void onGeneralParameterChanged(long j, String str, String str2) throws RemoteException;

    void onIRPropertyChanged(long j, String str, String str2) throws RemoteException;

    void onImageProcessed(long j, int i, byte[] bArr, int i2, int i3) throws RemoteException;

    void onRemoved(long j, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IIrisDaemonCallback {
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticated = 3;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onEnumerate = 7;
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onGeneralParameterChanged = 9;
        static final int TRANSACTION_onIRPropertyChanged = 6;
        static final int TRANSACTION_onImageProcessed = 8;
        static final int TRANSACTION_onRemoved = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, IIrisDaemonCallback.DESCRIPTOR);
        }

        public static IIrisDaemonCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIrisDaemonCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIrisDaemonCallback)) {
                return (IIrisDaemonCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEnrollResult";
                case 2:
                    return "onAcquired";
                case 3:
                    return "onAuthenticated";
                case 4:
                    return "onError";
                case 5:
                    return "onRemoved";
                case 6:
                    return "onIRPropertyChanged";
                case 7:
                    return "onEnumerate";
                case 8:
                    return "onImageProcessed";
                case 9:
                    return "onGeneralParameterChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIrisDaemonCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIrisDaemonCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long j = parcel.readLong();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(j, i3, i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long j2 = parcel.readLong();
                    EyeInfo eyeInfo = (EyeInfo) parcel.readTypedObject(EyeInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAcquired(j2, eyeInfo);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    long j3 = parcel.readLong();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onAuthenticated(j3, i6, i7, bArrCreateByteArray, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long j4 = parcel.readLong();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(j4, i8);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    long j5 = parcel.readLong();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(j5, i9, i10);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    long j6 = parcel.readLong();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onIRPropertyChanged(j6, string, string2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    long j7 = parcel.readLong();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onEnumerate(j7, iArrCreateIntArray, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    long j8 = parcel.readLong();
                    int i11 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImageProcessed(j8, i11, bArrCreateByteArray3, i12, i13);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    long j9 = parcel.readLong();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onGeneralParameterChanged(j9, string3, string4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIrisDaemonCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIrisDaemonCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onEnrollResult(long j, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onAcquired(long j, EyeInfo eyeInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(eyeInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onAuthenticated(long j, int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onError(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onRemoved(long j, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onIRPropertyChanged(long j, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onEnumerate(long j, int[] iArr, int[] iArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onImageProcessed(long j, int i, byte[] bArr, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onGeneralParameterChanged(long j, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
