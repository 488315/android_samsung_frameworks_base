package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactUceCapability;

/* loaded from: classes4.dex */
public interface IOptionsRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IOptionsRequestCallback";

    public static class Default implements IOptionsRequestCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IOptionsRequestCallback
        public void respondToCapabilityRequest(RcsContactUceCapability rcsContactUceCapability, boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IOptionsRequestCallback
        public void respondToCapabilityRequestWithError(int i, String str) throws RemoteException {
        }
    }

    void respondToCapabilityRequest(RcsContactUceCapability rcsContactUceCapability, boolean z) throws RemoteException;

    void respondToCapabilityRequestWithError(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IOptionsRequestCallback {
        static final int TRANSACTION_respondToCapabilityRequest = 1;
        static final int TRANSACTION_respondToCapabilityRequestWithError = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IOptionsRequestCallback.DESCRIPTOR);
        }

        public static IOptionsRequestCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOptionsRequestCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOptionsRequestCallback)) {
                return (IOptionsRequestCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "respondToCapabilityRequest";
            }
            if (i != 2) {
                return null;
            }
            return "respondToCapabilityRequestWithError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOptionsRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOptionsRequestCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                RcsContactUceCapability rcsContactUceCapability = (RcsContactUceCapability) parcel.readTypedObject(RcsContactUceCapability.CREATOR);
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                respondToCapabilityRequest(rcsContactUceCapability, readBoolean);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                respondToCapabilityRequestWithError(readInt, readString);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IOptionsRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOptionsRequestCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IOptionsRequestCallback
            public void respondToCapabilityRequest(RcsContactUceCapability rcsContactUceCapability, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOptionsRequestCallback.DESCRIPTOR);
                    obtain.writeTypedObject(rcsContactUceCapability, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IOptionsRequestCallback
            public void respondToCapabilityRequestWithError(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOptionsRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
