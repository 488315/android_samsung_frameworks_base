package android.companion.virtual;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IVirtualDeviceIntentInterceptor extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.IVirtualDeviceIntentInterceptor";

    public static class Default implements IVirtualDeviceIntentInterceptor {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
        public void onIntentIntercepted(Intent intent) throws RemoteException {
        }
    }

    void onIntentIntercepted(Intent intent) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDeviceIntentInterceptor {
        static final int TRANSACTION_onIntentIntercepted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IVirtualDeviceIntentInterceptor.DESCRIPTOR);
        }

        public static IVirtualDeviceIntentInterceptor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVirtualDeviceIntentInterceptor.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVirtualDeviceIntentInterceptor)) {
                return (IVirtualDeviceIntentInterceptor) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onIntentIntercepted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVirtualDeviceIntentInterceptor.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualDeviceIntentInterceptor.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                parcel.enforceNoDataAvail();
                onIntentIntercepted(intent);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IVirtualDeviceIntentInterceptor {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDeviceIntentInterceptor.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
            public void onIntentIntercepted(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDeviceIntentInterceptor.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
