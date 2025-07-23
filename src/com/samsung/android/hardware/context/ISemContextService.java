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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemContextService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemContextService)) {
                return (ISemContextService) queryLocalInterface;
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
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt = parcel.readInt();
                    SemContextAttribute semContextAttribute = (SemContextAttribute) parcel.readTypedObject(SemContextAttribute.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerCallback(readStrongBinder, readInt, semContextAttribute, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean unregisterCallback = unregisterCallback(readStrongBinder2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterCallback);
                    return true;
                case 3:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    initializeService(readStrongBinder3, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt4 = parcel.readInt();
                    SemContextAttribute semContextAttribute2 = (SemContextAttribute) parcel.readTypedObject(SemContextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean changeParameters = changeParameters(readStrongBinder4, readInt4, semContextAttribute2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(changeParameters);
                    return true;
                case 5:
                    Map availableServiceMap = getAvailableServiceMap();
                    parcel2.writeNoException();
                    parcel2.writeMap(availableServiceMap);
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean referenceData = setReferenceData(readInt5, readInt6, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(referenceData);
                    return true;
                case 7:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestToUpdate(readStrongBinder5, readInt7, readString2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestHistoryData(readStrongBinder6, readInt8, readString3);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(semContextAttribute, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean unregisterCallback(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void initializeService(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean changeParameters(IBinder iBinder, int i, SemContextAttribute semContextAttribute) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(semContextAttribute, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public Map getAvailableServiceMap() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public boolean setReferenceData(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void requestToUpdate(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public void requestHistoryData(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextService
            public String getCurrentServiceList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemContextService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
