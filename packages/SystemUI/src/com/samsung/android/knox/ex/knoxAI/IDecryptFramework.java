package com.samsung.android.knox.ex.knoxAI;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ex.knoxAI.IDeathNotifier;
import com.samsung.android.knox.ex.knoxAI.IKeyProvisioningCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IDecryptFramework extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.knoxAI.IDecryptFramework";

    int close(long j) throws RemoteException;

    long createKnoxAiSession(IDeathNotifier iDeathNotifier) throws RemoteException;

    int destroyKnoxAiSession(long j) throws RemoteException;

    int execute(long j, DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2) throws RemoteException;

    void getKeyProvisioning(IKeyProvisioningCallback iKeyProvisioningCallback) throws RemoteException;

    int getModelInputShape(long j, int i, int[] iArr) throws RemoteException;

    int open(long j, KfaOptions kfaOptions) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IDecryptFramework {
        public static final int TRANSACTION_close = 6;
        public static final int TRANSACTION_createKnoxAiSession = 1;
        public static final int TRANSACTION_destroyKnoxAiSession = 2;
        public static final int TRANSACTION_execute = 5;
        public static final int TRANSACTION_getKeyProvisioning = 7;
        public static final int TRANSACTION_getModelInputShape = 3;
        public static final int TRANSACTION_open = 4;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IDecryptFramework {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int close(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public long createKnoxAiSession(IDeathNotifier iDeathNotifier) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    obtain.writeStrongInterface(iDeathNotifier);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int destroyKnoxAiSession(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int execute(long j, DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedArray(dataBufferArr, 0);
                    obtain.writeTypedArray(dataBufferArr2, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readTypedArray(dataBufferArr2, DataBuffer.CREATOR);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IDecryptFramework.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public void getKeyProvisioning(IKeyProvisioningCallback iKeyProvisioningCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyProvisioningCallback);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int getModelInputShape(long j, int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readIntArray(iArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int open(long j, KfaOptions kfaOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(kfaOptions, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDecryptFramework.DESCRIPTOR);
        }

        public static IDecryptFramework asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDecryptFramework.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDecryptFramework)) ? new Proxy(iBinder) : (IDecryptFramework) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDecryptFramework.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDecryptFramework.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IDeathNotifier asInterface = IDeathNotifier.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long createKnoxAiSession = createKnoxAiSession(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeLong(createKnoxAiSession);
                    return true;
                case 2:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int destroyKnoxAiSession = destroyKnoxAiSession(readLong);
                    parcel2.writeNoException();
                    parcel2.writeInt(destroyKnoxAiSession);
                    return true;
                case 3:
                    long readLong2 = parcel.readLong();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    if (readInt2 > 1000000) {
                        throw new BadParcelableException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(readInt2, "Array too large: "));
                    }
                    int[] iArr = readInt2 < 0 ? null : new int[readInt2];
                    parcel.enforceNoDataAvail();
                    int modelInputShape = getModelInputShape(readLong2, readInt, iArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(modelInputShape);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 4:
                    long readLong3 = parcel.readLong();
                    KfaOptions kfaOptions = (KfaOptions) parcel.readTypedObject(KfaOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    int open = open(readLong3, kfaOptions);
                    parcel2.writeNoException();
                    parcel2.writeInt(open);
                    return true;
                case 5:
                    long readLong4 = parcel.readLong();
                    Parcelable.Creator<DataBuffer> creator = DataBuffer.CREATOR;
                    DataBuffer[] dataBufferArr = (DataBuffer[]) parcel.createTypedArray(creator);
                    DataBuffer[] dataBufferArr2 = (DataBuffer[]) parcel.createTypedArray(creator);
                    parcel.enforceNoDataAvail();
                    int execute = execute(readLong4, dataBufferArr, dataBufferArr2);
                    parcel2.writeNoException();
                    parcel2.writeInt(execute);
                    parcel2.writeTypedArray(dataBufferArr2, 1);
                    return true;
                case 6:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int close = close(readLong5);
                    parcel2.writeNoException();
                    parcel2.writeInt(close);
                    return true;
                case 7:
                    IKeyProvisioningCallback asInterface2 = IKeyProvisioningCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getKeyProvisioning(asInterface2);
                    parcel2.writeNoException();
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IDecryptFramework {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public int close(long j) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public long createKnoxAiSession(IDeathNotifier iDeathNotifier) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public int destroyKnoxAiSession(long j) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public int execute(long j, DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public int getModelInputShape(long j, int i, int[] iArr) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public int open(long j, KfaOptions kfaOptions) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public void getKeyProvisioning(IKeyProvisioningCallback iKeyProvisioningCallback) throws RemoteException {
        }
    }
}
