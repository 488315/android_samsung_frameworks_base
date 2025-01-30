package com.samsung.android.knox.kpm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.kpm.IKnoxPushServiceCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IKnoxPushService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.kpm.IKnoxPushService";

    void isRegistered(IKnoxPushServiceCallback iKnoxPushServiceCallback);

    void registerDevice(boolean z, IKnoxPushServiceCallback iKnoxPushServiceCallback);

    void unRegisterDevice(IKnoxPushServiceCallback iKnoxPushServiceCallback);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IKnoxPushService {
        public static final int TRANSACTION_isRegistered = 3;
        public static final int TRANSACTION_registerDevice = 1;
        public static final int TRANSACTION_unRegisterDevice = 2;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IKnoxPushService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IKnoxPushService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushService
            public final void isRegistered(IKnoxPushServiceCallback iKnoxPushServiceCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    obtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushService
            public final void registerDevice(boolean z, IKnoxPushServiceCallback iKnoxPushServiceCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.kpm.IKnoxPushService
            public final void unRegisterDevice(IKnoxPushServiceCallback iKnoxPushServiceCallback) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxPushService.DESCRIPTOR);
                    obtain.writeStrongInterface(iKnoxPushServiceCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxPushService.DESCRIPTOR);
        }

        public static IKnoxPushService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxPushService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxPushService)) ? new Proxy(iBinder) : (IKnoxPushService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxPushService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxPushService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                IKnoxPushServiceCallback asInterface = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerDevice(readBoolean, asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IKnoxPushServiceCallback asInterface2 = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unRegisterDevice(asInterface2);
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                IKnoxPushServiceCallback asInterface3 = IKnoxPushServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                isRegistered(asInterface3);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IKnoxPushService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushService
        public final void isRegistered(IKnoxPushServiceCallback iKnoxPushServiceCallback) {
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushService
        public final void unRegisterDevice(IKnoxPushServiceCallback iKnoxPushServiceCallback) {
        }

        @Override // com.samsung.android.knox.kpm.IKnoxPushService
        public final void registerDevice(boolean z, IKnoxPushServiceCallback iKnoxPushServiceCallback) {
        }
    }
}
