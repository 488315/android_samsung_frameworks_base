package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactTerminatedReason;
import android.telephony.ims.SipDetails;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISubscribeResponseCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.ISubscribeResponseCallback";

    public static class Default implements ISubscribeResponseCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onCommandError(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onNetworkResponse(SipDetails sipDetails) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onNotifyCapabilitiesUpdate(List<String> list) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onResourceTerminated(List<RcsContactTerminatedReason> list) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
        public void onTerminated(String str, long j) throws RemoteException {
        }
    }

    void onCommandError(int i) throws RemoteException;

    void onNetworkResponse(SipDetails sipDetails) throws RemoteException;

    void onNotifyCapabilitiesUpdate(List<String> list) throws RemoteException;

    void onResourceTerminated(List<RcsContactTerminatedReason> list) throws RemoteException;

    void onTerminated(String str, long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ISubscribeResponseCallback {
        static final int TRANSACTION_onCommandError = 1;
        static final int TRANSACTION_onNetworkResponse = 2;
        static final int TRANSACTION_onNotifyCapabilitiesUpdate = 3;
        static final int TRANSACTION_onResourceTerminated = 4;
        static final int TRANSACTION_onTerminated = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISubscribeResponseCallback.DESCRIPTOR);
        }

        public static ISubscribeResponseCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISubscribeResponseCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISubscribeResponseCallback)) {
                return (ISubscribeResponseCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCommandError";
            }
            if (i == 2) {
                return "onNetworkResponse";
            }
            if (i == 3) {
                return "onNotifyCapabilitiesUpdate";
            }
            if (i == 4) {
                return "onResourceTerminated";
            }
            if (i != 5) {
                return null;
            }
            return "onTerminated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISubscribeResponseCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISubscribeResponseCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCommandError(readInt);
            } else if (i == 2) {
                SipDetails sipDetails = (SipDetails) parcel.readTypedObject(SipDetails.CREATOR);
                parcel.enforceNoDataAvail();
                onNetworkResponse(sipDetails);
            } else if (i == 3) {
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                parcel.enforceNoDataAvail();
                onNotifyCapabilitiesUpdate(createStringArrayList);
            } else if (i == 4) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(RcsContactTerminatedReason.CREATOR);
                parcel.enforceNoDataAvail();
                onResourceTerminated(createTypedArrayList);
            } else if (i == 5) {
                String readString = parcel.readString();
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                onTerminated(readString, readLong);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISubscribeResponseCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISubscribeResponseCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onCommandError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onNetworkResponse(SipDetails sipDetails) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeTypedObject(sipDetails, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onNotifyCapabilitiesUpdate(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onResourceTerminated(List<RcsContactTerminatedReason> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.ISubscribeResponseCallback
            public void onTerminated(String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISubscribeResponseCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
