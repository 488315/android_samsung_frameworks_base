package com.samsung.android.knox.knoxanalyticsproxy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IKnoxAnalyticsProxy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy";

    public static class Default implements IKnoxAnalyticsProxy {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy
        public void log(KnoxAnalyticsData knoxAnalyticsData) throws RemoteException {
        }
    }

    void log(KnoxAnalyticsData knoxAnalyticsData) throws RemoteException;

    public static abstract class Stub extends Binder implements IKnoxAnalyticsProxy {
        static final int TRANSACTION_log = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKnoxAnalyticsProxy.DESCRIPTOR);
        }

        public static IKnoxAnalyticsProxy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxAnalyticsProxy.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKnoxAnalyticsProxy)) {
                return (IKnoxAnalyticsProxy) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "log";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxAnalyticsProxy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxAnalyticsProxy.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                KnoxAnalyticsData knoxAnalyticsData = (KnoxAnalyticsData) parcel.readTypedObject(KnoxAnalyticsData.CREATOR);
                parcel.enforceNoDataAvail();
                log(knoxAnalyticsData);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKnoxAnalyticsProxy {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKnoxAnalyticsProxy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.knoxanalyticsproxy.IKnoxAnalyticsProxy
            public void log(KnoxAnalyticsData knoxAnalyticsData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxAnalyticsProxy.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knoxAnalyticsData, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
