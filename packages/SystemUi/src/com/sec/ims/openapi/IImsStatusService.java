package com.sec.ims.openapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.IImsRegistrationListener;
import com.sec.ims.volte2.IImsCallEventListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IImsStatusService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.openapi.IImsStatusService";

    int[] getCallCount();

    void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener);

    void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener);

    void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener);

    void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IImsStatusService {
        static final int TRANSACTION_getCallCount = 5;
        static final int TRANSACTION_registerImsCallEventListener = 3;
        static final int TRANSACTION_registerImsRegistrationListener = 1;
        static final int TRANSACTION_unregisterImsCallEventListener = 4;
        static final int TRANSACTION_unregisterImsRegistrationListener = 2;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IImsStatusService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public int[] getCallCount() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsStatusService.DESCRIPTOR;
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IImsStatusService
            public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsStatusService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsStatusService.DESCRIPTOR);
        }

        public static IImsStatusService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsStatusService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsStatusService)) ? new Proxy(iBinder) : (IImsStatusService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsStatusService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsStatusService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IImsRegistrationListener asInterface = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerImsRegistrationListener(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IImsRegistrationListener asInterface2 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterImsRegistrationListener(asInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                IImsCallEventListener asInterface3 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                registerImsCallEventListener(asInterface3);
                parcel2.writeNoException();
            } else if (i == 4) {
                IImsCallEventListener asInterface4 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                unregisterImsCallEventListener(asInterface4);
                parcel2.writeNoException();
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                int[] callCount = getCallCount();
                parcel2.writeNoException();
                parcel2.writeIntArray(callCount);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IImsStatusService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public int[] getCallCount() {
            return null;
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener) {
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener) {
        }

        @Override // com.sec.ims.openapi.IImsStatusService
        public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        }
    }
}
