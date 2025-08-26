package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IApplicationStartInfoCompleteListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.IApplicationStartInfoCompleteListener";

    public static class Default implements IApplicationStartInfoCompleteListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IApplicationStartInfoCompleteListener
        public void onApplicationStartInfoComplete(ApplicationStartInfo applicationStartInfo) throws RemoteException {
        }
    }

    void onApplicationStartInfoComplete(ApplicationStartInfo applicationStartInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IApplicationStartInfoCompleteListener {
        static final int TRANSACTION_onApplicationStartInfoComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IApplicationStartInfoCompleteListener.DESCRIPTOR);
        }

        public static IApplicationStartInfoCompleteListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IApplicationStartInfoCompleteListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IApplicationStartInfoCompleteListener)) {
                return (IApplicationStartInfoCompleteListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onApplicationStartInfoComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IApplicationStartInfoCompleteListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IApplicationStartInfoCompleteListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ApplicationStartInfo applicationStartInfo = (ApplicationStartInfo) parcel.readTypedObject(ApplicationStartInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onApplicationStartInfoComplete(applicationStartInfo);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IApplicationStartInfoCompleteListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IApplicationStartInfoCompleteListener.DESCRIPTOR;
            }

            @Override // android.app.IApplicationStartInfoCompleteListener
            public void onApplicationStartInfoComplete(ApplicationStartInfo applicationStartInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IApplicationStartInfoCompleteListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(applicationStartInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
