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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDataService)) {
                return (IDataService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createDataServiceProvider(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDataServiceProvider(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    DataProfile dataProfile = (DataProfile) parcel.readTypedObject(DataProfile.CREATOR);
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    int i7 = parcel.readInt();
                    LinkProperties linkProperties = (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR);
                    int i8 = parcel.readInt();
                    NetworkSliceInfo networkSliceInfo = (NetworkSliceInfo) parcel.readTypedObject(NetworkSliceInfo.CREATOR);
                    TrafficDescriptor trafficDescriptor = (TrafficDescriptor) parcel.readTypedObject(TrafficDescriptor.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    IDataServiceCallback iDataServiceCallbackAsInterface = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setupDataCall(i5, i6, dataProfile, z, z2, i7, linkProperties, i8, networkSliceInfo, trafficDescriptor, z3, iDataServiceCallbackAsInterface);
                    return true;
                case 4:
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    IDataServiceCallback iDataServiceCallbackAsInterface2 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deactivateDataCall(i9, i10, i11, iDataServiceCallbackAsInterface2);
                    return true;
                case 5:
                    int i12 = parcel.readInt();
                    DataProfile dataProfile2 = (DataProfile) parcel.readTypedObject(DataProfile.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    IDataServiceCallback iDataServiceCallbackAsInterface3 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setInitialAttachApn(i12, dataProfile2, z4, iDataServiceCallbackAsInterface3);
                    return true;
                case 6:
                    int i13 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(DataProfile.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    IDataServiceCallback iDataServiceCallbackAsInterface4 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDataProfile(i13, arrayListCreateTypedArrayList, z5, iDataServiceCallbackAsInterface4);
                    return true;
                case 7:
                    int i14 = parcel.readInt();
                    IDataServiceCallback iDataServiceCallbackAsInterface5 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestDataCallList(i14, iDataServiceCallbackAsInterface5);
                    return true;
                case 8:
                    int i15 = parcel.readInt();
                    IDataServiceCallback iDataServiceCallbackAsInterface6 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForDataCallListChanged(i15, iDataServiceCallbackAsInterface6);
                    return true;
                case 9:
                    int i16 = parcel.readInt();
                    IDataServiceCallback iDataServiceCallbackAsInterface7 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForDataCallListChanged(i16, iDataServiceCallbackAsInterface7);
                    return true;
                case 10:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    IDataServiceCallback iDataServiceCallbackAsInterface8 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startHandover(i17, i18, iDataServiceCallbackAsInterface8);
                    return true;
                case 11:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    IDataServiceCallback iDataServiceCallbackAsInterface9 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelHandover(i19, i20, iDataServiceCallbackAsInterface9);
                    return true;
                case 12:
                    int i21 = parcel.readInt();
                    IDataServiceCallback iDataServiceCallbackAsInterface10 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForUnthrottleApn(i21, iDataServiceCallbackAsInterface10);
                    return true;
                case 13:
                    int i22 = parcel.readInt();
                    IDataServiceCallback iDataServiceCallbackAsInterface11 = IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForUnthrottleApn(i22, iDataServiceCallbackAsInterface11);
                    return true;
                case 14:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestNetworkValidation(i23, i24, iIntegerConsumerAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void removeDataServiceProvider(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setupDataCall(int i, int i2, DataProfile dataProfile, boolean z, boolean z2, int i3, LinkProperties linkProperties, int i4, NetworkSliceInfo networkSliceInfo, TrafficDescriptor trafficDescriptor, boolean z3, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(dataProfile, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(linkProperties, 0);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeTypedObject(networkSliceInfo, 0);
                    parcelObtain.writeTypedObject(trafficDescriptor, 0);
                    parcelObtain.writeBoolean(z3);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void deactivateDataCall(int i, int i2, int i3, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setInitialAttachApn(int i, DataProfile dataProfile, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(dataProfile, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setDataProfile(int i, List<DataProfile> list, boolean z, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void requestDataCallList(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void registerForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void unregisterForDataCallListChanged(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void startHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void cancelHandover(int i, int i2, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void registerForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void unregisterForUnthrottleApn(int i, IDataServiceCallback iDataServiceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void requestNetworkValidation(int i, int i2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
