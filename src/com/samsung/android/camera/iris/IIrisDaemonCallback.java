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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIrisDaemonCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIrisDaemonCallback)) {
                return (IIrisDaemonCallback) queryLocalInterface;
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
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(readLong, readInt, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long readLong2 = parcel.readLong();
                    EyeInfo eyeInfo = (EyeInfo) parcel.readTypedObject(EyeInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAcquired(readLong2, eyeInfo);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    long readLong3 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onAuthenticated(readLong3, readInt4, readInt5, createByteArray, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long readLong4 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readLong4, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    long readLong5 = parcel.readLong();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(readLong5, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    long readLong6 = parcel.readLong();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onIRPropertyChanged(readLong6, readString, readString2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    long readLong7 = parcel.readLong();
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onEnumerate(readLong7, createIntArray, createIntArray2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    long readLong8 = parcel.readLong();
                    int readInt9 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImageProcessed(readLong8, readInt9, createByteArray3, readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    long readLong9 = parcel.readLong();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onGeneralParameterChanged(readLong9, readString3, readString4);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onAcquired(long j, EyeInfo eyeInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(eyeInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onAuthenticated(long j, int i, int i2, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onError(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onRemoved(long j, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onIRPropertyChanged(long j, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onEnumerate(long j, int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onImageProcessed(long j, int i, byte[] bArr, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.camera.iris.IIrisDaemonCallback
            public void onGeneralParameterChanged(long j, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisDaemonCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
