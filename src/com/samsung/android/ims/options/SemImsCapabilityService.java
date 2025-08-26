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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(SemImsCapabilityService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof SemImsCapabilityService)) {
                return (SemImsCapabilityService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemCapabilities ownCapabilities = getOwnCapabilities(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownCapabilities, 1);
                    return true;
                case 2:
                    String string = parcel.readString();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemCapabilities capabilities = getCapabilities(string, i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilities, 1);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    int i6 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemCapabilities capabilitiesByNumber = getCapabilitiesByNumber(string2, i6, z, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesByNumber, 1);
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    SemCapabilities[] capabilitiesByContactId = getCapabilitiesByContactId(string3, i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(capabilitiesByContactId, 1);
                    return true;
                case 5:
                    SemCapabilityServiceEventListener semCapabilityServiceEventListenerAsInterface = SemCapabilityServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterListener = registerListener(semCapabilityServiceEventListenerAsInterface, i10);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterListener);
                    return true;
                case 6:
                    String string4 = parcel.readString();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterListener(string4, i11);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemCapabilities) parcelObtain2.readTypedObject(SemCapabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities getCapabilities(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemCapabilities) parcelObtain2.readTypedObject(SemCapabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities getCapabilitiesByNumber(String str, int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemCapabilities) parcelObtain2.readTypedObject(SemCapabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public SemCapabilities[] getCapabilitiesByContactId(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemCapabilities[]) parcelObtain2.createTypedArray(SemCapabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public String registerListener(SemCapabilityServiceEventListener semCapabilityServiceEventListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(semCapabilityServiceEventListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.ims.options.SemImsCapabilityService
            public void unregisterListener(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(SemImsCapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
