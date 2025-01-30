package com.samsung.android.knox.ex.knoxAI;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.ex.knoxAI.IDeathNotifier;
import com.samsung.android.knox.ex.knoxAI.IKeyProvisioningCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IDecryptFramework extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.knoxAI.IDecryptFramework";

    int close(long j);

    long createKnoxAiSession(IDeathNotifier iDeathNotifier);

    int destroyKnoxAiSession(long j);

    int execute(long j, DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2);

    void getKeyProvisioning(IKeyProvisioningCallback iKeyProvisioningCallback);

    int getModelInputShape(long j, int i, int[] iArr);

    int open(long j, KfaOptions kfaOptions);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDecryptFramework {
        public static final int TRANSACTION_close = 6;
        public static final int TRANSACTION_createKnoxAiSession = 1;
        public static final int TRANSACTION_destroyKnoxAiSession = 2;
        public static final int TRANSACTION_execute = 5;
        public static final int TRANSACTION_getKeyProvisioning = 7;
        public static final int TRANSACTION_getModelInputShape = 3;
        public static final int TRANSACTION_open = 4;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDecryptFramework {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public final int close(long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final long createKnoxAiSession(IDeathNotifier iDeathNotifier) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int destroyKnoxAiSession(long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int execute(long j, DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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

            public final String getInterfaceDescriptor() {
                return IDecryptFramework.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
            public final void getKeyProvisioning(IKeyProvisioningCallback iKeyProvisioningCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int getModelInputShape(long j, int i, int[] iArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
            public final int open(long j, KfaOptions kfaOptions) {
                Parcel obtain = Parcel.obtain(this.mRemote);
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
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDecryptFramework {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public final int close(long j) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public final long createKnoxAiSession(IDeathNotifier iDeathNotifier) {
            return 0L;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public final int destroyKnoxAiSession(long j) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public final int execute(long j, DataBuffer[] dataBufferArr, DataBuffer[] dataBufferArr2) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public final int getModelInputShape(long j, int i, int[] iArr) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public final int open(long j, KfaOptions kfaOptions) {
            return 0;
        }

        @Override // com.samsung.android.knox.ex.knoxAI.IDecryptFramework
        public final void getKeyProvisioning(IKeyProvisioningCallback iKeyProvisioningCallback) {
        }
    }
}
