package com.samsung.android.hardware.context;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

/* loaded from: classes6.dex */
public interface ISemContextService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hardware.context.ISemContextService";

    public static class Default implements ISemContextService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public boolean changeParameters(IBinder iBinder, int i, SemContextAttribute semContextAttribute) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public Map getAvailableServiceMap() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public String getCurrentServiceList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public void initializeService(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public void registerCallback(IBinder iBinder, int i, SemContextAttribute semContextAttribute, String str) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public void requestHistoryData(IBinder iBinder, int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public void requestToUpdate(IBinder iBinder, int i, String str) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public boolean setReferenceData(int i, int i2, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.hardware.context.ISemContextService
        public boolean unregisterCallback(IBinder iBinder, int i) throws RemoteException {
            return false;
        }
    }

    boolean changeParameters(IBinder iBinder, int i, SemContextAttribute semContextAttribute) throws RemoteException;

    Map getAvailableServiceMap() throws RemoteException;

    String getCurrentServiceList() throws RemoteException;

    void initializeService(IBinder iBinder, int i) throws RemoteException;

    void registerCallback(IBinder iBinder, int i, SemContextAttribute semContextAttribute, String str) throws RemoteException;

    void requestHistoryData(IBinder iBinder, int i, String str) throws RemoteException;

    void requestToUpdate(IBinder iBinder, int i, String str) throws RemoteException;

    boolean setReferenceData(int i, int i2, byte[] bArr) throws RemoteException;

    boolean unregisterCallback(IBinder iBinder, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemContextService {
        static final int TRANSACTION_changeParameters = 4;
        static final int TRANSACTION_getAvailableServiceMap = 5;
        static final int TRANSACTION_getCurrentServiceList = 9;
        static final int TRANSACTION_initializeService = 3;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_requestHistoryData = 8;
        static final int TRANSACTION_requestToUpdate = 7;
        static final int TRANSACTION_setReferenceData = 6;
        static final int TRANSACTION_unregisterCallback = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ISemContextService.DESCRIPTOR);
        }

        public static ISemContextService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemContextService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemContextService)) {
                return (ISemContextService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "initializeService";
                case 4:
                    return "changeParameters";
                case 5:
                    return "getAvailableServiceMap";
                case 6:
                    return "setReferenceData";
                case 7:
                    return "requestToUpdate";
                case 8:
                    return "requestHistoryData";
                case 9:
                    return "getCurrentServiceList";
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
                parcel.enforceInterface(ISemContextService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemContextService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i3 = parcel.readInt();
                    SemContextAttribute semContextAttribute = (SemContextAttribute) parcel.readTypedObject(SemContextAttribute.CREATOR);
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerCallback(strongBinder, i3, semContextAttribute, string);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterCallback = unregisterCallback(strongBinder2, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterCallback);
                    return true;
                case 3:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initializeService(strongBinder3, i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i6 = parcel.readInt();
                    SemContextAttribute semContextAttribute2 = (SemContextAttribute) parcel.readTypedObject(SemContextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zChangeParameters = changeParameters(strongBinder4, i6, semContextAttribute2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zChangeParameters);
                    return true;
                case 5:
                    Map availableServiceMap = getAvailableServiceMap();
                    parcel2.writeNoException();
                    parcel2.writeMap(availableServiceMap);
                    return true;
                case 6:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean referenceData = setReferenceData(i7, i8, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(referenceData);
                    return true;
                case 7:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    int i9 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestToUpdate(strongBinder5, i9, string2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    int i10 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestHistoryData(strongBinder6, i10, string3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String currentServiceList = getCurrentServiceList();
                    parcel2.writeNoException();
                    parcel2.writeString(currentServiceList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemContextService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContextService.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void registerCallback(IBinder iBinder, int i, SemContextAttribute semContextAttribute, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(semContextAttribute, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean unregisterCallback(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void initializeService(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean changeParameters(IBinder iBinder, int i, SemContextAttribute semContextAttribute) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(semContextAttribute, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public Map getAvailableServiceMap() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean setReferenceData(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void requestToUpdate(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void requestHistoryData(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public String getCurrentServiceList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
