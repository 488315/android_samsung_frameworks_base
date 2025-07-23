package com.android.ims.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;

/* loaded from: classes5.dex */
public interface IImsRegistrationListener extends IInterface {

    public static class Default implements IImsRegistrationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationAssociatedUriChanged(Uri[] uriArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationChangeFailed(int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationConnected() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationConnectedWithRadioTech(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationDisconnected(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationFeatureCapabilityChanged(int i, int[] iArr, int[] iArr2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationProgressing() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationProgressingWithRadioTech(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationResumed() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationServiceCapabilityChanged(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void registrationSuspended() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsRegistrationListener
        public void voiceMessageCountUpdate(int i) throws RemoteException {
        }
    }

    void registrationAssociatedUriChanged(Uri[] uriArr) throws RemoteException;

    void registrationChangeFailed(int i, ImsReasonInfo imsReasonInfo) throws RemoteException;

    @Deprecated
    void registrationConnected() throws RemoteException;

    void registrationConnectedWithRadioTech(int i) throws RemoteException;

    void registrationDisconnected(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void registrationFeatureCapabilityChanged(int i, int[] iArr, int[] iArr2) throws RemoteException;

    @Deprecated
    void registrationProgressing() throws RemoteException;

    void registrationProgressingWithRadioTech(int i) throws RemoteException;

    void registrationResumed() throws RemoteException;

    void registrationServiceCapabilityChanged(int i, int i2) throws RemoteException;

    void registrationSuspended() throws RemoteException;

    void voiceMessageCountUpdate(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsRegistrationListener {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsRegistrationListener";
        static final int TRANSACTION_registrationAssociatedUriChanged = 11;
        static final int TRANSACTION_registrationChangeFailed = 12;
        static final int TRANSACTION_registrationConnected = 1;
        static final int TRANSACTION_registrationConnectedWithRadioTech = 3;
        static final int TRANSACTION_registrationDisconnected = 5;
        static final int TRANSACTION_registrationFeatureCapabilityChanged = 9;
        static final int TRANSACTION_registrationProgressing = 2;
        static final int TRANSACTION_registrationProgressingWithRadioTech = 4;
        static final int TRANSACTION_registrationResumed = 6;
        static final int TRANSACTION_registrationServiceCapabilityChanged = 8;
        static final int TRANSACTION_registrationSuspended = 7;
        static final int TRANSACTION_voiceMessageCountUpdate = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsRegistrationListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsRegistrationListener)) {
                return (IImsRegistrationListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registrationConnected";
                case 2:
                    return "registrationProgressing";
                case 3:
                    return "registrationConnectedWithRadioTech";
                case 4:
                    return "registrationProgressingWithRadioTech";
                case 5:
                    return "registrationDisconnected";
                case 6:
                    return "registrationResumed";
                case 7:
                    return "registrationSuspended";
                case 8:
                    return "registrationServiceCapabilityChanged";
                case 9:
                    return "registrationFeatureCapabilityChanged";
                case 10:
                    return "voiceMessageCountUpdate";
                case 11:
                    return "registrationAssociatedUriChanged";
                case 12:
                    return "registrationChangeFailed";
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
                    registrationConnected();
                    return true;
                case 2:
                    registrationProgressing();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registrationConnectedWithRadioTech(readInt);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registrationProgressingWithRadioTech(readInt2);
                    return true;
                case 5:
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    registrationDisconnected(imsReasonInfo);
                    return true;
                case 6:
                    registrationResumed();
                    return true;
                case 7:
                    registrationSuspended();
                    return true;
                case 8:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registrationServiceCapabilityChanged(readInt3, readInt4);
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    registrationFeatureCapabilityChanged(readInt5, createIntArray, createIntArray2);
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    voiceMessageCountUpdate(readInt6);
                    return true;
                case 11:
                    Uri[] uriArr = (Uri[]) parcel.createTypedArray(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    registrationAssociatedUriChanged(uriArr);
                    return true;
                case 12:
                    int readInt7 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo2 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    registrationChangeFailed(readInt7, imsReasonInfo2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsRegistrationListener {
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

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationConnected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationProgressing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationConnectedWithRadioTech(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationProgressingWithRadioTech(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationDisconnected(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationResumed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationSuspended() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationServiceCapabilityChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationFeatureCapabilityChanged(int i, int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void voiceMessageCountUpdate(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationAssociatedUriChanged(Uri[] uriArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(uriArr, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsRegistrationListener
            public void registrationChangeFailed(int i, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
