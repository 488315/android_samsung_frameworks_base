package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDeviceIdleControllerAdapter extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.IDeviceIdleControllerAdapter";

    public static class Default implements IDeviceIdleControllerAdapter {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IDeviceIdleControllerAdapter
        public void exemptAppTemporarilyForEvent(String str, long j, int i, String str2) throws RemoteException {
        }
    }

    void exemptAppTemporarilyForEvent(String str, long j, int i, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceIdleControllerAdapter {
        static final int TRANSACTION_exemptAppTemporarilyForEvent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDeviceIdleControllerAdapter.DESCRIPTOR);
        }

        public static IDeviceIdleControllerAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDeviceIdleControllerAdapter.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDeviceIdleControllerAdapter)) {
                return (IDeviceIdleControllerAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "exemptAppTemporarilyForEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceIdleControllerAdapter.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceIdleControllerAdapter.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                exemptAppTemporarilyForEvent(readString, readLong, readInt, readString2);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDeviceIdleControllerAdapter {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceIdleControllerAdapter.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IDeviceIdleControllerAdapter
            public void exemptAppTemporarilyForEvent(String str, long j, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceIdleControllerAdapter.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
