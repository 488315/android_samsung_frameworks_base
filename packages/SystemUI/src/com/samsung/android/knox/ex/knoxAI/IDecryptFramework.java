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

    public abstract class Stub extends Binder implements IDecryptFramework {
        public static final int TRANSACTION_close = 6;
        public static final int TRANSACTION_createKnoxAiSession = 1;
        public static final int TRANSACTION_destroyKnoxAiSession = 2;
        public static final int TRANSACTION_execute = 5;
        public static final int TRANSACTION_getKeyProvisioning = 7;
        public static final int TRANSACTION_getModelInputShape = 3;
        public static final int TRANSACTION_open = 4;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public long createKnoxAiSession(IDeathNotifier iDeathNotifier) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iDeathNotifier);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int destroyKnoxAiSession(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int execute(long j, DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedArray(dataBufferArr, 0);
                    parcelObtain.writeTypedArray(dataBufferArr2, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    parcelObtain2.readTypedArray(dataBufferArr2, DataBuffer.CREATOR);
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IDecryptFramework.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public void getKeyProvisioning(IKeyProvisioningCallback iKeyProvisioningCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iKeyProvisioningCallback);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int getModelInputShape(long j, int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(iArr.length);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readIntArray(iArr);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public int open(long j, KfaOptions kfaOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDecryptFramework.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(kfaOptions, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDecryptFramework.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDecryptFramework)) ? new Proxy(iBinder) : (IDecryptFramework) iInterfaceQueryLocalInterface;
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
                    IDeathNotifier iDeathNotifierAsInterface = IDeathNotifier.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long jCreateKnoxAiSession = createKnoxAiSession(iDeathNotifierAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeLong(jCreateKnoxAiSession);
                    return true;
                case 2:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int iDestroyKnoxAiSession = destroyKnoxAiSession(j);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDestroyKnoxAiSession);
                    return true;
                case 3:
                    long j2 = parcel.readLong();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    if (i4 > 1000000) {
                        throw new BadParcelableException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "Array too large: "));
                    }
                    int[] iArr = i4 < 0 ? null : new int[i4];
                    parcel.enforceNoDataAvail();
                    int modelInputShape = getModelInputShape(j2, i3, iArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(modelInputShape);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 4:
                    long j3 = parcel.readLong();
                    KfaOptions kfaOptions = (KfaOptions) parcel.readTypedObject(KfaOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iOpen = open(j3, kfaOptions);
                    parcel2.writeNoException();
                    parcel2.writeInt(iOpen);
                    return true;
                case 5:
                    long j4 = parcel.readLong();
                    Parcelable.Creator<DataBuffer> creator = DataBuffer.CREATOR;
                    DataBuffer[] dataBufferArr = (DataBuffer[]) parcel.createTypedArray(creator);
                    DataBuffer[] dataBufferArr2 = (DataBuffer[]) parcel.createTypedArray(creator);
                    parcel.enforceNoDataAvail();
                    int iExecute = execute(j4, dataBufferArr, dataBufferArr2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iExecute);
                    parcel2.writeTypedArray(dataBufferArr2, 1);
                    return true;
                case 6:
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int iClose = close(j5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iClose);
                    return true;
                case 7:
                    IKeyProvisioningCallback iKeyProvisioningCallbackAsInterface = IKeyProvisioningCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getKeyProvisioning(iKeyProvisioningCallbackAsInterface);
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
