package android.media.tv.extension.event;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IEventMonitorListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.event.IEventMonitorListener";

    public static class Default implements IEventMonitorListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.event.IEventMonitorListener
        public void onInfoChanged(long j, Bundle bundle) throws RemoteException {
        }
    }

    void onInfoChanged(long j, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IEventMonitorListener {
        static final int TRANSACTION_onInfoChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.event.IEventMonitorListener");
        }

        public static IEventMonitorListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.event.IEventMonitorListener");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IEventMonitorListener)) {
                return (IEventMonitorListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onInfoChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.event.IEventMonitorListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.event.IEventMonitorListener");
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onInfoChanged(j, bundle);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IEventMonitorListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.event.IEventMonitorListener";
            }

            @Override // android.media.tv.extension.event.IEventMonitorListener
            public void onInfoChanged(long j, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.event.IEventMonitorListener");
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
