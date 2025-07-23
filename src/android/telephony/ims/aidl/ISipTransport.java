package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.DelegateRequest;
import android.telephony.ims.aidl.ISipDelegate;
import android.telephony.ims.aidl.ISipDelegateMessageCallback;
import android.telephony.ims.aidl.ISipDelegateStateCallback;

/* loaded from: classes4.dex */
public interface ISipTransport extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISipTransport";

    public static class Default implements ISipTransport {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.ISipTransport
        public void createSipDelegate(int i, DelegateRequest delegateRequest, ISipDelegateStateCallback iSipDelegateStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISipTransport
        public void destroySipDelegate(ISipDelegate iSipDelegate, int i) throws RemoteException {
        }
    }

    void createSipDelegate(int i, DelegateRequest delegateRequest, ISipDelegateStateCallback iSipDelegateStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) throws RemoteException;

    void destroySipDelegate(ISipDelegate iSipDelegate, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISipTransport {
        static final int TRANSACTION_createSipDelegate = 1;
        static final int TRANSACTION_destroySipDelegate = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISipTransport.DESCRIPTOR);
        }

        public static ISipTransport asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISipTransport.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISipTransport)) {
                return (ISipTransport) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "createSipDelegate";
            }
            if (i != 2) {
                return null;
            }
            return "destroySipDelegate";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISipTransport.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISipTransport.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                DelegateRequest delegateRequest = (DelegateRequest) parcel.readTypedObject(DelegateRequest.CREATOR);
                ISipDelegateStateCallback asInterface = ISipDelegateStateCallback.Stub.asInterface(parcel.readStrongBinder());
                ISipDelegateMessageCallback asInterface2 = ISipDelegateMessageCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                createSipDelegate(readInt, delegateRequest, asInterface, asInterface2);
            } else if (i == 2) {
                ISipDelegate asInterface3 = ISipDelegate.Stub.asInterface(parcel.readStrongBinder());
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                destroySipDelegate(asInterface3, readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISipTransport {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISipTransport.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISipTransport
            public void createSipDelegate(int i, DelegateRequest delegateRequest, ISipDelegateStateCallback iSipDelegateStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipTransport.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(delegateRequest, 0);
                    obtain.writeStrongInterface(iSipDelegateStateCallback);
                    obtain.writeStrongInterface(iSipDelegateMessageCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISipTransport
            public void destroySipDelegate(ISipDelegate iSipDelegate, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISipTransport.DESCRIPTOR);
                    obtain.writeStrongInterface(iSipDelegate);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
