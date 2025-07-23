package android.telephony.data;

import android.net.LinkProperties;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.data.IDataServiceCallback;
import com.android.internal.telephony.IIntegerConsumer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IDataService extends IInterface {

    public static class Default implements IDataService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.data.IDataService
        public void cancelHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void createDataServiceProvider(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void deactivateDataCall(int i, int i2, int i3, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void registerForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void registerForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void removeDataServiceProvider(int i) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void requestDataCallList(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void requestNetworkValidation(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void setDataProfile(int i, List<DataProfile> list, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void setInitialAttachApn(int i, DataProfile dataProfile, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void setupDataCall(int i, int i2, DataProfile dataProfile, boolean z, boolean z2, int i3, LinkProperties linkProperties, int i4, NetworkSliceInfo networkSliceInfo, TrafficDescriptor trafficDescriptor, boolean z3, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void startHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void unregisterForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void unregisterForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
        }
    }

    void cancelHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void createDataServiceProvider(int i) throws RemoteException;

    void deactivateDataCall(int i, int i2, int i3, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void registerForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void registerForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void removeDataServiceProvider(int i) throws RemoteException;

    void requestDataCallList(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void requestNetworkValidation(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void setDataProfile(int i, List<DataProfile> list, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void setInitialAttachApn(int i, DataProfile dataProfile, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void setupDataCall(int i, int i2, DataProfile dataProfile, boolean z, boolean z2, int i3, LinkProperties linkProperties, int i4, NetworkSliceInfo networkSliceInfo, TrafficDescriptor trafficDescriptor, boolean z3, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void startHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void unregisterForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    void unregisterForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataService {
        public static final String DESCRIPTOR = "android.telephony.data.IDataService";
        static final int TRANSACTION_cancelHandover = 11;
        static final int TRANSACTION_createDataServiceProvider = 1;
        static final int TRANSACTION_deactivateDataCall = 4;
        static final int TRANSACTION_registerForDataCallListChanged = 8;
        static final int TRANSACTION_registerForUnthrottleApn = 12;
        static final int TRANSACTION_removeDataServiceProvider = 2;
        static final int TRANSACTION_requestDataCallList = 7;
        static final int TRANSACTION_requestNetworkValidation = 14;
        static final int TRANSACTION_setDataProfile = 6;
        static final int TRANSACTION_setInitialAttachApn = 5;
        static final int TRANSACTION_setupDataCall = 3;
        static final int TRANSACTION_startHandover = 10;
        static final int TRANSACTION_unregisterForDataCallListChanged = 9;
        static final int TRANSACTION_unregisterForUnthrottleApn = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDataService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDataService)) {
                return (IDataService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createDataServiceProvider";
                case 2:
                    return "removeDataServiceProvider";
                case 3:
                    return "setupDataCall";
                case 4:
                    return "deactivateDataCall";
                case 5:
                    return "setInitialAttachApn";
                case 6:
                    return "setDataProfile";
                case 7:
                    return "requestDataCallList";
                case 8:
                    return "registerForDataCallListChanged";
                case 9:
                    return "unregisterForDataCallListChanged";
                case 10:
                    return "startHandover";
                case 11:
                    return "cancelHandover";
                case 12:
                    return "registerForUnthrottleApn";
                case 13:
                    return "unregisterForUnthrottleApn";
                case 14:
                    return "requestNetworkValidation";
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
                    parcel.enforceNoDataAvail();
                    createDataServiceProvider(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDataServiceProvider(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    DataProfile dataProfile = (DataProfile) parcel.readTypedObject(DataProfile.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    LinkProperties linkProperties = (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR);
                    int readInt6 = parcel.readInt();
                    NetworkSliceInfo networkSliceInfo = (NetworkSliceInfo) parcel.readTypedObject(NetworkSliceInfo.CREATOR);
                    TrafficDescriptor trafficDescriptor = (TrafficDescriptor) parcel.readTypedObject(TrafficDescriptor.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    IDataServiceCallback asInterface = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setupDataCall(readInt3, readInt4, dataProfile, readBoolean, readBoolean2, readInt5, linkProperties, readInt6, networkSliceInfo, trafficDescriptor, readBoolean3, asInterface);
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    IDataServiceCallback asInterface2 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deactivateDataCall(readInt7, readInt8, readInt9, asInterface2);
                    return true;
                case 5:
                    int readInt10 = parcel.readInt();
                    DataProfile dataProfile2 = (DataProfile) parcel.readTypedObject(DataProfile.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    IDataServiceCallback asInterface3 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setInitialAttachApn(readInt10, dataProfile2, readBoolean4, asInterface3);
                    return true;
                case 6:
                    int readInt11 = parcel.readInt();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(DataProfile.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    IDataServiceCallback asInterface4 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDataProfile(readInt11, createTypedArrayList, readBoolean5, asInterface4);
                    return true;
                case 7:
                    int readInt12 = parcel.readInt();
                    IDataServiceCallback asInterface5 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestDataCallList(readInt12, asInterface5);
                    return true;
                case 8:
                    int readInt13 = parcel.readInt();
                    IDataServiceCallback asInterface6 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForDataCallListChanged(readInt13, asInterface6);
                    return true;
                case 9:
                    int readInt14 = parcel.readInt();
                    IDataServiceCallback asInterface7 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForDataCallListChanged(readInt14, asInterface7);
                    return true;
                case 10:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    IDataServiceCallback asInterface8 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startHandover(readInt15, readInt16, asInterface8);
                    return true;
                case 11:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    IDataServiceCallback asInterface9 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelHandover(readInt17, readInt18, asInterface9);
                    return true;
                case 12:
                    int readInt19 = parcel.readInt();
                    IDataServiceCallback asInterface10 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForUnthrottleApn(readInt19, asInterface10);
                    return true;
                case 13:
                    int readInt20 = parcel.readInt();
                    IDataServiceCallback asInterface11 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForUnthrottleApn(readInt20, asInterface11);
                    return true;
                case 14:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    IIntegerConsumer asInterface12 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestNetworkValidation(readInt21, readInt22, asInterface12);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDataService {
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

            @Override // android.telephony.data.IDataService
            public void createDataServiceProvider(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void removeDataServiceProvider(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setupDataCall(int i, int i2, DataProfile dataProfile, boolean z, boolean z2, int i3, LinkProperties linkProperties, int i4, NetworkSliceInfo networkSliceInfo, TrafficDescriptor trafficDescriptor, boolean z3, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(dataProfile, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(linkProperties, 0);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(networkSliceInfo, 0);
                    obtain.writeTypedObject(trafficDescriptor, 0);
                    obtain.writeBoolean(z3);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void deactivateDataCall(int i, int i2, int i3, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setInitialAttachApn(int i, DataProfile dataProfile, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataProfile, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setDataProfile(int i, List<DataProfile> list, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void requestDataCallList(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void registerForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void unregisterForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void startHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void cancelHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void registerForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void unregisterForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void requestNetworkValidation(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
