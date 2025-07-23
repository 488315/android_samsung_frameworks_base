package android.telephony.data;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IDataServiceCallback extends IInterface {

    public static class Default implements IDataServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onApnUnthrottled(String str) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onDataCallListChanged(List<DataCallResponse> list) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onDataProfileUnthrottled(DataProfile dataProfile) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onDeactivateDataCallComplete(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onHandoverCancelled(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onHandoverStarted(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onRequestDataCallListComplete(int i, List<DataCallResponse> list) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onSetDataProfileComplete(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onSetInitialAttachApnComplete(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IDataServiceCallback
        public void onSetupDataCallComplete(int i, DataCallResponse dataCallResponse) throws RemoteException {
        }
    }

    void onApnUnthrottled(String str) throws RemoteException;

    void onDataCallListChanged(List<DataCallResponse> list) throws RemoteException;

    void onDataProfileUnthrottled(DataProfile dataProfile) throws RemoteException;

    void onDeactivateDataCallComplete(int i) throws RemoteException;

    void onHandoverCancelled(int i) throws RemoteException;

    void onHandoverStarted(int i) throws RemoteException;

    void onRequestDataCallListComplete(int i, List<DataCallResponse> list) throws RemoteException;

    void onSetDataProfileComplete(int i) throws RemoteException;

    void onSetInitialAttachApnComplete(int i) throws RemoteException;

    void onSetupDataCallComplete(int i, DataCallResponse dataCallResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataServiceCallback {
        public static final String DESCRIPTOR = "android.telephony.data.IDataServiceCallback";
        static final int TRANSACTION_onApnUnthrottled = 9;
        static final int TRANSACTION_onDataCallListChanged = 6;
        static final int TRANSACTION_onDataProfileUnthrottled = 10;
        static final int TRANSACTION_onDeactivateDataCallComplete = 2;
        static final int TRANSACTION_onHandoverCancelled = 8;
        static final int TRANSACTION_onHandoverStarted = 7;
        static final int TRANSACTION_onRequestDataCallListComplete = 5;
        static final int TRANSACTION_onSetDataProfileComplete = 4;
        static final int TRANSACTION_onSetInitialAttachApnComplete = 3;
        static final int TRANSACTION_onSetupDataCallComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDataServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDataServiceCallback)) {
                return (IDataServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSetupDataCallComplete";
                case 2:
                    return "onDeactivateDataCallComplete";
                case 3:
                    return "onSetInitialAttachApnComplete";
                case 4:
                    return "onSetDataProfileComplete";
                case 5:
                    return "onRequestDataCallListComplete";
                case 6:
                    return "onDataCallListChanged";
                case 7:
                    return "onHandoverStarted";
                case 8:
                    return "onHandoverCancelled";
                case 9:
                    return "onApnUnthrottled";
                case 10:
                    return "onDataProfileUnthrottled";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    DataCallResponse dataCallResponse = (DataCallResponse) parcel.readTypedObject(DataCallResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetupDataCallComplete(readInt, dataCallResponse);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDeactivateDataCallComplete(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetInitialAttachApnComplete(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetDataProfileComplete(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(DataCallResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestDataCallListComplete(readInt5, createTypedArrayList);
                    return true;
                case 6:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(DataCallResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDataCallListChanged(createTypedArrayList2);
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHandoverStarted(readInt6);
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHandoverCancelled(readInt7);
                    return true;
                case 9:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onApnUnthrottled(readString);
                    return true;
                case 10:
                    DataProfile dataProfile = (DataProfile) parcel.readTypedObject(DataProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDataProfileUnthrottled(dataProfile);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDataServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onSetupDataCallComplete(int i, DataCallResponse dataCallResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataCallResponse, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onDeactivateDataCallComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onSetInitialAttachApnComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onSetDataProfileComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onRequestDataCallListComplete(int i, List<DataCallResponse> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onDataCallListChanged(List<DataCallResponse> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onHandoverStarted(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onHandoverCancelled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onApnUnthrottled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataServiceCallback
            public void onDataProfileUnthrottled(DataProfile dataProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(dataProfile, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
