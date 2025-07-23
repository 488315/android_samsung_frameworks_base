package com.samsung.android.ims.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.ims.options.SemCapabilityServiceEventListener;

/* loaded from: classes6.dex */
public interface SemImsCapabilityService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.options.SemImsCapabilityService";

    public static class Default implements SemImsCapabilityService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public SemCapabilities getCapabilities(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public SemCapabilities[] getCapabilitiesByContactId(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public SemCapabilities getCapabilitiesByNumber(String str, int i, boolean z, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public SemCapabilities getOwnCapabilities(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public String registerListener(SemCapabilityServiceEventListener semCapabilityServiceEventListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.ims.options.SemImsCapabilityService
        public void unregisterListener(String str, int i) throws RemoteException {
        }
    }

    SemCapabilities getCapabilities(String str, int i, int i2) throws RemoteException;

    SemCapabilities[] getCapabilitiesByContactId(String str, int i, int i2) throws RemoteException;

    SemCapabilities getCapabilitiesByNumber(String str, int i, boolean z, int i2) throws RemoteException;

    SemCapabilities getOwnCapabilities(int i) throws RemoteException;

    String registerListener(SemCapabilityServiceEventListener semCapabilityServiceEventListener, int i) throws RemoteException;

    void unregisterListener(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements SemImsCapabilityService {
        static final int TRANSACTION_getCapabilities = 2;
        static final int TRANSACTION_getCapabilitiesByContactId = 4;
        static final int TRANSACTION_getCapabilitiesByNumber = 3;
        static final int TRANSACTION_getOwnCapabilities = 1;
        static final int TRANSACTION_registerListener = 5;
        static final int TRANSACTION_unregisterListener = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, SemImsCapabilityService.DESCRIPTOR);
        }

        public static SemImsCapabilityService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(SemImsCapabilityService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof SemImsCapabilityService)) {
                return (SemImsCapabilityService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getOwnCapabilities";
                case 2:
                    return "getCapabilities";
                case 3:
                    return "getCapabilitiesByNumber";
                case 4:
                    return "getCapabilitiesByContactId";
                case 5:
                    return "registerListener";
                case 6:
                    return "unregisterListener";
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
                parcel.enforceInterface(SemImsCapabilityService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(SemImsCapabilityService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemCapabilities ownCapabilities = getOwnCapabilities(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownCapabilities, 1);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemCapabilities capabilities = getCapabilities(readString, readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilities, 1);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemCapabilities capabilitiesByNumber = getCapabilitiesByNumber(readString2, readInt4, readBoolean, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesByNumber, 1);
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemCapabilities[] capabilitiesByContactId = getCapabilitiesByContactId(readString3, readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(capabilitiesByContactId, 1);
                    return true;
                case 5:
                    SemCapabilityServiceEventListener asInterface = SemCapabilityServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerListener = registerListener(asInterface, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeString(registerListener);
                    return true;
                case 6:
                    String readString4 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterListener(readString4, readInt9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements SemImsCapabilityService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemImsCapabilityService.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities getOwnCapabilities(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemCapabilities) obtain2.readTypedObject(SemCapabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities getCapabilities(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemCapabilities) obtain2.readTypedObject(SemCapabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities getCapabilitiesByNumber(String str, int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemCapabilities) obtain2.readTypedObject(SemCapabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities[] getCapabilitiesByContactId(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemCapabilities[]) obtain2.createTypedArray(SemCapabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public String registerListener(SemCapabilityServiceEventListener semCapabilityServiceEventListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    obtain.writeStrongInterface(semCapabilityServiceEventListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public void unregisterListener(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
