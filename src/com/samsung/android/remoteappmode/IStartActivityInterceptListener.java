package com.samsung.android.remoteappmode;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IStartActivityInterceptListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.remoteappmode.IStartActivityInterceptListener";

    public static class Default implements IStartActivityInterceptListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.remoteappmode.IStartActivityInterceptListener
        public void onStartActivityIntercepted(Intent intent, Bundle bundle, ActivityInfo activityInfo, int i, boolean z, int i2, int i3, int i4) throws RemoteException {
        }
    }

    void onStartActivityIntercepted(Intent intent, Bundle bundle, ActivityInfo activityInfo, int i, boolean z, int i2, int i3, int i4) throws RemoteException;

    public static abstract class Stub extends Binder implements IStartActivityInterceptListener {
        static final int TRANSACTION_onStartActivityIntercepted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IStartActivityInterceptListener.DESCRIPTOR);
        }

        public static IStartActivityInterceptListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IStartActivityInterceptListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStartActivityInterceptListener)) {
                return (IStartActivityInterceptListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStartActivityIntercepted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStartActivityInterceptListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStartActivityInterceptListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
                int readInt = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStartActivityIntercepted(intent, bundle, activityInfo, readInt, readBoolean, readInt2, readInt3, readInt4);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStartActivityInterceptListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStartActivityInterceptListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IStartActivityInterceptListener
            public void onStartActivityIntercepted(Intent intent, Bundle bundle, ActivityInfo activityInfo, int i, boolean z, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IStartActivityInterceptListener.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(activityInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
