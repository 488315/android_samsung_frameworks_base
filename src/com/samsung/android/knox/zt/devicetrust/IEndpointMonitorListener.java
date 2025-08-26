package com.samsung.android.knox.zt.devicetrust;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IEndpointMonitorListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener";

    public static class Default implements IEndpointMonitorListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
        public void onEvent(int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
        public void onEventGeneralized(int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
        public void onEventSimplified(int i, String str) throws RemoteException {
        }
    }

    void onEvent(int i, Bundle bundle) throws RemoteException;

    void onEventGeneralized(int i, String str) throws RemoteException;

    void onEventSimplified(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IEndpointMonitorListener {
        static final int TRANSACTION_onEvent = 3;
        static final int TRANSACTION_onEventGeneralized = 2;
        static final int TRANSACTION_onEventSimplified = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IEndpointMonitorListener.DESCRIPTOR);
        }

        public static IEndpointMonitorListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IEndpointMonitorListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEndpointMonitorListener)) {
                return (IEndpointMonitorListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onEventSimplified";
            }
            if (i == 2) {
                return "onEventGeneralized";
            }
            if (i != 3) {
                return null;
            }
            return "onEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEndpointMonitorListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEndpointMonitorListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onEventSimplified(i3, string);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onEventGeneralized(i4, string2);
            } else if (i == 3) {
                int i5 = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onEvent(i5, bundle);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IEndpointMonitorListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEndpointMonitorListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
            public void onEventSimplified(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEndpointMonitorListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
            public void onEventGeneralized(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEndpointMonitorListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener
            public void onEvent(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IEndpointMonitorListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
