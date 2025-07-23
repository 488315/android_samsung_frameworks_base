package android.service.contentcapture;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IContentProtectionService extends IInterface {
    public static final String DESCRIPTOR = "android.service.contentcapture.IContentProtectionService";

    public static class Default implements IContentProtectionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.contentcapture.IContentProtectionService
        public void onLoginDetected(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.service.contentcapture.IContentProtectionService
        public void onUpdateAllowlistRequest(IBinder iBinder) throws RemoteException {
        }
    }

    void onLoginDetected(ParceledListSlice parceledListSlice) throws RemoteException;

    void onUpdateAllowlistRequest(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IContentProtectionService {
        static final int TRANSACTION_onLoginDetected = 1;
        static final int TRANSACTION_onUpdateAllowlistRequest = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IContentProtectionService.DESCRIPTOR);
        }

        public static IContentProtectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContentProtectionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContentProtectionService)) {
                return (IContentProtectionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onLoginDetected";
            }
            if (i != 2) {
                return null;
            }
            return "onUpdateAllowlistRequest";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContentProtectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContentProtectionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                parcel.enforceNoDataAvail();
                onLoginDetected(parceledListSlice);
            } else if (i == 2) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onUpdateAllowlistRequest(readStrongBinder);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContentProtectionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContentProtectionService.DESCRIPTOR;
            }

            @Override // android.service.contentcapture.IContentProtectionService
            public void onLoginDetected(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentProtectionService.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.contentcapture.IContentProtectionService
            public void onUpdateAllowlistRequest(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IContentProtectionService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
