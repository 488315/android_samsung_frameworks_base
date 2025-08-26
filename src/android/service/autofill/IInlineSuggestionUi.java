package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.autofill.ISurfacePackageResultCallback;

/* loaded from: classes3.dex */
public interface IInlineSuggestionUi extends IInterface {
    public static final String DESCRIPTOR = "android.service.autofill.IInlineSuggestionUi";

    public static class Default implements IInlineSuggestionUi {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.IInlineSuggestionUi
        public void getSurfacePackage(ISurfacePackageResultCallback iSurfacePackageResultCallback) throws RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionUi
        public void releaseSurfaceControlViewHost() throws RemoteException {
        }
    }

    void getSurfacePackage(ISurfacePackageResultCallback iSurfacePackageResultCallback) throws RemoteException;

    void releaseSurfaceControlViewHost() throws RemoteException;

    public static abstract class Stub extends Binder implements IInlineSuggestionUi {
        static final int TRANSACTION_getSurfacePackage = 1;
        static final int TRANSACTION_releaseSurfaceControlViewHost = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IInlineSuggestionUi.DESCRIPTOR);
        }

        public static IInlineSuggestionUi asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInlineSuggestionUi.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInlineSuggestionUi)) {
                return (IInlineSuggestionUi) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getSurfacePackage";
            }
            if (i != 2) {
                return null;
            }
            return "releaseSurfaceControlViewHost";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInlineSuggestionUi.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInlineSuggestionUi.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ISurfacePackageResultCallback iSurfacePackageResultCallbackAsInterface = ISurfacePackageResultCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getSurfacePackage(iSurfacePackageResultCallbackAsInterface);
            } else if (i == 2) {
                releaseSurfaceControlViewHost();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInlineSuggestionUi {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineSuggestionUi.DESCRIPTOR;
            }

            @Override // android.service.autofill.IInlineSuggestionUi
            public void getSurfacePackage(ISurfacePackageResultCallback iSurfacePackageResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionUi.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSurfacePackageResultCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionUi
            public void releaseSurfaceControlViewHost() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionUi.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
